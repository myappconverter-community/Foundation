
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.functions.NSDecimal;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Logger;


public class NSNumber extends NSValue implements Comparable<NSNumber>, Serializable {

    public NSNumber() {
    }

    char objCType;

    public String getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }

    public Number getNumber() {
        Number number = new Number() {

            @Override
            public long longValue() {
                try {
                    return NSNumber.this.longValue();
                } catch (Exception e) {
                    Logger.getLogger("context", String.valueOf(e));
                    return 0;
                }
            }

            @Override
            public int intValue() {
                try {
                    return NSNumber.this.intValue();
                } catch (Exception e) {
                    Logger.getLogger("context", String.valueOf(e));
                    return 0;
                }
            }

            @Override
            public float floatValue() {
                try {
                    return NSNumber.this.floatValue();
                } catch (Exception e) {
                    Logger.getLogger("context", String.valueOf(e));
                    return 0;
                }
            }

            @Override
            public double doubleValue() {
                try {
                    return NSNumber.this.doubleValue();
                } catch (Exception e) {
                    Logger.getLogger("context", String.valueOf(e));
                    return 0;
                }
            }
        };
        return number;
    }

    // Creating an NSNumber Object

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed char.
     * @Signature: numberWithChar:
     * @Declaration : + (NSNumber *)numberWithChar:(char)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a signed char.
     **/
    
    public static NSNumber numberWithChar(char value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'c';
        nsbr.numberValue = String.valueOf(value);
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned char.
     * @Signature: numberWithUnsignedChar:
     * @Declaration : + (NSNumber *)numberWithUnsignedChar:(unsigned char)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an unsigned char.
     **/
    
    public static NSNumber numberWithUnsignedChar(char value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 's';
        nsbr.numberValue = String.valueOf(value);
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed short.
     * @Signature: numberWithShort:
     * @Declaration : + (NSNumber *)numberWithShort:(short)value
     * @Description : Creates and returns an NSNumber object containing value, treating it as a
     * signed short.
     **/
    
    public static NSNumber numberWithShort(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 's';
        nsbr.numberValue = String.valueOf(value.shortValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned short.
     * @Signature: numberWithUnsignedShort:
     * @Declaration : + (NSNumber *)numberWithUnsignedShort:(unsigned short)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an unsigned short.
     **/
    
    public static NSNumber numberWithUnsignedShort(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'i';
        nsbr.numberValue = String.valueOf(Math.abs(value.shortValue()));
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed int.
     * @Signature: numberWithInt:
     * @Declaration : + (NSNumber *)numberWithInt:(int)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a signed int.
     **/
    
    public static NSNumber numberWithInt(int value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'i';
        nsbr.numberValue = value + "";

        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned int.
     * @Signature: numberWithUnsignedInt:
     * @Declaration : + (NSNumber *)numberWithUnsignedInt:(unsigned int)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an unsigned int.
     **/
    
    public static NSNumber numberWithUnsignedInt(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'q';
        nsbr.numberValue = String.valueOf(Math.abs(value.intValue()));
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed long.
     * @Signature: numberWithLong:
     * @Declaration : + (NSNumber *)numberWithLong:(long)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a signed long.
     **/
    
    public static NSNumber numberWithLong(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'i';
        nsbr.numberValue = String.valueOf(value.longValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned long.
     * @Signature: numberWithUnsignedLong:
     * @Declaration : + (NSNumber *)numberWithUnsignedLong:(unsigned long)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an unsigned long.
     **/
    
    public static NSNumber numberWithUnsignedLong(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'q';
        nsbr.numberValue = String.valueOf(Math.abs(value.longValue()));
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed long long.
     * @Signature: numberWithLongLong:
     * @Declaration : + (NSNumber *)numberWithLongLong:(long long)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a signed long long.
     **/
    
    public static NSNumber numberWithLongLong(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'q';
        nsbr.numberValue = String.valueOf(value.longValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned long
     * long.
     * @Signature: numberWithUnsignedLongLong:
     * @Declaration : + (NSNumber *)numberWithUnsignedLongLong:(unsigned long long)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an unsigned long long.
     **/
    
    public static NSNumber numberWithUnsignedLongLong(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'q';
        nsbr.numberValue = String.valueOf(Math.abs(value.longValue()));
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a float.
     * @Signature: numberWithFloat:
     * @Declaration : + (NSNumber *)numberWithFloat:(float)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a float.
     **/
    
    public static NSNumber numberWithFloat(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'f';
        nsbr.numberValue = String.valueOf(value.floatValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a double.
     * @Signature: numberWithDouble:
     * @Declaration : + (NSNumber *)numberWithDouble:(double)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a double.
     **/
    
    public static NSNumber numberWithDouble(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'd';
        nsbr.numberValue = String.valueOf(value.doubleValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a BOOL.
     * @Signature: numberWithBool:
     * @Declaration : + (NSNumber *)numberWithBool:(BOOL)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as a BOOL.
     **/
    
    public static NSNumber numberWithBool(boolean value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'c';
        nsbr.numberValue = String.valueOf(value);
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an NSInteger.
     * @Signature: numberWithInteger:
     * @Declaration : + (NSNumber *)numberWithInteger:(NSInteger)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an NSInteger.
     **/
    
    public static NSNumber numberWithInteger(Number value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'i';
        nsbr.numberValue = String.valueOf(value.intValue());
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an NSUInteger.
     * @Signature: numberWithUnsignedInteger:
     * @Declaration : + (NSNumber *)numberWithUnsignedInteger:(NSUInteger)value
     * @Description : Creates and returns an NSNumber object containing a given value, treating it
     * as an NSUInteger.
     **/
    
    public static NSNumber numberWithUnsignedInteger(int value) {
        NSNumber nsbr = new NSNumber();
        nsbr.objCType = 'i';
        nsbr.numberValue = String.valueOf(value);
        return nsbr;
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed char.
     * @Signature: initWithChar:
     * @Declaration : - (id)initWithChar:(char)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * signed char.
     **/
    
    public NSNumber initWithChar(char value) {
        return NSNumber.numberWithChar(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned char.
     * @Signature: initWithUnsignedChar:
     * @Declaration : - (id)initWithUnsignedChar:(unsigned char)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * unsigned char.
     **/
    
    public NSNumber initWithUnsignedChar(char value) {
        return NSNumber.numberWithChar(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed short.
     * @Signature: initWithShort:
     * @Declaration : - (id)initWithShort:(short)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * signed short.
     **/
    
    public NSNumber initWithShort(Number value) {
        return NSNumber.numberWithShort(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned short.
     * @Signature: initWithUnsignedShort:
     * @Declaration : - (id)initWithUnsignedShort:(unsigned short)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * unsigned short.
     **/
    
    public NSNumber initWithUnsignedShort(Number value) {
        return NSNumber.numberWithShort(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed int.
     * @Signature: initWithInt:
     * @Declaration : - (id)initWithInt:(int)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * signed int.
     **/
    
    public NSNumber initWithInt(Number value) {
        return NSNumber.numberWithInt(value.intValue());
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned int.
     * @Signature: initWithUnsignedInt:
     * @Declaration : - (id)initWithUnsignedInt:(unsigned int)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * unsigned int.
     **/
    
    public NSNumber initWithUnsignedInt(Number value) {
        return NSNumber.numberWithInt(value.intValue());
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an NSInteger.
     * @Signature: initWithInteger:
     * @Declaration : - (id)initWithInteger:(NSInteger)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * NSInteger.
     **/
    
    public NSNumber initWithInteger(Number value) {
        return NSNumber.numberWithInt(value.intValue());
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an NSUInteger.
     * @Signature: initWithUnsignedInteger:
     * @Declaration : - (id)initWithUnsignedInteger:(NSUInteger)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * NSUInteger.
     **/
    
    public NSNumber initWithUnsignedInteger(Number value) {
        return NSNumber.numberWithInt(value.intValue());
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed long.
     * @Signature: initWithLong:
     * @Declaration : - (id)initWithLong:(long)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * signed long.
     **/
    
    public NSNumber initWithLong(Number value) {
        return NSNumber.numberWithLong(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned long.
     * @Signature: initWithUnsignedLong:
     * @Declaration : - (id)initWithUnsignedLong:(unsigned long)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * unsigned long.
     **/
    
    public NSNumber initWithUnsignedLong(Number value) {
        return NSNumber.numberWithUnsignedLong(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a signed long long.
     * @Signature: initWithLongLong:
     * @Declaration : - (id)initWithLongLong:(long long)value
     * @Description : Returns an NSNumber object initialized to contain value, treated as a signed
     * long long.
     **/
    
    public NSNumber initWithLongLong(Number value) {
        return NSNumber.numberWithLong(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as an unsigned long
     * long.
     * @Signature: initWithUnsignedLongLong:
     * @Declaration : - (id)initWithUnsignedLongLong:(unsigned long long)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as an
     * unsigned long long.
     **/
    
    public NSNumber initWithUnsignedLongLong(Number value) {
        return NSNumber.numberWithUnsignedLongLong(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a float.
     * @Signature: initWithFloat:
     * @Declaration : - (id)initWithFloat:(float)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * float.
     **/
    
    public NSNumber initWithFloat(Number value) {
        return NSNumber.numberWithFloat(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a double.
     * @Signature: initWithDouble:
     * @Declaration : - (id)initWithDouble:(double)value
     * @Description : Returns an NSNumber object initialized to contain value, treated as a double.
     **/
    
    public NSNumber initWithDouble(Number value) {
        return NSNumber.numberWithDouble(value);
    }

    /**
     * @param value The value for the new number.
     * @return Return Value An NSNumber object containing value, treating it as a BOOL.
     * @Signature: initWithBool:
     * @Declaration : - (id)initWithBool:(BOOL)value
     * @Description : Returns an NSNumber object initialized to contain a given value, treated as a
     * BOOL.
     **/
    
    public NSNumber initWithBool(boolean value) {
        numberValue = String.valueOf(value);
        return this;
    }

    /**
     * @return Return Value The receiver’s value, expressed as an NSDecimal structure. The value
     * returned isn’t guaranteed to be exact for
     * float and double values.
     * @Signature: decimalValue
     * @Declaration : - (NSDecimal)decimalValue
     * @Description : Returns the receiver’s value, expressed as an NSDecimal structure.
     **/
    
    public NSDecimal decimalValue() {
        NSDecimal nsDecimal = new NSDecimal();
        nsDecimal.numberValue = numberValue;
        return nsDecimal;

    }

    public NSDecimal getDecimalValue() {
        return decimalValue();

    }

    /**
     * @return Return Value The receiver’s value as a char, converting it as necessary.
     * @Signature: charValue
     * @Declaration : - (char)charValue
     * @Description : Returns the receiver’s value as a char.
     **/
    
    public char charValue() {
        return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as an unsigned char, converting it as necessary.
     * @Signature: unsignedCharValue
     * @Declaration : - (unsigned char)unsignedCharValue
     * @Description : Returns the receiver’s value as an unsigned char.
     **/
    
    public char unsignedCharValue() {
        return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as a short, converting it as necessary.
     * @Signature: shortValue
     * @Declaration : - (short)shortValue
     * @Description : Returns the receiver’s value as a short.
     **/
    
    public short shortValue() {
        return Short.valueOf(numberValue);
    }

    /**
     * @return Return Value The receiver’s value as an unsigned short, converting it as necessary.
     * @Signature: unsignedShortValue
     * @Declaration : - (unsigned short)unsignedShortValue
     * @Description : Returns the receiver’s value as an unsigned short.
     **/
    
    public short unsignedShortValue() {
        return Short.valueOf(numberValue);
    }

    /**
     * @return Return Value The receiver’s value as an int, converting it as necessary.
     * @Signature: intValue
     * @Declaration : - (int)intValue
     * @Description : Returns the receiver’s value as an int.
     **/
    
    public int intValue() {
        try {
            if (objCType != 'c')
                return Integer.valueOf(numberValue);
            else
                return numberValue.charAt(0);
        } catch (NumberFormatException e) {
            if ("true".equals(numberValue)) {
                return 1;
            } else if ("false".equals(numberValue)) {
                return 0;
            }
            return 0;
        }

    }

    public int gteIntValue() {
        return intValue();

    }

    /**
     * @return Return Value The receiver’s value as an unsigned int, converting it as necessary.
     * @Signature: unsignedIntValue
     * @Declaration : - (unsigned int)unsignedIntValue
     * @Description : Returns the receiver’s value as an unsigned int.
     **/
    
    public int unsignedIntValue() {
        if (objCType != 'c')
            return Integer.valueOf(numberValue);
        else
            return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as a long, converting it as necessary.
     * @Signature: longValue
     * @Declaration : - (long)longValue
     * @Description : Returns the receiver’s value as a long.
     **/
    
    public long longValue() {
        if (objCType != 'c') {
            return Long.valueOf(longValue);
        } else {
            return numberValue.charAt(0);
        }

    }

    /**
     * @return Return Value The receiver’s value as an unsigned long, converting it as necessary.
     * @Signature: unsignedLongValue
     * @Declaration : - (unsigned long)unsignedLongValue
     * @Description : Returns the receiver’s value as an unsigned long.
     **/
    
    public long unsignedLongValue() {
        if (objCType != 'c')
            return Long.valueOf(numberValue);
        else
            return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as a long long, converting it as necessary.
     * @Signature: longLongValue
     * @Declaration : - (long long)longLongValue
     * @Description : Returns the receiver’s value as a long long.
     **/
    
    public long longLongValue() {
        if (objCType != 'c')
            return Long.valueOf(numberValue);
        else
            return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as an unsigned long long, converting it as
     * necessary.
     * @Signature: unsignedLongLongValue
     * @Declaration : - (unsigned long long)unsignedLongLongValue
     * @Description : Returns the receiver’s value as an unsigned long long.
     **/
    
    public long unsignedLongLongValue() {
        if (objCType != 'c')
            return Long.valueOf(numberValue);
        else
            return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as a float, converting it as necessary.
     * @Signature: floatValue
     * @Declaration : - (float)floatValue
     * @Description : Returns the receiver’s value as a float.
     **/
    
    public float floatValue() {
        if (objCType != 'c')
            return Float.valueOf(numberValue);
        else
            return numberValue.charAt(0);
    }

    /**
     * @return Return Value The receiver’s value as a double, converting it as necessary.
     * @Signature: doubleValue
     * @Declaration : - (double)doubleValue
     * @Description : Returns the receiver’s value as a double.
     **/
    
    public double doubleValue() {
        if (Double.doubleToRawLongBits(doubleValue)==Double.MIN_VALUE) {
            if (objCType != 'c') {
                if (numberValue.length() > 0) {
                    return Double.valueOf(numberValue);
                } else
                    return -1;

            } else {
                return numberValue.charAt(0);
            }
        } else {
            return doubleValue;
        }

    }

    /**
     * @return Return Value The receiver’s value as a BOOL, converting it as necessary.
     * @Signature: boolValue
     * @Declaration : - (BOOL)boolValue
     * @Description : Returns the receiver’s value as a BOOL.
     **/
    
    public boolean boolValue() {
        int value = integerValue();
        return value == 0 ? false : true;
    }

    /**
     * @return Return Value The receiver’s value as an NSInteger, converting it as necessary.
     * @Signature: integerValue
     * @Declaration : - (NSInteger)integerValue
     * @Description : Returns the receiver’s value as an NSInteger.
     **/
    
    public int integerValue() {
        int pValue = 0;
        if ("true".equals(numberValue)) {
            pValue = 1;
        } else if ("false".equals(numberValue)) {
            pValue = 0;
        } else {
            try {
                pValue = Integer.valueOf(numberValue);
            } catch (NumberFormatException e) {
                Logger.getLogger("context",String.valueOf(e));
                try {
                    pValue = Integer.parseInt(numberValue);
                } catch (NumberFormatException e1) {
                    Logger.getLogger("context", String.valueOf(e1));
                }
            }
        }
        return pValue;
    }

    public int getIntegerValue() {
        return integerValue();
    }

    /**
     * @return Return Value The receiver’s value as an NSUInteger, converting it as necessary.
     * @Signature: unsignedIntegerValue
     * @Declaration : - (NSUInteger)unsignedIntegerValue
     * @Description : Returns the receiver’s value as an NSUInteger.
     **/
    
    public int unsignedIntegerValue() {
        return Integer.valueOf(numberValue);
    }

    /**
     * @return Return Value The receiver’s value as a human-readable string, created by invoking
     * descriptionWithLocale: where locale is nil.
     * @Signature: stringValue
     * @Declaration : - (NSString *)stringValue
     * @Description : Returns the receiver’s value as a human-readable string.
     **/
    
    public NSString stringValue() {
        return new NSString(numberValue);
    }

    /**
     * @param aNumber The number with which to compare the receiver. This value must not be nil. If
     *                the value is nil, the behavior is
     *                undefined and may change in future versions of OS X.
     * @return Return Value NSOrderedAscending if the value of aNumber is greater than the
     * receiver’s, NSOrderedSame if they’re equal, and
     * NSOrderedDescending if the value of aNumber is less than the receiver’s.
     * @Signature: compare:
     * @Declaration : - (NSComparisonResult)compare:(NSNumber *)aNumber
     * @Description : Returns an NSComparisonResult value that indicates whether the receiver is
     * greater than, equal to, or less than a
     * given number.
     * @Discussion The compare: method follows the standard C rules for type conversion. For
     * example, if you compare an NSNumber object that
     * has an integer value with an NSNumber object that has a floating point value, the integer
     * value is converted to a
     * floating-point value for comparison.
     **/
    
    public NSComparisonResult compare(NSNumber otherNumber) {
        switch (compareTo(otherNumber)) {
            case 1:
                return NSComparisonResult.NSOrderedDescending;
            case -1:
                return NSComparisonResult.NSOrderedAscending;
            case 0:
                return NSComparisonResult.NSOrderedSame;
            default:
                break;
        }
        return null;
    }

    /**
     * @param aNumber The number with which to compare the receiver.
     * @return Return Value YES if the receiver and aNumber are equal, otherwise NO.
     * @Signature: isEqualToNumber:
     * @Declaration : - (BOOL)isEqualToNumber:(NSNumber *)aNumber
     * @Description : Returns a Boolean value that indicates whether the receiver and a given number
     * are equal.
     * @Discussion Two NSNumber objects are considered equal if they have the same id values or if
     * they have equivalent values (as
     * determined by the compare: method). This method is more efficient than compare: if you know
     * the two objects are numbers.
     **/
    
    public boolean isEqualToNumber(NSNumber number) {
        return numberValue.equals(number.numberValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NSNumber) {
            NSNumber nb = (NSNumber) o;
            return numberValue.equals(nb.numberValue);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        hashcode = numberValue.hashCode();
        return hashcode;
    }

    /**
     * @param aLocale An object containing locale information with which to format the description.
     *                Use nil if you don’t want the
     *                description formatted.
     * @return Return Value A string that represents the contents of the receiver formatted using
     * the locale information in locale.
     * @Signature: descriptionWithLocale:
     * @Declaration : - (NSString *)descriptionWithLocale:(id)aLocale
     * @Description : Returns a string that represents the contents of the receiver for a given
     * locale.
     * @Discussion For example, if you have an NSNumber object that has the integer value 522,
     * sending it the descriptionWithLocale: message
     * returns the string “522�?. To obtain the string representation, this method invokes NSString’s
     * initWithFormat:locale:
     * method, supplying the format based on the type the NSNumber object was created with: Data
     * Type Format Specification char
     * %i double %0.16g float %0.7g int %i long %li long long %lli short %hi unsigned char %u
     * unsigned int %u unsigned long %lu
     * unsigned long long %llu unsigned short %hu
     **/
    
    public NSString descriptionWithLocale(NSLocale locale) {
        NumberFormat nbrFormat = NumberFormat.getInstance(locale.getWrappedLocale());
        try {
            return new NSString(nbrFormat.parse(numberValue).toString());
        } catch (ParseException e) {
            Logger.getLogger(NSNumber.class.getName(),String.valueOf(e));
        }
        return null;
    }

    /**
     * @return Return Value A C string containing the Objective-C type of the data contained in the
     * receiver, as encoded by the @encode()
     * compiler directive.
     * @Signature: objCType
     * @Declaration : - (const char *)objCType
     * @Description : Returns a C string containing the Objective-C type of the data contained in
     * the receiver.
     **/
    @Override
    
    public String objCType() {
        return getObjectType();
    }

    @Override
    public int compareTo(NSNumber another) {
        if (numberValue != null && another.numberValue != null) {
            if (Double.valueOf(numberValue) > Double.valueOf(another.numberValue))
                return 1;
            if (Double.valueOf(numberValue) < Double.valueOf(another.numberValue))
                return -1;
            else
                return 0;
        }
        return -2;

    }

    /**
     * Indicates that the number's value is an integer. The number is stored as a Java
     * <code>long</code>. Its original value could have been
     * char, short, int, long or even long long.
     */
    public static final int INTEGER = 0;

    /**
     * Indicates that the number's value is a real number. The number is stored as a Java
     * <code>double</code>. Its original value could have
     * been float or double.
     */
    public static final int REAL = 1;

    /**
     * Indicates that the number's value is boolean.
     */
    public static final int BOOLEAN = 2;

    // Holds the current type of this number
    private int type;

    private long longValue = Long.MIN_VALUE;

    
    public double doubleValue = Double.MIN_VALUE;

    
    public boolean boolValue = false;

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public long getLongLongValue() {
        return longLongValue;
    }

    public void setLongLongValue(long longLongValue) {
        this.longLongValue = longLongValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public char getUnsignedCharValue() {
        return unsignedCharValue;
    }

    public void setUnsignedCharValue(char unsignedCharValue) {
        this.unsignedCharValue = unsignedCharValue;
    }

    public Integer getUnsignedIntegerValue() {
        return unsignedIntegerValue;
    }

    public void setUnsignedIntegerValue(Integer unsignedIntegerValue) {
        this.unsignedIntegerValue = unsignedIntegerValue;
    }

    public int getUnsignedIntValue() {
        return unsignedIntValue;
    }

    public void setUnsignedIntValue(int unsignedIntValue) {
        this.unsignedIntValue = unsignedIntValue;
    }

    public long getUnsignedLongLongValue() {
        return unsignedLongLongValue;
    }

    public void setUnsignedLongLongValue(long unsignedLongLongValue) {
        this.unsignedLongLongValue = unsignedLongLongValue;
    }

    public long getUnsignedLongValue() {
        return unsignedLongValue;
    }

    public void setUnsignedLongValue(long unsignedLongValue) {
        this.unsignedLongValue = unsignedLongValue;
    }

    public short getUnsignedShortValue() {
        return unsignedShortValue;
    }

    public void setUnsignedShortValue(short unsignedShortValue) {
        this.unsignedShortValue = unsignedShortValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int intValue;
    private Integer integerValue;
    private long longLongValue;
    private short shortValue;
    private char unsignedCharValue;
    private Integer unsignedIntegerValue;
    private int unsignedIntValue;
    private long unsignedLongLongValue;
    private long unsignedLongValue;
    private short unsignedShortValue;

    // use this or use object from NSValue
    protected String numberValue = "";

    /**
     * Parses integers and real numbers from their binary representation. <i>Note: real numbers are
     * not yet supported.</i>
     *
     * @param bytes The binary representation
     * @param type  The type of number
     * @see #INTEGER
     * @see #REAL
     */
    public NSNumber(byte[] bytes, int type) {
        switch (type) {
            case INTEGER: {
                doubleValue = longValue = BinaryPropertyListParser.parseLong(bytes);
                this.numberValue = doubleValue + "";
                break;
            }
            case REAL: {
                doubleValue = BinaryPropertyListParser.parseDouble(bytes);
                longValue = Math.round(doubleValue);
                this.numberValue = doubleValue + "";
                break;
            }
            default: {
                throw new IllegalArgumentException("Type argument is not valid.");
            }
        }
        this.type = type;
    }



    /**
     * Creates a number from its textual representation.
     *
     * @param text The textual representation of the number.
     * @throws IllegalArgumentException If the text does not represent an integer, real number or
     *                                  boolean value.
     * @see Boolean#parseBoolean(String)
     * @see Long#parseLong(String)
     * @see Double#parseDouble(String)
     */
    public NSNumber(String text) {
        if (text == null)
            throw new IllegalArgumentException(
                    "The given string is null and cannot be parsed as number.");
        try {
            numberValue = text;
            long l = Long.parseLong(text);
            doubleValue = longValue = l;
            objCType = 'i';
            type = INTEGER;
        } catch (Exception ex) {
            try {
                doubleValue = Double.parseDouble(text);
                longValue = Math.round(doubleValue);
                type = REAL;
                objCType = 'd';
            } catch (Exception ex2) {
                try {
                    boolValue = "true".equals(text.toLowerCase())
                            || "yes".equals(text.toLowerCase());
                    if (!boolValue && !("false".equals(text.toLowerCase())
                            || "no".equals(text.toLowerCase()))) {
                        throw new Exception("not a boolean");
                    }
                    type = BOOLEAN;
                    doubleValue = longValue = boolValue ? 1 : 0;
                    objCType = 'c';
                } catch (Exception ex3) {
                    throw new IllegalArgumentException(
                            "The given string neither represents a double, an int nor a boolean value.");
                }
            }
        }
        synchronizeValues();
    }

    private void synchronizeValues() {

    }

    /**
     * Creates an integer number.
     *
     * @param i The integer value.
     */
    public NSNumber(int i) {
        this.doubleValue = i;
        this.longValue = i;
        this.numberValue = i + "";
        type = INTEGER;
    }

    /**
     * Creates an integer number.
     *
     * @param l The long integer value.
     */
    public NSNumber(long l) {
        doubleValue = longValue = l;
        this.numberValue = l + "";
        type = INTEGER;
    }

    /**
     * Creates a real number.
     *
     * @param d The real value.
     */
    public NSNumber(double d) {
        longValue = (long) (doubleValue = d);
        this.numberValue = d + "";
        type = REAL;
    }

    /**
     * Creates a boolean number.
     *
     * @param b The boolean value.
     */
    public NSNumber(boolean b) {
        boolValue = b;
        doubleValue = longValue = b ? 1 : 0;
        this.numberValue = b + "";
        type = BOOLEAN;
    }

    /**
     * Gets the type of this number's value.
     *
     * @return The type flag.
     * @see #BOOLEAN
     * @see #INTEGER
     * @see #REAL
     */
    public int type() {
        return type;
    }

    /**
     * Checks whether the value of this NSNumber is a boolean.
     *
     * @return Whether the number's value is a boolean.
     */
    public boolean isBoolean() {
        return type == BOOLEAN;
    }

    /**
     * Checks whether the value of this NSNumber is an integer.
     *
     * @return Whether the number's value is an integer.
     */
    public boolean isInteger() {
        return type == INTEGER;
    }

    /**
     * Checks whether the value of this NSNumber is a real number.
     *
     * @return Whether the number's value is a real number.
     */
    public boolean isReal() {
        return type == REAL;
    }

    @Override
    public String toString() {
        switch (type) {
            case INTEGER: {
                return String.valueOf(longValue());
            }
            case REAL: {
                return String.valueOf(doubleValue());
            }
            case BOOLEAN: {
                return String.valueOf(boolValue());
            }
            default: {
                return super.toString();
            }
        }
    }

    @Override
    public void toXML(StringBuilder xml, int level) {
        indent(xml, level);
        switch (type) {
            case INTEGER: {
                xml.append("<integer>");
                xml.append(longValue());
                xml.append("</integer>");
                break;
            }
            case REAL: {
                xml.append("<real>");
                xml.append(doubleValue());
                xml.append("</real>");
                break;
            }
            case BOOLEAN: {
                if (boolValue())
                    xml.append("<true/>");
                else
                    xml.append("<false/>");
                break;
            }
        }
    }

    @Override
    public void toBinary(BinaryPropertyListWriter out) throws IOException {
        switch (type()) {
            case INTEGER: {
                if (longValue() < 0) {
                    out.write(0x13);
                    out.writeBytes(longValue(), 8);
                } else if (longValue() <= 0xff) {
                    out.write(0x10);
                    out.writeBytes(longValue(), 1);
                } else if (longValue() <= 0xffff) {
                    out.write(0x11);
                    out.writeBytes(longValue(), 2);
                } else if (longValue() <= 0xffffffffL) {
                    out.write(0x12);
                    out.writeBytes(longValue(), 4);
                } else {
                    out.write(0x13);
                    out.writeBytes(longValue(), 8);
                }
                break;
            }
            case REAL: {
                out.write(0x23);
                out.writeDouble(doubleValue());
                break;
            }
            case BOOLEAN: {
                out.write(boolValue() ? 0x09 : 0x08);
                break;
            }
        }
    }

    @Override
    public void toASCII(StringBuilder ascii, int level) {
        // Although Apple's documentation does not explicitly say so, unqouted
        // strings are also allowed in ASCII property lists
        // Use this to represent numbers which anyhow contain now spaces, so
        // that a clearer distinction between numbers and strings is made
        // Also booleans are represented as YES and NO as in GnuStep
        indent(ascii, level);
        if (type == BOOLEAN) {
            ascii.append(boolValue ? "YES" : "NO");
        } else {
            ascii.append(toString());
        }
    }

    @Override
    public void toASCIIGnuStep(StringBuilder ascii, int level) {
        indent(ascii, level);
        switch (type) {
            case INTEGER: {
                ascii.append("<*I");
                ascii.append(toString());
                ascii.append(">");
                break;
            }
            case REAL: {
                ascii.append("<*R");
                ascii.append(toString());
                ascii.append(">");
                break;
            }
            case BOOLEAN: {
                if (boolValue) {
                    ascii.append("<*BY>");
                } else {
                    ascii.append("<*BN>");
                }
            }

            default:break;
        }
    }

    public Object getValue() {

        if (this.objCType == 'd') {
            return this.doubleValue();
        } else if (this.objCType == 'i') {
            return this.longValue();
        } else if (this.objCType == 'f') {
            return this.floatValue();
        }
        return null;
    }

}