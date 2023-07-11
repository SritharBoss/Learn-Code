package com.srithar.materialapp.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.srithar.materialapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DialogFragment extends Fragment implements View.OnClickListener{
    NestedScrollView nestedScrollView;
    MaterialButton button1,button2,button3,button4,button5,button6,button7,button8,button9;

    boolean[] selectedMultiChoiceItems={false,false,false,true};
    String singleChoiceItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nestedScrollView=(NestedScrollView) inflater.inflate(R.layout.fragment_dialog, container, false);
        button1=nestedScrollView.findViewById(R.id.dialog_simple);
        button2=nestedScrollView.findViewById(R.id.dialog_simple1);
        button3=nestedScrollView.findViewById(R.id.dialog_singleChoice);
        button4=nestedScrollView.findViewById(R.id.dialog_multiChoice);
        button5=nestedScrollView.findViewById(R.id.dialog_progress_dismiss);
        button6=nestedScrollView.findViewById(R.id.dialog_progress_horizontal);
        button7=nestedScrollView.findViewById(R.id.dialog_date_picker);
        button8=nestedScrollView.findViewById(R.id.dialog_time_picker);
        button9=nestedScrollView.findViewById(R.id.dialog_bottom_sheet);
        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Context context=Objects.requireNonNull(getContext());
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(AlertDialog.BUTTON_POSITIVE==which)
                    Snackbar.make(nestedScrollView,"Clicked Positive Button",Snackbar.LENGTH_LONG).setAnchorView(nestedScrollView).show();
                else if(AlertDialog.BUTTON_NEGATIVE==which)
                    Snackbar.make(nestedScrollView,"Clicked Negative Button",Snackbar.LENGTH_LONG).show();
                else if (AlertDialog.BUTTON_NEUTRAL==which)
                    Snackbar.make(nestedScrollView,"Clicked Neutral Button",Snackbar.LENGTH_LONG).show();
            }
        };

        switch (v.getId()){
            case R.id.dialog_simple:
                new AlertDialog.Builder(context).setMessage("A Simple Message").setPositiveButton("Ok",listener).show();
                break;

            case R.id.dialog_simple1:
                builder.setIcon(R.drawable.ic_launcher_foreground)
                        .setTitle("Alert")
                        .setMessage("A Simple Message with some options")
                        .setPositiveButton("OK", listener)
                        .setNegativeButton("Cancel",listener)
                        .setNeutralButton("Neutral",listener).show();
                break;

            case R.id.dialog_singleChoice:
                builder.setTitle("Choose only one");
                builder.setSingleChoiceItems(R.array.str_array1,-1,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        singleChoiceItem="";
                        String[] a= getResources().getStringArray(R.array.str_array1);
                        singleChoiceItem=a[which];
                    }
                });
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(nestedScrollView,"Clicked item: "+singleChoiceItem,Snackbar.LENGTH_LONG).show();
                    }
                }).show();
                break;

            case R.id.dialog_multiChoice:
                builder.setMultiChoiceItems(R.array.str_array1, selectedMultiChoiceItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedMultiChoiceItems[which]=isChecked;
                    }
                })
                        .setTitle("Select below").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] a=getResources().getStringArray(R.array.str_array1);
                        String text="";
                        for (int i=0;i<selectedMultiChoiceItems.length;i++){
                            boolean checked=selectedMultiChoiceItems[i];
                            if(checked) {
                                text = text.concat(a[i]);
                                if(i!=selectedMultiChoiceItems.length-1)text=text.concat(" ,");
                            }
                        }
                        Snackbar.make(nestedScrollView,text,Snackbar.LENGTH_LONG).show();
                    }
                }).show();
                break;

            case R.id.dialog_progress_dismiss:
                final ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("You can cancel this by clicking outside of this dialog.");
                progressDialog.show();
                break;
            case R.id.dialog_progress_horizontal:
                final ProgressDialog horizontal=new ProgressDialog(context);
                horizontal.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                horizontal.setTitle("Loading...");
                horizontal.setMessage("Showing Progress");
                horizontal.setMax(100);
                horizontal.show();
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        for(int i=1;i<=100;i++){
                            try {
                                sleep(100);
                                if(i==100) horizontal.setMessage("Progress Completed");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            horizontal.setProgress(i);
                        }
                    }
                };
                thread.start();
                break;
            case R.id.dialog_date_picker:
                MaterialDatePicker.Builder<?> materialBuilder= MaterialDatePicker.Builder.datePicker();
                assert getFragmentManager() != null;
                materialBuilder.setTitleText("Select a Date");
                materialBuilder.build().show(getFragmentManager(),"Date");
                break;
            case R.id.dialog_time_picker:
                break;
            case R.id.dialog_bottom_sheet:
                break;
        }
    }
}