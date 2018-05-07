package com.ccs.app.note.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.model.item.NoteItem;

public class NoteEditModel extends ViewModel {

    private MutableLiveData<NoteItem> note;

    public MutableLiveData<NoteItem> getNote() {
        if(note == null) note = new MutableLiveData<>();
        return note;
    }

}
