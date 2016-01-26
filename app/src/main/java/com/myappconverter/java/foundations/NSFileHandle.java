
package com.myappconverter.java.foundations;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import android.net.LocalServerSocket;
import android.util.Log;

import com.myappconverter.java.corefoundations.utilities.AndroidIOUtils;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.AndroidStream;


public class NSFileHandle extends NSObject implements NSSecureCoding {

	// private static final int MAX_FILE_DATA_BUFFER = 0;
	// case of file
	FileDescriptor fDescriptor;
	// case of socket
	SocketChannel socketChannel;
	private NSError error;

	// Reading and Writing Using Blocks

	public static interface NSFileHandleBlock {
		/**
		 * @Declaration : @property (copy) void (^readabilityHandler)(NSFileHandle *);
		 * @Description : The block to use for reading the contents of the file handle asynchronously.
		 * @Discussion The default value of this property is nil. Assigning a valid block object to this property creates a dispatch source
		 *             for reading the contents of the file or socket. Your block is submitted to the file handle’s dispatch queue when
		 *             there is data to read. When reading a file, your handler block is typically executed repeatedly until the entire
		 *             contents of the file have been read. When reading data from a socket, your handler block is executed whenever there
		 *             is data on the socket waiting to be read. The block you provide must accept a single parameter that is the current
		 *             file handle. The return type of your block should be void. To stop reading the file or socket, set the value of this
		 *             property to nil. Doing so cancels the dispatch source and cleans up the file handle’s structures appropriately.
		 **/
		void readabilityHandler(NSFileHandle nsFileHandle);

		/**
		 * @Declaration : @property (copy) void (^writeabilityHandler)(NSFileHandle *);
		 * @Description : The block to use for writing the contents of the file handle asynchronously.
		 * @Discussion The default value of this property is nil. Assigning a valid block object to this property creates a dispatch source
		 *             for writing the contents of the file or socket. Your block is submitted to the file handle’s dispatch queue when
		 *             there is room available to write more data. When writing a file, your handler block is typically executed repeatedly
		 *             until the entire contents of the file have been written. When writing data to a socket, your handler block is
		 *             executed whenever the socket is ready to accept more data. The block you provide must accept a single parameter that
		 *             is the current file handle. The return type of your block should be void. To stop writing data to the file or socket,
		 *             set the value of this property to nil. Doing so cancels the dispatch source and cleans up the file handle’s
		 *             structures appropriately.
		 **/
		void writeabilityHandler(NSFileHandle nsFileHandle);
	}

	public NSFileHandle() {
		fDescriptor = new FileDescriptor();
	}

