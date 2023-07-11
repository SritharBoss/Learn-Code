package com.example.restarting.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restarting.R;

public class MainActivity extends AppCompatActivity {
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button6=findViewById(R.id.button8);
        button7=findViewById(R.id.button9);
        button8=findViewById(R.id.button10);
        button9=findViewById(R.id.button11);
        button10=findViewById(R.id.button12);
        button11=findViewById(R.id.button13);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent,getTheme()));

        final Intent textViewIntent=new Intent(this, TextViewActivity.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(textViewIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent editTextIntent=new Intent(this,EditTextActivity.class);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(editTextIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent scrollViewIntent=new Intent(this,ScrollingActivity.class);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(scrollViewIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http:www.google.com"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent intentFragment=new Intent(this, FragmentActivity.class);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentFragment);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent customListView=new Intent(this,com.example.restarting.CustomListView.class);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(customListView);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent spinnerIntent=new Intent(this, SpinnerActivity.class);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(spinnerIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent tabHostIntent=new Intent(this, TabHostActivity.class);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(tabHostIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent sqLiteIntent=new Intent(this, SqliteActivity.class);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(sqLiteIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent scoreKeeperIntent=new Intent(this, ScoreKeeper.class);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(scoreKeeperIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final Intent materialDialogIntent=new Intent(this, MaterialActivity.class);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(materialDialogIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });



    }

}