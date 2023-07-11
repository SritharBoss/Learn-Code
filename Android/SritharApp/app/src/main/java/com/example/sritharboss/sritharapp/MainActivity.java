package com.example.sritharboss.sritharapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2;
    TextView tv4;
    Button addBtn, subBtn, mulBtn, divBut, social;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        tv4 = (TextView) findViewById(R.id.tv4);
        addBtn = (Button) findViewById(R.id.add);
        subBtn = (Button) findViewById(R.id.sub);
        mulBtn = (Button) findViewById(R.id.mul);
        divBut = (Button) findViewById(R.id.div);
        social=(Button) findViewById(R.id.social);

        social.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(getApplicationContext(),"Currently it supports only Integers,In future i'll add float and others, ThankU! Follow Me on Twitter : @SritharBoss",
                        Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.TOP,0,0);

            }
        });

    }

    public void add(View v) {
        try
        {
            int i = Integer.parseInt(et1.getText().toString());
            int j = Integer.parseInt(et2.getText().toString());
            int k = i + j;
            tv4.setText("Answer is " + k);
        } catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, Something Went Wrong, Kandippa Sari Panniduven",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }
    public void sub(View v) {
        try
        {
            int i = Integer.parseInt(et1.getText().toString());
            int j = Integer.parseInt(et2.getText().toString());
            int k = i - j;
            tv4.setText("Answer is " + k);
        } catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, Something Went Wrong, Kandippa Sari Panniduven",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }
    public void mul(View v) {
        try
        {
            int i = Integer.parseInt(et1.getText().toString());
            int j = Integer.parseInt(et2.getText().toString());
            int k = i * j;
            tv4.setText("Answer is " + k);
        } catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, Something Went Wrong, Kandippa Sari Panniduven",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }
    public void div(View v) {
        try
        {
            int i = Integer.parseInt(et1.getText().toString());
            int j = Integer.parseInt(et2.getText().toString());
            int k = i / j;
            tv4.setText("Answer is " + k);
        } catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Sorry, Something Went Wrong, Kandippa Sari Panniduven",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }

}
