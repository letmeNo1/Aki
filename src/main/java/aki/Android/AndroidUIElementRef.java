package aki.Android;

import aki.Common.WaitFun;
import aki.Mac.CallQuartzEventServices;
import aki.OpenCV.FindUIElementByImage;
import aki.OpenCV.OptionOfFindByImage;
import aki.Windows.*;
import org.opencv.core.Point;

import java.io.IOException;
import java.util.ArrayList;

public class AndroidUIElementRef implements WaitFun, FindUIElementByImage{
    public AndroidUIElementRef() {}

    public AndroidUIElementRef(String uuid) {
        this.platform = this.platform + "/" + uuid;
    }

    private int x;
    private int y;
    private int h;
    private int w;
    private String platform = "Android";

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getPlatform() {
        return this.platform;
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

    public AndroidUIElementRef findElementByImage(String imagePath, float ratioThreshValue){
        return findElementByImage(imagePath,ratioThreshValue,DEFAULT_TIMEOUT);
    }

    public AndroidUIElementRef findElementByImage(String imagePath, float ratioThreshValue, int timeOut){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setPlatform("Android");
        option.setImagePath(imagePath);
        option.setRatioThreshValue(ratioThreshValue);
        Point point = findElementByWait(findElementByImage,option,timeOut);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public AndroidUIElementRef findElementByImage(String imagePath){
        return findElementByImage(imagePath,DEFAULT_TIMEOUT);
    }

    public AndroidUIElementRef findElementByImage(String imagePath,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setPlatform(this.platform);
        option.setImagePath(imagePath);
        option.setRatioThreshValue(0.4f);
        Point point = findElementByWait(findElementByImage,option,timeout);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public AndroidUIElementRef findElementsByImage(String imagePath, int cluster, int index) {
        return findElementsByImage(imagePath,cluster,index,DEFAULT_TIMEOUT);
    }

    public AndroidUIElementRef findElementsByImage(String imagePath, int cluster, int index,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setPlatform(this.platform);
        option.setImagePath(imagePath);
        option.setCluster(cluster);
        option.setRatioThreshValue(0.4f);
        ArrayList<Point> pointArrayList = findPointListByWait(findElementsByImage,option,timeout);
        Point point = pointArrayList.get(index);
        this.setXY(new int[]{(int) point.x,(int) point.y});
        return this;
    }

    public AndroidUIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index) {
        return findElementsByImage(imagePath,ratioThreshValue,cluster,index,DEFAULT_TIMEOUT);
    }

    public AndroidUIElementRef findElementsByImage(String imagePath, float ratioThreshValue, int cluster, int index,int timeout){
        OptionOfFindByImage option = new OptionOfFindByImage();
        option.setPlatform(this.platform);
        option.setImagePath(imagePath);
        option.setCluster(cluster);
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
        option.setPlatform(this.platform);
        option.setRatioThreshValue(ratioThreshValue);
        boolean res;
        res = AssertElementExistByWait(findElementByImage,option,timeout);
        return  res;
    }

    public void click() throws IOException {
        adbCommand.click(this.getX(),this.getY());
    }

    public void longClick(int duration) throws IOException {
        adbCommand.longClick(this.getX(),this.getY(),duration);
    }


}
