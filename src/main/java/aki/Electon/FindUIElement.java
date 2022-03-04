package aki.Electon;


import aki.OpenCV.CallOpenCV;
import org.opencv.core.Point;


import java.util.ArrayList;
import java.util.function.Function;

public interface FindUIElement {
    static Point findElementLocationByImage(String imagePath){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatches(imagePath);
    }

    static ArrayList<Point> findElementsLocationByImage(String imagePath, int k){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatchesMultiple(imagePath, k);
    }

    Function<String, Point> findElementLocationByImage = FindUIElement::findElementLocationByImage;
}
