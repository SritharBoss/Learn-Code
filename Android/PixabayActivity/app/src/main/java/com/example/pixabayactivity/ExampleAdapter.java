package com.example.pixabayactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }

    ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likeCount = currentItem.getLikeCount();
        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText(String.format("Likes: %d", likeCount));
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        ProgressBar progressBar = ((Activity) mContext).findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTextViewCreator;
        private TextView mTextViewLikes;

        private ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.textView_creator);
            mTextViewLikes = itemView.findViewById(R.id.textView_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


}