package aki.Mac;

import com.sun.jna.Pointer;
import com.sun.jna.platform.mac.CoreFoundation.*;
import aki.Mac.CoreGraphics.CGGeometry.CGImageRef;
import aki.Mac.CoreGraphics.CGGeometry.CGRect;
import aki.Mac.CoreGraphics.CGImage.CGDataProviderRef;
import aki.Mac.CoreGraphics.CoreGraphics;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class CallQuartzWindowServices {
    private static final CoreGraphics cg = CoreGraphics.INSTANCE;

    /**
     * List all windows in this user session, including both on- and off-screen
     * windows. The parameter `relativeToWindow' should be `kCGNullWindowID'.
     */
    public static final int kCGWindowListOptionAll = 0;

    /**
     * List all on-screen windows in this user session, ordered from front to back.
     * The parameter `relativeToWindow' should be `kCGNullWindowID'.
     */
    public static final int kCGWindowListOptionOnScreenOnly = (1);

    /**
     * List all on-screen windows above the window specified by `relativeToWindow',
     * ordered from front to back.
     */
    public static final int kCGWindowListOptionOnScreenAboveWindow = (1 << 1);

    /**
     * List all on-screen windows below the window specified by `relativeToWindow',
     * ordered from front to back.
     */
    public static final int kCGWindowListOptionOnScreenBelowWindow = (1 << 2);

    /**
     * Include the window specified by `relativeToWindow' in any list, effectively
     * creating `at-or-above' or `at-or-below' lists.
     */
    public static final int kCGWindowListOptionIncludingWindow = (1 << 3);

    /**
     *  Exclude any windows from the list that are elements of the desktop.
     */

    public static final int kCGWindowListExcludeDesktopElements = (1 << 4);


    public static final int kCGWindowImageDefault = 0;
    public static final int kCGWindowImageBoundsIgnoreFraming = 1;
    public static final int kCGWindowImageShouldBeOpaque = 1 << 1;
    public static final int kCGWindowImageOnlyShadows = 1 << 2;
    public static final int kCGWindowImageBestResolution = 1 << 3;
    public static final int kCGWindowImageNominalResolution = 1 << 4;

    // Set up keys for dictionary lookup
    private static final CFStringRef kCGWindowNumber = CFStringRef.createCFString("kCGWindowNumber");
    private static final CFStringRef kCGWindowOwnerPID = CFStringRef.createCFString("kCGWindowOwnerPID");
    private static final CFStringRef kCGWindowName = CFStringRef.createCFString("kCGWindowName");
    private static final CFStringRef kCGWindowOwnerName = CFStringRef.createCFString("kCGWindowOwnerName");


    public static CFArrayRef getWindowRefListOnScreenOnly(){
        return cg.CGWindowListCopyWindowInfo(kCGWindowListOptionOnScreenOnly, 0);
    }

    public static long getWindowNumber(CFDictionaryRef windowRef){
        CFNumberRef windowNumber = new CFNumberRef(windowRef.getValue(kCGWindowNumber));
        return windowNumber.longValue();
    }

    public static long getWindowPid(CFDictionaryRef windowRef){
        CFNumberRef windowNumber = new CFNumberRef(windowRef.getValue(kCGWindowOwnerPID));
        return windowNumber.longValue();
    }

    public static List<Integer> getWindowIdsByPid(int pid){
        List<Integer> windowNumbers = new ArrayList<>();
        CFArrayRef windowInfo = getWindowRefListOnScreenOnly();
        for(int i =0;i<windowInfo.getCount();i++){
            Pointer result = windowInfo.getValueAtIndex(i);
            CFDictionaryRef windowRef = new CFDictionaryRef(result);
            if(getWindowPid(windowRef)==pid){
                windowNumbers.add((int) getWindowNumber(windowRef));
            }

        }
        windowInfo.release();
        return windowNumbers;
    }

    private static CGImageRef getImageRefByWindowId(int windowId){
        CGRect bounds = new CGRect(0,0,0,0);
        return cg.CGWindowListCreateImage(bounds,kCGWindowListOptionIncludingWindow,windowId,kCGWindowListOptionOnScreenOnly);
    }

    public static void takeScreenshot(int windowId, String savaPath){
        CGImageRef imageRef = getImageRefByWindowId(windowId);
        CGDataProviderRef dataProviderRef = cg.CGImageGetDataProvider(imageRef);
        CFDataRef dataRef = cg.CGDataProviderCopyData(dataProviderRef);
        int length = cg.CFDataGetLength(dataRef);
        int width = cg.CGImageGetWidth(imageRef);
        int height = cg.CGImageGetHeight(imageRef);
        int bitsPerPixel = cg.CGImageGetBitsPerPixel(imageRef);
        int bytesPerPixel = bitsPerPixel / 8;
        int bytesPerRow = cg.CGImageGetBytesPerRow(imageRef);
        DataBufferInt buffer = new DataBufferInt(length / bytesPerPixel);
        ByteBuffer byteBuffer = cg.CFDataGetBytePtr(dataRef).getByteBuffer(0, length);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.get(buffer.getData());
        DirectColorModel cm = new DirectColorModel(
                bitsPerPixel,
                0x00ff0000,        // Red
                0x0000ff00,        // Green
                0x000000ff,        // Blue
                0xff000000         // Alpha
        );
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, bytesPerRow / bytesPerPixel, cm.getMasks(), new Point());
        BufferedImage buffered = new BufferedImage(cm, raster, false, null);
        buffered.setRGB(255, 255, new Color(0f, 1f, 0).getRGB());
        File outPutFile = new File(savaPath);
        try{
            ImageIO.write(buffered, "png", outPutFile);
        }catch (Exception E){
            System.out.println(MessageFormat.format("Save failed, {0}", E.getMessage()));
        }
        cg.CGImageRelease(imageRef);
    }

    public static void takeScreenshotForDesktop(String savaPath)  {
        try{
            Runtime.getRuntime().exec("screencapture " + savaPath);
        }catch (Exception ignored){
        }

    }
}
