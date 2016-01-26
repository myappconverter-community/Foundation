
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSAttributedString.NSAttributedStringBlock.usingBlockBlock;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.mapping.utils.InvokableHelper;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.Iterator;
import java.util.Map;


public class NSAttributedString extends NSObject implements NSCoding, NSCopying, NSMutableCopying {

	//members
    protected SpannableStringBuilder spannableString;
    private NSDictionary attributes;
    protected NSDictionary ranges= new NSDictionary();

    public NSDictionary getRanges() {
        return ranges;
    }

    public void setRanges(NSDictionary ranges) {
        this.ranges = ranges;
    }

    // Constants
	
	public static final NSString NSFontAttributeName = new NSString("NSFontAttributeName");
	
	public static final NSString NSParagraphStyleAttributeName = new NSString("NSParagraphStyleAttributeName");
	
	public static final NSString NSForegroundColorAttributeName = new NSString("NSForegroundColorAttributeName");
	
	public static final NSString NSBackgroundColorAttributeName = new NSString("NSBackgroundColorAttributeName");
	
	public static final NSString NSLigatureAttributeName = new NSString("NSLigatureAttributeName");
	
	public static final NSString NSKernAttributeName = new NSString("NSKernAttributeName");
	
	public static final NSString NSStrikethroughStyleAttributeName = new NSString("NSStrikethroughStyleAttributeName");
	
	public static final NSString NSUnderlineStyleAttributeName = new NSString("NSUnderlineStyleAttributeName");
	
	public static final NSString NSStrokeColorAttributeName = new NSString("NSStrokeColorAttributeName");
	
	public static final NSString NSStrokeWidthAttributeName = new NSString("NSStrokeWidthAttributeName");
	
	public static final NSString NSShadowAttributeName = new NSString("NSShadowAttributeName");
	
	public static final NSString NSTextEffectAttributeName = new NSString("NSTextEffectAttributeName");
	
	public static final NSString NSAttachmentAttributeName = new NSString("NSAttachmentAttributeName");
	
	public static final NSString NSLinkAttributeName = new NSString("NSLinkAttributeName");
	
	public static final NSString NSBaselineOffsetAttributeName = new NSString("NSBaselineOffsetAttributeName");
	
	public static final NSString NSUnderlineColorAttributeName = new NSString("NSUnderlineColorAttributeName");
	
	public static final NSString NSStrikethroughColorAttributeName = new NSString("NSStrikethroughColorAttributeName");
	
	public static final NSString NSObliquenessAttributeName = new NSString("NSObliquenessAttributeName");
	
	public static final NSString NSExpansionAttributeName = new NSString("NSExpansionAttributeName");
	
	public static final NSString NSWritingDirectionAttributeName = new NSString("NSWritingDirectionAttributeName");
	
	public static final NSString NSVerticalGlyphFormAttributeName = new NSString("NSVerticalGlyphFormAttributeName");

	// Enum
	
	public static enum NSAttributedStringEnumerationOptions {
		NSAttributedStringEnumerationReverse(1L << 1), //
		NSAttributedStringEnumerationLongestEffectiveRangeNotRequired(1L << 20);
		private long value;

		private NSAttributedStringEnumerationOptions(long v) {
			value = v;
		}

		public long getValue() {
			return value;
		}
	};

	public static interface NSAttributedStringBlock {

		public static interface usingBlockBlock {
			void perform(Object value, NSRange range, boolean stop);

			void perform(NSDictionary attrs, NSRange range, boolean stop);

			boolean perform(Object anObject, String string);
		}

	}

	
	public static enum NSUnderlineStyle {

		NSUnderlineStyleNone(0x00), //
		NSUnderlineStyleSingle(0x01), //
		NSUnderlineStyleThick(0x02), //
		NSUnderlineStyleDouble(0x09), //
		NSUnderlinePatternSolid(0x0000), //
		NSUnderlinePatternDot(0x0100), //
		NSUnderlinePatternDash(0x0200), //
		NSUnderlinePatternDashDot(0x0300), //
		NSUnderlinePatternDashDotDot(0x0400), //
		NSUnderlineByWord(0x8000);//
		private int value;

