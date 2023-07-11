package com.example.airtelchannellist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ArrayAdapter<String> stringArrayAdapter;
    ListView listView;
    String[] stringArray;
    Boolean mDoubleBackPressed=false;
    Animation animation;
    RatingBar ratingBar;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Channel Number Identifier");
        textView =findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);
        listView=findViewById(R.id.listView1);
        textView.setSelected(true);
        ratingBar=findViewById(R.id.ratingBar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.isSelected())
                    textView.setSelected(false);
                else
                    textView.setSelected(true);
            }
        });

        doAirtelTV();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.stringArrayAdapter.getFilter().filter(s, new Filter.FilterListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFilterComplete(int count) {
                        if(count==0) {
                            textView.setText("No Results Found");
                            resetTextView();
                        }
                        else{
                            textView.setText(count+" Results Found");
                            resetTextView();
                        }
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {
                listView.scrollTo(0,0);
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=stringArrayAdapter.getItem(position);
                animation=AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_in_left);
                textView.setText(value);
                textView.startAnimation(animation);
                resetTextView();
                FancyToast.makeText(getApplicationContext(),"Nothing is gonna happen. Change using your remote",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.airtelTV:
                doAirtelTV();
                break;
            case R.id.sunDirect:
                doSunDirect();
                break;
            case R.id.isBold:
                if(!item.isChecked()) {
                    item.setChecked(true);
                    textView.setTypeface(null, Typeface.BOLD);
                }else{
                    item.setChecked(false);
                    textView.setTypeface(null,Typeface.NORMAL);
                }
                break;
            case R.id.rate_me:
                builder=new AlertDialog.Builder(this);
                LayoutInflater inflater=getLayoutInflater();
                View v=inflater.inflate(R.layout.rating_bar,(ViewGroup)findViewById(R.id.linear_layout1),true);
                final RatingBar ratingBar1=v.findViewById(R.id.ratingBar);
                builder.setTitle("Please Rate Me");
                builder.setView(v);
                builder.setIcon(R.drawable.ic_rate_review_black_24dp);
                builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FancyToast.makeText(getApplicationContext(),"Thanks for Rating Me :"+ratingBar1.getRating(),Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                break;
            case R.id.exit:
                BuildAlertDialogExit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void BuildAlertDialogExit(){
        builder=new AlertDialog.Builder(this);
        builder.setMessage("Do You Want to Close the Application");
        builder.setTitle("Confirm");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setNeutralButton("Nothing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void doAirtelTV(){
        stringArray=getResources().getStringArray(R.array.channel_list);
        stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, stringArray);
        listView.setAdapter(stringArrayAdapter);
        textView.setText(R.string.tv_Intro);
    }

    public void doSunDirect(){
        stringArray=getResources().getStringArray(R.array.channel_list1);
        stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, stringArray);
        listView.setAdapter(stringArrayAdapter);
        textView.setText(R.string.tv_Intro1);
    }

    public void resetTextView(){
        if(stringArray.length==getResources().getStringArray(R.array.channel_list).length) {
            final String temp=getResources().getString(R.string.tv_Intro);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(temp);
                }
            },3000);
        }
        else if(stringArray.length==getResources().getStringArray(R.array.channel_list1).length) {
            final String temp=getResources().getString(R.string.tv_Intro1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(temp);
                }
            },3000);
        }
    }

    @Override
    public void onBackPressed() {
        if(!mDoubleBackPressed)
        {
            mDoubleBackPressed=true;
            FancyToast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDoubleBackPressed=false;
                }
            },2000);
        }
        else super.onBackPressed();
    }

}
