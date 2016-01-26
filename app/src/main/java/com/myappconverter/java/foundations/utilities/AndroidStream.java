package com.myappconverter.java.foundations.utilities;

import java.io.IOException;
import java.io.OutputStream;

public class AndroidStream extends OutputStream {

	public static OutputStream nullOutputStream() {
		return null;
	}

	@Override
	public void write(int oneByte) throws IOException {
		// DO NOTHING
	}

}
