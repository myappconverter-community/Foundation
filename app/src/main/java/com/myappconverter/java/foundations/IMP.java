package com.myappconverter.java.foundations;

// A pointer to the start of a method implementation.
public class IMP {

	private Object receiver;
	private SEL selector;
	NSObject[] params;

	public IMP() {
	}

	public IMP(NSObject receiver, SEL selector, NSObject... aParams) {
		this.receiver = receiver;
		this.selector = selector;
		this.params = aParams;
	}

	public void setSelector(SEL selector) {
		this.selector = selector;
	}

	public SEL getSelector() {
		return selector;
	}

	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}

	public Object getReceiver() {
		return receiver;
	}

}
