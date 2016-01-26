package com.myappconverter.java.foundations.kvo;

import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.KeyValueChange;

public interface NSObserver {

	public void observeValueForKeyPath(String keyPath, NSObservable targetObject, KeyValueChange changes, Object context);

}