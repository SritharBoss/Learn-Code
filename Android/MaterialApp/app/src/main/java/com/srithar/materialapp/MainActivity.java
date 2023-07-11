package com.srithar.materialapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.srithar.materialapp.activites.AboutActivity;
import com.srithar.materialapp.activites.SharedPrefActivity;
import com.srithar.materialapp.adapters.FragmentAdapter;
import com.srithar.materialapp.fragments.DialogFragment;
import com.srithar.materialapp.fragments.ComponentsFragment;
import com.srithar.materialapp.fragments.WidgetFragment;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    NavigationView navigationView;
    CoordinatorLayout coordinatorLayout;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewPager();
    }

    private void initViewPager() {
        tabLayout=findViewById(R.id.tabLayout_main);
        viewPager=findViewById(R.id.viewPager_main);
        List<String> title=new ArrayList<>();
        title.add("Dialogs");
        title.add("Widgets");
        title.add("Components");
        tabLayout.addTab(tabLayout.newTab().setText(title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(title.get(2)));

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new DialogFragment());
        fragments.add(new WidgetFragment());
        fragments.add(new ComponentsFragment());

        viewPager.setOffscreenPageLimit(1);
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragments,title);

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void initViews() {
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawerLayout_main);
        coordinatorLayout=findViewById(R.id.coordinator_main);
        fab=findViewById(R.id.fab_main);
        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.drawer_header,null);

        textView=linearLayout.findViewById(R.id.nav_header_txt_view);
        imageView=linearLayout.findViewById(R.id.nav_header_img_view);

        navigationView=findViewById(R.id.nav_view);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.drawer_desc_open,R.string.drawer_desc_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HEYY","Works");
                Animation hyperspaceJump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyperspace_jump);
                textView.startAnimation(hyperspaceJump);
                textView.setText("Hello");
            }
        });
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_main) {
            Snackbar.make(coordinatorLayout,"This can be use for any action",2000).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        switch (item.getItemId()){
            case R.id.lang_english:
                conf.locale= new Locale("en");
                res.updateConfiguration(conf,dm);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.lang_tamil:
                conf.locale= new Locale("ta");
                res.updateConfiguration(conf,dm);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_about:
                Intent about=new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.menu_recycler_view:
                Snackbar.make(coordinatorLayout,"Recycler View Selected",2000).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_shared_pref:
                Intent sharedPref=new Intent(this, SharedPrefActivity.class);
                startActivity(sharedPref);
                break;
        }
        return false;
    }
}