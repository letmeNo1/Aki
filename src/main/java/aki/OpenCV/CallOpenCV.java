package aki.OpenCV;

import aki.Mac.CallQuartzWindowServices;
import aki.OpenCV.KMean.Cluster;
import aki.OpenCV.KMean.KMeansRun;
import aki.OpenCV.LOF.DataNode;
import aki.OpenCV.LOF.LOF;
import aki.Windows.CallGdi32Util;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static aki.Android.adbCommand.excuseAdbCommand;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class CallOpenCV {
    public CallOpenCV(){
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    private Mat getCurrentScreen(String platform) {
        String fileName = System.getProperty("java.io.tmpdir") + "CurrentScreenCapture.png";
        Mat mat;
        if (platform.contains("Android")) {
            if(platform.contains("/")){
                String deviceName = platform.split("/")[1];
                excuseAdbCommand(String.format("exec-out screencap -p > %s",System.getProperty("java.io.tmpdir")),deviceName);
            }else{
                excuseAdbCommand(String.format("exec-out screencap -p > %s",System.getProperty("java.io.tmpdir")));
            }
            mat = imread(fileName);
        } else {
            if (System.getProperty("os.name").contains("Windows")) {
                mat = CallGdi32Util.getMatFromScreenshotForDesktop();
            } else {
                CallQuartzWindowServices.takeScreenshotForDesktop(fileName);
                mat = imread(fileName);
            }
        }
        return mat;
    }

    public ArrayList<Point> getKnnMatchesMultiple(String platform,String imgScenePath, float ratioThreshValue, int k){
        return callKnnMatchesMultiple(getCurrentScreen(platform),imgScenePath,ratioThreshValue,k);
    }

    public ArrayList<Point> callKnnMatchesMultiple(Mat imgObject,String imgScenePath, float ratioThreshValue, int k){
    //????????????
    //???????????????????????????
        Mat imgScene = imread(imgScenePath);
        if (imgObject.empty() || imgScene.empty()) {
            System.err.println("Cannot read images!");
            System.exit(0);
        }

        //??????Sift?????????
        SIFT sift = SIFT.create(0, 3, 0.04, 10, 1.6);
        //??????imgObject????????????
        MatOfKeyPoint keypointsObject = new MatOfKeyPoint();
        //??????imgScene????????????
        MatOfKeyPoint keypointsScene = new MatOfKeyPoint();

        //????????????????????????????????????
        Mat descriptorsObject = new Mat(), descriptorsScene = new Mat();
        sift.detectAndCompute(imgObject, new Mat(), keypointsObject, descriptorsObject);
        sift.detectAndCompute(imgScene, new Mat(), keypointsScene, descriptorsScene);

        //????????????FLANN????????????,???????????????????????????
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2);

        //???????????????

            List<DMatch> listOfGoodMatches = new ArrayList<>();

        for (MatOfDMatch knnMatch : knnMatches) {
            //distance?????????????????????
            if (knnMatch.rows() > 1) {
                DMatch[] matches = knnMatch.toArray();
                if (matches[0].distance < ratioThreshValue * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        if(listOfGoodMatches.size()==0){
            return new ArrayList<>();
        }
        MatOfDMatch goodMatches = new MatOfDMatch();

        //????????????????????? MatOfDMatch
        goodMatches.fromList(listOfGoodMatches);

        //????????????????????????????????????
        List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();

        //??????????????????????????????????????????????????????
        ArrayList<float[]> dataSet = new ArrayList<>();
        for (DMatch listOfGoodMatch : listOfGoodMatches) {
            dataSet.add(new float[]{(float) listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.x, (float) listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.y});
        }
        KMeansRun kRun =new KMeansRun(k, dataSet);

        Set<Cluster> clusterSet = kRun.run();
        ArrayList<Point> pointArray = new ArrayList<>();
        for (Cluster cluster : clusterSet) {
            Point point = new Point(cluster.getCenter().getlocalArray()[0],cluster.getCenter().getlocalArray()[1]);
            pointArray.add(point);
        }
        return pointArray;
    }

    public Point getKnnMatches(String platform,String imgScenePath,float ratioThreshValue) {
        return callKnnMatches(getCurrentScreen(platform),imgScenePath, ratioThreshValue);
    }

    public Point callKnnMatches(Mat imgObject,String imgScenePath,float ratioThreshValue){
        //????????????
        //???????????????????????????
        if (imgObject.empty()) {
            System.err.println("Cannot read images!");
            System.exit(0);
        }
        Mat imgScene = imread(imgScenePath);
        if (imgScene.empty()) {
            System.err.println("Cannot read images!");
            System.exit(0);
        }

        //??????Sift?????????
        SIFT sift = SIFT.create(0, 3, 0.04, 10, 1.6);
        //??????imgObject????????????
        MatOfKeyPoint keypointsObject = new MatOfKeyPoint();
        //??????imgScene????????????
        MatOfKeyPoint keypointsScene = new MatOfKeyPoint();

        //????????????????????????????????????
        Mat descriptorsObject = new Mat(), descriptorsScene = new Mat();
        sift.detectAndCompute(imgObject, new Mat(), keypointsObject, descriptorsObject);
        sift.detectAndCompute(imgScene, new Mat(), keypointsScene, descriptorsScene);

        //????????????FLANN????????????,???????????????????????????
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2);

        //???????????????

        List<DMatch> listOfGoodMatches = new ArrayList<>();

        for (MatOfDMatch knnMatch : knnMatches) {
            //distance?????????????????????
            if (knnMatch.rows() > 1) {
                DMatch[] matches = knnMatch.toArray();
                if (matches[0].distance < ratioThreshValue * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        if(listOfGoodMatches.size()<=1){
            float x=-1,y=-1;
            return new Point(x,y);
        }else{
            MatOfDMatch goodMatches = new MatOfDMatch();

            //????????????????????? MatOfDMatch
            goodMatches.fromList(listOfGoodMatches);

            //????????????????????????????????????
            List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();

            //??????????????????????????????????????????????????????
            ArrayList<DataNode> dpoints = new ArrayList<>();
            for (int i = 0;i<listOfGoodMatches.size();i++) {
                dpoints.add(new DataNode("Point-"+i,new double[]{listOfKeypointsObject.get(listOfGoodMatches.get(i).queryIdx).pt.x,listOfKeypointsObject.get(listOfGoodMatches.get(i).queryIdx).pt.y}));
            }

            //??????LOF????????????????????????
            float x=-1,y=-1;
            LOF lof = new LOF();
            List<DataNode> nodeList = lof.getOutlierNode(dpoints);
            int j = 0;
            for (int i =0;i<nodeList.size();i++) {
                if(i>=Math.round(nodeList.size()*0.8)){
                    j++;
                    x+=nodeList.get(i).getDimensioin()[0];
                    y+=nodeList.get(i).getDimensioin()[1];
                }
            }

            //??????????????????
            x=x/j;
            y=y/j;
            return new Point(x,y);
        }
    }
}