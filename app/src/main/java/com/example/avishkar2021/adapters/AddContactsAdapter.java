package com.example.avishkar2021.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.UsersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddContactsAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<UsersModel> usersArrayList;

    public AddContactsAdapter(Context context, ArrayList<UsersModel> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
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
        return usersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AddContactsAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.add_contacts, null, true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (AddContactsAdapter.ViewHolder)convertView.getTag();
        }
        holder.phone = convertView.findViewById(R.id.userPhone);
        holder.name = convertView.findViewById(R.id.userName);
        holder.branch = convertView.findViewById(R.id.userBranch);
        holder.mail = convertView.findViewById(R.id.userMail);
        holder.profile = convertView.findViewById(R.id.userProfileImage);
        holder.mail.setText(usersArrayList.get(position).getMail());
        holder.name.setText(usersArrayList.get(position).getUserName());
        holder.branch.setText(usersArrayList.get(position).getBranch());
        holder.phone.setText(usersArrayList.get(position).getPhone());
        Picasso.get().load(usersArrayList.get(position).getProfilePic())
                .placeholder(R.drawable.avatar)
                .into(holder.profile);



        return convertView;
    }

    private class ViewHolder {

        protected TextView name;
        protected TextView mail;
        protected TextView branch;
        protected TextView phone;
        private ImageView profile;

    }
}
