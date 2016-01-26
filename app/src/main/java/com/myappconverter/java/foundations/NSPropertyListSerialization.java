
package com.myappconverter.java.foundations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

import com.myappconverter.mapping.utils.PList;
import android.util.Log;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.foundations.constants.NSPropertyList;



public class NSPropertyListSerialization extends NSObject implements com.myappconverter.java.foundations.protocols.NSObject {

	int SPropertyListReadOptions;

	/**
	 * The NSPropertyListSerialization class provides methods that convert property list objects to and from several serialized formats.
	 * Property list objects include NSData, NSString, NSArray, NSDictionary, NSDate, and NSNumber objects. These objects are toll-free
	 * bridged with their respective Core Foundation types (CFData, CFString, and so on). For more about toll-free bridging, see
	 * â€œInterchangeable Data Typesâ€?.
	 */

	public static final int PLIST_ARRAY = 0;
	public static final int PLIST_DICTIONARY = 1;
	public static final int PLIST_DATA = 2;
	public static final int PLIST_STRING = 3;

	public static final char[] TOKEN_BEGIN = new char[] { '(', '{', '<', '"' };
	public static final char[] TOKEN_END = new char[] { ')', '}', '>', '"' };
	public static final char[] QUOTING_CHARS = new char[] { ':', '/', '-', '.', '\\' };

	// Serializing a Property List

	/**
	 * + (NSData *)dataWithPropertyList:(id)plist format:(NSPropertyListFormat)format options:(NSPropertyListWriteOptions)opt error:(NSError
	 * **)error
	 *
	 * @Description : Returns an NSData object containing a given property list in a specified format.
	 * @param plist A property list object. Passing nil for this value will cause an exception to be raised.
	 * @param format A property list format. Possible values for format are described in NSPropertyListFormat.
	 * @param opt The opt parameter is currently unused and should be set to 0.
	 * @param error If the method does not complete successfully, upon return contains an NSError object that describes the problem.
	 * @return Return Value An NSData object containing plist in the format specified by format.
	 */

