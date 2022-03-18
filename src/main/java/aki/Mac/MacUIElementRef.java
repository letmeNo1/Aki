package aki.Mac;

import aki.Common.ImageAssert;
import aki.OpenCV.FindUIElementByImage;
import aki.Common.WaitFun;
import aki.Mac.CoreGraphics.CGGeometry.CGFloat;
import aki.OpenCV.OptionOfFindByImage;
import com.sun.jna.Pointer;
import com.sun.jna.platform.mac.CoreFoundation;
import com.sun.jna.platform.mac.CoreFoundation.*;
import com.sun.jna.ptr.PointerByReference;
import aki.Common.CurrentAppRefInfo;
import aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import aki.Mac.CoreGraphics.CGGeometry.CGSize;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MacUIElementRef extends CFTypeRef implements FindUIElement, WaitFun, FindUIElementByImage, ImageAssert {
    public MacUIElementRef() {
        super();
    }

    public MacUIElementRef(Pointer p) {
        super(p);
    }

    private CGFloat x = this.get_CGPoint().x;
    private CGFloat y = this.get_CGPoint().y;


    private final CallAppServices callAppServicesApi = new CallAppServices();

    private final int DEFAULT_TIMEOUT = 20000;

    public void setXY(CGFloat[] vars) {
        this.x = vars[0];
        this.y = vars[1];
    }

    public int getX() {
        return this.x.intValue();
    }

    public int getY() {
        return this.y.intValue();
    }

    public void isForefront(){
        MacUIElementRef topLevelElement;
        MacUIElementRef currentLevelElement = this;
        while (true) {
            MacUIElementRef parentElement = currentLevelElement.get_ParentElement();
            if (parentElement.get_Role().contains("AXWindow")){
                topLevelElement = parentElement;
                break;
            }else{
                currentLevelElement = parentElement;
            }
        }
        if(topLevelElement.get_FocusedAttribute()){
            CallNSWorkspace.launchApp(CurrentAppRefInfo.getInstance().getBundleIdentifier());
        }
    }

    public String get_Title(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXTitle"),value);
    }

    public String get_Role(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXRole"),value);
    }

    public String get_SubRole(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXSubrole"),value);
    }

    public String get_Help(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXHelp"),value);
    }

    public boolean get_FocusedAttribute(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfBooleanType(this, callAppServicesApi.createCFString("AXFocused"),value);
    }

    public boolean get_Main(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfBooleanType(this, callAppServicesApi.createCFString("AXMain"),value);
    }

    public String get_AXIdentifier(){
        PointerByReference value = new PointerByReference();
        String identifier = "Null";
        if(this.get_AttributeNames().contains("AXIdentifier")){
            identifier = CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXIdentifier"),value);
        }
        return identifier;
    }

    public String get_Value(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfStringType(this, callAppServicesApi.createCFString("AXValue"),value);
    }

    public List<MacUIElementRef> get_ChildrenElements(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfAXUIElementRefList(this, callAppServicesApi.createCFString("AXChildren"),value);
    }

    public List<String> get_AttributeNames(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getAXUIElementCopyAttributeNames(this ,value);
    }

    public MacUIElementRef get_ParentElement(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfAXUIElementRef(this, callAppServicesApi.createCFString("AXParent"),value);
    }

    public MacUIElementRef get_TopLevelUIElement(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfAXUIElementRef(this, callAppServicesApi.createCFString("AXTopLevelUIElement"),value);
    }


    public CGPoint get_CGPoint(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfCGPoint(this, callAppServicesApi.createCFString("AXPosition"),value);
    }

    public CGSize get_CGSize(){
        PointerByReference value = new PointerByReference();
        return CallAppServices.getCopyAttributeValueOfCGSize(this, callAppServicesApi.createCFString("AXSize"),value);
    }

    public void press(){
        CallAppServices.axUIElementPerformAction(this, callAppServicesApi.createCFString("AXPress"));
    }

    public void click(){
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.leftMouseSingleClickEvent(x,y);
    }

    public void hover(){
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.mouseMoveEvent(x,y);
    }

    public void doubleClick(){
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.leftMouseDoubleClickEvent(x,y);
        this.release();
    }

    public void longClick(int duration) throws InterruptedException {
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.mouseLongPressEvent(x,y,duration);
        this.release();
    }

    public void type(String input){
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.type(x,y,input);
        this.release();
    }

    public void clearInput(){
        isForefront();
        int x = this.get_CGPoint().x.intValue() + this.get_CGSize().width.intValue()/2;
        int y = this.get_CGPoint().y.intValue() + this.get_CGSize().height.intValue()/2;
        CallQuartzEventServices.clear(x,y);
        this.release();
    }

    public MacUIElementRef findElementsByText(String text, int index){
        return findElementsByWait(findElementsByText,this,text,DEFAULT_TIMEOUT).get(index);
    }

    public MacUIElementRef findElementsByText(String text, int index, int timeout){
        return findElementsByWait(findElementsByText,this,text,timeout).get(index);
    }

    public MacUIElementRef findElementByText(String text){
        return findElementByWait(findElementByText,this,text,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByText(String text, int timeout){
        return findElementByWait(findElementByText,this,text,timeout);
    }

    public MacUIElementRef findElementsByPartialText(String text, int index){
        return findElementsByWait(findElementsByPartialText,this, text,DEFAULT_TIMEOUT).get(index);
    }

    public MacUIElementRef findElementsByPartialText(String text, int index, int timeout){
        return findElementsByWait(findElementsByPartialText,this, text,timeout).get(index);
    }

    public MacUIElementRef findElementsByRole(String role, int index){
        return findElementsByWait(findElementsByRole,this,role,DEFAULT_TIMEOUT).get(index);
    }

    public MacUIElementRef findElementsByRole(String role, int index, int timeout){
        return findElementsByWait(findElementsByRole,this,role,timeout).get(index);
    }

    public MacUIElementRef findElementByRole(String role){
        return findElementByWait(findElementByRole,this,role,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByRole(String role, int timeout){
        return findElementByWait(findElementByRole,this,role,timeout);
    }


    public MacUIElementRef findElementsBySubRole(String SubRole, int index){
        return findElementsByWait(findElementsBySubRole,this,SubRole,DEFAULT_TIMEOUT).get(index);
    }

    public MacUIElementRef findElementsBySubRole(String SubRole, int index, int timeout){
        return findElementsByWait(findElementsBySubRole,this,SubRole,timeout).get(index);
    }

    public MacUIElementRef findElementBySubRole(String SubRole){
        return findElementByWait(findElementBySubRole,this,SubRole,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementBySubRole(String SubRole, int timeout){
        return findElementByWait(findElementBySubRole,this,SubRole,timeout);
    }

    public MacUIElementRef findElementByXpath(String xpath){
        return findElementByWait(findElementByXpath,this,xpath,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByXpath(String xpath, int timeout){
        return findElementByWait(findElementByXpath,this,xpath,timeout);
    }

    public MacUIElementRef findElementByIdentifier(String identifier){
        return findElementByWait(findElementByIdentifier,this,identifier,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByIdentifier(String identifier, int timeout){
        return findElementByWait(findElementByIdentifier,this,identifier,timeout);
    }

    public MacUIElementRef findElementByImage(String imagePath,float ratioThreshValue) {
        return  findElementByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByImage(String imagePath,float ratioThreshValue, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        CGFloat x = new CGFloat(point.x);
        CGFloat y = new CGFloat(point.y);
        this.setXY(new CGFloat[]{x,y});
        return this;
    }

    public MacUIElementRef findElementByImage(String imagePath) {
        return  findElementByImage(imagePath,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementByImage(String imagePath, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(0.4f);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        CGFloat x = new CGFloat(point.x);
        CGFloat y = new CGFloat(point.y);
        this.setXY(new CGFloat[]{x,y});
        return this;
    }


    public MacUIElementRef findElementsByImage(String imagePath,int cluster,int index){
        return findElementsByImage(imagePath,cluster,index,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementsByImage(String imagePath,int cluster, int index, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(0.4f);
        option.setCluster(cluster);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeOut);
        Point point = pointArrayList.get(index);
        CGFloat x = new CGFloat(point.x);
        CGFloat y = new CGFloat(point.y);
        this.setXY(new CGFloat[]{x,y});
        return this;
    }

    public MacUIElementRef findElementsByImage(String imagePath,float ratioThreshValue,int cluster,int index){
        return findElementsByImage(imagePath,ratioThreshValue,cluster,index,DEFAULT_TIMEOUT);
    }

    public MacUIElementRef findElementsByImage(String imagePath, float ratioThreshValue,int cluster, int index, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        option.setCluster(cluster);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeOut);
        Point point = pointArrayList.get(index);
        CGFloat x = new CGFloat(point.x);
        CGFloat y = new CGFloat(point.y);
        this.setXY(new CGFloat[]{x,y});
        return this;
    }

    public boolean assertElementExist(String var,String method) {
        return assertElementExist(var,method,DEFAULT_TIMEOUT);
    }

    public boolean assertElementExist(String var,String method,int timeout) {
        boolean res;
        if(Objects.equals(method, "ByText")){
            res = AssertElementExistByWait(findElementByText,this,var,timeout);
        }else if(Objects.equals(method, "ByIdentifier")){
            res = AssertElementExistByWait(findElementByIdentifier,this,var,timeout);
        }else if(Objects.equals(method, "ByXpath")){
            res = AssertElementExistByWait(findElementByXpath,this,var,timeout);
        }else if(Objects.equals(method,"ByPartialText")){
            res = AssertElementExistByWait(findElementByPartialText,this,var,timeout);
        }else if(Objects.equals(method,"ByRole")){
            res = AssertElementExistByWait(findElementByRole,this,var,timeout);
        }else if(Objects.equals(method,"BySubRole")){
            res = AssertElementExistByWait(findElementBySubRole,this,var,timeout);
        }
        else {
            throw new IllegalArgumentException("The method could not be found!");
        }
        return  res;
    }

    public void retain() {
        CoreFoundation.INSTANCE.CFRetain(this);
    }

    public void release() {
        CoreFoundation.INSTANCE.CFRelease(this);
    }
}
