package me.hongui.learngerman.bean;

public class Round {
    public int category;
    public long duration;
    public long round;
    public long finished;

    public long mius(){
        return duration/1000;
    }
}
