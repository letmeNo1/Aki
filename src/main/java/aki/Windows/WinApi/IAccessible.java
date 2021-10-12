package aki.Windows.WinApi;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

public class IAccessible extends Dispatch implements IUnknown {
    public IAccessible(Pointer p){
        super(p);
    }

    public WinNT.HRESULT get_accChildCount(NativeLongByReference pcountChildren) {
        return (WinNT.HRESULT) this._invokeNativeObject(IAccessibleVtbMapped.get_accChildCount,
                new Object[]{
                        this.getPointer(),
                        pcountChildren
                }, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT get_accName(Variant.VARIANT varID, WTypes.BSTRByReference pszName) {
        return (WinNT.HRESULT)this._invokeNativeObject(IAccessibleVtbMapped.get_accName ,
                new Object[]{
                        this.getPointer(),
                        varID,
                        pszName
                },
                WinNT.HRESULT.class);
    }

    public WinNT.HRESULT get_accRole(Variant.VARIANT varID, Variant.VARIANT pvarRole) {
        return (WinNT.HRESULT)this._invokeNativeObject(IAccessibleVtbMapped.get_accRole ,
                new Object[]{
                        this.getPointer(),
                        varID,
                        pvarRole
                },
                WinNT.HRESULT.class);
    }

    public WinNT.HRESULT get_accLocation(NativeLongByReference pxLeft,
                                            NativeLongByReference pyTop,
                                            NativeLongByReference pcxWidth,
                                            NativeLongByReference pcyHeight,
                                            Variant.VARIANT varID) {
        return (WinNT.HRESULT)this._invokeNativeObject(IAccessibleVtbMapped.getAccLocation ,
                new Object[]{
                        this.getPointer(),
                        pxLeft,
                        pyTop,
                        pcxWidth,
                        pcyHeight,
                        varID
                },
                WinNT.HRESULT.class);
    }

    public WinDef.HWND getHWNDFromIAccessible(){
        WinDef.HWND hwnd ;
        PointerByReference ptr = new PointerByReference();
        WinNT.HRESULT res = Oleacc.INSTANCE.WindowFromAccessibleObject(this.getPointer(), ptr);
        hwnd = new WinDef.HWND(ptr.getValue());
        return hwnd;
    }

}

