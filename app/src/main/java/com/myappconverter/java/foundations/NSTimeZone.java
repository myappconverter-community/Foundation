
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.InvokableHelper;



public class NSTimeZone extends NSObject implements NSCopying, NSSecureCoding {

	private TimeZone wrappedTimeZone;
	private NSData data;
	private NSString dataVersion;
	private NSDictionary abreviations;

	public NSTimeZone() {
		wrappedTimeZone = new SimpleTimeZone(0, "GMT+01:00");
		data = new NSData();
		abreviations = new NSDictionary<NSString, NSString>();
	}

	public NSTimeZone(SimpleTimeZone simpleTimeZone) {
		super();
		this.wrappedTimeZone = simpleTimeZone;
	}

	// Creating and Initializing Time Zone Objects

	public TimeZone getWrappedTimeZone() {
		return wrappedTimeZone;
	}

	public void setWrappedTimeZone(TimeZone simpleTimeZone) {
		this.wrappedTimeZone = simpleTimeZone;
	}

	/**
	 * @Signature: timeZoneWithAbbreviation:
	 * @Declaration : + (id)timeZoneWithAbbreviation:(NSString *)abbreviation
	 * @Description : Returns the time zone object identified by a given abbreviation.
	 * @param abbreviation An abbreviation for a time zone.
	 * @return Return Value The time zone object identified by abbreviation determined by resolving the abbreviation to a name using the
	 *         abbreviation dictionary and then returning the time zone for that name. Returns nil if there is no match for abbreviation.
	 * @Discussion In general, you are discouraged from using abbreviations except for unique instances such as â€œUTCâ€? or â€œGMTâ€?. Time Zone
	 *             abbreviations are not standardized and so a given abbreviation may have multiple meaningsâ€”for example, â€œESTâ€? refers to
	 *             Eastern Time in both the United States and Australia
	 **/

	public static Object timeZoneWithAbbreviation(NSString abbreviation) {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = new SimpleTimeZone(80, abbreviation.getWrappedString());
		return timezone;
	}

	public static NSTimeZone timeZoneWithAbbreviation(Class<?> clazz, NSString abbreviation) {
		NSTimeZone timezone = (NSTimeZone) InstanceTypeFactory.getInstance(clazz);
		timezone.wrappedTimeZone = new SimpleTimeZone(80, abbreviation.getWrappedString());
		return timezone;
	}

	/**
	 * @Signature: timeZoneWithName:
	 * @Declaration : + (id)timeZoneWithName:(NSString *)tzName
	 * @Description : Returns the time zone object identified by a given ID.
	 * @param tzName The ID for the time zone.
	 * @return Return Value The time zone in the information directory with a name matching tzName. Returns nil if there is no match for the
	 *         name.
	 **/

	public static Object timeZoneWithName(NSString tzName) {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = TimeZone.getTimeZone(tzName.getWrappedString());
		return timezone;
	}


	public static NSTimeZone timeZoneWithName(Class<?> clazz, NSString tzName) {
		NSTimeZone timezone = (NSTimeZone) InstanceTypeFactory.getInstance(clazz);
		timezone.wrappedTimeZone = TimeZone.getTimeZone(tzName.getWrappedString());
		return timezone;
	}

	/**
	 * @Signature: timeZoneWithName:data:
	 * @Declaration : + (id)timeZoneWithName:(NSString *)tzName data:(NSData *)data
	 * @Description : Returns the time zone with a given ID whose data has been initialized using given data,
	 * @param tzName The ID for the time zone.
	 * @param data The data from the time-zone files located at /usr/share/zoneinfo.
	 * @return Return Value The time zone with the ID tzName whose data has been initialized using the contents of data.
	 * @Discussion You should not call this method directlyâ€”use timeZoneWithName: to get the time zone object for a given name.
	 **/

	public static Object timeZoneWithNameData(NSString tzName, NSData data) {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = SimpleTimeZone.getTimeZone(tzName.getWrappedString());
		timezone.data = data;
		return timezone;
	}

	public static NSTimeZone timeZoneWithNameData(Class<?> clazz, NSString tzName, NSData data) {
		NSTimeZone timezone = (NSTimeZone) InstanceTypeFactory.getInstance(clazz);
		timezone.wrappedTimeZone = SimpleTimeZone.getTimeZone(tzName.getWrappedString());
		timezone.data = data;
		return timezone;
	}


