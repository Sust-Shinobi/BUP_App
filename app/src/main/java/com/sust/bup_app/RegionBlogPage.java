package com.sust.bup_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RegionBlogPage extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabaseReference;
    private TextView mtitle,msoil,mtemp,mrainfall,mcrop1,mcrop2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_blog_page);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Equipment");


        mtitle = (TextView) findViewById(R.id.titleid);
        msoil = (TextView) findViewById(R.id.soilid);
        mtemp = (TextView) findViewById(R.id.tempid);
        mrainfall = (TextView) findViewById(R.id.rainfallid);
        mcrop1 = (TextView) findViewById(R.id.crop1id);
        mcrop2 = (TextView) findViewById(R.id.crop2id);


        mPost_key = getIntent().getExtras().getString("blog_id");

        mDatabaseReference.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_soil = (String) dataSnapshot.child("soil").getValue();
                String post_temp = (String) dataSnapshot.child("temp").getValue();
                String post_rainfall = (String) dataSnapshot.child("rainfall").getValue();
                String post_crop1 = (String) dataSnapshot.child("crop1").getValue();
                String post_crop2 = (String) dataSnapshot.child("crop2").getValue();

                mtitle.setText(post_title);
                msoil.setText(post_soil);
                mtemp.setText(post_temp);
                mrainfall.setText(post_rainfall);
                mcrop1.setText(post_crop1);
                mcrop2.setText(post_crop2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    }

