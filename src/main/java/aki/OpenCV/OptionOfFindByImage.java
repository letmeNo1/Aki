package aki.OpenCV;

public class OptionOfFindByImage {
    String imagePath;
    float ratioThreshValue;
    int cluster;

    public void setRatioThreshValue(float ratioThreshValue){
        this.ratioThreshValue = ratioThreshValue;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void setCluster(int cluster){
        this.cluster = cluster;
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public float getRatioThreshValue(){
        return this.ratioThreshValue;
    }

    public int getCluster(){
        return this.cluster;
    }
}
