import aki.OpenCV.KMean.Cluster;
import aki.OpenCV.KMean.KMeansRun;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class test2 {
    public static void main(String[] args) throws Exception {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        Mat imgObject = imread("C:\\Users\\CNHAHUA16\\Desktop\\1.png",IMREAD_GRAYSCALE);
        //获取用于定位的图片
        Mat imgScene = imread("C:\\Users\\CNHAHUA16\\Desktop\\2.png",IMREAD_GRAYSCALE);

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

        //将最佳匹配转回 MatOfDMatch
        goodMatches.fromList(listOfGoodMatches);

        //将原图上的特征值转为数组
        List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();

        //获取原图上匹配到的特征值的坐标的合集
        ArrayList<float[]> dataSet = new ArrayList<>();
        for (DMatch listOfGoodMatch : listOfGoodMatches) {
            dataSet.add(new float[]{(float) listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.x, (float) listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.y});
        }
        KMeansRun kRun =new KMeansRun(3, dataSet);

        Set<Cluster> clusterSet = kRun.run();
        ArrayList<Point> pointArray = new ArrayList<>();
        for (Cluster cluster : clusterSet) {
            Point point = new Point(cluster.getCenter().getlocalArray()[0],cluster.getCenter().getlocalArray()[1]);
            pointArray.add(point);
        }

        System.out.println(pointArray);
    }
}
