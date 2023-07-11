package com.example.restarting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restarting.R;

import java.util.ArrayList;


public class ListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> tv;
    private ArrayList<Integer> image;

    public ListViewAdapter(Context context, ArrayList<String> tv, ArrayList<Integer> image){
        super(context,R.layout.list_item,tv);
        this.context=context;
        this.tv=tv;
        this.image=image;
    }

    @Override
    public int getCount() {
        return tv.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.list_item,parent,false);
        TextView textView = convertView.findViewById(R.id.textView7);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        textView.setText(tv.get(position));
        imageView.setImageResource(image.get(position));
        return convertView;
    }
}
