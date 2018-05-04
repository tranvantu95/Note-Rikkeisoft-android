package com.ccs.app.note.activity.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.fragment.base.BaseFragment;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.item.NoteItem;

public class NoteEditFragment extends BaseFragment<NoteEditModel> {

    private EditText editText;
    private NoteDao noteDao;
    private NoteItem note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model.getNoteDao().observe(this, new Observer<NoteDao>() {
            @Override
            public void onChanged(@Nullable NoteDao noteDao) {
                if(noteDao != null) NoteEditFragment.this.noteDao = noteDao;
            }
        });

        observeNote();
    }

    private void observeNote() {
        model.getNote().observe(this, new Observer<NoteItem>() {
            @Override
            public void onChanged(@Nullable NoteItem noteItem) {
                if(noteItem != null) updateNote(noteItem);
            }
        });
    }

    private void updateNote(@NonNull NoteItem noteItem) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateNote");
        note = noteItem;

        editText.setText(noteItem.getNote());
    }

    private void saveNote() {
        if(noteDao == null) return;

        if(TextUtils.isEmpty(editText.getText())) {
            if(note.isAdded()) {
                note.setAdded(false);
                noteDao.deleteAll(note);
            }
            return;
        }

        if(TextUtils.equals(note.getNote(), editText.getText())) return;

        note.setNote(editText.getText().toString());

        if(note.isAdded()) noteDao.updateAll(note);
        else {
            note.setAdded(true);
            noteDao.insertAll(note);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.edit_text);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveNote();
    }

    @LayoutRes
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_edit_note;
    }

    @NonNull
    @Override
    protected NoteEditModel onCreateModel() {
        return getModel(modelOwner, NoteEditModel.class);
    }

}
