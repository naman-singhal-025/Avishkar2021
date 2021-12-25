package com.example.avishkar2021.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AnswersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//recycler view adapter for replies of question asked in QnA section in view interview
//it is child recycler view with parent as question recycler view
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<AnswersModel> answersModelArrayList;

    public ReplyAdapter(Context context, ArrayList<AnswersModel> answersModelArrayList) {

        this.context = context;
        this.answersModelArrayList = answersModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qna_replies,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(answersModelArrayList.get(position).getAnswerUserName());
        holder.reply.setText(answersModelArrayList.get(position).getUserReply());
        Picasso.get().load(answersModelArrayList.get(position).getProfilePic())
                .placeholder(R.drawable.avatar)
                .into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return answersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePic;
        private TextView userName;
        private TextView reply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.reply_prof_image);
            userName = itemView.findViewById(R.id.ques_replier_name);
            reply = itemView.findViewById(R.id.user_reply);

        }
    }
}
