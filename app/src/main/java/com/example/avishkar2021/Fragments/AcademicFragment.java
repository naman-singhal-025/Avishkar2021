package com.example.avishkar2021.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.AcademicDetailsAdapter;
import com.example.avishkar2021.Adapters.PersonalDetailsAdapter;
import com.example.avishkar2021.databinding.FragmentAcademicBinding;
import com.example.avishkar2021.models.EditModel;
import com.example.avishkar2021.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AcademicFragment extends Fragment {

    private AcademicDetailsAdapter academicDetailsAdapter;
    public ArrayList<EditModel> editModelArrayList;
    FragmentAcademicBinding binding;
    ListView listV;
    FirebaseDatabase database;
    Users users;
    List<String> titleList = Arrays.asList("10th School","10th board","10th Year","10th %","12th School",
                                            "12th Board","12th Year","12th %","Graduating College","Year",
                                            "Semester","CPI");
    private List<String> textList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAcademicBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users  = snapshot.getValue(Users.class);
                        if(snapshot.child("academic").exists())
                        {
                            textList = users.getAcademic();
                        }
                        listV = binding.listView1;
                        editModelArrayList = populateList();
                        academicDetailsAdapter = new AcademicDetailsAdapter(getActivity(),editModelArrayList);
                        listV.setAdapter(academicDetailsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        binding.floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textList = new ArrayList<>();
                for(int i=0;i<12;i++)
                {
                    textList.add(AcademicDetailsAdapter.editModelArrayList.get(i).getEditTextValue());
                }
                HashMap<String,Object> obj = new HashMap<>();
                obj.put("academic",textList);
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 12; i++){
            EditModel editModel = new EditModel();
            editModel.setTextValue(titleList.get(i));
            if(textList!=null)
            {
                editModel.setEditTextValue(textList.get(i));
            }
            list.add(editModel);
        }

        return list;
    }
}