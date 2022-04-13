package me.hongui.learngerman.bean;

public class Error {
    public int category;
    public long total;
    public long error;

    public float rate(){
        return error *1F/total;
    }
}
