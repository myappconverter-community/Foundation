package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSFileVersion;
import com.myappconverter.java.foundations.NSOperationQueue;
import com.myappconverter.java.foundations.NSURL;


public interface NSFilePresenter extends NSObject {

	interface NSFilePresenterBlock {

		void reacquirer(NSFilePresenterBlock otherBlock);

		void completionHandler(NSError errorOrNil);

		static interface completionHandler {
			void performAction(NSError errorOrNil);
		}

		static interface writer {
			void performAction(NSFilePresenterBlock otherBlock);
		}

		static interface reader {
			void performAction(NSFilePresenterBlock otherBlock);
		}
	}

	// Accessing File Presenter Attributes

	// 1
	/**
	 * @Signature: accommodatePresentedItemDeletionWithCompletionHandler:
	 * @Declaration : - (void)accommodatePresentedItemDeletionWithCompletionHandler:(void (^)(NSError *errorOrNil))completionHandler
	 * @Description : Tells your object that its presented item is about to be deleted.
	 * @Discussion A file coordinator calls this method when your object’s presented item is about to be deleted. You can use this method to
	 *             perform any actions that are needed to prepare for the deletion. For example, document objects typically use this method
	 *             to close the document. Important: If you implement this method, you must execute the block in the completionHandler
	 *             parameter at the end of your implementation. The system waits for you to execute that block before allowing the other
	 *             object to delete the file or directory. Therefore, failure to execute the block could stall threads in your application
	 *             or other processes.
	 **/

	public void accommodatePresentedItemDeletionWithCompletionHandler(NSError errorOrNil,
																	  NSFilePresenterBlock.completionHandler completionHandler);

	// 2
	/**
	 * @Signature: accommodatePresentedSubitemDeletionAtURL:completionHandler:
	 * @Declaration : - (void)accommodatePresentedSubitemDeletionAtURL:(NSURL *)url completionHandler:(void (^)(NSError
	 *              *errorOrNil))completionHandler
	 * @Description : Tells the delegate that some entity wants to delete an item that is inside of a presented directory. (required)
	 * @Discussion This method is relevant for applications that present directories. This might occur if the delegate manages the contents
	 *             of a directory or manages a file that is implemented as a file package. When called, your implementation of this method
	 *             should take whatever actions needed to update your application to handle the deletion of the specified file.
	 *             Important: If you implement this method, you must execute the block in the completionHandler parameter at the end of your
	 *             implementation. The system waits for you to execute that block before allowing the other object to delete the item at the
	 *             specified URL. Therefore, failure to execute the block could stall threads in your application or in other processes.
	 **/

	public void accommodatePresentedSubitemDeletionAtURLCompletionHandler(NSURL url,
																		  NSFilePresenterBlock.completionHandler completionHandler);

	// 3
	/**
	 * @Signature: presentedItemDidChange
	 * @Declaration : - (void)presentedItemDidChange
	 * @Description : Tells your object that the presented item’s contents or attributes changed.
	 * @Discussion You can use this method to update your internal data structures to reflect the changes to the presented item. This method
	 *             reports both changes to the file’s contents, such as the data in a file or the files in a directory, or the attributes of
	 *             the item, such as whether the Hide extension checkbox of a file was toggled. Because this method notifies you of both
	 *             attribute and content changes, you might want to check the modification date before needlessly rereading the contents of
	 *             a file. To do that, you must store the date when your object last made changes to the file and compare that date with the
	 *             item’s current modification date. Use the coordinateReadingItemAtURL:options:error:byAccessor: method of a file
	 *             coordinator to ensure exclusive access to the file when reading the current modification date.
	 **/

	public void presentedItemDidChange();

	// 4
	/**
	 * @Signature: presentedItemDidGainVersion:
	 * @Declaration : - (void)presentedItemDidGainVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that a new version of the file or file package was added. (required)
	 * @Discussion Your delegate can use this method to determine how to incorporate data from the new version of the file or file package.
	 *             If the file has not been modified by your code, you might simply update to the new version quietly. However, if your
	 *             application has its own changes, you might need to ask the user how to proceed.
	 **/

