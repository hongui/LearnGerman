package me.hongui.learngerman.bean;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question{
    private int category;
    @PrimaryKey
    private long id;

    private String content;

    private String translate;

    private String sep;

    private String resource;

    private String backup;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getResource() {
        return resource;
    }

    public String getResourceName() {
        if (TextUtils.isEmpty(resource)) {
            return "";
        }
        String[] results = resource.split("\\.");
        return results[0];
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackup() {
        if (null == backup) {
            return "暂无";
        }
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getSepContent(String s) {
        if (null == s) {
            s = "______";
        }
        if (null == sep) {
            sep = "_";
        }
        return content.replace(sep, s);
    }

    public int getDrawable(Context context) {
        return getResouceId(context, "drawable");
    }

    private int getResouceId(Context context, String type) {
        return context.getResources().getIdentifier(getResourceName(), type, context.getPackageName());
    }

    public String getSepContent() {
        return getSepContent(null);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Question)){
            return false;
        }
        Question question=(Question)obj;
        return question.id==id;
    }
}
