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

public class SolutionPage extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabaseReference;
    private ImageView mimageView;
    private TextView mtitle,mdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_page);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");

        mimageView = (ImageView) findViewById(R.id.mSelectImage);
        mtitle = (TextView) findViewById(R.id.mPostTitle);
        mdesc = (TextView) findViewById(R.id.mPostDesc);


        mPost_key = getIntent().getExtras().getString("blog_id");

        mDatabaseReference.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();

                mtitle.setText(post_title);
                mdesc.setText(post_desc);

                Picasso.get().load(post_image).into(mimageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
