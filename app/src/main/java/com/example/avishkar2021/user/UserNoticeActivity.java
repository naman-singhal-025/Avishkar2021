package com.example.avishkar2021.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.adapters.UserNoticeBoardAdapter;
import com.example.avishkar2021.databinding.ActivityUserNoticeBinding;
import com.example.avishkar2021.models.NoticeModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserNoticeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    ProgressDialog progressDialog;
    ListView listView;
    ActivityUserNoticeBinding binding;
    ArrayList<NoticeModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Notice Board");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Please, wait !!");

        InternetConnection internetConnection = new InternetConnection(UserNoticeActivity.this);
        internetConnection.execute();

        database = FirebaseDatabase.getInstance();
//        FirebaseMessaging.getInstance().subscribeToTopic("notice");
        database.getReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot subshot) {
                        list.clear();
                        try {
                            DataSnapshot snapshot = subshot.child("notice");
                            for(DataSnapshot dataSnapshot : snapshot.getChildren())
                            {

                                NoticeModel model = new NoticeModel();
                                model.setDescription(dataSnapshot.child("description").getValue().toString());
                                model.setSubject(dataSnapshot.child("subject").getValue().toString());
                                model.setPublish_date(dataSnapshot.child("publish_date").getValue().toString());
                                model.setUid(dataSnapshot.getKey());
                                if(subshot.child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("notice_seen").child(model.getUid()).exists())
                                {
                                    model.setStatus("seen");
                                }
                                list.add(model);

                            }
                            listView = binding.listViewXXY;
                            UserNoticeBoardAdapter adapter = new UserNoticeBoardAdapter(UserNoticeActivity.this,list);
                            listView.setAdapter(adapter);

                        }catch (Exception e)
                        {

                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.listViewXXY.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .child("notice_seen").child(list.get(i).getUid()).setValue("seen");
                database.getReference().child("notice").child(list.get(i).getUid())
                        .child("seen_by")
                        .child(FirebaseAuth.getInstance().getUid()).setValue("seen");
                Intent intent = new Intent(UserNoticeActivity.this, DisplayNoticeActivity.class);
                intent.putExtra("subject",list.get(i).getSubject());
                intent.putExtra("description",list.get(i).getDescription());
                startActivity(intent);
            }
        });

    }
}