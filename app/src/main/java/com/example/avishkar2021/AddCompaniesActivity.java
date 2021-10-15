package com.example.avishkar2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddCompaniesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companies);
        getSupportActionBar().hide();
    }
}