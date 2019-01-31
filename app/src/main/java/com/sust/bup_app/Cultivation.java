package com.sust.bup_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Cultivation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation);

        getSupportActionBar().setTitle("চাষ পদ্ধতি");

        drawer = (DrawerLayout) findViewById(R.id.drawerid);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_id);
        navigationView.setItemIconTintList(null);
        drawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.nav_open,R.string.nav_close);

        Spinner spinner = findViewById(R.id.idCropSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.crops_list,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        webView = findViewById(R.id.idWebView);

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerid);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Intent intent = null;

        switch (id){

            case R.id.home:
                intent = new Intent(this,MainActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.community:
                    intent = new Intent(this,Community.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.solution:
                intent = new Intent(this,Solution.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.cultivation:
                intent = new Intent(this,Cultivation.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.seeds:
                intent = new Intent(this,Seeds.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.fertilizer:
                intent = new Intent(this,Fertilizer.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.equipment:
                intent = new Intent(this,Equipment.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_help:
                intent = new Intent(this,Helpline.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_feedback:
                intent = new Intent(this,Feedback.class);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

            webView.loadUrl("file:///android_asset/tomato.html");
        }else if(position == 1) {
            webView.loadUrl("file:///android_asset/potato.html");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
