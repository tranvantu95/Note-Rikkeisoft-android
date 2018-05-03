package com.ccs.app.note.activity.fragment;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.ccs.app.note.activity.fragment.base.SwitchListFragment;
import com.ccs.app.note.custom.adapter.NoteListAdapter;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.NoteListModel;

public class NoteListFragment extends SwitchListFragment<Note, NoteListModel, NoteListAdapter> {

    @Override
    protected int getFragmentLayoutId() {
        return 0;
    }

    @Override
    protected NoteListModel onCreateModel() {
        int owner = ACTIVITY_MODEL;
        if(getArguments() != null) owner = getArguments().getInt(MODEL_OWNER, owner);
        return getModel(owner, NoteListModel.class);
    }

    @Override
    protected DataSource.Factory<Integer, Note> getDataSourceFactory() {
        return model.getNoteDao().getAllNoteDataSource("date_edit");
    }

    @Override
    protected PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder().setPageSize(20).build();
    }

    @Override
    protected NoteListAdapter onCreateListAdapter() {
        return null;
    }

    @Override
    protected LinearLayoutManager onCreateLinearLayoutManager() {
        return null;
    }

    @Override
    protected GridLayoutManager onCreateGridLayoutManager() {
        return null;
    }

    @Override
    protected int onCreateDividerList() {
        return 0;
    }

    @Override
    protected int onCreateDividerGrid() {
        return 0;
    }

}
