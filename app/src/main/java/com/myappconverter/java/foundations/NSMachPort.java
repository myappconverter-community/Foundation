
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSMachPortDelegate;



public class NSMachPort extends NSPort {
	NSMachPortDelegate delegate;
	int machPort;

	public NSMachPort(int machPort) {
		super();
		this.machPort = machPort;
	}

	public enum MachPortRights {
		NSMachPortDeallocateNone, NSMachPortDeallocateSendRight, NSMachPortDeallocateReceiveRight
	}

	public int getMachPort() {
		return machPort;
	}

	public void setMachPort(int machPort) {
		this.machPort = machPort;
	}

	/**
	 * @Signature: portWithMachPort:
	 * @Declaration : + (NSPort *)portWithMachPort:(uint32_t)machPort
	 * @Description : Creates and returns a port object configured with the given Mach port.
	 * @param machPort The Mach port for the new port. This parameter should originally be of type mach_port_t.
	 * @return Return Value An NSMachPort object that uses machPort to send or receive messages.
	 * @Discussion Creates the port object if necessary. Depending on the access rights associated with machPort, the new port object may be
	 *             usable only for sending messages.
	 **/
	
	public static NSPort portWithMachPort(int machPort) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: portWithMachPort:options:
	 * @Declaration : + (NSPort *)portWithMachPort:(uint32_t)machPort options:(NSUInteger)options
	 * @Description : Creates and returns a port object configured with the specified options and the given Mach port.
	 * @param machPort The Mach port for the new port. This parameter should originally be of type mach_port_t.
	 * @param options Specifies options for what to do with the underlying port rights when the NSMachPort object is invalidated or
	 *            destroyed. For a list of constants, see “Mach Port Rights.
	 * @return Return Value An NSMachPort object that uses machPort to send or receive messages.
	 * @Discussion Creates the port object if necessary. Depending on the access rights associated with machPort, the new port object may be
	 *             usable only for sending messages.
	 **/
	
	public static NSPort portWithMachPortOptions(int machPort, long options) {
		// not yet covered
		return null;

	}

	/**
	 * @Signature: initWithMachPort:
	 * @Declaration : - (id)initWithMachPort:(uint32_t)machPort
	 * @Description : Initializes a newly allocated NSMachPort object with a given Mach port.
	 * @param machPort The Mach port for the new port. This parameter should originally be of type mach_port_t.
	 * @return Return Value Returns an initialized NSMachPort object that uses machPort to send or receive messages. The returned object
	 *         might be different than the original receiver
	 * @Discussion Depending on the access rights for machPort, the new port may be able to only send messages. If a port with machPort
	 *             already exists, this method deallocates the receiver, then retains and returns the existing port. This method is the
	 *             designated initializer for the NSMachPort class.
	 **/
	
	public Object initWithMachPort(int machPort) {
		return new NSMachPort(machPort);

	}

	/**
	 * @Signature: initWithMachPort:options:
	 * @Declaration : - (id)initWithMachPort:(uint32_t)machPort options:(NSUInteger)options
	 * @Description : Initializes a newly allocated NSMachPort object with a given Mach port and the specified options.
	 * @param machPort The Mach port for the new port. This parameter should originally be of type mach_port_t.
	 * @param options Specifies options for what to do with the underlying port rights when the NSMachPort object is invalidated or
	 *            destroyed. For a list of constants, see “Mach Port Rights.
	 * @return Return Value Returns an initialized NSMachPort object that uses machPort to send or receive messages. The returned object
	 *         might be different than the original receiver
	 * @Discussion Depending on the access rights for machPort, the new port may be able to only send messages. If a port with machPort
	 *             already exists, this method deallocates the receiver, then retains and returns the existing port.
	 **/
	
	public Object initWithMachPortOptions(int machPort, long options) {

		// TODO notion of right , send ,receive port do not exist in java(port peut étre en méme temps receiver et
		// sender)
		return new NSMachPort(machPort);

	}

	/**
	 * @Signature: machPort
	 * @Declaration : - (uint32_t)machPort
	 * @Description : Returns as an int the Mach port used by the receiver.
	 * @return Return Value The Mach port used by the receiver. Cast this value to a mach_port_t when using it with Mach system calls.
	 **/
	
	public int machPort() {
		return getMachPort();

	}

	/**
	 * @Signature: removeFromRunLoop:forMode:
	 * @Declaration : - (void)removeFromRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)mode
	 * @Description : Removes the receiver from the run loop mode mode of runLoop.
	 * @param runLoop The run loop from which to remove the receiver.
	 * @param mode The run loop mode from which to remove the receiver.
	 * @Discussion When the receiver is removed, the run loop stops monitoring the Mach port for incoming messages.
	 **/
	
	@Override
	public void removeFromRunLoopForMode(NSRunLoop runLoop, NSString mode) {
		runLoop.removePortForMode(this, mode);
	}

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)mode
	 * @Description : Schedules the receiver into the run loop mode mode of runLoop.
	 * @param runLoop The run loop to which to add the receiver.
	 * @param mode The run loop mode in which to add the receiver.
	 * @Discussion When the receiver is scheduled, the run loop monitors the mach port for incoming messages and, when a message arrives,
	 *             invokes the delegate method handleMachMessage:.
	 **/
	
	@Override
	public void scheduleInRunLoopForMode(NSRunLoop runLoop, NSString mode) {
		runLoop.addPortForMode(this, mode);
	}

	/**
	 * @Signature: delegate
	 * @Declaration : - (id < NSMachPortDelegate >)delegate
	 * @Description : Returns the receiver’s delegate.
	 * @return Return Value The receiver’s delegate.
	 **/
	
	public Object delegate() {
		return delegate;

	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id < NSMachPortDelegate >)anObject
	 * @Description : Sets the receiver’s delegate to a given object.
	 * @param anObject The delegate for the receiver.
	 **/
	
	public void setDelegate(Object anObject) {
		this.delegate = (NSMachPortDelegate) anObject;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

}