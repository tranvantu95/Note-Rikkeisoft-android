package com.ccs.app.note.model.base;

import android.arch.lifecycle.MutableLiveData;

import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;

public class SwitchListModel<Item> extends ListModel<Item> {

    public static final int LIST = SwitchListAdapter.LIST_VIEW;
    public static final int GRID = SwitchListAdapter.GRID_VIEW;

    public MutableLiveData<Integer> typeView = new MutableLiveData<>();

}
