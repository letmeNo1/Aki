package org.aki.Mac.CoreGraphics.CGGeometry;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

/**
A bitmap image or image mask.
 */
public class CGImageRef extends PointerType {
    public CGImageRef(Pointer pointer) {
        super(pointer);
    }
    public CGImageRef() {
        super();
    }
}