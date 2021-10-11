package org.aki.Helper;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;
import org.aki.CurrentAppRefInfo;
import org.aki.Mac.*;

import org.aki.Windows.CallGdi32Util;
import org.aki.Windows.CallKernel32;
import org.aki.Windows.CallOleacc;
import org.aki.Windows.CallUser32;

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

    public static org.aki.Windows.UIElementRef initializeAppRefForWin(String bundleIdentifierOrAppLaunchPath){
        DWORD pid = CallKernel32.launchApp(bundleIdentifierOrAppLaunchPath);
        CurrentAppRefInfo.getInstance().setPid(pid.intValue());
        WinUser.GUITHREADINFO xx = new WinUser.GUITHREADINFO();
        User32.INSTANCE.GetGUIThreadInfo(pid.intValue(),xx);
        HWND curHWND = CallUser32.waitAppLaunched(pid.intValue(), CurrentAppRefInfo.getInstance().getDefaultTimeout());
        CurrentAppRefInfo.getInstance().addHandleToList(curHWND);
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static org.aki.Windows.UIElementRef initializeAppRefByWindowName(String windowName){
        HWND curHWND = CallUser32.findWindowByNameByWait(windowName);
        CurrentAppRefInfo.getInstance().addHandleToList(curHWND);
        return CallOleacc.getAccessibleObject(curHWND);
    }

    public static void takeScreenshot(String path){
        if(System.getProperty("os.name").contains("Windows")){
            HWND currentHandle = CurrentAppRefInfo.getInstance().getCurrentHandle(0);
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
            CallQuartzWindowServices.takeScreenshot(windowId.get(index),path);
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
