
package com.myappconverter.java.foundations;


import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;


public class NSDateFormatter extends NSFormatter {

	private String[] quarterSymbols = new String[] { "1st quarter", "2nd quarter", "3rd quarter", "4th quarter" };
	private String[] shortStandaloneQuarterSymbols = new String[] { "Q1", "Q2", "Q3", "Q4" };
	private NSArray<NSString> veryShortMonthSymbols = new NSArray<NSString>();
	private NSArray<NSString> veryShortWeekdaySymbols = new NSArray<NSString>();
	private NSDateFormatterBehavior defaultFormatterBehavior = NSDateFormatterBehavior.NSDateFormatterBehaviorDefault;
	private NSDateFormatterBehavior formatterBehavior = NSDateFormatterBehavior.NSDateFormatterBehaviorDefault;
	private NSDateFormatterStyle dateStyle = NSDateFormatterStyle.NSDateFormatterFullStyle;
	private NSDateFormatterStyle timeStyle = NSDateFormatterStyle.NSDateFormatterFullStyle;

	private SimpleDateFormat wrappedSimpleDateFormatter;

	public NSDateFormatter() {
		wrappedSimpleDateFormatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
	}

	
	public static enum NSDateFormatterStyle {
		NSDateFormatterNoStyle, //
		NSDateFormatterShortStyle, //
		NSDateFormatterMediumStyle, //
		NSDateFormatterLongStyle, //
		NSDateFormatterFullStyle;

		public int getValue() {
			return ordinal();
		}
	}

	
	public static enum NSDateFormatterBehavior {
		NSDateFormatterBehaviorDefault(0), //
		NSDateFormatterBehavior10_0(1000), //
		NSDateFormatterBehavior10_4(1040);//

		private int value;

		private NSDateFormatterBehavior(int v) {
			value = v;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}

	// Converting Objects
	/**
	 * @Signature: dateFromString:
	 * @Declaration : - (NSDate *)dateFromString:(NSString *)string
	 * @Description : Returns a date representation of a given string interpreted using the receiver’s current settings.
	 * @param string The string to parse.
	 * @return Return Value A date representation of string interpreted using the receiver’s current settings. If dateFromString: can not
	 *         parse the string, returns nil.
	 **/
	
	public NSDate dateFromString(NSString string) {
		try {
			Date date = wrappedSimpleDateFormatter.parse(string.getWrappedString());
			return new NSDate(date);
		} catch (ParseException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return null;
		}
		
	}

	/**
	 * @Signature: stringFromDate:
	 * @Declaration : - (NSString *)stringFromDate:(NSDate *)date
	 * @Description : Returns a string representation of a given date formatted using the receiver’s current settings.
	 * @param date The date to format.
	 * @return Return Value A string representation of date formatted using the receiver’s current settings.
	 **/
	
	public NSString stringFromDate(NSDate pDate) {
		if(pDate != null){
			Date mDate = pDate.getWrappedDate();
			wrappedSimpleDateFormatter.format(mDate);
			return new NSString(wrappedSimpleDateFormatter.format(mDate));
		}
		return null;
	}

	/**
	 * @Signature: localizedStringFromDate:dateStyle:timeStyle:
	 * @Declaration : + (NSString *)localizedStringFromDate:(NSDate *)date dateStyle:(NSDateFormatterStyle)dateStyle
	 *              timeStyle:(NSDateFormatterStyle)timeStyle
	 * @Description : Returns string representation of a given date formatted for the current locale using the specified date and time
	 *              styles.
	 * @param date A date.
	 * @param dateStyle A format style for the date. For possible values, see NSDateFormatterStyle.
	 * @param timeStyle A format style for the time. For possible values, see NSDateFormatterStyle.
	 * @return Return Value A localized string representation of date using the specified date and time styles
	 * @Discussion This method uses a date formatter configured with the current default settings. The returned string is the same as if you
	 *             configured and used a date formatter as shown in the following example: NSDateFormatter *formatter = [[NSDateFormatter
	 *             alloc] init]; [formatter setFormatterBehavior:NSDateFormatterBehavior10_4]; [formatter setDateStyle:dateStyle];
	 *             [formatter setTimeStyle:timeStyle]; NSString *result = [formatter stringForObjectValue:date];
	 **/
	
	public static NSString localizedStringFromDateDateStyleTimeStyle(NSDate date, NSDateFormatterStyle dateStyle,
			NSDateFormatterStyle tstyle) {

		NSDateFormatter formatter = new NSDateFormatter();
		formatter.setFormatterBehavior(NSDateFormatterBehavior.NSDateFormatterBehavior10_4);
		formatter.setDateStyle(dateStyle);
		formatter.setTimeStyle(tstyle);

		formatter.wrappedSimpleDateFormatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(dateStyle.ordinal(),
				tstyle.ordinal());
		return new NSString(formatter.wrappedSimpleDateFormatter.format(date));
	}

