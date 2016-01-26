
package com.myappconverter.java.foundations;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myappconverter.java.foundations.functions.NSDecimal;
import com.myappconverter.java.foundations.protocols.NSCopying;



public class NSScanner extends NSObject implements NSCopying {

	private Scanner scanner;
	private String myString;
	private boolean caseSensitive = false;
	private int scanLocation = 0;
	private NSCharacterSet charactersToBeSkipped;

	// getter and setter
	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	public boolean getCaseSensitive() {
		return caseSensitive;
	}

	// Creating a Scanner
	/**
	 * @Signature: scannerWithString:
	 * @Declaration : + (id)scannerWithString:(NSString *)aString
	 * @Description : Returns an NSScanner object that scans a given string.
	 * @param aString The string to scan.
	 * @return Return Value An NSScanner object that scans aString.
	 * @Discussion Sets the string to scan by invoking initWithString: with aString.
	 **/

	public static NSScanner scannerWithString(NSString aString) {
		NSScanner nsScanner = new NSScanner();
		nsScanner.scanner = new Scanner(aString.getWrappedString());
		nsScanner.myString = aString.getWrappedString();
		return nsScanner;
	}

	/**
	 * @Signature: localizedScannerWithString:
	 * @Declaration : + (id)localizedScannerWithString:(NSString *)aString
	 * @Description : Returns an NSScanner object that scans a given string according to the userâ€™s default locale.
	 * @param aString The string to scan.
	 * @return Return Value An NSScanner object that scans aString according to the userâ€™s default locale.
	 * @Discussion Sets the string to scan by invoking initWithString: with aString. The locale is set with setLocale:.
	 **/

	public static NSScanner localizedScannerWithString(NSString aString) {
		NSScanner nsScanner = new NSScanner();
		nsScanner.scanner = new Scanner(aString.getWrappedString());
		nsScanner.scanner.useLocale(Locale.getDefault());
		nsScanner.myString = aString.getWrappedString();
		return nsScanner;
	}

	/**
	 * @Signature: initWithString:
	 * @Declaration : - (id)initWithString:(NSString *)aString
	 * @Description : Returns an NSScanner object initialized to scan a given string.
	 * @param aString The string to scan.
	 * @return Return Value An NSScanner object initialized to scan aString from the beginning. The returned object might be different than
	 *         the original receiver.
	 **/

	public NSScanner initWithString(NSString aString) {
		myString = aString.getWrappedString();
		scanner = new Scanner(aString.getWrappedString());
		return this;
	}

	// Getting a Scanner's String

	/**
	 * @Signature: string
	 * @Declaration : - (NSString *)string
	 * @Description : Returns the string with which the receiver was created or initialized.
	 * @return Return Value The string with which the receiver was created or initialized.
	 **/

	public NSString string() {
		return new NSString(this.getMyString());
	}

	// Configuring a Scanner

	/**
	 * @Signature: setScanLocation:
	 * @Declaration : - (void)setScanLocation:(NSUInteger)index
	 * @Description : Sets the location at which the next scan operation will begin to a given index.
	 * @param index The location at which the next scan operation will begin. Raises an NSRangeException if index is beyond the end of the
	 *            string being scanned.
	 * @Discussion This method is useful for backing up to rescan after an error. Rather than setting the scan location directly to skip
	 *             known sequences of characters, use scanString:intoString: or scanCharactersFromSet:intoString:, which allow you to verify
	 *             that the expected substring (or set of characters) is in fact present.
	 **/

	public void setScanLocation(int index) {
		if (index <= myString.length()) {
			scanLocation = index;
		}
	}

	/**
	 * @Signature: scanLocation
	 * @Declaration : - (NSUInteger)scanLocation
	 * @Description : Returns the character position at which the receiver will begin its next scanning operation.
	 * @return Return Value The character position at which the receiver will begin its next scanning operation.
	 **/

	public int scanLocation() {
		return scanLocation;
	}

