package com.ccs.app.note.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.Log;

import com.ccs.app.note.config.Debug;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Dao
public abstract class NoteDao {

    private static final ExecutorService executor = AppDatabase.executor;

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    public abstract DataSource.Factory<Integer, NoteItem> getAllWithDataSource(String orderColumn);

    @Query("SELECT * FROM note ORDER BY :orderColumn DESC")
    public abstract List<NoteItem> _getAll(String orderColumn);

    @Query("SELECT COUNT(*) FROM note")
    public abstract int _getCount();

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> _insertAll(Note... notes);

    @Update
    public abstract void _updateAll(Note... notes);

    @Delete
    public abstract void _deleteAll(Note... notes);

    //
    public LiveData<List<NoteItem>> getAll(final String orderColumn) {
        final MutableLiveData<List<NoteItem>> notes = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notes.postValue(_getAll(orderColumn));
            }
        });
        return notes;
    }

    public LiveData<Integer> getCount() {
        final MutableLiveData<Integer> count = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                count.postValue(_getCount());
            }
        });
        return count;
    }

    //
    public LiveData<List<Long>> insertAll(final Note... notes) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "insertAll");
        final MutableLiveData<List<Long>> ids = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Long> _ids = _insertAll(notes);
                int i = 0;
                for(Note note : notes) {
                    note.setId(_ids.get(i));
                    i++;
                }
                ids.postValue(_ids);
            }
        });
        return ids;
    }

    public void updateAll(final Note... notes) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateAll");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                _updateAll(notes);
            }
        });
    }

    public void deleteAll(final Note... notes) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "deleteAll");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                _deleteAll(notes);
            }
        });
    }
}
