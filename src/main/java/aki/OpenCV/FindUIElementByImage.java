package aki.OpenCV;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FindUIElementByImage {
    static Point findElementByImage(OptionOfFindByImage optionOfFindByImage){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatches(optionOfFindByImage.getImagePath(), optionOfFindByImage.getRatioThreshValue());
    }

    Function<OptionOfFindByImage, Point> findElementByImage = FindUIElementByImage::findElementByImage;


    static ArrayList<Point> findElementsByImage(OptionOfFindByImage optionOfFindByImage){
        CallOpenCV openCV =new CallOpenCV();
        return openCV.getKnnMatchesMultiple(optionOfFindByImage.getImagePath(), optionOfFindByImage.getRatioThreshValue(), optionOfFindByImage.getCluster());
    }

    Function<OptionOfFindByImage, ArrayList<Point>> findElementsByImage = FindUIElementByImage::findElementsByImage;

}
