package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteModel {

    private final Integer noteId;
    private final String noteTitle;
    private final String noteDescription;

    public NoteModel(Integer noteId, String noteTitle, String noteDescription) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }
}
