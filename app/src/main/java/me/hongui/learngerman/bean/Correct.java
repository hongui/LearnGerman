package me.hongui.learngerman.bean;

public class Correct {
    public long correct;
    public long total;
    public long date;
    public int category;

    public float correctRate(){
        return correct *1.0F/total;
    }

    public float errorRate(){
        return 1- correctRate();
    }

    public float correctPercent(){
        return correctRate()*100;
    }

}
