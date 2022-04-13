package me.hongui.learngerman.bean;

public class RecordState {
    private String title;
    private boolean canPrev=true;
    private boolean canNext=false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCanPrev() {
        return canPrev;
    }

    public void setCanPrev(boolean canPrev) {
        this.canPrev = canPrev;
    }

    public boolean isCanNext() {
        return canNext;
    }

    public void setCanNext(boolean canNext) {
        this.canNext = canNext;
    }
}
