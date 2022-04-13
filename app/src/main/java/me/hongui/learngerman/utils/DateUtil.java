package me.hongui.learngerman.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
    private static SimpleDateFormat formator=new SimpleDateFormat("MM/dd");
    public static final String formatDate(long date){
        return formator.format(date);
    }
}
