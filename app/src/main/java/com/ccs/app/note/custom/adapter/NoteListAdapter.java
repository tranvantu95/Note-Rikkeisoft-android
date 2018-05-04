package com.ccs.app.note.custom.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.TextView;

import com.ccs.app.note.R;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

public class NoteListAdapter extends SwitchListAdapter<NoteItem, NoteListAdapter.ViewHolder> {

    public NoteListAdapter(OnItemClickListener onItemClickListener) {
        super(onItemClickListener, createDiffCallback(NoteItem.class));
    }

    @LayoutRes
    @Override
    protected int getItemListLayoutId(int viewType) {
        return R.layout.item_list;
    }

    @LayoutRes
    @Override
    protected int getItemGridLayoutId(int viewType) {
        return R.layout.item_grid;
    }

    @NonNull
    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends SwitchListAdapter.ViewHolder<NoteItem, NoteListAdapter> {

        private TextView textView;

        public ViewHolder(NoteListAdapter adapter, View itemView) {
            super(adapter, itemView);

            textView = itemView.findViewById(R.id.text_view);
        }

        @Override
        protected void updateItem(@NonNull NoteItem noteItem, int position) {
            textView.setText(noteItem.getNote());
        }

        @Override
        protected void clearItem(int position) {
            textView.setText(null);
        }
    }

}