	/**
	 * @Signature: setCaseSensitive:
	 * @Declaration : - (void)setCaseSensitive:(BOOL)flag
	 * @Description : Sets whether the receiver is case sensitive when scanning characters.
	 * @param flag If YES, the receiver will distinguish case when scanning characters, otherwise it will ignore case distinctions.
	 * @Discussion Scanners are not case sensitive by default. Note that case sensitivity doesnâ€™t apply to the characters to be skipped.
	 **/

	public void setCaseSensitive(boolean flag) {
		this.caseSensitive = flag;
	}

	/**
	 * @Signature: caseSensitive
	 * @Declaration : - (BOOL)caseSensitive
	 * @Description : Returns a Boolean value that indicates whether the receiver distinguishes case in the characters it scans.
	 * @return Return Value YES if the receiver distinguishes case in the characters it scans, otherwise NO.
	 * @Discussion Scanners are not case sensitive by default. Note that case sensitivity doesnâ€™t apply to the characters to be skipped.
	 **/

	public boolean caseSensitive() {
		return getCaseSensitive();
	}

	/**
	 * @Signature: setCharactersToBeSkipped:
	 * @Declaration : - (void)setCharactersToBeSkipped:(NSCharacterSet *)skipSet
	 * @Description : Sets the set of characters to ignore when scanning for a value representation.
	 * @param skipSet The characters to ignore when scanning for a value representation. Pass nil to not ignore any characters.
	 * @Discussion For example, if a scanner ignores spaces and you send it a scanInt: message, it skips spaces until it finds a decimal
	 *             digit or other character. While an element is being scanned, however, no characters are skipped. If you scan for
	 *             something made of characters in the set to be skipped (for example, using scanInt: when the set of characters to be
	 *             skipped is the decimal digits), the result is undefined. The characters to be skipped are treated literally as single
	 *             values. A scanner doesnâ€™t apply its case sensitivity setting to these characters and doesnâ€™t attempt to match composed
	 *             character sequences with anything in the set of characters to be skipped (though it does match pre-composed characters
	 *             individually). If you want to skip all vowels while scanning a string, for example, you can set the characters to be
	 *             skipped to those in the string â€œAEIOUaeiouâ€? (plus any accented variants with pre-composed characters). The default set of
	 *             characters to skip is the whitespace and newline character set.
	 **/

	public void setCharactersToBeSkipped(NSCharacterSet skipSet) {
		charactersToBeSkipped = skipSet;
	}

	/**
	 * @Signature: charactersToBeSkipped
	 * @Declaration : - (NSCharacterSet *)charactersToBeSkipped
	 * @Description : Returns a character set containing the characters the receiver ignores when looking for a scannable element.
	 * @return Return Value A character set containing the characters the receiver ignores when looking for a scannable element.
	 * @Discussion For example, if a scanner ignores spaces and you send it a scanInt: message, it skips spaces until it finds a decimal
	 *             digit or other character. While an element is being scanned, however, no characters are skipped. If you scan for
	 *             something made of characters in the set to be skipped (for example, using scanInt: when the set of characters to be
	 *             skipped is the decimal digits), the result is undefined. The default set to skip is the whitespace and newline character
	 *             set.
	 **/

	public NSCharacterSet charactersToBeSkipped() {
		return charactersToBeSkipped;
	}

	/**
	 * @Signature: setLocale:
	 * @Declaration : - (void)setLocale:(id)aLocale
	 * @Description : Sets the receiverâ€™s locale to a given locale.
	 * @param aLocale The locale for the receiver.
	 * @Discussion A scannerâ€™s locale affects the way it interprets values from the string. In particular, a scanner uses the localeâ€™s
	 *             decimal separator to distinguish the integer and fractional parts of floating-point representations. A new scannerâ€™s
	 *             locale is by default nil, which causes it to use non-localized values.
	 **/

	public void setLocale(NSLocale aLocale) {
		scanner.useLocale(aLocale.getLocale());

	}