	/**
	 * @Declaration : - (BOOL)getObjectValue:(out id *)obj forString:(NSString *)string range:(inout NSRange *)rangep error:(out NSError
	 *              **)error
	 * @Description : Returns by reference a date representation of a given string and the range of the string used, and returns a Boolean
	 *              value that indicates whether the string could be parsed.
	 * @param obj If the receiver is able to parse string, upon return contains a date representation of string.
	 * @param string The string to parse.
	 * @param rangep If the receiver is able to parse string, upon return contains the range of string used to create the date.
	 * @param error If the receiver is unable to create a date by parsing string, upon return contains an NSError object that describes the
	 *            problem.
	 * @return Return Value YES if the receiver can create a date by parsing string, otherwise NO.
	 */
	
	public boolean getObjectValueForStringRangeError(Object[] obj, NSString string, NSRange[] rangep, NSError[] error) {
		boolean isParsed = false;
		String stringToParse = string.getWrappedString();
		try {
			obj[0] = wrappedSimpleDateFormatter.parse(stringToParse);
			if (obj != null) {
				isParsed = true;
				rangep[0].location = 0;
				rangep[0].length = stringToParse.trim().length();
			}
		} catch (ParseException e) {
			if (error != null) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
			}
		}
		return isParsed;
	}

	/**
	 * @Declaration : - (BOOL)generatesCalendarDates
	 * @Description : You should not use this method.
	 * @return Return Value YES if the receiver generates calendar dates, otherwise NO.
	 * @Discussion NSCalendarDate is no longer supported; you should not use this method.
	 */
	
	public boolean generatesCalendarDates() {
		return false;
	}

	/**
	 * @Declaration : - (void)setGeneratesCalendarDates:(BOOL)b
	 * @Description : You should not use this method.
	 * @param b A Boolean value that specifies whether the receiver generates calendar dates.
	 * @Discussion NSCalendarDate is no longer supported; you should not use this method.
	 */

	
	public void setGeneratesCalendarDates(boolean b) {
	}

	// Managing Formats and Styles

	/**
	 * @Declaration : - (NSString *)dateFormat
	 * @Description : Returns the date format string used by the receiver.
	 * @return Return Value The date format string used by the receiver.
	 * @Discussion See Data Formatting Guide for a list of the conversion specifiers permitted in date format strings.
	 **/
	
	public NSString dateFormat() {
		return new NSString(wrappedSimpleDateFormatter.toPattern());
	}


	public NSString getDateFormat() {
		dateFormat = new NSString(wrappedSimpleDateFormatter.toPattern());
		return dateFormat;
	}

	private NSString dateFormat;

	/**
	 * @Declaration : - (void)setDateFormat:(NSString *)string
	 * @Description : Sets the date format for the receiver.
	 * @param string The date format for the receiver. See Data Formatting Guide for a list of the conversion specifiers permitted in date
	 *            format strings.
	 */

	
	public void setDateFormat(NSString string) {
		wrappedSimpleDateFormatter = new SimpleDateFormat(string.getWrappedString(), Locale.getDefault());
		this.dateFormat = string;
	}

	/**
	 * @Declaration : - (NSDateFormatterStyle)dateStyle
	 * @Description : Returns the date style of the receiver.
	 * @return Return Value The date style of the receiver. For possible values, see NSDateFormatterStyle.
	 **/
	
	public NSDateFormatterStyle dateStyle() {
		return dateStyle;
	}

	/**
	 * @Declaration : - (void)setDateStyle:(NSDateFormatterStyle)style
	 * @Description : Sets the date style of the receiver.
	 * @param style The date style of the receiver. For possible values, see NSDateFormatterStyle.
	 **/
	
	public void setDateStyle(NSDateFormatterStyle style) {
		this.dateStyle = style;
	}

	/**
	 * @Declaration : - (NSDateFormatterStyle)timeStyle
	 * @Description : Returns the time style of the receiver.
	 * @return Return Value The time style of the receiver. For possible values, see NSDateFormatterStyle.
	 **/
	
	public NSDateFormatterStyle timeStyle() {
		return timeStyle;
	}

	/**
	 * @Declaration : - (void)setTimeStyle:(NSDateFormatterStyle)style
	 * @Description : Sets the time style of the receiver.
	 * @param style The time style for the receiver. For possible values, see NSDateFormatterStyle.
	 **/
	
	public void setTimeStyle(NSDateFormatterStyle style) {
		timeStyle = style;
	}

