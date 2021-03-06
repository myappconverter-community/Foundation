package com.myappconverter.java.foundations.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSObject;
import com.myappconverter.mapping.utils.GenericMainContext;

public class PropertyListParser {

	/**
	 * Prevent instantiation.
	 */
	protected PropertyListParser() {
		/** empty **/
	}

	/**
	 * Reads all bytes from an InputStream and stores them in an array, up to a maximum count.
	 * 
	 * @param in The InputStream pointing to the data that should be stored in the array.
	 * @param max The maximum number of bytes to read.
	 */
	protected static byte[] readAll(InputStream in, int max) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		while (max > 0) {
			int n = in.read();
			if (n == -1)
				break; // EOF
			buf.write(n);
			max--;
		}
		return buf.toByteArray();
	}

	/**
	 * Parses a property list from a file.
	 * 
	 * @param filePath Path to the property list file.
	 * @return The root object in the property list. This is usually a NSDictionary but can also be a NSArray.
	 * @throws Exception If an error occurred while parsing.
	 */
	public static Object parse(String filePath) throws ParserConfigurationException, ParseException, SAXException,
			PropertyListFormatException, IOException {
		return parse(GenericMainContext.sharedContext.getAssets().open(filePath));
	}

	/**
	 * Parses a property list from a file.
	 * 
	 * @param f The property list file.
	 * @return The root object in the property list. This is usually a NSDictionary but can also be a NSArray.
	 * @throws Exception If an error occurred while parsing.
	 */
	public static NSObject parse(File f) throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException,
			SAXException {
		FileInputStream fis = new FileInputStream(f);
		String magicString = new String(readAll(fis, 8), 0, 8);
		fis.close();
		if (magicString.startsWith("bplist")) {
			return BinaryPropertyListParser.parse(f);
		} else if (magicString.trim().startsWith("(") || magicString.trim().startsWith("{") || magicString.trim().startsWith("/")) {
			return ASCIIPropertyListParser.parse(f);
		} else {
			return XMLPropertyListParser.parse(f);
		}
	}

	
	public static NSObject parse(FileInputStream f) throws IOException, PropertyListFormatException, ParseException,
			ParserConfigurationException, SAXException {
		// FileInputStream fis = new FileInputStream(f);
		String magicString = new String(readAll(f, 8), 0, 8);
		f.close();
		if (magicString.startsWith("bplist")) {
			return BinaryPropertyListParser.parse(f);
		} else if (magicString.trim().startsWith("(") || magicString.trim().startsWith("{") || magicString.trim().startsWith("/")) {
			return ASCIIPropertyListParser.parse(f);
		} else {
			return XMLPropertyListParser.parse(f);
		}
	}
	
	/**
	 * Parses a property list from a byte array.
	 * 
	 * @param bytes The property list data as a byte array.
	 * @return The root object in the property list. This is usually a NSDictionary but can also be a NSArray.
	 * @throws Exception If an error occurred while parsing.
	 */
	public static NSObject parse(byte[] bytes) throws IOException, PropertyListFormatException, ParseException,
			ParserConfigurationException, SAXException {
		String magicString = new String(bytes, 0, 8);
		if (magicString.startsWith("bplist")) {
			return BinaryPropertyListParser.parse(bytes);
		} else if (magicString.trim().startsWith("(") || magicString.trim().startsWith("{") || magicString.trim().startsWith("/")) {
			return ASCIIPropertyListParser.parse(bytes);
		} else {
			return XMLPropertyListParser.parse(bytes);
		}
	}

	/**
	 * Parses a property list from an InputStream.
	 * 
	 * @param is The InputStream delivering the property list data.
	 * @return The root object of the property list. This is usually a NSDictionary but can also be a NSArray.
	 * @throws Exception If an error occurred while parsing.
	 */
	public static NSObject parse(InputStream is) throws IOException, PropertyListFormatException, ParseException,
			ParserConfigurationException, SAXException {
		if (is.markSupported()) {
			is.mark(10);
			String magicString = new String(readAll(is, 8), 0, 8);
			is.reset();
			if (magicString.startsWith("bplist")) {
				return BinaryPropertyListParser.parse(is);
			} else if (magicString.trim().startsWith("(") || magicString.trim().startsWith("{") || magicString.trim().startsWith("/")) {
				return ASCIIPropertyListParser.parse(is);
			} else {
				return XMLPropertyListParser.parse(is);
			}
		} else {
			// Now we have to read everything, because if one parsing method fails
			// the whole InputStream is lost as we can't reset it
			return parse(readAll(is, Integer.MAX_VALUE));
		}
	}

	/**
	 * Saves a property list with the given object as root into a XML file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsXML(NSObject root, File out) throws IOException {
		File parent = out.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		FileOutputStream fous = new FileOutputStream(out);
		saveAsXML(root, fous);
		fous.close();
	}

	/**
	 * Saves a property list with the given object as root in XML format into an output stream.
	 * 
	 * @param root The root object.
	 * @param out The output stream.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsXML(NSObject root, OutputStream out) throws IOException {
		OutputStreamWriter w = new OutputStreamWriter(out, "UTF-8");
		w.write(root.toXMLPropertyList());
		w.close();
	}

	/**
	 * Converts a given property list file into the OS X and iOS XML format.
	 * 
	 * @param in The source file.
	 * @param out The target file.
	 * @throws Exception When an error occurs during parsing or converting.
	 */
	public static void convertToXml(File in, File out) throws ParserConfigurationException, ParseException, SAXException,
			PropertyListFormatException, IOException {
		NSObject root = parse(in);
		saveAsXML(root, out);
	}

	/**
	 * Saves a property list with the given object as root into a binary file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsBinary(NSObject root, File out) throws IOException {
		File parent = out.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		BinaryPropertyListWriter.write(out, root);
	}

	/**
	 * Saves a property list with the given object as root in binary format into an output stream.
	 * 
	 * @param root The root object.
	 * @param out The output stream.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsBinary(NSObject root, OutputStream out) throws IOException {
		BinaryPropertyListWriter.write(out, root);
	}

	/**
	 * Converts a given property list file into the OS X and iOS binary format.
	 * 
	 * @param in The source file.
	 * @param out The target file.
	 * @throws Exception When an error occurs during parsing or converting.
	 */
	public static void convertToBinary(File in, File out) throws IOException, ParserConfigurationException, ParseException, SAXException,
			PropertyListFormatException {
		NSObject root = parse(in);
		saveAsBinary(root, out);
	}

	/**
	 * Saves a property list with the given object as root into a ASCII file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsASCII(NSDictionary root, File out) throws IOException {
		File parent = out.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(out), "ASCII");
		w.write(root.toASCIIPropertyList());
		w.close();
	}

	/**
	 * Saves a property list with the given object as root into a ASCII file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsASCII(NSArray root, File out) throws IOException {
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(out), "ASCII");
		w.write(root.toASCIIPropertyList());
		w.close();
	}

	/**
	 * Converts a given property list file into ASCII format.
	 * 
	 * @param in The source file.
	 * @param out The target file.
	 * @throws Exception When an error occurs during parsing or converting.
	 */
	public static void convertToASCII(File in, File out) throws ParserConfigurationException, ParseException, SAXException,
			PropertyListFormatException, IOException {
		NSObject root = parse(in);
		try {
			saveAsASCII((NSDictionary) root, out);
		} catch (Exception ex) {
			try {
				saveAsASCII((NSArray) root, out);
			} catch (Exception ex2) {
				throw new PropertyListFormatException("The root of the given input property list "
						+ "is neither a Dictionary nor an Array!");
			}
		}
	}

	/**
	 * Saves a property list with the given object as root into a ASCII file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsGnuStepASCII(NSDictionary root, File out) throws IOException {
		File parent = out.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(out), "ASCII");
		w.write(root.toGnuStepASCIIPropertyList());
		w.close();
	}

	/**
	 * Saves a property list with the given object as root into a ASCII file.
	 * 
	 * @param root The root object.
	 * @param out The output file.
	 * @throws IOException When an error occurs during the writing process.
	 */
	public static void saveAsGnuStepASCII(NSArray root, File out) throws IOException {
		File parent = out.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(out), "ASCII");
		w.write(root.toGnuStepASCIIPropertyList());
		w.close();
	}

	/**
	 * Converts a given property list file into ASCII format.
	 * 
	 * @param in The source file.
	 * @param out The target file.
	 * @throws Exception When an error occurs during parsing or converting.
	 */
	public static void convertToGnuStepASCII(File in, File out) throws ParserConfigurationException, ParseException, SAXException,
			PropertyListFormatException, IOException {
		NSObject root = parse(in);
		try {
			saveAsGnuStepASCII((NSDictionary) root, out);
		} catch (Exception ex) {
			try {
				saveAsGnuStepASCII((NSArray) root, out);
			} catch (Exception ex2) {
				throw new PropertyListFormatException("The root of the given input property list "
						+ "is neither a Dictionary nor an Array!");
			}
		}
	}
}
