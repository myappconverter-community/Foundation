package com.myappconverter.java.foundations.protocols;


public interface NSMachPortDelegate {

	// 1
	/**
	 * @Signature: handleMachMessage:
	 * @Declaration : - (void)handleMachMessage:(void *)machMessage
	 * @Description : Process an incoming Mach message.
	 * @Discussion The delegate should interpret this data as a pointer to a Mach message beginning with a msg_header_t structure and should
	 *             handle the message appropriately. The delegate should implement either handleMachMessage: or the NSPortDelegate Protocol
	 *             protocol method handlePortMessage:.
	 **/

	public void handleMachMessage(Object machMessage);

}