	// Getting a File Handle
	/**
	 * @Signature: fileHandleForReadingAtPath:
	 * @Declaration : + (id)fileHandleForReadingAtPath:(NSString *)path
	 * @Description : Returns a file handle initialized for reading the file, device, or named socket at the specified path.
	 * @param path The path to the file, device, or named socket to access.
	 * @return Return Value The initialized file handle object or nil if no file exists at path.
	 * @Discussion The file pointer is set to the beginning of the file. You cannot write data to the returned file handle object. Use the
	 *             readDataToEndOfFile or readDataOfLength: methods to read data from it. When using this method to create a file handle
	 *             object, the file handle owns its associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleForReadingAtPath(NSString path) {
		File file = new File(path.getWrappedString());
		if (file.exists()) {
			NSFileHandle nsFileHandle = new NSFileHandle();
			try {
				
				RandomAccessFile mFileHandler = new RandomAccessFile(file, "r");
				nsFileHandle.fDescriptor = mFileHandler.getFD();
				return nsFileHandle;
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return null;
	}

	/**
	 * @Signature: fileHandleForReadingFromURL:error:
	 * @Declaration : + (id)fileHandleForReadingFromURL:(NSURL *)url error:(NSError **)error
	 * @Description : Returns a file handle initialized for reading the file, device, or named socket at the specified URL.
	 * @param url The URL of the file, device, or named socket to access.
	 * @param error If an error occurs, upon return contains an NSError object that describes the problem. Pass NULL if you do not want
	 *            error information.
	 * @return Return Value The initialized file handle object or nil if no file exists at url.
	 * @Discussion The file pointer is set to the beginning of the file. You cannot write data to the returned file handle object. Use the
	 *             readDataToEndOfFile or readDataOfLength: methods to read data from it. When using this method to create a file handle
	 *             object, the file handle owns its associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleForReadingFromURLError(NSURL url, NSError error) {
		// TODO
		return fileHandleForReadingAtPath(url.path());
	}

	/**
	 * @Signature: fileHandleForWritingAtPath:
	 * @Declaration : + (id)fileHandleForWritingAtPath:(NSString *)path
	 * @Description : Returns a file handle initialized for writing to the file, device, or named socket at the specified path.
	 * @param path The path to the file, device, or named socket to access.
	 * @return Return Value The initialized file handle object or nil if no file exists at path.
	 * @Discussion The file pointer is set to the beginning of the file. You cannot read data from the returned file handle object. Use the
	 *             writeData: method to write data to the file handle. When using this method to create a file handle object, the file
	 *             handle owns its associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleForWritingAtPath(NSString path) {
		File file = new File(path.getWrappedString());
		RandomAccessFile mFileHandler = null;
		if (file.exists()) {
			NSFileHandle nsFileHandle = new NSFileHandle();
			try {
				mFileHandler = new RandomAccessFile(file, "rw");
				nsFileHandle.fDescriptor = mFileHandler.getFD();
				return nsFileHandle;
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} finally {
				try {
					if (mFileHandler != null)
						mFileHandler.close();
				} catch (IOException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
			}
		}
		return null;

	}

	/**
	 * @Signature: fileHandleForWritingToURL:error:
	 * @Declaration : + (id)fileHandleForWritingToURL:(NSURL *)url error:(NSError **)error
	 * @Description : Returns a file handle initialized for writing to the file, device, or named socket at the specified URL.
	 * @param url The URL of the file, device, or named socket to access.
	 * @param error If an error occurs, upon return contains an NSError object that describes the problem. Pass NULL if you do not want
	 *            error information.
	 * @return Return Value The initialized file handle object or nil if no file exists at url.
	 * @Discussion The file pointer is set to the beginning of the file. The returned object responds only to writeData:. When using this
	 *             method to create a file handle object, the file handle owns its associated file descriptor and is responsible for closing
	 *             it.
	 **/
	
	public static NSFileHandle fileHandleForWritingToURLError(NSURL url, NSError error) {
		return fileHandleForWritingAtPath(url.path());
	}

