package me.hongui.learngerman.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Answer {
    @PrimaryKey
    private long id;

    private long about;

    private boolean selected;

    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAbout() {
        return about;
    }

    public void setAbout(long about) {
        this.about = about;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
