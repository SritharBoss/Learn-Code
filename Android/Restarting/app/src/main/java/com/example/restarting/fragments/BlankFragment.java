package com.example.restarting.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restarting.Adapters.ListViewAdapter;
import com.example.restarting.CustomListView;
import com.example.restarting.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BlankFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> text=new ArrayList<>();
    private ArrayList<Integer> image=new ArrayList<>();

    public BlankFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_custom_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=view.findViewById(R.id.listView);

        String[] a= new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"};
        Integer[] b={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        text.addAll(Arrays.asList(a));
        image.addAll(Arrays.asList(b));

        final ListViewAdapter listViewAdapter=new ListViewAdapter(getContext(),text,image);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("Do You Want To delete this item");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Removed Item: \n"+text.get(position),Toast.LENGTH_LONG).show();
                        text.remove(position);
                        image.remove(position);
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return true;
            }
        });

        ViewCompat.setNestedScrollingEnabled(listView, true);
    }
}
