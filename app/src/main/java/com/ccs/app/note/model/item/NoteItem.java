package com.ccs.app.note.model.item;

import android.arch.persistence.room.Ignore;

import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.utils.Format;

public class NoteItem extends Note implements BaseItem {

    @Ignore
    private boolean isAdded;

    @Ignore
    private String dateCreateString;

    @Ignore
    private String dateEditString;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getDateCreateString() {
        return dateCreateString;
    }

    public void setDateCreateString(String dateCreateString) {
        this.dateCreateString = dateCreateString;
    }

    public String getDateEditString() {
        return dateEditString;
    }

    public void setDateEditString(String dateEditString) {
        this.dateEditString = dateEditString;
    }

    @Override
    public void setDateCreate(long dateCreate) {
        super.setDateCreate(dateCreate);
        setDateCreateString(Format.formatDate(getDateCreate()));
    }

    @Override
    public void setDateEdit(long dateEdit) {
        super.setDateEdit(dateEdit);
        setDateEditString(Format.formatDate(getDateEdit()));
    }
}
