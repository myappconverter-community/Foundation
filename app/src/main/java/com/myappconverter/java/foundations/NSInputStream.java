
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSStreamDelegate;



public class NSInputStream extends NSStream {

	InputStream wrappedInputStream;
	NSData data;
	NSString filePath;
	NSURL URL;

	public InputStream getWrappedInputStream() {
		return wrappedInputStream;
	}

	public void setWrappedInputStream(InputStream mInputStream) {
		this.wrappedInputStream = mInputStream;
	}

	/**
	 * @Signature: getBuffer:length:
	 * @Declaration : - (BOOL)getBuffer:(uint8_t **)buffer length:(NSUInteger *)len
	 * @Description : Returns by reference a pointer to a read buffer and, by reference, the number of bytes available, and returns a
	 *              Boolean value that indicates whether the buffer is available.
	 * @param buffer Upon return, contains a pointer to a read buffer. The buffer is only valid until the next stream operation is
	 *            performed.
	 * @param len Upon return, contains the number of bytes available.
	 * @return Return Value YES if the buffer is available, otherwise NO. Subclasses of NSInputStream may return NO if this operation is not
	 *         appropriate for the stream type.
	 **/
	
	public boolean getBufferLength(byte[][] buffer, int[] len) {

		try {
			byte[] bytes = ByteBuffer.allocate(4).putInt(wrappedInputStream.read()).array();
			buffer[0] = bytes;
			len[0] = wrappedInputStream.read();
			return true;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
	}

	/**
	 * @Signature: hasBytesAvailable
	 * @Declaration : - (BOOL)hasBytesAvailable
	 * @Description : Returns a Boolean value that indicates whether the receiver has bytes available to read.
	 * @return Return Value YES if the receiver has bytes available to read, otherwise NO. May also return YES if a read must be attempted
	 *         in order to determine the availability of bytes.
	 **/
	
	public boolean hasBytesAvailable() {
		try {
			wrappedInputStream.available();
			return true;
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
	}

	/**
	 * @Signature: read:maxLength:
	 * @Declaration : - (NSInteger)read:(uint8_t *)buffer maxLength:(NSUInteger)len
	 * @Description : Reads up to a given number of bytes into a given buffer.
	 * @param buffer A data buffer. The buffer must be large enough to contain the number of bytes specified by len.
	 * @param len The maximum number of bytes to read.
	 * @return Return Value A number indicating the outcome of the operation: A positive number indicates the number of bytes read; 0
	 *         indicates that the end of the buffer was reached; A negative number means that the operation failed.
	 **/
	
	public int readMaxLength(byte[] buffer, int len) {
		try {
			return wrappedInputStream.read(buffer, 0, len);
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return -1;
		}
	}

	/**
	 * @Signature: inputStreamWithData:
	 * @Declaration : + (id)inputStreamWithData:(NSData *)data
	 * @Description : Creates and returns an initialized NSInputStream object for reading from a given NSData object.
	 * @param data The data object from which to read. The contents of data are copied.
	 * @return Return Value An initialized NSInputStream object for reading from data. If data is not an NSData object, this method returns
	 *         nil.
	 **/
	
	public static NSInputStream inputStreamWithData(NSData DATA) {
		NSInputStream nsinput = new NSInputStream();
		nsinput.data = DATA;
		return nsinput;
	}

	/**
	 * @Signature: inputStreamWithFileAtPath:
	 * @Declaration : + (id)inputStreamWithFileAtPath:(NSString *)path
	 * @Description : Creates and returns an initialized NSInputStream object that reads data from the file at a given path.
	 * @param path The path to the file.
	 * @return Return Value An initialized NSInputStream object that reads data from the file at path.
	 **/
	
	public static NSInputStream inputStreamWithFileAtPath(NSString path) {
		NSInputStream nsinput = new NSInputStream();
		nsinput.filePath = path;
		return nsinput;
	}

	/**
	 * @Signature: inputStreamWithURL:
	 * @Declaration : + (id)inputStreamWithURL:(NSURL *)url
	 * @Description : Creates and returns an initialized NSInputStream object that reads data from the file at a given URL.
	 * @param url The URL to the file.
	 * @return Return Value An initialized NSInputStream object that reads data from the URL at url.
	 **/
	
	public static NSInputStream inputStreamWithURL(NSURL url) {
		NSInputStream nsinput = new NSInputStream();
		nsinput.URL = url;
		return nsinput;
	}

	/**
	 * @Signature: initWithData:
	 * @Declaration : - (id)initWithData:(NSData *)data
	 * @Description : Initializes and returns an NSInputStream object for reading from a given NSData object.
	 * @param data The data object from which to read. The contents of data are copied.
	 * @return Return Value An initialized NSInputStream object for reading from data.
	 **/
	
	public NSInputStream initWithData(NSData DATA) {
		this.data = DATA;
		return this;
	}

	/**
	 * @Signature: initWithFileAtPath:
	 * @Declaration : - (id)initWithFileAtPath:(NSString *)path
	 * @Description : Initializes and returns an NSInputStream object that reads data from the file at a given path.
	 * @param path The path to the file.
	 * @return Return Value An initialized NSInputStream object that reads data from the file at path.
	 **/
	
	public NSInputStream initWithFileAtPath(NSString path) {
		this.filePath = path;
		return this;
	}

	/**
	 * @Signature: initWithURL:
	 * @Declaration : - (id)initWithURL:(NSURL *)url
	 * @Description : Initializes and returns an NSInputStream object that reads data from the file at a given URL.
	 * @param url The URL to the file.
	 * @return Return Value An initialized NSInputStream object that reads data from the file at url.
	 **/
	
	public NSInputStream initWithURL(NSURL url) {
		this.URL = url;
		return this;
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

}