	/**
	 * @Signature: locale
	 * @Declaration : - (id)locale
	 * @Description : Returns the receiverâ€™s locale.
	 * @return Return Value The receiverâ€™s locale, or nil if it has none.
	 * @Discussion A scannerâ€™s locale affects the way it interprets numeric values from the string. In particular, a scanner uses the
	 *             localeâ€™s decimal separator to distinguish the integer and fractional parts of floating-point representations. A scanner
	 *             with no locale set uses non-localized values.
	 **/

	public NSLocale locale() {
		NSLocale locale = new NSLocale(Locale.getDefault());
		locale.setLocale(scanner.locale());
		return locale;
	}

	// Scanning a String

	/**
	 * @Signature: scanCharactersFromSet:intoString:
	 * @Declaration : - (BOOL)scanCharactersFromSet:(NSCharacterSet *)scanSet intoString:(NSString **)stringValue
	 * @Description : Scans the string as long as characters from a given character set are encountered, accumulating characters into a
	 *              string thatâ€™s returned by reference.
	 * @param scanSet The set of characters to scan.
	 * @param stringValue Upon return, contains the characters scanned.
	 * @return Return Value YES if the receiver scanned any characters, otherwise NO.
	 * @Discussion Invoke this method with NULL as stringValue to simply scan past a given set of characters.
	 **/

	public boolean scanCharactersFromSetIntoString(NSCharacterSet scanSet, NSString[] stringValue) {
		String refString = myString;
		// FIXME undefined method NSNumberFormatterBehavior
		String scanString = scanSet.getCharacterSet().toString();
		if (!caseSensitive) {
			refString = refString.toLowerCase();
			scanString = scanString.toLowerCase();
		}
		if (refString.contains(scanString)) {
			int end = refString.indexOf(scanString);
			stringValue[0] = new NSString(refString.substring(scanLocation, end));
			return true;
		}
		stringValue[0] = new NSString(myString);
		return true;
		// TODO verify
	}

	/**
	 * @Signature: scanUpToCharactersFromSet:intoString:
	 * @Declaration : - (BOOL)scanUpToCharactersFromSet:(NSCharacterSet *)stopSet intoString:(NSString **)stringValue
	 * @Description : Scans the string until a character from a given character set is encountered, accumulating characters into a string
	 *              thatâ€™s returned by reference.
	 * @param stopSet The set of characters up to which to scan.
	 * @param stringValue Upon return, contains the characters scanned.
	 * @return Return Value YES if the receiver scanned any characters, otherwise NO. If the only scanned characters are in the
	 *         charactersToBeSkipped character set (which is the whitespace and newline character set by default), then returns NO.
	 * @Discussion Invoke this method with NULL as stringValue to simply scan up to a given set of characters. If no characters in stopSet
	 *             are present in the scanner's source string, the remainder of the source string is put into stringValue, the receiverâ€™s
	 *             scanLocation is advanced to the end of the source string, and the method returns YES.
	 **/

	public boolean scanUpToCharactersFromSetIntoString(NSCharacterSet stopSet, NSString[] stringValue) {
		String refString = myString;
		// FIXME undefined method NSNumberFormatterBehavior
		String scanString = stopSet.getCharacterSet().toString();
		if (!caseSensitive) {
			refString = refString.toLowerCase();
			scanString = scanString.toLowerCase();
		}
		if (refString.contains(scanString)) {
			int end = refString.indexOf(scanString);
			stringValue[0] = new NSString(refString.substring(0, end));
			return true;
		}
		stringValue[0] = new NSString(myString);
		return true;
		// TODO verify
	}

	/**
	 * @Signature: scanDecimal:
	 * @Declaration : - (BOOL)scanDecimal:(NSDecimal *)decimalValue
	 * @Description : Scans for an NSDecimal value, returning a found value by reference.
	 * @param decimalValue Upon return, contains the scanned value. See the NSDecimalNumber class specification for more information about
	 *            NSDecimal values.
	 * @return Return Value YES if the receiver finds a valid NSDecimal representation, otherwise NO.
	 * @Discussion Invoke this method with NULL as decimalValue to simply scan past an NSDecimal representation.
	 **/

