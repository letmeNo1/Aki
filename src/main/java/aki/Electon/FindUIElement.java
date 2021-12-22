package aki.Electon;


import aki.OpenCV.CallOpenCV;
import org.opencv.core.Point;


import java.util.function.Function;

public interface FindUIElement {
    static Point findElementLocationByImage(String imagePath){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatches(imagePath);
    }

    Function<String, Point> findElementLocationByImage = FindUIElement::findElementLocationByImage;
}
