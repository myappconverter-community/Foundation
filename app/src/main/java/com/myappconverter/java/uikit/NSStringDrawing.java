package com.myappconverter.java.uikit;

public interface NSStringDrawing {

    public static enum NSStringDrawingOptions {
        NSStringDrawingTruncatesLastVisibleLine(1 << 5), //
        NSStringDrawingUsesLineFragmentOrigin(1 << 0), //
        NSStringDrawingUsesFontLeading(1 << 1), //
        NSStringDrawingUsesDeviceMetrics(1 << 3);
        private int value;

        private NSStringDrawingOptions(int v) {
            value = v;
        }

        public int getValue() {
            return value;
        }
    };

}