		private NSUnderlineStyle(int v) {
			value = v;
		}
	}

	public SpannableStringBuilder getSpannableString() {
		return spannableString;
	}

	public void setSpannableString(SpannableStringBuilder spannableString) {
		this.spannableString = spannableString;
	}


	public NSMutableString getMutableString() {
		return mutableString;
	}

	public NSMutableString mutableString() {
		return mutableString;
	}

	private NSMutableString mutableString;



	public NSDictionary getAttributes() {
		return attributes;
	}

	public void setAttributes(NSDictionary attributes) {
		this.attributes = attributes;
	}

	/**
	 * @Signature: initWithString:
	 * @Declaration : - (id)initWithString:(NSString *)aString
	 * @Description : Returns an NSAttributedString object initialized with the characters of a given string and no attribute information.
	 * @param aString The characters for the new object.
	 * @return Return Value An NSAttributedString object initialized with the characters of aString and no attribute information The
	 *         returned object might be different than the original receiver.
	 **/
	
	public NSObject initWithString(NSString aString) {
		NSAttributedString nsAttributedString = new NSAttributedString();
		SpannableStringBuilder wrSpannableString = new SpannableStringBuilder(aString.getWrappedString());
		nsAttributedString.setSpannableString(wrSpannableString);
		return nsAttributedString;
	}

	/**
	 * @Signature: initWithAttributedString:
	 * @Declaration : - (id)initWithAttributedString:(NSAttributedString *)attributedString
	 * @Description : Returns an NSAttributedString object initialized with the characters and attributes of another given attributed
	 *              string.
	 * @param attributedString An attributed string.
	 * @return Return Value An NSAttributedString object initialized with the characters and attributes of attributedString. The returned
	 *         object might be different than the original receiver.
	 **/
	
	public Object initWithAttributedString(NSAttributedString attributedString) {
		this.spannableString = attributedString.getSpannableString();
		this.ranges = attributedString.ranges;
		this.attributes = attributedString.getAttributes();
		return this;
	}

	/**
	 * @Signature: initWithString:attributes:
	 * @Declaration : - (id)initWithString:(NSString *)aString attributes:(NSDictionary *)attributes
	 * @Description : Returns an NSAttributedString object initialized with a given string and attributes.
	 * @param aString The string for the new attributed string.
	 * @param attributes The attributes for the new attributed string. For information about where to find the attribute keys you can
	 *            include in this dictionary, see the overview section of this document.
	 * @Discussion Returns an NSAttributedString object initialized with the characters of aString and the attributes of attributes. The
	 *             returned object might be different from the original receiver.
	 **/
	
