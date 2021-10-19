package com.example.avishkar2021.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.avishkar2021.Adapters.AddContactsAdapter;
import com.example.avishkar2021.AddContactsActivity;
import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.ActivityAddContactsBinding;
import com.example.avishkar2021.databinding.FragmentContactsBinding;
import com.example.avishkar2021.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends Fragment {

    private AddContactsAdapter addContactsAdapter;
    public ArrayList<Users> usersArrayList;
    FragmentContactsBinding binding;
    ListView listV;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();
        usersArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Contacts");
        progressDialog.setMessage("Fetching data....");

        database.getReference().child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Users user = dataSnapshot.getValue(Users.class);
                    usersArrayList.add(user);
                }
                if(usersArrayList!=null && usersArrayList.size()>0) {
                    listV = binding.listViewXx;
                    AddContactsAdapter addContactsAdapter = new AddContactsAdapter(getContext(), usersArrayList);
                    listV.setAdapter(addContactsAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return binding.getRoot();
    }
}