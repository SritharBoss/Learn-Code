package com.example.wifinetwork;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.elevation.SurfaceColors;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private MaterialButton ipRefresh;
    private TextView publicIp,ipv4,ipv6,wifi_dist;
    private String dist;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int color = SurfaceColors.SURFACE_2.getColor(this);
        getWindow().setStatusBarColor(color);
        getWindow().setNavigationBarColor(color);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();



        initViews();
        initListeners();
        refreshData();

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                new Async1().execute();
            }

            @Override
            public void onLost(Network network) {
                new Async1().execute();
            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        connectivityManager.registerDefaultNetworkCallback(networkCallback);

        runnable = new Runnable() {
            @Override
            public void run() {
                refreshData();
                handler.postDelayed(this,2000);
            }
        };

        handler.post(runnable);

    }

    private void refreshData() {
        getNetworkInfo();
        updateViews();
    }

    private void initViews(){
        ipRefresh=findViewById(R.id.ip_refresh);
        publicIp=findViewById(R.id.tv_ip);
        ipv4=findViewById(R.id.tv_ipv4);
        ipv6=findViewById(R.id.tv_ipv6);
        wifi_dist=findViewById(R.id.tv_wifi_dist);


    }

    @SuppressLint("SetTextI18n")
    private void initListeners(){

        ipRefresh.setOnClickListener(v->{
            setPublicAddress();
        });


    }

    private void updateViews(){

        ipv4.setText(getIPv4Address());
        ipv6.setText(getIPv6Address());
        wifi_dist.setText(dist);

    }

    private void getNetworkInfo(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        int freq = wifiInfo.getFrequency();
        int rssi = wifiInfo.getRssi();
        dist =String.format("~%.1fm", freqRssiToDistance(freq, rssi));

    }



    private void setPublicAddress(){
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("curl", "ifconfig.me");
//            Process process = processBuilder.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            ip=reader.readLine();
//        } catch (IOException  e) {
//            e.printStackTrace();
//        }

        new Async().execute();

    }

    private String getIPv4Address() {
        try {
            List<NetworkInterface> allNetworkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : allNetworkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0"))
                    continue;
                List<InetAddress> allInetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddr : allInetAddresses) {
                    if (!inetAddr.isLoopbackAddress() && inetAddr instanceof Inet4Address) {
                        return inetAddr.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("getIPv4Address()", ex.toString());
        }
        return null;
    }

    private String getIPv6Address() {
        try {
            List<NetworkInterface> allNetworkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : allNetworkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0"))
                    continue;
                List<InetAddress> allInetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddr : allInetAddresses) {
                    if (!inetAddr.isLoopbackAddress() && inetAddr instanceof Inet6Address) {
                        int index = String.valueOf(inetAddr).indexOf("%");
                        if (index != -1) {
                            return Objects.requireNonNull(inetAddr.getHostAddress()).substring(0, index - 1);
                        }
                        return inetAddr.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("getIPv6Address()", ex.toString());
        }
        return null;
    }

    private double freqRssiToDistance(int frequency, int rssi) {
        return Math.pow(10.0D, (27.55D - 20 * Math.log10(frequency) + Math.abs(rssi)) / 20.0D);
    }

    class Async extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {

                super.onPostExecute(s);
                publicIp.setText(Html.fromHtml("<b>"+s+"</b>",Html.FROM_HTML_MODE_COMPACT));


        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb=new StringBuilder("N/A");
            try {
                URL url = new URL("https://ifconfig.me");
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("User-Agent", "curl");
                BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
                int ch;
                sb=new StringBuilder();
                while ((ch=is.read()) !=-1) {
                    sb.append((char)ch);
                }
                Log.i("HELLO", sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    };

    class Async1 extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPostExecute(String s) {
            refreshData();

        }

        @Override
        protected String doInBackground(String... strings) {

            return null;


        }
    };

}



