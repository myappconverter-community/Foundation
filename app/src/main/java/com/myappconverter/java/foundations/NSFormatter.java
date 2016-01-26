
package com.myappconverter.java.foundations;




public abstract class NSFormatter extends NSObject {

	/**
	 * @Declaration : - (NSString *)stringForObjectValue:(id)anObject
	 * @Description : The default implementation of this method raises an exception.
	 * @param anObject The object for which a textual representation is returned.
	 * @return Return Value An NSString object that textually represents object for display. Returns nil if object is not of the correct
	 *         class.
	 * @Discussion When implementing a subclass, return the NSString object that textually represents the cell’s object for display and—if
	 *             editingStringForObjectValue: is unimplemented—for editing. First test the passed-in object to see if it’s of the correct
	 *             class. If it isn’t, return nil; but if it is of the right class, return a properly formatted and, if necessary, localized
	 *             string. (See the specification of the NSString class for formatting and localizing details.) The following implementation
	 *             (which is paired with the getObjectValue:forString:errorDescription: example above) prefixes a two-digit float
	 *             representation with a dollar sign: - (NSString *)stringForObjectValue:(id)anObject { if (![anObject
	 *             isKindOfClass:[NSNumber class]]) { return nil; } return [NSString stringWithFormat:@"$%.2f", [anObject floatValue]]; }
	 **/
	
	public abstract NSString stringForObjectValue(NSObject obj);

	/**
	 * @Declaration : - (NSAttributedString *)attributedStringForObjectValue:(id)anObject withDefaultAttributes:(NSDictionary *)attributes
	 * @Description : The default implementation returns nil to indicate that the formatter object does not provide an attributed string.
	 * @param anObject The object for which a textual representation is returned.
	 * @param attributes The default attributes to use for the returned attributed string.
	 * @return Return Value An attributed string that represents anObject.
	 * @Discussion When implementing a subclass, return an NSAttributedString object if the string for display should have some attributes.
	 *             For instance, you might want negative values in a financial application to appear in red text. Invoke your implementation
	 *             of stringForObjectValue: to get the non-attributed string, then create an NSAttributedString object with it (see
	 *             initWithString:). Use the attributes default dictionary to reset the attributes of the string when a change in value
	 *             warrants it (for example, a negative value becomes positive) For information on creating attributed strings, see
	 *             Attributed String Programming Guide.
	 **/
	
	public abstract NSAttributedString attributedStringForObjectValueWithDefaultAttributes(NSObject anObject, NSDictionary<?, ?> attributes);

	/**
	 * @Declaration : - (NSString *)editingStringForObjectValue:(id)anObject
	 * @Description : The default implementation of this method invokes stringForObjectValue:.
	 * @param anObject The object for which to return an editing string.
	 * @return Return Value An NSString object that is used for editing the textual representation of anObject.
	 * @Discussion When implementing a subclass, override this method only when the string that users see and the string that they edit are
	 *             different. In your implementation, return an NSString object that is used for editing, following the logic recommended
	 *             for implementing stringForObjectValue:. As an example, you would implement this method if you want the dollar signs in
	 *             displayed strings removed for editing.
	 **/
	
	public abstract NSString editingStringForObjectValue(NSObject obj);

	/**
	 * @Declaration : - (BOOL)getObjectValue:(id *)anObject forString:(NSString *)string errorDescription:(NSString **)error
	 * @Description : The default implementation of this method raises an exception.
	 * @param anObject If conversion is successful, upon return contains the object created from string.
	 * @param string The string to parse.
	 * @param error If non-nil, if there is a error during the conversion, upon return contains an NSString object that describes the
	 *            problem.
	 * @return Return Value YES if the conversion from string to cell content object was successful, otherwise NO.
	 * @Discussion When implementing a subclass, return by reference the object anObject after creating it from string. Return YES if the
	 *             conversion is successful. If you return NO, also return by indirection (in error) a localized user-presentable NSString
	 *             object that explains the reason why the conversion failed; the delegate (if any) of the NSControl object managing the
	 *             cell can then respond to the failure in control:didFailToFormatString:errorDescription:. However, if error is nil, the
	 *             sender is not interested in the error description, and you should not attempt to assign one.
	 **/
	
