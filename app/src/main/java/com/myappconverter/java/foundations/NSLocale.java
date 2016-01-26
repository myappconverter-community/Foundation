
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.AndroidRessourcesUtils;
import com.myappconverter.mapping.utils.GenericMainContext;



public class NSLocale extends NSObject implements NSCopying, NSSecureCoding {

	public Locale getLocale() {
		return wrappedLocale;
	}

	public void setLocale(Locale locale) {
		wrappedLocale = locale;
	}

	// Enumeration
	
	public enum NSLocaleLanguageDirection {
		NSLocaleLanguageDirectionUnknown, //
		NSLocaleLanguageDirectionLeftToRight, //
		NSLocaleLanguageDirectionRightToLeft, //
		NSLocaleLanguageDirectionTopToBottom, //
		SLocaleLanguageDirectionBottomToTop;

		@Override
		public String toString() {
			return "" + ordinal();
		}
	}

	private Locale wrappedLocale;
	private Currency wrappedCurrency;

	public Locale getWrappedLocale() {
		return wrappedLocale;
	}

	public void setWrappedLocale(Locale wrappedLocale) {
		this.wrappedLocale = wrappedLocale;
	}

	public NSLocale() {
		wrappedLocale = Locale.getDefault();
		wrappedCurrency = Currency.getInstance(wrappedLocale);
	}

	public NSLocale(Locale mLocale) {
		this.wrappedLocale = mLocale;
	}

	public Currency getWrappedCurrency() {
		return wrappedCurrency;
	}

	public void setWrappedCurrency(Currency currency) {
		this.wrappedCurrency = currency;
	}

	// Getting and Initializing Locales
	/**
	 * @Signature: initWithLocaleIdentifier:
	 * @Declaration : - (instancetype)initWithLocaleIdentifier:(NSString *)string
	 * @Description : Initializes the receiver using a given locale identifier.
	 * @param string The identifier for the new locale.
	 * @return Return Value The initialized locale.
	 **/
	
	public NSLocale initWithLocaleIdentifier(NSString string) {
		return this.localeBuilder(string);
	}

	/**
	 * @Signature: localeWithLocaleIdentifier:
	 * @Declaration : + (instancetype)localeWithLocaleIdentifier:(NSString *)string
	 * @Description : Returns a locale initialized using the given locale identifier.
	 * @param string The identifier for the new locale.
	 * @return Return Value The initialized locale.
	 **/
	
	public static NSLocale localeWithLocaleIdentifier(NSString string) {

		return new NSLocale().localeBuilder(string);
	}

	/**
	 * @Signature: systemLocale
	 * @Declaration : + (id)systemLocale
	 * @Description : Returns the “root, canonical locale, that contains fixed “backstop settings that provide values for otherwise
	 *              undefined keys.
	 * @return Return Value The “root, canonical locale, that contains fixed “backstop settings that provide values for otherwise
	 *         undefined keys.
	 **/
	
	public static NSLocale systemLocale() {
		// String user_lng = System.getProperty("user.language");
		// Locale lul = new Locale(user_lng);
		String user_region = System.getProperty("user.region");
		return new NSLocale(new Locale(user_region));
	}

	/**
	 * @Signature: autoupdatingCurrentLocale
	 * @Declaration : + (id)autoupdatingCurrentLocale
	 * @Description : Returns the current logical locale for the current user.
	 * @return Return Value The current logical locale for the current user. The locale is formed from the settings for the current user’s
	 *         chosen system locale overlaid with any custom settings the user has specified in System Preferences. The object always
	 *         reflects the current state of the current user’s locale settings.
	 * @Discussion Settings you get from this locale do change as the user’s settings change (contrast with currentLocale). Note that if you
	 *             cache values based on the locale or related information, those caches will of course not be automatically updated by the
	 *             updating of the locale object. You can recompute caches upon receipt of the notification
	 *             (NSCurrentLocaleDidChangeNotification) that gets sent out for locale changes (see Notification Programming Topics to
	 *             learn how to register for and receive notifications).
	 **/
	
