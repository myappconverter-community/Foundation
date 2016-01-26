
package com.myappconverter.java.foundations;

import java.util.HashMap;
import java.util.Map;

import com.myappconverter.java.foundations.protocols.NSStreamDelegate;



public abstract class NSStream extends NSObject {

	private NSError errorStream;
	private NSStream delegate;
	private Map<String, Object> property;


	public enum NSStreamStatus {
		NSStreamStatusNotOpen, //
		NSStreamStatusOpening, //
		NSStreamStatusOpen, //
		NSStreamStatusReading, //
		NSStreamStatusWriting, //
		NSStreamStatusAtEnd, //
		NSStreamStatusClosed, //
		NSStreamStatusError7
	}

	public NSStream() {
		property = new HashMap<String, Object>();
		property.put("NSStreamSocketSecurityLevelKey", null);
		property.put("NSStreamSOCKSProxyConfigurationKey", null);
		property.put("NSStreamSOCKSProxyHostKey", null);
		property.put("NSStreamSOCKSProxyPortKey", null);
		property.put("NSStreamSOCKSProxyVersionKey", null);
		property.put("NSStreamSOCKSProxyUserKey", null);
		property.put("NSStreamSOCKSProxyPasswordKey", null);
		property.put("NSStreamSOCKSProxyVersion4", null);
		property.put("NSStreamSOCKSProxyVersion5", null);
		property.put("NSStreamDataWrittenToMemoryStreamKey", null);
		property.put("NSStreamFileCurrentOffsetKey", null);
		property.put("NSStreamNetworkServiceType", null);
	}

	/**
	 * @Signature: propertyForKey:
	 * @Declaration : - (id)propertyForKey:(NSString *)key
	 * @Description : Returns the receiverâ€™s property for a given key.
	 * @param key The key for one of the receiver's properties. See â€œConstantsâ€? for a description of the available property-key constants
	 *            and associated values.
	 * @return Return Value The receiverâ€™s property for the key key.
	 **/

	public abstract Object propertyForKey(NSString key);

	/**
	 * @Signature: setProperty:forKey:
	 * @Declaration : - (BOOL)setProperty:(id)property forKey:(NSString *)key
	 * @Description : Attempts to set the value of a given property of the receiver and returns a Boolean value that indicates whether the
	 *              value is accepted by the receiver.
	 * @param property The value for key.
	 * @param key The key for one of the receiver's properties. See â€œConstantsâ€? for a description of the available property-key constants
	 *            and expected values.
	 * @return Return Value YES if the value is accepted by the receiver, otherwise NO.
	 **/

	public abstract boolean setPropertyForKey(Object Property, NSString key);

	/**
	 * @Signature: delegate
	 * @Declaration : - (id < NSStreamDelegate >)delegate
	 * @Description : Returns the receiverâ€™s delegate.
	 * @return Return Value The receiverâ€™s delegate. The delegate must implement the NSStreamDelegate Protocol.
	 * @Discussion By default, a stream is its own delegate, and subclasses of NSInputStream and NSOutputStream must maintain this contract.
	 **/

	public abstract NSStream delegate();

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id < NSStreamDelegate >)delegate
	 * @Description : Sets the receiverâ€™s delegate.
	 * @param delegate The delegate for the receiver.
	 * @Discussion By default, a stream is its own delegate, and subclasses of NSInputStream and NSOutputStream must maintain this contract.
	 *             If you override this method in a subclass, passing nil must restore the receiver as its own delegate. Delegates are not
	 *             retained. To learn about delegates and delegation, read "â€œDelegationâ€? in Cocoa Fundamentals Guide" in Cocoa Fundamentals
	 *             Guide.
	 **/

	public abstract void setDelegate(NSStreamDelegate delegate);

	/**
	 * @Signature: open
	 * @Declaration : - (void)open
	 * @Description : Opens the receiving stream.
	 * @Discussion A stream must be created before it can be opened. Once opened, a stream cannot be closed and reopened.
	 **/

	public abstract void open();

	/**
	 * @Signature: close
	 * @Declaration : - (void)close
	 * @Description : Closes the receiver.
	 * @Discussion Closing the stream terminates the flow of bytes and releases system resources that were reserved for the stream when it
	 *             was opened. If the stream has been scheduled on a run loop, closing the stream implicitly removes the stream from the run
	 *             loop. A stream that is closed can still be queried for its properties.
	 **/

	public abstract void close();

	/**
	 * @Signature: removeFromRunLoop:forMode:
	 * @Declaration : - (void)removeFromRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Removes the receiver from a given run loop running in a given mode.
	 * @param aRunLoop The run loop on which the receiver was scheduled.
	 * @param mode The mode for the run loop.
	 **/

	public abstract void removeFromRunLoopForMode(NSRunLoop aRunLoop, NSString mode);

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Schedules the receiver on a given run loop in a given mode.
	 * @param aRunLoop The run loop on which to schedule the receiver.
	 * @param mode The mode for the run loop.
	 * @Discussion Unless the client is polling the stream, it is responsible for ensuring that the stream is scheduled on at least one run
	 *             loop and that at least one of the run loops on which the stream is scheduled is being run.
	 **/

	public abstract void scheduleInRunLoopForMode(NSRunLoop aRunLoop, NSString mode);

	/**
	 * @Signature: streamError
	 * @Declaration : - (NSError *)streamError
	 * @Description : Returns an NSError object representing the stream error.
	 * @return Return Value An NSError object representing the stream error, or nil if no error has been encountered.
	 **/

	public abstract NSError streamError();

	/**
	 * @Signature: streamStatus
	 * @Declaration : - (NSStreamStatus)streamStatus
	 * @Description : Returns the receiverâ€™s status.
	 * @return Return Value The receiverâ€™s status.
	 * @Discussion See â€œConstantsâ€? for a description of the available NSStreamStatus constants.
	 **/

	public abstract NSStreamStatus streamStatus();

}