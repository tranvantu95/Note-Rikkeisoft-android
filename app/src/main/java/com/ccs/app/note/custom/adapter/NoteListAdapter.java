package com.ccs.app.note.custom.adapter;

import android.support.v7.util.DiffUtil;
import android.view.View;

import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.db.entity.Note;

public class NoteListAdapter extends SwitchListAdapter<Note, NoteListAdapter.ViewHolder> {

    public static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    public NoteListAdapter(OnItemClickListener onItemClickListener) {
        super(onItemClickListener, diffCallback);
    }

    @Override
    protected int getItemListLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected int getItemGridLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends SwitchListAdapter.ViewHolder<Note, NoteListAdapter> {
        public ViewHolder(NoteListAdapter adapter, View itemView) {
            super(adapter, itemView);
        }
    }

}
