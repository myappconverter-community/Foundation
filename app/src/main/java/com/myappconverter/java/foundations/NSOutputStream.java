
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSStreamDelegate;



public class NSOutputStream extends NSStream {

	OutputStream mOutputStream;
	byte[] mbuffer;
	NSString filePath;
	NSURL URL;

	public NSString getFilePath() {
		return filePath;
	}

	public void setFilePath(NSString filePath) {
		this.filePath = filePath;
	}

	public OutputStream getmOutputStream() {
		return mOutputStream;
	}

	public void setmOutputStream(OutputStream mOutputStream) {
		this.mOutputStream = mOutputStream;
	}

	/**
	 * @Signature: write:maxLength:
	 * @Declaration : - (NSInteger)write:(const uint8_t *)buffer maxLength:(NSUInteger)length
	 * @Description : Writes the contents of a provided data buffer to the receiver.
	 * @param buffer The data to write.
	 * @param length The length of the data buffer, in bytes.
	 * @param newParam TODO
	 * @return Return Value The number of bytes actually written, or -1 if an error occurs. More information about the error can be obtained
	 *         with streamError. If the receiver is a fixed-length stream and has reached its capacity, 0 is returned.
	 **/

	public int writeMaxLength(byte[] buffer, long length) {
		int mInteger = 0;
		try {

			System.arraycopy(buffer, 0, mbuffer, 0, (int) length);
			// TODO
			return mInteger;
		} catch (Exception e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return -1;
		}
	}

	/**
	 * @Signature: hasSpaceAvailable
	 * @Declaration : - (BOOL)hasSpaceAvailable
	 * @Description : Returns whether the receiver can be written to.
	 * @return Return Value YES if the receiver can be written to or if a write must be attempted in order to determine if space is
	 *         available, NO otherwise.
	 **/

