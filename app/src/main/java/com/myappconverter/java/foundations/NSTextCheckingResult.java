
package com.myappconverter.java.foundations;




public class NSTextCheckingResult extends NSObject {

	// Enumeration


	public static enum NSTextCheckingTypes {
		NSTextCheckingTypeOrthography(1L << 0), //
		NSTextCheckingTypeSpelling(1L << 1), //
		NSTextCheckingTypeGrammar(1L << 2), //
		NSTextCheckingTypeDate(1L << 3), //
		NSTextCheckingTypeAddress(1L << 4), //
		NSTextCheckingTypeLink(1L << 5), //
		NSTextCheckingTypeQuote(1L << 6), //
		NSTextCheckingTypeDash(1L << 7), //
		NSTextCheckingTypeReplacement(1L << 8), //
		NSTextCheckingTypeCorrection(1L << 9), //
		NSTextCheckingTypeRegularExpression(1L << 10), //
		NSTextCheckingTypePhoneNumber(1L << 11), //
		NSTextCheckingTypeTransitInformation(1L << 12);//
		long value;

		NSTextCheckingTypes(long l) {
			value = l;
		}
	};

	public NSTextCheckingResult(NSRange pRange) {
		this.range = pRange;
	}

	public NSTextCheckingResult(NSRange range, NSDictionary<?, ?> components) {
		this.range.length = range.length;
		this.range.location = range.location;
		this.components = new NSDictionary(components.getWrappedDictionary());
	}

	public NSTextCheckingResult(NSRange range, NSString replacementString, String stringName) {
		this.range.length = range.length;
		this.range.location = range.location;
		if ("replacementString".equals(stringName)) {
			this.replacementString = new NSString(replacementString.getWrappedString());
		} else if ("phoneNumber".equals(stringName)) {
			this.phoneNumber = new NSString(replacementString.getWrappedString());

		}
	}

	public NSTextCheckingResult(NSRange range, NSDate date, NSTimeZone timeZone, double duration) {
		this.range.length = range.length;
		this.range.location = range.location;
		this.date = date;
		this.timeZone = timeZone;
		this.duration = duration;
	}

	public NSTextCheckingResult(NSRange range, NSArray<NSString> details) {
		this.range.length = range.length;
		this.range.location = range.location;
		this.grammarDetails = details;
	}

	public NSTextCheckingResult(NSRange range, NSURL url) {
		this.range.length = range.length;
		this.range.location = range.location;
		this.URL = url;
	}

	public NSTextCheckingResult(NSRange range, NSOrthography orthography) {
		this.range.length = range.length;
		this.range.location = range.location;
		this.orthography = orthography;
	}

	/**
	 * range Returns the range of the result that the receiver represents. (read-only)
	 *
	 *  NSRange range
	 * @Discussion This property will be present for all returned NSTextCheckingResult instances.
	 */

	private NSRange range;

	public NSRange range() {
		return new NSRange(range.location, range.length);
	}

	// Properties

	/**
	 * @Declaration :  NSDictionary *addressComponents
	 * @Description : The address dictionary of a type checking result. (read-only)
	 * @Discussion The dictionary keys are described in â€œKeys for Address Components.â€?
	 **/
	private NSDictionary<NSString, NSObject> addressComponents;

	public NSDictionary<NSString, NSObject> getAddressComponents() {
		return addressComponents;
	}


	public NSDictionary<NSString, NSObject> addressComponents() {
		return addressComponents;

	}

	/**
	 * @Declaration :  NSDictionary *components
	 * @Description : A dictionary containing the components of a type checking result. (read-only)
	 * @Discussion Currently used by the transit checking result. The supported keys are located in â€œKeys for Transit Components.â€?
	 **/
	private NSDictionary<NSString, NSObject> components;

	public NSDictionary<NSString, NSObject> getComponents() {
		return components;
	}


	public NSDictionary<NSString, NSObject> components() {
		return components;
	}

	/**
	 * @Declaration :  NSDate *date
	 * @Description : The date component of a type checking result. (read-only)
	 **/
	private NSDate date;

	public NSDate getDate() {
		return date;
	}


	public NSDate date() {
		return new NSDate(date.getWrappedDate());
	}

	/**
	 * @Declaration :  NSTimeInterval duration
	 * @Description : The duration component of a type checking result. (read-only)
	 **/
	private double duration;

	public double getDuration() {
		return duration;
	}


	public double duration() {
		return duration;
	}

	/**
	 * grammarDetails The details of a located grammatical type checking result. (read-only)
	 *
	 *  NSArray *grammarDetails Discussion This array of strings is suitable for presenting to the user.
	 */
	private NSArray<NSString> grammarDetails;

