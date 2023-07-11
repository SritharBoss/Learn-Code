package com.example.pixabayactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener{
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private TextView textView;
    private static String ss = "Cat";
    private static final String api_key="12359692-2bffe7390665b4c0c81021744";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.recycler_view);
        textView=findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        loadData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ss=textView.getText().toString();
                loadData();
            }
        });
    }

    public void loadData(){

        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://pixabay.com/api/?key="+api_key+"&q=" +ss +"&image_type=photo&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");
                                String largeImageURL=hit.getString("largeImageURL");
                                mExampleList.add(new ExampleItem(imageUrl,creatorName, likeCount,largeImageURL));
                            }

                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            mExampleAdapter.setOnItemClickListener(MainActivity.this);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Log.d("ExtraLog","On item Clicked: "+position);
        ExampleItem clickedItem=mExampleList.get(position);
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra("Creator",clickedItem.getCreator());
        intent.putExtra("ImgURL",clickedItem.getImageUrl());
        intent.putExtra("Likes",clickedItem.getLikeCount());
        intent.putExtra("LargeImgURL",clickedItem.getLargeImageURL());
        startActivity(intent);
    }

}
