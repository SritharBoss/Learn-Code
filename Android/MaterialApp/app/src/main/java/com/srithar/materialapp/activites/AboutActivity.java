package com.srithar.materialapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.srithar.materialapp.R;

public class AboutActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mToolbar=findViewById(R.id.toolbar_about);
        setSupportActionBar(mToolbar);
        if(!(getSupportActionBar() ==null)){
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24);
        }



    }
}