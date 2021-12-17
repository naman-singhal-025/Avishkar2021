package com.example.avishkar2021.fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentPhotoResumeBinding;
import com.example.avishkar2021.models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PhotoResumeFragment extends Fragment {

    FragmentPhotoResumeBinding binding;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    Users users;
    private String lock="Not Locked",ver="Not Verified";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotoResumeBinding.inflate(inflater, container, false);
        binding.downloadBtn.setVisibility(View.GONE);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(container.getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Please, wait !");

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try
                        {
                            users = snapshot.getValue(Users.class);
//                        set profile image from database
                            Picasso.get().load(users.getProfilePic())
                                    .placeholder(R.drawable.avatar)
                                    .into(binding.profileImage);

                            if (snapshot.child("resume").exists()) {
                                binding.pdfName.setText("Resume Available");
                                binding.downloadBtn.setVisibility(View.VISIBLE);
                                DrawableCompat.setTint(binding.uploadpdf.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorPrimary));
                            }

                            if (snapshot.child("LockStatus").exists()) {
                                lock = snapshot.child("LockStatus").getValue().toString();
                            }

                            if (snapshot.child("verificationStatus").exists()) {
                                ver = snapshot.child("verificationStatus").getValue().toString();
                            }
                        }catch (Exception e)
                        {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock.equals("Locked"))
                {
                    Toast.makeText(getActivity(), "Your account is locked!!!", Toast.LENGTH_SHORT).show();
                }
                else if(ver.equals("Verified"))
                {
                    Toast.makeText(getActivity(), "Your account is already verified", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }


            }
        });

        binding.uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 2);
            }
        });

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getResume()));
//                startActivity(intent);
                String filename = "resume";
                try{
                    filename = users.getReg_no();
                }catch (Exception e)
                {

                }
                downloadFile(getContext(),filename,".pdf", Environment.DIRECTORY_DOWNLOADS,users.getResume());
                };

        });
        return binding.getRoot();
    }


    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
        return downloadmanager.enqueue(request);
    }


        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(data!=null && data.getData() != null)
            {
                progressDialog.show();
                Uri sFile  = data.getData();
                if(requestCode==1)
                {
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