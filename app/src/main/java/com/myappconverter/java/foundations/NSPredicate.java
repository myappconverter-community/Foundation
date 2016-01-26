
package com.myappconverter.java.foundations;

import java.util.regex.Pattern;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.PerformBlock;


public class NSPredicate extends NSObject implements NSCopying, NSSecureCoding {

	private String expression;
	private Pattern pattern;

	/**
	 * @Signature: predicateWithBlock:
	 * @Declaration : + (NSPredicate *)predicateWithBlock:(BOOL (^)(id evaluatedObject, NSDictionary *bindings))block
	 * @Description : Creates and returns a predicate that evaluates using a specified block object and bindings dictionary.
	 * @param block The block is applied to the object to be evaluated. The block takes two arguments: evaluatedObject The object to be
	 *            evaluated. bindings The substitution variables dictionary. The dictionary must contain key-value pairs for all variables
	 *            in the receiver. The block returns YES if the evaluatedObject evaluates to true, otherwise NO.
	 * @param evaluatedObject The object to be evaluated.
	 * @param bindings The substitution variables dictionary. The dictionary must contain key-value pairs for all variables in the receiver.
	 * @return Return Value A new predicate by that evaluates objects using block.
	 **/

	public static NSPredicate predicateWithBlock(PerformBlock.BOOLBlockIdNSDictionary block) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: predicateWithFormat:
	 * @Declaration : + (NSPredicate *)predicateWithFormat:(NSString *)format,, ...
	 * @Description : Creates and returns a new predicate formed by creating a new string with a given format and parsing the result.
	 * @param format The format string for the new predicate.
	 * @param ... A comma-separated list of arguments to substitute into format.
	 * @return Return Value A new predicate formed by creating a new string with format and parsing the result.
	 * @Discussion For details of the format of the format string and of limitations on variable substitution, see eeœPredicate Format
	 *             String Syntaxee .
	 **/

	public static NSPredicate predicateWithFormat(NSString format, Object... objs) {
		NSPredicate predicate = null;
		if ( ("SELF contains[cd] %@".equals(format.getWrappedString())
				|| "SELF MATCHES %@".equals(format.getWrappedString()) && objs[0] != null) &&
				objs[0] instanceof NSString) {
			// search pattern in given text
			String text = ((NSString) objs[0]).getWrappedString();
			predicate = new NSPredicate();
			predicate.expression = text;
			return predicate;
		}
		return predicate;
	}

	/**
	 * @Signature: predicateWithFormat:argumentArray:
	 * @Declaration : + (NSPredicate *)predicateWithFormat:(NSString *)predicateFormat argumentArray:(NSArray *)arguments
	 * @Description : Creates and returns a new predicate by substituting the values in a given array into a format string and parsing the
	 *              result.
	 * @param predicateFormat The format string for the new predicate.
	 * @param arguments The arguments to substitute into predicateFormat. Values are substituted into predicateFormat in the order they
	 *            appear in the array.
	 * @return Return Value A new predicate by substituting the values in arguments into predicateFormat, and parsing the result.
	 * @Discussion For details of the format of the format string and of limitations on variable substitution, see eeœPredicate Format
	 *             String Syntaxee .
	 **/

	public static NSPredicate predicateWithFormatArgumentArray(NSString predicateFormat,
															   NSArray<NSString> arguments) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: predicateWithFormat:arguments:
	 * @Declaration : + (NSPredicate *)predicateWithFormat:(NSString *)format arguments:(va_list)argList
	 * @Description : Creates and returns a new predicate by substituting the values in an argument list into a format string and parsing
	 *              the result.
	 * @param format The format string for the new predicate.
	 * @param argList The arguments to substitute into predicateFormat. Values are substituted into predicateFormat in the order they appear
	 *            in the argument list.
	 * @return Return Value A new predicate by substituting the values in argList into predicateFormat and parsing the result.
	 * @Discussion For details of the format of the format string and of limitations on variable substitution, see eeœPredicate Format
	 *             String Syntaxee .
	 **/

