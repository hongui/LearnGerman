package me.hongui.learngerman.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;
import me.hongui.learngerman.bean.BookMark;
import me.hongui.learngerman.bean.Question;

@Dao
public interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(BookMark bookMark);

    @Delete
    void delete(BookMark bookMark);

    @Query("SELECT * FROM bookmark WHERE id = :id LIMIT 1 ")
    Observable<BookMark> exists(long id);

    @Query("SELECT id,category,content,translate,sep,resource,backup FROM bookmark ")
    Observable<List<Question>> all();
}
