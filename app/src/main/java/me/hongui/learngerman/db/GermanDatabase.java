package me.hongui.learngerman.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.utils.ResourceUtil;


@Database(entities = {Question.class, Answer.class},version = 1,exportSchema = false)
public abstract class GermanDatabase extends RoomDatabase {

    private static volatile GermanDatabase INSTANCE;

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    public static GermanDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GermanDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GermanDatabase.class, ResourceUtil.DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
