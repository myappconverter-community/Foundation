
package com.myappconverter.java.foundations;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;


public abstract class NSPort extends NSObject implements NSCoding, NSCopying {

	private Socket wrappedSocket;
	private static InetSocketAddress wrappedInetSocketAdress;
	private static int port;

	/**
	 * @Signature: port
	 * @Declaration : + (NSPort *)port
	 * @Description : Creates and returns a new NSPort object capable of both sending and receiving messages.
	 * @return Return Value A new NSPort object capable of both sending and receiving messages.
	 **/

	public static SocketAddress port() {
		wrappedInetSocketAdress = new InetSocketAddress(port);
		return wrappedInetSocketAdress;

	}

	/**
	 * Marks the receiver as invalid and posts an NSPortDidBecomeInvalidNotification to the default notification center. - (void)invalidate
	 *
	 * @Discussion You must call this method before releasing a port object (or removing strong references to it if your application is
	 *             garbage collected).
	 */


	public void invalidate() {

		try {

			wrappedSocket.close();

		} catch (IOException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	/**
	 * @Signature: isValid
	 * @Declaration : - (BOOL)isValid
	 * @Description : Returns a Boolean value that indicates whether the receiver is valid.
	 * @return Return Value NO if the receiver is known to be invalid, otherwise YES.
	 * @Discussion An NSPort object becomes invalid when its underlying communication resource, which is operating system dependent, is
	 *             closed or damaged.
	 **/

	public boolean isValid() {
		return wrappedSocket.isClosed();
	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id < NSPortDelegate >)anObject
	 * @Description : Sets the receiver’s delegate to a given object.
	 * @param anObject The delegate for the receiver.
	 **/

	public void setDelegate(Object anObject) {
		this.wrappedSocket = (Socket) anObject;
	}

	/**
	 * @Signature: delegate
	 * @Declaration : - (id < NSPortDelegate >)delegate
	 * @Description : Returns the receiver’s delegate.
	 * @return Return Value The receiver’s delegate.
	 **/

	public Object delegate() {
		return this.wrappedSocket;

	}

	/**
	 * @Signature: sendBeforeDate:components:from:reserved:
	 * @Declaration : - (BOOL)sendBeforeDate:(NSDate *)limitDate components:(NSMutableArray *)components from:(NSPort *)receivePort
	 *              reserved:(NSUInteger)headerSpaceReserved
	 * @Description : This method is provided for subclasses that have custom types of NSPort.
	 * @param limitDate The last instant that a message may be sent.
	 * @param components The message components.
	 * @param receivePort The receive port.
	 * @param headerSpaceReserved The number of bytes reserved for the header.
	 * @Discussion NSConnection calls this method at the appropriate times. This method should not be called directly. This method could
	 *             raise an NSInvalidSendPortException, NSInvalidReceivePortException, or an NSPortSendException, depending on the type of
	 *             send port and the type of error.
	 **/

	public boolean sendBeforeDateComponentsFromReserved(NSDate limitDate, String components, NSPort receivePort, long headerSpaceReserved) {

		Integer dateTime = (int) limitDate.getWrappedDate().getTime();

		InetSocketAddress socketAdre = (InetSocketAddress) receivePort.port();
		port = socketAdre.getPort();

		try {
			wrappedSocket.connect(socketAdre, dateTime);
			OutputStream os = wrappedSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(components);
			bw.flush();
			return true;
		} catch (IOException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}

	}

	/**
	 * @Signature: sendBeforeDate:msgid:components:from:reserved:
	 * @Declaration : - (BOOL)sendBeforeDate:(NSDate *)limitDate msgid:(NSUInteger)msgID components:(NSMutableArray *)components
	 *              from:(NSPort *)receivePort reserved:(NSUInteger)headerSpaceReserved
	 * @Description : This method is provided for subclasses that have custom types of NSPort.
	 * @param limitDate The last instant that a message may be sent.
	 * @param msgID The message ID.
	 * @param components The message components.
	 * @param receivePort The receive port.
	 * @param headerSpaceReserved The number of bytes reserved for the header.
	 * @Discussion NSConnection calls this method at the appropriate times. This method should not be called directly. This method could
	 *             raise an NSInvalidSendPortException, NSInvalidReceivePortException, or an NSPortSendException, depending on the type of
	 *             send port and the type of error.
	 **/

	public boolean sendBeforeDateMsgidComponentsFromReserved(NSDate limitDate, long msgID, String components, NSPort receivePort,
															 long headerSpaceReserved) {
		Integer dateTime = (int) limitDate.getWrappedDate().getTime();

		InetSocketAddress socketAdre = (InetSocketAddress) receivePort.port();
		port = socketAdre.getPort();

		try {
			wrappedSocket.connect(socketAdre, dateTime);
			OutputStream os = wrappedSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(components);
			bw.flush();
			return true;
		} catch (IOException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
	}

	/**
	 * @Signature: reservedSpaceLength
	 * @Declaration : - (NSUInteger)reservedSpaceLength
	 * @Description : Returns the number of bytes of space reserved by the receiver for sending data.
	 * @return Return Value The number of bytes reserved by the receiver for sending data. The default length is 0.
	 **/

	public long reservedSpaceLength() {
		try {

			wrappedSocket.getInputStream().available();
		} catch (IOException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		return 0;
	}

	/**
	 * @Signature: removeFromRunLoop:forMode:
	 * @Declaration : - (void)removeFromRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)mode
	 * @Description : This method should be implemented by a subclass to stop monitoring of a port when removed from a give run loop in a
	 *              given input mode.
	 * @param runLoop The run loop from which to remove the receiver.
	 * @param mode The run loop mode from which to remove the receiver
	 * @Discussion This method should not be called directly.
	 **/

	abstract public void removeFromRunLoopForMode(NSRunLoop runLoop, NSString mode);

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)mode
	 * @Description : This method should be implemented by a subclass to set up monitoring of a port when added to a given run loop in a
	 *              given input mode.
	 * @param runLoop The run loop to which to add the receiver.
	 * @param mode The run loop mode to which to add the receiver
	 * @Discussion This method should not be called directly.
	 **/

	abstract public void scheduleInRunLoopForMode(NSRunLoop runLoop, NSString mode);

	public static int getPort() {
		return port;
	}

	/**
	 * @Signature: allocWithZone:
	 * @Declaration : + (id)allocWithZone:(NSZone *)zone
	 * @Description : Returns an instance of the NSMachPort class. (Available in iOS 2.0 through iOS 4.3.)
	 * @param zone The memory zone in which to allocate the new object.
	 * @return Return Value An instance of the NSMachPort class.
	 * @Discussion For backward compatibility on Mach, allocWithZone: returns an instance of the NSMachPort class when sent to the NSPort
	 *             class. Otherwise, it returns an instance of a concrete subclass that can be used for messaging between threads or
	 *             processes on the local machine, or, in the case of NSSocketPort, between processes on separate machines.
	 **/


	public static NSObject allocWithZone(NSZone zone) {
		// not yet covered
		return null;
	}

	@Override
	abstract public NSCoding initWithCoder(NSCoder decoder);
}