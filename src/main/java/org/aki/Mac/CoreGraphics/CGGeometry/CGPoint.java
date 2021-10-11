package org.aki.Mac.CoreGraphics.CGGeometry;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * A structure that contains a point in a two-dimensional coordinate system.
 */

public class CGPoint extends Structure implements Structure.ByValue {
    public CGFloat x;
    public CGFloat y;

    public CGPoint() {
        this(0, 0);
    }

    public CGPoint(double x, double y) {
        this.x = new CGFloat(x);
        this.y = new CGFloat(y);
    }

    public CGPoint(Point2D point) {
        this(point.getX(), point.getY());
    }

    public Point2D getPoint() {
        return new Point2D.Double(x.doubleValue(), y.doubleValue());
    }

    protected List getFieldOrder() {
        return Arrays.asList("x", "y");
    }
}