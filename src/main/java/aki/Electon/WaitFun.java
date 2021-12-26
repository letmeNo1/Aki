package aki.Electon;

import aki.Electon.UIElementRef;
import com.sun.jna.platform.win32.WinDef;
import org.opencv.core.Point;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface WaitFun {
    Clock clock = Clock.systemDefaultZone();

    static Duration setTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

    default UIElementRef findElementByWait(Function< String, Point> function, String imagePath, int timeout) throws RuntimeException {
        Instant end = clock.instant().plus(setTimeout(timeout));
        while(true){
            Point res = function.apply(imagePath);
            if(!Double.isNaN(res.x)&&res.x!=0.0&&res.y!=0.0){
                UIElementRef uiElementRef = new UIElementRef();
                uiElementRef.setXY(new int[]{(int)res.x,(int)res.y});
                return uiElementRef;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException("Find element timed out, can't find element `%s` By");
            }
        }
    }
}
