package com.myappconverter.java.foundations.utilities;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

import com.myappconverter.java.foundations.NSUbiquitousKeyValueStore;

// The name of this class (NSUbiquitousKeyValueStoreHelper ) should specified in the backupAgent tag under application
// in AndroidManifest.XML
// this will tell the app that we use cloud backup.
public class NSUbiquitousKeyValueStoreHelper extends BackupAgentHelper {

	@Override
	public void onCreate() {
		super.onCreate();
		// A Helper for our Preferences, this name is the same name we use when saving SharedPreferences
		SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, NSUbiquitousKeyValueStore.PREFERENCES);
		addHelper(NSUbiquitousKeyValueStore.HELPER_KEY, helper);
	}
}