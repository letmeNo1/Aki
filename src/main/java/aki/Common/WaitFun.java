package aki.Common;

import aki.Mac.MacUIElementRef;
import aki.OpenCV.OptionOfFindByImage;
import aki.Windows.WinUIElementRef;
import com.sun.jna.platform.win32.WinDef;
import org.opencv.core.Point;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static aki.Common.ClockRef.clock;
import static aki.Common.ClockRef.setTimeout;


public interface WaitFun {
    TraceLog log = new TraceLog();

    default List<WinUIElementRef> findElementsByWait(BiFunction<WinUIElementRef, String, List<WinUIElementRef>> function, WinUIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            List<WinUIElementRef> resList = function.apply(element, attribute);
            if(!resList.isEmpty()){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",attribute,timeElapsed));
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }

    default WinUIElementRef findElementByWait(BiFunction<WinUIElementRef, String, WinUIElementRef> function, WinUIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            WinUIElementRef res = function.apply(element, attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",attribute,timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }

    default Boolean AssertElementExistByWait(BiFunction<WinUIElementRef, String, WinUIElementRef> function, WinUIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            WinUIElementRef res = function.apply(element, attribute);
            if(res != null){
                return true;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                log.logInfo(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
                return false;
            }
        }
    }

    static WinDef.HWND findWindowByWait(Function<String, WinDef.HWND> function, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            WinDef.HWND res = function.apply(attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found window in %ss",timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find window timed out, can't find window int %ss",time));
            }
        }
    }

    default Boolean AssertWindowExistByWait(Function<String, WinDef.HWND> function, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            WinDef.HWND res = function.apply(attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found window in %ss",timeElapsed));
                return true;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                log.logInfo(String.format("Find window timed out, can't find window int %ss",time));
                return false;
            }
        }
    }

    default List<MacUIElementRef> findElementsByWait(BiFunction<MacUIElementRef, String, List<MacUIElementRef>> function,
                                                     MacUIElementRef element, String attribute,
                                                     int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            List<MacUIElementRef> resList = function.apply(element, attribute);
            if(!resList.isEmpty()){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",attribute,timeElapsed));
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }

    default MacUIElementRef findElementByWait(BiFunction<MacUIElementRef, String, MacUIElementRef> function, MacUIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            MacUIElementRef res = function.apply(element, attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",attribute,timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }


    default boolean AssertElementExistByWait(BiFunction<MacUIElementRef, String, MacUIElementRef> function, MacUIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            MacUIElementRef res = function.apply(element, attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",attribute,timeElapsed));
                return true;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                log.logInfo(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
                return false;
            }
        }
    }

    default Point findElementByWait(Function<OptionOfFindByImage, Point> function, OptionOfFindByImage optionOfFindByImage, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            Point res = function.apply(optionOfFindByImage);
            if(res.x>=0){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",optionOfFindByImage.getImgScenePath(),timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element by image`%s` in %ss",optionOfFindByImage.getImgScenePath(), time));
            }
        }
    }

    default boolean AssertElementExistByWait(Function<OptionOfFindByImage, Point> function, OptionOfFindByImage option, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            Point res = function.apply(option);
            if(res.x>=0){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",option.getImgScenePath(),timeElapsed));
                return true;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                log.logInfo(String.format("Find element timed out, can't find element by image`%s` in %ss",option.getImgScenePath(), time));
                return false;
            }
        }
    }

    default ArrayList<Point> findPointListByWait(Function<OptionOfFindByImage, ArrayList<Point>> function, OptionOfFindByImage option, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            ArrayList<Point> resList = function.apply(option);
            if(!resList.isEmpty()){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element %s in %ss",option.getImgScenePath(),timeElapsed));
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element by image`%s` in %ss",option.getImgScenePath(), time));
            }
        }
    }
}
