package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSStream;

public abstract class NSStreamDelegate {

	// 1
	/**
	 * @Signature: stream:handleEvent:
	 * @Declaration : - (void)stream:(NSStream *)theStream handleEvent:(NSStreamEvent)streamEvent
	 * @Description : The delegate receives this message when a given event has occurred on a given stream.
	 * @Discussion The delegate receives this message only if theStream is scheduled on a run loop. The message is sent on the stream
	 *             object’s thread. The delegate should examine streamEvent to determine the appropriate action it should take.
	 **/

	public abstract void streamHandleEvent(NSStream theStream, NSStreamEvent streamEvent);

}