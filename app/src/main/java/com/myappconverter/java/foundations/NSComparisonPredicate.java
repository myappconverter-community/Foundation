
package com.myappconverter.java.foundations;




public class NSComparisonPredicate extends NSPredicate {

	NSExpression _leftExpression;
	NSExpression _rightExpression;

	private int comparisonPredicateModifier;
	int _predicateOperatorType;
	int _options;
	private SEL _customSelector;

	public static final int NSDirectPredicateModifier = 0;
	public static final int NSAllPredicateModifier = 1;
	public static final int NSAnyPredicateModifier = 2;

	public static final int NSLessThanPredicateOperatorType = 0; //
	public static final int NSLessThanOrEqualToPredicateOperatorType = 1; //
	public static final int NSGreaterThanPredicateOperatorType = 2; //
	public static final int NSGreaterThanOrEqualToPredicateOperatorType = 3; //
	public static final int NSEqualToPredicateOperatorType = 4; //
	public static final int NSNotEqualToPredicateOperatorType = 5; //
	public static final int NSMatchesPredicateOperatorType = 6; //
	public static final int NSLikePredicateOperatorType = 7; //
	public static final int NSBeginsWithPredicateOperatorType = 8; //
	public static final int NSEndsWithPredicateOperatorType = 9; //
	public static final int NSInPredicateOperatorType = 10; //
	public static final int NSCustomSelectorPredicateOperatorType = 11;

	public static final int NSCaseInsensitivePredicateOption = 0x01;
	public static final int NSDiacriticInsensitivePredicateOption = 0x02;
	public static final int NSNormalizedPredicateOption = 0x04;
	public static final int NSLocaleSensitivePredicateOption = 0x08;

	// Creating Comparison Predicates
	/**
	 * @Signature: predicateWithLeftExpression:rightExpression:modifier:type:options:
	 * @Declaration : + (NSPredicate *)predicateWithLeftExpression:(NSExpression *)lhs rightExpression:(NSExpression *)rhs
	 *              modifier:(NSComparisonPredicateModifier)modifier type:(NSPredicateOperatorType)type
	 *              options:(NSComparisonPredicateOptions)options
	 * @Description : Creates and returns a predicate of a given type formed by combining given left and right expressions using a given
	 *              modifier and options.
	 * @param lhs The left hand expression.
	 * @param rhs The right hand expression.
	 * @param modifier The modifier to apply.
	 * @param type The predicate operator type.
	 * @param options The options to apply (see NSComparisonPredicate Options). For no options, pass 0.
	 * @return Return Value A new predicate of type type formed by combining the given left and right expressions using the modifier and
	 *         options.
	 **/
	
	static NSPredicate predicateWithLeftExpressionRightExpressionModifierTypeOptions(NSExpression left, NSExpression right, int modifier,
			int type, int options) {
		NSComparisonPredicate myPredicate = new NSComparisonPredicate();
		return myPredicate.initWithLeftExpressionRightExpressionModifierTypeOptions(left, right, modifier, type, options);
	}

	/**
	 * @Signature: predicateWithLeftExpression:rightExpression:customSelector:
	 * @Declaration : + (NSPredicate *)predicateWithLeftExpression:(NSExpression *)lhs rightExpression:(NSExpression *)rhs
	 *              customSelector:(SEL)selector
	 * @Description : Returns a new predicate formed by combining the left and right expressions using a given selector.
	 * @param lhs The left hand side expression.
	 * @param rhs The right hand side expression.
	 * @param selector The selector to use for comparison. The method defined by the selector must take a single argument and return a BOOL
	 *            value.
	 * @return Return Value A new predicate formed by combining the left and right expressions using selector.
	 **/
	
	static NSPredicate predicateWithLeftExpressionRightExpressionCustomSelector(NSExpression left, NSExpression right, SEL selector) {
		NSComparisonPredicate myPredicate = new NSComparisonPredicate();
		return myPredicate.initWithLeftExpressionRightExpressionCustomSelector(left, right, selector);
	}

	/**
	 * @Signature: initWithLeftExpression:rightExpression:modifier:type:options:
	 * @Declaration : - (id)initWithLeftExpression:(NSExpression *)lhs rightExpression:(NSExpression *)rhs
	 *              modifier:(NSComparisonPredicateModifier)modifier type:(NSPredicateOperatorType)type
	 *              options:(NSComparisonPredicateOptions)options
	 * @Description : Initializes a predicate to a given type formed by combining given left and right expressions using a given modifier
	 *              and options.
	 * @param lhs The left hand expression.
	 * @param rhs The right hand expression.
	 * @param modifier The modifier to apply.
	 * @param type The predicate operator type.
	 * @param options The options to apply (see NSComparisonPredicate Options). For no options, pass 0.
	 * @return Return Value The receiver, initialized to a predicate of type type formed by combining the left and right expressions using
	 *         the modifier and options.
	 **/
	
	public <T extends NSPredicate> T initWithLeftExpressionRightExpressionModifierTypeOptions(NSExpression left, NSExpression right,
			int modifier, int type, int options) {
		_leftExpression = left;
		_rightExpression = right;
		comparisonPredicateModifier = modifier;
		_predicateOperatorType = type;
		_options = options;
		_customSelector = null;
		return (T) this;
	}

