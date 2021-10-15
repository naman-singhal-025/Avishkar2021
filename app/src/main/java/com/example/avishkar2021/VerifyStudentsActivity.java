package com.example.avishkar2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.AddUserAdapter;
import com.example.avishkar2021.Adapters.VerifyUserAdapter;
import com.example.avishkar2021.databinding.ActivityVerifyStudentsBinding;
import com.example.avishkar2021.models.Users;
import com.example.avishkar2021.models.VerifyUserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VerifyStudentsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    VerifyUserAdapter adapter;
    ListView listV;
    ArrayList<VerifyUserModel> list = new ArrayList<>();
    ActivityVerifyStudentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VerifyUserModel user = new VerifyUserModel();
                    user.setUid(dataSnapshot.getKey());
                    user.setRegistration_no(dataSnapshot.child("reg_no").getValue().toString());
                    if(dataSnapshot.child("status").exists())
                    {
                        user.setStatus(dataSnapshot.child("status").getValue().toString());
                    }
                    else
                    {
                        user.setStatus("Verify");
                    }
                    list.add(user);
                }
                listV = binding.listView3;
                adapter = new VerifyUserAdapter(VerifyStudentsActivity.this,list);
                listV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}