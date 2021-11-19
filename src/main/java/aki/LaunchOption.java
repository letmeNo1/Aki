package aki;

public class LaunchOption {
    private int defaultTimeout = 20000;
    private boolean checkWindowsShowOption = true;
    private boolean isUWPApp = false;

    public LaunchOption(){
    }

    public Boolean getCheckWindowsShowOption(){
        return this.checkWindowsShowOption;
    }

    public void setCheckWindowsShow(Boolean checkWindowsShowOption){
         this.checkWindowsShowOption = checkWindowsShowOption ;
    }

    public int getDefaultTimeout(){
        return this.defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout){
        this.defaultTimeout = defaultTimeout;
    }

    public boolean getIsUWPApp(){
        return this.isUWPApp;
    }

    public void setIsUWPApp(Boolean isUWPApp){
        this.isUWPApp = isUWPApp;
    }
}
