package com.example.restarting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restarting.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpinnerActivity extends AppCompatActivity {
    Spinner spinner,spinner1;
    TextView textView;
    String jsonString=null;
    JSONObject jsonObject;
    ArrayList<String> countries;
    ArrayList<String> cities;
    ArrayAdapter arrayAdapter;
    ArrayAdapter arrayAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        textView=findViewById(R.id.textView5);
        spinner=findViewById(R.id.spinner);
        spinner1=findViewById(R.id.spinner1);

        countries=new ArrayList<>();
        cities=new ArrayList<>();

        jsonString=getStringFromJsonFile();

        arrayAdapter=getCountryFromJson();
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("Select Country");

        arrayAdapter1=getCityFromCountry("Afghanistan");
        spinner1.setAdapter(arrayAdapter1);
        spinner1.setPrompt("Select City");

        Toast.makeText(getApplicationContext(),""+arrayAdapter.getCount(),Toast.LENGTH_LONG).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter1=getCityFromCountry(countries.get(position));
                textView.setText(cities.get(0));
                arrayAdapter.notifyDataSetChanged();
                spinner1.setAdapter(arrayAdapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(cities.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public ArrayAdapter getCityFromCountry(String s) {

        try {
            cities.clear();
            JSONArray cityJR=jsonObject.getJSONArray(s);
            for(int i=0;i<cityJR.length();i++)
            {
                cities.add(cityJR.get(i).toString());
            }
            Collections.sort(cities);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    public String getStringFromJsonFile(){

        try {
            InputStream jsonIpStream=getApplicationContext().getAssets().open("countries.txt");
            byte[] bytes = new byte[1000];
            StringBuilder x = new StringBuilder();
            int numRead = 0;
            while ((numRead = jsonIpStream.read(bytes)) >= 0) {
                x.append(new String(bytes, 0, numRead));
            }
            return x.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayAdapter getCountryFromJson(){

        try {
            countries.clear();
            jsonObject=new JSONObject(jsonString);
            JSONArray countryJR=jsonObject.names();
            for(int i = 0; i<countryJR.length(); i++)
            {
                countries.add(countryJR.get(i).toString());
            }
            Collections.sort(countries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
    }
}
