package com.ccs.app.note.model.base;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

public class ListModel<Item> extends ViewModel {

    protected MutableLiveData<DataSource.Factory<?, Item>> dataSourceFactory;

    protected LiveData<PagedList<Item>> items;

    @NonNull
    public MutableLiveData<DataSource.Factory<?, Item>> getDataSourceFactory() {
        if(dataSourceFactory == null) dataSourceFactory = new MutableLiveData<>();
        return dataSourceFactory;
    }

    @NonNull
    public LiveData<PagedList<Item>> getItems(@NonNull final PagedList.Config config) {
        if(items == null)
            items = Transformations.switchMap(getDataSourceFactory(),
                new Function<DataSource.Factory<?, Item>, LiveData<PagedList<Item>>>() {
                    @Override
                    public LiveData<PagedList<Item>> apply(DataSource.Factory<?, Item> input) {
                        if(input != null) return new LivePagedListBuilder<>(input, config).build();
                        return null;
                    }
                });

        return items;
    }

}
