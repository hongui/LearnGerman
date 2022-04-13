package me.hongui.learngerman.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Observable;
import me.hongui.learngerman.bean.Question;

@Dao
public interface AnswerDao {

    @Query("SELECT * FROM question WHERE category=:category")
    Observable<List<Question>> questionByCategory(int category);

}