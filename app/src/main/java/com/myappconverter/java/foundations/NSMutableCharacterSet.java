
package com.myappconverter.java.foundations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;



public class NSMutableCharacterSet extends NSCharacterSet implements NSCopying, NSMutableCopying, Iterable<Character>, Iterator<Character> {

	private Set<Character> characterInSet;

	public NSMutableCharacterSet() {
		this.characterInSet = new HashSet<Character>();
	}

	// Adding and Removing Characters
	/**
	 * @Signature: addCharactersInRange:
	 * @Declaration : - (void)addCharactersInRange:(NSRange)aRange
	 * @Description : Adds to the receiver the characters whose Unicode values are in a given range.
	 * @param aRange The range of characters to add. aRange.location is the value of the first character to add; aRange.location +
	 *            aRange.length– 1 is the value of the last. If aRange.length is 0, this method has no effect.
	 * @Discussion This code excerpt adds to a character set the lowercase English alphabetic characters: NSMutableCharacterSet
	 *             *aCharacterSet = [[NSMutableCharacterSet alloc] init]; NSRange lcEnglishRange; lcEnglishRange.location = (unsigned
	 *             int)'a'; lcEnglishRange.length = 26; [aCharacterSet addCharactersInRange:lcEnglishRange];
	 **/
	
	public void addCharactersInRange(NSRange aRange) {
		int start = aRange.location;
		int end = start + aRange.length - 1;

		for (int i = start; i < end; i++) {
			String aChar = Character.toString((char) i);
			if (!aChar.isEmpty()) {
				Character aCharacterInRange = new Character(aChar.charAt(0));
				this.characterInSet.add(aCharacterInRange);
			}
		}
	}

	/**
	 * @Signature: removeCharactersInRange:
	 * @Declaration : - (void)removeCharactersInRange:(NSRange)aRange
	 * @Description : Removes from the receiver the characters whose Unicode values are in a given range.
	 * @param aRange The range of characters to remove. aRange.location is the value of the first character to remove; aRange.location +
	 *            aRange.length– 1 is the value of the last. If aRange.length is 0, this method has no effect.
	 **/
	
	public void removeCharactersInRange(NSRange aRange) {
		int start = aRange.location;
		int end = start + aRange.length - 1;

		for (int i = start; i < end; i++) {
			String aChar = Character.toString((char) i);
			if (!aChar.isEmpty()) {
				Character aCharacterInRange = new Character(aChar.charAt(0));
				this.characterInSet.remove(aCharacterInRange);
			}
		}
	}

	/**
	 * @Signature: addCharactersInString:
	 * @Declaration : - (void)addCharactersInString:(NSString *)aString
	 * @Description : Adds to the receiver the characters in a given string.
	 * @param aString The characters to add to the receiver.
	 * @Discussion This method has no effect if aString is empty.
	 **/
	
	public void addCharactersInString(NSString aString) {
		char[] aCharArray = aString.wrappedString.toCharArray();

		for (int i = 0; i < aCharArray.length; i++) {
			Character aCharacterInString = new Character(aCharArray[i]);
			this.characterInSet.add(aCharacterInString);
		}
	}

	/**
	 * @Signature: removeCharactersInString:
	 * @Declaration : - (void)removeCharactersInString:(NSString *)aString
	 * @Description : Removes from the receiver the characters in a given string.
	 * @param aString The characters to remove from the receiver.
	 * @Discussion This method has no effect if aString is empty.
	 **/
	
	public void removeCharactersInString(NSString aString) {
		char[] aCharArray = aString.wrappedString.toCharArray();

		for (int i = 0; i < aCharArray.length; i++) {
			Character aCharacterInString = new Character(aCharArray[i]);
			this.characterInSet.remove(aCharacterInString);
		}
	}

	// Combining Character Sets
	/**
	 * @Signature: formIntersectionWithCharacterSet:
	 * @Declaration : - (void)formIntersectionWithCharacterSet:(NSCharacterSet *)otherSet
	 * @Description : Modifies the receiver so it contains only characters that exist in both the receiver and otherSet.
	 * @param otherSet The character set with which to perform the intersection.
	 **/
	
	public void formIntersectionWithCharacterSet(NSCharacterSet otherSet) {
		for (Character element : otherSet.getCharacterSet()) {
			if (!this.characterInSet.contains(element)) {
				this.characterInSet.remove(element);
			}
		}

	}

	/**
	 * @Signature: formUnionWithCharacterSet:
	 * @Declaration : - (void)formUnionWithCharacterSet:(NSCharacterSet *)otherSet
	 * @Description : Modifies the receiver so it contains all characters that exist in either the receiver or otherSet.
	 **/
	
	public void formUnionWithCharacterSet(NSCharacterSet otherSet) {
		for (Character element : otherSet.getCharacterSet()) {

			if (this.characterInSet.contains(element)) {
				this.characterInSet.remove(element);
			}
		}
	}

	// Inverting a Character Set

	/**
	 * @Signature: invert
	 * @Declaration : - (void)invert
	 * @Description : Replaces all the characters in the receiver with all the characters it didn’t previously contain.
	 * @Discussion Inverting a mutable character set, whether by invert or by invertedSet, is much less efficient than inverting an
	 *             immutable character set with invertedSet.
	 **/
	
	public void invert() {
		// not yet covered
	}

	// private NSMutableCharacterSet combineAllChacterSet() {
	//
	// NSMutableCharacterSet anNSCharacterSet = new NSMutableCharacterSet();
	//
	// anNSCharacterSet.characterInSet.addAll(alphanumericCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(capitalizedLetterCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(controlCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(decimalDigitCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(decomposableCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(illegalCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(lowercaseLetterCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(newlineCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(nonBaseCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(punctuationCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(symbolCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(uppercaseLetterCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(whitespaceAndNewlineCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(whitespaceCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(urlFragmentAllowedCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(urlHostAllowedCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(urlPasswordAllowedCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(urlQueryAllowedCharacterSet);
	// anNSCharacterSet.characterInSet.addAll(urlUserAllowedCharacterSet);
	//
	// return anNSCharacterSet;
	//
	// }

	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public boolean hasNext() {
		return characterInSet.iterator().hasNext();
	}

	@Override
	public Character next() {
		return characterInSet.iterator().next();
	}

	@Override
	public void remove() {
		characterInSet.iterator().remove();

	}

	@Override
	public Iterator<Character> iterator() {
		return characterInSet.iterator();
	}

}