package com.srithar.tokenkey;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.srithar.tokenkey.adapters.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton fab;
    ViewPager viewPager;
    TabLayout tabLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewPager();
        fab.bringToFront();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        fab=findViewById(R.id.fab_main);
        coordinatorLayout=findViewById(R.id.coordinator_main);
        viewPager=findViewById(R.id.viewpager_main);
        tabLayout=findViewById(R.id.tab_layout_main);
        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_main);


        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

//        fab.setOnClickListener(this);


    }

    private void initViewPager() {

        List<String> title=new ArrayList<>();
        title.add("New");
        title.add("Verify");
        title.add("View All");
        tabLayout.addTab(tabLayout.newTab().setText(title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(title.get(2)));

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new NewFragment());
        fragments.add(new VerifyFragment());
        fragments.add(new ViewFragment());

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragments,title);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.TEXT_ALIGNMENT_GRAVITY);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.fab_main){
            Snackbar.make(coordinatorLayout,"This is a Text",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}