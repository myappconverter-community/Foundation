package com.myappconverter.java.coregraphics;

import android.graphics.Rect;

public class CGRect {

    public CGPoint origin;

    public CGPoint getOrigin() {
        return origin;
    }

    public CGPoint origin() {
        return origin;
    }

    public void setOrigin(CGPoint pOrigin) {
        origin = pOrigin;
    }

    public CGSize getSize() {
        return size;
    }

    public CGSize size() {
        return size;
    }

    public void setSize(CGSize pSize) {
        size = pSize;
    }

    public CGSize size;

    public CGRect() {
        origin = new CGPoint();
        size = new CGSize();
    }

    public CGRect(float x, float y, float width, float height) {
        origin = new CGPoint(x, y);
        size = new CGSize(width, height);
    }

    public CGRect(CGRect other) {
        origin = new CGPoint(other.origin);
        size = new CGSize(other.size);
        origin.x = other.origin.x;
        origin.y = other.origin.y;
        size.width = other.size.width;
        size.height = other.size.height;
    }

    public static CGRect CGRectMake(float x, float y, float width, float height) {
        CGRect res = new CGRect();
        res.origin = new CGPoint();
        res.size = new CGSize();
        res.origin.x = x;
        res.origin.y = y;
        res.size.height = height;
        res.size.width = width;
        return res;
    }

    public boolean contains(float x, float y) {
        return size.width > 0 && size.height > 0 // check for empty first
                && x >= origin.x && x < (origin.x + size.width) && y >= origin.y && y < (origin.y + size.height);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CGRect) {

            if (((CGRect) o).origin.equals(origin) && ((CGRect) o).size.equals(size)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return "((" + origin.x + ", " + origin.y + "),(" + size.width + ", " + size.height + "))";
    }

    /**
     * Returns true if aPoint is inside aRect.
     */
    public static boolean containsPoint(final CGRect aRect, final CGPoint aPoint) {
        return ((aPoint.x >= minX(aRect)) && (aPoint.y >= minY(aRect)) && (aPoint.x < maxX(aRect)) && (aPoint.y < maxY(aRect)));
    }

    public static boolean containsRect(final CGRect aRect, final CGRect bRect) {
        return (!isEmptyRect(bRect) && (minX(aRect) <= minX(bRect)) && (minY(aRect) <= minY(bRect)) && (maxX(aRect) >= maxX(bRect)) && (maxY(aRect) >= maxY(bRect)));
    }

    public static boolean intersects(CGRect a, CGRect b) {
        return (a.origin.x >= (b.origin.x - a.size.width) && a.origin.x <= (b.origin.x - a.size.width) + (b.size.width + a.size.width)
                && a.origin.y >= (b.origin.y - a.size.height) && a.origin.y <= (b.origin.y - a.size.height)
                + (b.size.height + a.size.height));
    }

    /**
     * Returns the greatest x-coordinate value still inside aRect.
     */
    public static double maxX(final CGRect aRect) {
        return aRect.origin.x + aRect.size.width;
    }

    /**
     * Returns the greatest y-coordinate value still inside aRect.
     */
    public static double maxY(final CGRect aRect) {
        return aRect.origin.y + aRect.size.height;
    }

    /**
     * Returns the x-coordinate of aRect's middle point.
     */
    public static double midX(CGRect aRect) {
        return aRect.origin.x + (float) (aRect.size.width / 2.0);
    }

    /**
     * Returns the y-coordinate of aRect's middle point.
     */
    public static double midY(final CGRect aRect) {
        return aRect.origin.y + (float) (aRect.size.height / 2.0);
    }

    /**
     * Returns the least x-coordinate value still inside aRect.
     */
    public static double minX(final CGRect aRect) {
        return aRect.origin.x;
    }

    /**
     * Returns the least y-coordinate value still inside aRect.
     */
    public static double minY(final CGRect aRect) {
        return aRect.origin.y;
    }

    /**
     * Returns aRect's width.
     */
    public static double width(final CGRect aRect) {
        return aRect.size.width;
    }

    /**
     * Returns aRect's height.
     */
    public static double height(final CGRect aRect) {
        return aRect.size.height;
    }

    public static boolean isEmptyRect(CGRect aRect) {
        return (!(aRect.size.width > 0 && aRect.size.height > 0));
    }

    /**
     * Convert a {@link Rect} to a {@link CGRect}
     *
     * @param pRect
     * @return
     */
    public static CGRect convertRectToCGRect(Rect pRect) {
        CGRect pCGRect = new CGRect();
        pCGRect.origin = new CGPoint(pRect.left, pRect.bottom);// the origin is located in the
        // lower-left corner of the
        // rectangle
        pCGRect.size = new CGSize();
        pCGRect.size.width = pRect.width();
        pCGRect.size.height = pRect.height();
        return pCGRect;
    }

    /**
     * The opposite conversion from a {@link CGRect} to a {@link Rect}
     *
     * @param pCGRect
     * @return a {@link Rect}
     */
    public static Rect convertCGRectToRect(CGRect pCGRect) {
        Rect pRect = new Rect((int) pCGRect.origin.x, (int) (pCGRect.origin.y + pCGRect.size.width),
                (int) (pCGRect.origin.x + pCGRect.size.height), (int) pCGRect.origin.y);

        return pRect;
    }
}
