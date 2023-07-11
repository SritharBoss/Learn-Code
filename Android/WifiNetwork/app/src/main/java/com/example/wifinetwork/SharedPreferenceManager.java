package com.example.wifinetwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPreferenceManager {
    SharedPreferences sharedPreferences;

    public SharedPreferenceManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private SharedPreferences.Editor getEditor(){
        return sharedPreferences.edit();
    }

    private Boolean retrieveBoolean(String tag, boolean defaultValue){
        return sharedPreferences.getBoolean(tag,defaultValue);
    }

    private void storeBoolean(String tag, boolean value){
        getEditor().putBoolean(tag,value).commit();
    }

    private String retrieveString(String tag, String defaultValue){
        return sharedPreferences.getString(tag,defaultValue);
    }

    private void storeString(String tag, String value){
        getEditor().putString(tag,value).commit();
    }
}
