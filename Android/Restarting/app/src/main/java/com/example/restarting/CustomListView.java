package com.example.restarting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restarting.Adapters.ListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomListView extends AppCompatActivity {

    ListView listView;
    ArrayList<String> text=new ArrayList<>();
    ArrayList<Integer> image=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MaterialActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Custom List View Activity");

        listView=findViewById(R.id.listView);

        String[] a= new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"};
        Integer[] b={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        text.addAll(Arrays.asList(a));
        image.addAll(Arrays.asList(b));

        final ListViewAdapter listViewAdapter=new ListViewAdapter(CustomListView.this,text,image);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CustomListView.this);
                builder.setTitle("Do You Want To delete this item");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Removed Item: \n"+text.get(position),Toast.LENGTH_LONG).show();
                        text.remove(position);
                        image.remove(position);
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