	public static NSLocale currentLocale() {
		NSLocale nsLocale = new NSLocale();
		nsLocale.wrappedLocale = Locale.getDefault();
		return nsLocale;
	}

	/**
	 * @Signature: autoupdatingCurrentLocale
	 * @Declaration : + (id)autoupdatingCurrentLocale
	 * @Description : Returns the current logical locale for the current user.
	 * @return Return Value The current logical locale for the current user. The locale is formed from the settings for the current user’s
	 *         chosen system locale overlaid with any custom settings the user has specified in System Preferences. The object always
	 *         reflects the current state of the current user’s locale settings.
	 * @Discussion Settings you get from this locale do change as the user’s settings change (contrast with currentLocale). Note that if you
	 *             cache values based on the locale or related information, those caches will of course not be automatically updated by the
	 *             updating of the locale object. You can recompute caches upon receipt of the notification
	 *             (NSCurrentLocaleDidChangeNotification) that gets sent out for locale changes (see Notification Programming Topics to
	 *             learn how to register for and receive notifications).
	 **/
	
	public static NSLocale autoupdatingCurrentLocale() {

		NSLocale locale = new NSLocale();
		return locale;
	}

	/**
	 * @Signature: displayNameForKey:value:
	 * @Declaration : - (NSString *)displayNameForKey:(id)key value:(id)value
	 * @Description : Returns the display name for the given value.
	 * @param key Specifies which of the locale property keys value is (see “Constants),
	 * @param value A value for key.
	 * @return Return Value The display name for value.
	 * @Discussion Not all locale property keys have values with display name values. You can use the NSLocaleIdentifier key to get the name
	 *             of a locale in the language of another locale,
	 **/
	
	public NSString displayNameForKeyValue(NSString key, NSString value) {

		// TODO com.myappconverter.java.foundations.constants.NSLocaleConstants is not used and the keys are already
		// declared as NSString

		String[] strTab = value.wrappedString.split("_");

		Locale locale = Locale.getDefault();

		if ((NSLocaleIdentifier.wrappedString).equalsIgnoreCase(key.wrappedString)) {
			return new NSString(this.wrappedLocale.getDisplayName((this.localeBuilder(value)).wrappedLocale));
		} else if (NSLocaleLanguageCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			locale = new Locale(strTab[0]);
			return new NSString(locale.getDisplayLanguage(locale));
		} else if (NSLocaleCountryCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			if (strTab.length == 1) {
				locale = new Locale("en", strTab[0]);
			} else {
				locale = new Locale("en", strTab[1]);
			}
			return new NSString(locale.getDisplayCountry(locale));
		} else if (NSLocaleScriptCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleVariantCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			if (strTab.length == 1) {
				locale = new Locale("en", "US", strTab[0]);
			} else {
				locale = new Locale("en", "US", strTab[2]);
			}

			return new NSString(locale.getDisplayVariant(locale));
		} else if (NSLocaleExemplarCharacterSet.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleCalendar.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleCollationIdentifier.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleUsesMetricSystem.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleMeasurementSystem.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleDecimalSeparator.wrappedString.equalsIgnoreCase(key.wrappedString)) {

			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
			Character sep = symbols.getDecimalSeparator();
			return new NSString(sep.toString());

		} else if (NSLocaleGroupingSeparator.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleCurrencySymbol.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			Currency newCurreny = Currency.getInstance(value.wrappedString);
			return new NSString(newCurreny.getSymbol());
		} else if (NSLocaleCurrencyCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			Currency newCurreny = Currency.getInstance(value.wrappedString);
			return new NSString(newCurreny.getCurrencyCode());
		} else if (NSLocaleCollatorIdentifier.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleQuotationBeginDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleQuotationEndDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleAlternateQuotationBeginDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		} else if (NSLocaleAlternateQuotationEndDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
					//TODO
		}

		return null;
	}

