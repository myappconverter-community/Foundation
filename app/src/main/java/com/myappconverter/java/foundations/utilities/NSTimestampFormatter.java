package com.myappconverter.java.foundations.utilities;

import java.text.SimpleDateFormat;

public class NSTimestampFormatter extends SimpleDateFormat {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6015893485374653163L;

	public NSTimestampFormatter() {
		super("z' 'yyyy'-'MM'-'dd' 'HH':'mm':'ss");
	}

	public NSTimestampFormatter(String aPattern) {
		super(aPattern);
	}
}
