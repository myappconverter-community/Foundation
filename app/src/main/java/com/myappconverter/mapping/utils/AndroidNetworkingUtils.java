package com.myappconverter.mapping.utils;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AndroidNetworkingUtils {

	private static final String TAG = null;

	public static byte[] getContentsFromUrl(final URL url) throws IOException {
		byte[] data = new byte[1024 * 1024];
		// Do Job in AsyncTask
		AsyncTask<Void, Void, Void> mAsynch = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				Log.d(TAG, "connecting to url " + url.toString());
				HttpURLConnection urlConnection = null;
				try {
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setConnectTimeout(5000);
					InputStream in = new BufferedInputStream(urlConnection.getInputStream());
					byte[] data = AndroidIOUtils.toByteArray(in);
					Log.d(TAG, "loaded data size : " + data.length);

				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				} finally {
					if (urlConnection != null)
						urlConnection.disconnect();
				}
				return null;
			}
		};
		mAsynch.execute();
		return data;
	}

	public static String readStream(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e(TAG, "IOException ", e);
			}
		}
		return sb.toString();
	}
}