	/**
	 * @Signature: localeIdentifier
	 * @Declaration : - (NSString *)localeIdentifier
	 * @Description : Returns the identifier for the receiver.
	 * @return Return Value The identifier for the receiver. This may not be the same string that the locale was created with, since
	 *         NSLocale may canonicalize it.
	 * @Discussion Equivalent to sending objectForKey: with key NSLocaleIdentifier.
	 **/
	
	public NSString localeIdentifier() {

		return new NSString(wrappedLocale.toString());

	}

	/**
	 * @Signature: objectForKey:
	 * @Declaration : - (id)objectForKey:(id)key
	 * @Description : Returns the object corresponding to the specified key.
	 * @param key The key for which to return the corresponding value. For valid values of key, see “Constants.
	 * @return Return Value The object corresponding to key.
	 **/
	
	public NSObject objectForKey(NSString key) {

		// TODO not complete

		if ((NSLocaleIdentifier.wrappedString).equalsIgnoreCase(key.wrappedString)) {
			return this.localeIdentifier();
		} else if (NSLocaleLanguageCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			return new NSString(this.wrappedLocale.getLanguage());
		} else if (NSLocaleCountryCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			return new NSString(this.wrappedLocale.getCountry());
		} else if (NSLocaleScriptCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleVariantCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			return new NSString(this.wrappedLocale.getVariant());
		} else if (NSLocaleExemplarCharacterSet.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			return new NSCharacterSet();
		} else if (NSLocaleCalendar.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			return new NSCalendar();
		} else if (NSLocaleCollationIdentifier.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleUsesMetricSystem.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleMeasurementSystem.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleDecimalSeparator.wrappedString.equalsIgnoreCase(key.wrappedString)) {

			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
			Character sep = symbols.getDecimalSeparator();
			return new NSString(sep.toString());

		} else if (NSLocaleGroupingSeparator.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleCurrencySymbol.wrappedString.equalsIgnoreCase(key.wrappedString)) {

			return new NSString(this.wrappedCurrency.getSymbol(this.wrappedLocale));

		} else if (NSLocaleCurrencyCode.wrappedString.equalsIgnoreCase(key.wrappedString)) {

			return new NSString(this.wrappedCurrency.getCurrencyCode());

		} else if (NSLocaleCollatorIdentifier.wrappedString.equalsIgnoreCase(key.wrappedString)) {
			//TODO
		} else if (NSLocaleQuotationBeginDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
            //TODO
		} else if (NSLocaleQuotationEndDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
            //TODO
		} else if (NSLocaleAlternateQuotationBeginDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
            //TODO
		} else if (NSLocaleAlternateQuotationEndDelimiterKey.wrappedString.equalsIgnoreCase(key.wrappedString)) {
            //TODO
		}

		return null;

	}

	/**
	 * @Signature: availableLocaleIdentifiers
	 * @Declaration : + (NSArray *)availableLocaleIdentifiers
	 * @Description : Returns an array of NSString objects, each of which identifies a locale available on the system.
	 * @return Return Value An array of NSString objects, each of which identifies a locale available on the system.
	 **/
	
	public static NSArray<NSString> availableLocaleIdentifiers() {

		List<NSString> list = new ArrayList<NSString>();
		for (Locale locale : Locale.getAvailableLocales()) {

			list.add(new NSString(locale.toString()));

		}
		NSArray<NSString> array = new NSArray<NSString>();
		array.setWrappedList(list);
		return array;
	}

	/**
	 * @Signature: ISOCountryCodes
	 * @Declaration : + (NSArray *)ISOCountryCodes
	 * @Description : Returns an array of NSString objects that represents all known legal country codes.
	 * @return Return Value An array of NSString objects that represents all known legal country codes.
	 * @Discussion Note that many of country codes do not have any supporting locale data in OS X.
	 **/
	
