
package com.myappconverter.java.foundations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;

import android.util.Log;



public class NSNumberFormatter extends NSFormatter {

	private DecimalFormat wrappedDecimalFormat;

	// public static final int NSNumberFormatterNoStyle = 0;
	// public static final int NSNumberFormatterDecimalStyle = 1;
	// public static final int NSNumberFormatterCurrencyStyle = 2;
	// public static final int NSNumberFormatterPercentStyle = 3;
	// public static final int NSNumberFormatterScientificStyle = 4;
	// public static final int NSNumberFormatterSpellOutStyle = 5;

	private int secondaryGroupingSize;
	private boolean usesGroupingSeparator = true;
	private String defaultPaddingString = "*";
	private int defaultPaddingPosition;
	private boolean isLenient;
	private int minimumInput;
	private int maximumInput;
	private boolean partialStringValidationEnabled;
	private boolean usesSignificantDigits;
	private String minimumSignificantDigits;
	private String maximumSignificantDigits;
	private int formatWidth;
	private NSNumber RoundingIncrementValue;

	private boolean doesGeneratesDecimalNumbers;

	private static NSNumberFormatterStyle numberFormatterStyle;
	private static NSNumberFormatterBehavior numberFormatterBehavior;
	private NSString negativeINF;
	private NSString positiveINF;
	private NSString nilSymbol;

	private NSDictionary<NSObject, NSObject> textAttributesForNegativeValues;
	private NSDictionary<NSObject, NSObject> textAttributesForPositiveValues;
	private NSDictionary<NSObject, NSObject> textAttributesForZero;
	private NSDictionary<NSObject, NSObject> textAttributesForNil;
	private NSDictionary<NSObject, NSObject> textAttributesForNotANumber;
	private NSDictionary<NSObject, NSObject> textAttributesForPositiveInfinity;
	private NSDictionary<NSObject, NSObject> textAttributesForNegativeInfinity;

	
	public static enum NSNumberFormatterPadPosition {
		NSNumberFormatterPadBeforePrefix, //
		NSNumberFormatterPadAfterPrefix, //
		NSNumberFormatterPadBeforeSuffix, //
		NSNumberFormatterPadAfterSuffix,
	};

	
	public static enum NSNumberFormatterStyle {
		NSNumberFormatterNoStyle(0), //
		NSNumberFormatterDecimalStyle(1), //
		NSNumberFormatterCurrencyStyle(2), //
		NSNumberFormatterPercentStyle(3), //
		NSNumberFormatterScientificStyle(4), //
		NSNumberFormatterSpellOutStyle(5);

		int value;

		public int getValue() {
			return value;
		}

		NSNumberFormatterStyle(int v) {
			value = v;
		}

	};

	
	public static enum NSNumberFormatterBehavior {
		NSNumberFormatterBehaviorDefault(0), //
		NSNumberFormatterBehavior10_0(1000), //
		NSNumberFormatterBehavior10_4(1040);//

		int value;

		public int getValue() {
			return value;
		}

		NSNumberFormatterBehavior(int v) {
			value = v;
		}

	};

	
	public static enum NSNumberFormatterRoundingMode {
		NSNumberFormatterRoundCeiling, //
		NSNumberFormatterRoundFloor, //
		NSNumberFormatterRoundDown, //
		NSNumberFormatterRoundUp, //
		NSNumberFormatterRoundHalfEven, //
		NSNumberFormatterRoundHalfDown, //
		NSNumberFormatterRoundHalfUp//
	};

	public NSNumberFormatter() {
		wrappedDecimalFormat = new DecimalFormat();
	}

	/**
	 * The number style used by the receiver.
	 *
	 * @Declaration OBJECTIVE-C
	 * @property NSNumberFormatterStyle numberStyle
	 * @Discussion Styles are essentially predetermined sets of values for certain properties. Examples of number-formatter styles are those
	 *             used for decimal values, percentage values, and currency.
	 */
	public NSNumberFormatterStyle numberStyle = numberStyle();

	// Configuring Formatter Behavior and Style

	/**
	 * @Declaration : - (void)setFormatterBehavior:(NSNumberFormatterBehavior)behavior
	 * @Description : Sets the formatter behavior of the receiver.
	 * @param behavior An NSNumberFormatterBehavior constant that indicates the revision of the NSNumberFormatter class providing the
	 *            current behavior.
	 */
	
	public void setFormatterBehavior(NSNumberFormatterBehavior behavior) {
        numberFormatterBehavior = behavior;
	}

	/**
	 * @Declaration : - (NSNumberFormatterBehavior)formatterBehavior
	 * @Description : Returns an NSNumberFormatterBehavior constant that indicates the formatter behavior of the receiver.
	 * @return Return Value An NSNumberFormatterBehavior constant that indicates the formatter behavior of the receiver.
	 */
	
	public NSNumberFormatterBehavior formatterBehavior() {
		return numberFormatterBehavior;
	}

	/**
	 * @Declaration : + (NSNumberFormatterBehavior)defaultFormatterBehavior
	 * @Description : Returns an NSNumberFormatterBehavior constant that indicates default formatter behavior for new instances of
	 *              NSNumberFormatter.
	 * @return Return Value An NSNumberFormatterBehavior constant that indicates default formatter behavior for new instances of
	 *         NSNumberFormatter.
	 */
	
	public static NSNumberFormatterBehavior defaultFormatterBehavior() {
		return NSNumberFormatterBehavior.NSNumberFormatterBehaviorDefault;
	}

	/**
	 * @Declaration : + (void)setDefaultFormatterBehavior:(NSNumberFormatterBehavior)behavior
	 * @Description : Sets the default formatter behavior for new instances of NSNumberFormatter .
	 * @param behavior An NSNumberFormatterBehavior constant that indicates the revision of the class providing the default behavior.
	 */
	
	public static void setDefaultFormatterBehavior(NSNumberFormatterBehavior behavior) {
        numberFormatterBehavior = behavior;
	}

	/**
	 * @Declaration : - (NSNumberFormatterStyle)numberStyle
	 * @Description : Returns the number-formatter style of the receiver.
	 * @return Return Value An NSNumberFormatterStyle constant that indicates the number-formatter style of the receiver.
	 * @Discussion Styles are essentially predetermined sets of values for certain properties. Examples of number-formatter styles are those
	 *             used for decimal values, percentage values, and currency.
	 */
	
	public NSNumberFormatterStyle numberStyle() {
		return numberFormatterStyle;
	}

	/**
	 * @Declaration : - (void)setNumberStyle:(NSNumberFormatterStyle)style
	 * @Description : Sets the number style used by the receiver.
	 * @param style An NSNumberFormatterStyle constant that specifies a formatter style.
	 * @Discussion Styles are essentially predetermined sets of values for certain properties. Examples of number-formatter styles are those
	 *             used for decimal values, percentage values, and currency.
	 */
	
	public void setNumberStyle(NSNumberFormatterStyle style) {
		numberFormatterStyle = style;
	}

	/**
	 * @Declaration : - (BOOL)generatesDecimalNumbers
	 * @Description : Returns a Boolean value that indicates whether the receiver creates instances of NSDecimalNumber when it converts
	 *              strings to number objects.
	 * @return Return Value YES if the receiver creates instances of NSDecimalNumber when it converts strings to number objects, NO if it
	 *         creates instance of NSNumber.
	 */
	