	public void presentedItemDidGainVersion(NSFileVersion version);

	// 5
	/**
	 * @Signature: presentedItemDidLoseVersion:
	 * @Declaration : - (void)presentedItemDidLoseVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that a version of the file or file package was removed. (required)
	 * @Discussion Your delegate can use this method to determine how to handle the loss of the specified file version. You can try to
	 *             revert the presented document to a previous version or you might want to prompt the user about how to proceed.
	 **/

	public void presentedItemDidLoseVersion(NSFileVersion version);

	// 6
	/**
	 * @Signature: presentedItemDidMoveToURL:
	 * @Declaration : - (void)presentedItemDidMoveToURL:(NSURL *)newURL
	 * @Description : Tells your object that the presented item moved or was renamed.
	 * @Discussion Use this method to update the value returned by the presentedItemURL property of your object.
	 **/

	public void presentedItemDidMoveToURL(NSURL newURL);

	// 7
	/**
	 * @Signature: presentedItemDidResolveConflictVersion:
	 * @Declaration : - (void)presentedItemDidResolveConflictVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that some other entity resolved a version conflict for the presenter’s file or file package.
	 *              (required)
	 * @Discussion Your delegate can use this method to respond to the resolution of a version conflict by a different file presenter. This
	 *             might occur if a version of your application running on another device resolves the conflict first. You might then use
	 *             this method to update your user interface to indicate that there is no longer a conflict.
	 **/

	public void presentedItemDidResolveConflictVersion(NSFileVersion version);

	// 8
	/**
	 * @Signature: presentedSubitemAtURL:didGainVersion:
	 * @Declaration : - (void)presentedSubitemAtURL:(NSURL *)url didGainVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that the item inside the presented directory gained a new version. (required)
	 * @Discussion Your delegate can use this method to determine how to incorporate data from the new version of the item. This might
	 *             involve incorporating the version silently or asking the user about how to proceed.
	 **/

	public void presentedSubitemAtURLDidGainVersion(NSURL url, NSFileVersion version);

	// 9
	/**
	 * @Signature: presentedSubitemAtURL:didLoseVersion:
	 * @Declaration : - (void)presentedSubitemAtURL:(NSURL *)url didLoseVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that the item inside the presented directory lost an existing version. (required)
	 * @Discussion Your delegate can use this method to determine how to handle the loss of the specified file version. For an old version,
	 *             you might not have to do anything. However, if your application is currently using the lost version, you would need to
	 *             update your application’s user interface or prompt the user about how to proceed.
	 **/

	public void presentedSubitemAtURLDidLoseVersion(NSURL url, NSFileVersion version);

	// 10
	/**
	 * @Signature: presentedSubitemAtURL:didMoveToURL:
	 * @Declaration : - (void)presentedSubitemAtURL:(NSURL *)oldURL didMoveToURL:(NSURL *)newURL
	 * @Description : Tells the delegate that an item in the presented directory moved to a new location. (required)
	 * @Discussion This method is relevant for applications that present directories. This might occur if the delegate manages the contents
	 *             of a directory or manages a file that is implemented as a file package. Your implementation of this method should take
	 *             whatever actions necessary to handle the change in location of the specified item. For example, you might update
	 *             references to the item in your application’s data structures and refresh your user interface. If the presented directory
	 *             is a file package, the system calls the presentedItemDidChange method if your delegate does not implement this method.
	 **/

	public void presentedSubitemAtURLDidMoveToURL(NSURL oldURL, NSURL newURL);

	// 11
	/**
	 * @Signature: presentedSubitemAtURL:didResolveConflictVersion:
	 * @Declaration : - (void)presentedSubitemAtURL:(NSURL *)url didResolveConflictVersion:(NSFileVersion *)version
	 * @Description : Tells the delegate that the item inside the presented directory had a version conflict resolved by an outside entity.
	 *              (required)
	 * @Discussion Your delegate can use this method to respond to the resolution of a version conflict by a different file presenter. This
	 *             might occur if a version of your application running on another device resolves the conflict first. You might then use
	 *             this method to update your user interface to indicate that there is no longer a conflict.
	 **/

