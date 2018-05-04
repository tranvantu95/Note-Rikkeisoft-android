package com.ccs.app.note.activity.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.ccs.app.note.config.Debug;
import com.ccs.app.note.utils.ViewModelUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onSaveInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    // Model
    protected <Model extends ViewModel> Model getModel(Class<Model> clazz) {
        return ViewModelProviders.of(this).get(clazz);
    }

    protected <Model extends ViewModel> Model getAppModel(Class<Model> clazz) {
        return ViewModelUtils.ofApp(getApplication()).get(clazz);
    }

}
