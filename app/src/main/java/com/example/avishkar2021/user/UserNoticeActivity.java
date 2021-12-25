package com.example.avishkar2021.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

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

//activity to display notices published by admins on user's side
public class UserNoticeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    ListView listView;
    ActivityUserNoticeBinding binding;
    ArrayList<NoticeModel> list=new ArrayList<>();
    private ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //customised toolbar
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("Notice Board");
        setSupportActionBar(toolbar);

        InternetConnection internetConnection = new InternetConnection(UserNoticeActivity.this);
        internetConnection.execute();

        pgsBar = binding.pBar;
        pgsBar.setVisibility(View.VISIBLE);

        database = FirebaseDatabase.getInstance();

        //fetching notices from databse
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

                            pgsBar.setVisibility(View.GONE);
                            binding.listViewXXY.setVisibility(View.VISIBLE);

                        }catch (Exception e)
                        {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pgsBar.setVisibility(View.GONE);
                    }
                });

        //on click listener to throw intent to display notice activity
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