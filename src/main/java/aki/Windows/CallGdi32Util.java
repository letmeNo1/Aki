package aki.Windows;

import aki.Common.TraceLog;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.GDI32Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;


public class CallGdi32Util {
    static TraceLog log = new TraceLog();

    public static void takeScreenshot(WinDef.HWND hWnd, String savaPath){
        // set a compatible pixel format
        File outPutFile = new File(savaPath);
        BufferedImage buffered = GDI32Util.getScreenshot(hWnd);
        try{
            ImageIO.write(buffered, "png", outPutFile);
        }catch (Exception E){
            log.logErr(MessageFormat.format("Save failed, {0}", E.getMessage()));
        }
    }

    public static void takeScreenshotForDesktop(String savaPath){
        WinDef.HWND hWnd = User32.INSTANCE.GetDesktopWindow();
        File outPutFile = new File(savaPath);
        BufferedImage buffered = GDI32Util.getScreenshot(hWnd);
        try{
            ImageIO.write(buffered, "png", outPutFile);
        }catch (Exception E){
            log.logErr(MessageFormat.format("Save failed, {0}", E.getMessage()));
        }
    }
}