	/**
	 * @Signature: fileHandleForUpdatingAtPath:
	 * @Declaration : + (id)fileHandleForUpdatingAtPath:(NSString *)path
	 * @Description : Returns a file handle initialized for reading and writing to the file, device, or named socket at the specified path.
	 * @param path The path to the file, device, or named socket to access.
	 * @return Return Value The initialized file handle object or nil if no file exists at path.
	 * @Discussion The file pointer is set to the beginning of the file. The returned object responds to both read... messages and
	 *             writeData:. When using this method to create a file handle object, the file handle owns its associated file descriptor
	 *             and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleForUpdatingAtPath(NSString path) {
		NSFileHandle nsFileHandle = new NSFileHandle();
		File file = new File(path.getWrappedString());
		if (file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file, true);
				nsFileHandle.fDescriptor = fos.getFD();
				return nsFileHandle;
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return null;
	}

	/**
	 * @Signature: fileHandleForUpdatingURL:error:
	 * @Declaration : + (id)fileHandleForUpdatingURL:(NSURL *)url error:(NSError **)error
	 * @Description : Returns a file handle initialized for reading and writing to the file, device, or named socket at the specified URL.
	 * @param url The URL of the file, device, or named socket to access.
	 * @param error If an error occurs, upon return contains an NSError object that describes the problem. Pass NULL if you do not want
	 *            error information.
	 * @return Return Value The initialized file handle object or nil if no file exists at url.
	 * @Discussion The file pointer is set to the beginning of the file. The returned object responds to both NSFileHandleread... messages
	 *             and writeData:. When using this method to create a file handle object, the file handle owns its associated file
	 *             descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleForUpdatingURLError(NSURL url, NSError error) {
		return fileHandleForUpdatingAtPath(url.path());
	}

	/**
	 * @Signature: fileHandleWithStandardError
	 * @Declaration : + (id)fileHandleWithStandardError
	 * @Description : Returns the file handle associated with the standard error file.
	 * @return Return Value The shared file handle associated with the standard error file.
	 * @Discussion Conventionally this is a terminal device to which error messages are sent. There is one standard error file handle per
	 *             process; it is a shared instance. When using this method to create a file handle object, the file handle owns its
	 *             associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleWithStandardError() {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = FileDescriptor.err;
		return nsFileHandle;
	}

	/**
	 * @Signature: fileHandleWithStandardInput
	 * @Declaration : + (id)fileHandleWithStandardInput
	 * @Description : Returns the file handle associated with the standard input file.
	 * @return Return Value The shared file handle associated with the standard input file.
	 * @Discussion Conventionally this is a terminal device on which the user enters a stream of data. There is one standard input file
	 *             handle per process; it is a shared instance. When using this method to create a file handle object, the file handle owns
	 *             its associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleWithStandardInput() {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = FileDescriptor.in;
		return nsFileHandle;
	}

	/**
	 * @Signature: fileHandleWithStandardOutput
	 * @Declaration : + (id)fileHandleWithStandardOutput
	 * @Description : Returns the file handle associated with the standard output file.
	 * @return Return Value The shared file handle associated with the standard output file.
	 * @Discussion Conventionally this is a terminal device that receives a stream of data from a program. There is one standard output file
	 *             handle per process; it is a shared instance. When using this method to create a file handle object, the file handle owns
	 *             its associated file descriptor and is responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleWithStandardOutput() {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = FileDescriptor.out;
		return nsFileHandle;
	}

	/**
	 * @Signature: fileHandleWithNullDevice
	 * @Declaration : + (id)fileHandleWithNullDevice
	 * @Description : Returns a file handle associated with a null device.
	 * @return Return Value A file handle associated with a null device.
	 * @Discussion You can use null-device file handles as “placeholders for standard-device file handles or in collection objects to avoid
	 *             exceptions and other errors resulting from messages being sent to invalid file handles. Read messages sent to a
	 *             null-device file handle return an end-of-file indicator (an empty NSData object) rather than raise an exception. Write
	 *             messages are no-ops, whereas fileDescriptor returns an illegal value. Other methods are no-ops or return “sensible
	 *             values. When using this method to create a file handle object, the file handle owns its associated file descriptor and is
	 *             responsible for closing it.
	 **/
	
	public static NSFileHandle fileHandleWithNullDevice() {
		NSFileHandle nsFileHandle = new NSFileHandle();

		return nsFileHandle;
	}

	// Creating a File Handle
	// Posix.java in Libcore.io android
	/**
	 * @Signature: initWithFileDescriptor:
	 * @Declaration : - (id)initWithFileDescriptor:(int)fileDescriptor
	 * @Description : Initializes and returns a file handle object associated with the specified file descriptor.
	 * @param fileDescriptor The POSIX file descriptor with which to initialize the file handle. This descriptor represents an open file or
	 *            socket that you created previously. For example, when creating a file handle for a socket, you would pass the value
	 *            returned by the socket function.
	 * @return Return Value A file handle initialized with fileDescriptor.
	 * @Discussion The file descriptor you pass in to this method is not owned by the file handle object. Therefore, you are responsible for
	 *             closing the file descriptor at some point after disposing of the file handle object. You can create a file handle for a
	 *             socket by using the result of a socket call as fileDescriptor.
	 **/
	
	public NSFileHandle initWithFileDescriptor(FileDescriptor fileDescriptor) {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = fileDescriptor;
		return nsFileHandle;
	}

