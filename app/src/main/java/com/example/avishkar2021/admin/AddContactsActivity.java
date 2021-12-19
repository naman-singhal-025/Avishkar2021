package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.adapters.AddContactsAdapter;
import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.ActivityAddContactsBinding;
import com.example.avishkar2021.models.UsersModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class AddContactsActivity extends AppCompatActivity {

    private AddContactsAdapter addContactsAdapter;
    public ArrayList<UsersModel> usersArrayList;
    ActivityAddContactsBinding binding;
    ListView listV;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    String key,f_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        usersArrayList = new ArrayList<>();
        listV = binding.listViewX;
        progressDialog = new ProgressDialog(AddContactsActivity.this);
        progressDialog.setTitle("Adding Contacts");
        progressDialog.setMessage("Uploading data....");


        database.getReference().child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UsersModel user = dataSnapshot.getValue(UsersModel.class);
                    usersArrayList.add(user);
                }
                try {
                    listV = binding.listViewX;
                    addContactsAdapter = new AddContactsAdapter(AddContactsActivity.this, usersArrayList);
                    listV.setAdapter(addContactsAdapter);
                }catch (Exception e)
                {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        binding.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersModel user = new UsersModel();
                try {
                    user.setBranch(binding.userBranch.getText().toString());
                    user.setPhone(binding.userPhone.getText().toString());
                    user.setMail(binding.userMail.getText().toString());
                    user.setUserName(binding.userName.getText().toString());
                    if (f_uri != null && f_uri != "" && isValid(user)) {
                        user.setProfilePic(f_uri.toString());
                        key = UUID.randomUUID().toString();
                        user.setUserId(key);
                        database.getReference().child("Contacts").child(key).setValue(user);
                        addContactsAdapter.notifyDataSetChanged();
                        binding.userBranch.setText(null);
                        binding.userPhone.setText(null);
                        binding.userMail.setText(null);
                        binding.userName.setText(null);
                        binding.userProfileImage.setImageResource(R.drawable.avatar);
                        f_uri=null;
                    } else {
                        Toast.makeText(AddContactsActivity.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(AddContactsActivity.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        binding.listViewX.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(AddContactsActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this contact")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UsersModel user = usersArrayList.get(i);
                                try {
                                    StorageReference ref = storage.getReferenceFromUrl(user.getProfilePic());
                                    ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                    database.getReference().child("Contacts").child(user.getUserId()).setValue(null);
                                }catch (Exception e)
                                {
                                    Toast.makeText(AddContactsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("DoError",e.toString());
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                return false;
            }
        });
    }

    private boolean isValid(UsersModel user) {
        return user.getUserName()!=null && user.getBranch()!=null &&
            user.getPhone()!=null && user.getMail()!=null &&
                !user.getUserName().equals("") && !user.getBranch().equals("") &&
                !user.getPhone().equals("") && !user.getMail().equals("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && data.getData() != null) {
            if (requestCode == 1) {
                f_uri="";
                progressDialog.setTitle("Adding Image");
                progressDialog.setMessage("Uploading data....");
                progressDialog.show();
                Uri sFile = data.getData();
                binding.userProfileImage.setImageURI(sFile);
                key = UUID.randomUUID().toString();
                StorageReference reference = storage.getReference().child("contactsPic").child(key);
                reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                binding.userProfileImage.setImageURI(uri);
//                                database.getReference().child("Contacts").child(key).child("profilePic").setValue(uri.toString());
                                f_uri = uri.toString();
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            }
        }
        else
        {
            Toast.makeText(AddContactsActivity.this, "No file is selected", Toast.LENGTH_SHORT).show();
        }
    }
}