package aki.Electon;

import aki.OpenCV.FindUIElementByImage;
import aki.Common.WaitFun;
import aki.Mac.*;
import aki.Windows.CallUser32;
import org.opencv.core.Point;

import java.util.ArrayList;

import static aki.OpenCV.FindUIElementByImage.findElementByImage;


public class UIElementRef implements WaitFun, FindUIElementByImage {
    private final int DEFAULT_TIMEOUT = 20000;
    public UIElementRef() {}

    public int x;
    public int y;

    public void setXY(int[] vars) {
        this.x = vars[0];
        this.y = vars[1];
    }

    public UIElementRef findElementByImage(String imagePath){
        Point point = findElementByWait(findElementByImage,imagePath,DEFAULT_TIMEOUT);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public UIElementRef findElementsByImage(String imagePath, int k, int index){
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,imagePath,k,DEFAULT_TIMEOUT);
        Point point = pointArrayList.get(index);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
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
