package com.ccs.app.note.activity.base;

import android.arch.lifecycle.ViewModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.ccs.app.note.config.Debug;
import com.ccs.app.note.utils.ModelUtils;

public class BaseActivity extends AppCompatActivity {
    
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Debug.TAG + TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Debug.TAG + TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Debug.TAG + TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Debug.TAG + TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Debug.TAG + TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Debug.TAG + TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Debug.TAG + TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(Debug.TAG + TAG, "onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(Debug.TAG + TAG, "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    // Model
    protected <Model extends ViewModel> Model getModel(Class<Model> clazz) {
        return ModelUtils.of(this).get(clazz);
    }

    protected <Model extends ViewModel> Model getAppModel(Class<Model> clazz) {
        return ModelUtils.ofApp(getApplication()).get(clazz);
    }

}
