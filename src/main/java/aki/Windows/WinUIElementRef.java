package aki.Windows;

import aki.Common.ImageAssert;
import aki.OpenCV.FindUIElementByImage;
import aki.Common.WaitFun;
import aki.Mac.CallQuartzEventServices;
import aki.OpenCV.OptionOfFindByImage;
import aki.Windows.WinApi.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Dispatch;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static aki.Windows.CallUser32.findWindowByName;
import static aki.Windows.WinApi.AutoElementPropertyIdentifiers.*;

public class WinUIElementRef extends IAccessible implements FindUIElement, WaitFun, FindUIElementByImage, ImageAssert {
    public WinUIElementRef(Pointer p) {
        super(p);
    }
    private int x = this.get_Location().x;
    private int y = this.get_Location().y;
    private int h = this.get_Location().h;
    private int w = this.get_Location().w;

    public void setXY(int[] vars) {
        this.x = vars[0];
        this.y = vars[1];
    }

    public void setHW(int[] vars) {
        this.h = vars[0];
        this.w = vars[1];
    }

    public int[] getXY(int[] vars) {
        vars[0]= this.x;
        vars[1] = this.y;
        return vars;
    }

    public int[] getHW(int[] vars) {
        vars[0]= this.h;
        vars[1] = this.w;
        return vars;
    }


    private int DEFAULT_TIMEOUT = 20000;

    public void setTimeout(int timeout){
        this.DEFAULT_TIMEOUT = timeout;
    }

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

