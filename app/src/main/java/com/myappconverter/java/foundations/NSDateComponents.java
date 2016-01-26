
package com.myappconverter.java.foundations;

import java.util.Calendar;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;



public class NSDateComponents extends NSObject implements NSCopying, NSSecureCoding {

	private Calendar mCalendar;

	public NSDateComponents() {
	}

	public Calendar getmCalendar() {
		return mCalendar;
	}

	public NSDateComponents(Calendar myCal) {
		mCalendar = myCal;
	}

	// Getting Information About an NSDateComponents Object
	/**
	 * @Signature: era
	 * @Declaration : - (NSInteger)era
	 * @Description : Returns the number of era units for the receiver.
	 * @return Return Value The number of era units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int era() {
		return mCalendar.get(Calendar.ERA);
	}

	private int era;

	/**
	 * @Signature: year
	 * @Declaration : - (NSInteger)year
	 * @Description : Returns the number of year units for the receiver.
	 * @return Return Value The number of year units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int year() {
		return mCalendar.get(Calendar.YEAR);
	}

	private int year;

	/**
	 * @Signature: month
	 * @Declaration : - (NSInteger)month
	 * @Description : Returns the number of month units for the receiver.
	 * @return Return Value The number of month units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int month() {
		return mCalendar.get(Calendar.MONTH);
	}

	private int month;

	/**
	 * @Signature: day
	 * @Declaration : - (NSInteger)day
	 * @Description : Returns the number of day units for the receiver.
	 * @return Return Value The number of day units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int day() {
		return mCalendar.get(Calendar.DAY_OF_MONTH);
	}

	private int day;

	/**
	 * @Signature: hour
	 * @Declaration : - (NSInteger)hour
	 * @Description : Returns the number of hour units for the receiver.
	 * @return Return Value The number of hour units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int hour() {
		return mCalendar.get(Calendar.HOUR_OF_DAY);
	}

	private int hour;

	/**
	 * @Signature: minute
	 * @Declaration : - (NSInteger)minute
	 * @Description : Returns the number of minute units for the receiver.
	 * @return Return Value The number of minute units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int minute() {
		return mCalendar.get(Calendar.MINUTE);
	}

	private int minute;

	/**
	 * @Signature: second
	 * @Declaration : - (NSInteger)second
	 * @Description : Returns the number of second units for the receiver.
	 * @return Return Value The number of second units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int second() {
		return mCalendar.get(Calendar.SECOND);
	}

	private int second;

	/**
	 * @Signature: week
	 * @Declaration : - (NSInteger)week
	 * @Description : Returns the number of week units for the receiver.
	 * @return Return Value The number of week units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int week() {
		return mCalendar.get(Calendar.WEEK_OF_MONTH);
	}

	private int week;

	/**
	 * @Signature: week
	 * @Declaration : - (NSInteger)week
	 * @Description : Returns the number of week units for the receiver.
	 * @return Return Value The number of week units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int weekday() {
		return 7;
	}

	private int weekday;

	/**
	 * @Signature: weekdayOrdinal
	 * @Declaration : - (NSInteger)weekdayOrdinal
	 * @Description : Returns the ordinal number of weekday units for the receiver.
	 * @return Return Value The ordinal number of weekday units for the receiver.
	 * @Discussion Weekday ordinal units represent the position of the weekday within the next larger calendar unit, such as the month. For
	 *             example, 2 is the weekday ordinal unit for the second Friday of the month. This value is interpreted in the context of
	 *             the calendar with which it is used—see “Calendars, Date Components, and Calendar Units in Date and Time Programming
	 *             Guide.
	 **/
	
	public int weekdayOrdinal() {

		Boolean curentMonth = true;
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(mCalendar.getTime());

		int myCurrentMoth = tempCalendar.get(Calendar.MONTH);

		int weekNo = 1;
		while (curentMonth) {
			tempCalendar.add(Calendar.DAY_OF_YEAR, -7);
			if (tempCalendar.get(Calendar.MONTH) != myCurrentMoth) {
				return weekNo;
			}
			weekNo++;
		}
		return weekNo;
	}

	private int weekdayOrdinal;

	/**
	 * @Signature: quarter
	 * @Declaration : - (NSInteger)quarter
	 * @Description : Returns the number of quarters in the calendar.
	 * @return Return Value The number of quarters units for the receiver.
	 **/
	
