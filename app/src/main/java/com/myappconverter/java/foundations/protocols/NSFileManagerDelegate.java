package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSFileManager;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.NSURL;

public interface NSFileManagerDelegate extends NSObject {

	// 1
	/**
	 * @Signature: fileManager:shouldCopyItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldCopyItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if the file manager should copy the specified item to the new path.
	 * @return Return Value YES if the item should be copied or NO if the file manager should stop copying items associated with the current
	 *         operation. If you do not implement this method, the file manager assumes a response of YES.
	 * @Discussion This method is called once for each item that needs to be copied. Thus, for a directory, this method is called once for
	 *             the directory and once for each item in the directory. This method performs the same task as the
	 *             fileManager:shouldCopyItemAtURL:toURL: method, which is preferred over this method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldCopyItemAtPathToPath(NSFileManager fileManager, NSString srcPath, NSString dstPath);

	// 2
	/**
	 * @Signature: fileManager:shouldCopyItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldCopyItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if the file manager should copy the specified item to the new URL.
	 * @return Return Value YES if the item should be copied or NO if the file manager should stop copying items associated with the current
	 *         operation. If you do not implement this method, the file manager assumes a response of YES.
	 * @Discussion This method is called once for each item that needs to be copied. Thus, for a directory, this method is called once for
	 *             the directory and once for each item in the directory. This method performs the same task as the
	 *             fileManager:shouldCopyItemAtPath:toPath: method and is preferred over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldCopyItemAtURLToURL(NSFileManager fileManager, NSURL srcURL, NSURL dstURL);

	// 3
	/**
	 * @Signature: fileManager:shouldLinkItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldLinkItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if a hard link should be created between the items at the two paths.
	 * @return Return Value YES if the operation should proceed, otherwise NO.
	 * @Discussion If the item specified by destURL is a directory, returning NO prevents links from being created to both the directory and
	 *             its children. This method performs the same task as the fileManager:shouldLinkItemAtURL:toURL: method, which is preferred
	 *             over this method in OS X v10.6 and later.
	 **/

	public void fileManagerShouldLinkItemAtPathToPath(NSFileManager fileManager, NSString srcPath, NSString dstPath);

