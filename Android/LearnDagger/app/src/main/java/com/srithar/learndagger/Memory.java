package com.srithar.learndagger;

import android.util.Log;

import javax.inject.Inject;

class Memory {

    @Inject
    public Memory() {
        Log.d(Laptop.TAG,"Memory Object Created");
    }
}
