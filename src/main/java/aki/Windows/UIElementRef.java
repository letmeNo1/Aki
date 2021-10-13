package aki.Windows;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

import aki.Windows.WinApi.GetIAccessibleExFromClient;
import aki.Windows.WinApi.IAccessible;
import aki.Windows.WinApi.IIDMapped;
import aki.Windows.WinApi.Oleacc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static aki.Windows.WinApi.AutoElementPropertyIdentifiers.*;

public class UIElementRef extends IAccessible implements FindUIElement,WaitFun{
    public UIElementRef(Pointer p) {
        super(p);
    }

    private final int DEFAULT_TIMEOUT = 20000;

    public int get_ChildCount() {
        NativeLongByReference pcountChildren = new NativeLongByReference();
        WinNT.HRESULT hr = get_accChildCount(pcountChildren);
        COMUtils.checkRC(hr);
        return pcountChildren.getValue().intValue();
    }

    public String get_AutomationId() {
        String automationId = "";
        try {
            automationId = GetIAccessibleExFromClient.getPropertyValue(this,UIA_AutomationIdPropertyId).toString();
        }catch (RuntimeException ignore){
        }
        return automationId;
    }

    public String get_FullDescriptionPropertyId() {
        String automationId = "";
        try {
            automationId = GetIAccessibleExFromClient.getPropertyValue(this,UIA_FullDescriptionPropertyId).toString();
        }catch (RuntimeException ignore){
        }
        return automationId;
    }

    public List<UIElementRef> get_ChildrenElements() {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
        List<UIElementRef> accChildren= new ArrayList<>();
        WinNT.HRESULT hr;
        LongByReference returnCount = new LongByReference();

        int childCount = this.get_ChildCount();

        if(childCount == 0) {
            accChildren = Collections.emptyList();
        }else {
            Variant.VARIANT[] tableRef = new Variant.VARIANT[(int) childCount];
            hr = Oleacc.INSTANCE.AccessibleChildren(this.getPointer(), 0,childCount, tableRef, returnCount);
            COMUtils.checkRC(hr);

            for (int x = 0; x < returnCount.getValue(); x++) {
                Variant.VARIANT vtChild = tableRef[x];
                Dispatch pDisp = vtChild._variant.__variant.pdispVal;
                PointerByReference pChild = new PointerByReference();
                //There may be a problem of returning inaccessible child elements,
                // so need to add a try to avoid known errors
                try{
                    hr = pDisp.QueryInterface(IIDMapped.REFIID_IAccessible, pChild);
                }catch (Error e){
                    hr = new WinNT.HRESULT(-1);
                }
                if (!COMUtils.FAILED(hr)) {
                    UIElementRef childElement = new UIElementRef(pChild.getValue());
                    accChildren.add(childElement);
                }
            }
        }

        return accChildren;
    }

    public long get_RoleID(){
        Variant.VARIANT.ByReference varResult = new Variant.VARIANT.ByReference();
        Variant.VARIANT varChild = new Variant.VARIANT();

        varChild.setValue((short)Variant.VT_I4, new WinDef.LONG(0));

        WinNT.HRESULT hr = this.get_accRole(varChild, varResult);

        COMUtils.checkRC(hr);

        return ((WinDef.LONG)varResult.getValue()).longValue();
    }

    public String get_Role(){
        WinDef.DWORD roleId = new WinDef.DWORD(this.get_RoleID());
        char[] buff;
        String text= "";
        int res = Oleacc.INSTANCE.GetRoleText(roleId.intValue(), null, 0);
        if (res > 0){
            buff = new char[res + 1];
            res= Oleacc.INSTANCE.GetRoleText(roleId.intValue(), buff, res + 1);
            if(res > 0)
                text= Native.toString(buff);
        }

        return text;
    }

    public String get_Name(){
        WTypes.BSTRByReference pstr = new WTypes.BSTRByReference();

        Variant.VARIANT varChild = new Variant.VARIANT();
        varChild.setValue((short)Variant.VT_I4, new WinDef.LONG(0));

        WinNT.HRESULT hr = this.get_accName(varChild, pstr);

        if (COMUtils.SUCCEEDED(hr))
            return pstr.getString();

        return "Null";
    }

    public Location get_Location(){
        NativeLongByReference pxLeft = new NativeLongByReference();
        NativeLongByReference pyTop = new NativeLongByReference();
        NativeLongByReference pcxWidth = new NativeLongByReference();
        NativeLongByReference pcyHeight = new NativeLongByReference();
        Variant.VARIANT varChild = new Variant.VARIANT();
        varChild.setValue((short)Variant.VT_I4, new WinDef.LONG(0));

        WinNT.HRESULT hr = this.get_accLocation(pxLeft,pyTop,pcxWidth,pcyHeight,varChild);
        Location Location = new Location();
        if (COMUtils.SUCCEEDED(hr))
        Location.x = (int) (pxLeft.getValue()).longValue();
        Location.y = (int) (pyTop.getValue()).longValue();
        Location.w = (int) (pcxWidth.getValue()).longValue();
        Location.h = (int) (pcyHeight.getValue()).longValue();
        return Location;

    }

    public UIElementRef findElementsByText(String text, int index){
        return findElementsByWait(findElementsByText,this,text,DEFAULT_TIMEOUT).get(index);
    }

    public UIElementRef findElementsByPartialText(String text, int index){
        return findElementsByWait(findElementsByPartialText,this,text,DEFAULT_TIMEOUT).get(index);
    }

    public UIElementRef findElementByAutomationId(String text){
        return findElementByWait(findElementByAutomationId,this,text,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementByFull(String text){
        return findElementByWait(findElementByAutomationId,this,text,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementByFullDescription(String fullDescription){
        return findElementByWait(findElementByFullDescription,this,fullDescription,DEFAULT_TIMEOUT);
    }

    public void click(boolean toHWND){
        CallUser32.leftMouseClickToHWND(this,this.get_Location());
    }

    public void click(){
        CallUser32.setForegroundWindow(this);
        int x = this.get_Location().x + this.get_Location().w/2;
        int y = this.get_Location().y + this.get_Location().h/2;
        CallUser32.leftMouseClick(x, y);
    }

    public void clear(){
        CallUser32.setForegroundWindow(this);
        int x = this.get_Location().x + this.get_Location().w/2;
        int y = this.get_Location().y + this.get_Location().h/2;
        CallUser32.clear(x, y);
    }

    public void type(String text){
        CallUser32.setForegroundWindow(this);
        int x = this.get_Location().x + this.get_Location().w/2;
        int y = this.get_Location().y + this.get_Location().h/2;
        CallUser32.type(x, y,text);
    }

    public void drag(WinDef.RECT startPosition,WinDef.RECT endPosition){
        CallUser32.setForegroundWindow(this);
        WinDef.RECT rect = CallUser32.GetDesktopRect();
        int x = (this.get_Location().x + this.get_Location().w/2) * 65535/rect.right;
        int y = (this.get_Location().y + this.get_Location().h/2) * 65535/rect.bottom;
        CallUser32.leftMouseClick(x, y);
    }

}