	public static NSPredicate predicateWithFormatArguments(NSString format, byte[] argList) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: predicateWithValue:
	 * @Declaration : + (NSPredicate *)predicateWithValue:(BOOL)value
	 * @Description : Creates and returns a predicate that always evaluates to a given value.
	 * @param value The value to which the new predicate should evaluate.
	 * @return Return Value A predicate that always evaluates to value.
	 **/

	public static NSPredicate predicateWithValue(boolean value) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: allowEvaluation
	 * @Declaration : - (void)allowEvaluation
	 * @Description : Force a predicate that was securely decoded to allow evaluation.
	 * @Discussion When securely decoding an NSPredicate object encoded using NSSecureCoding, evaluation is disabled because it is
	 *             potentially unsafe to evaluate predicates you get out of an archive. Before you enable evaluation, you should validate
	 *             key paths, selectors, etc to ensure no erroneous or malicious code will be executed. Once youee ve preflighted the
	 *             predicate, you can enable the receiver for evaluation by calling allowEvaluation.
	 **/

	public void allowEvaluation() {
		// not yet covered
	}

	/**
	 * @Signature: evaluateWithObject:
	 * @Declaration : - (BOOL)evaluateWithObject:(id)object
	 * @Description : Returns a Boolean value that indicates whether a given object matches the conditions specified by the receiver.
	 * @param object The object against which to evaluate the receiver.
	 * @return Return Value YES if object matches the conditions specified by the receiver, otherwise NO.
	 **/

	public boolean evaluateWithObject(Object object) {
		if (this.expression != null && object instanceof NSString) {
			NSString txt = (NSString) object;
			return Pattern.matches(expression, txt.getWrappedString());
		}
		return false;
	}

	/**
	 * @Signature: evaluateWithObject:substitutionVariables:
	 * @Declaration : - (BOOL)evaluateWithObject:(id)object substitutionVariables:(NSDictionary *)variables
	 * @Description : Returns a Boolean value that indicates whether a given object matches the conditions specified by the receiver after
	 *              substituting in the values in a given variables dictionary.
	 * @param object The object against which to evaluate the receiver.
	 * @param variables The substitution variables dictionary. The dictionary must contain key-value pairs for all variables in the
	 *            receiver.
	 * @return Return Value YES if object matches the conditions specified by the receiver after substituting in the values in variables for
	 *         any replacement tokens, otherwise NO.
	 * @Discussion This method returns the same result as the two step process of first invoking predicateWithSubstitutionVariables: on the
	 *             receiver and then invoking evaluateWithObject: on the returned predicate. This method is optimized for situations which
	 *             require repeatedly evaluating a predicate with substitution variables with different variable substitutions.
	 **/

	public boolean evaluateWithObjectSubstitutionVariables(NSObject object,
														   NSDictionary variables) {
		// not yet covered
		return false;
	}

	/**
	 * @Signature: predicateFormat
	 * @Declaration : - (NSString *)predicateFormat
	 * @Description : Returns the receiveree s format string.
	 * @return Return Value The receiver s format string.
	 **/

	public NSString predicateFormat() {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: predicateWithSubstitutionVariables:
	 * @Declaration : - (NSPredicate *)predicateWithSubstitutionVariables:(NSDictionary *)variables
	 * @Description : Returns a copy of the receiver with the receiveree s variables substituted by values specified in a given substitution
	 *              variables dictionary.
	 * @param variables The substitution variables dictionary. The dictionary must contain key-value pairs for all variables in the
	 *            receiver.
	 * @return Return Value A copy of the receiver with the receiveree s variables substituted by values specified in variables.
	 * @Discussion The receiver itself is not modified by this method, so you can reuse it for any number of substitutions.
	 **/

	public NSPredicate predicateWithSubstitutionVariables(NSDictionary variables) {
		// not yet covered
		return null;
	}

	@Override
	public boolean supportsSecureCoding() {
		// not yet covered
		return false;
	}

	@Override
	public Object copyWithZone(NSZone pZone) {
		// not yet covered
		return null;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}