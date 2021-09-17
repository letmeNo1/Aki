package org.aki.Helper;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;
import org.aki.CurrentAppRefInfo;
import org.aki.Mac.*;
import org.aki.Mac.CoreGraphics.CGGeometry.CGEventRef;
import org.aki.Mac.CoreGraphics.CGGeometry.CGFloat;
import org.aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import org.aki.Mac.CoreGraphics.CoreGraphics;
import org.aki.Windows.CallKernel32;
import org.aki.Windows.CallOleacc;
import org.aki.Windows.CallUser32;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

import static org.aki.Mac.CoreGraphics.CoreGraphics.kCGSessionEventTap;

public class Operation {
    public static UIElementRef initializeAppRefForMac(String bundleIdentifierOrAppLaunchPath){
        CallNSWorkspace.launchApp(bundleIdentifierOrAppLaunchPath);
        int pid = CallNSWorkspace.getPidByBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setBundleIdentifier(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid);
        return CallAppServices.getElementRefByPid(pid);
    }

    public static org.aki.Windows.UIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath){
        DWORD pid = CallKernel32.launchApp(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid.intValue());
        WinUser.GUITHREADINFO xx = new WinUser.GUITHREADINFO();
        User32.INSTANCE.GetGUIThreadInfo(pid.intValue(),xx);
        System.out.println(xx.hwndActive);
        HWND curHWND = CallUser32.waitAppLaunched(pid.intValue(), CurrentAppRefInfo.getInstance().getDefaultTimeout());
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static org.aki.Windows.UIElementRef initializeAppRefByWindowName(String windowName){
        HWND curHWND = CallUser32.findWindowByNameByWait(windowName);
        return CallOleacc.getAccessibleObject(curHWND);
    }


    public static void takeScreenshot(String path){
        List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
        CallQuartzWindowServices.takeScreenshot(windowId.get(0),path);
    }

    public static void takeScreenshot(String path,int windowIndex){
        List<Integer> windowId = CallQuartzWindowServices.getWindowIdsByPid(CurrentAppRefInfo.getInstance().getPid());
        CallQuartzWindowServices.takeScreenshot(windowId.get(windowIndex),path);
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
