package com.example.avishkar2021.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.avishkar2021.adapters.CurrentOpeningsAdapter;
import com.example.avishkar2021.databinding.FragmentCurrentOpeningsBinding;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//fragment to display all the current openings of companies which user's can register to
public class CurrentOpeningsFragment extends Fragment {

    private FragmentCurrentOpeningsBinding binding;
    private CurrentOpeningsAdapter currentOpeningsAdapter;
    ArrayList<AddCompaniesModel> list=new ArrayList<>();
    ListView listView;
    FirebaseDatabase database;
    private ProgressBar pgsBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentCurrentOpeningsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        pgsBar = binding.pBar;
        pgsBar.setVisibility(View.VISIBLE);

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        //method to fetch all current openings data from online database
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
                        try
                        {
                            model.setInternS(snapshot.child("Users").
                                    child(FirebaseAuth.getInstance().getUid()).child("internStatus").getValue().toString());
                            model.setLockS(snapshot.child("Users").
                                    child(FirebaseAuth.getInstance().getUid()).child("LockStatus").getValue().toString());
                            model.setVerS(snapshot.child("Users").
                                    child(FirebaseAuth.getInstance().getUid()).child("verificationStatus").getValue().toString());
                        }catch (Exception e)
                        {

                        }
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
                pgsBar.setVisibility(View.GONE);
                binding.currentOpeningsFrame.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pgsBar.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}