	/**
	 * @Signature: initWithFileDescriptor:closeOnDealloc:
	 * @Declaration : - (id)initWithFileDescriptor:(int)fileDescriptor closeOnDealloc:(BOOL)flag
	 * @Description : Initializes and returns a file handle object associated with the specified file descriptor and deallocation policy.
	 * @param fileDescriptor The POSIX file descriptor with which to initialize the file handle.
	 * @param flag YES if the returned file handle object should take ownership of the file descriptor and close it for you or NO if you
	 *            want to maintain ownership of the file descriptor.
	 * @return Return Value An initialized file handle object.
	 **/
	
	public NSFileHandle initWithFileDescriptorCloseOnDealloc(FileDescriptor fileDescriptor,
			boolean flag) {
		NSFileHandle nsFileHandle = new NSFileHandle();
		nsFileHandle.fDescriptor = fileDescriptor;
		return nsFileHandle;
	}

	// Getting a File Descriptor
	/**
	 * @Signature: fileDescriptor
	 * @Declaration : - (int)fileDescriptor
	 * @Description : Returns the file descriptor associated with the receiver.
	 * @return Return Value The POSIX file descriptor associated with the receiver.
	 * @Discussion You can use this method to retrieve the file descriptor while it is open. If the file handle object owns the file
	 *             descriptor, you must not close it yourself. However, you can use the closeFile method to close the file descriptor
	 *             programmatically. If you do call the closeFile method, subsequent calls to this method raise an exception.
	 **/
	
	public FileDescriptor fileDescriptor() {
		return fDescriptor;
	}

	public FileDescriptor getFileDescriptor() {
		return fDescriptor;
	}

	// Reading from a File Handle
	/**
	 * @Signature: availableData
	 * @Declaration : - (NSData *)availableData
	 * @Description : Returns the data currently available in the receiver.
	 * @return Return Value The data currently available through the receiver, up to the the maximum size that can be represented by an
	 *         NSData object.
	 * @Discussion If the receiver is a file, this method returns the data obtained by reading the file from the current file pointer to the
	 *             end of the file. If the receiver is a communications channel, this method reads up to a buffer of data and returns it; if
	 *             no data is available, the method blocks. Returns an empty data object if the end of file is reached. This method raises
	 *             NSFileHandleOperationException if attempts to determine the file-handle type fail or if attempts to read from the file or
	 *             channel fail.
	 **/
	
