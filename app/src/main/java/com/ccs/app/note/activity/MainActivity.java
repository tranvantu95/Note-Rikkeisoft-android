package com.ccs.app.note.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.base.SwitchListActivity;
import com.ccs.app.note.activity.fragment.NoteListFragment;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.utils.AppUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends SwitchListActivity {

    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container, NoteListFragment.class);

        noteDao = AppDatabase.getInstance(this).getNoteDao();

        getModel(NoteListModel.class).getNoteDao().setValue(noteDao);

        final Handler handler = new Handler();

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int count = noteDao.getCount();
                f(handler, executorService, count);
            }
        });

    }

    private void f(final Handler handler, final ExecutorService executorService, final int count) {
        handler.postDelayed(new Runnable() {
            int i = count;

            @Override
            public void run() {
                i++;

                final Note note = new Note();
                note.setNote("note" + i);

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        noteDao.insertAll(note);
                    }
                });

                if(i - count < 10) handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void onChangeTypeView(int typeView) {
        getModel(NoteListModel.class).getTypeView().setValue(typeView);
    }
}
