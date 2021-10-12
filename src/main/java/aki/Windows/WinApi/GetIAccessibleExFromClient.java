package aki.Windows.WinApi;

import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import aki.Windows.CallOleacc;
import aki.Windows.UIElementRef;

import java.io.IOException;

import static aki.Windows.WinApi.IIDMapped.*;

public class GetIAccessibleExFromClient {
    public static IAccessibleEx getIAccessibleExFromIAccessible(IAccessible pAcc) {
        WinNT.HRESULT hr;
        PointerByReference pSp = new PointerByReference();
        hr = pAcc.QueryInterface(REFIID_IServiceProvider, pSp);
        if (COMUtils.FAILED(hr)) {
            throw new RuntimeException(ReturnValueType.getDescription(hr.intValue()));
        }
        IServiceProvider iServiceProvider = new IServiceProvider(pSp.getValue());
        PointerByReference paex = new PointerByReference();
        hr = iServiceProvider.QueryService(REFIID_IAccessibleEx,REFIID_IAccessibleEx,paex);
        if (!COMUtils.FAILED(hr)) {
            return  new IAccessibleEx(paex.getValue());
        }else {
            throw new RuntimeException(ReturnValueType.getDescription(hr.intValue()));
        }
    }

    public static IRawElementProviderSimple getIRawElementProviderFromIAccessible(IAccessible pAcc){
        IAccessibleEx iAccessibleEx = getIAccessibleExFromIAccessible(pAcc);
        PointerByReference ppEl  = new PointerByReference();
        WinNT.HRESULT hr = new WinNT.HRESULT();
        iAccessibleEx.QueryInterface(REFIID_IRawElementProviderSimple,ppEl);
        if (!COMUtils.FAILED(hr)) {
            return new IRawElementProviderSimple(ppEl.getValue());
        }else {
            throw new RuntimeException(ReturnValueType.getDescription(hr.intValue()));
        }
    }

    public static Object getPropertyValue(IAccessible pAcc, Integer Identifiers){
        WinNT.HRESULT hr;
        Variant.VARIANT.ByReference varResult = new Variant.VARIANT.ByReference();
        IRawElementProviderSimple iRawElementProviderSimple = getIRawElementProviderFromIAccessible(pAcc);
        hr = iRawElementProviderSimple.GetPropertyValue(Identifiers,varResult);
        if (!COMUtils.FAILED(hr)) {
            return varResult.getValue();
        }else {
            throw new RuntimeException(ReturnValueType.getDescription(hr.intValue()));
        }
    }

//    public static Object getPropertyValue(IAccessible pAcc, Integer Identifiers){  WinNT.HRESULT hr;
//        PointerByReference pSp = new PointerByReference();
//        hr = pAcc.QueryInterface(REFIID_IServiceProvider, pSp);
//        if (!COMUtils.FAILED(hr)) {
//            System.out.println(hr);
//            System.out.println("successful");
//        }
//        IServiceProvider iServiceProvider = new IServiceProvider(pSp.getValue());
//        PointerByReference paex = new PointerByReference();
//
//        hr = iServiceProvider.QueryService(REFIID_IAccessibleEx,REFIID_IAccessibleEx,paex);
//        System.out.println(hr);
//        if (!COMUtils.FAILED(hr)) {
//            System.out.println("successful");
//        }else {
//            System.out.println("failed");
//        }
//        IAccessibleEx iAccessibleEx = new IAccessibleEx(paex.getValue());
//
//        PointerByReference ppEl  = new PointerByReference();
//
//        iAccessibleEx.QueryInterface(REFIID_IRawElementProviderSimple,ppEl);
//        if (!COMUtils.FAILED(hr)) {
//            System.out.println("successful2");
//        }else {
//            System.out.println("failed");
//        }
//        Variant.VARIANT.ByReference varResult = new Variant.VARIANT.ByReference();
//
//        IRawElementProviderSimple IRawElementProviderSimple = new IRawElementProviderSimple(ppEl.getValue());
//        hr = IRawElementProviderSimple.GetPropertyValue(UIA_AutomationIdPropertyId,varResult);
//        System.out.println(hr);
//        if (!COMUtils.FAILED(hr)) {
//            System.out.println("successful");
//        }else {
//            System.out.println("failed");
//
//        }
//        System.out.println(varResult.getValue());
//        return varResult.getValue();
//    }
    public static void main(String[] args) throws IOException, InterruptedException {
        WinDef.HWND app = User32.INSTANCE.FindWindow(null,"RingCentral Phone");
        User32.INSTANCE.SetForegroundWindow(app);
        UIElementRef app2 =  CallOleacc.getAccessibleObject(app);
        WinNT.HRESULT hr;
        Variant.VARIANT.ByReference varResult = new Variant.VARIANT.ByReference();
        varResult.setValue((short)Variant.VT_I4, new WinDef.LONG(0));
        IRawElementProviderSimple iRawElementProviderSimple = getIRawElementProviderFromIAccessible(app2.get_ChildrenElements().get(0));
        hr = iRawElementProviderSimple.GetPropertyValue(30165,varResult);
        if (!COMUtils.FAILED(hr)) {
            System.out.println(varResult.getValue());
        }else {
            throw new RuntimeException(ReturnValueType.getDescription(hr.intValue()));
        }
    }

}
