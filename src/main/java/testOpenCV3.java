import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.highgui.HighGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.opencv.calib3d.Calib3d.findHomography;
import static org.opencv.core.Core.FILLED;
import static org.opencv.features2d.Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS;
import static org.opencv.features2d.Features2d.drawMatches;
import static org.opencv.highgui.HighGui.*;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.circle;
//import static org.opencv.imgcodecs.Imgcodecs;


class testOpenCV3 {
    public static void main(String[] args) throws Exception {
        System.load(System.getProperties().getProperty("user.dir") + "/src/main/java/lib/opencv/opencv_java454.dll");
        //获取原图
        Mat imgObject = imread("C:\\Users\\86158\\Desktop\\desktop.png",IMREAD_GRAYSCALE);
        //获取用于定位的图片
        Mat imgScene = imread("C:\\Users\\86158\\Documents\\GitHub\\Aki\\src\\test\\java\\Image\\Play.png",IMREAD_GRAYSCALE);

        if (imgObject.empty() || imgScene.empty()) {
            System.err.println("Cannot read images!");
            System.exit(0);
        }

        //创建Sift检测器
        SIFT sift = SIFT.create(0, 3, 0.04, 10, 1.6);
        //存放imgObject的特征值
        MatOfKeyPoint keypointsObject = new MatOfKeyPoint();
        //存放imgScene的特征值
        MatOfKeyPoint keypointsScene = new MatOfKeyPoint();

        //分析计算两图的特征值矩阵
        Mat descriptorsObject = new Mat(), descriptorsScene = new Mat();
        sift.detectAndCompute(imgObject, new Mat(), keypointsObject, descriptorsObject);
        sift.detectAndCompute(imgScene, new Mat(), keypointsScene, descriptorsScene);

        //使用基于FLANN的匹配器,筛选符合条件的坐标
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2);
        //设置过滤值
        float ratioThresh = 0.4f;

        List<DMatch> listOfGoodMatches = new ArrayList<>();

        for (MatOfDMatch knnMatch : knnMatches) {
            //distance越小匹配度越高
            if (knnMatch.rows() > 1) {
                DMatch[] matches = knnMatch.toArray();
                if (matches[0].distance < ratioThresh * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        if(listOfGoodMatches.size()==0){
            throw new RuntimeException("No match can be found");
        }
        MatOfDMatch goodMatches = new MatOfDMatch();
        Mat res = new Mat();
        goodMatches.fromList(listOfGoodMatches);

        drawMatches(imgObject,keypointsObject,imgScene,keypointsScene,goodMatches,res,Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(),DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        namedWindow("picture of matching");
        imshow("picture of matching", res);
        waitKey(0);
        //将最佳匹配转回 MatOfDMatch

        //将原图上的特征值转为数组
        List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();

        ArrayList<DataNode> dpoints = new ArrayList<>();
        float x=0,y=0;
        for (int i = 0;i<listOfGoodMatches.size();i++) {
            //获取原图上匹配到的特征值的坐标的合集
            dpoints.add(new DataNode("Point-"+i,new double[]{listOfKeypointsObject.get(listOfGoodMatches.get(i).queryIdx).pt.x,listOfKeypointsObject.get(listOfGoodMatches.get(i).queryIdx).pt.y}));
        }

        //使用LOF算法筛除错误的点
        LOF lof = new LOF();
        List<DataNode> nodeList = lof.getOutlierNode(dpoints);
        int j = 0;
        System.out.println(Math.round(nodeList.size()*0.8));
        for (int i =0;i<nodeList.size();i++) {
            if(i>=Math.round(nodeList.size()*0.8)){
                j++;
                x+=nodeList.get(i).getDimensioin()[0];
                y+=nodeList.get(i).getDimensioin()[1];
            }
            System.out.println(nodeList.get(i).getNodeName() + "  " + nodeList.get(i).getLof() + " "+ Arrays.toString(nodeList.get(i).getDimensioin()) + " "+i);
        }
        //求坐标平均值

        x=x/j;
        y=y/j;
        circle(imgObject, new Point(x,y),10, new Scalar(0,0,255),3,FILLED);
        imshow("location",imgObject);
        HighGui.waitKey(0);//      System.out.println(res.rows());

    }
}