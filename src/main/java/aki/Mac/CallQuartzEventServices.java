package aki.Mac;

import aki.Mac.Appkit.AppAndEnvironment.NSPasteboard;
import aki.Mac.CoreGraphics.CGGeometry.CGEventRef;
import aki.Mac.CoreGraphics.CGGeometry.CGFloat;
import aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import aki.Mac.CoreGraphics.CoreGraphics;
import aki.Mac.CoreGraphics.QuartzEventServices.CGEventField;
import aki.Mac.CoreGraphics.QuartzEventServices.CGEventFlags;
import aki.Mac.CoreGraphics.QuartzEventServices.CGEventType;
import aki.Mac.CoreGraphics.QuartzEventServices.Keycodes;
import aki.Windows.CallUser32;
import aki.Windows.Location;

import static aki.Mac.CoreGraphics.CoreGraphics.*;

public class CallQuartzEventServices {
    private static final CoreGraphics cg = CoreGraphics.INSTANCE;

    public static CGPoint conversionCoordinate(int x, int y){
        CGPoint position = new CGPoint();
        position.y = new CGFloat(y);
        position.x = new CGFloat(x);
        return position;
    }

    public static void mouseMoveEvent(int x,int y ){
        CGPoint position = conversionCoordinate(x,y);
        CGEventRef theEvent = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventMouseMoved,position, kCGMouseButtonLeft);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
    }

    public static void scrollWheelEvent(){
        int kCGScrollEventUnitLine = 1;
        CGEventRef theEvent = cg.CGEventCreateScrollWheelEvent(null,kCGScrollEventUnitLine,2, 10,10);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
    }

    public static void leftMouseMoveEvent(int x,int y){
        mouseMoveEvent(x,y);
    }

    public static void leftMouseClickEvent(int x,int y,int clickCount){
        CGPoint position = conversionCoordinate(x,y);
        CGEventRef theEvent = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventLeftMouseDown,position, kCGMouseButtonLeft);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CGEventSetIntegerValueField(theEvent, CGEventField.kCGMouseEventClickState, clickCount);
        cg.CGEventSetType(theEvent, CGEventType.kCGEventLeftMouseUp);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
    }

    public static void rightMouseClickEvent(int x,int y){
        CGPoint position = conversionCoordinate(x,y);
        CGEventRef theEvent = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventRightMouseDown,position, kCGMouseButtonRight);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CGEventSetType(theEvent, CGEventType.kCGEventLeftMouseUp);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
    }

    public static void mouseLongPressEvent(int x,int y,int duration) throws InterruptedException {
        CGPoint position = conversionCoordinate(x,y);
        CGEventRef theEvent = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventLeftMouseDown,position,kCGMouseButtonLeft);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        Thread.sleep(duration);
        cg.CGEventSetType(theEvent, CGEventType.kCGEventLeftMouseUp);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
    }

    public static void leftMouseSingleClickEvent(int x, int y){
        leftMouseClickEvent(x,y,1);
    }

    public static void leftMouseDoubleClickEvent(int x, int y){
        leftMouseClickEvent(x,y,2);
    }

    public static void rightMouseSingleClickEvent(int x, int y){
        rightMouseClickEvent(x,y);
    }

    public static void leftMouseDraggedEvent(int x, int y,int x2, int y2,int duration) throws InterruptedException {
        CGPoint startPosition = conversionCoordinate(x,y);
        CGPoint endPosition = conversionCoordinate(x2,y2);
        CGEventRef theEvent = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventLeftMouseDown, startPosition, kCGMouseButtonLeft);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        CGEventRef theEvent2 = cg.CGEventCreateMouseEvent(null, CGEventType.kCGEventLeftMouseDragged, endPosition, kCGMouseButtonLeft);
        cg.CGEventPost(kCGHIDEventTap, theEvent2);
        Thread.sleep(duration);
        cg.CGEventSetType(theEvent, CGEventType.kCGEventLeftMouseUp);
        cg.CGEventPost(kCGHIDEventTap, theEvent);
        cg.CFRelease(theEvent);
        cg.CFRelease(theEvent2);
    }

    public static void type(int x,int y,String input){
        NSPasteboard ss = new NSPasteboard();
        ss.writeObjects(input);
        leftMouseSingleClickEvent(x,y);
        combinationKeyOperation(Keycodes.kVK_ANSI_V,CGEventFlags.kCGEventFlagMaskCommand);
    }

    public static void clear(int x,int y){
        leftMouseSingleClickEvent(x,y);
        combinationKeyOperation(Keycodes.kVK_ANSI_A,CGEventFlags.kCGEventFlagMaskCommand);
        combinationKeyOperation(Keycodes.kVK_Delete);
    }

    public static void combinationKeyOperation(int... keycodes){
        CoreGraphics cg = CoreGraphics.INSTANCE;
        CGEventRef event = cg.CGEventCreateKeyboardEvent (null, keycodes[0], true);
        if(keycodes.length==4){
            cg.CGEventSetFlags(event, keycodes[1] | keycodes[2] | keycodes[3]);
        }else if (keycodes.length==3) {
            cg.CGEventSetFlags(event, keycodes[1] | keycodes[2]);
        }else if (keycodes.length==2){
            cg.CGEventSetFlags(event, keycodes[1]);
        }
        cg.CGEventPost(kCGSessionEventTap, event);
        cg.CFRelease(event);
    }
}
