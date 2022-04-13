package me.hongui.learngerman.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import me.hongui.learngerman.bean.BookMark;
import me.hongui.learngerman.bean.Record;


@Database(entities = {Record.class, BookMark.class},version = 2,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract BookMarkDao bookMarkDao();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}