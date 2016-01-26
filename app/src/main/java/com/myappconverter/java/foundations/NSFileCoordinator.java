
package com.myappconverter.java.foundations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.Observable;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSFilePresenter;
import com.myappconverter.mapping.utils.PerformBlock;


public class NSFileCoordinator extends NSObject {

	private static Map<NSURL, NSFilePresenter> filePresentersList;
	private NSFilePresenter nsFilePresenter;

	// NSFileCoordinatorReadingOptions values
	public static final int NSFileCoordinatorReadingWithoutChanges = 1 << 0;
	public static final int NSFileCoordinatorReadingResolvesSymbolicLink = 1 << 1;

	// NSFileCoordinatorWritingOptions values
	public static final int NSFileCoordinatorWritingForDeleting = 1 << 0;
	public static final int NSFileCoordinatorWritingForReplacing = 1 << 3;
	public static final int NSFileCoordinatorWritingForMoving = 1 << 1;
	public static final int NSFileCoordinatorWritingForMerging = 1 << 2;

	public NSFilePresenter getNsFilePresenter() {
		return nsFilePresenter;
	}

	public void setNsFilePresenter(NSFilePresenter nsFilePresenter) {
		this.nsFilePresenter = nsFilePresenter;
	}

	// Initializing a File Coordinator
	/**
	 * @Signature: initWithFilePresenter:
	 * @Declaration : - (id)initWithFilePresenter:(id < NSFilePresenter >)filePresenterOrNil
	 * @Description : Initializes and returns a file coordinator object using the specified file presenter.
	 * @param filePresenterOrNil The file presenter object that is initiating some action on its file or directory. This object is assumed
	 *            to be performing the relevant file or directory operations and therefore does not receive notifications about those
	 *            operations from the returned file coordinator object. This parameter may be nil.
	 * @return Return Value A file coordinator object to use to coordinate file-related operations.
	 * @Discussion Specifying a file presenter at initialization time is strongly recommended, especially if the file presenter is
	 *             initiating the file operation. Otherwise, the file presenter itself would receive notifications when it made changes to
	 *             the file and would have to compensate for that fact. Receiving such notifications could also deadlock too if the file
	 *             presenter’s code and its notifications run on the same thread.
	 **/
	
	public Object initWithFilePresenter(NSFilePresenter filePresenterOrNil) {
		NSFileCoordinator nsFileCoordinator = new NSFileCoordinator();
		if (filePresenterOrNil != null
				&& filePresenterOrNil.getClass().isAssignableFrom(NSFilePresenter.class)) {
			nsFileCoordinator.setNsFilePresenter(filePresenterOrNil);

		}
		return nsFileCoordinator;
	}

	// Managing File Presenters
	/**
	 * @Signature: addFilePresenter:
	 * @Declaration : + (void)addFilePresenter:(id < NSFilePresenter >)filePresenter
	 * @Description : Registers the specified file presenter object so that it can receive notifications.
	 * @param filePresenter The file presenter object to register.
	 * @Discussion This method registers the file presenter object process-wide. Thus, any file coordinator objects you create later
	 *             automatically know about the file presenter object and know to message it when its file or directory is affected. In a
	 *             managed memory application—that is, an application that uses retain and release calls—it is your responsibility to
	 *             balance calls to this method with a corresponding call to the removeFilePresenter: method. You must remove file
	 *             presenters from the process-wide registry before the object is deallocated. If your application is garbage collected,
	 *             file presenters automatically unregister themselves in their finalize method. If you call this method while file
	 *             operations are already underway in another thread, your file presenter may not receive notifications for that operation.
	 *             To prevent missing such notifications, create a file coordinator, call its
	 *             coordinateReadingItemAtURL:options:error:byAccessor: method, and register your file presenter object there. Synchronizing
	 *             on the presented file or directory guarantees that when your block executes, all other objects have completed any tasks
	 *             and you have sole access to the item.
	 **/
	
	public static void addFilePresenter(Object filePresenter) {
		if (filePresenter != null
				&& filePresenter.getClass().isAssignableFrom(NSFilePresenter.class)) {

		}
	}

