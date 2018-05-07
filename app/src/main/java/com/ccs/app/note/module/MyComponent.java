package com.ccs.app.note.module;

import com.ccs.app.note.activity.MainActivity;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.dao.NoteDao;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyModule.class})
public interface MyComponent {

    MyModel getMyModel();

    AppDatabase getAppDatabase();

    NoteDao getNoteDao();

    void inject(MainActivity mainActivity);

//    @Component.Builder
//    public interface Builder {
//        MyComponent build();
//        Builder myModule(MyModule appModule);
//    }

}
