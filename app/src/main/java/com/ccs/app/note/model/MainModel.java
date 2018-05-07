package com.ccs.app.note.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.ccs.app.note.db.dao.NoteDao;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MainModel extends ViewModel {

    private MutableLiveData<NoteDao> noteDao;

    @NonNull
    public MutableLiveData<NoteDao> getNoteDao() {
        if(noteDao == null) noteDao = new MutableLiveData<>();
        return noteDao;
    }

}
