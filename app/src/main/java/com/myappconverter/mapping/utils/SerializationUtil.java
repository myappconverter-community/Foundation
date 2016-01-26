package com.myappconverter.mapping.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializationUtil {

	public static boolean storeObject(Object object, String fileName) {
		ObjectOutputStream objOut = null;
		OutputStream out = null;
		boolean saveOk = false;
		try {

			out = GenericMainContext.sharedContext.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			objOut = new ObjectOutputStream(out);
			objOut.writeObject(object);
			saveOk = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOut != null)
					objOut.close();
				if (out != null)
					out.close();
				return saveOk;
			} catch (IOException e) {
				e.printStackTrace();
				return saveOk;
			}
		}
	}

	public static Object retrieveObject(String fileName) {
		InputStream inStream = null;
		ObjectInputStream objIn = null;
		Object object = null;
		try {

			inStream = GenericMainContext.sharedContext
					.openFileInput(fileName);
			objIn = new ObjectInputStream(inStream);
			object = objIn.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIn != null)
					objIn.close();
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

}
