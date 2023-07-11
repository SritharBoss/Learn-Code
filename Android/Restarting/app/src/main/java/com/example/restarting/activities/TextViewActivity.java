package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.restarting.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TextViewActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        getSupportActionBar().setTitle("Text View");

    }

    public void onClick(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setMessage(R.string.textViewCode);
        alertDialog=builder.create();
        alertDialog.setTitle("XML Code");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
//        View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(dialogView);
//        dialog.show();
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
