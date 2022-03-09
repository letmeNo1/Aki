package aki.OpenCV;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FindUIElementByImage {
    static Point findElementByImage(String imagePath){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatches(imagePath);
    }

    Function<String, Point> findElementByImage = FindUIElementByImage::findElementByImage;


    static ArrayList<Point> findElementsByImage(String imagePath, int k){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatchesMultiple(imagePath, k);
    }

    BiFunction<String, Integer, ArrayList<Point>> findElementsByImage = FindUIElementByImage::findElementsByImage;

}
