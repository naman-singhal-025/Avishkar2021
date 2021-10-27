package com.example.avishkar2021.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentForumBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ForumFragment extends Fragment {


    FragmentForumBinding binding;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForumBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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