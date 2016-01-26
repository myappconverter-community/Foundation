
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.mapping.utils.PerformBlock;



public class NSRegularExpression extends NSObject implements NSCoding, NSCopying {

	private Pattern aPattern;

	// Enumeration

	public static enum NSMatchingFlags {
		NSMatchingProgress(1 << 0), //
		NSMatchingCompleted(1 << 1), //
		NSMatchingHitEnd(1 << 2), //
		NSMatchingRequiredEnd(1 << 3), //
		NSMatchingInternalError(1 << 4);//

		int value;

		NSMatchingFlags(int v) {
			value = v;
		}
	};


	public static enum NSRegularExpressionOptions {
		NSRegularExpressionCaseInsensitive(1 << 0), //
		NSRegularExpressionAllowCommentsAndWhitespace(1 << 1), //
		NSRegularExpressionIgnoreMetacharacters(1 << 2), //
		NSRegularExpressionDotMatchesLineSeparators(1 << 3), //
		NSRegularExpressionAnchorsMatchLines(1 << 4), //
		NSRegularExpressionUseUnixLineSeparators(1 << 5), //
		NSRegularExpressionUseUnicodeWordBoundaries(1 << 6);

		int value;

		NSRegularExpressionOptions(int v) {
			value = v;
		}
	};


	public static enum NSMatchingOptions {
		NSMatchingReportProgress(1 << 0), //
		NSMatchingReportCompletion(1 << 1), //
		NSMatchingAnchored(1 << 2), //
		NSMatchingWithTransparentBounds(1 << 3), //
		NSMatchingWithoutAnchoringBounds(1 << 4);

		int value;

		NSMatchingOptions(int v) {
			value = v;
		}
	};

	public NSRegularExpression() {
	}

	public NSRegularExpression(NSString pattern) {
		this.aPattern = Pattern.compile(pattern.getWrappedString());
	}

	public NSRegularExpression(NSString pattern, NSRegularExpressionOptions options) {
		this.aPattern = Pattern.compile(pattern.getWrappedString());
		this.options = options;
	}

	/**
	 * @Declaration :  NSUInteger numberOfCaptureGroups
	 * @Description : Returns the number of capture groups in the regular expression. (read-only)
	 * @Discussion A capture group consists of each possible match within a regular expression. Each capture group can then be used in a
	 *             replacement template to insert that value into a replacement string. This value puts a limit on the values of n for $n in
	 *             templates, and it determines the number of ranges in the returned NSTextCheckingResult instances returned in the match...
	 *             methods. An exception will be generated if you attempt to access a result with an index value exceeding
	 *             numberOfCaptureGroups-1.
	 **/
	private int numberOfCaptureGroups;


	int numberOfCaptureGroups() {
		return numberOfCaptureGroups;
	}

	/**
	 * @Declaration :  NSRegularExpressionOptions options
	 * @Description : Returns the options used when the regular expression option was created. (read-only)
	 * @Discussion The options property specifies aspects of the regular expression matching that are always used when matching the regular
	 *             expression. For example, if the expression is case sensitive, allows comments, ignores metacharacters, etc.. See
	 *             â€œNSRegularExpressionOptionsâ€? for a complete discussion of the possible constants and their meanings.
	 **/
	private NSRegularExpressionOptions options;


	public NSRegularExpressionOptions options() {
		return options;
	}

	/**
	 * @Declaration :  NSString *pattern
	 * @Description : Returns the regular expression pattern. (read-only)
	 **/
	private NSString pattern;


	public NSString pattern() {
		return pattern;
	};

	/**
	 * @Signature: escapedPatternForString:
	 * @Declaration : + (NSString *)escapedPatternForString:(NSString *)string
	 * @Description : Returns a string by adding backslash escapes as necessary to protect any characters that would match as pattern
	 *              metacharacters.
	 * @param string The string.
	 * @return Return Value The escaped string.
	 * @Discussion Returns a string by adding backslash escapes as necessary to the given string, to escape any characters that would
	 *             otherwise be treated as pattern metacharacters. See â€œFlag Optionsâ€? for the format of template.
	 **/

	public static NSString escapedPatternForString(NSString string) {

		return new NSString(Pattern.quote(string.getWrappedString()));

	}

	/**
	 * @Signature: escapedTemplateForString:
	 * @Declaration : + (NSString *)escapedTemplateForString:(NSString *)string
	 * @Description : Returns a template string by adding backslash escapes as necessary to protect any characters that would match as
	 *              pattern metacharacters
	 * @param string The template string
	 * @return Return Value The escaped template string.
	 * @Discussion Returns a string by adding backslash escapes as necessary to the given string, to escape any characters that would
	 *             otherwise be treated as pattern metacharacters. See â€œFlag Optionsâ€? for the format of template.
	 **/