	public NSArray<NSString> getGrammarDetails() {
		return grammarDetails;
	}


	public NSArray<NSString> grammarDetails() {
		return new NSArray<NSString>(grammarDetails.getWrappedList());
	}

	/**
	 * @Declaration :  NSUInteger numberOfRanges
	 * @Description : Returns the number of ranges. (read-only)
	 * @Discussion A result must have at least one range, but may optionally have more (for example, to represent regular expression capture
	 *             groups). Passing rangeAtIndex: the value 0 always returns the value of the the range property. Additional ranges, if any,
	 *             will have indexes from 1 to numberOfRanges-1.
	 **/
	private int numberOfRanges;

	public int getNumberOfRanges() {
		return numberOfRanges;
	}


	public int numberOfRanges() {
		return numberOfRanges;
	}

	/**
	 * @Declaration :  NSOrthography *orthography
	 * @Description : The detected orthography of a type checking result. (read-only)
	 **/

	private NSOrthography orthography;

	public NSOrthography getOrthography() {
		return orthography;
	}


	public NSOrthography orthography() {
		return orthography;
	}

	/**
	 * @Declaration :  NSString *phoneNumber
	 * @Description : The phone number of a type checking result. (read-only)
	 **/
	private NSString phoneNumber;

	public NSString getPhoneNumber() {
		return phoneNumber;
	}


	public NSString phoneNumber() {
		return new NSString(phoneNumber.getWrappedString());
	}

	/**
	 * @Declaration :  NSRegularExpression *regularExpression
	 * @Description : The regular expression of a type checking result. (read-only)
	 **/

	private NSRegularExpression regularExpression;


	public NSRegularExpression regularExpression() {
		return new NSRegularExpression(new NSString(regularExpression.getaPattern().pattern()));
	}

	public NSRegularExpression getRegularExpression() {
		return regularExpression;
	}

	public NSString getReplacementString() {
		return replacementString;
	}

	public NSTextCheckingTypes getResultType() {
		return resultType;
	}

	public NSTimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 * @Declaration :  NSString *replacementString
	 * @Description : A replacement string from one of a number of replacement checking results. (read-only)
	 **/
	private NSString replacementString;


	public NSString replacementString() {
		return new NSString(replacementString.getWrappedString());
	}

	/**
	 * @Declaration :  NSTextCheckingType resultType
	 * @Description : Returns the text checking result type that the receiver represents. (read-only)
	 * @Discussion The possible result types for the built in checking capabilities are described in â€œNSTextCheckingType.â€? This property
	 *             will be present for all returned NSTextCheckingResult instances.
	 **/
	private NSTextCheckingTypes resultType;


	public NSTextCheckingTypes resultType() {
		return resultType;
	}

	/**
	 * @Declaration :  NSTimeZone *timeZone
	 * @Description : The time zone component of a type checking result. (read-only)
	 **/
	private NSTimeZone timeZone;


	public NSTimeZone timeZone() {
		return timeZone;
	}

	/**
	 * @Declaration :  NSURL *URL
	 * @Description : The URL of a type checking result. (read-only)
	 **/
	private NSURL URL;


	public NSURL URL() {
		return URL;
	}

	/**
	 * @Signature: addressCheckingResultWithRange:components:
	 * @Declaration : + (NSTextCheckingResult *)addressCheckingResultWithRange:(NSRange)range components:(NSDictionary *)components
	 * @Description : Creates and returns a text checking result with the specified address components.
	 * @param range The range of the detected result.
	 * @param components A dictionary containing the address components. The dictionary keys are described in â€œKeys for Address Components.â€?
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeAddress.
	 **/

	public static NSTextCheckingResult addressCheckingResultWithRangeComponents(NSRange range, NSDictionary<?, ?> components) {
		return new NSTextCheckingResult(range, components);

	}

	/**
	 * @Signature: correctionCheckingResultWithRange:replacementString:
	 * @Declaration : + (NSTextCheckingResult *)correctionCheckingResultWithRange:(NSRange)range replacementString:(NSString
	 *              *)replacementString
	 * @Description : Creates and returns a text checking result after detecting a possible correction.
	 * @param range The range of the detected result.
	 * @param replacementString The suggested replacement string.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeSpelling.
	 **/

	public static NSTextCheckingResult correctionCheckingResultWithRangeReplacementString(NSRange range, NSString replacementString) {
		return new NSTextCheckingResult(range, replacementString, "replacementString");
	}

