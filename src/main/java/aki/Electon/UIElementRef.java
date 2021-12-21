package aki.Electon;

import aki.CurrentAppRefInfo;
import aki.Mac.*;
import aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import aki.Mac.CoreGraphics.CGGeometry.CGSize;
import aki.Windows.CallUser32;
import aki.Windows.FindUIElement;
import aki.Windows.WaitFun;
import com.sun.jna.Pointer;
import com.sun.jna.platform.mac.CoreFoundation;
import com.sun.jna.platform.mac.CoreFoundation.CFTypeRef;
import com.sun.jna.ptr.PointerByReference;
import org.opencv.core.Point;

import java.util.List;


public class UIElementRef implements FindUIElement, WaitFun {
    private final int DEFAULT_TIMEOUT = 20000;
    public UIElementRef() {}

    public int x;
    public int y;

    public void setXY(int[] vars) {
        this.x = vars[0];
        this.y = vars[1];
    }

    public Point findElementLocationByImage(String imagePath){
        return findElementByWait(findElementLocationByImage,imagePath,DEFAULT_TIMEOUT);
    }

    public void click(){
        CallUser32.setForegroundWindow(this);
        CallUser32.leftMouseClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void hover(){
        CallUser32.setForegroundWindow(this);
        CallQuartzEventServices.mouseMoveEvent(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void doubleClick(){
        CallUser32.setForegroundWindow(this);
        CallUser32.leftMouseDoubleClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void longClick(int duration) throws InterruptedException {
        CallUser32.setForegroundWindow(this);
        CallUser32.leftMouseLongClick(coordinateTransformation(this)[0], coordinateTransformation(this)[1],duration);
    }

    public void clear(){
        CallUser32.setForegroundWindow(this);
        CallUser32.clear(coordinateTransformation(this)[0], coordinateTransformation(this)[1]);
    }

    public void type(String text){
        CallUser32.setForegroundWindow(this);
        CallUser32.type(coordinateTransformation(this)[0], coordinateTransformation(this)[1],text);
    }
}
