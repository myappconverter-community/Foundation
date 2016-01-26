package com.myappconverter.java.corefoundations.utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidIOUtils {

	public static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[2 * 2065];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public static void write(byte[] primitive, OutputStream wrappedOutputStream) throws IOException {
	}

	public static void copy(InputStream inStream, OutputStream outStream) throws IOException {
	}

	public static String toString(InputStream is) {
		return null;
	}

	public static void closeQuietly(InputStream is) throws IOException {
	}

	public static void read(InputStream wrappedInputStream, byte[] wrappedData) throws IOException {
	}

	public static void write(String wrappedString, FileOutputStream fileos, String encoding) throws IOException {
	}

}
