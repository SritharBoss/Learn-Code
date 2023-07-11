package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.restarting.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.logging.Logger;

public class ScoreKeeper extends AppCompatActivity {

    private int team1_Score=0;
    private int team2_Score=0;
    private TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ScoreKeeper);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeper);

        tv1=findViewById(R.id.textView15);
        tv2=findViewById(R.id.textView16);

    }

    public void decreaseScore(View view) {
        int viewId=view.getId();
        switch (viewId){
            case R.id.imageButton2:
                team1_Score--;
                tv1.setText(String.valueOf(team1_Score));
                break;
            case R.id.imageButton4:
                team2_Score--;
                tv2.setText(String.valueOf(team2_Score));
                break;
        }
    }

    public void increaseScore(View view) {
        int viewId=view.getId();
        switch (viewId){
            case R.id.imageButton:
                team1_Score++;
                tv1.setText(String.valueOf(team1_Score));
                break;
            case R.id.imageButton3:
                team2_Score++;
                tv2.setText(String.valueOf(team2_Score));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.night_mode,menu);
        SwitchMaterial item=menu.findItem(R.id.night_mode_toggle).getActionView().findViewById(R.id.night_mode_switch);
        item.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(AppCompatDelegate.getDefaultNightMode()!=AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(getApplicationContext(),"Night Mode : ON",Toast.LENGTH_SHORT).show();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(getApplicationContext(),"Night Mode : OFF",Toast.LENGTH_SHORT).show();
                }
                recreate();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}