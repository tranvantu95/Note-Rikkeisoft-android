package com.ccs.app.note.fragment;

import android.support.annotation.LayoutRes;

import com.ccs.app.note.R;
import com.ccs.app.note.controller.base.BaseFragment;

public class NoteEditFragment extends BaseFragment {

    @Override
    protected void addController() {
        setController(NoteEditFragmentController.class);
    }

    @LayoutRes
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_note_edit;
    }

}