	public boolean scanDecimal(NSDecimal[] decimalValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("^-?\\d*\\.\\d+$");
			String doubleResult = null;
			String resteString = myString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				doubleResult = matcher.group();
				scanLocation = scanLocation + doubleResult.length();
				NSDecimal decimal = new NSDecimal();
				decimal.setWrappedNumber(Double.valueOf(doubleResult));
				decimalValue[0] = decimal;
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanDouble:
	 * @Declaration : - (BOOL)scanDouble:(double *)doubleValue
	 * @Description : Scans for a double value, returning a found value by reference.
	 * @param doubleValue Upon return, contains the scanned value. Contains HUGE_VAL or â€“HUGE_VAL on overflow, or 0.0 on underflow.
	 * @return Return Value YES if the receiver finds a valid floating-point representation, otherwise NO.
	 * @Discussion Skips past excess digits in the case of overflow, so the scannerâ€™s position is past the entire floating-point
	 *             representation. Invoke this method with NULL as doubleValue to simply scan past a double value representation.
	 *             Floating-point representations are assumed to be IEEE compliant.
	 **/

	public boolean scanDouble(double[] doubleValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
			String floatResult = null;
			String refString = myString;
			if (!caseSensitive) {
				refString = refString.toLowerCase();
			}
			String resteString = refString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				floatResult = matcher.group();
				scanLocation = scanLocation + floatResult.length();
				doubleValue[0] = Double.valueOf(floatResult);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanFloat:
	 * @Declaration : - (BOOL)scanFloat:(float *)floatValue
	 * @Description : Scans for a float value, returning a found value by reference.
	 * @param floatValue Upon return, contains the scanned value. Contains HUGE_VAL or â€“HUGE_VAL on overflow, or 0.0 on underflow.
	 * @return Return Value YES if the receiver finds a valid floating-point representation, otherwise NO.
	 * @Discussion Skips past excess digits in the case of overflow, so the scannerâ€™s position is past the entire floating-point
	 *             representation. Invoke this method with NULL as floatValue to simply scan past a float value representation.
	 *             Floating-point representations are assumed to be IEEE compliant.
	 **/

	public boolean scanFloat(float[] floatValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
			String floatResult = null;
			String refString = myString;
			if (!caseSensitive) {
				refString = refString.toLowerCase();
			}
			String resteString = refString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				floatResult = matcher.group();
				scanLocation = scanLocation + floatResult.length();
				floatValue[0] = Float.valueOf(floatResult);
				return true;
			}
			return false;
		}
		return false;
	}

	public static final Adressable adressable = new Adressable() {

		float[] temp;
		double[] doubleVlaue;
		int[] intValue;

		@Override
		public boolean scanFloat(float floatValue, NSScanner y) {
			temp = new float[] { floatValue };
			return y.scanFloat(temp);
		}

		@Override
		public boolean scanHexDouble(double result, NSScanner y) {
			doubleVlaue = new double[] { result };
			return y.scanHexDouble(doubleVlaue);
		}

		@Override
		public boolean scanHexInt(int result, NSScanner y) {
			intValue = new int[] { result };
			return y.scanHexInt(intValue);
		}

		@Override
		public float[] getScanFloatArray() {
			return temp;
		}

		@Override
		public double[] getScanHexDoubleArray() {
			return doubleVlaue;
		}

		@Override
		public int[] getScanHexIntArray() {
			return intValue;
		}

	};

	public interface Adressable {
		public boolean scanFloat(float floatValue, NSScanner y);

		public boolean scanHexDouble(double result, NSScanner y);

		public boolean scanHexInt(int result, NSScanner y);

		public float[] getScanFloatArray();

		public double[] getScanHexDoubleArray();

		public int[] getScanHexIntArray();
	}

