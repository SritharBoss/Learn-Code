package com.srithar.tokenkey;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class NewFragment extends Fragment implements View.OnClickListener {

    CoordinatorLayout coordinatorLayout;
    TextInputEditText inputEditTextToken,inputEditTextDate;
    Calendar myCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener;
    ActionProcessButton submitBtn;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        coordinatorLayout=(CoordinatorLayout) inflater.inflate(R.layout.fragment_new, container, false);
        return coordinatorLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

        inputEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getContext()),onDateSetListener,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submitBtn.setOnClickListener(this);



    }

    private void initViews() {
        inputEditTextToken =coordinatorLayout.findViewById(R.id.textinput_token);
        inputEditTextDate =coordinatorLayout.findViewById(R.id.textinput_date);
        submitBtn=coordinatorLayout.findViewById(R.id.submit_btn);
        textView=coordinatorLayout.findViewById(R.id.textView_new);
    }

    private void updateLabel(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        inputEditTextDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submit_btn){
            if(inputEditTextToken.getText().length()>0&&inputEditTextDate.getText().length()>0) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(coordinatorLayout.getWindowToken(), 0);
                submitBtn.setProgress(50);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        submitBtn.setProgress(0);
                        String a=inputEditTextToken.getText().toString();
                        String b=inputEditTextDate.getText().toString().substring(0,2);
                        String c=a.concat(b.concat(inputEditTextDate.getText().toString().substring(3,5)));

                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                                textView.setText(CryptUtil.encrypt(c));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },2000);


            }else {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(coordinatorLayout.getWindowToken(), 0);
                Snackbar.make(coordinatorLayout,"Please Fill Up all fields",Snackbar.LENGTH_LONG).show();
            }
        }
    }
}