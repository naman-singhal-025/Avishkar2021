package com.example.avishkar2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.avishkar2021.databinding.ActivityStudentDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class StudentDetailActivity extends AppCompatActivity {

    ActivityStudentDetailBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    private String resume;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Please, wait !");
//        progressDialog.show();

        uid = getIntent().getStringExtra("uid");
        database.getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.regNo.setText(snapshot.child("reg_no").getValue().toString());
                binding.mailId.setText(snapshot.child("editTextMail").getValue().toString());
                if(snapshot.child("personal").exists())
                {
                    DataSnapshot subshot  = snapshot.child("personal");
                    binding.studName.setText(subshot.child("1").getValue().toString());
                    binding.Course.setText(subshot.child("2").getValue().toString());
                    binding.branch.setText(subshot.child("3").getValue().toString());
                    binding.gender.setText(subshot.child("6").getValue().toString());
                    binding.category.setText(subshot.child("7").getValue().toString());
                    binding.physicallyChallenged.setText(subshot.child("8").getValue().toString());
                    binding.residentStatus.setText(subshot.child("9").getValue().toString());
                    binding.guardian.setText(subshot.child("10").getValue().toString());
                    binding.presentAdd.setText(subshot.child("11").getValue().toString());
                    binding.permanentAdd.setText(subshot.child("12").getValue().toString());
                    binding.maritalStatus.setText(subshot.child("13").getValue().toString());
                    binding.state.setText(subshot.child("14").getValue().toString());
                    binding.country.setText(subshot.child("15").getValue().toString());
                }
                if(snapshot.child("academic").exists())
                {
                    DataSnapshot subshot  = snapshot.child("academic");
                    binding.tenSchool.setText(subshot.child("0").getValue().toString());
                    binding.tenBoard.setText(subshot.child("1").getValue().toString());
                    binding.tenYear.setText(subshot.child("2").getValue().toString());
                    binding.tenPercent.setText(subshot.child("3").getValue().toString());
                    binding.twelSchool.setText(subshot.child("4").getValue().toString());
                    binding.twelBoard.setText(subshot.child("5").getValue().toString());
                    binding.twelYear.setText(subshot.child("6").getValue().toString());
                    binding.twelPercent.setText(subshot.child("7").getValue().toString());
                    binding.colg.setText(subshot.child("8").getValue().toString());
                    binding.year.setText(subshot.child("9").getValue().toString());
                    binding.semester.setText(subshot.child("10").getValue().toString());
                    binding.CPI.setText(subshot.child("11").getValue().toString());
                }
                if(snapshot.child("projects").exists())
                {
                    DataSnapshot subshot  = snapshot.child("projects");
                    binding.projTitle.setText(subshot.child("title").getValue().toString());
                    binding.projDec.setText(subshot.child("pdescription").getValue().toString());
                    binding.internOrg.setText(subshot.child("organisation").getValue().toString());
                    binding.internDesc.setText(subshot.child("idescription").getValue().toString());
                }
                if(snapshot.child("resume").exists())
                {
                    resume = snapshot.child("resume").getValue().toString();
                    binding.resumeStatus.setText("Resume Available");
                    binding.downloadBtn.setVisibility(View.VISIBLE);
                }
                if(snapshot.child("profilePic").exists())
                {
                    Picasso.get().load(snapshot.child("profilePic").getValue().toString())
                            .placeholder(R.drawable.avatar)
                            .into(binding.studImage);
                }
                if(snapshot.child("verificationStatus").exists())
                {
                    if(snapshot.child("verificationStatus").getValue().toString().equals("Verified"))
                    {
                        binding.verStatus.setTextOn("Verified");
                        binding.verStatus.setChecked(true);
                    }
                    else
                    {
                        binding.verStatus.setTextOn("Unverified");
                        binding.verStatus.setChecked(false);
                    }
                }
                if(snapshot.child("LockStatus").exists())
                {
                    if(snapshot.child("LockStatus").getValue().toString().equals("Locked"))
                    {
                        binding.lockStatus.setTextOn("Locked");
                        binding.lockStatus.setChecked(true);
                    }
                    else
                    {
                        binding.lockStatus.setTextOn("Not Locked");
                        binding.lockStatus.setChecked(false);
                    }
                }
                if(snapshot.child("internStatus").exists())
                {
                    if(snapshot.child("internStatus").getValue().toString().equals("Assigned"))
                    {
                        binding.lockStatus.setTextOn("Assigned");
                        binding.lockStatus.setChecked(true);
                    }
                    else
                    {
                        binding.lockStatus.setTextOn("Not Assigned");
                        binding.lockStatus.setChecked(false);
                    }
                }
                if(snapshot.child("company").exists())
                {
                    binding.companyName.setText(snapshot.child("company").getValue().toString());
                }
                if(snapshot.child("stipend").exists())
                {
                    binding.stipend.setText(snapshot.child("stipend").getValue().toString());
                }
                if(snapshot.child("credits").exists())
                {
                    binding.credits.setText(snapshot.child("credits").getValue().toString());
                    if(Integer.parseInt(binding.credits.getText().toString()) <=  4)
                    {
                        binding.lockStatus.setTextOn("Locked");
                        binding.lockStatus.setChecked(true);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resume));
                startActivity(intent);
            }

        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> obj = new HashMap<>();
                if(binding.stipend.getText()!=null)
                    obj.put("stipend",binding.stipend.getText().toString());
                if(binding.companyName.getText()!=null)
                    obj.put("company",binding.companyName.getText().toString());
                obj.put("credits",binding.credits.getText().toString());
                if(Integer.parseInt(binding.credits.getText().toString()) <=  4)
                {
                    binding.lockStatus.setTextOn("Locked");
                    binding.lockStatus.setChecked(true);
                }
               obj.put("LockStatus",binding.lockStatus.getText().toString());
               obj.put("verificationStatus",binding.verStatus.getText().toString());
               obj.put("internStatus",binding.internStatus.getText().toString());

                database.getReference().child("Users").child(uid).updateChildren(obj);
                Toast.makeText(StudentDetailActivity.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.verStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    binding.verStatus.setText("Verified");
                }
                else
                {
                    binding.verStatus.setText("Unverified");
                }
            }
        });

        binding.lockStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    binding.lockStatus.setText("Locked");
                }
                else
                {
                    binding.lockStatus.setText("Not Locked");
                }
            }
        });
        binding.internStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    binding.internStatus.setText("Assigned");
                }
                else
                {
                    binding.internStatus.setText("Not Assigned");
                }
            }
        });
    }
}