package com.myappconverter.java.foundations.utilities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.myappconverter.java.foundations.NSObject;

public class GenericProxyFactory {

	// FIXME maybe change Object to NSObject

	public static <T> T getProxy(Class<? extends NSObject> class1, final NSObject object) {
		return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class[] { class1 }, new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return method.invoke(object, args);
			}
		});
	}

}
