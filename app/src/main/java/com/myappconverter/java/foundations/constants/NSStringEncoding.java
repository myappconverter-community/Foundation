package com.myappconverter.java.foundations.constants;

import android.widget.Switch;

import java.nio.charset.Charset;

import com.myappconverter.java.foundations.NSString;


public final class NSStringEncoding {

	public static final int NSASCIIStringEncoding = 1;
	public static final int NSNEXTSTEPStringEncoding = 2;
	public static final int NSJapaneseEUCStringEncoding = 3;
	public static final int NSUTF8StringEncoding = 4;
	public static final int NSISOLatin1StringEncoding = 5;
	public static final int NSSymbolStringEncoding = 6;
	public static final int NSNonLossyASCIIStringEncoding = 7;
	public static final int NSShiftJISStringEncoding = 8;
	public static final int NSISOLatin2StringEncoding = 9;
	public static final int NSUnicodeStringEncoding = 10;
	public static final int NSWindowsCP1251StringEncoding = 11;
	public static final int NSWindowsCP1252StringEncoding = 12;
	public static final int NSWindowsCP1253StringEncoding = 13;
	public static final int NSWindowsCP1254StringEncoding = 14;
	public static final int NSWindowsCP1250StringEncoding = 15;
	public static final int NSISO2022JPStringEncoding = 21;
	public static final int NSMacOSRomanStringEncoding = 30;
	public static final int NSUTF16StringEncoding = NSUnicodeStringEncoding;
	public static final int NSUTF16BigEndianStringEncoding = 0x90000100;
	public static final int NSUTF16LittleEndianStringEncoding = 0x94000100;
	public static final int NSUTF32StringEncoding = 0x8c000100;
	public static final int NSUTF32BigEndianStringEncoding = 0x98000100;
	public static final int NSUTF32LittleEndianStringEncoding = 0x9c000100;
	public static final int NSProprietaryStringEncoding = 65536;


	public static int getIntFromCharset(String charSetName){
		switch (charSetName){
			case "US-ASCII":
				return 1;
//			case Charset.defaultCharset().name():
//				return 2;
			case "EUC-JP":
			return 3;
			case "UTF-8":
			return 4;
			case "ISO-8859-1":
			return 5;
//			case "UTF-8":duplicate
//			case "US-ASCII":duplicate
//			return 7;
			case "Shift_JIS":
			return 8;
			case "ISO-8859-2":
			return 9;
			case "default":
			return 10;
			case "windows-1251":
			return 11;
			case "windows-1252":
			return 12;
			case "windows-1253":
			return 13;
			case "windows-1254":
			return 14;
//			case "windows-1254":duplicate
			case "ISO-2022-JP":
			return 21;
			case "x-MacRoman":
				return 30;
			case "UTF-16BE":
				return 0x90000100;
			case "UTF-32":
				return 0x94000100;
			case "UTF-32BE":
				return 0x98000100;
			case "UTF-32LE":
				return 0x9c000100;
			default:
				return 2;
		}
	}

	public static Charset getCharsetFromInt(int code){
		switch(code){
			case 1:
				return Charset.forName("US-ASCII");
			case 2:
				return Charset.defaultCharset();
			case 3:
				return Charset.forName("EUC-JP");
			case 4:
				return Charset.forName("UTF-8");
			case 5:
				return Charset.forName("ISO-8859-1");
			case 6:
				return Charset.forName("UTF-8");
			case 7:
				return Charset.forName("US-ASCII");
			case 8:
				return Charset.forName("Shift_JIS");
			case 9:
				return Charset.forName("ISO-8859-2");
			case 10:
				return Charset.defaultCharset();
			case 11:
				return Charset.forName("windows-1251");
			case 12:
				return Charset.forName("windows-1252");
			case 13:
				return Charset.forName("windows-1253");
			case 14:
				return Charset.forName("windows-1254");
			case 15:
				return Charset.forName("windows-1254");
			case 21:
				return Charset.forName("ISO-2022-JP");
			case 30:
				return Charset.forName("x-MacRoman");
			case 0x90000100:
				return Charset.forName("UTF-16BE");
			case 0x94000100:
				return Charset.forName("UTF-32");
			case 0x8c000100:
				return Charset.forName("UTF-32");
			case 0x98000100:
				return Charset.forName("UTF-32BE");
			case 0x9c000100:
				return Charset.forName("UTF-32LE");
			case 65536:
				return Charset.forName("UTF-8");
			default: return Charset.defaultCharset();
		}
	}
}

