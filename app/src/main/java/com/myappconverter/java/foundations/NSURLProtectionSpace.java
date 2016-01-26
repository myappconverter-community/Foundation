
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.security.SecTrustRef;

/**
 * An NSURLProtectionSpace object represents a server or an area on a server, commonly referred to as a realm, that requires authentication.
 * The protection space defines a series of matching constraints that determine which credential should be provided. For example, if a
 * request provides your delegate with an NSURLAuthenticationChallenge object that requests a client username and password, your app should
 * provide the correct username and password for the particular host, port, protocol, and realm, as specified in the challengeâ€™s protection
 * space. Note: This class has no designated initializer; its init method always returns nil. You must initialize this class by calling one
 * of the initialization methods described in â€œCreating a protection space.â€?
 */


public class NSURLProtectionSpace extends NSObject implements NSCopying, NSSecureCoding {

	NSString host = null;
	int port;
	NSString proxyType = null;
	NSString protocol = null;
	NSString realm = null;
	NSString authenticationMethod = null;

	// Creating a Protection Space

	public NSURLProtectionSpace() {
		super();
	}

	/**
	 * @Signature: initWithHost:port:protocol:realm:authenticationMethod:
	 * @Declaration : - (id)initWithHost:(NSString *)host port:(NSInteger)port protocol:(NSString *)protocol realm:(NSString *)realm
	 *              authenticationMethod:(NSString *)authenticationMethod
	 * @Description : Initializes a protection space object.
	 * @param host The host name for the protection space object.
	 * @param port The port for the protection space object. If port is 0 the default port for the specified protocol is used, for example,
	 *            port 80 for HTTP. Note that servers can, and do, treat these values differently.
	 * @param protocol The protocol for the protection space object. The value of protocol is equivalent to the scheme for a URL in the
	 *            protection space, for example, â€œhttpâ€?, â€œhttpsâ€?, â€œftpâ€?, etc.
	 * @param realm A string indicating a protocol specific subdivision of the host. realm may be nil if there is no specified realm or if
	 *            the protocol doesnâ€™t support realms.
	 * @param authenticationMethod The type of authentication to use. authenticationMethod should be set to one of the values in
	 *            â€œNSURLProtectionSpace Authentication Methodsâ€? or nil to use the default, NSURLAuthenticationMethodDefault.
	 **/

	public Object initWithHostPortLProtocolRealmAuthenticationMethod(NSString host, int port, NSString protocol, NSString realm,
																	 NSString authenticationMethod) {
		this.host = host;
		this.port = port;
		this.protocol = protocol;
		this.realm = realm;
		this.authenticationMethod = authenticationMethod;
		return this;
	}

	/**
	 * @Signature: initWithProxyHost:port:type:realm:authenticationMethod:
	 * @Declaration : - (id)initWithProxyHost:(NSString *)host port:(NSInteger)port type:(NSString *)proxyType realm:(NSString *)realm
	 *              authenticationMethod:(NSString *)authenticationMethod
	 * @Description : Initializes a protection space object representing a proxy server.
	 * @param host The host of the proxy server for the protection space object.
	 * @param port The port for the protection space object. If port is 0 the default port for the specified proxy type is used, for
	 *            example, port 80 for HTTP. Note that servers can, and do, treat these values differently.
	 * @param proxyType The type of proxy server. The value of proxyType should be set to one of the values specified in
	 *            â€œNSURLProtectionSpace Proxy Types.â€?
	 * @param realm A string indicating a protocol specific subdivision of the host. realm may be nil if there is no specified realm or if
	 *            the protocol doesnâ€™t support realms.
	 * @param authenticationMethod The type of authentication to use. authenticationMethod should be set to one of the values in
	 *            â€œNSURLProtectionSpace Authentication Methodsâ€? or nil to use the default, NSURLAuthenticationMethodDefault.
	 **/

