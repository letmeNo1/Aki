import org.opencv.core.*;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.features2d.SIFT;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.MinMaxLocResult;
import static org.opencv.features2d.Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS;
import static org.opencv.features2d.Features2d.drawMatches;
import static org.opencv.highgui.HighGui.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;


class testOpenCV2 {
    public static void main(String[] args) throws Exception {
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java454.dll");
        System.load(url.getPath());
        //获取原图
        Mat img = imread("C:\\Users\\CNHAHUA16\\Desktop\\lena.png");
        //获取用于定位的图片
        Mat template = imread("C:\\Users\\CNHAHUA16\\Desktop\\face.png");
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
        double minDist = 1000;

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
            Double dist = (double) mats[i].distance;
            System.out.printf(i + " match distance best : %s%n", dist);
            if (dist <= threshold)
            {
                bestMatches.add(mats[i]);
                System.out.printf(i + " best match added : %s%n", dist);
            }
        }
        md.fromList(bestMatches);
        System.out.println(bestMatches.size());
        Mat res = new Mat();
        Scalar color = new Scalar(-1);
        MatOfByte matchesMask = new MatOfByte();
        drawMatches(img,keypoints1,template,keypoints2,md,res,color,color,matchesMask,DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        namedWindow("picture of matching");
        imshow("picture of matching", res);
        waitKey(0);
    }
}