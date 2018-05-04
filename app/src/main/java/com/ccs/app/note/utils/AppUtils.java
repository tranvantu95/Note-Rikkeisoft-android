package com.ccs.app.note.utils;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class AppUtils {

    // resources
    public static Bitmap getBitmapFromVectorDrawable(Resources resources, int resId) {
        return getBitmapFromDrawable(getVectorDrawable(resources, resId));
    }

    public static Drawable getVectorDrawable(Resources resources, int resId) {
        if(currentVersionSupportVectorDrawable()) return resources.getDrawable(resId, null);
        return VectorDrawableCompat.create(resources, resId, null);
    }

    public static Bitmap getBitmapFromDrawable(Resources resources, int resId) {
        return getBitmapFromDrawable(resources.getDrawable(resId));
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if(drawable == null) return null;

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    // Fragment
    public static Fragment findPagerFragment(FragmentManager fm, int viewPagerId, int pagerPosition) {
        return fm.findFragmentByTag("android:switcher:" + viewPagerId + ":" + pagerPosition);
    }

    @Nullable
    public static Fragment getFragment(FragmentManager fm, Class<? extends Fragment> clazz, int viewPagerId, int pagerPosition) {
        Fragment fragment = findPagerFragment(fm, viewPagerId, pagerPosition);
        if(fragment != null) return fragment;
        return newInstanceFragment(clazz);
    }

    @Nullable
    public static Fragment getFragment(FragmentManager fragmentManager, Class<? extends Fragment> clazz) {
        Fragment fragment = fragmentManager.findFragmentByTag(clazz.getName());
        if(fragment != null) return fragment;
        return newInstanceFragment(clazz);
    }

    @Nullable
    private static Fragment newInstanceFragment(Class<? extends Fragment> clazz) {
        Fragment fragment = null;

        try {
            fragment = clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fragment;
    }

    @Nullable
    public static Fragment addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Class<? extends Fragment> clazz) {
        Fragment fragment = getFragment(fragmentManager, clazz);
        if(fragment != null) addFragment(fragmentManager, containerViewId, fragment);
        return fragment;
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        addFragment(fragmentManager, containerViewId, fragment, fragment.getClass().getName());
    }

    public static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment, String tag) {
        if(fragment.isAdded()) return;

        fragmentManager.beginTransaction()
                .add(containerViewId, fragment, tag)
                .commit();
    }

    // Activity
    public static Intent makeMainIntent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        return intent;
    }

    public static Intent makeHomeIntent(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setAction(Intent.ACTION_MAIN);
        return intent;
    }

    // Service
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null)
            for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE))
                if(serviceClass.getName().equals(service.service.getClassName())) return true;

        return false;
    }

    // Widget
    public static AppWidgetManager getAppWidgetManager(Context context) {
        return AppWidgetManager.getInstance(context);
    }

    public static int[] getAppWidgetIds(Context context, AppWidgetManager appWidgetManager, Class<?> widgetClass) {
        return appWidgetManager.getAppWidgetIds(new ComponentName(context, widgetClass));
    }

    // check support
    public static boolean currentVersionSupportLockScreenControls() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean currentVersionSupportBigNotification() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean currentVersionSupportVectorDrawable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