	/**
	 * @Signature: timeZoneForSecondsFromGMT:
	 * @Declaration : + (id)timeZoneForSecondsFromGMT:(NSInteger)seconds
	 * @Description : Returns a time zone object offset from Greenwich Mean Time by a given number of seconds.
	 * @param seconds The number of seconds by which the new time zone is offset from GMT.
	 * @return Return Value A time zone object offset from Greenwich Mean Time by seconds.
	 * @Discussion The name of the new time zone is GMT +/â€“ the offset, in hours and minutes. Time zones created with this method never have
	 *             daylight savings, and the offset is constant no matter the date.
	 **/

	public static Object timeZoneForSecondsFromGMT(int seconds) {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = new SimpleTimeZone(seconds, "UTC");
		return timezone;

	}

	public static NSTimeZone timeZoneForSecondsFromGMT(Class<?> clazz, int seconds) {
		NSTimeZone timezone = (NSTimeZone) InstanceTypeFactory.getInstance(clazz);
		timezone.wrappedTimeZone = new SimpleTimeZone(seconds, "UTC");
		return timezone;

	}

	/**
	 * @Signature: initWithName:
	 * @Declaration : - (id)initWithName:(NSString *)tzName
	 * @Description : Returns a time zone initialized with a given ID.
	 * @param tzName The ID for the time zone. Must not be nil.
	 * @return Return Value A time zone object initialized with the ID tzName.
	 * @Discussion If tzName is a known ID, this method calls initWithName:data: with the appropriate data object. In OS X v10.4 and earlier
	 *             providing nil for the parameter would have caused a crash. In OS X v10.5 and later, this now raises an invalid argument
	 *             exception.
	 **/

	public NSTimeZone initWithName(NSString tzName) {
		return timeZoneWithName(this.getClass(), tzName);
	}

	/**
	 * @Signature: initWithName:data:
	 * @Declaration : - (id)initWithName:(NSString *)tzName data:(NSData *)data
	 * @Description : Initializes a time zone with a given ID and time zone data.
	 * @param tzName The ID for the time zone. Must not be nil.
	 * @param data The data from the time-zone files located at /usr/share/zoneinfo.
	 * @Discussion You should not call this method directlyâ€”use initWithName: to get a time zone object. In OS X v10.4 and earlier providing
	 *             nil for the tzName parameter would have caused a crash. In OS X v10.5 and later, this now raises an invalid argument
	 *             exception.
	 **/

	public NSTimeZone initWithNameData(NSString tzName, NSData data) {
		return timeZoneWithNameData(this.getClass(), tzName, data);
	}

	/**
	 * @Signature: timeZoneDataVersion
	 * @Declaration : + (NSString *)timeZoneDataVersion
	 * @Description : Returns the time zone data version.
	 * @return Return Value A string containing the time zone data version.
	 **/

	public NSString timeZoneDataVersion() {
		return new NSString(android.util.TimeUtils.getTimeZoneDatabaseVersion());
	}

	/**
	 * @Signature: localTimeZone
	 * @Declaration : + (NSTimeZone *)localTimeZone
	 * @Description : Returns an object that forwards all messages to the default time zone for the current application.
	 * @return Return Value An object that forwards all messages to the default time zone for the current application.
	 * @Discussion The local time zone represents the current state of the default time zone at all times. If you get the default time zone
	 *             (using defaultTimeZone) and hold onto the returned object, it does not change if a subsequent invocation of
	 *             setDefaultTimeZone: changes the default time zoneâ€”you still have the specific time zone you originally got. The local
	 *             time zone adds a level of indirection, it acts as if it were the current default time zone whenever you invoke a method
	 *             on it.
	 **/

	public static NSTimeZone localTimeZone() {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = TimeZone.getDefault();
		return timezone;
	}

	/**
	 * @Signature: defaultTimeZone
	 * @Declaration : + (NSTimeZone *)defaultTimeZone
	 * @Description : Returns the default time zone for the current application.
	 * @return Return Value The default time zone for the current application. If no default time zone has been set, this method invokes
	 *         systemTimeZone and returns the system time zone.
	 * @Discussion The default time zone is the one that the application is running with, which you can change (so you can make the
	 *             application run as if it were in a different time zone). If you get the default time zone and hold onto the returned
	 *             object, it does not change if a subsequent invocation of setDefaultTimeZone: changes the default time zoneâ€”you still have
	 *             the specific time zone you originally got. Contrast this behavior with the object returned by localTimeZone.
	 **/