	/**
	 * @Signature: scanHexDouble:
	 * @Declaration : - (BOOL)scanHexDouble:(double *)result
	 * @Description : Scans for a double value from a hexadecimal representation, returning a found value by reference.
	 * @param result Upon return, contains the scanned value.
	 * @return Return Value YES if the receiver finds a valid double-point representation, otherwise NO.
	 * @Discussion This corresponds to %a or %A formatting. The hexadecimal double representation must be preceded by 0x or 0X. Invoke this
	 *             method with NULL as result to simply scan past a hexadecimal double representation.
	 **/

	public boolean scanHexDouble(double[] result) {
		if (scanner.hasNext()) {
			String next = scanner.next();
			if (next.toLowerCase().contains("0x"))
				next = next.replace("0x", "");
			Long i = Long.parseLong(next, 16);
			result[0] = i.doubleValue();
			return true;
		}
		return false;

	}

	/**
	 * @Signature: scanHexFloat:
	 * @Declaration : - (BOOL)scanHexFloat:(float *)result
	 * @Description : Scans for a double value from a hexadecimal representation, returning a found value by reference.
	 * @param result Upon return, contains the scanned value.
	 * @return Return Value YES if the receiver finds a valid float-point representation, otherwise NO.
	 * @Discussion This corresponds to %a or %A formatting. The hexadecimal float representation must be preceded by 0x or 0X. Invoke this
	 *             method with NULL as result to simply scan past a hexadecimal float representation.
	 **/

	public boolean scanHexFloat(float[] result) {
		if (scanner.hasNext()) {
			String next = scanner.next();
			if (next.toLowerCase().contains("0x"))
				next = next.replace("0x", "");
			Long i = Long.parseLong(next, 16);
			result[0] = i.floatValue();
			return true;
		}
		return false;
	}

	/**
	 * @Signature: scanHexInt:
	 * @Declaration : - (BOOL)scanHexInt:(unsigned int *)intValue
	 * @Description : Scans for an unsigned value from a hexadecimal representation, returning a found value by reference.
	 * @param intValue Upon return, contains the scanned value. Contains UINT_MAX on overflow.
	 * @return Return Value Returns YES if the receiver finds a valid hexadecimal integer representation, otherwise NO.
	 * @Discussion The hexadecimal integer representation may optionally be preceded by 0x or 0X. Skips past excess digits in the case of
	 *             overflow, so the receiverâ€™s position is past the entire hexadecimal representation. Invoke this method with NULL as
	 *             intValue to simply scan past a hexadecimal integer representation.
	 **/

	public boolean scanHexInt(int[] intValue) {
		if (scanner.hasNext()) {
			String next = scanner.next();
			if (next.toLowerCase().contains("0x"))
				next = next.replace("0x", "");
			Long i = Long.parseLong(next, 16);
			intValue[0] = i.intValue();
			return true;
		}
		return false;
	}

	/**
	 * @Signature: scanHexLongLong:
	 * @Declaration : - (BOOL)scanHexLongLong:(unsigned long long *)result
	 * @Description : Scans for a long long value from a hexadecimal representation, returning a found value by reference.
	 * @param result Upon return, contains the scanned value.
	 * @return Return Value YES if the receiver finds a valid hexadecimal long long representation, otherwise NO.
	 * @Discussion The hexadecimal integer representation may optionally be preceded by 0x or 0X. Skips past excess digits in the case of
	 *             overflow, so the receiverâ€™s position is past the entire hexadecimal representation. Invoke this method with NULL as
	 *             result to simply scan past a hexadecimal long long representation.
	 **/

	public boolean scanHexLongLong(long[] result) {
		if (scanner.hasNext()) {
			String next = scanner.next();
			if (next.toLowerCase().contains("0x"))
				next = next.replace("0x", "");
			Long i = Long.parseLong(next, 16);
			result[0] = i.longValue();
			return true;

		}
		return false;
	}

