package me.hongui.learngerman.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.bean.Round;
import me.hongui.learngerman.bean.Wrong;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRecord(Record record);

    @Query("SELECT * FROM record WHERE date BETWEEN :start AND :end")
    Observable<List<Record>> records(long start, long end);

    @Query("SELECT * FROM record WHERE category=:category AND correct=1 AND round = (SELECT round FROM record WHERE category = :category ORDER BY round DESC LIMIT 1) ORDER BY date ASC")
    Observable<List<Record>> rounds(int category);

    @Query("SELECT min(date) FROM record")
    Observable<Long> days();

    @Query("SELECT round,category,max(date) - min(date) AS duration,sum(correct) as finished FROM record WHERE category IN (1,2,11,12,13,14) GROUP BY category,round")
    Observable<List<Round>> progress();

    @Query("SELECT date,category,count(correct) AS total,sum(correct) AS correct FROM record WHERE  category IN (3,4 ,5,6 ,7,8 ,9 ,10) GROUP BY category")
    Observable<List<Correct>> categoryRate();

    @Query("SELECT category,count(correct) AS total,count(correct)-sum(correct) AS wrong FROM record WHERE category IN (3,4,5,6,7,8,9,10) GROUP BY category")
    Observable<List<Wrong>> wrong();

    @Query("SELECT category,:start AS date,count(correct) AS total,sum(correct) AS correct FROM record WHERE category IN (3,4,5,6,7,8,9,10) AND date BETWEEN :start AND :end")
    Observable<Correct> correct(long start,long end);
}
