
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.functions.NSDecimal;
import com.myappconverter.java.foundations.types.NSDecimalNumberBehaviors;

import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;



public class NSDecimalNumber extends NSNumber {

    // default roundMode and scale
    private static int roundMode;
    private static int scale;
    private BigDecimal mBigDecimal;

    public NSDecimalNumber() {
    }

    public BigDecimal getDecimalNumber() {
        return mBigDecimal;
    }

    public void setDecimalNumber(BigDecimal mBigDecimal) {
        this.mBigDecimal = mBigDecimal;
    }

	/*
     * Static methods
	 */

    /**
     * @param mantissa   The mantissa for the new decimal number object.
     * @param exponent   The exponent for the new decimal number object.
     * @param isNegative A Boolean value that specifies whether the sign of the number is negative.
     * @Signature: decimalNumberWithMantissa:exponent:isNegative:
     * @Declaration : + (NSDecimalNumber *)decimalNumberWithMantissa:(unsigned long long)mantissa
     * exponent:(short)exponent
     * isNegative:(BOOL)isNegative
     * @Description : Creates and returns an NSDecimalNumber object equivalent to the number
     * specified by the arguments.
     * @Discussion The arguments express a number in a kind of scientific notation that requires the
     * mantissa to be an integer. So, for
     * example, if the number to be represented is –12.345, it is expressed as 12345x10^–3—mantissa
     * is 12345; exponent is –3;
     * and isNegative is YES, as illustrated by the following example. NSDecimalNumber *number =
     * [NSDecimalNumber
     * decimalNumberWithMantissa:12345 exponent:-3 isNegative:YES];
     **/
    
    public static NSDecimalNumber decimalNumberWithMantissaExponentIsNegative(long mantissa, short exponent, boolean isNegative) {
        NSDecimalNumber nsd = new NSDecimalNumber();
        nsd.mBigDecimal = BigDecimal.valueOf(mantissa * Math.pow(10, exponent));
        return nsd;
    }

    /**
     * @param decimal An NSDecimal structure that specifies the value for the new decimal number
     *                object.
     * @return Return Value An NSDecimalNumber object equivalent to decimal.
     * @Signature: decimalNumberWithDecimal:
     * @Declaration : + (NSDecimalNumber *)decimalNumberWithDecimal:(NSDecimal)decimal
     * @Description : Creates and returns an NSDecimalNumber object equivalent to a given NSDecimal
     * structure.
     * @Discussion You can initialize decimal programmatically or generate it using the NSScanner
     * method, scanDecimal:
     **/
    
    public static NSDecimalNumber decimalNumberWithDecimal(NSDecimal decimal) {
        NSDecimalNumber bg = new NSDecimalNumber();
        bg.mBigDecimal = BigDecimal.valueOf(decimal.getWrappedNumber().doubleValue());
        return bg;
    }

    /**
     * @param numericString A numeric string. Besides digits, numericString can include an initial
     *                      “+ or “–; a single “E or “e, to
     *                      indicate the exponent of a number in scientific notation; and a single
     *                      NSLocaleDecimalSeparator to divide the fractional
     *                      from the integral part of the number.
     * @return Return Value An NSDecimalNumber object whose value is equivalent to numericString.
     * @Signature: decimalNumberWithString:
     * @Declaration : + (NSDecimalNumber *)decimalNumberWithString:(NSString *)numericString
     * @Description : Creates and returns an NSDecimalNumber object whose value is equivalent to
     * that in a given numeric string.
     * @Discussion Whether the NSLocaleDecimalSeparator is a period (as is used, for example, in the
     * United States) or a comma (as is used,
     * for example, in France) depends on the default locale.
     **/
    
    public static NSDecimalNumber decimalNumberWithString(NSString numericString) {
        NSDecimalNumber bg = new NSDecimalNumber();
        bg.mBigDecimal = BigDecimal.valueOf(numericString.doubleValue());
        return bg;
    }

