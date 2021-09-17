package org.aki.Mac.Appkit.Cocoa;

import com.sun.jna.*;

import com.sun.jna.platform.mac.CoreFoundation.*;
import com.sun.jna.ptr.PointerByReference;
import org.aki.Mac.UIElementRef;
import org.aki.Mac.AXValueRef;


public interface ApplicationServices extends Library {
    ApplicationServices INSTANCE = Native.load("ApplicationServices", ApplicationServices.class);

    /**
     * Create a ElementRef by pid
     *
     * @param pid process identifier of application
     */
    UIElementRef AXUIElementCreateApplication(int pid);

    int AXUIElementGetPid(UIElementRef element, int value);

    /**
     * get attribute of element
     * e.g. Title,Role,Position...
     *
     * @param element The AXUIElementRef representing the accessibility object.
     * @param attribute e.g. The attribute name. AXRole, AXRole, AXChildren,
     * You can find it here: https://developer.apple.com/documentation/applicationservices/axattributeconstants_h/miscellaneous_defines
     * @param value On return, the value associated with the specified attribute. The corresponding value in C++ is *value
     */
    int AXUIElementCopyAttributeValue(UIElementRef element, CFStringRef attribute, PointerByReference value);

    int AXUIElementCopyAttributeNames(UIElementRef element, PointerByReference value);
    /**
     * Decodes the structure stored in value and copies it into valuePtr. If the structure stored in value
     * is not the same as requested by theType, the function returns false.
     *
     * @param value
     * @param type e.g. The attribute name. AXRole, AXRole, AXChildren,
     * You can find it here: https://developer.apple.com/documentation/applicationservices/axattributeconstants_h/miscellaneous_defines
     * @param callback On return, the value associated with the specified attribute. The corresponding value in C++ is *value
     */
    boolean AXValueGetValue(AXValueRef value, long type, Pointer callback);

    void AXUIElementPerformAction(UIElementRef element, CFStringRef action);

    int AXObserverCreate(int pid);

}
