package com.bbo.todoapptask;

public class Todo {
    private String title, desc;
    private Boolean isCompleted;

    public Todo(String title, String desc, Boolean isCompleted) {
        this.title = title;
        this.desc = desc;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
