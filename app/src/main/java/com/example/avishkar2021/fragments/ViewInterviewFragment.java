package com.example.avishkar2021.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.avishkar2021.adapters.ViewInterviewAdapter;
import com.example.avishkar2021.user.ViewInterviewActivity;
import com.example.avishkar2021.databinding.FragmentViewInterviewBinding;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewInterviewFragment extends Fragment {

    FirebaseDatabase database;
    ProgressDialog progressDialog;
    GridView listView;
    FragmentViewInterviewBinding binding;
    ArrayList<String> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewInterviewBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Please, wait !!");

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        database = FirebaseDatabase.getInstance();

        database.getReference().child("InterviewExperiences")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    for(DataSnapshot subshot : dataSnapshot.getChildren())
                    {
                        list.add(subshot.child("company").getValue().toString());
                        break;
                    }

                }
               try {
                    listView = binding.listViewxx;
                    ViewInterviewAdapter adapter = new ViewInterviewAdapter(getActivity(),list,0);
                    listView.setAdapter(adapter);

               }catch (Exception e)
               {
                   Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
               }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.listViewxx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ViewInterviewActivity.class);
                intent.putExtra("company",adapterView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}