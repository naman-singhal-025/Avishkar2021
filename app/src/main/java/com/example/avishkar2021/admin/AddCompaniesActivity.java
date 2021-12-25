package com.example.avishkar2021.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.adapters.AddCompanyAdapter;
import com.example.avishkar2021.databinding.ActivityAddCompaniesBinding;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//activity to add companies from admin portal
public class AddCompaniesActivity extends AppCompatActivity {

    private AddCompanyAdapter addCompanyAdapter;
    public ArrayList<AddCompaniesModel> addCompaniesModelArrayList;
    ActivityAddCompaniesBinding binding;
    ListView listV;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCompaniesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setting customized action bar
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("Add Companies Portal");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AddCompaniesActivity.this);
        progressDialog.setMessage("Uploading data");

        InternetConnection internetConnection = new InternetConnection(AddCompaniesActivity.this);
        internetConnection.execute();

        //btn to upload data from list view into database
        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                int size = listV.getAdapter().getCount();
                int i=0;
                try {
                    ArrayList<AddCompaniesModel> list = new ArrayList<>();
                    while(i<size && isValid(i))
                    {
                        AddCompaniesModel model  = AddCompanyAdapter.addCompaniesModelArrayList.get(i);
                        list.add(model);
                        i++;
                    }
                    if(i>0) {
                        Date currentTime = Calendar.getInstance().getTime();
                        database.getReference().child("newOpenings").child(currentTime.toString()).setValue(list);
                        Toast.makeText(AddCompaniesActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                    else
                    {
                        Toast.makeText(AddCompaniesActivity.this, "Enter at least one company", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e)
                {
                    Log.d("Mera Error",e.toString());
                    if(i==0)
                    {
                        Toast.makeText(AddCompaniesActivity.this, "Enter at least one company", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        listV = binding.listView3;
        addCompaniesModelArrayList = populateList();
        addCompanyAdapter = new AddCompanyAdapter(AddCompaniesActivity.this,addCompaniesModelArrayList);
        listV.setAdapter(addCompanyAdapter);
    }

    //check if text fields are not empty or null
    private boolean isValid(int i) {
        return AddCompanyAdapter.addCompaniesModelArrayList.get(i).getCompany()!=null &&
                AddCompanyAdapter.addCompaniesModelArrayList.get(i).getBranches()!=null &&
                AddCompanyAdapter.addCompaniesModelArrayList.get(i).getDeadline()!=null &&
                AddCompanyAdapter.addCompaniesModelArrayList.get(i).getStipend()!=null &&
                !AddCompanyAdapter.addCompaniesModelArrayList.get(i).getStipend().equals("") &&
                !AddCompanyAdapter.addCompaniesModelArrayList.get(i).getDeadline().equals("") &&
                !AddCompanyAdapter.addCompaniesModelArrayList.get(i).getBranches().equals("") &&
                !AddCompanyAdapter.addCompaniesModelArrayList.get(i).getCompany().equals("");
    }

    //populate list view with 10 items allowing a max of 10 companies to be added at a time
    private ArrayList<AddCompaniesModel> populateList(){

        ArrayList<AddCompaniesModel> list = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            AddCompaniesModel addCompaniesModel = new AddCompaniesModel();
            list.add(addCompaniesModel);
        }

        return list;
    }
}