	/**
	 * @Signature: scanInteger:
	 * @Declaration : - (BOOL)scanInteger:(NSInteger *)value
	 * @Description : Scans for an NSInteger value from a decimal representation, returning a found value by reference
	 * @param value Upon return, contains the scanned value.
	 * @return Return Value YES if the receiver finds a valid integer representation, otherwise NO.
	 * @Discussion Skips past excess digits in the case of overflow, so the receiverâ€™s position is past the entire integer representation.
	 *             Invoke this method with NULL as value to simply scan past a decimal integer representation.
	 **/

	public boolean scanInteger(long[] value) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("^-?\\d{1,19}$");
			String doubleString = null;
			String resteString = myString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				doubleString = matcher.group();
				scanLocation = scanLocation + doubleString.length();
				value[0] = Long.valueOf(doubleString);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanInt:
	 * @Declaration : - (BOOL)scanInt:(int *)intValue
	 * @Description : Scans for an int value from a decimal representation, returning a found value by reference.
	 * @param intValue Upon return, contains the scanned value. Contains INT_MAX or INT_MIN on overflow.
	 * @return Return Value YES if the receiver finds a valid decimal integer representation, otherwise NO.
	 * @Discussion Skips past excess digits in the case of overflow, so the receiverâ€™s position is past the entire decimal representation.
	 *             Invoke this method with NULL as intValue to simply scan past a decimal integer representation.
	 **/