	public static NSTimeZone defaultTimeZone() {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = TimeZone.getDefault();
		return timezone;
	}

	/**
	 * @Signature: setDefaultTimeZone:
	 * @Declaration : + (void)setDefaultTimeZone:(NSTimeZone *)aTimeZone
	 * @Description : Sets the default time zone for the current application to a given time zone.
	 * @param aTimeZone The new default time zone for the current application.
	 * @Discussion There can be only one default time zone, so by setting a new default time zone, you lose the previous one.
	 **/

	public static void setDefaultTimeZone(NSTimeZone aTimeZone) {
		SimpleTimeZone.setDefault(aTimeZone.wrappedTimeZone);
	}

	/**
	 * @Signature: resetSystemTimeZone
	 * @Declaration : + (void)resetSystemTimeZone
	 * @Description : Resets the system time zone object cached by the application, if any.
	 * @Discussion If the application has cached the system time zone, this method clears that cached object. If you subsequently invoke
	 *             systemTimeZone, NSTimeZone will attempt to redetermine the system time zone and a new object will be created and cached
	 *             (see systemTimeZone).
	 **/

	public void resetSystemTimeZone() {
		wrappedTimeZone = (Calendar.getInstance(Locale.getDefault())).getTimeZone();
	}

	/**
	 * @Signature: systemTimeZone
	 * @Declaration : + (NSTimeZone *)systemTimeZone
	 * @Description : Returns the time zone currently used by the system.
	 * @return Return Value The time zone currently used by the system. If the current time zone cannot be determined, returns the GMT time
	 *         zone.
	 **/

	public static NSTimeZone systemTimeZone() {
		NSTimeZone timezone = new NSTimeZone();
		timezone.wrappedTimeZone = Calendar.getInstance(Locale.getDefault()).getTimeZone();
		return timezone;
	}

	// Getting Time Zone Information

	/**
	 * @Signature: abbreviationDictionary
	 * @Declaration : + (NSDictionary *)abbreviationDictionary
	 * @Description : Returns a dictionary holding the mappings of time zone abbreviations to time zone names.
	 * @return Return Value A dictionary holding the mappings of time zone abbreviations to time zone names.
	 * @Discussion Note that more than one time zone may have the same abbreviationâ€”for example, US/Pacific and Canada/Pacific both use the
	 *             abbreviation â€œPST.â€? In these cases, abbreviationDictionary chooses a single name to map the abbreviation to.
	 **/

	public static NSDictionary<NSString, NSString> abbreviationDictionary() {
		NSDictionary<NSString, NSString> dict = new NSDictionary<NSString, NSString>();
		Map<NSString, NSString> map = new HashMap<NSString, NSString>();
		String[] iDs = SimpleTimeZone.getAvailableIDs();
		for (int i = 0; i < iDs.length; i++) {
			map.put(new NSString(iDs[i]), new NSString(SimpleTimeZone.getTimeZone(iDs[i]).getID()));
		}
		dict.setWrappedDictionary(map);
		return dict;
	}

	/**
	 * @Signature: knownTimeZoneNames
	 * @Declaration : + (NSArray *)knownTimeZoneNames
	 * @Description : Returns an array of strings listing the IDs of all the time zones known to the system.
	 * @return Return Value An array of strings listing the IDs of all the time zones known to the system.
	 **/

	public static NSArray knownTimeZoneNames() {
		String[] iDs = TimeZone.getAvailableIDs();
		List idList = Arrays.asList(iDs);
		return new NSArray<Object>(idList);
	}

	/**
	 * @Signature: setAbbreviationDictionary:
	 * @Declaration : + (void)setAbbreviationDictionary:(NSDictionary *)dict
	 * @Description : Sets the abbreviation dictionary to the specified dictionary.
	 * @param dict A dictionary containing key-value pairs for looking up time zone names given their abbreviations. The keys should be
	 *            NSString objects containing the abbreviations; the values should be NSString objects containing their corresponding
	 *            geopolitical region names.
	 **/

	public void setAbbreviationDictionary(NSDictionary dict) {
		abreviations = dict;
	}

	// Getting Information About a Specific Time Zone
	/**
	 * @Signature: abbreviation
	 * @Declaration : - (NSString *)abbreviation
	 * @Description : Returns the abbreviation for the receiver.
	 * @return Return Value The abbreviation for the receiver, such as â€œEDTâ€? (Eastern Daylight Time).
	 * @Discussion Invokes abbreviationForDate: with the current date as the argument.
	 **/

