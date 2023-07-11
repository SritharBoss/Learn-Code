package com.srithar.learndagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Singleton;


public class MainActivity extends AppCompatActivity {

    @Inject
    Laptop mLaptop;
    @Inject
    HardDrive mHardDrive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LaptopComponent laptopComponent=DaggerLaptopComponent.create();
        laptopComponent.inject(this);
        laptopComponent.getHardDrive();
        mLaptop.createLaptop();


    }
}