import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.net.URL;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;


class testOpenCV {
    public static void main(String[] args) throws Exception {
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java454.dll");
        System.load(url.getPath());
        //获取原图
        Mat img = imread("C:\\Users\\CNHAHUA16\\Desktop\\lena.png");
        //获取用于定位的图片
        Mat template = imread("C:\\Users\\CNHAHUA16\\Desktop\\face.png");
        //计算两图的横纵坐标的差值
        int result_rows = img.rows() - template.rows() + 1;
        int result_cols = img.cols() - template.cols() + 1;

        //创建识别框
        Mat g_result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        if (img.empty()) {
            throw new Exception("image is empty");
        }

        int[] methods = new int[]{TM_CCOEFF, TM_CCOEFF_NORMED, TM_CCORR,
                TM_CCORR_NORMED, TM_SQDIFF, TM_SQDIFF_NORMED};

        matchTemplate(img, template, g_result, TM_SQDIFF_NORMED);
//        for (int method : methods) {
//            Mat img2 = img.clone();
//            matchTemplate(img, template, method)
//        }

//        normalize(g_result, g_result, 0, 1, TM_SQDIFF_NORMED, -1, new Mat());
        Core.MinMaxLocResult mmlr = Core.minMaxLoc(g_result);
        Point matchLocation = mmlr.minLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
        System.out.println(matchLocation);
        Imgproc.rectangle(img, matchLocation,
                new Point(matchLocation.x + template.cols(), matchLocation.y + template.rows()),
                new Scalar(0, 0, 0, 0));

        Imgcodecs.imwrite("C:\\Users\\CNHAHUA16\\Desktop\\face2.png", img);
        // 创建输出单通道图像
//        Mat grayImage = new Mat(image.rows(), image.cols(), CvType.CV_8SC1);
//        // 进行图像色彩空间转换
//        cvtColor(image, grayImage, COLOR_RGB2GRAY);
//
//        imshow("Processed Image", grayImage);
//        imwrite("C:\\Users\\CNHAHUA16\\Desktop\\MicrosoftTeams-image2.png", grayImage);
//        waitKey();
    }
}