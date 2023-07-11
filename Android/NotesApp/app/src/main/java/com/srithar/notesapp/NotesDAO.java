package com.srithar.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDAO {

    @Insert
    void insert(NotesEntity notes);

    @Update
    void update(NotesEntity notes);

    @Delete
    void delete(NotesEntity notes);

    @Query("delete from notes_table")
    void deleteAllNotes();

    @Query("select * from notes_table order by id")
    LiveData<List<NotesEntity>> getAllNotes();
}
