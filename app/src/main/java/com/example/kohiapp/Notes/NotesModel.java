package com.example.kohiapp.Notes;

public class NotesModel {
    private String id;
    private String title;
    private String context;
    private String uid;

    public NotesModel(){

    }

    public NotesModel(String id, String title, String context, String uid) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.uid = uid;
    }

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
