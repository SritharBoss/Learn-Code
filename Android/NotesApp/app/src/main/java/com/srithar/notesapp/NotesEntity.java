package com.srithar.notesapp;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
class NotesEntity implements NoteAdapter.OnNoteListener {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;

    public NotesEntity(){}

    public NotesEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void onItemLongClick(int position) {
        Log.d("Srithar", "onItemLongClick: called in NotesEntity ");
    }
}
