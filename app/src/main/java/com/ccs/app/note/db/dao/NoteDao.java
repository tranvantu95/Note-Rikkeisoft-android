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
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.ccs.app.note.config.Debug;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Dao
public abstract class NoteDao {

    private static final ExecutorService executor = AppDatabase.executor;

    @MainThread
    @Query("SELECT * FROM note ORDER BY date_create DESC")
    public abstract DataSource.Factory<Integer, NoteItem> getAllByDateCreateWithDataSource();

    @MainThread
    @Query("SELECT * FROM note ORDER BY date_edit DESC")
    public abstract DataSource.Factory<Integer, NoteItem> getAllByDateEditWithDataSource();

    @WorkerThread
    @Query("SELECT * FROM note")
    public abstract List<NoteItem> _getAll();

    @WorkerThread
    @Query("SELECT * FROM note ORDER BY date_create DESC")
    public abstract List<NoteItem> _getAllByDateCreate();

    @WorkerThread
    @Query("SELECT * FROM note")
    public abstract List<NoteItem> _getAllByDateEdit();

    @WorkerThread
    @Query("SELECT COUNT(*) FROM note ORDER BY date_edit DESC")
    public abstract int _getCount();

    @WorkerThread
    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] _insertAll(Note... notes);

    @WorkerThread
    @Update
    public abstract void _updateAll(Note... notes);

    @WorkerThread
    @Delete
    public abstract void _deleteAll(Note... notes);

    //
    @MainThread
    public DataSource.Factory<Integer, NoteItem> getAllWithDataSource(String orderColumn) {
        if(Note.DATE_CREATE_COLUMN.equals(orderColumn)) return getAllByDateCreateWithDataSource();
        return getAllByDateEditWithDataSource();
    }

    //
    @MainThread
    public LiveData<List<NoteItem>> getAll() {
        final MutableLiveData<List<NoteItem>> notes = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notes.postValue(_getAll());
            }
        });
        return notes;
    }

    @MainThread
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
    @MainThread
    public LiveData<List<Long>> insertAll(final Note... notes) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "insertAll");
        final MutableLiveData<List<Long>> ids = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Long[] _ids = _insertAll(notes);
                int i = 0;
                for(long id : _ids) {
                    notes[i].setId(id);
                    i++;
                }
                ids.postValue(Arrays.asList(_ids));
            }
        });
        return ids;
    }

    @MainThread
    public void updateAll(final Note... notes) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateAll");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                _updateAll(notes);
            }
        });
    }

    @MainThread
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
