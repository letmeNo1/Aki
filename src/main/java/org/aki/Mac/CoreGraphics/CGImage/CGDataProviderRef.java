package org.aki.Mac.CoreGraphics.CGImage;

import com.sun.jna.ptr.ByReference;
import com.sun.jna.Native;

public class CGDataProviderRef extends ByReference {
        public CGDataProviderRef() {
            super(Native.POINTER_SIZE);
        }
    }
