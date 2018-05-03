package com.ccs.app.note.model;

import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.base.SwitchListModel;

public class NoteListModel extends SwitchListModel<Note> {

    private NoteDao noteDao;

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}
