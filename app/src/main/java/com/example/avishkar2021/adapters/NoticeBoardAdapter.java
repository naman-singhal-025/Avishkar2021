package com.example.avishkar2021.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.notice_model;

import java.util.ArrayList;

public class NoticeBoardAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<notice_model> list;

    public NoticeBoardAdapter(Context context, ArrayList<notice_model> list) {
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
        final NoticeBoardAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notice_item, null, true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (NoticeBoardAdapter.ViewHolder)convertView.getTag();
        }
        holder.subject = convertView.findViewById(R.id.notice_subject);
        holder.publish_date = convertView.findViewById(R.id.publish_date);
        holder.subject.setText(list.get(position).getSubject());
        holder.publish_date.setText(list.get(position).getPublish_date());
        return convertView;
    }

    private class ViewHolder {

        protected TextView subject;
        protected TextView publish_date;

    }
}
