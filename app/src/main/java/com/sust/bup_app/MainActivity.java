package com.sust.bup_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 118;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";
    private String diseaseName;


    private static final String MODEL_FILE = "file:///android_asset/retrained_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/retrained_labels.txt";


    private Classifier classifier;


    private FloatingActionButton mButtonSelectPhoto;
    private FloatingActionButton mButtonTakePhoto;
    private Button mButtonDiagnose;
    private Button mSolutionButton;
    private ImageView mImageView;
    private TextView mTextView;
    private File temFile;
    private Bitmap mDiagnosisBitmap;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;



    private static final int PHOTO_REQUEST_CAMERA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int PHOTO_REQUEST_CODE = 4;
    private static final int PERMISSION_REQUEST_CODE = 5;
    private static final String PHOTO_FILE_NAME = "temp_pic.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final AssetManager assetManager = getAssets();
        classifier = TensorFlowImageClassifier.create(assetManager,MODEL_FILE,LABEL_FILE,INPUT_SIZE,IMAGE_MEAN,IMAGE_STD,INPUT_NAME,OUTPUT_NAME);

        mButtonSelectPhoto =  findViewById(R.id.idButtonSelectPhoto);
        mButtonTakePhoto =  findViewById(R.id.idButtonTakePhoto);
        mButtonDiagnose = (Button) findViewById(R.id.idButtonDiagnose);
        mSolutionButton = findViewById(R.id.idSolutionBtn);

        drawer = (DrawerLayout) findViewById(R.id.drawerid);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_id);
        navigationView.setItemIconTintList(null);
        drawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.nav_open,R.string.nav_close);

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.idTextClassificationResult);

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, PHOTO_REQUEST_CODE);
                        Log.d("Permission","Failed");
                    }
                    else {
                        camera(view);
                    }
                }
            }
        });
        mButtonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery(view);
            }
        });

        mButtonDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDiagnosisBitmap != null) {
                    final List<Classifier.Recognition> results = classifier.recognizeImage(mDiagnosisBitmap);
                    System.out.println(results.size());
                    for (final Classifier.Recognition result : results) {
                        System.out.println("Result:" + result.getTitle() + " " + result.getConfidence() + result.toString());
                    }
                    diseaseName = results.get(0).getTitle();
                    mTextView.setText(results.get(0).getTitle());
                } else
                {
                    Snackbar.make(view.getRootView(), "No image detected!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        mSolutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SolutionPage2.class);
                intent.putExtra("Disease",diseaseName);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY){
            if (data!=null){
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    Log.d("Message",uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Success!!!");
                mDiagnosisBitmap = scaleImage(bitmap);
                mImageView.setImageBitmap(mDiagnosisBitmap);

            }
        }else if (requestCode == PHOTO_REQUEST_CAMERA){
            if (hasSdcard()){
                Uri uri = Uri.fromFile(temFile);
                Log.d("Message 2",uri.getPath().toString());
                Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
                mDiagnosisBitmap = scaleImage(bitmap);
                mImageView.setImageBitmap(mDiagnosisBitmap);
            }


        }else if (requestCode == PHOTO_REQUEST_CUT){
            if(data!=null){
                Bitmap bitmap = data.getParcelableExtra("data");
                mImageView.setImageBitmap(bitmap);
            }try {
                temFile.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void gallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void camera(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (hasSdcard()){
            Log.d("Has sd card","true");
            temFile = new File(getApplicationContext().getExternalFilesDir("files").getAbsolutePath(),PHOTO_FILE_NAME);
            Uri uri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID +".provider",temFile);
            Log.d("FileAccess","Success!!");

            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
            Log.d("Hello WOrld","This works");
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

    }

    private boolean hasSdcard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public Bitmap scaleImage(Bitmap bitmap){
        int orignalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = ((float)INPUT_SIZE)/orignalWidth;
        float scaleHeight = ((float)INPUT_SIZE)/originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap,0,0,orignalWidth,originalHeight,matrix,true);
        return scaledBitmap;
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

        switch(id){

            case R.id.home:
                intent = new Intent(this,MainActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.community:
                intent = new Intent(this,Community.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.solution:
                intent = new Intent(this,MapActivity.class);
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
