package com.sust.bup_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class SolutionPage2 extends AppCompatActivity {

    private String diseaseName;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_page2);

        Intent intent = getIntent();
        diseaseName = intent.getStringExtra("Disease");

        webView = findViewById(R.id.idWebView);
        setWebView(webView);
    }

    public void setWebView(WebView webView) {
        if(diseaseName.equals("টমেটো লেট ব্লাইট"))
        webView.loadUrl("file:///android_asset/tomatolateblight.html");
        else if(diseaseName.equals("টমেটো আরলি ব্লাইট")){
            webView.loadUrl("file:///android_asset/tomatoearlyblight.html");
        }
    }
}
