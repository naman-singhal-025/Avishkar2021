package com.example.avishkar2021.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.avishkar2021.R;
import com.example.avishkar2021.admin.StudentDetailActivity;
import com.example.avishkar2021.models.VerifyUserModel;

import java.util.ArrayList;

//list view adapter for displaying all the registered students on admin side
//it allows admins to verify and lock user profile
// (associated with verify students activity)
public class VerifyUserAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<VerifyUserModel> verifyUserModelArrayList;

    //public constructor
    public VerifyUserAdapter(Context context, ArrayList<VerifyUserModel> verifyUserModelArrayList) {

        this.context = context;
        this.verifyUserModelArrayList = verifyUserModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    //returns total of items in the list
    @Override
    public int getCount() {
        return verifyUserModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return verifyUserModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final VerifyUserAdapter.ViewHolder holder;

        // inflate the layout for each list row
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.verify_user, null, true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (VerifyUserAdapter.ViewHolder)convertView.getTag();
        }

        holder.numbering = (TextView)convertView.findViewById(R.id.numbering);
        holder.reg_no = (TextView)convertView.findViewById(R.id.reg_no);
        holder.status = (TextView)convertView.findViewById(R.id.status);
        holder.numbering.setText(verifyUserModelArrayList.get(position).getNumbering());
        holder.reg_no.setText(verifyUserModelArrayList.get(position).getRegistration_no());
        holder.status.setText(verifyUserModelArrayList.get(position).getStatus());
        if(verifyUserModelArrayList.get(position).getStatus().equals("Verified"))
        {
            holder.status.setTextColor(ContextCompat.getColor(context,R.color.verified));
        }
        else if(verifyUserModelArrayList.get(position).getStatus().equals("Locked"))
        {
            holder.status.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentDetailActivity.class);
                intent.putExtra("uid",verifyUserModelArrayList.get(position).getUid());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView numbering;
        private TextView reg_no;
        private TextView status;

    }
}
