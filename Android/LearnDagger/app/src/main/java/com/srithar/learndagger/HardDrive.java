package com.srithar.learndagger;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
class HardDrive {
    @Inject
    public HardDrive() {
        Log.d(Laptop.TAG,"HardDrive Object Created");
    }

    @Inject
    public void enableSata(SataCable sataCable){
        sataCable.setListener();
    }
}
