package com.myappconverter.java.foundations;

public class NSURLCredentialStorage extends NSObject {

	/**
	 * NSURLCredentialStorage implements a singleton (shared object) that manages the credential storage.
	 */

	private static NSURLCredentialStorage instance = null;
	private NSMutableDictionary<NSURLProtectionSpace, NSMutableDictionary<NSString, NSURLCredential>> allCredentials;
	private NSMutableDictionary<NSURLProtectionSpace, NSURLCredential> defaultCredentials;

	// Map<NSURLProtectionSpace, Map<NSString, NSURLCredential>> allCredentials;

	protected NSURLCredentialStorage() {
		defaultCredentials = new NSMutableDictionary<NSURLProtectionSpace, NSURLCredential>();
		allCredentials = new NSMutableDictionary<NSURLProtectionSpace, NSMutableDictionary<NSString, NSURLCredential>>();
	}

	public static NSURLCredentialStorage getInstance() {
		if (instance == null) {
			instance = new NSURLCredentialStorage();
		}
		return instance;
	}

	// Getting the Credential Storage

	/**
	 * + (NSURLCredentialStorage *)sharedCredentialStorage
	 *
	 * @Description : Returns the shared URL credential storage object.
	 * @return Return Value The shared NSURLCredentialStorage object.
	 */

	public static NSURLCredentialStorage sharedCredentialStorage() {
		return new NSURLCredentialStorage();
	}

	// Getting and Setting Default Credentials

	/**
	 * - (NSURLCredential *)defaultCredentialForProtectionSpace:(NSURLProtectionSpace *)protectionSpace
	 *
	 * @Description : Returns the default credential for the specified protectionSpace.
	 * @param protectionSpace The URL protection space of interest.
	 * @return Return Value The default credential for protectionSpace or nil if no default has been set.
	 */

	public NSURLCredential defaultCredentialForProtectionSpace(NSURLProtectionSpace protectionSpace) {
		return (NSURLCredential) defaultCredentials.objectForKey(protectionSpace);
	}

	/**
	 * @Signature: setCredential:forProtectionSpace:
	 * @Declaration : - (void)setCredential:(NSURLCredential *)credential forProtectionSpace:(NSURLProtectionSpace *)protectionSpace
	 * @Description : Adds credential to the credential storage for the specified protectionSpace.
	 * @param credential The credential to add. If a credential with the same user name already exists in protectionSpace, then credential
	 *            replaces the existing object.
	 * @param protectionSpace The protection space to which to add the credential.
	 * @Discussion If the credential is not yet in the set for the protection space, it will be added to it.
	 **/

	public void setDefaultCredentialForProtectionSpace(NSURLCredential credential, NSURLProtectionSpace protectionSpace) {
		defaultCredentials.setObjectForKey(credential, protectionSpace);
	}

	// Adding and Removing Credentials

	/**
	 * @Signature: removeCredential:forProtectionSpace:
	 * @Declaration : - (void)removeCredential:(NSURLCredential *)credential forProtectionSpace:(NSURLProtectionSpace *)protectionSpace
	 * @Description : Removes a specified credential from the credential storage for the specified protection space.
	 * @param credential The credential to remove.
	 * @param protectionSpace The protection space from which to remove the credential.
	 **/

	public void removeCredentialForProtectionSpace(NSURLCredential credential, NSURLProtectionSpace protectionSpace) {
		NSMutableDictionary<NSString, NSURLCredential> dico = (NSMutableDictionary) allCredentials.objectForKey(protectionSpace);
		dico.removeObjectForKey(credential.user());
	}

	/**
	 * @Signature: removeCredential:forProtectionSpace:options:
	 * @Declaration : - (void)removeCredential:(NSURLCredential *)credential forProtectionSpace:(NSURLProtectionSpace *)space
	 *              options:(NSDictionary *)options
	 * @Description : Removes a specified credential from the credential storage for the specified protection space using the given options.
	 * @param credential The credential to remove.
	 * @param protectionSpace The protection space from which to remove the credential.
	 * @param options A dictionary containing options to consider when removing the credential. For possible keys, see â€œDictionary Key for
	 *            Credential Removal Options.â€? You should use this when trying to delete a credential that has the
	 *            NSURLCredentialPersistenceSynchronizable policy. Note:Â When NSURLCredential objects that have a
	 *            NSURLCredentialPersistenceSynchronizable policy are removed, the credential will be removed on all devices that contain
	 *            this credential.
	 * @Discussion The credential is removed from both persistent and temporary storage.
	 **/

	public void removeCredentialForProtectionSpaceOptions(NSURLCredential credential, NSURLProtectionSpace space, NSDictionary options) {
		removeCredentialForProtectionSpace(credential, space);
		// FIXME should test option " NSURLCredentialPersistenceSynchronizable" ios 7 and later
	}

	/**
	 * @Signature: setCredential:forProtectionSpace:
	 * @Declaration : - (void)setCredential:(NSURLCredential *)credential forProtectionSpace:(NSURLProtectionSpace *)protectionSpace
	 * @Description : Adds credential to the credential storage for the specified protectionSpace.
	 * @param credential The credential to add. If a credential with the same user name already exists in protectionSpace, then credential
	 *            replaces the existing object.
	 * @param protectionSpace The protection space to which to add the credential.
	 * @Discussion If the credential is not yet in the set for the protection space, it will be added to it.
	 **/

	public void setCredentialForProtectionSpac(NSURLCredential credential, NSURLProtectionSpace protectionSpace) {
		NSObject object = (NSObject) allCredentials.objectForKey(protectionSpace);
		if (object != null) {
			NSMutableDictionary<NSString, NSURLCredential> credProSapce = (NSMutableDictionary<NSString, NSURLCredential>) object;
			allCredentials.setObjectForKey(credProSapce, protectionSpace);
			credProSapce.setObjectForKey(credential, credential.user());
		} else {
			NSMutableDictionary<NSString, NSURLCredential> credProSapce = new NSMutableDictionary<NSString, NSURLCredential>();
			credProSapce.setObjectForKey(credential, credential.user());
			allCredentials.setObjectForKey(credProSapce, protectionSpace);
		}
	}

	// Retrieving Credentials

	/**
	 * - (NSDictionary *)allCredentials
	 *
	 * @Description : Returns a dictionary containing the credentials for all available protection spaces.
	 * @return Return Value A dictionary containing the credentials for all available protection spaces. The dictionary has keys
	 *         corresponding to the NSURLProtectionSpace objects. The values for the NSURLProtectionSpace keys consist of dictionaries where
	 *         the keys are user name strings, and the value is the corresponding NSURLCredential object.
	 */

	public NSDictionary allCredentials() {
		return allCredentials;
	}

	/**
	 * - (NSDictionary *)credentialsForProtectionSpace:(NSURLProtectionSpace *)protectionSpace
	 *
	 * @Description : Returns a dictionary containing the credentials for the specified protection space.
	 * @param protectionSpace The protection space whose credentials you want to retrieve.
	 * @return Return Value A dictionary containing the credentials for protectionSpace. The dictionaryâ€™s keys are user name strings, and
	 *         the value is the corresponding NSURLCredential.
	 */

	public NSDictionary credentialsForProtectionSpace(NSURLProtectionSpace protectionSpace) {
		return (NSDictionary) allCredentials.objectForKey(protectionSpace);
	}

}