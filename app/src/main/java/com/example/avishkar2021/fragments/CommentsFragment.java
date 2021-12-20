package com.example.avishkar2021.fragments;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.adapters.QuestionAdapter;
import com.example.avishkar2021.models.AnswersModel;
import com.example.avishkar2021.models.QnaModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentsFragment extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    ArrayList<QnaModel> list = new ArrayList<>();
    QuestionAdapter questionAdapter;
    FirebaseDatabase database;
    String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_layout, container, false);
        Bundle bundle = this.getArguments();
        path = bundle.getString("url");
        path = path.substring(49);
        Log.d("SeeDesc2",path);
        database= FirebaseDatabase.getInstance();
        Button post_btn = view.findViewById(R.id.post_question);

        EditText question = view.findViewById(R.id.question);

        InternetConnection internetConnection = new InternetConnection(getContext());
        internetConnection.execute();

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetConnection internetConnection = new InternetConnection(getContext());
                internetConnection.execute();
                if(!question.equals(null) && !question.equals(""))
                {
                    QnaModel model = new QnaModel();
                    model.setQuestion(question.getText().toString());
                    question.setText(null);
                    model.setQuestion_user_id(FirebaseAuth.getInstance().getUid());
                    database.getReference().child(path).child("questions").push().setValue(model);
                }else {
                    Toast.makeText(getContext(), "Write your query!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        database.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot questionShot:snapshot.child(path).child("questions").getChildren())
                {
                    ArrayList<AnswersModel> a_list = new ArrayList<>();
                    QnaModel model = new QnaModel();
                    model.setQuestion_id(questionShot.getRef().toString().substring(49));
                    model.setQuestion(questionShot.child("question").getValue().toString());
                    try
                    {
                        model.setUserName(snapshot.child("Users").child(questionShot.child("question_user_id").getValue().toString())
                                .child("editTextMail").getValue().toString());
                        model.setProfilePic(snapshot.child("Users").child(questionShot.child("question_user_id").getValue().toString())
                                .child("profilePic").getValue().toString());
                    }catch (Exception e)
                    {

                    }
                    for(DataSnapshot repliesShot : questionShot.child("replies").getChildren())
                    {
                        AnswersModel answersModel = new AnswersModel();
                        answersModel.setReply_id(repliesShot.child("reply_id").getValue().toString());
                        answersModel.setUserReply(repliesShot.child("userReply").getValue().toString());
                        try
                        {
                            answersModel.setAnswerUserName(snapshot.child("Users").
                                    child(answersModel.getReply_id()).child("editTextMail").getValue().toString());
                            answersModel.setProfilePic(snapshot.child("Users").
                                    child(answersModel.getReply_id()).child("profilePic").getValue().toString());
                        }catch (Exception e){}
                        a_list.add(answersModel);

                    }
                    model.setReplyList(a_list);
                    list.add(model);
                }
                try
                {
                    recyclerView = view.findViewById(R.id.question_list_view);
                    questionAdapter = new QuestionAdapter(getContext(), list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(questionAdapter);
                }catch (Exception e)
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}