	/**
	 * @Signature: dashCheckingResultWithRange:replacementString:
	 * @Declaration : + (NSTextCheckingResult *)dashCheckingResultWithRange:(NSRange)range replacementString:(NSString *)replacementString
	 * @Description : Creates and returns a text checking result with the specified dash corrected replacement string.
	 * @param range The range of the detected result.
	 * @param replacementString The replacement string.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeDash.
	 **/

	public static NSTextCheckingResult dashCheckingResultWithRangeReplacementString(NSRange range, NSString replacementString) {
		return new NSTextCheckingResult(range, replacementString, "replacementString");
	}

	/**
	 * @Signature: dateCheckingResultWithRange:date:
	 * @Declaration : + (NSTextCheckingResult *)dateCheckingResultWithRange:(NSRange)range date:(NSDate *)date
	 * @Description : Creates and returns a text checking result with the specified date.
	 * @param range The range of the detected result.
	 * @param date The detected date.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeDate.
	 **/

	public static NSTextCheckingResult dateCheckingResultWithRangeDate(NSRange range, NSString replacementString) {
		return new NSTextCheckingResult(range, replacementString, "replacementString");
	}

	/**
	 * @Signature: dateCheckingResultWithRange:date:timeZone:duration:
	 * @Declaration : + (NSTextCheckingResult *)dateCheckingResultWithRange:(NSRange)range date:(NSDate *)date timeZone:(NSTimeZone
	 *              *)timeZone duration:(NSTimeInterval)duration
	 * @Description : Creates and returns a text checking result with the specified date, time zone, and duration.
	 * @param range The range of the detected result.
	 * @param date The detected date.
	 * @param timeZone The detected time zone.
	 * @param duration The detected duration.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeDate.
	 **/

	public static NSTextCheckingResult dateCheckingResultWithRangeDateTimeZoneDuration(NSRange range, NSDate date, NSTimeZone timeZone,
																					   double duration) {
		return new NSTextCheckingResult(range, date, timeZone, duration);
	}

	/**
	 * @Signature: grammarCheckingResultWithRange:details:
	 * @Declaration : + (NSTextCheckingResult *)grammarCheckingResultWithRange:(NSRange)range details:(NSArray *)details
	 * @Description : Creates and returns a text checking result with the specified array of grammatical errors.
	 * @param range The range of the detected result.
	 * @param details An array of details regarding the grammatical errors. This array of strings is suitable for presenting to the user.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeGrammar.
	 **/

	public static NSTextCheckingResult grammarCheckingResultWithRangeDetails(NSRange range, NSArray<NSString> details) {
		return new NSTextCheckingResult(range, details);
	}

	/**
	 * @Signature: linkCheckingResultWithRange:URL:
	 * @Declaration : + (NSTextCheckingResult *)linkCheckingResultWithRange:(NSRange)range URL:(NSURL *)url
	 * @Description : Creates and returns a text checking result with the specified URL.
	 * @param range The range of the detected result.
	 * @param url The URL.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeLink.
	 **/

	public static NSTextCheckingResult linkCheckingResultWithRangeURL(NSRange range, NSURL url) {
		return new NSTextCheckingResult(range, url);
	}

	/**
	 * @Signature: orthographyCheckingResultWithRange:orthography:
	 * @Declaration : + (NSTextCheckingResult *)orthographyCheckingResultWithRange:(NSRange)range orthography:(NSOrthography *)orthography
	 * @Description : Creates and returns a text checking result with the specified orthography.
	 * @param range The range of the detected result.
	 * @param orthography An orthography object that describes the script.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeOrthography.
	 **/

	public static NSTextCheckingResult orthographyCheckingResultWithRangeOrthography(NSRange range, NSOrthography orthography) {
		return new NSTextCheckingResult(range, orthography);
	}

	/**
	 * @Signature: phoneNumberCheckingResultWithRange:phoneNumber:
	 * @Declaration : + (NSTextCheckingResult *)phoneNumberCheckingResultWithRange:(NSRange)range phoneNumber:(NSString *)phoneNumber
	 * @Description : Creates and returns a text checking result with the specified phone number.
	 * @param range The range of the detected result.
	 * @param phoneNumber The phone number.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypePhoneNumber.
	 **/

	public static NSTextCheckingResult phoneNumberCheckingResultWithRangePhoneNumber(NSRange range, NSString phoneNumber) {
		return new NSTextCheckingResult(range, phoneNumber, "phoneNumber");

	}

	/**
	 * @Signature: quoteCheckingResultWithRange:replacementString:
	 * @Declaration : + (NSTextCheckingResult *)quoteCheckingResultWithRange:(NSRange)range replacementString:(NSString *)replacementString
	 * @Description : Creates and returns a text checking result with the specified quote-balanced replacement string.
	 * @param range The range of the detected result.
	 * @param replacementString The replacement string.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeQuote.
	 **/

