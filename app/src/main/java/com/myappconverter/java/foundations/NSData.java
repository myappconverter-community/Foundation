
package com.myappconverter.java.foundations;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.ASCIIPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.mapping.utils.AndroidArrayUtils;
import com.myappconverter.mapping.utils.AndroidNetworkingUtils;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.PerformBlock;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class NSData extends NSObject
		implements NSCopying, NSMutableCopying, NSSecureCoding, Serializable {

    private static final Logger LOGGER = Logger.getLogger(NSData.class.getName());

	
	public static enum NSDataWritingOptions {
		NSDataWritingAtomic(1L << 0), //
		NSDataWritingWithoutOverwriting(1L << 1), //
		NSDataWritingFileProtectionNone(0x10000000), //
		NSDataWritingFileProtectionComplete(0x20000000), //
		NSDataWritingFileProtectionCompleteUnlessOpen(0x30000000), //
		NSDataWritingFileProtectionCompleteUntilFirstUserAuthentication(0x40000000), //
		NSDataWritingFileProtectionMask(0xf0000000);//
		private long value;

		private NSDataWritingOptions(long v) {
			value = v;
		}

		public int getValue() {
			return (int) value;
		}
	};

	// NSDataSearchOptions possible values
	
	public static enum NSDataSearchOptions {
		NSDataSearchBackwards(1L << 0), //
		NSDataSearchAnchored(1L << 1);
		long value;

		private NSDataSearchOptions(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	}


	public static final long NSDataReadingMappedIfSafe = (1L << 0);
	public static final long NSDataReadingUncached = (1L << 1);
	public static final long NSDataReadingMappedAlways = (1L << 3);

	// NSDataBase64EncodingOptions possible values
	
	public static enum NSDataBase64EncodingOptions {
		NSDataBase64Encoding64CharacterLineLength(1L << 0), //
		NSDataBase64Encoding76CharacterLineLength(1L << 1), //
		NSDataBase64EncodingEndLineWithCarriageReturn(1L << 4), //
		NSDataBase64EncodingEndLineWithLineFeed(1L << 5);//
		private long value;

		private NSDataBase64EncodingOptions(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	}

	// NSDataBase64DecodingOptions
	
	public static enum NSDataBase64DecodingOptions {
		NSDataBase64DecodingIgnoreUnknownCharacters(1L << 0);
		private long value;

		private NSDataBase64DecodingOptions(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	}

	private byte[] wrappedData;
	static IOException exception;
	private static boolean NSDATA_DEALLOCATED = false;
	public static final NSData EmptyData = new NSData();
	private NSRange range;

	public byte[] getWrappedData() {
		return wrappedData;
	}

	public void setWrappedData(byte[] wrappedData) {
		this.wrappedData = wrappedData;
	}

	private boolean getState() {
		return NSDATA_DEALLOCATED;
	}

	private void setState(boolean isDesalloced) {
		NSDATA_DEALLOCATED = isDesalloced;
	}

	/**
	 * @Declaration : + (id)data
	 * @Description : Creates and returns an empty data object.
	 * @return Return Value An empty data object.
	 * @Discussion This method is declared primarily for the use of mutable subclasses of NSData.
	 **/
	
	
	
	public static NSData data(Class<?> clazz) {
		NSData tData = new NSData();
		tData = (NSData) InstanceTypeFactory.getInstance(tData, NSData.class, clazz, null, null);
		return tData;
	}

	/**
	 * @Declaration : + (id)data
	 * @Description : Creates and returns an empty data object.
	 * @return Return Value An empty data object.
	 * @Discussion This method is declared primarily for the use of mutable subclasses of NSData.
	 **/
	
	
	
	public static Object data() {
		NSData tData = new NSData();
		return tData;
	}

	/**
	 * @Declaration : + (id)dataWithBytes:(const void *)bytes length:(NSUInteger)length
	 * @Description : Creates and returns a data object containing a given number of bytes copied from a given buffer.
	 * @param bytes A buffer containing data for the new object.
	 * @param length The number of bytes to copy from bytes. This value must not exceed the length of bytes.
	 * @return Return Value A data object containing length bytes copied from the buffer bytes. Returns nil if the data object could not be
	 *         created.
	 **/
	
	public static NSData dataWithBytesLength(Class<?> clazz, byte[] bytes, int length) {
		byte[] fdata;
		if (length >= bytes.length)
			fdata = bytes;
		else {
			fdata = new byte[length];
			System.arraycopy(bytes, 0, fdata, 0, length);
		}
		return new NSData(fdata);
	}

	
	public static NSData dataWithBytesLength(Class<?> clazz, int[] bytes, int length) {
		byte[] fdata = null;
		System.arraycopy(bytes, 0, fdata, 0, length);
		return new NSData(fdata);
	}

	/**
	 * @Declaration : + (id)dataWithBytesNoCopy:(void *)bytes length:(NSUInteger)length
	 * @Description : Creates and returns a data object that holds length bytes from the buffer bytes.
	 * @param bytes A buffer containing data for the new object. bytes must point to a memory block allocated with malloc.
	 * @param length The number of bytes to hold from bytes. This value must not exceed the length of bytes.
	 * @return Return Value A data object that holds length bytes from the buffer bytes. Returns nil if the data object could not be
	 *         created.
	 * @Discussion The returned object takes ownership of the bytes pointer and frees it on deallocation. Therefore, bytes must point to a
	 *             memory block allocated with malloc.
	 **/
	
	
	public static NSData dataWithBytesNoCopyLength(Class<?> clazz, byte[] bytes, int length) {
		byte[] fdata = new byte[length];
		int minLength = length > bytes.length ? bytes.length : length;
		System.arraycopy(bytes, 0, fdata, 0, minLength);
		return new NSData(fdata);
	}

	/**
	 * @Declaration : + (id)dataWithBytesNoCopy:(void *)bytes length:(NSUInteger)length freeWhenDone:(BOOL)freeWhenDone
	 * @Description : Creates and returns a data object that holds a given number of bytes from a given buffer.
	 * @param bytes A buffer containing data for the new object. If freeWhenDone is YES, bytes must point to a memory block allocated with
	 *            malloc.
	 * @param length The number of bytes to hold from bytes. This value must not exceed the length of bytes.
	 * @param freeWhenDone If YES, the returned object takes ownership of the bytes pointer and frees it on deallocation.
	 * @return Return Value A data object that holds length bytes from the buffer bytes. Returns nil if the data object could not be
	 *         created.
	 **/
	
	
	public static NSData dataWithBytesNoCopyLengthFreeWhenDone(Class<?> clazz, byte[] bytes,
			int length, Boolean freeWhenDone) {
		return dataWithBytesNoCopyLength(clazz, bytes, length);
	}

	/**
	 * @Declaration : + (id)dataWithContentsOfFile:(NSString *)path
	 * @Description : Creates and returns a data object by reading every byte from the file specified by a given path.
	 * @param path The absolute path of the file from which to read data.
	 * @return Return Value A data object by reading every byte from the file specified by path. Returns nil if the data object could not be
	 *         created.
	 * @Discussion This method is equivalent to dataWithContentsOfFile:options:error: with no options. If you need to know what was the
	 *             reason for failure, use dataWithContentsOfFile:options:error:. A sample using this method can be found in “Working With
	 *             Binary Data�?.
	 **/
	
	
	public static NSData dataWithContentsOfFile(Class<?> clazz, NSString path) {
		NSData data;
		if (path.hasPrefix(new NSString("assets"))) {
			try {
				String assets = "assets";
				path.setWrappedString(path.getWrappedString().substring(assets.length() + 1));
				data = new NSData(
						GenericMainContext.sharedContext.getAssets().open(path.getWrappedString()));
				if (data.wrappedData != null)
					return data;
			} catch (IOException e) {
				// TODO Auto-generated catch block
                LOGGER.info(String.valueOf(e));
                return null;
			}
		}

		try {
			data = new NSData(new FileInputStream(path.getWrappedString()));
			if (data.wrappedData != null)
				return data;
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Declaration : + (id)dataWithContentsOfFile:(NSString *)path options:(NSDataReadingOptions)mask error:(NSError **)errorPtr
	 * @Description : Creates and returns a data object by reading every byte from the file specified by a given path.
	 * @param path The absolute path of the file from which to read data.
	 * @param mask A mask that specifies options for reading the data. Constant components are described in “NSDataReadingOptions�?.
	 * @param errorPtr If an error occurs, upon return contains an NSError object that describes the problem.
	 * @return Return Value A data object by reading every byte from the file specified by path. Returns nil if the data object could not be
	 *         created.
	 **/

	
	
	public static NSData dataWithContentsOfFileOptionsError(Class<?> clazz, NSString path, int mask,
			NSError[] errorPtr) {
		NSData data = null;
		try {
			data = new NSData(new FileInputStream(path.getWrappedString()));
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			NSDictionary errorMsg = new NSDictionary();
			errorMsg.dictionaryWithObjectForKey(errorMsg.getClass(), new NSString(e.getMessage()),
					new NSString("description"));
			errorPtr[0].setUserInfo(errorMsg);
		}
		if (data.wrappedData != null)
			return data;
		return null;
	}

	/**
	 * @Declaration : + (id)dataWithContentsOfURL:(NSURL *)aURL
	 * @Description : Returns a data object containing the data from the location specified by a given URL.
	 * @param aURL The URL from which to read data.
	 * @return Return Value A data object containing the data from the location specified by aURL. Returns nil if the data object could not
	 *         be created.
	 * @Discussion This method is ideal for converting data:// URLs to NSData objects, and can also be used for reading short files
	 *             synchronously. If you need to read potentially large files, use inputStreamWithURL: to open a stream, then read the file
	 *             a piece at a time. If you need to know the reason for failure, use dataWithContentsOfURL:options:error:. Important: Do
	 *             not use this synchronous method to request network-based URLs. For network-based URLs, this method can block the current
	 *             thread for tens of seconds on a slow network, resulting in a poor user experience, and in iOS, may cause your app to be
	 *             terminated. Instead, for non-file URLs, consider using the dataTaskWithURL:completionHandler: method of the NSSession
	 *             class. See URL Loading System Programming Guide for details.
	 **/

	
	
	public static NSData dataWithContentsOfURL(Class<?> clazz, final NSURL url) {
		final NSData[] data = { null };
		AsyncTask task = new AsyncTask<NSString, Void, NSData>() {

			@Override
			protected NSData doInBackground(NSString... param) {
				data[0] = new NSData(url.getUrl());
				return data[0];
			}

			@Override
			protected void onPostExecute(NSData str) {
				super.onPostExecute(str);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
		};

		NSData str = null;
		try {
			str = (NSData) task.execute(new NSString[] {}).get();
		} catch (Exception ex) {
            LOGGER.info(String.valueOf(ex));
		}
		return str;
	}

	/**
	 * @Declaration : + (id)dataWithContentsOfURL:(NSURL *)aURL options:(NSDataReadingOptions)mask error:(NSError **)errorPtr
	 * @Description : Creates and returns a data object containing the data from the location specified by aURL.
	 * @param aURL The URL from which to read data.
	 * @param mask A mask that specifies options for reading the data. Constant components are described in “NSDataReadingOptions�?.
	 * @param errorPtr If there is an error reading in the data, upon return contains an NSError object that describes the problem.
	 * @Discussion This method is ideal for converting data:// URLs to NSData objects, and can also be used for reading short files
	 *             synchronously. If you need to read potentially large files, use inputStreamWithURL: to open a stream, then read the file
	 *             a piece at a time. Important: Do not use this synchronous method to request network-based URLs. For network-based URLs,
	 *             this method can block the current thread for tens of seconds on a slow network, resulting in a poor user experience, and
	 *             in iOS, may cause your app to be terminated. Instead, for non-file URLs, consider using the
	 *             dataTaskWithURL:completionHandler: method of the NSSession class. See URL Loading System Programming Guide for details.
	 **/
	
	
	public static NSData dataWithContentsOfURL(Class<?> clazz, NSURL aURL, int mask,
			NSError[] errorPtr) {
		NSData data = new NSData(aURL.getUrl());
		if (data.wrappedData != null)
			return data;
		NSDictionary error = new NSDictionary();
		error = NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
				new NSString(exception.getMessage()), new NSString("description"));
		errorPtr[0].setUserInfo(error);
		return null;
	}

	/**
	 * @Declaration : + (id)dataWithData:(NSData *)aData
	 * @Description : Creates and returns a data object containing the contents of another data object.
	 * @param aData A data object.
	 * @return Return Value A data object containing the contents of aData. Returns nil if the data object could not be created.
	 **/
	
	
	public static NSData dataWithData(Class<?> clazz, NSData aData) {
		byte[] bytes = aData.wrappedData;
		NSRange range = new NSRange(0, bytes.length);
		NSData nData = (NSData) InstanceTypeFactory.getInstance(clazz);

		nData.wrappedData = new byte[range.length()];
		for (int i = 0, j = range.location(); i < bytes.length; i++, j++) {
			nData.wrappedData[i] = bytes[j];
		}
		range = new NSRange(0, nData.wrappedData.length);
		return nData;
	}

	/**
	 * @Declaration : - (id)initWithBase64EncodedData:(NSData *)base64Data options:(NSDataBase64DecodingOptions)options
	 * @Description : Returns a data object initialized with the given Base-64 encoded data.
	 * @param base64Data A Base-64, UTF-8 encoded data object.
	 * @param options A mask that specifies options for Base-64 decoding the data. Possible values are given in
	 *            “NSDataBase64DecodingOptions�?.
	 * @return Return Value A data object containing the Base-64 decoded data. Returns nil if the data object could not be decoded.
	 * @Discussion The default implementation of this method will reject non-alphabet characters, including line break characters. To
	 *             support different encodings and ignore non-alphabet characters, specify an options value of
	 *             NSDataBase64DecodingIgnoreUnknownCharacters.
	 **/

	
	
	public NSData initWithBase64EncodedDataOption(NSData base64Data,
			NSDataBase64DecodingOptions options) {
		byte[] decodedBase64 = Base64.decode(base64Data.wrappedData, Base64.DEFAULT);
		return initWithData(new NSData(decodedBase64));
	}

	/**
	 * @Declaration : - (id)initWithBase64EncodedString:(NSString *)base64String options:(NSDataBase64DecodingOptions)options
	 * @Description : Returns a data object initialized with the given Base-64 encoded string.
	 * @param base64String A Base-64 encoded string.
	 * @param options A mask that specifies options for Base-64 decoding the data. Possible values are given in
	 *            “NSDataBase64DecodingOptions�?.
	 * @return Return Value A data object built by Base-64 decoding the provided string. Returns nil if the data object could not be
	 *         decoded.
	 * @Discussion The default implementation of this method will reject non-alphabet characters, including line break characters. To
	 *             support different encodings and ignore non-alphabet characters, specify an options value of
	 *             NSDataBase64DecodingIgnoreUnknownCharacters.
	 **/

	
	
	public NSData initWithBase64EncodedStringOption(NSString base64String,
			NSDataBase64DecodingOptions options) {
		byte[] decodedBase64 = Base64.decode(base64String.getWrappedString(), Base64.DEFAULT);
		range = new NSRange(0, decodedBase64.length);
		wrappedData = new byte[range.length()];
		for (int i = 0, j = range.location(); i < decodedBase64.length; i++, j++) {
			wrappedData[i] = decodedBase64[j];
		}
		range = new NSRange(0, wrappedData.length);
		return this;
	}

	/**
	 * @Declaration : - (id)initWithBytes:(const void *)bytes length:(NSUInteger)length
	 * @Description : Returns a data object initialized by adding to it a given number of bytes of data copied from a given buffer.
	 * @Discussion A data object initialized by adding to it length bytes of data copied from the buffer bytes. The returned object might be
	 *             different than the original receiver.
	 **/

	
	
	public NSData initWithBytesLength(byte[] bytes, int length) {
		byte[] fdata;
		if (length >= bytes.length)
			fdata = bytes;
		else {
			fdata = new byte[length];
			System.arraycopy(bytes, 0, fdata, 0, length);
		}
		this.wrappedData = fdata;
		return this;
	}

	/**
	 * @Declaration : - (id)initWithBytesNoCopy:(void *)bytes length:(NSUInteger)length
	 * @Description : Returns a data object initialized by adding to it a given number of bytes of data from a given buffer.
	 * @param bytes A buffer containing data for the new object. bytes must point to a memory block allocated with malloc.
	 * @param length The number of bytes to hold from bytes. This value must not exceed the length of bytes.
	 * @return Return Value A data object initialized by adding to it length bytes of data from the buffer bytes. The returned object might
	 *         be different than the original receiver.
	 * @Discussion The returned object takes ownership of the bytes pointer and frees it on deallocation. Therefore, bytes must point to a
	 *             memory block allocated with malloc.
	 **/
	
	
	public NSData initWithBytesNoCopyLength(byte[] bytes, int length) {
		System.arraycopy(bytes, 0, wrappedData, 0, length);
		return this;
	}

	/**
	 * @Declaration : - (id)initWithBytesNoCopy:(void *)bytes length:(NSUInteger)length deallocator:(void (^)(void *bytes, NSUInteger
	 *              length))deallocator
	 * @Description : Returns a data object initialized by adding to it a given number of bytes of data from a given buffer, with a custom
	 *              deallocator block.
	 * @param bytes A buffer containing data for the new object.
	 * @param length The number of bytes to hold from bytes. This value must not exceed the length of bytes.
	 * @param deallocator A block to invoke when the resulting NSData object is deallocated.
	 * @return Return Value A data object initialized by adding to it length bytes of data from the buffer bytes. The returned object might
	 *         be different than the original receiver.
	 * @Discussion Use this method to define your own deallocation behavior for the data buffer you provide. In order to avoid any
	 *             inadvertent strong reference cycles, you should avoid capturing pointers to any objects that may in turn maintain strong
	 *             references to the NSData object. This includes explicit references to self, and implicit references to self due to direct
	 *             instance variable access. To make it easier to avoid these references, the deallocator block takes two parameters, a
	 *             pointer to the buffer, and its length; you should always use these values instead of trying to use references from
	 *             outside the block.
	 **/

	
	
	public NSData initWithBytesNoCopy(byte[] bytes, int length,
			PerformBlock.VoidBlockVoidNSUInteger deallocator) {
		NSData nData = initWithBytesNoCopyLength(bytes, length);
		if (nData == null) {
			NSData ns = deallocator.perform(bytes, length);
			return ns;
		}
		return initWithBytesNoCopyLength(bytes, length);
	}

	/**
	 * @Declaration : - (id)initWithBytesNoCopy:(void *)bytes length:(NSUInteger)length freeWhenDone:(BOOL)flag
	 * @Description : Initializes a newly allocated data object by adding to it length bytes of data from the buffer bytes.
	 * @param bytes A buffer containing data for the new object. If flag is YES, bytes must point to a memory block allocated with malloc.
	 * @param length The number of bytes to hold from bytes. This value must not exceed the length of bytes.
	 * @param flag If YES, the returned object takes ownership of the bytes pointer and frees it on deallocation.
	 **/
	
	
	public NSData initWithBytesNoCopy(byte[] bytes, int length, boolean flag) {
		return initWithBytesNoCopyLength(bytes, length);
	}

	/**
	 * @Declaration : - (id)initWithContentsOfFile:(NSString *)path
	 * @Description : Returns a data object initialized by reading into it the data from the file specified by a given path.
	 * @param path The absolute path of the file from which to read data.
	 * @return Return Value A data object initialized by reading into it the data from the file specified by path. The returned object might
	 *         be different than the original receiver.
	 * @Discussion This method is equivalent to initWithContentsOfFile:options:error: with no options.
	 **/

	
	
	public NSData initWithContentsOfFile(NSString path) {
		InputStream fis = null;
		if (path.getWrappedString().contains("assets ")) {
			String newPath = path.getWrappedString().replace("assets ", "");
			try {
				fis = GenericMainContext.sharedContext.getAssets().open(newPath);
			} catch (IOException e) {
                LOGGER.info(String.valueOf(e));
			}
		} else {
			try {
				fis = new FileInputStream(path.getWrappedString());
			} catch (FileNotFoundException e1) {
                LOGGER.info(String.valueOf(e1));
				Log.d("Exception ", "Message :" + e1.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e1));
			}
		}

		try {

			this.wrappedData = AndroidIOUtils.toByteArray(fis);
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (Exception e) {
            LOGGER.info(String.valueOf(e));
        }
		return this;
	}

	/**
	 * @Declaration : - (id)initWithContentsOfFile:(NSString *)path options:(NSDataReadingOptions)mask error:(NSError **)errorPtr
	 * @Description : Returns a data object initialized by reading into it the data from the file specified by a given path.
	 * @param path The absolute path of the file from which to read data.
	 * @param mask A mask that specifies options for reading the data. Constant components are described in “NSDataReadingOptions�?.
	 * @param errorPtr If an error occurs, upon return contains an NSError object that describes the problem.
	 * @return Return Value A data object initialized by reading into it the data from the file specified by path. The returned object might
	 *         be different than the original receiver.
	 **/
	
	
	public NSData initWithContentsOfFileOptionsError(NSString path, int mask, NSError[] errorPtr) {
		NSDictionary error = new NSDictionary();
		try {
			this.wrappedData = AndroidIOUtils
					.toByteArray(new FileInputStream(path.getWrappedString()));
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error = NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
					new NSString(e.getMessage()), new NSString("description"));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error = NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
					new NSString(e.getMessage()), new NSString("description"));
		}
		errorPtr[0].setUserInfo(error);
		return this;
	}

	/**
	 * @Declaration : - (id)initWithContentsOfURL:(NSURL *)aURL
	 * @Description : Initializes a newly allocated data object initialized with the data from the location specified by aURL.
	 * @param aURL The URL from which to read data
	 * @return Return Value An NSData object initialized with the data from the location specified by aURL. The returned object might be
	 *         different than the original receiver.
	 **/

	
	
	public NSData initWithContentsOfURL(NSURL aURL) {
		NSData nsData = (NSData) InstanceTypeFactory.getInstance(this.getClass());
		try {
			nsData.wrappedData = AndroidNetworkingUtils.getContentsFromUrl(aURL.getUrl());
			return nsData;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Declaration : - (id)initWithContentsOfURL:(NSURL *)aURL options:(NSDataReadingOptions)mask error:(NSError **)errorPtr
	 * @Description : Returns a data object initialized with the data from the location specified by a given URL.
	 * @param aURL The URL from which to read data.
	 * @param mask A mask that specifies options for reading the data. Constant components are described in “NSDataReadingOptions�?.
	 * @param errorPtr If there is an error reading in the data, upon return contains an NSError object that describes the problem.
	 * @return Return Value A data object initialized with the data from the location specified by aURL. The returned object might be
	 *         different than the original receiver.
	 **/
	
	
	public NSData initWithContentsOfURLOptionsError(NSURL aURL, int mask, NSError[] errorPtr) {
		NSData nsData = (NSData) InstanceTypeFactory.getInstance(this.getClass());
		try {
			nsData.wrappedData = AndroidNetworkingUtils.getContentsFromUrl(aURL.getUrl());
		} catch (MalformedURLException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			errorPtr[0].setUserInfo(NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
					new NSString(e.getMessage()), new NSString("description")));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			errorPtr[0].setUserInfo(NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
					new NSString(e.getMessage()), new NSString("description")));
		}
		return nsData;
	}

	/**
	 * @Declaration : - (id)initWithData:(NSData *)data
	 * @Description : Returns a data object initialized with the contents of another data object.
	 * @param data A data object.
	 * @return Return Value A data object initialized with the contents data. The returned object might be different than the original
	 *         receiver.
	 **/
	
	
	public NSData initWithData(NSData data) {
		this.setWrappedData(data.getWrappedData());
		return this;
	}

	/**
	 * @Description: Returns a data object initialized with the given Base-64 encoded string.
	 * @Deprecated in iOS 7.0. You should transition to either initWithBase64EncodedString:options: or initWithBase64EncodedData:options
	 * @param base64String A Base-64 encoded string.
	 * @return A data object built by Base-64 decoding the provided string. Returns nil if the data object could not be decoded. Although
	 *         this method was only introduced publicly for iOS 7, it has existed since iOS 4; you can use it if your application needs to
	 *         target an operating system prior to iOS 7. This method behaves like initWithBase64EncodedString:options:, but ignores all
	 *         unknown characters.
	 */
	
	
	public NSData initWithBase64Encoding(NSString base64String) {
		return initWithBase64EncodedStringOption(base64String,
				NSDataBase64DecodingOptions.NSDataBase64DecodingIgnoreUnknownCharacters);
	}

	/**
	 * @Description: Creates and returns a data object from the mapped file specified by path
	 * @Deprecated : iOS 5.0
	 * @param path The absolute path of the file from which to read data.
	 * @return A data object from the mapped file specified by path. Returns nil if the data object could not be created. Because of file
	 *         mapping restrictions, this method should only be used if the file is guaranteed to exist for the duration of the data
	 *         object’s existence. It is generally safer to use the dataWithContentsOfFile: method. This methods assumes mapped files are
	 *         available from the underlying operating system. A mapped file uses virtual memory techniques to avoid copying pages of the
	 *         file into memory until they are actually needed.
	 */
	
	
	public static NSData dataWithContentsOfMappedFile(NSString path) {
		return dataWithContentsOfFile(NSData.class, path);
	}

	/**
	 * @Description: Returns a data object initialized by reading into it the mapped file specified by a given path.
	 * @Deprecated : iOS 5.0
	 * @param path The absolute path of the file from which to read data.
	 * @return A data object initialized by reading into it the mapped file specified by path. The returned object might be different than
	 *         the original receiver.
	 */
	
	
	public NSData initWithContentsOfMappedFile(NSString path) {
		return initWithContentsOfFile(path);
	}

	// Accessing Data

	/**
	 * @Declaration : - (const void *)bytes
	 * @Description : Returns a pointer to the receiver’s contents.
	 * @return Return Value A read-only pointer to the receiver’s contents.
	 * @Discussion If the length of the receiver is 0, this method returns nil.
	 **/
	
	
	public byte[] bytes() {
		return bytes(0, range.length());
	}

	public byte[] getBytes() {
		return bytes(0, range.length());
	}

	/**
	 * @Declaration : - (NSString *)description
	 * @Description : Returns an NSString object that contains a hexadecimal representation of the receiver’s contents.
	 * @return Return Value An NSString object that contains a hexadecimal representation of the receiver’s contents in NSData property list
	 *         format.
	 **/
	@Override
	
	
	public NSString description() {
		byte bytes[] = bytes();
		StringBuilder result = new StringBuilder(2 * length());
		for (int i = 0; i < length(); i++) {
			int b = bytes[i] & 255;
			result.append(Integer.toHexString(b).toUpperCase());
		}
		return new NSString(result.toString());
	}

	public NSString getDescription() {
		byte bytes[] = bytes();
		StringBuilder result = new StringBuilder(2 * length());
		for (int i = 0; i < length(); i++) {
			int b = bytes[i] & 255;
			result.append(Integer.toHexString(b).toUpperCase());
		}
		return new NSString(result.toString());
	}

	/**
	 * @Declaration : - (void)enumerateByteRangesUsingBlock:(void (^)(const void *bytes, NSRange byteRange, BOOL *stop))block
	 * @Description : Enumerate through each range of bytes in the data object using a block.
	 * @param block The block to apply to byte ranges in the array. The block takes three arguments: bytes The bytes for the current range.
	 *            byteRange The range of the current data bytes. stop A reference to a Boolean value. The block can set the value to YES to
	 *            stop further processing of the data. The stop argument is an out-only argument. You should only ever set this Boolean to
	 *            YES within the Block.
	 * @param bytes The bytes for the current range.
	 * @param byteRange The range of the current data bytes.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the data. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion The enumeration block is called once for each contiguous region of memory in the receiver (once total for a contiguous
	 *             NSData object), until either all bytes have been enumerated, or the stop parameter is set to YES.
	 **/

	
	
	public void enumerateByteRangesUsingBlock(PerformBlock.VoidBlockVoidNSRangeBOOL block) {
		// block.performAction(bytes, byteRange, stop);
	}

	/**
	 * @Declaration : - (void)getBytes:(void *)buffer length:(NSUInteger)length
	 * @Description : Copies a number of bytes from the start of the receiver's data into a given buffer.
	 * @param buffer A buffer into which to copy data.
	 * @param length The number of bytes from the start of the receiver's data to copy to buffer.
	 * @Discussion The number of bytes copied is the smaller of the length parameter and the length of the data encapsulated in the object.
	 **/
	
	
	public void getBytesLength(byte[] buffer, int length) {
		if (wrappedData.length < length) {
			length = wrappedData.length;
		}
		System.arraycopy(wrappedData, 0, buffer, 0, length);

	}

	/**
	 * @Declaration : - (void)getBytes:(void *)buffer range:(NSRange)range
	 * @Description : Copies a range of bytes from the receiver’s data into a given buffer.
	 * @param buffer A buffer into which to copy data.
	 * @param range The range of bytes in the receiver's data to copy to buffer. The range must lie within the range of bytes of the
	 *            receiver's data.
	 * @Discussion If range isn’t within the receiver’s range of bytes, an NSRangeException is raised.
	 **/
	
	
	public void getBytesRange(byte[] buffer, NSRange range) {
		if ((wrappedData.length - range.location) < range.length) {
			range.length = wrappedData.length - range.location;
		}
		System.arraycopy(wrappedData, range.location, buffer, 0, range.length);
	}

	/**
	 * @Declaration : - (NSData *)subdataWithRange:(NSRange)range
	 * @Description : Returns a data object containing the receiver’s bytes that fall within the limits specified by a given range.
	 * @param range The range in the receiver from which to get the data. The range must not exceed the bounds of the receiver.
	 * @return Return Value A data object containing the receiver’s bytes that fall within the limits specified by range. If range isn’t
	 *         within the receiver’s range of bytes, raises NSRangeException.
	 * @Discussion A sample using this method can be found in “Working With Binary Data�?.
	 **/
	
	
	public NSData subdataWithRange(NSRange range) {
		return new NSData(bytes(range));
	}

	/**
	 * @Declaration : - (NSRange)rangeOfData:(NSData *)dataToFind options:(NSDataSearchOptions)mask range:(NSRange)searchRange
	 * @Description : Finds and returns the range of the first occurrence of the given data, within the given range, subject to given
	 *              options.
	 * @param dataToFind The data for which to search. This value must not be nil. Important: Raises an NSInvalidArgumentException if
	 *            dataToFind is nil.
	 * @param mask A mask specifying search options. The “NSDataSearchOptions�? options may be specified singly or by combining them with the
	 *            C bitwise OR operator.
	 * @param searchRange The range within the receiver in which to search for dataToFind. If this range is not within the receiver’s range
	 *            of bytes, an NSRangeException raised.
	 * @return Return Value An NSRange structure giving the location and length of dataToFind within searchRange, modulo the options in
	 *         mask. The range returned is relative to the start of the searched data, not the passed-in search range. Returns {NSNotFound,
	 *         0} if dataToFind is not found or is empty (@"").
	 **/

	
	
	public NSRange rangeOfDataOptionsRange(NSData dataToFind, NSDataSearchOptions mask,
			NSRange searchRange) {
		if (dataToFind == null) {
			throw new IllegalArgumentException("NullArgumentException :: dataToFind is null.");
		}
		NSData searchData = subdataWithRange(searchRange);
		NSRange resRange = new NSRange(0, 0);
		if (searchData.wrappedData.length < dataToFind.wrappedData.length) {
			resRange.location = NSObjCRuntime.NSNotFound;
			return resRange;
		}
		if (mask == NSDataSearchOptions.NSDataSearchBackwards) {
			AndroidArrayUtils.reverse(searchData.wrappedData);
		}
		// TODO others mask, throws NSRangeException
		int location = Collections.indexOfSubList(convertArrayToList(dataToFind.wrappedData),
				convertArrayToList(searchData.wrappedData));
		resRange = new NSRange(location, dataToFind.length());

		return resRange;
	}

	/**
	 * 
	 * @Description: Copies a data object’s contents into a given buffer. (Deprecated in iOS 4.0. This method is unsafe because it could
	 *               potentially cause buffer overruns. You should use getBytes:length: or getBytes:range: instead.)
	 * @Declaration: - (void)getBytes:(void *)buffer
	 * @param buffer A buffer into which to copy the receiver's data. The buffer must be at least length bytes.
	 */
	
	
	public void getBytes(byte[] buffer) {
		buffer = ByteBuffer.wrap(wrappedData).array();
	}

	// Base-64 Encoding

	/**
	 * Create a Base-64, UTF-8 encoded NSData from the receiver's contents using the given options.
	 * 
	 * @param options A mask that specifies options for Base-64 encoding the data. Possible values are given in
	 *            “NSDataBase64EncodingOptions�?.
	 * @return A Base-64, UTF-8 encoded data object. By default, no line endings are inserted. If you specify one of the line length options
	 *         (NSDataBase64Encoding64CharacterLineLength or NSDataBase64Encoding76CharacterLineLength) but don’t specify the kind of line
	 *         ending to insert, the default line ending is Carriage Return + Line Feed.
	 */

	
	public NSData base64EncodedDataWithOptions(NSDataBase64EncodingOptions options) {
		byte[] encodedBase64 = Base64.encode(wrappedData, Base64.DEFAULT);
		return initWithData(new NSData(encodedBase64));
		// TODO verify option
	}

	/**
	 * Create a Base-64 encoded NSString from the receiver's contents using the given options.
	 * 
	 * @param options A mask that specifies options for Base-64 encoding the data. Possible values are given in
	 *            “NSDataBase64EncodingOptions�?.
	 * @return A Base-64 encoded string. By default, no line endings are inserted. If you specify one of the line length options
	 *         (NSDataBase64Encoding64CharacterLineLength or NSDataBase64Encoding76CharacterLineLength) but don’t specify the kind of line
	 *         ending to insert, the default line ending is Carriage Return + Line Feed.
	 */
	
	public NSString base64EncodedStringWithOptions(NSDataBase64EncodingOptions options) {
		byte[] encodedBase64 = Base64.encode(wrappedData, Base64.DEFAULT);
		return NSString.stringWithString(new NSString(new String(encodedBase64)));
	}

	/**
	 * Create a Base-64 encoded NSString from the receiver's contents. (Deprecated in iOS 7.0. You should transition to either
	 * base64EncodedStringWithOptions: or base64EncodedDataWithOptions:)
	 * 
	 * @return A Base-64 encoded string. Although this method was only introduced publicly for iOS 7, it has existed since iOS 4; you can
	 *         use it if your application needs to target an operating system prior to iOS 7. This method behaves like
	 *         base64EncodedStringWithOptions:, but ignores all unknown characters.
	 */
	
	
	public NSString base64Encoding() {
		return base64EncodedStringWithOptions(
				NSDataBase64EncodingOptions.NSDataBase64Encoding64CharacterLineLength);
	}

	// Testing Data
	/**
	 * @Signature: isEqualToData:
	 * @Declaration : - (BOOL)isEqualToData:(NSData *)otherData
	 * @Description : Compares the receiving data object to otherData.
	 * @param otherData The data object with which to compare the receiver.
	 * @return Return Value YES if the contents of otherData are equal to the contents of the receiver, otherwise NO.
	 * @Discussion Two data objects are equal if they hold the same number of bytes, and if the bytes at the same position in the objects
	 *             are the same.
	 **/
	
	public Boolean isEqualToData(NSData otherData) {
		if (otherData == null) {
			return false;
		}
		if (getClass() != otherData.getClass()) {
			return false;
		}
		if (Arrays.equals(wrappedData, otherData.wrappedData)) {
			return true;
		}
		return false;
	}

	/**
	 * @Signature: length
	 * @Declaration : - (NSUInteger)length
	 * @Description : Returns the number of bytes contained in the receiver.
	 * @return Return Value The number of bytes contained in the receiver.
	 **/
	
	public int length() {
		return range.length();
	}

	public int getLength() {
		return range.length();
	}
	// Storing Data

	/**
	 * @Signature: writeToFile:atomically:
	 * @Declaration : - (BOOL)writeToFile:(NSString *)path atomically:(BOOL)flag
	 * @Description : Writes the bytes in the receiver to the file specified by a given path.
	 * @param path The location to which to write the receiver's bytes. If path contains a tilde (~) character, you must expand it with
	 *            stringByExpandingTildeInPath before invoking this method.
	 * @param atomically If YES, the data is written to a backup file, and then—assuming no errors occur—the backup file is renamed to the
	 *            name specified by path; otherwise, the data is written directly to path.
	 * @return Return Value YES if the operation succeeds, otherwise NO.
	 **/
	
	public Boolean writeToFileAtomically(NSString path, boolean flag) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path.getWrappedString());
			out.write(wrappedData);
			out.close();
			return true;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
		}
		return false;
	}

	/**
	 * @Declaration : - (BOOL)writeToFile:(NSString *)path options:(NSDataWritingOptions)mask error:(NSError **)errorPtr
	 * @Description : Writes the bytes in the receiver to the file specified by a given path.
	 * @param path The location to which to write the receiver's bytes.
	 * @param mask A mask that specifies options for writing the data. Constant components are described in “NSDataWritingOptions�?.
	 * @param errorPtr If there is an error writing out the data, upon return contains an NSError object that describes the problem.
	 * @return Return Value YES if the operation succeeds, otherwise NO.
	 **/
	
	public Boolean writeToFileOptionsError(NSString path, NSDataWritingOptions mask,
			NSError[] errorPtr) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path.getWrappedString());
			out.write(wrappedData);
			out.close();
			return true;
		} catch (IOException ex) {
            LOGGER.info(String.valueOf(ex));
            if (errorPtr != null)
				errorPtr[0].setUserInfo(NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
						new NSString(ex.getMessage()), new NSString("description")));
			if (out != null)
				try {
					out.close();
				} catch (IOException ex1) {
                    LOGGER.info(String.valueOf(ex1));
                    if (errorPtr != null)
						errorPtr[0].setUserInfo(NSDictionary.dictionaryWithObjectForKey(
								NSDictionary.class, new NSString(ex1.getMessage()),
								new NSString("description")));
				}
		}
		return false;
	}

	/**
	 * @Declaration : - (BOOL)writeToURL:(NSURL *)aURL atomically:(BOOL)atomically
	 * @Description : Writes the bytes in the receiver to the location specified by aURL.
	 * @param aURL The location to which to write the receiver's bytes. Only file:// URLs are supported.
	 * @param atomically If YES, the data is written to a backup location, and then—assuming no errors occur—the backup location is renamed
	 *            to the name specified by aURL; otherwise, the data is written directly to aURL. atomically is ignored if aURL is not of a
	 *            type the supports atomic writes.
	 * @return Return Value YES if the operation succeeds, otherwise NO.
	 * @Discussion Since at present only file:// URLs are supported, there is no difference between this method and writeToFile:atomically:,
	 *             except for the type of the first argument.
	 **/
	
	public Boolean writeToURLAtomically(NSURL aURL, boolean atomically) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(aURL.absoluteString().getWrappedString());
			out.write(wrappedData);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;
	}

	/**
	 * @Declaration : - (BOOL)writeToURL:(NSURL *)aURL options:(NSDataWritingOptions)mask error:(NSError **)errorPtr
	 * @Description : Writes the bytes in the receiver to the location specified by a given URL.
	 * @param aURL The location to which to write the receiver's bytes.
	 * @param mask A mask that specifies options for writing the data. Constant components are described in “NSDataWritingOptions�?.
	 * @param errorPtr If there is an error writing out the data, upon return contains an NSError object that describes the problem.
	 * @return Return Value YES if the operation succeeds, otherwise NO.
	 * @Discussion Since at present only file:// URLs are supported, there is no difference between this method and
	 *             writeToFile:options:error:, except for the type of the first argument.
	 **/
	
	public Boolean writeToURL(NSURL aURL, NSDataWritingOptions mask, NSError[] errorPtr) {
		NSDictionary error = new NSDictionary();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(aURL.absoluteString().getWrappedString());
			out.write(wrappedData);
			out.close();
			return true;
		} catch (IOException ex) {
            LOGGER.info(String.valueOf(ex));
            error = NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
					new NSString(ex.getMessage()), new NSString("description"));
			errorPtr[0].setUserInfo(error);
			if (out != null)
				try {
					out.close();
				} catch (IOException ex1) {
                    LOGGER.info(String.valueOf(ex1));
                    error = NSDictionary.dictionaryWithObjectForKey(NSDictionary.class,
							new NSString(ex1.getMessage()), new NSString("description"));
					errorPtr[0].setUserInfo(error);
				}
		}
		return false;
	}

	
	@Override
	public void dealloc() {
		wrappedData = null;
		NSDATA_DEALLOCATED = true;
	}

	
	@Override
	public String toString() {
		return "NSData [" + Arrays.toString(wrappedData) + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(wrappedData);
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	};

	/** Added **/
	public NSData() {
		this(new byte[0], NSRange.ZeroRange, false);
	}

	public NSData(byte[] bytes, int offset, int count) {
		this(bytes, new NSRange(offset, count), false);
	}

	public NSData(byte[] bytes, NSRange range) {
		this(bytes, range, false);
	}

	public NSData(byte[] bytes, NSRange range, boolean noCopy) {
		if (noCopy) {
			wrappedData = bytes;
			this.range = range;
		} else {
			wrappedData = new byte[range.length()];
			for (int i = 0, j = range.location(); i < bytes.length; i++, j++) {
				wrappedData[i] = bytes[j];
			}
		}
	}

	public NSData(NSData otherData) {
		this(otherData.bytes());
	}

	public byte[] bytes(int offset, int length) {
		byte[] result = new byte[length];
		for (int i = 0, j = range.location() + offset; i < result.length; i++, j++) {
			result[i] = wrappedData[j];
		}
		return result;
	}

	public byte[] bytes(NSRange range) {
		return bytes(range.location(), range.length());
	}

	protected byte[] bytesNoCopy() {
		return wrappedData;
	}

	public int _offset() {
		return range.location();
	}

	public void writeToStream(OutputStream stream) {
		try {
			stream.write(bytesNoCopy(), _offset(), length());
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	
	@Override
	public NSObject clone() {
		return this;
	}

	protected byte[] immutableBytes() {
		return wrappedData;
	}

	protected NSRange immutableRange() {
		return range;
	}

	public String _hexString() {
		byte bytes[] = bytes();
		StringBuilder result = new StringBuilder(2 * length());
		for (int i = 0; i < length(); i++) {
			int b = bytes[i] & 255;
			result.append(Integer.toHexString(b).toUpperCase());
		}

		return result.toString();
	}

	// Protocols
	
	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	
	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		return null;
	}

	private class DataChunk {

		private byte[] chunk = new byte[1000];
		private int size = 0;
		private DataChunk next = null;

		private DataChunk(InputStream in) {
			try {
				size = in.read(chunk);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				size = 0;
				chunk = null;
			}
		}

		private byte[] consumeBytes() {
			DataChunk current = this;
			int total = 0;
			// Calculate data size
			while (current != null) {
				total += current.size;
				current = current.next;
			}
			if (total < 1)
				return null;

			// reconstruct array
			byte[] res = new byte[total];
			int loc = 0;
			current = this;
			while (current != null) {
				System.arraycopy(current.chunk, 0, res, loc, current.size);
				current.chunk = null;
				loc += current.size;
				current.size = 0;

				DataChunk cnext = current.next;
				current.next = null;
				current = cnext;
			}
			return res;
		}

		private boolean isValid() {
			return size > 0;
		}
	}

	public NSData(byte[] bytes) {
		this(bytes, new NSRange(0, bytes.length), false);
	}

	NSData(InputStream inputstream) {
		DataChunk head = null;
		DataChunk queue = null;
		DataChunk current = null;
		if (inputstream != null) {
			do {
				current = new DataChunk(inputstream);
				if (current.isValid()) {
					if (queue != null)
						queue.next = current;
					else
						head = current;
					queue = current;
				}
			} while (current.isValid());
			if (head != null)
				wrappedData = head.consumeBytes();
			else
				wrappedData = null;
		} else
			wrappedData = null;
	}

	NSData(URL url) {
		try {
			URLConnection yc = url.openConnection();
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String result = "";
			String inputLine = "";
			while ((inputLine = in.readLine()) != null)
				result += inputLine;
			in.close();
			wrappedData = result.getBytes();
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			exception = e;
		}
	}

	public List<Byte> convertArrayToList(byte[] data2) {
		if (data2 == null) {
			return null;
		}
		List<Byte> output = new ArrayList<Byte>(data2.length);
		for (int i = 0; i < data2.length; i++) {
			output.add(Byte.valueOf(data2[i]));
		}

		return output;
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}


	/**
	 * Creates a NSData object from its textual representation, which is a Base64 encoded amount of bytes.
	 * 
	 * @param base64 The Base64 encoded contents of the NSData object.
	 * @throws IOException When the given string is not a proper Base64 formatted string.
	 */
	public NSData(String base64) throws IOException {
		// Remove all white spaces from the string so that it is parsed completely
		// and not just until the first white space occurs.
		String data = base64.replaceAll("\\s+", "");
		wrappedData = Base64.decode(data, 0);
	}

	/**
	 * Creates a NSData object from a file. Using the files contents as the contents of this NSData object.
	 * 
	 * @param file The file containing the data.
	 * @throws FileNotFoundException If the file could not be found.
	 * @throws IOException If the file could not be read.
	 */
	public NSData(File file) throws FileNotFoundException, IOException {

		wrappedData = new byte[(int) file.length()];

		RandomAccessFile raf = new RandomAccessFile(file, "r");
		raf.read(wrappedData);
		raf.close();

	}

	/**
	 * Loads the bytes from this NSData object into a byte buffer
	 * 
	 * @param buf The byte buffer which will contain the data
	 * @param length The amount of data to copy
	 */
	public void getBytes(ByteBuffer buf, int length) {
		buf.put(wrappedData, 0, Math.min(wrappedData.length, length));
	}

	/**
	 * Loads the bytes from this NSData object into a byte buffer
	 * 
	 * @param buf The byte buffer which will contain the data
	 * @param rangeStart The start index
	 * @param rangeStop The stop index
	 */
	public void getBytes(ByteBuffer buf, int rangeStart, int rangeStop) {
		buf.put(wrappedData, rangeStart, Math.min(wrappedData.length, rangeStop));
	}

	/**
	 * Gets the Base64 encoded data contained in this NSData object.
	 * 
	 * @return The Base64 encoded data as a <code>String</code>.
	 */
	public String getBase64EncodedData() {
		return com.myappconverter.mapping.utils.Base64.encodeBytes(wrappedData).toString();
	}

	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<data>");
		xml.append("\n");
		String base64 = getBase64EncodedData();
		for (String line : base64.split("\n")) {
			indent(xml, level + 1);
			xml.append(line);
			xml.append("\n");
		}
		indent(xml, level);
		xml.append("</data>");
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		out.writeIntHeader(0x4, wrappedData.length);
		out.write(wrappedData);
	}

	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.DATA_BEGIN_TOKEN);
		int indexOfLastNewLine = ascii.lastIndexOf("\n");
		for (int i = 0; i < wrappedData.length; i++) {
			int b = wrappedData[i] & 0xFF;
			if (b < 16)
				ascii.append("0");
			ascii.append(Integer.toHexString(b));
			if (ascii.length() - indexOfLastNewLine > 80) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
			} else if ((i + 1) % 2 == 0 && i != wrappedData.length - 1) {
				ascii.append(" ");
			}
		}
		ascii.append(ASCIIPropertyListParser.DATA_END_TOKEN);
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		toASCII(ascii, level);
	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
        //
	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}