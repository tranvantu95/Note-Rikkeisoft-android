package com.ccs.app.note.activity.fragment.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ccs.app.note.activity.fragment.base.ListFragment;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.model.base.SwitchListModel;

public abstract class SwitchListFragment<Item,
        Model extends SwitchListModel<Item>,
        LA extends SwitchListAdapter<Item, ?>>
        extends ListFragment<Item, Model, LA> {

    protected LinearLayoutManager linearLayoutManager;
    protected GridLayoutManager gridLayoutManager;

    protected int dividerList, dividerGrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model.getTypeView().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer != null) setTypeView(integer);
            }
        });
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        linearLayoutManager = onCreateLinearLayoutManager();
        gridLayoutManager = onCreateGridLayoutManager();
        return linearLayoutManager;
    }

    @Override
    protected int onCreateDivider() {
        dividerList = onCreateDividerList();
        dividerGrid = onCreateDividerGrid();
        return dividerList;
    }

    // abstract
    protected abstract LinearLayoutManager onCreateLinearLayoutManager();

    protected abstract GridLayoutManager onCreateGridLayoutManager();

    protected abstract int onCreateDividerList();

    protected abstract int onCreateDividerGrid();

    //
    protected void setTypeView(int typeView) {
        if(listAdapter.getTypeView() == typeView) return;
        Log.d(getClass().getSimpleName(), "setTypeView " + SwitchListAdapter.getTypeView(typeView));

        listAdapter.setTypeView(typeView);
        layoutManager = switchLayoutManager(typeView);
        divider = switchDivider(typeView);

        if(listView != null) updateListView();
    }

    //
    private RecyclerView.LayoutManager switchLayoutManager(int typeView) {
        switch (typeView) {
            case SwitchListAdapter.LIST_VIEW:
                return linearLayoutManager;

            case SwitchListAdapter.GRID_VIEW:
                return gridLayoutManager;

            default:
                return linearLayoutManager;
        }
    }

    private int switchDivider(int typeView) {
        switch (typeView) {
            case SwitchListAdapter.LIST_VIEW:
                return dividerList;

            case SwitchListAdapter.GRID_VIEW:
                return dividerGrid;

            default:
                return dividerList;
        }
    }

}
