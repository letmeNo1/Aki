package aki.Mac.CoreGraphics;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.mac.CoreFoundation.*;
import aki.Mac.CoreGraphics.CGGeometry.CGEventRef;
import aki.Mac.CoreGraphics.CGGeometry.CGImageRef;
import aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import aki.Mac.CoreGraphics.CGGeometry.CGRect;
import aki.Mac.CoreGraphics.CGImage.CGDataProviderRef;

public interface CoreGraphics extends Library {
    CoreGraphics INSTANCE = Native.load("CoreGraphics", CoreGraphics.class);
    /**
     CGMouseButton
     */
    int
            kCGMouseButtonLeft   = 0,
            kCGMouseButtonRight  = 1,
            kCGMouseButtonCenter = 2;

    /**
     CGEventTapLocation
     */
    int
            kCGHIDEventTap              = 0x0,
            kCGSessionEventTap          = 0x1,
            kCGAnnotatedSessionEventTap = 0x2;

    void CGEventPostToPid(int pid, CGEventRef event);

    /**
     * Create a mouse event
     */
    CGEventRef CGEventCreateMouseEvent(String source, int mouseType, CGPoint mouseCursorPosition, int mouseButton);

    /**
     * Create a scrolling event
     */
    CGEventRef CGEventCreateScrollWheelEvent(String source, int mouseType, int wheelCount, int x, int y);

    void CGEventSetIntegerValueField(CGEventRef theEvent,int field, int clickCount);

    void CGEventSetType(CGEventRef event, int type);

    /**
     * Post an event, Used to perform operational events
     */
    void CGEventPost(int cgEventTapLocation,  CGEventRef event);

    /**
     * Release event
     */
    void CFRelease(CGEventRef event);

    /**
     * Sets the event flags of a Quartz event.
     * Usually used to combine events
     */
    void CGEventSetFlags(CGEventRef event, int flags);


    /**
     * Returns a new Quartz keyboard event.
     */
    CGEventRef CGEventCreateKeyboardEvent(String source, int virtualKey, boolean keyDown);


    /**
     *
     * Quartz Window Services
     */
    int CGMainDisplayID();

    CFArrayRef CGWindowListCopyWindowInfo(int option, int relativeToWindow);

    CGImageRef CGWindowListCreateImage(CGRect screenBounds,int CGWindowListOption,int CGWindowID, int CGWindowImageOption);

    CGImageRef CGDisplayCreateImageForRect(int pid,CGRect rect);

    void CGImageRelease(CGImageRef image);

    boolean CGImageIsMask(CGImageRef image);

    int CGImageGetWidth(CGImageRef image);

    int CGImageGetHeight(CGImageRef image);

    int CGImageGetBitsPerComponent(CGImageRef image);

    int CGImageGetBitsPerPixel(CGImageRef image);

    int CGImageGetBytesPerRow(CGImageRef image);

    int CGImageGetBitmapInfo(CGImageRef image);

    Float CGImageGetDecode(CGImageRef image);

    CGDataProviderRef CGImageGetDataProvider(CGImageRef image);

    CFDataRef CGDataProviderCopyData(CGDataProviderRef provider);

    int CFDataGetLength(CFDataRef dataRef);

    Pointer CFDataGetBytePtr(CFDataRef ref);
}