package com.ccs.app.note.app.base;

import android.content.SharedPreferences;

import com.ccs.app.note.model.base.SwitchListModel;

public class SwitchListApplication extends BaseApplication {

    public static final String TYPE_VIEW = "type_view";
    public static int typeView;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        typeView = sharedPreferences.getInt(TYPE_VIEW, SwitchListModel.LIST);
    }

}
