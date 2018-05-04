package com.ccs.app.note.activity.fragment;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ccs.app.note.R;
import com.ccs.app.note.activity.base.SwitchListActivity;
import com.ccs.app.note.activity.fragment.base.SwitchListFragment;
import com.ccs.app.note.app.MyApplication;
import com.ccs.app.note.app.base.BaseApplication;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.custom.adapter.NoteListAdapter;
import com.ccs.app.note.custom.adapter.base.ListAdapter;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.model.item.NoteItem;
import com.ccs.app.note.utils.AppUtils;

public class NoteListFragment extends SwitchListFragment<NoteItem, NoteListModel, NoteListAdapter> {

    private String orderColumn = Note.DATE_EDIT_COLUMN;
    private Menu sortTypeMenu;
    private NoteDao noteDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(MyApplication.DATA, Context.MODE_PRIVATE);
            orderColumn = preferences.getString("orderColumn", orderColumn);
        }

        model.getNoteDao().observe(this, new Observer<NoteDao>() {
            @Override
            public void onChanged(@Nullable NoteDao noteDao) {
                if(noteDao != null) updateNoteDao(noteDao);
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView.setVerticalScrollBarEnabled(true);
    }

    //
    private void updateNoteDao(@NonNull NoteDao noteDao) {
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateNoteDao");
        this.noteDao = noteDao;
        updateDataSourceFactory(orderColumn);
    }

    private void updateDataSourceFactory(String orderColumn) {
        if(noteDao == null) return;
        Log.d(Debug.TAG + getClass().getSimpleName(), "updateDataSourceFactory " + orderColumn);
        setDataSourceFactory(noteDao.getAllWithDataSource(orderColumn));
    }

    //
    private void setOrder(String orderColumn) {
        if(this.orderColumn.equals(orderColumn)) return;
        this.orderColumn = orderColumn;
        saveOrder();

        updateDataSourceFactory(orderColumn);

        setChecked(orderColumn);
    }

    private void saveOrder() {
        if(getActivity() == null) return;
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(BaseApplication.DATA, Context.MODE_PRIVATE).edit();
        editor.putString("orderColumn", orderColumn);
        editor.apply();
    }

    private void setChecked(String orderColumn) {
        if(sortTypeMenu == null) return;
        Log.d(Debug.TAG + getClass().getSimpleName(), "setChecked " + orderColumn);
        SwitchListActivity.clearChecked(sortTypeMenu);
        SwitchListActivity.setChecked(sortTypeMenu.findItem(getSortTypeMenuItemId(orderColumn)), true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem sortTypeMenuItem = menu.findItem(R.id.sort_type);

        if(sortTypeMenuItem != null) {
            Menu sortTypeMenu = sortTypeMenuItem.getSubMenu();
            if(this.sortTypeMenu != sortTypeMenu) {
                this.sortTypeMenu = sortTypeMenu;
                setChecked(orderColumn);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        switch (id) {
            case R.id.sort_by_date_edit:
            case R.id.sort_by_date_create:
                if(item.isChecked()) return true;
                setOrder(getOrderColumn(id));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_list;
    }

    @NonNull
    @Override
    protected NoteListModel onCreateModel() {
        return getModel(modelOwner, NoteListModel.class);
    }

    @NonNull
    @Override
    protected PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder().setPageSize(1).build();
    }

    @NonNull
    @Override
    protected NoteListAdapter onCreateListAdapter() {
        return new NoteListAdapter(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View itemView, int position) {
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                PagedList<NoteItem> items = listAdapter.getCurrentList();
                if(items == null) return;
                NoteItem item = items.get(position);
                if(item == null) return;
                item.setAdded(true);
                NoteEditModel noteEditModel = getActivityModel(NoteEditModel.class);
                noteEditModel.getNoteDao().setValue(noteDao);
                noteEditModel.getNote().setValue(item);
                AppUtils.addFragment(getFragmentManager(), R.id.fragment_container, NoteEditFragment.class, true);
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

    //
    private String getOrderColumn(int menuItemId) {
        switch (menuItemId) {
            case R.id.sort_by_date_create:
                return Note.DATE_CREATE_COLUMN;

            case R.id.sort_by_date_edit:
                return Note.DATE_EDIT_COLUMN;

            default:
                return Note.DATE_EDIT_COLUMN;
        }
    }

    private int getSortTypeMenuItemId(String orderColumn) {
        switch (orderColumn) {
            case Note.DATE_CREATE_COLUMN:
                return R.id.sort_by_date_create;

            case Note.DATE_EDIT_COLUMN:
                return R.id.sort_by_date_edit;

            default:
                return R.id.sort_by_date_edit;
        }
    }

}
