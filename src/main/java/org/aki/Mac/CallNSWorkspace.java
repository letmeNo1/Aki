package org.aki.Mac;

import org.aki.CurrentAppRefInfo;
import org.aki.Mac.Appkit.AppAndEnvironment.NSWorkspace;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CallNSWorkspace {
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

    public static String getRunningApplications(){
        return new NSWorkspace().runningApplications().toString();
    }

    public static int getPidByBundleIdentifier(String bundleIdentifier){
        return Integer.parseInt(Objects.requireNonNull(Arrays.stream(new NSWorkspace().runningApplications().toString().split(",")).filter(
                line -> line.contains(bundleIdentifier)).findFirst().orElse(null)).split("- ")[1].split("\\)")[0]);
    }
}
