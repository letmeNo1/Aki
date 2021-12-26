package aki.Electon;


import aki.OpenCV.CallOpenCV;
import org.opencv.core.Point;


import java.util.function.Function;

public interface FindUIElement {
    static Point findElementLocationByImage(String imagePath){
        CallOpenCV openCV =new CallOpenCV();
        Point point = new Point();
        try{
            point = openCV.getKnnMatches(imagePath);
        }
        catch (Exception ignored){
        }
        return point;
    }

    Function<String, Point> findElementLocationByImage = FindUIElement::findElementLocationByImage;
}
