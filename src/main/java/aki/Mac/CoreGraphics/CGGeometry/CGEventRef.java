package aki.Mac.CoreGraphics.CGGeometry;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.mac.CoreFoundation;

/**
 * Defines an opaque type that represents a low-level hardware event.
 *
 */
@Structure.FieldOrder({"CFHashCode", "CFTypeID", "CFTypeRef"})
public class CGEventRef extends CoreFoundation.CFTypeRef {
    public NativeLong CFHashCode;
    public NativeLong CFTypeID;
    public Pointer CFTypeRef;
}