    /**
     * @param numericString A numeric string. Besides digits, numericString can include an initial
     *                      “+ or “–; a single “E or “e, to
     *                      indicate the exponent of a number in scientific notation; and a single
     *                      NSLocaleDecimalSeparator to divide the fractional
     *                      from the integral part of the number.
     * @param locale        A dictionary that defines the locale (specifically the
     *                      NSLocaleDecimalSeparator) to use to interpret the number in
     *                      numericString.
     * @return Return Value An NSDecimalNumber object whose value is equivalent to numericString.
     * @Signature: decimalNumberWithString:locale:
     * @Declaration : + (NSDecimalNumber *)decimalNumberWithString:(NSString *)numericString
     * locale:(NSDictionary *)locale
     * @Description : Creates and returns an NSDecimalNumber object whose value is equivalent to
     * that in a given numeric string, interpreted
     * using a given locale.
     * @Discussion The locale parameter determines whether the NSLocaleDecimalSeparator is a period
     * (as is used, for example, in the United
     * States) or a comma (as is used, for example, in France). The following strings show examples
     * of acceptable values for
     * numericString: “2500.6 (or “2500,6, depending on locale) “–2500.6 (or “–2500.6)
     * “–2.5006e3 (or “–2,5006e3)
     * “–2.5006E3 (or “–2,5006E3) The following strings are unacceptable: “2,500.6 “2500 3/5
     * “2.5006x10e3 “two thousand
     * five hundred and six tenths
     **/
    
    public static NSDecimalNumber decimalNumberWithStringLocale(NSString numericString, NSDictionary locale) {
        // FIXME use locale
        NSDecimalNumber bg = new NSDecimalNumber();
        Long d = Long.valueOf(numericString.getWrappedString());
        bg.mBigDecimal = BigDecimal.valueOf(d.doubleValue());
        return bg;
    }

    /**
     * @return Return Value An NSDecimalNumber object equivalent to the number 0.0.
     * @Signature: zero
     * @Declaration : + (NSDecimalNumber *)zero
     * @Description : Returns an NSDecimalNumber object equivalent to the number 0.0.
     **/
    
    public static NSDecimalNumber zero() {
        NSDecimalNumber bg = new NSDecimalNumber();
        bg.mBigDecimal = BigDecimal.ZERO;
        return bg;
    }

    /**
     * @return Return Value An NSDecimalNumber object equivalent to the number 1.0.
     * @Signature: one
     * @Declaration : + (NSDecimalNumber *)one
     * @Description : Returns an NSDecimalNumber object equivalent to the number 1.0.
     **/
    
    public static NSDecimalNumber one() {
        NSDecimalNumber bone = new NSDecimalNumber();
        bone.mBigDecimal = BigDecimal.ONE;
        return bone;
    }

    /**
     * @return Return Value The smallest possible value of an NSDecimalNumber object.
     * @Signature: minimumDecimalNumber
     * @Declaration : + (NSDecimalNumber *)minimumDecimalNumber
     * @Description : Returns the smallest possible value of an NSDecimalNumber object.
     **/
    
    public static NSDecimalNumber minimumDecimalNumber() {
        NSDecimalNumber nsd = new NSDecimalNumber();
        nsd.mBigDecimal = BigDecimal.valueOf(Long.MIN_VALUE);
        return nsd;
    }

    /**
     * @return Return Value The largest possible value of an NSDecimalNumber object.
     * @Signature: maximumDecimalNumber
     * @Declaration : + (NSDecimalNumber *)maximumDecimalNumber
     * @Description : Returns the largest possible value of an NSDecimalNumber object.
     **/
    
    public static NSDecimalNumber maximumDecimalNumber() {
        NSDecimalNumber nsd = new NSDecimalNumber();
        nsd.mBigDecimal = BigDecimal.valueOf(Long.MAX_VALUE);
        return nsd;
    }

    /**
     * @return Return Value An NSDecimalNumber object that specifies no number.
     * @Signature: notANumber
     * @Declaration : + (NSDecimalNumber *)notANumber
     * @Description : Returns an NSDecimalNumber object that specifies no number.
     * @Discussion Any arithmetic method receiving notANumber as an argument returns notANumber.
     * This value can be a useful way of handling
     * non-numeric data in an input file. This method can also be a useful response to calculation
     * errors. For more information
     * on calculation errors, see the exceptionDuringOperation:error:leftOperand:rightOperand:
     * method description in the
     * NSDecimalNumberBehaviors protocol specification.
     **/
    
    public static NSDecimalNumber notANumber() {
        NSDecimalNumber nsd = new NSDecimalNumber();
        nsd.numberValue = "nan";
        return nsd;
    }

    // Initializing a Decimal Number

