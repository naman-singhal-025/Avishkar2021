package com.example.avishkar2021.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {

    ActivityAdminMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("Admin Portal");
        setSupportActionBar(toolbar);

        binding.addStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AddStudentsActivity.class);
                startActivity(intent);
            }
        });
        binding.addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AddCompaniesActivity.class);
                startActivity(intent);
            }
        });
        binding.addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AddContactsActivity.class);
                startActivity(intent);
            }
        });
        binding.addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
        binding.verifyStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, VerifyStudentsActivity.class);
                startActivity(intent);
            }
        });

        binding.forumFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}