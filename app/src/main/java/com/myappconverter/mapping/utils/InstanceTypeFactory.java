package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.SEL;

import android.util.Log;

import java.lang.reflect.Field;

public class InstanceTypeFactory {
	private static final String TAG = "InstanceTypeFactory";
	private static InstanceTypeFactory sharedInstance = null;

	public static InstanceTypeFactory getInstance() {
		if (sharedInstance == null)
			sharedInstance = new InstanceTypeFactory();
		return sharedInstance;
	}

	public Object getInstanceType(Object src, Class<?> clazz) {
		Object dest = InstanceTypeFactory.getInstance().instanceType(src, clazz);
		Object castDest = InstanceTypeFactory.getInstance().castToType(dest, clazz);
		return castDest;
	}

	public Object instanceType(Object object, Class<?> clazz) {
		Object newObject = null;
		if (object != null && clazz != null) {
			if (object.getClass().isAssignableFrom(clazz)) {
				try {
					newObject = clazz.newInstance();
					copyFields(object, newObject);
				} catch (InstantiationException e) {
					Log.d(TAG, "Exception : ", e);
				} catch (IllegalAccessException e) {
					Log.d(TAG, "Exception : ", e);
				}
			}
		}
		return newObject;

	}

	public Object castToType(Object object, Class<?> clazz) {
		return clazz.cast(object);
	}

	private <T extends Object, Y extends Object> void copyFields(T from, Y too) {

		Class<? extends Object> fromClass = from.getClass();
		Field[] fromFields = fromClass.getDeclaredFields();

		Class<? extends Object> tooClass = too.getClass();
		Field[] tooFields = tooClass.getDeclaredFields();

		if (fromFields != null && tooFields != null) {
			for (Field fromF : fromFields) {
				try {

					System.out.println("fromClass :" + fromClass.getName());
					System.out.println("tooClass :" + tooClass.getName());
					System.out.println("fromF :" + fromF.getName());
					Field tooF = tooClass.getField(fromF.getName());

					if (fromF.getType().equals(tooF.getType())) {
						tooF.set(too, fromF.get(from));
					}
				} catch (Exception e) {
					Log.d(TAG, "Exception : ", e);
				}

			}
		}
	}

	public static Object getInstance(Object src, Class<?> clazzSrc, Class<?> clazztarget, NSString methodeName, Object param) {
		if (clazzSrc.equals(clazztarget)) {
			return src;
		}
		Object target;
		try {
			target = clazztarget.newInstance();
			if (methodeName != null) {
				SEL sel = new SEL(methodeName);
				sel.invokeMethodOnTarget(target, sel.getMethodName(), param);
				return target;
			} else {
				// if you want execute just constructor
				return target;
			}
		} catch (InstantiationException e) {
			Log.d(TAG, "Exception : ", e);
		} catch (IllegalAccessException e) {
			Log.d(TAG, "Exception : ", e);
		}
		return null;
	}

	public static Object getInstance(Object src, Class<?> clazzSrc, Class<?> clazztarget, NSString methodeName, Object... params) {
		if (clazzSrc.equals(clazztarget)) {
			return src;
		}
		Object target;
		try {
			target = clazztarget.newInstance();
			if (methodeName != null) {
				SEL sel = new SEL(methodeName);
				sel.invokeMethodOnTarget(target, sel.getMethodName(), params);
				return target;
			} else {
				// if you want execute just constructor
				return target;
			}
		} catch (InstantiationException e) {
			Log.d(TAG, "Exception : ", e);
		} catch (IllegalAccessException e) {
			Log.d(TAG, "Exception : ", e);
		}
		return null;
	}

	public static Object getInstance(Object src, Class<?> clazzSrc, Class<?> clazztarget) {
		if (clazzSrc.equals(clazztarget)) {
			return src;
		}
		Object target;
		try {
			target = clazztarget.newInstance();
            return target;
		} catch (InstantiationException e) {
			Log.d(TAG, "Exception : ", e);
		} catch (IllegalAccessException e) {
			Log.d(TAG, "Exception : ", e);
		}
		return src;
	}

	public static Object getInstance(Class<?> clazztarget) {
		Object target;
		try {
			target = clazztarget.newInstance();
			return target;
		} catch (InstantiationException e) {
			Log.d(TAG, "Exception : ", e);
		} catch (IllegalAccessException e) {
			Log.d(TAG, "Exception : ", e);
		}
		return null;
	}
}
