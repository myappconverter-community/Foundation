package com.myappconverter.java.foundations.functions;

import java.io.File;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.mapping.utils.GenericMainContext;

public class NSPathUtilities {

	// NSSearchPathDirectory

	public static enum NSSearchPathDirectory {

		NSApplicationDirectory(1), //
		NSDemoApplicationDirectory(2), //
		NSDeveloperApplicationDirectory(3), //
		NSAdminApplicationDirectory(4), //
		NSLibraryDirectory(5), //
		NSDeveloperDirectory(6), //
		NSUserDirectory(7), //
		NSDocumentationDirectory(8), //
		NSDocumentDirectory(9), //
		NSCoreServiceDirectory(10), //
		NSAutosavedInformationDirectory(11), //
		NSDesktopDirectory(12), //
		NSCachesDirectory(13), //
		NSApplicationSupportDirectory(14), //
		NSDownloadsDirectory(15), //
		NSInputMethodsDirectory(16), //
		NSMoviesDirectory(17), //
		NSMusicDirectory(18), //
		NSPicturesDirectory(19), //
		NSPrinterDescriptionDirectory(20), //
		NSSharedPublicDirectory(21), //
		NSPreferencePanesDirectory(22), //
		NSApplicationScriptsDirectory(23), //
		NSItemReplacementDirectory(99), //
		NSAllApplicationsDirectory(100), //
		NSAllLibrariesDirectory(101), //
		NSTrashDirectory(102);//

		int value;

		private NSSearchPathDirectory(int v) {
			value = v;
		}

		public int getValue() {
			return value;
		}

	}

	// NSSearchPathDomainMask

	public static enum NSSearchPathDomainMask {
		NSUserDomainMask(1), //
		NSLocalDomainMask(2), //
		NSNetworkDomainMask(4), //
		NSSystemDomainMask(8), //
		NSAllDomainsMask(0x0ffff);//
		int value;

		NSSearchPathDomainMask(int v) {
			value = v;
		}

		public int getValue() {
			return value;
		}
	};

	/**
	 * @Description : Returns the path of the temporary directory for the current user.
	 * @return : Return Value A string containing the path of the temporary directory for the current user. If no such directory is
	 *         currently available, returns nil.
	 **/

	public static NSString NSTemporaryDirectory() {
		File cacheDir = GenericMainContext.sharedContext.getCacheDir();
		return new NSString(cacheDir.getAbsolutePath().toString());
	}

	/**
	 * @Description : Creates a list of directory search paths.
	 **/

	public static NSArray<?> NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory directory, NSSearchPathDomainMask domainMask,
			boolean expandTilde) {

		NSArray<NSString> arr = new NSArray<NSString>();
		String appData = GenericMainContext.sharedContext.getFilesDir().getAbsolutePath();
		arr.getWrappedList().add(new NSString(appData));
		return arr;

	}

	/**
	 * @Description : Returns the logon name of the current user.
	 * @return : Return Value The logon name of the current user.
	 **/

	public static NSString NSUserName() {
		return null;
	}

	/**
	 * @Description : Returns a string containing the full name of the current user.
	 * @return : Return Value A string containing the full name of the current user.
	 **/


	public static NSString NSFullUserName() {
		return new NSString("");
	}

	/**
	 * @Description : Returns the path to either the user’s or application’s home directory, depending on the platform.
	 * @return : Return Value The path to the current home directory..
	 **/


	public static NSString NSHomeDirectory() {
		NSString aPath = new NSString(GenericMainContext.sharedContext.getApplication().getFilesDir().getPath());
		return aPath;
	}

	/**
	 * @Description : Returns the path to a given user’s home directory.
	 * @param userName : The name of a user.
	 * @return : Return Value The path to the home directory for the user specified by userName.
	 **/


	public static NSString NSHomeDirectoryForUser(NSString userName) {
		return null;
	}

	/**
	 * @Description : Returns the root directory of the user’s system.
	 * @return : Return Value A string identifying the root directory of the user’s system.
	 **/


	public static NSString NSOpenStepRootDirectory() {
		return null;
	}

	/**
	 * @Signature: stringByAppendingPathComponent:
	 * @Declaration : - (NSString *)stringByAppendingPathComponent:(NSString *)aString
	 * @Description : Returns a new string made by appending to the receiver a given string.
	 * @param aString The path component to append to the receiver.
	 * @return Return Value A new string made by appending aString to the receiver, preceded if necessary by a path separator.
	 * @Discussion The following table illustrates the effect of this method on a variety of different paths, assuming that aString is
	 *             supplied as “scratch.tiff”: Receiver’s String Value Resulting String “/tmp” “/tmp/scratch.tiff” “/tmp/”
	 *             “/tmp/scratch.tiff” “/” “/scratch.tiff” “” (an empty string) “scratch.tiff” Note that this method only works with file
	 *             paths (not, for example, string representations of URLs).
	 **/
	public NSString stringByAppendingPathComponent(NSString mString) {
		if (mString != null) {
			StringBuffer strbuff = new StringBuffer(mString.getWrappedString());
			if (!mString.getWrappedString().endsWith("/")//
					&& (!mString.getWrappedString().equals("") //
					|| mString.getWrappedString().equals("/")) //
			)
				return new NSString(strbuff.append("/" + mString).toString());
		}
		return mString;
	}

	/**
	 * @Signature: stringsByAppendingPaths:
	 * @Declaration : - (NSArray *)stringsByAppendingPaths:(NSArray *)paths
	 * @Description : Returns an array of strings made by separately appending to the receiver each string in in a given array.
	 * @param paths An array of NSString objects specifying paths to add to the receiver.
	 * @return Return Value An array of NSString objects made by separately appending each string in paths to the receiver, preceded if
	 *         necessary by a path separator.
	 * @Discussion Note that this method only works with file paths (not, for example, string representations of URLs). See
	 *             stringByAppendingPathComponent: for an individual example.
	 **/
	public NSArray<NSString> stringsByAppendingPaths(NSArray<NSString> paths) {
		NSArray<NSString> result = new NSArray<NSString>();
		for (int i = 0; i < paths.getWrappedList().size(); i++) {
			result.getWrappedList().add(stringByAppendingPathComponent(paths.getWrappedList().get(i)));
		}
		return result;
	}
}
