package com.srithar.materialapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.srithar.materialapp.utils.AppUtils;
import com.srithar.materialapp.R;

public class SharedPrefActivity extends AppCompatActivity {

    public static final String SHARED_PREF_NAME="sharedPref";
    public static final String SAVED_STRING = "";

    TextView mTextView;
    TextInputEditText mTextInputEditText;
    Button mButton,fetchButton;
    ConstraintLayout view;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        mTextView=findViewById(R.id.shared_pref_textView);
        mTextInputEditText=findViewById(R.id.shared_pref_editText);
        mButton=findViewById(R.id.shared_pref_button);
        fetchButton=findViewById(R.id.shared_prefs_fetch_btn);
        view=findViewById(R.id.shared_pref_view);
        mToolbar=findViewById(R.id.shared_pref_toolbar);

        final Activity activity=this;

        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Shared Prefs");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrefs();
                AppUtils.hideKeyboard(activity);
            }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchPrefs();

            }
        });


    }

    private void fetchPrefs() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String s=sharedPreferences.getString(SAVED_STRING,"");
        if(s.equals("")){
            Snackbar.make(view,"Data Not Available",Snackbar.LENGTH_INDEFINITE).show();
        }else{
            Snackbar.make(view,"Data Fetched Successfully",Snackbar.LENGTH_INDEFINITE).show();
            mTextView.setText(s);
        }
    }


    public void savePrefs(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (mTextInputEditText.getText()!=null&& !mTextInputEditText.getText().toString().equals("")) {
            String text=mTextInputEditText.getText().toString();
            editor.putString(SAVED_STRING,text);
            editor.apply();
            Snackbar.make(view,"Data Stored Successfully", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}