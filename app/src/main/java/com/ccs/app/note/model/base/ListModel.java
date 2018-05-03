package com.ccs.app.note.model.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

public class ListModel<Item> extends ViewModel {

    protected LiveData<PagedList<Item>> items;

    public LiveData<PagedList<Item>> getItems(DataSource.Factory<Integer, Item> factory, PagedList.Config config) {
        if(items == null) items = new LivePagedListBuilder<>(factory, config).build();
        return items;
    }

}
