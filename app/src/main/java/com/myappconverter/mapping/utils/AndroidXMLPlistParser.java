package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSObject;
import com.myappconverter.java.foundations.NSString;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

public class AndroidXMLPlistParser extends DefaultHandler {

	private final ArrayList<Map<NSString, NSObject>> root_dict = new ArrayList<Map<NSString, NSObject>>();
	private Stack<Container> stack = new Stack<Container>();
	private StringBuffer text;
	private String keyname;

	@Override
	public void startElement(String uri, String elementName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("dict")) {
			NSDictionaryDict dict = new NSDictionaryDict();
			dict.key = keyname;
			stack.push(dict);
			if (root_dict.isEmpty())
				// FIXME
				root_dict.addAll((Collection<? extends Map<NSString, NSObject>>) dict);
			return;
		}

		if (stack.isEmpty())
			return;

		if (qName.equals("array")) {
			NSDictionaryArray list = new NSDictionaryArray();
			list.key = keyname;
			stack.push(list);
			return;
		}

		if (qName.equals("key") || qName.equals("string") || qName.equals("integer") || qName.equals("real") || qName.equals("date")
				|| qName.equals("data"))
			text = new StringBuffer();
	}

	@Override
	public void endElement(String uri, String elementName, String qName) throws SAXException {
		if (stack.isEmpty())
			return;

		if (qName.equals("dict") || qName.equals("array")) {
			Container child = stack.pop();
			if (!stack.isEmpty())
				stack.peek().store(child.getkey(), child);
			keyname = null;
			text = null;
			return;
		}

		if (qName.equals("key")) {
			keyname = text.toString();
			text = null;
			return;
		}

		Object item = null;
		if (qName.equals("true"))
			item = Boolean.TRUE;
		else if (qName.equals("false"))
			item = Boolean.FALSE;
		else if (qName.equals("string"))
			item = text.toString();
		else if (qName.equals("integer"))
			item = Integer.valueOf(text.toString());
		else if (qName.equals("real"))
			item = Double.valueOf(text.toString());
		else if (qName.equals("date"))
			try {
				item = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.getDefault()).parse(text.toString());
			} catch (ParseException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));

			}
		else if (qName.equals("data"))
			item = new NSData(MiGBase64.decode(text.toString()));
		if (item != null)
			stack.peek().store(keyname, item);
		keyname = null;
		text = null;
	}

	@Override
	public void endDocument() throws SAXException {
		Log.d("XMLPlist", "end document");
		super.endDocument();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (text != null)
			text.append(new String(ch, start, length));
	}

	public Map<NSString, NSObject> getResult() {
		Log.d("XMLPlist", "elements size : " + root_dict.size());
		return root_dict.isEmpty() ? null : root_dict.get(0);
	}

	private interface Container {

		String getkey();

		void store(String key, Object item);
	}

	private class NSDictionaryDict extends HashMap<String, Object> implements Container {

		private static final long serialVersionUID = 1L;
		private String key;

		@Override
		public void store(String key, Object item) {
			if (key == null)
				throw new NullPointerException("NSDictionary <dict> error: <key> not defined");
			put(key, item);
		}

		@Override
		public String getkey() {
			return key;
		}
	}

	private class NSDictionaryArray extends ArrayList<Object> implements Container {

		private static final long serialVersionUID = 1L;
		private String key;

		@Override
		public void store(String key, Object item) {
			add(item);
		}

		@Override
		public String getkey() {
			return key;
		}
	}

}
