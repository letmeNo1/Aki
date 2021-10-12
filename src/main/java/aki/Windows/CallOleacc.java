package aki.Windows;

import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import aki.Windows.WinApi.IAccessible;
import aki.Windows.WinApi.Oleacc;

public class CallOleacc {

    public static UIElementRef getAccessibleObject(WinDef.HWND winHWND){
        Guid.IID IID_IAccessible = new Guid.IID("{618736E0-3C3D-11CF-810C-00AA00389B71}");
        Guid.REFIID REFIID_IAccessible = new Guid.REFIID(IID_IAccessible);
        PointerByReference ptr = new PointerByReference();
        Oleacc.INSTANCE.AccessibleObjectFromWindow(winHWND,0,REFIID_IAccessible,ptr);
        return new UIElementRef(ptr.getValue());
    }

    private WinDef.HWND getHWNDFromIAccessible(IAccessible pAcc){
        PointerByReference ptr = new PointerByReference();
        WinNT.HRESULT res = Oleacc.INSTANCE.WindowFromAccessibleObject(pAcc.getPointer(), ptr);

        COMUtils.checkRC(res);

        return new WinDef.HWND(ptr.getValue());
    }
}
