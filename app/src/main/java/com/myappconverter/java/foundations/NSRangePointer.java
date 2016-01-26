package com.myappconverter.java.foundations;

public class NSRangePointer extends NSRange {

	private static final long serialVersionUID = 5695645698146346977L;
	private int start;
	private int end;

	public NSRangePointer(int start, int end) {
		super(start, end);
		this.start = end;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
