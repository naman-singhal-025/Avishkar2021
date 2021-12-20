package com.example.avishkar2021.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.adapters.QuestionAdapter;
import com.example.avishkar2021.databinding.ActivityExperienceBinding;
import com.example.avishkar2021.fragments.CommentsFragment;
import com.example.avishkar2021.models.QnaModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class ExperienceActivity extends AppCompatActivity {

    ActivityExperienceBinding binding;
    private StorageReference reference;
    ListView listView;
    ArrayList<QnaModel> list = new ArrayList<>();
    QuestionAdapter questionAdapter;
    ProgressDialog progressDialog;
    String s,path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        binding = ActivityExperienceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Please, wait !");

        InternetConnection internetConnection = new InternetConnection(ExperienceActivity.this);
        internetConnection.execute();

        progressDialog.show();
        Intent intent = getIntent();
        s = intent.getStringExtra("description");
        path = intent.getStringExtra("path");
        Log.d("SeeDesc",s);
        reference = FirebaseStorage.getInstance().getReference().child("InterviewExperiencesSS")
        .child(s);
        try{
            final File file = File.createTempFile("experience","jpeg");
            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ImageView view = findViewById(R.id.experience_ss);
                    view.setImageBitmap(bitmap);
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ExperienceActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }catch (Exception e)
        {

        }

        binding.commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                InternetConnection internetConnection = new InternetConnection(ExperienceActivity.this);
                internetConnection.execute();
                Bundle bundle = new Bundle();
                bundle.putString("url",path);
                CommentsFragment commentsFragment = new CommentsFragment();
                commentsFragment.setArguments(bundle);
                commentsFragment.show(getSupportFragmentManager(),commentsFragment.getTag());
            }
        });

    }
}