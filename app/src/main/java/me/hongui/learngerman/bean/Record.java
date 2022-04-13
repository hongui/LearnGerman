package me.hongui.learngerman.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Record{
    @Ignore
    private boolean join;
    @Ignore
    private boolean enable;
    private boolean correct;
    private int category;
    @Ignore
    private int day;
    @PrimaryKey
    private long date;
    private long round;
    private long qustion;
    private String answer;

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    public long getQustion() {
        return qustion;
    }

    public void setQustion(long qustion) {
        this.qustion = qustion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
