import org.opencv.core.*;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.highgui.HighGui;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.FILLED;
import static org.opencv.features2d.Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS;
import static org.opencv.features2d.Features2d.drawMatches;
import static org.opencv.highgui.HighGui.*;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;
//import static org.opencv.imgcodecs.Imgcodecs;

import static org.opencv.imgproc.Imgproc.*;


class testOpenCV2 {
    public static void main(String[] args) throws Exception {
//        URL url = ClassLoader.getSystemResource("resources/lib/opencv/opencv_java454.dll");
//        System.out.println(url);
        System.load(System.getProperties().getProperty("user.dir") + "/src/main/java/lib/opencv/opencv_java454.dll");
        //获取原图
        Mat img = imread("C:\\Users\\CNHAHUA16\\Desktop\\CurrentScreenCapture (2).png",IMREAD_GRAYSCALE);
        //获取用于定位的图片
        Mat template = imread("C:\\Users\\CNHAHUA16\\Documents\\Github\\Aki\\src\\test\\java\\Image\\back.png",IMREAD_GRAYSCALE);


        SIFT sift = SIFT.create(0, 3, 0.04, 10, 1.6);

        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        Mat des1=new Mat();
        Mat des2=new Mat();

        //检测并计算特征描述子
        sift.detectAndCompute(img,new Mat(),keypoints1,des1);
        sift.detectAndCompute(template,new Mat(),keypoints2,des2);


        //创建设置FLANN匹配
        MatOfDMatch md = new MatOfDMatch();
        FlannBasedMatcher matcher=FlannBasedMatcher.create();
        matcher.match(des1, des2, md);//特征描述子匹配

        //找出最优特征点
        double maxDist = 0; //初始化最大最小距离
        double minDist = 500;

        DMatch[] mats = md.toArray();
        List<DMatch> bestMatches= new ArrayList<>();

        for (DMatch mat : mats) {
            double dist = mat.distance;
            if (dist < minDist) {
                minDist = dist;
            }
            if (dist > maxDist) {
                maxDist = dist;
            }
        }
        System.out.println("max_dist : "+maxDist);
        System.out.println("min_dist : "+minDist);

        double threshold = 3 * minDist;
        double threshold2 = 2 * minDist;

        if (threshold2 >= maxDist){
            threshold = minDist * 1.1;
        }
        else if (threshold >= maxDist){
            threshold = threshold2 * 1.4;
        }

        if(0d==threshold) {
            threshold=0.3*maxDist;
        }

        System.out.println("Threshold : "+threshold);
        for (int i = 0; i < mats.length; i++)
        {
            //distance越小,代表DMatch的匹配率越高,
            double dist = mats[i].distance;
            if (dist <= threshold)
            {
                bestMatches.add(mats[i]);
                System.out.printf(i + " best match added : %s%n", dist);
            }
        }
        //将最佳匹配转回 MatOfDMatch
        md.fromList(bestMatches);

        //新建结果mat
        Mat res = new Mat();
        //设置线条颜色
        //绘制匹配线条，去除其他非匹配的点
        drawMatches(img,keypoints1,template,keypoints2,md,res,Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(),DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        namedWindow("picture of matching");
        imshow("picture of matching", res);
        waitKey(0);


        MatOfPoint2f srcPoints = new MatOfPoint2f();
        MatOfPoint2f dstPoints = new MatOfPoint2f();

        List<Point> srcPointsList = new ArrayList<>();
        List<Point> dstPointsList = new ArrayList<>();
        float x=0,y=0;

        for (DMatch bestMatch : bestMatches) {
            //——从最佳匹配中获取关键点
            srcPointsList.add(keypoints1.toList().get(bestMatch.queryIdx).pt);
            dstPointsList.add(keypoints2.toList().get(bestMatch.trainIdx).pt);
            x+=keypoints1.toList().get(bestMatch.queryIdx).pt.x;
            y+=keypoints1.toList().get(bestMatch.queryIdx).pt.y;
        }
        x=x/bestMatches.size();
        y=y/bestMatches.size();
        System.out.println(x);
        System.out.println(y);

        circle(img, new Point(x,y),10, new Scalar(0,0,255),3,FILLED);
        imshow("location",img);
        HighGui.waitKey(0);//      System.out.println(res.rows());


        srcPoints.fromList(srcPointsList);
        dstPoints.fromList(dstPointsList);


        System.out.println(srcPointsList);
        System.out.println(dstPointsList);

    }
}