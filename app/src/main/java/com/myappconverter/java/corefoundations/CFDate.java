package com.myappconverter.java.corefoundations;

import java.util.Date;

public class CFDate extends CFTypeRef {
    private Date wrappedDate;

    public Date getWrappedDate() {
        return wrappedDate;
    }

    public void setWrappedDate(Date wrappedDate) {
        this.wrappedDate = wrappedDate;
    }
}
