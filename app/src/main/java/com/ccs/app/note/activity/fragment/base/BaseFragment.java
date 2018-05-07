package com.ccs.app.note.activity.fragment.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccs.app.note.config.Debug;
import com.ccs.app.note.utils.ModelUtils;

public abstract class BaseFragment<Model extends ViewModel> extends Fragment {

    // Model Owner
    public static final String MODEL_OWNER = "model_owner";

    public static final int APP_MODEL = 1;
    public static final int ACTIVITY_MODEL = 2;
    public static final int PARENT_FRAGMENT_MODEL = 3;

    protected int modelOwner = ACTIVITY_MODEL;

    protected Model model;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onAttach Context");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onAttach Activity");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreate");

        modelOwner = getModelOwner();
        model = onCreateModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreateView");
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(Debug.TAG + getClass().getSimpleName(), "onDetach");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onCreateOptionsMenu");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.d(Debug.TAG + getClass().getSimpleName(), "onPrepareOptionsMenu");
    }

    // abstract
    @LayoutRes
    protected abstract int getFragmentLayoutId();

    @NonNull
    protected abstract Model onCreateModel();

    //
    protected int getModelOwner() {
        if(getArguments() != null) return getArguments().getInt(MODEL_OWNER, modelOwner);
        return modelOwner;
    }

    // Model
    protected <Model extends ViewModel> Model getAppModel(Class<Model> clazz) {
        return ModelUtils.ofApp(getActivity().getApplication()).get(clazz);
    }

    protected <Model extends ViewModel> Model getActivityModel(Class<Model> clazz) {
        return ModelUtils.of(getActivity()).get(clazz);
    }

    protected <Model extends ViewModel> Model getParentFragmentModel(Class<Model> clazz) {
        return ModelUtils.of(getParentFragment()).get(clazz);
    }

    protected <Model extends ViewModel> Model getFragmentModel(Class<Model> clazz) {
        return ModelUtils.of(this).get(clazz);
    }

    protected <Model extends ViewModel> Model getModel(int owner, Class<Model> clazz) {
        switch (owner) {
            case APP_MODEL:
                return getAppModel(clazz);

            case ACTIVITY_MODEL:
                return getActivityModel(clazz);

            case PARENT_FRAGMENT_MODEL:
                return getParentFragmentModel(clazz);

            default:
                return getFragmentModel(clazz);
        }
    }
}
