package aki.Mac;

import aki.Common.TraceLog;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;
import static aki.Common.ClockRef.setTimeout;

import static aki.Common.ClockRef.clock;

public interface WaitFun {
    TraceLog log = new TraceLog();


    default List<UIElementRef> findElementsByWait(BiFunction<UIElementRef, String, List<UIElementRef>> function,
                                                  UIElementRef element, String attribute,
                                                  int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            List<UIElementRef> resList = function.apply(element, attribute);
            if(!resList.isEmpty()){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element in %ss",timeElapsed));
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }

    default UIElementRef findElementByWait(BiFunction<UIElementRef, String, UIElementRef> function, UIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            log.logInfo("Looking for element..");
            UIElementRef res = function.apply(element, attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                log.logInfo(String.format("Found element in %ss",timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }
}
