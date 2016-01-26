package com.myappconverter.java.corefoundations;

public class CFStringRef extends CFTypeRef {

    String wrappedString = null;
    private static long kCFNotFound = 9999;

    public CFStringRef(String aString) {
        this.wrappedString = aString;
    }

    public CFStringRef(long maxLength) {
        StringBuffer strBuffer = new StringBuffer((int) maxLength);
        this.wrappedString = new String(strBuffer.toString());
    }

    public CFStringRef() {
    }

    public String getWrappedString() {
        return wrappedString;
    }

    public void setWrappedString(String wrappedString) {
        this.wrappedString = wrappedString;
    }

    final static long kCFStringEncodingInvalidId = 0xffffffff;

}