	public static NSArray<NSString> ISOCountryCodes() {

		List<NSString> list = new ArrayList<NSString>();
		for (String str : Locale.getISOCountries()) {
			list.add(new NSString(str));
		}

		NSArray<NSString> array = new NSArray<NSString>();
		array.setWrappedList(list);
		return array;

	}

	/**
	 * @Signature: ISOCurrencyCodes
	 * @Declaration : + (NSArray *)ISOCurrencyCodes
	 * @Description : Returns an array of NSString objects that represents all known legal ISO currency codes.
	 * @return Return Value An array of NSString objects that represents all known legal ISO currency codes.
	 * @Discussion Note that some of the currency codes may not have any supporting locale data in OS X.
	 **/
	
	public static NSArray<NSString> ISOCurrencyCodes() {

		List<NSString> list = new ArrayList<NSString>();
		Currency currency;

		for (Locale locale : Locale.getAvailableLocales()) {

			if ((locale.getCountry()).length() == 2) {
				currency = Currency.getInstance(locale);
				list.add(new NSString(currency.getCurrencyCode()));
			}
		}

		NSArray<NSString> array = new NSArray<NSString>();
		array.setWrappedList(list);
		return array;
	}

	/**
	 * @Signature: ISOLanguageCodes
	 * @Declaration : + (NSArray *)ISOLanguageCodes
	 * @Description : Returns an array of NSString objects that represents all known legal ISO language codes.
	 * @return Return Value An array of NSString objects that represents all known legal ISO language codes.
	 * @Discussion Note that many of the language codes do not have any supporting locale data in OS X.
	 **/
	
	public static NSArray<NSString> ISOLanguageCodes() {

		List<NSString> list = new ArrayList<NSString>();
		for (String str : Locale.getISOLanguages()) {
			list.add(new NSString(str));
		}
		NSArray<NSString> array = new NSArray<NSString>();
		array.setWrappedList(list);
		return array;
	}

	/**
	 * @Signature: canonicalLocaleIdentifierFromString:
	 * @Declaration : + (NSString *)canonicalLocaleIdentifierFromString:(NSString *)string
	 * @Description : Returns the canonical identifier for a given locale identification string.
	 * @param string A locale identification string.
	 * @return Return Value The canonical identifier for an the locale identified by string.
	 **/
	
	public static NSString canonicalLocaleIdentifierFromString(NSString string) {
		NSString nsString = new NSString(string.wrappedString.intern());
		return nsString;
	}

	/**
	 * @Signature: componentsFromLocaleIdentifier:
	 * @Declaration : + (NSDictionary *)componentsFromLocaleIdentifier:(NSString *)string
	 * @Description : Returns a dictionary that is the result of parsing a locale ID.
	 * @param string A locale ID, consisting of language, script, country, variant, and keyword/value pairs, for example,
	 *            "en_US@calendar=japanese".
	 * @return Return Value A dictionary that is the result of parsing string as a locale ID. The keys are the constant NSString constants
	 *         corresponding to the locale ID components, and the values correspond to constants where available. For the complete set of
	 *         dictionary keys, see “Constants.
	 * @Discussion For example: the locale ID "en_US@calendar=japanese" yields a dictionary with three entries: NSLocaleLanguageCode=en,
	 *             NSLocaleCountryCode=US, and NSLocaleCalendar=NSJapaneseCalendar.
	 **/
	
