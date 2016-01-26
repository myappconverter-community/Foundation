
package com.myappconverter.java.foundations;

import java.io.IOException;

import android.os.ParcelFileDescriptor;
import android.util.Log;



public class NSPipe extends NSObject {

	// a handle to your read and write fd objects.
	private ParcelFileDescriptor readFD;
	private ParcelFileDescriptor writeFD;

	private ParcelFileDescriptor[] fdPair;

	// Creating an NSPipe Object

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Returns an initialized NSPipe object.
	 * @return Return Value An initialized NSPipe object. Returns nil if the method encounters errors while attempting to create the pipe or
	 *         the NSFileHandle objects that serve as endpoints of the pipe.
	 **/
	@Override

	public NSPipe init() {
		NSPipe nsPipe = new NSPipe();
		// make a pipe containing a read and a write parcelfd
		try {
			nsPipe.fdPair = ParcelFileDescriptor.createPipe();
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return nsPipe;
	}

	/**
	 * @Signature: pipe
	 * @Declaration : + (id)pipe
	 * @Description : Returns an NSPipe object.
	 * @return Return Value An initialized NSPipe object. Returns nil if the method encounters errors while attempting to create the pipe or
	 *         the NSFileHandle objects that serve as endpoints of the pipe.
	 **/

	public static Object pipe() {
		NSPipe nsPipe = new NSPipe();
		try {
			nsPipe.fdPair = ParcelFileDescriptor.createPipe();
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return nsPipe;
	}

	// Getting the File Handles for a Pipe

	/**
	 * @Signature: fileHandleForReading
	 * @Declaration : - (NSFileHandle *)fileHandleForReading
	 * @Description : Returns the receiver's read file handle.
	 * @return Return Value The receiver's read file handle.The descriptor represented by this object is deleted, and the object itself is
	 *         automatically deallocated when the receiver is deallocated.
	 * @Discussion You use the returned file handle to read from the pipe using NSFileHandle's read methods—availableData,
	 *             readDataToEndOfFile, and readDataOfLength:. You don’t need to send closeFile to this object or explicitly release the
	 *             object after you have finished using it.
	 **/

	public NSFileHandle fileHandleForReading() {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = readFD.getFileDescriptor();
		return nsFileHandle;

	}

	public NSFileHandle getFileHandleForReading() {
		return fileHandleForReading();

	}
	/**
	 * @Declaration : - (NSFileHandle *)fileHandleForWriting
	 * @Description : Returns the receiver's write file handle.
	 * @return Return Value The receiver's write file handle. This object is automatically deallocated when the receiver is deallocated.
	 * @Discussion You use the returned file handle to write to the pipe using NSFileHandle's writeData: method. When you are finished
	 *             writing data to this object, send it a closeFile message to delete the descriptor. Deleting the descriptor causes the
	 *             reading process to receive an end-of-data signal (an empty NSData object).
	 */

	public NSFileHandle fileHandleForWriting() {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = writeFD.getFileDescriptor();
		return nsFileHandle;
	}

	public NSFileHandle getFileHandleForWriting() {
		return fileHandleForWriting();
	}

}