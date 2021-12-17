package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.avishkar2021.adapters.CustomizedExpandableListAdapter;
import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.ActivityFeedbackBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    ActivityFeedbackBinding binding;
    ExpandableListView expandableListViewExample;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableTitleList;
    LinkedHashMap<String, List<String>> expandableDetailList;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        expandableDetailList = new LinkedHashMap<>();

        progressDialog = new ProgressDialog(FeedbackActivity.this);
        progressDialog.setMessage("Fetching data...");
        expandableListViewExample = (ExpandableListView) findViewById(R.id.expandableListViewSample);
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot p_snapshot = snapshot.child("feedback");
                for(DataSnapshot dataSnapshot : p_snapshot.getChildren())
                {
                    try
                    {
                        String uid = dataSnapshot.getKey();
                        String reg_no = snapshot.child("Users").
                                    child(uid).child("reg_no").getValue().toString();
                        String feedback = dataSnapshot.getValue().toString();
                        List<String > list = new ArrayList<>();
                        list.add(feedback);
                        expandableDetailList.put(reg_no,list);
//                          Toast.makeText(context, feedback, Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        Toast.makeText(FeedbackActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                expandableTitleList = new ArrayList<String>(expandableDetailList.keySet());
                expandableListAdapter = new CustomizedExpandableListAdapter(FeedbackActivity.this, expandableTitleList, expandableDetailList);
                expandableListViewExample.setAdapter(expandableListAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // This method is called when the group is expanded
        expandableListViewExample.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the group is collapsed
        expandableListViewExample.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the child in any group is clicked
        // via a toast method, it is shown to display the selected child item as a sample
        // we may need to add further steps according to the requirements
        expandableListViewExample.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition)
//                        + " -> "
//                        + expandableDetailList.get(
//                        expandableTitleList.get(groupPosition)).get(
//                        childPosition), Toast.LENGTH_SHORT
//                ).show();
                return false;
            }
        });

    }
}