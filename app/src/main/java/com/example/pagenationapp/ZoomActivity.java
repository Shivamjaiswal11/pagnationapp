package com.example.pagenationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class ZoomActivity extends AppCompatActivity {
    ZoomageView myZoomageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        myZoomageView=findViewById(R.id.myZoomageView);

        Glide.with(this).load(getIntent().getStringExtra("image")).into(myZoomageView);
    }
}