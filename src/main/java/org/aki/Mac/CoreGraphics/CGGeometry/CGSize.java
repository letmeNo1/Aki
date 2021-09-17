package org.aki.Mac.CoreGraphics.CGGeometry;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class CGSize extends Structure implements Structure.ByValue {
    public CGFloat width;
    public CGFloat height;

    public CGSize() {
        this(0, 0);
    }

    public CGSize(double width, double height) {
        this.width = new CGFloat(width);
        this.height = new CGFloat(height);
    }

    public CGSize(Dimension2D pSize) {
        this(pSize.getWidth(), pSize.getHeight());
    }

    public Point2D getPoint() {
        return new Point2D.Double(width.doubleValue(), height.doubleValue());
    }
    @Override
    protected List getFieldOrder() {
        return Arrays.asList("width", "height");
    }
}