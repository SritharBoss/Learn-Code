package com.example.wifinetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.window.SplashScreen;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final Boolean darkMode=true;

    private FloatingActionButton fab;
    private MaterialButton ipRefresh;
    private TextView publicIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int color = SurfaceColors.SURFACE_2.getColor(this);
        getWindow().setStatusBarColor(color);
        getWindow().setNavigationBarColor(color);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initViews();
        initListeners();



    }

    private void initViews(){
        fab=findViewById(R.id.regular_fab);
        ipRefresh=findViewById(R.id.ip_refresh);
        publicIp=findViewById(R.id.tv_ip);
    }

    @SuppressLint("SetTextI18n")
    private void initListeners(){
        fab.setOnClickListener(v->{
            Intent intent_tools = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent_tools);
        });

        ipRefresh.setOnClickListener(v->{
            String ip=getIpv4Address();
            publicIp.setText(Html.fromHtml("<b>"+ip+"</b>",Html.FROM_HTML_MODE_COMPACT));
        });


    }

    private void updateViews(){

    }

    private String getIpv4Address(){
        String ip=null;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("curl", "ifconfig.me");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            ip=reader.readLine();
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return ip!=null?ip:"0";
    }
}