package aki.OpenCV;

import aki.Mac.CallQuartzWindowServices;
import aki.Windows.CallGdi32Util;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class CallOpenCV {
    private String getCurrentScreen(){
        String fileName =System.getProperty("java.io.tmpdir")+"CurrentScreenCapture.png";
        if(System.getProperty("os.name").contains("Windows")){
            CallGdi32Util.takeScreenshotForDesktop(fileName);
        }else {
            CallQuartzWindowServices.takeScreenshotForDesktop(fileName);
        }
        return  fileName;
    }

    public Point getKnnMatches(String imagePath){
        System.load(System.getProperties().getProperty("user.dir") + "/src/main/java/lib/opencv/opencv_java454.dll");
        //获取原图
        Mat imgObject = imread(getCurrentScreen());
        //获取用于定位的图片
        Mat imgScene = imread(imagePath);
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
        MatOfDMatch goodMatches = new MatOfDMatch();

        //将最佳匹配转回 MatOfDMatch
        goodMatches.fromList(listOfGoodMatches);

        //将原图上的特征值转为数组
        List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();

        float x=0,y=0;
        for (DMatch listOfGoodMatch : listOfGoodMatches) {
            //获取原图上匹配到的特征值的坐标的合集
            x+=listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.x;
            y+=listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt.y;
        }
        //求坐标平均值
        x=x/listOfGoodMatches.size();
        y=y/listOfGoodMatches.size();
        return new Point(x,y);
    }

    public static void main(String[] args) throws Exception {
        CallOpenCV ss = new CallOpenCV();
        ss.getCurrentScreen();
    }
}