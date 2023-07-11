package com.srithar.learndagger;

import android.util.Log;
import javax.inject.Inject;

class Laptop {

    public static final String TAG="LAPTOP";

    Memory mMemory;
    @Inject
    HardDrive mHardDrive;
    @Inject
    public Laptop(Memory memory) {
        mMemory=memory;
    }

    public void createLaptop(){
        Log.d(TAG,"Laptop Created with"+mMemory+" and "+mHardDrive);
    }
}