	/**
	 * @Signature: initWithLeftExpression:rightExpression:customSelector:
	 * @Declaration : - (id)initWithLeftExpression:(NSExpression *)lhs rightExpression:(NSExpression *)rhs customSelector:(SEL)selector
	 * @Description : Initializes a predicate formed by combining given left and right expressions using a given selector.
	 * @param lhs The left hand expression.
	 * @param rhs The right hand expression.
	 * @param selector The selector to use. The method defined by the selector must take a single argument and return a BOOL value.
	 * @return Return Value The receiver, initialized by combining the left and right expressions using selector.
	 **/
	
	public <T extends NSPredicate> T initWithLeftExpressionRightExpressionCustomSelector(NSExpression left, NSExpression right, SEL selector) {
		_leftExpression = left;
		_rightExpression = right;
		comparisonPredicateModifier = NSDirectPredicateModifier;
		_predicateOperatorType = NSCustomSelectorPredicateOperatorType;
		_options = NSCaseInsensitivePredicateOption;
		_customSelector = selector;
		return (T) this;
	}

	// Getting Information About a Comparison Predicate
	/**
	 * @Signature: comparisonPredicateModifier
	 * @Declaration : - (NSComparisonPredicateModifier)comparisonPredicateModifier
	 * @Description : Returns the comparison predicate modifier for the receiver.
	 * @return Return Value The comparison predicate modifier for the receiver.
	 * @Discussion The default value is NSDirectPredicateModifier.
	 **/
	
	public int comparisonPredicateModifier() {
		return comparisonPredicateModifier;
	}

	public int getComparisonPredicateModifier() {
		return comparisonPredicateModifier;

	}

	/**
	 * @Signature: customSelector
	 * @Declaration : - (SEL)customSelector
	 * @Description : Returns the selector for the receiver.
	 * @return Return Value The selector for the receiver, or NULL if there is none.
	 **/
	
	public SEL customSelector() {
		return _customSelector;
	}

	public SEL getCustomSelector() {
		return _customSelector;
	}

	/**
	 * @Signature: leftExpression
	 * @Declaration : - (NSExpression *)leftExpression
	 * @Description : Returns the left expression for the receiver.
	 * @return Return Value The left expression for the receiver, or nil if there is none.
	 **/
	public NSExpression leftExpression() {
		return _leftExpression;
	}

	public NSExpression getLeftExpression() {
		return _leftExpression;
	}

	/**
	 * @Signature: options
	 * @Declaration : - (NSComparisonPredicateOptions)options
	 * @Description : Returns the options that are set for the receiver.
	 * @return Return Value The options that are set for the receiver.
	 **/
	
	public int options() {
		return _options;
	}

	public int getOptions() {
		return _options;
	}

	/**
	 * @Signature: predicateOperatorType
	 * @Declaration : - (NSPredicateOperatorType)predicateOperatorType
	 * @Description : Returns the predicate type for the receiver.
	 * @return Return Value The predicate type for the receiver.
	 **/
	
	public int predicateOperatorType() {
		return _predicateOperatorType;
	}

	public int getPredicateOperatorType() {
		return _predicateOperatorType;
	}
	/**
	 * @Signature: rightExpression
	 * @Declaration : - (NSExpression *)rightExpression
	 * @Description : Returns the right expression for the receiver.
	 * @return Return Value The right expression for the receiver, or nil if there is none.
	 **/
	
	public NSExpression rightExpression() {
		return _rightExpression;
	}

	public NSExpression getRightExpression() {
		return _rightExpression;
	}
	/*** Added ***/

	
	@Override
	public Object copyWithZone(NSZone zone) {
		return this;
	}

	
	@Override
	public NSString predicateFormat() {
		StringBuilder result = new StringBuilder();
		String operator = "";
		String options = "";

		switch (comparisonPredicateModifier) {
		case NSDirectPredicateModifier:
			break;

		case NSAllPredicateModifier:
			result.append("ALL ");
			break;

		case NSAnyPredicateModifier:
			result.append("ANY ");
			break;
		default:
			break;
		}

		switch (_predicateOperatorType) {

		case NSLessThanPredicateOperatorType:
			operator = "<";
			break;

		case NSLessThanOrEqualToPredicateOperatorType:
			operator = "<=";
			break;

		case NSGreaterThanPredicateOperatorType:
			operator = ">";
			break;

		case NSGreaterThanOrEqualToPredicateOperatorType:
			operator = ">=";
			break;

		case NSEqualToPredicateOperatorType:
			operator = "==";
			break;

		case NSNotEqualToPredicateOperatorType:
			operator = "!=";
			break;

		case NSMatchesPredicateOperatorType:
			operator = "MATCHES";
			break;

		case NSLikePredicateOperatorType:
			operator = "LIKE";
			break;

		case NSBeginsWithPredicateOperatorType:
			operator = "BEGINSWITH";
			break;

		case NSEndsWithPredicateOperatorType:
			operator = "ENDSWITH";
			break;

		case NSInPredicateOperatorType:
			operator = "IN";
			break;

		case NSCustomSelectorPredicateOperatorType:
			operator = "@selector(" + _customSelector.getMethodName() + ")";
			break;

        default:
            break;
		}
		result = result.append(_leftExpression).append(" ").append(operator).append(options).append(" ").append(_rightExpression);

		return new NSString(result.toString());
	}

	
	@Override
	public NSPredicate predicateWithSubstitutionVariables(NSDictionary variables) {
		return null;
	}

	public boolean _evaluateValueWithObject(Object leftResult, Object object) {
		return false;

	}

	
	@Override
	public boolean evaluateWithObject(Object object) {

		return false;
	}

	boolean function(Object leftResult, SEL _customSelector, Object rightResult) {
		return false;
	}

	
	@Override
	public void encodeWithCoder(NSCoder coder) {
        //
	}


}