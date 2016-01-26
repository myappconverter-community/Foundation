
package com.myappconverter.java.foundations;

import java.awt.font.TextAttribute;
import java.util.Iterator;
import java.util.Map;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

public class NSMutableAttributedString extends NSAttributedString {

	
	private NSMutableString nsMutableString;

	public NSMutableAttributedString() {
		setSpannableString(new SpannableStringBuilder(""));
	}

	public NSMutableAttributedString(NSString aString) {

		setSpannableString(new SpannableStringBuilder(aString.getWrappedString().toString()));

	}

	public NSMutableAttributedString(NSAttributedString atrString) {

		this.setSpannableString(atrString.getSpannableString());
		this.setAttributes(atrString.getAttributes());
		this.ranges = atrString.ranges;
	}


	/********************************* Creating an NSAttributedString Object **********************************/
	// – initWithString:
	public NSMutableAttributedString initWithString(NSString aString) {
		return new NSMutableAttributedString(aString);

	}

	// – initWithAttributedString:
	public NSMutableAttributedString initWithAttributedString(NSAttributedString atrString) {
		NSMutableAttributedString  ms = new NSMutableAttributedString(atrString);
		return ms;
	}

	/********************************* Retrieving Character Information **********************************/
	/**
	 * @Signature: mutableString
	 * @Declaration : - (NSMutableString *)mutableString
	 * @Description : Returns the character contents of the receiver as an NSMutableString object.
	 * @return Return Value The mutable string object.
	 * @Discussion The receiver tracks changes to this string and keeps its attribute mappings up to date.
	 **/
	
	public NSMutableString mutableString() {
		return this.nsMutableString;
	}

	/********************************* Changing Characters **********************************/
	/**
	 * @Signature: replaceCharactersInRange:withAttributedString:
	 * @Declaration : - (void)replaceCharactersInRange:(NSRange)aRange withAttributedString:(NSAttributedString *)attributedString
	 * @Description : Replaces the characters and attributes in a given range with the characters and attributes of the given attributed
	 *              string.
	 * @param aRange The range of characters and attributes replaced.
	 * @param attributedString The attributed string whose characters and attributes replace those in the specified range.
	 * @Discussion Raises an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void replaceCharactersInRangeWithAttributedString(NSRange aRange, NSAttributedString attributedString) {
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		if(attributedString != null ){
			getSpannableString().replace(start, end, attributedString.spannableString);
		}
	}

	/**
	 * @Signature: deleteCharactersInRange:
	 * @Declaration : - (void)deleteCharactersInRange:(NSRange)aRange
	 * @Description : Deletes the characters in the given range along with their associated attributes.
	 * @param aRange A range specifying the characters to delete.
	 * @Discussion Raises an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void deleteCharactersInRange(NSRange aRange) {
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		getSpannableString().replace(start, end, "");
	}

	/********************************* Changing Attributes **********************************/
	/**
	 * @Signature: setAttributes:range:
	 * @Declaration : - (void)setAttributes:(NSDictionary *)attributes range:(NSRange)aRange
	 * @Description : Sets the attributes for the characters in the specified range to the specified attributes.
	 * @param attributes A dictionary containing the attributes to set. Attribute keys can be supplied by another framework or can be custom
	 *            ones you define. For information about where to find the system-supplied attribute keys, see the overview section in
	 *            NSAttributedString Class Reference.
	 * @param aRange The range of characters whose attributes are set.
	 * @Discussion These new attributes replace any attributes previously associated with the characters in aRange. Raises an
	 *             NSRangeException if any part of aRange lies beyond the end of the receiver’s characters. To set attributes for a
	 *             zero-length NSMutableAttributedString displayed in a text view, use the NSTextView method setTypingAttributes:.
	 **/
	