	public void presentedSubitemAtURLDidResolveConflictVersion(NSURL url, NSFileVersion version);

	// 12
	/**
	 * @Signature: presentedSubitemDidAppearAtURL:
	 * @Declaration : - (void)presentedSubitemDidAppearAtURL:(NSURL *)url
	 * @Description : Tells the delegate that an item was added to the presented directory. (required)
	 * @Discussion This method is relevant for applications that present directories. This might occur if the delegate manages the contents
	 *             of a directory or manages a file that is implemented as a file package. Your implementation of this method should take
	 *             whatever actions necessary to incorporate the new file or directory into the presented content. For example, you might
	 *             add the new item to your application’s data structures and refresh your user interface. If the presented directory is a
	 *             file package, the system calls the presentedItemDidChange method if your delegate does not implement this method.
	 **/

	public void presentedSubitemDidAppearAtURL(NSURL url);

	// 13
	/**
	 * @Signature: presentedSubitemDidChangeAtURL:
	 * @Declaration : - (void)presentedSubitemDidChangeAtURL:(NSURL *)url
	 * @Description : Tells the delegate that the contents or attributes of the specified item changed. (required)
	 * @Discussion This method is relevant for applications that present directories. This might occur if the delegate manages the contents
	 *             of a directory or manages a file that is implemented as a file package. Your implementation of this method should take
	 *             whatever actions necessary to handle the change in content or attributes of the specified item. If the presented
	 *             directory is a file package, the system calls the presentedItemDidChange method if your delegate does not implement this
	 *             method.
	 **/

	public void presentedSubitemDidChangeAtURL(NSURL url);

	// 14
	/**
	 * @Signature: relinquishPresentedItemToReader:
	 * @Declaration : - (void)relinquishPresentedItemToReader:(void)reader
	 * @Description : Notifies your object that another object or process wants to read the presented file or directory.
	 * @Discussion You use this method to provide an appropriate response when another object wants to read from your presented URL. For
	 *             example, when this method is called, you might temporarily stop making changes to the file or directory. After taking any
	 *             appropriate steps, you must execute the block in the reader parameter to let the waiting object know that it may now
	 *             proceed with its task. If you want to be notified when the reader has completed its task, pass your own block to the
	 *             reader and use that block to reacquire the file or URL for your own uses. Important: If you implement this method, you
	 *             must execute the block in the reader parameter as part of your implementation. The system waits for you to execute that
	 *             block before allowing the reader to operate on the file. Therefore, failure to execute the block could stall threads in
	 *             your application or other processes until the user takes corrective actions. The following listing shows a simple
	 *             implementation of this method that sets a Boolean flag that the file being monitored is not writable at the moment. After
	 *             setting the flag, it executes the reader block and passes in yet another block for the reader to execute when it is done.
	 *             - (void)relinquishPresentedItemToReader:(void (^)(void (^reacquirer)(void))) reader { // Prepare for another object to
	 *             read the file. self.fileIsWritable = NO; // Now let the reader know that it can have the file. // But pass a
	 *             reacquisition block so that this object // can update itself when the reader is done. reader(^{ self.fileIsWritable =
	 *             YES; }); } Your implementation of this method is executed using the queue in the presentedItemOperationQueue property.
	 *             Your reacquirer block is executed on the queue associated with the reader.
	 **/

	public void relinquishPresentedItemToReader();

