package aki.Android;

import java.io.IOException;

public class adbCommand {
    public static void excuseAdbCommand(String command){
        try {
            Runtime.getRuntime().exec("adb " + command);
        }catch (Exception ignored){

        }
    }

    public static void excuseAdbCommand(String command,String deviceName){
        try {
            Runtime.getRuntime().exec(String.format("adb -s %s ",deviceName)+command);
        }catch (Exception ignored){

        }
    }

    public static void click(int x,int y) {
        excuseAdbCommand(String.format("shell input tap %s %s", x,y));
    }

    public static void longClick(int x,int y,int duration) {
        excuseAdbCommand(String.format("shell input swipe %s %s %s %s %s", x,y,x,y, duration));
    }

    public static void swipe(int x1,int y1,int x2, int y2,int duration) {
        excuseAdbCommand(String.format("shell input swipe %s %s %s %s %s", x1,y1,x2,y2,duration));
    }

    public static void drag(int x1,int y1,int x2, int y2,int duration)  {
        excuseAdbCommand(String.format("shell input touchscreen swipe %s %s %s %s %s", x1,y1,x2,y2,duration));
    }
}