	public static NSString escapedTemplateForString(NSString string) {
		return new NSString(Pattern.quote(string.getWrappedString()));

	}

	/**
	 * @Signature: regularExpressionWithPattern:options:error:
	 * @Declaration : + (NSRegularExpression *)regularExpressionWithPattern:(NSString *)pattern options:(NSRegularExpressionOptions)options
	 *              error:(NSError **)error
	 * @Description : Creates an NSRegularExpression instance with the specified regular expression pattern and options.
	 * @param pattern The regular expression pattern to compile.
	 * @param options The matching options. See â€œNSRegularExpressionOptionsâ€? for possible values. The values can be combined using the
	 *            C-bitwise OR operator.
	 * @param error An out value that returns any error encountered during initialization. Returns an NSError object if the regular
	 *            expression pattern is invalid; otherwise returns nil.
	 * @return Return Value An instance of NSRegularExpression for the specified regular expression and options.
	 **/

	public static NSRegularExpression regularExpressionWithPatternOptionsError(NSString pattern,
																			   NSRegularExpressionOptions options, NSError[] error) {
		if(options != null && pattern != null)
			return new NSRegularExpression(pattern, options);
		else {
			LOGGER.info(String.valueOf(error));
			return null;
		}
	}

	/**
	 * @Signature: enumerateMatchesInString:options:range:usingBlock:
	 * @Declaration : - (void)enumerateMatchesInString:(NSString *)string options:(NSMatchingOptions)options range:(NSRange)range
	 *              usingBlock:(void (^)(NSTextCheckingResult *result, NSMatchingFlags flags, BOOL *stop))block
	 * @Description : Enumerates the string allowing the Block to handle each regular expression match.
	 * @param string The string.
	 * @param options The matching options to report. See â€œNSMatchingOptionsâ€? for the supported values.
	 * @param range The range of the string to test.
	 * @param block The Block enumerates the matches of the regular expression in the string.. The block takes three arguments: result An
	 *            NSTextCheckingResult specifying the match. This result gives the overall matched range via its range property, and the
	 *            range of each individual capture group via its rangeAtIndex: method. The range {NSNotFound, 0} is returned if one of the
	 *            capture groups did not participate in this particular match. flags The current state of the matching progress. See
	 *            â€œNSMatchingFlagsâ€? for the possible values. stop A reference to a Boolean value. The Block can set the value to YES to stop
	 *            further processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES
	 *            within the Block. The Block returns void.
	 * @param result An NSTextCheckingResult specifying the match. This result gives the overall matched range via its range property, and
	 *            the range of each individual capture group via its rangeAtIndex: method. The range {NSNotFound, 0} is returned if one of
	 *            the capture groups did not participate in this particular match.
	 * @param flags The current state of the matching progress. See â€œNSMatchingFlagsâ€? for the possible values.
	 * @param stop A reference to a Boolean value. The Block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion This method is the fundamental matching method for regular expressions and is suitable for overriding by subclassers.
	 *             There are additional convenience methods for returning all the matches as an array, the total number of matches, the
	 *             first match, and the range of the first match. By default, the Block iterator method calls the Block precisely once for
	 *             each match, with a non-nil result and the appropriate flags. The client may then stop the operation by setting the
	 *             contents of stop to YES. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *             the Block.
	 **/

	public void enumerateMatchesInStringOptionsRangeUsingBlock(NSString string,
															   NSMatchingOptions options, NSRange range,
															   PerformBlock.VoidBlockNSTextCheckingResultNSMatchingFlagsBOOL aBlock) {

		NSString substring = string.substringWithRange(range);
		Matcher m = this.aPattern.matcher(substring.getWrappedString());

		while (m.find()) {
			Boolean stop = false;
			// TODO NSMatchingFlags
			aBlock.perform(new NSTextCheckingResult(new NSRange(m.start(), m.end() - m.start())),
					null, stop);
			if (stop) {
				break;
			}
		}

	}

