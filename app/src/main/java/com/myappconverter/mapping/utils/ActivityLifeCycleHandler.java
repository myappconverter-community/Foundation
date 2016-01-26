package com.myappconverter.mapping.utils;

import android.view.MenuItem;
import android.view.MotionEvent;

public interface ActivityLifeCycleHandler {
	public void onPause();

	public void onDestroy();

	public void onStop();

	public void onResume();

	public boolean onTouchEvent(MotionEvent event);
	
	public void onBackPressed();

	public boolean onOptionsItemSelected(MenuItem item);
}
