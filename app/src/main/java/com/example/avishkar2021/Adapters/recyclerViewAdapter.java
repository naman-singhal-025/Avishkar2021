package com.example.avishkar2021.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar2021.R;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.holder> {

    //creating array list for data
    ArrayList<holder> list;

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.companies,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.company.setText((CharSequence) list.get(position));
        holder.branch.setText((CharSequence) list.get(position));
        holder.jobProfile.setText((CharSequence) list.get(position));

        // add on click listener for register button

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //creating holder class to hold the data
    static class holder extends RecyclerView.ViewHolder {

        TextView company;
        TextView branch;
        TextView jobProfile;
        Button register;

        public holder(@NonNull View itemView) {
            super(itemView);
            company = (TextView) itemView.findViewById(R.id.company_name);
            branch = (TextView) itemView.findViewById(R.id.branch_allowed);
            jobProfile = (TextView) itemView.findViewById(R.id.job_profile);
            register = (Button) itemView.findViewById(R.id.register_button);
        }
    }

}
