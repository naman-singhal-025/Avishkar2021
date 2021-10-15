package com.example.avishkar2021.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.StudentDetailActivity;
import com.example.avishkar2021.VerifyStudentsActivity;
import com.example.avishkar2021.models.AddUserModel;
import com.example.avishkar2021.models.VerifyUserModel;

import java.util.ArrayList;

public class VerifyUserAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<VerifyUserModel> verifyUserModelArrayList;

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
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentDetailActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView numbering;
        protected TextView reg_no;
        protected TextView status;

    }
}
