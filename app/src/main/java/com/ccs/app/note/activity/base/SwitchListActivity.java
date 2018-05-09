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
import com.ccs.app.note.config.Debug;
import com.ccs.app.note.custom.adapter.base.SwitchListAdapter;
import com.ccs.app.note.model.base.SwitchListModel;
import com.ccs.app.note.utils.AppUtils;
import com.ccs.app.note.utils.General;

public abstract class SwitchListActivity extends AppbarActivity {

    private Menu typeViewMenu;

    private int typeView;

    @Override
    protected void onStart() {
        super.onStart();

        setTypeView(General.typeView);
    }

    private void setTypeView(int typeView) {
        if(this.typeView == typeView) return;
        this.typeView = typeView;
        
        if(General.typeView != typeView) {
            General.typeView = typeView;
            saveTypeView();
        }

        Log.d(Debug.TAG + TAG, "onChangeTypeView " + SwitchListAdapter.getTypeView(typeView));
        onChangeTypeView(typeView);

        setChecked(typeView);
    }

    private void saveTypeView() {
        SharedPreferences.Editor editor = getSharedPreferences(General.DATA, MODE_PRIVATE).edit();
        editor.putInt(General.TYPE_VIEW, General.typeView);
        editor.apply();
    }

    protected abstract void onChangeTypeView(int typeView);

    private void setChecked(int typeView) {
        if(typeViewMenu == null) return;
        Log.d(Debug.TAG + TAG, "setChecked " + SwitchListAdapter.getTypeView(typeView));
        AppUtils.clearChecked(typeViewMenu);
        AppUtils.setChecked(typeViewMenu.findItem(getTypeViewMenuItemId(typeView)), true);
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
