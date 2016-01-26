package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.kvc.NSKeyValueCoding;
import com.myappconverter.java.foundations.utilities._NSMethod;
import com.myappconverter.java.foundations.utilities._NSReflectionUtilities;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class SEL implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private static final long serialVersionUID = 1L;
	private static final Class<?>[] _NoClassArray = new Class[0];
	private static final Object[] _NoObjectArray = new Object[0];
	private static final String TAG = "SEL";

	protected String _name;
	protected Class<?>[] _types;
	private transient Class<?> _cachedClass;
	private transient _NSMethod _cachedMethod;
	private transient NSMutableDictionary<String, _NSMethod> _classToMethodMapTable = new NSMutableDictionary<String, _NSMethod>(8);

	public SEL(NSString name) {
		this(name.getWrappedString());
	}

	public String name() {
		return _name;
	}

	public void setMethodName(String methodName) {
		this._name = methodName;
	}

	public String getMethodName() {
		return _name;
	}

	public void invokeMethodOnTarget(Object target, String methodName, Object... parameter) {
		Method method = null;
		boolean paramNull = false;
		if (parameter == null) {
			try {
				method = target.getClass().getDeclaredMethod(methodName);
			} catch (NoSuchMethodException e) {
				Log.d(TAG, "Exception occured ", e);
			}
			if (method == null) {
				try {
					method = target.getClass().getMethod(methodName);
				} catch (NoSuchMethodException e) {
					Log.d(TAG, "Exception occured ", e);
				}
			}
			paramNull = true;
		} else {
			if (parameter.length > 1) {

				try {
					Log.d(TAG, "try to invoke method name " + methodName + " with param className " + parameter.getClass().getSimpleName()
							+ " on target : " + target.getClass().getSimpleName());
					method = target.getClass().getDeclaredMethod(methodName, parameter.getClass());
				} catch (NoSuchMethodException e) {
					Log.d(TAG, "Exception occured ", e);
				}
				if (method == null) {
					try {
						method = target.getClass().getMethod(methodName, parameter.getClass());
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
				}
				// Objective C retourne la methode meme si parameter is null
				if (method == null) {
					try {
						parameter = null;
						method = target.getClass().getMethod(methodName);
						paramNull = true;
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
				}
				if (method == null) {
					try {
						parameter = null;
						method = target.getClass().getDeclaredMethod(methodName);
						paramNull = true;
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
				}

			} else if (parameter.length == 1) {
				// Special Case

				Object singleParam = parameter[0];
				if (singleParam != null) {
					try {
						Log.d(TAG, "try to invoke method name " + methodName + " with param className "
								+ singleParam.getClass().getSimpleName() + " on target : " + target.getClass().getSimpleName());
						method = target.getClass().getDeclaredMethod(methodName, singleParam.getClass());
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
					if (method == null) {
						try {
							method = target.getClass().getMethod(methodName, singleParam.getClass());
						} catch (NoSuchMethodException e) {
							Log.d(TAG, "Exception occured ", e);
						}
					}
				}
				if (method == null) {
					try {
						singleParam = null;
						method = target.getClass().getMethod(methodName);
						paramNull = true;
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
				}
				if (method == null) {
					try {
						singleParam = null;
						method = target.getClass().getDeclaredMethod(methodName);
						paramNull = true;
					} catch (NoSuchMethodException e) {
						Log.d(TAG, "Exception occured ", e);
					}
				}
				// End Special Case

			}
		}
		if (method != null) {
			try {
				if (paramNull) {
					method.invoke(target);
				} else {
					method.invoke(target, parameter);
				}
			} catch (Exception e) {
				Log.d(TAG, "Exception occured :", e);
			}

		} else
			Log.d(TAG, "method not found");
	}

	public static Object _safeInvokeSelector(SEL selector, Object receiver, Object... parameters) {
		try {
			return selector.invoke(receiver, parameters != null ? parameters : _NoObjectArray);
		} catch (InvocationTargetException e) {
            LOGGER.info(String.valueOf(e));
		} catch (IllegalAccessException e) {
            LOGGER.info(String.valueOf(e));
		} catch (NoSuchMethodException e) {
            LOGGER.info(String.valueOf(e));
		}
		return null;
	}

	protected SEL() {
	}

	public SEL(String name, Class<?>... parameterTypes) {
		if (name == null) {
			throw new IllegalArgumentException("Selector name cannot be null");
		}

		_name = name;
		if (parameterTypes != null) {
			_types = new Class[parameterTypes.length];
			System.arraycopy(parameterTypes, 0, _types, 0, parameterTypes.length);
		} else {
			_types = _NoClassArray;
		}
	}

	public Object invoke(Object target, Object... parameters) throws IllegalArgumentException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		_NSMethod method = methodOnObject(target);
		return method.invoke(target, parameters != null ? parameters : new Object[0]);
	}

	private Object invoke(Object target) throws IllegalArgumentException, NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		return invoke(target, _NoObjectArray);
	}

	private static Object invoke(String name, Class<?> parameterTypes[], Object target, Object... parameters)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		SEL selector = new SEL(name, parameterTypes);
		return selector.invoke(target, parameters);
	}

	private static Object invoke(String name, Class<?> parameterType, Object target, Object argument) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		if (parameterType == null) {
			throw new IllegalArgumentException("Parameter type cannot be null");
		}
		SEL selector = new SEL(name, parameterType);
		return selector.invoke(target, argument);
	}

	private static Object invoke(String name, Class<?> parameterType1, Class<?> parameterType2, Object target, Object argument1,
			Object argument2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		if (parameterType1 == null || parameterType2 == null) {
			throw new IllegalArgumentException("Neither parameter type can be null");
		}
		SEL selector = new SEL(name, parameterType1, parameterType2);
		return selector.invoke(target, argument1, argument2);
	}

	public Class<?>[] parameterTypes() {
		if (_types== null ) {
			_types = _NoClassArray;
			return _NoClassArray;
		}
		if (_types!= null && _types.length == 0) {
			return _types;
		}

		Class<?>[] types = new Class[_types.length];
		System.arraycopy(_types, 0, types, 0, _types.length);
		return types;
	}

	private synchronized _NSMethod _methodOnObject(Object targetObject) {
		Class<?> targetClass = targetObject.getClass();
		if (targetClass == _cachedClass) {
			return _cachedMethod;
		}

		String className = targetClass.getName();
		Object value = _classToMethodMapTable.objectForKey(className);
		if (value == null) {
			try {
				value = _NSReflectionUtilities.methodOnObject(targetObject, _name, _types);
			} catch (NoSuchMethodException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}

			if (value == null)
				value = NSKeyValueCoding.NullValue;

			_classToMethodMapTable.takeValueForKey(value, className);

		}

		_NSMethod method = null;
		if (value != NSKeyValueCoding.NullValue) {
			method = (_NSMethod) value;
		}

		_cachedClass = targetClass;
		_cachedMethod = method;
		return method;
	}

	private _NSMethod methodOnObject(Object targetObject) throws NoSuchMethodException {
		if (targetObject == null)
			throw new IllegalArgumentException("Target object cannot be null");

		_NSMethod method = _methodOnObject(targetObject);
		Class<?> targetClass = targetObject.getClass();
		if (method == null)
			throw new NoSuchMethodException("Class " + targetClass.getName() + " does not implement method "
					+ _NSReflectionUtilities._methodSignature(_name, _types));

		return method;
	}

	public static boolean respondsToSelector(Object object, SEL selector) {
		return selector._methodOnObject(object) != null;
	}

}
