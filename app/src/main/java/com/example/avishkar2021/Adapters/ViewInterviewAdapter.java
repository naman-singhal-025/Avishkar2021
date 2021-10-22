package com.example.avishkar2021.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AddUserModel;
import com.example.avishkar2021.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewInterviewAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<String > list;
    int id=-1;

    public ViewInterviewAdapter(Context context, ArrayList<String> list,int id) {
        this.context = context;
        this.list = list;
        this.id = id;
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
        final ViewInterviewAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_interview, null, true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewInterviewAdapter.ViewHolder)convertView.getTag();
        }
       holder.name = convertView.findViewById(R.id.company_name);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        holder.name.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {

        protected TextView name;

    }
}
