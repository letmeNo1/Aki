package aki.Windows.WinApi;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public interface Oleacc extends StdCallLibrary, WinNT {

        Oleacc INSTANCE = Native.load("oleacc", Oleacc.class, W32APIOptions.DEFAULT_OPTIONS);

        HRESULT AccessibleObjectFromWindow(HWND win, int objID, Guid.REFIID iid, PointerByReference ptr);

        HRESULT AccessibleChildren(Pointer pAccPointer, int startFrom, int childCount, Variant.VARIANT[] tableRef, LongByReference returnCount);

        HRESULT WindowFromAccessibleObject(Pointer pointer, PointerByReference ptr);

        int GetRoleText(int roleId, char[] buff, int i);

}


