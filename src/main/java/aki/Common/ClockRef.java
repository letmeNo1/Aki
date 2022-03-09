package aki.Common;
import java.time.Clock;
import java.time.Duration;

public class ClockRef {
    public static Clock clock = Clock.systemDefaultZone();

    public static Duration setTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

}
