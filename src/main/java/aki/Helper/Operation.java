package aki.Helper;


import aki.LaunchOption;
import aki.Mac.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;
import aki.CurrentAppRefInfo;

import aki.Windows.CallGdi32Util;
import aki.Windows.CallKernel32;
import aki.Windows.CallOleacc;
import aki.Windows.CallUser32;
import com.sun.jna.ptr.IntByReference;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.IOException;
import java.util.List;


public class Operation {
    public static UIElementRef initializeAppRefForMac(String bundleIdentifierOrAppLaunchPath){
        CallNSWorkspace.launchApp(bundleIdentifierOrAppLaunchPath);
        int pid = CallNSWorkspace.getPidByBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid);
        return CallAppServices.getElementRefByPid(pid);
    }

    public static aki.Windows.UIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath) {
        LaunchOption launchOption = new LaunchOption();
        return initializeAppRefForWin(bundleIdentifierOrAppLaunchPath,launchOption);
    }

    public static aki.Windows.UIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath, LaunchOption launchOption) {
        int pid;
        pid = CallKernel32.launchApp(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid);
        HWND curHWND;
        if(launchOption.getIsUWPApp()){
            curHWND = CallUser32.waitAppLaunchedForUWPApp(launchOption.getDefaultTimeout());
        }else {
            curHWND = CallUser32.waitAppLaunched(pid, launchOption.getDefaultTimeout());
        }
        CurrentAppRefInfo.getInstance().addHandleToList(curHWND);
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static void initializeAppRefForElectron(String bundleIdentifierOrAppLaunchPath) {
        if(System.getProperty("os.name").contains("Windows")){
            CallKernel32.launchAppForElectron(bundleIdentifierOrAppLaunchPath);
        }else {
            CallNSWorkspace.launchApp(bundleIdentifierOrAppLaunchPath);
        }
    }

    public static aki.Windows.UIElementRef initializeAppRefByWindowName(String windowName){
        HWND curHWND = CallUser32.findWindowByNameByWait(windowName);
        CurrentAppRefInfo.getInstance().addHandleToList(curHWND);
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static void takeScreenshot(String path){
        if(System.getProperty("os.name").contains("Windows")){
            HWND currentHandle = CurrentAppRefInfo.getInstance().getCurrentHandle(0);
            IntByReference currentDwProcessId = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(currentHandle, currentDwProcessId);
            CallGdi32Util.takeScreenshot(currentHandle,path);
        }else {
            List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
            CallQuartzWindowServices.takeScreenshot(windowId.get(0),path);
        }
    }


    public static void takeScreenshot(String path,int index){
        if(System.getProperty("os.name").contains("Windows")){
            HWND currentHandle = CurrentAppRefInfo.getInstance().getCurrentHandle(index);
            CallGdi32Util.takeScreenshot(currentHandle,path);
        }else {
            List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
            try {
                System.out.println(windowId.get(index));

                Runtime.getRuntime().exec("screencapture -l " + windowId.get(index) + " " + path);
            }catch (Exception ignored){
            }
        }
    }

    public static void takeScreenshotForDesktop(String path){
        if(System.getProperty("os.name").contains("Windows")){
            CallGdi32Util.takeScreenshotForDesktop(path);
        }else {
            CallQuartzWindowServices.takeScreenshotForDesktop(path);
        }
    }

    public static void killApp() throws IOException {
        if(System.getProperty("os.name").contains("Windows")){
            Runtime.getRuntime().exec(String.format("taskkill /pid %s -f", CurrentAppRefInfo.getInstance().getPid()));
        }else {
            Runtime.getRuntime().exec(String.format("kill -9 %s", CurrentAppRefInfo.getInstance().getPid()));
        }
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
            CallUser32.MouseMove(x,y);
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
