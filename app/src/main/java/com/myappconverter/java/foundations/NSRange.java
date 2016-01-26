
package com.myappconverter.java.foundations;

import java.io.Serializable;




public class NSRange extends NSObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 6130929727183379156L;
	public static final NSRange ZeroRange = new NSRange();
	public int location;
	public int length;

	public NSRange() {
		location = 0;
		length = 0;
	}

	public NSRange(int loc, int len) {
		super();
		location = loc;
		length = len;
	}

	@Override
	public int hashCode() {
		return (122 * length) - location;
	}

	/**
	 * @Description : Returns a Boolean value that indicates whether two given ranges are equal.
	 * @return : Return Value YES if range1 and range2 have the same locations and lengths.
	 **/

	public static boolean NSEqualRanges(NSRange range1, NSRange range2) {
		if (range1 != null && range2 != null &&
				range1.location == range2.location && range1.length == range2.length) {
			return true;
		}
		return false;
	}

	/**
	 * @Description : Returns the intersection of the specified ranges.
	 * @return : Return Value A range describing the intersection of range1 and range2â€”that is, a range containing the indices that exist in
	 *         both ranges.
	 **/


	public static NSRange NSIntersectionRange(NSRange rang1, NSRange rang2) {
		if (rang1.length + rang1.location < rang2.location
				|| rang2.length + rang2.location < rang1.location)
			return new NSRange(0, 0);

		int newLength;
		int newLocation = Math.max(rang1.location, rang2.location);
		int maxRange1 = rang1.location + rang1.length;
		int maxRange2 = rang2.location + rang2.length;
		if (maxRange1 < maxRange2)
			newLength = rang1.length - rang2.location;
		else
			newLength = rang2.length - rang1.location;
		return new NSRange(newLocation, newLength);
	}

	/**
	 * @Description : Returns a Boolean value that indicates whether a specified position is in a given range.
	 * @return : Return Value YES if loc lies within rangeâ€”that is, if itâ€™s greater than or equal to range.location and less than
	 *         range.location plus range.length.
	 **/


	public static boolean NSLocationInRange(int aLocation, NSRange range) {
		return (aLocation >= range.location) && (aLocation < (range.location + range.length));
	}

	/**
	 * @Description : Creates a new NSRange from the specified values.
	 * @return : Return Value An NSRange with location location and length length.
	 **/


	public static NSRange NSMakeRange(int loc, int len) {
		return new NSRange(loc, len);
	}

	/**
	 * @Description : Returns the sum of the location and length of the range.
	 * @return : Return Value The sum of the location and length of the rangeâ€”that is, range.location + range.length.
	 **/

	public static int NSMaxRange(NSRange range) {
		return range.location + range.length;

	}

	/**
	 * @Description : Returns a range from a textual representation.
	 **/

	public static NSRange NSRangeFromString(NSString aString) {
		String string = aString.getWrappedString();

		if (string == null || !string.trim().matches("\\{[0-9]+,\\s*[0-9]+\\}")) {
			return new NSRange(0, 0);
		}

		String str = string.trim();
		str = str.substring(1, str.length() - 1);
		String[] parts = str.split(",\\s*");

		return new NSRange(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

	}

	/**
	 * @Description : Returns a string representation of a range.
	 * @return : Return Value A string of the form â€œ{a, b}â€?, where a and b are non-negative integers representing aRange.
	 **/

	public static NSString NSStringFromRange(NSRange nsRange) {
		return new NSString("{" + nsRange.location + ", " + nsRange.length + "}");
	}

	/**
	 * @Description : Returns the union of the specified ranges.
	 * @return : Return Value A range covering all indices in and between range1 and range2. If one range is completely contained in the
	 *         other, the returned range is equal to the larger range.
	 **/

	public static NSRange NSUnionRange(NSRange rang1, NSRange rang2) {

		int locationUnion = rang1.location < rang2.location ? rang1.location : rang2.location;
		int maxRange1 = rang1.location + rang1.length;
		int maxRange2 = rang2.location + rang2.length;
		int lengthUnion = maxRange1 > maxRange2 ? maxRange1 - locationUnion : maxRange2 - locationUnion;
		return new NSRange(locationUnion, lengthUnion);
	}

	public String toString() {
		return "{location=" + location + ", length=" + length + "}";
	}

	/** ADDDED **/

	public NSRange(NSRange range) {
		this();
		if (range != null) {
			this.location = range.location();
			this.length = range.length();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof NSRange))
			return false;
		final NSRange other = (NSRange) obj;
		return location == other.location && length == other.length;
	}

	public int location() {
		return location;
	}

	public int length() {
		return length;
	}

	@Override
	public NSObject clone() {
		return this;
	}

	public boolean containsLocation(int _location) {
		return _location >= location && _location < this.maxRange();
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public int maxRange() {
		return location + length;
	}

	public NSRange rangeByIntersectingRange(NSRange otherRange) {
		if (otherRange == null)
			return ZeroRange;

		int start = Math.max(location, otherRange.location());
		int end = Math.min(maxRange(), otherRange.maxRange());
		return new NSRange(start, end - start);
	}

	public boolean isSubrangeOfRange(NSRange otherRange) {
		if (otherRange == null)
			return false;

		return otherRange.location <= location && otherRange.maxRange() >= maxRange();
	}

	public boolean intersectsRange(NSRange otherRange) {
		if (otherRange == null)
			return false;

		if (otherRange.location >= location && otherRange.location <= maxRange())
			return true; // starts in this range
		else if (otherRange.maxRange() >= location && otherRange.maxRange() <= maxRange())
			return true; // ends in this range
		else
			return false;
	}

	public void subtractRange(NSRange otherRange, NSMutableRange resultRange1,
							  NSMutableRange resultRange2) {
		NSRange intersection = rangeByIntersectingRange(otherRange);

		// no intersection, return empty range
		if (intersection.isEmpty()) {
			resultRange1.setLocation(0);
			resultRange1.setLength(0);

			resultRange2.setLocation(0);
			resultRange2.setLength(0);

			// total intersection, return empty range
		} else if (intersection.location() == location() && intersection.length() == length()) {
			resultRange1.setLocation(0);
			resultRange1.setLength(0);

			resultRange2.setLocation(0);
			resultRange2.setLength(0);

			// intersects from the start, single result with the remaining range
		} else if (intersection.location() == location()) {
			resultRange1.setLocation(intersection.maxRange());
			resultRange1.setLength(length() - intersection.length());

			resultRange2.setLocation(0);
			resultRange2.setLength(0);

			// intersects from the end, single result with the beginning range
		} else {
			resultRange1.setLocation(intersection.location - 1);
			resultRange1.setLength(length() - intersection.length());

			resultRange2.setLocation(0);
			resultRange2.setLength(0);
		}
	}

	private NSRange(int location, int length, boolean checkValues) {
		this.location = 0;
		this.length = 0;

		if (checkValues) {
			if (location < 0)
				throw new IllegalArgumentException("Cannot create an" + super.getClass().getName()
						+ " with negative location.");
			if (length < 0)
				throw new IllegalArgumentException(
						"Cannot create an" + super.getClass().getName() + " with negative length.");

			if (location - 1 > 2147483647 - length)
				throw new IllegalArgumentException("Range endpoint greater than Integer.MAX_VALUE");

		}

		this.location = location;
		this.length = length;
	}

	public NSRange rangeByUnioningRange(NSRange otherRange) {
		if (otherRange == null)
			return this;

		int start = Math.min(location, otherRange.location());
		int end = Math.max(maxRange(), otherRange.maxRange());
		return new NSRange(start, end - start);
	}

}