	public static NSDictionary<NSString, NSString> componentsFromLocaleIdentifier(NSString string) {
		Map<NSString, NSString> dictionnaire = new HashMap<NSString, NSString>();

        StringBuilder buffer = new StringBuilder(string.wrappedString);

		String lang = string.wrappedString.substring(0, buffer.indexOf("_"));
		String country = string.wrappedString.substring(buffer.indexOf("_"), buffer.lastIndexOf("_"));
		String var = string.wrappedString.substring(buffer.lastIndexOf("_"), string.getWrappedString().length());

		dictionnaire.put(new NSString("NSLocaleLanguageCode"), new NSString(lang));
		dictionnaire.put(new NSString("NSLocaleCountryCode"), new NSString(country));
		dictionnaire.put(new NSString("NSLocaleVariant"), new NSString(var));

		NSDictionary<NSString, NSString> nsDictionary = new NSDictionary<NSString, NSString>(dictionnaire);
		return nsDictionary;
	}

	/**
	 * @Signature: localeIdentifierFromComponents:
	 * @Declaration : + (NSString *)localeIdentifierFromComponents:(NSDictionary *)dict
	 * @Description : Returns a locale identifier from the components specified in a given dictionary.
	 * @param dict A dictionary containing components that specify a locale. For valid dictionary keys, see “Constants.
	 * @return Return Value A locale identifier created from the components specified in dict.
	 * @Discussion This reverses the actions of componentsFromLocaleIdentifier:, so for example the dictionary {NSLocaleLanguageCode="en",
	 *             NSLocaleCountryCode="US", NSLocaleCalendar=NSJapaneseCalendar} becomes "en_US@calendar=japanese".
	 **/
	
	public static NSString localeIdentifierFromComponents(NSDictionary<NSString, NSString> dict) {

		NSString lang = dict.getWrappedDictionary().get(new NSString("NSLocaleLanguageCode"));
		NSString country = dict.getWrappedDictionary().get(new NSString("NSLocaleCountryCode"));
		NSString var = dict.getWrappedDictionary().get(new NSString("NSLocaleVariant"));

		String stringid = lang.wrappedString + "_" + country.wrappedString + "_" + var.wrappedString;

		return new NSString(stringid);
	}

	/**
	 * @Signature: canonicalLanguageIdentifierFromString:
	 * @Declaration : + (NSString *)canonicalLanguageIdentifierFromString:(NSString *)string
	 * @Description : Returns a canonical language identifier by mapping an arbitrary locale identification string to the canonical
	 *              identifier.
	 * @param string A string representation of an arbitrary locale identifier.
	 * @return Return Value A string that represents the canonical language identifier for the specified arbitrary locale identifier.
	 **/
	
	public static NSString canonicalLanguageIdentifierFromString(NSString string) {
        StringBuilder buffer = new StringBuilder(string.wrappedString);
		String lang = string.wrappedString.substring(0, buffer.indexOf("_"));
		return new NSString(lang.intern());
	}

	/**
	 * @Signature: localeIdentifierFromWindowsLocaleCode:
	 * @Declaration : + (NSString *)localeIdentifierFromWindowsLocaleCode:(uint32_t)lcid
	 * @Description : Returns a locale identifier from a Windows locale code.
	 * @param lcid The Windows locale code.
	 * @return Return Value The locale identifier.
	 **/
	
