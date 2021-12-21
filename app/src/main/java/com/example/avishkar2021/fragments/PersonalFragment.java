package com.example.avishkar2021.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.avishkar2021.adapters.PersonalDetailsAdapter;
import com.example.avishkar2021.databinding.FragmentPersonalBinding;
import com.example.avishkar2021.models.EditModel;
import com.example.avishkar2021.models.UsersModel;
import com.example.avishkar2021.utils.InternetConnection;
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
    UsersModel users;
    FirebaseDatabase database;
    private ProgressBar pgsBar;
    private String lock = "Not Locked",ver = "Not Verified";
    private String reg_no="",mail="";
    List<String> titleList = Arrays.asList("Reg No","Name","Course","Branch","Date of Birth","E-Mail","Gender (M/F)",
                                            "Category (Gen/OBC/SC/ST)","Physically  Challenged","Residential Status","Guardian","Present Address",
                                            "Permanent Address","Marital Status","State","Country");
    private List<String> textList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        pgsBar = binding.pBar;

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        pgsBar.setVisibility(View.VISIBLE);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reg_no = snapshot.child("reg_no").getValue().toString();
                        mail = snapshot.child("editTextMail").getValue().toString();
                        users= snapshot.getValue(UsersModel.class);
                        if(snapshot.child("personal").exists())
                        {
//                            textList = (List)snapshot.child("personal").getValue();
                            textList = users.getPersonal();
                        }
                        if(snapshot.child("LockStatus").exists())
                        {
                            lock = snapshot.child("LockStatus").getValue().toString();
                        }

                        if(snapshot.child("verificationStatus").exists())
                        {
                            ver = snapshot.child("verificationStatus").getValue().toString();
                        }
                        listV = binding.listView;
                        editModelArrayList = populateList(lock,ver);
                        personalDetailsAdapter = new PersonalDetailsAdapter(getActivity(),editModelArrayList);
                        listV.setAdapter(personalDetailsAdapter);
                        pgsBar.setVisibility(View.GONE);
                        binding.personalFrame.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pgsBar.setVisibility(View.GONE);
                    }

                });


        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetConnection internetConnection = new InternetConnection(getContext());
                internetConnection.execute();

                textList = new ArrayList<>();
                int i = 0;
                int flag=1;
                while (i < 16) {
                    String s = PersonalDetailsAdapter.editModelArrayList.get(i).getEditTextValue();
                    if(s.isEmpty() || s.equals(""))
                    {
                        flag=0;
                        break;
                    }
                    textList.add(s);
                    i++;
                }
                if(flag!=0)
                {
                    HashMap<String, Object> obj = new HashMap<>();
                    obj.put("personal", textList);
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                    Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Complete your profile", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }
    private ArrayList<EditModel> populateList(String lock, String ver){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 16; i++){
            EditModel editModel = new EditModel();
            editModel.setTextValue(titleList.get(i));
            editModel.setVerStatus(ver);
            editModel.setLockStatus(lock);
            editModel.setEditTextValue("");
            if(i==0)
            {
                editModel.setEditTextValue(reg_no);
            }
            else if(i==5)
            {
                editModel.setEditTextValue(mail);
            }
            else if(textList!=null && textList.size()==16)
            {
                editModel.setEditTextValue(textList.get(i));
            }
            list.add(editModel);
        }

        return list;
    }
}