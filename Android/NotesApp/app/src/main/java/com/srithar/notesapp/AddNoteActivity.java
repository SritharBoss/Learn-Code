package com.srithar.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE="Extra Title From AddNote Activity";
    public static final String EXTRA_DESCRIPTION="Extra Description From AddNote Activity";
    public static final String EXTRA_ID="Extra ID From AddNote Activity";

    TextInputEditText et_title,et_desc;
    Button mButton;
    String title,desc;
    Long id= 0L;
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_add_note);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Add Your Note Here");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        }

        et_title=findViewById(R.id.add_note_edit_text_title);
        et_desc=findViewById(R.id.add_note_edit_text_desc);
        mButton=findViewById(R.id.add_note_save_button);
        mRelativeLayout=findViewById(R.id.add_note_relative_layout);

        if(getIntent().getExtras()!=null){
            et_title.setText(getIntent().getStringExtra("Title"));
            et_desc.setText(getIntent().getStringExtra("Description"));
            id=getIntent().getLongExtra("Id",0);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

    }

    public void saveNote(){
        View view =this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm!=null&&view!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        title= Objects.requireNonNull(et_title.getText()).toString();
        desc= Objects.requireNonNull(et_desc.getText()).toString();
        if(!(title.trim().isEmpty()||desc.trim().isEmpty())){
            Intent intent=new Intent();
            intent.putExtra(EXTRA_TITLE,title);
            intent.putExtra(EXTRA_DESCRIPTION,desc);
            if(id!=0)intent.putExtra(EXTRA_ID,id);
            setResult(RESULT_OK,intent);
            finish();
        }else
            Snackbar.make(mRelativeLayout,"Please Enter All Fields", Snackbar.LENGTH_LONG).setAnchorView(mButton).show();
    }
}