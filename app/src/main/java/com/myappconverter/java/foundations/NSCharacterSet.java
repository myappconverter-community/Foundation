
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class NSCharacterSet extends NSObject implements NSCoding, NSCopying, NSMutableCopying {

	
	private String type;
	private Set<Character> characterSet;

	public NSCharacterSet(Set<Character> characterSet) {

		this.setCharacterSet(characterSet);
	}

	public NSCharacterSet() {

		this.setCharacterSet(new HashSet<Character>());
	}

	// Creating a Standard Character Set

	// Creating a Custom Character Set

	/**
	 * @Signature: characterSetWithCharactersInString:
	 * @Declaration : + (id)characterSetWithCharactersInString:(NSString *)aString
	 * @Description : Returns a character set containing the characters in a given string.
	 * @param aString A string containing characters for the new character set.
	 * @return Return Value A character set containing the characters in aString. Returns an empty character set if aString is empty.
	 **/
	
	public static NSCharacterSet characterSetWithCharactersInString(NSString aString) {
		NSCharacterSet nsChar = new NSCharacterSet();
		Set mCharSet = convertToSet(aString.wrappedString);
		nsChar.setCharacterSet(mCharSet);
		return nsChar;
	}

	public static Set convertToSet(String string) {
		Set resultSet = new HashSet();
		for (int i = 0; i < string.length(); i++) {
			resultSet.add(new Character(string.charAt(i)));
		}
		return resultSet;
	}

	/**
	 * @Signature: characterSetWithRange:
	 * @Declaration : + (id)characterSetWithRange:(NSRange)aRange
	 * @Description : Returns a character set containing characters with Unicode values in a given range.
	 * @param aRange A range of Unicode values. aRange.location is the value of the first character to return; aRange.location +
	 *            aRange.length– 1 is the value of the last.
	 * @return Return Value A character set containing characters whose Unicode values are given by aRange. If aRange.length is 0, returns
	 *         an empty character set.
	 * @Discussion This code excerpt creates a character set object containing the lowercase English alphabetic characters: NSRange
	 *             lcEnglishRange; NSCharacterSet *lcEnglishLetters; lcEnglishRange.location = (unsigned int)'a'; lcEnglishRange.length =
	 *             26; lcEnglishLetters = [NSCharacterSet characterSetWithRange:lcEnglishRange];
	 **/
	
	public static NSCharacterSet characterSetWithRange(NSRange aRange) {
		int start = aRange.location;
		int end = start + aRange.length - 1;
		Set<Character> charactersInRangeSet = new HashSet<Character>();
		for (int i = start; i < end; i++) {
			String aChar = Character.toString((char) i);
			if (!aChar.isEmpty()) {
				Character aCharacterInRange = Character.valueOf(aChar.charAt(0));
				charactersInRangeSet.add(aCharacterInRange);
			}
		}
		return new NSCharacterSet(charactersInRangeSet);
	}


	private boolean isInvertedSet = false;

	private Set<Character> invertedSet ;
	private NSCharacterSet _invertedSet;
	/**
	 * @Signature: invertedSet
	 * @Declaration : - (NSCharacterSet *)invertedSet
	 * @Description : Returns a character set containing only characters that don’t exist in the receiver.
	 * @return Return Value A character set containing only characters that don’t exist in the receiver.
	 * @Discussion Inverting an immutable character set is much more efficient than inverting a mutable character set.
	 **/
	
	public NSCharacterSet invertedSet() {
		NSCharacterSet invertedSet = this;
		invertedSet.isInvertedSet = true;
		return invertedSet;
	}

    public Set<Character> getInvertedSet() {
        return this.invertedSet;
    }
	// Creating and Managing Character Sets as Bitmap Representations
	/**
	 * @Signature: characterSetWithBitmapRepresentation:
	 * @Declaration : + (id)characterSetWithBitmapRepresentation:(NSData *)data
	 * @Description : Returns a character set containing characters determined by a given bitmap representation.
	 * @param data A bitmap representation of a character set.
	 * @return Return Value A character set containing characters determined by data.
	 * @Discussion This method is useful for creating a character set object with data from a file or other external data source. A raw
	 *             bitmap representation of a character set is a byte array of 2^16 bits (that is, 8192 bytes). The value of the bit at
	 *             position n represents the presence in the character set of the character with decimal Unicode value n. To add a character
	 *             with decimal Unicode value n to a raw bitmap representation, use a statement such as the following: unsigned char
	 *             bitmapRep[8192]; bitmapRep[n >> 3] |= (((unsigned int)1) << (n & 7)); To remove that character: bitmapRep[n >> 3] &=
	 *             ~(((unsigned int)1) << (n & 7));
	 **/
	
	static NSCharacterSet characterSetWithBitmapRepresentation(NSData data) {
		// TODO : convert to bitmap representation
		return null;

	}

	/**
	 * @Signature: characterSetWithContentsOfFile:
	 * @Declaration : + (id)characterSetWithContentsOfFile:(NSString *)path
	 * @Description : Returns a character set read from the bitmap representation stored in the file a given path.
	 * @param path A path to a file containing a bitmap representation of a character set. The path name must end with the extension
	 *            .bitmap.
	 * @return Return Value A character set read from the bitmap representation stored in the file at path.
	 * @Discussion To read a bitmap representation from any file, use the NSData methoddataWithContentsOfFile:options:error: and pass the
	 *             result to characterSetWithBitmapRepresentation:. This method doesn’t use filenames to check for the uniqueness of the
	 *             character sets it creates. To prevent duplication of character sets in memory, cache them and make them available through
	 *             an API that checks whether the requested set has already been loaded.
	 **/
	
	public static NSCharacterSet characterSetWithContentsOfFile(NSString path) {
		// TODO
		NSData myData = NSData.dataWithContentsOfFileOptionsError(NSData.class, path, 0, null);
		return characterSetWithBitmapRepresentation(myData);
	}

	/**
	 * @Signature: bitmapRepresentation
	 * @Declaration : - (NSData *)bitmapRepresentation
	 * @Description : Returns an NSData object encoding the receiver in binary format.
	 * @return Return Value An NSData object encoding the receiver in binary format.
	 * @Discussion This format is suitable for saving to a file or otherwise transmitting or archiving.
	 **/
	
	public NSData bitmapRepresentation() {

		List myDataLIst = new ArrayList<byte[]>();
		int i = 0;
		for (Character element : characterSet) {
			char myChars[] = { element.charValue() };
			myDataLIst.addAll(Arrays.asList(new String(myChars).getBytes()));
		}

		byte[] data = new byte[myDataLIst.size()];
		for (int j = 0; j < myDataLIst.size(); j++) {
			data[i] = (Byte) myDataLIst.get(j);
		}

		// TODO : convert to bitmap representation
        // not yet covered
		return null;
	}

	public NSData getBitmapRepresentation() {

		List myDataLIst = new ArrayList<byte[]>();
		int i = 0;
		for (Character element : characterSet) {
			char myChars[] = { element.charValue() };
			myDataLIst.addAll(Arrays.asList(new String(myChars).getBytes()));
		}

		byte[] data = new byte[myDataLIst.size()];
		for (int j = 0; j < myDataLIst.size(); j++) {
			data[i] = (Byte) myDataLIst.get(j);
		}

		// TODO : convert to bitmap representation
        // not yet covered
		return null;
	}
	// Testing Set Membership
	/**
	 * @Signature: characterIsMember:
	 * @Declaration : - (BOOL)characterIsMember:(unichar)aCharacter
	 * @Description : Returns a Boolean value that indicates whether a given character is in the receiver.
	 * @param aCharacter The character to test for membership of the receiver.
	 * @return Return Value YES if aCharacter is in the receiving character set, otherwise NO.
	 **/
	
	public boolean characterIsMember(char aCharacter) {
		if(isInvertedSet == false)
		{
			if("lower".equals(type))
				return Character.isLowerCase(aCharacter);
			if("upper".equals(type))
				return Character.isUpperCase(aCharacter);
			if("decimalDigit".equals(type))
				return Character.isDigit(aCharacter);
			if("letter".equals(type))
				return Character.isLetter(aCharacter);
			if("alphanumeric".equals(type))
				return Character.isLetterOrDigit(aCharacter);
			if("whitespace".equals(type))
				return Character.isSpaceChar(aCharacter);
			if("whitespaceAndNewline".equals(type))
				return Character.isSpaceChar(aCharacter);
			if("symbol".equals(type)){
				int codeType = Character.getType(aCharacter);
				if(codeType == Character.MATH_SYMBOL ||codeType == Character.CURRENCY_SYMBOL
						||codeType == Character.MODIFIER_SYMBOL ||codeType == Character.OTHER_SYMBOL)
					return true;
				return false;
			}if("punctuation".equals(type)){
				int codeType = Character.getType(aCharacter);
				if(codeType == Character.DASH_PUNCTUATION ||codeType == Character.START_PUNCTUATION
						||codeType == Character.END_PUNCTUATION || codeType == Character.CONNECTOR_PUNCTUATION ||codeType == Character.OTHER_PUNCTUATION)
					return true;
				return false;
			}
			if("unicode".equals(type))
				return Character.isUnicodeIdentifierPart(aCharacter);
		}
		else
		{
			if("lower".equals(type))
				return !Character.isLowerCase(aCharacter);
			if("upper".equals(type))
				return !Character.isUpperCase(aCharacter);
			if("decimalDigit".equals(type))
				return !Character.isDigit(aCharacter);
			if("letter".equals(type))
				return !Character.isLetter(aCharacter);
			if("alphanumeric".equals(type))
				return !Character.isLetterOrDigit(aCharacter);
			if("unicode".equals(type))
				return !Character.isUnicodeIdentifierPart(aCharacter);
		}
		return false;
	}

	/**
	 * @Signature: hasMemberInPlane:
	 * @Declaration : - (BOOL)hasMemberInPlane:(uint8_t)thePlane
	 * @Description : Returns a Boolean value that indicates whether the receiver has at least one member in a given character plane.
	 * @param thePlane A character plane.
	 * @return Return Value YES if the receiver has at least one member in thePlane, otherwise NO.
	 * @Discussion This method makes it easier to find the plane containing the members of the current character set. The Basic Multilingual
	 *             Plane is plane 0.
	 **/
	
	public boolean hasMemberInPlane(int thePlane) {
		if(isInvertedSet == false)
		{
			if("lower".equals(type))
				return Character.isLowerCase(thePlane);
			if("upper".equals(type))
				return Character.isUpperCase(thePlane);
			if("decimalDigit".equals(type))
				return Character.isDigit(thePlane);
			if("letter".equals(type))
				return Character.isLetter(thePlane);
			if("alphanumeric".equals(type))
				return Character.isLetterOrDigit(thePlane);
			if("unicode".equals(type))
				return Character.isUnicodeIdentifierPart(thePlane);
		}
		else
		{
			if("lower".equals(type))
				return !Character.isLowerCase(thePlane);
			if("upper".equals(type))
				return !Character.isUpperCase(thePlane);
			if("decimalDigit".equals(type))
				return !Character.isDigit(thePlane);
			if("letter".equals(type))
				return !Character.isLetter(thePlane);
			if("alphanumeric".equals(type))
				return !Character.isLetterOrDigit(thePlane);
			if("unicode".equals(type))
				return !Character.isUnicodeIdentifierPart(thePlane);
		}
		return false;
	}

	/**
	 * @Signature: isSupersetOfSet:
	 * @Declaration : - (BOOL)isSupersetOfSet:(NSCharacterSet *)theOtherSet
	 * @Description : Returns a Boolean value that indicates whether the receiver is a superset of another given character set.
	 * @param theOtherSet A character set.
	 * @return Return Value YES if the receiver is a superset of theOtherSet, otherwise NO.
	 **/
	
	public boolean isSupersetOfSet(NSCharacterSet theOtherSet) {
		if (this.characterSet.containsAll(theOtherSet.characterSet))
			return true;
		return false;
	}

	/**
	 * @Signature: longCharacterIsMember:
	 * @Declaration : - (BOOL)longCharacterIsMember:(UTF32Char)theLongChar
	 * @Description : Returns a Boolean value that indicates whether a given long character is a member of the receiver.
	 * @param theLongChar A UTF32 character.
	 * @return Return Value YES if theLongChar is in the receiver, otherwise NO.
	 * @Discussion This method supports the specification of 32-bit characters.
	 **/
	
	public boolean longCharacterIsMember(char aCharacter) {
		Character aCharacterINSet = Character.valueOf(aCharacter);
		if (this.characterSet.contains(aCharacterINSet))
			return true;
		return false;
	}

	// Adopted Protocols

	/*** NSCoding ***/

	// initWithCoder:
	
	public Object initWithCoder() {
		NSCharacterSet myNewCharacterSet = new NSCharacterSet();
		return myNewCharacterSet;

	}

	/*** NSCopying ***/
	
	public Object copyWithZone() {
		NSCharacterSet myNewCharacterSet = new NSCharacterSet(this.getCharacterSet());
		return myNewCharacterSet;

	}

	
	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		NSCharacterSet myNewCharacterSet = new NSCharacterSet(this.getCharacterSet());
		return myNewCharacterSet;

	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
		NSCharacterSet myNewCharacterSet = new NSCharacterSet();
		encoder.encodeObject(myNewCharacterSet);
	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		NSCharacterSet myNewCharacterSet = new NSCharacterSet();
		return myNewCharacterSet;
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	// /Generated

	/**
	 * @Signature: controlCharacterSet
	 * @Declaration : + (id)controlCharacterSet
	 * @Description : Returns a character set containing the characters in the categories of Control or Format Characters.
	 * @Discussion These characters are specifically the Unicode values U+0000 to U+001F and U+007F to U+009F.
	 **/
	
	public static Object controlCharacterSet() {
        // not yet covered
		return null;
	}

	/**
	 * @Signature: decimalDigitCharacterSet
	 * @Declaration : + (id)decimalDigitCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Decimal Numbers.
	 * @Discussion Informally, this set is the set of all characters used to represent the decimal values 0 through 9. These characters
	 *             include, for example, the decimal digits of the Indic scripts and Arabic.
	 **/
	
	public static NSCharacterSet decimalDigitCharacterSet() {
		NSCharacterSet decimalDigit = new NSCharacterSet();
		decimalDigit.type = "decimalDigit";
		return decimalDigit;
	}

	/**
	 * @Signature: decomposableCharacterSet
	 * @Declaration : + (id)decomposableCharacterSet
	 * @Description : Returns a character set containing all individual Unicode characters that can also be represented as composed
	 *              character sequences.
	 * @Discussion These characters include compatibility characters as well as pre-composed characters. Note: This character set doesn’t
	 *             currently include the Hangul characters defined in version 2.0 of the Unicode standard.
	 **/
	
	public static Object decomposableCharacterSet() {
		return null;
	}

	/**
	 * @Signature: illegalCharacterSet
	 * @Declaration : + (id)illegalCharacterSet
	 * @Description : Returns a character set containing values in the category of Non-Characters or that have not yet been defined in
	 *              version 3.2 of the Unicode standard.
	 **/
	
	public static Object illegalCharacterSet() {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: letterCharacterSet
	 * @Declaration : + (id)letterCharacterSet
	 * @Description : Returns a character set containing the characters in the categories Letters and Marks.
	 * @Discussion Informally, this set is the set of all characters used as letters of alphabets and ideographs.
	 **/
	
	public static NSCharacterSet letterCharacterSet() {
		NSCharacterSet letter = new NSCharacterSet();
		letter.type = "letter";
		return letter;
	}

	/**
	 * @Signature: lowercaseLetterCharacterSet
	 * @Declaration : + (id)lowercaseLetterCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Lowercase Letters.
	 * @Discussion Informally, this set is the set of all characters used as lowercase letters in alphabets that make case distinctions.
	 **/
	
	public static NSCharacterSet lowercaseLetterCharacterSet() {
		NSCharacterSet lowerLetter = new NSCharacterSet();
		lowerLetter.type = "lower";
		return lowerLetter;
	}

	/**
	 * @Signature: newlineCharacterSet
	 * @Declaration : + (id)newlineCharacterSet
	 * @Description : Returns a character set containing the newline characters.
	 **/
	
	public static NSCharacterSet newlineCharacterSet() {
		NSCharacterSet whitespace = new NSCharacterSet();
		whitespace.type = "newline";
		return whitespace;
	}

	/**
	 * @Signature: nonBaseCharacterSet
	 * @Declaration : + (id)nonBaseCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Marks.
	 * @Discussion This set is also defined as all legal Unicode characters with a non-spacing priority greater than 0. Informally, this set
	 *             is the set of all characters used as modifiers of base characters.
	 **/
	
	public static NSCharacterSet nonBaseCharacterSet() {
		// not yet covered
        return null;
	}

	/**
	 * @Signature: punctuationCharacterSet
	 * @Declaration : + (id)punctuationCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Punctuation.
	 * @Discussion Informally, this set is the set of all non-whitespace characters used to separate linguistic units in scripts, such as
	 *             periods, dashes, parentheses, and so on.
	 **/
	
	public static NSCharacterSet punctuationCharacterSet() {
		NSCharacterSet punctuation = new NSCharacterSet();
		punctuation.type = "punctuation";
		return punctuation;
	}

	/**
	 * @Signature: symbolCharacterSet
	 * @Declaration : + (id)symbolCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Symbols.
	 * @Discussion These characters include, for example, the dollar sign ($) and the plus (+) sign.
	 **/
	
	public static NSCharacterSet symbolCharacterSet() {
		NSCharacterSet symbol = new NSCharacterSet();
		symbol.type = "symbol";
		return symbol;
	}

	/**
	 * @Signature: uppercaseLetterCharacterSet
	 * @Declaration : + (id)uppercaseLetterCharacterSet
	 * @Description : Returns a character set containing the characters in the categories of Uppercase Letters and Titlecase Letters.
	 * @Discussion Informally, this set is the set of all characters used as uppercase letters in alphabets that make case distinctions.
	 **/
	
	public static NSCharacterSet uppercaseLetterCharacterSet() {
		NSCharacterSet upperLetter = new NSCharacterSet();
		upperLetter.type = "upper";
		return upperLetter;
	}

	/**
	 * @Signature: URLFragmentAllowedCharacterSet
	 * @Declaration : + (id)URLFragmentAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a fragment URL component.
	 * @Discussion The fragment component of a URL is the component after a # symbol. For example, in the URL
	 *             http://www.example.com/index.html#jumpLocation, the fragment is jumpLocation.
	 **/
	
	public static Object URLFragmentAllowedCharacterSet() {
		// not yet covered
        return null;
	}

	/**
	 * @Signature: URLHostAllowedCharacterSet
	 * @Declaration : + (id)URLHostAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a host URL subcomponent.
	 * @Discussion The host component of a URL is usually the component immediately after the first two leading slashes. If the URL contains
	 *             a username and password, the host component is the component after the @ sign. For example, in the URL
	 *             http://username:password@www.example.com/index.html, the host component is www.example.com.
	 **/
	
	public static Object URLHostAllowedCharacterSet() {
        // not yet covered
        return null;
	}

	/**
	 * @Signature: URLPasswordAllowedCharacterSet
	 * @Declaration : + (id)URLPasswordAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a password URL subcomponent.
	 * @Discussion The password component of a URL is the component immediately following the colon after the username component of the URL,
	 *             and ends at the @ sign. For example, in the URL http://username:password@www.example.com/index.html, the pass component
	 *             is password.
	 **/
	
	public static Object URLPasswordAllowedCharacterSet() {
        // not yet covered
        return null;
	}

	/**
	 * @Signature: URLPathAllowedCharacterSet
	 * @Declaration : + (id)URLPathAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a path URL component.
	 * @Discussion The path component of a URL is the component immediately following the host component (if present). It ends wherever the
	 *             query or fragment component begins. For example, in the URL http://www.example.com/index.php?key1=value1, the path
	 *             component is /index.php.
	 **/
	
	public static Object URLPathAllowedCharacterSet() {
        // not yet covered
        return null;
	}

	/**
	 * @Signature: URLQueryAllowedCharacterSet
	 * @Declaration : + (id)URLQueryAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a query URL component.
	 * @Discussion The query component of a URL is the component immediately following a question mark (?). For example, in the URL
	 *             http://www.example.com/index.php?key1=value1#jumpLink, the query component is key1=value1.
	 **/
	
	public static Object URLQueryAllowedCharacterSet() {
        // not yet covered
        return null;
	}

	/**
	 * @Signature: URLUserAllowedCharacterSet
	 * @Declaration : + (id)URLUserAllowedCharacterSet
	 * @Description : Returns the character set for characters allowed in a user URL subcomponent.
	 * @Discussion The user component of a URL is an optional component that precedes the host component, and ends at either a colon (if a
	 *             password is specified) or an @ sign (if no password is specified). For example, in the URL
	 *             http://username:password@www.example.com/index.html, the user component is username.
	 **/
	
	public static Object URLUserAllowedCharacterSet() {
        // not yet covered
        return null;
	}

	/**
	 * @Signature: whitespaceAndNewlineCharacterSet
	 * @Declaration : + (id)whitespaceAndNewlineCharacterSet
	 * @Description : Returns a character set containing Unicode General Category Z*, U000A ~ U000D, and U0085.
	 **/
	
	public static NSCharacterSet whitespaceAndNewlineCharacterSet() {
		NSCharacterSet whitespace = new NSCharacterSet();
		whitespace.type = "whitespaceAndNewline";
		return whitespace;
	}

	/**
	 * @Signature: whitespaceCharacterSet
	 * @Declaration : + (id)whitespaceCharacterSet
	 * @Description : Returns a character set containing only the in-line whitespace characters space (U+0020) and tab (U+0009).
	 * @Discussion This set doesn’t contain the newline or carriage return characters.
	 **/
	
	public static NSCharacterSet whitespaceCharacterSet() {
		NSCharacterSet whitespace = new NSCharacterSet();
		whitespace.type = "whitespace";
		return whitespace;
	}

	/**
	 * @Signature: alphanumericCharacterSet
	 * @Declaration : + (id)alphanumericCharacterSet
	 * @Description : Returns a character set containing the characters in the categories Letters, Marks, and Numbers.
	 * @Discussion Informally, this set is the set of all characters used as basic units of alphabets, syllabaries, ideographs, and digits.
	 **/
	
	public static NSCharacterSet alphanumericCharacterSet() {
		NSCharacterSet alphanumeric = new NSCharacterSet();
		alphanumeric.type = "alphanumeric";
		return alphanumeric;
	}

	/**
	 * @Signature: capitalizedLetterCharacterSet
	 * @Declaration : + (id)capitalizedLetterCharacterSet
	 * @Description : Returns a character set containing the characters in the category of Titlecase Letters.
	 **/
	
	public Object capitalizedLetterCharacterSet() {
        // not yet covered
        return null;
	}

	public Set<Character> getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(Set<Character> characterSet) {
		this.characterSet = characterSet;
	}

}