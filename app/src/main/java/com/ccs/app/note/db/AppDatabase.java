package com.ccs.app.note.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ccs.app.note.config.AppConfig;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.item.NoteItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = AppConfig.APP_DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final String DATABASE_NAME = AppConfig.APP_DATABASE;

    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
    }

    public abstract NoteDao getNoteDao();
}
