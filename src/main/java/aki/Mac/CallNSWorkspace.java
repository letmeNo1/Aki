package aki.Mac;

import aki.Common.CurrentAppRefInfo;
import aki.Mac.Appkit.AppAndEnvironment.NSWorkspace;
import aki.Common.TraceLog;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class CallNSWorkspace {
    static TraceLog log = new TraceLog();
    /**
     * Launch App by BundleIdentifier
     */
    public static void launchApp(String bundleIdentifier){
        new NSWorkspace().launchAppWithBundleIdentifier(bundleIdentifier);
    }

    /**
     * Kill App by pid
     */
    public static void killApp(int pid) throws IOException {
        Runtime.getRuntime().exec(String.format("kill -9 %s",pid));
    }

    public static void killApp() throws IOException {
        Runtime.getRuntime().exec(String.format("kill -9 %s", CurrentAppRefInfo.getInstance().getPid()));
    }

    /**
     * Get the app information at the front end of the page
     */
    public static int getPidFromFrontMostApplication(){
        return Integer.parseInt(new NSWorkspace().frontmostApplication().toString().split("- ")[1].split("\\)")[0]);
    }

    private static Duration SetTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

    public static void waitAppLaunched(String bundleIdentifier) {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plus(SetTimeout(20000));
        while (true) {
            if (getRunningApplications().contains(bundleIdentifier)) {
                log.logInfo("launch app successful");
                break;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException("launch app failed");
            }
        }
    }

    public static String getRunningApplications(){
        return new NSWorkspace().runningApplications().toString();
    }

    public static int getPidByBundleIdentifier(String bundleIdentifier){
        waitAppLaunched(bundleIdentifier);
        return Integer.parseInt(Objects.requireNonNull(Arrays.stream(new NSWorkspace().runningApplications().toString().split(",")).filter(
                line -> line.contains(bundleIdentifier)).findFirst().orElse(null)).split("- ")[1].split("\\)")[0]);
    }
}
