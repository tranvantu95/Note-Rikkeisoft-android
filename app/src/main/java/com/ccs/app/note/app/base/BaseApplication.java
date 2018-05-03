package com.ccs.app.note.app.base;

import android.app.Application;
import android.util.Log;

public class BaseApplication extends Application {

    public static final String DATA = "app_data";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(getClass().getSimpleName(), "onCreate");
    }

}
