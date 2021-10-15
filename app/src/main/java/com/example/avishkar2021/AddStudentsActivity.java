package com.example.avishkar2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.AcademicDetailsAdapter;
import com.example.avishkar2021.Adapters.AddUserAdapter;
import com.example.avishkar2021.databinding.ActivityAddStudentsBinding;
import com.example.avishkar2021.databinding.FragmentAcademicBinding;
import com.example.avishkar2021.models.AddUserModel;
import com.example.avishkar2021.models.EditModel;
import com.example.avishkar2021.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddStudentsActivity extends AppCompatActivity {

    private AddUserAdapter addUserAdapter;
    public ArrayList<AddUserModel> addUserModelArrayList;
    ActivityAddStudentsBinding binding;
    ListView listV;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AddStudentsActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Uploading data");


        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                int size = listV.getAdapter().getCount();
                int i=0;
                try {
                    while(i<size && AddUserAdapter.addUserModelArrayList.get(i).getEditTextMail()!=null &&
                            AddUserAdapter.addUserModelArrayList.get(i).getEditTextPassword()!=null
                            && AddUserAdapter.addUserModelArrayList.get(i).getReg_no()!=null)
                    {
                        String mail = AddUserAdapter.addUserModelArrayList.get(i).getEditTextMail();
                        String pass = AddUserAdapter.addUserModelArrayList.get(i).getEditTextPassword();
                        String reg_no = AddUserAdapter.addUserModelArrayList.get(i).getReg_no();
                        auth.createUserWithEmailAndPassword(mail,pass).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.dismiss();
                                        if(task.isSuccessful()){
                                            AddUserModel user  = new AddUserModel();
                                            user.setEditTextMail(mail);
                                            user.setEditTextPassword(pass);
                                            user.setReg_no(reg_no);
                                            String id  = task.getResult().getUser().getUid();
                                            database.getReference().child("Users").child(id).setValue(user);
                                            Toast.makeText(AddStudentsActivity.this,"User Created Successfully",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(AddStudentsActivity.this,task.getException().getMessage()
                                                    ,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        Toast.makeText(AddStudentsActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();

                        i++;
                    }
                    progressDialog.dismiss();
                    finish();
                    startActivity(getIntent());
                }catch (Exception e)
                {
                    Log.d("Mera Error",e.toString());
                    if(i==0)
                    {
                        Toast.makeText(AddStudentsActivity.this, "Enter at least one user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        listV = binding.listView2;
        addUserModelArrayList = populateList();
        addUserAdapter = new AddUserAdapter(AddStudentsActivity.this,addUserModelArrayList);
        listV.setAdapter(addUserAdapter);
    }
    private ArrayList<AddUserModel> populateList(){

        ArrayList<AddUserModel> list = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            AddUserModel addUserModel = new AddUserModel();
            addUserModel.setNumbering(String.valueOf(i));
            list.add(addUserModel);
        }

        return list;
    }
}