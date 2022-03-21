package aki.OpenCV;

public class OptionOfFindByImage {
    String platform;
    String imgScenePath;
    String devicesName;
    float ratioThreshValue;
    int cluster;

    public void setRatioThreshValue(float ratioThreshValue){
        this.ratioThreshValue = ratioThreshValue;
    }

    public void setImagePath(String imagePath){
        this.imgScenePath = imagePath;
    }

    public void setCluster(int cluster){
        this.cluster = cluster;
    }

    public void setPlatform(String platform){
        this.platform = platform;
    }


    public String getImgScenePath(){
        return this.imgScenePath;
    }

    public float getRatioThreshValue(){
        return this.ratioThreshValue;
    }

    public int getCluster(){
        return this.cluster;
    }

    public String getPlatform(){
        return this.platform;
    }
}
