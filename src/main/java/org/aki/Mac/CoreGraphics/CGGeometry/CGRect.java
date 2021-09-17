package org.aki.Mac.CoreGraphics.CGGeometry;

import com.sun.jna.Structure;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class CGRect extends Structure implements Structure.ByValue {
    public CGPoint origin;
    public CGSize size;

    public CGRect() {
        this.origin = new CGPoint();
        this.size = new CGSize();
    }

    public CGRect(CGPoint origin, CGSize size) {
        this.origin = origin;
        this.size = size;
    }

    public CGRect(Point2D origin, Dimension2D size) {
        this.origin = new CGPoint(origin);
        this.size = new CGSize(size);
    }

    public CGRect(Rectangle2D rect) {
        this.origin = new CGPoint(rect.getX(), rect.getY());
        this.size = new CGSize(rect.getWidth(), rect.getHeight());
    }

    public CGRect(double w, double h) {
        this(0, 0, w, h);
    }

    public CGRect(double x, double y, double w, double h) {
        this.origin = new CGPoint(x, y);
        this.size = new CGSize(w, h);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(origin.x.doubleValue(), origin.y.doubleValue(), size.width.doubleValue(), size.height.doubleValue());
    }

    protected List getFieldOrder() {
        return Arrays.asList("origin", "size");
    }

}