	public static NSTextCheckingResult quoteCheckingResultWithRangeReplacementString(NSRange range, NSString phoneNumber) {
		return new NSTextCheckingResult(range, phoneNumber, "phoneNumber");

	}

	/**
	 * @Signature: regularExpressionCheckingResultWithRanges:count:regularExpression:
	 * @Declaration : + (NSTextCheckingResult *)regularExpressionCheckingResultWithRanges:(NSRangePointer)ranges count:(NSUInteger)count
	 *              regularExpression:(NSRegularExpression *)regularExpression
	 * @Description : Creates and returns a type checking result with the specified regular expression data.
	 * @param ranges A C array of ranges, which must have at least one element, and the first element represents the overall range.
	 * @param count The number of items in the ranges array.
	 * @param regularExpression The regular expression.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of
	 *         NSTextCheckingTypeRegularExpression.
	 **/

	public static NSTextCheckingResult regularExpressionCheckingResultWithRangesCountRegularExpression(NSRangePointer ranges, int count,
																									   NSRegularExpression regularExpression) {
		return null;

	}

	/**
	 * @Signature: replacementCheckingResultWithRange:replacementString:
	 * @Declaration : + (NSTextCheckingResult *)replacementCheckingResultWithRange:(NSRange)range replacementString:(NSString
	 *              *)replacementString
	 * @Description : Creates and returns a text checking result with the specified replacement string.
	 * @param range The range of the detected result.
	 * @param replacementString The replacement string.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeReplacement.
	 **/

	static NSTextCheckingResult replacementCheckingResultWithRangeReplacementString(NSRange range, NSString replacementString) {
		return new NSTextCheckingResult(range, replacementString, "replacementString");
	}

	/**
	 * @Signature: spellCheckingResultWithRange:
	 * @Declaration : + (NSTextCheckingResult *)spellCheckingResultWithRange:(NSRange)range
	 * @Description : Creates and returns a text checking result with the range of a misspelled word.
	 * @param range The range of the detected result.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of NSTextCheckingTypeSpelling.
	 **/

	public static NSTextCheckingResult spellCheckingResultWithRange(NSRange range) {
		return new NSTextCheckingResult(range);

	}

	/**
	 * @Signature: transitInformationCheckingResultWithRange:components:
	 * @Declaration : + (NSTextCheckingResult *)transitInformationCheckingResultWithRange:(NSRange)range components:(NSDictionary
	 *              *)components
	 * @Description : Creates and returns a text checking result with the specified transit information.
	 * @param range The range of the detected result.
	 * @param components A dictionary containing the transit components. The currently supported keys are NSTextCheckingAirlineKey and
	 *            NSTextCheckingFlightKey.
	 * @return Return Value Returns an NSTextCheckingResult with the specified range and a resultType of
	 *         NSTextCheckingTypeTransitInformation.
	 **/

	public static NSTextCheckingResult transitInformationCheckingResultWithRangeComponents(NSRange range, NSDictionary<?, ?> components) {
		return new NSTextCheckingResult(range, components);

	}

	/**
	 * @Signature: rangeAtIndex:
	 * @Declaration : - (NSRange)rangeAtIndex:(NSUInteger)idx
	 * @Description : Returns the result type that the range represents.
	 * @param idx The index of the result.
	 * @return Return Value The range of the result.
	 * @Discussion A result must have at least one range, but may optionally have more (for example, to represent regular expression capture
	 *             groups). Passing rangeAtIndex: the value 0 always returns the value of the the range property. Additional ranges, if any,
	 *             will have indexes from 1 to numberOfRanges-1.
	 **/

	public NSRange rangeAtIndex(int idx) {

		if (idx == 0 || numberOfRanges < idx) {
			return this.range;
		} else {
			return this.range;
		}

	}

	/**
	 * @Signature: resultByAdjustingRangesWithOffset:
	 * @Declaration : - (NSTextCheckingResult *)resultByAdjustingRangesWithOffset:(NSInteger)offset
	 * @Description : Returns a new text checking result after adjusting the ranges as specified by the offset.
	 * @param offset The amount the ranges are adjusted.
	 * @return Return Value A new NSTextCheckingResult instance with the adjusted range or ranges.
	 **/

	public NSTextCheckingResult resultByAdjustingRangesWithOffset(int offset) {
		return new NSTextCheckingResult(new NSRange(this.range.location + offset, this.range.length));

	}

}