package com.example.avishkar2021.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentHomeBinding;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//fragment to display basic user information like
//name, registration number, branch, profile status, tpo credits, internship status
public class HomeFragment extends Fragment{

    FragmentHomeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        root = binding.getRoot();

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();


        //method for fetching user information from online database
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set profile image from database
                if(snapshot.child("profilePic").exists()) {
                    Picasso.get().load(snapshot.child("profilePic").getValue().toString())
                            .placeholder(R.drawable.avatar)
                            .into(binding.profileImage);
                }

                if(snapshot.child("reg_no").exists())
                {
                    binding.regNo.setText(snapshot.child("reg_no").getValue().toString());
                }
                if(snapshot.child("personal/1").exists())
                {
                    binding.studName.setText(snapshot.child("personal/1").getValue().toString());
                }
                if(snapshot.child("credits").exists())
                {
                    binding.tpoCred.setText(snapshot.child("credits").getValue().toString());
                }
                if(snapshot.child("personal/3").exists())
                {
                    binding.branch.setText(snapshot.child("personal/3").getValue().toString());
                }
                if(snapshot.child("LockStatus").exists() &&
                        snapshot.child("LockStatus").getValue().toString().equals("Locked"))
                {
                    binding.verifyStatus.setText("Locked");
                    try {
                        binding.verifyStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
                    }catch(Exception e)
                    {
                        Log.d("MyError",e.toString());
                    }
                    binding.verified.setImageResource(R.drawable.ic_baseline_block_24);
                    binding.verified.setVisibility(View.VISIBLE);
                }
                else if(snapshot.child("verificationStatus").exists() &&
                        snapshot.child("verificationStatus").getValue().toString().equals("Verified"))
                {
                    binding.verifyStatus.setText("Verified");
                    try {
                        binding.verifyStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.verified));
                    }catch(Exception e)
                    {
                        Log.d("MyError",e.toString());
                    }

                    binding.verified.setImageResource(R.drawable.ic_baseline_verified_24);
                    binding.verified.setVisibility(View.VISIBLE);
                }

                if(snapshot.child("company").exists())
                {
                    binding.company.setText(snapshot.child("company").getValue().toString());
                }

                if(snapshot.child("stipend").exists())
                {
                    binding.stipend.setText(snapshot.child("stipend").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}