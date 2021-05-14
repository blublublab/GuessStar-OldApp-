package com.demo.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }
}