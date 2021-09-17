package org.aki.Windows.WinApi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32Ex extends StdCallLibrary, WinNT, Wincon {
    Kernel32Ex INSTANCE = Native.load("kernel32", Kernel32Ex.class, W32APIOptions.DEFAULT_OPTIONS);

    int GMEM_MOVEABLE = 0x2;

    Pointer GlobalAlloc (int uFlags, int dwBytes);

    Pointer GlobalLock (Pointer hMem);

    void GlobalUnlock (Pointer hMem);

    int GetTickCount ();

    int GetCurrentProcessId ();
}
