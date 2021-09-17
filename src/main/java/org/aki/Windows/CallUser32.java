package org.aki.Windows;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import org.aki.Mac.CoreGraphics.QuartzEventServices.Keycodes;
import org.aki.Windows.WinApi.Kernel32Ex;
import org.aki.Windows.WinApi.User32Ex;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.sun.jna.platform.win32.WinUser.CF_UNICODETEXT;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_V;
import static org.aki.Windows.WinApi.Kernel32Ex.*;
import static org.aki.Windows.WinApi.WinUser.*;

public class CallUser32 implements WaitFun {
    public static WinDef.LPARAM makeLParam(int l, int h) {
        // note the high word bitmask must include L
        return new WinDef.LPARAM((l & 0xffff) | (h & 0xffffL) << 16);
    }

    private static Duration SetTimeout(long timeout) {
        return Duration.ofMillis(timeout);
    }

    public static HWND waitAppLaunched(int dwProcessId, int timeout) {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plus(SetTimeout(timeout));
        IntByReference currentDwProcessId = new IntByReference();
        HWND currentWinHWND;
        while (true) {
            currentWinHWND = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowThreadProcessId(currentWinHWND, currentDwProcessId);
            if (dwProcessId == currentDwProcessId.getValue()) {
                System.out.println("launch app successful");
                break;
            }
            if (end.isBefore(clock.instant())) {
                throw new RuntimeException("launch app failed");
            }
        }
        return currentWinHWND;
    }


    public static RECT GetDesktopRect() {
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(User32.INSTANCE.GetDesktopWindow(), rect);
        return rect;
    }

    public static Location conversionCoordinate(int x, int y) {
        WinDef.RECT rect = CallUser32.GetDesktopRect();
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

    public static void setForegroundWindow(UIElementRef uiElementRef) {
        HWND hwnd = uiElementRef.getHWNDFromIAccessible();
        User32.INSTANCE.SetForegroundWindow(hwnd);
    }

    public static HWND findWindowByName(String windowName) {
        return User32.INSTANCE.FindWindow(null,windowName);
    }

    static Function<String, HWND> findWindowByName = CallUser32::findWindowByName;

    public final static int DEFAULT_TIMEOUT = 20000;

    public static HWND findWindowByNameByWait(String windowName) {
        return WaitFun.findWindowByWait(findWindowByName,windowName,DEFAULT_TIMEOUT);
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

    public static void MouseMove(int dx, int dy) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
    }

    public static void leftMouseClick(int dx, int dy) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, location.x, location.y);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, location.x, location.y);
    }

    public static void leftMouseDrag(int dx, int dy, int dx2, int dy2) {
        Location location = conversionCoordinate(dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTDOWN, dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_MOVE, dx, dy);
        mouseEvent(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_LEFTUP, dx, dy);
    }

    public static void leftMouseClickToHWND(UIElementRef uiElementRef, Location location) {
        HWND hwnd = uiElementRef.getHWNDFromIAccessible();
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

    public static void type(int x,int y,String input){
        writeObjectsToClipboard(null,input);
        leftMouseClick(x,y);
        combinationKeyOperation(VK_CONTROL, VK_V);
    }

    public static void clear(int x,int y){
        leftMouseClick(x,y);
        combinationKeyOperation(Keycodes.kVK_ANSI_A,Keycodes.kVK_Control);
        combinationKeyOperation(Keycodes.kVK_Delete);
    }

    public static void writeObjectsToClipboard(HWND hwnd, String text) {
        openClipboard(hwnd);
        setClipboardContents(hwnd, text);
    }

    public static void combinationKeyOperation(int... keycodes){
        for (int keycode : keycodes) {
            keyboardPress(keycode);
        }
        for (int keycode : keycodes) {
            keyboardRelease(keycode);
        }
    }
    public static void main(String[] args) {
        type(800,400,"fdsf");
    }
}
