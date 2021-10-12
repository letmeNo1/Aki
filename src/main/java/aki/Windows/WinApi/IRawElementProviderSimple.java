package aki.Windows.WinApi;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;

//Defines methods and properties that expose simple UI elements.

public class IRawElementProviderSimple extends Dispatch implements IUnknown {
    public IRawElementProviderSimple() {
    }

    public IRawElementProviderSimple(Pointer pvInstance) {
        this.setPointer(pvInstance);
    }

    public WinNT.HRESULT getProviderOptions(Variant.VARIANT pRetVal) {
        return (WinNT.HRESULT)this._invokeNativeObject(3, new Object[]{
                this.getPointer(), pRetVal
        }, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT GetPatternProvider(Variant.VARIANT pRetVal) {
        return (WinNT.HRESULT)this._invokeNativeObject(4, new Object[]{
                this.getPointer(), pRetVal
        }, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT GetPropertyValue(Integer varID,
                                          Variant.VARIANT pRetVal) {
        return (WinNT.HRESULT)this._invokeNativeObject(5, new Object[]{
                this.getPointer(), varID,pRetVal
        }, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT getHostRawElementProvider(Variant.VARIANT pRetVal) {
        return (WinNT.HRESULT)this._invokeNativeObject(6, new Object[]{
                this.getPointer(), pRetVal
        }, WinNT.HRESULT.class);
    }
}
