package com.ccs.app.note.activity.fragment;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.fragment.base.SwitchListFragment;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.custom.adapter.NoteListAdapter;
import com.ccs.app.note.custom.adapter.base.ListAdapter;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.model.item.NoteItem;

public class NoteListFragment extends SwitchListFragment<NoteItem, NoteListModel, NoteListAdapter> {

    private NoteDao noteDao;
    private String orderColumn = "date_edit";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model.getNoteDao().observe(this, new Observer<NoteDao>() {
            @Override
            public void onChanged(@Nullable NoteDao noteDao) {
                if(noteDao != null) updateNoteDao(noteDao);
            }
        });
    }

    private void updateNoteDao(@NonNull NoteDao noteDao) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateNoteDao");
        this.noteDao = noteDao;
        updateDataSourceFactory(orderColumn);
    }

    private void updateDataSourceFactory(String orderColumn) {
//        if(this.orderColumn.equals(orderColumn)) return;
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateDataSourceFactory " + orderColumn);
        this.orderColumn = orderColumn;
        setDataSourceFactory(noteDao.getAllWithDataSource(orderColumn));
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_list;
    }

    @NonNull
    @Override
    protected NoteListModel onCreateModel() {
        int owner = ACTIVITY_MODEL;
        if(getArguments() != null) owner = getArguments().getInt(MODEL_OWNER, owner);
        return getModel(owner, NoteListModel.class);
    }

    @NonNull
    @Override
    protected PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder().setPageSize(20).build();
    }

    @NonNull
    @Override
    protected NoteListAdapter onCreateListAdapter() {
        return new NoteListAdapter(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View itemView, int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    protected LinearLayoutManager onCreateLinearLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected GridLayoutManager onCreateGridLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Dimension
    @Override
    protected int onCreateDividerList() {
        return 0;
    }

    @Dimension
    @Override
    protected int onCreateDividerGrid() {
        return 0;
    }

}
