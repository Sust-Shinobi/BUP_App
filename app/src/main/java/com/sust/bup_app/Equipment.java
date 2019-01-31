package com.sust.bup_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Equipment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private RecyclerView mBlogList;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        getSupportActionBar().setTitle("কৃষি সরঞ্জাম");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Equipment");
        mBlogList = (RecyclerView) findViewById(R.id.region_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

        drawer = (DrawerLayout) findViewById(R.id.drawerid);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_id);
        navigationView.setItemIconTintList(null);
        drawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.nav_open,R.string.nav_close);

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RegionBlog,Equipment.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RegionBlog, Equipment.BlogViewHolder>(
                RegionBlog.class,
                R.layout.region_view,
                Equipment.BlogViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(Equipment.BlogViewHolder viewHolder, RegionBlog model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setSoil(model.getSoil());
                viewHolder.setTemp(model.getTemp());
                viewHolder.setRainfall(model.getRainfall());



                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Equipment.this,RegionBlogPage.class);
                        intent.putExtra("blog_id",post_key);
                        startActivity(intent);
                    }
                });



            }

        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;



        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;


        }

        public void setTitle(String title) {
            TextView post_tile = mView.findViewById(R.id.mPostTitle);
            post_tile.setText(title);
        }

        public void setSoil(String soil) {
            TextView post_desc = mView.findViewById(R.id.soilid);
            post_desc.setText(soil);
        }

        public void setTemp(String temp) {
            TextView post_desc = mView.findViewById(R.id.tempid);
            post_desc.setText(temp);
        }

        public void setRainfall(String rainfall) {
            TextView post_desc = mView.findViewById(R.id.rainfallid);
            post_desc.setText(rainfall);
        }
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
}
