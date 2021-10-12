package aki.Windows.WinApi;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;

public class IAccessibleEx extends Dispatch implements IUnknown {
    public IAccessibleEx() {
    }

    public IAccessibleEx(Pointer pvInstance) {
        this.setPointer(pvInstance);
    }

    public WinNT.HRESULT GetObjectForChild(Integer idChild, Variant.VARIANT pRetVal) {
        return (WinNT.HRESULT) this._invokeNativeObject(3,
                new Object[]{
                        this.getPointer(),
                        idChild,
                        pRetVal
                }, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT GetRuntimeId(Variant.VARIANT pRetVal){
        return (WinNT.HRESULT) this._invokeNativeObject(5,
                new Object[]{
                        this.getPointer(),
                        pRetVal,
                }, WinNT.HRESULT.class);
    };

    public WinNT.HRESULT ConvertReturnedElement(IRawElementProviderSimple pIn, Variant.VARIANT ppRetValOut){
        return (WinNT.HRESULT) this._invokeNativeObject(6,
                new Object[]{
                        this.getPointer(),
                        pIn,
                        ppRetValOut
                }, WinNT.HRESULT.class);
    };


}
