package aki.Mac.CoreGraphics.QuartzEventServices;

public class CGEventType {
    /**
     * CGEventType
     */
    public static final int
            kCGEventNull                   = 0,
            kCGEventLeftMouseDown          = 1,
            kCGEventLeftMouseUp            = 2,
            kCGEventRightMouseDown         = 3,
            kCGEventRightMouseUp           = 4,
            kCGEventMouseMoved             = 5,
            kCGEventLeftMouseDragged       = 6,
            kCGEventRightMouseDragged      = 7,
            kCGEventKeyDown                = 0xA,
            kCGEventKeyUp                  = 0xB,
            kCGEventFlagsChanged           = 0xC,
            kCGEventScrollWheel            = 22,
            kCGEventTabletPointer          = 23,
            kCGEventTabletProximity        = 24,
            kCGEventOtherMouseDown         = 0x19,
            kCGEventOtherMouseUp           = 0x1A,
            kCGEventOtherMouseDragged      = 0x1B,
            kCGEventTapDisabledByTimeout   = 0xFFFFFFFE,
            kCGEventTapDisabledByUserInput = 0xFFFFFFFF;
}
