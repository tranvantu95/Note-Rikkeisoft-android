package com.ccs.app.note.adapter.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.View;

public abstract class SwitchListAdapter1<Item, VH extends SwitchListAdapter1.ViewHolder<Item, ?>>
        extends ListAdapter1<Item, VH> {

    public static final int LIST_VIEW = 1;
    public static final int GRID_VIEW = 2;

    public static String getTypeView(int typeView) {
        switch (typeView) {
            case LIST_VIEW:
                return "LIST_VIEW";

            case GRID_VIEW:
                return "GRID_VIEW";

            default:
                return "LIST_VIEW";
        }
    }

    private int typeView;

    public SwitchListAdapter1(OnItemClickListener onItemClickListener) {
        super(onItemClickListener);
    }

    public int getTypeView() {
        return typeView;
    }

    public void setTypeView(int typeView) {
        this.typeView = typeView;
    }

    @LayoutRes
    @Override
    protected int getItemLayoutId(int viewType) {
        switch (getTypeView()) {
            case LIST_VIEW:
                return getItemListLayoutId(viewType);

            case GRID_VIEW:
                return getItemGridLayoutId(viewType);

            default:
                return getItemListLayoutId(viewType);
        }
    }

    @LayoutRes
    protected abstract int getItemListLayoutId(int viewType);

    @LayoutRes
    protected abstract int getItemGridLayoutId(int viewType);

    public static abstract class ViewHolder<Item, RA extends SwitchListAdapter1<Item, ?>>
            extends ListAdapter1.ViewHolder<Item, RA> {

        public ViewHolder(RA adapter, View itemView) {
            super(adapter, itemView);
        }
    }
}
