package com.myappconverter.mapping.utils;

import android.view.View;

public class AndroidViewHelper {

	public static View ViewById(int viewID) {
		return GenericMainContext.sharedContext.findViewById(viewID);
	}
}