	public void setAttributesRange(NSDictionary attributes, NSRange aRange) {
		
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		this.ranges.put(attributes, aRange);
		 Iterator it = attributes.getWrappedDictionary().entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if(pair.getKey().toString().equals(NSBackgroundColorAttributeName.getWrappedString())){
		        	getSpannableString().setSpan(new BackgroundColorSpan((int) pair.getValue()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSForegroundColorAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new ForegroundColorSpan((int) pair.getValue()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSUnderlineStyleAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSUnderlineColorAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSStrikethroughStyleAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new StrikethroughSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSLinkAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new URLSpan(pair.getValue().toString()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSFontAttributeName.getWrappedString())){
			    	   //Bridged
			       } 
		        it.remove();
		    }
	}

	/**
	 * @Signature: addAttribute:value:range:
	 * @Declaration : - (void)addAttribute:(NSString *)name value:(id)value range:(NSRange)aRange
	 * @Description : Adds an attribute with the given name and value to the characters in the specified range.
	 * @param name A string specifying the attribute name. Attribute keys can be supplied by another framework or can be custom ones you
	 *            define. For information about where to find the system-supplied attribute keys, see the overview section in
	 *            NSAttributedString Class Reference.
	 * @param value The attribute value associated with name.
	 * @param aRange The range of characters to which the specified attribute/value pair applies.
	 * @Discussion You may assign any name/value pair you wish to a range of characters, in addition to the standard attributes described in
	 *             the “Constants section of NSAttributedString Additions. Raises an NSInvalidArgumentException if name or value is nil and
	 *             an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void addAttributeValueRange(NSString name, Object value, NSRange aRange) {

		int start = aRange.location;
		int end = aRange.location + aRange.length;
		this.ranges.wrappedDictionary.put(value, aRange);
		 if(name.getWrappedString().toString().equals(NSBackgroundColorAttributeName.getWrappedString())){
	    	getSpannableString().setSpan(new BackgroundColorSpan((int) value),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	      } 
	     if(name.getWrappedString().toString().equals(NSForegroundColorAttributeName.getWrappedString())){
	    	getSpannableString().setSpan(new ForegroundColorSpan((int) value),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	      } 
	     if(name.getWrappedString().toString().equals(NSUnderlineStyleAttributeName.getWrappedString())){
	    	getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	     } 
	     if(name.getWrappedString().toString().equals(NSUnderlineColorAttributeName.getWrappedString())){
	    	   getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	      }
	     if(name.getWrappedString().toString().equals(NSFontAttributeName.getWrappedString())){
	    	  getSpannableString().setSpan(new TypefaceSpan("sans-serif"),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		  } 
	     if(name.getWrappedString().toString().equals(NSStrikethroughStyleAttributeName.getWrappedString())){
	    	 getSpannableString().setSpan(new StrikethroughSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	       }
	     if(name.getWrappedString().toString().equals(NSLinkAttributeName.getWrappedString())){
	    	 getSpannableString().setSpan(new URLSpan(value.toString()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	       }
	}

	/**
	 * @Signature: addAttributes:range:
	 * @Declaration : - (void)addAttributes:(NSDictionary *)attributes range:(NSRange)aRange
	 * @Description : Adds the given collection of attributes to the characters in the specified range.
	 * @param attributes A dictionary containing the attributes to add. Attribute keys can be supplied by another framework or can be custom
	 *            ones you define. For information about where to find the system-supplied attribute keys, see the overview section in
	 *            NSAttributedString Class Reference.
	 * @param aRange The range of characters to which the specified attributes apply.
	 * @Discussion You may assign any name/value pair you wish to a range of characters, in addition to the standard attributes described in
	 *             the “Constants section of NSAttributedString Additions. Raises an NSInvalidArgumentException if attributes is nil and an
	 *             NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void addAttributeRange(NSDictionary attributes, NSRange aRange) {

		int start = aRange.location;
		int end = aRange.location + aRange.length;
		this.ranges.put(attributes, aRange);
		 Iterator it = attributes.getWrappedDictionary().entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if(pair.getKey().toString().equals(NSBackgroundColorAttributeName.getWrappedString())){
		        	getSpannableString().setSpan(new BackgroundColorSpan((int) pair.getValue()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSForegroundColorAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new ForegroundColorSpan((int) pair.getValue()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSUnderlineStyleAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       } 
			       if(pair.getKey().toString().equals(NSUnderlineColorAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new UnderlineSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSStrikethroughStyleAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new StrikethroughSpan(),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSLinkAttributeName.getWrappedString())){
			    	   getSpannableString().setSpan(new URLSpan(pair.getValue().toString()),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			       }
			       if(pair.getKey().toString().equals(NSFontAttributeName.getWrappedString())){
			    	  //Bridged
			       } 
		        it.remove();
		    }
	}

	/**
	 * @Signature: removeAttribute:range:
	 * @Declaration : - (void)removeAttribute:(NSString *)name range:(NSRange)aRange
	 * @Description : Removes the named attribute from the characters in the specified range.
	 * @param name A string specifying the attribute name to remove. Attribute keys can be supplied by another framework or can be custom
	 *            ones you define. For information about where to find the system-supplied attribute keys, see the overview section in
	 *            NSAttributedString Class Reference.
	 * @param aRange The range of characters from which the specified attribute is removed.
	 * @Discussion Raises an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void removeAttributeRange(NSString name, NSRange aRange) {
		
		if(ranges.containsKey(aRange)){
			getSpannableString().removeSpan(ranges.get(aRange));
		}
	}

	/********************************* Changing Characters and Attributes **********************************/
	/**
	 * @Signature: appendAttributedString:
	 * @Declaration : - (void)appendAttributedString:(NSAttributedString *)attributedString
	 * @Description : Adds the characters and attributes of a given attributed string to the end of the receiver.
	 * @param attributedString The string whose characters and attributes are added.
	 **/

	
	public void appendAttributedString(NSAttributedString attributedString) {
	
		if(attributedString != null){
			getSpannableString().append(attributedString.getSpannableString());
		}
	}

	/**
	 * @Signature: insertAttributedString:atIndex:
	 * @Declaration : - (void)insertAttributedString:(NSAttributedString *)attributedString atIndex:(NSUInteger)index
	 * @Description : Inserts the characters and attributes of the given attributed string into the receiver at the given index.
	 * @param attributedString The string whose characters and attributes are inserted.
	 * @param index The index at which the characters and attributes are inserted.
	 * @Discussion The new characters and attributes begin at the given index and the existing characters and attributes from the index to
	 *             the end of the receiver are shifted by the length of the attributed string. Raises an NSRangeException if index lies
	 *             beyond the end of the receiver’s characters.
	 **/
	
	public void insertAttributedStringAtIndex(NSAttributedString attributedString, long index) {
		if(attributedString != null){
			getSpannableString().insert((int)index, attributedString.getSpannableString());
		}
	}

	/**
	 * @Signature: replaceCharactersInRange:withString:
	 * @Declaration : - (void)replaceCharactersInRange:(NSRange)aRange withString:(NSString *)aString
	 * @Description : Replaces the characters in the given range with the characters of the given string.
	 * @param aRange A range specifying the characters to replace.
	 * @param aString A string specifying the characters to replace those in aRange.
	 * @Discussion The new characters inherit the attributes of the first replaced character from aRange. Where the length of aRange is 0,
	 *             the new characters inherit the attributes of the character preceding aRange if it has any, otherwise of the character
	 *             following aRange. Raises an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters.
	 **/
	
	public void replaceCharactersInRangeWithString(NSRange aRange, NSString aString) {
		int start = aRange.location;
		int end = aRange.location + aRange.length;
		if(aString != null){
			getSpannableString().replace(start, end, aString.getWrappedString().toString());
		}

	}

	/**
	 * @Signature: setAttributedString:
	 * @Declaration : - (void)setAttributedString:(NSAttributedString *)attributedString
	 * @Description : Replaces the receiver’s entire contents with the characters and attributes of the given attributed string.
	 * @param attributedString The attributed string whose characters and attributes replace those in the receiver.
	 **/
	
	public void setAttributedString(NSAttributedString attributedString) {
		this.setSpannableString(attributedString.getSpannableString());
		this.setAttributes(attributedString.getAttributes());
		this.ranges = attributedString.ranges;
	}

	/********************************* Grouping Changes **********************************/
	/**
	 * @Signature: beginEditing
	 * @Declaration : - (void)beginEditing
	 * @Description : Overridden by subclasses to buffer or optimize a series of changes to the receiver’s characters or attributes, until
	 *              it receives a matching endEditing message, upon which it can consolidate changes and notify any observers that it has
	 *              changed.
	 * @Discussion You can nest pairs of beginEditing and endEditing messages.
	 **/
	
	public void beginEditing() {
		// not yet covered
	}

	/**
	 * @Signature: endEditing
	 * @Declaration : - (void)endEditing
	 * @Description : Overridden by subclasses to consolidate changes made since a previous beginEditing message and to notify any observers
	 *              of the changes.
	 * @Discussion The NSMutableAttributedString implementation does nothing. NSTextStorage, for example, overrides this method to invoke
	 *             fixAttributesInRange: and to inform its NSLayoutManager objects that they need to re-lay the text.
	 **/
	
	public void endEditing() {
		// not yet covered
	}

	public TextAttribute getEquivalentAttribute(String iosAttr) {

		if ("NSAttachmentAttributeName".equals(iosAttr)) {
			return null;
		} else if ("NSBackgroundColorAttributeName".equals(iosAttr)) {
			return TextAttribute.BACKGROUND;

		} else if ("NSBaselineOffsetAttributeName".equals(iosAttr)) {
			return null;
		} else if ("NSFontAttributeName".equals(iosAttr)) {
			return TextAttribute.FONT;
		} else if ("NSForegroundColorAttributeName".equals(iosAttr)) {
			return TextAttribute.FOREGROUND;
		} else if ("NSKernAttributeName".equals(iosAttr)) {
			return TextAttribute.KERNING;
		} else if ("NSLigatureAttributeName".equals(iosAttr)) {
			return TextAttribute.LIGATURES;
		} else if ("NSLinkAttributeName".equals(iosAttr)) {
			return null;
		} else if ("NSParagraphStyleAttributeName".equals(iosAttr)) {
			return null;
		} else if ("NSSuperscriptAttributeName".equals(iosAttr)) {
			return TextAttribute.SUPERSCRIPT;
		} else if ("NSUnderlineStyleAttributeName".equals(iosAttr)) {
			return TextAttribute.UNDERLINE;
		}
		return null;

	}

}