package org.aki.Mac;

import com.sun.jna.Memory;
import com.sun.jna.platform.mac.CoreFoundation;
import com.sun.jna.platform.mac.CoreFoundation.*;
import com.sun.jna.ptr.PointerByReference;
import org.aki.Mac.Appkit.Cocoa.ApplicationServices;
import org.aki.Mac.CoreGraphics.CGGeometry.CGPoint;
import org.aki.Mac.CoreGraphics.CGGeometry.CGSize;

import java.util.*;


/**
 * TODO  kAXErrorAttributeUnsupported: ErrorUnsupported, # -25205
 *         kAXErrorActionUnsupported: ErrorUnsupported, # -25206
 *         kAXErrorNotificationUnsupported: ErrorUnsupported, # -25207
 *         kAXErrorAPIDisabled: ErrorAPIDisabled, # -25211
 *         kAXErrorInvalidUIElement: ErrorInvalidUIElement, # -25202
 *         kAXErrorCannotComplete: ErrorCannotComplete, # -25204
 *         kAXErrorNotImplemented: ErrorNotImplemented, # -25208
 *         kAXErrorNoValue：ErrorNoValue, # -25212
 *
 To avoid memory leaks, an operation will be done every time the API is called
 */

public class CallAppServices {

    /**
     * get elementRef by pid
     */
    public static UIElementRef getElementRefByPid(int pid){
        return ApplicationServices.INSTANCE.AXUIElementCreateApplication(pid);
    }
    
    /**
     * Convert String to CFString
     */
    public CFStringRef createCFString(String value){
        return CFStringRef.createCFString(value);
    }

    /**
     * Convert CFString to String
     */
    public String getValueOfCFStringRef(PointerByReference value){
        CFStringRef cfStringRef = new CFStringRef();
        cfStringRef.setPointer(value.getValue());
        return cfStringRef.stringValue();
    }

    /**
     * If the string consists entirely of 3-byte UTF characters, stringValue fails, so this method can only be overridden
     * add 1 to the calculated max number of bytes
     */
    public static String getStringValue(CFStringRef cfObject){
        CoreFoundation.CFIndex length = CoreFoundation.INSTANCE.CFStringGetLength(cfObject);
        if (length.longValue() == 0L) {
            return "";
        }
        CoreFoundation.CFIndex maxSize = CoreFoundation.INSTANCE.CFStringGetMaximumSizeForEncoding(length, 134217984);
        if (maxSize.intValue() == -1) {
            throw new StringIndexOutOfBoundsException("CFString maximum number of bytes exceeds LONG_MAX.");
        }
        maxSize = new CFIndex(maxSize.intValue() + 1);
        Memory buf = new Memory(maxSize.longValue());
        if (0 != CoreFoundation.INSTANCE.CFStringGetCString(cfObject, buf, maxSize, 134217984)) {
            return buf.getString(0L, "UTF8");
        }
        throw new IllegalArgumentException("CFString conversion fails or the provided buffer is too small.");
    }

    /**
     * Get the specified attribute of the String type from an accessibility object's attribute.
     */
    public static String getCopyAttributeValueOfStringType(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                CFStringRef cfObject = new CFStringRef();
                cfObject.setPointer(value.getValue());
                try {
                    if(cfObject.getTypeID().toString().equals("CFNumber")){
                        //If the return value is of type CFNumber, it needs to be converted to CFString
                        return String.valueOf(new CFNumberRef(cfObject.getPointer()).intValue());
                    }else if(cfObject.getTypeID().toString().equals("CFString")){
                        return cfObject.stringValue();
                    }else{
                        //If the return value is of type CFNumber, it needs to be converted to CFString
                        return cfObject.toString();
                    }
                }catch (IllegalArgumentException e){
                    //Call the overridden method if it prompts "CFString conversion failed or the buffer provided is too small."
                    return getStringValue(cfObject);
                }
            } else {
                return "null";
            }
        }finally {
            attribute.release();
        }
    }

    /**
     * Get the specified attribute of the Boolean type from an accessibility object's attribute.
     */
    public static boolean getCopyAttributeValueOfBooleanType(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                CFBooleanRef cfObject = new CFBooleanRef();
                cfObject.setPointer(value.getValue());
                return cfObject.booleanValue();
            } else {
                return false;
            }
        }finally {
            attribute.release();
        }
    }

    /**
     * Get the specified attribute of the GCPoint type from an accessibility object's attribute.
     */
    public static CGPoint getCopyAttributeValueOfCGPoint(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                CGPoint position = new CGPoint();
                AXValueRef axValue = new AXValueRef();
                axValue.setPointer(value.getValue());
                ApplicationServices.INSTANCE.AXValueGetValue(axValue,1, position.getPointer());
                position.read();
                return position;
            } else {
                return null;
            }
        }finally {
            attribute.release();
        }
    }


    /**
     * Get the specified attribute of the CGSize type from an accessibility object's attribute.
     */
    public static CGSize getCopyAttributeValueOfCGSize(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                CGSize size = new CGSize();
                AXValueRef axValue = new AXValueRef();
                axValue.setPointer(value.getValue());
                ApplicationServices.INSTANCE.AXValueGetValue(axValue,2, size.getPointer());
                size.read();
                return size;
            } else {
                return null;
            }
        }finally {
            attribute.release();
//            element.release();
        }
    }

    /**
     * Get all attribute names from an accessibility object.
     */
    public static List<String> getAXUIElementCopyAttributeNames(UIElementRef element, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeNames(element,value);
        try{
            if (error == 0) {
                CFArrayRef cfObject = new CFArrayRef();
                cfObject.setPointer(value.getValue());
                List<String> axUIElementRefs = new ArrayList<>();
                for(int i= 0; i<cfObject.getCount();i++){
                    axUIElementRefs.add(new CFStringRef(cfObject.getValueAtIndex(i)).stringValue());
                }
                return axUIElementRefs;
            } else {
                return Collections.emptyList();
            }
        }finally {
        }
    }

    /**
     * Get the specified attribute of the List<AXUIElementRef> from an accessibility object's attribute.
     */
    public static List<UIElementRef> getCopyAttributeValueOfAXUIElementRefList(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                CFArrayRef cfObject = new CFArrayRef();
                cfObject.setPointer(value.getValue());
                List<UIElementRef> axUIElementRefs = new ArrayList<>();
                for(int i= 0; i<cfObject.getCount();i++){
                    axUIElementRefs.add(new UIElementRef(cfObject.getValueAtIndex(i)));
                }
                return axUIElementRefs;
            } else {
                return Collections.emptyList();
            }
        }finally {
            attribute.release();
        }
    }

    /**
     * Get the specified attribute of the AXUIElementRef from an accessibility object's attribute.
     */
    public static UIElementRef getCopyAttributeValueOfAXUIElementRef(UIElementRef element, CFStringRef attribute, PointerByReference value){
        final int error = ApplicationServices.INSTANCE.AXUIElementCopyAttributeValue(element,attribute,value);
        try{
            if (error == 0) {
                UIElementRef cfObject = new UIElementRef();
                cfObject.setPointer(value.getValue());
                return cfObject;
            }else {
                return null;
            }

        }finally {
            attribute.release();
//            element.release();
        }
    }

    /**
     * Execute the specified auxiliary function instance to perform the specified operation
     */
    public static void axUIElementPerformAction(UIElementRef element, CFStringRef action){
        try{
            ApplicationServices.INSTANCE.AXUIElementPerformAction(element,action);
        }finally {
            action.release();
            element.release();
        }
    }


}