	public static NSString localeIdentifierFromWindowsLocaleCode(Long lcid) {
		NSString string = new NSString();
		InputStream in = GenericMainContext.sharedContext.getResources().openRawResource(
				AndroidRessourcesUtils.getResID("lcid_to_country_identifier", "raw"));
		Properties mProperties = new Properties();
		try {
			mProperties.load(in);
			String result = mProperties.getProperty("" + lcid);
			if (result != null) {
				string.setWrappedString(result);
				return string;
			}
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: windowsLocaleCodeFromLocaleIdentifier:
	 * @Declaration : + (uint32_t)windowsLocaleCodeFromLocaleIdentifier:(NSString *)localeIdentifier
	 * @Description : Returns a Window locale code from the locale identifier.
	 * @param localeIdentifier The locale identifier.
	 * @return Return Value The Windows locale code.
	 **/
	
	public static long windowsLocaleCodeFromLocaleIdentifier(NSLocale locale) {
		InputStream in = GenericMainContext.sharedContext.getResources().openRawResource(
				AndroidRessourcesUtils.getResID("country_to_lcid", "raw"));
		Properties mProperties = new Properties();
		try {
			mProperties.load(in);
			String lcid = mProperties.getProperty((locale.wrappedLocale).toString());
			return Long.valueOf(lcid);
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return -1;
	}

	/**
	 * @Signature: preferredLanguages
	 * @Declaration : + (NSArray *)preferredLanguages
	 * @Description : Returns the user's language preference order as an array of strings.
	 * @return Return Value The user's language preference order as an array of NSString objects, each of which is a canonicalized IETF BCP
	 *         47 language identifier.
	 **/
	
	public static NSArray<NSString> preferredLanguages() {
		Locale preffred = Locale.getDefault();
		NSString[] preffredLang = { new NSString(preffred.getLanguage()) };
		return new NSArray<NSString>(Arrays.asList(preffredLang));
	}

	/**
	 * @Signature: characterDirectionForLanguage:
	 * @Declaration : + (NSLocaleLanguageDirection)characterDirectionForLanguage:(NSString *)isoLangCode
	 * @Description : Returns the character direction for the specified ISO language code.
	 * @param isoLangCode The ISO language code.
	 * @return Return Value Returns the character direction for the language. See “NSLocaleLanguageDirection for possible values. If the
	 *         appropriate direction can’t be determined NSLocaleLanguageDirectionUnknown is returned.
	 **/
	
	public static NSLocaleLanguageDirection characterDirectionForLanguage(NSString codeLang) {

		List<String> array = new ArrayList<String>();

		array.add("ar");
		array.add("arc");
		array.add("dv");
		array.add("fa");
		array.add("ha");
		array.add("he");
		array.add("khw");
		array.add("ks");
		array.add("ku ");
		array.add("ps");
		array.add("ur");
		array.add("yi");

		String code = (codeLang.wrappedString).toLowerCase();
		if (array.contains(code)) {
			return NSLocaleLanguageDirection.NSLocaleLanguageDirectionRightToLeft;
		} else if ("ch".equals(code) || "ja".equals(code) || "ko".equals(code)) {
			return NSLocaleLanguageDirection.NSLocaleLanguageDirectionTopToBottom;
		} else {
			return NSLocaleLanguageDirection.NSLocaleLanguageDirectionLeftToRight;
		}

	}

	/**
	 * @Signature: lineDirectionForLanguage:
	 * @Declaration : + (NSLocaleLanguageDirection)lineDirectionForLanguage:(NSString *)isoLangCode
	 * @Description : Returns the line direction for the specified ISO language code.
	 * @param isoLangCode The ISO language code.
	 * @return Return Value Returns the line direction for the language. See “NSLocaleLanguageDirection for possible values. If the
	 *         appropriate direction can’t be determined NSLocaleLanguageDirectionUnknown is returned.
	 **/
	
	public static NSLocaleLanguageDirection lineDirectionForLanguage(NSString isoLangCode) {
		// TODO Line Direction Top To Bottom or vice versa
		return null;

	}

	/**
	 * @Signature: commonISOCurrencyCodes
	 * @Declaration : + (NSArray *)commonISOCurrencyCodes
	 * @Description : Returns an array of common ISO currency codes
	 * @return Return Value An array of NSString objects that represents common ISO currency codes.
	 * @Discussion Common codes may include, for example, AED, AUD, BZD, DKK, EUR, GBP, JPY, KES, MXN, OMR, STD, USD, XCD, and ZWD.
	 **/
	
	public static NSArray<NSString> commonISOCurrencyCodes() {

		List<NSString> list = Arrays.asList(new NSString("AED"), new NSString("AFN"), new NSString("ALL"), new NSString("AMD"),
				new NSString("ANG"), new NSString("AOA"), new NSString("ARS"), new NSString("AUD"), new NSString("AWG"),
				new NSString("AZN"), new NSString("BAM"), new NSString("BBD"), new NSString("BDT"), new NSString("BGN"),
				new NSString("BHD"), new NSString("BIF"), new NSString("BMD"), new NSString("BND"), new NSString("BOB"),
				new NSString("BRL"), new NSString("BSD"), new NSString("BTN"), new NSString("BWP"), new NSString("BYR"),
				new NSString("BZD"), new NSString("CAD"), new NSString("CDF"), new NSString("CHF"), new NSString("CLP"),
				new NSString("CNY"), new NSString("COP"), new NSString("CRC"), new NSString("CUC"), new NSString("CUP"),
				new NSString("CVE"), new NSString("CZK"), new NSString("DJF"), new NSString("DKK"), new NSString("DOP"),
				new NSString("DZD"), new NSString("EGP"), new NSString("ERN"), new NSString("ETB"), new NSString("EUR"),
				new NSString("FJD"), new NSString("FKP"), new NSString("GBP"), new NSString("GEL"), new NSString("GHS"),
				new NSString("GIP"), new NSString("GMD"), new NSString("GNF"), new NSString("GTQ"), new NSString("GWP"),
				new NSString("GYD"), new NSString("HKD"), new NSString("HNL"), new NSString("HRK"), new NSString("HTG"),
				new NSString("HUF"), new NSString("IDR"), new NSString("ILS"), new NSString("INR"), new NSString("IQD"),
				new NSString("IRR"), new NSString("ISK"), new NSString("JMD"), new NSString("JOD"), new NSString("JPY"),
				new NSString("KES"), new NSString("KGS"), new NSString("KHR"), new NSString("KMF"), new NSString("KPW"),
				new NSString("KRW"), new NSString("KWD"), new NSString("KYD"), new NSString("KZT"), new NSString("LAK"),
				new NSString("LBP"), new NSString("LKR"), new NSString("LRD"), new NSString("LSL"), new NSString("LTL"),
				new NSString("LVL"), new NSString("LYD"), new NSString("MAD"), new NSString("MDL"), new NSString("MGA"),
				new NSString("MKD"), new NSString("MMK"), new NSString("MNT"), new NSString("MOP"), new NSString("MRO"),
				new NSString("MUR"), new NSString("MVR"), new NSString("MWK"), new NSString("MXN"), new NSString("MYR"),
				new NSString("MZE"), new NSString("MZN"), new NSString("NAD"), new NSString("NGN"), new NSString("NIO"),
				new NSString("NOK"), new NSString("NPR"), new NSString("NZD"), new NSString("OMR"), new NSString("PAB"),
				new NSString("PEN"), new NSString("PGK"), new NSString("PHP"), new NSString("PKR"), new NSString("PLN"),
				new NSString("PYG"), new NSString("QAR"), new NSString("RON"), new NSString("RSD"), new NSString("RUB"),
				new NSString("RWF"), new NSString("SAR"), new NSString("SBD"), new NSString("SCR"), new NSString("SDG"),
				new NSString("SEK"), new NSString("SGD"), new NSString("SHP"), new NSString("SKK"), new NSString("SLL"),
				new NSString("SOS"), new NSString("SRD"), new NSString("SSP"), new NSString("STD"), new NSString("SVC"),
				new NSString("SYP"), new NSString("SZL"), new NSString("THB"), new NSString("TJS"), new NSString("TMT"),
				new NSString("TND"), new NSString("TOP"), new NSString("TRY"), new NSString("TTD"), new NSString("TWD"),
				new NSString("TZS"), new NSString("UAH"), new NSString("UGX"), new NSString("USD"), new NSString("UYU"),
				new NSString("UZS"), new NSString("VEF"), new NSString("VND"), new NSString("VUV"), new NSString("WST"),
				new NSString("XAF"), new NSString("XCD"), new NSString("XOF"), new NSString("XPF"), new NSString("YER"),
				new NSString("ZAR"), new NSString("ZMW"));

		NSArray<NSString> array = new NSArray<NSString>();
		array.setWrappedList(list);
		return array;
	}

	private NSLocale localeBuilder(NSString string) {

		String[] strTab = string.wrappedString.split("_");

		if (strTab.length >= 3) {
			this.setLocale(new Locale(strTab[0], strTab[1], strTab[2]));

		} else if (strTab.length == 2) {
			this.setLocale(new Locale(strTab[0], strTab[1]));
		} else {
			this.setLocale(new Locale(strTab[0]));

		}

		return this;

	}

	/**
	 * NSLocale Component Keys The following constants specify keys used to retrieve components of a locale with objectForKey:.
	 */

	public static final NSString NSLocaleIdentifier = new NSString(" kCFLocaleIdentifierKey ");
	public static final NSString NSLocaleLanguageCode = new NSString(" kCFLocaleLanguageCodeKey ");
	public static final NSString NSLocaleCountryCode = new NSString(" kCFLocaleCountryCodeKey ");
	public static final NSString NSLocaleScriptCode = new NSString(" kCFLocaleScriptCodeKey ");
	public static final NSString NSLocaleVariantCode = new NSString(" kCFLocaleVariantCodeKey ");
	public static final NSString NSLocaleExemplarCharacterSet = new NSString(" kCFLocaleExemplarCharacterSetKey ");
	public static final NSString NSLocaleCalendar = new NSString(" kCFLocaleCalendarKey ");
	public static final NSString NSLocaleCollationIdentifier = new NSString(" collation ");
	public static final NSString NSLocaleUsesMetricSystem = new NSString(" kCFLocaleUsesMetricSystemKey ");
	public static final NSString NSLocaleMeasurementSystem = new NSString(" kCFLocaleMeasurementSystemKey ");
	public static final NSString NSLocaleDecimalSeparator = new NSString(" kCFLocaleDecimalSeparatorKey ");
	public static final NSString NSLocaleGroupingSeparator = new NSString(" kCFLocaleGroupingSeparatorKey ");
	public static final NSString NSLocaleCurrencySymbol = new NSString(" kCFLocaleCurrencySymbolKey ");
	public static final NSString NSLocaleCurrencyCode = new NSString(" currency ");
	public static final NSString NSLocaleCollatorIdentifier = new NSString(" kCFLocaleCollatorIdentifierKey ");
	public static final NSString NSLocaleQuotationBeginDelimiterKey = new NSString(" kCFLocaleQuotationBeginDelimiterKey ");
	public static final NSString NSLocaleQuotationEndDelimiterKey = new NSString(" kCFLocaleQuotationEndDelimiterKey ");
	public static final NSString NSLocaleAlternateQuotationBeginDelimiterKey = new NSString(
			" kCFLocaleAlternateQuotationBeginDelimiterKey ");
	public static final NSString NSLocaleAlternateQuotationEndDelimiterKey = new NSString(" kCFLocaleAlternateQuotationEndDelimiterKey ");

	/**
	 * NSLocale Calendar Keys These constants identify NSCalendar instances.
	 */

	public static final NSString NSGregorianCalendar = new NSString("gregorian");
	public static final NSString NSBuddhistCalendar = new NSString("buddhist");
	public static final NSString NSChineseCalendar = new NSString("chinese");
	public static final NSString NSHebrewCalendar = new NSString("hebrew");
	public static final NSString NSIslamicCalendar = new NSString("islamic");
	public static final NSString NSIslamicCivilCalendar = new NSString("islamic-civil");
	public static final NSString NSJapaneseCalendar = new NSString("japanese");
	public static final NSString NSRepublicOfChinaCalendar = new NSString("roc");
	public static final NSString NSPersianCalendar = new NSString("persian");
	public static final NSString NSIndianCalendar = new NSString("indian");
	public static final NSString NSISO8601Calendar = new NSString("iso8601");

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}