package com.myappconverter.java.foundations;

import java.io.Serializable;

public class NSMutableRange extends NSRange implements Serializable {

	private static final long serialVersionUID = -5788186328871692048L;

	public NSMutableRange() {
		super();
	}

	public NSMutableRange(int location, int length) {
		super(location, length);
	}

	public NSMutableRange(NSRange range) {
		super(range);
	}

	@Override
	public NSObject clone() {
		return new NSMutableRange(this);
	}

	public void intersectRange(NSRange otherRange) {
		NSRange newRange = this.rangeByIntersectingRange(otherRange);
		setLength(newRange.length);
		setLocation(newRange.location);
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void unionRange(NSRange otherRange) {
		NSRange newRange = this.rangeByUnioningRange(otherRange);
		setLength(newRange.length);
		setLocation(newRange.location);
	}

}