    /**
     * @param mantissa The mantissa for the new decimal number object.
     * @param exponent The exponent for the new decimal number object.
     * @param flag     A Boolean value that specifies whether the sign of the number is negative.
     * @return Return Value An NSDecimalNumber object initialized using the given mantissa,
     * exponent, and sign.
     * @Signature: initWithMantissa:exponent:isNegative:
     * @Declaration : - (id)initWithMantissa:(unsigned long long)mantissa exponent:(short)exponent
     * isNegative:(BOOL)flag
     * @Description : Returns an NSDecimalNumber object initialized using the given mantissa,
     * exponent, and sign.
     * @Discussion The arguments express a number in a type of scientific notation that requires the
     * mantissa to be an integer. So, for
     * example, if the number to be represented is 1.23, it is expressed as 123x10^–2—mantissa is
     * 123; exponent is –2; and
     * isNegative, which refers to the sign of the mantissa, is NO.
     **/
    
    public NSDecimalNumber initWithMantissaExponentIsNegative(long mantissa, short exponent, boolean flag) {
        NSString str = new NSString(" " + mantissa + "^" + exponent + "E" + (flag ? 1 : -1));
        NSDecimalNumber nsd = new NSDecimalNumber();
        nsd.mBigDecimal = BigDecimal.valueOf(str.doubleValue());
        return nsd;
    }

    /**
     * @param decimal The value of the new object.
     * @return Return Value An NSDecimalNumber object initialized to represent decimal.
     * @Signature: initWithDecimal:
     * @Declaration : - (id)initWithDecimal:(NSDecimal)decimal
     * @Description : Returns an NSDecimalNumber object initialized to represent a given decimal.
     * @Discussion This method is the designated initializer for NSDecimalNumber.
     **/
    
    public NSDecimalNumber initWithDecimal(NSDecimal decimal) {
        NSDecimalNumber nsd = new NSDecimalNumber();
        this.mBigDecimal = BigDecimal.valueOf(decimal.getWrappedNumber().doubleValue());
        return nsd;
    }

    /**
     * @param numericString A numeric string. Besides digits, numericString can include an initial
     *                      “+ or “–; a single “E or “e, to
     *                      indicate the exponent of a number in scientific notation; and a single
     *                      NSLocaleDecimalSeparator to divide the fractional
     *                      from the integral part of the number. For a listing of acceptable and
     *                      unacceptable strings, see the class method
     *                      decimalNumberWithString:locale:.
     * @return Return Value An NSDecimalNumber object initialized so that its value is equivalent to
     * that in numericString.
     * @Signature: initWithString:
     * @Declaration : - (id)initWithString:(NSString *)numericString
     * @Description : Returns an NSDecimalNumber object initialized so that its value is equivalent
     * to that in a given numeric string.
     **/
    
    public NSDecimalNumber initWithString(NSString numericString) {
        this.mBigDecimal = BigDecimal.valueOf(numericString.doubleValue());
        return this;
    }

    /**
     * @param numericString A numeric string. Besides digits, numericString can include an initial
     *                      “+ or “–; a single “E or “e, to
     *                      indicate the exponent of a number in scientific notation; and a single
     *                      NSLocaleDecimalSeparator to divide the fractional
     *                      from the integral part of the number.
     * @param locale        A dictionary that defines the locale (specifically the
     *                      NSLocaleDecimalSeparator) to use to interpret the number in
     *                      numericString.
     * @return Return Value An NSDecimalNumber object initialized so that its value is equivalent to
     * that in numericString, interpreted
     * using locale.
     * @Signature: initWithString:locale:
     * @Declaration : - (id)initWithString:(NSString *)numericString locale:(NSDictionary *)locale
     * @Description : Returns an NSDecimalNumber object initialized so that its value is equivalent
     * to that in a given numeric string,
     * interpreted using a given locale.
     **/
    
    public NSDecimalNumber initWithStringLocale(NSString numericString, NSLocale locale) {
        this.mBigDecimal = new BigDecimal(numericString.getWrappedString());
        return this;
    }

    /**
     * @return Return Value The receiver’s value, expressed as an NSDecimal structure.
     * @Signature: decimalValue
     * @Declaration : - (NSDecimal)decimalValue
     * @Description : Returns the receiver’s value, expressed as an NSDecimal structure.
     **/
    @Override
    
    public NSDecimal decimalValue() {
        NSDecimal nsDecimal = new NSDecimal();
        nsDecimal.numberValue = mBigDecimal.toString();
        return nsDecimal;
    }

    public NSDecimal getDecimalValue() {
        NSDecimal nsDecimal = new NSDecimal();
        nsDecimal.numberValue = mBigDecimal.toString();
        return nsDecimal;
    }