	public NSString abbreviation() {
		return new NSString(wrappedTimeZone.getID());
	}

	/**
	 * @Signature: abbreviationForDate:
	 * @Declaration : - (NSString *)abbreviationForDate:(NSDate *)aDate
	 * @Description : Returns the abbreviation for the receiver at a given date.
	 * @param aDate The date for which to get the abbreviation for the receiver.
	 * @return Return Value The abbreviation for the receiver at aDate.
	 * @Discussion Note that the abbreviation may be different at different dates. For example, during daylight savings time the US/Eastern
	 *             time zone has an abbreviation of â€œEDT.â€? At other times, its abbreviation is â€œEST.â€?
	 **/

	public NSString abbreviationForDate(NSDate aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate.getWrappedDate());
		return new NSString(cal.getTimeZone().getID());
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the geopolitical region ID that identifies the receiver.
	 * @return Return Value The geopolitical region ID that identifies the receiver.
	 **/

	public NSString name() {
		return new NSString(wrappedTimeZone.getDisplayName());
	}

	/**
	 * @Signature: secondsFromGMT
	 * @Declaration : - (NSInteger)secondsFromGMT
	 * @Description : Returns the current difference in seconds between the receiver and Greenwich Mean Time.
	 * @return Return Value The current difference in seconds between the receiver and Greenwich Mean Time.
	 **/

	public int secondsFromGMT() {
		return wrappedTimeZone.getOffset(System.currentTimeMillis());
	}

	/**
	 * @Signature: secondsFromGMTForDate:
	 * @Declaration : - (NSInteger)secondsFromGMTForDate:(NSDate *)aDate
	 * @Description : Returns the difference in seconds between the receiver and Greenwich Mean Time at a given date.
	 * @param aDate The date against which to test the receiver.
	 * @return Return Value The difference in seconds between the receiver and Greenwich Mean Time at aDate.
	 * @Discussion The difference may be different from the current difference if the time zone changes its offset from GMT at different
	 *             points in the yearâ€”for example, the U.S. time zones change with daylight savings time.
	 **/

	public int secondsFromGMTForDate(NSDate aDate) {
		return wrappedTimeZone.getOffset(aDate.getWrappedDate().getTime());
	}

	/**
	 * @Signature: data
	 * @Declaration : - (NSData *)data
	 * @Description : Returns the data that stores the information used by the receiver.
	 * @return Return Value The data that stores the information used by the receiver.
	 * @Discussion This data should be treated as an opaque object.
	 **/

	public NSData data() {
		return data;
	}

	// Comparing Time Zones
	/**
	 * @Signature: isEqualToTimeZone:
	 * @Declaration : - (BOOL)isEqualToTimeZone:(NSTimeZone *)aTimeZone
	 * @Description : Returns a Boolean value that indicates whether the receiver has the same name and data as another given time zone.
	 * @param aTimeZone The time zone to compare with the receiver.
	 * @return Return Value YES if aTimeZone and the receiver have the same name and data, otherwise NO.
	 **/

	public boolean isEqualToTimeZone(NSTimeZone aTimeZone) {
		if (wrappedTimeZone.getID().equals(aTimeZone.getWrappedTimeZone().getID()))
			return true;
		return false;
	}

	// Describing a Time Zone

	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns the description of the receiver.
	 * @return Return Value The description of the receiver, including the name, abbreviation, offset from GMT, and whether or not daylight
	 *         savings time is currently in effect.
	 **/
	@Override

	public NSString description() {
		return new NSString(wrappedTimeZone.toString());
	}

	/**
	 * @Signature: localizedName:locale:
	 * @Declaration : - (NSString *)localizedName:(NSTimeZoneNameStyle)style locale:(NSLocale *)locale
	 * @Description : Returns the name of the receiver localized for a given locale.
	 * @param style The format style for the returned string.
	 * @param locale The locale for which to format the name.
	 * @return Return Value The name of the receiver localized for locale using style.
	 **/

	public NSString localizedNameLocale(int style, NSLocale locale) {
		return new NSString(wrappedTimeZone.getDisplayName(isDaylightSavingTime(), style, locale.getWrappedLocale()));
	}

	// Getting Information About Daylight Saving

