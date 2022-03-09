package aki.Common;

import aki.Mac.UIElementRef;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

import static aki.Common.ClockRef.clock;


public class WaitFun {

    UIElementRef findElementByWait(BiFunction<UIElementRef, String, UIElementRef> function, UIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(ClockRef.setTimeout(timeout));
        Instant start = Instant.now();
        while(true){
            new TraceLog().logInfo("Looking for element..");
            UIElementRef res = function.apply(element, attribute);
            if(res != null){
                Instant finish = Instant.now();
                BigDecimal timeElapsed = new BigDecimal(Duration.between(start, finish).toMillis()).divide(new BigDecimal(1000));
                new TraceLog().logInfo(String.format("Found element in %ss",timeElapsed));
                return res;
            }
            if (end.isBefore(clock.instant())) {
                BigDecimal time = new BigDecimal(timeout).divide(new BigDecimal(1000));
                throw new RuntimeException(String.format("Find element timed out, can't find element By `%s` in %ss",attribute, time));
            }
        }
    }
}
