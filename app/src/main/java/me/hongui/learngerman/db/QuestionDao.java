package me.hongui.learngerman.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Observable;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.bean.SimpleQuestion;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM question WHERE category=:category")
    Observable<List<Question>> questionByCategory(int category);

    @Query("SELECT * FROM question WHERE category=:category LIMIT :limit")
    Observable<List<Question>> questionByCategory(int category,int limit);

    @Query("SELECT category,count(category) AS total FROM question WHERE category=1 OR category=2 OR category=11 OR category=12 OR category=13 OR category=14 GROUP BY category")
    Observable<List<SimpleQuestion>> simpleQuestion();

    @Query("SELECT * FROM answer WHERE about=:about")
    Observable<List<Answer>> answerForQuestion(long about);
}