	public boolean generatesDecimalNumbers() {
		return doesGeneratesDecimalNumbers;
	}

	/**
	 * @Declaration : - (void)setGeneratesDecimalNumbers:(BOOL)flag
	 * @Description : Controls whether the receiver creates instances of NSDecimalNumber when it converts strings to number objects.
	 * @param flag YES if the receiver should generate NSDecimalNumber instances, NO if it should generate NSNumber instances.
	 * @Discussion The default is YES.
	 */
	
	public void setGeneratesDecimalNumbers(boolean flag) {
		doesGeneratesDecimalNumbers = flag;
	}

	// Converting Between Numbers and Strings

	/**
	 * @Signature: getObjectValue:forString:range:error:
	 * @Declaration : - (BOOL)getObjectValue:(out id *)anObject forString:(NSString *)aString range:(inout NSRange *)rangep error:(out
	 *              NSError **)error
	 * @Description : Returns by reference a cell-content object after creating it from a range of characters in a given string.
	 * @param anObject On return, contains an instance of NSDecimalNumber or NSNumber based on the current value of generatesDecimalNumbers.
	 *            The default is to return NSDecimalNumber instances
	 * @param aString A string object with the range of characters specified in rangep that is used to create anObject.
	 * @param rangep A range of characters in aString. On return, contains the actual range of characters used to create the object.
	 * @param error If an error occurs, upon return contains an NSError object that explains why the conversion failed. If you pass in nil
	 *            for error you are indicating that you are not interested in error information.
	 * @return Return Value YES if the conversion from string to cell-content object was successful, otherwise NO. Returns nil if there are
	 *         no numbers in the passed string.
	 * @Discussion Any leading spaces in a string are ignored. For example, the strings “ 5�? and “5�? are handled in the same way. If there
	 *             is an error, calls control:didFailToFormatString:errorDescription: on the delegate.
	 **/
	