	/**
	 * @Signature: removeFilePresenter:
	 * @Declaration : + (void)removeFilePresenter:(id < NSFilePresenter >)filePresenter
	 * @Description : Unregisters the specified file presenter object.
	 * @param filePresenter The file presenter object to unregister. If the object is not currently registered, this method does nothing.
	 * @Discussion If your application uses a managed memory model, you must call this method to unregister file presenters before those
	 *             objects are deallocated. In a garbage-collected application, file presenters are unregistered automatically when they are
	 *             finalized.
	 **/
	
	public static void removeFilePresenter(Object filePresenter) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: filePresenters
	 * @Declaration : + (NSArray *)filePresenters
	 * @Description : Returns an array containing the currently registered file presenter objects.
	 * @return Return Value An array of objects that conform to the NSFilePresenter protocol.
	 **/
	
	public static NSArray<NSObject> filePresenters() {
		return new NSArray<NSObject>();
	}

	// Coordinating File Operations
	/**
	 * @Signature: coordinateReadingItemAtURL:options:error:byAccessor:
	 * @Declaration : - (void)coordinateReadingItemAtURL:(NSURL *)url options:(NSFileCoordinatorReadingOptions)options error:(NSError
	 *              **)outError byAccessor:(void (^)(NSURL *newURL))reader
	 * @Description : Initiates a read operation on a single file or directory using the specified options.
	 * @param url A URL identifying the file or directory to read. If other objects or processes are acting on the item at the URL, the
	 *            actual URL passed to reader parameter may be different than the one in this parameter.
	 * @param options One of the reading options described in “NSFileCoordinatorReadingOptions. If you pass 0 for this parameter, the
	 *            savePresentedItemChangesWithCompletionHandler: method of relevant file presenters is called before your block executes.
	 * @param outError On input, a pointer to a pointer for an error object. If a file presenter encounters an error while preparing for
	 *            this read operation, that error is returned in this parameter and the block in the reader parameter is not executed. If
	 *            you cancel this operation before the reader block is executed, this parameter contains an error object on output.
	 * @param reader A Block object containing the file operations you want to perform in a coordinated manner. This block receives an NSURL
	 *            object containing the URL of the item and returns no value. Always use the URL passed into the block instead of the value
	 *            in the url parameter.
	 * @Discussion You use this method to perform read-related operations on a file or directory in a coordinated manner. This method
	 *             executes synchronously, blocking the current thread until the reader block finishes executing.
	 **/
	
