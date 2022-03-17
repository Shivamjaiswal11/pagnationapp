package com.example.pagenationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pagenationapp.Network.ServiceGenerator;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageload();
    }

    private void imageload() {

        Requestinterface api = ServiceGenerator.createService(Requestinterface.class);
    }
}