package com.ccs.app.note.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {

    public static final String DATE_CREATE_COLUMN = "date_create";
    public static final String DATE_EDIT_COLUMN = "date_edit";

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String note;

    @ColumnInfo(name = DATE_CREATE_COLUMN)
    private long dateCreate; // currentTimeMillis

    @ColumnInfo(name = DATE_EDIT_COLUMN)
    private long dateEdit; // currentTimeMillis

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(long dateEdit) {
        this.dateEdit = dateEdit;
    }
}