	/**
	 * @Signature: firstMatchInString:options:range:
	 * @Declaration : - (NSTextCheckingResult *)firstMatchInString:(NSString *)string options:(NSMatchingOptions)options
	 *              range:(NSRange)range
	 * @Description : Returns the first match of the regular expression within the specified range of the string.
	 * @param string The string to search.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @return Return Value An NSTextCheckingResult object. This result gives the overall matched range via its range property, and the
	 *         range of each individual capture group via its rangeAtIndex: method. The range {NSNotFound, 0} is returned if one of the
	 *         capture groups did not participate in this particular match.
	 * @Discussion This is a convenience method that calls enumerateMatchesInString:options:range:usingBlock:.
	 **/

	public NSTextCheckingResult firstMatchInStringOptionsRange(NSString string,
															   NSMatchingOptions options) {
		// TODO NSMatchingOptions options are ignored
		Matcher m = this.aPattern.matcher(string.getWrappedString());

		while (m.find()) {

			return new NSTextCheckingResult(new NSRange(m.start(), m.end() - m.start()));
		}
		return null;

	}

	/**
	 * @Signature: initWithPattern:options:error:
	 * @Declaration : - (id)initWithPattern:(NSString *)pattern options:(NSRegularExpressionOptions)options error:(NSError **)error
	 * @Description : Returns an initialized NSRegularExpression instance with the specified regular expression pattern and options.
	 * @param pattern The regular expression pattern to compile.
	 * @param options The regular expression options that are applied to the expression during matching. See â€œNSRegularExpressionOptionsâ€?
	 *            for possible values.
	 * @param error An out value that returns any error encountered during initialization. Returns an NSError object if the regular
	 *            expression pattern is invalid; otherwise returns nil.firstMatchInString:options:range
	 * @return Return Value An instance of NSRegularExpression for the specified regular expression and options.
	 **/

	public NSObject initWithPatternOptionsError(NSString pattern,
												NSRegularExpressionOptions options, NSError[] error) {
		if (Pattern.compile(pattern.getWrappedString()) != null) {
			return new NSRegularExpression(pattern, options);
		} else {
			error[0].setDomain(new NSString("nil.firstMatchInString:options:range"));
			return null;
		}

	}

	/**
	 * @Signature: matchesInString:options:range:
	 * @Declaration : - (NSArray *)matchesInString:(NSString *)string options:(NSMatchingOptions)options range:(NSRange)range
	 * @Description : Returns an array containing all the matches of the regular expression in the string.
	 * @param string The string to search.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @return Return Value An array of NSTextCheckingResult objects. Each result gives the overall matched range via its range property,
	 *         and the range of each individual capture group via its rangeAtIndex: method. The range {NSNotFound, 0} is returned if one of
	 *         the capture groups did not participate in this particular match.
	 * @Discussion This is a convenience method that calls enumerateMatchesInString:options:range:usingBlock: passing the appropriate
	 *             string, options, and range..
	 **/

	public NSArray<NSTextCheckingResult> matchesInStringOptionsRange(NSString string,
																	 NSMatchingOptions options, NSRange range) {
		// FIXME NSMatchingOptions options are ignored
		NSString substring = string.substringWithRange(range);
		Matcher m = this.aPattern.matcher(substring.getWrappedString());
		List<NSTextCheckingResult> anArray = new ArrayList<NSTextCheckingResult>();

		while (m.find()) {

			anArray.add(new NSTextCheckingResult(new NSRange(m.start(), m.end() - m.start())));
		}

		return new NSArray(anArray);

	}

	/**
	 * @Signature: numberOfMatchesInString:options:range:
	 * @Declaration : - (NSUInteger)numberOfMatchesInString:(NSString *)string options:(NSMatchingOptions)options range:(NSRange)range
	 * @Description : Returns the number of matches of the regular expression within the specified range of the string.
	 * @param string The string to search.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @return Return Value The number of matches of the regular expression.
	 * @Discussion This is a convenience method that calls enumerateMatchesInString:options:range:usingBlock:.
	 **/

	public int numberOfMatchesInStringOptionsRange(NSString string, NSMatchingOptions options,
												   NSRange range) {

		NSString substring = string.substringWithRange(range);
		Matcher m = this.aPattern.matcher(substring.getWrappedString());

		int count = 0;

		while (m.find()) {

			count++;
		}
		return count;

	}

	/**
	 * @Signature: rangeOfFirstMatchInString:options:range:
	 * @Declaration : - (NSRange)rangeOfFirstMatchInString:(NSString *)string options:(NSMatchingOptions)options range:(NSRange)range
	 * @Description : Returns the range of the first match of the regular expression within the specified range of the string.
	 * @param string The string to search.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @return Return Value The range of the first match. Returns {NSNotFound, 0} if no match is found.
	 * @Discussion This is a convenience method that calls enumerateMatchesInString:options:range:usingBlock:.
	 **/

