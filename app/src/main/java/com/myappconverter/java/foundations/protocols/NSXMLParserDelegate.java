package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.NSXMLParser;


public interface NSXMLParserDelegate {

	// 1
	/**
	 * @Signature: parser:didEndElement:namespaceURI:qualifiedName:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI
	 *              qualifiedName:(NSString *)qName
	 * @Description : Sent by a parser object to its delegate when it encounters an end tag for a specific element.
	 **/

	public void parserDidEndElementNamespaceURIQualifiedName(NSXMLParser parser, NSString elementName, NSString namespaceURI, NSString qName);

	// 2
	/**
	 * @Signature: parser:didEndMappingPrefix:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser didEndMappingPrefix:(NSString *)prefix
	 * @Description : Sent by a parser object to its delegate when a given namespace prefix goes out of scope.
	 * @Discussion The parser sends this message only when namespace-prefix reporting is turned on through the
	 *             setShouldReportNamespacePrefixes: method.
	 **/

	public void parserDidEndMappingPrefix(NSXMLParser parser, NSString prefix);

	// 3
	/**
	 * @Signature: parser:didStartElement:namespaceURI:qualifiedName:attributes:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI
	 *              qualifiedName:(NSString *)qualifiedName attributes:(NSDictionary *)attributeDict
	 * @Description : Sent by a parser object to its delegate when it encounters a start tag for a given element.
	 **/

	public void parserDidStartElementNamespaceURIQualifiedNameAttributes(NSXMLParser parser, NSString elementName, NSString namespaceURI,
																		 NSString qualifiedName, NSDictionary<?, ?> attributeDict);

	// 4
	/**
	 * @Signature: parser:didStartMappingPrefix:toURI:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser didStartMappingPrefix:(NSString *)prefix toURI:(NSString *)namespaceURI
	 * @Description : Sent by a parser object to its delegate the first time it encounters a given namespace prefix, which is mapped to a
	 *              URI.
	 * @Discussion The parser object sends this message only when namespace-prefix reporting is turned on through the
	 *             setShouldReportNamespacePrefixes: method.
	 **/

	public void parserDidStartMappingPrefixToURI(NSXMLParser parser, NSString prefix, NSString namespaceURI);

	// 5
	/**
	 * @Signature: parser:foundAttributeDeclarationWithName:forElement:type:defaultValue:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundAttributeDeclarationWithName:(NSString *)attributeName forElement:(NSString
	 *              *)elementName type:(NSString *)type defaultValue:(NSString *)defaultValue
	 * @Description : Sent by a parser object to its delegate when it encounters a declaration of an attribute that is associated with a
	 *              specific element.
	 **/

	public void parserFoundAttributeDeclarationWithNameForElementTypeDefaultValue(NSXMLParser parser, NSString attributeName,
																				  NSString elementName, NSString type, NSString defaultValue);

	// 6
	/**
	 * @Signature: parser:foundCDATA:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundCDATA:(NSData *)CDATABlock
	 * @Description : Sent by a parser object to its delegate when it encounters a CDATA block.
	 * @Discussion Through this method the parser object passes the contents of the block to its delegate in an NSData object. The CDATA
	 *             block is character data that is ignored by the parser. The encoding of the character data is UTF-8. To convert the data
	 *             object to a string object, use the NSString method initWithData:encoding:.
	 **/

	public void parserFoundCDATA(NSXMLParser parser, NSData CDATABlock);

	// 7
	/**
	 * @Signature: parser:foundCharacters:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
	 * @Description : Sent by a parser object to provide its delegate with a string representing all or part of the characters of the
	 *              current element.
	 * @Discussion The parser object may send the delegate several parser:foundCharacters: messages to report the characters of an element.
	 *             Because string may be only part of the total character content for the current element, you should append it to the
	 *             current accumulation of characters until the element changes.
	 **/

	public void parserFoundCharacters(NSXMLParser parser, NSString string);

	// 8
	/**
	 * @Signature: parser:foundComment:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundComment:(NSString *)comment
	 * @Description : Sent by a parser object to its delegate when it encounters a comment in the XML.
	 **/

	public void parserFoundComment(NSXMLParser parser, NSString comment);

	// 9
	/**
	 * @Signature: parser:foundElementDeclarationWithName:model:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundElementDeclarationWithName:(NSString *)elementName model:(NSString *)model
	 * @Description : Sent by a parser object to its delegate when it encounters a declaration of an element with a given model.
	 **/

	public void parserFoundElementDeclarationWithNameModel(NSXMLParser parser, NSString elementName, NSString model);

	// 10
	/**
	 * @Signature: parser:foundExternalEntityDeclarationWithName:publicID:systemID:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundExternalEntityDeclarationWithName:(NSString *)entityName publicID:(NSString
	 *              *)publicID systemID:(NSString *)systemID
	 * @Description : Sent by a parser object to its delegate when it encounters an external entity declaration.
	 **/

