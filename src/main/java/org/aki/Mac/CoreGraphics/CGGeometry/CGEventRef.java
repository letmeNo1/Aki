package org.aki.Mac.CoreGraphics.CGGeometry;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.mac.CoreFoundation;

@Structure.FieldOrder({"CFHashCode", "CFTypeID", "CFTypeRef"})
public class CGEventRef extends CoreFoundation.CFTypeRef {
    public NativeLong CFHashCode;
    public NativeLong CFTypeID;
    public Pointer CFTypeRef;
}
