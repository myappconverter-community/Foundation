package com.myappconverter.mapping.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GenericMainContext {
    public static AppCompatActivity sharedContext;
    public static Bundle bundle;
    public static boolean isSpritekit = false;

    public static Context getContext() {
        return sharedContext;
    }

    public static void setContext(Context ctx) {
        sharedContext = (AppCompatActivity) ctx;
    }

    public static void setContext(AppCompatActivity ctx) {
        sharedContext = ctx;
    }

}
