package com.example.avishkar2021.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.PersonalDetailsAdapter;
import com.example.avishkar2021.databinding.FragmentPersonalBinding;
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

public class PersonalFragment extends Fragment {

    private PersonalDetailsAdapter personalDetailsAdapter;
    public ArrayList<EditModel> editModelArrayList;
    FragmentPersonalBinding binding;
    ListView listV;
    FirebaseDatabase database;
    Users users;
    List<String> titleList = Arrays.asList("Reg No","Name","Course","Branch","Date of Birth","E-Mail","Gender (M/F)",
                                            "Category (Gen/OBC/SC/ST)","Physically  Challenged","Residential Address","Guardian","Present Address",
                                            "Permanent Address","Marital Status","State","Country");
    private List<String> textList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users  = snapshot.getValue(Users.class);
                        if(snapshot.child("personal").exists())
                        {
                            textList = users.getPersonal();
                        }
                        listV = binding.listView;
                        editModelArrayList = populateList();
                        personalDetailsAdapter = new PersonalDetailsAdapter(getActivity(),editModelArrayList);
                        listV.setAdapter(personalDetailsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textList = new ArrayList<>();
                int i=0;
                while(i<16 && PersonalDetailsAdapter.editModelArrayList.get(i).getEditTextValue()!=null)
                {
                    textList.add(PersonalDetailsAdapter.editModelArrayList.get(i).getEditTextValue());
                    i++;
                }
                HashMap<String,Object> obj = new HashMap<>();
                obj.put("personal",textList);
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }
    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 16; i++){
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