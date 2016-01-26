
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;


public class NSMessagePort extends NSPort {

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public Object copyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public void removeFromRunLoopForMode(NSRunLoop runLoop, NSString mode) {
	}

	@Override
	public void scheduleInRunLoopForMode(NSRunLoop runLoop, NSString mode) {
	}

}