	public NSRange rangeOfFirstMatchInStringOptionsRange(NSString string, NSMatchingOptions options,
														 NSRange range) {
		NSString substring = string.substringWithRange(range);
		Matcher m = this.aPattern.matcher(substring.getWrappedString());
		while (m.find()) {
			return new NSRange(m.start(), m.end() - m.start());
		}
		return new NSRange(NSObjCRuntime.NSNotFound, 0);
	}

	/**
	 * @Signature: replaceMatchesInString:options:range:withTemplate:
	 * @Declaration : - (NSUInteger)replaceMatchesInString:(NSMutableString *)string options:(NSMatchingOptions)options range:(NSRange)range
	 *              withTemplate:(NSString *)template
	 * @Description : Replaces regular expression matches within the mutable string using the template string.
	 * @param string The mutable string to search and replace values within.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @param template The substitution template used when replacing matching instances.
	 * @return Return Value The number of matches.
	 * @Discussion See â€œFlag Optionsâ€? for the format of template.
	 **/

	public int replaceMatchesInStringOptionsRangeWithTemplate(NSMutableString string,
															  NSMatchingOptions options, NSRange range, NSString template) {

		NSString substring = string.substringWithRange(range);
		Matcher m = this.aPattern.matcher(substring.getWrappedString());

		int count = 0;

		while (m.find()) {

			count++;
		}
		m.replaceAll(template.getWrappedString());
		return count;

	}

	// Replacing Strings Using Regular Expressions

	/**
	 * @Signature: replacementStringForResult:inString:offset:template:
	 * @Declaration : - (NSString *)replacementStringForResult:(NSTextCheckingResult *)result inString:(NSString *)string
	 *              offset:(NSInteger)offset template:(NSString *)template
	 * @Description : Used to perform template substitution for a single result for clients implementing their own replace functionality.
	 * @param result The result of the single match.
	 * @param string The string from which the result was matched.
	 * @param offset The offset to be added to the location of the result in the string.
	 * @param template See â€œFlag Optionsâ€? for the format of template.
	 * @return Return Value A replacement string.
	 * @Discussion For clients implementing their own replace functionality, this is a method to perform the template substitution for a
	 *             single result, given the string from which the result was matched, an offset to be added to the location of the result in
	 *             the string (for example, in cases that modifications to the string moved the result since it was matched), and a
	 *             replacement template. This is an advanced method that is used only if you wanted to iterate through a list of matches
	 *             yourself and do the template replacement for each one, plus maybe some other calculation that you want to do in code,
	 *             then you would use this at each step.
	 **/

	public NSString replacementStringForResultInStringOffsetTemplate(NSTextCheckingResult result,
																	 NSString string, int offset, NSString template) {
		NSRange aRange = result.range();
		int start = aRange.location + offset;
		int end = start + aRange.length;
		Matcher m = this.aPattern.matcher(string.getWrappedString());

		while (m.find()) {
			if (m.start() == start && m.end() == end) {
				String aString = m.group();
				aString = aString.replaceAll(aString, template.getWrappedString());
				return new NSString(aString);
			}
		}

		return new NSString("");

	}

	/**
	 * @Signature: stringByReplacingMatchesInString:options:range:withTemplate:
	 * @Declaration : - (NSString *)stringByReplacingMatchesInString:(NSString *)string options:(NSMatchingOptions)options
	 *              range:(NSRange)range withTemplate:(NSString *)template
	 * @Description : Returns a new string containing matching regular expressions replaced with the template string.
	 * @param string The string to search for values within.
	 * @param options The matching options to use. See â€œNSMatchingOptionsâ€? for possible values.
	 * @param range The range of the string to search.
	 * @param template The substitution template used when replacing matching instances.
	 * @return Return Value A string with matching regular expressions replaced by the template string.
	 * @Discussion See â€œFlag Optionsâ€? for the format of template.
	 **/

	public NSString stringByReplacingMatchesInStringOptionsRangeWithTemplate(NSString string,
																			 NSMatchingOptions options, NSRange range, NSString template) {
		int start = range.location;
		int end = start + range.length;
		Matcher m = this.aPattern.matcher(string.getWrappedString());
		m.region(start, end);
		String aString = string.getWrappedString();
		while (m.find()) {
			String myStr = m.group();
			aString = aString.replaceAll(myStr, template.getWrappedString());
		}
		return new NSString(aString);
	}

	public Pattern getaPattern() {
		return aPattern;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {

	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {

		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

}