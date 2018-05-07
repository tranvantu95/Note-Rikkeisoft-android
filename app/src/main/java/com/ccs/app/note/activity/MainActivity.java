package com.ccs.app.note.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.base.SwitchListActivity;
import com.ccs.app.note.activity.fragment.NoteEditFragment;
import com.ccs.app.note.activity.fragment.NoteListFragment;
import com.ccs.app.note.app.MyApplication;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.db.AppDatabase;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.MainModel;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.model.item.NoteItem;
import com.ccs.app.note.module.MyModel;
import com.ccs.app.note.utils.AppUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MainActivity extends SwitchListActivity {

    @Inject
    public NoteDao noteDao;

    @Inject
    public MyModel myModel;

    @Inject
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        ((MyApplication) getApplication()).getMyComponent().inject(this);
        Log.d(Debug.TAG + getClass().getSimpleName(), "" + myModel.toString());

        init();

//        noteDao = AppDatabase.getInstance(this).getNoteDao();
//        noteDao = appDatabase.getNoteDao();

        getModel(MainModel.class).getNoteDao().setValue(noteDao);

        findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteItem noteItem = new NoteItem();
                noteItem.setDateCreate(System.currentTimeMillis());

                getModel(NoteEditModel.class).getNote().setValue(noteItem);

                AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container, NoteEditFragment.class, true);
            }
        });

        AppUtils.addFragment(getSupportFragmentManager(), R.id.fragment_container, NoteListFragment.class, false);

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