    public List<WinUIElementRef> get_ChildrenElements() {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
        List<WinUIElementRef> accChildren= new ArrayList<>();
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
                    WinUIElementRef childElement = new WinUIElementRef(pChild.getValue());
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


    public WinUIElementRef findElementByText(String text){
        return findElementByText(text,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByText(String text, int timeOut){
        return findElementByWait(findElementByText,this,text,timeOut);
    }

    public WinUIElementRef findElementsByText(String text, int index){
        return findElementsByText(text,index,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementsByText(String text, int index, int timeOut){
        return findElementsByWait(findElementsByText,this,text,timeOut).get(index);
    }

    public WinUIElementRef findElementsByPartialText(String text, int index){
        return findElementsByPartialText(text,index,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementsByPartialText(String text, int index, int timeOut){
        return findElementsByWait(findElementsByPartialText,this,text,timeOut).get(index);
    }

    public WinUIElementRef findElementByPartialText(String text ){
        return findElementByPartialText(text,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByPartialText(String text, int timeOut ){
        return findElementByWait(findElementByPartialText,this,text,timeOut);
    }

    public WinUIElementRef findElementByRole(String role){
        return findElementByRole(role,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByRole(String role, int timeOut){
        return findElementByWait(findElementByRole,this,role,timeOut);
    }

    public WinUIElementRef findElementsByRole(String role, int index){
        return findElementsByRole(role,index,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementsByRole(String role, int index, int timeOut){
        return findElementsByWait(findElementsByRole,this,role,timeOut).get(index);
    }

    public WinUIElementRef findElementByAutomationId(String text) {
        return findElementByAutomationId(text,DEFAULT_TIMEOUT);
    }
    public WinUIElementRef findElementByAutomationId(String text, int timeOut){
        return findElementByWait(findElementByAutomationId,this,text,timeOut);
    }

    public WinUIElementRef findElementsByAutomationId(String text, int index) {
        return findElementsByAutomationId(text,index,DEFAULT_TIMEOUT);
    }
    public WinUIElementRef findElementsByAutomationId(String text, int index, int timeOut){
        return findElementsByWait(findElementsByAutomationId,this,text,timeOut).get(index);
    }

    public WinUIElementRef findElementByFullDescription(String fullDescription) {
        return findElementByFullDescription(fullDescription,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByFullDescription(String fullDescription, int timeOut){
        return findElementByWait(findElementByFullDescription,this,fullDescription,timeOut);
    }

    public WinUIElementRef findWindowByWindowName(String windowName, int timeOut){
        return CallOleacc.getAccessibleObject(CallUser32.findWindowByNameByWait(windowName,timeOut));
    }

    public WinUIElementRef findElementByImage(String imagePath) {
        return  findElementByImage(imagePath,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByImage(String imagePath,float ratioThreshValue) {
        return  findElementByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementByImage(String imagePath, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(0.4f);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        this.setHW(new int[]{0,0});
        return this;
    }

    public WinUIElementRef findElementByImage(String imagePath, float ratioThreshValue, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        this.setHW(new int[]{0,0});
        return this;
    }

    public WinUIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index){
        return findElementsByImage(imagePath,ratioThreshValue,cluster,index,DEFAULT_TIMEOUT);
    }

    public WinUIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        option.setCluster(cluster);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeOut);
        Point point = pointArrayList.get(index);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        this.setHW(new int[]{0,0});
        return this;
    }

    public boolean assertElementExist(String var,String method) {
        return  assertElementExist(var,method,DEFAULT_TIMEOUT);
    }

    public boolean assertElementExist(String var,String method,int timeout) {
        boolean res;
        if(Objects.equals(method, "ByText")){
            res = AssertElementExistByWait(findElementByText,this,var,timeout);
        }else if(Objects.equals(method, "ByFullDescription")){
            res = AssertElementExistByWait(findElementByFullDescription,this,var,timeout);
        }else if(Objects.equals(method, "ByAutomationId")){
            res = AssertElementExistByWait(findElementByAutomationId,this,var,timeout);
        }else if(Objects.equals(method,"ByPartialText")){
            res = AssertElementExistByWait(findElementByPartialText,this,var,timeout);
        }else if(Objects.equals(method,"ByRole")){
            res = AssertElementExistByWait(findElementByRole,this,var,timeout);
        }else {
            throw new IllegalArgumentException("The method could not be found!");
        }
        return  res;
    }

    public boolean assertWindowExist(String windowName) {
        return assertWindowExist(windowName,DEFAULT_TIMEOUT);
    }

    public boolean assertWindowExist(String windowName,int timeout) {
        return AssertWindowExistByWait(findWindowByName,windowName,timeout);
    }

    public void click(boolean toHWND){
        CallUser32.leftMouseClickToHWND(this,this.get_Location());
    }

    private int[] coordinateTransformation(WinUIElementRef winUiElementRef){
        int x;
        int y;
        if(winUiElementRef.h!=0&& winUiElementRef.w!=0){
            x = winUiElementRef.x + winUiElementRef.w/2;
            y = winUiElementRef.y + winUiElementRef.h/2;
        }else {
            x = winUiElementRef.x;
            y = winUiElementRef.y;
        }

        return new int[]{x,y};
    }

    public void click(){
        CallUser32.SetFocus(this);
        CallUser32.leftMouseClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void hover(){
        CallUser32.SetFocus(this);
        CallUser32.MouseMove(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void doubleClick(){
        CallUser32.SetFocus(this);
        CallUser32.leftMouseDoubleClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void longClick(int duration) throws InterruptedException {
        CallUser32.SetFocus(this);
        CallUser32.leftMouseLongClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1],duration);
    }

    public void clear(){
        CallUser32.SetFocus(this);
        CallUser32.clear(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void type(String text){
        CallUser32.SetFocus(this);
        CallUser32.type(coordinateTransformation(this)[0], coordinateTransformation(this)[1],text);
    }

    public void kill() {
        CallUser32.sendMessage(CallOleacc.getHWNDFromUIElement(this), WinUser.WM_CLOSE,new WinDef.WPARAM(0),
        new WinDef.LPARAM(0));
    }

    public void takeScreenshot(String path){
        PointerByReference ptr = new PointerByReference();
        WinNT.HRESULT res = Oleacc.INSTANCE.WindowFromAccessibleObject(this.getPointer(), ptr);
        COMUtils.checkRC(res);
        CallGdi32Util.takeScreenshot(new WinDef.HWND(ptr.getValue()),path);
    }

    public void release() {
        Kernel32Ex.INSTANCE.GlobalFree(this.getPointer());
    }

}
