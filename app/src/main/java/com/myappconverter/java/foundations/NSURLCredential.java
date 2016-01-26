
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.security.SecIdentity;
import com.myappconverter.java.security.SecTrustRef;



public class NSURLCredential extends NSObject implements NSCopying, NSSecureCoding {

	// Enumeration

	public enum NSURLCredentialPersistence {
		NSURLCredentialPersistenceNone, //
		NSURLCredentialPersistenceForSession, //
		NSURLCredentialPersistencePermanent, //
		NSURLCredentialPersistenceSynchronizable;//

		private int code;
		private NSURLCredentialPersistence label;

		NSURLCredentialPersistence() {
		}

		NSURLCredentialPersistence(int code, NSURLCredentialPersistence label) {
			this.code = code;
			this.label = label;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public NSURLCredentialPersistence getLabel() {
			return label;
		}

		public void setLabel(NSURLCredentialPersistence label) {
			this.label = label;
		}
	}

	private SecTrustRef trust;
	private NSArray certificates;
	private SecIdentity identity;
	private NSString password;
	private NSURLCredentialPersistence persistence;
	private NSString user;

	/**
	 * Overview NSURLCredential is an immutable object representing an authentication credential consisting of authentication information
	 * specific to the type of credential and the type of persistent storage to use, if any. The URL loading system supports three types of
	 * credentials: password-based user credentials, certificate-based user credentials, and certificate-based server credentials (used when
	 * verifying the server’s identity). When you create a credential, you can specify that it should be used for a single request,
	 * persisted temporarily (until your app quits), or persisted permanently (in the keychain).
	 */

	// Creating a Credential

	/**
	 * + (NSURLCredential *)credentialForTrust:(SecTrustRef)trust
	 *
	 * @Description : Creates and returns an NSURLCredential object for server trust authentication with a given accepted trust.
	 * @param trust The accepted trust.
	 * @Discussion Before creating a server trust credential, it is the responsibility of the delegate of an NSURLConnection object or an
	 *             NSURLDownload object to evaluate the trust. Do this by calling SecTrustEvaluate, passing it the trust obtained from the
	 *             serverTrust method of the server’s NSURLProtectionSpace object. If the trust is invalid, the authentication challenge
	 *             should be cancelled with cancelAuthenticationChallenge:.
	 */

	public static NSURLCredential credentialForTrust(SecTrustRef trust) {
		NSURLCredential urlCri = new NSURLCredential();
		urlCri.trust = trust;
		return urlCri;
	}

	/**
	 * @Signature: credentialWithUser:password:persistence:
	 * @Declaration : + (NSURLCredential *)credentialWithUser:(NSString *)user password:(NSString *)password
	 *              persistence:(NSURLCredentialPersistence)persistence
	 * @Description : Creates and returns an NSURLCredential object for internet password authentication with a given user name and password
	 *              using a given persistence setting.
	 * @param user The user for the credential.
	 * @param password The password for user.
	 * @param persistence The persistence setting for the credential.
	 * @return Return Value An NSURLCredential object with user name user, password password, and using persistence setting persistence.
	 * @Discussion If persistence is NSURLCredentialPersistencePermanent the credential is stored in the keychain.
	 **/

	public static NSURLCredential credentialWithUserPasswordPersistence(NSString user, NSString password,
																		NSURLCredentialPersistence persistence) {
		NSURLCredential urlCri = new NSURLCredential();
		urlCri.user = user;
		urlCri.password = password;
		urlCri.persistence = persistence;
		return urlCri;
	}

	/**
	 * @Signature: credentialWithIdentity:certificates:persistence:
	 * @Declaration : + (NSURLCredential *)credentialWithIdentity:(SecIdentityRef)identity certificates:(NSArray *)certArray
	 *              persistence:(NSURLCredentialPersistence)persistence
	 * @Description : Creates and returns an NSURLCredential object for client certificate authentication with a given identity and a given
	 *              array of client certificates using a given persistence setting.
	 * @param identity The identity for the credential.
	 * @param certArray An array of one or more SecCertificateRef objects representing certificates for the credential.
	 * @param persistence The persistence setting for the credential.
	 **/

	public static NSURLCredential credentialWithIdentityCertificatesPersistence(SecIdentity identity, NSArray certArray,
																				NSURLCredentialPersistence persistence) {
		NSURLCredential urlCri = new NSURLCredential();
		urlCri.identity = identity;
		urlCri.certificates = certArray;
		urlCri.persistence = persistence;
		return urlCri;
	}

