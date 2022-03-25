package aki.Windows;

import aki.Common.TraceLog;
import com.sun.jna.platform.win32.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;



public class CallGdi32Util {
    static TraceLog log = new TraceLog();


    public static Mat BufImg2Mat(BufferedImage image) {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        Mat out;
        byte[] data;
        int r, g, b;

        if(image.getType() == BufferedImage.TYPE_INT_RGB)
        {
            out = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
            data = new byte[image.getWidth() * image.getHeight() * (int)out.elemSize()];
            int[] dataBuff = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            for(int i = 0; i < dataBuff.length; i++)
            {
                data[i*3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i*3 + 2] = (byte) ((dataBuff[i]) & 0xFF);
            }
        }
        else
        {
            out = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC1);
            data = new byte[image.getWidth() * image.getHeight() * (int)out.elemSize()];
            int[] dataBuff = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            for(int i = 0; i < dataBuff.length; i++)
            {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i]) & 0xFF);
                data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
        }
        out.put(0, 0, data);
        return out;
    }

    public static void takeScreenshot(WinDef.HWND hWnd, String savaPath) {
        // set a compatible pixel format
        File outPutFile = new File(savaPath);
        BufferedImage buffered = GDI32Util.getScreenshot(hWnd);
        try {
            ImageIO.write(buffered, "png", outPutFile);
        } catch (Exception E) {
            log.logErr(MessageFormat.format("Save failed, {0}", E.getMessage()));
        }
    }

    public static void takeScreenshotForDesktop(String savaPath) {
        WinDef.HWND hWnd = User32.INSTANCE.GetDesktopWindow();
        File outPutFile = new File(savaPath);
        BufferedImage buffered = GDI32Util.getScreenshot(hWnd);
        try {
            ImageIO.write(buffered, "png", outPutFile);
        } catch (Exception E) {
            log.logErr(MessageFormat.format("Save failed, {0}", E.getMessage()));
        }
    }

    public static Mat getMatFromScreenshotForDesktop() {
        WinDef.HWND hWnd = User32.INSTANCE.GetDesktopWindow();
        BufferedImage buffered = GDI32Util.getScreenshot(hWnd);
        return BufImg2Mat(buffered);
    }

}