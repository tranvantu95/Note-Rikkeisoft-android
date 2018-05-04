package com.ccs.app.note.activity.base;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ccs.app.note.R;
import com.ccs.app.note.app.base.SwitchListApplication;
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.model.base.SwitchListModel;

public abstract class SwitchListActivity extends AppbarActivity {

    private Menu typeViewMenu;

    private int typeView;

    @Override
    protected void onStart() {
        super.onStart();

        setTypeView(SwitchListApplication.typeView);
    }

    private void setTypeView(int typeView) {
        if(this.typeView == typeView) return;
        this.typeView = typeView;
        
        if(SwitchListApplication.typeView != typeView) {
            SwitchListApplication.typeView = typeView;
            saveTypeView();
        }

        Log.d(Debug.TAG + getClass().getSimpleName(), "onChangeTypeView " + SwitchListAdapter.getTypeView(typeView));
        onChangeTypeView(typeView);

        setChecked(typeView);
    }

    private void saveTypeView() {
        SharedPreferences.Editor editor = getSharedPreferences(SwitchListApplication.DATA, MODE_PRIVATE).edit();
        editor.putInt(SwitchListApplication.TYPE_VIEW, SwitchListApplication.typeView);
        editor.apply();
    }

    protected abstract void onChangeTypeView(int typeView);

    private void setChecked(int typeView) {
        if(typeViewMenu == null) return;
        Log.d(Debug.TAG + getClass().getSimpleName(), "setChecked " + SwitchListAdapter.getTypeView(typeView));
        clearChecked(typeViewMenu);
        setChecked(typeViewMenu.findItem(getTypeViewMenuItemId(typeView)), true);
    }

    public static void clearChecked(@NonNull Menu menu) {
        for(int i = menu.size() - 1; i >= 0; i--) {
            setChecked(menu.getItem(i), false);
        }
    }

    public static void setChecked(@Nullable MenuItem item, boolean isChecked) {
        if(item == null) return;
        item.setChecked(isChecked);

        SpannableString spannable = new SpannableString(item.getTitle());
        spannable.setSpan(new ForegroundColorSpan(isChecked ? Color.BLUE : Color.BLACK),
                0, spannable.length(), 0);
        item.setTitle(spannable);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        boolean b = super.onPrepareOptionsMenu(menu);
        
        MenuItem typeViewMenuItem = menu.findItem(R.id.type_view);
        
        if(typeViewMenuItem != null) {
            Menu typeViewMenu = typeViewMenuItem.getSubMenu();
            if(this.typeViewMenu != typeViewMenu) {
                this.typeViewMenu = typeViewMenu;
                setChecked(typeView);
            }
        }

        return b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.type_list:
            case R.id.type_grid: {
                if(item.isChecked()) return true;
                setTypeView(getTypeView(id));
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getTypeView(int menuItemId) {
        switch (menuItemId) {
            case R.id.type_list:
                return SwitchListModel.LIST;

            case R.id.type_grid:
                return SwitchListModel.GRID;

            default:
                return SwitchListModel.LIST;
        }
    }

    private int getTypeViewMenuItemId(int typeView) {
        switch (typeView) {
            case SwitchListModel.LIST:
                return R.id.type_list;

            case SwitchListModel.GRID:
                return R.id.type_grid;

            default:
                return R.id.type_list;
        }
    }

}
