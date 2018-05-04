package com.ccs.app.note.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.model.item.NoteItem;

public class NoteEditModel extends ViewModel {

    private MutableLiveData<NoteDao> noteDao;

    @NonNull
    public MutableLiveData<NoteDao> getNoteDao() {
        if(noteDao == null) noteDao = new MutableLiveData<>();
        return noteDao;
    }

    private MutableLiveData<NoteItem> note;

    public MutableLiveData<NoteItem> getNote() {
        if(note == null) note = new MutableLiveData<>();
        return note;
    }

}