	public int quarter() {
		return (mCalendar.get(Calendar.MONTH) / 3) + 1;
	}

	private int quarter;

	/**
	 * @Signature: calendar
	 * @Declaration : - (NSCalendar *)calendar
	 * @Description : Returns the calendar of the receiver.
	 * @return Return Value The calendar.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public NSCalendar calendar() {
		return new NSCalendar(mCalendar);
	}

	private NSCalendar calendar;

	/**
	 * @Signature: timeZone
	 * @Declaration : - (NSTimeZone *)timeZone
	 * @Description : Returns the receiver’s time zone.
	 * @return Return Value The time zone.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public NSTimeZone timeZone() {
		NSTimeZone tmz = new NSTimeZone();
		tmz.setWrappedTimeZone(mCalendar.getTimeZone());
		return tmz;
	}

	private NSTimeZone timeZone;

	/**
	 * @Signature: weekOfMonth
	 * @Declaration : - (NSInteger)weekOfMonth
	 * @Description : Returns the week of the month.
	 * @return Return Value The week number of the month.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int weekOfMonth() {
		return mCalendar.get(Calendar.WEEK_OF_MONTH);
	}

	private int weekOfMonth;

	/**
	 * @Signature: weekOfYear
	 * @Declaration : - (NSInteger)weekOfYear
	 * @Description : Returns the week of the year.
	 * @return Return Value The week number of the year.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int weekOfYear() {
		return mCalendar.get(Calendar.WEEK_OF_YEAR);
	}

	private int weekOfYear;

	/**
	 * @Signature: yearForWeekOfYear
	 * @Declaration : - (NSInteger)yearForWeekOfYear
	 * @Description : Returns the year for the week of the year.
	 * @return Return Value The year number for the week of the year.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public int yearForWeekOfYear() {
		return 0;
	}

	private int yearForWeekOfYear;

	// Setting Information for an NSDateComponents Object
	/**
	 * @Signature: setEra:
	 * @Declaration : - (void)setEra:(NSInteger)v
	 * @Description : Sets the number of era units for the receiver.
	 * @param v The number of era units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setEra(int v) {
		mCalendar.set(Calendar.ERA, v);
	}

	/**
	 * @Signature: setYear:
	 * @Declaration : - (void)setYear:(NSInteger)v
	 * @Description : Sets the number of year units for the receiver.
	 * @param v The number of year units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setYear(int value) {
		mCalendar.set(Calendar.YEAR, value);
	}

	/**
	 * @Signature: setMonth:
	 * @Declaration : - (void)setMonth:(NSInteger)v
	 * @Description : Sets the number of month units for the receiver.
	 * @param v The number of month units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setMonth(int v) {
		mCalendar.set(Calendar.MONTH, v);
	}

	/**
	 * @Signature: setDay:
	 * @Declaration : - (void)setDay:(NSInteger)v
	 * @Description : Sets the number of day units for the receiver.
	 * @param v The number of day units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setDay(int v) {
		mCalendar.set(Calendar.DAY_OF_MONTH, v);
	}

	/**
	 * @Signature: setHour:
	 * @Declaration : - (void)setHour:(NSInteger)v
	 * @Description : Sets the number of hour units for the receiver.
	 * @param v The number of hour units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setHour(int v) {
		mCalendar.set(Calendar.HOUR, v);
	}

	/**
	 * @Signature: setMinute:
	 * @Declaration : - (void)setMinute:(NSInteger)v
	 * @Description : Sets the number of minute units for the receiver.
	 * @param v The number of minute units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setMinute(int v) {
		mCalendar.set(Calendar.MINUTE, v);
	}

	/**
	 * @Signature: setSecond:
	 * @Declaration : - (void)setSecond:(NSInteger)v
	 * @Description : Sets the number of second units for the receiver.
	 * @param v The number of second units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setSecond(int v) {
		mCalendar.set(Calendar.SECOND, v);
	}

	/**
	 * @Signature: setWeek:
	 * @Declaration : - (void)setWeek:(NSInteger)v
	 * @Description : Sets the number of week units for the receiver.
	 * @param v The number of week units for the receiver.
	 * @Discussion This value will be interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setWeek(int v) {
		mCalendar.set(Calendar.WEEK_OF_MONTH, v);
	}

	/**
	 * @Signature: setWeekday:
	 * @Declaration : - (void)setWeekday:(NSInteger)v
	 * @Description : Sets the number of weekday units for the receiver.
	 * @param v The number of weekday units for the receiver.
	 * @Discussion Weekday units are the numbers 1 through n, where n is the number of days in the week. For example, in the Gregorian
	 *             calendar, n is 7 and Sunday is represented by 1. This value will be interpreted in the context of the calendar with which
	 *             it is used—see “Calendars, Date Components, and Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setWeekday(int v) {
		mCalendar.set(Calendar.DAY_OF_WEEK, v);
	}

	/**
	 * @Signature: setWeekdayOrdinal:
	 * @Declaration : - (void)setWeekdayOrdinal:(NSInteger)v
	 * @Description : Sets the ordinal number of weekday units for the receiver.
	 * @param v The ordinal number of weekday units for the receiver.
	 * @Discussion Weekday ordinal units represent the position of the weekday within the next larger calendar unit, such as the month. For
	 *             example, 2 is the weekday ordinal unit for the second Friday of the month. This value will be interpreted in the context
	 *             of the calendar with which it is used—see “Calendars, Date Components, and Calendar Units in Date and Time Programming
	 *             Guide.
	 **/
	
