package com.myappconverter.java.coregraphics;

public class CGSize {

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    // A structure that contains width and height values.
    public float width;
    public float height;

    public CGSize() {
        this(0, 0);
    }

    public CGSize(float w, float h) {
        width = w;
        height = h;
    }

    public static CGSize make(float w, float h) {
        return new CGSize(w, h);
    }

    public static CGSize zero() {
        return new CGSize(0, 0);
    }

    public void set(CGSize s) {
        width = s.width;
        height = s.height;
    }

    public void set(float w, float h) {
        width = w;
        height = h;
    }

    private static CGSize ZERO_SIZE = CGSize.zero();

    public static CGSize getZero() {
        return ZERO_SIZE;
    }

    public float getWidth() {
        return width;
    }

    public float height() {
        return height;
    }

    public float width() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public CGSize(CGSize size) {
        width = size.width;
        height = size.height;
    }

    @Override
    public String toString() {
        return "[" + width + "," + height + "]";
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
        result = prime * result + Float.floatToIntBits(height);
        result = prime * result + Float.floatToIntBits(width);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CGSize))
            return false;

        CGSize size = (CGSize) obj;
        return size.width == this.width && size.height == this.height;
    }

}
