package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSFileManager;
import com.myappconverter.java.foundations.NSObject;
import com.myappconverter.java.foundations.NSString;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class AndroidFileUtils {

	private final static String basicAttrib = "basic:";
	private final static String lastModifiedTime = "lastModifiedTime";
	private final static String lastAccessTime = "lastAccessTime";
	private final static String creationTime = "creationTime";
	private final static String size = "size";
	private final static String isRegularFile = "isRegularFile";
	private final static String isDirectory = "isDirectory";
	private final static String isSymbolicLink = "isSymbolicLink";
	private final static String isOther = "isOther";
	private final static String fileKey = "fileKey";
	private static Context context;
	private static ArrayList<String> listAssets = new ArrayList<String>();

	public AndroidFileUtils(Context ctx) {
		context = ctx;
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	public static boolean isLink(File file) {
		try {
			if (!file.exists())
				return true;
			else {
				String cnnpath = file.getCanonicalPath();
				String abspath = file.getAbsolutePath();
				return !abspath.equals(cnnpath);
			}
		} catch (IOException ex) {
			System.err.println(ex);
			return true;
		}
	}

	public static NSDictionary<NSString, NSObject> getNSDictionaryFromFileOrDirAttributes(NSString path) {
		NSDictionary<NSString, NSObject> nsDictionary = new NSDictionary<NSString, NSObject>();
		File file = new File(path.getWrappedString());
		// NSFileType
		// nsDictionary.getDictionary().put(NSFileManager.NSFileType, new NSString(file));
		// NSFileSize
		nsDictionary.getWrappedDictionary().put(NSFileManager.NSFileSize, new NSString(String.valueOf(file.length())));
		// NSFileModificationDate
		nsDictionary.getWrappedDictionary().put(NSFileManager.NSFileModificationDate, new NSString(String.valueOf(file.lastModified())));
		// NSFileReferenceCount
		// NSFileDeviceIdentifier
		nsDictionary.getWrappedDictionary().put(NSFileManager.NSFileDeviceIdentifier,
				new NSString(Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)));
		// NSFileOwnerAccountName
		// NSFileGroupOwnerAccountName
		// NSFilePosixPermissions
		// NSFileSystemNumber
		// NSFileSystemFileNumber
		// NSFileExtensionHidden
		nsDictionary.getWrappedDictionary().put(NSFileManager.NSFileSize, new NSString(String.valueOf(file.isHidden())));
		// NSFileHFSCreatorCode
		// NSFileHFSTypeCode
		// NSFileImmutable
		// NSFileAppendOnly
		// NSFileCreationDate
		nsDictionary.getWrappedDictionary().put(NSFileManager.NSFileCreationDate,
				new NSString(String.valueOf(filecreateTime(file.getAbsolutePath()))));
		// NSFileOwnerAccountID
		// NSFileGroupOwnerAccountID
		// NSFileBusy
		return nsDictionary;
	}

	public static boolean setFileOrDirAttributes(NSDictionary<NSString, NSObject> nsDictionary, NSString path) {

		File file = new File(path.getWrappedString());

		try {
			// NSFileCreationDate
			// NSFileType
			// NSFileSize
			// NSFileModificationDate
			NSString fileDateAttrib = (NSString) nsDictionary.getWrappedDictionary().get(NSFileManager.NSFileModificationDate);
			file.setLastModified(Long.valueOf(fileDateAttrib.getWrappedString()));

			// NSFileReferenceCount
			// NSFileDeviceIdentifier
			// NSFileOwnerAccountName
			// NSFileGroupOwnerAccountName
			// NSFilePosixPermissions
			// NSFileSystemNumber
			// NSFileSystemFileNumber
			// NSFileExtensionHidden

			// NSFileHFSCreatorCode
			// NSFileHFSTypeCode
			// NSFileImmutable
			// NSFileAppendOnly
			// NSFileCreationDate

			// NSFileOwnerAccountID
			// NSFileGroupOwnerAccountID
			// NSFileBusy
			return true;
		} catch (Exception e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
	}

	public static String filecreateTime(String path) {

		try {
			Process p = Runtime.getRuntime().exec("stat " + path);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
			}
			return line;
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;

	}

	public static ArrayList<String> getResourceAtlas(String shortName) {

		String nameWithExt = shortName;
		// check extension .atlas
		if (!nameWithExt.contains(".atlas"))
			nameWithExt = nameWithExt + ".atlas";

		// check if .atlas exist
		if (!listAssets.contains(nameWithExt)) {
			return null;
		}

		// load atlas subFolder
		AssetManager am = GenericMainContext.sharedContext.getAssets();
		String fileList[];
		try {
			fileList = am.list(nameWithExt);
			ArrayList<String> atlasImages = new ArrayList<String>();
			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					if (!atlasImages.contains(fileList[i].replaceAll("@2x", ""))) {
						atlasImages.add(fileList[i].replaceAll("@2x", ""));
					}
				}
				return atlasImages;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getResourceNameWithExt(String shortName) {

		String nameWithExt = shortName;
		try {

			if (nameWithExt.contains(".")){
				
				nameWithExt = AndroidRessourcesUtils.formatImageResourceName(nameWithExt.substring(0, nameWithExt.indexOf('.')))
						+ nameWithExt.substring(nameWithExt.indexOf('.'));
				return nameWithExt;
			}
			for (String fullName : listAssets) {

				if (fullName.contains("."))

					if (shortName.equalsIgnoreCase(fullName.substring(0, fullName.indexOf('.')))) {
						//nameWithExt = fullName.toLowerCase();
						nameWithExt = fullName;
						break;
					}

			}

		} catch (Exception e) {
			Log.d("FileUtils", "error loading resource " + e.toString());
		}

		return nameWithExt;
	}

	public static ArrayList<String> getListAssets() {
		return listAssets;
	}

	public static boolean loadAssets() {
		try {
			String[] list = GenericMainContext.sharedContext.getAssets().list("");
			Collections.addAll(listAssets, list);
		} catch (Exception e) {
			Log.e("AndroidFileUtils", "exception loading assets " + e.toString());
			return false;
		}

		return true;
	}

	public static String readFileToString(File file, String encoding) throws FileNotFoundException, IOException {
		return null;
	}

	public static Iterator<File> iterateFilesAndDirs(File f, Object object, Object object2) {
		return null;
	}

	public static Collection<File> listFiles(File directory) {
		return null;
	}

	public static void deleteDirectory(File file) throws IOException {
	}

	public static void copyDirectory(File fileSrc, File fileDest) throws IOException {
	}

	public static void copyFile(File sourceItem, File destItem) throws IOException {
	}

	public static void moveDirectory(File sourceItem, File destItem) throws IOException {
	}

	public static void moveFile(File sourceItem, File destItem) throws IOException {
	}

	public static boolean contentEquals(File file1, File file2) throws IOException {
		return false;
	}

	public static boolean isSymlink(File file) throws IOException {
		return false;
	}
}
