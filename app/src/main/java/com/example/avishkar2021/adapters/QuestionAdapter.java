package com.example.avishkar2021.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AnswersModel;
import com.example.avishkar2021.models.QnaModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<QnaModel> qnaModelArrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public QuestionAdapter(Context context, ArrayList<QnaModel> qnaModelArrayList) {

        this.context = context;
        this.qnaModelArrayList = qnaModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qna_questions,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        QnaModel model = qnaModelArrayList.get(position);
        holder.userName.setText(model.getUserName());
        holder.question.setText(model.getQuestion());
        Picasso.get().load(model.getProfilePic())
                .placeholder(R.drawable.avatar)
                .into(holder.profilePic);

        holder.reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.reply_bar.getVisibility()== holder.reply_bar.VISIBLE)
                {
                    holder.reply_bar.setVisibility(holder.reply_bar.GONE);
                }
                else
                {
                    holder.reply_bar.setVisibility(holder.reply_bar.VISIBLE);
                }
            }
        });

        holder.view_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.replies_list_view.getVisibility()== holder.replies_list_view.VISIBLE)
                {
                    holder.view_reply.setText("View Replies");
                    holder.replies_list_view.setVisibility(holder.replies_list_view.GONE);
                }
                else
                {
                    if(model.getReplyList().size()!=0) {
                        holder.view_reply.setText("Hide Replies");
                        holder.replies_list_view.setVisibility(holder.replies_list_view.VISIBLE);
                        try {
                            ReplyAdapter replyAdapter = new ReplyAdapter(context, model.getReplyList());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            holder.replies_list_view.setLayoutManager(layoutManager);
                            holder.replies_list_view.setAdapter(replyAdapter);
                        } catch (Exception e) {

                        }
                    }
                }
            }
        });

        holder.post_reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswersModel answersModel = new AnswersModel();
                answersModel.setReply_id(FirebaseAuth.getInstance().getUid());
                answersModel.setUserReply(holder.reply.getText().toString());
                database.getReference().child(qnaModelArrayList.get(position).getQuestion_id())
                                .child("replies").push().setValue(answersModel);
            }
        });
    }
    @Override
    public int getItemCount() {
        return qnaModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePic;
        private TextView userName;
        private TextView question;
        private EditText reply;
        private Button post_reply_btn;
        private ImageView reply_btn;
        private TextView view_reply;
        private LinearLayout reply_bar;
        private RecyclerView replies_list_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.question_prof_image);
            userName = itemView.findViewById(R.id.ques_user_name);
            question = itemView.findViewById(R.id.posted_question);
            reply_btn = itemView.findViewById(R.id.reply_btn);
            post_reply_btn = itemView.findViewById(R.id.post_reply_btn);
            reply = itemView.findViewById(R.id.reply);
            view_reply = itemView.findViewById(R.id.view_replies);
            reply_bar = itemView.findViewById(R.id.reply_bar);
            replies_list_view = itemView.findViewById(R.id.replies_list_view);

        }
    }
}