    /**
     * @param decimalNumber The number to add to the receiver.
     * @return Return Value A new NSDecimalNumber object whose value is the sum of the receiver and
     * decimalNumber.
     * @Signature: decimalNumberByAdding:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByAdding:(NSDecimalNumber *)decimalNumber
     * @Description : Returns a new NSDecimalNumber object whose value is the sum of the receiver
     * and another given NSDecimalNumber object.
     * @Discussion This method uses the default behavior when handling calculation errors and
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberByAdding(NSDecimalNumber decimalNumber) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = mBigDecimal.add(decimalNumber.mBigDecimal);
        return myTmpNSD;
    }

    /**
     * @param decimalNumber The number to subtract from the receiver.
     * @return Return Value A new NSDecimalNumber object whose value is decimalNumber subtracted
     * from the receiver.
     * @Signature: decimalNumberBySubtracting:
     * @Declaration : - (NSDecimalNumber *)decimalNumberBySubtracting:(NSDecimalNumber
     * *)decimalNumber
     * @Description : Returns a new NSDecimalNumber object whose value is that of another given
     * NSDecimalNumber object subtracted from the
     * value of the receiver.
     * @Discussion This method uses the default behavior when handling calculation errors and when
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberBySubtracting(NSDecimalNumber decimalNumber) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = mBigDecimal.subtract(decimalNumber.mBigDecimal);
        return myTmpNSD;
    }

    /**
     * @param decimalNumber The number by which to multiply the receiver.
     * @return Return Value A new NSDecimalNumber object whose value is decimalNumber multiplied by
     * the receiver.
     * @Signature: decimalNumberByMultiplyingBy:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByMultiplyingBy:(NSDecimalNumber
     * *)decimalNumber
     * @Description : Returns a new NSDecimalNumber object whose value is the value of the receiver
     * multiplied by that of another given
     * NSDecimalNumber object.
     * @Discussion This method uses the default behavior when handling calculation errors and when
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberByMultiplyingBy(NSDecimalNumber decimalNumber) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = mBigDecimal.multiply(decimalNumber.mBigDecimal);
        return myTmpNSD;
    }

    /**
     * @param decimalNumber The number by which to divide the receiver.
     * @return Return Value A new NSDecimalNumber object whose value is the value of the receiver
     * divided by decimalNumber.
     * @Signature: decimalNumberByDividingBy:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByDividingBy:(NSDecimalNumber
     * *)decimalNumber
     * @Description : Returns a new NSDecimalNumber object whose value is the value of the receiver
     * divided by that of another given
     * NSDecimalNumber object.
     * @Discussion This method uses the default behavior when handling calculation errors and
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberByDividingBy(NSDecimalNumber decimalNumber) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = this.mBigDecimal.divide(decimalNumber.mBigDecimal);
        return myTmpNSD;
    }

    /**
     * @param power The power to which to raise the receiver.
     * @return Return Value A new NSDecimalNumber object whose value is the value of the receiver
     * raised to the power power.
     * @Signature: decimalNumberByRaisingToPower:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByRaisingToPower:(NSUInteger)power
     * @Description : Returns a new NSDecimalNumber object whose value is the value of the receiver
     * raised to a given power.
     * @Discussion This method uses the default behavior when handling calculation errors and when
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberByRaisingToPower(int power) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = mBigDecimal.pow(power);
        return myTmpNSD;
    }

    /**
     * @param decimalNumber The number with which to compare the receiver. This value must not be
     *                      nil. If this value is nil, the behavior is
     *                      undefined and may change in future versions of OS X.
     * @return Return Value NSOrderedAscending if the value of decimalNumber is greater than the
     * receiver; NSOrderedSame if they’re equal;
     * and NSOrderedDescending if the value of decimalNumber is less than the receiver.
     * @Signature: compare:
     * @Declaration : - (NSComparisonResult)compare:(NSNumber *)decimalNumber
     * @Description : Returns an NSComparisonResult value that indicates the numerical ordering of
     * the receiver and another given
     * NSDecimalNumber object.
     **/
    @Override
    
    public NSComparisonResult compare(NSNumber decimalNumber) {
        NSNumber nsn = new NSNumber();
        return nsn.compare(decimalNumber);
    }

    /**
     * @Signature: objCType
     * @Declaration : - (const char *)objCType
     * @Description : Returns a C string containing the Objective-C type of the data contained in
     * the receiver, which for an NSDecimalNumber
     * object is always “d (for double).
     **/
    @Override
    
