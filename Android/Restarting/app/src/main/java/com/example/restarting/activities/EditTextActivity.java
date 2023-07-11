package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restarting.R;

import java.util.Objects;

public class EditTextActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    String txt="Submitted Text Appeared Here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Text");

        editText=findViewById(R.id.editText);
        button=findViewById(R.id.submitBtn);
        textView=findViewById(R.id.textView4);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txt=editText.getText().toString();
                if(txt.isEmpty())
                    textView.setText("Enter something");
                else
                    textView.setText(txt);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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
