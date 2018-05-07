package com.ccs.app.note.custom.adapter;

import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccs.app.note.R;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

public class NoteListAdapter extends SwitchListAdapter<NoteItem, NoteListAdapter.ViewHolder> {

    private String orderColumn;

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public NoteListAdapter(OnItemClickListener onItemClickListener) {
        super(onItemClickListener, createDiffCallback(NoteItem.class));
    }

    @LayoutRes
    @Override
    protected int getItemListLayoutId(int viewType) {
        return R.layout.item_note_list;
    }

    @LayoutRes
    @Override
    protected int getItemGridLayoutId(int viewType) {
        return R.layout.item_note_grid;
    }

    @NonNull
    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends SwitchListAdapter.ViewHolder<NoteItem, NoteListAdapter> {

        private TextView date;
        private TextView note;

        public ViewHolder(NoteListAdapter adapter, View itemView) {
            super(adapter, itemView);

            date = itemView.findViewById(R.id.date);
            note = itemView.findViewById(R.id.note);
        }

        @Override
        protected void updateItem(@NonNull NoteItem noteItem, int position) {
//            Log.d(Debug.TAG + getClass().getSimpleName(), "noteId " + noteItem.getId());
            date.setText(switchDate(noteItem, adapter.getOrderColumn()));
            note.setText(noteItem.getNote());
            highlightFirstLine();
        }

        @Override
        protected void clearItem(int position) {
            date.setText(null);
            note.setText(null);
        }

        private void highlightFirstLine() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    int start = 0; // note.getLayout().getLineStart(0);
                    int end = note.getLayout().getLineEnd(0);
//                    Log.d(Debug.TAG + getClass().getSimpleName(), "getLineStart " + start);
//                    Log.d(Debug.TAG + getClass().getSimpleName(), "getLineEnd " + end);

                    SpannableString spannableString = new SpannableString(note.getText());
                    spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    note.setText(spannableString);
                }
            });
        }

    }

    private static String switchDate(NoteItem noteItem, String orderColumn) {
        if(Note.DATE_CREATE_COLUMN.equals(orderColumn)) return noteItem.getDateCreateString();
        return noteItem.getDateEditString();
    }

}
