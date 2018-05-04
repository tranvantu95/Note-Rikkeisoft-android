package com.ccs.app.note.db.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    List<NoteItem> getAll(String orderColumn);

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    DataSource.Factory<Integer, NoteItem> getAllWithDataSource(String orderColumn);

    @Query("SELECT COUNT(*) FROM note")
    int getCount();

    @Insert
    void insertAll(Note... notes);

}
