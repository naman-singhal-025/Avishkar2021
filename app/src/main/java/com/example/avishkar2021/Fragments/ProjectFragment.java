package com.example.avishkar2021.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.PersonalDetailsAdapter;
import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentProjectBinding;
import com.example.avishkar2021.models.ProjectInternModel;
import com.example.avishkar2021.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProjectFragment extends Fragment {

    FragmentProjectBinding binding;
    FirebaseDatabase database;
    String lockStatus="",verStatus="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProjectBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users  = snapshot.getValue(Users.class);
                        if(snapshot.child("projects").exists())
                        {
                            binding.projectTitle.setText(snapshot.child("projects").child("title").getValue().toString());
                            binding.projectDescription.setText(snapshot.child("projects").child("pdescription").getValue().toString());
                            binding.internDescription.setText(snapshot.child("projects").child("idescription").getValue().toString());
                            binding.organisation.setText(snapshot.child("projects").child("organisation").getValue().toString());
                        }

                        if(snapshot.child("LockStatus").exists())
                        {
                            if(snapshot.child("LockStatus").getValue().toString().equals("Locked"))
                            {
                                lockStatus = "Locked";
                            }
                        }

                        if(snapshot.child("verificationStatus").exists())
                        {
                            if(snapshot.child("verificationStatus").getValue().toString().equals("Verified"))
                            {
                                verStatus = "Verified";

                            }
                        }

                        if(verStatus.equals("Verified") || lockStatus.equals("Locked"))
                        {
                            binding.projectTitle.setEnabled(false);
                            binding.projectDescription.setEnabled(false);
                            binding.internDescription.setEnabled(false);
                            binding.organisation.setEnabled(false);
                        }
                        else
                        {
                            binding.projectTitle.setEnabled(true);
                            binding.projectDescription.setEnabled(true);
                            binding.internDescription.setEnabled(true);
                            binding.organisation.setEnabled(true);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        binding.pisaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    ProjectInternModel projectInternModel = new ProjectInternModel();
                    projectInternModel.setTitle(binding.projectTitle.getText().toString());
                    projectInternModel.setPDescription(binding.projectDescription.getText().toString());
                    projectInternModel.setIDescription(binding.internDescription.getText().toString());
                    projectInternModel.setOrganisation(binding.organisation.getText().toString());
                    HashMap<String, Object> obj = new HashMap<>();
                    obj.put("projects", projectInternModel);
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                    Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }
}