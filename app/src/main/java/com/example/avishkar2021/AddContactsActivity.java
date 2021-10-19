package com.example.avishkar2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.AddContactsAdapter;
import com.example.avishkar2021.Adapters.AddUserAdapter;
import com.example.avishkar2021.databinding.ActivityAddContactsBinding;
import com.example.avishkar2021.databinding.ActivityAddStudentsBinding;
import com.example.avishkar2021.models.AddUserModel;
import com.example.avishkar2021.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddContactsActivity extends AppCompatActivity {

    private AddContactsAdapter addContactsAdapter;
    public ArrayList<Users> usersArrayList;
    ActivityAddContactsBinding binding;
    ListView listV;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

        usersArrayList = new ArrayList<>();
        listV = binding.listViewX;
        progressDialog = new ProgressDialog(AddContactsActivity.this);
        progressDialog.setTitle("Adding Contacts");
        progressDialog.setMessage("Uploading data....");
        addContactsAdapter = new AddContactsAdapter(AddContactsActivity.this,usersArrayList);
        binding.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users user = new Users();
                try
                {
                    user.setBranch(binding.userBranch.getText().toString());
                    user.setPhone(binding.userPhone.getText().toString());
                    user.setMail(binding.userMail.getText().toString());
                    user.setUserName(binding.userName.getText().toString());
                    user.setProfilePic(binding.userProfileImage.toString());
                    usersArrayList.add(user);
                    listV.setAdapter(addContactsAdapter);
                    addContactsAdapter.notifyDataSetChanged();

                }catch (Exception e)
                {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && data.getData() != null) {
            if (requestCode == 1) {
                Uri sFile = data.getData();
                binding.userProfileImage.setImageURI(sFile);
            }
        }
        else
        {
            Toast.makeText(AddContactsActivity.this, "No file is selected", Toast.LENGTH_SHORT).show();
        }
    }
}