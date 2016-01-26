package com.myappconverter.java.foundations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.foundations.protocols.NSXMLParserDelegate;



public class NSXMLParser extends NSObject {
	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	// Tag for Log
	protected static final String TAG = "NSXMLParser";
	// flag
	private boolean abortParsing = false;
	private NSData wrappedXmlData;
	//
	private NSXMLParserDelegate delegate;
	private XmlPullParser xmlPullParser;
	//
	private boolean shouldReportNamespacePrefixes;
	private boolean shouldResolveExternalEntities;
	//
	private NSString publicId;
	private NSString systemId;
	// error handling
	NSError nsError;

	public NSData getWrappedXmlData() {
		return wrappedXmlData;
	}

	public void setWrappedXmlData(NSData wrappedXmlData) {
		this.wrappedXmlData = wrappedXmlData;
	}

	public NSXMLParser() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlPullParser = factory.newPullParser();
			if (wrappedXmlData == null) {
				wrappedXmlData = new NSData();
			}
			wrappedXmlData.setWrappedData(new byte[1024 * 1024]);
			nsError = new NSError();
		} catch (XmlPullParserException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	public NSXMLParser(NSData data) {
		this.wrappedXmlData = data;
	}

	// Initializing a Parser Object

	/**
	 * @Signature: initWithContentsOfURL:
	 * @Declaration : - (id)initWithContentsOfURL:(NSURL *)url
	 * @Description : Initializes the receiver with the XML content referenced by the given URL.
	 * @param url An NSURL object specifying a URL. The URL must be fully qualified and refer to a scheme that is supported by the NSURL
	 *            class.
	 * @return Return Value An initialized NSXMLParser object or nil if an error occurs.
	 **/

	public NSXMLParser initWithContentsOfURL(NSURL url) {
		NSXMLParser nsxmlParser = new NSXMLParser();
		nsxmlParser.wrappedXmlData.initWithContentsOfURL(url);
		return nsxmlParser;
	}

	/**
	 * @Signature: initWithData:
	 * @Declaration : - (id)initWithData:(NSData *)data
	 * @Description : Initializes the receiver with the XML contents encapsulated in a given data object.
	 * @param data An NSData object containing XML markup.
	 * @return Return Value An initialized NSXMLParser object or nil if an error occurs.
	 * @Discussion This method is the designated initializer.
	 **/

	public NSXMLParser initWithData(NSData data) {
		NSXMLParser nsxmlParser = new NSXMLParser();
		nsxmlParser.wrappedXmlData.initWithData(data);
		return nsxmlParser;
	}

	/**
	 * @Signature: initWithStream:
	 * @Declaration : - (id)initWithStream:(NSInputStream *)stream
	 * @Description : Initializes the receiver with the XML contents from the specified stream and parses it..
	 * @param stream The input stream. The content is incrementally loaded from the specified stream and parsed. The NSXMLParser will open
	 *            the stream, and synchronously read from it without scheduling it.
	 * @return Return Value An initialized NSXMLParser object or nil if an error occurs.
	 **/

	public NSXMLParser initWithStream(NSInputStream stream) {
		try {
			int avaibledatasize = stream.getWrappedInputStream().available();
			wrappedXmlData.setWrappedData(ByteBuffer.allocate(avaibledatasize).array());
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		try {
			AndroidIOUtils.read(stream.getWrappedInputStream(), wrappedXmlData.getWrappedData());
			return this;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	// Managing Delegates

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id < NSXMLParserDelegate >)delegate
	 * @Description : Sets the receiver’s delegate.
	 * @param delegate An object that is the new delegate. It is not retained. The delegate must conform to the NSXMLParserDelegate Protocol
	 *            protocol.
	 **/

	public <E extends NSXMLParserDelegate> void setDelegate(E delegate) {
		this.delegate = delegate;
	}

	/**
	 * @Signature: delegate
	 * @Declaration : - (id < NSXMLParserDelegate >)delegate
	 * @Description : Returns the receiver’s delegate.
	 **/

	public NSXMLParserDelegate delegate() {
		return delegate;
	}

	// Managing Parser Behavior
	/**
	 * @Declaration : - (void)setShouldProcessNamespaces:(BOOL)shouldProcessNamespaces
	 * @Description : Specifies whether the receiver reports the namespace and the qualified name of an element in related delegation
	 *              methods .
	 * @param shouldProcessNamespaces YES if the receiver should report the namespace and qualified name of each element, NO otherwise. The
	 *            default value is NO.
	 * @Discussion The invoked delegation methods are parser:didStartElement:namespaceURI:qualifiedName:attributes: and
	 *             parser:didEndElement:namespaceURI:qualifiedName:.
	 */

	public void setShouldProcessNamespaces(boolean shouldProcessNamespaces) {
		try {
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, shouldReportNamespacePrefixes);
		} catch (XmlPullParserException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Declaration : - (BOOL)shouldProcessNamespaces
	 * @Description : Indicates whether the receiver reports the namespace and the qualified name of an element in related delegation
	 *              methods.
	 * @return Return Value YES if the receiver reports namespace and qualified name, NO otherwise.
	 * @Discussion The invoked delegation methods are parser:didStartElement:namespaceURI:qualifiedName:attributes: and
	 *             parser:didEndElement:namespaceURI:qualifiedName:.
	 */

	public boolean shouldProcessNamespaces() {
		return xmlPullParser.getFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES);
	}

	/**
	 * @Declaration : - (void)setShouldReportNamespacePrefixes:(BOOL)shouldReportNamespacePrefixes
	 * @Description : Specifies whether the receiver reports the scope of namespace declarations using related delegation methods.
	 * @param shouldReportNamespacePrefixes YES if the receiver should report the scope of namespace declarations, NO otherwise. The default
	 *            value is NO.
	 * @Discussion The invoked delegation methods are parser:didStartMappingPrefix:toURI: and parser:didEndMappingPrefix:.
	 */

	public void setShouldReportNamespacePrefixes(boolean shouldReportNamespacePrefixes) {
		this.shouldReportNamespacePrefixes = shouldReportNamespacePrefixes;
	}

	/**
	 * @Declaration : - (BOOL)shouldReportNamespacePrefixes
	 * @Description : Indicates whether the receiver reports the prefixes indicating the scope of namespace declarations using related
	 *              delegation methods.
	 * @return Return Value YES if the receiver reports the scope of namespace declarations, NO otherwise. The default value is NO.
	 * @Discussion The invoked delegation methods are parser:didStartMappingPrefix:toURI: and parser:didEndMappingPrefix:.
	 */

	public boolean shouldReportNamespacePrefixes() {
		return shouldReportNamespacePrefixes;

	}

	/**
	 * @Declaration : - (void)setShouldResolveExternalEntities:(BOOL)shouldResolveExternalEntities
	 * @Description : Specifies whether the receiver reports declarations of external entities using the delegate method
	 *              parser:foundExternalEntityDeclarationWithName:publicID:systemID:.
	 * @param shouldResolveExternalEntities YES if the receiver should report declarations of external entities, NO otherwise. The default
	 *            value is NO.
	 * @Discussion If you pass in YES, you may cause other I/O operations, either network-based or disk-based, to load the external DTD.
	 */

	public void setShouldResolveExternalEntities(boolean shouldResolveExternalEntities) {
		this.shouldResolveExternalEntities = shouldResolveExternalEntities;
	}

	/**
	 * @Declaration : - (BOOL)shouldResolveExternalEntities
	 * @Description : Indicates whether the receiver reports declarations of external entities using the delegate method
	 *              parser:foundExternalEntityDeclarationWithName:publicID:systemID:.
	 * @return Return Value YES if the receiver reports declarations of external entities, NO otherwise. The default value is NO.
	 */

	public boolean shouldResolveExternalEntities() {
		return shouldResolveExternalEntities;
	}

	// Parsing
	/**
	 * @Declaration : - (BOOL)parse
	 * @Description : Starts the event-driven parsing operation.
	 * @return Return Value YES if parsing is successful and NO in there is an error or if the parsing operation is aborted.
	 */

	public boolean parse() {
		ByteArrayInputStream bai = null;
		if (wrappedXmlData == null)
			return false;
		try {
			bai = new ByteArrayInputStream(wrappedXmlData.getWrappedData());
			xmlPullParser.setInput(bai, "UTF-8");
			int eventType = xmlPullParser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT && !abortParsing) {
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT:
							delegate.parserDidStartDocument(this);
							break;

						case XmlPullParser.CDSECT:
							NSData nsData = new NSData();
							nsData.setWrappedData(xmlPullParser.getText().getBytes());
							delegate.parserFoundCDATA(this, nsData);
							break;

						case XmlPullParser.DOCDECL:
							if (xmlPullParser.getFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL)) {
								// TODO need IOS Example
							}
							break;
						case XmlPullParser.PROCESSING_INSTRUCTION:
							// TODO need IOS Example

							break;
						case XmlPullParser.ENTITY_REF:
							break;

						case XmlPullParser.IGNORABLE_WHITESPACE:
							NSString whitespaceString = new NSString(xmlPullParser.getText());
							delegate.parserFoundIgnorableWhitespace(this, whitespaceString);
							break;

						case XmlPullParser.COMMENT:
							delegate.parserFoundComment(this, new NSString(xmlPullParser.getText()));
							break;

						case XmlPullParser.START_TAG:
							NSDictionary<NSString, Object> attributes = new NSDictionary<NSString, Object>();
							NSString elementName = new NSString(xmlPullParser.getName());
							NSString namespaceURI = new NSString(xmlPullParser.getNamespace());
							NSString prefix = new NSString(xmlPullParser.getPrefix());
							NSString qualifiedName = new NSString(prefix.getWrappedString() + elementName.getWrappedString());
							if (xmlPullParser.getAttributeCount() != -1) {
								for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
									NSString attributeName = new NSString(xmlPullParser.getAttributeName(i));
									NSString attributeValue = new NSString(xmlPullParser.getAttributeValue(i));
									Object objectValue = attributeValue;
									try {
										NSNumber number = new NSNumber();
										number.numberValue = attributeValue.getWrappedString();
										objectValue = number;

									} catch (NumberFormatException exc) {

									}
									attributes.getWrappedDictionary().put(attributeName, objectValue);
								}
							}
							delegate.parserDidStartElementNamespaceURIQualifiedNameAttributes(this, elementName, namespaceURI, qualifiedName,
									attributes);
							break;

						case XmlPullParser.END_TAG:
							NSString elementNameEnd = new NSString(xmlPullParser.getName());
							NSString namespaceURIEnd = new NSString(xmlPullParser.getNamespace());
							NSString prefixEnd = new NSString(xmlPullParser.getPrefix());
							NSString qualifiedNameEnd = new NSString(prefixEnd.getWrappedString() + elementNameEnd.getWrappedString());
							delegate.parserDidEndElementNamespaceURIQualifiedName(this, elementNameEnd, namespaceURIEnd, qualifiedNameEnd);
							break;

						case XmlPullParser.TEXT:
							delegate.parserFoundCharacters(this, new NSString(xmlPullParser.getText()));
							break;

						case XmlPullParser.END_DOCUMENT:
							delegate.parserDidEndDocument(this);
							break;

						default:
							break;

					}
					eventType = xmlPullParser.next();
				}
			}
			if(eventType == XmlPullParser.END_DOCUMENT){
				delegate.parserDidEndDocument(this);
			}
		} catch (XmlPullParserException ex) {
			LOGGER.info(String.valueOf(ex));
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} finally {
			if (bai != null)
				try {
					bai.close();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
		}
		return true;
	}

	/**
	 * @Declaration : - (void)abortParsing
	 * @Description : Stops the parser object.
	 * @Discussion If you invoke this method, the delegate, if it implements parser:parseErrorOccurred:, is informed of the cancelled
	 *             parsing operation.
	 */

	public void abortParsing() {
		abortParsing = true;
	}

	/**
	 * @Declaration : - (NSError *)parserError
	 * @Description : Returns an NSError object from which you can obtain information about a parsing error.
	 * @Discussion You may invoke this method after a parsing operation abnormally terminates to determine the cause of error.
	 */

	public NSError parserError() {
		return nsError;
	}

	// Obtaining Parser State
	/**
	 * @Signature: columnNumber
	 * @Declaration : - (NSInteger)columnNumber
	 * @Description : Returns the column number of the XML document being processed by the receiver.
	 * @Discussion The column refers to the nesting level of the XML elements in the document. You may invoke this method once a parsing
	 *             operation has begun or after an error occurs.
	 **/

	public int columnNumber() {
		return xmlPullParser.getColumnNumber();
	}

	/**
	 * @Signature: lineNumber
	 * @Declaration : - (NSInteger)lineNumber
	 * @Description : Returns the line number of the XML document being processed by the receiver.
	 * @Discussion You may invoke this method once a parsing operation has begun or after an error occurs.
	 **/

	public int lineNumber() {
		return xmlPullParser.getLineNumber();
	}

	/**
	 * @Signature: publicID
	 * @Declaration : - (NSString *)publicID
	 * @Description : Returns the public identifier of the external entity referenced in the XML document.
	 * @Discussion You may invoke this method once a parsing operation has begun or after an error occurs.
	 **/

	public NSString publicID() {
		return publicId;
	}

	/**
	 * @Signature: systemID
	 * @Declaration : - (NSString *)systemID
	 * @Description : Returns the system identifier of the external entity referenced in the XML document.
	 * @Discussion You may invoke this method once a parsing operation has begun or after an error occurs.
	 **/

	public NSString systemID() {
		return systemId;
	}
}