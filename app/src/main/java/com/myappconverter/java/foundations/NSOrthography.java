
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;

/**
 * Overview The NSOrthography class describes the linguistic content of a piece of text, typically used for the purposes of spelling and
 * grammar checking.
 */



public class NSOrthography extends NSObject implements NSCoding, NSCopying {

	public NSOrthography() {
		// not yet covered
	}

	/**
	 * @Signature: orthographyWithDominantScript:languageMap:
	 * @Declaration : + (id)orthographyWithDominantScript:(NSString *)script languageMap:(NSDictionary *)map
	 * @Description : Creates and returns an orthography instance with the specified dominant script and language map.
	 * @param script The dominant script.
	 * @param map A dictionary containing the language map.
	 * @return Return Value An initialized orthography object for the specified script and language map.
	 **/

	public static NSObject orthographyWithDominantScriptLanguageMap(NSString script, NSDictionary map) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: dominantLanguageForScript:
	 * @Declaration : - (NSString *)dominantLanguageForScript:(NSString *)script
	 * @Description : Returns the dominant language for the specified script.
	 * @param script The script.
	 * @return Return Value A string containing the dominant language
	 **/

	public NSString dominantLanguageForScript(NSString script) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: initWithDominantScript:languageMap:
	 * @Declaration : - (id)initWithDominantScript:(NSString *)script languageMap:(NSDictionary *)map
	 * @Description : Creates and returns an orthography instance with the specified dominant script and language map.
	 * @param script The dominant script.
	 * @param map A dictionary containing the language map.
	 * @return Return Value An initialized orthography object for the specified script and language map.
	 **/

	public NSObject initWithDominantScriptLanguageMap(NSString script, NSDictionary map) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: languagesForScript:
	 * @Declaration : - (NSArray *)languagesForScript:(NSString *)script
	 * @Description : Returns the list of languages for the specified script.
	 * @param script The script.
	 * @return Return Value An array of strings containing the languages.
	 **/

	public NSArray languagesForScript(NSString script) {
		return allLanguages;
	}

	/**
	 * @Declaration :  NSArray *allLanguages
	 * @Description : Returns an array containing all the languages appearing in the values of the language map. (read-only)
	 **/
	private NSArray allLanguages;

	/**
	 * @return the allLanguages
	 */
	public NSArray getAllLanguages() {
		return allLanguages;
	}

	/**
	 * @Declaration :  NSArray *allScripts
	 * @Description : Returns an array containing all the scripts appearing as keys in the language map. (read-only)
	 **/

	public NSArray allScripts;

	/**
	 * @return the allScripts
	 */
	public NSArray allScripts() {
		return allScripts;
	}

	/**
	 * @Declaration :  NSString *dominantLanguage
	 * @Description : Returns the first language in the list of languages for the dominant script. (read-only)
	 **/

	public NSString dominantLanguage;

	/**
	 * @return the dominantLanguage
	 */
	public NSString dominantLanguage() {
		return dominantLanguage;
	}

	/**
	 * @Declaration :  NSString *dominantScript
	 * @Description : The dominant script for the text. (read-only)
	 * @Discussion The dominant script should be a script tag, such as Latn, Cyrl, etc.
	 **/

	public NSString dominantScript;

	/**
	 * @return the dominantScript
	 */
	public NSString dominantScript() {
		return dominantScript;
	}

	/**
	 * @Declaration :  NSDictionary *languageMap
	 * @Description : A dictionary that map script tags to arrays of language tags. (read-only)
	 * @Discussion The dictionary’s keys are script tags (such as Latn, Cyrl, and so forth) and whose values are arrays of language tags
	 *             (such as en, fr, de, etc.)
	 **/

	public NSDictionary languageMap;

	/**
	 * @return the languageMap
	 */
	public NSDictionary languageMap() {
		return languageMap;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {

	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {

		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

}