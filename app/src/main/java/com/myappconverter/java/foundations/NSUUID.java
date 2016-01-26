
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.util.UUID;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;



public class NSUUID extends NSObject implements NSCopying, NSSecureCoding {

	private UUID wrappeUuid;

	public UUID getWrappeUuid() {
		return wrappeUuid;
	}

	public void setWrappeUuid(UUID wrappeUuid) {
		this.wrappeUuid = wrappeUuid;
	}

	/**
	 * @Signature: UUID
	 * @Declaration : + (id)UUID
	 * @Description : Create and returns a new UUID with RFC 4122 version 4 random bytes.
	 * @return Return Value A new UUID object.
	 **/

	public static Object UUID() {
		return new NSUUID();
	}

	public NSUUID() {
		super();
		this.wrappeUuid = new java.util.UUID(0, 0);
	}

	/**
	 * @Signature: getUUIDBytes:
	 * @Declaration : - (void)getUUIDBytes:(uuid_t)uuid
	 * @Description : Returns the UUIDs bytes.
	 * @param uuid The value of uuid represented as raw bytes.
	 **/

	public Object getUUIDBytes(NSUUID uuid) {
		return uuid.wrappeUuid.toString().getBytes();
	}

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Create and returns a new UUID with RFC 4122 version 4 random bytes.
	 * @return Return Value A new UUID object.
	 **/
	@Override

	public NSObject init() {
		this.wrappeUuid = UUID.randomUUID();
		return this;

	}

	/**
	 * @Signature: initWithUUIDBytes:
	 * @Declaration : - (id)initWithUUIDBytes:(const uuid_t)bytes
	 * @Description : Creates and returns a new UUID with the given bytes.
	 * @param bytes Raw UUID bytes to use to create the UUID.
	 * @return Return Value A new UUID object.
	 **/

	public Object initWithUUIDBytes(NSUUID bytes) {
		this.wrappeUuid = UUID.nameUUIDFromBytes(bytes.wrappeUuid.toString().getBytes());
		return this;

	}

	/**
	 * @Signature: initWithUUIDString:
	 * @Declaration : - (id)initWithUUIDString:(NSString *)string
	 * @Description : Creates and returns a new UUID from the formatted string.
	 * @param string The source string containing the UUID. The standard format for UUIDs represented in ASCII is a string punctuated by
	 *            hyphens, for example 68753A44-4D6F-1226-9C60-0050E4C00067.
	 * @return Return Value A new UUID object. Returns nil for invalid strings.
	 **/

	public NSObject initWithUUIDString(NSString string) {
		this.wrappeUuid = UUID.nameUUIDFromBytes(string.wrappedString.getBytes());
		return this;
	}

	/**
	 * UUIDString
	 *
	 * @Returns the UUID as a string. - (NSString *)UUIDString
	 * @Return Value A string containing a formatted UUID for example E621E1F8-C36C-495A-93FC-0C247A3E6E5F.
	 * @Discussion Use this method to create a string representation of the NSUUID object to compare with a CFUUIDRef.
	 */

	public NSString UUIDString() {
		return new NSString(wrappeUuid.toString());
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	private byte[] bytes;
	private String name;

	public NSUUID(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getName() {
		if (name == null || "".equals(name))
			return "0";
		return name;
	}

	/**
	 * There is no XML representation specified for UIDs. In this implementation UIDs are represented as strings in the XML output.
	 *
	 * @param xml The xml StringBuilder
	 * @param level The indentation level
	 */
	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<string>");
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			if (b < 16)
				xml.append("0");
			xml.append(Integer.toHexString(b));
		}
		xml.append("</string>");
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		out.write(0x80 + bytes.length - 1);
		out.write(bytes);
	}

	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append("\"");
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			if (b < 16)
				ascii.append("0");
			ascii.append(Integer.toHexString(b));
		}
		ascii.append("\"");
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		toASCII(ascii, level);
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}