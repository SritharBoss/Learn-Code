package com.example.restarting.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.restarting.R;
import com.example.restarting.ViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class TabHostActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBarLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");

        toolbar.setBackgroundColor(getResources().getColor(R.color.whatsapp_green,getTheme()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.pager_header);
        fab=findViewById(R.id.fab);
        coordinatorLayout=findViewById(R.id.coordinator_layout);


        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar=Snackbar.make(coordinatorLayout,"This is just a Sample",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp,getTheme()));
                else if(position==2)
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground,getTheme()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whatsapp_main_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.search_icon:
                //Snackbar.make(coordinatorLayout,"You Clicked Search",Snackbar.LENGTH_LONG).show();
                break;
            case R.id.new_group: Snackbar.make(coordinatorLayout,"You Clicked New Group",Snackbar.LENGTH_LONG).show();break;
            case R.id.new_broadcast: Snackbar.make(coordinatorLayout,"You Clicked Broadcast",Snackbar.LENGTH_LONG).show();break;
            case R.id.whatsapp_web: Snackbar.make(coordinatorLayout,"You Clicked WhatsApp Web",Snackbar.LENGTH_LONG).show();break;
            case R.id.starred_messages: Snackbar.make(coordinatorLayout,"You Clicked Messages",Snackbar.LENGTH_LONG).show();break;
            case R.id.settings: Snackbar.make(coordinatorLayout,"You Clicked Settings",Snackbar.LENGTH_LONG).show();break;
        }

        return super.onOptionsItemSelected(item);
    }
}
