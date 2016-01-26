package com.myappconverter.java.foundations.protocols;


public interface NSSecureCoding extends NSCoding {

	// 1
	/**
	 * @Signature: supportsSecureCoding
	 * @Declaration : + (BOOL)supportsSecureCoding;
	 * @Description : Returns whether the class supports secure coding. (required)
	 **/

	public boolean supportsSecureCoding();

}