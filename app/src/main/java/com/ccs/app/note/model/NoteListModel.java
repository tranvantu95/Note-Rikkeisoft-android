package com.ccs.app.note.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.base.SwitchListModel;
import com.ccs.app.note.model.item.NoteItem;

public class NoteListModel extends SwitchListModel<NoteItem> {

    private MutableLiveData<NoteDao> noteDao;

    @NonNull
    public MutableLiveData<NoteDao> getNoteDao() {
        if(noteDao == null) noteDao = new MutableLiveData<>();
        return noteDao;
    }

}