	public void parserFoundExternalEntityDeclarationWithNamePublicIDSystemID(NSXMLParser parser, NSString entityName, NSString publicID,
																			 NSString systemID);

	// 11
	/**
	 * @Signature: parser:foundIgnorableWhitespace:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundIgnorableWhitespace:(NSString *)whitespaceString
	 * @Description : Reported by a parser object to provide its delegate with a string representing all or part of the ignorable whitespace
	 *              characters of the current element.
	 * @Discussion All the whitespace characters of the element (including carriage returns, tabs, and new-line characters) may not be
	 *             provided through an individual invocation of this method. The parser may send the delegate several
	 *             parser:foundIgnorableWhitespace: messages to report the whitespace characters of an element. You should append the
	 *             characters in each invocation to the current accumulation of characters.
	 **/

	public void parserFoundIgnorableWhitespace(NSXMLParser parser, NSString whitespaceString);

	// 12
	/**
	 * @Signature: parser:foundInternalEntityDeclarationWithName:value:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundInternalEntityDeclarationWithName:(NSString *)name value:(NSString *)value
	 * @Description : Sent by a parser object to the delegate when it encounters an internal entity declaration.
	 **/

	public void parserFoundInternalEntityDeclarationWithNameValue(NSXMLParser parser, NSString name, NSString value);

	// 13
	/**
	 * @Signature: parser:foundNotationDeclarationWithName:publicID:systemID:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundNotationDeclarationWithName:(NSString *)name publicID:(NSString *)publicID
	 *              systemID:(NSString *)systemID
	 * @Description : Sent by a parser object to its delegate when it encounters a notation declaration.
	 **/

	public void parserFoundNotationDeclarationWithNamePublicIDSystemID(NSXMLParser parser, NSString name, NSString publicID,
																	   NSString systemID);

	// 14
	/**
	 * @Signature: parser:foundProcessingInstructionWithTarget:data:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundProcessingInstructionWithTarget:(NSString *)target data:(NSString *)data
	 * @Description : Sent by a parser object to its delegate when it encounters a processing instruction.
	 **/

	public void parserFoundProcessingInstructionWithTargetData(NSXMLParser parser, NSString target, NSString data);

	// 15
	/**
	 * @Signature: parser:foundUnparsedEntityDeclarationWithName:publicID:systemID:notationName:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser foundUnparsedEntityDeclarationWithName:(NSString *)name publicID:(NSString
	 *              *)publicID systemID:(NSString *)systemID notationName:(NSString *)notationName
	 * @Description : Sent by a parser object to its delegate when it encounters an unparsed entity declaration.
	 **/

	public void parserFoundUnparsedEntityDeclarationWithNamePublicIDSystemIDNotationName(NSXMLParser parser, NSString name,
																						 NSString publicID, NSString systemID, NSString notationName);

	// 16
	/**
	 * @Signature: parser:parseErrorOccurred:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError
	 * @Description : Sent by a parser object to its delegate when it encounters a fatal error.
	 * @Discussion When this method is invoked, parsing is stopped. For further information about the error, you can query parseError or you
	 *             can send the parser a parserError message. You can also send the parser lineNumber and columnNumber messages to further
	 *             isolate where the error occurred. Typically you implement this method to display information about the error to the user.
	 **/

	public void parserParseErrorOccurred(NSXMLParser parser, NSError parseError);

	// 17
	/**
	 * @Signature: parser:resolveExternalEntityName:systemID:
	 * @Declaration : - (NSData *)parser:(NSXMLParser *)parser resolveExternalEntityName:(NSString *)entityName systemID:(NSString
	 *              *)systemID
	 * @Description : Sent by a parser object to its delegate when it encounters a given external entity with a specific system ID.
	 * @Discussion The delegate can resolve the external entity (for example, locating and reading an externally declared DTD) and provide
	 *             the result to the parser object as an NSData object.
	 **/

	public void parserResolveExternalEntityNameSystemID(NSXMLParser parser, NSString entityName, NSString systemID);

	// 18
	/**
	 * @Signature: parser:validationErrorOccurred:
	 * @Declaration : - (void)parser:(NSXMLParser *)parser validationErrorOccurred:(NSError *)validError
	 * @Description : Sent by a parser object to its delegate when it encounters a fatal validation error. NSXMLParser currently does not
	 *              invoke this method and does not perform validation.
	 **/

	public void parserValidationErrorOccurred(NSXMLParser parser, NSError validError);

	// 19
	/**
	 * @Signature: parserDidEndDocument:
	 * @Declaration : - (void)parserDidEndDocument:(NSXMLParser *)parser
	 * @Description : Sent by the parser object to the delegate when it has successfully completed parsing.
	 **/

	public void parserDidEndDocument(NSXMLParser parser);

	// 20
	/**
	 * @Signature: parserDidStartDocument:
	 * @Declaration : - (void)parserDidStartDocument:(NSXMLParser *)parser
	 * @Description : Sent by the parser object to the delegate when it begins parsing a document.
	 **/

	public void parserDidStartDocument(NSXMLParser parser);

}