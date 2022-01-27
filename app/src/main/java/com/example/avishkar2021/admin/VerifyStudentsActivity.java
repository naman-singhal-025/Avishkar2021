package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.Collections;
import java.util.Comparator;

//activity to display all the students that are so far registered by admin(s)
public class VerifyStudentsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    VerifyUserAdapter adapter;
    ProgressDialog progressDialog;
    ListView listV;
    ArrayList<VerifyUserModel> list = new ArrayList<>();
    ActivityVerifyStudentsBinding binding;
    AlertDialog dialog;
    AlertDialog.Builder builder;
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


        // setup the alert builder
        builder = new AlertDialog.Builder(VerifyStudentsActivity.this);
        builder.setTitle("Sort By");

        // add a list
        String[] sorting = {"Registration number(asc)","Registration number(desc)","Verification Status"};
        builder.setItems(sorting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    adapter.mySort(which);
                    adapter.notifyDataSetChanged();
                }
        });
        dialog = builder.create();
        binding.myfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create and show the alert dialog
                dialog.show();
            }
        });



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


                /**
                 * Enabling Search Filter
                 * */
                binding.inputSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}