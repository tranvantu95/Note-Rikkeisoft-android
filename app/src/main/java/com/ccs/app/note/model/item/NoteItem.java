package com.ccs.app.note.model.item;

import android.arch.persistence.room.Ignore;

import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.base.BaseItem;

public class NoteItem extends Note implements BaseItem {

    @Ignore
    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

}
