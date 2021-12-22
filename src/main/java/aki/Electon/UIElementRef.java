package aki.Electon;

import aki.Mac.*;
import aki.Windows.CallUser32;
import aki.Electon.FindUIElement;
import aki.Electon.WaitFun;
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

    public UIElementRef findElementLocationByImage(String imagePath){
        return findElementByWait(findElementLocationByImage,imagePath,DEFAULT_TIMEOUT);
    }

    public void click(){
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.leftMouseClick(this.x,this.y);}
        else{
            CallQuartzEventServices.leftMouseSingleClickEvent(this.x,this.y);
        }
    }

    public void hover(){
        if(System.getProperty("os.name").contains("Windows")){
            CallQuartzEventServices.mouseMoveEvent(this.x,this.y);
        }else {
            CallQuartzEventServices.mouseMoveEvent(this.x,this.y);
        }
    }

    public void doubleClick(){
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.leftMouseDoubleClick(this.x,this.y);
        }else {
            CallQuartzEventServices.leftMouseDoubleClickEvent(this.x,this.y);
        }
    }

    public void longClick(int duration) throws InterruptedException {
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.leftMouseLongClick(this.x,this.y,duration);
        }else {
            CallQuartzEventServices.mouseLongPressEvent(this.x,this.y,duration);
        }
    }

    public void clear(){
        if(System.getProperty("os.name").contains("Windows")){
            CallUser32.clear(this.x,this.y);
        }else{
            CallQuartzEventServices.clear(this.x,this.y);
        }
    }

    public void type(String text){
        if (System.getProperty("os.name").contains("Windows")){
            CallUser32.type(this.x,this.y,text);
        }else {
            CallQuartzEventServices.type(this.x,this.y,text);
        }
    }
}