	public boolean scanInt(int[] intValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("^-?\\d+$");
			String intResult = null;
			String resteString = myString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				intResult = matcher.group();
				scanLocation = scanLocation + intResult.length();
				intValue[0] = Integer.valueOf(intResult);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanLongLong:
	 * @Declaration : - (BOOL)scanLongLong:(long long *)longLongValue
	 * @Description : Scans for a long long value from a decimal representation, returning a found value by reference.
	 * @param longLongValue Upon return, contains the scanned value. Contains LLONG_MAX or LLONG_MIN on overflow.
	 * @return Return Value YES if the receiver finds a valid decimal integer representation, otherwise NO.
	 * @Discussion All overflow digits are skipped. Skips past excess digits in the case of overflow, so the receiverâ€™s position is past the
	 *             entire decimal representation. Invoke this method with NULL as longLongValue to simply scan past a long decimal integer
	 *             representation.
	 **/

	public boolean scanLongLong(long[] longLongValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("^-?\\d{1,19}$");
			String doubleString = null;
			String resteString = myString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				doubleString = matcher.group();
				scanLocation = scanLocation + doubleString.length();
				longLongValue[0] = Long.valueOf(doubleString);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanString:intoString:
	 * @Declaration : - (BOOL)scanString:(NSString *)string intoString:(NSString **)stringValue
	 * @Description : Scans a given string, returning an equivalent string object by reference if a match is found.
	 * @param string The string for which to scan at the current scan location.
	 * @param stringValue Upon return, if the receiver contains a string equivalent to string at the current scan location, contains a
	 *            string equivalent to string.
	 * @return Return Value YES if string matches the characters at the scan location, otherwise NO.
	 * @Discussion If string is present at the current scan location, then the current scan location is advanced to after the string;
	 *             otherwise the scan location does not change. Invoke this method with NULL as stringValue to simply scan past a given
	 *             string.
	 **/

	public boolean scanStringIntoString(NSString string, NSString[] stringValue) {
		if (string == null) {
			return false;
		}
		if (stringValue == null) {
			stringValue = new NSString[] { new NSString("") };
		}
		if (!this.isAtEnd()) {
			String refString = myString;
			String searchedString = string.getWrappedString();
			if (!caseSensitive) {
				refString = refString.toLowerCase();
				searchedString = searchedString.toLowerCase();
			}
			int end = scanLocation + searchedString.length();
			String stringResult = myString.substring(scanLocation, end);
			if (!"".equalsIgnoreCase(stringResult) && stringResult != null &&
					stringResult.equals(string.getWrappedString()))  {
				stringValue[0] = new NSString(stringResult);
				scanLocation = end;
				return true;
			}
		}
		stringValue[0] = new NSString(myString);
		return false;
	}

	/**
	 * @Signature: scanUnsignedLongLong:
	 * @Declaration : - (BOOL)scanLongLong:(unsigned long long *)unsignedLongLongValue
	 * @Description : Scans for an unsigned long long value from a decimal representation, returning a found value by reference.
	 * @param unsignedLongLongValue Upon return, contains the scanned value. Contains ULLONG_MAX on overflow.
	 * @return Return Value YES if the receiver finds a valid decimal integer representation, otherwise NO.
	 * @Discussion All overflow digits are skipped. Skips past excess digits in the case of overflow, so the receiverâ€™s position is past the
	 *             entire decimal representation. Invoke this method with NULL as unsignedLongLongValue to simply scan past an unsigned long
	 *             decimal integer representation.
	 **/

	public boolean scanUnsignedLongLong(long[] unsignedLongLongValue) {
		if (!this.isAtEnd()) {
			Pattern pattern = Pattern.compile("^-?\\d{1,19}$");
			String doubleString = null;
			String resteString = myString.substring(scanLocation, myString.length());
			Matcher matcher = pattern.matcher(resteString);
			if (matcher.find()) {
				doubleString = matcher.group();
				scanLocation = scanLocation + doubleString.length();
				unsignedLongLongValue[0] = Long.valueOf(doubleString);
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @Signature: scanUpToString:intoString:
	 * @Declaration : - (BOOL)scanUpToString:(NSString *)stopString intoString:(NSString **)stringValue
	 * @Description : Scans the string until a given string is encountered, accumulating characters into a string thatâ€™s returned by
	 *              reference.
	 * @param stopString The string to scan up to.
	 * @param stringValue Upon return, contains any characters that were scanned.
	 * @return Return Value YES if the receiver scans any characters, otherwise NO. If the only scanned characters are in the
	 *         charactersToBeSkipped character set (which by default is the whitespace and newline character set), then this method returns
	 *         NO.
	 * @Discussion If stopString is present in the receiver, then on return the scan location is set to the beginning of that string. If
	 *             stopString is the first string in the receiver, then the method returns NO and stringValue is not changed. If the search
	 *             string (stopString) isn't present in the scanner's source string, the remainder of the source string is put into
	 *             stringValue, the receiverâ€™s scanLocation is advanced to the end of the source string, and the method returns YES. Invoke
	 *             this method with NULL as stringValue to simply scan up to a given string.
	 **/

	public boolean scanUpToStringIntoString(NSString stopString, NSString[] stringValue) {
		String refString = myString;
		if (stopString == null) {
			return false;
		}
		if (stringValue == null) {
			stringValue = new NSString[] { new NSString("") };
		}
		String scanString = stopString.getWrappedString();
		if (!caseSensitive) {
			refString = refString.toLowerCase();
			scanString = scanString.toLowerCase();
		}
		if (refString.contains(scanString)) {
			int end = myString.indexOf(scanString, scanLocation);
			if (end != -1) {
				String stringResult = myString.substring(scanLocation, end);
				if (!"".equalsIgnoreCase(stringResult) && stringResult != null) {
					stringValue[0] = new NSString(stringResult);
				}
				scanLocation = end;
				return true;
			}
		}
		stringValue[0] = new NSString(myString);
		return false;
	}

	/**
	 * @Signature: isAtEnd
	 * @Declaration : - (BOOL)isAtEnd
	 * @Description : Returns a Boolean value that indicates whether the receiver has exhausted all significant characters
	 * @return Return Value YES if the receiver has exhausted all significant characters in its string, otherwise NO. If only characters
	 *         from the set to be skipped remain, returns YES.
	 **/

	public boolean isAtEnd() {
		if (scanLocation >= myString.length())
			return true;
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {
		NSScanner nSc = NSScanner.scannerWithString(new NSString(myString));
		nSc.scanner = scanner;
		nSc.caseSensitive = caseSensitive;
		nSc.scanLocation = scanLocation;
		nSc.charactersToBeSkipped = charactersToBeSkipped;
		return nSc;
	}

}