	public NSData availableData() {
		NSData nsData = new NSData();
		ByteBuffer byteBuffer;
		FileInputStream fis = new FileInputStream(fDescriptor);
		try {
			byteBuffer = ByteBuffer.allocate(fis.available());
			fis.read(byteBuffer.array());
			nsData = NSData.dataWithBytesLength(NSData.class, byteBuffer.array(),
					byteBuffer.capacity());
			fis.close();
			return nsData;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	public NSData getAvailableData() {
		return availableData();
	}

	/**
	 * @Signature: readDataToEndOfFile
	 * @Declaration : - (NSData *)readDataToEndOfFile
	 * @Description : Synchronously reads the available data up to the end of file or maximum number of bytes.
	 * @return Return Value The data available through the receiver up to maximum size that can be represented by an NSData object or, if a
	 *         communications channel, until an end-of-file indicator is returned.
	 * @Discussion This method invokes readDataOfLength: as part of its implementation.
	 **/
	
	public NSData readDataToEndOfFile() {
		NSData nsData = null;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 2);
		FileInputStream fis = new FileInputStream(fDescriptor);
		try {
			while (fis.read() != -1) {
				fis.read(byteBuffer.array());
			}
			nsData = NSData.dataWithBytesNoCopyLength(NSData.class, byteBuffer.array(),
					byteBuffer.capacity());
			fis.close();
			return nsData;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return nsData;
	}

	/**
	 * @Signature: readDataOfLength:
	 * @Declaration : - (NSData *)readDataOfLength:(NSUInteger)length
	 * @Description : Synchronously reads data up to the specified number of bytes.
	 * @param length The number of bytes to read from the receiver.
	 * @return Return Value The data available through the receiver up to a maximum of length bytes, or the maximum size that can be
	 *         represented by an NSData object, whichever is the smaller.
	 * @Discussion If the receiver is a file, this method returns the data obtained by reading length bytes starting at the current file
	 *             pointer. If length bytes are not available, this method returns the data from the current file pointer to the end of the
	 *             file. If the receiver is a communications channel, the method reads up to length bytes from the channel. Returns an empty
	 *             NSData object if the file is positioned at the end of the file or if an end-of-file indicator is returned on a
	 *             communications channel. This method raises NSFileHandleOperationException if attempts to determine the file-handle type
	 *             fail or if attempts to read from the file or channel fail.
	 **/
	
	public NSData readDataOfLength(int length) {
		NSData nsData = new NSData();
		ByteBuffer byteBuffer;
		FileInputStream fis = new FileInputStream(fDescriptor);
		try {
			byteBuffer = ByteBuffer.allocate(length);
			fis.read(byteBuffer.array());
			nsData = NSData.dataWithBytesLength(NSData.class, byteBuffer.array(), length);
			fis.close();
			return nsData;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;

	}

	// Writing to a File Handle
	/**
	 * @Signature: writeData:
	 * @Declaration : - (void)writeData:(NSData *)data
	 * @Description : Synchronously writes the specified data to the receiver.
	 * @param data The data to be written.
	 * @Discussion If the receiver is a file, writing takes place at the file pointer’s current position. After it writes the data, the
	 *             method advances the file pointer by the number of bytes written. This method raises an exception if the file descriptor
	 *             is closed or is not valid, if the receiver represents an unconnected pipe or socket endpoint, if no free space is left on
	 *             the file system, or if any other writing error occurs.
	 **/
	
	public void writeData(NSData data) {
		OutputStream output = new FileOutputStream(fDescriptor);
		try {
			AndroidIOUtils.write(data.bytes(), output);
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	// Communicating Asynchronously
	/**
	 * @Signature: acceptConnectionInBackgroundAndNotify
	 * @Declaration : - (void)acceptConnectionInBackgroundAndNotify
	 * @Description : Accepts a socket connection (for stream-type sockets only) in the background and creates a file handle for the “near
	 *              (client) end of the communications channel.
	 * @Discussion This method asynchronously creates a file handle for the other end of the socket connection and returns that object by
	 *             posting a NSFileHandleConnectionAcceptedNotification notification in the current thread. The notification includes a
	 *             userInfo dictionary with the created NSFileHandle object, which is accessible using the
	 *             NSFileHandleNotificationFileHandleItem key. You must call this method from a thread that has an active run loop.
	 **/
	
	public void acceptConnectionInBackgroundAndNotify() {
		LocalServerSocket localSocket;
		try {
			localSocket = new LocalServerSocket(fDescriptor);
			localSocket.accept();
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Signature: acceptConnectionInBackgroundAndNotifyForModes:
	 * @Declaration : - (void)acceptConnectionInBackgroundAndNotifyForModes:(NSArray *)modes
	 * @Description : Accepts a socket connection (for stream-type sockets only) in the background and creates a file handle for the “near
	 *              (client) end of the communications channel.
	 * @param modes The runloop modes in which the connection accepted notification can be posted.
	 * @Discussion See acceptConnectionInBackgroundAndNotify for details of how this method operates. This method differs from
	 *             acceptConnectionInBackgroundAndNotify in that modes specifies the run-loop mode (or modes) in which
	 *             NSFileHandleConnectionAcceptedNotification can be posted. You must call this method from a thread that has an active run
	 *             loop.
	 **/
	
	public void acceptConnectionInBackgroundAndNotifyForModes() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: readInBackgroundAndNotify
	 * @Declaration : - (void)readInBackgroundAndNotify
	 * @Description : Reads from the file or communications channel in the background and posts a notification when finished.
	 * @Discussion This method performs an asynchronous availableData operation on a file or communications channel and posts an
	 *             NSFileHandleReadCompletionNotification notification on the current thread when that operation is complete. You must call
	 *             this method from a thread that has an active run loop. The length of the data is limited to the buffer size of the
	 *             underlying operating system. The notification includes a userInfo dictionary that contains the data read; access this
	 *             object using the NSFileHandleNotificationDataItem key. Any object interested in receiving this data asynchronously must
	 *             add itself as an observer of NSFileHandleReadCompletionNotification. In communication via stream-type sockets, the
	 *             receiver is often the object returned in the userInfo dictionary of NSFileHandleConnectionAcceptedNotification. Note that
	 *             this method does not cause a continuous stream of notifications to be sent. If you wish to keep getting notified, you’ll
	 *             also need to call readInBackgroundAndNotify in your observer method.
	 **/
	
	public void readInBackgroundAndNotify() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				availableData();
			}
		}, "readInBackgroundAndNotify");
		thread.start();
	}

	/**
	 * @Signature: readInBackgroundAndNotifyForModes:
	 * @Declaration : - (void)readInBackgroundAndNotifyForModes:(NSArray *)modes
	 * @Description : Reads from the file or communications channel in the background and posts a notification when finished.
	 * @param modes The runloop modes in which the read completion notification can be posted.
	 * @Discussion See readInBackgroundAndNotify for details of how this method operates. This method differs from readInBackgroundAndNotify
	 *             in that modes specifies the run-loop mode (or modes) in which NSFileHandleReadCompletionNotification can be posted. You
	 *             must call this method from a thread that has an active run loop.
	 **/
	
	public void readInBackgroundAndNotifyForModes(NSArray modes) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				throw new UnsupportedOperationException();
			}
		}, "readInBackgroundAndNotifyForModes");
		thread.start();
	}

	/**
	 * @Signature: readToEndOfFileInBackgroundAndNotify
	 * @Declaration : - (void)readToEndOfFileInBackgroundAndNotify
	 * @Description : Reads to the end of file from the file or communications channel in the background and posts a notification when
	 *              finished.
	 * @Discussion This method performs an asynchronous readToEndOfFile operation on a file or communications channel and posts an
	 *             NSFileHandleReadToEndOfFileCompletionNotification. You must call this method from a thread that has an active run loop.
	 *             The notification includes a userInfo dictionary that contains the data read; access this object using the
	 *             NSFileHandleNotificationDataItem key. Any object interested in receiving this data asynchronously must add itself as an
	 *             observer of NSFileHandleReadToEndOfFileCompletionNotification. In communication via stream-type sockets, the receiver is
	 *             often the object returned in the userInfo dictionary of NSFileHandleConnectionAcceptedNotification.
	 **/
	
	public void readToEndOfFileInBackgroundAndNotify() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				readDataToEndOfFile();
			}
		}, "readToEndOfFileInBackgroundAndNotify");
		thread.start();
	}

	/**
	 * @Signature: readToEndOfFileInBackgroundAndNotifyForModes:
	 * @Declaration : - (void)readToEndOfFileInBackgroundAndNotifyForModes:(NSArray *)modes
	 * @Description : Reads to the end of file from the file or communications channel in the background and posts a notification when
	 *              finished.
	 * @param modes The runloop modes in which the read completion notification can be posted.
	 * @Discussion See readToEndOfFileInBackgroundAndNotify for details of this method's operation. The method differs from
	 *             readToEndOfFileInBackgroundAndNotify in that modes specifies the run-loop mode (or modes) in which
	 *             NSFileHandleReadToEndOfFileCompletionNotification can be posted. You must call this method from a thread that has an
	 *             active run loop.
	 **/
	
	public void readToEndOfFileInBackgroundAndNotifyForModes(NSArray modes) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				readDataToEndOfFile();
			}
		}, "readToEndOfFileInBackgroundAndNotifyForModes");
		thread.start();
	}

	/**
	 * @Signature: waitForDataInBackgroundAndNotify
	 * @Declaration : - (void)waitForDataInBackgroundAndNotify
	 * @Description : Asynchronously checks to see if data is available.
	 * @Discussion When the data becomes available, this method posts a NSFileHandleDataAvailableNotification notification on the current
	 *             thread. You must call this method from a thread that has an active run loop.
	 **/
	
	public void waitForDataInBackgroundAndNotify() {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Signature: waitForDataInBackgroundAndNotifyForModes:
	 * @Declaration : - (void)waitForDataInBackgroundAndNotifyForModes:(NSArray *)modes
	 * @Description : Asynchronously checks to see if data is available.
	 * @param modes The runloop modes in which the data available notification can be posted.
	 * @Discussion When the data becomes available, this method posts a NSFileHandleDataAvailableNotification notification on the current
	 *             thread. This method differs from waitForDataInBackgroundAndNotify in that modes specifies the run-loop mode (or modes) in
	 *             which NSFileHandleDataAvailableNotification can be posted. You must call this method from a thread that has an active run
	 *             loop.
	 **/
	
	public void waitForDataInBackgroundAndNotifyForModes(NSArray modes) {
		throw new UnsupportedOperationException();

	}

	// Seeking Within a File
	/**
	 * @Signature: offsetInFile
	 * @Declaration : - (unsigned long long)offsetInFile
	 * @Description : Returns the position of the file pointer within the file represented by the receiver.
	 * @return Return Value The position of the file pointer within the file represented by the receiver.
	 **/
	
	public long offsetInFile() {
		try {
			FileInputStream fis = new FileInputStream(fDescriptor);
			return fis.getChannel().position();
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return -1;
	}

	public long getOffsetInFile() {
		return offsetInFile();
	}

	/**
	 * @Signature: seekToEndOfFile
	 * @Declaration : - (unsigned long long)seekToEndOfFile
	 * @Description : Puts the file pointer at the end of the file referenced by the receiver and returns the new file offset.
	 * @return Return Value The file offset with the file pointer at the end of the file. This is therefore equal to the size of the file.
	 **/
	
	public long seekToEndOfFile() {
		return 0;
	}

	/**
	 * @Signature: seekToFileOffset:
	 * @Declaration : - (void)seekToFileOffset:(unsigned long long)offset
	 * @Description : Moves the file pointer to the specified offset within the file represented by the receiver.
	 * @param offset The offset to seek to.
	 **/
	
	public void seekToFileOffset(long offset) {
		FileInputStream fis = new FileInputStream(fDescriptor);
		try {
			fis.getChannel().position(offset);
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	// Operating on a File
	/**
	 * @Signature: closeFile
	 * @Declaration : - (void)closeFile
	 * @Description : Disallows further access to the represented file or communications channel and signals end of file on communications
	 *              channels that permit writing.
	 * @Discussion If the file handle object owns its file descriptor, it automatically closes that descriptor when it is deallocated. If
	 *             you initialized the file handle object using the initWithFileDescriptor: method, or you initialized it using the
	 *             initWithFileDescriptor:closeOnDealloc: and passed NO for the flag parameter, you can use this method to close the file
	 *             descriptor; otherwise, you must close the file descriptor yourself. After calling this method, you may still use the file
	 *             handle object but must not attempt to read or write data or use the object to operate on the file descriptor. Attempts to
	 *             read or write a closed file descriptor raise an exception.
	 **/
	
	public void closeFile() {
		FileInputStream fis = new FileInputStream(fDescriptor);
		try {
			fis.close();
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Signature: synchronizeFile
	 * @Declaration : - (void)synchronizeFile
	 * @Description : Causes all in-memory data and attributes of the file represented by the receiver to be written to permanent storage.
	 * @Discussion This method should be invoked by programs that require the file to always be in a known state. An invocation of this
	 *             method does not return until memory is flushed.
	 **/
	
	public void synchronizeFile() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: truncateFileAtOffset:
	 * @Declaration : - (void)truncateFileAtOffset:(unsigned long long)offset
	 * @Description : Truncates or extends the file represented by the receiver to a specified offset within the file and puts the file
	 *              pointer at that position.
	 * @param offset The offset within the file that will mark the new end of the file.
	 * @Discussion If the file is extended (if offset is beyond the current end of file), the added characters are null bytes.
	 **/
	
	public void truncateFileAtOffset(long offset) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}