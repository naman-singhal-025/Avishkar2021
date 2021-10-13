package com.example.avishkar2021.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentPhotoResumeBinding;
import com.example.avishkar2021.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PhotoResumeFragment extends Fragment {

    FragmentPhotoResumeBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    DatabaseReference database2;
    Users users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotoResumeBinding.inflate(inflater, container, false);
        binding.downloadBtn.setVisibility(View.GONE);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(container.getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Please, wait !");

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users  = snapshot.getValue(Users.class);
                        //set profile image from database
                        Picasso.get().load(users.getProfilePic())
                                .placeholder(R.drawable.avatar)
                                .into(binding.profileImage);
                        String resume = users.getResume();
                        if(resume!="")
                        {
                            binding.pdfName.setText("Resume Available");
                            binding.downloadBtn.setVisibility(View.VISIBLE);
                            DrawableCompat.setTint(binding.uploadpdf.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                progressDialog.show();
                startActivityForResult(intent, 1);

            }
        });

        binding.uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                progressDialog.show();
                startActivityForResult(intent, 2);
            }
        });

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getResume()));
                startActivity(intent);
                };

        });
        return binding.getRoot();
    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(data.getData() != null)
            {
                if(requestCode==1)
                {
                    Uri sFile  = data.getData();
                    binding.profileImage.setImageURI(sFile);

                    final StorageReference reference = storage.getReference().child("profile_pictures")
                            .child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("profilePic").setValue(uri.toString());
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Uri sFile  = data.getData();
                    final StorageReference reference = storage.getReference().child("resume")
                            .child(FirebaseAuth.getInstance().getUid());
                    reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("resume").setValue(uri.toString());
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
            else
            {
                Toast.makeText(getActivity(), "No file is selected", Toast.LENGTH_SHORT).show();
            }
        }
}