	// 4
	/**
	 * @Signature: fileManager:shouldLinkItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldLinkItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if a hard link should be created between the items at the two URLs.
	 * @return Return Value YES if the link should be created or NO if it should not be created.
	 * @Discussion If the item specified by destURL is a directory, returning NO prevents links from being created to both the directory and
	 *             its children. This method performs the same task as the fileManager:shouldLinkItemAtPath:toPath: method and is preferred
	 *             over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldLinkItemAtURLToURL(NSFileManager fileManager, NSURL srcURL, NSURL dstURL);

	// 5
	/**
	 * @Signature: fileManager:shouldMoveItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldMoveItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if the file manager should move the specified item to the new path.
	 * @return Return Value YES if the operation should proceed, otherwise NO. If you do not implement this method, the file manager assumes
	 *         a response of YES.
	 * @Discussion This method is called only once for the item being moved, regardless of whether the item is a file, directory, or
	 *             symbolic link. This method performs the same task as the fileManager:shouldMoveItemAtURL:toURL: method, which is
	 *             preferred over this method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldMoveItemAtPathToPath(NSFileManager fileManager, NSString srcPath, NSString dstPath);

	// 6
	/**
	 * @Signature: fileManager:shouldMoveItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldMoveItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if the file manager should move the specified item to the new URL.
	 * @return Return Value YES if the item should be moved or NO if it should not be moved. If you do not implement this method, the file
	 *         manager assumes a response of YES.
	 * @Discussion This method is called only once for the item being moved, regardless of whether the item is a file, directory, or
	 *             symbolic link. This method performs the same task as the fileManager:shouldMoveItemAtPath:toPath: method and is preferred
	 *             over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldMoveItemAtURLToURL(NSFileManager fileManager, NSURL srcURL, NSURL dstURL);

	// 7
	/**
	 * @Signature: fileManager:shouldProceedAfterError:copyingItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error copyingItemAtPath:(NSString
	 *              *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if the move operation should continue after an error occurs while copying the item at the specified
	 *              path.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem copying the item to the specified location. If you return YES,
	 *             the file manager continues copying any other items and ignores the error. This method performs the same task as the
	 *             fileManager:shouldProceedAfterError:copyingItemAtURL:toURL: method, which is preferred over this method in OS X v10.6 and
	 *             later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorCopyingItemAtPathToPath(NSFileManager fileManager, NSError error, NSString srcPath,
																			 NSString dstPath);

	// 8
	/**
	 * @Signature: fileManager:shouldProceedAfterError:copyingItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error copyingItemAtURL:(NSURL
	 *              *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if the move operation should continue after an error occurs while copying the item at the specified
	 *              URL.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem copying the item to the specified location. If you return YES,
	 *             the file manager continues copying any other items and ignores the error. This method performs the same task as the
	 *             fileManager:shouldProceedAfterError:copyingItemAtPath:toPath: method and is preferred over that method in OS X v10.6 and
	 *             later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorCopyingItemAtURLToURL(NSFileManager fileManager, NSError error, NSURL srcURL,
																		   NSURL dstURL);

	// 9
	/**
	 * @Signature: fileManager:shouldProceedAfterError:linkingItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error linkingItemAtPath:(NSString
	 *              *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if the operation should continue after an error occurs while linking to the item at the specified
	 *              path.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem creating a hard link to the item at the specified location. If
	 *             you return YES, the file manager continues creating any other links associated with the current operation and ignores the
	 *             error. This method performs the same task as the fileManager:shouldProceedAfterError:linkingItemAtURL:toURL: method,
	 *             which is preferred over this method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorLinkingItemAtPathToPath(NSFileManager fileManager, NSError error, NSString srcPath,
																			 NSString dstPath);

	// 10
	/**
	 * @Signature: fileManager:shouldProceedAfterError:linkingItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error linkingItemAtURL:(NSURL
	 *              *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if the operation should continue after an error occurs while linking to the item at the specified
	 *              URL.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem creating a hard link to the item at the specified location. If
	 *             you return YES, the file manager continues creating any other links associated with the current operation and ignores the
	 *             error. This method performs the same task as the fileManager:shouldProceedAfterError:linkingItemAtPath:toPath: method and
	 *             is preferred over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorLinkingItemAtURLToURL(NSFileManager fileManager, NSError error, NSURL srcURL,
																		   NSURL dstURL);

	// 11
	/**
	 * @Signature: fileManager:shouldProceedAfterError:movingItemAtPath:toPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error movingItemAtPath:(NSString
	 *              *)srcPath toPath:(NSString *)dstPath
	 * @Description : Asks the delegate if the move operation should continue after an error occurs while moving the item at the specified
	 *              path.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem moving the item to the specified location. If you return YES,
	 *             the file manager proceeds to remove the item from its current location as if the move operation had completed
	 *             successfully. This method performs the same task as the fileManager:shouldProceedAfterError:movingItemAtURL:toURL:
	 *             method, which is preferred over this method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorMovingItemAtPathToPath(NSFileManager fileManager, NSError error, NSString srcPath,
																			NSString dstPath);

	// 12
	/**
	 * @Signature: fileManager:shouldProceedAfterError:movingItemAtURL:toURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error movingItemAtURL:(NSURL
	 *              *)srcURL toURL:(NSURL *)dstURL
	 * @Description : Asks the delegate if the move operation should continue after an error occurs while moving the item at the specified
	 *              URL.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem moving the item to the specified location. If you return YES,
	 *             the file manager proceeds to remove the item from its current location as if the move operation had completed
	 *             successfully. This method performs the same task as the fileManager:shouldProceedAfterError:movingItemAtPath:toPath:
	 *             method and is preferred over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorMovingItemAtURLToURL(NSFileManager fileManager, NSError error, NSURL srcURL,
																		  NSURL dstURL);

	// 13
	/**
	 * @Signature: fileManager:shouldProceedAfterError:removingItemAtPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error removingItemAtPath:(NSString
	 *              *)path
	 * @Description : Asks the delegate if the operation should continue after an error occurs while removing the item at the specified
	 *              path.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem deleting the item to the specified location. If you return
	 *             YES, the file manager continues deleting any remaining items and ignores the error. This method performs the same task as
	 *             the fileManager:shouldProceedAfterError:removingItemAtURL: method, which is preferred over this method in OS X v10.6 and
	 *             later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorRemovingItemAtPath(NSFileManager fileManager, NSError error, NSString path);

	// 14
	/**
	 * @Signature: fileManager:shouldProceedAfterError:removingItemAtURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldProceedAfterError:(NSError *)error removingItemAtURL:(NSURL
	 *              *)URL
	 * @Description : Asks the delegate if the operation should continue after an error occurs while removing the item at the specified URL.
	 * @return Return Value YES if the operation should proceed or NO if it should be aborted. If you do not implement this method, the file
	 *         manager assumes a response of NO.
	 * @Discussion The file manager calls this method when there is a problem deleting the item to the specified location. If you return
	 *             YES, the file manager continues deleting any remaining items and ignores the error. This method performs the same task as
	 *             the fileManager:shouldProceedAfterError:removingItemAtPath: method and is preferred over that method in OS X v10.6 and
	 *             later.
	 **/

	public boolean fileManagerShouldProceedAfterErrorRemovingItemAtURL(NSFileManager fileManager, NSError error, NSURL URL);

	// 15
	/**
	 * @Signature: fileManager:shouldRemoveItemAtPath:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldRemoveItemAtPath:(NSString *)path
	 * @Description : Asks the delegate whether the item at the specified path should be deleted.
	 * @return Return Value YES if the specified item should be deleted or NO if it should not be deleted.
	 * @Discussion Removed items are deleted immediately and not placed in the Trash. If the specified item is a directory, returning NO
	 *             prevents both the directory and its children from being deleted. This method performs the same task as the
	 *             fileManager:shouldRemoveItemAtURL: method, which is preferred over this method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldRemoveItemAtPath(NSFileManager fileManager, NSString path);

	// 16
	/**
	 * @Signature: fileManager:shouldRemoveItemAtURL:
	 * @Declaration : - (BOOL)fileManager:(NSFileManager *)fileManager shouldRemoveItemAtURL:(NSURL *)URL
	 * @Description : Asks the delegate whether the item at the specified URL should be deleted.
	 * @return Return Value YES if the specified item should be removed or NO if it should not be removed.
	 * @Discussion Removed items are deleted immediately and not placed in the Trash. If the specified item is a directory, returning NO
	 *             prevents both the directory and its children from being deleted. This method performs the same task as the
	 *             fileManager:shouldRemoveItemAtPath: method and is preferred over that method in OS X v10.6 and later.
	 **/

	public boolean fileManagerShouldRemoveItemAtURL(NSFileManager fileManager, NSURL URL);

}
