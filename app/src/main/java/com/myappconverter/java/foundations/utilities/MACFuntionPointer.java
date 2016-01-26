package com.myappconverter.java.foundations.utilities;

// this class allow us to create equivalent object of a function pointer
public class MACFuntionPointer {
	public Class clazz;
	public String methodName;
	public Class[] args;

	public MACFuntionPointer(Class clazz, String methodName, Class[] args) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
		this.args = args;
	}

}
