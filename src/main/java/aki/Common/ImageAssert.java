package aki.Common;

import aki.OpenCV.OptionOfFindByImage;

import static aki.OpenCV.FindUIElementByImage.findElementByImage;
import static aki.Windows.CallUser32.DEFAULT_TIMEOUT;

public interface ImageAssert extends WaitFun {
    default boolean assertElementExistByImage(String imagePath) {
        return assertElementExistByImage(imagePath,0.4f,DEFAULT_TIMEOUT);
    }

    default boolean assertElementExistByImage(String imagePath, int timeout) {
        return assertElementExistByImage(imagePath,0.4f,timeout);
    }

    default boolean assertElementExistByImage(String imagePath, float ratioThreshValue) {
        return assertElementExistByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    default boolean assertElementExistByImage(String imagePath, float ratioThreshValue, int timeout) {
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        boolean res;
        res = AssertElementExistByWait(findElementByImage,option,timeout);
        return  res;
    }
}
