package com.ccs.app.note.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.ccs.app.note.R;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.controller.base.BaseFragment;
import com.ccs.app.note.controller.base.FragmentController;
import com.ccs.app.note.model.MainModel;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.item.NoteItem;

public class NoteEditFragmentController extends FragmentController<NoteEditModel> implements View.OnClickListener {

    private EditText editText;
    private NoteDao noteDao;
    private NoteItem note;

    public NoteEditFragmentController(BaseFragment view) {
        super(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragment().setRetainInstance(true); // saved Instance State when app destroy by system --> leak memory

        setHasOptionsMenu(true);

        observe(getActivityModel(MainModel.class).getNoteDao(), new Observer<NoteDao>() {
            @Override
            public void onChanged(@Nullable NoteDao noteDao) {
                if(noteDao != null) NoteEditFragmentController.this.noteDao = noteDao;
            }
        });

        observeNote();

    }

    private void observeNote() {
        observe(model.getNote(), new Observer<NoteItem>() {
            @Override
            public void onChanged(@Nullable NoteItem noteItem) {
                if(noteItem != null) updateNote(noteItem);
            }
        });
    }

    private void updateNote(@NonNull NoteItem noteItem) {
        Log.d(Debug.TAG + TAG, "updateNote");
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
        note.setDateEdit(System.currentTimeMillis());

        if(!note.isAdded()) {
            note.setAdded(true);
            noteDao.insertAll(note);
        }
        else {
            noteDao.updateAll(note);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(this);

        editText = view.findViewById(R.id.edit_text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null)
            getActivity().findViewById(R.id.btn_plus).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStop() {
        super.onStop();
        saveNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(getActivity() != null)
            getActivity().findViewById(R.id.btn_plus).setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @NonNull
    @Override
    protected NoteEditModel onCreateModel(int modelOwner) {
        return getModel(modelOwner, NoteEditModel.class);
    }

}
