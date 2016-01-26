package com.myappconverter.java.foundations.utilities;

import java.lang.reflect.Method;

public interface IAdressable<T> {

	public boolean perform(Method method, int index);

	public boolean perform(Object receiver, Method method, int index);

	public T getResult();
}
