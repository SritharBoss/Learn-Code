package com.srithar.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NotesRepository mRepository;
    private LiveData<List<NotesEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository=new NotesRepository(application);
        allNotes=mRepository.getAllNotes();
    }

    public void insert(NotesEntity notesEntity){
        mRepository.insert(notesEntity);
    }

    public void delete(NotesEntity notesEntity){
        mRepository.delete(notesEntity);
    }

    public void update(NotesEntity notesEntity){
        mRepository.update(notesEntity);
    }

    public void deleteAllNotes(){
        mRepository.deleteAllNotes();
    }

    public LiveData<List<NotesEntity>> getAllNotes(){
        return allNotes;
    }
}
