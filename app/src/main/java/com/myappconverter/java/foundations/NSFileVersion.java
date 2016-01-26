
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class NSFileVersion extends NSObject {

	public static final int NSFileVersionReplacingByMoving = 1 << 0;

	public static final int NSFileVersionAddingByMoving = 1 << 0;

	// Properties
	private NSURL url;
	private NSString localizedName;
	private NSString localizedNameOfSavingComputer;
	private NSDate modificationDate;
	private NSCoding persistentIdentifier;
	private boolean isConflict;
	private boolean isResolved;

	public static final Map<NSURL, LinkedHashSet<MFileEntry>> fileEntries = new HashMap<NSURL, LinkedHashSet<MFileEntry>>();
	int version_id;

	// Getting the Version of a File
	/**
	 * @Signature: currentVersionOfItemAtURL:
	 * @Declaration : + (NSFileVersion *)currentVersionOfItemAtURL:(NSURL *)url
	 * @Description : Returns the most recent version object for the file at the specified URL.
	 * @param url The URL of the file whose version object you want.
	 * @return Return Value The version object representing the current version of the file or nil if there is no such file.
	 **/
	
	public static NSFileVersion currentVersionOfItemAtURL(NSURL url) {
		NSFileVersion nsFileVersion = new NSFileVersion();
		if (fileEntries.containsKey(url)) {
			return nsFileVersion;
		}
		return null;
	}

	/**
	 * @Signature: otherVersionsOfItemAtURL:
	 * @Declaration : + (NSArray *)otherVersionsOfItemAtURL:(NSURL *)url
	 * @Description : Returns all versions of the specified file except the current version.
	 * @param url The URL of the file whose versions you want.
	 * @return Return Value An array of file version objects or nil if there is no such file. The array does not contain the version object
	 *         returned by the currentVersionOfItemAtURL: method.
	 * @Discussion For locally based files, this property typically contains versions of the file that you saved explicitly or that were
	 *             saved at appropriate times while the file was being edited. For documents residing in the cloud, this property typically
	 *             returns zero or more file versions representing conflicting versions of a file that need to be resolved with the current
	 *             version.
	 **/
	
	public static NSArray otherVersionsOfItemAtURL(NSURL url) {
		if (fileEntries.containsKey(url)) {
			NSArray nsArray = new NSArray();
			nsArray.wrappedList.add(fileEntries.get(url));
			return nsArray;
		}
		return null;
	}

	/**
	 * @Signature: versionOfItemAtURL:forPersistentIdentifier:
	 * @Declaration : + (NSFileVersion *)versionOfItemAtURL:(NSURL *)url forPersistentIdentifier:(id)persistentIdentifier
	 * @Description : Returns the version of the file that has the specified persistent ID.
	 * @param url The URL of the file whose version you want.
	 * @param persistentIdentifier The persistent ID of the NSFileVersion object you want.
	 * @return Return Value The file version object with the specified ID or nil if no such version object exists.
	 **/
	
	public static NSFileVersion versionOfItemAtURLForPersistentIdentifier(NSURL url, NSObject persistentIdentifier) {
		if (fileEntries.containsKey(url)) {
			Set<MFileEntry> mFileEntry = fileEntries.get(url);
			for (MFileEntry fileEntry : mFileEntry) {
				// if(fileEntry.)
			}
		}
		return null;
	}

	// Accessing the Version Information
	/**
	 * @Declaration :  NSURL *URL
	 * @Description : The URL identifying the location of the file associated with the file version object. (read-only)
	 * @Discussion The URL identifies the location of the file associated with this version. If this version of the file has been deleted,
	 *             the value in this property is nil. Do not display any part of this URL to the user. The location of file versions is
	 *             managed by the system and should not be exposed to the user. If you want to present the name of a file version, use the
	 *             localizedName property.
	 **/
	
	public NSURL URL() {
		return url;
	}

	/**
	 * @Declaration :  NSString *localizedName
	 * @Description : The string containing the user-presentable name of the file version. (read-only)
	 * @Discussion When displaying different versions of a file to the user, you should present this string to the user instead of the
	 *             version’s URL.
	 */
	
	public NSString localizedName() {
		return localizedName;
	}

	/**
	 * @Declaration : @property (readonly) NSString *localizedNameOfSavingComputer
	 * @Description : The user-presentable name of the computer on which the revision was saved. (read-only)
	 * @Discussion If the current revision has been deleted from disk, or if no computer name was recorded, the value in this property is
	 *             nil. The computer name is guaranteed to be recorded only when the current version is in conflict with another version.
	 *             The version object does not track changes to the computer name itself. Thus, if the computer name changed, the value in
	 *             this string might be an old value.
	 */
	
	public NSString localizedNameOfSavingComputer() {
		return localizedNameOfSavingComputer;
	}

	/**
	 * @Declaration :  NSDate *modificationDate
	 * @Description : The modification date of the version. (read-only)
	 * @Discussion If the version has been deleted, this value is nil.
	 */
	
	public NSDate modificationDate() {
		return modificationDate;
	}

	/**
	 * @Declaration :  id<NSCoding> persistentIdentifier
	 * @Description : The identifier for this version of the file. (read-only)
	 * @Discussion You can save the value of this property persistently and use it to recreate the version object later. When recreating the
	 *             version object using the versionOfItemAtURL:forPersistentIdentifier: method, the version object returned is equivalent to
	 *             the current object.
	 */
	
	public NSCoding persistentIdentifier() {
		return persistentIdentifier;
	}

	// Handling Version Conflicts
	/**
	 * @Declaration : @property (readonly, getter=isConflict) BOOL conflict
	 * @Description : A Boolean value indicating whether the contents of the version are in conflict with the contents of another version.
	 *              (read-only)
	 * @Discussion When two or more versions of a file are written at the same time, perhaps because the file is saved in the cloud and one
	 *             or more of the writers were offline when they were writing, the system attempts to resolve the conflict automatically. It
	 *             does this by picking one of the file versions to be the current file and setting this property to YES for the other file
	 *             versions that are in conflict.
	 */
	
	public boolean isConflict() {
		return isConflict;
	}

	/**
	 * @Declaration : @property (getter=isResolved) BOOL resolved
	 * @Description : A Boolean value that indicates the version object is not in conflict (YES) or is in conflict (NO).
	 * @Discussion When the system detects a conflict involving versions of a file, it sets this property to NO to indicate an unresolved
	 *             conflict. After you resolve the conflict, set this property to YES to tell the system it is resolved; you must then
	 *             remove any versions of the file that are no longer useful. Important: If you do not explicitly remove versions of a file
	 *             that are no longer useful, iCloud continues to sync them to all a user’s devices and those versions continue to consume
	 *             user iCloud quota. To remove an unused version of a file, call the removeAndReturnError: method. To remove all unused
	 *             versions of a file, call the removeOtherVersionsOfItemAtURL:error: method. Important: Never set the value of this
	 *             property to NO. If you do, the system raises an exception. Resolving a conflict causes the file version object to be
	 *             removed from any reports about conflicting versions, such as those returned by the unresolvedConflictVersionsOfItemAtURL:
	 *             method.
	 */
	
	public boolean isResolved() {
		return isResolved;
	}

	/**
	 * @Signature: unresolvedConflictVersionsOfItemAtURL:
	 * @Declaration : + (NSArray *)unresolvedConflictVersionsOfItemAtURL:(NSURL *)url
	 * @Description : Returns an array of version objects that are currently in conflict for the specified URL.
	 * @param url The URL of the file that has associated version objects.
	 * @return Return Value An array of NSFileVersion objects that represent the versions in conflict or nil if the file at URL does not
	 *         exist.
	 **/
	
	public static NSArray<NSFileVersion> unresolvedConflictVersionsOfItemAtURL(NSURL url) {
		return null;
	}

	// Replacing and Deleting Versions
	/**
	 * @Signature: replaceItemAtURL:options:error:
	 * @Declaration : - (NSURL *)replaceItemAtURL:(NSURL *)url options:(NSFileVersionReplacingOptions)options error:(NSError **)error
	 * @Description : Replace the contents of the specified file with the contents of the current version’s file.
	 * @param url The file whose contents you want to replace. If the file at this URL does not exist, a new file is created at the
	 *            location.
	 * @param options Specify 0 to overwrite the file in place; otherwise, specify one of the constants described in
	 *            “NSFileVersionReplacingOptions.
	 * @param error On input, a pointer to an error object. If an error occurs, this pointer is set to an NSError object with information
	 *            about the error.
	 * @return Return Value The URL of the file that was written, which may be different than the one specified in the url parameter.
	 * @Discussion When replacing the contents of the file, this method does not normally replace the display name associated with the file.
	 *             The only exception is when the file at url is of a different type than the file associated with this version object. In
	 *             such a case, the file name remains the same but its filename extension changes to match the type of the new contents. (Of
	 *             course, if filename extension hiding is enabled, this change is not noticeable to users.)
	 **/
	
	public NSURL replaceItemAtURLOptionsError(NSURL url, int options, NSError[] error) {
		return null;
	}

	/**
	 * @Signature: removeAndReturnError:
	 * @Declaration : - (BOOL)removeAndReturnError:(NSError **)outError
	 * @Description : Remove this version object and its associated file from the version store.
	 * @param outError On input, a pointer to an error object. If an error occurs, this pointer is set to an NSError object with information
	 *            about the error.
	 * @return Return Value YES if this version was removed successfully or NO if it was not.
	 * @Discussion This method removes this version object and its file from the version store, freeing up the associated storage space. You
	 *             must not call this method for the current file version—that is, the version object returned by the
	 *             currentVersionOfItemAtURL: method. You should always remove file versions as part of a coordinated write operation to a
	 *             file. In other words, always call this method from a block passed to a file coordinator object to initiate a write
	 *             operation. Doing so ensures that no other processes are operating on the file while you remove the version information.
	 *             If successful, subsequent requests for the versions of the file do not include this version object (or any object with
	 *             the same information). You can use this method to free up disk space by removing versions that are no longer needed.
	 **/
	
	public boolean removeAndReturnError(NSError[] outError) {
		return false;
	}

	/**
	 * @Signature: removeOtherVersionsOfItemAtURL:error:
	 * @Declaration : + (BOOL)removeOtherVersionsOfItemAtURL:(NSURL *)inFileURL error:(NSError **)outError
	 * @Description : Removes all versions of a file, except the current one, from the version store.
	 * @param inFileURL The file whose older versions you want to delete. If the file at this URL does not exist, a new file is created at
	 *            the location.
	 * @param outError On input, a pointer to an error object. If an error occurs, this pointer is set to an NSError object with information
	 *            about the error.
	 * @return Return Value YES if the older file versions were removed successfully or NO if an error occurred.
	 * @Discussion This method removes all versions except the current one from the version store, freeing up the associated storage space.
	 *             You should always remove file versions as part of a coordinated write operation to a file. In other words, always call
	 *             this method from a block passed to a file coordinator object to initiate a write operation. Doing so ensures that no
	 *             other processes are operating on the file while you remove the version information. If successful, subsequent requests
	 *             for the versions of the file reflect that only the current version is available. You can use this method to free up disk
	 *             space by removing versions that are no longer needed.
	 **/
	
	public static void removeOtherVersionsOfItemAtURLError(NSURL inFileURL, NSError[] outError) {
		// TODO
	}

	class MFileEntry extends File {

		public MFileEntry(String path) {
			super(path);
		}

		private static final long serialVersionUID = 1L;

	}

}