	/**
	 * @Signature: dateFormatFromTemplate:options:locale:
	 * @Declaration : + (NSString *)dateFormatFromTemplate:(NSString *)template options:(NSUInteger)opts locale:(NSLocale *)locale
	 * @Description : Returns a localized date format string representing the given date format components arranged appropriately for the
	 *              specified locale.
	 * @param template A string containing date format patterns. For full details, see Date and Time Programming
	 *            Guide.
	 * @param opts No options are currently defined—pass 0.
	 * @param locale The locale for which the template is required.
	 * @return Return Value A localized date format string representing the date format components given in template, arranged appropriately
	 *         for the locale specified by locale. The returned string may not contain exactly those components given in template, but
	 *         may—for example—have locale-specific adjustments applied.
	 * @Discussion Different locales have different conventions for the ordering of date components. You use this method to get an
	 *             appropriate format string for a given set of components for a specified locale (typically you use the current locale—see
	 *             currentLocale).
	 **/
	
	public static NSString dateFormatFromTemplateOptionsLocale(NSString template, int options, NSLocale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(template.getWrappedString(), locale.getLocale());
		return new NSString(sdf.toLocalizedPattern());
	}

	/**
	 * @Signature: timeZone
	 * @Declaration : - (NSTimeZone *)timeZone
	 * @Description : Returns the time zone for the receiver.
	 * @return Return Value The time zone for the receiver.
	 **/
	
	public NSTimeZone timeZone() {
		return new NSTimeZone((SimpleTimeZone) wrappedSimpleDateFormatter.getTimeZone());
	}

	/**
	 * @Signature: setTimeZone:
	 * @Declaration : - (void)setTimeZone:(NSTimeZone *)tz
	 * @Description : Sets the time zone for the receiver.
	 * @param tz The time zone for the receiver.
	 **/
	
	public void setTimeZone(NSTimeZone tz) {
		wrappedSimpleDateFormatter.getCalendar().setTimeZone(tz.getWrappedTimeZone());
	}

	// Managing Month Symbols

	/**
	 * @Signature: monthSymbols
	 * @Declaration : - (NSArray *)monthSymbols
	 * @Description : Returns the month symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the month symbols for the receiver.
	 **/
	
