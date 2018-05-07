package com.ccs.app.note.module;

import android.content.Context;

import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.dao.NoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

    private Context context;

    public MyModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public MyModel getMyModel() {
        return new MyModel();
    }

    @Provides
    @Singleton
    public AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public NoteDao getNoteDao() {
        return getAppDatabase().getNoteDao();
    }

}
