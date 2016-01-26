package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.NSURL;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class AndroidRessourcesUtils {
	static int countSubString = 0;
	// return id res from url
	public static int getResIDFromURL(NSURL url, String ressourcetype, Context ctx) {

		NSString lastComp = url.lastPathComponent();
		if (lastComp != null) {
			int resID = ctx.getResources().getIdentifier(lastComp.getWrappedString(), ressourcetype, ctx.getPackageName());

			return resID;
		}
		return 0;

	}

	// return id res from resource name
	public static int getResID(String resName, String resType) {
		int resID = GenericMainContext.sharedContext.getResources().getIdentifier(resName, resType,
				GenericMainContext.sharedContext.getPackageName());
		return resID;
	}

	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Given ResourceID , read resource and return result as String
	 */
	public static String readRawTextFile(Context ctx, int resId) {
		InputStream inputStream = ctx.getResources().openRawResource(resId);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while ((line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			return null;
		}
		return text.toString();
	}

	public static String getFileExtension(String name) {
		try {
			return name.substring(name.lastIndexOf("."));

		} catch (Exception e) {
			return "";
		}

	}

	public static String formatImageResourceName(String str) {

		String extString = getFileExtension(str);
		if (extString.equalsIgnoreCase(".jpg") || extString.equalsIgnoreCase(".jpeg") || extString.equalsIgnoreCase(".png")
				|| extString.equalsIgnoreCase(".gif")) {
			str = str.replace(extString, "");
		}
		if (str == null || (str != null && str.isEmpty())) {
			return str;
		}
		String parsedVal = str.replaceAll("\\s", "_");
		parsedVal = parsedVal.replaceAll("\\.", "_");
		parsedVal = parsedVal.replaceAll(":", "_");
		parsedVal = parsedVal.replaceAll("\\n", "_");
		parsedVal = parsedVal.replaceAll("-", "_");
		if (parsedVal.equals("default")) {
			parsedVal = "ic_" + parsedVal;
		}
		if (parsedVal.contains("@")) {
			parsedVal = parsedVal.replaceAll("@", "");
		}
		if (parsedVal.startsWith("_")) {
			parsedVal = parsedVal.replaceAll("_", "ic_");
		}
		if (parsedVal.startsWith("0")) {
			parsedVal = parsedVal.replaceAll("0", "ic_0");
		} else if (parsedVal.startsWith("1")) {
			parsedVal = parsedVal.replaceAll("1", "ic_1");
		} else if (parsedVal.startsWith("2")) {
			parsedVal = parsedVal.replaceAll("2", "ic_2");
		} else if (parsedVal.startsWith("3")) {
			parsedVal = parsedVal.replaceAll("3", "ic_3");
		} else if (parsedVal.startsWith("4")) {
			parsedVal = parsedVal.replaceAll("4", "ic_4");
		} else if (parsedVal.startsWith("5")) {
			parsedVal = parsedVal.replaceAll("5", "ic_5");
		} else if (parsedVal.startsWith("6")) {
			parsedVal = parsedVal.replaceAll("6", "ic_6");
		} else if (parsedVal.startsWith("7")) {
			parsedVal = parsedVal.replaceAll("7", "ic_7");
		} else if (parsedVal.startsWith("8")) {
			parsedVal = parsedVal.replaceAll("8", "ic_8");
		} else if (parsedVal.startsWith("9")) {
			parsedVal = parsedVal.replaceAll("9", "ic_9");
		} else if (parsedVal.startsWith("(")) {
			parsedVal = parsedVal.replaceAll("(", "ic_");
		} else if (parsedVal.startsWith(")")) {
			parsedVal = parsedVal.replaceAll(")", "ic_");
		} else if (parsedVal.startsWith("(")) {
			parsedVal = parsedVal.replaceAll("(", "ic_");
		} else if (parsedVal.startsWith(")")) {
			parsedVal = parsedVal.replaceAll(")", "ic_");
		}

		if (parsedVal.contains("(")) {
			parsedVal = parsedVal.replaceAll("(", "_");
		}
		if (parsedVal.contains(")")) {
			parsedVal = parsedVal.replaceAll(")", "_");
		}

		return parsedVal;

		/*
		 * int idString = getResID(parsedVal, "drawable"); if (idString == 0) { try { InputStream inputStream =
		 * GenericMainContext.sharedContext.getAssets().open(str); if (inputStream == null) { inputStream =
		 * GenericMainContext.sharedContext.getAssets().open(parsedVal); return "assets "+parsedVal; }else { return "assets "+str; } } catch
		 * (IOException e) { e.printStackTrace(); }
		 * 
		 * }else { return parsedVal; } return str;
		 */

	}

	public static String formatStringResourceName(String str) {

		if (str == null || (str != null && str.isEmpty())) {
			return str;
		}
		str = str.toLowerCase();
		String parsedVal = str.replaceAll("\\s", "_");
		parsedVal = parsedVal.replaceAll("\\.", "_");
		parsedVal = parsedVal.replaceAll(":", "_");
		parsedVal = parsedVal.replaceAll("\\n", "_");
		parsedVal = parsedVal.replaceAll("-", "_");
		if (parsedVal.equals("default")) {
			parsedVal = "r_" + parsedVal;
		}
		if (parsedVal.contains("@")) {
			parsedVal = parsedVal.replaceAll("@", "");
		}
		if (parsedVal.startsWith("_")) {
			parsedVal = parsedVal.replaceAll("_", "r_");
		}
		if (parsedVal.startsWith("0")) {
			parsedVal = parsedVal.replaceAll("0", "r_0");
		} else if (parsedVal.startsWith("1")) {
			parsedVal = parsedVal.replaceAll("1", "r_1");
		} else if (parsedVal.startsWith("2")) {
			parsedVal = parsedVal.replaceAll("2", "r_2");
		} else if (parsedVal.startsWith("3")) {
			parsedVal = parsedVal.replaceAll("3", "r_3");
		} else if (parsedVal.startsWith("4")) {
			parsedVal = parsedVal.replaceAll("4", "r_4");
		} else if (parsedVal.startsWith("5")) {
			parsedVal = parsedVal.replaceAll("5", "r_5");
		} else if (parsedVal.startsWith("6")) {
			parsedVal = parsedVal.replaceAll("6", "r_6");
		} else if (parsedVal.startsWith("7")) {
			parsedVal = parsedVal.replaceAll("7", "r_7");
		} else if (parsedVal.startsWith("8")) {
			parsedVal = parsedVal.replaceAll("8", "r_8");
		} else if (parsedVal.startsWith("9")) {
			parsedVal = parsedVal.replaceAll("9", "r_9");
		} else if (parsedVal.startsWith("(")) {
			parsedVal = parsedVal.replaceAll("(", "r_");
		} else if (parsedVal.startsWith(")")) {
			parsedVal = parsedVal.replaceAll(")", "r_");
		} else if (parsedVal.startsWith("(")) {
			parsedVal = parsedVal.replaceAll("(", "r_");
		} else if (parsedVal.startsWith(")")) {
			parsedVal = parsedVal.replaceAll(")", "r_");
		}
		if (parsedVal.contains("(")) {
			parsedVal = parsedVal.replaceAll("(", "_");
		}
		if (parsedVal.contains(")")) {
			parsedVal = parsedVal.replaceAll(")", "_");
		}

		return parsedVal;

	}

	public static boolean listAssetFiles(String path, List<String> result) {
		if (result == null) {
			result = new ArrayList<String>();
		}

		String[] list;
		try {
			list = GenericMainContext.sharedContext.getAssets().list(path);
			if (list.length > 0) {
				// This is a folder
				for (String file : list) {
					if (!listAssetFiles(path + "/" + file, result))
						return false;
				}
			} else {
				result.add(path);
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the resource-IDs for all attributes specified in the given <declare-styleable>-resource tag as an int array.
	 * 
	 * @param context The current application context.
	 * @param name The name of the <declare-styleable>-resource-tag to pick.
	 * @return All resource-IDs of the child-attributes for the given <declare-styleable>-resource or <code>null</code> if this tag could
	 *         not be found or an error occured.
	 */
	public static final int[] getResourceDeclareStyleableIntArray(Context context, String name) {
		try {
			// use reflection to access the resource class
			Field[] fields2 = Class.forName(context.getPackageName() + ".R$styleable").getFields();

			// browse all fields
			for (Field f : fields2) {
				// pick matching field
				if (f.getName().equals(name)) {
					// return as int array
					int[] ret = (int[]) f.get(null);
					return ret;
				}
			}
		} catch (Throwable t) {
		}

		return null;
	}
	
	public static ByteBuffer getByteBufferFixed(int top, int left, int bottom, int right) {
	    //Docs check the NinePatchChunkFile
	    ByteBuffer buffer = ByteBuffer.allocate(84).order(ByteOrder.nativeOrder());
	    //was translated
	    buffer.put((byte)0x01);
	    //divx size
	    buffer.put((byte)0x02);
	    //divy size
	    buffer.put((byte)0x02);
	    //color size
	    buffer.put(( byte)0x09);

	    //skip
	    buffer.putInt(0);
	    buffer.putInt(0);

	    //padding
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);

	    //skip 4 bytes
	    buffer.putInt(0);

	    buffer.putInt(left);
	    buffer.putInt(right);
	    buffer.putInt(top);
	    buffer.putInt(bottom);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    buffer.putInt(0);
	    return buffer;
	}



	public static int getCountSubString(String string) {
	

		if (string.indexOf(":") != -1) {
			String tmp = string.substring(string.indexOf(":") + 1);
			countSubString++;
			getCountSubString(tmp);

		}
		return countSubString;
	}

	public static String getQualifiedName(String string) {
		StringBuilder stringBuilder = new StringBuilder(string);
		if (getCountSubString(string) == 1) {
			return string;
		} else {
			if (string.indexOf(":") != -1) {
				String tmpName = string.substring(0, string.indexOf(":") - 1);
				stringBuilder.append(tmpName);
				String tmp = string.substring(string.indexOf(":") + 1);
				getQualifiedName(tmp);

			}
			return stringBuilder.toString();
		}

	}

	public static int deviceWidth(){
		DisplayMetrics displayMetrics = GenericMainContext.sharedContext.getResources().getDisplayMetrics();
		return displayMetrics.widthPixels;
	}

	public static int deviceHeight(){
		DisplayMetrics displayMetrics = GenericMainContext.sharedContext.getResources().getDisplayMetrics();
		return displayMetrics.heightPixels;
	}

}
