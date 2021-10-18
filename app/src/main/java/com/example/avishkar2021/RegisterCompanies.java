package com.example.avishkar2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar2021.Adapters.recyclerViewAdapter;

public class RegisterCompanies extends AppCompatActivity {
    //object for recycle view
    RecyclerView rcv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_companies);
        rcv_register = (RecyclerView) findViewById(R.id.recyclerViewRegister);
        rcv_register.setLayoutManager(new LinearLayoutManager(this));

        //data from firebase

        rcv_register.setAdapter(new recyclerViewAdapter());
    }
}
