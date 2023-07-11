package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.restarting.R;
import com.example.restarting.fragments.FragmentOne;
import com.example.restarting.fragments.FragmentTwo;

public class FragmentActivity extends AppCompatActivity {
    Button button1,button2;
    Fragment fragment=null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        button1=findViewById(R.id.button6);
        button2=findViewById(R.id.button7);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary,getTheme()));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new FragmentOne();
                loadFragment(fragment);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new FragmentTwo();
                loadFragment(fragment);
            }
        });
    }

    private void loadFragment(Fragment fragment){
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
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

}