	public boolean getObjectValueForStringRangeError(NSDecimalNumber anObject, NSString aString,
			NSRange rangep, NSError[] error) {
		try {
			NSString newString = aString.substringWithRange(rangep);
			Number number = wrappedDecimalFormat.parse(newString.getWrappedString());
			if (doesGeneratesDecimalNumbers) {
				NSDecimalNumber nsDecimalNumber = new NSDecimalNumber();
				nsDecimalNumber.setDecimalNumber(BigDecimal.valueOf(number.doubleValue()));
			} else {
				NSNumber nsNumber = new NSNumber();
				nsNumber.setNumberValue(String.valueOf(number.doubleValue()));
			}
		} catch (java.text.ParseException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			error[0] = NSError.errorWithDomainCodeUserInfo(
					NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
		}
		return false;
	}

	/**
	 * @Signature: stringFromNumber:
	 * @Declaration : - (NSString *)stringFromNumber:(NSNumber *)number
	 * @Description : Returns a string containing the formatted value of the provided number object.
	 * @param number An NSNumber object that is parsed to create the returned string object.
	 * @return A string containing the formatted value of number using the receiver’s current settings.
	 **/
	
	public NSString stringFromNumber(NSNumber number) {
		return new NSString(Integer.toString(number.getIntegerValue()));
	}

	/**
	 * @Declaration : - (NSNumber *)numberFromString:(NSString *)string
	 * @Description : Returns an NSNumber object created by parsing a given string.
	 * @param string An NSString object that is parsed to generate the returned number object.
	 * @return Return Value An NSNumber object created by parsing string using the receiver’s format.
	 */
	
	public NSNumber numberFromString(NSString string) {
		NSNumber nsNumber = new NSNumber();
		try {
			nsNumber.setNumberValue(
					String.valueOf(wrappedDecimalFormat.parse(string.getWrappedString())));
			return nsNumber;
		} catch (java.text.ParseException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: localizedStringFromNumber:numberStyle:
	 * @Declaration : + (NSString *)localizedStringFromNumber:(NSNumber *)num numberStyle:(NSNumberFormatterStyle)localizationStyle
	 * @Description : Returns a localized number string with the specified style.
	 * @param num The number to localize
	 * @param localizationStyle The localization style to use. See “NSNumberFormatterStyle�? for the supported values.
	 * @return Return Value An appropriately formatted NSString.
	 **/
	
	public static NSString localizedStringFromNumberNumberStyle(NSNumber num,
			NSNumberFormatterStyle localizationStyle) {
		NumberFormat numberFormat;
		if (localizationStyle == NSNumberFormatterStyle.NSNumberFormatterCurrencyStyle) {
			numberFormat = NumberFormat.getCurrencyInstance();
		} else if (localizationStyle == NSNumberFormatterStyle.NSNumberFormatterDecimalStyle) {
			numberFormat = NumberFormat.getNumberInstance();
		} else if (localizationStyle == NSNumberFormatterStyle.NSNumberFormatterPercentStyle) {
			numberFormat = NumberFormat.getPercentInstance();
		} else if (localizationStyle == NSNumberFormatterStyle.NSNumberFormatterScientificStyle) {
			// TODO validate later
			numberFormat = DecimalFormat.getInstance();
			if (numberFormat instanceof DecimalFormat) {
				((DecimalFormat) numberFormat).applyPattern("0.##");
			}
		} else if (localizationStyle == NSNumberFormatterStyle.NSNumberFormatterSpellOutStyle) {
			numberFormat = NumberFormat.getInstance();
		} else {
			numberFormat = NumberFormat.getInstance();
		}

		return new NSString(numberFormat.format(num));
	}

	// Managing Localization of Numbers

	/**
	 * @Declaration : - (NSLocale *)locale
	 * @Description : Returns the locale of the receiver.
	 * @return Return Value The locale of the receiver.
	 * @Discussion A number formatter’s locale specifies default localization attributes, such as ISO country and language codes, currency
	 *             code, calendar, system of measurement, and decimal separator.
	 */
	
	public NSLocale locale() {
		NSLocale nsLocale = new NSLocale();
		return nsLocale.currentLocale();
	}

	/**
	 * @Declaration : - (void)setLocale:(NSLocale *)theLocale
	 * @Description : Sets the locale of the receiver.
	 * @param theLocale An NSLocale object representing the new locale of the receiver.
	 * @Discussion The locale determines the default values for many formatter attributes, such as ISO country and language codes, currency
	 *             code, calendar, system of measurement, and decimal separator.
	 */
	
	public void setLocale(NSLocale theLocale) {
		DecimalFormatSymbols dfsLocalized = DecimalFormatSymbols.getInstance(theLocale.getLocale());
		wrappedDecimalFormat.setDecimalFormatSymbols(dfsLocalized);
	}

	// Configuring Rounding Behavior

	/**
	 * @Declaration : - (NSNumber *)roundingIncrement
	 * @Description : Returns the rounding increment used by the receiver.
	 * @return Return Value The rounding increment used by the receiver.
	 */
	
	public NSNumber roundingIncrement() {
		return RoundingIncrementValue;
	}

	/**
	 * @Declaration : - (void)setRoundingIncrement:(NSNumber *)number
	 * @Description : Sets the rounding increment used by the receiver.
	 * @param number A number object specifying a rounding increment.
	 */
	
	public void setRoundingIncrement(NSNumber number) {
		this.RoundingIncrementValue = number;

	}

	/**
	 * @Declaration : - (void)setRoundingMode:(NSNumberFormatterRoundingMode)mode
	 * @Description : Sets the rounding mode used by the receiver.
	 * @param mode An NSNumberFormatterRoundingMode constant that indicates a rounding mode.
	 */
	
	public void setRoundingMode(NSNumberFormatterRoundingMode mode) {
		wrappedDecimalFormat.setRoundingMode(RoundingMode.valueOf(mode.ordinal()));
	}

	/**
	 * @Declaration : - (NSNumberFormatterRoundingMode)roundingMode
	 * @Description : Returns the rounding mode used by the receiver.
	 * @return Return Value The rounding mode used by the receiver.
	 */
	
	public NSNumberFormatterRoundingMode roundingMode() {
		// must define equivalent for each Java RoundingMode and Ios
		return NSNumberFormatterRoundingMode.valueOf(wrappedDecimalFormat.getRoundingMode().name());
	}

	// Configuring Numeric Formats
	/**
	 * @Signature: setFormatWidth:
	 * @Declaration : - (void)setFormatWidth:(NSUInteger)number
	 * @Description : Sets the format width used by the receiver.
	 * @param number An integer that specifies the format width.
	 * @Discussion The format width is the number of characters of a formatted number within a string that is either left justified or right
	 *             justified based on the value returned from paddingPosition.
	 **/
	
	public void setFormatWidth(int number) {
		formatWidth = number;
	}

	/**
	 * @Signature: formatWidth
	 * @Declaration : - (NSUInteger)formatWidth
	 * @Description : Returns the format width of the receiver.
	 * @Discussion The format width is the number of characters of a formatted number within a string that is either left justified or right
	 *             justified based on the value returned from paddingPosition.
	 **/
	
	public int formatWidth() {
		return formatWidth;

	}

	/**
	 * @Declaration : - (void)setNegativeFormat:(NSString *)aFormat
	 * @Description : Sets the format the receiver uses to display negative values.
	 * @param aFormat A string that specifies the format for negative values.
	 */
	
	public void setNegativeFormat(NSString aFormat) {
		wrappedDecimalFormat.setNegativePrefix(aFormat.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)negativeFormat
	 * @Description : Returns the format used by the receiver to display negative numbers.
	 */
	
	public NSString negativeFormat() {
		return new NSString(wrappedDecimalFormat.getNegativePrefix());
	}

	/**
	 * @Declaration : - (void)setPositiveFormat:(NSString *)aFormat
	 * @Description : Sets the format the receiver uses to display positive values.
	 * @param aFormat A string that specifies the format for positive values.
	 */
	
	public void setPositiveFormat(NSString aFormat) {
		wrappedDecimalFormat.setPositivePrefix(aFormat.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)positiveFormat
	 * @Description : Returns the format used by the receiver to display positive numbers.
	 */
	
	public NSString positiveFormat() {
		return new NSString(wrappedDecimalFormat.getPositivePrefix());
	}

	/**
	 * @Declaration : - (void)setMultiplier:(NSNumber *)number
	 * @Description : Sets the multiplier of the receiver.
	 * @param number A number object that represents a multiplier.
	 * @Discussion A multiplier is a factor used in conversions between numbers and strings (that is, numbers as stored and numbers as
	 *             displayed). When the input value is a string, the multiplier is used to divide, and when the input value is a number, the
	 *             multiplier is used to multiply. These operations allow the formatted values to be different from the values that a
	 *             program manipulates internally.
	 */
	
	public void setMultiplier(NSNumber number) {
		wrappedDecimalFormat.setMultiplier(number.intValue());
	}

	/**
	 * @Declaration : - (NSNumber *)multiplier
	 * @Description : Returns the multiplier used by the receiver as an NSNumber object.
	 * @Discussion A multiplier is a factor used in conversions between numbers and strings (that is, numbers as stored and numbers as
	 *             displayed). When the input value is a string, the multiplier is used to divide, and when the input value is a number, the
	 *             multiplier is used to multiply. These operations allow the formatted values to be different from the values that a
	 *             program manipulates internally.
	 */
	
	public NSNumber multiplier() {
		return NSNumber.numberWithInt(wrappedDecimalFormat.getMultiplier());
	}

	// Configuring Numeric Symbols

	/**
	 * @Declaration : - (void)setPercentSymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver to represent the percent symbol.
	 * @param string A string that represents a percent symbol.
	 */
	
	public void setPercentSymbol(NSString string) {
		DecimalFormatSymbols tmp = wrappedDecimalFormat.getDecimalFormatSymbols();
		tmp.setPercent(string.getWrappedString().charAt(0));
		wrappedDecimalFormat.setDecimalFormatSymbols(tmp);
	}

	/**
	 * @Declaration : - (NSString *)percentSymbol
	 * @Description : Returns the string that the receiver uses to represent the percent symbol.
	 */
	
	public NSString percentSymbol() {
		return new NSString(
				String.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getPercent()));
	}

	/**
	 * @Declaration : - (void)setPerMillSymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver to represent the per-mill (per-thousand) symbol.
	 * @param string A string that represents a per-mill symbol.
	 */
	
	public void setPerMillSymbol(NSString string) {
		DecimalFormatSymbols tmp = wrappedDecimalFormat.getDecimalFormatSymbols();
		tmp.setPerMill(string.getWrappedString().charAt(0));
		wrappedDecimalFormat.setDecimalFormatSymbols(tmp);
	}

	/**
	 * @Declaration : - (NSString *)perMillSymbol
	 * @Description : Returns the string that the receiver uses for the per-thousands symbol.
	 * @return Return Value The string that the receiver uses for the per-thousands symbol.
	 */
	
	public NSString perMillSymbol() {
		return new NSString(
				String.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getPerMill()));
	}

	/**
	 * @Declaration : - (void)setMinusSign:(NSString *)string
	 * @Description : Sets the string used by the receiver for the minus sign.
	 * @param string A string that represents a minus sign.
	 */
	
	public void setMinusSign(NSString string) {
		wrappedDecimalFormat.setNegativePrefix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)minusSign
	 * @Description : Returns the string the receiver uses to represent the minus sign.
	 * @return Return Value The string that represents the receiver’s minus sign.
	 */
	
	public NSString minusSign() {
		return new NSString(wrappedDecimalFormat.getNegativePrefix());
	}

	/**
	 * @Declaration : - (void)setPlusSign:(NSString *)string
	 * @Description : Sets the string used by the receiver to represent the plus sign.
	 * @param string A string that represents a plus sign.
	 */
	
	public void setPlusSign(NSString string) {
		wrappedDecimalFormat.setPositivePrefix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)plusSign
	 * @Description : Returns the string the receiver uses for the plus sign.
	 * @return Return Value The string the receiver uses for the plus sign.
	 */
	
	public NSString plusSign() {
		return new NSString(wrappedDecimalFormat.getPositivePrefix());
	}

	/**
	 * @Declaration : - (void)setExponentSymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver to represent the exponent symbol.
	 * @param string A string that represents an exponent symbol.
	 * @Discussion The exponent symbol is the “E�? or “e�? in the scientific notation of numbers, as in 1.0e+56.
	 */
	
	public void setExponentSymbol(NSString string) {
		DecimalFormatSymbols tmp = wrappedDecimalFormat.getDecimalFormatSymbols();
		tmp.setExponentSeparator(string.getWrappedString());
		wrappedDecimalFormat.setDecimalFormatSymbols(tmp);
	}

	/**
	 * @Declaration : - (NSString *)exponentSymbol
	 * @Description : Returns the string the receiver uses as an exponent symbol.
	 * @return Return Value The string the receiver uses as an exponent symbol.
	 * @Discussion The exponent symbol is the “E�? or “e�? in the scientific notation of numbers, as in 1.0e+56.
	 */
	
	public NSString exponentSymbol() {
		return new NSString(wrappedDecimalFormat.getDecimalFormatSymbols().getExponentSeparator());
	}

	/**
	 * @Declaration : - (void)setZeroSymbol:(NSString *)string
	 * @Description : Sets the string the receiver uses as the symbol to show the value zero.
	 * @param string The string the receiver uses as the symbol to show the value zero.
	 * @Discussion By default this is 0; you might want to set it to, for example, “ - �?, similar to the way that a spreadsheet might when a
	 *             column is defined as accounting.
	 */
	
	public void setZeroSymbol(NSString string) {
		DecimalFormatSymbols tmp = wrappedDecimalFormat.getDecimalFormatSymbols();
		tmp.setZeroDigit(string.getWrappedString().charAt(0));
		wrappedDecimalFormat.setDecimalFormatSymbols(tmp);
	}

	/**
	 * @Declaration : - (NSString *)zeroSymbol
	 * @Description : Returns the string the receiver uses as the symbol to show the value zero.
	 * @return Return Value The string the receiver uses as the symbol to show the value zero.
	 * @Discussion For a discussion of how this is used, see setZeroSymbol:.
	 */
	
	public NSString zeroSymbol() {
		return new NSString(
				String.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getZeroDigit()));
	}

	/**
	 * @Declaration : - (void)setNilSymbol:(NSString *)string
	 * @Description : Sets the string the receiver uses to represent nil values.
	 * @param string A string that represents a nil value.
	 */
	
	public void setNilSymbol(NSString string) {
		nilSymbol = string;
	}

	/**
	 * @Declaration : - (NSString *)nilSymbol
	 * @Description : Returns the string the receiver uses to represent a nil value.
	 * @return Return Value The string the receiver uses to represent a nil value.
	 */
	
	public NSString nilSymbol() {
		if (nilSymbol != null)
			return nilSymbol;
		else
			return new NSString("");
	}

	/**
	 * @Declaration : - (void)setNotANumberSymbol:(NSString *)string
	 * @Description : Sets the string the receiver uses to represent NaN (“not a number�?).
	 * @param string A string that represents a NaN symbol.
	 */
	
	public void setNotANumberSymbol(NSString string) {
		wrappedDecimalFormat.getDecimalFormatSymbols().setNaN(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)notANumberSymbol
	 * @Description : Returns the symbol the receiver uses to represent NaN (“not a number�?) when it converts values.
	 * @return Return Value The symbol the receiver uses to represent NaN (“not a number�?) when it converts values.
	 */
	
	public NSString notANumberSymbol() {
		return new NSString(wrappedDecimalFormat.getDecimalFormatSymbols().getNaN());
	}

	/**
	 * @Declaration : - (void)setNegativeInfinitySymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver for the negative infinity symbol.
	 * @param string A string that represents a negative infinity symbol.
	 */
	
	public void setNegativeInfinitySymbol(NSString string) {
		negativeINF = string;
	}

	/**
	 * @Declaration : - (NSString *)negativeInfinitySymbol
	 * @Description : Returns the symbol the receiver uses to represent negative infinity.
	 * @return Return Value The symbol the receiver uses to represent negative infinity.
	 */
	
	public NSString negativeInfinitySymbol() {
		if (negativeINF != null)
			return negativeINF;
		else {
			return minusSign();
		}
	}

	/**
	 * @Declaration : - (void)setPositiveInfinitySymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver for the positive infinity symbol.
	 * @param string A string that represents a positive infinity symbol.
	 */
	
	public void setPositiveInfinitySymbol(NSString string) {
		positiveINF = string;
	}

	/**
	 * @Declaration : - (NSString *)positiveInfinitySymbol
	 * @Description : Returns the string the receiver uses for the positive infinity symbol.
	 * @return Return Value The string the receiver uses for the positive infinity symbol.
	 */
	
	public NSString positiveInfinitySymbol() {
		if (positiveINF != null)
			return positiveINF;
		else {
			return plusSign();
		}
	}

	// Configuring the Format of Currency

	/**
	 * @Declaration : - (void)setCurrencySymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver as a local currency symbol.
	 * @param string A string that represents a local currency symbol.
	 * @Discussion The local symbol is used within the country, while the international currency symbol is used in international contexts to
	 *             specify that country’s currency unambiguously. The local currency symbol is often represented by a Unicode code point.
	 */
	
	public void setCurrencySymbol(NSString string) {
		wrappedDecimalFormat.getDecimalFormatSymbols().setCurrencySymbol(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)currencySymbol
	 * @Description : Returns the receiver’s local currency symbol.
	 * @Discussion A country typically has a local currency symbol and an international currency symbol. The local symbol is used within the
	 *             country, while the international currency symbol is used in international contexts to specify that country’s currency
	 *             unambiguously. The local currency symbol is often represented by a Unicode code point.
	 */
	
	public NSString currencySymbol() {
		return new NSString(wrappedDecimalFormat.getCurrency().getSymbol());
	}

	/**
	 * @Declaration : - (void)setCurrencyCode:(NSString *)string
	 * @Description : Sets the receiver’s currency code.
	 * @param string A string specifying the receiver's new currency code.
	 * @Discussion A currency code is a three-letter code that is, in most cases, composed of a country’s two-character Internet country
	 *             code plus an extra character to denote the currency unit. For example, the currency code for the Australian dollar is
	 *             “AUD�?. Currency codes are based on the ISO 4217 standard.
	 */
	
	public void setCurrencyCode(NSString string) {
		Currency currency = Currency.getInstance(string.getWrappedString());
		wrappedDecimalFormat.setCurrency(currency);
	}

	/**
	 * @Declaration : - (NSString *)currencyCode
	 * @Description : Returns the receiver’s currency code as a string.
	 * @return Return Value The receiver’s currency code as a string.
	 * @Discussion A currency code is a three-letter code that is, in most cases, composed of a country’s two-character Internet country
	 *             code plus an extra character to denote the currency unit. For example, the currency code for the Australian dollar is
	 *             “AUD�?. Currency codes are based on the ISO 4217 standard.
	 */
	
	public NSString currencyCode() {
		return new NSString(wrappedDecimalFormat.getCurrency().getCurrencyCode());
	}

	/**
	 * @Declaration : - (void)setInternationalCurrencySymbol:(NSString *)string
	 * @Description : Sets the string used by the receiver for the international currency symbol.
	 * @param string A string that represents an international currency symbol.
	 * @Discussion The local symbol is used within the country, while the international currency symbol is used in international contexts to
	 *             specify that country’s currency unambiguously. The local currency symbol is often represented by a Unicode code point.
	 */
	
	public void setInternationalCurrencySymbol(NSString string) {
		wrappedDecimalFormat.getDecimalFormatSymbols()
				.setInternationalCurrencySymbol(string.getWrappedString());
		// DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		// dfs.setInternationalCurrencySymbol(symbol);
		// decimalFormat.setDecimalFormatSymbols(dfs);
	}

	/**
	 * @Declaration : - (NSString *)internationalCurrencySymbol
	 * @Description : Returns the international currency symbol used by the receiver.
	 * @Discussion A country typically has a local currency symbol and an international currency symbol. The local symbol is used within the
	 *             country, while the international currency symbol is used in international contexts to specify that country’s currency
	 *             unambiguously. The international currency symbol is often represented by a Unicode code point.
	 */
	
	public NSString internationalCurrencySymbol() {
		return new NSString(
				wrappedDecimalFormat.getDecimalFormatSymbols().getInternationalCurrencySymbol());
	}

	/**
	 * @Declaration : - (void)setCurrencyGroupingSeparator:(NSString *)string
	 * @Description : Sets the currency grouping separator for the receiver.
	 * @param string The currency grouping separator for the receiver.
	 */
	
	public void setCurrencyGroupingSeparator(NSString string) {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(string.getWrappedString().charAt(0));
		wrappedDecimalFormat.setDecimalFormatSymbols(dfs);
	}

	/**
	 * @Declaration : - (NSString *)currencyGroupingSeparator
	 * @Description : Returns the currency grouping separator for the receiver.
	 * @return Return Value The currency grouping separator for the receiver.
	 */
	
	public NSString currencyGroupingSeparator() {
		return new NSString(String
				.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator()));
	}

	// Configuring Numeric Prefixes and Suffixes

	/**
	 * @Declaration : - (void)setPositivePrefix:(NSString *)string
	 * @Description : Sets the string the receiver uses as the prefix for positive values.
	 * @param string A string to use as the prefix for positive values.
	 */
	
	public void setPositivePrefix(NSString string) {
		wrappedDecimalFormat.setPositivePrefix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)positivePrefix
	 * @Description : Returns the string the receiver uses as the prefix for positive values.
	 * @return Return Value The string the receiver uses as the prefix for positive values.
	 */
	
	public NSString positivePrefix() {
		return new NSString(wrappedDecimalFormat.getPositivePrefix());
	}

	/**
	 * @Declaration : - (void)setPositiveSuffix:(NSString *)string
	 * @Description : Sets the string the receiver uses as the suffix for positive values.
	 * @param string A string to use as the suffix for positive values.
	 */
	
	public void setPositiveSuffix(NSString string) {
		wrappedDecimalFormat.setPositiveSuffix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)positiveSuffix
	 * @Description : Returns the string the receiver uses as the suffix for positive values.
	 * @return Return Value The string the receiver uses as the suffix for positive values.
	 */
	
	public NSString positiveSuffix() {
		return new NSString(wrappedDecimalFormat.getPositiveSuffix());
	}

	/**
	 * @Declaration : - (void)setNegativePrefix:(NSString *)string
	 * @Description : Sets the string the receiver uses as a prefix for negative values.
	 * @param string A string to use as the prefix for negative values.
	 */
	
	public void setNegativePrefix(NSString string) {
		wrappedDecimalFormat.setNegativePrefix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)negativePrefix
	 * @Description : Returns the string the receiver inserts as a prefix to negative values.
	 * @return Return Value The string the receiver inserts as a prefix to negative values.
	 */
	
	public NSString negativePrefix() {
		return new NSString(wrappedDecimalFormat.getNegativePrefix());
	}

	// setNegativeSuffix:

	/**
	 * @Declaration : - (void)setNegativeSuffix:(NSString *)string
	 * @Description : Sets the string the receiver uses as a suffix for negative values.
	 * @param string A string to use as the suffix for negative values.
	 */
	
	public void setNegativeSuffix(NSString string) {
		wrappedDecimalFormat.setNegativeSuffix(string.getWrappedString());
	}

	/**
	 * @Declaration : - (NSString *)negativeSuffix
	 * @Description : Returns the string the receiver adds as a suffix to negative values.
	 * @return Return Value The string the receiver adds as a suffix to negative values.
	 */
	
	public NSString negativeSuffix() {
		return new NSString(wrappedDecimalFormat.getNegativeSuffix());
	}

	// Configuring the Display of Numeric Values

	/**
	 * @Declaration : - (void)setTextAttributesForNegativeValues:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes to be used in displaying negative values .
	 * @param newAttributes A dictionary containing properties for the display of negative values.
	 */
	
	public void setTextAttributesForNegativeValues(NSDictionary newAttributes) {
		textAttributesForNegativeValues.initWithDictionary(newAttributes);
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForNegativeValues
	 * @Description : Returns a dictionary containing the text attributes that have been set for negative values.
	 * @return Return Value A dictionary containing the text attributes that have been set for negative values.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForNegativeValues() {
		return textAttributesForNegativeValues;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForPositiveValues:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes to be used in displaying positive values.
	 * @param newAttributes A dictionary containing text attributes for the display of positive values.
	 * @Discussion See setTextAttributesForNegativeValues: for an example of how a related method might be used.
	 */
	
	public void setTextAttributesForPositiveValues(NSDictionary<NSObject, NSObject> newAttributes) {
		textAttributesForPositiveValues.initWithDictionary(newAttributes);
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForPositiveValues
	 * @Description : Returns a dictionary containing the text attributes that have been set for positive values.
	 * @return Return Value A dictionary containing the text attributes that have been set for positive values.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForPositiveValues() {
		return textAttributesForPositiveValues;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForZero:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes used to display a zero value.
	 * @param newAttributes A dictionary containing text attributes for the display of zero values.
	 */
	
	public void setTextAttributesForZero(NSDictionary<NSObject, NSObject> newAttributes) {
		textAttributesForZero = newAttributes;
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForZero
	 * @Description : Returns a dictionary containing the text attributes used to display a value of zero.
	 * @return Return Value A dictionary containing the text attributes used to display a value of zero.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForZero() {
		return textAttributesForZero;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForNil:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes used to display the nil symbol.
	 * @param newAttributes A dictionary containing text attributes for the display of the nil symbol.
	 */
	
	public void setTextAttributesForNil(NSDictionary<NSObject, NSObject> newAttributes) {
		textAttributesForNil = newAttributes;
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForNil
	 * @Description : Returns a dictionary containing the text attributes used to display the nil symbol.
	 * @return Return Value A dictionary containing the text attributes used to display the nil symbol.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForNil() {
		return textAttributesForNil;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForNotANumber:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes used to display the NaN ("not a number") string.
	 * @param newAttributes A dictionary containing text attributes for the display of the NaN symbol.
	 */
	
	public void setTextAttributesForNotANumber(NSDictionary<NSObject, NSObject> newAttributes) {
		textAttributesForNotANumber = newAttributes;
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForNotANumber
	 * @Description : Returns a dictionary containing the text attributes used to display the NaN ("not a number") symbol.
	 * @return Return Value A dictionary containing the text attributes used to display the NaN ("not a number") symbol.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForNotANumber() {
		return textAttributesForNotANumber;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForPositiveInfinity:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes used to display the positive infinity symbol.
	 * @param newAttributes A dictionary containing text attributes for the display of the positive infinity symbol.
	 */
	
	public void setTextAttributesForPositiveInfinity(
			NSDictionary<NSObject, NSObject> newAttributes) {
		textAttributesForPositiveInfinity = newAttributes;
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForPositiveInfinity
	 * @Description : Returns a dictionary containing the text attributes used to display the positive infinity symbol.
	 * @return Return Value A dictionary containing the text attributes used to display the positive infinity symbol.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForPositiveInfinity() {
		return textAttributesForPositiveInfinity;
	}

	/**
	 * @Declaration : - (void)setTextAttributesForNegativeInfinity:(NSDictionary *)newAttributes
	 * @Description : Sets the text attributes used to display the negative infinity symbol.
	 * @param newAttributes A dictionary containing text attributes for the display of the negative infinity symbol.
	 */
	
	public void setTextAttributesForNegativeInfinity(
			NSDictionary<NSObject, NSObject> newAttributes) {
		this.textAttributesForNegativeInfinity = newAttributes;
	}

	/**
	 * @Declaration : - (NSDictionary *)textAttributesForNegativeInfinity
	 * @Description : Returns a dictionary containing the text attributes used to display the negative infinity string.
	 * @return Return Value A dictionary containing the text attributes used to display the negative infinity string.
	 */
	
	public NSDictionary<NSObject, NSObject> textAttributesForNegativeInfinity() {
		return textAttributesForNegativeInfinity;
	}

	// Configuring Separators and Grouping Size

	/**
	 * @Declaration : - (void)setGroupingSeparator:(NSString *)string
	 * @Description : Specifies the string used by the receiver for a grouping separator.
	 * @param string A string that specifies the grouping separator to use.
	 */
	
	public void setGroupingSeparator(NSString string) {
		wrappedDecimalFormat.getDecimalFormatSymbols()
				.setGroupingSeparator(string.getWrappedString().charAt(0));
	}

	/**
	 * @Declaration : - (NSString *)groupingSeparator
	 * @Description : Returns a string containing the receiver’s grouping separator.
	 * @return Return Value A string containing the receiver’s grouping separator.
	 * @Discussion For example, the grouping separator used in the United States is the comma (“10,000�?) whereas in France it is the space
	 *             (“10 000�?).
	 */
	
	public NSString groupingSeparator() {
		return new NSString(String
				.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator()));
	}

	/**
	 * @Declaration : - (void)setUsesGroupingSeparator:(BOOL)flag
	 * @Description : Controls whether the receiver displays the grouping separator.
	 * @param flag YES if the receiver should display the grouping separator, NO otherwise.
	 */
	
	public void setUsesGroupingSeparator(boolean bool) {
		usesGroupingSeparator = bool;
	}

	/**
	 * @Declaration : - (BOOL)usesGroupingSeparator
	 * @Description : Returns a Boolean value that indicates whether the receiver uses the grouping separator.
	 * @return Return Value YES if the receiver uses the grouping separator, otherwise NO.
	 */
	
	public boolean usesGroupingSeparator() {
		return usesGroupingSeparator;
	}

	/**
	 * @Declaration : - (void)setDecimalSeparator:(NSString *)newSeparator
	 * @Description : Sets the character the receiver uses as a decimal separator.
	 * @param newSeparator The string that specifies the decimal-separator character to use. If newSeparator contains multiple characters,
	 *            only the first one is used.
	 * @Discussion If you don’t have decimal separators enabled through another means (such as setFormat:), using this method enables them.
	 */
	
	public void setDecimalSeparator(NSString newSeparator) {
		wrappedDecimalFormat.getDecimalFormatSymbols()
				.setDecimalSeparator(newSeparator.getWrappedString().charAt(0));
	}

	/**
	 * @Declaration : - (NSString *)decimalSeparator
	 * @Description : Returns a string containing the character the receiver uses to represent decimal separators.
	 * @return Return Value A string containing the character the receiver uses to represent decimal separators.
	 * @Discussion The return value doesn’t indicate whether decimal separators are enabled.
	 */
	
	public NSString decimalSeparator() {
		return new NSString(String
				.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator()));
	}

	/**
	 * @Declaration : - (void)setAlwaysShowsDecimalSeparator:(BOOL)flag
	 * @Description : Controls whether the receiver always shows the decimal separator, even for integer numbers.
	 * @param flag YES if the receiver should always show the decimal separator, NO otherwise.
	 */
	
	public void setAlwaysShowsDecimalSeparator(boolean flag) {
		wrappedDecimalFormat.setDecimalSeparatorAlwaysShown(flag);
	}

	/**
	 * @Declaration : - (BOOL)alwaysShowsDecimalSeparator
	 * @Description : Returns a Boolean value that indicates whether the receiver always shows a decimal separator, even if the number is an
	 *              integer.
	 * @return Return Value YES if the receiver always shows a decimal separator, even if the number is an integer, otherwise NO.
	 */
	
	public boolean alwaysShowsDecimalSeparator() {
		return wrappedDecimalFormat.isDecimalSeparatorAlwaysShown();
	}

	/**
	 * @Declaration : - (void)setCurrencyDecimalSeparator:(NSString *)string
	 * @Description : Sets the string used by the receiver as a decimal separator.
	 * @param string The string to use as the currency decimal separator.
	 */
	
	public void setCurrencyDecimalSeparator(NSString string) {
		wrappedDecimalFormat.getDecimalFormatSymbols()
				.setPatternSeparator(string.getWrappedString().charAt(0));
	}

	/**
	 * @Declaration : - (NSString *)currencyDecimalSeparator
	 * @Description : Returns the receiver’s currency decimal separator as a string.
	 * @return Return Value The receiver’s currency decimal separator as a string.
	 */
	
	public NSString currencyDecimalSeparator() {
		return new NSString(String
				.valueOf(wrappedDecimalFormat.getDecimalFormatSymbols().getPatternSeparator()));
	}

	/**
	 * @Signature: setGroupingSize:
	 * @Declaration : - (void)setGroupingSize:(NSUInteger)numDigits
	 * @Description : Sets the grouping size of the receiver.
	 * @param numDigits An integer that specifies the grouping size.
	 **/
	
	public void setGroupingSize(int numDigits) {
		wrappedDecimalFormat.setGroupingSize(numDigits);
	}

	/**
	 * @Signature: groupingSize
	 * @Declaration : - (NSUInteger)groupingSize
	 * @Description : Returns the receiver’s primary grouping size.
	 * @return Return Value The receiver’s primary grouping size.
	 **/
	
	public int groupingSize() {
		return wrappedDecimalFormat.getGroupingSize();
	}

	/**
	 * @Signature: setSecondaryGroupingSize:
	 * @Declaration : - (void)setSecondaryGroupingSize:(NSUInteger)number
	 * @Description : Sets the secondary grouping size of the receiver.
	 * @param number An integer that specifies the size of secondary groupings.
	 * @Discussion Some locales allow the specification of another grouping size for larger numbers. For example, some locales may represent
	 *             a number such as 61, 242, 378.46 (as in the United States) as 6,12,42,378.46. In this case, the secondary grouping size
	 *             (covering the groups of digits furthest from the decimal point) is 2.
	 **/
	
	public void setSecondaryGroupingSize(int number) {
		secondaryGroupingSize = number;
	}

	/**
	 * @Signature: secondaryGroupingSize
	 * @Declaration : - (NSUInteger)secondaryGroupingSize
	 * @Description : Returns the size of secondary groupings for the receiver.
	 * @return Return Value The size of secondary groupings for the receiver.
	 * @Discussion Some locales allow the specification of another grouping size for larger numbers. For example, some locales may represent
	 *             a number such as 61, 242, 378.46 (as in the United States) as 6,12,42,378.46. In this case, the secondary grouping size
	 *             (covering the groups of digits furthest from the decimal point) is 2.
	 **/
	
	public int secondaryGroupingSize() {
		return secondaryGroupingSize;
	}

	// Managing the Padding of Numbers

	/**
	 * @Declaration : - (void)setPaddingCharacter:(NSString *)string
	 * @Description : Sets the string that the receiver uses to pad numbers in the formatted string representation.
	 * @param string A string containing a padding character (or characters).
	 */
	
	public void setPaddingCharacter(NSString pattern) {
		defaultPaddingString = pattern.getWrappedString();
		wrappedDecimalFormat.setPositivePrefix("");
	}

	// padSpec = '*' padChar
	// padChar = '\\u0000'..'\\uFFFD'
	/**
	 * @Declaration : - (NSString *)paddingCharacter
	 * @Description : Returns a string containing the padding character for the receiver.
	 */
	
	public NSString paddingCharacter() {
		return new NSString(defaultPaddingString);
	}

	/**
	 * @Declaration : - (void)setPaddingPosition:(NSNumberFormatterPadPosition)position
	 * @Description : Sets the padding position used by the receiver.
	 * @param position An NSNumberFormatterPadPosition constant that indicates a padding position (before or after prefix or suffix).
	 */
	
	public void setPaddingPosition(NSNumberFormatterPadPosition position) {
		defaultPaddingPosition = position.ordinal();
	}

	/**
	 * @Declaration : - (NSNumberFormatterPadPosition)paddingPosition
	 * @Description : Returns the padding position of the receiver.
	 * @Discussion The returned constant indicates whether the padding is before or after the number’s prefix or suffix.
	 */
	
	public NSNumberFormatterPadPosition paddingPosition() {
		return NSNumberFormatterPadPosition.valueOf(String.valueOf(defaultPaddingPosition));
	}

	// Managing Input and Output Attributes

	/**
	 * @Declaration : - (void)setAllowsFloats:(BOOL)flag
	 * @Description : Sets whether the receiver allows as input floating-point values (that is, values that include the period character
	 *              [.]).
	 * @param flag YES if the receiver allows floating-point values, NO otherwise.
	 * @Discussion By default, floating point values are allowed as input.
	 */
	
	public void setAllowsFloats(boolean flag) {
		wrappedDecimalFormat.setParseBigDecimal(flag);
	}

	/**
	 * @Declaration : - (BOOL)allowsFloats
	 * @Description : Returns a Boolean value that indicates whether the receiver allows floating-point values as input.
	 * @return Return Value YES if the receiver allows as input floating-point values (that is, values that include the period character
	 *         [.]), otherwise NO.
	 * @Discussion When this method returns NO, only integer values can be provided as input. The default is YES.
	 */
	
	public boolean allowsFloats() {
		return !wrappedDecimalFormat.isParseIntegerOnly();
	}

	/**
	 * @Declaration : - (void)setMinimum:(NSNumber *)aMinimum
	 * @Description : Sets the lowest number the receiver allows as input.
	 * @param aMinimum A number object that specifies a minimum input value.
	 * @Discussion If aMinimum is nil, checking for the minimum value is disabled. For versions prior to OS X v10.4 (and number-formatter
	 *             behavior set to NSNumberFormatterBehavior10_0) this method requires an NSDecimalNumber argument.
	 */
	
	public void setMinimum(NSNumber aMinimum) {
		minimumInput = aMinimum.intValue();
	}

	/**
	 * @Declaration : - (NSNumber *)minimum
	 * @Description : Returns the lowest number allowed as input by the receiver.
	 * @return Return Value The lowest number allowed as input by the receiver or nil, meaning no limit.
	 * @Discussion For versions prior to OS X v10.4 (and number-formatter behavior set to NSNumberFormatterBehavior10_0) this method returns
	 *             an NSDecimalNumber object.
	 */
	
	public NSNumber minimum() {
		return NSNumber.numberWithInt(minimumInput);
	}

	/**
	 * @Declaration : - (void)setMaximum:(NSNumber *)aMaximum
	 * @Description : Sets the highest number the receiver allows as input.
	 * @param aMaximum A number object that specifies a maximum input value.
	 * @Discussion If aMaximum is nil, checking for the maximum value is disabled. For versions prior to OS X v10.4 (and number-formatter
	 *             behavior set to NSNumberFormatterBehavior10_0) this method requires an NSDecimalNumber argument.
	 */
	
	public void setMaximum(NSNumber aMaximum) {
		maximumInput = aMaximum.intValue();
	}

	/**
	 * @Declaration : - (NSNumber *)maximum
	 * @Description : Returns the highest number allowed as input by the receiver.
	 * @return Return Value The highest number allowed as input by the receiver or nil, meaning no limit.
	 * @Discussion For versions prior to OS X v10.4 (and number-formatter behavior set to NSNumberFormatterBehavior10_0) this method returns
	 *             an NSDecimalNumber object.
	 */
	
	public int maximum() {
		return maximumInput;
	}

	/**
	 * @Signature: setMinimumIntegerDigits:
	 * @Declaration : - (void)setMinimumIntegerDigits:(NSUInteger)number
	 * @Description : Sets the minimum number of integer digits allowed as input and output by the receiver.
	 * @param number The minimum number of integer digits allowed as input and output.
	 **/
	
	public void setMinimumIntegerDigits(int value) {
		wrappedDecimalFormat.setMinimumIntegerDigits(value);
	}

	/**
	 * @Signature: minimumIntegerDigits
	 * @Declaration : - (NSUInteger)minimumIntegerDigits
	 * @Description : Returns the minimum number of integer digits allowed as input and output by the receiver.
	 * @return Return Value The minimum number of integer digits allowed as input and output.
	 **/
	
	public int minimumIntegerDigits() {
		return wrappedDecimalFormat.getMinimumIntegerDigits();
	}

	/**
	 * @Signature: setMinimumFractionDigits:
	 * @Declaration : - (void)setMinimumFractionDigits:(NSUInteger)number
	 * @Description : Sets the minimum number of digits after the decimal separator allowed as input and output by the receiver.
	 * @param number The minimum number of digits after the decimal separator allowed as input and output.
	 **/
	
	public void setMinimumFractionDigits(int number) {
		wrappedDecimalFormat.setMinimumFractionDigits(number);
	}

	/**
	 * @Signature: minimumFractionDigits
	 * @Declaration : - (NSUInteger)minimumFractionDigits
	 * @Description : Returns the minimum number of digits after the decimal separator allowed as input and output by the receiver.
	 * @return Return Value The minimum number of digits after the decimal separator allowed as input and output.
	 **/
	
	public int minimumFractionDigits() {
		return wrappedDecimalFormat.getMinimumFractionDigits();
	}

	/**
	 * @Signature: setMaximumIntegerDigits:
	 * @Declaration : - (void)setMaximumIntegerDigits:(NSUInteger)number
	 * @Description : Sets the maximum number of integer digits allowed as input and output by the receiver.
	 * @param number The maximum number of integer digits allowed as input and output.
	 **/
	
	public void setMaximumIntegerDigits(int number) {
		wrappedDecimalFormat.setMaximumIntegerDigits(number);
	}

	/**
	 * @Signature: maximumIntegerDigits
	 * @Declaration : - (NSUInteger)maximumIntegerDigits
	 * @Description : Returns the maximum number of integer digits allowed as input and output by the receiver.
	 * @return Return Value The maximum number of integer digits allowed as input and output.
	 **/
	
	public int maximumIntegerDigits() {
		return wrappedDecimalFormat.getMaximumIntegerDigits();
	}

	/**
	 * @Signature: setMaximumFractionDigits:
	 * @Declaration : - (void)setMaximumFractionDigits:(NSUInteger)number
	 * @Description : Sets the maximum number of digits after the decimal separator allowed as input and output by the receiver.
	 * @param number The maximum number of digits after the decimal separator allowed as input and output.
	 **/
	
	public void setMaximumFractionDigits(int number) {
		wrappedDecimalFormat.setMaximumFractionDigits(number);
	}

	/**
	 * @Signature: maximumFractionDigits
	 * @Declaration : - (NSUInteger)maximumFractionDigits
	 * @Description : Returns the maximum number of digits after the decimal separator allowed as input and output by the receiver.
	 * @return Return Value The maximum number of digits after the decimal separator allowed as input and output.
	 **/
	
	public int maximumFractionDigits() {
		return wrappedDecimalFormat.getMaximumFractionDigits();
	}

	// Configuring Significant Digits

	/**
	 * @Declaration : - (void)setUsesSignificantDigits:(BOOL)b
	 * @Description : Sets whether the receiver uses significant digits.
	 * @param b YES if the receiver uses significant digits, otherwise NO.
	 */
	
	public void setUsesSignificantDigits(boolean b) {
		usesSignificantDigits = b;
	}

	/**
	 * @Declaration : - (BOOL)usesSignificantDigits
	 * @Description : Returns a Boolean value that indicates whether the receiver uses significant digits.
	 * @return Return Value YES if the receiver uses significant digits, otherwise NO.
	 */
	
	public boolean usesSignificantDigits() {
		return usesSignificantDigits;
	}

	/**
	 * @Signature: setMinimumSignificantDigits:
	 * @Declaration : - (void)setMinimumSignificantDigits:(NSUInteger)number
	 * @Description : Sets the minimum number of significant digits for the receiver.
	 * @param number The minimum number of significant digits for the receiver.
	 **/
	
	public void setMinimumSignificantDigits(int number) {
		setMaximumFractionDigits(number);
	}

	/**
	 * @Signature: minimumSignificantDigits
	 * @Declaration : - (NSUInteger)minimumSignificantDigits
	 * @Description : Returns the minimum number of significant digits for the receiver.
	 * @return Return Value The minimum number of significant digits for the receiver.
	 **/
	
	public int minimumSignificantDigits() {
		return maximumFractionDigits();
	}

	/**
	 * @Signature: setMaximumSignificantDigits:
	 * @Declaration : - (void)setMaximumSignificantDigits:(NSUInteger)number
	 * @Description : Sets the maximum number of significant digits for the receiver.
	 * @param number The maximum number of significant digits for the receiver.
	 **/
	
	public void setMaximumSignificantDigits(int number) {
		setMaximumFractionDigits(number);
	}

	/**
	 * @Signature: maximumSignificantDigits
	 * @Declaration : - (NSUInteger)maximumSignificantDigits
	 * @Description : Returns the maximum number of significant digits for the receiver.
	 * @return Return Value The maximum number of significant digits for the receiver.
	 **/
	
	public int maximumSignificantDigits() {
		return maximumFractionDigits();
	}

	// Managing Leniency Behavior
	/**
	 * @Declaration : - (void)setLenient:(BOOL)b
	 * @Description : Sets whether the receiver will use heuristics to guess at the number which is intended by a string.
	 * @param b YES if the receiver will use heuristics to guess at the number which is intended by the string; otherwise NO.
	 * @Discussion If the formatter is set to be lenient, as with any guessing it may get the result number wrong (that is, a number other
	 *             than that which was intended).
	 */
	
	public void setLenient(boolean b) {
		isLenient = b;
	}

	/**
	 * @Declaration : - (BOOL)isLenient
	 * @Description : Returns a Boolean value that indicates whether the receiver uses heuristics to guess at the number which is intended
	 *              by a string.
	 * @return Return Value YES if the receiver uses heuristics to guess at the number which is intended by the string; otherwise NO.
	 */
	
	public boolean isLenient() {
		return isLenient;
	}

	// Managing the Validation of Partial Numeric Strings
	/**
	 * @Declaration : - (void)setPartialStringValidationEnabled:(BOOL)enabled
	 * @Description : Sets whether partial string validation is enabled for the receiver.
	 * @param enabled YES if partial string validation is enabled, otherwise NO.
	 */
	
	public void setPartialStringValidationEnabled(boolean enabled) {
		partialStringValidationEnabled = enabled;
	}

	/**
	 * @Declaration : - (BOOL)isPartialStringValidationEnabled
	 * @Description : Returns a Boolean value that indicates whether partial string validation is enabled.
	 * @return Return Value YES if partial string validation is enabled, otherwise NO.
	 */
	
	public boolean isPartialStringValidationEnabled() {
		return partialStringValidationEnabled;
	}

	// helper function
	public static String parseToScientificNotation(double valor) {
		int cont = 0;
		double tmpv = valor;
		DecimalFormat DECIMAL_FORMATER = new DecimalFormat("0.##");
		while (((int) tmpv) != 0) {
			tmpv /= 10;
			cont++;
		}
		return DECIMAL_FORMATER.format(tmpv).replace(",", ".") + " x10^ -" + cont;
	}

	@Override
	public NSString stringForObjectValue(NSObject obj) {
		return null;
	}

	@Override
	public NSAttributedString attributedStringForObjectValueWithDefaultAttributes(NSObject anObject,
			NSDictionary<?, ?> attributes) {
		return null;
	}

	@Override
	public NSString editingStringForObjectValue(NSObject obj) {
		return null;
	}

	@Override
	public boolean getObjectValueForStringErrorDescription(NSObject[] anObject, NSString string,
			NSString[] error) {
		return false;
	}

	@Override
	public boolean isPartialStringValidNewEditingStringErrorDescription(NSString partialString,
			NSString[] newString, NSString[] error) {
		return false;
	}

	@Override
	public boolean isPartialStringValidProposedSelectedRangeOriginalStringOriginalSelectedRangeErrorDescription(
			NSString partialStringPtr, NSRangePointer proposedSelRangePtr, NSString origString,
			NSRange origSelRange, NSString error) {
		return false;
	}

}