	public Object initWithProxyHostPortTypeRealmAuthenticationMethod(NSString host, int port, NSString proxyType, NSString realm,
																	 NSString authenticationMethod) {
		this.host = host;
		this.proxyType = proxyType;
		this.port = port;
		this.realm = realm;
		this.authenticationMethod = authenticationMethod;
		return this;
	}

	// Getting Protection Space Properties

	/**
	 * - (NSString *)authenticationMethod
	 *
	 * @Description : Returns the authentication method used by the receiver.
	 * @return Return Value The authentication method used by the receiver. The supported authentication methods are listed in
	 *         â€œNSURLProtectionSpace Authentication Methods.â€?
	 */

	public NSString authenticationMethod() {
		return this.authenticationMethod;
	}

	/**
	 * - (NSArray *)distinguishedNames
	 *
	 * @Description : Returns an array of acceptable certificate-issuing authorities for client certificate authentication.
	 * @return Return Value An array of acceptable certificate-issuing authorities, or nil if the authentication method of the protection
	 *         space is not client certificate.
	 * @Discussion The returned issuing authorities are encoded with Distinguished Encoding Rules (DER).
	 */

	public NSArray distinguishedNames() {
		return null;
		// TODO
	}

	/**
	 * - (NSString *)host
	 *
	 * @Description : Returns the receiverâ€™s host.
	 * @return Return Value The receiver's host.
	 */

	public NSString host() {
		return this.host;
	}

	/**
	 * @Signature: isProxy
	 * @Declaration : - (BOOL)isProxy
	 * @Description : Returns whether the receiver represents a proxy server.
	 * @return Return Value YES if the receiver represents a proxy server, NO otherwise.
	 **/
	@Override

	public boolean isProxy() {
		if (this.proxyType != null) {
			return true;
		}
		return false;
	}

	/**
	 * @Signature: port
	 * @Declaration : - (NSInteger)port
	 * @Description : Returns the receiverâ€™s port.
	 * @return Return Value The receiver's port.
	 **/

	public int port() {
		return this.port;
	}

	/**
	 * - (NSString *)protocol
	 *
	 * @Description : Returns the receiverâ€™s protocol.
	 * @return Return Value The receiver's protocol, or nil if the receiver represents a proxy protection space.
	 */

	public NSString protocol() {
		return this.protocol;
	}

	/**
	 * - (NSString *)proxyType
	 *
	 * @Description : Returns the receiver's proxy type.
	 * @return Return Value The receiver's proxy type, or nil if the receiver does not represent a proxy protection space. The supported
	 *         proxy types are listed in â€œNSURLProtectionSpace Proxy Types.â€?
	 */

	public NSString proxyType() {
		return this.proxyType;
	}

	/**
	 * - (NSString *)realm
	 *
	 * @Description : Returns the receiverâ€™s authentication realm
	 * @return Return Value The receiverâ€™s authentication realm, or nil if no realm has been set.
	 * @Discussion A realm is generally only specified for HTTP and HTTPS authentication.
	 */

	public NSString realm() {
		return this.realm;
	}

	/**
	 * @Signature: receivesCredentialSecurely
	 * @Declaration : - (BOOL)receivesCredentialSecurely
	 * @Description : Returns whether the credentials for the protection space can be sent securely.
	 * @return Return Value YES if the credentials for the protection space represented by the receiver can be sent securely, NO otherwise.
	 **/

	public boolean receivesCredentialSecurely() {
		return false;
		// TODO
	}

	/**
	 * - (SecTrustRef)serverTrust
	 *
	 * @Description : Returns a representation of the serverâ€™s SSL transaction state.
	 * @return Return Value The serverâ€™s SSL transaction state, or nil if the authentication method of the protection space is not server
	 *         trust.
	 */

	public SecTrustRef serverTrust() {
		if ("NSURLAuthenticationMethodServerTrust".equalsIgnoreCase(authenticationMethod.getWrappedString())) {

		}
		return null;
		// TODO wait NSURLCredential
	}

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