    public String objCType() {
        objCType = 'd';
        return String.valueOf(objCType);
    }

    public String getObjCType() {
        objCType = 'd';
        return String.valueOf(objCType);
    }

    /**
     * @return Return Value The approximate value of the receiver as a double.
     * @Signature: doubleValue
     * @Declaration : - (double)doubleValue
     * @Description : Returns the approximate value of the receiver as a double.
     **/
    @Override
    
    public double doubleValue() {
        return mBigDecimal.doubleValue();
    }

    public double getDoubleValue() {
        return mBigDecimal.doubleValue();
    }

    /**
     * @param numericString A numeric string. Besides digits, numericString can include an initial
     *                      “+ or “–; a single “E or “e, to
     *                      indicate the exponent of a number in scientific notation; and a single
     *                      NSLocaleDecimalSeparator to divide the fractional
     *                      from the integral part of the number.
     * @param locale        A dictionary that defines the locale (specifically the
     *                      NSLocaleDecimalSeparator) to use to interpret the number in
     *                      numericString.
     * @return Return Value An NSDecimalNumber object initialized so that its value is equivalent to
     * that in numericString, interpreted
     * using locale.
     * @Signature: initWithString:locale:
     * @Declaration : - (id)initWithString:(NSString *)numericString locale:(NSDictionary *)locale
     * @Description : Returns an NSDecimalNumber object initialized so that its value is equivalent
     * to that in a given numeric string,
     * interpreted using a given locale.
     **/
    
    public NSDecimalNumber initWithStringLocale(String numericString, NSDictionary locale) {
        // FIXME use locale
        mBigDecimal = new BigDecimal(numericString);

        try {
            NumberFormat format = NumberFormat.getInstance(Locale.CANADA);
            mBigDecimal = new BigDecimal(format.parse(numericString).toString());
        } catch (ParseException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }

        return this;

    }

    /**
     * @Signature: decimalNumberByMultiplyingByPowerOf10:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByMultiplyingByPowerOf10:(short)power
     * @Description : Multiplies the receiver by 10^power and returns the product, a newly created
     * NSDecimalNumber object.
     * @Discussion This method uses the default behavior when handling calculation errors and when
     * rounding.
     **/
    
    public NSDecimalNumber decimalNumberByMultiplyingByPowerOf10(short power) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        myTmpNSD.mBigDecimal = mBigDecimal.multiply(BigDecimal.valueOf(Math.pow(10, power)));
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberByAdding:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByAdding:(NSDecimalNumber *)decimalNumber
     * withBehavior:(id <
     * NSDecimalNumberBehaviors >)behavior
     * @Description : Adds decimalNumber to the receiver and returns the sum, a newly created
     * NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberByAddingWithBehavior(NSDecimalNumber decimalNumber, NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.add(decimalNumber.mBigDecimal, mathCtxt);
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberBySubtracting:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberBySubtracting:(NSDecimalNumber
     * *)decimalNumber withBehavior:(id <
     * NSDecimalNumberBehaviors >)behavior
     * @Description : Subtracts decimalNumber from the receiver and returns the difference, a newly
     * created NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberBySubtractingWithBehavior(NSDecimalNumber decimalNumber, NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));

        myTmpNSD.mBigDecimal = mBigDecimal.add(decimalNumber.mBigDecimal.negate(), mathCtxt);
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberByRoundingAccordingToBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByRoundingAccordingToBehavior:(id <
     * NSDecimalNumberBehaviors >)behavior
     * @Description : Rounds the receiver off in the way specified by behavior and returns the
     * result, a newly created NSDecimalNumber
     * object.
     * @Discussion For a description of the different ways of rounding, see the roundingMode method
     * in the NSDecimalNumberBehaviors protocol
     * specification.
     **/
    
