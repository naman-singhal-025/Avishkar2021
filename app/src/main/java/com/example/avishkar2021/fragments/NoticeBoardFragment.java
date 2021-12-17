package com.example.avishkar2021.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.adapters.NoticeBoardAdapter;
import com.example.avishkar2021.user.DisplayNoticeActivity;
import com.example.avishkar2021.databinding.FragmentNoticeBoardBinding;
import com.example.avishkar2021.models.notice_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeBoardFragment extends Fragment {
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    ListView listView;
    FragmentNoticeBoardBinding binding;
    ArrayList<notice_model> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoticeBoardBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Please, wait !!");

        database = FirebaseDatabase.getInstance();

        database.getReference().child("notice")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        try {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {

                                        notice_model model = new notice_model();
                                        model.setDescription(dataSnapshot.child("description").getValue().toString());
                                        model.setSubject(dataSnapshot.child("subject").getValue().toString());
                                        model.setPublish_date(dataSnapshot.child("publish_date").getValue().toString());
                                        model.setUid(dataSnapshot.getKey());
                                        list.add(model);

                                }
                                listView = binding.listViewXX;
                                NoticeBoardAdapter adapter = new NoticeBoardAdapter(getActivity(),list);
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


        binding.listViewXX.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this notice")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notice_model model = list.get(i);
                                try {
                                    database.getReference().addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            DataSnapshot subshot = snapshot.child("notice").child(model.getUid()).child("seen_by");
                                            for(DataSnapshot dataSnapshot : subshot.getChildren())
                                            {
                                                snapshot.child("Users").child(dataSnapshot.getKey()).child("notice_seen")
                                                        .child(snapshot.child("notice").child(model.getUid()).getKey()).getRef().setValue(null);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    database.getReference().child("notice").child(model.getUid()).setValue(null);
                                }catch (Exception e)
                                {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("DoError",e.toString());
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                return true;
            }
        });

        binding.listViewXX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DisplayNoticeActivity.class);
                intent.putExtra("subject",list.get(i).getSubject());
                intent.putExtra("description",list.get(i).getDescription());
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}