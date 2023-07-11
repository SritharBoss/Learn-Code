package com.example.restarting.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restarting.ItemContract;
import com.example.restarting.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GroceryViewHolder> {
    private final Context mContext;
    private Cursor mCursor;

    public RecyclerViewAdapter(Context context, Cursor cursor){
        mContext=context;
        mCursor=cursor;
    }

    static class GroceryViewHolder extends RecyclerView.ViewHolder{

        private final TextView textView;
        private final TextView textView1;

        GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView13);
            textView1=itemView.findViewById(R.id.textView14);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View v=layoutInflater.inflate(R.layout.recyclerview_item,parent,false);
        return new GroceryViewHolder(v);
    }

    public void onBindViewHolder(@NonNull RecyclerViewAdapter.GroceryViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)) {
            return;
        }
        String amount=mCursor.getString(mCursor.getColumnIndex(ItemContract.ContractEntry.COLUMN_NAME));
        String count=mCursor.getString(mCursor.getColumnIndex(ItemContract.ContractEntry.COLUMN_COUNT));
        long id=mCursor.getLong(mCursor.getColumnIndex(ItemContract.ContractEntry._ID));
        holder.textView.setText(count);
        holder.textView1.setText(amount);
        holder.itemView.setTag(id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"This is sample Toast",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