    public NSDecimalNumber decimalNumberByRoundingAccordingToBehavior(NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.round(mathCtxt);
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberByMultiplyingBy:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByMultiplyingBy:(NSDecimalNumber
     * *)decimalNumber withBehavior:(id <
     * NSDecimalNumberBehaviors >)behavior
     * @Description : Multiplies the receiver by decimalNumber and returns the product, a newly
     * created NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberByMultiplyingByWithBehavior(NSDecimalNumber decimalNumber, NSDecimalNumberBehaviors behavior) {
        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.multiply(decimalNumber.mBigDecimal, mathCtxt);
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberByDividingBy:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByDividingBy:(NSDecimalNumber
     * *)decimalNumber withBehavior:(id <
     * NSDecimalNumberBehaviors >)behavior
     * @Description : Divides the receiver by decimalNumber and returns the quotient, a newly
     * created NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberByDividingByWithBehavior(NSDecimalNumber decimalNumber, NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.divide(decimalNumber.mBigDecimal, mathCtxt);
        return myTmpNSD;
    }

    /**
     * @Signature: decimalNumberByRaisingToPower:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByRaisingToPower:(NSUInteger)power
     * withBehavior:(id < NSDecimalNumberBehaviors
     * >)behavior
     * @Description : Raises the receiver to power and returns the result, a newly created
     * NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberByRaisingToPowerWithBehavior(int power, NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.pow(power, mathCtxt);
        return myTmpNSD;

    }

    /**
     * @Signature: decimalNumberByMultiplyingByPowerOf10:withBehavior:
     * @Declaration : - (NSDecimalNumber *)decimalNumberByMultiplyingByPowerOf10:(short)power
     * withBehavior:(id < NSDecimalNumberBehaviors
     * >)behavior
     * @Description : Multiplies the receiver by 10^power and returns the product, a newly created
     * NSDecimalNumber object.
     * @Discussion behavior specifies the handling of calculation errors and rounding.
     **/
    
    public NSDecimalNumber decimalNumberByMultiplyingByPowerOf10WithBehavior(short power, NSDecimalNumberBehaviors behavior) {

        NSDecimalNumber myTmpNSD = new NSDecimalNumber();
        MathContext mathCtxt = new MathContext(behavior.scale, RoundingMode.valueOf(behavior.roundingMode));
        myTmpNSD.mBigDecimal = mBigDecimal.multiply(BigDecimal.valueOf(Math.pow(10, power)), mathCtxt);
        return myTmpNSD;

    }

    /**
     * @param locale A dictionary that defines the locale (specifically the
     *               NSLocaleDecimalSeparator) to use to generate the returned
     *               string.
     * @return Return Value A string that represents the contents of the receiver, according to
     * locale.
     * @Signature: descriptionWithLocale:
     * @Declaration : - (NSString *)descriptionWithLocale:(NSDictionary *)locale
     * @Description : Returns a string, specified according to a given locale, that represents the
     * contents of the receiver.
     **/
    
    public String descriptionWithLocale(NSDictionary locale) {
        // FIXME use locale
        NumberFormat format = NumberFormat.getInstance();
        try {
            return format.parse(mBigDecimal.toString()).toString();
        } catch (ParseException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return numberValue;

    }

    /**
     * @Signature: defaultBehavior
     * @Declaration : + (id < NSDecimalNumberBehaviors >)defaultBehavior
     * @Description : Returns the way arithmetic methods, like decimalNumberByAdding:, round off and
     * handle error conditions.
     * @Discussion By default, the arithmetic methods use the NSRoundPlain behavior; that is, the
     * methods round to the closest possible
     * return value. The methods assume your need for precision does not exceed 38 significant
     * digits and raise exceptions when
     * they try to divide by 0 or produce a number too big or too small to be represented. If this
     * default behavior doesn’t suit
     * your application, you should use methods that let you specify the behavior, like
     * decimalNumberByAdding:withBehavior:. If
     * you find yourself using a particular behavior consistently, you can specify a different
     * default behavior with
     * setDefaultBehavior:. The default behavior is maintained separately for each thread in your
     * app.
     **/
    
    public static NSDecimalNumberBehaviors defaultBehavior() {
        NSDecimalNumberBehaviors nsd = new NSDecimalNumberBehaviors();
        nsd.scale = scale;
        nsd.roundingMode = RoundingMode.valueOf(roundMode).ordinal();
        return nsd;
    }

    /**
     * @Signature: setDefaultBehavior:
     * @Declaration : + (void)setDefaultBehavior:(id < NSDecimalNumberBehaviors >)behavior
     * @Description : Specifies the way that arithmetic methods, like decimalNumberByAdding:, round
     * off and handle error conditions.
     * @Discussion behavior must conform to the NSDecimalNumberBehaviors protocol. The default
     * behavior is maintained separately for each
     * thread in your app. setDefaultBehavior: sets the default behavior for the thread on which
     * it’s executed.
     **/
    
    public static void setDefaultBehavior(NSDecimalNumberBehaviors behavior) {
        scale = behavior.scale;
        roundMode = behavior.roundingMode;
    }

}