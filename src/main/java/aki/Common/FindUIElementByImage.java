package aki.Common;

import aki.OpenCV.CallOpenCV;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FindUIElementByImage {
    static Point findElementLocationByImage(String imagePath){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatches(imagePath);
    }

    Function<String, Point> findElementLocationByImage = FindUIElementByImage::findElementLocationByImage;


    static ArrayList<Point> findElementsLocationByImage(String imagePath, int k){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatchesMultiple(imagePath, k);
    }

    BiFunction<String, Integer, ArrayList<Point>> findElementsLocationByImage = FindUIElementByImage::findElementsLocationByImage;

}
