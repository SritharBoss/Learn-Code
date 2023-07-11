package com.srithar.learndagger;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SataCable {

    @Inject
    public SataCable(){
    }

    public void setListener(){
        Log.d(Laptop.TAG,"Sata cable connected to HardDrive");
    }

}