	public void setWeekdayOrdinal(int v) {
		Calendar tempCalendar = Calendar.getInstance(mCalendar.getTimeZone());
		int currentWeekDayOridnal = weekdayOrdinal();
		if (v > currentWeekDayOridnal) {
			tempCalendar.add(Calendar.DAY_OF_WEEK, 7 * (currentWeekDayOridnal - v));
		} else {
			tempCalendar.add(Calendar.DAY_OF_WEEK, -7 * (currentWeekDayOridnal - v));
		}
		mCalendar.set(Calendar.DAY_OF_MONTH, tempCalendar.get(Calendar.DAY_OF_MONTH));

	}

	/**
	 * @Signature: setQuarter:
	 * @Declaration : - (void)setQuarter:(NSInteger)v
	 * @Description : Sets the number of quarters in the calendar.
	 * @param v The number of quarters units for the receiver.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setQuarter(int v) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setCalendar:
	 * @Declaration : - (void)setCalendar:(NSCalendar *)cal
	 * @Description : Sets the receiver’s calendar.
	 * @param cal The calendar.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setCalendar(NSCalendar cal) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setTimeZone:
	 * @Declaration : - (void)setTimeZone:(NSTimeZone *)tz
	 * @Description : Sets the receiver’s time zone.
	 * @param tz The time zone.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setTimeZone(NSTimeZone tz) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setWeekOfMonth:
	 * @Declaration : - (void)setWeekOfMonth:(NSInteger)week
	 * @Description : Sets the week of the month.
	 * @param week The week number of the month.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setWeekOfMonth(int week) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setWeekOfYear:
	 * @Declaration : - (void)setWeekOfYear:(NSInteger)week
	 * @Description : Sets the week of the year.
	 * @param week The week number of the year.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setWeekOfYear(int week) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setYearForWeekOfYear:
	 * @Declaration : - (void)setYearForWeekOfYear:(NSInteger)year
	 * @Description : Sets the year for the week of the year.
	 * @param year The year when the calendar is being interpreted as a week-based calendar.
	 * @Discussion This value is interpreted in the context of the calendar with which it is used—see “Calendars, Date Components, and
	 *             Calendar Units in Date and Time Programming Guide.
	 **/
	
	public void setYearForWeekOfYear() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: setLeapMonth:
	 * @Declaration : - (void)setLeapMonth:(BOOL)lm
	 * @Description : Sets the month as a leap month.
	 * @param lm YES if the month is a leap month, NO otherwise.
	 **/
	
	public void setLeapMonth(boolean lm) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: isLeapMonth
	 * @Declaration : - (BOOL)isLeapMonth
	 * @Description : Returns whether the month is a leap month.
	 * @return Return Value YES if the month is a leap month, NO otherwise.
	 **/
	
	public boolean isLeapMonth() {
		int localmonth = mCalendar.get(Calendar.MONTH);
		boolean is_leap_month = (localmonth == 1 || localmonth == 3 || localmonth == 5
				|| localmonth == 7 || localmonth == 8 || localmonth == 10 || localmonth == 12);
		if (is_leap_month)
			return true;

		return false;
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