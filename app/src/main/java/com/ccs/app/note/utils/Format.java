package com.ccs.app.note.utils;

import java.util.Date;

public class Format {

    public static String formatDate(long ms) {
        Date date = new Date(ms);
        return date.toString();
    }

    public static String formatTime(int ms) {
        int s = ms / 1000;
        int h = s / 3600;
        int m = s % 3600 / 60;
        s %= 60;

        if(h > 0) return "" + h + ":" + m + ":" + s;
        return "" + m + ":" + s;
    }

}
