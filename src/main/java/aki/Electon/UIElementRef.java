package aki.Electon;

import aki.OpenCV.FindUIElementByImage;
import aki.Common.WaitFun;
import aki.Mac.*;
import aki.OpenCV.OptionOfFindByImage;
import aki.Windows.CallUser32;
import org.opencv.core.Point;

import java.util.ArrayList;



public class UIElementRef implements WaitFun, FindUIElementByImage {
    private final int DEFAULT_TIMEOUT = 20000;
    public UIElementRef() {}

    private int x;
    private int y;

    public void setXY(int[] vars) {
        this.x = vars[0];
        this.y = vars[1];
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public UIElementRef findElementByImage(String imagePath,float ratioThreshValue){
        return findElementByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementByImage(String imagePath,float ratioThreshValue,int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public UIElementRef findElementByImage(String imagePath){
        return findElementByImage(imagePath,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementByImage(String imagePath,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setPlatform("PC");
        option.setRatioThreshValue(0.4f);
        Point point = findElementByWait(findElementByImage,option,timeout);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public UIElementRef findElementsByImage(String imagePath, int cluster, int index) {
        return findElementsByImage(imagePath,cluster,index,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementsByImage(String imagePath, int cluster, int index,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setCluster(cluster);
        option.setPlatform("PC");
        option.setRatioThreshValue(0.4f);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeout);
        Point point = pointArrayList.get(index);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public UIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index) {
        return findElementsByImage(imagePath,ratioThreshValue,cluster,index,DEFAULT_TIMEOUT);
    }

    public UIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setCluster(cluster);
        option.setPlatform("PC");
        option.setRatioThreshValue(ratioThreshValue);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeout);
        Point point = pointArrayList.get(index);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public boolean assertElementExistByImage(String imagePath) {
        return assertElementExistByImage(imagePath,0.4f,DEFAULT_TIMEOUT);
    }

    public boolean assertElementExistByImage(String imagePath,int timeout) {
        return assertElementExistByImage(imagePath,0.4f,timeout);
    }

    public boolean assertElementExistByImage(String imagePath,float ratioThreshValue) {
        return assertElementExistByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    public boolean assertElementExistByImage(String imagePath, float ratioThreshValue,int timeout) {
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setImagePath(imagePath);
        option.setPlatform("PC");
        option.setRatioThreshValue(ratioThreshValue);
        boolean res;
        res = AssertElementExistByWait(findElementByImage,option,timeout);
        return  res;
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
