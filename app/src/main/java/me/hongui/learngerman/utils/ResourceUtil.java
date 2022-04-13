package me.hongui.learngerman.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceUtil {
    public static final String DB_NAME="german.db";
    public static final String DB_INFO="dbInfo";
    public static final String DB_INITED="dbInited";

    public static boolean checkDbExists(Context context){
        SharedPreferences preferences = context.getSharedPreferences(DB_INFO, Context.MODE_PRIVATE);
        boolean inited = preferences.getBoolean(DB_INITED,false);
        return inited;
    }

    private static void dbInited(Context context){
        SharedPreferences preferences = context.getSharedPreferences(DB_INFO, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(DB_INITED,true).commit();
    }

    public static void moveDb(Context c){
        final Context context=c.getApplicationContext();
        if(checkDbExists(context)){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetManager assets = context.getAssets();
                InputStream inputStream=null;
                OutputStream outputStream=null;
                try {
                    inputStream = assets.open(DB_NAME);

                    File databasePath = context.getDatabasePath(DB_NAME);
                    if(databasePath.exists()){
                        databasePath.delete();
                    }
                    outputStream = new FileOutputStream(databasePath);

                    byte[] buffer=new byte[4096];
                    int length=0;
                    while ((length=inputStream.read(buffer))>0){
                        outputStream.write(buffer,0,length);
                    }

                    dbInited(context);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try{
                        if(null!=inputStream) {
                            inputStream.close();
                        }
                        if(null!=outputStream) {
                            outputStream.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
