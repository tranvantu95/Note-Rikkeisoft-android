package com.ccs.app.note.model.base;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ccs.app.note.adapter.base.SwitchListAdapter;

public class SwitchListModel<Item> extends ListModel<Item> {

    public static final int LIST = SwitchListAdapter.LIST_VIEW;
    public static final int GRID = SwitchListAdapter.GRID_VIEW;

    protected MutableLiveData<Integer> typeView = new MutableLiveData<>();

    @NonNull
    public MutableLiveData<Integer> getTypeView() {
        if(typeView == null) typeView = new MutableLiveData<>();
        return typeView;
    }

}
