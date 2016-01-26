
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.List;

import com.myappconverter.mapping.utils.PerformBlock;



public class NSLinguisticTagger extends NSObject {

	// Enumerations
	
	enum NSLinguisticTaggerOptions {

		NSLinguisticTaggerOmitWords(1 << 0), //
		NSLinguisticTaggerOmitPunctuation(1 << 1), //
		NSLinguisticTaggerOmitWhitespace(1 << 2), //
		NSLinguisticTaggerOmitOther(1 << 3), //
		NSLinguisticTaggerJoinNames(1 << 4);

		int val;

		NSLinguisticTaggerOptions(int val) {
			this.val = val;
		}

	}

	NSArray<NSString> tagSchemes = new NSArray<NSString>();
	static NSArray<NSString> tagSchemesforlang = new NSArray<NSString>();
	int opts;
	NSString theString;
	NSString sentence;
	NSOrthography orthography;

	public NSLinguisticTagger(NSArray tagSchemes, int opts) {
		super();
		this.tagSchemes = tagSchemes;
		this.opts = opts;

	}

	/**
	 * @Signature: initWithTagSchemes:options:
	 * @Declaration : - (id)initWithTagSchemes:(NSArray *)tagSchemes options:(NSUInteger)opts
	 * @Description : Creates a linguistic tagger instance using the specified tag schemes and options.
	 * @param tagSchemes An array of tag schemes. See “Linguistic Tag Schemes for the possible values.
	 * @param opts The linguistic tagger options to use. See “NSLinguisticTaggerOptions for the constants. These constants can be combined
	 *            using the C-Bitwise OR operator.
	 * @return Return Value An initialized linguistic tagger.
	 **/
	
	public NSLinguisticTagger initWithTagSchemesOptions(NSArray tagSchemes, int opts) {
		return new NSLinguisticTagger(tagSchemes, opts);
	}

	/**
	 * @Signature: tagSchemes
	 * @Declaration : - (NSArray *)tagSchemes
	 * @Description : Returns the tag schemes supported by the linguistic tagger for a particular language.
	 * @return Return Value An array of tag schemes. See “Linguistic Tag Schemes for the possible values.
	 **/
	
	public NSArray<NSString> tagSchemes() {
		return this.tagSchemes;

	}

	/**
	 * @Signature: availableTagSchemesForLanguage:
	 * @Declaration : + (NSArray *)availableTagSchemesForLanguage:(NSString *)language
	 * @Description : Returns the tag schemes supported by the linguistic tagger for a particular language.
	 * @param language A standard abbreviation as with NSOrthography.
	 * @return Return Value An array of “Linguistic Tag Schemes.
	 * @Discussion Clients wishing to know the tag schemes supported for a NSLinguisticTagger instance for a particular language may query
	 *             them with this method. The language should be specified using a standard abbreviation as with NSOrthography.
	 **/
	
