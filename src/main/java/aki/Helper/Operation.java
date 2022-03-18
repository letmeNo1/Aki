package aki.Helper;


import aki.Common.LaunchOption;
import aki.Mac.*;
import aki.Windows.*;
import com.sun.jna.platform.win32.WinDef.*;
import aki.Common.CurrentAppRefInfo;

import java.io.IOException;
import java.util.List;


public class Operation {
    public static MacUIElementRef initializeAppRefForMac(String bundleIdentifierOrAppLaunchPath){
        CallNSWorkspace.launchApp(bundleIdentifierOrAppLaunchPath);
        int pid = CallNSWorkspace.getPidByBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid);
        return CallAppServices.getElementRefByPid(pid);
    }

    public static WinUIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath) {
        LaunchOption launchOption = new LaunchOption();
        return initializeAppRefForWin(bundleIdentifierOrAppLaunchPath,launchOption);
    }

    public static WinUIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath, LaunchOption launchOption) {
        int pid;
        pid = CallKernel32.launchApp(bundleIdentifierOrAppLaunchPath,launchOption);
        CurrentAppRefInfo.getInstance().setPid(pid);
        HWND curHWND = CallUser32.waitAppLaunched(pid,launchOption);
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static void initializeAppRefForElectron(String bundleIdentifierOrAppLaunchPath,LaunchOption launchOption) {
        if(System.getProperty("os.name").contains("Windows")){
            CallKernel32.launchAppForElectron(bundleIdentifierOrAppLaunchPath);
            CallUser32.waitAppLaunched(bundleIdentifierOrAppLaunchPath, launchOption);
        }else {
            CallNSWorkspace.launchApp(bundleIdentifierOrAppLaunchPath);
        }
    }

    //only for mac
    public static void takeScreenshot(String path){
        List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
        CallQuartzWindowServices.takeScreenshot(windowId.get(0),path);
    }


    //Only for mac
    public static void takeScreenshot(String path,int index){
        List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
        try {
            System.out.println(windowId.get(index));
            Runtime.getRuntime().exec("screencapture -l " + windowId.get(index) + " " + path);
        }catch (Exception ignored){
        }
    }

    public static void takeScreenshotForDesktop(String path){
        if(System.getProperty("os.name").contains("Windows")){
            CallGdi32Util.takeScreenshotForDesktop(path);
        }else {
            CallQuartzWindowServices.takeScreenshotForDesktop(path);
        }
    }


    //only for mac
    public static void killApp() throws IOException {
        Runtime.getRuntime().exec(String.format("kill -9 %s", CurrentAppRefInfo.getInstance().getPid()));
    }

    public static void killApp(int pid) throws IOException {
        if(System.getProperty("os.name").contains("Windows")){
            Runtime.getRuntime().exec(String.format("taskkill /pid %s -f", pid));
        }else {
            Runtime.getRuntime().exec(String.format("kill -9 %s",pid));
        }
    }

    public static void mouseMoveEvent(int x,int y) {
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.mouseMove(x,y);
        }else {
            CallQuartzEventServices.mouseMoveEvent(x,y);
        }
    }

    public static void wheelEvent(int distance,int x,int y) {
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.wheelMove(distance,x,y);
        }else {
            CallQuartzEventServices.mouseMoveEvent(x,y);
        }
    }

    public static void combinationKeyOperation(int... keycodes) {
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.combinationKeyOperation(keycodes);
        }else {
            CallQuartzEventServices.combinationKeyOperation(keycodes);
        }
    }


}
