package com.myappconverter.java.foundations;

public abstract class NSValueTransformer extends NSObject {

	NSString NSNegateBooleanTransformerName = new NSString("NSNegateBooleanTransformerName");
	NSString NSIsNilTransformerName = new NSString("NSIsNilTransformerName");
	NSString NSIsNotNilTransformerName = new NSString("NSIsNotNilTransformerName");
	NSString NSUnarchiveFromDataTransformerName = new NSString("NSUnarchiveFromDataTransformerName");
	NSString NSKeyedUnarchiveFromDataTransformerName = new NSString("NSKeyedUnarchiveFromDataTransformerName");

	// Using Name-based Registry
	/**
	 * @Signature: setValueTransformer:forName:
	 * @Declaration : + (void)setValueTransformer:(NSValueTransformer *)transformer forName:(NSString *)name
	 * @Description : Registers the provided value transformer with a given identifier.
	 * @param transformer The transformer to register.
	 * @param name The name for transformer.
	 **/

	public static void setValueTransformerForName(NSValueTransformer transformer, NSString name) {

	}

	/**
	 * @Signature: valueTransformerForName:
	 * @Declaration : + (NSValueTransformer *)valueTransformerForName:(NSString *)name
	 * @Description : Returns the value transformer identified by a given identifier.
	 * @param name The transformer identifier.
	 * @return Return Value The value transformer identified by name in the shared registry, or nil if not found.
	 * @Discussion If valueTransformerForName: does not find a registered transformer instance for name, it will attempt to find a class
	 *             with the specified name. If a corresponding class is found an instance will be created and initialized using its init:
	 *             method and then automatically registered with name.
	 **/

	public static NSValueTransformer valueTransformerForName(NSString name) {
		return null;
	}

	/**
	 * @Signature: valueTransformerNames
	 * @Declaration : + (NSArray *)valueTransformerNames
	 * @Description : Returns an array of all the registered value transformers.
	 * @return Return Value An array of all the registered value transformers.
	 **/

	public static NSArray valueTransformerNames() {
		return null;
	}

	// Getting Information About a Transformer
	/**
	 * @Signature: allowsReverseTransformation
	 * @Declaration : + (BOOL)allowsReverseTransformation
	 * @Description : Returns a Boolean value that indicates whether the receiver can reverse a transformation.
	 * @return Return Value YES if the receiver supports reverse value transformations, otherwise NO. The default is YES.
	 * @Discussion Subclasses should override this method to return NO if they do not support reverse value transformations.
	 **/

	public static boolean allowsReverseTransformation() {
		return false;
	}

	/**
	 * @Signature: transformedValueClass
	 * @Declaration : + (Class)transformedValueClass
	 * @Description : Returns the class of the value returned by the receiver for a forward transformation.
	 * @return Return Value The class of the value returned by the receiver for a forward transformation.
	 * @Discussion A subclass should override this method to return the appropriate class.
	 **/

	public static Class<?> transformedValueClass() {
		return null;
	}

	// Using Transformers
	/**
	 * @Signature: transformedValue:
	 * @Declaration : - (id)transformedValue:(id)value
	 * @Description : Returns the result of transforming a given value.
	 * @param value The value to transform.
	 * @return Return Value The result of transforming value. The default implementation simply returns value.
	 * @Discussion A subclass should override this method to transform and return an object based on value.
	 **/

	public abstract void transformedValue(NSObject value);

	/**
	 * @Signature: reverseTransformedValue:
	 * @Declaration : - (id)reverseTransformedValue:(id)value
	 * @Description : Returns the result of the reverse transformation of a given value.
	 * @param value The value to reverse transform.
	 * @return Return Value The reverse transformation of value.
	 * @Discussion The default implementation raises an exception if allowsReverseTransformation returns NO; otherwise it will invoke
	 *             transformedValue: with value. A subclass should override this method if they require a reverse transformation that is not
	 *             the same as simply reapplying the original transform (as would be the case with negation, for example). For example, if a
	 *             value transformer converts a value in Fahrenheit to Celsius, this method would converts a value from Celsius to
	 *             Fahrenheit.
	 **/

	public abstract NSObject reverseTransformedValue(NSObject value);

}