	public NSAttributedString initWithStringAttributes(NSString aString, NSDictionary attributes) {
		if(aString != null && attributes != null) {
			NSAttributedString attributed = new NSAttributedString();
			attributed.setSpannableString(new SpannableStringBuilder(aString.getWrappedString()));
			attributed.setAttributes(attributes);
			Iterator it = attributes.getWrappedDictionary().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				resolveAttribute(pair.getKey(), pair.getValue(), attributed);
				if (pair.getKey().toString().equals(NSBackgroundColorAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new BackgroundColorSpan((int) pair.getValue()), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else if (pair.getKey().toString().equals(NSForegroundColorAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new ForegroundColorSpan((int) pair.getValue()), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else if (pair.getKey().toString().equals(NSUnderlineStyleAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new UnderlineSpan(), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else if (pair.getKey().toString().equals(NSUnderlineColorAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new UnderlineSpan(), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else if (pair.getKey().toString().equals(NSStrikethroughStyleAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new StrikethroughSpan(), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				else if (pair.getKey().toString().equals(NSLinkAttributeName.getWrappedString())) {
					attributed.getSpannableString().setSpan(new URLSpan(pair.getValue().toString()), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				//
				else if (pair.getKey().toString().equals(NSFontAttributeName.getWrappedString())) {
					try {
						attributed.getSpannableString().setSpan(new StyleSpan(((Typeface) InvokableHelper.invoke(pair.getValue(), "getTypeface")).getStyle()), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						attributed.getSpannableString().setSpan(new TypefaceSpan((String) InvokableHelper.invoke(pair.getValue(), "getMfontName")), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						attributed.getSpannableString().setSpan(new RelativeSizeSpan((float) InvokableHelper.invoke(pair.getValue(), "getFontSize")), 0, attributed.getSpannableString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				it.remove();
			}
			return attributed;
		}
		else
			return new NSAttributedString();
	}

	protected void resolveAttribute(Object key,Object value,NSAttributedString sstring) throws UnsupportedOperationException{

	}
	//
	// Retrieving Character Information

	/**
	 * @Signature: string
	 * @Declaration : - (NSString *)string
	 * @Description : Returns the character contents of the receiver as an NSString object.
	 * @return Return Value The character contents of the receiver as an NSString object.
	 * @Discussion This method doesn’t strip out attachment characters. For performance reasons, this method returns the current backing
	 *             store of the attributed string object. If you want to maintain a snapshot of this as you manipulate the returned string,
	 *             you should make a copy of the appropriate substring. This primitive method must guarantee efficient access to an
	 *             attributed string’s characters; subclasses should implement it to execute in O(1) time.
	 **/
	
	public NSString string() {
		String aString = "";
		aString = this.spannableString.toString();
		return new NSString(aString);

	}

	private NSString string;

	public NSString getString(){
		return string();
	}

	/**
	 * @Signature: length
	 * @Declaration : - (NSUInteger)length
	 * @Description : Returns the length of the receiver’s string object.
	 **/
	
	public int length() {
		String aString = "";
		aString = this.spannableString.toString();
		return aString.length();

	}

	private int length;

	public int getLength(){
		return length();
	}

	// Retrieving Attribute Information

	/**
	 * @Signature: attributesAtIndex:effectiveRange:
	 * @Declaration : - (NSDictionary *)attributesAtIndex:(NSUInteger)index effectiveRange:(NSRangePointer)aRange
	 * @Description : Returns the attributes for the character at a given index.
	 * @param index The index for which to return attributes. This value must lie within the bounds of the receiver.
	 * @param aRange Upon return, the range over which the attributes and values are the same as those at index. This range isn’t
	 *            necessarily the maximum range covered, and its extent is implementation-dependent. If you need the maximum range, use
	 *            attributesAtIndex:longestEffectiveRange:inRange:. If you don't need this value, pass NULL.
	 * @return Return Value The attributes for the character at index.
	 * @Discussion Raises an NSRangeException if index lies beyond the end of the receiver’s characters. For information about where to find
	 *             the attribute keys for the returned dictionary, see the overview section of this document.
	 **/
	
	public NSDictionary attributesAtIndexEffectiveRange(Integer index, NSRangePointer aRange) {

		//no such equivalent
		return null;

	}

	/**
	 * @Signature: attributesAtIndex:longestEffectiveRange:inRange:
	 * @Declaration : - (NSDictionary *)attributesAtIndex:(NSUInteger)index longestEffectiveRange:(NSRangePointer)aRange
	 *              inRange:(NSRange)rangeLimit
	 * @Description : Returns the attributes for the character at a given index, and by reference the range over which the attributes apply.
	 * @param index The index for which to return attributes. This value must not exceed the bounds of the receiver.
	 * @param aRange If non-NULL, upon return contains the maximum range over which the attributes and values are the same as those at
	 *            index, clipped to rangeLimit.
	 * @param rangeLimit The range over which to search for continuous presence of the attributes at index. This value must not exceed the
	 *            bounds of the receiver.
	 * @Discussion Raises an NSRangeException if index or any part of rangeLimit lies beyond the end of the receiver’s characters. If you
	 *             don’t need the range information, it’s far more efficient to use the attributesAtIndex:effectiveRange: method to retrieve
	 *             the attribute value. For information about where to find the attribute keys for the returned dictionary, see the overview
	 *             section of this document.
	 **/
	
	public NSDictionary attributesAtIndexLongestEffectiveRangeInRange(int index, NSRangePointer aRange, NSRange rangeLimit) {

		//no such equivalent
		return null;

	}

	/**
	 * @Signature: attributesAtIndex:effectiveRange:
	 * @Declaration : - (NSDictionary *)attributesAtIndex:(NSUInteger)index effectiveRange:(NSRangePointer)aRange
	 * @Description : Returns the attributes for the character at a given index.
	 * @param index The index for which to return attributes. This value must lie within the bounds of the receiver.
	 * @param aRange Upon return, the range over which the attributes and values are the same as those at index. This range isn’t
	 *            necessarily the maximum range covered, and its extent is implementation-dependent. If you need the maximum range, use
	 *            attributesAtIndex:longestEffectiveRange:inRange:. If you don't need this value, pass NULL.
	 * @return Return Value The attributes for the character at index.
	 * @Discussion Raises an NSRangeException if index lies beyond the end of the receiver’s characters. For information about where to find
	 *             the attribute keys for the returned dictionary, see the overview section of this document.
	 **/
	
	public Object attributesAtIndexEffectiveRange(String attributeName, int index, NSRange aRange) {

		//no such equivalent

		return null;
	}

	private String getAttributeName(Attribute attr) {

		String fullName = attr.toString();
		return (String) fullName.subSequence(fullName.indexOf('('), fullName.indexOf(')'));
	}

	/**
	 * @Signature: attribute:atIndex:longestEffectiveRange:inRange:
	 * @Declaration : - (id)attribute:(NSString *)attributeName atIndex:(NSUInteger)index longestEffectiveRange:(NSRangePointer)aRange
	 *              inRange:(NSRange)rangeLimit
	 * @Description : Returns the value for the attribute with a given name of the character at a given index, and by reference the range
	 *              over which the attribute applies.
	 * @param attributeName The name of an attribute.
	 * @param index The index at which to test for attributeName.
	 * @param aRange If non-NULL: If the named attribute exists at index, upon return aRange contains the full range over which the value of
	 *            the named attribute is the same as that at index, clipped to rangeLimit. If the named attribute does not exist at index,
	 *            upon return aRange contains the full range over which the attribute does not exist, clipped to rangeLimit. If you don't
	 *            need this value, pass NULL.
	 * @param rangeLimit The range over which to search for continuous presence of attributeName. This value must not exceed the bounds of
	 *            the receiver.
	 * @return Return Value The value for the attribute named attributeName of the character at index, or nil if there is no such attribute.
	 * @Discussion Raises an NSRangeException if index or any part of rangeLimit lies beyond the end of the receiver’s characters. If you
	 *             don’t need the longest effective range, it’s far more efficient to use the attribute:atIndex:effectiveRange: method to
	 *             retrieve the attribute value. For information about where to find the attribute keys for the returned dictionary, see the
	 *             overview section of this document.
	 **/
	
	public Object attributeAtIndexLongestEffectiveRangeInRange(String attributeName, int index, NSRange aRange, NSRange rangeLimit) {

		// no such android equivalent

		return null;
	}

	/**
	 * @Signature: isEqualToAttributedString:
	 * @Declaration : - (BOOL)isEqualToAttributedString:(NSAttributedString *)otherString
	 * @Description : Returns a Boolean value that indicates whether the receiver is equal to another given attributed string.
	 * @param otherString The attributed string with which to compare the receiver.
	 * @return Return Value YES if the receiver is equal to otherString, otherwise NO.
	 * @Discussion Attributed strings must match in both characters and attributes to be equal.
	 **/
	
	public boolean isEqualToAttributedString(NSAttributedString otherString) {
		if(otherString!=null){
			return getSpannableString().equals(otherString.getSpannableString());
		}
		return false;
	}

	/**
	 * @Signature: attributedSubstringFromRange:
	 * @Declaration : - (NSAttributedString *)attributedSubstringFromRange:(NSRange)aRange
	 * @Description : Returns an NSAttributedString object consisting of the characters and attributes within a given range in the receiver.
	 * @param aRange The range from which to create a new attributed string. aRange must lie within the bounds of the receiver.
	 * @return Return Value An NSAttributedString object consisting of the characters and attributes within aRange in the receiver.
	 * @Discussion Raises an NSRangeException if any part of aRange lies beyond the end of the receiver’s characters. This method treats the
	 *             length of the string as a valid range value that returns an empty string.
	 **/
	
	public NSAttributedString attributedSubstringFromRange(NSRange aRange) {
		return null;
	}

	/**
	 * @Signature: enumerateAttribute:inRange:options:usingBlock:
	 * @Declaration : - (void)enumerateAttribute:(NSString *)attrName inRange:(NSRange)enumerationRange
	 *              options:(NSAttributedStringEnumerationOptions)opts usingBlock:(void (^)(id value, NSRange range, BOOL *stop))block
	 * @Description : Executes the Block for the specified attribute run in the specified range.
	 * @param attrName The name of an attribute.
	 * @param enumerationRange If non-NULL, contains the maximum range over which the attributes and values are enumerated, clipped to
	 *            enumerationRange.
	 * @param opts The options used by the enumeration. The values can be combined using C-bitwise OR. The values are described in
	 *            “NSAttributedStringEnumerationOptions.
	 * @param block The Block to apply to ranges of the attribute in the attributed string. The Block takes three arguments: value The
	 *            attrName value. range An NSRange indicating the run of the attribute. stop A reference to a Boolean value. The block can
	 *            set the value to YES to stop further processing of the set. The stop argument is an out-only argument. You should only
	 *            ever set this Boolean to YES within the Block.
	 * @param value The attrName value.
	 * @param range An NSRange indicating the run of the attribute.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion If this method is sent to an instance of NSMutableAttributedString, mutation (deletion, addition, or change) is allowed,
	 *             as long as it is within the range provided to the block; after a mutation, the enumeration continues with the range
	 *             immediately following the processed range, after the length of the processed range is adjusted for the mutation. (The
	 *             enumerator basically assumes any change in length occurs in the specified range.) For example, if block is called with a
	 *             range starting at location N, and the block deletes all the characters in the supplied range, the next call will also
	 *             pass N as the index of the range.
	 **/
	
	// FIXME ? fix block use
	public void enumerateAttributeInRangeOptionsUsingBlock(String attrName, NSRange enumerationRange,
			NSAttributedStringEnumerationOptions opts, usingBlockBlock block) {
		//no such equivalent
	}

	/**
	 * @Signature: enumerateAttributesInRange:options:usingBlock:
	 * @Declaration : - (void)enumerateAttributesInRange:(NSRange)enumerationRange options:(NSAttributedStringEnumerationOptions)opts
	 *              usingBlock:(void (^)(NSDictionary *attrs, NSRange range, BOOL *stop))block
	 * @Description : Executes the Block for each attribute in the range.
	 * @param enumerationRange If non-NULL, contains the maximum range over which the attributes and values are enumerated, clipped to
	 *            enumerationRange.
	 * @param opts The options used by the enumeration. The values can be combined using C-bitwise OR. The values are described in
	 *            “NSAttributedStringEnumerationOptions.
	 * @param block The Block to apply to ranges of the attribute in the attributed string. The Block takes three arguments: attrs An
	 *            NSDictionary that contains the attributes for the range. range An NSRange indicating the run of the attribute. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @param attrs An NSDictionary that contains the attributes for the range.
	 * @param range An NSRange indicating the run of the attribute.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion If this method is sent to an instance of NSMutableAttributedString, mutation (deletion, addition, or change) is allowed,
	 *             as long as it is within the range provided to the block; after a mutation, the enumeration continues with the range
	 *             immediately following the processed range, after the length of the processed range is adjusted for the mutation. (The
	 *             enumerator basically assumes any change in length occurs in the specified range.) For example, if block is called with a
	 *             range starting at location N, and the block deletes all the characters in the supplied range, the next call will also
	 *             pass N as the index of the range.
	 **/
	
	public void enumerateAttributesInRangeOptionsUsingBlock(NSRange enumerationRange, NSAttributedStringEnumerationOptions opts,
			usingBlockBlock block) {
		//no such equivalent

	}

	// UIKit Additions

	/**
	 * @Signature: dataFromRange:documentAttributes:error:
	 * @Declaration : - (NSData *)dataFromRange:(NSRange)range documentAttributes:(NSDictionary *)dict error:(NSError **)error
	 * @Description : Returns an data object that contains a text stream corresponding to the characters and attributes within the given
	 *              range.
	 * @Discussion Raises an NSRangeException if any part of range lies beyond the end of the receiver’s characters.
	 **/
	
	public NSData dataFromRangeDocumentAttributesError(NSRange range, NSDictionary dict, NSError error) {
		return null;
	}

	/**
	 * @Signature: fileWrapperFromRange:documentAttributes:error:
	 * @Declaration : - (NSFileWrapper *)fileWrapperFromRange:(NSRange)range documentAttributes:(NSDictionary *)dict error:(NSError **)error
	 * @Description : Returns an NSFileWrapper object that contains a text stream corresponding to the characters and attributes within the
	 *              given range.
	 * @Discussion Raises an NSRangeException if any part of range lies beyond the end of the receiver’s characters.
	 **/
	
	public NSFileWrapper fileWrapperFromRangeDocumentAttributesError(NSRange range, NSDictionary dict, NSError error) {
		return null;
	}

	/**
	 * @Signature: initWithData:options:documentAttributes:error:
	 * @Declaration : - (id)initWithData:(NSData *)data options:(NSDictionary *)options documentAttributes:(NSDictionary **)dict
	 *              error:(NSError **)error
	 * @Description : Initializes and returns a new attributed string object from the data contained in the given data object.
	 * @Discussion The HTML importer should not be called from a background thread (that is, the options dictionary includes
	 *             NSDocumentTypeDocumentAttribute with a value of NSHTMLTextDocumentType). It will try to synchronize with the main thread,
	 *             fail, and time out. Calling it from the main thread works (but can still time out if the HTML contains references to
	 *             external resources, which should be avoided at all costs). The HTML import mechanism is meant for implementing something
	 *             like markdown (that is, text styles, colors, and so on), not for general HTML import.
	 **/
	
	public Object initWithDataOptionsDocumentAttributesError(NSData data, NSDictionary options, NSDictionary dict, NSError error) {
		//no such equivalent
		return null;
	}

	/**
	 * @Signature: initWithFileURL:options:documentAttributes:error:
	 * @Declaration : - (id)initWithFileURL:(NSURL *)url options:(NSDictionary *)options documentAttributes:(NSDictionary **)dict
	 *              error:(NSError **)error
	 * @Description : Initializes a new attributed string object from the data at the given URL.
	 * @Discussion The HTML importer should not be called from a background thread (that is, the options dictionary includes
	 *             NSDocumentTypeDocumentAttribute with a value of NSHTMLTextDocumentType). It will try to synchronize with the main thread,
	 *             fail, and time out. Calling it from the main thread works (but can still time out if the HTML contains references to
	 *             external resources, which should be avoided at all costs). The HTML import mechanism is meant for implementing something
	 *             like markdown (that is, text styles, colors, and so on), not for general HTML import.
	 **/
	
	public void initWithFileURLOptionsDocumentAttributesError(NSURL url, NSDictionary options, NSDictionary dict, NSError error) throws UnsupportedOperationException{
	}

	// Protocol Methods
	
	@Override
	public void encodeWithCoder(NSCoder encoder) throws UnsupportedOperationException{

	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {

		return null;
	}

	
	@Override
	public Object mutableCopyWithZone(NSZone zone) {

		return null;
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	
	@Override
	public Class classForCoder() {
		return null;
	}
}