package me.hongui.learngerman.bean;

public class Quiz {
    private int title;
    private int summary;
    private int resource;
    private int[] categoirs;
    private boolean collapse;

    public Quiz(int title, int summary, int resource) {
        this.title = title;
        this.summary = summary;
        this.resource = resource;
    }

    public Quiz(int title, int summary, int resource, int[] categoirs) {
        this.title = title;
        this.summary = summary;
        this.resource = resource;
        this.categoirs = categoirs;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int[] getCategoirs() {
        return categoirs;
    }

    public void setCategoirs(int[] categoirs) {
        this.categoirs = categoirs;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }
}
