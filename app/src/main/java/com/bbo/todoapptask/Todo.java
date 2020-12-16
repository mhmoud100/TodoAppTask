package com.bbo.todoapptask;

public class Todo {
    private int id;
    private String title;
    private Boolean isCompleted;

    public Todo(String title, int id, Boolean isCompleted) {
        this.title = title;
        this.id = id;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
