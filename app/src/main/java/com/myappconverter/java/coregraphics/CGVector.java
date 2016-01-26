package com.myappconverter.java.coregraphics;

public class CGVector {
    public float dx;
    public float dy;

    public CGVector() {
        super();
        this.dx = 0;
        this.dy = 0;
    }

    public CGVector(float dx, float dy) {
        super();
        this.dx = dx;
        this.dy = dy;
    }

    public static CGVector CGVectorMake(float dx, float dy){
        return new CGVector(dx, dy);
    }
}
