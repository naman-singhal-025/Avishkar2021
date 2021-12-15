package com.example.avishkar2021.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.avishkar2021.databinding.ActivityDisplayNoticeBinding;

public class DisplayNoticeActivity extends AppCompatActivity {

    ActivityDisplayNoticeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Notice");

        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        String desc = intent.getStringExtra("description");

        binding.dispDesc.setText(desc);
        binding.dispSubject.setText(subject);
    }
}