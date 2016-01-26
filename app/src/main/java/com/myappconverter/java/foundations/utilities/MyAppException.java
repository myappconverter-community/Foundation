package com.myappconverter.java.foundations.utilities;

public class MyAppException extends Exception {

	public MyAppException() {
	}

	public MyAppException(String detailMessage) {
		super(detailMessage);
	}

	public MyAppException(Throwable throwable) {
		super(throwable);
	}

	public MyAppException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
