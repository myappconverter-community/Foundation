
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.mapping.utils.InstanceTypeFactory;



public class NSDate extends NSObject implements NSCopying, NSSecureCoding, NSCoding, Serializable {

	private static final String DEFAULT_IOS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// NSTimeInterval is always specified in seconds; it yields sub-millisecond
	// precision over a range of 10,000 years.

	private Date wrappedDate;

	public Date getWrappedDate() {
		return wrappedDate;
	}

	public void setWrappedDate(Date mDate) {
		this.wrappedDate = mDate;
	}

	public NSDate(Date aDate) {
		wrappedDate = (Date) aDate.clone();
	}

	public NSDate() {
		wrappedDate = new Date();
	}

	// Creating and Initializing Date Objects
	/**
	 * @Declaration : + (instancetype)date
	 * @Description : Creates and returns a new date set to the current date and time.
	 * @return Return Value A new date object set to the current date and time.
	 * @Discussion This method uses the default initializer method for the class, init. The following code sample shows how to use date to
	 *             get the current date: NSDate *today = [NSDate date];
	 */
	
	public static NSDate date() {
		return new NSDate(new Date());
	}

	public static NSDate date(Class clazz) {
		NSDate date = new NSDate(new Date());
		return (NSDate) InstanceTypeFactory.getInstance(date, NSDate.class, clazz,
				new NSString("setWrappedDate"), date.getWrappedDate());
	}

	/**
	 * @Signature: dateWithTimeIntervalSinceNow:
	 * @Declaration : + (instancetype)dateWithTimeIntervalSinceNow:(NSTimeInterval)seconds
	 * @Description : Creates and returns an NSDate object set to a given number of seconds from the current date and time.
	 * @param seconds The number of seconds from the current date and time for the new date. Use a negative value to specify a date before
	 *            the current date.
	 * @return Return Value An NSDate object set to seconds seconds from the current date and time.
	 **/
	
