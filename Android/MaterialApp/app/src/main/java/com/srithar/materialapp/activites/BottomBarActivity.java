package com.srithar.materialapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srithar.materialapp.R;

public class BottomBarActivity extends AppCompatActivity {
    FloatingActionButton mFloatingActionButton;
    BottomAppBar mBottomAppBar;
    int clickedTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        initViews();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedTimes++;
                mBottomAppBar.setFabAlignmentMode((clickedTimes%2==1)
                        ?BottomAppBar.FAB_ALIGNMENT_MODE_END
                        :BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            }
        });


    }

    private void initViews() {
        Toolbar toolbar=findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24);
        }

        mFloatingActionButton=findViewById(R.id.button_bottom_appbar);
        mBottomAppBar=findViewById(R.id.bottom);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }




}