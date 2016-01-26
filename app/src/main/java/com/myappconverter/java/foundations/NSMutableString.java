
package com.myappconverter.java.foundations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.myappconverter.java.corefoundations.CFMutableStringRef;
import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.foundations.constants.NSStringEncoding;
import com.myappconverter.mapping.utils.AndroidFileUtils;
import com.myappconverter.mapping.utils.AndroidRessourcesUtils;
import com.myappconverter.mapping.utils.AndroidVersionUtils;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InstanceTypeFactory;


public class NSMutableString extends NSString implements Serializable {

	private static final long serialVersionUID = 4027500593825564135L;

	public static NSMutableString string() {

		return new NSMutableString();
	}

	public NSMutableString() {
		this.wrappedString = new String();
	}

	public NSMutableString(String aString) {
		super(aString);
		this.wrappedString = aString;
	}

	// Creating and Initializing a Mutable String
	/**
	 * @Signature: stringWithCapacity:
	 * @Declaration : + (id)stringWithCapacity:(NSUInteger)capacity
	 * @Description : Returns an empty NSMutableString object with initial storage for a given number of characters.
	 * @param capacity The number of characters the string is expected to initially contain.
	 * @return Return Value An empty NSMutableString object with initial storage for capacity characters.
	 * @Discussion The number of characters indicated by capacity is simply a hint to increase the efficiency of data storage. The value
	 *             does not limit the length of the string.
	 **/
	
