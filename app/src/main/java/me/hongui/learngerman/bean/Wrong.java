package me.hongui.learngerman.bean;

public class Wrong {
    public int category;
    public long total;
    public long wrong;

    public float rate(){
        return wrong*1F/total;
    }
}
