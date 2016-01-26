package com.myappconverter.mapping.utils;

public class FunctionPointer {

	String returnType;
	String functionName;
	Class[] args;

	public FunctionPointer(String returnType, String functionName, Class[] args) {
		super();
		this.returnType = returnType;
		this.functionName = functionName;
		this.args = args;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Class[] getArgs() {
		return args;
	}

	public void setArgs(Class[] args) {
		this.args = args;
	}

}
