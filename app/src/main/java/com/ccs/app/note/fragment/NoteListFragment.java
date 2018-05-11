package com.ccs.app.note.fragment;

import android.support.annotation.LayoutRes;

import com.ccs.app.note.R;
import com.ccs.app.note.controller.base.BaseFragment;

public class NoteListFragment extends BaseFragment {

    @Override
    protected void addController() {
        setController(NoteListFragmentController.class);
    }

    @LayoutRes
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_note_list;
    }

}
