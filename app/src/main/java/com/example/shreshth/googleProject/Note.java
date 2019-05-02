package com.example.shreshth.googleProject;

public class Note {
    private String title;
    private String author;
    private String link;
    private int priority;

    public Note(){
        //empty constructor needed
    }

    public Note(String title,String author,int priority,String link){
        this.title=title;
        this.author=author;
        this.priority=priority;
        this.link=link;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public int getPriority(){
        return priority;
    }

    public String getLink(){
        return link;
    }
}