	/**
	 * @Signature: initWithIdentity:certificates:persistence:
	 * @Declaration : - (id)initWithIdentity:(SecIdentityRef)identity certificates:(NSArray *)certArray
	 *              persistence:(NSURLCredentialPersistence)persistence
	 * @Description : Returns an NSURLCredential object for client certificate authentication initialized with a given identity and a given
	 *              array of client certificates using a given persistence setting.
	 * @param identity The identity for the credential.
	 * @param certArray An array of one or more SecCertificateRef objects representing certificates for the credential.
	 * @param persistence The persistence setting for the credential.
	 **/

	public Object initWithIdentityCertificatesPersistence(SecIdentity identity, NSArray certArray, NSURLCredentialPersistence persistence) {
		this.identity = identity;
		this.certificates = certArray;
		this.persistence = persistence;
		return this;
	}

	/**
	 * @Signature: initWithTrust:
	 * @Declaration : - (id)initWithTrust:(SecTrustRef)trust
	 * @Description : Returns an NSURLCredential object for server trust authentication initialized with a given accepted trust.
	 * @param trust The accepted trust.
	 * @Discussion Before creating a server trust credential, it is the responsibility of the delegate of an NSURLConnection object or an
	 *             NSURLDownload object to evaluate the trust. Do this by calling SecTrustEvaluate, passing it the trust obtained from the
	 *             serverTrust method of the server’s NSURLProtectionSpace object. If the trust is invalid, the authentication challenge
	 *             should be cancelled with cancelAuthenticationChallenge:.
	 **/

	public Object initWithTrust(SecTrustRef trust) {
		this.trust = trust;
		return this;
	}

	/**
	 * @Signature: initWithUser:password:persistence:
	 * @Declaration : - (id)initWithUser:(NSString *)user password:(NSString *)password persistence:(NSURLCredentialPersistence)persistence
	 * @Description : Returns an NSURLCredential object initialized with a given user name and password using a given persistence setting.
	 * @param user The user for the credential.
	 * @param password The password for user.
	 * @param persistence The persistence setting for the credential.
	 * @return Return Value An NSURLCredential object initialized with user name user, password password, and using persistence setting
	 *         persistence.
	 * @Discussion If persistence is NSURLCredentialPersistencePermanent the credential is stored in the keychain.
	 **/

	public Object initWithUserPasswordPersistence(NSString user, NSString password, NSURLCredentialPersistence persistence) {
		this.user = user;
		this.password = password;
		this.persistence = persistence;
		return this;
	}

	// Getting Credential Properties

	/**
	 * @Signature: certificates
	 * @Declaration : - (NSArray *)certificates
	 * @Description : Returns an array of SecCertificateRef objects representing the certificates of the credential if it is a client
	 *              certificate credential.
	 * @return Return Value The certificates of the credential, or nil if this is not a client certificate credential.
	 **/

	public NSArray certificates() {
		return this.certificates;
	}

	/**
	 * @Signature: hasPassword
	 * @Declaration : - (BOOL)hasPassword
	 * @Description : Returns a Boolean value that indicates whether the receiver has a password.
	 * @return Return Value YES if the receiver has a password, NO otherwise.
	 * @Discussion This method does not attempt to retrieve the password. If this credential's password is stored in the user’s keychain,
	 *             password may return nil even if this method returns YES, since getting the password may fail, or the user may refuse
	 *             access.
	 **/

	public boolean hasPassword() {
		if (this.password != null) {
			return true;
		}
		return false;
	}

	/**
	 * - (SecIdentityRef)identity
	 *
	 * @Description : Returns the identity of this credential if it is a client certificate credential.
	 * @return Return Value The identity of the credential, or NULL if this is not a client certificate credential.
	 */

	public SecIdentity identity() {
		return this.identity;
	}

	/**
	 * - (NSString *)password
	 *
	 * @Description : Returns the receiver’s password.
	 * @return Return Value The receiver’s password.
	 * @Discussion If the password is stored in the user’s keychain, this method may result in prompting the user for access.
	 */

	public NSString password() {
		return this.password;
	}

	/**
	 * - (NSURLCredentialPersistence)persistence
	 *
	 * @Description : Returns the receiver’s persistence setting.
	 * @return Return Value The receiver’s persistence setting.
	 */

	public NSURLCredentialPersistence persistence() {
		return this.persistence;
	}

	/**
	 * - (NSString *)user
	 *
	 * @Description : Returns the receiver’s user name.
	 * @return Return Value The receiver’s user name.
	 */

	public NSString user() {
		return this.user;
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