package com.ccs.app.note.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.base.SwitchListActivity;
import com.ccs.app.note.activity.fragment.NoteEditFragment;
import com.ccs.app.note.activity.fragment.NoteListFragment;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.model.item.NoteItem;
import com.ccs.app.note.utils.AppUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends SwitchListActivity {

    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container, NoteListFragment.class, false);

        noteDao = AppDatabase.getInstance(this).getNoteDao();

        getModel(NoteListModel.class).getNoteDao().setValue(noteDao);

        final Handler handler = new Handler();

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                int count = noteDao.getCount();
//                f(handler, executorService, count);
//            }
//        });

        findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteItem noteItem = new NoteItem();
                NoteEditModel noteEditModel = getModel(NoteEditModel.class);
                noteEditModel.getNoteDao().setValue(noteDao);
                noteEditModel.getNote().setValue(noteItem);
                AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container, NoteEditFragment.class, true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