	public static NSArray availableTagSchemesForLanguage(NSString language) {
		NSArray<NSString> nsArray = new NSArray<NSString>();
		if ("en".equals(language)) {

			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeTokenType"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeLexicalClass"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeNameType"));
			nsArray.getWrappedList()
					.add(new NSString("NSLinguisticTagSchemeNameTypeOrLexicalClass"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeLemma"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeLanguage"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeScript"));

		} else {
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeTokenType"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeLexicalClass"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeLanguage"));
			nsArray.getWrappedList().add(new NSString("NSLinguisticTagSchemeScript"));
		}

		return nsArray;

	}

	/**
	 * @Signature: string
	 * @Declaration : - (NSString *)string
	 * @Description : Returns the string being analyzed by the linguistic tagger.
	 * @return Return Value The string.
	 **/
	public NSString string() {
		return this.theString;
	}

	/**
	 * @Signature: setString:
	 * @Declaration : - (void)setString:(NSString *)string
	 * @Description : Sets the string to be analyzed by the linguistic tagger.
	 * @param string The string.
	 **/
	public void setString(NSString string) {
		this.theString = new NSString(string.getWrappedString());
	}

	/**
	 * @Signature: stringEditedInRange:changeInLength:
	 * @Declaration : - (void)stringEditedInRange:(NSRange)newCharRange changeInLength:(NSInteger)delta
	 * @Description : Notifies the linguistic tagger that the string (if mutable) has changed as specified by the parameters.
	 * @param newCharRange The range in the final string that was edited.
	 * @param delta The change in length.
	 **/
	
	public void stringEditedInRangeChangeInLength(NSRange newCharRange, int delta) {
		// TODO check that
		String result = theString.getWrappedString().substring(newCharRange.location,
				newCharRange.location + delta);
		theString.setWrappedString(result);
	}

	/**
	 * @Signature: setOrthography:range:
	 * @Declaration : - (void)setOrthography:(NSOrthography *)orthography range:(NSRange)charRange
	 * @Description : Sets the orthography for the specified range.
	 * @param orthography The orthography.
	 * @param charRange The range.
	 * @Discussion If the orthography of the linguistic tagger is not set, it will determine it automatically from the contents of the text.
	 *             Clients should call this method only if they already know the language of the text by some other means.
	 **/
	
	public void setOrthographyRange(NSOrthography orthography, NSRange charRange) {
		this.orthography = orthography;
		if (this.orthography == null) {
			// not yet covered
		}
	}

	/**
	 * @Signature: orthographyAtIndex:effectiveRange:
	 * @Declaration : - (NSOrthography *)orthographyAtIndex:(NSUInteger)charIndex effectiveRange:(NSRangePointer)effectiveRange
	 * @Description : Returns the orthography at the index and also returns the effective range.
	 * @param charIndex The character index to begin examination.
	 * @param effectiveRange An NSRangePointer that, upon completion, contains the range of the orthography containing charIndex.
	 * @return Return Value The orthography for the location.
	 **/
	
	public NSOrthography orthographyAtIndexEffectiveRange(int charIndex,
			NSRangePointer effectiveRange) {
		// not yet covered
		return null;

	}

	/**
	 * @Signature: enumerateTagsInRange:scheme:options:usingBlock:
	 * @Declaration : - (void)enumerateTagsInRange:(NSRange)range scheme:(NSString *)tagScheme options:(NSLinguisticTaggerOptions)opts
	 *              usingBlock:(void (^)(NSString *tag, NSRange tokenRange, NSRange sentenceRange, BOOL *stop))block
	 * @Description : Enumerates the specific range of the string, providing the Block with the located tags.
	 * @param range The range to analyze
	 * @param tagScheme The tag scheme.
	 * @param opts The linguistic tagger options to use. See “NSLinguisticTaggerOptions for the constants. These constants can be combined
	 *            using the C Bitwise operator.
	 * @param block The Block to apply to ranges of the string. The Block takes four arguments: tag The located linguistic tag. tokenRange
	 *            The range of the linguistic tag. sentenceRange The range of the sentence in which the tag occurs. stop A reference to a
	 *            Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an out-only
	 *            argument. You should only ever set this Boolean to YES within the Block.
	 * @param tag The located linguistic tag.
	 * @param tokenRange The range of the linguistic tag.
	 * @param sentenceRange The range of the sentence in which the tag occurs.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion The tagger will segment the string as needed into sentences and tokens, and return those ranges along with a tag for any
	 *             scheme in its array of tag schemes. This is the fundamental tagging method of NSLinguisticTagger. This method’s block
	 *             iterates over all tokens intersecting a given range, supplying tags and ranges. There are several additional convenience
	 *             methods, for obtaining a sentence range, information about a single token, or information about all tokens intersecting a
	 *             given range at once. For example, if the tag scheme is NSLinguisticTagSchemeLexicalClass, the tags will specify the part
	 *             of speech (for word tokens) or the type of whitespace or punctuation (for whitespace or punctuation tokens). If the tag
	 *             scheme is NSLinguisticTagSchemeLemma, the tags will specify the stem form of the word (if known) for each word token. It
	 *             is important to note that this method will return the ranges of all tokens that intersect the given range.
	 **/
	
	public void enumerateTagsInRangeSchemeOptionsUsingBlock(NSRange range, NSString tagScheme,
			NSLinguisticTaggerOptions opts,
			PerformBlock.VoidBlockNSStringNSRangeNSRangeBOOL block) {
		// not yet covered
	}

	/**
	 * @Signature: possibleTagsAtIndex:scheme:tokenRange:sentenceRange:scores:
	 * @Declaration : - (NSArray *)possibleTagsAtIndex:(NSUInteger)charIndex scheme:(NSString *)tagScheme
	 *              tokenRange:(NSRangePointer)tokenRange sentenceRange:(NSRangePointer)sentenceRange scores:(NSArray **)scores
	 * @Description : Returns an array of possible tags for the given scheme at the specified range, supplying matching scores.
	 * @param charIndex The initial character index.
	 * @param tagScheme The tag scheme. See “Linguistic Tag Schemes for the possible values.
	 * @param tokenRange The token range.
	 * @param sentenceRange The range of the sentence.
	 * @param scores Returns by-reference an array of numeric scores (wrapped as NSValue objects) indicating the likelihood that the range
	 *            matches the tag scheme.
	 * @return Return Value Returns an array of possible tags for the tagScheme at the specified location, starting with the most likely tag
	 *         scheme. For some tag schemes only a single tag will be returned, but for others a list of possibilities will be provided.
	 **/
	
	public NSArray<NSString> possibleTagsAtIndexSchemeTokenRangeSentenceRangeScores(int charIndex,
			NSString tagScheme, NSRange tokenRange, NSRange sentenceRange, NSArray[] scores) {
		NSArray<NSString> tags = new NSArray<NSString>();
		String StreEumerate = theString.getWrappedString().substring(charIndex,
				(theString.length() - charIndex));
		if (tagScheme.equals("NSLinguisticTagSchemeLexicalClass")) {
			// not yet covered
		}
		return tags;
	}

	/**
	 * @Signature: tagAtIndex:scheme:tokenRange:sentenceRange:
	 * @Declaration : - (NSString *)tagAtIndex:(NSUInteger)charIndex scheme:(NSString *)tagScheme tokenRange:(NSRangePointer)tokenRange
	 *              sentenceRange:(NSRangePointer)sentenceRange
	 * @Description : Returns a tag for a single scheme at the specified index.
	 * @param charIndex The initial character index.
	 * @param tagScheme The tag scheme. See “Linguistic Tag Schemes for the possible values.
	 * @param tokenRange A pointer to the token range. If NULL, no pointer range is returned.
	 * @param sentenceRange A pointer to the range of the sentence. If NULL, no pointer range is returned.
	 * @return Return Value Returns the tag for the requested tagScheme. There are cases in which there may not be a tag for a given scheme
	 *         and token, in which case the return value of the method would be nil.
	 * @Discussion When the returned array contains entries that do not have a corresponding tagScheme, that entry is an instance of NSNull.
	 **/
	
	public String tagAtIndexSchemeTokenRangeSentenceRange(int charIndex, NSString tagScheme,
			NSRangePointer tokenRange, NSRangePointer sentenceRange) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: tagsInRange:scheme:options:tokenRanges:
	 * @Declaration : - (NSArray *)tagsInRange:(NSRange)range scheme:(NSString *)tagScheme options:(NSLinguisticTaggerOptions)opts
	 *              tokenRanges:(NSArray **)tokenRanges
	 * @Description : Returns an array of linguistic tags and token ranges.
	 * @param range The range from which to return tags.
	 * @param tagScheme The tag scheme. See “Linguistic Tag Schemes for the possible values.
	 * @param opts The linguistic tagger options to use. See “NSLinguisticTaggerOptions for the constants. These constants can be combined
	 *            using the C-Bitwise OR operator.
	 * @param tokenRanges Returns by-reference an array of token range objects wrapped in NSValue objects.
	 * @return Return Value An array of the tag schemes corresponding to the entries in the tokenRanges array.
	 **/
	
	public List<String> tagsInRangeSchemeOptionsTokenRanges(NSRange range, NSString tagScheme,
			NSLinguisticTaggerOptions opts, NSArray[] tokenRanges) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: sentenceRangeForRange:
	 * @Declaration : - (NSRange)sentenceRangeForRange:(NSRange)charRange
	 * @Description : Returns the range of a sentence boundary containing the specified range.
	 * @param charRange The range.
	 * @return Return Value Returns the range of a sentence that contains charRange.
	 * @Discussion This method can be used to obtain the enclosing sentence range given a token range.
	 **/
	
	public NSRange sentenceRangeForRange(NSRange charRange) {
        // not yet covered
        return null;
	}

}