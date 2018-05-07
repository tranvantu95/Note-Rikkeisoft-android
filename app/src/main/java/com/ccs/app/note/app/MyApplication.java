package com.ccs.app.note.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.ccs.app.note.config.AppConfig;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.model.base.SwitchListModel;
import com.ccs.app.note.module.DaggerMyComponent;
import com.ccs.app.note.module.MyComponent;
import com.ccs.app.note.module.MyModule;
import com.ccs.app.note.utils.General;

public class MyApplication extends Application {

    public static final String DATA = AppConfig.APP_DATA;

    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreate");

        // set support vector drawable for api < 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        General.typeView = sharedPreferences.getInt(General.TYPE_VIEW, SwitchListModel.LIST);

        //
        myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }
}
