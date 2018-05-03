package com.ccs.app.note.model.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class ListModel<Item> extends ViewModel {

    public MutableLiveData<List<Item>> items = new MutableLiveData<>();

}
