package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.adapters.VerifyUserAdapter;
import com.example.avishkar2021.databinding.ActivityVerifyStudentsBinding;
import com.example.avishkar2021.models.VerifyUserModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//activity to display all the students that are so far registered by admin(s)
public class VerifyStudentsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    VerifyUserAdapter adapter;
    ProgressDialog progressDialog;
    ListView listV;
    ArrayList<VerifyUserModel> list = new ArrayList<>();
    ActivityVerifyStudentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setting customized action bar
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("Verify Students");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Please, wait !");

        progressDialog.show();

        InternetConnection internetConnection = new InternetConnection(VerifyStudentsActivity.this);
        internetConnection.execute();


        //display all students names and their verification/ lock status
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VerifyUserModel user = new VerifyUserModel();
                    user.setUid(dataSnapshot.getKey());
                    user.setRegistration_no(dataSnapshot.child("reg_no").getValue().toString());
                    if(dataSnapshot.child("verificationStatus").exists())
                    {
                        user.setStatus(dataSnapshot.child("verificationStatus").getValue().toString());
                    }
                    if(dataSnapshot.child("LockStatus").exists())
                    {
                        if(dataSnapshot.child("LockStatus").getValue().toString().equals("Locked"))
                        user.setStatus("Locked");
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
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}