	public static NSData dataWithPropertyListFormatOptionsError(Object plist, int format, int opt, NSError[] error) {
		try {
			NSMutableData data = new NSMutableData();
			if (format == NSPropertyList.NSPropertyListBinaryFormat_v1_0) {
				// binary
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				ObjectOutputStream o;
				try {
					o = new ObjectOutputStream(b);
					o.writeObject(plist);
					o.flush();
					o.close();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
				data.setWrappedData(b.toByteArray());
			} else {
				PList formatPlsit = new PList();
				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("root", plist);
					String xml = formatPlsit.encode(map);
					byte[] b = xml.getBytes();
					data.appendBytesLength(b, b.length);
				} catch (Exception e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
			}
			return data;
		} catch (NullPointerException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
			return null;
		}
	}

	/**
	 * + (NSInteger)writePropertyList:(id)plist toStream:(NSOutputStream *)stream format:(NSPropertyListFormat)format
	 * options:(NSPropertyListWriteOptions)opt error:(NSError **)error
	 *
	 * @Description : Writes the specified property list to the specified stream.
	 * @param plist A property list object. Passing nil for this value will cause an exception to be raised.
	 * @param stream An NSStream object. The stream should be open and configured for reading.
	 * @param format A property list format. Possible values for format are described in NSPropertyListFormat.
	 * @param opt The opt parameter is currently unused and should be set to 0.
	 * @param error If the method does not complete successfully, upon return contains an NSError object that describes the problem.
	 * @return Return Value Returns the number of bytes written to the stream. If the value is 0 an error occurred.
	 */

	public static long writePropertyListToStreamFormatOptionsError(NSObject plist, NSOutputStream stream, int format, int opt,
																   NSError[] error) {
		NSData data = dataWithPropertyListFormatOptionsError(plist, format, opt, error);
		try {
			AndroidIOUtils.write(data.getWrappedData(), stream.getmOutputStream());
			return data.getWrappedData().length;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return 0;
	}

	// Deserializing a Property List

	/**
	 * + (id)propertyListWithData:(NSData *)data options:(NSPropertyListReadOptions)opt format:(NSPropertyListFormat *)format error:(NSError
	 * **)error
	 *
	 * @Description : Creates and returns a property list from the specified data.
	 * @param data A data object containing a serialized property list. Passing nil for this value will cause an exception to be raised.
	 * @param opt The options can be any of those listed in â€œNSPropertyListMutabilityOptions.â€?
	 * @param format Upon return, contains the format that the property list was stored in. Pass NULL if you do not need to know the format.
	 * @param error If the method does not complete successfully, upon return contains an NSError object that describes the problem.
	 * @return Return Value A property list object corresponding to the representation in data. If data is not in a supported format,
	 *         returns nil.
	 */

	public static NSObject propertyListWithDataOptionsFormatError(NSData data, int opt, int format, NSError[] error) {
		if (format == NSPropertyList.NSPropertyListBinaryFormat_v1_0) {
			ByteArrayInputStream in = new ByteArrayInputStream(data.getWrappedData());
			ObjectInputStream is;
			try {
				is = new ObjectInputStream(in);
				Object obj = is.readObject();
				NSObject resObject = (NSObject) obj;
				return resObject;
			} catch (StreamCorruptedException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (ClassNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
		} else {
			ByteArrayInputStream in = new ByteArrayInputStream(data.getWrappedData());
			ObjectInputStream is;
			try {
				is = new ObjectInputStream(in);
				Object obj = is.readObject();
				Map<String, NSObject> map = (Map<String, NSObject>) obj;
				return map.get("root");
			} catch (StreamCorruptedException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (ClassNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
		}
		return null;
	}

	/**
	 * + (id)propertyListWithStream:(NSInputStream *)stream options:(NSPropertyListReadOptions)opt format:(NSPropertyListFormat *)format
	 * error:(NSError **)error
	 *
	 * @Description : Creates and returns a property list by reading from the specified stream.
	 * @param stream An NSStream object. The stream should be open and configured for reading.
	 * @param opt Set to 0â€”read options are not implemented.
	 * @param format Upon return, contains the format that the property list was stored in. Pass NULL if you do not need to know the format.
	 * @param error If the method does not complete successfully, upon return contains an NSError object that describes the problem.
	 * @return Return Value A property list object corresponding to the representation in data. If data is not in a supported format,
	 *         returns nil.
	 */

	public static NSObject propertyListWithStreamOptionsFormatError(NSInputStream stream, int opt, int format, NSError[] error) {
		try {
			byte[] data = AndroidIOUtils.toByteArray(stream.getWrappedInputStream());
			NSData mData = new NSData(data);
			return propertyListWithDataOptionsFormatError(mData, opt, format, error);
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	// Validating a Property List

	/**
	 * + (BOOL)propertyList:(id)plist isValidForFormat:(NSPropertyListFormat)format
	 *
	 * @Description : Returns a Boolean value that indicates whether a given property list is valid for a given format.
	 * @param plist A property list object.
	 * @param format A property list format. Possible values for format are listed in NSPropertyListFormat.
	 * @return Return Value YES if plist is a valid property list in format format, otherwise NO.
	 */

	public static boolean propertyListIsValidForFormat(NSObject plist, int format) {
		if (plist instanceof NSArray || plist instanceof NSString || plist instanceof NSDate || plist instanceof NSData
				|| plist instanceof NSDictionary || plist instanceof NSNumber) {
			return true;
		}
		return false;
	}

	// Obsolete Methods

	/**
	 * + (NSData *)dataFromPropertyList:(id)plist format:(NSPropertyListFormat)format errorDescription:(NSString **)errorString
	 *
	 * @Description : This method is obsolete and will be deprecated soon. (Deprecated. Use dataWithPropertyList:format:options:error:
	 *              instead.)
	 * @param plist A property list object.
	 * @param format A property list format. Possible values for format are described in NSPropertyListFormat.
	 * @param errorString Upon return, if the conversion is successful, errorString is nil. If the conversion fails, upon return contains a
	 *            string describing the nature of the error.
	 * @return Return Value An NSData object containing plist in the format specified by format.
	 */

	public static NSData dataFromPropertyListFormatErrorDescription(NSObject plist, int format, NSString[] errorString) {
		NSError[] error = new NSError[1];
		error[0] = new NSError();
		NSData data = dataWithPropertyListFormatOptionsError(plist, format, 0, error);
		errorString[0] = error[0].getDomain();
		return data;
	}

	/**
	 * + (id)propertyListFromData:(NSData *)data mutabilityOption:(NSPropertyListMutabilityOptions)opt format:(NSPropertyListFormat *)format
	 * errorDescription:(NSString **)errorString
	 *
	 * @Description : This method is obsolete and will be deprecated soon. (Deprecated. Use propertyListWithData:options:format:error:
	 *              instead.)
	 * @param data A data object containing a serialized property list.
	 * @param opt The opt parameter is currently unused and should be set to 0.
	 * @param format If the property list is valid, upon return contains the format. format can be NULL, in which case the property list
	 *            format is not returned. Possible values are described in NSPropertyListFormat.
	 * @param errorString Upon return, if the conversion is successful, errorString is nil. If the conversion fails, upon return contains a
	 *            string describing the nature of the error.
	 * @return Return Value A property list object corresponding to the representation in data. If data is not in a supported format,
	 *         returns nil.
	 */

	public static NSObject propertyListFromDataMutabilityOptionFormatErrorDescription(NSData data, int opt, int format,
																					  NSString[] errorString) {
		NSError[] error = new NSError[1];
		error[0] = new NSError();
		NSObject obj = propertyListWithDataOptionsFormatError(data, 0, format, error);
		errorString[0] = error[0].getDomain();
		return obj;
	}

	/**
	 * Returns the string representation of a property list.
	 *
	 * @plist The property list. It can be a String, NSData, NSArray, NSDictionary.
	 */
	public static String stringForPropertyList(Object plist) {
		if (plist == null)
			return "";
		if (plist instanceof NSArray || plist instanceof NSDictionary || plist instanceof NSData)
			return plist.toString();
		String x = plist.toString();
		boolean quote = false;
		for (int i = 0; i < x.length(); i++) {
			char c = x.charAt(i);
			for (int z = 0; z < TOKEN_BEGIN.length; z++) {
				if (c == TOKEN_BEGIN[z] || c == TOKEN_END[z])
					quote = true;
			}
			if (!quote) {
				for (int z = 0; z < QUOTING_CHARS.length; z++) {
					if (c == QUOTING_CHARS[z])
						quote = true;
				}
			}
			if (!quote && Character.isWhitespace(c)) {
				quote = true;
				i = x.length();
			}
		}
		if (quote)
			return "\"" + x + "\"";
		return x;
	}

}