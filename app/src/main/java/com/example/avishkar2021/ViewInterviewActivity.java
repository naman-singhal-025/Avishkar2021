package com.example.avishkar2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.ViewInterviewAdapter;
import com.example.avishkar2021.Adapters.ViewInterviewExtraAdapter;
import com.example.avishkar2021.databinding.ActivityVerifyStudentsBinding;
import com.example.avishkar2021.databinding.ActivityViewInterviewBinding;
import com.example.avishkar2021.databinding.FragmentViewInterviewBinding;
import com.example.avishkar2021.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ViewInterviewActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    ListView listView;
    ActivityViewInterviewBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewInterviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String company = intent.getStringExtra("company");

        database = FirebaseDatabase.getInstance();
        storage  =FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Please, wait !");

        database.getReference().child("InterviewExperiences").child(company)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            String s = dataSnapshot.child("name").getValue()
                                    +" ( "+ dataSnapshot.child("date").getValue() + ") ";
                            Users user = new Users();
                            user.setUserName(s);
                            user.setUserId(dataSnapshot.getKey());
                            list.add(user);
                        }
                        try {
                            listView = binding.listViewXY;
                            ViewInterviewExtraAdapter adapter = new ViewInterviewExtraAdapter(ViewInterviewActivity.this,list);
                            listView.setAdapter(adapter);
                        }catch (Exception e)
                        {
                            Toast.makeText(ViewInterviewActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}