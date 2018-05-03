package com.ccs.app.note.db.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.ccs.app.note.db.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    public abstract List<Note> getAllNote(String orderColumn);

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    public abstract DataSource.Factory<Integer, Note> getAllNoteDataSource(String orderColumn);

}
