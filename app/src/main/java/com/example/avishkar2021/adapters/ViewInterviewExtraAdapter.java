package com.example.avishkar2021.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.avishkar2021.user.ExperienceActivity;
import com.example.avishkar2021.R;
import com.example.avishkar2021.models.UsersModel;

import java.util.ArrayList;

//list view adapter for displaying names of users who have posted their interview experiences
// (associated with view interview activity)
public class ViewInterviewExtraAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<UsersModel> list;

    //public constructor
    public ViewInterviewExtraAdapter(Context context, ArrayList<UsersModel> list) {
        this.context = context;
        this.list = list;
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
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewInterviewExtraAdapter.ViewHolder holder;

        // inflate the layout for each list row
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_interview, null, true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewInterviewExtraAdapter.ViewHolder)convertView.getTag();
        }
        holder.name = convertView.findViewById(R.id.company_name);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.red));
        holder.name.setTextSize(18);
        holder.name.setText(list.get(position).getUserName());

        //on click listener for each list item which will direct to experience activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExperienceActivity.class);
                intent.putExtra("description",list.get(position).getUserId());
                intent.putExtra("path",list.get(position).getUserPath());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        protected TextView name;

    }
}
