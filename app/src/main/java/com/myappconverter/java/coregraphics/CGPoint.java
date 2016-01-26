package com.myappconverter.java.coregraphics;

import java.util.Comparator;

public class CGPoint implements Comparable<CGPoint> {
    public static CGPoint zero() {
        return new CGPoint(0, 0);
    }

    public float getX() {
        return x;
    }

    public float x() {
        return getX();
    }

    public void x(float pX) {
        setX(pX);
    }

    public void setX(float pX) {
        x = pX;
    }

    public float getY() {
        return y;
    }

    public float y() {
        return getY();
    }

    public void setY(float pY) {
        y = pY;
    }

    public void y(float pY) {
        setY(pY);
    }

    public float x;
    public float y;

    public CGPoint() {
        x = y = 0;
    }

    public CGPoint(CGPoint point) {
        x = point.x;
        y = point.y;
    }

    public CGPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static CGPoint make(float x, float y) {
        return new CGPoint(x, y);
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
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CGPoint) {
            if (((CGPoint) o).x == x && ((CGPoint) o).y == y) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    @Override
    public int compareTo(CGPoint another) {

        return 0;
    }

    public static class Comparators {

        public static Comparator<CGPoint> MINX = new Comparator<CGPoint>() {
            @Override
            public int compare(CGPoint o1, CGPoint o2) {
                return o1.getX() < o2.getX() ? -1 : o1.getX() > o2.getX() ? 1 : 0;
            }
        };
        public static Comparator<CGPoint> MINY = new Comparator<CGPoint>() {
            @Override
            public int compare(CGPoint o1, CGPoint o2) {
                return o1.getY() < o2.getY() ? -1 : o1.getY() > o2.getY() ? 1 : 0;
            }
        };
        public static Comparator<CGPoint> MAXX = new Comparator<CGPoint>() {
            @Override
            public int compare(CGPoint o1, CGPoint o2) {
                return -(o1.getX() < o2.getX() ? -1 : o1.getX() > o2.getX() ? 1 : 0);
            }
        };

        public static Comparator<CGPoint> MAXY = new Comparator<CGPoint>() {
            @Override
            public int compare(CGPoint o1, CGPoint o2) {
                return -(o1.getY() < o2.getY() ? -1 : o1.getY() > o2.getY() ? 1 : 0);
            }
        };
    }

    public float[] toFloatArray() {
        float[] data = new float[2];
        data[0] = this.x;
        data[1] = this.y;
        return data;
    }

    public static int sizeOf() {
        return 2 * 4;
    }
}
