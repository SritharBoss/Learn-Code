package com.srithar.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener, BottomSheetMenu.BottomSheetListener {
    public static final int ADD_NOTE_REQUEST=1;
    public static final int EDIT_NOTE_REQUEST=21;

    int position=0;
    RecyclerView mRecyclerView;
    NoteViewModel mViewModel;
    FloatingActionButton mFloatingActionButton;
    CoordinatorLayout mCoordinatorLayout;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=findViewById(R.id.main_recycler_view);
        mFloatingActionButton=findViewById(R.id.main_fab);
        mCoordinatorLayout=findViewById(R.id.main_coordinator_layout);
        mTextView=findViewById(R.id.main_empty_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        final NoteAdapter adapter=new NoteAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mViewModel= new ViewModelProvider(this).get(NoteViewModel.class);
        mViewModel.getAllNotes().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(List<NotesEntity> notesEntities) {
                if(notesEntities.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                }else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.GONE);
                    adapter.setNotes(notesEntities);
                }
            }

        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,mFloatingActionButton,"addImg");
                Intent intent=new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST,activityOptionsCompat.toBundle());
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ADD_NOTE_REQUEST&&resultCode== RESULT_OK&&data!=null){
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            NotesEntity notesEntity=new NotesEntity(title,desc);
            mViewModel.insert(notesEntity);
            Snackbar.make(mCoordinatorLayout,"Notes Saved Successfully",Snackbar.LENGTH_LONG).setAnchorView(mFloatingActionButton).show();
        }else if(requestCode==EDIT_NOTE_REQUEST&&resultCode==RESULT_OK&&data!=null){
            NotesEntity notesEntity=new NotesEntity();
            notesEntity.setId(data.getLongExtra(AddNoteActivity.EXTRA_ID,0));
            notesEntity.setTitle(data.getStringExtra(AddNoteActivity.EXTRA_TITLE));
            notesEntity.setDescription(data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION));
            mViewModel.update(notesEntity);
            Snackbar.make(mCoordinatorLayout,"Notes Saved",Snackbar.LENGTH_LONG).setAnchorView(mFloatingActionButton).show();
        }else{
            Snackbar.make(mCoordinatorLayout,"Notes not Saved",Snackbar.LENGTH_LONG).setAnchorView(mFloatingActionButton).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_night_mode) {
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemLongClick(int position) {
        Log.d("Srithar", "onItemLongClick: called in MainActivity ");
        BottomSheetMenu bottomSheetMenu=new BottomSheetMenu();
        bottomSheetMenu.show(getSupportFragmentManager(),"Show Menu");
        this.position=position;
    }

    @Override
    public void onButtonClicked(String text) {
        if(text.equals("Clicked Delete")){
            LiveData<List<NotesEntity>> allNotes=mViewModel.getAllNotes();
            NotesEntity note= Objects.requireNonNull(allNotes.getValue()).get(position);
            mViewModel.delete(note);
            Snackbar.make(mCoordinatorLayout,"Note Deleted",Snackbar.LENGTH_LONG).show();
        }else if(text.equals("Clicked Edit")){
            LiveData<List<NotesEntity>> allNotes=mViewModel.getAllNotes();
            NotesEntity note= Objects.requireNonNull(allNotes.getValue()).get(position);
            Intent intent=new Intent(getApplicationContext(),AddNoteActivity.class);
            intent.putExtra("Title",note.getTitle());
            intent.putExtra("Description",note.getDescription());
            intent.putExtra("Id",note.getId());
            startActivityForResult(intent,EDIT_NOTE_REQUEST);
        }
    }
}