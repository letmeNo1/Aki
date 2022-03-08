package aki.Common;

import aki.TraceLog;
import org.opencv.core.Point;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface WaitFunForImage {
    Clock clock = Clock.systemDefaultZone();

    static Duration setTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

    TraceLog log = new TraceLog();

    default Point findElementByWait(Function< String, Point> function, String imagePath, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            Point res = function.apply(imagePath);
            if(!Double.isNaN(res.x)){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element in %ss",timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element by image`%s` in %ss",imagePath, time));
            }
        }
    }

    default ArrayList<Point> findPointListByWait(BiFunction< String,Integer,  ArrayList<Point>> function, String imagePath, int k, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            ArrayList<Point> resList = function.apply(imagePath,k);
            if(!resList.isEmpty()){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element in %ss",timeElapsed));
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element by image`%s` in %ss",imagePath, time));
            }
        }
    }
}