	// 15
	/**
	 * @Signature: relinquishPresentedItemToWriter:
	 * @Declaration : - (void)relinquishPresentedItemToWriter:(void)writer
	 * @Description : Notifies your object that another object or process wants to write to the presented file or directory.
	 * @Discussion You use this method to provide an appropriate response when another object wants to write to your presented URL. For
	 *             example, when this method is called, you would likely stop making changes to the file or directory. After taking any
	 *             appropriate steps, you must execute the block in the writer parameter to let the waiting object know that it may now
	 *             proceed with its task. If you want to be notified when the writer has completed its task, pass your own block to the
	 *             writer and use that block to reacquire the file or URL for your own uses. Important: If you implement this method, you
	 *             must execute the block in the writer parameter at the end of your implementation. The system waits for you to execute
	 *             that block before allowing the writer to operate on the file. Therefore, failure to execute the block could stall threads
	 *             in your application or other processes. If the writer changes the file or directory, you do not need to incorporate those
	 *             changes in your reacquirer block. Instead, implement the presentedItemDidChange method and use it to detect when a writer
	 *             actually wrote its changes to disk. The following listing shows a simple implementation of this method that sets a
	 *             Boolean flag that the file being monitored is not writable at the moment. After setting the flag, it executes the writer
	 *             block and passes in yet another block for the writer to execute when it is done. -
	 *             (void)relinquishPresentedItemToWriter:(void (^)(void (^reacquirer)(void))) writer { // Prepare for another object to
	 *             write to the file. self.fileIsWritable = NO; // Now let the writer know that it can have the file. // But pass a
	 *             reacquisition block so that this object // can update itself when the writer is done. writer(^{ self.fileIsWritable =
	 *             YES; }); } Your implementation of this method is executed using the queue in the presentedItemOperationQueue property.
	 *             Your reacquirer block is executed on the queue associated with the writer.
	 **/

	public void relinquishPresentedItemToWriter();

	// 16
	/**
	 * @Signature: savePresentedItemChangesWithCompletionHandler:
	 * @Declaration : - (void)savePresentedItemChangesWithCompletionHandler:(void (^)(NSError *errorOrNil))completionHandler
	 * @Description : Tells your object to save any unsaved changes for the presented item.
	 * @Discussion The file coordinator calls this method to ensure that all objects trying to access the file or directory see the same
	 *             contents. Implement this method if your object can change the presented item in a way that requires you to write those
	 *             changes back to disk. If your presenter object does not make changes that need to be saved, you do not need to implement
	 *             this method. Important: If you implement this method, you must execute the block in the completionHandler parameter at
	 *             the end of your implementation. The system waits for you to execute that block before allowing other objects to operate
	 *             on the file. Therefore, failure to execute the block could stall threads in your application or other processes.
	 **/

	public void savePresentedItemChangesWithCompletionHandler(NSFilePresenterBlock.completionHandler completionHandler);

	// 17
	/**
	 * @Declaration : @property (readonly) NSOperationQueue *presentedItemOperationQueue
	 * @Description : The operation queue in which to execute presenter-related messages. (required) (read-only)
	 * @Discussion As other objects and processes interact with the presented item, the system queues relevant messages for this presenter
	 *             object on the operation queue in this property. For example, when another process attempts to read a file presented by
	 *             this object, the system places an invocation of this object’s relinquishPresentedItemToReader: method on the queue for
	 *             execution. The other process must wait to read the file until that method is dequeued and executed. Requests for an
	 *             object’s presented URL are not processed on this queue.
	 **/
	public NSOperationQueue presentedItemOperationQueue = new NSOperationQueue();

	// 18
	/**
	 * @Declaration : @property (readonly) NSURL *presentedItemURL
	 * @Description : The URL of the presented file or directory. (required) (read-only)
	 * @Discussion File presenters must implement this property and use it to return the file or directory of interest. If this object
	 *             presents a group of related files that all reside in the same directory, specify the URL of the directory instead of
	 *             creating separate presenter objects for each file. For example, a single-window application that manages multiple files
	 *             inside a project directory should monitor the project directory. The URL associated with your item may be requested by
	 *             objects not associated with your presenter. Therefore, your implementation of the accessor method for this property must
	 *             be thread safe and capable of running in multiple dispatch or operation queues simultaneously.
	 **/
	public NSURL presentedItemURL = new NSURL();
}
