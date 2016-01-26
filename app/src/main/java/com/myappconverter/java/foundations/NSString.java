
package com.myappconverter.java.foundations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.util.Log;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.coregraphics.CGPoint;
import com.myappconverter.java.coregraphics.CGRect;
import com.myappconverter.java.coregraphics.CGSize;
import com.myappconverter.java.foundations.NSLinguisticTagger.NSLinguisticTaggerOptions;
import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.constants.NSStringEncoding;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.AndroidFilenameUtils;
import com.myappconverter.java.foundations.utilities.AndroidWordUtils;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.java.uikit.NSStringDrawing.NSStringDrawingOptions;
import com.myappconverter.java.uikit.NSStringDrawingContext;
import com.myappconverter.mapping.utils.AndroidFileUtils;
import com.myappconverter.mapping.utils.AndroidRessourcesUtils;
import com.myappconverter.mapping.utils.AndroidStringUtils;
import com.myappconverter.mapping.utils.AndroidVersionUtils;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.PerformBlock;


public class NSString extends NSObject
		implements Serializable, NSCopying, NSMutableCopying, NSSecureCoding, Comparable<NSString> {

	private static final Logger LOGGER = Logger.getLogger(NSString.class.getName());
	private static final long serialVersionUID = 1L;
	protected static final String TAG = "NSString";

	public interface Adressable {
		NSString stringWithContentsOfURLEncodingError(NSURL url, NSStringEncoding enc,
													  NSError error);

		NSError[] getStringWithContentsOfURLEncodingErrorArray();
	}

	// Enumeration

	public static enum NSStringEnumerationOptions {
		NSStringEnumerationByLines(0), //
		NSStringEnumerationByParagraphs(1), //
		NSStringEnumerationByComposedCharacterSequences(2), //
		NSStringEnumerationByWords(3), //
		NSStringEnumerationBySentences(4), //
		NSStringEnumerationReverse(1L << 8), //
		NSStringEnumerationSubstringNotRequired(1L << 9), //
		NSStringEnumerationLocalized(1L << 10);

		long value;

		NSStringEnumerationOptions(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	};


	public static enum NSStringEncodingConversionOptions {
		NSStringEncodingConversionAllowLossy(1), //
		NSStringEncodingConversionExternalRepresentation(2);

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		int value;

		NSStringEncodingConversionOptions(int v) {
			value = v;
		}
	};


	public static enum NSStringCompareOptions {
		NSCaseInsensitiveSearch(1), //
		NSLiteralSearch(2), //
		NSBackwardsSearch(4), //
		NSAnchoredSearch(8), //
		NSNumericSearch(64), //
		NSDiacriticInsensitiveSearch(128), //
		NSWidthInsensitiveSearch(256), //
		NSForcedOrderingSearch(512), //
		NSRegularExpressionSearch(1024);
		int value;

		NSStringCompareOptions(int v) {
			value = v;
		}

		public int getValue() {
			return value;
		}
	}

	protected String wrappedString;

	public String getWrappedString() {
		return wrappedString;
	}

	public void setWrappedString(String aString) {
		wrappedString = aString;
	}

	public NSString() {
		this.wrappedString = new String();
	}

	public NSString(String string) {
		this.wrappedString = string;
	}

	// Creating and Initializing Strings
	/**
	 * @Signature: string
	 * @Declaration : + (instancetype)string
	 * @Description : Returns an empty string.
	 * @return Return Value An empty string.
	 **/

	public static NSString string() {
		return new NSString();
	}

	public static NSString string(Class clazz) {
		NSString str = new NSString();
		return (NSString) InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Returns an initialized NSString object that contains no characters.
	 * @return Return Value An initialized NSString object that contains no characters. The returned object may be different from the
	 *         original receiver.
	 **/
	@Override

	public NSString init() {
		return this;
	}

	/**
	 * @Signature: initWithBytes:length:encoding:
	 * @Declaration : - (instancetype)initWithBytes:(const void *)bytes length:(NSUInteger)length encoding:(NSStringEncoding)encoding
	 * @Description : Returns an initialized NSString object containing a given number of bytes from a given buffer of bytes interpreted in
	 *              a given encoding.
	 * @param bytes A buffer of bytes interpreted in the encoding specified by encoding.
	 * @param length The number of bytes to use from bytes.
	 * @param encoding The character encoding applied to bytes.
	 * @return Return Value An initialized NSString object containing length bytes from bytes interpreted using the encoding encoding. The
	 *         returned object may be different from the original receiver. The return byte strings are allowed to be unterminated. If the
	 *         length of the byte string is greater than the specified length a nil value is returned.
	 **/

	public Object initWithBytesLengthEncoding(byte[] bytes, int length, int encoding) {
		if (bytes.length < length) {
			return null;
		} else {
			this.wrappedString = new String(bytes, 0, length,
					NSStringEncoding.getCharsetFromInt(encoding));
			return this;
		}
	}

	public Object initWithBytesLengthEncoding(Class clazz, byte[] bytes, int length, NSStringEncoding encoding) {
		// not yet covered
		if (bytes.length < length) {
			return null;
		} else {
			// NSStringEncoding.getWrappedCharset() not yet covered
//		 	this.wrappedString = new String(bytes, 0, length, encoding.getWrappedCharset());
			return InstanceTypeFactory.getInstance(this, NSString.class, clazz, new NSString("setWrappedString"),
					this.getWrappedString());
		}
	}

	/**
	 * @Signature: initWithBytesNoCopy:length:encoding:freeWhenDone:
	 * @Declaration : - (instancetype)initWithBytesNoCopy:(void *)bytes length:(NSUInteger)length encoding:(NSStringEncoding)encoding
	 *              freeWhenDone:(BOOL)flag
	 * @Description : Returns an initialized NSString object that contains a given number of bytes from a given buffer of bytes interpreted
	 *              in a given encoding, and optionally frees the buffer.
	 * @param bytes A buffer of bytes interpreted in the encoding specified by encoding.
	 * @param length The number of bytes to use from bytes.
	 * @param encoding The character encoding of bytes.
	 * @param flag If YES, the receiver frees the memory when it no longer needs the data; if NO it wonâ€™t.
	 * @return Return Value An initialized NSString object containing length bytes from bytes interpreted using the encoding encoding. The
	 *         returned object may be different from the original receiver.
	 **/

	public Object initWithBytesNoCopyLengthEncodingFreeWhenDone(byte[] bytes, int length,
																int encoding, boolean freeWhenDone) {

		if (bytes.length < length) {
			return null;

		} else {
			this.wrappedString = new String(bytes, 0, length,
					NSStringEncoding.getCharsetFromInt(encoding));
			if (freeWhenDone) {
				bytes = null;
			}
			return this;

		}
	}

	public Object initWithBytesNoCopyLengthEncodingFreeWhenDone(Class clazz, byte[] bytes, int length, NSStringEncoding encoding,
																boolean freeWhenDone) {
		// not yet covered
		if (bytes.length < length) {
			return null;

		} else {
			// NSStringEncoding.getWrappedCharset() not yet covered
//	    this.wrappedString = new String(bytes, 0, length, encoding.getWrappedCharset());
			if (freeWhenDone) {
				bytes = null;
			}
			return InstanceTypeFactory.getInstance(this, NSString.class, clazz, new NSString("setWrappedString"), this.getWrappedString());
		}
	}

	/**
	 * @Signature: initWithCharacters:length:
	 * @Declaration : - (instancetype)initWithCharacters:(const unichar *)characters length:(NSUInteger)length
	 * @Description : Returns an initialized NSString object that contains a given number of characters from a given C array of Unicode
	 *              characters.
	 * @param characters A C array of Unicode characters; the value must not be NULL. Important:Â Raises an exception if characters is NULL,
	 *            even if length is 0.
	 * @param length The number of characters to use from characters.
	 * @return Return Value An initialized NSString object containing length characters taken from characters. The returned object may be
	 *         different from the original receiver.
	 **/

	public Object initWithCharactersLength(char[] characters, int length) {
		this.wrappedString = new String(characters, 0, length);
		return this;
	}

	public Object initWithCharactersLength(Class clazz, char[] characters, int length) {
		this.wrappedString = new String(characters, 0, length);
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithCharactersNoCopy:length:freeWhenDone:
	 * @Declaration : - (instancetype)initWithCharactersNoCopy:(unichar *)characters length:(NSUInteger)length freeWhenDone:(BOOL)flag
	 * @Description : Returns an initialized NSString object that contains a given number of characters from a given C array of Unicode
	 *              characters.
	 * @param characters A C array of Unicode characters.
	 * @param length The number of characters to use from characters.
	 * @param flag If YES, the receiver will free the memory when it no longer needs the characters; if NO it wonâ€™t.
	 * @return Return Value An initialized NSString object that contains length characters from characters. The returned object may be
	 *         different from the original receiver.
	 **/

	public Object initWithCharactersNoCopyLengthFreeWhenDone(char[] characters, int length,
															 boolean freeWhenDone) {
		if (length < characters.length)
			this.wrappedString = new String(characters, 0, length);
		else
			this.wrappedString = new String(characters);

		if (freeWhenDone) {
			characters = null;
		}
		return this;
	}

	public Object initWithCharactersNoCopyLengthFreeWhenDone(Class clazz, char[] characters,
															 int length, boolean freeWhenDone) {
		if (length < characters.length)
			this.wrappedString = new String(characters, 0, length);
		else
			this.wrappedString = new String(characters);

		if (freeWhenDone) {
			characters = null;
		}
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithString:
	 * @Declaration : - (instancetype)initWithString:(NSString *)aString
	 * @Description : Returns an NSString object initialized by copying the characters from another given string.
	 * @param aString The string from which to copy characters. This value must not be nil. Important:Â Raises an NSInvalidArgumentException
	 *            if aString is nil.
	 * @return Return Value An NSString object initialized by copying the characters from aString. The returned object may be different from
	 *         the original receiver.
	 **/

	public Object initWithString(NSString string) {
		this.wrappedString = string.wrappedString;
		return this;
	}

	public Object initWithString(Class clazz, NSString string) {
		this.wrappedString = string.wrappedString;
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithCString:encoding:
	 * @Declaration : - (instancetype)initWithCString:(const char *)nullTerminatedCString encoding:(NSStringEncoding)encoding
	 * @Description : Returns an NSString object initialized using the characters in a given C array, interpreted according to a given
	 *              encoding.
	 * @param nullTerminatedCString A C array of characters. The array must end with a NULL character; intermediate NULL characters are not
	 *            allowed.
	 * @param encoding The encoding of nullTerminatedCString.
	 * @return Return Value An NSString object initialized using the characters from nullTerminatedCString. The returned object may be
	 *         different from the original receiver
	 * @Discussion If nullTerminatedCString is not a NULL-terminated C string, or encoding does not match the actual encoding, the results
	 *             are undefined.
	 **/

	public Object initWithCStringEncoding(char[] characters, int encoding) {
		Object nsString = null;
		try {
			nsString = this.getClass().newInstance();
			CharBuffer cbuf = CharBuffer.wrap(characters);
			Charset charset = (encoding != 0) ? NSStringEncoding.getCharsetFromInt(encoding)
					: Charset.defaultCharset();
			ByteBuffer bbuf = charset.encode(cbuf);
			if (nsString instanceof NSString) {
				((NSString) nsString).setWrappedString(new String(bbuf.array()));
			} else if (nsString instanceof NSMutableString) {
				((NSMutableString) nsString).setWrappedString(new String(bbuf.array()));

			}
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return nsString;
	}

	public Object initWithCStringEncoding(Class clazz, char[] characters, int encoding) {
		Object nsString = null;
		try {
			nsString = this.getClass().newInstance();
			CharBuffer cbuf = CharBuffer.wrap(characters);
			Charset charset = (encoding != 0) ? NSStringEncoding.getCharsetFromInt(encoding)
					: Charset.defaultCharset();
			ByteBuffer bbuf = charset.encode(cbuf);
			if (nsString instanceof NSString) {
				((NSString) nsString).setWrappedString(new String(bbuf.array()));
			} else if (nsString instanceof NSMutableString) {
				((NSMutableString) nsString).setWrappedString(new String(bbuf.array()));

			}
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return InstanceTypeFactory.getInstance(nsString, NSString.class, clazz,
				new NSString("setWrappedString"), ((NSString) nsString).getWrappedString());
	}

	/**
	 * @Signature: initWithUTF8String:
	 * @Declaration : - (instancetype)initWithUTF8String:(const char *)bytes
	 * @Description : Returns an NSString object initialized by copying the characters from a given C array of UTF8-encoded bytes.
	 * @param bytes A NULL-terminated C array of bytes in UTF-8 encoding. This value must not be NULL. Important:Â Raises an exception if
	 *            bytes is NULL.
	 * @return Return Value An NSString object initialized by copying the bytes from bytes. The returned object may be different from the
	 *         original receiver.
	 **/

	public Object initWithUTF8String(char[] characters) {
		// @TODO check encoding
		this.wrappedString = new String(characters);
		return this;
	}

	public Object initWithUTF8String(Class clazz, char[] characters) {
		// @TODO check encoding
		this.wrappedString = new String(characters);
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithFormat:
	 * @Declaration : - (instancetype)initWithFormat:(NSString *)format, ...
	 * @Description : Returns an NSString object initialized by using a given format string as a template into which the remaining argument
	 *              values are substituted.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if format is
	 *            nil.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @return Return Value An NSString object initialized by using format as a template into which the remaining argument values are
	 *         substituted according to the system locale. The returned object may be different from the original receiver.
	 * @Discussion Invokes initWithFormat:locale:arguments: with nil as the locale, hence using the system locale to format numbers. This is
	 *             useful, for example, if you want to produce "non-localized" formatting which needs to be written out to files and parsed
	 *             back later.
	 **/

	public Object initWithFormat(NSString format, Object... arg) {

		if (format == null)
			throw new IllegalArgumentException();

		this.wrappedString = String.format(format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));

		return this;

	}

	public Object initWithFormat(Class clazz, NSString format, Object... arg) {
		if (format == null)
			throw new IllegalArgumentException("format is null");
		this.wrappedString = String.format(format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());

	}

	/**
	 * @Signature: initWithFormat:arguments:
	 * @Declaration : - (instancetype)initWithFormat:(NSString *)format arguments:(va_list)argList
	 * @Description : Returns an NSString object initialized by using a given format string as a template into which the remaining argument
	 *              values are substituted according to the current locale.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if format is
	 *            nil.
	 * @param argList A list of arguments to substitute into format.
	 * @return Return Value An NSString object initialized by using format as a template into which the values in argList are substituted
	 *         according to the current locale. The returned object may be different from the original receiver.
	 * @Discussion Invokes initWithFormat:locale:arguments: with nil as the locale.
	 **/

	public Object initWithFormatArguments(NSString format, NSObject... arg) {
		List<NSObject> argList = new ArrayList<NSObject>();
		for (NSObject objArg : arg) {
			argList.add(objArg);
		}
		this.wrappedString = String.format(format.getWrappedString(), argList.toArray());
		return this;
	}

	public Object initWithFormatArguments(Class clazz, NSString format, NSObject... arg) {
		List<NSObject> argList = new ArrayList<NSObject>();
		for (NSObject objArg : arg) {
			argList.add(objArg);
		}
		this.wrappedString = String.format(format.getWrappedString(), argList.toArray());
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithFormat:locale:
	 * @Declaration : - (instancetype)initWithFormat:(NSString *)format locale:(id)locale, ...
	 * @Description : Returns an NSString object initialized by using a given format string as a template into which the remaining argument
	 *              values are substituted according to given locale.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if format is
	 *            nil.
	 * @param locale An NSLocale object specifying the locale to use. To use the current locale, pass [NSLocalecurrentLocale]. To use the
	 *            system locale, pass nil. For legacy support, this may be an instance of NSDictionary containing locale information.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @Discussion Invokes initWithFormat:locale:arguments: with locale as the locale.
	 **/

	public Object initWithFormatLocale(NSString format, NSLocale local, NSString... arg) {
		if (arg.length > 0) {
			format = arg[0];

			if (format == null)
				throw new IllegalArgumentException();

		}
		this.wrappedString = String.format(local.getWrappedLocale(), format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));

		return this;
	}

	public Object initWithFormatLocale(Class clazz, NSString format, NSLocale local,
									   NSString... arg) {
		if (arg.length > 0) {
			format = arg[0];

			if (format == null)
				throw new IllegalArgumentException();

		}
		this.wrappedString = String.format(local.getWrappedLocale(), format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));

		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithFormat:locale:arguments:
	 * @Declaration : - (instancetype)initWithFormat:(NSString *)format locale:(id)locale arguments:(va_list)argList
	 * @Description : Returns an NSString object initialized by using a given format string as a template into which the remaining argument
	 *              values are substituted according to given locale information.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if format is
	 *            nil.
	 * @param locale An NSLocale object specifying the locale to use. To use the current locale (specified by user preferences), pass
	 *            [NSLocalecurrentLocale]. To use the system locale, pass nil. For legacy support, this may be an instance of NSDictionary
	 *            containing locale information.
	 * @param argList A list of arguments to substitute into format.
	 * @return Return Value An NSString object initialized by using format as a template into which values in argList are substituted
	 *         according the locale information in locale. The returned object may be different from the original receiver.
	 * @Discussion The following code fragment illustrates how to create a string from myArgs, which is derived from a string object with
	 *             the value â€œCost:â€? and an int with the value 32: va_list myArgs; NSString *myString = [[NSString alloc] initWithFormat:@
	 *             "%@:  %d\n" locale:[NSLocale currentLocale] arguments:myArgs]; The resulting string has the value â€œCost: 32\nâ€?. See
	 *             String Programming Guide for more information.
	 **/

	public Object initWithFormatLocaleArguments(NSString format, NSLocale local, NSString... arg) {
		if (arg.length > 0) {
			format = arg[0];
			if (format == null)
				throw new IllegalArgumentException();
		}
		this.wrappedString = String.format(local.getWrappedLocale(), format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));

		return this;
	}

	public Object initWithFormatLocaleArguments(Class clazz, NSString format, NSLocale local,
												NSString... arg) {
		if (arg.length > 0) {
			format = arg[0];
			if (format == null)
				throw new IllegalArgumentException();
		}
		this.wrappedString = String.format(local.getWrappedLocale(), format.getWrappedString(),
				(Object) (Arrays.copyOfRange(arg, 0, arg.length - 1)));

		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: initWithData:encoding:
	 * @Declaration : - (instancetype)initWithData:(NSData *)data encoding:(NSStringEncoding)encoding
	 * @Description : Returns an NSString object initialized by converting given data into Unicode characters using a given encoding.
	 * @param data An NSData object containing bytes in encoding and the default plain text format (that is, pure content with no attributes
	 *            or other markups) for that encoding.
	 * @param encoding The encoding used by data.
	 * @return Return Value An NSString object initialized by converting the bytes in data into Unicode characters using encoding. The
	 *         returned object may be different from the original receiver. Returns nil if the initialization fails for some reason (for
	 *         example if data does not represent valid data for encoding).
	 **/

	public Object initWithDataEncoding(NSData data, int encoding) {

		this.wrappedString = new String(data.getWrappedData(),
				NSStringEncoding.getCharsetFromInt(encoding));
		return this;
	}

	public Object initWithDataEncoding(Class clazz, NSData data, int encoding) {

		this.wrappedString = new String(data.getWrappedData(),
				NSStringEncoding.getCharsetFromInt(encoding));
		return InstanceTypeFactory.getInstance(this, NSString.class, clazz,
				new NSString("setWrappedString"), this.getWrappedString());
	}

	/**
	 * @Signature: stringWithFormat:
	 * @Declaration : + (instancetype)stringWithFormat:(NSString *)format,, ...
	 * @Description : Returns a string created by using a given format string as a template into which the remaining argument values are
	 *              substituted.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if format is
	 *            nil.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @return Return Value A string created by using format as a template into which the remaining argument values are substituted
	 *         according to the system locale.
	 * @Discussion This method is similar to localizedStringWithFormat:, but using the system locale to format numbers. This is useful, for
	 *             example, if you want to produce â€œnon-localizedâ€? formatting which needs to be written out to files and parsed back later.
	 **/

	public static NSString stringWithFormat(NSString format, Object... args) {
		// not yet covered
		// if (format.getWrappedString() == null) {
		// throw new IllegalArgumentException("stringWithFormat : format must not be null");
		// }
		// // String aFormat = format.getWrappedString().replace("%@", "%s");
		// // return new NSString(String.format(aFormat, args));
		//
		// String[] params = { "1$", "2$", "3$", "4$", "5$" };
		// String mStr = format.getWrappedString();
		// for (String str : params) {
		// if (format.getWrappedString().indexOf(str) != -1)
		// mStr = mStr.replace(str, "");
		// }
		//
		// mStr = mStr.replaceAll("%@", "%s");
		// mStr = mStr.replaceAll("ld", "d");
		// mStr = mStr.replaceAll("lf", "f");
		// mStr = mStr.replaceAll("lx", "d");
		// mStr = mStr.replaceAll("lu", "d");
		// // mStr = mStr.replaceAll("(?i)u", "d");//FIXME music ===> mdsic
		// mStr = mStr.replaceAll("%%", "%c");
		// mStr = mStr.replaceAll("\\.f", "f");
		// mStr = mStr.replaceAll("%02i", "%02d");
		// mStr = mStr.replaceAll("%i", "%d");
		// return new NSString(String.format(mStr, args));
		return null;
	}

	public static NSString stringWithFormat(Class clazz, NSString format, Object... args) {
		if (format.getWrappedString() == null) {
			throw new IllegalArgumentException("stringWithFormat : format must not be null");
		}

		String[] params = { "1$", "2$", "3$", "4$", "5$" };
		String mStr = format.getWrappedString();
		for (String str : params) {
			if (format.getWrappedString().indexOf(str) != -1)
				mStr = mStr.replace(str, "");
		}

		mStr = mStr.replaceAll("%@", "%s");
		mStr = mStr.replaceAll("%ld", "%d");
		mStr = mStr.replaceAll("%lf", "%f");
		mStr = mStr.replaceAll("%lx", "%d");
		mStr = mStr.replaceAll("%lu", "%d");
		// convert an int32 argument
		mStr = mStr.replaceAll("%li", "%d");
		mStr = mStr.replaceAll("%lX", "%d");
		mStr = mStr.replaceAll("%lo", "%d");
		mStr = mStr.replaceAll("%lu", "%d");

		// mStr = mStr.replaceAll("(?i)u", "d");//FIXME music ===> mdsic
		mStr = mStr.replaceAll("%%", "%c");
		mStr = mStr.replaceAll("\\.f", "%f");
		mStr = mStr.replaceAll("%02i", "%02d");
		mStr = mStr.replaceAll("%i", "%d");
		mStr = mStr.replaceAll("%04d", "%04d");
		mStr = mStr.replaceAll("%02li", "%02d");

		if (args.length < 1) {
			args = null;
		}

		NSString newstr = new NSString(String.format(mStr, args));
		return (NSString) InstanceTypeFactory.getInstance(newstr, NSString.class, clazz,
				new NSString("setWrappedString"), newstr.getWrappedString());
	}

	/**
	 * @Signature: localizedStringWithFormat:
	 * @Declaration : + (instancetype)localizedStringWithFormat:(NSString *)format, ...
	 * @Description : Returns a string created by using a given format string as a template into which the remaining argument values are
	 *              substituted according to the current locale.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for examples of how to use this method, and â€œString Format Specifiersâ€?
	 *            for a list of format specifiers. This value must not be nil. Raises an NSInvalidArgumentException if format is nil.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @return Return Value A string created by using format as a template into which the following argument values are substituted
	 *         according to the formatting information in the current locale.
	 * @Discussion This method is equivalent to using initWithFormat:locale: and passing the current locale as the locale argument.
	 **/

	public static NSString localizedStringWithFormat(NSString format, NSString... args) {
		NSString nsString = new NSString();
		if (format == null)
			throw new IllegalArgumentException();
		nsString.setWrappedString(String.format(Locale.getDefault(), format.getWrappedString()));
		return nsString;
	}

	public static NSString localizedStringWithFormat(Class clazz, NSString format,
													 NSString... args) {
		NSString nsString = new NSString();
		if (format == null)
			throw new IllegalArgumentException();
		nsString.setWrappedString(String.format(Locale.getDefault(), format.getWrappedString()));
		return nsString;
	}

	/**
	 * @Signature: stringWithCharacters:length:
	 * @Declaration : + (instancetype)stringWithCharacters:(const unichar *)chars length:(NSUInteger)length
	 * @Description : Returns a string containing a given number of characters taken from a given C array of Unicode characters.
	 * @param chars A C array of Unicode characters; the value must not be NULL. Important:Â Raises an exception if chars is NULL, even if
	 *            length is 0.
	 * @param length The number of characters to use from chars.
	 * @return Return Value A string containing length Unicode characters taken (starting with the first) from chars.
	 **/

	public static NSString stringWithCharactersLength(char[] characters, int length) {
		NSString myNSString = new NSString();
		myNSString.wrappedString = new String(characters, 0, length);
		return myNSString;
	}

	public static Object stringWithCharactersLength(Class clazz, char[] characters, int length) {
		NSString myNSString = new NSString();
		myNSString.wrappedString = new String(characters, 0, length);
		return InstanceTypeFactory.getInstance(myNSString, NSString.class, clazz,
				new NSString("setWrappedString"), myNSString.getWrappedString());
	}

	/**
	 * @Signature: stringWithString:
	 * @Declaration : + (instancetype)stringWithString:(NSString *)aString
	 * @Description : Returns a string created by copying the characters from another given string.
	 * @param aString The string from which to copy characters. This value must not be nil. Important:Â Raises an NSInvalidArgumentException
	 *            if aString is nil.
	 * @return Return Value A string created by copying the characters from aString.
	 **/

	public static NSString stringWithString(NSString mString) {
		NSString myNSString = new NSString();
		myNSString.wrappedString = mString.wrappedString;
		return myNSString;
	}

	public static Object stringWithString(Class clazz, NSString mString) {
		NSString myNSString = new NSString();
		myNSString.wrappedString = mString.wrappedString;
		return InstanceTypeFactory.getInstance(myNSString, NSString.class, clazz,
				new NSString("setWrappedString"), myNSString.getWrappedString());
	}

	/**
	 * @Signature: stringWithCString:encoding:
	 * @Declaration : + (instancetype)stringWithCString:(const char *)cString encoding:(NSStringEncoding)enc
	 * @Description : Returns a string containing the bytes in a given C array, interpreted according to a given encoding.
	 * @param cString A C array of bytes. The array must end with a NULL byte; intermediate NULL bytes are not allowed.
	 * @param enc The encoding of cString.
	 * @return Return Value A string containing the characters described in cString.
	 * @Discussion If cString is not a NULL-terminated C string, or encoding does not match the actual encoding, the results are undefined.
	 **/

	public static NSString stringWithCStringEncoding(char[] characters, int encoding) {
		if (characters != null && characters.length > 0 && encoding != 0) {
			byte bytes[] = (new String(characters))
					.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			return new NSString(new String(bytes, Charset.defaultCharset()));
		}
		throw new IllegalArgumentException(" This value must not be null");
	}

	public static Object stringWithCStringEncoding(Class clazz, char[] characters, int encoding) {
		if (characters != null && characters.length > 0 && encoding != 0) {
			byte bytes[] = (new String(characters))
					.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			NSString str = new NSString(new String(bytes, Charset.defaultCharset()));
			return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
					new NSString("setWrappedString"), str.getWrappedString());
		}
		throw new IllegalArgumentException(" This value must not be null");
	}

	/**
	 * @Signature: stringWithCString:encoding:
	 * @Declaration : + (instancetype)stringWithCString:(const char *)cString encoding:(NSStringEncoding)enc
	 * @Description : Returns a string containing the bytes in a given C array, interpreted according to a given encoding.
	 * @param cString A C array of bytes. The array must end with a NULL byte; intermediate NULL bytes are not allowed.
	 * @param enc The encoding of cString.
	 * @return Return Value A string containing the characters described in cString.
	 * @Discussion If cString is not a NULL-terminated C string, or encoding does not match the actual encoding, the results are undefined.
	 **/

	public static NSString stringWithCStringEncoding(String characters, int encoding) {
		if (characters != null && characters.length() > 0 && encoding != 0) {
			byte bytes[] = characters.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			return new NSString(new String(bytes, Charset.defaultCharset()));
		}
		throw new IllegalArgumentException(" This value must not be null");

	}

	public static Object stringWithCStringEncoding(Class clazz, String characters, int encoding) {
		if (characters != null && characters.length() > 0 && encoding != 0) {
			byte bytes[] = characters.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			NSString str = new NSString(new String(bytes, Charset.defaultCharset()));
			return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
					new NSString("setWrappedString"), str.getWrappedString());

		}
		throw new IllegalArgumentException(" This value must not be null");

	}

	/**
	 * @Signature: stringWithUTF8String:
	 * @Declaration : + (instancetype)stringWithUTF8String:(const char *)bytes
	 * @Description : Returns a string created by copying the data from a given C array of UTF8-encoded bytes.
	 * @param bytes A NULL-terminated C array of bytes in UTF8 encoding. Important:Â Raises an exception if bytes is NULL.
	 * @return Return Value A string created by copying the data from bytes.
	 **/

	public static NSString stringWithUTF8String(char[] string) {
		NSString myNSSTring = new NSString();
		myNSSTring.wrappedString = new String(string);
		return myNSSTring;
	}

	public static Object stringWithUTF8String(Class clazz, char[] string) {
		NSString myNSSTring = new NSString();
		myNSSTring.wrappedString = new String(string);
		return InstanceTypeFactory.getInstance(myNSSTring, NSString.class, clazz,
				new NSString("setWrappedString"), myNSSTring.getWrappedString());
	}

	public static Object stringWithUTF8String(Class clazz, NSString string) {
		return InstanceTypeFactory.getInstance(string, NSString.class, clazz,
				new NSString("setWrappedString"), string.getWrappedString());
	}

	/**
	 * @Signature: stringWithCString:
	 * @Declaration : + (id)stringWithCString:(const char *)cString
	 * @Description : Creates a new string using a given C-string. (Deprecated in iOS 2.0. Use stringWithCString:encoding: instead.)
	 * @Discussion cString should contain data in the default C string encoding. If the argument passed to stringWithCString: is not a
	 *             zero-terminated C-string, the results are undefined.
	 **/


	public static NSString stringWithCString(char[] string) {
		NSString myNSSTring = new NSString();
		myNSSTring.wrappedString = new String(string);
		return myNSSTring;
	}

	/**
	 * @Signature: stringWithCString:length:
	 * @Declaration : + (id)stringWithCString:(const char *)cString length:(NSUInteger)length
	 * @Description : Returns a string containing the characters in a given C-string. (Deprecated in iOS 2.0. Use
	 *              stringWithCString:encoding: instead.)
	 * @Discussion cString must not be NULL. cString should contain characters in the default C-string encoding. This method converts length
	 *             * sizeof(char) bytes from cString and doesnâ€™t stop short at a NULL character.
	 **/


	public static NSString stringWithCStringLength(char[] string, int length) {
		NSString myNSSTring = new NSString();
		myNSSTring.wrappedString = new String(string, 0, length);
		return myNSSTring;
	}

	/**
	 * @Signature: initWithCString:
	 * @Declaration : - (id)initWithCString:(const char *)cString
	 * @Description : Initializes the receiver, a newly allocated NSString object, by converting the data in a given C-string from the
	 *              default C-string encoding into the Unicode character encoding. (Deprecated in iOS 2.0. Use initWithCString:encoding:
	 *              instead.)
	 * @Discussion cString must be a zero-terminated C string in the default C string encoding, and may not be NULL. Returns an initialized
	 *             object, which might be different from the original receiver. To create an immutable string from an immutable C string
	 *             buffer, do not attempt to use this method. Instead, use initWithCStringNoCopy:length:freeWhenDone:.
	 **/


	public NSString initWithCString(char[] string) {
		this.wrappedString = new String(string);
		return this;
	}

	/**
	 * @Signature: initWithCString:length:
	 * @Declaration : - (id)initWithCString:(const char *)cString length:(NSUInteger)length
	 * @Description : Initializes the receiver, a newly allocated NSString object, by converting the data in a given C-string from the
	 *              default C-string encoding into the Unicode character encoding. (Deprecated in iOS 2.0. Use
	 *              initWithBytes:length:encoding: instead.)
	 * @Discussion This method converts length * sizeof(char) bytes from cString and doesnâ€™t stop short at a zero character. cString must
	 *             contain bytes in the default C-string encoding and may not be NULL. Returns an initialized object, which might be
	 *             different from the original receiver.
	 **/


	public NSString initWithCStringLength(char[] string, int length) {
		this.wrappedString = new String(string, 0, length);
		return this;
	}

	/**
	 * @Signature: initWithCStringNoCopy:length:freeWhenDone:
	 * @Declaration : - (id)initWithCStringNoCopy:(char *)cString length:(NSUInteger)length freeWhenDone:(BOOL)flag
	 * @Description : Initializes the receiver, a newly allocated NSString object, by converting the data in a given C-string from the
	 *              default C-string encoding into the Unicode character encoding. (Deprecated in iOS 2.0. Use
	 *              initWithBytesNoCopy:length:encoding:freeWhenDone: instead.)
	 * @Discussion This method converts length * sizeof(char) bytes from cString and doesnâ€™t stop short at a zero character. cString must
	 *             contain data in the default C-string encoding and may not be NULL. The receiver becomes the owner of cString; if flag is
	 *             YES it will free the memory when it no longer needs it, but if flag is NO it wonâ€™t. Returns an initialized object, which
	 *             might be different from the original receiver. You can use this method to create an immutable string from an immutable
	 *             (const char *) C-string buffer. If you receive a warning message, you can disregard it; its purpose is simply to warn you
	 *             that the C string passed as the methodâ€™s first argument may be modified. If you make certain the freeWhenDone argument to
	 *             initWithStringNoCopy is NO, the C string passed as the methodâ€™s first argument cannot be modified, so you can safely use
	 *             initWithStringNoCopy to create an immutable string from an immutable (const char *) C-string buffer.
	 **/


	public NSString initWithCStringNoCopyLengthFreeWhenDone(char[] string, int length,
															boolean freeWhenDone) {
		this.wrappedString = new String(string, 0, length);
		if (freeWhenDone) {
			string = null;
		}

		return this;
	}

	// Creating and Initializing a String from a File

	/**
	 * @Signature: stringWithContentsOfFile:encoding:error:
	 * @Declaration : + (instancetype)stringWithContentsOfFile:(NSString *)path encoding:(NSStringEncoding)enc error:(NSError **)error
	 * @Description : Returns a string created by reading data from the file at a given path interpreted using a given encoding.
	 * @param path A path to a file.
	 * @param enc The encoding of the file at path.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, pass in NULL.
	 * @return Return Value A string created by reading data from the file named by path using the encoding, enc. If the file canâ€™t be
	 *         opened or there is an encoding error, returns nil.
	 **/

	public static NSString stringWithContentsOfFileEncodingError(NSString path, int enc,
																 NSError[] error) {
		// TODO use Encoding
		if (AndroidVersionUtils.isInteger(path.getWrappedString())) {
			if (Integer.parseInt(path.getWrappedString()) == 0) {
				return null;
			}
			int resId = Integer.parseInt(path.getWrappedString());
			InputStream is = GenericMainContext.sharedContext.getResources().openRawResource(resId);
			String mystring = null;
			try {
				mystring = AndroidIOUtils.toString(is);
				AndroidIOUtils.closeQuietly(is);
				return new NSString(mystring);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
				return null;
			}
		} else if (path.getWrappedString().contains("assets ")) {
			try {
				String realPath = path.getWrappedString().substring("assets ".length(),
						path.getWrappedString().length());
				InputStream is = GenericMainContext.sharedContext.getAssets().open(realPath);
				String mystring = AndroidRessourcesUtils.getStringFromInputStream(is);
				return new NSString(mystring);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
				return null;
			}
		} else {

			NSString nsString = new NSString();
			// get default system encoding
			String encoding = Charset.defaultCharset().name();
			if (enc != 0)
				encoding = NSStringEncoding.getCharsetFromInt(enc).name();
			try {
				File file = new File(path.getWrappedString());
				String encodedString = AndroidFileUtils.readFileToString(file, encoding);
				nsString.setWrappedString(encodedString);
				return nsString;
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
			}
			return null;
		}
	}

	public static Object stringWithContentsOfFileEncodingError(Class clazz, NSString path, int enc,
															   NSError[] error) {
		NSString str = stringWithContentsOfFileEncodingError(path, enc, error);
		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: initWithContentsOfFile:encoding:error:
	 * @Declaration : - (instancetype)initWithContentsOfFile:(NSString *)path encoding:(NSStringEncoding)enc error:(NSError **)error
	 * @Description : Returns an NSString object initialized by reading data from the file at a given path using a given encoding.
	 * @param path A path to a file.
	 * @param enc The encoding of the file at path.
	 * @param error If an error occurs, upon return contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, pass in NULL.
	 * @return Return Value An NSString object initialized by reading data from the file named by path using the encoding, enc. The returned
	 *         object may be different from the original receiver. If the file canâ€™t be opened or there is an encoding error, returns nil.
	 **/

	public NSString initWithContentsOfFileEncodingError(NSString path, int enc, NSError[] error) {
		NSString nsString = new NSString();
		nsString = stringWithContentsOfFileEncodingError(path, enc, error);
		return nsString;
	}

	public Object initWithContentsOfFileEncodingError(Class clazz, NSString path, int enc,
													  NSError[] error) {
		NSString nsString = new NSString();
		nsString = stringWithContentsOfFileEncodingError(path, enc, error);
		return InstanceTypeFactory.getInstance(nsString, NSString.class, clazz,
				new NSString("setWrappedString"), nsString.getWrappedString());
	}

	/**
	 * @Signature: stringWithContentsOfFile:usedEncoding:error:
	 * @Declaration : + (instancetype)stringWithContentsOfFile:(NSString *)path usedEncoding:(NSStringEncoding *)enc error:(NSError **)error
	 * @Description : Returns a string created by reading data from the file at a given path and returns by reference the encoding used to
	 *              interpret the file.
	 * @param path A path to a file.
	 * @param enc Upon return, if the file is read successfully, contains the encoding used to interpret the file at path.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, you may pass in NULL.
	 * @return Return Value A string created by reading data from the file named by path. If the file canâ€™t be opened or there is an
	 *         encoding error, returns nil.
	 * @Discussion This method attempts to determine the encoding of the file at path.
	 **/

	public static NSString stringWithContentsOfFileUsedEncodingError(NSString path, int[] enc,
																	 NSError[] error) {
		// TODO
		int encBridge = 0;
		if (error != null && error.length > 0) {
			encBridge = enc[0];
		}
		return stringWithContentsOfFileEncodingError(path, encBridge, error);
	}

	public static Object stringWithContentsOfFileUsedEncodingError(Class clazz, NSString path,
																   int[] enc, NSError[] error) {
		// TODO
		NSString str = stringWithContentsOfFileUsedEncodingError(path, enc, error);
		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: initWithContentsOfFile:usedEncoding:error:
	 * @Declaration : - (instancetype)initWithContentsOfFile:(NSString *)path usedEncoding:(NSStringEncoding *)enc error:(NSError **)error
	 * @Description : Returns an NSString object initialized by reading data from the file at a given path and returns by reference the
	 *              encoding used to interpret the characters.
	 * @param path A path to a file.
	 * @param enc Upon return, if the file is read successfully, contains the encoding used to interpret the file at path.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, pass in NULL.
	 * @return Return Value An NSString object initialized by reading data from the file named by path. The returned object may be different
	 *         from the original receiver. If the file canâ€™t be opened or there is an encoding error, returns nil.
	 **/

	public NSString initWithContentsOfFileUsedEncodingError(NSString path, int[] enc,
															NSError[] error) {
		int encodBridge = 0;
		if (enc != null && enc.length > 0) {
			encodBridge = enc[0];
		}
		return initWithContentsOfFileEncodingError(path, encodBridge, error);
	}

	/**
	 * @Signature: stringWithContentsOfFile:
	 * @Declaration : + (id)stringWithContentsOfFile:(NSString *)path
	 * @Description : Returns a string created by reading data from the file named by a given path. (Deprecated in iOS 2.0. Use
	 *              stringWithContentsOfFile:encoding:error: or stringWithContentsOfFile:usedEncoding:error: instead.)
	 * @Discussion If the contents begin with a Unicode byte-order mark (U+FEFF or U+FFFE), interprets the contents as Unicode characters.
	 *             If the contents begin with a UTF-8 byte-order mark (EFBBBF), interprets the contents as UTF-8. Otherwise, interprets the
	 *             contents as data in the default C string encoding. Since the default C string encoding will vary with the userâ€™s
	 *             configuration, do not depend on this method unless you are using Unicode or UTF-8 or you can verify the default C string
	 *             encoding. Returns nil if the file canâ€™t be opened.
	 **/


	public static NSString stringWithContentsOfFile(NSString path) {
		// TODO
		if (AndroidVersionUtils.isInteger(path.getWrappedString())) {
			if (Integer.parseInt(path.getWrappedString()) == 0) {
				return null;
			}
			int resId = Integer.parseInt(path.getWrappedString());
			InputStream is = GenericMainContext.sharedContext.getResources().openRawResource(resId);
			String mystring = null;
			mystring = AndroidRessourcesUtils.getStringFromInputStream(is);
			return new NSString(mystring);

		} else if (path.getWrappedString().contains("assets ")) {
			try {
				String realPath = path.getWrappedString().substring("assets ".length(),
						path.getWrappedString().length());
				InputStream is = GenericMainContext.sharedContext.getAssets().open(realPath);
				String mystring = AndroidRessourcesUtils.getStringFromInputStream(is);
				return new NSString(mystring);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				return null;
			}
		} else
			return stringWithContentsOfFileEncodingError(path, 0, null);
	}

	/**
	 * @Signature: initWithContentsOfFile:
	 * @Declaration : - (id)initWithContentsOfFile:(NSString *)path
	 * @Description : Initializes the receiver, a newly allocated NSString object, by reading data from the file named by path. (Deprecated
	 *              in iOS 2.0. Use initWithContentsOfFile:encoding:error: or initWithContentsOfFile:usedEncoding:error: instead.)
	 * @Discussion Initializes the receiver, a newly allocated NSString object, by reading data from the file named by path. If the contents
	 *             begin with a byte-order mark (U+FEFF or U+FFFE), interprets the contents as Unicode characters; otherwise interprets the
	 *             contents as data in the default C string encoding. Returns an initialized object, which might be different from the
	 *             original receiver, or nil if the file canâ€™t be opened.
	 **/


	public NSString initWithContentsOfFile(NSString path) {
		return initWithContentsOfFileEncodingError(path, 0, null);
	}

	// Creating and Initializing a String from an URL

	/**
	 * @Signature: stringWithContentsOfURL:encoding:error:
	 * @Declaration : + (instancetype)stringWithContentsOfURL:(NSURL *)url encoding:(NSStringEncoding)enc error:(NSError **)error
	 * @Description : Returns a string created by reading data from a given URL interpreted using a given encoding.
	 * @param url The URL to read.
	 * @param enc The encoding of the data at url.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, you may pass in NULL.
	 * @return Return Value A string created by reading data from URL using the encoding, enc. If the URL canâ€™t be opened or there is an
	 *         encoding error, returns nil.
	 **/


	public static NSString stringWithContentsOfURLEncodingError(NSURL url, int enc,
																NSError[] error) {
		String encoding = Charset.defaultCharset().name();
		NSString nsString = null;

		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.getUrl().openStream()));
			StringBuilder strBuilder = new StringBuilder();
			String str;
			while ((str = in.readLine()) != null) {
				strBuilder.append(str);
			}
			in.close();
			nsString = new NSString();
			nsString.wrappedString = new String(strBuilder.toString().getBytes(), encoding);

		} catch (MalformedURLException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(
					NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(
					NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
		}

		return nsString;
	}

	public static Object stringWithContentsOfURLEncodingError(Class clazz, final NSURL url,
															  final int enc, final NSError[] error) {

		AsyncTask task = new AsyncTask<NSString, Void, NSString>() {

			@Override
			protected NSString doInBackground(NSString... param) {
				NSString str = stringWithContentsOfURLEncodingError(url, enc, error);
				return str;
			}

			@Override
			protected void onPostExecute(NSString str) {
				super.onPostExecute(str);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
		};

		NSString str = new NSString("");
		try {
			str = (NSString) task.execute(new NSString[] {}).get();
		} catch (Exception ex) {
			LOGGER.info(String.valueOf(ex));
		}

		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: initWithContentsOfURL:encoding:error:
	 * @Declaration : - (instancetype)initWithContentsOfURL:(NSURL *)url encoding:(NSStringEncoding)enc error:(NSError **)error
	 * @Description : Returns an NSString object initialized by reading data from a given URL interpreted using a given encoding.
	 * @param url The URL to read.
	 * @param enc The encoding of the file at path.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, pass in NULL.
	 * @return Return Value An NSString object initialized by reading data from url. The returned object may be different from the original
	 *         receiver. If the URL canâ€™t be opened or there is an encoding error, returns nil.
	 **/

	public NSString initWithContentsOfURLEncodingError(NSURL url, int encoding, NSError[] error) {
		return stringWithContentsOfURLEncodingError(url, encoding, error);
	}

	public Object initWithContentsOfURLEncodingError(Class clazz, NSURL url, int encoding,
													 NSError[] error) {
		NSString str = stringWithContentsOfURLEncodingError(url, encoding, error);
		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: stringWithContentsOfURL:usedEncoding:error:
	 * @Declaration : + (instancetype)stringWithContentsOfURL:(NSURL *)url usedEncoding:(NSStringEncoding *)enc error:(NSError **)error
	 * @Description : Returns a string created by reading data from a given URL and returns by reference the encoding used to interpret the
	 *              data.
	 * @param url The URL from which to read data.
	 * @param enc Upon return, if url is read successfully, contains the encoding used to interpret the data.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, you may pass in NULL.
	 * @return Return Value A string created by reading data from url. If the URL canâ€™t be opened or there is an encoding error, returns
	 *         nil.
	 * @Discussion This method attempts to determine the encoding at url.
	 **/

	public static NSString stringWithContentsOfURLUsedEncodingError(NSURL url, int[] usedEncoding,
																	NSError[] error) {
		return new NSString().initWithContentsOfURLUsedEncodingError(url, usedEncoding, error);
	}

	public static Object stringWithContentsOfURLUsedEncodingError(Class clazz, NSURL url,
																  int[] usedEncoding, NSError[] error) {
		NSString str = new NSString().initWithContentsOfURLUsedEncodingError(url, usedEncoding,
				error);
		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: initWithContentsOfURL:usedEncoding:error:
	 * @Declaration : - (instancetype)initWithContentsOfURL:(NSURL *)url usedEncoding:(NSStringEncoding *)enc error:(NSError **)error
	 * @Description : Returns an NSString object initialized by reading data from a given URL and returns by reference the encoding used to
	 *              interpret the data.
	 * @param url The URL from which to read data.
	 * @param enc Upon return, if url is read successfully, contains the encoding used to interpret the data.
	 * @param error If an error occurs, upon returns contains an NSError object that describes the problem. If you are not interested in
	 *            possible errors, pass in NULL.
	 * @return Return Value An NSString object initialized by reading data from url. If url canâ€™t be opened or the encoding cannot be
	 *         determined, returns nil. The returned initialized object might be different from the original receiver
	 * @Discussion To read data with an unknown encoding, pass 0 as the enc parameter as in: NSURL *textFileURL = â€¦; NSStringEncoding
	 *             encoding = 0; NSError *error = nil; NSString *string = [[NSString alloc] initWithContentsOfURL:textFileURL
	 *             usedEncoding:&encoding error:&error];
	 **/

	public NSString initWithContentsOfURLUsedEncodingError(NSURL url, int[] enc, NSError[] error) {
		int bridgeEncoding = 0;
		if (enc != null && enc.length > 0) {
			bridgeEncoding = enc[0];
		} else {
			bridgeEncoding = 2;// default
		}

		return initWithContentsOfURLEncodingError(url, bridgeEncoding, error);
	}

	public Object initWithContentsOfURLUsedEncodingError(Class clazz, NSURL url, int[] enc,
														 NSError[] error) {
		NSString str = initWithContentsOfURLUsedEncodingError(url, enc, error);
		return InstanceTypeFactory.getInstance(str, NSString.class, clazz,
				new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * @Signature: stringWithContentsOfURL:
	 * @Declaration : + (id)stringWithContentsOfURL:(NSURL *)aURL
	 * @Description : Returns a string created by reading data from the file named by a given URL. (Deprecated in iOS 2.0. Use
	 *              stringWithContentsOfURL:encoding:error: or stringWithContentsOfURL:usedEncoding:error: instead.)
	 * @Discussion If the contents begin with a byte-order mark (U+FEFF or U+FFFE), interprets the contents as Unicode characters. If the
	 *             contents begin with a UTF-8 byte-order mark (EFBBBF), interprets the contents as UTF-8. Otherwise interprets the contents
	 *             as data in the default C string encoding. Since the default C string encoding will vary with the userâ€™s configuration, do
	 *             not depend on this method unless you are using Unicode or UTF-8 or you can verify the default C string encoding. Returns
	 *             nil if the location canâ€™t be opened.
	 **/


	public NSString stringWithContentsOfURL(NSURL aURL) {
		return stringWithContentsOfURLUsedEncodingError(aURL, null, null);
	}

	/**
	 * @Signature: initWithContentsOfURL:
	 * @Declaration : - (id)initWithContentsOfURL:(NSURL *)aURL
	 * @Description : Initializes the receiver, a newly allocated NSString object, by reading data from the location named by a given URL.
	 *              (Deprecated in iOS 2.0. Use initWithContentsOfURL:encoding:error: or initWithContentsOfURL:usedEncoding:error: instead.)
	 * @Discussion Initializes the receiver, a newly allocated NSString object, by reading data from the location named by aURL. If the
	 *             contents begin with a byte-order mark (U+FEFF or U+FFFE), interprets the contents as Unicode characters; otherwise
	 *             interprets the contents as data in the default C string encoding. Returns an initialized object, which might be different
	 *             from the original receiver, or nil if the location canâ€™t be opened.
	 **/


	public NSString initWithContentsOfURL(NSURL aURL) {
		return initWithContentsOfURLUsedEncodingError(aURL, null, null);
	}

	/**
	 * @Signature: writeToFile:atomically:encoding:error:
	 * @Declaration : - (BOOL)writeToFile:(NSString *)path atomically:(BOOL)useAuxiliaryFile encoding:(NSStringEncoding)enc error:(NSError
	 *              **)error
	 * @Description : Writes the contents of the receiver to a file at a given path using a given encoding.
	 * @param path The file to which to write the receiver. If path contains a tilde (~) character, you must expand it with
	 *            stringByExpandingTildeInPath before invoking this method.
	 * @param useAuxiliaryFile If YES, the receiver is written to an auxiliary file, and then the auxiliary file is renamed to path. If NO,
	 *            the receiver is written directly to path. The YES option guarantees that path, if it exists at all, wonâ€™t be corrupted
	 *            even if the system should crash during writing.
	 * @param enc The encoding to use for the output.
	 * @param error If there is an error, upon return contains an NSError object that describes the problem. If you are not interested in
	 *            details of errors, you may pass in NULL.
	 * @return Return Value YES if the file is written successfully, otherwise NO (if there was a problem writing to the file or with the
	 *         encoding).
	 * @Discussion This method overwrites any existing file at path. This method stores the specified encoding with the file in an extended
	 *             attribute under the name com.apple.TextEncoding. The value contains the IANA name for the encoding and the
	 *             CFStringEncoding value for the encoding, separated by a semicolon. The CFStringEncoding value is written as an ASCII
	 *             string containing an unsigned 32-bit decimal integer and is not terminated by a null character. One or both of these
	 *             values may be missing.
	 **/

	public boolean writeToFileAtomicallyEncodingError(NSString path, boolean useAuxiliaryFile,
													  int enc, NSError[] error) {
		String encoding = Charset.defaultCharset().name();
		if (enc != 0)
			encoding = NSStringEncoding.getCharsetFromInt(enc).name();
		File file = new File(path.getWrappedString());
		if (file.isFile() && file.canWrite()) {
			FileOutputStream fileos;
			try {
				fileos = new FileOutputStream(path.getWrappedString());
				AndroidIOUtils.write(wrappedString, fileos, encoding);

				return true;
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(
						NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
						null);
			}
		}
		return false;
	}

	/**
	 * @Signature: writeToURL:atomically:encoding:error:
	 * @Declaration : - (BOOL)writeToURL:(NSURL *)url atomically:(BOOL)useAuxiliaryFile encoding:(NSStringEncoding)enc error:(NSError
	 *              **)error
	 * @Description : Writes the contents of the receiver to the URL specified by url using the specified encoding.
	 * @param url The URL to which to write the receiver. Only file URLs are supported.
	 * @param useAuxiliaryFile If YES, the receiver is written to an auxiliary file, and then the auxiliary file is renamed to url. If NO,
	 *            the receiver is written directly to url. The YES option guarantees that url, if it exists at all, wonâ€™t be corrupted even
	 *            if the system should crash during writing. The useAuxiliaryFile parameter is ignored if url is not of a type that can be
	 *            accessed atomically.
	 * @param enc The encoding to use for the output.
	 * @param error If there is an error, upon return contains an NSError object that describes the problem. If you are not interested in
	 *            details of errors, you may pass in NULL.
	 * @return Return Value YES if the URL is written successfully, otherwise NO (if there was a problem writing to the URL or with the
	 *         encoding).
	 * @Discussion This method stores the specified encoding with the file in an extended attribute under the name com.apple.TextEncoding.
	 *             The value contains the IANA name for the encoding and the CFStringEncoding value for the encoding, separated by a
	 *             semicolon. The CFStringEncoding value is written as an ASCII string containing an unsigned 32-bit decimal integer and is
	 *             not terminated by a null character. One or both of these values may be missing. Examples of the value written include the
	 *             following: MACINTOSH;0 UTF-8;134217984 UTF-8; ;3071 The methods initWithContentsOfFile:usedEncoding:error:,
	 *             initWithContentsOfURL:usedEncoding:error:, stringWithContentsOfFile:usedEncoding:error:, and
	 *             stringWithContentsOfURL:usedEncoding:error: use this information to open the file using the right encoding. Note:Â In the
	 *             future this attribute may be extended compatibly by adding additional information after what's there now, so any readers
	 *             should be prepared for an arbitrarily long value for this attribute, with stuff following the CFStringEncoding value,
	 *             separated by a non-digit.
	 **/

	public boolean writeToURLAtomicallyEncodingError(NSURL url, boolean useAuxiliaryFile, int enc,
													 NSError[] error) {
		String encoding = Charset.defaultCharset().name();
		if (enc != 0)
			encoding = NSStringEncoding.getCharsetFromInt(enc).name();
		FileOutputStream fileos;
		try {
			fileos = new FileOutputStream(url.path().wrappedString);
			AndroidIOUtils.write(wrappedString, fileos, encoding);
			return true;
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(
					NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(
					NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
		}
		return false;
	}

	/**
	 * @Signature: writeToFile:atomically:
	 * @Declaration : - (BOOL)writeToFile:(NSString *)path atomically:(BOOL)flag
	 * @Description : Writes the contents of the receiver to the file specified by a given path. (Deprecated in iOS 2.0. Use
	 *              writeToFile:atomically:encoding:error: instead.)
	 * @return Return Value YES if the file is written successfully, otherwise NO.
	 * @Discussion Writes the contents of the receiver to the file specified by path (overwriting any existing file at path). path is
	 *             written in the default C-string encoding if possible (that is, if no information would be lost), in the Unicode encoding
	 *             otherwise. If flag is YES, the receiver is written to an auxiliary file, and then the auxiliary file is renamed to path.
	 *             If flag is NO, the receiver is written directly to path. The YES option guarantees that path, if it exists at all, wonâ€™t
	 *             be corrupted even if the system should crash during writing. If path contains a tilde (~) character, you must expand it
	 *             with stringByExpandingTildeInPath before invoking this method.
	 **/


	public void writeToFileAtomically(NSString path, boolean atomically) {
		writeToFileAtomicallyEncodingError(path, atomically, 0, null);
	}

	/**
	 * @Signature: writeToURL:atomically:
	 * @Declaration : - (BOOL)writeToURL:(NSURL *)aURL atomically:(BOOL)atomically
	 * @Description : Writes the contents of the receiver to the location specified by a given URL. (Deprecated in iOS 2.0. Use
	 *              writeToURL:atomically:encoding:error: instead.)
	 * @return Return Value YES if the location is written successfully, otherwise NO.
	 * @Discussion If atomically is YES, the receiver is written to an auxiliary location, and then the auxiliary location is renamed to
	 *             aURL. If atomically is NO, the receiver is written directly to aURL. The YES option guarantees that aURL, if it exists at
	 *             all, wonâ€™t be corrupted even if the system should crash during writing. The atomically parameter is ignored if aURL is
	 *             not of a type that can be accessed atomically.
	 **/


	public void writeToURLAtomically(NSURL url, boolean atomically) {
		writeToURLAtomicallyEncodingError(url, atomically, 0, null);
	}

	// Getting Characters and Bytes
	/**
	 * @Signature: characterAtIndex:
	 * @Declaration : - (unichar)characterAtIndex:(NSUInteger)index
	 * @Description : Returns the character at a given array position.
	 * @param index The index of the character to retrieve. The index value must not lie outside the bounds of the receiver.
	 * @return Return Value The character at the array position given by index.
	 * @Discussion Raises an NSRangeException if index lies beyond the end of the receiver.
	 **/

	public char characterAtIndex(int index) {
		return wrappedString.charAt(index);
	}

	/**
	 * @Signature: getCharacters:range:
	 * @Declaration : - (void)getCharacters:(unichar *)buffer range:(NSRange)aRange
	 * @Description : Copies characters from a given range in the receiver into a given buffer.
	 * @param buffer Upon return, contains the characters from the receiver. buffer must be large enough to contain the characters in the
	 *            range aRange (aRange.length*sizeof(unichar)).
	 * @param aRange The range of characters to retrieve. The range must not exceed the bounds of the receiver. Important:Â Raises an
	 *            NSRangeException if any part of aRange lies beyond the bounds of the receiver.
	 * @Discussion This method does not add a NULL character. The abstract implementation of this method uses characterAtIndex: repeatedly,
	 *             correctly extracting the characters, though very inefficiently. Subclasses should override it to provide a fast
	 *             implementation.
	 **/

	public void getCharactersRange(CharBuffer[] buffer, NSRange aRange) {
		if (buffer != null && buffer.length > 0) {
			buffer[0] = CharBuffer.allocate(aRange.length);
			buffer[0].append(wrappedString.subSequence(aRange.location, aRange.length));
		}

	}

	/**
	 * @Signature: getBytes:maxLength:usedLength:encoding:options:range:remainingRange:
	 * @Declaration : - (BOOL)getBytes:(void *)buffer maxLength:(NSUInteger)maxBufferCount usedLength:(NSUInteger *)usedBufferCount
	 *              encoding:(NSStringEncoding)encoding options:(NSStringEncodingConversionOptions)options range:(NSRange)range
	 *              remainingRange:(NSRangePointer)leftover
	 * @Description : Gets a given range of characters as bytes in a specified encoding.
	 * @param buffer A buffer into which to store the bytes from the receiver. The returned bytes are not NULL-terminated.
	 * @param maxBufferCount The maximum number of bytes to write to buffer.
	 * @param usedBufferCount The number of bytes used from buffer. Pass NULL if you do not need this value.
	 * @param encoding The encoding to use for the returned bytes.
	 * @param options A mask to specify options to use for converting the receiverâ€™s contents to encoding (if conversion is necessary).
	 * @param range The range of characters in the receiver to get.
	 * @param leftover The remaining range. Pass NULL If you do not need this value.
	 * @return Return Value YES if some characters were converted, otherwise NO.
	 * @Discussion Conversion might stop when the buffer fills, but it might also stop when the conversion isn't possible due to the chosen
	 *             encoding.
	 **/

	public boolean getBytesMaxLengthUsedLengthEncodingOptionsRangeRemainingRange(ByteBuffer buffer,
																				 int maxBufferCount, int usedBufferCount, int enc,
																				 NSStringEncodingConversionOptions options, NSRange range, NSRangePointer leftover) {
		String encoding = Charset.defaultCharset().name();
		if (enc != 0)
			encoding = NSStringEncoding.getCharsetFromInt(enc).name();
		buffer = (maxBufferCount == 0) ? ByteBuffer.allocate(wrappedString.length())
				: ByteBuffer.allocate(maxBufferCount);
		try {
			byte[] result = new String(
					wrappedString.substring(range.location, range.length).getBytes(), encoding)
					.getBytes();
			buffer.put(result);
			return true;
		} catch (UnsupportedEncodingException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;
	}

	/**
	 * @Signature: getCharacters:
	 * @Declaration : - (void)getCharacters:(unichar *)buffer
	 * @Description : Copies all characters from the receiver into a given buffer. (Deprecated in iOS 4.0. This method is unsafe because it
	 *              could potentially cause buffer overruns. Use getCharacters:range: instead.)
	 * @param buffer Upon return, contains the characters from the receiver. buffer must be large enough to contain all characters in the
	 *            string ([string length]*sizeof(unichar)).
	 * @Discussion Invokes getCharacters:range: with buffer and the entire extent of the receiver as the range.
	 **/


	public void getCharacters(char[] buffer) {
		buffer = wrappedString.toCharArray();
	}

	// Combining Strings
	/**
	 * @Signature: stringByAppendingFormat:
	 * @Declaration : - (NSString *)stringByAppendingFormat:(NSString *)format, ...
	 * @Description : Returns a string made by appending to the receiver a string constructed from a given format string and the following
	 *              arguments.
	 * @param format A format string. See â€œFormatting String Objectsâ€? for more information. This value must not be nil. Important:Â Raises an
	 *            NSInvalidArgumentException if format is nil.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @return Return Value A string made by appending to the receiver a string constructed from format and the following arguments, in the
	 *         manner of stringWithFormat:.
	 **/

	public NSString stringByAppendingFormat(NSString format, Object... args) {
		StringBuilder strBuilder = new StringBuilder(wrappedString);
		if (format == null)
			throw new IllegalArgumentException(" This value must not be null");
		strBuilder.append(String.format(format.getWrappedString(), args));
		this.setWrappedString(strBuilder.toString());
		return this;
	}

	/**
	 * @Signature: stringByAppendingString:
	 * @Declaration : - (NSString *)stringByAppendingString:(NSString *)aString
	 * @Description : Returns a new string made by appending a given string to the receiver.
	 * @param aString The string to append to the receiver. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if
	 *            aString is nil.
	 * @return Return Value A new string made by appending aString to the receiver.
	 * @Discussion This code excerpt, for example: NSString *errorTag = @"Error: "; NSString *errorString = @"premature end of file.";
	 *             NSString *errorMessage = [errorTag stringByAppendingString:errorString]; produces the string â€œError: premature end of
	 *             file.â€?.
	 **/

	public NSString stringByAppendingString(NSString mString) {
		StringBuilder strBuilder = new StringBuilder(this.wrappedString);
		if (strBuilder == null)
			throw new IllegalArgumentException(" This value must not be null ");
		return new NSString(strBuilder.append(mString.getWrappedString()).toString());
	}

	/**
	 * @Signature: stringByPaddingToLength:withString:startingAtIndex:
	 * @Declaration : - (NSString *)stringByPaddingToLength:(NSUInteger)newLength withString:(NSString *)padString
	 *              startingAtIndex:(NSUInteger)padIndex
	 * @Description : Returns a new string formed from the receiver by either removing characters from the end, or by appending as many
	 *              occurrences as necessary of a given pad string.
	 * @param newLength The new length for the receiver.
	 * @param padString The string with which to extend the receiver.
	 * @param padIndex The index in padString from which to start padding.
	 * @return Return Value A new string formed from the receiver by either removing characters from the end, or by appending as many
	 *         occurrences of padString as necessary.
	 * @Discussion Here are some examples of usage: [@"abc" stringByPaddingToLength: 9 withString: @"." startingAtIndex:0]; // Results in
	 *             "abc......" [@"abc" stringByPaddingToLength: 2 withString: @"." startingAtIndex:0]; // Results in "ab" [@"abc"
	 *             stringByPaddingToLength: 9 withString: @". " startingAtIndex:1]; // Results in "abc . . ." // Notice that the first
	 *             character in the padding is " "
	 **/

	public NSString stringByPaddingToLengthWithStringStartingAtIndex(int newLength,
																	 NSString padString, int padIndex) {
		return new NSString(
				(wrappedString.length() < newLength ? wrappedString + padString : wrappedString)
						.substring(0, newLength));
	}

	// Finding Characters and Substrings

	/**
	 * @Signature: rangeOfCharacterFromSet:
	 * @Declaration : - (NSRange)rangeOfCharacterFromSet:(NSCharacterSet *)aSet
	 * @Description : Finds and returns the range in the receiver of the first character from a given character set.
	 * @param aSet A character set. This value must not be nil. Raises an NSInvalidArgumentException if aSet is nil.
	 * @return Return Value The range in the receiver of the first character found from aSet. Returns a range of {NSNotFound, 0} if none of
	 *         the characters in aSet are found.
	 * @Discussion Invokes rangeOfCharacterFromSet:options: with no options.
	 **/

	public NSRange rangeOfCharacterFromSet(NSCharacterSet aSet) {
		return rangeOfCharacterFromSetOptionsRange(aSet,
				NSStringCompareOptions.NSCaseInsensitiveSearch, null);
	}

	/**
	 * @Signature: rangeOfCharacterFromSet:options:
	 * @Declaration : - (NSRange)rangeOfCharacterFromSet:(NSCharacterSet *)aSet options:(NSStringCompareOptions)mask
	 * @Description : Finds and returns the range in the receiver of the first character, using given options, from a given character set.
	 * @param aSet A character set. This value must not be nil. Raises an NSInvalidArgumentException if aSet is nil.
	 * @param mask A mask specifying search options. The following options may be specified by combining them with the C bitwise OR
	 *            operator: NSAnchoredSearch, NSBackwardsSearch.
	 * @return Return Value The range in the receiver of the first character found from aSet. Returns a range of {NSNotFound, 0} if none of
	 *         the characters in aSet are found.
	 * @Discussion Invokes rangeOfCharacterFromSet:options:range: with mask for the options and the entire extent of the receiver for the
	 *             range.
	 **/

	public NSRange rangeOfCharacterFromSetOptions(NSCharacterSet aSet,
												  NSStringCompareOptions mask) {
		return rangeOfCharacterFromSetOptionsRange(aSet, mask, null);
	}

	/**
	 * @Signature: rangeOfCharacterFromSet:options:range:
	 * @Declaration : - (NSRange)rangeOfCharacterFromSet:(NSCharacterSet *)aSet options:(NSStringCompareOptions)mask range:(NSRange)aRange
	 * @Description : Finds and returns the range in the receiver of the first character from a given character set found in a given range
	 *              with given options.
	 * @param aSet A character set. This value must not be nil. Raises an NSInvalidArgumentException if aSet is nil.
	 * @param mask A mask specifying search options. The following options may be specified by combining them with the C bitwise OR
	 *            operator: NSAnchoredSearch, NSBackwardsSearch.
	 * @param aRange The range in which to search. aRange must not exceed the bounds of the receiver. Raises an NSRangeException if aRange
	 *            is invalid.
	 * @return Return Value The range in the receiver of the first character found from aSet within aRange. Returns a range of {NSNotFound,
	 *         0} if none of the characters in aSet are found.
	 * @Discussion Because pre-composed characters in aSet can match composed character sequences in the receiver, the length of the
	 *             returned range can be greater than 1. For example, if you search for â€œÃ¼â€? in the string â€œstruÂ¨delâ€?, the returned range is
	 *             {3,2}.
	 **/

	public NSRange rangeOfCharacterFromSetOptionsRange(NSCharacterSet aSet,
													   NSStringCompareOptions mask, NSRange aRange) {
		if (aSet == null)
			throw new IllegalArgumentException(" This value must not be null ");

		NSRange nsRange = new NSRange(0, 0);

		int indexOfMatch = -1;
		if (aSet.getCharacterSet() == null || wrappedString.isEmpty()
				|| aSet.getCharacterSet().isEmpty()) {
			return nsRange;
		}
		// check invertedSet if set
		if (aSet.getInvertedSet() != null) {
			while (aSet.getInvertedSet().iterator().hasNext()) {
				if ((indexOfMatch = wrappedString
						.indexOf(aSet.getInvertedSet().iterator().next())) == -1) {
					nsRange.location = NSObjCRuntime.NSNotFound;
					return nsRange;
				}
			}
		}
		while (aSet.getCharacterSet().iterator().hasNext()) {
			if ((indexOfMatch = wrappedString
					.indexOf(aSet.getCharacterSet().iterator().next())) != -1) {
				nsRange.location = indexOfMatch;
				return nsRange;
			}
		}
		return nsRange;
	}

	/**
	 * @Signature: rangeOfString:
	 * @Declaration : - (NSRange)rangeOfString:(NSString *)aString
	 * @Description : Finds and returns the range of the first occurrence of a given string within the receiver.
	 * @param aString The string to search for. This value must not be nil. Raises an NSInvalidArgumentException if aString is nil.
	 * @return Return Value An NSRange structure giving the location and length in the receiver of the first occurrence of aString. Returns
	 *         {NSNotFound, 0} if aString is not found or is empty (@"").
	 * @Discussion Invokes rangeOfString:options: with no options.
	 **/

	public NSRange rangeOfString(NSString mString) {
		NSRange range = new NSRange();
		if (!this.getWrappedString().contains(mString.getWrappedString()))
			range.location = NSObjCRuntime.NSNotFound;
		else {
			NSRange searchRange = NSRange.NSMakeRange(0, wrappedString.length());
			range = rangeOfStringOptionsRangeLocale(mString,
					NSStringCompareOptions.NSCaseInsensitiveSearch, searchRange, null);
		}
		return range;
	}

	/**
	 * @Signature: rangeOfString:options:
	 * @Declaration : - (NSRange)rangeOfString:(NSString *)aString options:(NSStringCompareOptions)mask
	 * @Description : Finds and returns the range of the first occurrence of a given string within the receiver, subject to given options.
	 * @param aString The string to search for. This value must not be nil. Important:Â Raises an NSInvalidArgumentException if aString is
	 *            nil.
	 * @param mask A mask specifying search options. The following options may be specified by combining them with the C bitwise OR
	 *            operator: NSCaseInsensitiveSearch, NSLiteralSearch, NSBackwardsSearch, NSAnchoredSearch. See String Programming Guide for
	 *            details on these options.
	 * @return Return Value An NSRange structure giving the location and length in the receiver of the first occurrence of aString, modulo
	 *         the options in mask. Returns {NSNotFound, 0} if aString is not found or is empty (@"").
	 * @Discussion Invokes rangeOfString:options:range: with the options specified by mask and the entire extent of the receiver as the
	 *             range.
	 **/

	public NSRange rangeOfStringOptions(NSString mString, NSStringCompareOptions mask) {
		return rangeOfStringOptionsRangeLocale(mString, mask, null, null);
	}

	/**
	 * @Signature: rangeOfString:options:range:
	 * @Declaration : - (NSRange)rangeOfString:(NSString *)aString options:(NSStringCompareOptions)mask range:(NSRange)aRange
	 * @Description : Finds and returns the range of the first occurrence of a given string, within the given range of the receiver, subject
	 *              to given options.
	 * @param aString The string for which to search. This value must not be nil. Raises an NSInvalidArgumentException if aString is nil.
	 * @param mask A mask specifying search options. The following options may be specified by combining them with the C bitwise OR
	 *            operator: NSCaseInsensitiveSearch, NSLiteralSearch, NSBackwardsSearch, and NSAnchoredSearch. See String Programming Guide
	 *            for details on these options.
	 * @param aRange The range within the receiver for which to search for aString. Raises an NSRangeException if aRange is invalid.
	 * @return Return Value An NSRange structure giving the location and length in the receiver of aString within aRange in the receiver,
	 *         modulo the options in mask. The range returned is relative to the start of the string, not to the passed-in range. Returns
	 *         {NSNotFound, 0} if aString is not found or is empty (@"").
	 * @Discussion The length of the returned range and that of aString may differ if equivalent composed character sequences are matched.
	 **/

	public NSRange rangeOfStringOptionsRange(NSCharacterSet aSet, NSStringCompareOptions mask,
											 NSRange aRange) {
		// TODO
		NSRange nsRange = new NSRange(0, 0);
		final Pattern pattern = Pattern.compile("[" + Pattern.quote(wrappedString) + "]");
		final Matcher matcher = pattern.matcher(wrappedString);
		return nsRange;
	}

	/**
	 * @Signature: rangeOfString:options:range:locale:
	 * @Declaration : - (NSRange)rangeOfString:(NSString *)aString options:(NSStringCompareOptions)mask range:(NSRange)searchRange
	 *              locale:(NSLocale *)locale
	 * @Description : Finds and returns the range of the first occurrence of a given string within a given range of the receiver, subject to
	 *              given options, using the specified locale, if any.
	 * @param aString The string for which to search. This value must not be nil. Raises an NSInvalidArgumentException if aString is nil.
	 * @param mask A mask specifying search options. The following options may be specified by combining them with the C bitwise OR
	 *            operator: NSCaseInsensitiveSearch, NSLiteralSearch, NSBackwardsSearch, and NSAnchoredSearch. See String Programming Guide
	 *            for details on these options.
	 * @param aRange The range within the receiver for which to search for aString. Raises an NSRangeException if aRange is invalid.
	 * @param locale The locale to use when comparing the receiver with aString. To use the current locale, pass [NSLocalecurrentLocale]. To
	 *            use the system locale, pass nil. The locale argument affects the equality checking algorithm. For example, for the Turkish
	 *            locale, case-insensitive compare matches â€œIâ€? to â€œÄ±â€? (Unicode code point U+0131, Latin Small Dotless I), not the normal â€œiâ€?
	 *            character.
	 * @return Return Value An NSRange structure giving the location and length in the receiver of aString within aRange in the receiver,
	 *         modulo the options in mask. The range returned is relative to the start of the string, not to the passed-in range. Returns
	 *         {NSNotFound, 0} if aString is not found or is empty (@"").
	 * @Discussion The length of the returned range and that of aString may differ if equivalent composed character sequences are matched.
	 **/

	public NSRange rangeOfStringOptionsRangeLocale(NSString aString, NSStringCompareOptions mask,
												   NSRange searchRange, Locale locale) {
		NSRange nsRange = new NSRange(0, 0);
		Locale defaultLocale;
		if (aString == null)
			throw new IllegalArgumentException("This value must not be nil.");
		if (locale == null)
			defaultLocale = Locale.getDefault();
		else
			defaultLocale = locale;
		String receiverRangeString = new String(
				getWrappedString().substring(searchRange.location, searchRange.length));
		receiverRangeString = String.format(defaultLocale, "%s", receiverRangeString);
		aString = new NSString(String.format(defaultLocale, "%s", aString));
		if (AndroidStringUtils.contains(receiverRangeString, aString.getWrappedString())) {
			nsRange.location = AndroidStringUtils.indexOf(receiverRangeString,
					aString.getWrappedString());
			nsRange.length = aString.getLength();
		}
		return nsRange;
	}

	/**
	 * @Signature: enumerateLinesUsingBlock:
	 * @Declaration : - (void)enumerateLinesUsingBlock:(void (^)(NSString *line, BOOL *stop))block
	 * @Description : Enumerates all the lines in a string.
	 * @param block The block executed for the enumeration. The block takes two arguments: line The current line of the string being
	 *            enumerated. The line contains just the contents of the line, without the line terminators. See
	 *            getLineStart:end:contentsEnd:forRange: for a discussion of line terminators. stop A reference to a Boolean value that the
	 *            block can use to stop the enumeration by setting *stop = YES; it should not touch *stop otherwise.
	 * @param line The current line of the string being enumerated. The line contains just the contents of the line, without the line
	 *            terminators. See getLineStart:end:contentsEnd:forRange: for a discussion of line terminators.
	 * @param stop A reference to a Boolean value that the block can use to stop the enumeration by setting *stop = YES; it should not touch
	 *            *stop otherwise.
	 **/

	public void enumerateLinesUsingBlock(PerformBlock.VoidBlockNSStringNSRangeNSRangeBOOL block) {
		boolean[] stop = new boolean[1];
		String lineSeparator = System.getProperty("line.separator");
		Matcher m = Pattern.compile(lineSeparator).matcher(wrappedString);
		NSString nLine = new NSString();
		while (m.find()) {
			nLine.wrappedString = m.group();
			block.perform(nLine, stop);
		}

	}

	/**
	 * @Signature: enumerateSubstringsInRange:options:usingBlock:
	 * @Declaration : - (void)enumerateSubstringsInRange:(NSRange)range options:(NSStringEnumerationOptions)opts usingBlock:(void
	 *              (^)(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop))block
	 * @Description : Enumerates the substrings of the specified type in the specified range of the string.
	 * @param range The range within the string to enumerate substrings.
	 * @param opts Options specifying types of substrings and enumeration styles.
	 * @param block The block executed for the enumeration. The block takes four arguments: substring The enumerated string. substringRange
	 *            The range of the enumerated string in the receiver. enclosingRange The range that includes the substring as well as any
	 *            separator or filler characters that follow. For instance, for lines, enclosingRange contains the line terminators. The
	 *            enclosingRange for the first string enumerated also contains any characters that occur before the string. Consecutive
	 *            enclosing ranges are guaranteed not to overlap, and every single character in the enumerated range is included in one and
	 *            only one enclosing range. stop A reference to a Boolean value that the block can use to stop the enumeration by setting
	 *            *stop = YES; it should not touch *stop otherwise.
	 * @param substring The enumerated string.
	 * @param substringRange The range of the enumerated string in the receiver.
	 * @param enclosingRange The range that includes the substring as well as any separator or filler characters that follow. For instance,
	 *            for lines, enclosingRange contains the line terminators. The enclosingRange for the first string enumerated also contains
	 *            any characters that occur before the string. Consecutive enclosing ranges are guaranteed not to overlap, and every single
	 *            character in the enumerated range is included in one and only one enclosing range.
	 * @param stop A reference to a Boolean value that the block can use to stop the enumeration by setting *stop = YES; it should not touch
	 *            *stop otherwise.
	 * @Discussion If this method is sent to an instance of NSMutableString, mutation (deletion, addition, or change) is allowed, as long as
	 *             it is within enclosingRange. After a mutation, the enumeration continues with the range immediately following the
	 *             processed range, after the length of the processed range is adjusted for the mutation. (The enumerator assumes any change
	 *             in length occurs in the specified range.) For example, if the block is called with a range starting at location N, and
	 *             the block deletes all the characters in the supplied range, the next call will also pass N as the index of the range.
	 *             This is the case even if mutation of the previous range changes the string in such a way that the following substring
	 *             would have extended to include the already enumerated range. For example, if the string "Hello World" is enumerated via
	 *             words, and the block changes "Hello " to "Hello", thus forming "HelloWorld", the next enumeration will return "World"
	 *             rather than "HelloWorld".
	 **/

	public void enumerateSubstringsInRangeOptionsUsingBlock(NSRange range,
															NSStringEnumerationOptions opts,
															PerformBlock.VoidBlockNSStringNSRangeNSRangeBOOL block) {
		// not yet covered
	}

	// Determining Line and Paragraph Ranges
	/**
	 * @Signature: getLineStart:end:contentsEnd:forRange:
	 * @Declaration : - (void)getLineStart:(NSUInteger *)startIndex end:(NSUInteger *)lineEndIndex contentsEnd:(NSUInteger
	 *              *)contentsEndIndex forRange:(NSRange)aRange
	 * @Description : Returns by reference the beginning of the first line and the end of the last line touched by the given range.
	 * @param startIndex Upon return, contains the index of the first character of the line containing the beginning of aRange. Pass NULL if
	 *            you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param lineEndIndex Upon return, contains the index of the first character past the terminator of the line containing the end of
	 *            aRange. Pass NULL if you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param contentsEndIndex Upon return, contains the index of the first character of the terminator of the line containing the end of
	 *            aRange. Pass NULL if you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param aRange A range within the receiver. The value must not exceed the bounds of the receiver. Raises an NSRangeException if aRange
	 *            is invalid.
	 * @Discussion A line is delimited by any of these characters, the longest possible sequence being preferred to any shorter: U+000D (\r
	 *             or CR) U+2028 (Unicode line separator) U+000A (\n or LF) U+2029 (Unicode paragraph separator) \r\n, in that order (also
	 *             known as CRLF) If aRange is contained with a single line, of course, the returned indexes all belong to that line. You
	 *             can use the results of this method to construct ranges for lines by using the start index as the rangeâ€™s location and the
	 *             difference between the end index and the start index as the rangeâ€™s length.
	 **/

	public void getLineStartEndContentsEndForRange(int[] startIndex, int[] lineEndIndex,
												   int[] contentsEndIndex, NSRange aRange) {
		// not yet covered
	}

	/**
	 * @Signature: lineRangeForRange:
	 * @Declaration : - (NSRange)lineRangeForRange:(NSRange)aRange
	 * @Description : Returns the range of characters representing the line or lines containing a given range.
	 * @param aRange A range within the receiver. The value must not exceed the bounds of the receiver.
	 * @return Return Value The range of characters representing the line or lines containing aRange, including the line termination
	 *         characters. See getLineStart:end:contentsEnd:forRange: for a discussion of line terminators.
	 **/

	public NSRange lineRangeForRange(NSRange aRange) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: getParagraphStart:end:contentsEnd:forRange:
	 * @Declaration : - (void)getParagraphStart:(NSUInteger *)startIndex end:(NSUInteger *)endIndex contentsEnd:(NSUInteger
	 *              *)contentsEndIndex forRange:(NSRange)aRange
	 * @Description : Returns by reference the beginning of the first paragraph and the end of the last paragraph touched by the given
	 *              range.
	 * @param startIndex Upon return, contains the index of the first character of the paragraph containing the beginning of aRange. Pass
	 *            NULL if you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param endIndex Upon return, contains the index of the first character past the terminator of the paragraph containing the end of
	 *            aRange. Pass NULL if you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param contentsEndIndex Upon return, contains the index of the first character of the terminator of the paragraph containing the end
	 *            of aRange. Pass NULL if you do not need this value (in which case the work to compute the value isnâ€™t performed).
	 * @param aRange A range within the receiver. The value must not exceed the bounds of the receiver.
	 * @Discussion If aRange is contained with a single paragraph, of course, the returned indexes all belong to that paragraph. Similar to
	 *             getLineStart:end:contentsEnd:forRange:, you can use the results of this method to construct the ranges for paragraphs.
	 **/

	public void getParagraphStartEndContentsEndForRange(int[] startIndex, int[] endIndex,
														int[] contentsEndIndex, NSRange aRange) {
		// not yet covered
	}

	/**
	 * @Signature: paragraphRangeForRange:
	 * @Declaration : - (NSRange)paragraphRangeForRange:(NSRange)aRange
	 * @Description : Returns the range of characters representing the paragraph or paragraphs containing a given range.
	 * @param aRange A range within the receiver. The range must not exceed the bounds of the receiver.
	 * @return Return Value The range of characters representing the paragraph or paragraphs containing aRange, including the paragraph
	 *         termination characters.
	 **/

	public NSRange paragraphRangeForRange(NSRange aRange) {
		// not yet covered
		return null;
	}

	// Converting String Contents Into a Property List

	/**
	 * @Signature: propertyList
	 * @Declaration : - (id)propertyList
	 * @Description : Parses the receiver as a text representation of a property list, returning an NSString, NSData, NSArray, or
	 *              NSDictionary object, according to the topmost element.
	 * @return Return Value A property list representation of returning an NSString, NSData, NSArray, or NSDictionary object, according to
	 *         the topmost element.
	 * @Discussion The receiver must contain a string in a property list format. For a discussion of property list formats, see Property
	 *             List Programming Guide. Important:Â Raises an NSParseErrorException if the receiver cannot be parsed as a property list.
	 **/

	public void propertyList() {
		// not yet covered
	}

	/**
	 * @Signature: propertyListFromStringsFileFormat
	 * @Declaration : - (NSDictionary *)propertyListFromStringsFileFormat
	 * @Description : Returns a dictionary object initialized with the keys and values found in the receiver.
	 * @return Return Value A dictionary object initialized with the keys and values found in the receiver
	 * @Discussion The receiver must contain text in the format used for .strings files. In this format, keys and values are separated by an
	 *             equal sign, and each key-value pair is terminated with a semicolon. The value is optionalâ€”if not present, the equal sign
	 *             is also omitted. The keys and values themselves are always strings enclosed in straight quotation marks.
	 **/

	public NSDictionary propertyListFromStringsFileFormat() {
		// not yet covered
		return null;
	}

	// Folding Strings

	/**
	 * @Signature: stringByFoldingWithOptions:locale:
	 * @Declaration : - (NSString *)stringByFoldingWithOptions:(NSStringCompareOptions)options locale:(NSLocale *)locale
	 * @Description : Returns a string with the given character folding options applied.
	 * @param options A mask of compare flags with a suffix InsensitiveSearch.
	 * @param locale The locale to use for the folding. To use the current locale, pass [NSLocalecurrentLocale]. To use the system locale,
	 *            pass nil.
	 * @return Return Value A string with the character folding options applied.
	 * @Discussion Character folding operations remove distinctions between characters. For example, case folding may replace uppercase
	 *             letters with their lowercase equivalents. The locale affects the folding logic. For example, for the Turkish locale,
	 *             case-insensitive compare matches â€œIâ€? to â€œÄ±â€? (Unicode code point U+0131, Latin Small Dotless I), not the normal â€œiâ€?
	 *             character.
	 **/

	public String stringByFoldingWithOptionsLocale(NSStringCompareOptions options, Locale locale) {
		return Normalizer.normalize(wrappedString, Normalizer.Form.NFD);
	}

	// Changing Case
	/**
	 * @Declaration : @property (readonly, copy) NSString *capitalizedString
	 * @Description : A capitalized representation of the receiver. (read-only)
	 * @Discussion A string with the first character in each word changed to its corresponding uppercase value, and all remaining characters
	 *             set to their corresponding lowercase values. A â€œwordâ€? is any sequence of characters delimited by spaces, tabs, or line
	 *             terminators (listed under getLineStart:end:contentsEnd:forRange:). Some common word delimiting punctuation isnâ€™t
	 *             considered, so this property may not generally produce the desired results for multiword strings. Case transformations
	 *             arenâ€™t guaranteed to be symmetrical or to produce strings of the same lengths as the originals. See lowercaseString for
	 *             an example. Note:Â This property performs the canonical (non-localized) mapping. It is suitable for programming operations
	 *             that require stable results not depending on the current locale. For localized case mapping for strings presented to
	 *             users, use the capitalizedStringWithLocale: method.
	 **/
	public NSString capitalizedString;

	public NSString capitalizedString() {
		capitalizedString = new NSString(AndroidWordUtils.capitalizeFully(wrappedString));
		return capitalizedString;
	}

	/**
	 * @Signature: capitalizedStringWithLocale:
	 * @Declaration : - (NSString *)capitalizedStringWithLocale:(NSLocale *)locale
	 * @Description : Returns a capitalized representation of the receiver using the specified locale.
	 * @param locale The locale. For strings presented to users, pass the current locale ([NSLocalecurrentLocale]). To use the system
	 *            locale, pass nil.
	 * @return Return Value A string with the first character from each word in the receiver changed to its corresponding uppercase value,
	 *         and all remaining characters set to their corresponding lowercase values.
	 * @Discussion A â€œwordâ€? is any sequence of characters delimited by spaces, tabs, or line terminators (listed under
	 *             getLineStart:end:contentsEnd:forRange:). Some common word delimiting punctuation isnâ€™t considered, so this method may not
	 *             generally produce the desired results for multiword strings.
	 **/

	public String capitalizedStringWithLocale(Locale locale) {
		return AndroidWordUtils.capitalizeFully(String.format(locale, "%s", wrappedString));
	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *lowercaseString
	 * @Description : A lowercase representation of the string.
	 * @Discussion Case transformations arenâ€™t guaranteed to be symmetrical or to produce strings of the same lengths as the originals. The
	 *             result of this statement: lcString = [myString lowercaseString]; might not be equal to this statement: lcString =
	 *             [[myString uppercaseString] lowercaseString]; For example, the uppercase form of â€œÃŸâ€? in German is â€œSSâ€?, so converting
	 *             â€œStraÃŸeâ€? to uppercase, then lowercase, produces this sequence of strings: â€œStraÃŸeâ€? â€œSTRASSEâ€? â€œstrasseâ€? Note:Â This
	 *             property performs the canonical (non-localized) mapping. It is suitable for programming operations that require stable
	 *             results not depending on the current locale. For localized case mapping for strings presented to users, use
	 *             lowercaseStringWithLocale:.
	 **/

	private NSString lowercaseString;


	public NSString lowercaseString() {
		lowercaseString = new NSString(wrappedString.toLowerCase());
		return lowercaseString;
	}


	public NSString getLowercaseString() {
		return lowercaseString();
	}


	public void setLowercaseString(NSString lowercaseString) {
		this.lowercaseString = lowercaseString;
	}

	/**
	 * @Signature: lowercaseStringWithLocale:
	 * @Declaration : - (NSString *)lowercaseStringWithLocale:(NSLocale *)locale
	 * @Description : Returns a version of the string with all letters converted to lowercase, taking into account the specified locale.
	 * @param locale The locale. For strings presented to users, pass the current locale ([NSLocalecurrentLocale]). To use the system local,
	 *            pass nil.
	 * @return Return Value A lowercase string using the locale. Input of @"ABcde" would result in a return of @"abcde".
	 * @Discussion .
	 **/

	public NSString lowercaseStringWithLocale(NSLocale locale) {
		if (locale == null)
			return lowercaseString();
		return new NSString(wrappedString.toLowerCase(locale.getWrappedLocale()));
	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *uppercaseString
	 * @Description : An uppercase representation of the string. (read-only)
	 * @Discussion Case transformations arenâ€™t guaranteed to be symmetrical or to produce strings of the same lengths as the originals. See
	 *             lowercaseString for an example. Note:Â This property performs the canonical (non-localized) mapping. It is suitable for
	 *             programming operations that require stable results not depending on the current locale. For localized case mapping for
	 *             strings presented to users, use the uppercaseStringWithLocale: method.
	 **/

	private NSString uppercaseString;


	public NSString uppercaseString() {
		uppercaseString = new NSString(wrappedString.toUpperCase(Locale.getDefault()));
		return uppercaseString;
	}

	/**
	 * @Signature: uppercaseStringWithLocale:
	 * @Declaration : - (NSString *)uppercaseStringWithLocale:(NSLocale *)locale
	 * @Description : Returns a version of the string with all letters converted to uppercase, taking into account the specified locale.
	 * @param locale The locale. For strings presented to users, pass the current locale ([NSLocalecurrentLocale]). To use the system
	 *            locale, pass nil.
	 * @return Return Value An uppercase string using the locale. Input of @"ABcde" would result in a return of @"ABCDE".
	 **/

	public NSString uppercaseStringWithLocale(NSLocale locale) {
		if (locale == null)
			return uppercaseString();
		return new NSString(wrappedString.toUpperCase(locale.getWrappedLocale()));
	}

	// Getting Numeric Values

	/**
	 * @Declaration : @property (readonly) double doubleValue
	 * @Description : The floating-point value of the string as a double. (read-only)
	 * @Discussion This property doesnâ€™t include any whitespace at the beginning of the string. This property is HUGE_VAL or â€“HUGE_VAL on
	 *             overflow, 0.0 on underflow. This property is 0.0 if the string doesnâ€™t begin with a valid text representation of a
	 *             floating-point number. This property uses formatting information stored in the non-localized value; use an NSScanner
	 *             object for localized scanning of numeric values from a string.
	 **/


	public double doubleValue() {
		if (!"".equals(wrappedString)) {
			wrappedString = wrappedString.trim().replaceAll(" ", "");
			return Double.parseDouble(wrappedString);
		}
		return 0;
	}


	public double getDoubleValue() {
		return doubleValue();
	}

	/**
	 * @Declaration : @property (readonly) float floatValue
	 * @Description : The floating-point value of the string as a float. (read-only)
	 * @Discussion This property doesnâ€™t include whitespace at the beginning of the string. This property is HUGE_VAL or â€“HUGE_VAL on
	 *             overflow, 0.0 on underflow. This property is 0.0 if the string doesnâ€™t begin with a valid text representation of a
	 *             floating-point number. This method uses formatting information stored in the non-localized value; use an NSScanner object
	 *             for localized scanning of numeric values from a string.
	 **/
	private float floatValue;


	public float floatValue() {
		floatValue = Float.parseFloat(wrappedString);
		return floatValue;
	}

	/**
	 * @Declaration : @property (readonly) int intValue
	 * @Description : The integer value of the string. (read-only)
	 * @Discussion The integer value of the string, assuming a decimal representation and skipping whitespace at the beginning of the
	 *             string. This property is INT_MAX or INT_MIN on overflow. This property is 0 if the string doesnâ€™t begin with a valid
	 *             decimal text representation of a number. This property uses formatting information stored in the non-localized value; use
	 *             an NSScanner object for localized scanning of numeric values from a string.
	 **/
	private int intValue;


	public int intValue() {
		if ("".equalsIgnoreCase(wrappedString)) {
			return 0;
		}
		return Integer.parseInt(wrappedString);
	}

	/**
	 * @Declaration : @property (readonly) NSInteger integerValue
	 * @Description : The NSInteger value of the string. (read-only)
	 * @Discussion The NSInteger value of the string, assuming a decimal representation and skipping whitespace at the beginning of the
	 *             string. This property is 0 if the string doesnâ€™t begin with a valid decimal text representation of a number. This
	 *             property uses formatting information stored in the non-localized value; use an NSScanner object for localized scanning of
	 *             numeric values from a string.
	 **/
	private int integerValue;


	public int integerValue() {
		integerValue = Integer.parseInt(wrappedString);
		return integerValue;
	}

	/**
	 * @Declaration : @property (readonly) long long longLongValue
	 * @Description : The long long value of the string. (read-only)
	 * @Discussion The long long value of the string, assuming a decimal representation and skipping whitespace at the beginning of the
	 *             string. This property is LLONG_MAX or LLONG_MIN on overflow. This property is 0 if the receiver doesnâ€™t begin with a
	 *             valid decimal text representation of a number. This property uses formatting information stored in the non-localized
	 *             value; use an NSScanner object for localized scanning of numeric values from a string.
	 **/
	private long longLongValue;


	public Long longLongValue() {
		longLongValue = Long.parseLong(wrappedString);
		return longLongValue;
	}

	/**
	 * @Declaration : @property (readonly) BOOL boolValue
	 * @Description : The Boolean value of the string. (read-only)
	 * @Discussion This property is YES on encountering one of "Y", "y", "T", "t", or a digit 1-9â€”the method ignores any trailing
	 *             characters. This property is NO if the receiver doesnâ€™t begin with a valid decimal text representation of a number. The
	 *             property assumes a decimal representation and skips whitespace at the beginning of the string. It also skips initial
	 *             whitespace characters, or optional -/+ sign followed by zeroes.
	 **/
	private boolean boolValue;

	public Boolean boolValue() {
		boolValue = Boolean.parseBoolean(wrappedString);
		return boolValue;
	}

	// Working with Paths

	/**
	 * @Signature: pathWithComponents:
	 * @Declaration : + (NSString *)pathWithComponents:(NSArray *)components
	 * @Description : Returns a string built from the strings in a given array by concatenating them with a path separator between each
	 *              pair.
	 * @param components An array of NSString objects representing a file path. To create an absolute path, use a slash mark (â€œ/â€?) as the
	 *            first component. To include a trailing path divider, use an empty string as the last component.
	 * @return Return Value A string built from the strings in components by concatenating them (in the order they appear in the array) with
	 *         a path separator between each pair.
	 * @Discussion This method doesnâ€™t clean up the path created; use stringByStandardizingPath to resolve empty components, references to
	 *             the parent directory, and so on.
	 **/

	public static NSString pathWithComponents(NSArray<?> components) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: pathComponents
	 * @Declaration : - (NSArray *)pathComponents
	 * @Description : Returns an array of NSString objects containing, in order, each path component of the receiver.
	 * @return Return Value An array of NSString objects containing, in order, each path component of the receiver.
	 * @Discussion The strings in the array appear in the order they did in the receiver. If the string begins or ends with the path
	 *             separator, then the first or last component, respectively, will contain the separator. Empty components (caused by
	 *             consecutive path separators) are deleted. For example, this code excerpt: NSString *path = @"tmp/scratch"; NSArray
	 *             *pathComponents = [path pathComponents]; produces an array with these contents: Index Path Component 0 â€œtmpâ€? 1 â€œscratchâ€?
	 *             If the receiver begins with a slashâ€”for example, â€œ/tmp/scratchâ€?â€”the array has these contents: Index Path Component 0 â€œ/â€?
	 *             1 â€œtmpâ€? 2 â€œscratchâ€? If the receiver has no separatorsâ€”for example, â€œscratchâ€?â€”the array contains the string itself, in
	 *             this case â€œscratchâ€?. Note that this method only works with file paths (not, for example, string representations of URLs).
	 **/

	public NSArray<NSString> pathComponents() {
		String[] Result = wrappedString.split(File.pathSeparator);
		NSArray<NSString> nsArray = new NSArray<NSString>();
		for (String string : Result) {
			nsArray.getWrappedList().add(new NSString(string));
		}
		return nsArray;
	}

	/**
	 * @Signature: completePathIntoString:caseSensitive:matchesIntoArray:filterTypes:
	 * @Declaration : - (NSUInteger)completePathIntoString:(NSString **)outputName caseSensitive:(BOOL)flag matchesIntoArray:(NSArray
	 *              **)outputArray filterTypes:(NSArray *)filterTypes
	 * @Description : Interprets the receiver as a path in the file system and attempts to perform filename completion, returning a numeric
	 *              value that indicates whether a match was possible, and by reference the longest path that matches the receiver.
	 * @param outputName Upon return, contains the longest path that matches the receiver.
	 * @param flag If YES, the method considers case for possible completions.
	 * @param outputArray Upon return, contains all matching filenames.
	 * @param filterTypes An array of NSString objects specifying path extensions to consider for completion. Only paths whose extensions
	 *            (not including the extension separator) match one of these strings are included in outputArray. Pass nil if you donâ€™t want
	 *            to filter the output.
	 * @return Return Value 0 if no matches are found and 1 if exactly one match is found. In the case of multiple matches, returns the
	 *         actual number of matching paths if outputArray is provided, or simply a positive value if outputArray is NULL.
	 * @Discussion You can check for the existence of matches without retrieving by passing NULL as outputArray. Note that this method only
	 *             works with file paths (not, for example, string representations of URLs).
	 **/

	public int completePathIntoStringCaseSensitiveMatchesIntoArrayFilterTypes(NSString[] outputName,
																			  boolean caseSensitive, NSArray<NSString> outputArray, NSArray<NSString> filterTypes) {
		// TODO will not be implemented
		return 0;
	}

	/**
	 * @Signature: fileSystemRepresentation
	 * @Declaration : - (const char *)fileSystemRepresentation
	 * @Description : Returns a file system-specific representation of the receiver.
	 * @return Return Value A file system-specific representation of the receiver, as described for getFileSystemRepresentation:maxLength:.
	 * @Discussion The returned C string will be automatically freed just as a returned object would be released; your code should copy the
	 *             representation or use getFileSystemRepresentation:maxLength: if it needs to store the representation outside of the
	 *             memory context in which the representation was created. Raises an NSCharacterConversionException if the receiver canâ€™t be
	 *             represented in the file systemâ€™s encoding. It also raises an exception if the receiver contains no characters. Note that
	 *             this method only works with file paths (not, for example, string representations of URLs). To convert a char * path (such
	 *             as you might get from a C library routine) to an NSString object, use NSFileManagerâ€˜s
	 *             stringWithFileSystemRepresentation:length: method.
	 **/

	public String fileSystemRepresentation() {
		// NOT IMPLEMENTED
		return "";
	}

	/**
	 * @Signature: getFileSystemRepresentation:maxLength:
	 * @Declaration : - (BOOL)getFileSystemRepresentation:(char *)buffer maxLength:(NSUInteger)maxLength
	 * @Description : Interprets the receiver as a system-independent path and fills a buffer with a C-string in a format and encoding
	 *              suitable for use with file-system calls.
	 * @param buffer Upon return, contains a C-string that represent the receiver as as a system-independent path, plus the NULL termination
	 *            byte. The size of buffer must be large enough to contain maxLength bytes.
	 * @param maxLength The maximum number of bytes in the string to return in buffer (including a terminating NULL character, which this
	 *            method adds).
	 * @return Return Value YES if buffer is successfully filled with a file-system representation, otherwise NO (for example, if maxLength
	 *         would be exceeded or if the receiver canâ€™t be represented in the file systemâ€™s encoding).
	 * @Discussion This method operates by replacing the abstract path and extension separator characters (â€˜/â€™ and â€˜.â€™ respectively) with
	 *             their equivalents for the operating system. If the system-specific path or extension separator appears in the abstract
	 *             representation, the characters it is converted to depend on the system (unless theyâ€™re identical to the abstract
	 *             separators). Note that this method only works with file paths (not, for example, string representations of URLs). The
	 *             following example illustrates the use of the maxLength argument. The first method invocation returns failure as the file
	 *             representation of the string (@"/mach_kernel") is 12 bytes long and the value passed as the maxLength argument (12) does
	 *             not allow for the addition of a NULL termination byte. char filenameBuffer[13]; BOOL success; success = [@"/mach_kernel"
	 *             getFileSystemRepresentation:filenameBuffer maxLength:12]; // success == NO // Changing the length to include the NULL
	 *             character does work success = [@"/mach_kernel" getFileSystemRepresentation:filenameBuffer maxLength:13]; // success ==
	 *             YES
	 **/

	public boolean getFileSystemRepresentationMaxLength(byte[] buffer, int maxLength) {
		// TODO WILL NOT BE IMPLEMENTED
		return true;
	}

	/**
	 * @Signature: isAbsolutePath
	 * @Declaration : - (BOOL)isAbsolutePath
	 * @Description : Returning a Boolean value that indicates whether the receiver represents an absolute path.
	 * @return Return Value YES if the receiver (if interpreted as a path) represents an absolute path, otherwise NO (if the receiver
	 *         represents a relative path).
	 * @Discussion See String Programming Guide for more information on paths. Note that this method only works with file paths (not, for
	 *             example, string representations of URLs). The method does not check the filesystem for the existence of the path (use
	 *             fileExistsAtPath: or similar methods in NSFileManager for that task).
	 **/

	public boolean isAbsolutePath() {
		URI uri;
		File f = new File(wrappedString);
		try {
			uri = new URI(wrappedString);
			return uri.isAbsolute() || f.isAbsolute();
		} catch (URISyntaxException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}

	}

	/**
	 * @Signature: lastPathComponent
	 * @Declaration : - (NSString *)lastPathComponent
	 * @Description : Returns the last path component of the receiver.
	 * @return Return Value The last path component of the receiver.
	 * @Discussion Path components are alphanumeric strings delineated by the path separator (slash â€œ/â€?) or the beginning or end of the path
	 *             string. Multiple path separators at the end of the string are stripped. The following table illustrates the effect of
	 *             lastPathComponent on a variety of different paths: Receiverâ€™s String Value String Returned â€œ/tmp/scratch.tiffâ€?
	 *             â€œscratch.tiffâ€? â€œ/tmp/scratchâ€? â€œscratchâ€? â€œ/tmp/â€? â€œtmpâ€? â€œscratch///â€? â€œscratchâ€? â€œ/â€? â€œ/â€? Note that this method only works
	 *             with file paths (not, for example, string representations of URLs).
	 **/

	public NSString lastPathComponent() {
		if (wrappedString.contains("assets ")) {
			wrappedString = wrappedString.replace("assets ", "");
		}
		return new NSString(wrappedString.substring(wrappedString.lastIndexOf("/") + 1,
				wrappedString.length()));
	}

	/**
	 * @Signature: pathExtension
	 * @Declaration : - (NSString *)pathExtension
	 * @Description : Interprets the receiver as a path and returns the receiverâ€™s extension, if any.
	 * @return Return Value The receiverâ€™s extension, if any (not including the extension divider).
	 * @Discussion The path extension is the portion of the last path component which follows the final period, if there is one. The
	 *             following table illustrates the effect of pathExtension on a variety of different paths: Receiverâ€™s String Value String
	 *             Returned â€œ/tmp/scratch.tiffâ€? â€œtiffâ€? â€œ.scratch.tiffâ€? â€œtiffâ€? â€œ/tmp/scratchâ€? â€œâ€? (an empty string) â€œ/tmp/â€? â€œâ€? (an empty
	 *             string) â€œ/tmp/scratch..tiffâ€? â€œtiffâ€? Note that this method only works with file paths (not, for example, string
	 *             representations of URLs).
	 **/

	public NSString pathExtension() {
		return new NSString(AndroidFilenameUtils.getExtension(wrappedString));
	}

	/**
	 * @Signature: stringByAbbreviatingWithTildeInPath
	 * @Declaration : - (NSString *)stringByAbbreviatingWithTildeInPath
	 * @Description : Returns a new string that replaces the current home directory portion of the current path with a tilde (~) character.
	 * @return Return Value A new string based on the current string object. If the new string specifies a file in the current home
	 *         directory, the home directory portion of the path is replaced with a tilde (~) character. If the string does not specify a
	 *         file in the current home directory, this method returns a new string object whose path is unchanged from the path in the
	 *         current string.
	 * @Discussion Note that this method only works with file paths. It does not work for string representations of URLs. For sandboxed apps
	 *             in OS X, the current home directory is not the same as the userâ€™s home directory. For a sandboxed app, the home directory
	 *             is the appâ€™s home directory. So if you specified a path of /Users/<current_user>/file.txt for a sandboxed app, the
	 *             returned path would be unchanged from the original. However, if you specified the same path for an app not in a sandbox,
	 *             this method would replace the /Users/<current_user> portion of the path with a tilde.
	 **/

	public void stringByAbbreviatingWithTildeInPath() {
		// TODO will not be implemented
	}

	/**
	 * @Signature: stringByAppendingPathExtension:
	 * @Declaration : - (NSString *)stringByAppendingPathExtension:(NSString *)ext
	 * @Description : Returns a new string made by appending to the receiver an extension separator followed by a given extension.
	 * @param ext The extension to append to the receiver.
	 * @return Return Value A new string made by appending to the receiver an extension separator followed by ext.
	 * @Discussion The following table illustrates the effect of this method on a variety of different paths, assuming that ext is supplied
	 *             as @"tiff": Receiverâ€™s String Value Resulting String â€œ/tmp/scratch.oldâ€? â€œ/tmp/scratch.old.tiffâ€? â€œ/tmp/scratch.â€?
	 *             â€œ/tmp/scratch..tiffâ€? â€œ/tmp/â€? â€œ/tmp.tiffâ€? â€œscratchâ€? â€œscratch.tiffâ€? Note that adding an extension to @"/tmp/" causes the
	 *             result to be @"/tmp.tiff" instead of @"/tmp/.tiff". This difference is because a file named @".tiff" is not considered to
	 *             have an extension, so the string is appended to the last nonempty path component. Note that this method only works with
	 *             file paths (not, for example, string representations of URLs).
	 **/

	public NSString stringByAppendingPathExtension(NSString ext) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(wrappedString);
		strBuilder.append(AndroidFilenameUtils.EXTENSION_SEPARATOR);
		strBuilder.append(ext.getWrappedString());
		return new NSString(strBuilder.toString());
	}

	/**
	 * @Signature: stringByDeletingLastPathComponent
	 * @Declaration : - (NSString *)stringByDeletingLastPathComponent
	 * @Description : Returns a new string made by deleting the last path component from the receiver, along with any final path separator.
	 * @return Return Value A new string made by deleting the last path component from the receiver, along with any final path separator. If
	 *         the receiver represents the root path it is returned unaltered.
	 * @Discussion The following table illustrates the effect of this method on a variety of different paths: Receiverâ€™s String Value
	 *             Resulting String â€œ/tmp/scratch.tiffâ€? â€œ/tmpâ€? â€œ/tmp/lock/â€? â€œ/tmpâ€? â€œ/tmp/â€? â€œ/â€? â€œ/tmpâ€? â€œ/â€? â€œ/â€? â€œ/â€? â€œscratch.tiffâ€? â€œâ€? (an
	 *             empty string) Note that this method only works with file paths (not, for example, string representations of URLs).
	 **/

	public NSString stringByDeletingLastPathComponent() {
		if ("/".equals(wrappedString))
			return new NSString(wrappedString);
		if (wrappedString.contains(File.pathSeparator)) {
			int index = wrappedString.lastIndexOf(File.pathSeparator);
			return new NSString(wrappedString.substring(0, index));
		}
		return null;
	}

	/**
	 * @Signature: stringByDeletingPathExtension
	 * @Declaration : - (NSString *)stringByDeletingPathExtension
	 * @Description : Returns a new string made by deleting the extension (if any, and only the last) from the receiver.
	 * @return Return Value a new string made by deleting the extension (if any, and only the last) from the receiver. Strips any trailing
	 *         path separator before checking for an extension. If the receiver represents the root path, it is returned unaltered.
	 * @Discussion The following table illustrates the effect of this method on a variety of different paths: Receiverâ€™s String Value
	 *             Resulting String â€œ/tmp/scratch.tiffâ€? â€œ/tmp/scratchâ€? â€œ/tmp/â€? â€œ/tmpâ€? â€œscratch.bundle/â€? â€œscratchâ€? â€œscratch..tiffâ€? â€œscratch.â€?
	 *             â€œ.tiffâ€? â€œ.tiffâ€? â€œ/â€? â€œ/â€? Note that attempting to delete an extension from @".tiff" causes the result to be @".tiff"
	 *             instead of an empty string. This difference is because a file named @".tiff" is not considered to have an extension, so
	 *             nothing is deleted. Note also that this method only works with file paths (not, for example, string representations of
	 *             URLs).
	 **/

	public NSString stringByDeletingPathExtension() {
		if ("/".equals(wrappedString))
			return new NSString(wrappedString);
		if (wrappedString.contains(".")) {
			int index = wrappedString.lastIndexOf(".");
			return new NSString(wrappedString.substring(0, index));
		}
		return null;
	}

	/**
	 * @Signature: stringByExpandingTildeInPath
	 * @Declaration : - (NSString *)stringByExpandingTildeInPath
	 * @Description : Returns a new string made by expanding the initial component of the receiver to its full path value.
	 * @return Return Value A new string made by expanding the initial component of the receiver, if it begins with â€œ~â€? or â€œ~userâ€?, to its
	 *         full path value. Returns a new string matching the receiver if the receiverâ€™s initial component canâ€™t be expanded.
	 * @Discussion Note that this method only works with file paths (not, for example, string representations of URLs).
	 **/

	public NSString stringByExpandingTildeInPath() {
		// TODO will not be implemented
		return null;
	}

	/**
	 * @Signature: stringByResolvingSymlinksInPath
	 * @Declaration : - (NSString *)stringByResolvingSymlinksInPath
	 * @Description : Returns a new string made from the receiver by resolving all symbolic links and standardizing path.
	 * @return Return Value A new string made by expanding an initial tilde expression in the receiver, then resolving all symbolic links
	 *         and references to current or parent directories if possible, to generate a standardized path. If the original path is
	 *         absolute, all symbolic links are guaranteed to be removed; if itâ€™s a relative path, symbolic links that canâ€™t be resolved are
	 *         left unresolved in the returned string. Returns self if an error occurs.
	 * @Discussion If the name of the receiving path begins with /private, the stringByResolvingSymlinksInPath method strips off the
	 *             /private designator, provided the result is the name of an existing file. Note that this method only works with file
	 *             paths (not, for example, string representations of URLs).
	 **/

	public void stringByResolvingSymlinksInPath() {
		// TODO will not be implemented
	}

	/**
	 * @Signature: stringByStandardizingPath
	 * @Declaration : - (NSString *)stringByStandardizingPath
	 * @Description : Returns a new string made by removing extraneous path components from the receiver.
	 * @return Return Value A new string made by removing extraneous path components from the receiver.
	 * @Discussion If an invalid pathname is provided, stringByStandardizingPath may attempt to resolve it by calling
	 *             stringByResolvingSymlinksInPath, and the results are undefined. If any other kind of error is encountered (such as a path
	 *             component not existing), self is returned. This method can make the following changes in the provided string: Expand an
	 *             initial tilde expression using stringByExpandingTildeInPath. Reduce empty components and references to the current
	 *             directory (that is, the sequences â€œ//â€? and â€œ/./â€?) to single path separators. In absolute paths only, resolve references
	 *             to the parent directory (that is, the component â€œ..â€?) to the real parent directory if possible using
	 *             stringByResolvingSymlinksInPath, which consults the file system to resolve each potential symbolic link. In relative
	 *             paths, because symbolic links canâ€™t be resolved, references to the parent directory are left in place. Remove an initial
	 *             component of â€œ/privateâ€? from the path if the result still indicates an existing file or directory (checked by consulting
	 *             the file system). Note that the path returned by this method may still have symbolic link components in it. Note also
	 *             that this method only works with file paths (not, for example, string representations of URLs).
	 **/

	public void stringByStandardizingPath() {
		wrappedString = AndroidFilenameUtils.normalize(wrappedString);
	}

	// Linguistic Tagging and Analysis

	/**
	 * @Signature: enumerateLinguisticTagsInRange:scheme:options:orthography:usingBlock:
	 * @Declaration : - (void)enumerateLinguisticTagsInRange:(NSRange)range scheme:(NSString *)tagScheme
	 *              options:(NSLinguisticTaggerOptions)opts orthography:(NSOrthography *)orthography usingBlock:(void (^)(NSString *tag,
	 *              NSRange tokenRange, NSRange sentenceRange, BOOL *stop))block
	 * @Description : Performs linguistic analysis on the specified string by enumerating the specific range of the string, providing the
	 *              Block with the located tags.
	 * @param range The range of the string to analyze.
	 * @param tagScheme The tag scheme to use. See Linguistic Tag Schemes for supported values.
	 * @param opts The linguistic tagger options to use. See NSLinguisticTaggerOptionsfor the constants. These constants can be combined
	 *            using the C-Bitwise OR operator.
	 * @param orthography The orthography of the string. If nil, the linguistic tagger will attempt to determine the orthography from the
	 *            string content.
	 * @param block The Block to apply to the string. The block takes four arguments: tag The tag scheme for the token. The opts parameter
	 *            specifies the types of tagger options that are located. tokenRange The range of a string matching the tag scheme.
	 *            sentenceRange The range of the sentence in which the token is found. stop A reference to a Boolean value. The block can
	 *            set the value to YES to stop further processing of the array. The stop argument is an out-only argument. You should only
	 *            ever set this Boolean to YES within the Block.
	 * @param tag The tag scheme for the token. The opts parameter specifies the types of tagger options that are located.
	 * @param tokenRange The range of a string matching the tag scheme.
	 * @param sentenceRange The range of the sentence in which the token is found.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion This is a convenience method. It is the equivalent of creating an instance of NSLinguisticTagger, specifying the receiver
	 *             as the string to be analyzed, and the orthography (or nil) and then invoking the NSLinguisticTagger method or
	 *             enumerateTagsInRange:scheme:options:usingBlock:.
	 **/

	public void enumerateLinguisticTagsInRangeSchemeOptionsOrthographyUsingBlock(NSRange range,
																				 NSString tagScheme, NSLinguisticTaggerOptions opts, NSOrthography orthography,
																				 PerformBlock.VoidBlockNSStringNSRangeNSRangeBOOL block) {
	}

	/**
	 * @Signature: linguisticTagsInRange:scheme:options:orthography:tokenRanges:
	 * @Declaration : - (NSArray *)linguisticTagsInRange:(NSRange)range scheme:(NSString *)tagScheme options:(NSLinguisticTaggerOptions)opts
	 *              orthography:(NSOrthography *)orthography tokenRanges:(NSArray **)tokenRanges
	 * @Description : Returns an array of linguistic tags for the specified range and requested tags within the receiving string.
	 * @param range The range of the string to analyze.
	 * @param tagScheme The tag scheme to use. See Linguistic Tag Schemes for supported values.
	 * @param opts The linguistic tagger options to use. See NSLinguisticTaggerOptions for the constants. These constants can be combined
	 *            using the C-Bitwise OR operator.
	 * @param orthography The orthography of the string. If nil, the linguistic tagger will attempt to determine the orthography from the
	 *            string content.
	 * @param tokenRanges An array returned by-reference containing the token ranges of the linguistic tags wrapped in NSValue objects.
	 * @return Return Value Returns an array containing the linguistic tags for the tokenRanges within the receiving string.
	 * @Discussion This is a convenience method. It is the equivalent of creating an instance of NSLinguisticTagger, specifying the receiver
	 *             as the string to be analyzed, and the orthography (or nil) and then invoking the NSLinguisticTagger method or
	 *             linguisticTagsInRange:scheme:options:orthography:tokenRanges:.
	 **/

	public NSArray linguisticTagsInRangeSchemeOptionsOrthographyTokenRanges(NSRange range,
																			NSString tagScheme, NSLinguisticTaggerOptions opts, NSOrthography orthography,
																			NSArray[] tokenRanges) {
		return null;
	}

	/**
	 * @Declaration : @property (readonly) NSUInteger length
	 * @Description : The number of Unicode characters in the receiver. (read-only)
	 * @Discussion This number includes the individual characters of composed character sequences, so you cannot use this property to
	 *             determine if a string will be visible when printed or how long it will appear.
	 **/

	public int length;


	public int length() {
		length = wrappedString.length();
		return length;
	}


	public int getLength() {
		return this.length();
	}

	/**
	 * @Signature: lengthOfBytesUsingEncoding:
	 * @Declaration : - (NSUInteger)lengthOfBytesUsingEncoding:(NSStringEncoding)enc
	 * @Description : Returns the number of bytes required to store the receiver in a given encoding.
	 * @param enc The encoding for which to determine the receiver's length.
	 * @return Return Value The number of bytes required to store the receiver in the encoding enc in a non-external representation. The
	 *         length does not include space for a terminating NULL character. Returns 0 if the specified encoding cannot be used to convert
	 *         the receiver or if the amount of memory required for storing the results of the encoding conversion would exceed
	 *         NSIntegerMax.
	 * @Discussion The result is exact and is returned in O(n) time.
	 **/

	public int lengthOfBytesUsingEncoding(int enc) {
		CharsetEncoder encoder = NSStringEncoding.getCharsetFromInt(enc).newEncoder();
		char[] arrayTmp = this.wrappedString.toCharArray();
		char[] array = { arrayTmp[0] };
		int len = 0;
		CharBuffer input = CharBuffer.wrap(array);
		ByteBuffer output = ByteBuffer.allocate(10);
		for (int i = 0; i < arrayTmp.length; i++) {
			array[0] = arrayTmp[i];
			output.clear();
			input.clear();
			encoder.encode(input, output, false);
			len += output.position();
		}
		return len;
	}

	/**
	 * @Signature: maximumLengthOfBytesUsingEncoding:
	 * @Declaration : - (NSUInteger)maximumLengthOfBytesUsingEncoding:(NSStringEncoding)enc
	 * @Description : Returns the maximum number of bytes needed to store the receiver in a given encoding.
	 * @param enc The encoding for which to determine the receiver's length.
	 * @return Return Value The maximum number of bytes needed to store the receiver in encoding in a non-external representation. The
	 *         length does not include space for a terminating NULL character. Returns 0 if the amount of memory required for storing the
	 *         results of the encoding conversion would exceed NSIntegerMax.
	 * @Discussion The result is an estimate and is returned in O(1) time; the estimate may be considerably greater than the actual length
	 *             needed.
	 **/

	public int maximumLengthOfBytesUsingEncoding(int enc) {
		if (enc == NSStringEncoding.NSUnicodeStringEncoding)
			return length() * 2;
		if ((enc) == NSStringEncoding.NSUTF8StringEncoding)
			return length() * 6;
		if ((enc) == NSStringEncoding.NSUTF16StringEncoding)
			return length() * 8;

		return this.length();
	}

	// Dividing Strings

	/**
	 * @Signature: componentsSeparatedByString:
	 * @Declaration : - (NSArray *)componentsSeparatedByString:(NSString *)separator
	 * @Description : Returns an array containing substrings from the receiver that have been divided by a given separator.
	 * @param separator The separator string.
	 * @return Return Value An NSArray object containing substrings from the receiver that have been divided by separator.
	 * @Discussion The substrings in the array appear in the order they did in the receiver. Adjacent occurrences of the separator string
	 *             produce empty strings in the result. Similarly, if the string begins or ends with the separator, the first or last
	 *             substring, respectively, is empty.
	 **/

	public NSArray<NSString> componentsSeparatedByString(NSString separator) {
		String[] arrayOfString = this.wrappedString.split(separator.getWrappedString());
		List<NSString> anArray = new ArrayList<NSString>();
		for (int i = 0; i < arrayOfString.length; i++) {
			anArray.add(new NSString(arrayOfString[i]));
		}
		return new NSArray<NSString>(anArray);
	}

	/**
	 * @Signature: componentsSeparatedByCharactersInSet:
	 * @Declaration : - (NSArray *)componentsSeparatedByCharactersInSet:(NSCharacterSet *)separator
	 * @Description : Returns an array containing substrings from the receiver that have been divided by characters in a given set.
	 * @param separator A character set containing the characters to to use to split the receiver. Must not be nil.
	 * @return Return Value An NSArray object containing substrings from the receiver that have been divided by characters in separator.
	 * @Discussion The substrings in the array appear in the order they did in the receiver. Adjacent occurrences of the separator
	 *             characters produce empty strings in the result. Similarly, if the string begins or ends with separator characters, the
	 *             first or last substring, respectively, is empty.
	 **/

	public NSArray<Object> componentsSeparatedByCharactersInSet(NSCharacterSet separator) {
		NSArray<Object> nsArray = new NSArray<Object>();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < separator.getCharacterSet().size(); i++) {
			result.append("\\" + separator.getCharacterSet().toArray()[i]);
			if (i < separator.getCharacterSet().size() - 1) {
				result.append("|\\");
			}
		}
		String myRegexStr = result.toString();
		String[] arrayOfString = this.wrappedString.split(myRegexStr);
		for (String string : arrayOfString) {
			nsArray.getWrappedList().add(new NSString(string));
		}
		return nsArray;
	}

	/**
	 * @Signature: stringByTrimmingCharactersInSet:
	 * @Declaration : - (NSString *)stringByTrimmingCharactersInSet:(NSCharacterSet *)set
	 * @Description : Returns a new string made by removing from both ends of the receiver characters contained in a given character set.
	 * @param set A character set containing the characters to remove from the receiver. set must not be nil.
	 * @return Return Value A new string made by removing from both ends of the receiver characters contained in set. If the receiver is
	 *         composed entirely of characters from set, the empty string is returned.
	 * @Discussion Use whitespaceCharacterSet or whitespaceAndNewlineCharacterSet to remove whitespace around strings.
	 **/

	public NSString stringByTrimmingCharactersInSet(NSCharacterSet separator) {
		NSString result = new NSString();
		StringBuilder regEx = new StringBuilder();

		Iterator<Character> it = separator.getCharacterSet().iterator();
		int count = 0;
		while (it.hasNext()) {
			Character c = it.next();
			regEx.append("\\" + c);

			if (count < separator.getCharacterSet().size() - 1) {
				regEx.append("|");

			}
			count++;

		}

		String myRegexStr = regEx.toString();
		result.wrappedString = this.wrappedString.replaceAll(myRegexStr, "");

		this.wrappedString = result.wrappedString;
		return result;

	}

	/**
	 * @Signature: substringFromIndex:
	 * @Declaration : - (NSString *)substringFromIndex:(NSUInteger)anIndex
	 * @Description : Returns a new string containing the characters of the receiver from the one at a given index to the end.
	 * @param anIndex An index. The value must lie within the bounds of the receiver, or be equal to the length of the receiver. Raises an
	 *            NSRangeException if (anIndex - 1) lies beyond the end of the receiver.
	 * @return Return Value A new string containing the characters of the receiver from the one at anIndex to the end. If anIndex is equal
	 *         to the length of the string, returns an empty string.
	 **/

	public NSString substringFromIndex(int anIndex) {
		NSString result = new NSString();
		result.wrappedString = this.wrappedString.substring(anIndex);
		if (anIndex - 1 < this.wrappedString.length()) {
		} else {
			throw new IndexOutOfBoundsException("Index lies beyond the end of the receiver");
		}
		return result;
	}

	/**
	 * @Signature: substringWithRange:
	 * @Declaration : - (NSString *)substringWithRange:(NSRange)aRange
	 * @Description : Returns a string object containing the characters of the receiver that lie within a given range.
	 * @param aRange A range. The range must not exceed the bounds of the receiver. Raises an NSRangeException if (aRange.location - 1) or
	 *            (aRange.location + aRange.length - 1) lies beyond the end of the receiver.
	 * @return Return Value A string object containing the characters of the receiver that lie within aRange.
	 **/

	public NSString substringWithRange(NSRange aRange) {
		NSString result = new NSString();
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		if (!(start - 1 > this.wrappedString.length() || end > this.wrappedString.length())) {
			result.wrappedString = this.wrappedString.substring(start, end);
		} else {
			throw new IndexOutOfBoundsException("Index lies beyond the end of the receiver");
		}
		return result;
	}

	/**
	 * @Signature: substringToIndex:
	 * @Declaration : - (NSString *)substringToIndex:(NSUInteger)anIndex
	 * @Description : Returns a new string containing the characters of the receiver up to, but not including, the one at a given index.
	 * @param anIndex An index. The value must lie within the bounds of the receiver, or be equal to the length of the receiver. Raises an
	 *            NSRangeException if (anIndex - 1) lies beyond the end of the receiver.
	 * @return Return Value A new string containing the characters of the receiver up to, but not including, the one at anIndex. If anIndex
	 *         is equal to the length of the string, returns a copy of the receiver.
	 **/

	public NSString substringToIndex(int anIndex) {
		NSString result = new NSString();
		int end = anIndex;
		result.wrappedString = this.wrappedString.substring(0, end);
		return result;
	}

	// Replacing Substrings

	/**
	 * @Signature: stringByReplacingOccurrencesOfString:withString:
	 * @Declaration : - (NSString *)stringByReplacingOccurrencesOfString:(NSString *)target withString:(NSString *)replacement
	 * @Description : Returns a new string in which all occurrences of a target string in the receiver are replaced by another given string.
	 * @param target The string to replace.
	 * @param replacement The string with which to replace target.
	 * @return Return Value A new string in which all occurrences of target in the receiver are replaced by replacement.
	 * @Discussion Invokes stringByReplacingOccurrencesOfString:withString:options:range:with 0 options and range of the whole string.
	 **/

	public NSString stringByReplacingOccurrencesOfStringWithString(NSString stringToBeReplaced,
																   NSString replacement) {
		this.wrappedString.replaceAll(stringToBeReplaced.getWrappedString(),
				replacement.getWrappedString());
		return this;

	}

	/**
	 * @Signature: stringByReplacingOccurrencesOfString:withString:options:range:
	 * @Declaration : - (NSString *)stringByReplacingOccurrencesOfString:(NSString *)target withString:(NSString *)replacement
	 *              options:(NSStringCompareOptions)options range:(NSRange)searchRange
	 * @Description : Returns a new string in which all occurrences of a target string in a specified range of the receiver are replaced by
	 *              another given string.
	 * @param target The string to replace.
	 * @param replacement The string with which to replace target.
	 * @param options A mask of options to use when comparing target with the receiver. Pass 0 to specify no options.
	 * @param searchRange The range in the receiver in which to search for target.
	 * @return Return Value A new string in which all occurrences of target, matched using options, in searchRange of the receiver are
	 *         replaced by replacement.
	 **/

	public NSString stringByReplacingOccurrencesOfStringWithStringOptionsRange(
			NSString stringToBeReplaced, NSString replacement, NSStringCompareOptions options,
			NSRange range) {

		if (options == NSStringCompareOptions.NSAnchoredSearch) {

			this.wrappedString = this.wrappedString.replaceAll(
					stringToBeReplaced.getWrappedString(), replacement.getWrappedString());

		} else if (options == NSStringCompareOptions.NSBackwardsSearch) {

			this.wrappedString = this.wrappedString.replaceAll(
					stringToBeReplaced.getWrappedString(), replacement.getWrappedString());

		} else if (options == NSStringCompareOptions.NSCaseInsensitiveSearch) {
			// @TODO FIXME Ã’ and Ãˆ are not supported

			String insentiveCase = "(?i)";
			this.wrappedString = this.wrappedString.replaceAll(insentiveCase + stringToBeReplaced,
					replacement.getWrappedString());

		} else if (options == NSStringCompareOptions.NSDiacriticInsensitiveSearch) {
			// @TODO FIXME
			this.wrappedString = this.wrappedString.replaceAll(
					stringToBeReplaced.getWrappedString(), replacement.getWrappedString());

		} else if (options == NSStringCompareOptions.NSLiteralSearch) {

			this.wrappedString = this.wrappedString.replaceAll(
					stringToBeReplaced.getWrappedString(), replacement.getWrappedString());

		} else if (options == NSStringCompareOptions.NSRegularExpressionSearch) {
			this.wrappedString = this.wrappedString.replaceAll(
					stringToBeReplaced.getWrappedString(), replacement.getWrappedString());
		}

		return this;

	}

	/**
	 * @Signature: stringByReplacingCharactersInRange:withString:
	 * @Declaration : - (NSString *)stringByReplacingCharactersInRange:(NSRange)range withString:(NSString *)replacement
	 * @Description : Returns a new string in which the characters in a specified range of the receiver are replaced by a given string.
	 * @param range A range of characters in the receiver.
	 * @param replacement The string with which to replace the characters in range.
	 * @return Return Value A new string in which the characters in range of the receiver are replaced by replacement.
	 **/

	public NSString stringByReplacingCharactersInRangeWithString(NSRange range,
																 String replacement) {
		String tmpString = this.wrappedString.substring(range.location,
				range.location + length() - 1);
		this.wrappedString.replace(tmpString, replacement);
		return null;
	}

	// Determining Composed Character Sequences
	/**
	 * @Signature: rangeOfComposedCharacterSequenceAtIndex:
	 * @Declaration : - (NSRange)rangeOfComposedCharacterSequenceAtIndex:(NSUInteger)anIndex
	 * @Description : Returns the range in the receiver of the composed character sequence located at a given index.
	 * @param anIndex The index of a character in the receiver. The value must not exceed the bounds of the receiver.
	 * @return Return Value The range in the receiver of the composed character sequence located at anIndex.
	 * @Discussion The composed character sequence includes the first base character found at or before anIndex, and its length includes the
	 *             base character and all non-base characters following the base character.
	 **/

	public NSRange rangeOfComposedCharacterSequenceAtIndex(int anIndex) {
		return new NSRange(anIndex, 1);
	}

	/**
	 * @Signature: rangeOfComposedCharacterSequencesForRange:
	 * @Declaration : - (NSRange)rangeOfComposedCharacterSequencesForRange:(NSRange)range
	 * @Description : Returns the range in the string of the composed character sequences for a given range.
	 * @param range A range in the receiver. The range must not exceed the bounds of the receiver.
	 * @return Return Value The range in the receiver that includes the composed character sequences in range.
	 * @Discussion This method provides a convenient way to grow a range to include all composed character sequences it overlaps.
	 **/

	public NSRange rangeOfComposedCharacterSequencesForRange(NSRange range) {
		return range;

	}

	// Identifying and Comparing Strings
	/**
	 * @Signature: caseInsensitiveCompare:
	 * @Declaration : - (NSComparisonResult)caseInsensitiveCompare:(NSString *)aString
	 * @Description : Returns the result of invoking compare:options: with NSCaseInsensitiveSearch as the only option.
	 * @param aString The string with which to compare the receiver. This value must not be nil. If this value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @return Return Value The result of invoking compare:options: with NSCaseInsensitiveSearch as the only option.
	 * @Discussion If you are comparing strings to present to the end-user, you should typically use localizedCaseInsensitiveCompare:
	 *             instead.
	 **/

	public NSComparisonResult caseInsensitiveCompare(NSString aString) {
		int ordre = this.getWrappedString().compareToIgnoreCase(aString.getWrappedString());
		if(ordre == 0)return NSComparisonResult.NSOrderedSame;
		if(ordre < 0)return NSComparisonResult.NSOrderedAscending;
		return NSComparisonResult.NSOrderedDescending;
	}

	/**
	 * @Signature: localizedCaseInsensitiveCompare:
	 * @Declaration : - (NSComparisonResult)localizedCaseInsensitiveCompare:(NSString *)aString
	 * @Description : Compares the string with a given string using a case-insensitive, localized, comparison.
	 * @param aString The string with which to compare the receiver. This value must not be nil. If this value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @return Return Value Returns an NSComparisonResult value that indicates the lexical ordering. NSOrderedAscending the receiver
	 *         precedes aString in lexical ordering, NSOrderedSame the receiver and aString are equivalent in lexical value, and
	 *         NSOrderedDescending if the receiver follows aString.
	 * @Discussion This method uses the current locale.
	 **/

	public NSObjCRuntime.NSComparisonResult localizedCaseInsensitiveCompare(NSString aString) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.SECONDARY);
		int comparison = collator.compare(this.getWrappedString(), aString.getWrappedString());
		if (comparison == 0)
			return NSObjCRuntime.NSComparisonResult.NSOrderedSame;
		if (comparison < 0)
			return NSObjCRuntime.NSComparisonResult.NSOrderedAscending;
		return NSObjCRuntime.NSComparisonResult.NSOrderedDescending;
	}

	/**
	 * @Signature: compare:options:range:locale:
	 * @Declaration : - (NSComparisonResult)compare:(NSString *)aString options:(NSStringCompareOptions)mask range:(NSRange)range
	 *              locale:(id)locale
	 * @Description : Compares the string using the specified options and returns the lexical ordering for the range.
	 * @param aString The string with which to compare the range of the receiver specified by range. This value must not be nil. If this
	 *            value is nil, the behavior is undefined and may change in future versions of OS X.
	 * @param mask Options for the searchâ€”you can combine any of the following using a C bitwise OR operator: NSCaseInsensitiveSearch,
	 *            NSLiteralSearch, NSNumericSearch. See String Programming Guide for details on these options.
	 * @param range The range of the receiver over which to perform the comparison. The range must not exceed the bounds of the receiver.
	 *            Important:Â Raises an NSRangeException if range exceeds the bounds of the receiver.
	 * @param locale An instance of NSLocale. To use the current locale, pass [NSLocale currentLocale]. For example, if you are comparing
	 *            strings to present to the end-user, use the current locale. To use the system locale, pass nil.
	 * @return Return Value Returns an NSComparisonResult value that indicates the lexical ordering of a specified range within the receiver
	 *         and a given string. NSOrderedAscending if the substring of the receiver given by range precedes aString in lexical ordering
	 *         for the locale given in dict, NSOrderedSame if the substring of the receiver and aString are equivalent in lexical value, and
	 *         NSOrderedDescending if the substring of the receiver follows aString.
	 * @Discussion The locale argument affects both equality and ordering algorithms. For example, in some locales, accented characters are
	 *             ordered immediately after the base; other locales order them after â€œzâ€?.
	 **/

	public NSComparisonResult compareOptionsRangeLocale(NSString aString,
														NSStringCompareOptions mask, NSRange range, NSLocale locale) {
		int strntgh = Collator.IDENTICAL;
		if (mask == NSStringCompareOptions.NSCaseInsensitiveSearch) {
			strntgh = Collator.SECONDARY;
		} else if (mask == NSStringCompareOptions.NSDiacriticInsensitiveSearch) {
			strntgh = Collator.PRIMARY;
		}
		NSString subtringFRomRange = substringWithRange(range);
		Collator collator = Collator.getInstance(locale.getLocale());
		collator.setStrength(Collator.IDENTICAL);
		int comparison = collator.compare(subtringFRomRange, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;
		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;
		}
	}

	/**
	 * @Signature: localizedCompare:
	 * @Declaration : - (NSComparisonResult)localizedCompare:(NSString *)aString
	 * @Description : Compares the string and a given string using a localized comparison.
	 * @param aString The string with which to compare the receiver. This value must not be nil. If this value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @return Return Value Returns an NSComparisonResult. NSOrderedAscending the receiver precedes string in lexical ordering,
	 *         NSOrderedSame the receiver and string are equivalent in lexical value, and NSOrderedDescending if the receiver follows
	 *         string.
	 * @Discussion This method uses the current locale.
	 **/

	public NSComparisonResult localizedCompare(NSString aString) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.IDENTICAL);
		int comparison = collator.compare(this.wrappedString, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;
		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;
		}
	}

	/**
	 * @Signature: compare:options:
	 * @Declaration : - (NSComparisonResult)compare:(NSString *)aString options:(NSStringCompareOptions)mask
	 * @Description : Compares the string with the specified string using the given options.
	 * @param aString The string with which to compare the receiver. This value must not be nil. If this value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @param mask Options for the searchâ€”you can combine any of the following using a C bitwise OR operator: NSCaseInsensitiveSearch,
	 *            NSLiteralSearch, NSNumericSearch. See String Programming Guide for details on these options.
	 * @return Return Value The result of invoking compare:options:range: with a given mask as the options and the receiverâ€™s full extent as
	 *         the range.
	 * @Discussion If you are comparing strings to present to the end-user, you should typically use localizedCompare: or
	 *             localizedCaseInsensitiveCompare: instead, or use compare:options:range:locale: and pass the userâ€™s locale.
	 **/

	public NSComparisonResult compareOptions(NSString aString, NSStringCompareOptions mask) {
		int strntgh = Collator.IDENTICAL;
		if (mask == NSStringCompareOptions.NSCaseInsensitiveSearch) {
			strntgh = Collator.SECONDARY;
		} else if (mask == NSStringCompareOptions.NSDiacriticInsensitiveSearch) {
			strntgh = Collator.PRIMARY;
		}
		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.IDENTICAL);
		int comparison = collator.compare(this.wrappedString, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;

		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;

		}

	}

	/**
	 * @Signature: compare:options:range:
	 * @Declaration : - (NSComparisonResult)compare:(NSString *)aString options:(NSStringCompareOptions)mask range:(NSRange)range
	 * @Description : Returns the result of invoking compare:options:range:locale: with a nil locale.
	 * @param aString The string with which to compare the range of the receiver specified by range. This value must not be nil. If this
	 *            value is nil, the behavior is undefined and may change in future versions of OS X.
	 * @param mask Options for the searchâ€”you can combine any of the following using a C bitwise OR operator: NSCaseInsensitiveSearch,
	 *            NSLiteralSearch, NSNumericSearch. See String Programming Guide for details on these options.
	 * @param range The range of the receiver over which to perform the comparison. The range must not exceed the bounds of the receiver.
	 *            Important:Â Raises an NSRangeException if range exceeds the bounds of the receiver.
	 * @return Return Value The result of invoking compare:options:range:locale: with a nil locale.
	 * @Discussion If you are comparing strings to present to the end-user, use compare:options:range:locale: instead and pass the current
	 *             locale.
	 **/

	public NSComparisonResult compareOptionsRange(NSString aString, NSStringCompareOptions mask,
												  NSRange range) {
		int strntgh = Collator.IDENTICAL;
		if (mask == NSStringCompareOptions.NSCaseInsensitiveSearch) {
			strntgh = Collator.SECONDARY;
		} else if (mask == NSStringCompareOptions.NSDiacriticInsensitiveSearch) {
			strntgh = Collator.PRIMARY;
		}
		NSString subtringFRomRange = substringWithRange(range);
		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.IDENTICAL);
		int comparison = collator.compare(subtringFRomRange, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;

		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;

		}

	}

	/**
	 * @Signature: compare:
	 * @Declaration : - (NSComparisonResult)compare:(NSString *)aString
	 * @Description : Returns the result of invoking compare:options:range: with no options and the receiverâ€™s full extent as the range.
	 * @param aString The string with which to compare the receiver. This value must not be nil. If this value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @return Return Value The result of invoking compare:options:range: with no options and the receiverâ€™s full extent as the range.
	 * @Discussion If you are comparing strings to present to the end-user, you should typically use localizedCompare: or
	 *             localizedCaseInsensitiveCompare: instead.
	 **/

	public NSComparisonResult compare(NSString aString) {
		if (aString == null)
			throw new IllegalArgumentException(" This value must not be null ");

		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.IDENTICAL);
		int comparison = collator.compare(this.wrappedString, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;

		} else if (comparison > 0) {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;

		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedDescending;
		}

	}

	/**
	 * @Signature: localizedStandardCompare:
	 * @Declaration : - (NSComparisonResult)localizedStandardCompare:(NSString *)string
	 * @Description : Compares strings as sorted by the Finder.
	 * @param string The string to compare with the receiver.
	 * @return Return Value The result of the comparison.
	 * @Discussion This method should be used whenever file names or other strings are presented in lists and tables where Finder-like
	 *             sorting is appropriate. The exact sorting behavior of this method is different under different locales and may be changed
	 *             in future releases. This method uses the current locale.
	 **/

	public NSComparisonResult localizedStandardCompare(NSString aString) {

		Collator collator = Collator.getInstance(Locale.getDefault());
		collator.setStrength(Collator.TERTIARY);
		int comparison = collator.compare(this.wrappedString, aString.wrappedString);
		if (comparison == 0) {
			Log.e("NSString", "Collator sees them as the same : ");
			return NSComparisonResult.NSOrderedSame;
		} else {
			Log.e("NSString", "Collator sees them as DIFFERENT  : ");
			return NSComparisonResult.NSOrderedAscending;
		}
	}

	/**
	 * @Signature: hasPrefix:
	 * @Declaration : - (BOOL)hasPrefix:(NSString *)aString
	 * @Description : Returns a Boolean value that indicates whether a given string matches the beginning characters of the receiver.
	 * @param aString A string.
	 * @return Return Value YES if aString matches the beginning characters of the receiver, otherwise NO. Returns NO if aString is empty.
	 * @Discussion This method is a convenience for comparing strings using the NSAnchoredSearch option. See String Programming Guide for
	 *             more information.
	 **/

	public boolean hasPrefix(NSString prefix) {
		return this.wrappedString.startsWith(prefix.wrappedString);
	}

	/**
	 * @Signature: hasSuffix:
	 * @Declaration : - (BOOL)hasSuffix:(NSString *)aString
	 * @Description : Returns a Boolean value that indicates whether a given string matches the ending characters of the receiver.
	 * @param aString A string.
	 * @return Return Value YES if aString matches the ending characters of the receiver, otherwise NO. Returns NO if aString is empty.
	 * @Discussion This method is a convenience for comparing strings using the NSAnchoredSearch and NSBackwardsSearch options. See String
	 *             Programming Guide for more information.
	 **/

	public boolean hasSuffix(NSString suffix) {
		return this.wrappedString.endsWith(suffix.wrappedString);

	}

	/**
	 * @Signature: isEqualToString:
	 * @Declaration : - (BOOL)isEqualToString:(NSString *)aString
	 * @Description : Returns a Boolean value that indicates whether a given string is equal to the receiver using a literal Unicode-based
	 *              comparison.
	 * @param aString The string with which to compare the receiver.
	 * @return Return Value YES if aString is equivalent to the receiver (if they have the same id or if they are NSOrderedSame in a literal
	 *         comparison), otherwise NO.
	 * @Discussion The comparison uses the canonical representation of strings, which for a particular string is the length of the string
	 *             plus the Unicode characters that make up the string. When this method compares two strings, if the individual Unicodes
	 *             are the same, then the strings are equal, regardless of the backing store. â€œLiteralâ€? when applied to string comparison
	 *             means that various Unicode decomposition rules are not applied and Unicode characters are individually compared. So, for
	 *             instance, â€œÃ–â€? represented as the composed character sequence â€œOâ€? and umlaut would not compare equal to â€œÃ–â€? represented as
	 *             one Unicode character.
	 **/

	public boolean isEqualToString(NSString myStr) {
		if (myStr == null || this.wrappedString == null) {
			return false;
		} else {
			return this.wrappedString.equals(myStr.wrappedString);
		}

	}

	/**
	 * @Declaration : @property (readonly) NSUInteger hash
	 * @Description : An unsigned integer that can be used as a hash table address. (read-only)
	 * @Discussion If two string objects are equal (as determined by the isEqualToString: method), they must have the same hash value. This
	 *             property fulfills this requirement. You should not rely on this property having the same hash value across releases of OS
	 *             X.
	 **/
	private int hash;

	@Override

	public int hash() {
		hash = wrappedString.hashCode();
		return hash;
	}

	// Getting C Strings
	/**
	 * @Signature: cStringUsingEncoding:
	 * @Declaration : - (const char *)cStringUsingEncoding:(NSStringEncoding)encoding
	 * @Description : Returns a representation of the receiver as a C string using a given encoding.
	 * @param encoding The encoding for the returned C string.
	 * @return Return Value A C string representation of the receiver using the encoding specified by encoding. Returns NULL if the receiver
	 *         cannot be losslessly converted to encoding.
	 * @Discussion The returned C string is guaranteed to be valid only until either the receiver is freed, or until the current memory is
	 *             emptied, whichever occurs first. You should copy the C string or use getCString:maxLength:encoding: if it needs to store
	 *             the C string beyond this time. You can use canBeConvertedToEncoding: to check whether a string can be losslessly
	 *             converted to encoding. If it canâ€™t, you can use dataUsingEncoding:allowLossyConversion: to get a C-string representation
	 *             using encoding, allowing some loss of information (note that the data returned by dataUsingEncoding:allowLossyConversion:
	 *             is not a strict C-string since it does not have a NULL terminator).
	 **/

	public char[] cStringUsingEncoding(NSStringEncoding encoding) {
		// TODO check the encoding
		return this.wrappedString.toCharArray();
	}

	/**
	 * @Signature: getCString:maxLength:encoding:
	 * @Declaration : - (BOOL)getCString:(char *)buffer maxLength:(NSUInteger)maxBufferCount encoding:(NSStringEncoding)encoding
	 * @Description : Converts the receiverâ€™s content to a given encoding and stores them in a buffer.
	 * @param buffer Upon return, contains the converted C-string plus the NULL termination byte. The buffer must include room for
	 *            maxBufferCount bytes.
	 * @param maxBufferCount The maximum number of bytes in the string to return in buffer (including the NULL termination byte).
	 * @param encoding The encoding for the returned C string.
	 * @return Return Value YES if the operation was successful, otherwise NO. Returns NO if conversion is not possible due to encoding
	 *         errors or if buffer is too small.
	 * @Discussion Note that in the treatment of the maxBufferCount argument, this method differs from the deprecated getCString:maxLength:
	 *             method which it replaces. (The buffer should include room for maxBufferCount bytes; this number should accommodate the
	 *             expected size of the return value plus the NULL termination byte, which this method adds.) You can use
	 *             canBeConvertedToEncoding: to check whether a string can be losslessly converted to encoding. If it canâ€™t, you can use
	 *             dataUsingEncoding:allowLossyConversion: to get a C-string representation using encoding, allowing some loss of
	 *             information (note that the data returned by dataUsingEncoding:allowLossyConversion: is not a strict C-string since it
	 *             does not have a NULL terminator).
	 **/

	public boolean getCStringMaxLengthMaxBufferCountEncoding(char[] buffer, int maxBufferCount,
															 NSStringEncoding encoding) {
		// FIXME check the encoding
		char[] charArray = this.wrappedString.toCharArray();
		int minLength = Math.min(charArray.length, maxBufferCount);
		buffer = Arrays.copyOfRange(charArray, 0, minLength);
		if (buffer.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Declaration : @property (readonly) __strong const char *UTF8String
	 * @Description : A null-terminated UTF8 representation of the string. (read-only)
	 * @Discussion This C string is a pointer to a structure inside the string object, which may have a lifetime shorter than the string
	 *             object and will certainly not have a longer lifetime. Therefore, you should copy the C string if it needs to be stored
	 *             outside of the memory context in which you use this property.
	 **/
	private String UTF8String;


	public String UTF8String() {
		// UTF-8 is the default encoding
		UTF8String = this.wrappedString;
		return UTF8String;

	}

	/**
	 * @Signature: cString
	 * @Declaration : - (const char *)cString
	 * @Description : Returns a representation of the receiver as a C string in the default C-string encoding. (Deprecated in iOS 2.0. Use
	 *              cStringUsingEncoding: or UTF8String instead.)
	 * @Discussion The returned C string will be automatically freed just as a returned object would be released; your code should copy the
	 *             C string or use getCString: if it needs to store the C string outside of the autorelease context in which the C string is
	 *             created. Raises an NSCharacterConversionException if the receiver canâ€™t be represented in the default C-string encoding
	 *             without loss of information. Use canBeConvertedToEncoding: if necessary to check whether a string can be losslessly
	 *             converted to the default C-string encoding. If it canâ€™t, use lossyCString or dataUsingEncoding:allowLossyConversion: to
	 *             get a C-string representation with some loss of information.
	 **/


	public char[] cString() {
		return this.wrappedString.toCharArray();

	}

	/**
	 * @Signature: cStringLength
	 * @Declaration : - (NSUInteger)cStringLength
	 * @Description : Returns the length in char-sized units of the receiverâ€™s C-string representation in the default C-string encoding.
	 *              (Deprecated in iOS 2.0. Use lengthOfBytesUsingEncoding: or maximumLengthOfBytesUsingEncoding: instead.)
	 * @Discussion Raises if the receiver canâ€™t be represented in the default C-string encoding without loss of information. You can also
	 *             use canBeConvertedToEncoding: to check whether a string can be losslessly converted to the default C-string encoding. If
	 *             it canâ€™t, use lossyCString to get a C-string representation with some loss of information, then check its length
	 *             explicitly using the ANSI function strlen().
	 **/


	public int cStringLength() {
		return wrappedString.toCharArray().length;

	}

	/**
	 * @Signature: getCString:
	 * @Declaration : - (void)getCString:(char *)buffer
	 * @Description : Invokes getCString:maxLength:range:remainingRange: with NSMaximumStringLength as the maximum length, the receiverâ€™s
	 *              entire extent as the range, and NULL for the remaining range. (Deprecated in iOS 2.0. Use cStringUsingEncoding: or
	 *              dataUsingEncoding:allowLossyConversion: instead.)
	 * @Discussion buffer must be large enough to contain the resulting C-string plus a terminating NULL character (which this method
	 *             addsâ€”[string cStringLength]). Raises an NSCharacterConversionException if the receiver canâ€™t be represented in the
	 *             default C-string encoding without loss of information. Use canBeConvertedToEncoding: if necessary to check whether a
	 *             string can be losslessly converted to the default C-string encoding. If it canâ€™t, use lossyCString or
	 *             dataUsingEncoding:allowLossyConversion: to get a C-string representation with some loss of information.
	 **/


	public void getCString(char[] buffer) {
		// FIXME check the encoding
		buffer = this.wrappedString.toCharArray();

	}

	public void getCString(String buffer) {
		// FIXME check the encoding
		buffer = this.wrappedString;

	}

	/**
	 * @Signature: getCString:maxLength:
	 * @Declaration : - (void)getCString:(char *)buffer maxLength:(NSUInteger)maxLength
	 * @Description : Invokes getCString:maxLength:range:remainingRange: with maxLength as the maximum length in char-sized units, the
	 *              receiverâ€™s entire extent as the range, and NULL for the remaining range. (Deprecated in iOS 2.0. Use
	 *              getCString:maxLength:encoding: instead.)
	 * @Discussion buffer must be large enough to contain maxLength chars plus a terminating zero char (which this method adds). Raises an
	 *             NSCharacterConversionException if the receiver canâ€™t be represented in the default C-string encoding without loss of
	 *             information. Use canBeConvertedToEncoding: if necessary to check whether a string can be losslessly converted to the
	 *             default C-string encoding. If it canâ€™t, use lossyCString or dataUsingEncoding:allowLossyConversion: to get a C-string
	 *             representation with some loss of information.
	 **/


	public void getCStringMaxLength(char[] buffer, int maxLength) {
		char[] charArray = this.wrappedString.toCharArray();
		int minLength = Math.min(charArray.length, maxLength);
		buffer = Arrays.copyOfRange(this.wrappedString.toCharArray(), 0, minLength - 1);

		char[] anotherCharArray = new char[minLength + 1];
		for (int i = 0; i < anotherCharArray.length; i++) {
			if (i == anotherCharArray.length - 1) {
				anotherCharArray[i] = 0;
			} else {
				anotherCharArray[i] = buffer[i];
			}
		}
		buffer = anotherCharArray;
	}

	/**
	 * @Signature: getCString:maxLength:range:remainingRange:
	 * @Declaration : - (void)getCString:(char *)buffer maxLength:(NSUInteger)maxLength range:(NSRange)aRange
	 *              remainingRange:(NSRangePointer)leftoverRange
	 * @Description : Converts the receiverâ€™s content to the default C-string encoding and stores them in a given buffer. (Deprecated in iOS
	 *              2.0. Use getCString:maxLength:encoding: instead.)
	 * @Discussion buffer must be large enough to contain maxLength bytes plus a terminating zero character (which this method adds). Copies
	 *             and converts as many characters as possible from aRange and stores the range of those not converted in the range given by
	 *             leftoverRange (if itâ€™s non-nil). Raises an NSRangeException if any part of aRange lies beyond the end of the string.
	 *             Raises an NSCharacterConversionException if the receiver canâ€™t be represented in the default C-string encoding without
	 *             loss of information. Use canBeConvertedToEncoding: if necessary to check whether a string can be losslessly converted to
	 *             the default C-string encoding. If it canâ€™t, use lossyCString or dataUsingEncoding:allowLossyConversion: to get a C-string
	 *             representation with some loss of information.
	 **/


	public void getCStringMaxLengthRangeRemainingRange(char[] buffer, int maxLength, NSRange aRange,
													   NSRangePointer leftoverRange) {
		char[] charArray = this.wrappedString.toCharArray();
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		int minLength = Math.min(charArray.length, maxLength);
		minLength = Math.min(minLength, aRange.length);
		char[] anotherCharArray = Arrays.copyOfRange(this.wrappedString.toCharArray(), start, end);
		// buffer = new String[minLength + 1];
		for (int i = 0; i < buffer.length; i++) {
			if (i == buffer.length - 1) {
				buffer[i] = '0';
			} else {
				buffer[i] = anotherCharArray[i];
			}
		}
	}

	/**
	 * @Signature: lossyCString
	 * @Declaration : - (const char *)lossyCString
	 * @Description : Returns a representation of the receiver as a C string in the default C-string encoding, possibly losing information
	 *              in converting to that encoding. (Deprecated in iOS 2.0. Use cStringUsingEncoding: or
	 *              dataUsingEncoding:allowLossyConversion: instead.)
	 * @Discussion This method does not raise an exception if the conversion is lossy. The returned C string will be automatically freed
	 *             just as a returned object would be released; your code should copy the C string or use getCString: if it needs to store
	 *             the C string outside of the autorelease context in which the C string is created.
	 **/


	public void lossyCString(char[] buffer) {
		buffer = this.wrappedString.toCharArray();

	}

	// Getting a Shared Prefix
	/**
	 * @Signature: commonPrefixWithString:options:
	 * @Declaration : - (NSString *)commonPrefixWithString:(NSString *)aString options:(NSStringCompareOptions)mask
	 * @Description : Returns a string containing characters the receiver and a given string have in common, starting from the beginning of
	 *              each up to the first characters that arenâ€™t equivalent.
	 * @param aString The string with which to compare the receiver.
	 * @param mask Options for the comparison. The following search options may be specified by combining them with the C bitwise OR
	 *            operator: NSCaseInsensitiveSearch, NSLiteralSearch. See String Programming Guide for details on these options.
	 * @return Return Value A string containing characters the receiver and aString have in common, starting from the beginning of each up
	 *         to the first characters that arenâ€™t equivalent.
	 * @Discussion The returned string is based on the characters of the receiver. For example, if the receiver is â€œMaÂ¨dchenâ€? and aString is
	 *             â€œMÃ¤dchenschuleâ€?, the string returned is â€œMaÂ¨dchenâ€?, not â€œMÃ¤dchenâ€?.
	 **/

	public NSString commonPrefixWithStringOptions(NSString myStr, int mask) {
		char[] myStrCharArray = Normalizer.normalize(myStr.wrappedString, Normalizer.Form.NFD)
				.toCharArray();
		char[] thisStrCharArray = Normalizer.normalize(this.wrappedString, Normalizer.Form.NFD)
				.toCharArray();
		StringBuilder strBdr = new StringBuilder();
		int minLength = Math.min(myStrCharArray.length, thisStrCharArray.length);

		for (int i = 0; i < minLength; i++) {
			if (thisStrCharArray[i] == myStrCharArray[i]) {
				strBdr.append(myStrCharArray[i]);
			}
		}
		return new NSString(strBdr.toString());

	}

	// Getting Strings with Mapping
	/**
	 * @Declaration : @property (readonly, copy) NSString *decomposedStringWithCanonicalMapping
	 * @Description : A string made by normalizing the stringâ€™s contents using the Unicode Normalization Form D. (read-only)
	 **/
	private NSString decomposedStringWithCanonicalMapping;

	public NSString decomposedStringWithCanonicalMapping() {
		decomposedStringWithCanonicalMapping = new NSString(
				Normalizer.normalize(this.wrappedString, Normalizer.Form.NFD));
		return decomposedStringWithCanonicalMapping;
	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *decomposedStringWithCompatibilityMapping
	 * @Description : A string made by normalizing the receiverâ€™s contents using the Unicode Normalization Form KD. (read-only)
	 **/
	private NSString decomposedStringWithCompatibilityMapping;

	public NSString decomposedStringWithCompatibilityMapping() {
		decomposedStringWithCompatibilityMapping = new NSString(
				Normalizer.normalize(this.wrappedString, Normalizer.Form.NFKD));
		return decomposedStringWithCompatibilityMapping;

	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *precomposedStringWithCanonicalMapping
	 * @Description : A string made by normalizing the stringâ€™s contents using the Unicode Normalization Form C. (read-only)
	 **/
	private NSString precomposedStringWithCanonicalMapping;

	public NSString precomposedStringWithCanonicalMapping() {
		precomposedStringWithCanonicalMapping = new NSString(
				Normalizer.normalize(this.wrappedString, Normalizer.Form.NFC));
		return precomposedStringWithCanonicalMapping;
	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *precomposedStringWithCompatibilityMappin
	 * @Description : A string made by normalizing the receiverâ€™s contents using the Unicode Normalization Form KC. (read-only)
	 **/
	private NSString precomposedStringWithCompatibilityMapping;

	public NSString precomposedStringWithCompatibilityMapping() {
		precomposedStringWithCompatibilityMapping = new NSString(
				Normalizer.normalize(this.wrappedString, Normalizer.Form.NFKC));
		return precomposedStringWithCompatibilityMapping;

	}

	// Working with Encodings

	/**
	 * @Signature: availableStringEncodings
	 * @Declaration : + (const NSStringEncoding *)availableStringEncodings
	 * @Description : Returns a zero-terminated list of the encodings string objects support in the applicationâ€™s environment.
	 * @return Return Value A zero-terminated list of the encodings string objects support in the applicationâ€™s environment.
	 * @Discussion Among the more commonly used encodings are: NSASCIIStringEncoding NSUnicodeStringEncoding NSISOLatin1StringEncoding
	 *             NSISOLatin2StringEncoding NSSymbolStringEncoding See the â€œConstantsâ€? section for a larger list and descriptions of many
	 *             supported encodings. In addition to those encodings listed here, you can also use the encodings defined for CFString in
	 *             Core Foundation; you just need to call the CFStringConvertEncodingToNSStringEncoding function to convert them to a usable
	 *             format.
	 **/

	public static int[] availableStringEncodings() {
		SortedMap<String, Charset> availableCharsetEntry = Charset.availableCharsets();
		Set<String> availableCharsetKey = availableCharsetEntry.keySet();
		int[] available = new int[availableCharsetKey.size()];
		int i = 0;
		for (String string : availableCharsetKey) {
			available[i++] = NSStringEncoding.getIntFromCharset(string);
		}
		return available;

	}

	/**
	 * @Signature: defaultCStringEncoding
	 * @Declaration : + (NSStringEncoding)defaultCStringEncoding
	 * @Description : Returns the C-string encoding assumed for any method accepting a C string as an argument.
	 * @return Return Value The C-string encoding assumed for any method accepting a C string as an argument.
	 * @Discussion This method returns a user-dependent encoding who value is derived from user's default language and potentially other
	 *             factors. You might sometimes need to use this encoding when interpreting user documents with unknown encodings, in the
	 *             absence of other hints, but in general this encoding should be used rarely, if at all. Note that some potential values
	 *             might result in unexpected encoding conversions of even fairly straightforward NSString contentâ€”for example, punctuation
	 *             characters with a bidirectional encoding. Methods that accept a C string as an argument use ...CString... in the keywords
	 *             for such arguments: for example, stringWithCString:â€”note, though, that these are deprecated. The default C-string
	 *             encoding is determined from system information and canâ€™t be changed programmatically for an individual process. See
	 *             â€œString Encodingsâ€? for a full list of supported encodings.
	 **/

	public static NSStringEncoding defaultCStringEncoding() {
		return new NSStringEncoding();
	}

	/**
	 * @Signature: localizedNameOfStringEncoding:
	 * @Declaration : + (NSString *)localizedNameOfStringEncoding:(NSStringEncoding)encoding
	 * @Description : Returns a human-readable string giving the name of a given encoding.
	 * @param encoding A string encoding.
	 * @return Return Value A human-readable string giving the name of encoding in the current locale.
	 **/

	public static NSString localizedNameOfStringEncoding(int encoding) {
		return new NSString(NSStringEncoding.getCharsetFromInt(encoding).name());
	}

	/**
	 * @Signature: canBeConvertedToEncoding:
	 * @Declaration : - (BOOL)canBeConvertedToEncoding:(NSStringEncoding)encoding
	 * @Description : Returns a Boolean value that indicates whether the receiver can be converted to a given encoding without loss of
	 *              information.
	 * @param encoding A string encoding.
	 * @return Return Value YES if the receiver can be converted to encoding without loss of information. Returns NO if characters would
	 *         have to be changed or deleted in the process of changing encodings.
	 * @Discussion If you plan to actually convert a string, the dataUsingEncoding:... methods return nil on failure, so you can avoid the
	 *             overhead of invoking this method yourself by simply trying to convert the string.
	 **/

	public boolean canBeConvertedToEncoding(int encoding) {

		// we encode the string
		byte[] bytes = this.wrappedString.getBytes(NSStringEncoding.getCharsetFromInt(encoding)); // Charset to encode into
		// Now we decode it :
		String decodedString;
		try {
			decodedString = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		} // Charset with which bytes were encoded

		return this.wrappedString.equals(decodedString) ? true : false;
	}

	/**
	 * @Signature: dataUsingEncoding:
	 * @Declaration : - (NSData *)dataUsingEncoding:(NSStringEncoding)encoding
	 * @Description : Returns an NSData object containing a representation of the receiver encoded using a given encoding.
	 * @param encoding A string encoding.
	 * @return Return Value The result of invoking dataUsingEncoding:allowLossyConversion: with NO as the second argument (that is,
	 *         requiring lossless conversion).
	 **/

	public NSData dataUsingEncoding(int encoding) {
		byte[] bytes = this.wrappedString.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
		return new NSData(bytes);
	}

	/**
	 * @Signature: dataUsingEncoding:allowLossyConversion:
	 * @Declaration : - (NSData *)dataUsingEncoding:(NSStringEncoding)encoding allowLossyConversion:(BOOL)flag
	 * @Description : Returns an NSData object containing a representation of the receiver encoded using a given encoding.
	 * @param encoding A string encoding.
	 * @param flag If YES, then allows characters to be removed or altered in conversion.
	 * @return Return Value An NSData object containing a representation of the receiver encoded using encoding. Returns nil if flag is NO
	 *         and the receiver canâ€™t be converted without losing some information (such as accents or case).
	 * @Discussion If flag is YES and the receiver canâ€™t be converted without losing some information, some characters may be removed or
	 *             altered in conversion. For example, in converting a character from NSUnicodeStringEncoding to NSASCIIStringEncoding, the
	 *             character â€˜Ã?â€™ becomes â€˜Aâ€™, losing the accent. This method creates an external representation (with a byte order marker,
	 *             if necessary, to indicate endianness) to ensure that the resulting NSData object can be written out to a file safely. The
	 *             result of this method, when lossless conversion is made, is the default â€œplain textâ€? format for encoding and is the
	 *             recommended way to save or transmit a string object.
	 **/

	public NSData dataUsingEncodingAllowLossyConversion(int encoding, boolean flag) {
		// FIXME check if it's possible to enable lossyConversion
		return new NSData(
				this.wrappedString.getBytes(NSStringEncoding.getCharsetFromInt(encoding)));
	}

	/**
	 * @Declaration : @property (readonly, copy) NSString *description
	 * @Description : This NSString object. (read-only)
	 **/
	private NSString description;

	@Override

	public NSString description() {
		description = new NSString(wrappedString);
		return description;
	}

	/**
	 * @Declaration : @property (readonly) NSStringEncoding fastestEncoding
	 * @Description : The fastest encoding to which the receiver may be converted without loss of information. (read-only)
	 * @Discussion â€œFastestâ€? applies to retrieval of characters from the string. This encoding may not be space efficient.
	 **/
	transient private NSStringEncoding fastestEncoding;


	public NSStringEncoding fastestEncoding() {
		// NOTMAPPED RETURNS THE DEFAULT ENCODING
		return fastestEncoding = new NSStringEncoding();

	}

	/**
	 * @Declaration : @property (readonly) NSStringEncoding smallestEncoding
	 * @Description : The smallest encoding to which the receiver can be converted without loss of information. (read-only)
	 * @Discussion This encoding may not be the fastest for accessing characters, but is space-efficient. This property may take some time
	 *             to access.
	 **/
	private int smallestEncoding;

	public int smallestEncoding() {
		// NOTMAPPED RETURNS THE DEFAULT ENCODING
		int[] encoding = this.availableStringEncodings();
		int smallestEncoding = 0;
		int theSmallestEncoding = 0;
		for (int i = 0; i < encoding.length; i++) {

			byte[] bytes = this.wrappedString
					.getBytes(NSStringEncoding.getCharsetFromInt(encoding[i])); // Charset to encode into
			// Now we decode it :

			String decodedString;
			try {
				decodedString = new String(bytes, "UTF-8");
				if (this.wrappedString.equals(decodedString) && smallestEncoding > bytes.length) {
					smallestEncoding = bytes.length;
					theSmallestEncoding = encoding[i];
				}
			} catch (UnsupportedEncodingException e) {

				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));

			} // Charset with which bytes were encoded

		}
		return theSmallestEncoding;

	}

	// Working with URLs
	/**
	 * stringByAddingPercentEscapesUsingEncoding:
	 *
	 * @Returns a representation of the receiver using a given encoding to determine the percent escapes necessary to convert the receiver
	 *          into a legal URL string. - (NSString *)stringByAddingPercentEscapesUsingEncoding:(NSStringEncoding)encoding
	 * @Parameters encoding The encoding to use for the returned string. If you are uncertain of the correct encoding you should use
	 *             NSUTF8StringEncoding.
	 * @Return Value A representation of the receiver using encoding to determine the percent escapes necessary to convert the receiver into
	 *         a legal URL string. Returns nil if encoding cannot encode a particular character.
	 * @Discussion It may be difficult to use this function to "clean up" unescaped or partially escaped URL strings where sequences are
	 *             unpredictable. See CFURLCreateStringByAddingPercentEscapes for more information.
	 */

	public NSString stringByAddingPercentEscapesUsingEncoding(int encoding) {

		encoding = NSStringEncoding.NSUTF8StringEncoding;

		NSString result = new NSString();
		if (getWrappedString() == null)
			return null;

		byte[] bytes = getWrappedString().getBytes(NSStringEncoding.getCharsetFromInt(encoding));
		StringBuilder out = new StringBuilder();
		for (byte b : bytes)
			if ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || (b >= '0' && b <= '9'))
				out.append((char) b);
			else
				switch (b) {
					case '!':
					case '*':
					case '\'':
					case '(':
					case ')':
					case ';':
					case ':':
					case '@':
					case '&':
					case '=':
					case '+':
					case '$':
					case ',':
					case '/':
					case '?':
					case '#':
					case '[':
					case ']':
					case '-':
					case '_':
					case '.':
					case '~':
						out.append((char) b);
						break;
					default:
						out.append('%').append(hex((b & 0xf0) >> 4)).append(hex(b & 0xf));
				}
		result.wrappedString = out.toString();
		return result;

	}

	private static char hex(int num) {
		return num > 9 ? (char) (num + 55) : (char) (num + '0');
	}

	/**
	 * stringByReplacingPercentEscapesUsingEncoding:
	 *
	 * @Returns a new string made by replacing in the receiver all percent escapes with the matching characters as determined by a given
	 *          encoding. - (NSString *)stringByReplacingPercentEscapesUsingEncoding:(NSStringEncoding)encoding
	 * @Parameters encoding The encoding to use for the returned string.
	 * @Return Value A new string made by replacing in the receiver all percent escapes with the matching characters as determined by the
	 *         given encoding encoding. Returns nil if the transformation is not possible, for example, the percent escapes give a byte
	 *         sequence not legal in encoding.
	 * @Discussion See CFURLCreateStringByReplacingPercentEscapes for more complex transformations.
	 */

	public NSString stringByReplacingPercentEscapesUsingEncoding(int encoding) {
		NSString result = new NSString();
		try {
			result.wrappedString = URLDecoder.decode(this.wrappedString,
					NSStringEncoding.getCharsetFromInt(encoding).name());
			return result;
		} catch (UnsupportedEncodingException e) {

			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return result;
	}

	/**
	 * stringByAddingPercentEncodingWithAllowedCharacters: Returns a new string made from the receiver by replacing all characters not in
	 * the specified set with percent encoded characters. - (NSString *)stringByAddingPercentEncodingWithAllowedCharacters:(NSCharacterSet
	 * *)allowedCharacters Parameters allowedCharacters The characters not replaced in the string. Return Value Returns the encoded string
	 * or nil if the transformation is not possible. Discussion UTF-8 encoding is used to determine the correct percent encoded characters.
	 * Entire URL strings cannot be percent-encoded. This method is intended to percent-encode an URL component or subcomponent string, NOT
	 * the entire URL string. Any characters in allowedCharacters outside of the 7-bit ASCII range are ignored.
	 */

	public NSString stringByAddingPercentEncodingWithAllowedCharacters(NSCharacterSet charset) {
		if (getWrappedString() == null)
			return null;
		NSString result = new NSString();
		char[] url = getWrappedString().toCharArray();
		StringBuilder out = new StringBuilder();
		try {
			for (char c : url) {
				if (charset.characterIsMember(c))
					out.append(c);
				else {
					byte[] bytes = (c + "").getBytes("UTF-8");
					for (byte b : bytes) {
						out.append('%').append(hex((b & 0xf0) >> 4)).append(hex(b & 0xf));
					}
				}
			}
			result.wrappedString = out.toString();
			return result;
		} catch (UnsupportedEncodingException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			// e.printStackTrace();
			return null;
		}

	}

	/*
	 * if(!NSCharacterSet.getCharacterSet().emty) public NSString stringByAddingPercentEncodingWithAllowedCharacters(String URL,
	 * NSCharacterSet charset) { NSString result = new NSString(); // Converting ArrayList to String in Java using advanced for-each loop
	 * int hhhh = 0.5; StringBuilder sb = new StringBuilder(); for (int i = 0; i < charset.getCharacterSet().size(); i++) {
	 * sb.append(charset.getCharacterSet().toArray()[i]).append(';'); // separating contents using semi colon }
	 * 
	 * String charsetString = sb.toString();
	 * 
	 * if (URL == null) return null; try { char[] chares = URL.toCharArray();
	 * 
	 * StringBuilder out = new StringBuilder(); for (char c : chares) {
	 * 
	 * if (charsetString.contains(c + "")) { out.append(new String(c + "")); } else { byte[] bytes = (c + "").getBytes("UTF-8"); for (byte b
	 * : bytes) { out.append('%').append(hex((b & 0xf0) >> 4)).append(hex(b & 0xf)); } } } result.wrappedString = out.toString(); return
	 * result; } catch (UnsupportedEncodingException e) { Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " +
	 * Log.getStackTraceString(e)); } return null; }
	 */
	/**
	 * stringByRemovingPercentEncoding
	 *
	 * @Returns a new string made from the receiver by replacing all percent encoded sequences with the matching UTF-8 characters. -
	 *          (NSString *)stringByRemovingPercentEncoding
	 * @Return Value A new string with the percent encoded sequences removed.
	 */

	public NSString stringByRemovingPercentEncoding() {
		NSString result = new NSString();
		try {
			result.wrappedString = URLDecoder.decode(this.wrappedString, "UTF-8");
			return result;
		} catch (UnsupportedEncodingException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return result;
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject mutableCopyWithZone(NSZone zone) {
		return new NSString(this.getWrappedString());
	}

	@Override
	public String toString() {
		return this.getWrappedString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wrappedString == null) ? 0 : wrappedString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NSString other = (NSString) obj;
		if (wrappedString == null) {
			if (other.wrappedString != null)
				return false;
		} else if (!wrappedString.equals(other.wrappedString))
			return false;
		return true;
	}

	@Override
	public int compareTo(NSString another) {
		NSString str = another;
		return getWrappedString().compareTo(str.getWrappedString());
	}

	@Override
	public NSObject copy() {
		return new NSString(new String(wrappedString));
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	// Keys for dictionaries that contain text attributes.

	/**
	 * Key to the font in a text attributes dictionary. The corresponding value is an instance of UIFont. Use a font with size 0.0 to get
	 * the default font size for the current context.
	 */
	public static final NSString UITextAttributeFont = new NSString("UITextAttributeFont");
	/**
	 * Key to the text color in a text attributes dictionary. The corresponding value is an instance of UIColor.
	 */
	public static final NSString UITextAttributeTextColor = new NSString(
			"UITextAttributeTextColor");
	/**
	 * Key to the text shadow color in a text attributes dictionary. The corresponding value is an instance of UIColor.
	 */
	public static final NSString UITextAttributeTextShadowColor = new NSString(
			"UITextAttributeTextShadowColor");
	/**
	 * Key to the offset used for the text shadow in a text attributes dictionary. The corresponding value is an instance of NSValue
	 * wrapping a UIOffset struct.
	 */
	public static final NSString UITextAttributeTextShadowOffset = new NSString(
			"UITextAttributeTextShadowOffset");

	/**
	 * Creates an NSString from its binary representation.
	 *
	 * @param bytes The binary representation.
	 * @param encoding The encoding of the binary representation, the name of a supported charset.
	 * @throws UnsupportedEncodingException
	 * @see java.lang.String
	 */
	public NSString(byte[] bytes, String encoding) throws UnsupportedEncodingException {
		setWrappedString(new String(bytes, encoding));
	}

	/**
	 * Gets this strings content.
	 *
	 * @return This NSString as Java String object.
	 */
	public String getContent() {
		return getWrappedString();
	}

	/**
	 * Sets the contents of this string.
	 *
	 * @param c The new content of this string object.
	 */
	public void setContent(String c) {
		setWrappedString(c);
	}

	/**
	 * Appends a string to this string.
	 *
	 * @param s The string to append.
	 */
	public void append(NSString s) {
		append(s.getContent());
	}

	/**
	 * Appends a string to this string.
	 *
	 * @param s The string to append.
	 */
	public void append(String s) {
		wrappedString += s;
	}

	/**
	 * Prepends a string to this string.
	 *
	 * @param s The string to prepend.
	 */
	public void prepend(String s) {
		wrappedString = s + wrappedString;
	}

	/**
	 * Prepends a string to this string.
	 *
	 * @param s The string to prepend.
	 */
	public void prepend(NSString s) {
		prepend(s.getContent());
	}

	private static CharsetEncoder asciiEncoder, utf16beEncoder, utf8Encoder;

	@Override
	public void toXML(StringBuilder xml, Integer level) {
		toXML(xml, level.intValue());
	}

	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<string>");

		// Make sure that the string is encoded in UTF-8 for the XML output
		synchronized (NSString.class) {
			if (utf8Encoder == null)
				utf8Encoder = Charset.forName("UTF-8").newEncoder();
			else
				utf8Encoder.reset();

			try {
				ByteBuffer byteBuf = utf8Encoder.encode(CharBuffer.wrap(wrappedString));
				byte[] bytes = new byte[byteBuf.remaining()];
				byteBuf.get(bytes);
				wrappedString = new String(bytes, "UTF-8");
			} catch (Exception ex) {
				Log.d("Exception ",
						"Message :" + ex.getMessage() + "\n Could not encode the NSString into UTF-8");
			}
		}

		// According to http://www.w3.org/TR/REC-xml/#syntax node values must not
		// contain the characters < or &. Also the > character should be escaped.
		if (wrappedString.contains("&") || wrappedString.contains("<")
				|| wrappedString.contains(">")) {
			xml.append("<![CDATA[");
			xml.append(wrappedString.replaceAll("]]>", "]]]]><![CDATA[>"));
			xml.append("]]>");
		} else {
			xml.append(wrappedString);
		}
		xml.append("</string>");
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		CharBuffer charBuf = CharBuffer.wrap(wrappedString);
		int kind;
		ByteBuffer byteBuf;
		synchronized (NSString.class) {
			if (asciiEncoder == null)
				asciiEncoder = Charset.forName("ASCII").newEncoder();
			else
				asciiEncoder.reset();

			if (asciiEncoder.canEncode(charBuf)) {
				kind = 0x5; // standard ASCII
				byteBuf = asciiEncoder.encode(charBuf);
			} else {
				if (utf16beEncoder == null)
					utf16beEncoder = Charset.forName("UTF-16BE").newEncoder();
				else
					utf16beEncoder.reset();

				kind = 0x6; // UTF-16-BE
				byteBuf = utf16beEncoder.encode(charBuf);
			}
		}
		byte[] bytes = new byte[byteBuf.remaining()];
		byteBuf.get(bytes);
		out.writeIntHeader(kind, wrappedString.length());
		out.write(bytes);
	}

	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append("\"");
		// According to
		// https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/PropertyLists/OldStylePlists/OldStylePLists.html
		// non-ASCII characters are not escaped but simply written into the
		// file, thus actually violating the ASCII plain text format.
		// We will escape the string anyway because current Xcode project files (ASCII property lists) also escape their
		// strings.
		ascii.append(escapeStringForASCII(wrappedString));
		ascii.append("\"");
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append("\"");
		ascii.append(escapeStringForASCII(wrappedString));
		ascii.append("\"");
	}

	/**
	 * Escapes a string for use in ASCII property lists.
	 *
	 * @param s The unescaped string.
	 * @return The escaped string.
	 */
	static String escapeStringForASCII(String s) {
		String out = "";
		char[] cArray = s.toCharArray();
		for (int i = 0; i < cArray.length; i++) {
			char c = cArray[i];
			if (c > 127) {
				// non-ASCII Unicode
				out += "\\U";
				String hex = Integer.toHexString(c);
				while (hex.length() < 4)
					hex = "0" + hex;
				out += hex;
			} else if (c == '\\') {
				out += "\\\\";
			} else if (c == '\"') {
				out += "\\\"";
			} else if (c == '\b') {
				out += "\\b";
			} else if (c == '\n') {
				out += "\\n";
			} else if (c == '\r') {
				out += "\\r";
			} else if (c == '\t') {
				out += "\\t";
			} else {
				out += c;
			}
		}
		return out;
	}

	/**
	 * Options for aligning text horizontally. (Deprecated. Use â€œNSTextAlignmentâ€? instead.)
	 */

	public static enum UITextAlignment {
		UITextAlignmentLeft, //
		UITextAlignmentCenter, //
		UITextAlignmentRight;

		int value;

		public int getValue() {
			return this.ordinal();
		}
	}

	@Override
	public NSString retain() {
		return this;
	}


	public NSString stringByAppendingPathComponent(NSString mString) {
		if (mString != null) {
			StringBuilder strBuilder = new StringBuilder(getWrappedString());
			if (!mString.getWrappedString().endsWith("/") && (!"".equals(mString.getWrappedString())
					|| "/".equals(mString.getWrappedString())))
				return new NSString(strBuilder.append("/" + mString).toString());
		}
		return mString;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	// Addressable Methods

	public static final Adressable adressable = new Adressable() {

		NSError[] outError;

		@Override
		public NSString stringWithContentsOfURLEncodingError(NSURL url, NSStringEncoding enc,
															 NSError error) {
			outError = new NSError[] { error };
			return stringWithContentsOfURLEncodingError(url, enc, error);
		}

		@Override
		public NSError[] getStringWithContentsOfURLEncodingErrorArray() {
			return outError;
		}
	};

	/*
	 *  public static CGSize sizeWithFontForWidthLineBreakMode(UIFont
	 * font, CGSize size, int lineBreakMode) { return new CGSize(); }
	 */

	/**
	 * Returns the size of the string if it were to be rendered with the specified font on a single line.
	 *
	 * @deprecated Deprecation Statement Use sizeWithAttributes: instead.
	 * @Declaration OBJECTIVE-C - (CGSize)sizeWithFont:(UIFont *)font
	 * @Parameters font The font to use for computing the string size.
	 * @return Return Value The width and height of the resulting stringâ€™s bounding box. These values may be rounded up to the nearest whole
	 *         number.
	 * @Discussion You can use this method to obtain the layout metrics you need to draw a string in your user interface. This method does
	 *             not actually draw the string or alter the receiverâ€™s text in any way.
	 *
	 *             In iOS 6, this method wraps text using the NSLineBreakByWordWrapping option by default. In earlier versions of iOS, this
	 *             method does not perform any line wrapping and returns the absolute width and height of the string using the specified
	 *             font.
	 */
	/*
	 * @Deprecated
	 * 
	 *  public CGSize sizeWithFont(UIFont font) { Rect bounds = new Rect(); Paint paint = new
	 * Paint(); paint.setTextAlign(Align.LEFT); Typeface typeface = font.typeface; paint.setTypeface(typeface); float textSize =
	 * font.getWrappedTextSize(); paint.setTextSize(textSize); paint.getTextBounds(getWrappedString(), 0, getWrappedString().length(),
	 * bounds); return new CGSize(bounds.width(), bounds.height()); }
	 */

	/**
	 * Returns the size of the string if it were rendered and constrained to the specified size.
	 *
	 * @deprecated Deprecation Statement Use boundingRectWithSize:options:attributes:context: instead. See also UILabel as a possible
	 *             alternative for some use cases.
	 * @Declaration OBJECTIVE-C - (CGSize)sizeWithFont:(UIFont *)font constrainedToSize:(CGSize)size
	 * @Parameters font The font to use for computing the string size. size The maximum acceptable size for the string. This value is used
	 *             to calculate where line breaks and wrapping would occur.
	 * @return Return Value The width and height of the resulting stringâ€™s bounding box. These values may be rounded up to the nearest whole
	 *         number.
	 * @Discussion You can use this method to obtain the layout metrics you need to draw a string in your user interface. This method does
	 *             not actually draw the string or alter the receiverâ€™s text in any way.
	 *
	 *             This method computes the metrics needed to draw the specified string. This method lays out the receiverâ€™s text and
	 *             attempts to make it fit the specified size using the specified font and the NSLineBreakByWordWrapping line break option.
	 *             During layout, the method may break the text onto multiple lines to make it fit better. If the receiverâ€™s text does not
	 *             completely fit in the specified size, it lays out as much of the text as possible and truncates it (for layout purposes
	 *             only) according to the specified line break mode. It then returns the size of the resulting truncated string. If the
	 *             height specified in the size parameter is less than a single line of text, this method may return a height value that is
	 *             bigger than the one specified.
	 */
	/*
	 * @Deprecated
	 * 
	 *  public CGSize sizeWithFontConstrainedToSize(UIFont font, CGSize size) { Rect bounds = new
	 * Rect();
	 * 
	 * Paint paint = new Paint(); paint.setTextAlign(Align.LEFT); Typeface typeface = font.typeface; paint.setTypeface(typeface); float
	 * textSize = font.getWrappedTextSize(); paint.setTextSize(textSize); paint.getTextBounds(getWrappedString(), 0,
	 * getWrappedString().length(), bounds); float h = size.height(); float w = size.width(); if (size.width() > bounds.width()) { w =
	 * bounds.width(); } if (size.height() > bounds.height()) { h = bounds.height(); } return new CGSize(w, h); }
	 */

	/**
	 * Returns the size of the string if it were rendered with the specified constraints.
	 *
	 * @deprecated Deprecation Statement Use boundingRectWithSize:options:attributes:context: instead.
	 * @Declaration OBJECTIVE-C - (CGSize)sizeWithFont:(UIFont *)font constrainedToSize:(CGSize)size
	 *              lineBreakMode:(NSLineBreakMode)lineBreakMode
	 * @Parameters font The font to use for computing the string size.<br>
	 *             size The maximum acceptable size for the string. This value is used to calculate where line breaks and wrapping would
	 *             occur.<br>
	 *             lineBreakMode The line break options for computing the size of the string. For a list of possible values, see
	 *             NSLineBreakMode.<br>
	 * @return Return Value The width and height of the resulting stringâ€™s bounding box. These values may be rounded up to the nearest whole
	 *         number.
	 * @Discussion You can use this method to obtain the layout metrics you need to draw a string in your user interface. This method does
	 *             not actually draw the string or alter the receiverâ€™s text in any way.
	 *
	 *             This method computes the metrics needed to draw the specified string. This method lays out the receiverâ€™s text and
	 *             attempts to make it fit the specified size using the specified font and line break options. During layout, the method may
	 *             break the text onto multiple lines to make it fit better. If the receiverâ€™s text does not completely fit in the specified
	 *             size, it lays out as much of the text as possible and truncates it (for layout purposes only) according to the specified
	 *             line break mode. It then returns the size of the resulting truncated string. If the height specified in the size
	 *             parameter is less than a single line of text, this method may return a height value that is bigger than the one
	 *             specified.
	 */
	/*
	 * @Deprecated
	 * 
	 *  public CGSize sizeWithFontConstrainedToSizeLineBreakMode(UIFont
	 * font, CGSize size, NSLineBreakMode lineBreakMode) { // By default android takes lines breaks in the measure return
	 * sizeWithFontConstrainedToSize(font, size); }
	 */

	/**
	 * Draws the string in a single line at the specified point in the current graphics context using the specified font.
	 *
	 * @deprecated Deprecation Statement Use drawAtPoint:withAttributes: instead.
	 * @Declaration OBJECTIVE-C - (CGSize)drawAtPoint:(CGPoint)point withFont:(UIFont *)font
	 * @Parameters point The location (in the coordinate system of the current graphics context) at which to draw the string. This point
	 *             represents the top-left corner of the stringâ€™s bounding box. font The font to use for rendering.
	 * @return Return Value The size of the rendered string. The returned values may be rounded up to the nearest whole number.
	 * @Discussion This method draws only a single line of text, drawing as much of the string as possible using the given font. This method
	 *             does not perform any line wrapping during drawing.
	 */
	/*
	 * @Deprecated
	 * 
	 *  public CGSize drawAtPointWithFont(CGPoint point, UIFont font) { Paint paint = new
	 * Paint(); paint.setTextAlign(Align.LEFT); View view =
	 * GenericMainContext.sharedContext.getWindow().getDecorView().findViewById(android.R.id.content); Canvas canvas = new Canvas();
	 * canvas.drawText(getWrappedString(), point.getX(), point.getY(), paint); return sizeWithFont(font); }
	 */

	// Depricated UIKit Additions

	/**
	 * @Signature: boundingRectWithSize:options:context:
	 * @Declaration : - (CGRect)boundingRectWithSize:(CGSize)size options:(NSStringDrawingOptions)options context:(NSStringDrawingContext
	 *              *)context
	 * @Description : Returns the bounding rectangle required to draw the string.
	 * @Discussion You can use this method to compute the space required to draw the string. The constraints you specify in the size
	 *             parameter are a guide for the renderer for how to size the string. However, the actual bounding rectangle returned by
	 *             this method can be larger than the constraints if additional space is needed to render the entire string. Typically, the
	 *             renderer preserves the width constraint and adjusts the height constraint as needed. In iOS 7 and later, this method
	 *             returns fractional sizes (in the size component of the returned CGRect); to use a returned size to size views, you must
	 *             use raise its value to the nearest higher integer using the ceil function.
	 **/

	public CGRect boundingRectWithSizeOptionsContext(CGSize size, NSStringDrawingOptions options,
													 NSStringDrawingContext context) {
		return null;
	}

	/**
	 * @Signature: drawAtPoint:
	 * @Declaration : - (void)drawAtPoint:(CGPoint)point
	 * @Description : Draws the attributed string starting at the specified point in the current graphics context.
	 * @Discussion This method draws the entire string starting at the specified point. This method draws the line using the attributes
	 *             specified in the attributed string itself. If newline characters are present in the string, those characters are honored
	 *             and cause subsequent text to be placed on the next line underneath the starting point.
	 **/

	public void drawAtPoint(CGPoint point) {
	}

	/**
	 * @Signature: drawInRect:
	 * @Declaration : - (void)drawInRect:(CGRect)rect
	 * @Description : Draws the attributed string inside the specified bounding rectangle in the current graphics context.
	 * @Discussion This method draws as much of the string as it can inside the specified rectangle, wrapping the string text as needed to
	 *             make it fit. If the string is too long to fit inside the rectangle, the method renders as much as possible and clips the
	 *             rest. This method draws the line using the attributes specified in the attributed string itself. If newline characters
	 *             are present in the string, those characters are honored and cause subsequent text to be placed on the next line
	 *             underneath the starting point.
	 **/

	public void drawInRect(CGRect rect) {
	}

	/**
	 * @Signature: drawWithRect:options:context:
	 * @Declaration : - (void)drawWithRect:(CGRect)rect options:(NSStringDrawingOptions)options context:(NSStringDrawingContext *)context
	 * @Description : Draws the attributed string in the specified bounding rectangle using the provided options.
	 * @Discussion This method draws as much of the string as it can inside the specified rectangle. If
	 *             NSStringDrawingUsesLineFragmentOrigin is specified in options, it wraps the string text as needed to make it fit. If the
	 *             string is too big to fit completely inside the rectangle, the method scales the font or adjusts the letter spacing to
	 *             make the string fit within the given bounds. This method draws the line using the attributes specified in the attributed
	 *             string itself. If newline characters are present in the string, those characters are honored and cause subsequent text to
	 *             be placed on the next line underneath the starting point.
	 **/


	public void drawWithRectOptionsContext(CGRect rect, NSStringDrawingOptions options,
										   NSStringDrawingContext context) {
	}

	/**
	 * @Signature: size
	 * @Declaration : - (CGSize)size
	 * @Description : Returns the size required to draw the string.
	 * @Discussion You can use this method prior to drawing to compute how much space is required to draw the string. In iOS 7 and later,
	 *             this method returns fractional sizes; to use a returned size to size views, you must use raise its value to the nearest
	 *             higher integer using the ceil function.
	 **/

	public CGSize size() {
		return null;
	}

	// UIKit Addition

	// 1
	/**
	 * @Signature: boundingRectWithSize:options:attributes:context:
	 * @Declaration : - (CGRect)boundingRectWithSize:(CGSize)size options:(NSStringDrawingOptions)options attributes:(NSDictionary
	 *              *)attributes context:(NSStringDrawingContext *)context
	 * @Description : Calculates and returns the bounding rect for the receiver drawn using the given options and display characteristics,
	 *              within the specified rectangle in the current graphics context.
	 * @Discussion To correctly draw and size multi-line text, pass NSStringDrawingUsesLineFragmentOrigin in the options parameter. This
	 *             method returns fractional sizes (in the size component of the returned CGRect); to use a returned size to size views, you
	 *             must raise its value to the nearest higher integer using the ceil function.
	 **/

	public CGRect boundingRectWithSizeOptionsAttributesContext(CGSize size,
															   NSStringDrawingOptions options, NSDictionary attributes,
															   NSStringDrawingContext context) {
		return null;
	}

	// 2
	/**
	 * @Signature: drawAtPoint:withAttributes:
	 * @Declaration : - (void)drawAtPoint:(CGPoint)point withAttributes:(NSDictionary *)attrs
	 * @Description : Draws the receiver with the font and other display characteristics of the given attributes, at the specified point in
	 *              the current graphics context.
	 * @Discussion The width (height for vertical layout) of the rendering area is unlimited, unlike drawInRect:withAttributes:, which uses
	 *             a bounding rectangle. As a result, this method renders the text in a single line. However, if newline characters are
	 *             present in the string, those characters are honored and cause subsequent text to be placed on the next line underneath
	 *             the starting point.
	 **/

	public void drawAtPointWithAttributes(CGPoint point, NSDictionary attrs) {
	}

	// 3
	/**
	 * @Signature: drawInRect:withAttributes:
	 * @Declaration : - (void)drawInRect:(CGRect)rect withAttributes:(NSDictionary *)attrs
	 * @Description : Draws the attributed string inside the specified bounding rectangle in the current graphics context.
	 * @Discussion This method draws as much of the string as it can inside the specified rectangle, wrapping the string text as needed to
	 *             make it fit. If the string is too long to fit inside the rectangle, the method renders as much as possible and clips the
	 *             rest. If newline characters are present in the string, those characters are honored and cause subsequent text to be
	 *             placed on the next line underneath the starting point.
	 **/

	public void drawInRectWithAttributes(CGRect rect, NSDictionary attrs) {
	}

	// 4
	/**
	 * @Signature: drawWithRect:options:attributes:context:
	 * @Declaration : - (void)drawWithRect:(CGRect)rect options:(NSStringDrawingOptions)options attributes:(NSDictionary *)attributes
	 *              context:(NSStringDrawingContext *)context
	 * @Description : Draws the attributed string in the specified bounding rectangle using the provided options.
	 * @Discussion This method draws as much of the string as it can inside the specified rectangle, wrapping the string text as needed to
	 *             make it fit. If the string is too big to fit completely inside the rectangle, the method scales the font or adjusts the
	 *             letter spacing to make the string fit within the given bounds. If newline characters are present in the string, those
	 *             characters are honored and cause subsequent text to be placed on the next line underneath the starting point. To
	 *             correctly draw and size multi-line text, pass NSStringDrawingUsesLineFragmentOrigin in the options parameter.
	 **/

	public void drawWithRectOptionsAttributesContext(CGRect rect, NSStringDrawingOptions options,
													 NSDictionary attributes, NSStringDrawingContext context) {
	}

	// 5
	/**
	 * @Signature: sizeWithAttributes:
	 * @Declaration : - (CGSize)sizeWithAttributes:(NSDictionary *)attrs
	 * @Description : Returns the bounding box size the receiver occupies when drawn with the given attributes.
	 * @Discussion This method returns fractional sizes; to use a returned size to size views, you must raise its value to the nearest
	 *             higher integer using the ceil function.
	 **/


	public CGSize sizeWithAttributes(NSDictionary attrs) {
		return null;
	}


	public boolean containsString(NSString aString) {
		if (aString != null && aString.getWrappedString() != null) {
			if (this.getWrappedString() != null) {
				return this.getWrappedString().contains(aString.getWrappedString());
			}
		}
		return false;
	}

}