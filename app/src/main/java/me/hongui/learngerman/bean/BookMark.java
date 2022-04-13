package me.hongui.learngerman.bean;

import androidx.room.Entity;

@Entity
public class BookMark extends Question{
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static BookMark fromQuestion(Question question){
        BookMark bookMark=new BookMark();
        bookMark.setDate(System.currentTimeMillis());
        bookMark.setId(question.getId());
        bookMark.setBackup(question.getBackup());
        bookMark.setCategory(question.getCategory());
        bookMark.setContent(question.getContent());
        bookMark.setResource(question.getResource());
        bookMark.setSep(question.getSep());
        bookMark.setTranslate(question.getTranslate());
        return bookMark;
    }
}