	public NSArray<NSString> monthSymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getMonths();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;
	}

	/**
	 * @Signature: setMonthSymbols:
	 * @Declaration : - (void)setMonthSymbols:(NSArray *)array
	 * @Description : Sets the month symbols for the receiver.
	 * @param array An array of NSString objects that specify the month symbols for the receiver.
	 **/
	
	public void setMonthSymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setMonths((String[]) list.toArray());
	}

	/**
	 * @Signature: shortMonthSymbols
	 * @Declaration : - (NSArray *)shortMonthSymbols
	 * @Description : Returns the array of short month symbols for the receiver.
	 * @return Return Value An array containing NSString objects representing the short month symbols for the receiver.
	 **/
	
	public NSArray<NSString> shortMonthSymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getShortMonths();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;
	}

	/**
	 * @Signature: setShortMonthSymbols:
	 * @Declaration : - (void)setShortMonthSymbols:(NSArray *)array
	 * @Description : Sets the short month symbols for the receiver.
	 * @param array An array of NSString objects that specify the short month symbols for the receiver.
	 **/
	
	public void setShortMonthSymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setShortMonths((String[]) list.toArray());
	}

	/**
	 * @Signature: veryShortMonthSymbols
	 * @Declaration : - (NSArray *)veryShortMonthSymbols
	 * @Description : Returns the very short month symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the very short month symbols for the receiver.
	 **/
	
	public NSArray<NSString> veryShortMonthSymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getShortMonths();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			if (veryShortMonthSymbols.getWrappedList().isEmpty()) {
				array.getWrappedList().add(new NSString(string.substring(0, 1)));
			} else {
				array.getWrappedList().add(new NSString(string));
			}
		}
		return array;
	}

	/**
	 * @Signature: setVeryShortMonthSymbols:
	 * @Declaration : - (void)setVeryShortMonthSymbols:(NSArray *)array
	 * @Description : Sets the very short month symbols for the receiver.
	 * @param array An array of NSString objects that specify the very short month symbols for the receiver.
	 **/
	
	public void setVeryShortMonthSymbols(NSArray<NSString> array) {
		veryShortMonthSymbols = new NSArray<NSString>();
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
			veryShortMonthSymbols.getWrappedList().add(string);
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setShortMonths((String[]) list.toArray());
	}

	/**
	 * @Signature: standaloneMonthSymbols
	 * @Declaration : - (NSArray *)standaloneMonthSymbols
	 * @Description : Returns the standalone month symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the standalone month symbols for the receiver.
	 **/
	
	public NSArray<NSString> standaloneMonthSymbols() {
		return monthSymbols();
	}

	/**
	 * @Signature: setStandaloneMonthSymbols:
	 * @Declaration : - (void)setStandaloneMonthSymbols:(NSArray *)array
	 * @Description : Sets the standalone month symbols for the receiver.
	 * @param array An array of NSString objects that specify the standalone month symbols for the receiver.
	 **/
	
	public void setStandaloneMonthSymbols(NSArray<NSString> array) {
		setMonthSymbols(array);
	}

	/**
	 * @Signature: shortStandaloneMonthSymbols
	 * @Declaration : - (NSArray *)shortStandaloneMonthSymbols
	 * @Description : Returns the short standalone month symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the short standalone month symbols for the receiver.
	 **/
	
	public NSArray<NSString> shortStandaloneMonthSymbols() {
		return shortMonthSymbols();
	}

	/**
	 * @Signature: setShortStandaloneMonthSymbols:
	 * @Declaration : - (void)setShortStandaloneMonthSymbols:(NSArray *)array
	 * @Description : Sets the short standalone month symbols for the receiver.
	 * @param array An array of NSString objects that specify the short standalone month symbols for the receiver.
	 **/
	
	public void setShortStandaloneMonthSymbols(NSArray<NSString> array) {
		setShortMonthSymbols(array);
	}

	/**
	 * @Signature: veryShortStandaloneMonthSymbols
	 * @Declaration : - (NSArray *)veryShortStandaloneMonthSymbols
	 * @Description : Returns the very short month symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the very short standalone month symbols for the receiver.
	 **/
	
	public NSArray<NSString> veryShortStandaloneMonthSymbols() {
		return veryShortMonthSymbols();
	}

	/**
	 * @Signature: setVeryShortStandaloneMonthSymbols:
	 * @Declaration : - (void)setVeryShortStandaloneMonthSymbols:(NSArray *)array
	 * @Description : Sets the very short standalone month symbols for the receiver.
	 * @param array An array of NSString objects that specify the very short standalone month symbols for the receiver.
	 **/
	
	public void setVeryShortStandaloneMonthSymbols(NSArray<NSString> array) {
		setVeryShortMonthSymbols(array);
	}

	/**
	 * @Signature: setPMSymbol:
	 * @Declaration : - (void)setPMSymbol:(NSString *)string
	 * @Description : Sets the PM symbol for the receiver.
	 * @param string The PM symbol for the receiver.
	 **/
	
	public void setPMSymbol(NSString string) {
		DateFormatSymbols sym = wrappedSimpleDateFormatter.getDateFormatSymbols();
		String[] listString = { AMSymbol().getWrappedString(), string.getWrappedString() };
		sym.setAmPmStrings(listString);
		wrappedSimpleDateFormatter.setDateFormatSymbols(sym);
	}

	/**
	 * @Signature: PMSymbol
	 * @Declaration : - (NSString *)PMSymbol
	 * @Description : Returns the PM symbol for the receiver.
	 * @return Return Value The PM symbol for the receiver.
	 **/
	
	public NSString PMSymbol() {
		return new NSString(wrappedSimpleDateFormatter.getDateFormatSymbols().getAmPmStrings()[Calendar.PM]);
	}

	public NSString getPMSymbol() {
		return PMSymbol();
	}

	private NSString PMSymbol;

	// Managing AM and PM Symbols

	/**
	 * @Signature: AMSymbol
	 * @Declaration : - (NSString *)AMSymbol
	 * @Description : Returns the AM symbol for the receiver.
	 * @return Return Value The AM symbol for the receiver.
	 **/
	
	public NSString AMSymbol() {
		return new NSString(wrappedSimpleDateFormatter.getDateFormatSymbols().getAmPmStrings()[Calendar.AM]);
	}

	public NSString getAMSymbol() {
		return AMSymbol();
	}

	private NSString AMSymbol;

	/**
	 * @Signature: setAMSymbol:
	 * @Declaration : - (void)setAMSymbol:(NSString *)string
	 * @Description : Sets the AM symbol for the receiver.
	 * @param string The AM symbol for the receiver.
	 **/
	
	public void setAMSymbol(NSString string) {
		DateFormatSymbols sym = wrappedSimpleDateFormatter.getDateFormatSymbols();
		String[] listString = { string.getWrappedString(), PMSymbol().getWrappedString() };
		sym.setAmPmStrings(listString);
		wrappedSimpleDateFormatter.setDateFormatSymbols(sym);
	}

	/**
	 * @Signature: setLenient:
	 * @Declaration : - (void)setLenient:(BOOL)b
	 * @Description : Sets whether the receiver uses heuristics when parsing a string.
	 * @param b YES to use heuristics when parsing a string to guess at the date which is intended, otherwise NO.
	 * @Discussion If a formatter is set to be lenient, when parsing a string it uses heuristics to guess at the date which is intended. As
	 *             with any guessing, it may get the result date wrong (that is, a date other than that which was intended).
	 **/
	
	public void setLenient(boolean lenient) {
		wrappedSimpleDateFormatter.setLenient(lenient);
	}

	// Managing Natural Language Support

	/**
	 * @Signature: isLenient
	 * @Declaration : - (BOOL)isLenient
	 * @Description : Returns a Boolean value that indicates whether the receiver uses heuristics when parsing a string.
	 * @return Return Value YES if the receiver has been set to use heuristics when parsing a string to guess at the date which is intended,
	 *         otherwise NO.
	 **/
	
	public boolean isLenient() {
		return wrappedSimpleDateFormatter.getCalendar().isLenient();
	}

	// Managing Behavior Version
	/**
	 * @Signature: formatterBehavior
	 * @Declaration : - (NSDateFormatterBehavior)formatterBehavior
	 * @Description : Returns the formatter behavior for the receiver.
	 * @return Return Value The formatter behavior for the receiver. For possible values, see NSDateFormatterBehavior.
	 **/
	
	public NSDateFormatterBehavior formatterBehavior() {
		return formatterBehavior;
	}

	/**
	 * @Signature: setFormatterBehavior:
	 * @Declaration : - (void)setFormatterBehavior:(NSDateFormatterBehavior)behavior
	 * @Description : Sets the formatter behavior for the receiver.
	 * @param behavior The formatter behavior for the receiver. For possible values, see NSDateFormatterBehavior.
	 **/
	
	public void setFormatterBehavior(NSDateFormatterBehavior behavior) {
		formatterBehavior = behavior;
	}

	/**
	 * @Signature: calendar
	 * @Declaration : - (NSCalendar *)calendar
	 * @Description : Returns the calendar for the receiver.
	 * @return Return Value The calendar for the receiver.
	 **/
	
	public NSCalendar calendar() {
		return new NSCalendar(this.wrappedSimpleDateFormatter.getCalendar());
	}

	public NSCalendar getCalendar() {
		return calendar();
	}

	private NSCalendar calendar;


	/**
	 * @Signature: setCalendar:
	 * @Declaration : - (void)setCalendar:(NSCalendar *)calendar
	 * @Description : Sets the calendar for the receiver.
	 * @param calendar The calendar for the receiver.
	 **/
	
	public void setCalendar(NSCalendar calendar) {
		wrappedSimpleDateFormatter.setCalendar(calendar.getWrappedCalendar());
	}

	/**
	 * @Signature: twoDigitStartDate
	 * @Declaration : - (NSDate *)twoDigitStartDate
	 * @Description : Returns the earliest date that can be denoted by a two-digit year specifier.
	 * @return Return Value The earliest date that can be denoted by a two-digit year specifier.
	 * @Discussion If the two-digit start date is set to January 6, 1976, then “January 1, 76 is interpreted as New Year's Day in 2076,
	 *             whereas “February 14, 76 is interpreted as Valentine's Day in 1976. The default date is December 31, 1949.
	 **/
	
	public NSDate twoDigitStartDate() {
		return new NSDate(wrappedSimpleDateFormatter.get2DigitYearStart());
	}

	/**
	 * @Signature: setTwoDigitStartDate:
	 * @Declaration : - (void)setTwoDigitStartDate:(NSDate *)date
	 * @Description : Sets the two-digit start date for the receiver.
	 * @param date The earliest date that can be denoted by a two-digit year specifier.
	 **/
	
	public void setTwoDigitStartDate(NSDate date) {
		wrappedSimpleDateFormatter.set2DigitYearStart(date.getWrappedDate());
	}

	/**
	 * @Signature: defaultDate
	 * @Declaration : - (NSDate *)defaultDate
	 * @Description : Returns the default date for the receiver.
	 * @return Return Value The default date for the receiver.
	 * @Discussion The default default date is nil.
	 **/
	
	public NSDate defaultDate() {
		return new NSDate(wrappedSimpleDateFormatter.getCalendar().getTime());
	}

	/**
	 * @Signature: setDefaultDate:
	 * @Declaration : - (void)setDefaultDate:(NSDate *)date
	 * @Description : Sets the default date for the receiver.
	 * @param date The default date for the receiver.
	 **/
	
	public void setDefaultDate(NSDate date) {
		wrappedSimpleDateFormatter.getCalendar().setTime(date.getWrappedDate());
	}

	/**
	 * @Signature: gregorianStartDate
	 * @Declaration : - (NSDate *)gregorianStartDate
	 * @Description : Returns the start date of the Gregorian calendar for the receiver.
	 * @return Return Value The start date of the Gregorian calendar for the receiver.
	 **/
	
	public NSDate gregorianStartDate() {
		return new NSDate(wrappedSimpleDateFormatter.getCalendar().getTime());
	}

	/**
	 * @Signature: setGregorianStartDate:
	 * @Declaration : - (void)setGregorianStartDate:(NSDate *)date
	 * @Description : Sets the start date of the Gregorian calendar for the receiver.
	 * @param date The start date of the Gregorian calendar for the receiver.
	 **/
	
	public void setGregorianStartDate(NSDate date) {
        wrappedSimpleDateFormatter.getCalendar().setTime(date.getWrappedDate());
	}

	/**
	 * @Signature: doesRelativeDateFormatting
	 * @Declaration : - (BOOL)doesRelativeDateFormatting
	 * @Description : Returns a Boolean value that indicates whether the receiver uses phrases such as “today and “tomorrow for the date
	 *              component.
	 * @return Return Value YES if the receiver uses relative date formatting, otherwise NO.
	 * @Discussion For a full discussion, see setDoesRelativeDateFormatting:.
	 **/
	
	public boolean doesRelativeDateFormatting() {
		return true;
	}

	/**
	 * @Signature: setDoesRelativeDateFormatting:
	 * @Declaration : - (void)setDoesRelativeDateFormatting:(BOOL)b
	 * @Description : Specifies whether the receiver uses phrases such as “today and “tomorrow for the date component.
	 * @param b YES to specify that the receiver should use relative date formatting, otherwise NO.
	 * @Discussion If a date formatter uses relative date formatting, where possible it replaces the date component of its output with a
	 *             phrase—such as “today or “tomorrow—that indicates a relative date. The available phrases depend on the locale for the
	 *             date formatter; whereas, for dates in the future, English may only allow “tomorrow, French may allow “the day after the
	 *             day after tomorrow,
	 **/
	
	public void setDoesRelativeDateFormatting(boolean b) {
		// Implemented in other way in android
	}

	// Managing Weekday Symbols

	/**
	 * @Signature: weekdaySymbols
	 * @Declaration : - (NSArray *)weekdaySymbols
	 * @Description : Returns the array of weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> weekdaySymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getWeekdays();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;
	}

	/**
	 * @Signature: setWeekdaySymbols:
	 * @Declaration : - (void)setWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the weekday symbols for the receiver.
	 * @param array An array of NSString objects that specify the weekday symbols for the receiver.
	 **/
	
	public void setWeekdaySymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setWeekdays((String[]) list.toArray());
	}

	/**
	 * @Signature: shortWeekdaySymbols
	 * @Declaration : - (NSArray *)shortWeekdaySymbols
	 * @Description : Returns the array of short weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the short weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> shortWeekdaySymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getShortWeekdays();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;
	}

	/**
	 * @Signature: setShortWeekdaySymbols:
	 * @Declaration : - (void)setShortWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the short weekday symbols for the receiver.
	 * @param array An array of NSString objects that specify the short weekday symbols for the receiver.
	 **/
	
	public void setShortWeekdaySymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setShortWeekdays((String[]) list.toArray());
	}

	/**
	 * @Signature: veryShortWeekdaySymbols
	 * @Declaration : - (NSArray *)veryShortWeekdaySymbols
	 * @Description : Returns the array of very short weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the very short weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> veryShortWeekdaySymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getShortWeekdays();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			if (veryShortWeekdaySymbols.getWrappedList().isEmpty()) {
				array.getWrappedList().add(new NSString(string.substring(0, 1)));
			} else {
				array.getWrappedList().add(new NSString(string));
			}
		}
		return array;
	}

	/**
	 * @Signature: setVeryShortWeekdaySymbols:
	 * @Declaration : - (void)setVeryShortWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the vert short weekday symbols for the receiver
	 * @param array An array of NSString objects that specify the very short weekday symbols for the receiver.
	 **/
	
	public void setVeryShortWeekdaySymbols(NSArray<NSString> array) {
		veryShortWeekdaySymbols = new NSArray<NSString>();
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
			veryShortWeekdaySymbols.getWrappedList().add(string);
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setShortWeekdays((String[]) list.toArray());
	}

	/**
	 * @Signature: standaloneWeekdaySymbols
	 * @Declaration : - (NSArray *)standaloneWeekdaySymbols
	 * @Description : Returns the array of standalone weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the standalone weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> standaloneWeekdaySymbols() {
		return weekdaySymbols();
	}

	/**
	 * @Signature: setStandaloneWeekdaySymbols:
	 * @Declaration : - (void)setStandaloneWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the standalone weekday symbols for the receiver.
	 * @param array An array of NSString objects that specify the standalone weekday symbols for the receiver.
	 **/
	
	public void setStandaloneWeekdaySymbols(NSArray<NSString> array) {
		setWeekdaySymbols(array);
	}

	/**
	 * @Signature: shortStandaloneWeekdaySymbols
	 * @Declaration : - (NSArray *)shortStandaloneWeekdaySymbols
	 * @Description : Returns the array of short standalone weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the short standalone weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> shortStandaloneWeekdaySymbols() {
		return shortWeekdaySymbols();
	}

	/**
	 * @Signature: setShortStandaloneWeekdaySymbols:
	 * @Declaration : - (void)setShortStandaloneWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the short standalone weekday symbols for the receiver.
	 * @param array An array of NSString objects that specify the short standalone weekday symbols for the receiver.
	 **/
	
	public void setShortStandaloneWeekdaySymbols(NSArray<NSString> array) {
		setShortStandaloneWeekdaySymbols(array);
	}

	/**
	 * @Signature: veryShortStandaloneWeekdaySymbols
	 * @Declaration : - (NSArray *)veryShortStandaloneWeekdaySymbols
	 * @Description : Returns the array of very short standalone weekday symbols for the receiver.
	 * @return Return Value An array of NSString objects that specify the very short standalone weekday symbols for the receiver.
	 **/
	
	public NSArray<NSString> veryShortStandaloneWeekdaySymbols() {
		return veryShortWeekdaySymbols();
	}

	/**
	 * @Signature: setVeryShortStandaloneWeekdaySymbols:
	 * @Declaration : - (void)setVeryShortStandaloneWeekdaySymbols:(NSArray *)array
	 * @Description : Sets the very short standalone weekday symbols for the receiver.
	 * @param array An array of NSString objects that specify the very short standalone weekday symbols for the receiver.
	 **/
	
	public void setVeryShortStandaloneWeekdaySymbols(NSArray<NSString> array) {
		setVeryShortWeekdaySymbols(array);
	}

	// Managing Attributes

	/**
	 * @Signature: locale
	 * @Declaration : - (NSLocale *)locale
	 * @Description : Returns the locale for the receiver.
	 * @return Return Value The locale for the receiver.
	 **/
	
	public NSLocale locale() {
		return new NSLocale(Locale.ENGLISH);
	}

	/**
	 * @Signature: setLocale:
	 * @Declaration : - (void)setLocale:(NSLocale *)locale
	 * @Description : Sets the locale for the receiver.
	 * @param locale The locale for the receiver.
	 **/
	
	public void setLocale(NSLocale locale) {
		Calendar mCal = Calendar.getInstance(locale.getLocale());
		wrappedSimpleDateFormatter.setCalendar(mCal);
	}

	/**
	 * @Signature: defaultFormatterBehavior
	 * @Declaration : + (NSDateFormatterBehavior)defaultFormatterBehavior
	 * @Description : Returns the default formatting behavior for instances of the class.
	 * @return Return Value The default formatting behavior for instances of the class. For possible values, see NSDateFormatterBehavior.
	 * @Discussion For iOS and for OS X applications linked against OS X v10.5 and later, the default is NSDateFormatterBehavior10_4.
	 **/
	
	public NSDateFormatterBehavior defaultFormatterBehavior() {
		return this.defaultFormatterBehavior;

	}

	/**
	 * @Signature: setDefaultFormatterBehavior:
	 * @Declaration : + (void)setDefaultFormatterBehavior:(NSDateFormatterBehavior)behavior
	 * @Description : Sets the default formatting behavior for instances of the class.
	 * @param behavior The default formatting behavior for instances of the class. For possible values, see NSDateFormatterBehavior.
	 **/
	
	public void setDefaultFormatterBehavior(NSDateFormatterBehavior behavior) {
		this.defaultFormatterBehavior = behavior;
	}

	
	@Override
	public NSString stringForObjectValue(NSObject obj) {
		return new NSString();
	}

	// Managing Quarter Symbols

	/**
	 * @Signature: quarterSymbols
	 * @Declaration : - (NSArray *)quarterSymbols
	 * @Description : Returns the quarter symbols for the receiver.
	 * @return Return Value An array containing NSString objects representing the quarter symbols for the receiver.
	 **/
	
	public NSArray<NSString> quarterSymbols() {
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < quarterSymbols.length - 1; i++) {
			String string = quarterSymbols[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;

	}

	/**
	 * @Signature: setQuarterSymbols:
	 * @Declaration : - (void)setQuarterSymbols:(NSArray *)array
	 * @Description : Sets the quarter symbols for the receiver.
	 * @param array An array of NSString objects that specify the quarter symbols for the receiver.
	 **/
	
	void setQuarterSymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		list.toArray(quarterSymbols);
	}

	/**
	 * @Signature: setShortQuarterSymbols:
	 * @Declaration : - (void)setShortQuarterSymbols:(NSArray *)array
	 * @Description : Sets the short quarter symbols for the receiver.
	 * @param array An array of NSString objects that specify the short quarter symbols for the receiver.
	 **/
	
	public void setShortQuarterSymbols(NSArray<NSString> array) {
		array.getWrappedList().toArray(shortStandaloneQuarterSymbols);
	}

	/**
	 * @Signature: standaloneQuarterSymbols
	 * @Declaration : - (NSArray *)standaloneQuarterSymbols
	 * @Description : Returns the standalone quarter symbols for the receiver.
	 * @return Return Value An array containing NSString objects representing the standalone quarter symbols for the receiver.
	 **/
	
	public NSArray<NSString> standaloneQuarterSymbols() {
		NSArray<NSString> nsArray = new NSArray<NSString>();
		List<NSString> list = new ArrayList<NSString>();
		for (String elmt : Arrays.asList(quarterSymbols)) {
			list.add(new NSString(elmt));
		}
		nsArray.setWrappedList(list);
		return nsArray;
	}

	/**
	 * @Signature: setStandaloneQuarterSymbols:
	 * @Declaration : - (void)setStandaloneQuarterSymbols:(NSArray *)array
	 * @Description : Sets the standalone quarter symbols for the receiver.
	 * @param array An array of NSString objects that specify the standalone quarter symbols for the receiver.
	 **/
	
	public void setStandaloneQuarterSymbols(NSArray<NSString> array) {
		array.getWrappedList().toArray(quarterSymbols);
	}

	/**
	 * @Signature: shortStandaloneQuarterSymbols
	 * @Declaration : - (NSArray *)shortStandaloneQuarterSymbols
	 * @Description : Returns the short standalone quarter symbols for the receiver.
	 * @return Return Value An array containing NSString objects representing the short standalone quarter symbols for the receiver.
	 **/
	
	public NSArray<NSString> shortStandaloneQuarterSymbols() {
		NSArray<NSString> nsArray = new NSArray<NSString>();
		List<NSString> list = new ArrayList<NSString>();
		for (String elmt : Arrays.asList(shortStandaloneQuarterSymbols)) {
			list.add(new NSString(elmt));
		}
		nsArray.setWrappedList(list);
		return nsArray;
	}

	/**
	 * @Signature: setShortStandaloneQuarterSymbols:
	 * @Declaration : - (void)setShortStandaloneQuarterSymbols:(NSArray *)array
	 * @Description : Sets the short standalone quarter symbols for the receiver.
	 * @param array An array of NSString objects that specify the short standalone quarter symbols for the receiver.
	 **/
	
	public void setShortStandaloneQuarterSymbols(NSArray<NSString> array) {
		array.getWrappedList().toArray(shortStandaloneQuarterSymbols);
	}

	// Managing Era Symbols

	/**
	 * @Signature: eraSymbols
	 * @Declaration : - (NSArray *)eraSymbols
	 * @Description : Returns the era symbols for the receiver.
	 * @return Return Value An array containing NSString objects representing the era symbols for the receiver (for example, {“B.C.E.,
	 *         “C.E.}).
	 **/
	
	public NSArray<NSString> eraSymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getEras();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			String string = list[i];
			array.getWrappedList().add(new NSString(string));
		}
		return array;
	}

	/**
	 * @Signature: setEraSymbols:
	 * @Declaration : - (void)setEraSymbols:(NSArray *)array
	 * @Description : Sets the era symbols for the receiver.
	 * @param array An array containing NSString objects representing the era symbols for the receiver (for example, {“B.C.E., “C.E.}).
	 **/
	
	public void setEraSymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setEras((String[]) list.toArray());
	}

	/**
	 * @Signature: longEraSymbols
	 * @Declaration : - (NSArray *)longEraSymbols
	 * @Description : Returns the long era symbols for the receiver
	 * @return Return Value An array containing NSString objects representing the era symbols for the receiver (for example, {“Before Common
	 *         Era, “Common Era}).
	 **/
	
	public NSArray<NSString> longEraSymbols() {
		String[] list = wrappedSimpleDateFormatter.getDateFormatSymbols().getEras();
		NSArray<NSString> array = new NSArray<NSString>();
		for (int i = 0; i < list.length - 1; i++) {
			array.getWrappedList().add(new NSString(list[i]));
		}
		return array;
	}

	/**
	 * @Signature: setLongEraSymbols:
	 * @Declaration : - (void)setLongEraSymbols:(NSArray *)array
	 * @Description : Sets the long era symbols for the receiver.
	 * @param array An array containing NSString objects representing the era symbols for the receiver (for example, {“Before Common Era,
	 *            “Common Era}).
	 **/
	
	public void setLongEraSymbols(NSArray<NSString> array) {
		List<String> list = new ArrayList<String>();
		for (NSString string : array.getWrappedList()) {
			list.add(string.getWrappedString());
		}
		wrappedSimpleDateFormatter.getDateFormatSymbols().setEras((String[]) list.toArray());
	}

	// Override method from NSFormatter
	
	@Override
	public NSAttributedString attributedStringForObjectValueWithDefaultAttributes(NSObject anObject, NSDictionary<?, ?> attributes) {
		return null;
	}

	
	@Override
	public NSString editingStringForObjectValue(NSObject obj) {
		return null;
	}

	
	@Override
	public boolean getObjectValueForStringErrorDescription(NSObject[] anObject, NSString string, NSString[] error) {
		return false;
	}

	
	@Override
	public boolean isPartialStringValidNewEditingStringErrorDescription(NSString partialString, NSString[] newString, NSString[] error) {
		return false;
	}

	
	@Override
	public boolean isPartialStringValidProposedSelectedRangeOriginalStringOriginalSelectedRangeErrorDescription(NSString partialStringPtr,
			NSRangePointer proposedSelRangePtr, NSString origString, NSRange origSelRange, NSString error) {
		return false;
	}

}