import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.calib3d.Calib3d.RANSAC;
import static org.opencv.calib3d.Calib3d.findHomography;
import static org.opencv.core.Core.FILLED;
import static org.opencv.core.Core.perspectiveTransform;
import static org.opencv.features2d.Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS;
import static org.opencv.features2d.Features2d.drawMatches;
import static org.opencv.highgui.HighGui.*;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.circle;
//import static org.opencv.imgcodecs.Imgcodecs;


class testOpenCV3 {
    public static void main(String[] args) throws Exception {
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java454.dll");
        System.load(url.getPath());
        //获取原图
        Mat imgObject = imread("C:\\Users\\CNHAHUA16\\Desktop\\lena.png",IMREAD_GRAYSCALE);
        //获取用于定位的图片
        Mat imgScene = imread("C:\\Users\\CNHAHUA16\\Desktop\\face.png",IMREAD_GRAYSCALE);
        if (imgObject.empty() || imgScene.empty()) {
            System.err.println("Cannot read images!");
            System.exit(0);
        }
        //-- Step 1: Detect the keypoints using SURF Detector, compute the descriptors
        SIFT sift = SIFT.create(0, 3, 0.04, 10, 1.6);
        MatOfKeyPoint keypointsObject = new MatOfKeyPoint(), keypointsScene = new MatOfKeyPoint();
        Mat descriptorsObject = new Mat(), descriptorsScene = new Mat();
        sift.detectAndCompute(imgObject, new Mat(), keypointsObject, descriptorsObject);
        sift.detectAndCompute(imgScene, new Mat(), keypointsScene, descriptorsScene);
        //-- Step 2: Matching descriptor vectors with a FLANN based matcher
        // Since SURF is a floating-point descriptor NORM_L2 is used
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2);
        //-- Filter matches using the Lowe's ratio test
        float ratioThresh = 0.75f;
        List<DMatch> listOfGoodMatches = new ArrayList<>();
        for (MatOfDMatch knnMatch : knnMatches) {
            if (knnMatch.rows() > 1) {
                DMatch[] matches = knnMatch.toArray();
                if (matches[0].distance < ratioThresh * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(listOfGoodMatches);
        //-- Draw matches
        Mat imgMatches = new Mat();
        drawMatches(imgObject, keypointsObject, imgScene, keypointsScene, goodMatches, imgMatches, Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(), DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        //-- Localize the object
        List<Point> obj = new ArrayList<>();
        List<Point> scene = new ArrayList<>();
        List<KeyPoint> listOfKeypointsObject = keypointsObject.toList();
        List<KeyPoint> listOfKeypointsScene = keypointsScene.toList();
        float x = 0,y=0;
        for (DMatch listOfGoodMatch : listOfGoodMatches) {
            //-- Get the keypoints from the good matches
            obj.add(listOfKeypointsObject.get(listOfGoodMatch.queryIdx).pt);
            scene.add(listOfKeypointsScene.get(listOfGoodMatch.trainIdx).pt);
            x+=listOfKeypointsScene.get(listOfGoodMatch.trainIdx).pt.x;
            y+=listOfKeypointsScene.get(listOfGoodMatch.trainIdx).pt.y;
        }

        x=x/listOfGoodMatches.size();
        y=y/listOfGoodMatches.size();
        System.out.println(x);
        System.out.println(y);

        circle(imgObject, new Point(x,y),10, new Scalar(0,0,255),3,FILLED);
        imshow("location",imgObject);
        HighGui.waitKey(0);//      System.out.println(res.rows());

        MatOfPoint2f objMat = new MatOfPoint2f(), sceneMat = new MatOfPoint2f();
        objMat.fromList(obj);
        sceneMat.fromList(scene);
        double ransacReprojThreshold = 3.0;
        Mat H = findHomography( objMat, sceneMat, RANSAC, ransacReprojThreshold );
        //-- Get the corners from the image_1 ( the object to be "detected" )
        Mat objCorners = new Mat(4, 1, CvType.CV_32FC2), sceneCorners = new Mat();
        float[] objCornersData = new float[(int) (objCorners.total() * objCorners.channels())];
        objCorners.get(0, 0, objCornersData);
        objCornersData[0] = 0;
        objCornersData[1] = 0;
        objCornersData[2] = imgObject.cols();
        objCornersData[3] = 0;
        objCornersData[4] = imgObject.cols();
        objCornersData[5] = imgObject.rows();
        objCornersData[6] = 0;
        objCornersData[7] = imgObject.rows();
        objCorners.put(0, 0, objCornersData);
        Core.perspectiveTransform(objCorners, sceneCorners, H);
        float[] sceneCornersData = new float[(int) (sceneCorners.total() * sceneCorners.channels())];
        sceneCorners.get(0, 0, sceneCornersData);
        //-- Draw lines between the corners (the mapped object in the scene - image_2 )
        Imgproc.line(imgMatches, new Point( Math.abs(sceneCornersData[0])/3 , sceneCornersData[1]/3),
                new Point(Math.abs(sceneCornersData[2])/3 , sceneCornersData[3]/3), new Scalar(0, 255, 0), 4);
        Imgproc.line(imgMatches, new Point(sceneCornersData[2]/3 , sceneCornersData[3]/3),
                new Point(sceneCornersData[4]/3 , sceneCornersData[5]/3), new Scalar(0, 255, 0), 4);
        Imgproc.line(imgMatches, new Point(sceneCornersData[4]/3 , sceneCornersData[5]/3),
                new Point(sceneCornersData[6]/3 , sceneCornersData[7]/3), new Scalar(0, 255, 0), 4);
        Imgproc.line(imgMatches, new Point(sceneCornersData[6]/3 , sceneCornersData[7]/3),
                new Point(sceneCornersData[0]/3 , sceneCornersData[1]/3), new Scalar(0, 255, 0), 4);
        //-- Show detected matches
        HighGui.imshow("Good Matches & Object detection", imgMatches);
        HighGui.waitKey(0);
    }
}