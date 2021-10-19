package com.example.avishkar2021.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.avishkar2021.Adapters.CurrentOpeningsAdapter;
import com.example.avishkar2021.databinding.FragmentCurrentOpeningsBinding;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentOpeningsFragment extends Fragment {

    private FragmentCurrentOpeningsBinding binding;
    private CurrentOpeningsAdapter currentOpeningsAdapter;
    ArrayList<AddCompaniesModel> list=new ArrayList<>();
    ListView listView;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentCurrentOpeningsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Please, wait !");

        progressDialog.show();
        database.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                DataSnapshot pshot = snapshot.child("newOpenings");
                for(DataSnapshot dataSnapshot : pshot.getChildren())
                {
                    for(DataSnapshot subshot : dataSnapshot.getChildren())
                    {
                        AddCompaniesModel model;
                        model = subshot.getValue(AddCompaniesModel.class);
                        model.setDate(dataSnapshot.getKey());
                        if(!snapshot.child("reg_com_uid").child(model.getCompany()).
                                child(FirebaseAuth.getInstance().getUid()).exists())
                        {
                            list.add(model);
                        }
                    }
                }
                if(list!=null && list.size()>0) {
                    listView = binding.listView4;
                    currentOpeningsAdapter = new CurrentOpeningsAdapter(getActivity(), list);
                    listView.setAdapter(currentOpeningsAdapter);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}