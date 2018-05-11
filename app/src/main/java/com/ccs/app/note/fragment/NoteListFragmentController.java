package com.ccs.app.note.fragment;

import android.arch.lifecycle.Observer;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ccs.app.note.R;
import com.ccs.app.note.adapter.NoteListAdapter;
import com.ccs.app.note.app.MyApplication;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.adapter.base.ListAdapter;
import com.ccs.app.note.db.dao.NoteDao;
import com.ccs.app.note.db.entity.Note;
import com.ccs.app.note.controller.base.BaseFragment;
import com.ccs.app.note.controller.SwitchListFragmentController;
import com.ccs.app.note.model.MainModel;
import com.ccs.app.note.model.NoteEditModel;
import com.ccs.app.note.model.NoteListModel;
import com.ccs.app.note.model.item.NoteItem;
import com.ccs.app.note.utils.AppUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteListFragmentController extends SwitchListFragmentController<NoteItem, NoteListModel, NoteListAdapter> {

    private String orderColumn = Note.DATE_EDIT_COLUMN;
    private Menu sortTypeMenu;
    private NoteDao noteDao;

    public NoteListFragmentController(BaseFragment view) {
        super(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(MyApplication.DATA, Context.MODE_PRIVATE);
            orderColumn = preferences.getString("orderColumn", orderColumn);
        }

        observe(getActivityModel(MainModel.class).getNoteDao(), new Observer<NoteDao>() {
            @Override
            public void onChanged(@Nullable NoteDao noteDao) {
                if(noteDao != null) updateNoteDao(noteDao);
            }
        });

        listAdapter.setOrderColumn(orderColumn);
    }

    // test rxJava
    private void loadAllNote() {
        Observable<List<NoteItem>> observable = Observable.create(new ObservableOnSubscribe<List<NoteItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NoteItem>> emitter) {
                try {
                    List<NoteItem> items;
                    if (Note.DATE_CREATE_COLUMN.equals(orderColumn))
                        items = noteDao._getAllByDateCreate();
                    else items = noteDao._getAllByDateEdit();

                    emitter.onNext(items);
                    emitter.onComplete();
                }
                catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<List<NoteItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<NoteItem> items) {
                        updateItems(items);
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    private void updateItems(final List<NoteItem> items) {
        setDataSourceFactory(new DataSource.Factory<Integer, NoteItem>() {
            @Override
            public DataSource<Integer, NoteItem> create() {
                return new PositionalDataSource<NoteItem>() {
                    private int computeCount() {
                        // actual count code here
                        return items.size();
                    }

                    private List<NoteItem> loadRangeInternal(int startPosition, int loadCount) {
                        // actual load code here
                        return items.subList(startPosition, startPosition + loadCount);
                    }

                    @Override
                    public void loadInitial(@NonNull LoadInitialParams params,
                                            @NonNull LoadInitialCallback<NoteItem> callback) {
                        int totalCount = computeCount();
                        int position = computeInitialLoadPosition(params, totalCount);
                        int loadSize = computeInitialLoadSize(params, position, totalCount);
                        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount);
                    }

                    @Override
                    public void loadRange(@NonNull LoadRangeParams params,
                                          @NonNull LoadRangeCallback<NoteItem> callback) {
                        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize));
                    }
                };
            }
        });
        // or
//        updateListAdapter(items);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        listView.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void updateListAdapter(@NonNull List<NoteItem> noteItems) {
        super.updateListAdapter(noteItems);

//        NoteItem currentNote = getActivityModel(NoteEditModel.class).getNote().getValue();
//        // PagedList get item can be null
//        int currentPos = ListUtils.findIndex(noteItems, currentNote);
//        Log.d(Debug.TAG + TAG, "currentPos " + currentPos);

        listView.smoothScrollToPosition(0);
    }

    //
    private void updateNoteDao(@NonNull NoteDao noteDao) {
        Log.d(Debug.TAG + TAG, "updateNoteDao");
        this.noteDao = noteDao;
        updateDataSourceFactory(orderColumn);
//        loadAllNote(); // test rxJava
    }

    private void updateDataSourceFactory(String orderColumn) {
        if(noteDao == null) return;
        Log.d(Debug.TAG + TAG, "updateDataSourceFactory " + orderColumn);
        setDataSourceFactory(noteDao.getAllWithDataSource(orderColumn));
    }

    //
    private void setOrder(String orderColumn) {
        if(this.orderColumn.equals(orderColumn)) return;
        this.orderColumn = orderColumn;
        saveOrder();

        listAdapter.setOrderColumn(orderColumn);

        updateDataSourceFactory(orderColumn);

        setChecked(orderColumn);
    }

    private void saveOrder() {
        if(getActivity() == null) return;
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MyApplication.DATA, Context.MODE_PRIVATE).edit();
        editor.putString("orderColumn", orderColumn);
        editor.apply();
    }

    private void setChecked(String orderColumn) {
        if(sortTypeMenu == null) return;
        Log.d(Debug.TAG + TAG, "setChecked " + orderColumn);
        AppUtils.clearChecked(sortTypeMenu);
        AppUtils.setChecked(sortTypeMenu.findItem(getSortTypeMenuItemId(orderColumn)), true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_note_list, menu);
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

    @NonNull
    @Override
    protected NoteListModel onCreateModel(int modelOwner) {
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
        return new NoteListAdapter(ListAdapter.PAGED_LIST_ADAPTER_MODE, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View itemView, int position) {
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                PagedList<NoteItem> items = listAdapter.getCurrentList();
                if(items == null) return;
                NoteItem item = items.get(position);
                if(item == null) return;
                item.setAdded(true);

                getActivityModel(NoteEditModel.class).getNote().setValue(item);

                AppUtils.addFragment(getFragmentManager(), R.id.fragment_container, NoteEditFragment.class, true);
            }
        });
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager onCreateLinearLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager onCreateGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
    }

    @Dimension
    @Override
    protected int onCreateDividerList() {
        return getResources().getDimensionPixelOffset(R.dimen.divider_list);
    }

    @Dimension
    @Override
    protected int onCreateDividerGrid() {
        return getResources().getDimensionPixelOffset(R.dimen.divider_grid);
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
