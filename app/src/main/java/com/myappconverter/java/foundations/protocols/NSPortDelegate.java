package com.myappconverter.java.foundations.protocols;



public abstract class NSPortDelegate {

	// 1
	/**
	 * @Signature: handlePortMessage:
	 * @Declaration : - (void)handlePortMessage:(NSPortMessage *)portMessage
	 * @Description : Processes a given incoming message on the port.
	 * @Discussion See NSPort Class Reference for more information. The delegate should implement either handlePortMessage: or the
	 *             NSMachPortDelegate Protocol protocol method handleMachMessage:. You must not implement both delegate methods.
	 **/

	public abstract void handlePortMessage(NSPortMessage portMessage);

}