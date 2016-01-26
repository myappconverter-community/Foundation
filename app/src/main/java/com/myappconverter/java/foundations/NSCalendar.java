
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class NSCalendar extends NSObject implements NSCopying, NSSecureCoding {

	// Enumerations

	
	public static enum NSCalendarUnit {
		NSEraCalendarUnit(1 << 1), //
		NSYearCalendarUnit(1 << 2), //
		NSMonthCalendarUnit(1 << 3), //
		NSDayCalendarUnit(1 << 4), //
		NSHourCalendarUnit(1 << 5), //
		NSMinuteCalendarUnit(1 << 6), //
		NSSecondCalendarUnit(1 << 7), //
		NSWeekCalendarUnit(1 << 8), //
		NSWeekdayCalendarUnit(1 << 9), //
		NSWeekdayOrdinalCalendarUnit(1 << 10), //
		NSQuarterCalendarUnit(1L << 11), //
		NSWeekOfMonthCalendarUnit(1L << 12), // ,
		NSWeekOfYearCalendarUnit(1L << 13), //
		NSYearForWeekOfYearCalendarUnit(1L << 14), //
		NSCalendarCalendarUnit(1 << 20), //
		NSTimeZoneCalendarUnit(1 << 21);//

		long value;

		NSCalendarUnit(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	};

	// helper enum
	public static enum IOS_Units {
		Era, //
		Year, //
		Month, //
		Day, //
		Hour, //
		Minute, //
		Second, //
		Week, //
		Weekday, //
		WeekdayOrdinal, //
		Quarter, //
		UnitQuarter, //
		WeekOfMonth, //
		WeekOfYear, //
		YearForWeekOfYear
	};

	
	public static final int NSUndefinedDateComponent = 0x7fffffff;

	String calendarIdent;
	private Calendar wrappedCalendar;
	private NSLocale nslocale = new NSLocale();

	public NSCalendar() {
		wrappedCalendar = Calendar.getInstance();
	}

	public NSCalendar(Calendar calender) {
		this.wrappedCalendar = (Calendar) calender.clone();
	}

	public NSCalendar(String ident) {
		calendarIdent = ident;
		wrappedCalendar = Calendar.getInstance();

	}

	public Calendar getWrappedCalendar() {
		return wrappedCalendar;
	}

	public void setWrappedCalendar(Calendar c) {
		this.wrappedCalendar = c;
	}

	/**
	 * @Signature: autoupdatingCurrentCalendar
	 * @Declaration : + (id)autoupdatingCurrentCalendar
	 * @Description : Returns the current logical calendar for the current user.
	 * @return Return Value The current logical calendar for the current user.
	 * @Discussion Settings you get from this calendar do change as the user’s settings change (contrast with currentCalendar). Note that if
	 *             you cache values based on the calendar or related information those caches will of course not be automatically updated by
	 *             the updating of the calendar object.
	 **/
	
	public static NSCalendar autoupdatingCurrentCalendar() {
		return new NSCalendar(Calendar.getInstance());
	}

	/**
	 * @Signature: setLocale:
	 * @Declaration : - (void)setLocale:(NSLocale *)locale
	 * @Description : Sets the locale for the receiver.
	 * @param locale The locale for the receiver.
	 **/
	
	public void setLocale(NSLocale locale) {
		nslocale = locale;
		Calendar tmpCal = Calendar.getInstance(locale.getLocale());
		wrappedCalendar.setFirstDayOfWeek(tmpCal.getFirstDayOfWeek());
		wrappedCalendar.setMinimalDaysInFirstWeek(tmpCal.getMinimalDaysInFirstWeek());
	}

	/**
	 * @Signature: calendarIdentifier
	 * @Declaration : - (NSString *)calendarIdentifier
	 * @Description : Returns the identifier for the receiver.
	 * @return Return Value The identifier for the receiver. For valid identifiers, see NSLocale.
	 **/
	
	public NSString calendarIdentifier() {
		return getCalendarIdentifier();
	}

	public NSString getCalendarIdentifier() {
		return new NSString(calendarIdent);
	}

	public void setCalendarIdentifier(NSString calendarIdentifier) {
		calendarIdent = calendarIdentifier.getWrappedString();
	}

	/**
	 * @Signature: maximumRangeOfUnit:
	 * @Declaration : - (NSRange)maximumRangeOfUnit:(NSCalendarUnit)unit
	 * @Description : The maximum range limits of the values that a given unit can take on in the receive
	 * @param unit The unit for which the maximum range is returned.
	 * @return Return Value The maximum range limits of the values that the unit specified by unit can take on in the receiver.
	 * @Discussion As an example, in the Gregorian calendar the maximum range of values for the Day unit is 1-31.
	 **/
	
	public NSRange maximumRangeOfUnit(NSCalendarUnit unit) {
		NSRange nsRange = new NSRange(1,
				wrappedCalendar.getMaximum(NSCalendarUnit.valueOf(unit.name()).ordinal()));
		return nsRange;
	}

	/**
	 * @Signature: minimumRangeOfUnit:
	 * @Declaration : - (NSRange)minimumRangeOfUnit:(NSCalendarUnit)unit
	 * @Description : Returns the minimum range limits of the values that a given unit can take on in the receiver.
	 * @param unit The unit for which the maximum range is returned.
	 * @return Return Value The minimum range limits of the values that the unit specified by unit can take on in the receiver.
	 * @Discussion As an example, in the Gregorian calendar the minimum range of values for the Day unit is 1-28.
	 **/
	
	public NSRange minimumRangeOfUnit(NSCalendarUnit unit) {
		NSRange nsRange = new NSRange(1,
				wrappedCalendar.getMinimum(NSCalendarUnit.valueOf(unit.name()).ordinal()));
		return nsRange;
	}

	/**
	 * @Signature: components:fromDate:
	 * @Declaration : - (NSDateComponents *)components:(NSUInteger)unitFlags fromDate:(NSDate *)date
	 * @Description : Returns a NSDateComponents object containing a given date decomposed into specified components.
	 * @param unitFlags The components into which to decompose date—a bitwise OR of NSCalendarUnit constants.
	 * @param date The date for which to perform the calculation.
	 * @return Return Value An NSDateComponents object containing date decomposed into the components specified by unitFlags. Returns nil if
	 *         date falls outside of the defined range of the receiver or if the computation cannot be performed
	 * @Discussion The Weekday ordinality, when requested, refers to the next larger (than Week) of the requested units. Some computations
	 *             can take a relatively long time.
	 **/
	
	public NSDateComponents componentsFromDate(NSCalendarUnit unitFlags, NSDate date) {
		Calendar dc = Calendar.getInstance();
		dc.setTime(date.getWrappedDate());
		int result = dc.get((int) unitFlags.getValue());
		wrappedCalendar.set((int) unitFlags.getValue(), result);
		return new NSDateComponents(wrappedCalendar);
	}

	public NSDateComponents componentsFromDate(long unitFlags, NSDate date) {
		Calendar dc = Calendar.getInstance();
		dc.setTime(date.getWrappedDate());
		int result = dc.get((int) unitFlags);
		wrappedCalendar.set((int) unitFlags, result);
		return new NSDateComponents(wrappedCalendar);
	}

	/**
	 * @Signature: components:fromDate:toDate:options:
	 * @Declaration : - (NSDateComponents *)components:(NSUInteger)unitFlags fromDate:(NSDate *)startingDate toDate:(NSDate *)resultDate
	 *              options:(NSUInteger)opts
	 * @Description : Returns, as an NSDateComponents object using specified components, the difference between two supplied dates.
	 * @param unitFlags Specifies the components for the returned NSDateComponents object—a bitwise OR of NSCalendarUnit constants.
	 * @param startingDate The start date for the calculation.
	 * @param resultDate The end date for the calculation.
	 * @param opts Options for the calculation. If you specify a “wrap�? option (NSWrapCalendarComponents), the specified components are
	 *            incremented and wrap around to zero/one on overflow, but do not cause higher units to be incremented. When the wrap option
	 *            is false, overflow in a unit carries into the higher units, as in typical addition.
	 * @return Return Value An NSDateComponents object whose components are specified by unitFlags and calculated from the difference
	 *         between the resultDate and startDate using the options specified by opts. Returns nil if either date falls outside the
	 *         defined range of the receiver or if the computation cannot be performed.
	 * @Discussion The result is lossy if there is not a small enough unit requested to hold the full precision of the difference. Some
	 *             operations can be ambiguous, and the behavior of the computation is calendar-specific, but generally larger components
	 *             will be computed before smaller components;
	 **/
	
	public NSDateComponents componentsFromDateToDateOptions(NSCalendarUnit unitFlags,
			NSDate startingDate, NSDate resultDate, int opts) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startingDate.getWrappedDate());
		c2.setTime(resultDate.getWrappedDate());
		// get value from calendars
		int v1 = c1.get((int) unitFlags.getValue());
		int v2 = c2.get((int) unitFlags.getValue());
		wrappedCalendar.set((int) unitFlags.getValue(), Math.abs(v1 - v2));
		return new NSDateComponents(wrappedCalendar);
	}

	public NSDateComponents componentsFromDateToDateOptions(long unitFlags, NSDate startingDate,
			NSDate resultDate, int opts) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startingDate.getWrappedDate());
		c2.setTime(resultDate.getWrappedDate());
		// get value from calendars
		int v1 = c1.get((int) unitFlags);
		int v2 = c2.get((int) unitFlags);
		wrappedCalendar.set((int) unitFlags, Math.abs(v1 - v2));
		return new NSDateComponents(wrappedCalendar);
	}

	/**
	 * @Signature: ordinalityOfUnit:inUnit:forDate:
	 * @Declaration : - (NSUInteger)ordinalityOfUnit:(NSCalendarUnit)smaller inUnit:(NSCalendarUnit)larger forDate:(NSDate *)date
	 * @Description : Returns, for a given absolute time, the ordinal number of a smaller calendar unit (such as a day) within a specified
	 *              larger calendar unit (such as a week).
	 * @param smaller The smaller calendar unit
	 * @param larger The larger calendar unit
	 * @param date The absolute time for which the calculation is performed
	 * @return Return Value The ordinal number of smaller within larger at the time specified by date. Returns NSNotFound if larger is not
	 *         logically bigger than smaller in the calendar, or the given combination of units does not make sense (or is a computation
	 *         which is undefined).
	 * @Discussion The ordinality is in most cases not the same as the decomposed value of the unit. Typically return values are 1 and
	 *             greater. For example, the time 00:45 is in the first hour of the day, and for units Hour and Day respectively, the result
	 *             would be 1. An exception is the week-in-month calculation, which returns 0 for days before the first week in the month
	 *             containing the date. Note that some computations can take a relatively long time.
	 **/
	
	public int ordinalityOfUnitInUnitForDate(long smaller, long larger, NSDate date) {
		Calendar calendarGivenDate = wrappedCalendar;
		calendarGivenDate.setTime(date.getWrappedDate());
		if (smaller == larger)
			return 1;
		else {
			// Year
			if (smaller == IOS_Units.Year.ordinal()) {
				if (larger == IOS_Units.Era.ordinal())
					return calendarGivenDate.get(Calendar.YEAR);
			} else
				// Month
				if (smaller == IOS_Units.Month.ordinal()) {
				if (larger == IOS_Units.Year.ordinal())
					return calendarGivenDate.get(Calendar.MONTH);
			} else
					// Week
					if (smaller == IOS_Units.Week.ordinal()) {
				if (larger == IOS_Units.Month.ordinal())
					return calendarGivenDate.get(Calendar.WEEK_OF_MONTH);
				else if (larger == IOS_Units.Year.ordinal())
					return calendarGivenDate.get(Calendar.WEEK_OF_YEAR);
			} else
						// Day
						if (smaller == IOS_Units.Day.ordinal()) {

				if (larger == IOS_Units.Year.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_YEAR);
				else if (larger == IOS_Units.Month.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_MONTH);
				else if (larger == IOS_Units.Week.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_WEEK);
			} else
							// Hour
							if (smaller == IOS_Units.Hour.ordinal()) {
				if (larger == IOS_Units.Year.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_YEAR) * 24;
				else if (larger == IOS_Units.Month.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_MONTH) * 24;
				else if (larger == IOS_Units.Week.ordinal())
					return calendarGivenDate.get(Calendar.DAY_OF_WEEK) * 24;
				else if (larger == IOS_Units.Day.ordinal())
					return calendarGivenDate.get(Calendar.HOUR);
			} else
								//
								if (smaller == IOS_Units.Minute.ordinal()) {
				return calendarGivenDate.get(Calendar.HOUR_OF_DAY) * 60;
			} else if (smaller == IOS_Units.Second.ordinal()) {
				return calendarGivenDate.get(Calendar.SECOND) * 60;
			}
		}
		return 0;
	}

	/**
	 * @Signature: currentCalendar
	 * @Declaration : + (id)currentCalendar
	 * @Description : Returns the logical calendar for the current user.
	 * @return Return Value The logical calendar for the current user.
	 * @Discussion The returned calendar is formed from the settings for the current user’s chosen system locale overlaid with any custom
	 *             settings the user has specified in System Preferences. Settings you get from this calendar do not change as System
	 *             Preferences are changed, so that your operations are consistent (contrast with autoupdatingCurrentCalendar).
	 **/
	
	public static NSCalendar currentCalendar() {
		return new NSCalendar(Calendar.getInstance());
	}

	/**
	 * @Signature: locale
	 * @Declaration : - (NSLocale *)locale
	 * @Description : Returns the locale for the receiver.
	 * @return Return Value The locale for the receiver.
	 **/
	
	public NSLocale locale() {
		return getLocale();
	}

	public NSLocale getLocale() {
		return nslocale;
	}

	/**
	 * @Signature: setFirstWeekday:
	 * @Declaration : - (void)setFirstWeekday:(NSUInteger)weekday
	 * @Description : Sets the index of the first weekday for the receiver.
	 * @param weekday The first weekday for the receiver.
	 **/
	
	public void setFirstWeekday(int weekday) {
		getWrappedCalendar().setFirstDayOfWeek(weekday);
	}

	/**
	 * @Signature: firstWeekday
	 * @Declaration : - (NSUInteger)firstWeekday
	 * @Description : Returns the index of the first weekday of the receiver.
	 * @return Return Value The index of the first weekday of the receiver.
	 **/
	
	public int firstWeekday() {
		return getFirstWeekday();
	}

	public int getFirstWeekday() {
		return getWrappedCalendar().getFirstDayOfWeek();
	}

	/**
	 * @Signature: initWithCalendarIdentifier:
	 * @Declaration : - (id)initWithCalendarIdentifier:(NSString *)string
	 * @Description : Initializes a newly-allocated NSCalendar object for the calendar specified by a given identifier.
	 * @param string The identifier for the new calendar. For valid identifiers, see NSLocale.
	 * @return Return Value The initialized calendar, or nil if the identifier is unknown (if, for example, it is either an unrecognized
	 *         string or the calendar is not supported by the current version of the operating system).
	 **/
	
	public NSCalendar initWithCalendarIdentifier(NSString string) {
		return new NSCalendar(string.getWrappedString());
	}

	/**
	 * @Signature: minimumDaysInFirstWeek
	 * @Declaration : - (NSUInteger)minimumDaysInFirstWeek
	 * @Description : Returns the minimum number of days in the first week of the receiver.
	 * @return Return Value The minimum number of days in the first week of the receiver
	 **/
	
	public int minimumDaysInFirstWeek() {
		return getMinimumDaysInFirstWeek();
	}

	public int getMinimumDaysInFirstWeek() {
		return getWrappedCalendar().getMinimalDaysInFirstWeek();
	}

	/**
	 * @Signature: setMinimumDaysInFirstWeek:
	 * @Declaration : - (void)setMinimumDaysInFirstWeek:(NSUInteger)mdw
	 * @Description : Sets the minimum number of days in the first week of the receiver.
	 * @param mdw The minimum number of days in the first week of the receiver.
	 **/
	
	public void setMinimumDaysInFirstWeek(int mdw) {
		getWrappedCalendar().setMinimalDaysInFirstWeek(mdw);
	}

	/**
	 * @Signature: timeZone
	 * @Declaration : - (NSTimeZone *)timeZone
	 * @Description : Returns the time zone for the receiver.
	 * @return Return Value The time zone for the receiver.
	 **/
	
	public NSTimeZone timeZone() {
		return getTimeZone();
	}

	public NSTimeZone getTimeZone() {
		NSTimeZone timezone = new NSTimeZone();
		TimeZone tmz = Calendar.getInstance(Locale.getDefault()).getTimeZone();
		timezone.setWrappedTimeZone(tmz);
		return timezone;
	}

	/**
	 * @Signature: setTimeZone:
	 * @Declaration : - (void)setTimeZone:(NSTimeZone *)tz
	 * @Description : Sets the time zone for the receiver.
	 * @param tz The time zone for the receiver.
	 **/
	
	public void setTimeZone(NSTimeZone tz) {
		getWrappedCalendar().setTimeZone(tz.getWrappedTimeZone());
	}

	/**
	 * @Signature: dateFromComponents:
	 * @Declaration : - (NSDate *)dateFromComponents:(NSDateComponents *)comps
	 * @Description : Returns a new NSDate object representing the absolute time calculated from given components.
	 * @param comps The components from which to calculate the returned date.
	 * @return Return Value A new NSDate object representing the absolute time calculated from comps. Returns nil if the receiver cannot
	 *         convert the components given in comps into an absolute time. The method also returns nil and for out-of-range values.
	 * @Discussion When there are insufficient components provided to completely specify an absolute time, a calendar uses default values of
	 *             its choice. When there is inconsistent information, a calendar may ignore some of the components parameters or the method
	 *             may return nil. Unnecessary components are ignored (for example, Day takes precedence over Weekday and Weekday ordinals).
	 **/
	
	public NSDate dateFromComponents(NSDateComponents comps) {
		return new NSDate(comps.getmCalendar().getTime());
	}

	/**
	 * @Signature: dateByAddingComponents:toDate:options:
	 * @Declaration : - (NSDate *)dateByAddingComponents:(NSDateComponents *)comps toDate:(NSDate *)date options:(NSUInteger)opts
	 * @Description : Returns a new NSDate object representing the absolute time calculated by adding given components to a given date.
	 * @param comps The components to add to date.
	 * @param date The date to which comps are added.
	 * @param opts Options for the calculation. See “NSDateComponents wrapping behavior�? for possible values. Pass 0 to specify no options.
	 *            If you specify no options (you pass 0), overflow in a unit carries into the higher units (as in typical addition).
	 * @return Return Value A new NSDate object representing the absolute time calculated by adding to date the calendrical components
	 *         specified by comps using the options specified by opts. Returns nil if date falls outside the defined range of the receiver
	 *         or if the computation cannot be performed.
	 * @Discussion Some operations can be ambiguous, and the behavior of the computation is calendar-specific, but generally components are
	 *             added in the order specified.
	 **/
	
	public NSDate dateByAddingComponentstoDateoptions(NSDateComponents comps, NSDate date,
			int opts) {
		long sumDate = comps.getmCalendar().getTime().getTime() + date.getWrappedDate().getTime();
		NSDate nsdate = new NSDate(new Date(sumDate));
		return nsdate;
	}

	/**
	 * @Signature: rangeOfUnit:inUnit:forDate:
	 * @Declaration : - (NSRange)rangeOfUnit:(NSCalendarUnit)smaller inUnit:(NSCalendarUnit)larger forDate:(NSDate *)date
	 * @Description : Returns the range of absolute time values that a smaller calendar unit (such as a day) can take on in a larger
	 *              calendar unit (such as a month) that includes a specified absolute time.
	 * @param smaller The smaller calendar unit.
	 * @param larger The larger calendar unit.
	 * @param date The absolute time for which the calculation is performed.
	 * @return Return Value The range of absolute time values smaller can take on in larger at the time specified by date. Returns
	 *         {NSNotFound, NSNotFound} if larger is not logically bigger than smaller in the calendar, or the given combination of units
	 *         does not make sense (or is a computation which is undefined).
	 * @Discussion You can use this method to calculate, for example, the range the Day unit can take on in the Month in which date lies.
	 **/
	
	public NSRange rangeOfUnitinUnitforDate(NSCalendarUnit smaller, NSCalendarUnit larger,
			NSDate date) {
		return null;

	}

	/**
	 * @Signature: rangeOfUnit:startDate:interval:forDate:
	 * @Declaration : - (BOOL)rangeOfUnit:(NSCalendarUnit)unit startDate:(NSDate **)datep interval:(NSTimeInterval *)tip forDate:(NSDate
	 *              *)date
	 * @Description : Returns by reference the starting time and duration of a given calendar unit that contains a given date.
	 * @param unit A calendar unit (see “Calendar Units�? for possible values).
	 * @param datep Upon return, contains the starting time of the calendar unit unit that contains the date date
	 * @param tip Upon return, contains the duration of the calendar unit unit that contains the date date
	 * @param date A date.
	 * @return Return Value YES if the starting time and duration of a unit could be calculated, otherwise NO.
	 **/
	
	public boolean rangeOfUnitstartDateintervalforDate(NSCalendarUnit unit, NSDate[] datep,
			double[] tip, NSDate date) {
		return false;
		// TODO
	}

	
	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}