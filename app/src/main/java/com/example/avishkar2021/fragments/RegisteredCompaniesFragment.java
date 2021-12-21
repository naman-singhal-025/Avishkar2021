package com.example.avishkar2021.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.avishkar2021.adapters.RegisteredCompaniesAdapter;
import com.example.avishkar2021.databinding.FragmentRegisteredCompaniesBinding;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisteredCompaniesFragment extends Fragment {

    private FragmentRegisteredCompaniesBinding binding;
    private RegisteredCompaniesAdapter registeredCompaniesAdapter;
    ArrayList<AddCompaniesModel> list=new ArrayList<>();
    ListView listView;
    FirebaseDatabase database;
    private ProgressBar pgsBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentRegisteredCompaniesBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        pgsBar = binding.pBar;
        pgsBar.setVisibility(View.VISIBLE);

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("RegisteredCompanies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    AddCompaniesModel model = dataSnapshot.getValue(AddCompaniesModel.class);
                    list.add(model);
                }
                if(list!=null && list.size()>0) {
                    listView = binding.listView4;
                    registeredCompaniesAdapter = new RegisteredCompaniesAdapter(getActivity(), list);
                    listView.setAdapter(registeredCompaniesAdapter);
                }else {
                    binding.noCompReg.setVisibility(View.VISIBLE);
                }
                pgsBar.setVisibility(View.GONE);
                binding.registeredCompFrame.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pgsBar.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}