	public static NSMutableString stringWithCapacity(int capacity) {
		return new NSMutableString();
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (id)initWithCapacity:(NSUInteger)capacity
	 * @Description : Returns an NSMutableString object initialized with initial storage for a given number of characters,
	 * @param capacity The number of characters the string is expected to initially contain.
	 * @return Return Value An initialized NSMutableString object with initial storage for capacity characters. The returned object might be
	 *         different than the original receiver.
	 * @Discussion The number of characters indicated by capacity is simply a hint to increase the efficiency of data storage. The value
	 *             does not limit the length of the string.
	 **/
	
	public static NSMutableString initWithCapacity(int capacity) {
		return new NSMutableString();

	}

	// Modifying a String
	/**
	 * @Signature: appendFormat:
	 * @Declaration : - (void)appendFormat:(NSString *)format ...
	 * @Description : Adds a constructed string to the receiver.
	 * @param format A format string. See “Formatting String Objects for more information. This value must not be nil. Important: Raises an
	 *            NSInvalidArgumentException if format is nil.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 **/
	
	public void appendFormat(NSString format, Object... args) {
		StringBuilder strbuff = new StringBuilder(wrappedString);

		if (format == null)
			throw new IllegalArgumentException(" This value must not be null");
		format.setWrappedString(format.getWrappedString().replace("%@", "%s"));
		strbuff.append(String.format(format.getWrappedString(), args));
		wrappedString = strbuff.toString();
	}

	/**
	 * @Signature: appendString:
	 * @Declaration : - (void)appendString:(NSString *)aString
	 * @Description : Adds to the end of the receiver the characters of a given string.
	 * @param aString The string to append to the receiver. aString must not be nil
	 **/
	
	public void appendString(NSString aString) {
		this.wrappedString += aString;
	}

	/**
	 * @Signature: deleteCharactersInRange:
	 * @Declaration : - (void)deleteCharactersInRange:(NSRange)aRange
	 * @Description : Removes from the receiver the characters in a given range.
	 * @param aRange The range of characters to delete. aRange must not exceed the bounds of the receiver. Important: Raises an
	 *            NSRangeException if any part of aRange lies beyond the end of the string.
	 * @Discussion This method treats the length of the string as a valid range value that returns an empty string.
	 **/
	
	public void deleteCharactersInRange(NSRange aRange) {
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		if (start > 0 && end <= this.wrappedString.length()) {
			this.wrappedString = this.wrappedString.substring(0, start) + this.wrappedString.substring(end, this.wrappedString.length());
		} else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @Signature: insertString:atIndex:
	 * @Declaration : - (void)insertString:(NSString *)aString atIndex:(NSUInteger)anIndex
	 * @Description : Inserts into the receiver the characters of a given string at a given location.
	 * @param aString The string to insert into the receiver. aString must not be nil.
	 * @param anIndex The location at which aString is inserted. The location must not exceed the bounds of the receiver. Important: Raises
	 *            an NSRangeException if anIndex lies beyond the end of the string.
	 * @Discussion The new characters begin at anIndex and the existing characters from anIndex to the end are shifted by the length of
	 *             aString. This method treats the length of the string as a valid index value that returns an empty string.
	 **/
	
	public void insertStringAtIndex(NSString aString, int anIndex) {
		int index = anIndex;
		if (index <= this.wrappedString.length()) {
			this.wrappedString = this.wrappedString.substring(0, index) + aString.wrappedString
					+ this.wrappedString.substring(index, this.wrappedString.length());
		} else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @Signature: replaceCharactersInRange:withString:
	 * @Declaration : - (void)replaceCharactersInRange:(NSRange)aRange withString:(NSString *)aString
	 * @Description : Replaces the characters from aRange with those in aString.
	 * @param aRange The range of characters to replace. aRange must not exceed the bounds of the receiver. Important: Raises an
	 *            NSRangeException if any part of aRange lies beyond the end of the receiver.
	 * @param aString The string with which to replace the characters in aRange. aString must not be nil.
	 * @Discussion This method treats the length of the string as a valid range value that returns an empty string.
	 **/
	
	public void replaceCharactersInRangeWithString(NSRange aRange, NSString aString) {
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		if (start > 0 && end <= this.wrappedString.length()) {
			String shunk1 = this.wrappedString.substring(0, start);
			String shunk2 = this.wrappedString.substring(end, this.wrappedString.length());
			this.wrappedString = shunk1 + aString.getWrappedString() + shunk2;
		} else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @Signature: replaceOccurrencesOfString:withString:options:range:
	 * @Declaration : - (NSUInteger)replaceOccurrencesOfString:(NSString *)target withString:(NSString *)replacement
	 *              options:(NSStringCompareOptions)opts range:(NSRange)searchRange
	 * @Description : Replaces all occurrences of a given string in a given range with another given string, returning the number of
	 *              replacements.
	 * @param target The string to replace. Important: Raises an NSInvalidArgumentException if target is nil.
	 * @param replacement The string with which to replace target. Important: Raises an NSInvalidArgumentException if replacement is nil.
	 * @param opts A mask specifying search options. See String Programming Guide for details. If opts is NSBackwardsSearch, the search is
	 *            done from the end of the range. If opts is NSAnchoredSearch, only anchored (but potentially multiple) instances are
	 *            replaced. NSLiteralSearch and NSCaseInsensitiveSearch also apply.
	 * @param searchRange The range of characters to replace. aRange must not exceed the bounds of the receiver. Specify searchRange as
	 *            NSMakeRange(0, [receiver length]) to process the entire string. Important: Raises an NSRangeException if any part of
	 *            searchRange lies beyond the end of the receiver.
	 * @return Return Value The number of replacements made.
	 * @Discussion This method treats the length of the string as a valid range value that returns an empty string.
	 **/
	
	public int replaceOccurrencesOfStringWithStringOptionsRange(NSString target, NSString replacement, NSStringCompareOptions opts,
			NSRange searchRange) {

		int start = searchRange.location;
		int end = start + searchRange.length - 1;
		if (start > this.getWrappedString().length() || end > this.getWrappedString().length())
			throw new IllegalArgumentException(" This range must not be outside the index space of the string");
		String subString = this.getWrappedString().substring(start, end);
		Pattern p = Pattern.compile(target.getWrappedString());
		Matcher m = p.matcher(subString);
		switch (opts) {
		case NSCaseInsensitiveSearch:
			p = Pattern.compile(target.getWrappedString(), Pattern.CASE_INSENSITIVE);
			m = p.matcher(subString);
			break;
		case NSBackwardsSearch:
			m = p.matcher(subString);
			break;
		case NSAnchoredSearch:
			m.useAnchoringBounds(true);
			break;
		case NSDiacriticInsensitiveSearch:
		case NSForcedOrderingSearch:
		case NSLiteralSearch:
		case NSNumericSearch:
		case NSRegularExpressionSearch:
		case NSWidthInsensitiveSearch:
			break;
		default:
			break;
		}

		int i = 0;
		while (m.find()) {
			i++;
			m.replaceFirst(replacement.getWrappedString());
		}
		return i;

	}

	/**
	 * @Signature: setString:
	 * @Declaration : - (void)setString:(NSString *)aString
	 * @Description : Replaces the characters of the receiver with those in a given string.
	 * @param aString The string with which to replace the receiver's content. aString must not be nil.
	 **/
	
	public void setString(NSString aString) {
		this.wrappedString = aString.wrappedString;
	}

	/**
	 * stringWithCString:encoding:
	 * 
	 * @Returns a string containing the bytes in a given C array, interpreted according to a given encoding. +
	 *          (instancetype)stringWithCString:(const char *)cString encoding:(NSStringEncoding)enc
	 * @Parameters cString A C array of bytes. The array must end with a NULL byte; intermediate NULL bytes are not allowed. enc The
	 *             encoding of cString.
	 * @Return Value A string containing the characters described in cString.
	 * @Discussion If cString is not a NULL-terminated C string, or encoding does not match the actual encoding, the results are undefined.
	 * @Special Considerations Because NULL bytes are not allowed, 16-bit encodings are not supported because their content will typically
	 *          include NULL bytes.
	 */
	
	public static NSMutableString stringWithCStringEncoding(char[] characters, int encoding) {
		if (characters != null && characters.length > 0 && encoding != 0) {
			byte bytes[] = (new String(characters)).getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			return new NSMutableString(new String(bytes, Charset.defaultCharset()));

		}
		throw new IllegalArgumentException(" This value must not be null");

	}

	public static Object stringWithCStringEncoding(Class clazz, char[] characters, int encoding) {
		NSString str = stringWithCStringEncoding(characters, encoding);
		return InstanceTypeFactory.getInstance(str, NSMutableString.class, clazz, new NSString("setWrappedString"), str.getWrappedString());

	}

	// it can be either a char array (char[] ) or a String ("cccc")
	
	public static NSMutableString stringWithCString(String characters, int encoding) {
		if (characters != null && characters.length() > 0 && encoding != 0) {
			byte bytes[] = characters.getBytes(NSStringEncoding.getCharsetFromInt(encoding));
			return new NSMutableString(new String(bytes, Charset.defaultCharset()));
		}
		throw new IllegalArgumentException(" This value must not be null");

	}

	public static Object stringWithCString(Class clazz, String characters, int encoding) {
		NSString str = stringWithCString(characters, encoding);
		return InstanceTypeFactory.getInstance(str, NSMutableString.class, clazz, new NSString("setWrappedString"), str.getWrappedString());
	}

	/**
	 * Surcharged for instancetype
	 */

	public static NSMutableString localizedStringWithFormat(NSString format, NSString... args) {
		NSMutableString nsString = new NSMutableString();
		if (format == null)
			throw new IllegalArgumentException();
		// not yet covered
		nsString.setWrappedString(String.format(Locale.getDefault(), format.getWrappedString()));
		return nsString;
	}

	public static NSMutableString stringWithCharactersLength(char[] characters, int length) {
		NSMutableString myNSString = new NSMutableString();
		myNSString.wrappedString = new String(characters, 0, length);
		return myNSString;
	}

	public static NSMutableString stringWithContentsOfFileEncodingError(NSString path, int enc, NSError error) {
		// not yet covered
		if (AndroidVersionUtils.isInteger(path.getWrappedString())) {
			if (Integer.parseInt(path.getWrappedString()) == 0) {
                Log.d("Error :",""+error.toString());
				return null;
			}
			int resId = Integer.parseInt(path.getWrappedString());
			InputStream is = GenericMainContext.sharedContext.getResources().openRawResource(resId);
			String mystring = null;
			try {
				mystring = AndroidIOUtils.toString(is);
				AndroidIOUtils.closeQuietly(is);
				return new NSMutableString(mystring);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                Log.d("Error :",""+error.toString());
				return null;
			}
		} else if (path.getWrappedString().contains("assets ")) {
			try {
				String realPath = path.getWrappedString().substring("assets ".length(), path.getWrappedString().length());
				InputStream is = GenericMainContext.sharedContext.getAssets().open(realPath);
				String mystring = AndroidRessourcesUtils.getStringFromInputStream(is);
				return new NSMutableString(mystring);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                Log.d("Error :",""+error.toString());
				return null;
			}
		} else {

			NSMutableString nsString = new NSMutableString();
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
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
			Log.d("Error :",""+error.toString());
			return null;
		}
	}

	public static NSMutableString stringWithContentsOfFileUsedEncodingError(NSString path, int enc, NSError error) {
		return stringWithContentsOfFileEncodingError(path, enc, error);
	}

	public static NSMutableString stringWithContentsOfURLEncodingError(NSURL url, int enc, NSError error) {
		String encoding = Charset.defaultCharset().name();
		if (enc != 0)
			encoding = NSStringEncoding.getCharsetFromInt(enc).name();
		NSMutableString nsString = new NSMutableString();

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.getUrl().openStream()));
			StringBuilder strbuffer = new StringBuilder();
			String str;
			while ((str = in.readLine()) != null) {
				strbuffer.append(str);
			}
			in.close();
			nsString.wrappedString = new String(strbuffer.toString().getBytes(), encoding);
			return nsString;
		} catch (MalformedURLException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
        Log.d("Error :",""+error.toString());
		return null;
	}

	public static NSMutableString stringWithContentsOfURLUsedEncodingError(NSURL url, int usedEncoding, NSError error) {
		return stringWithContentsOfURLEncodingError(url, usedEncoding, error);
	}

	@Override
	public NSMutableString stringWithContentsOfURL(NSURL aURL) {
		return NSMutableString.stringWithContentsOfURLUsedEncodingError(aURL, NSStringEncoding.getIntFromCharset(Charset.defaultCharset().name()), new NSError());
	}

	public static NSMutableString stringWithFormat(NSString format, Object... args) {
		Log.d(TAG, "format : " + format.getWrappedString());
		if (format.getWrappedString() == null) {
			throw new IllegalArgumentException("stringWithFormat : format must not be null");
		}
		String[] params = { "1$", "2$", "3$", "4$", "5$" };
		String mStr = format.getWrappedString();
		for (String str : params) {
			if (format.getWrappedString().indexOf(str) != -1)
				mStr = mStr.replace(str, "");
		}
		mStr = mStr.replace("%@", " %s ");
		return new NSMutableString(String.format(mStr, args));
	}

	public static NSMutableString stringWithString(NSString mString) {
		NSMutableString myNSString = new NSMutableString();
		myNSString.wrappedString = mString.wrappedString;
		return myNSString;
	}

	public static NSMutableString stringWithUTF8String(char[] string) {
		NSMutableString myNSSTring = new NSMutableString();
		myNSSTring.wrappedString = new String(string);
		return myNSSTring;
	}

	public CFMutableStringRef toCFMutableString() {
		return new CFMutableStringRef(this.getWrappedString());
	}

}