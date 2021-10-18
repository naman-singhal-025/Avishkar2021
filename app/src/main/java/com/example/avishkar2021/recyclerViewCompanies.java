package com.example.avishkar2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.avishkar2021.Adapters.recyclerViewAdapter;

public class recyclerViewCompanies extends AppCompatActivity {

    //object for recycle view
    RecyclerView rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_companies);
        rcv = (RecyclerView) findViewById(R.id.recyclerViewCompany);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        //data from firebase

        rcv.setAdapter(new recyclerViewAdapter());
    }
}