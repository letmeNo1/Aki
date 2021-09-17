package org.aki.Mac;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

public interface WaitFun {
    Clock clock = Clock.systemDefaultZone();

    default Duration setTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

    default List<UIElementRef> findElementsByWait(BiFunction<UIElementRef, String, List<UIElementRef>> function,
                                                  UIElementRef element, String attribute,
                                                  int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        while(true){
            List<UIElementRef> resList = function.apply(element, attribute);
            if(!resList.isEmpty()){
                return resList;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException(String.format("Find element timed out, can't find element `%s` By",attribute));
            }
        }
    }

    default UIElementRef findElementByWait(BiFunction<UIElementRef, String, UIElementRef> function, UIElementRef element, String attribute, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        while(true){
            UIElementRef res = function.apply(element, attribute);
            if(res != null){
                return res;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException(String.format("Find element timed out, can't find element `%s` By",attribute));
            }
        }
    }
}