	public static NSDate dateWithTimeIntervalSinceNow(double secs) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, (int) secs);
		NSDate nsDate = new NSDate();
		nsDate.wrappedDate = calendar.getTime();
		return nsDate;
	}

	public static NSDate dateWithTimeIntervalSinceNow(Class clazz, double secs) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, (int) secs);
		NSDate nsDate = new NSDate();
		nsDate.wrappedDate = calendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: dateWithTimeInterval:sinceDate:
	 * @Declaration : + (instancetype)dateWithTimeInterval:(NSTimeInterval)seconds sinceDate:(NSDate *)date
	 * @Description : Creates and returns an NSDate object set to a given number of seconds from the specified date.
	 * @param seconds The number of seconds to add to date. Use a negative argument to specify a date and time before date.
	 * @param date The date.
	 * @return Return Value An NSDate object set to seconds seconds from date.
	 **/
	
	public static NSDate dateWithTimeIntervalsinceDate(int seconds, NSDate sinceDate) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(sinceDate.getWrappedDate());
		myCalendar.add(Calendar.SECOND, seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public static NSDate dateWithTimeIntervalsinceDate(Class clazz, int seconds, NSDate sinceDate) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(sinceDate.getWrappedDate());
		myCalendar.add(Calendar.SECOND, seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: dateWithTimeIntervalSinceReferenceDate:
	 * @Declaration : + (instancetype)dateWithTimeIntervalSinceReferenceDate:(NSTimeInterval)seconds
	 * @Description : Creates and returns an NSDate object set to a given number of seconds from the first instant of 1 January 2001, GMT.
	 * @param seconds The number of seconds from the absolute reference date (the first instant of 1 January 2001, GMT) for the new date.
	 *            Use a negative argument to specify a date and time before the reference date.
	 * @return Return Value An NSDate object set to seconds seconds from the absolute reference date.
	 **/
	
	public static NSDate dateWithTimeIntervalSinceReferenceDate(double seconds) {
		NSDate nsDate = new NSDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 0, 1, 0, 0, 0);
		calendar.set(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = calendar.getTime();
		return nsDate;
	}

	public static NSDate dateWithTimeIntervalSinceReferenceDate(Class clazz, double seconds) {
		NSDate nsDate = new NSDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 0, 1, 0, 0, 0);
		calendar.set(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = calendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: dateWithTimeIntervalSince1970:
	 * @Declaration : + (instancetype)dateWithTimeIntervalSince1970:(NSTimeInterval)seconds
	 * @Description : Creates and returns an NSDate object set to the given number of seconds from the first instant of 1 January 1970, GMT.
	 * @param seconds The number of seconds from the reference date, 1 January 1970, GMT, for the new date. Use a negative argument to
	 *            specify a date before this date.
	 * @return Return Value An NSDate object set to seconds seconds from the reference date.
	 * @Discussion This method is useful for creating NSDate objects from time_t values returned by BSD system functions.
	 **/
	
	public static NSDate dateWithTimeIntervalSince1970(double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(1970, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public static NSDate dateWithTimeIntervalSince1970(Class clazz, double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(1970, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Returns an NSDate object initialized to the current date and time.
	 * @return Return Value An NSDate object initialized to the current date and time.
	 * @Discussion This method is a designated initializer for NSDate.
	 **/
	@Override
	
	public NSDate init() {
		return date();
	}

	/**
	 * @Signature: initWithTimeIntervalSinceNow:
	 * @Declaration : - (instancetype)initWithTimeIntervalSinceNow:(NSTimeInterval)seconds
	 * @Description : Returns an NSDate object initialized relative to the current date and time by a given number of seconds.
	 * @param seconds The number of seconds from relative to the current date and time to which the receiver should be initialized. A
	 *            negative value means the returned object will represent a date in the past.
	 * @return Return Value An NSDate object initialized relative to the current date and time by seconds seconds.
	 **/
	
	public NSDate initWithTimeIntervalSinceNow(double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public NSDate initWithTimeIntervalSinceNow(Class clazz, double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: initWithTimeInterval:sinceDate:
	 * @Declaration : - (instancetype)initWithTimeInterval:(NSTimeInterval)seconds sinceDate:(NSDate *)refDate
	 * @Description : Returns an NSDate object initialized relative to another given date by a given number of seconds.
	 * @param seconds The number of seconds to add to refDate. A negative value means the receiver will be earlier than refDate.
	 * @param refDate The reference date.
	 * @return Return Value An NSDate object initialized relative to refDate by seconds seconds.
	 **/
	
	public NSDate initWithTimeIntervalsinceDate(double seconds, NSDate refDate) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(refDate.wrappedDate);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public NSDate initWithTimeIntervalsinceDate(Class clazz, double seconds, NSDate refDate) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(refDate.wrappedDate);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: initWithTimeIntervalSinceReferenceDate:
	 * @Declaration : - (instancetype)initWithTimeIntervalSinceReferenceDate:(NSTimeInterval)seconds
	 * @Description : Returns an NSDate object initialized relative the first instant of 1 January 2001, GMT by a given number of seconds.
	 * @param seconds The number of seconds to add to the reference date (the first instant of 1 January 2001, GMT). A negative value means
	 *            the receiver will be earlier than the reference date.
	 * @return Return Value An NSDate object initialized relative to the absolute reference date by seconds seconds.
	 * @Discussion This method is a designated initializer for the NSDate class and is declared primarily for the use of subclasses of
	 *             NSDate. When you subclass NSDate to create a concrete date class, you must override this method.
	 **/
	
	public NSDate initWithTimeIntervalSinceReferenceDate(double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(2001, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public NSDate initWithTimeIntervalSinceReferenceDate(Class clazz, double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(2001, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	/**
	 * @Signature: initWithTimeIntervalSince1970:
	 * @Declaration : - (instancetype)initWithTimeIntervalSince1970:(NSTimeInterval)seconds
	 * @Description : Returns an NSDate object set to the given number of seconds from the first instant of 1 January 1970, GMT.
	 * @param seconds The number of seconds from the reference date, 1 January 1970, GMT, for the new date. Use a negative argument to
	 *            specify a date before this date.
	 * @return Return Value An NSDate object set to seconds seconds from the reference date.
	 * @Discussion This method is useful for creating NSDate objects from time_t values returned by BSD system functions.
	 **/
	
	public NSDate initWithTimeIntervalSince1970(double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(1970, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	public NSDate initWithTimeIntervalSince1970(Class clazz, double seconds) {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(1970, 0, 1, 0, 0, 0);
		myCalendar.add(Calendar.SECOND, (int) seconds);
		nsDate.wrappedDate = myCalendar.getTime();
		return (NSDate) InstanceTypeFactory.getInstance(nsDate, NSDate.class, clazz,
				new NSString("setWrappedDate"), nsDate.getWrappedDate());
	}

	// Getting Temporal Boundaries

	/**
	 * @Signature: distantFuture
	 * @Declaration : + (id)distantFuture
	 * @Description : Creates and returns an NSDate object representing a date in the distant future.
	 * @return Return Value An NSDate object representing a date in the distant future (in terms of centuries).
	 * @Discussion You can pass this value when an NSDate object is required to have the date argument essentially ignored.
	 **/
	
	public static NSDate distantFuture() {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		int randomNum = (int) (Math.random() * 1000);
		myCalendar.add(Calendar.YEAR, randomNum);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;

	}

	/**
	 * @Signature: distantPast
	 * @Declaration : + (id)distantPast
	 * @Description : Creates and returns an NSDate object representing a date in the distant past.
	 * @return Return Value An NSDate object representing a date in the distant past (in terms of centuries).
	 * @Discussion You can use this object as a control date, a guaranteed temporal boundary.
	 **/
	
	public static NSDate distantPast() {
		NSDate nsDate = new NSDate();
		Calendar myCalendar = Calendar.getInstance();
		int randomNum = (int) (Math.random() * 1000);
		myCalendar.add(Calendar.YEAR, -randomNum);
		nsDate.wrappedDate = myCalendar.getTime();
		return nsDate;
	}

	// Comparing Dates

	/**
	 * @Signature: isEqualToDate:
	 * @Declaration : - (BOOL)isEqualToDate:(NSDate *)anotherDate
	 * @Description : Returns a Boolean value that indicates whether a given object is an NSDate object and exactly equal the receiver.
	 * @param anotherDate The date to compare with the receiver.
	 * @return Return Value YES if the anotherDate is an NSDate object and is exactly equal to the receiver, otherwise NO.
	 * @Discussion This method detects sub-second differences between dates. If you want to compare dates with a less fine granularity, use
	 *             timeIntervalSinceDate: to compare the two dates.
	 **/
	
	public boolean isEqualToDate(NSDate anotherDate) {
		return getWrappedDate().equals(anotherDate.getWrappedDate());
	}

	/**
	 * @Signature: earlierDate:
	 * @Declaration : - (NSDate *)earlierDate:(NSDate *)anotherDate
	 * @Description : Returns the earlier of the receiver and another given date.
	 * @param anotherDate The date with which to compare the receiver.
	 * @return Return Value The earlier of the receiver and anotherDate, determined using timeIntervalSinceDate:. If the receiver and
	 *         anotherDate represent the same date, returns the receiver.
	 **/
	
	public NSDate earlierDate(NSDate anotherDate) {
		if (wrappedDate.before(anotherDate.getWrappedDate()))
			return this;
		else
			return anotherDate;
	}

	/**
	 * @Signature: laterDate:
	 * @Declaration : - (NSDate *)laterDate:(NSDate *)anotherDate
	 * @Description : Returns the later of the receiver and another given date.
	 * @param anotherDate The date with which to compare the receiver.
	 * @return Return Value The later of the receiver and anotherDate, determined using timeIntervalSinceDate:. If the receiver and
	 *         anotherDate represent the same date, returns the receiver.
	 **/
	
	public NSDate laterDate(NSDate anotherDate) {
		if (wrappedDate.after(anotherDate.getWrappedDate()))
			return this;
		return anotherDate;

	}

	/**
	 * @Signature: compare:
	 * @Declaration : - (NSComparisonResult)compare:(NSDate *)anotherDate
	 * @Description : Returns an NSComparisonResult value that indicates the temporal ordering of the receiver and another given date.
	 * @param anotherDate The date with which to compare the receiver. This value must not be nil. If the value is nil, the behavior is
	 *            undefined and may change in future versions of OS X.
	 * @return Return Value If: The receiver and anotherDate are exactly equal to each other, NSOrderedSame The receiver is later in time
	 *         than anotherDate, NSOrderedDescending The receiver is earlier in time than anotherDate, NSOrderedAscending.
	 * @Discussion This method detects sub-second differences between dates. If you want to compare dates with a less fine granularity, use
	 *             timeIntervalSinceDate: to compare the two dates.
	 **/
	
	public NSComparisonResult compare(NSDate anotherDate) {
		if (anotherDate != null) {
			if (timeIntervalSinceDate(anotherDate) > 0) {
				return NSComparisonResult.NSOrderedDescending;
			} else if (timeIntervalSinceDate(anotherDate) < 0) {
				return NSComparisonResult.NSOrderedAscending;
			} else if (isEqualToDate(anotherDate)) {
				return NSComparisonResult.NSOrderedSame;
			}
		}
		return null;
	}

	// Getting Time Intervals

	/**
	 * @Signature: timeIntervalSinceDate:
	 * @Declaration : - (NSTimeInterval)timeIntervalSinceDate:(NSDate *)anotherDate
	 * @Description : Returns the interval between the receiver and another given date.
	 * @param anotherDate The date with which to compare the receiver.
	 * @return Return Value The interval between the receiver and anotherDate. If the receiver is earlier than anotherDate, the return value
	 *         is negative.
	 **/
	
	public double timeIntervalSinceDate(NSDate anotherDate) {
		Calendar myCalendar = Calendar.getInstance();
		long delta = wrappedDate.getTime() - anotherDate.getWrappedDate().getTime();
		myCalendar.setTimeInMillis(delta);
		return myCalendar.getTimeInMillis();
	}

	/**
	 * @Signature: timeIntervalSinceNow
	 * @Declaration : - (NSTimeInterval)timeIntervalSinceNow
	 * @Description : Returns the interval between the receiver and the current date and time.
	 * @return Return Value The interval between the receiver and the current date and time. If the receiver is earlier than the current
	 *         date and time, the return value is negative.
	 **/
	
	public double timeIntervalSinceNow() {
		long now = new Date().getTime();
		return ((double) getWrappedDate().getTime() - (double) now) / 1000;
	}

	public double getTimeIntervalSinceNow() {
		long now = new Date().getTime();
		return ((double) getWrappedDate().getTime() - (double) now) / 1000;
	}

	/**
	 * @Signature: timeIntervalSinceReferenceDate
	 * @Declaration : + (NSTimeInterval)timeIntervalSinceReferenceDate
	 * @Description : Returns the interval between the first instant of 1 January 2001, GMT and the current date and time.
	 * @return Return Value The interval between the system’s absolute reference date (the first instant of 1 January 2001, GMT) and the
	 *         current date and time.
	 * @Discussion This method is the primitive method for NSDate. If you subclass NSDate, you must override this method with your own
	 *             implementation for it.
	 **/
	
	public static double _timeIntervalSinceReferenceDate() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 0, 1, 0, 0, 0);
		Date referenceDate = calendar.getTime();
		return now.getTime() - referenceDate.getTime();
	}

	/**
	 * @Signature: timeIntervalSinceReferenceDate
	 * @Declaration : - (NSTimeInterval)timeIntervalSinceReferenceDate
	 * @Description : Returns the interval between the receiver and the first instant of 1 January 2001, GMT.
	 * @return Return Value The interval between the receiver and the system’s absolute reference date (the first instant of 1 January 2001,
	 *         GMT). If the receiver is earlier than the reference date, the value is negative.
	 **/
	

	public double getTimeIntervalSinceReferenceDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 0, 1, 0, 0, 0);
		Date referenceDate = calendar.getTime();
		return wrappedDate.getTime() - referenceDate.getTime();
	}

	
	public static double timeIntervalSinceReferenceDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 0, 1, 0, 0, 0);
		Date referenceDate = calendar.getTime();
		Date currentDate = new Date();
		return currentDate.getTime() - referenceDate.getTime();
	}

	/**
	 * @Signature: timeIntervalSince1970
	 * @Declaration : - (NSTimeInterval)timeIntervalSince1970
	 * @Description : Returns the interval between the receiver and the first instant of 1 January 1970, GMT.
	 * @return Return Value The interval between the receiver and the reference date, 1 January 1970, GMT. If the receiver is earlier than
	 *         the reference date, the value is negative.
	 **/
	
	public double timeIntervalSince1970() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 1, 0, 0, 0);
		Date referenceDate = calendar.getTime();
		return wrappedDate.getTime() - referenceDate.getTime();
	}

	public double getTimeIntervalSince1970() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 1, 0, 0, 0);
		Date referenceDate = calendar.getTime();
		return wrappedDate.getTime() - referenceDate.getTime();
	}
	// Adding a Time Interval

	/**
	 * @Signature: dateByAddingTimeInterval:
	 * @Declaration : - (id)dateByAddingTimeInterval:(NSTimeInterval)seconds
	 * @Description : Returns a new NSDate object that is set to a given number of seconds relative to the receiver.
	 * @param seconds The number of seconds to add to the receiver. Use a negative value for seconds to have the returned object specify a
	 *            date before the receiver.
	 * @return Return Value A new NSDate object that is set to seconds seconds relative to the receiver. The date returned might have a
	 *         representation different from the receiver’s.
	 **/
	
	public NSDate dateByAddingTimeInterval(double seconds) {
		return initWithTimeIntervalsinceDate(seconds, this);
	}

	/**
	 * @Signature: addTimeInterval:
	 * @Declaration : - (id)addTimeInterval:(NSTimeInterval)seconds
	 * @Description : Returns a new NSDate object that is set to a given number of seconds relative to the receiver. (Deprecated in iOS 4.0.
	 *              This method has been replaced by dateByAddingTimeInterval:.)
	 * @param seconds The number of seconds to add to the receiver. Use a negative value for seconds to have the returned object specify a
	 *            date before the receiver.
	 * @return Return Value A new NSDate object that is set to seconds seconds relative to the receiver. The date returned might have a
	 *         representation different from the receiver’s.
	 **/
	
	
	public NSDate addTimeInterval(double seconds) {
		return dateByAddingTimeInterval(seconds);
	}

	@Override
	public NSObject copy() {
		Object date2 = this.wrappedDate.clone();
		return new NSDate((Date) date2);

	}

	// Representing Dates as Strings
	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns a string representation of the receiver.
	 * @return Return Value A string representation of the receiver.
	 * @Discussion The representation is useful for debugging only. There are a number of options to aquire a formated string for a date
	 *             including: date formatters (see NSDateFormatter and Data Formatting Guide), and the NSDate methods
	 *             descriptionWithLocale:, dateWithCalendarFormat:timeZone:, and descriptionWithCalendarFormat:timeZone:locale:
	 **/
	@Override
	
	public NSString description() {
		return descriptionWithLocale(null);
	}

	public NSString getDescription() {
		return descriptionWithLocale(null);
	}

	/**
	 * @Signature: descriptionWithLocale:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale
	 * @Description : Returns a string representation of the receiver using the given locale.
	 * @param locale An NSLocale object. If you pass nil, NSDate formats the date in the same way as the description method. On OS X v10.4
	 *            and earlier, this parameter was an NSDictionary object. If you pass in an NSDictionary object on OS X v10.5, NSDate uses
	 *            the default user locale—the same as if you passed in [NSLocale currentLocale].
	 * @return Return Value A string representation of the receiver, using the given locale, or if the locale argument is nil, in the
	 *         international format YYYY-MM-DD HH:MM:SS ±HHMM, where ±HHMM represents the time zone offset in hours and minutes from GMT
	 *         (for example, “2001-03-24 10:45:32 +0600�?)
	 **/
	
	public NSString descriptionWithLocale(NSLocale locale) {
		Locale mLocale;
		mLocale = (locale != null) ? locale.getLocale() : Locale.getDefault();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_IOS_DATE_FORMAT, mLocale);
		return new NSString(dateFormatter.format(wrappedDate));
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	/** Added **/

	@Override
	public boolean equals(Object paramObject) {
		if (paramObject instanceof NSDate)
			return isEqualToDate((NSDate) paramObject);
		return super.equals(paramObject);
	}

	
	@Override
	public int hashCode() {
		return (int) Math.round(timeIntervalSinceReferenceDate());
	}

	public static long timeIntervalToMilliseconds(double seconds) {
		return Math.round(seconds * 1000.0D);
	}

	public static double millisecondsToTimeInterval(long millis) {
		return millis / 1000.0D;
	}

	@Override
	public Class classForCoder() {
		return getClass();
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
		// TODO
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	private Date date;


	private final static long EPOCH = 978307200000L;

	private static final SimpleDateFormat sdfDefault = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static final SimpleDateFormat sdfGnuStep = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss Z");

	static {
		sdfDefault.setTimeZone(TimeZone.getTimeZone("GMT"));
		sdfGnuStep.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Parses the XML date string and creates a Java Date object from it. This function is synchronized as SimpleDateFormat is not
	 * thread-safe.
	 * 
	 * @param textRepresentation The date string as found in the XML property list
	 * @return The parsed Date
	 * @throws ParseException If the given string cannot be parsed.
	 * @see SimpleDateFormat#parse(String)
	 */
	private static synchronized Date parseDateString(String textRepresentation)
			throws ParseException {
		try {
			return sdfDefault.parse(textRepresentation);
		} catch (ParseException ex) {
			return sdfGnuStep.parse(textRepresentation);
		}
	}

	/**
	 * Generates a String representation of a Java Date object. The string is formatted according to the specification for XML property list
	 * dates.
	 * 
	 * @param date The date which should be represented.
	 * @return The string representation of the date.
	 */
	private static synchronized String makeDateString(Date date) {
		return sdfDefault.format(date);
	}

	/**
	 * Generates a String representation of a Java Date object. The string is formatted according to the specification for GnuStep ASCII
	 * property list dates.
	 * 
	 * @param date The date which should be represented.
	 * @return The string representation of the date.
	 */
	private static synchronized String makeDateStringGnuStep(Date date) {
		return sdfGnuStep.format(date);
	}

	/**
	 * Creates a date from its binary representation.
	 * 
	 * @param bytes The date bytes
	 */
	public NSDate(byte[] bytes) {
		// dates are 8 byte big-endian double, seconds since the epoch
		setWrappedDate(
				new Date(EPOCH + (long) (1000 * BinaryPropertyListParser.parseDouble(bytes))));
	}

	/**
	 * Parses a date from its textual representation. That representation has the following pattern: <code>yyyy-MM-dd'T'HH:mm:ss'Z'</code>
	 * 
	 * @param textRepresentation The textual representation of the date (ISO 8601 format)
	 * @throws ParseException When the date could not be parsed, i.e. it does not match the expected pattern.
	 */
	public NSDate(String textRepresentation) throws ParseException {
		setWrappedDate(parseDateString(textRepresentation));
	}

	/**
	 * Gets the date.
	 * 
	 * @return The date.
	 */
	public Date getDate() {
		return getWrappedDate();
	}

	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<date>");
		xml.append(makeDateString(getWrappedDate()));
		xml.append("</date>");
	}

	@Override
	public void toXML(StringBuilder xml, Integer level) {
		toXML(xml, level.intValue());
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		out.write(0x33);
		out.writeDouble((getWrappedDate().getTime() - EPOCH) / 1000.0);
	}

	/**
	 * Generates a string representation of the date.
	 * 
	 * @return A string representation of the date.
	 * @see Date#toString()
	 */
	@Override
	public String toString() {
		return getWrappedDate().toString();
	}

	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append("\"");
		ascii.append(makeDateString(date));
		ascii.append("\"");
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append("<*D");
		ascii.append(makeDateStringGnuStep(getWrappedDate()));
		ascii.append(">");
	}

}