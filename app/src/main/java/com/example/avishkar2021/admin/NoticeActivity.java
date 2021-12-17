package com.example.avishkar2021.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.avishkar2021.adapters.NoticeFragmentsAdapter;
import com.example.avishkar2021.databinding.ActivityNoticeBinding;

public class NoticeActivity extends AppCompatActivity {

    ActivityNoticeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager2.setAdapter(new NoticeFragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout2.setupWithViewPager(binding.viewPager2);

        getSupportActionBar().hide();
    }
}