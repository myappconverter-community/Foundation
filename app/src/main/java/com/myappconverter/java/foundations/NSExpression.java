
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.SetsUtils;
import com.myappconverter.mapping.utils.PerformBlock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class NSExpression extends NSObject implements NSCopying, NSSecureCoding {

    private NSObject mExpressionFormat;
    private NSArray<NSExpression> mArguments;
    private Object mCollection;
    private NSExpression mRightExpression;
    private NSExpression mLeftExpression;
    private Object mConstant;
    private NSPredicate mPredicate;
    private NSExpression mOperand;
    private NSString mFunction;
    private NSString mPath;
    private NSString mVariable;
    private NSExpressionType mType;

    private static NSString mExpressionForEvaluatedObject;

    public interface NSExpressionBlock {

        // expressionBlock
        NSObject performAction(NSObject evaluatedObject, NSArray<?> expressions, NSMutableDictionary<?, ?> context);

    }

    
    public static enum NSExpressionType {
        NSConstantValueExpressionType(0), //
        NSEvaluatedObjectExpressionType(0), //
        NSVariableExpressionType(0), //
        NSKeyPathExpressionType(0), //
        NSFunctionExpressionType(0), //
        NSUnionSetExpressionType(0), //
        NSIntersectSetExpressionType(0), //
        NSMinusSetExpressionType(0), //
        NSSubqueryExpressionType(13), //
        NSAggregateExpressionType(14), //
        NSAnyKeyExpressionType(15), //
        NSBlockExpressionType(19);

        int value;

        private NSExpressionType(int v) {
            value = v;
        }

        public int getValue() {
            return value;
        }
    }

    // Initializing an Expression

    public NSExpression() {
        super();
    }

    /**
     * @param type The type of the new expression, as defined by NSExpressionType.
     * @return Return Value An initialized NSExpression object of the type type.
     * @Signature: initWithExpressionType:
     * @Declaration : - (id)initWithExpressionType:(NSExpressionType)type
     * @Description : Initializes the receiver with the specified expression type.
     **/
    
    public Object initWithExpressionType(NSExpressionType type) {
        NSExpression exp = new NSExpression();
        exp.mType = type;
        return exp;
    }

    /**
     * @param expressionFormat, The expression format.
     * @param ...               A comma-separated list of arguments to substitute into format. The
     *                          list is terminated by nil.
     * @return Return Value An initialized NSExpression object with the specified format.
     * @Signature: expressionWithFormat:
     * @Declaration : + (NSExpression *)expressionWithFormat:(NSString *)expressionFormat,, ...
     * @Description : Initializes the receiver with the specified expression arguments.
     **/
    
    public static NSExpression expressionWithFormat(NSString expressionFormat, Object... args) {
        NSExpression exp = new NSExpression();
        NSExpression.mExpressionForEvaluatedObject = new NSString(String.format(expressionFormat.getWrappedString(), args));
        return exp;
    }

    /**
     * @param expressionFormat The expression format.
     * @param arguments        An array of arguments to be used with the expressionFormat string.
     * @return Return Value An initialized NSExpression object with the specified arguments.
     * @Signature: expressionWithFormat:argumentArray:
     * @Declaration : + (NSExpression *)expressionWithFormat:(NSString *)expressionFormat
     * argumentArray:(NSArray *)arguments
     * @Description : Initializes the receiver with the specified expression format and array of
     * arguments.
     **/
    
    public static NSExpression expressionWithFormatArgumentArray(NSString format, NSArray<NSObject> arguments) {
        NSExpression exp = new NSExpression();
        NSExpression.mExpressionForEvaluatedObject = (new NSString(String.format(format.getWrappedString(), arguments.getWrappedList())));
        return exp;
    }

    /**
     * @param expressionFormat The expression format.
     * @param argList          A list of arguments to be inserted into the expressionFormat string.
     *                         The argument list is terminated by nil.
     * @return Return Value An initialized NSExpression object with the specified arguments.
     * @Signature: expressionWithFormat:arguments:
     * @Declaration : + (NSExpression *)expressionWithFormat:(NSString *)expressionFormat
     * arguments:(va_list)argList
     * @Description : Initializes the receiver with the specified expression format and arguments
     * list.
     **/
    
    public static NSExpression expressionWithFormatArguments(NSString format, Object... args) {
        NSExpression exp = new NSExpression();
        NSExpression.mExpressionForEvaluatedObject = (new NSString(String.format(format.getWrappedString(), args)));
        // FIXME does this work?
        for (Object obj : args) {
            exp.mArguments.getWrappedList().add(expressionWithFormat(format));
        }
        return exp;
    }

    // Creating an Expression for a Value

    /**
     * @param obj The constant value the new expression is to represent.
     * @return Return Value A new expression that represents the constant value, obj.
     * @Signature: expressionForConstantValue:
     * @Declaration : + (NSExpression *)expressionForConstantValue:(id)obj
     * @Description : Returns a new expression that represents a given constant value.
     **/
    
    public static NSExpression expressionForConstantValue(Object obj) {
        NSExpression exp = new NSExpression();
        exp.mType = (NSExpressionType.NSConstantValueExpressionType);
        exp.mConstant = obj;
        return exp;
    }

    /**
     * @return Return Value A new expression that represents the object being evaluated.
     * @Signature: expressionForEvaluatedObject
     * @Declaration : + (NSExpression *)expressionForEvaluatedObject
     * @Description : Returns a new expression that represents the object being evaluated.
     **/
    
    public static NSExpression expressionForEvaluatedObject() {
        return null;
    }

    /**
     * @param keyPath The key path that the new expression should evaluate.
     * @return Return Value A new expression that invokes valueForKeyPath: with keyPath.
     * @Signature: expressionForKeyPath:
     * @Declaration : + (NSExpression *)expressionForKeyPath:(NSString *)keyPath
     * @Description : Returns a new expression that invokes valueForKeyPath: with a given key path.
     **/
    
    public static NSExpression expressionForKeyPath(NSString keyPath) {
        NSExpression exp = (NSExpression) new NSExpression().initWithExpressionType(NSExpressionType.NSKeyPathExpressionType);
        return exp;
    }

    /**
     * @param string The key for the variable to extract from the variable bindings dictionary.
     * @return Return Value A new expression that extracts from the variable bindings dictionary the
     * value for the key string.
     * @Signature: expressionForVariable:
     * @Declaration : + (NSExpression *)expressionForVariable:(NSString *)string
     * @Description : Returns a new expression that extracts a value from the variable bindings
     * dictionary for a given key.
     **/
    
    public static NSExpression expressionForVariable(NSString string) {
        NSExpression exp = (NSExpression) new NSExpression().initWithExpressionType(NSExpressionType.NSVariableExpressionType);
        exp.mVariable = string;
        return exp;
    }

    /**
     * @return Return Value A new expression that represents any key for a Spotlight query.
     * @Signature: expressionForAnyKey
     * @Declaration : + (NSExpression *)expressionForAnyKey
     * @Description : Returns a new expression that represents any key for a Spotlight query.
     **/
    
    public static NSExpression expressionForAnyKey() {
        NSExpression exp = (NSExpression) new NSExpression().initWithExpressionType(NSExpressionType.NSKeyPathExpressionType);
        return expressionForKeyPath(exp.mPath);
    }

    // Creating a Collection Expression

    /**
     * @param collection A collection object (an instance of NSArray, NSSet, or NSDictionary) that
     *                   contains further expressions.
     * @return Return Value A new expression that contains the expressions in collection.
     * @Signature: expressionForAggregate:
     * @Declaration : + (NSExpression *)expressionForAggregate:(NSArray *)collection
     * @Description : Returns a new aggregate expression for a given collection.
     **/
    
    public static NSExpression expressionForAggregate(Object collection) {

        NSExpression exp = (NSExpression) new NSExpression().initWithExpressionType(NSExpressionType.NSAggregateExpressionType);

        if (exp == null)
            return null;

        if (collection instanceof NSArray || collection instanceof NSSet || collection instanceof NSDictionary) {
            exp.mCollection = collection;
        } else {
            exp = null;
        }
        return exp;
    }

    /**
     * @param left  An expression that evaluates to an NSSet object.
     * @param right An expression that evaluates to a collection object (an instance of NSArray,
     *              NSSet, or NSDictionary).
     * @return Return Value An new NSExpression object that represents the union of left and right.
     * @Signature: expressionForUnionSet:with:
     * @Declaration : + (NSExpression *)expressionForUnionSet:(NSExpression *)left
     * with:(NSExpression *)right
     * @Description : Returns a new NSExpression object that represent the union of a given set and
     * collection.
     **/
    
    public static NSExpression expressionForUnionSetWith(NSExpression left, NSExpression right) {
        NSExpression exp = new NSExpression();
        Set<NSExpression> s1 = new HashSet<NSExpression>();
        s1.add(right);
        Set<NSExpression> s2 = new HashSet<NSExpression>();
        s1.add(left);
        Set<NSExpression> union = SetsUtils.union(s1, s2);
        NSExpression.mExpressionForEvaluatedObject = new NSString(union.toString());
        return exp;

    }

    /**
     * @param left  An expression that evaluates to an NSSet object.
     * @param right An expression that evaluates to a collection object (an instance of NSArray,
     *              NSSet, or NSDictionary).
     * @return Return Value A new NSExpression object that represents the intersection of left and
     * right.
     * @Signature: expressionForIntersectSet:with:
     * @Declaration : + (NSExpression *)expressionForIntersectSet:(NSExpression *)left
     * with:(NSExpression *)right
     * @Description : Returns a new NSExpression object that represent the intersection of a given
     * set and collection.
     **/
    
    public static NSExpression expressionForIntersectSetWith(NSExpression left, NSExpression right) {
        Set<NSExpression> s1 = new HashSet<NSExpression>();
        s1.add(right);
        Set<NSExpression> s2 = new HashSet<NSExpression>();
        s1.add(left);
        Set<NSExpression> intersection = SetsUtils.intersection(s1, s2);
        return NSExpression.expressionWithFormat(new NSString(intersection.toString()));
    }

    /**
     * @param left  An expression that evaluates to an NSSet object.
     * @param right An expression that evaluates to a collection object (an instance of NSArray,
     *              NSSet, or NSDictionary).
     * @return Return Value A new NSExpression object that represents the subtraction of right from
     * left.
     * @Signature: expressionForMinusSet:with:
     * @Declaration : + (NSExpression *)expressionForMinusSet:(NSExpression *)left
     * with:(NSExpression *)right
     * @Description : Returns a new NSExpression object that represent the subtraction of a given
     * collection from a given set.
     **/
    
    public static NSExpression expressionForMinusSetWith(NSExpression left, NSExpression right) {
        Set<NSExpression> s1 = new HashSet<NSExpression>();
        s1.add(right);
        Set<NSExpression> s2 = new HashSet<NSExpression>();
        s1.add(left);
        Set<NSExpression> minus = new HashSet<NSExpression>(s1);
        Set<NSExpression> tmp = new HashSet<NSExpression>(s2);
        minus.removeAll(tmp);
        return NSExpression.expressionWithFormat(new NSString(tmp.toString()));
    }

    // Creating a Subquery

    /**
     * @param expression A predicate expression that evaluates to a collection.
     * @param variable   Used as a local variable, and will shadow any instances of variable in the
     *                   bindings dictionary. The variable is
     *                   removed or the old value replaced once evaluation completes.
     * @param predicate  The predicate used to determine whether the element belongs in the result
     *                   collection.
     * @return Return Value An expression that filters a collection by storing elements in the
     * collection in the variable variable and
     * keeping the elements for which qualifier returns true
     * @Signature: expressionForSubquery:usingIteratorVariable:predicate:
     * @Declaration : + (NSExpression *)expressionForSubquery:(NSExpression *)expression
     * usingIteratorVariable:(NSString *)variable
     * predicate:(id)predicate
     * @Description : Returns an expression that filters a collection by storing elements in the
     * collection in a given variable and keeping
     * the elements for which qualifier returns true.
     * @Discussion This method creates a sub-expression, evaluation of which returns a subset of a
     * collection of objects. It allows you to
     * create sophisticated queries across relationships, such as a search for multiple correlated
     * values on the destination
     * object of a relationship.
     **/
    
    public static NSExpression expressionForSubqueryUsingIteratorVariablePredicate(NSExpression expression, NSString variable,
                                                                                   NSObject predicate) {

        // TODO INCOMPLETE

        NSMutableArray<NSExpression> array = new NSMutableArray<NSExpression>();
        NSExpression exp = expressionForFunctionArguments(variable, array);

        for (NSExpression e : exp.mArguments.getWrappedList()) {
            if (predicate == exp.expressionValueWithObjectContext(e, null)) {
                array.addObject(e);
            }
        }

        if (array.getWrappedList().iterator().hasNext())
            exp = array.getWrappedList().iterator().next();

        return exp;
    }

    // Creating an Expression Using Blocks

    /**
     * @param block           The Block is applied to the object to be evaluated. The Block takes
     *                        three arguments and returns a value: evaluatedObject
     *                        The object to be evaluated. expressions An array of predicate
     *                        expressions that evaluates to a collection. context A
     *                        dictionary that the expression can use to store temporary state for
     *                        one predicate evaluation. Note that context is
     *                        mutable, and that it can only be accessed during the evaluation of the
     *                        expression. You must not attempt to retain it for
     *                        use elsewhere. ] The Block returns the evaluatedObject.
     * @param evaluatedObject The object to be evaluated.
     * @param expressions     An array of predicate expressions that evaluates to a collection.
     * @param context         A dictionary that the expression can use to store temporary state for
     *                        one predicate evaluation. Note that context is
     *                        mutable, and that it can only be accessed during the evaluation of the
     *                        expression. You must not attempt to retain it for
     *                        use elsewhere. ]
     * @param arguments       An array containing NSExpression objects that will be used as
     *                        parameters during the invocation of selector. For a
     *                        selector taking no parameters, the array should be empty. For a
     *                        selector taking one or more parameters, the array should
     *                        contain one NSExpression object which will evaluate to an instance of
     *                        the appropriate type for each parameter. If there is
     *                        a mismatch between the number of parameters expected and the number
     *                        you provide during evaluation, an exception may be
     *                        raised or missing parameters may simply be replaced by nil (which
     *                        occurs depends on how many parameters are provided, and
     *                        whether you have over- or underflow). See expressionForFunction:arguments:
     *                        for a complete list of arguments.
     * @return Return Value An expression that filters a collection using the specified Block.
     * @Signature: expressionForBlock:arguments:
     * @Declaration : + (NSExpression *)expressionForBlock:(id (^)(id evaluatedObject, NSArray
     * *expressions, NSMutableDictionary
     * *context))block arguments:(NSArray *)arguments
     * @Description : Creates an NSExpression object that will use the Block for evaluating
     * objects.
     **/
    
    public static NSExpression expressionForBlockArguments(PerformBlock.IdBlockIdNSArrayNSMutableDictionary expressionBlock, NSArray<NSExpression> arguments) {

        NSExpression exp = (NSExpression) new NSExpression().initWithExpressionType(NSExpressionType.NSBlockExpressionType);
        // FIXME
        //
        //
        // exp = (NSExpression) expressionBlock.performAction(eval, exp, arguments);

        return exp;
    }

    // Creating an Expression for a Function

    /**
     * @param name       The name of the function to invoke.
     * @param parameters An array containing NSExpression objects that will be used as parameters
     *                   during the invocation of selector. For a
     *                   selector taking no parameters, the array should be empty. For a selector
     *                   taking one or more parameters, the array should
     *                   contain one NSExpression object which will evaluate to an instance of the
     *                   appropriate type for each parameter. If there is
     *                   a mismatch between the number of parameters expected and the number you
     *                   provide during evaluation, an exception may be
     *                   raised or missing parameters may simply be replaced by nil (which occurs
     *                   depends on how many parameters are provided, and
     *                   whether you have over- or underflow).
     * @return Return Value A new expression that invokes the function name using the parameters in
     * parameters.
     * @Signature: expressionForFunction:arguments:
     * @Declaration : + (NSExpression *)expressionForFunction:(NSString *)name arguments:(NSArray
     * *)parameters
     * @Description : Returns a new expression that will invoke one of the predefined functions.
     **/
    
    public static NSExpression expressionForFunctionArguments(NSString name, NSArray parameters) {
        List<Double> params = new ArrayList<Double>();
        params = parameters.getWrappedList();
        NSExpression exp = new NSExpression();
        double sum = 0, avg = 0, mul = 1;
        for (Double i : params) {
            sum += i;
            mul *= i;
        }
        avg = sum / params.size();
        if ("average:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(avg)));
        else if ("sum:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(sum)));
        else if ("min:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Collections.min(params))));
        else if ("max:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Collections.max(params))));
        else if ("median:".equals(name.getWrappedString())) {
            Collections.sort(params);
            if (params.size() % 2 == 1)
                NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(params.get((params.size() + 1) / 2 - 1))));
            else {
                double l = params.get(params.size() / 2 - 1);
                double u = params.get(params.size() / 2);
                NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf((l + u) / 2)));
            }
        } else if ("count:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(params.size())));
        else if ("now".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(Calendar.getInstance().getTime().toString()));
        else if ("multiply:by:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(mul)));
        else if ("sqrt:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Math.sqrt(params.get(0)))));
        else if ("log:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Math.log(params.get(0)))));
        else if ("ln:".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Math.log(params.get(0)) / Math.log(10))));
        else if ("raise:toPower".equals(name.getWrappedString()))
            NSExpression.mExpressionForEvaluatedObject = (new NSString(String.valueOf(Math.pow(params.get(0), params.get(1)))));

        exp.mType = NSExpressionType.NSFunctionExpressionType;
        exp.mFunction = name;
        return exp;
    }

    /**
     * @param target     An NSExpression object which will evaluate an object on which the selector
     *                   identified by name may be invoked.
     * @param name       The name of the method to be invoked.
     * @param parameters An array containing NSExpression objects which can be evaluated to provide
     *                   parameters for the method specified by
     *                   name.
     * @return Return Value An expression which will return the result of invoking the selector
     * named name on the result of evaluating the
     * target expression with the parameters specified by evaluating the elements of parameters.
     * @Signature: expressionForFunction:selectorName:arguments:
     * @Declaration : + (NSExpression *)expressionForFunction:(NSExpression *)target
     * selectorName:(NSString *)name arguments:(NSArray
     * *)parameters
     * @Description : Returns an expression which will return the result of invoking on a given
     * target a selector with a given name using
     * given arguments.
     * @Discussion See the description of expressionForFunction:arguments: for examples of how to
     * construct the parameter array.
     **/
    
    public NSExpression expressionForFunctionSelectorNameArguments(NSExpression target, NSString name, NSArray parameters) {
        // TODO INCOMPLETE
        NSExpression exp = null;
        exp.mArguments = parameters;
        exp.mFunction = name;
        exp = (NSExpression) this.expressionValueWithObjectContext(target, null);

        return exp;
    }

    // Getting Information About an Expression

    /**
     * @return Return Value The arguments for the receiver—that is, the array of expressions that
     * will be passed as parameters during
     * invocation of the selector on the operand of a function expression.
     * @Signature: arguments
     * @Declaration : - (NSArray *)arguments
     * @Description : Returns the arguments for the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSArray<NSExpression> arguments() {
        return this.mArguments;
    }

    public NSArray<NSExpression> getArguments() {
        return arguments();
    }

    /**
     * @return Return Value Returns the collection of expressions in an aggregate expression, or the
     * collection element of a subquery
     * expression.
     * @Signature: collection
     * @Declaration : - (id)collection
     * @Description : Returns the collection of expressions in an aggregate expression, or the
     * collection element of a subquery expression.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public Object collection() {
        return mCollection;
    }

    public Object getCollection() {
        return collection();
    }

    /**
     * @return Return Value The constant value of the receiver.
     * @Signature: constantValue
     * @Declaration : - (id)constantValue
     * @Description : Returns the constant value of the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public Object constantValue() {
        return this.mConstant;
    }

    public Object getConstantValue() {
        return constantValue();
    }

    /**
     * @return Return Value The expression type for the receiver.
     * @Signature: expressionType
     * @Declaration : - (NSExpressionType)expressionType
     * @Description : Returns the expression type for the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSExpressionType expressionType() {
        return mType;
    }

    /**
     * @return Return Value The function for the receiver.
     * @Signature: function
     * @Declaration : - (NSString *)function
     * @Description : Returns the function for the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSString function() {
        return mFunction;
    }

    /**
     * @return Return Value The key path for the receiver.
     * @Signature: keyPath
     * @Declaration : - (NSString *)keyPath
     * @Description : Returns the key path for the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSString keyPath() {
        return mPath;
    }

    /**
     * @return Return Value The left expression of a set expression.
     * @Signature: leftExpression
     * @Declaration : - (NSExpression *)leftExpression
     * @Description : Returns the left expression of an aggregate expression.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSExpression leftExpression() {
        return mLeftExpression;
    }

    /**
     * @return Return Value The operand for the receiver—that is, the object on which the selector
     * will be invoked.
     * @Signature: operand
     * @Declaration : - (NSExpression *)operand
     * @Description : Returns the operand for the receiver.
     * @Discussion The object is the result of evaluating a key path or one of the defined
     * functions. This method raises an exception if it
     * is not applicable to the receiver.
     **/
    
    public NSExpression operand() {
        return mOperand;
    }

    /**
     * @return Return Value The predicate of a subquery expression.
     * @Signature: predicate
     * @Declaration : - (NSPredicate *)predicate
     * @Description : Return the predicate of a subquery expression.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSPredicate predicate() {
        return mPredicate;
    }

    /**
     * @return Return Value The right expression of a set expression.
     * @Signature: rightExpression
     * @Declaration : - (NSExpression *)rightExpression
     * @Description : Returns the right expression of an aggregate expression.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSExpression rightExpression() {
        return mRightExpression;
    }

    /**
     * @return Return Value The variable for the receiver.
     * @Signature: variable
     * @Declaration : - (NSString *)variable
     * @Description : Returns the variable for the receiver.
     * @Discussion This method raises an exception if it is not applicable to the receiver.
     **/
    
    public NSString variable() {
        return mVariable;
    }

    // Evaluating an Expression

    /**
     * @param object  The object against which the receiver is evaluated.
     * @param context A dictionary that the expression can use to store temporary state for one
     *                predicate evaluation. Can be nil. Note that
     *                context is mutable, and that it can only be accessed during the evaluation of
     *                the expression. You must not attempt to
     *                retain it for use elsewhere.
     * @return Return Value The evaluated object.
     * @Signature: expressionValueWithObject:context:
     * @Declaration : - (id)expressionValueWithObject:(id)object context:(NSMutableDictionary
     * *)context
     * @Description : Evaluates an expression using a given object and context.
     **/
    
    public NSObject expressionValueWithObjectContext(NSObject object, NSMutableDictionary<NSObject, NSString> context) {
        return null;
    }

    /**
     * @Signature: allowEvaluation
     * @Declaration : - (void)allowEvaluation
     * @Description : Force an expression that was securely decoded to allow evaluation.
     * @Discussion When securely decoding an NSExpression object encoded using NSSecureCoding,
     * evaluation is disabled because it is
     * potentially unsafe to evaluate expressions you get out of an archive. Before you enable
     * evaluation, you should validate
     * key paths, selectors, etc to ensure no erroneous or malicious code will be executed. Once
     * you’ve preflighted the
     * expression, you can enable the receiver for evaluation by calling allowEvaluation.
     **/
    
    public void allowEvaluation() {
        // FIXME
    }

    // Accessing the Expression Block

    /**
     * @return Return Value The expression’s expression Block as created in
     * expressionForBlock:arguments:.
     * @Signature: expressionBlock
     * @Declaration : - (id (^)(id, NSArray *, NSMutableDictionary *))expressionBlock
     * @Description : Returns the expression’s expression Block.
     **/
    public NSExpressionBlock expressionBlock(NSObject id, NSArray<NSExpression> nsArray,
                                             NSMutableDictionary<NSObject, NSString> nsMutableDic) {
        // FIXME
        // NSExpressionBlock exp = (NSExpressionBlock) NSExpression.expressionForBlockArguments(id, nsArray,
        // nsMutableDic,mArguments);
        return null;
    }

    @Override
    public String toString() {
        return NSExpression.mExpressionForEvaluatedObject.getWrappedString();
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