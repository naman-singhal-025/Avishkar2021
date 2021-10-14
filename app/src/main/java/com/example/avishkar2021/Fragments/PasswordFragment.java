package com.example.avishkar2021.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.databinding.FragmentPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordFragment extends Fragment {

    FragmentPasswordBinding binding;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPasswordBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        binding.savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = binding.oldPass.getText().toString();
                String newPassword = binding.newPass.getText().toString();
                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(getActivity(), "Enter current password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPassword.length()<6){
                    Toast.makeText(getActivity(), "Password length must be at-least 6!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePassword(oldPassword,newPassword);
            }
        });
        return binding.getRoot();
    }

    private void updatePassword(String oldPassword, String newPassword) {
        //get current user
        FirebaseUser user = auth.getCurrentUser();

        //before changing password re-authenticate user
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //successfully authenticated, begin update

                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //updated successfully
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //authentication failed
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}