	/**
	 * @Signature: isDaylightSavingTime
	 * @Declaration : - (BOOL)isDaylightSavingTime
	 * @Description : Returns a Boolean value that indicates whether the receiver is currently using daylight saving time.
	 * @return Return Value YES if the receiver is currently using daylight savings time, otherwise NO.
	 * @Discussion This method invokes isDaylightSavingTimeForDate: with the current date as the argument.
	 **/

	public boolean isDaylightSavingTime() {
		return wrappedTimeZone.useDaylightTime();
	}

	/**
	 * @Signature: daylightSavingTimeOffset
	 * @Declaration : - (NSTimeInterval)daylightSavingTimeOffset
	 * @Description : Returns the current daylight saving time offset of the receiver.
	 * @return Return Value The daylight current saving time offset of the receiver.
	 **/

	public double daylightSavingTimeOffset() {
		return wrappedTimeZone.getOffset(System.currentTimeMillis());
	}

	/**
	 * @Signature: isDaylightSavingTimeForDate:
	 * @Declaration : - (BOOL)isDaylightSavingTimeForDate:(NSDate *)aDate
	 * @Description : Returns a Boolean value that indicates whether the receiver uses daylight savings time at a given date.
	 * @param aDate The date against which to test the receiver.
	 * @return Return Value YES if the receiver uses daylight savings time at aDate, otherwise NO.
	 **/

	public boolean isDaylightSavingTimeForDate(NSDate aDate) {
		return wrappedTimeZone.inDaylightTime(new Date(aDate.getWrappedDate().getTime()));
	}

	/**
	 * @Signature: daylightSavingTimeOffsetForDate:
	 * @Declaration : - (NSTimeInterval)daylightSavingTimeOffsetForDate:(NSDate *)aDate
	 * @Description : Returns the daylight saving time offset for a given date.
	 * @param aDate A date.
	 * @return Return Value The daylight saving time offset for aDate.
	 **/

	public double daylightSavingTimeOffsetForDate(NSDate aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate.getWrappedDate());
		return wrappedTimeZone.getOffset(cal.getTimeInMillis());
	}

	/**
	 * @Signature: nextDaylightSavingTimeTransition
	 * @Declaration : - (NSDate *)nextDaylightSavingTimeTransition
	 * @Description : Returns the date of the next daylight saving time transition for the receiver.
	 * @return Return Value The date of the next (after the current instant) daylight saving time transition for the receiver.
	 **/

	public NSDate nextDaylightSavingTimeTransition() {
		Dst dst = new Dst();
		dst.calculate(wrappedTimeZone, Calendar.getInstance().get(Calendar.YEAR));
		return new NSDate(dst.getStart());
	}

	/**
	 * @Signature: nextDaylightSavingTimeTransitionAfterDate:
	 * @Declaration : - (NSDate *)nextDaylightSavingTimeTransitionAfterDate:(NSDate *)aDate
	 * @Description : Returns the next daylight saving time transition after a given date.
	 * @param aDate A date.
	 * @return Return Value The next daylight saving time transition after aDate.
	 **/

	public NSDate nextDaylightSavingTimeTransitionAfterDate(NSDate aDate) {
		Dst dst = new Dst();
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate.getWrappedDate());
		dst.calculate(wrappedTimeZone, cal.get(Calendar.YEAR));
		return new NSDate(dst.getStart());
	}

	/**
	 *
	 */

	private class Dst {

		Date start;
		Date end;

		public Date getStart() {
			return start;
		}


		public void setStart(Date start) {
			this.start = start;
		}


		public Date getEnd() {
			return end;
		}


		public void setEnd(Date end) {
			this.end = end;
		}

		public int calculate(TimeZone tz, int year) {
			final Calendar c = Calendar.getInstance(tz);
			c.setLenient(false);
			c.set(year, Calendar.JANUARY, 1, 1, 0, 0);
			c.set(Calendar.MILLISECOND, 0);

			if (tz.getDSTSavings() == 0) {
				return 0;
			}

			boolean flag = false;

			do {
				Date date = c.getTime();
				boolean daylight = tz.inDaylightTime(date);

				if (daylight && !flag) {
					flag = true;
					start = date;
				} else if (!daylight && flag) {
					flag = false;
					end = date;
				}

				c.add(Calendar.HOUR_OF_DAY, 1);
			} while (c.get(Calendar.YEAR) == year);

			return 1;
		}
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
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}