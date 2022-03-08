package aki;

public class LaunchOption {
    private int defaultTimeout = 20000;
    private int launchTimeout = 20000;
    private boolean checkWindowsShowOption = true;
    private boolean isUWPApp = false;
    private boolean alreadyLaunch = false;

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


    public int getLaunchTimeoutTimeout(){
        return  this.launchTimeout;
    }

    public void setLaunchTimeoutTimeout(int defaultTimeout){
        this.launchTimeout = defaultTimeout;
    }

    public boolean getIsUWPApp(){
        return this.isUWPApp;
    }

    public void setIsUWPApp(Boolean isUWPApp){
        this.isUWPApp = isUWPApp;
    }

    public boolean getAlreadyLaunch(){
        return this.alreadyLaunch;
    }

    public void setAlreadyLaunch(Boolean alreadyLaunch){
        this.alreadyLaunch = alreadyLaunch;
    }
}
