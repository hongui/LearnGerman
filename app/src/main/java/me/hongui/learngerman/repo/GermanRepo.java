package me.hongui.learngerman.repo;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import me.hongui.learngerman.app.AppRepo;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.bean.SimpleQuestion;
import me.hongui.learngerman.db.GermanDatabase;

public class GermanRepo extends AppRepo {
    private GermanDatabase database;

    public GermanRepo(Context context) {
        database=GermanDatabase.getInstance(context);
    }

    public Observable<List<Question>>  questionByCategory(int category){
        return database.questionDao().questionByCategory(category);
    }

    public Observable<List<Question>>  questionByCategory(int category,int limit){
        return database.questionDao().questionByCategory(category,limit);
    }

    public Observable<List<Answer>> answerForQuestion(long questionId){
        return database.questionDao().answerForQuestion(questionId);
    }

    public Observable<List<SimpleQuestion>> simpleQuestions(){
        return database.questionDao().simpleQuestion();
    }
}
