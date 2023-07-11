package com.srithar.notesapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NotesEntity.class},version = 1)
abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase dbInstance;

    public abstract NotesDAO mNotesDAO();

    public static synchronized NotesDatabase getDbInstance(Context context){
        if(dbInstance==null){
            dbInstance= Room.databaseBuilder(context.getApplicationContext(),NotesDatabase.class,"notes_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return dbInstance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(dbInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NotesDAO mNotesDAO;

        private PopulateDbAsyncTask(NotesDatabase notesDatabase){
            mNotesDAO=notesDatabase.mNotesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNotesDAO.insert(new NotesEntity("Title 1", "Description 1"));
            mNotesDAO.insert(new NotesEntity("Title 2", "Description 2"));
            mNotesDAO.insert(new NotesEntity("Title 3", "Description 3"));
            return null;
        }
    }
}
