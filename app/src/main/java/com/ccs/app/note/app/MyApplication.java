package com.ccs.app.note.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.ccs.app.note.config.AppConfig;
import com.ccs.app.note.config.Debug;

public class MyApplication extends Application {

    public static final String DATA = AppConfig.APP_DATA;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreate");

        // set support vector drawable for api < 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
