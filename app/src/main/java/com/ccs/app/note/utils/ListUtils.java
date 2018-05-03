package com.ccs.app.note.utils;

import java.util.List;

public class ListUtils {

    //
    public static boolean isValidateIndex(List list, int index) {
        return index >= 0 && index < list.size();
    }

}
