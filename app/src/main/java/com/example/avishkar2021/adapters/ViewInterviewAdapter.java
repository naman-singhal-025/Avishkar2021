package com.example.avishkar2021.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.avishkar2021.R;

import java.util.ArrayList;

//list view adapter for displaying companies names of which interviews have been added
// (associated with view interview fragment) on user's side
public class ViewInterviewAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<String > list;
    int id=-1;

    //public constructor
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
        final ViewInterviewAdapter.ViewHolder holder;

        // inflate the layout for each list row
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
