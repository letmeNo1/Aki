package aki.Windows.WinApi;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;


public class IServiceProvider extends Dispatch implements IUnknown {
    public IServiceProvider() {
    }

    public IServiceProvider(Pointer pvInstance) {
        this.setPointer(pvInstance);
    }

    public WinNT.HRESULT QueryInterface(Guid.REFIID var1, PointerByReference var2) {
        return (WinNT.HRESULT)this._invokeNativeObject(0, new Object[]{this.getPointer(), var1,var2}, WinNT.HRESULT.class);
    }

    public WinNT.HRESULT QueryService(Guid.REFIID var1, Guid.REFIID var2, PointerByReference var3) {
        return (WinNT.HRESULT)this._invokeNativeObject(3, new Object[]{this.getPointer(), var1, var2,var3}, WinNT.HRESULT.class);
    }
}
