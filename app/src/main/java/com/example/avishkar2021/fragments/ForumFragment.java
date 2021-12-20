package com.example.avishkar2021.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.databinding.FragmentForumBinding;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForumFragment extends Fragment {


    FragmentForumBinding binding;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForumBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetConnection internetConnection = new InternetConnection(getContext());
                internetConnection.execute();
                try
                {
                    database.getReference().child("feedback").
                            child(FirebaseAuth.getInstance().getUid()).
                            setValue(binding.feedback.getText().toString());
                    Toast.makeText(getContext(), "Sent", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();

    }
}