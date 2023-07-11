package com.srithar.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class NotesRepository {
    NotesDatabase dbInstance;
    NotesDAO mNotesDAO;
    LiveData<List<NotesEntity>> allNotes;
    public NotesRepository(Application application){
        dbInstance=NotesDatabase.getDbInstance(application);
        mNotesDAO=dbInstance.mNotesDAO();
        allNotes=mNotesDAO.getAllNotes();
    }

    public void insert(NotesEntity notesEntity){
        new InsertNoteAsyncTask(mNotesDAO).execute(notesEntity);
    }

    public void update(NotesEntity notesEntity){
        new EditNoteAsyncTask(mNotesDAO).execute(notesEntity);
//        mNotesDAO.update(notesEntity);
    }

    public void delete(NotesEntity notesEntity){
        new DeleteNoteAsyncTask(mNotesDAO).execute(notesEntity);
    }

    public void deleteAllNotes(){
        mNotesDAO.deleteAllNotes();
    }

    public LiveData<List<NotesEntity>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDAO noteDao;

        private InsertNoteAsyncTask(NotesDAO noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NotesEntity... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDAO noteDao;
        private DeleteNoteAsyncTask(NotesDAO noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NotesEntity... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class EditNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDAO noteDao;
        private EditNoteAsyncTask(NotesDAO noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NotesEntity... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }


}
