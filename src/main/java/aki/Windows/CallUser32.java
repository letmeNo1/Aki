package aki.Windows;

import aki.Common.LaunchOption;
import aki.Common.WaitFun;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import aki.Mac.CoreGraphics.QuartzEventServices.Keycodes;
import aki.Windows.WinApi.Kernel32Ex;
import aki.Windows.WinApi.User32Ex;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Function;

import static com.sun.jna.platform.win32.WinUser.CF_UNICODETEXT;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_V;
import static aki.Windows.WinApi.Kernel32Ex.*;
import static aki.Windows.WinApi.WinUser.*;

public class CallUser32 implements WaitFun {
    public static WinDef.LPARAM makeLParam(int l, int h) {
        // note the high word bitmask must include L
        return new WinDef.LPARAM((l & 0xffff) | (h & 0xffffL) << 16);
    }

    private static Duration SetTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }


    public static HWND waitAppLaunched(int pid,LaunchOption launchOption) {
        log.logInfo("Wait for the window to initialize successfully...");
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plus(SetTimeout(launchOption.getLaunchTimeoutTimeout()));
        while (true) {
            HWND hwnd = getCurrentWinHWND(pid);
            if (hwnd.getPointer() != null) {
                log.logInfo("launch app successful!");
                User32.INSTANCE.SetForegroundWindow(hwnd);
                User32.INSTANCE.SetFocus(hwnd);
                return hwnd;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException("launch app timeout");
            }
        }
    }

    public static void waitAppLaunched(String bundleIdentifierOrAppLaunchPath, LaunchOption launchOption) {
        log.logInfo("Wait for the window to initialize successfully...");
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plus(SetTimeout(launchOption.getLaunchTimeoutTimeout()));
        HWND currentWinHWND;
        if (launchOption.getIsUWPApp()) {
            bundleIdentifierOrAppLaunchPath = "C:\\Windows\\System32\\ApplicationFrameHost.exe";
        }
        while (true) {
            String A = "";
            currentWinHWND = User32.INSTANCE.GetForegroundWindow();
            try {
                A = Objects.requireNonNull(getImageName(currentWinHWND)).toLowerCase();
            } catch (Exception ignored) {
            }
            if (Objects.equals(bundleIdentifierOrAppLaunchPath.toLowerCase(), A)) {
                log.logInfo("launch app successful");
                User32.INSTANCE.SetForegroundWindow(currentWinHWND);
                User32.INSTANCE.SetFocus(currentWinHWND);
                break;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException("launch app failed");
            }
        }
    }


    private static String getImageName(HWND window) {
        // Get the process ID of the window
        IntByReference procId = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(window, procId);

        // Open the process to get permissions to the image name
        HANDLE procHandle = Kernel32.INSTANCE.OpenProcess(
                Kernel32.PROCESS_QUERY_LIMITED_INFORMATION,
                false,
                procId.getValue()
        );

        // Get the image name
        char[] buffer = new char[4096];
        IntByReference bufferSize = new IntByReference(buffer.length);
        boolean success = Kernel32.INSTANCE.QueryFullProcessImageName(procHandle, 0, buffer, bufferSize);

        // Clean up: close the opened process
        Kernel32.INSTANCE.CloseHandle(procHandle);

        return success ? new String(buffer, 0, bufferSize.getValue()) : null;
    }


    public static void sendMessage(HWND hwnd, int msg, WPARAM var3, LPARAM var4) {
        User32.INSTANCE.SendMessage(hwnd, msg, var3, var4);
    }

    public static void postMessage(HWND hwnd, int msg, WPARAM var3, LPARAM var4) {
        User32.INSTANCE.PostMessage(hwnd, msg, var3, var4);
    }


    public static RECT getDesktopRect() {
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(User32.INSTANCE.GetDesktopWindow(), rect);
        return rect;
    }

    public static Location conversionCoordinate(int x, int y) {
        WinDef.RECT rect = CallUser32.getDesktopRect();
        Location location = new Location();
        location.x = x * 65535 / rect.right;
        location.y = y * 65535 / rect.bottom;
        return location;
    }

    protected static void keyboardEvent(long key) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(0);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    protected static void mouseEvent(long key, int dx, int dy) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.dx = new WinDef.LONG(dx);
        input.input.mi.dy = new WinDef.LONG(dy);
        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(key);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    protected static void wheelEvent(int mouseData) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(aki.Windows.WinApi.WinUser.MOUSEEVENTF_WHEEL);
        input.input.mi.mouseData = new WinDef.DWORD(mouseData);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void setForegroundWindow(WinUIElementRef winUiElementRef) {
        HWND hwnd = winUiElementRef.getHWNDFromIAccessible();
        User32.INSTANCE.SetForegroundWindow(hwnd);
    }

    public static void SetFocus(WinUIElementRef winUiElementRef) {
        HWND hwnd = winUiElementRef.getHWNDFromIAccessible();
        User32.INSTANCE.SetFocus(hwnd);
    }

    public static HWND findWindowByName(String windowName) {
        return getCurrentWinHWND(windowName);
    }

    static Function<String, HWND> findWindowByName = CallUser32::findWindowByName;

    public final static int DEFAULT_TIMEOUT = 20000;

    public static HWND findWindowByNameByWait(String windowName,int timeOut) {
        return WaitFun.findWindowByWait(findWindowByName, windowName, timeOut);
    }

    public static void keyboardPress(int key) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(0);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void keyboardRelease(int key) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(2);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void mouseMove(int dx, int dy) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
    }

    public static void wheelMove(int distance,int dx, int dy) {
        mouseMove(dx,dy);
        wheelEvent(distance);
    }

    public static void leftMouseClick(int dx, int dy) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, location.x, location.y);
    }

    public static void leftMouseDoubleClick(int dx, int dy) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, location.x, location.y);
    }

    public static void leftMouseLongClick(int dx, int dy, int duringTime) throws InterruptedException {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, location.x, location.y);
        Thread.sleep(duringTime);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, location.x, location.y);
    }

    public static void leftMouseDrag(int dx, int dy, int dx2, int dy2) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, dx, dy);
    }

    public static void leftMouseClickToHWND(WinUIElementRef winUiElementRef, Location location) {
        HWND hwnd = winUiElementRef.getHWNDFromIAccessible();
        User32.INSTANCE.SendMessage(hwnd, WM_LBUTTONDOWN, new WPARAM(1), makeLParam(location.w / 2, location.h));
        User32.INSTANCE.SendMessage(hwnd, WM_LBUTTONUP, new WPARAM(0), makeLParam(location.w / 2, location.h));
    }

    private static boolean openClipboard(HWND hwnd) {
        if (!User32Ex.INSTANCE.OpenClipboard(hwnd)) {
            throw new RuntimeException("Unable to open clipboard");
        } else {
            return true;
        }
    }

    public static boolean setClipboardContents(HWND hwnd, String text) {
        try {
            if (!User32Ex.INSTANCE.EmptyClipboard()) {
                throw new RuntimeException("Unable to empty clipboard");
            }
            Pointer data = Kernel32Ex.INSTANCE.GlobalAlloc(GMEM_MOVEABLE, (text.length() + 1) * 2);
            if (data == null) {
                throw new RuntimeException("Unable to allocate data");
            }
            Pointer buffer = Kernel32Ex.INSTANCE.GlobalLock(data);
            if (buffer == null) {
                throw new RuntimeException("Unable to lock buffer");
            }
            buffer.setWideString(0, text);
            Kernel32Ex.INSTANCE.GlobalUnlock(data);
            if (User32Ex.INSTANCE.SetClipboardData(CF_UNICODETEXT, buffer) == null) {
                throw new RuntimeException("Unable to set clipboard data");
            }
            return true;
        } finally {
            User32Ex.INSTANCE.CloseClipboard(hwnd);
        }
    }

    public static void type(int x, int y, String input) {
        writeObjectsToClipboard(null, input);
        leftMouseClick(x, y);
        combinationKeyOperation(VK_CONTROL, VK_V);
    }

    public static void clear(int x, int y) {
        leftMouseClick(x, y);
        combinationKeyOperation(Keycodes.kVK_ANSI_A, Keycodes.kVK_Control);
        combinationKeyOperation(Keycodes.kVK_Delete);
    }

    public static void writeObjectsToClipboard(HWND hwnd, String text) {
        openClipboard(hwnd);
        setClipboardContents(hwnd, text);
    }

    public static void combinationKeyOperation(int... keycodes) {
        for (int keycode : keycodes) {
            keyboardPress(keycode);
        }
        for (int keycode : keycodes) {
            keyboardRelease(keycode);
        }
    }

    public static HWND getCurrentWinHWND(int curPid){
        final HWND[] currentWinHWND = {new HWND()};
        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            int count = 0;
            public boolean callback(HWND hWnd, Pointer arg1) {
                char[] windowText = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                RECT rectangle = new RECT();
                User32.INSTANCE.GetWindowRect(hWnd, rectangle);
                IntByReference pid = new IntByReference();
                User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                // second condition is for visible and non minimised windows
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd)
                        && rectangle.left > -32000)) {
                    return true;
                }
                if(pid.getValue()==curPid&&count<1&&!wText.equals("Settings")){
                    log.logInfo("Window initialization succeeded!");
                    count+=1;
                    User32.INSTANCE.SetForegroundWindow(hWnd);
                    currentWinHWND[0] = hWnd;
                }
                return true;
            }
        }, null);
        return currentWinHWND[0];
    }

    public static HWND getCurrentWinHWND(String windowName){
        final HWND[] currentWinHWND = {new HWND()};
        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            int count = 0;
            public boolean callback(HWND hWnd, Pointer arg1) {
                char[] windowText = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                RECT rectangle = new RECT();
                User32.INSTANCE.GetWindowRect(hWnd, rectangle);
                IntByReference pid = new IntByReference();
                User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                // second condition is for visible and non minimised windows
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd)
                        && rectangle.left > -32000)) {
                    return true;
                }
                if(wText.equals(windowName)){
                    log.logInfo("Window initialization succeeded!");
                    count+=1;
                    User32.INSTANCE.SetForegroundWindow(hWnd);
                    currentWinHWND[0] = hWnd;
                }
                return true;
            }
        }, null);
        return currentWinHWND[0];
    }

    public static HWND getCurrentWinHWNDFuzzyMatching(String windowName){
        final HWND[] currentWinHWND = {new HWND()};
        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            int count = 0;
            public boolean callback(HWND hWnd, Pointer arg1) {
                char[] windowText = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                RECT rectangle = new RECT();
                User32.INSTANCE.GetWindowRect(hWnd, rectangle);
                IntByReference pid = new IntByReference();
                User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                // second condition is for visible and non minimised windows
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd)
                        && rectangle.left > -32000)) {
                    return true;
                }
                if(wText.contains(windowName)){
                    log.logInfo("Window initialization succeeded!");
                    count+=1;
                    User32.INSTANCE.SetForegroundWindow(hWnd);
                    currentWinHWND[0] = hWnd;
                }
                return true;
            }
        }, null);
        return currentWinHWND[0];
    }

    public static void main(String[] args) throws Exception {
        getCurrentWinHWNDFuzzyMatching("https://139.219.68.160/history/ - Google Chrome");
        System.out.println("xxx");
    }
}