import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.net.URL;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;


class test {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "false");
        System.out.println(System.getProperty("java.library.path"));
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java454.dll");
        System.out.println(url.getPath());
        System.load(url.getPath());
        Mat image = imread("C:\\Users\\CNHAHUA16\\Desktop\\MicrosoftTeams-image.png");
        if (image.empty()) {
            throw new Exception("image is empty");
        }
        imshow("Original Image", image);

        // 创建输出单通道图像
        Mat grayImage = new Mat(image.rows(), image.cols(), CvType.CV_8SC1);
        // 进行图像色彩空间转换
        cvtColor(image, grayImage, COLOR_RGB2GRAY);

        imshow("Processed Image", grayImage);
        imwrite("C:\\Users\\CNHAHUA16\\Desktop\\MicrosoftTeams-image2.png", grayImage);
        waitKey();
    }
}