	public boolean hasSpaceAvailable() {
		try {
			mOutputStream.write(mbuffer);
			return true;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
	}

	/**
	 * @Signature: initToBuffer:capacity:
	 * @Declaration : - (id)initToBuffer:(uint8_t *)buffer capacity:(NSUInteger)capacity
	 * @Description : Returns an initialized output stream that can write to a provided buffer.
	 * @param buffer The buffer the output stream will write to.
	 * @param capacity The size of the buffer in bytes.
	 * @return Return Value An initialized output stream that can write to buffer.
	 * @Discussion The stream must be opened before it can be used. When the number of bytes written to buffer has reached capacity, the
	 *             stream’s streamStatus will return NSStreamStatusAtEnd.
	 **/

	public Object initToBufferCapacity(byte[] buffer, long capacity) {
		// TODO check that, the stream's streamStatus will return NSStreamStatusAtEnd.
		mbuffer = new byte[(int) capacity];
		try {
			open();
			System.arraycopy(buffer, 0, mbuffer, 0, (int) capacity);
			mOutputStream.write(buffer, 0, (int) capacity);
			return this;
		} catch (IndexOutOfBoundsException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return null;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return null;
		}
	}

	/**
	 * @Signature: initWithURL:append:
	 * @Declaration : - (id)initWithURL:(NSURL *)url append:(BOOL)shouldAppend
	 * @Description : Returns an initialized output stream for writing to a specified URL.
	 * @param url The URL to the file the output stream will write to.
	 * @param shouldAppend YES if newly written data should be appended to any existing file contents, NO otherwise.
	 * @return Return Value An initialized output stream that can write to url.
	 * @Discussion The stream must be opened before it can be used.
	 **/

	public Object initWithURLAppend(NSURL url, Boolean shouldAppend) {
		if (shouldAppend) {
			this.URL = url;
			return this;
		} else {
			try {
				this.mOutputStream.write(0);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
			return this;
		}
	}

	/**
	 * @Signature: initToFileAtPath:append:
	 * @Declaration : - (id)initToFileAtPath:(NSString *)path append:(BOOL)shouldAppend
	 * @Description : Returns an initialized output stream for writing to a specified file.
	 * @param path The path to the file the output stream will write to.
	 * @param shouldAppend YES if newly written data should be appended to any existing file contents, NO otherwise.
	 * @return Return Value An initialized output stream that can write to path.
	 * @Discussion The stream must be opened before it can be used.
	 **/

	public Object initToFileAtPathAppend(NSString path, Boolean shouldAppend) {
		if (shouldAppend) {
			this.filePath = path;
			return this;
		} else {
			try {
				this.filePath = path;
				// TODO check that
				this.mbuffer = new byte[255];
				this.mOutputStream.write(0);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
			return this;
		}
	}

	/**
	 * @Signature: initToMemory
	 * @Declaration : - (id)initToMemory
	 * @Description : Returns an initialized output stream that will write to memory.
	 * @return Return Value An initialized output stream that will write stream data to memory.
	 * @Discussion The stream must be opened before it can be used. The contents of the memory stream are retrieved by passing the constant
	 *             NSStreamDataWrittenToMemoryStreamKey to propertyForKey:.
	 **/

	public NSOutputStream initToMemory() {
		return this;
	}

	/**
	 * @Signature: outputStreamWithURL:append:
	 * @Declaration : + (id)outputStreamWithURL:(NSURL *)url append:(BOOL)shouldAppend
	 * @Description : Creates and returns an initialized output stream for writing to a specified URL.
	 * @param url The URL to the file the output stream will write to.
	 * @param shouldAppend YES if newly written data should be appended to any existing file contents, NO otherwise.
	 * @return Return Value An initialized output stream that can write to url.
	 * @Discussion The stream must be opened before it can be used.
	 **/

	public static Object outputStreamWithURLAppend(NSURL url, Boolean shouldAppend) {
		NSOutputStream OutPutS = new NSOutputStream();
		OutPutS.URL = url;
		if (shouldAppend) {
			OutPutS.URL = url;
			return OutPutS;
		} else {
			try {
				OutPutS.URL = url;
				// TODO check that
				OutPutS.mbuffer = new byte[255];
				OutPutS.mOutputStream.write(0);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
			return OutPutS;
		}
	}

	/**
	 * @Signature: outputStreamToFileAtPath:append:
	 * @Declaration : + (id)outputStreamToFileAtPath:(NSString *)path append:(BOOL)shouldAppend
	 * @Description : Creates and returns an initialized output stream for writing to a specified file.
	 * @param path The path to the file the output stream will write to.
	 * @param shouldAppend YES if newly written data should be appended to any existing file contents, NO otherwise.
	 * @return Return Value An initialized output stream that can write to path.
	 * @Discussion The stream must be opened before it can be used.
	 **/

	public static Object outputStreamToFileAtPathAppend(NSString path, Boolean shouldAppend) {
		NSOutputStream OutPutS = new NSOutputStream();
		OutPutS.filePath = path;
		if (shouldAppend) {
			OutPutS.filePath = path;
			return OutPutS;
		} else {
			try {
				OutPutS.filePath = path;
				// TODO check that
				OutPutS.mbuffer = new byte[255];
				OutPutS.mOutputStream.write(0);
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
			return OutPutS;
		}
	}

	/**
	 * @Signature: outputStreamToBuffer:capacity:
	 * @Declaration : + (id)outputStreamToBuffer:(uint8_t *)buffer capacity:(NSUInteger)capacity
	 * @Description : Creates and returns an initialized output stream that can write to a provided buffer.
	 * @param buffer The buffer the output stream will write to.
	 * @param capacity The size of the buffer in bytes.
	 * @return Return Value An initialized output stream that can write to buffer.
	 * @Discussion The stream must be opened before it can be used. When the number of bytes written to buffer has reached capacity, the
	 *             stream’s streamStatus will return NSStreamStatusAtEnd.
	 **/

	public static Object outputStreamToBufferCapacity(byte[] buffer, long capacity) {
		// TODO check that, the stream's streamStatus will return NSStreamStatusAtEnd.
		NSOutputStream OutPutS = new NSOutputStream();
		OutPutS.mbuffer = new byte[(int) capacity];
		try {
			OutPutS.open();
			System.arraycopy(buffer, 0, OutPutS.mbuffer, 0, (int) capacity);
			OutPutS.mOutputStream.write(buffer, 0, (int) capacity);
			return OutPutS;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return null;
		}
	}

	/**
	 * @Signature: outputStreamToMemory
	 * @Declaration : + (id)outputStreamToMemory
	 * @Description : Creates and returns an initialized output stream that will write stream data to memory.
	 * @return Return Value An initialized output stream that will write stream data to memory.
	 * @Discussion The stream must be opened before it can be used. You retrieve the contents of the memory stream by sending the message
	 *             propertyForKey: to the receiver with an argument of NSStreamDataWrittenToMemoryStreamKey.
	 **/

	public static Object outputStreamToMemory() {
		return new NSOutputStream();
	}

	@Override
	public Object propertyForKey(NSString key) {

		return null;
	}

	@Override
	public boolean setPropertyForKey(Object Property, NSString key) {

		return false;
	}

	@Override
	public NSStream delegate() {

		return null;
	}

	@Override
	public void setDelegate(NSStreamDelegate delegate) {

	}

	@Override
	public void open() {

	}

	@Override
	public void close() {

	}

	@Override
	public void removeFromRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {

	}

	@Override
	public void scheduleInRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {

	}

	@Override
	public NSError streamError() {

		return null;
	}

	@Override
	public NSStreamStatus streamStatus() {

		return null;
	}

	// protocol methods

}