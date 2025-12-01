package com.srithar.rtspplayer;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alexvas.rtsp.widget.RtspSurfaceView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SRITHAR_TAG";
    RtspSurfaceView viewById=null;

    private String ipAddress=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewById = findViewById(R.id.rtsp);

        SharedPreferences prefs = this.getSharedPreferences("sri_prefs", MODE_PRIVATE);
        if(prefs.contains("ipAddress")){
            ipAddress=prefs.getString("ipAddress",null);
        }

        if(ipAddress==null){
            startSearchAndPlay();
        }

        if(isReachable(ipAddress,554,200)){
            Uri uri = Uri.parse("rtsp://"+ ipAddress +"/stream1");
            String username = "srithar";
            String password = "srithar";
            viewById.init(uri, username, password);
            viewById.start(true, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(viewById!=null){
            viewById.stop();
            Log.d(TAG,"Video Stopped");
        }
    }




    public void startSearchAndPlay(){
        Log.d(TAG,"STARTED SEARCH AND PLAY");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        AtomicBoolean status= new AtomicBoolean(false);
        for (int i = 1; i < 255; i++) {
            final String host = "192.168.1." + i;
            executor.submit(() -> {
                    try (Socket soc = new Socket()) {
                        soc.connect(new InetSocketAddress(host, 554), 200);
                        Log.d(TAG,"Connection Success :: "+host);
                        SharedPreferences prefs = this.getSharedPreferences("sri_prefs", MODE_PRIVATE);
                        prefs.edit().putString("ipAddress",host).apply();
                        this.ipAddress=host;
                        status.set(true);
                        executor.shutdownNow();
                        Log.d(TAG, "startSearchAndPlay: SUCCESS");
                    } catch (IOException ex) {
                        Log.d(TAG,"Cannot Connect to "+host);
                    }
            });
            if(status.get()){
                Log.d(TAG, "Status: "+status.get());
                break;
            }

        }
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean isReachable(String host,int port,int timeout){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean status= new AtomicBoolean(false);
        executor.submit(()->{
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(host, port), timeout);
                Log.d(TAG,"Connection Success :: "+host);
                status.set(true);
            } catch (IOException ex) {
                Log.d(TAG,"Cannot Connect to "+host);
            }
        });
        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return status.get();
    }



}