	public void coordinateReadingItemAtURLOptionsErrorByAccessor(NSURL url, int options,
			NSError[] outError, NSURL newURL, PerformBlock.VoidBlockNSURL reader) {

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(url.path().getWrappedString(), "r");
			// Lock it!
			FileLock lock = file.getChannel().lock();
			try {
				if (options == 0 && filePresentersList.containsKey(url)) {
					// If you pass 0 for this parameter, the savePresentedItemChangesWithCompletionHandler: method of
					// relevant
					// file presenters is called before your block executes
					NSFilePresenter filePresenter = filePresentersList.get(url);
					filePresenter.savePresentedItemChangesWithCompletionHandler(null);

				}
				reader.perform(newURL);
			} finally {
				// Release the lock.
				try {
					lock.release();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
			}
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));

		}
	}

	/**
	 * @Signature: coordinateWritingItemAtURL:options:error:byAccessor:
	 * @Declaration : - (void)coordinateWritingItemAtURL:(NSURL *)url options:(NSFileCoordinatorWritingOptions)options error:(NSError
	 *              **)outError byAccessor:(void (^)(NSURL *newURL))writer
	 * @Description : Initiates a write operation on a single file or directory using the specified options.
	 * @param url A URL identifying the file or directory to write. If other objects or processes are acting on the item at the URL, the
	 *            actual URL passed to writer parameter may be different than the one in this parameter.
	 * @param options One of the writing options described in “NSFileCoordinatorWritingOptions. The options you specify partially determine
	 *            how file presenters are notified and how this file coordinator object waits to execute your block.
	 * @param outError On input, a pointer to a pointer for an error object. If a file presenter encounters an error while preparing for
	 *            this write operation, that error is returned in this parameter and the block in the writer parameter is not executed. If
	 *            you cancel this operation before the writer block is executed, this parameter contains an error object on output.
	 * @param writer A Block object containing the file operations you want to perform in a coordinated manner. This block receives an NSURL
	 *            object containing the URL of the item and returns no value. Always use the URL passed into the block instead of the value
	 *            in the url parameter.
	 * @Discussion You use this method to perform write-related operations on a file or directory in a coordinated manner. This method
	 *             executes synchronously, blocking the current thread until the writer block finishes executing. Before executing the
	 *             block, though, the file coordinator waits until other relevant file presenter objects finish in-progress actions.
	 **/
	
	public void coordinateWritingItemAtURLOptionsErrorByAccessor(NSURL url, int options,
			NSError[] outError, PerformBlock.VoidBlockNSURL writer) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: coordinateReadingItemAtURL:options:writingItemAtURL:options:error:byAccessor:
	 * @Declaration : - (void)coordinateReadingItemAtURL:(NSURL *)readingURL options:(NSFileCoordinatorReadingOptions)readingOptions
	 *              writingItemAtURL:(NSURL *)writingURL options:(NSFileCoordinatorWritingOptions)writingOptions error:(NSError **)outError
	 *              byAccessor:(void (^)(NSURL *newReadingURL, NSURL *newWritingURL))readerWriter
	 * @Description : Initiates a read operation that contains a follow-up write operation.
	 * @param readingURL A URL identifying the file or directory to read. If other objects or processes are acting on the item at the URL,
	 *            the actual URL passed to the block in the readerWriter parameter may be different than the one in this parameter.
	 * @param readingOptions One of the reading options described in “NSFileCoordinatorReadingOptions. If you pass 0 for this parameter,
	 *            the savePresentedItemChangesWithCompletionHandler: method of relevant file presenters is called before your block
	 *            executes.
	 * @param writingURL A URL identifying the file or directory to write. If other objects or processes are acting on the item at the URL,
	 *            the actual URL passed to the block in the readerWriter parameter may be different than the one in this parameter.
	 * @param writingOptions One of the writing options described in “NSFileCoordinatorWritingOptions. The options you specify partially
	 *            determine how file presenters are notified and how this file coordinator object waits to execute your block.
	 * @param outError On input, a pointer to a pointer for an error object. If a file presenter encounters an error while preparing for
	 *            this operation, that error is returned in this parameter and the block in the readerWriter parameter is not executed. If
	 *            you cancel this operation before the readerWriter block is executed, this parameter contains an error object on output.
	 * @param readerWriter A block object containing the read and write operations you want to perform in a coordinated manner. This block
	 *            receives NSURL objects containing the URLs of the items to read and write and returns no value. Always use the URLs passed
	 *            into the block instead of the values in the readingURL and writingURL parameters.
	 * @Discussion You use this method to perform a read operation that might also contain a write operation that needs to be coordinated.
	 *             This method executes synchronously, blocking the current thread until the readerWriter block finishes executing. When
	 *             performing the write operation, you may call the coordinateWritingItemAtURL:options:error:byAccessor: method from your
	 *             readerWriter block. This method does the canonical lock ordering that is required to prevent a potential deadlock of the
	 *             file operations. This method makes the same calls to file presenters, and has the same general wait behavior as the
	 *             coordinateReadingItemAtURL:options:error:byAccessor: method.
	 **/
	
	public void coordinateReadingItemAtURLOptionsWritingItemAtURLOptionsErrorByAccessor(
			NSURL readingURL, int readingOptions, NSURL writingURL, int writingOptions,
			NSError[] outError, PerformBlock.VoidBlockNSURLNSURL readerWriter) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: coordinateWritingItemAtURL:options:writingItemAtURL:options:error:byAccessor:
	 * @Declaration : - (void)coordinateWritingItemAtURL:(NSURL *)url1 options:(NSFileCoordinatorWritingOptions)options1
	 *              writingItemAtURL:(NSURL *)url2 options:(NSFileCoordinatorWritingOptions)options2 error:(NSError **)outError
	 *              byAccessor:(void (^)(NSURL *newURL1, NSURL *newURL2))writer
	 * @Description : Initiates a write operation that involves a secondary write operation.
	 * @param url1 A URL identifying the first file or directory to write. If other objects or processes are acting on the item at the URL,
	 *            the actual URL passed to the block in the writer parameter may be different than the one in this parameter.
	 * @param options1 One of the writing options described in “NSFileCoordinatorWritingOptions.�?
	 * @param url2 A URL identifying the other file or directory to write. If other objects or processes are acting on the item at the URL,
	 *            the actual URL passed to the block in the writer parameter may be different than the one in this parameter.
	 * @param options2 One of the writing options described in “NSFileCoordinatorWritingOptions.�? The options you specify partially
	 *            determine how file presenters are notified and how this file coordinator object waits to execute your block.
	 * @param outError On input, a pointer to a pointer for an error object. If a file presenter encounters an error while preparing for
	 *            this operation, that error is returned in this parameter and the block in the writer parameter is not executed. If you
	 *            cancel this operation before the writer block is executed, this parameter contains an error object on output.
	 * @param writer A Block object containing the write operations you want to perform in a coordinated manner. This block receives NSURL
	 *            objects containing the URLs of the items to write and returns no value. Always use the URLs passed into the block instead
	 *            of the values in the url1 and url2 parameters.
	 * @Discussion You use this method to perform two write operations without the risk of those operations creating a deadlock. This method
	 *             executes synchronously, blocking the current thread until the writer block finishes executing. You may call the
	 *             coordinateWritingItemAtURL:options:error:byAccessor: method from your writer block. This method does the canonical lock
	 *             ordering that is required to prevent a potential deadlock of the file operations. This method makes the same calls to
	 *             file presenters, and has the same general wait behavior as the coordinateWritingItemAtURL:options:error:byAccessor:
	 *             method.
	 **/
	
	public void coordinateWritingItemAtURLOptionsWritingItemAtURLOptionsErrorByAccessor(NSURL url1,
			int options1, NSURL url2, int options2, NSError[] outError,
			PerformBlock.VoidBlockNSURLNSURL writer) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: prepareForReadingItemsAtURLs:options:writingItemsAtURLs:options:error:byAccessor:
	 * @Declaration : - (void)prepareForReadingItemsAtURLs:(NSArray *)readingURLs options:(NSFileCoordinatorReadingOptions)readingOptions
	 *              writingItemsAtURLs:(NSArray *)writingURLs options:(NSFileCoordinatorWritingOptions)writingOptions error:(NSError
	 *              **)outError byAccessor:(void)batchAccessor
	 * @Description : Prepare to read or write from multiple files in a single batch operation.
	 * @param readingURLs An array of NSURL objects identifying the items you want to read.
	 * @param readingOptions One of the reading options described in “NSFileCoordinatorReadingOptions.�? If you pass 0 for this parameter,
	 *            the savePresentedItemChangesWithCompletionHandler: method of relevant file presenters is called before your block
	 *            executes.
	 * @param writingURLs An array of NSURL objects identifying the items you want to write.
	 * @param writingOptions One of the writing options described in “NSFileCoordinatorWritingOptions.�? The options you specify partially
	 *            determine how file presenters are notified and how this file coordinator object waits to execute your block.
	 * @param outError On input, a pointer to a pointer for an error object. If a file presenter encounters an error while preparing for
	 *            this operation, that error is returned in this parameter and the block in the writer parameter is not executed. If you
	 *            cancel this operation before the batchAccessor block is executed, this parameter contains an error object on output.
	 * @param batchAccessor A Block object containing additional calls to methods of this class. This block receives a completion handler
	 *            that it must execute when it has finished its read and write calls.
	 * @Discussion You use this method to prepare the file coordinator for multiple read and write operations. This method executes
	 *             synchronously, blocking the current thread until the batchAccessor block finishes executing. The block you provide for
	 *             the batchAccessor parameter does not perform the actual operations itself but calls the
	 *             coordinateReadingItemAtURL:options:error:byAccessor: and coordinateWritingItemAtURL:options:error:byAccessor: methods to
	 *             perform them. The reason to call this method first is to improve performance when reading and writing large numbers of
	 *             files or directories. Because file coordination requires interprocess communication, it is much more efficient to batch
	 *             changes to large numbers of files and directories than to change each item individually. The file coordinator uses the
	 *             values in the readingURLs and writingURLs parameters together with reading and writing options to prepare any relevant
	 *             file presenters for the upcoming operations. Specifically, it uses these parameters in the same way as the
	 *             coordinateReadingItemAtURL:options:error:byAccessor: and coordinateWritingItemAtURL:options:error:byAccessor: methods to
	 *             determine which file presenter methods to call.
	 **/
	
	public void prepareForReadingItemsAtURLsOptionsWritingItemsAtURLsOptionsErrorByAccessor(
			NSArray readingURLs, int readingOptions, NSArray writingURLs, int writingOptions,
			NSError[] outError, Object batchAccessor) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: itemAtURL:willMoveToURL:
	 * @Declaration : - (void)itemAtURL:(NSURL *)oldURL willMoveToURL:(NSURL *)newURL
	 * @Description : Announces that your app is moving a file to a new URL.
	 * @param oldURL The old location of the file or directory.
	 * @param newURL The new location of the file or directory.
	 * @Discussion This method is intended for apps that adopt App Sandbox. Some apps need to rename files while saving them. For example,
	 *             when a user adds an attachment to a rich text document, TextEdit changes the document’s filename extension from .rtf to
	 *             .rtfd. In such a case, in a sandboxed app, you must call this method to declare your intent to rename a file without user
	 *             approval. After the renaming process succeeds, call the itemAtURL:didMoveToURL: method, with the same arguments, to
	 *             provide your app with continued access to the file under its new name, while also giving up access to any file that
	 *             appears with the old name. If your OS X app is not sandboxed, this method serves to purpose. This method is nonfunctional
	 *             in iOS.
	 **/
	
	public void itemAtURLwillMoveToURL(NSURL oldURL, NSURL newURL) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: itemAtURL:didMoveToURL:
	 * @Declaration : - (void)itemAtURL:(NSURL *)oldURL didMoveToURL:(NSURL *)newURL
	 * @Description : Notifies relevant file presenters that the location of a file or directory changed.
	 * @param oldURL The old location of the file or directory.
	 * @param newURL The new location of the file or directory.
	 * @Discussion If you move or rename a file or directory as part of a write operation, call this method to notify relevant file
	 *             presenters that the change occurred. This method calls the presentedItemDidMoveToURL: method of any relevant file
	 *             presenters. You must call this method from a block invoked by the coordinateWritingItemAtURL:options:error:byAccessor: or
	 *             coordinateWritingItemAtURL:options:writingItemAtURL:options:error:byAccessor: method. Calling this method with the same
	 *             URL in the oldURL and newURL parameters is harmless.
	 **/
	
	public void itemAtURLdidMoveToURL(NSURL oldURL, NSURL newURL) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: cancel
	 * @Declaration : - (void)cancel
	 * @Description : Cancels any active file coordination calls.
	 * @Discussion Use this method to cancel any active calls to coordinate the reading or writing of a file. If the block passed to the
	 *             file coordination call has not yet been executed—perhaps because the file coordinator is still waiting for a response
	 *             from other file presenters—the file coordinator method stops waiting for a response, does not execute its block, and
	 *             returns an error object with the error code NSUserCancelledError. However, if the block is already being executed, the
	 *             file coordinator method does not return until the block finishes executing. You can call this method from any thread of
	 *             your application and it returns immediately without waiting for the file coordinator object to respond. Thus, when this
	 *             method returns, you cannot assume that the read or write operation occurred or did not occur. (In fact, it is possible
	 *             for this method to return while the file coordinator is in the middle of executing a block.) If you want to know whether
	 *             the operation actually occurred, you must track it yourself by setting a flag when the block starts executing or by using
	 *             some other means.
	 **/
	
	public void cancel() {
		throw new UnsupportedOperationException();
	}

}