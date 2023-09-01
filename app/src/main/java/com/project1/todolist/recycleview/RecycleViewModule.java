package com.project1.todolist.recycleview;


public class RecycleViewModule {
    String Task,date,time;
    boolean complete = false ;

    public RecycleViewModule(String Task, String date, String time ){
        this.Task = Task;
        this.date = date;
        this.time = time;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