	public abstract boolean getObjectValueForStringErrorDescription(NSObject[] anObject, NSString string, NSString[] error);

	/**
	 * @Declaration : - (BOOL)isPartialStringValid:(NSString *)partialString newEditingString:(NSString **)newString
	 *              errorDescription:(NSString **)error
	 * @Description : Returns a Boolean value that indicates whether a partial string is valid.
	 * @param partialString The text currently in a cell.
	 * @param newString If partialString needs to be modified, upon return contains the replacement string.
	 * @param error If non-nil, if validation fails contains an NSString object that describes the problem.
	 * @return Return Value YES if partialString is an acceptable value, otherwise NO.
	 * @Discussion This method is invoked each time the user presses a key while the cell has the keyboard focus—it lets you verify and edit
	 *             the cell text as the user types it. In a subclass implementation, evaluate partialString according to the context, edit
	 *             the text if necessary, and return by reference any edited string in newString. Return YES if partialString is acceptable
	 *             and NO if partialString is unacceptable. If you return NO and newString is nil, the cell displays partialString minus the
	 *             last character typed. If you return NO, you can also return by indirection an NSString object (in error) that explains
	 *             the reason why the validation failed; the delegate (if any) of the NSControl object managing the cell can then respond to
	 *             the failure in control:didFailToValidatePartialString:errorDescription:. The selection range will always be set to the
	 *             end of the text if replacement occurs. This method is a compatibility method. If a subclass overrides this method and
	 *             does not override isPartialStringValid:proposedSelectedRange:originalString:originalSelectedRange:errorDescription:, this
	 *             method will be called as before
	 *             (isPartialStringValid:proposedSelectedRange:originalString:originalSelectedRange:errorDescription: just calls this one by
	 *             default).
	 **/
	
	public abstract boolean isPartialStringValidNewEditingStringErrorDescription(NSString partialString, NSString[] newString,
			NSString[] error);

	/**
	 * @Declaration : - (BOOL)isPartialStringValid:(NSString **)partialStringPtr proposedSelectedRange:(NSRangePointer)proposedSelRangePtr
	 *              originalString:(NSString *)origString originalSelectedRange:(NSRange)origSelRange errorDescription:(NSString **)error
	 * @Description : This method should be implemented in subclasses that want to validate user changes to a string in a field, where the
	 *              user changes are not necessarily at the end of the string, and preserve the selection (or set a different one, such as
	 *              selecting the erroneous part of the string the user has typed).
	 * @param partialStringPtr The new string to validate.
	 * @param proposedSelRangePtr The selection range that will be used if the string is accepted or replaced.
	 * @param origString The original string, before the proposed change.
	 * @param origSelRange The selection range over which the change is to take place.
	 * @param error If non-nil, if validation fails contains an NSString object that describes the problem.
	 * @return Return Value YES if partialStringPtr is acceptable, otherwise NO.
	 * @Discussion In a subclass implementation, evaluate partialString according to the context. Return YES if partialStringPtr is
	 *             acceptable and NO if partialStringPtr is unacceptable. Assign a new string to partialStringPtr and a new range to
	 *             proposedSelRangePtr and return NO if you want to replace the string and change the selection range. If you return NO, you
	 *             can also return by indirection an NSString object (in error) that explains the reason why the validation failed; the
	 *             delegate (if any) of the NSControl object managing the cell can then respond to the failure in
	 *             control:didFailToValidatePartialString:errorDescription:.
	 **/
	
	public abstract boolean isPartialStringValidProposedSelectedRangeOriginalStringOriginalSelectedRangeErrorDescription(
			NSString partialStringPtr, NSRangePointer proposedSelRangePtr, NSString origString, NSRange origSelRange, NSString error);
}