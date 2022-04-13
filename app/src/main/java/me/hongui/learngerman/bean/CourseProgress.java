package me.hongui.learngerman.bean;

import java.util.ArrayList;
import java.util.List;

public class CourseProgress {
    public int category;
    public long total;
    public long finished;
    public List<Round> rounds;

    public CourseProgress(){
        rounds = new ArrayList<>();
    }

    public float finishRate(){
        if(null==rounds || rounds.isEmpty()){
            return 0;
        }
        float real=finished*1.0F/total;
        return real>1f?1f:real;
    }
}
