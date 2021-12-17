package com.example.avishkar2021.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AddCompaniesModel;
import java.util.ArrayList;

public class RegisteredCompaniesAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<AddCompaniesModel> addCompaniesModelArrayList;

    public RegisteredCompaniesAdapter(Context context, ArrayList<AddCompaniesModel> addCompaniesModelArrayList) {

        this.context = context;
        this.addCompaniesModelArrayList = addCompaniesModelArrayList;
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
        return addCompaniesModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return addCompaniesModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RegisteredCompaniesAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new RegisteredCompaniesAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.registered_companies, null, true);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RegisteredCompaniesAdapter.ViewHolder)convertView.getTag();
        }
        View view = convertView;
        try
        {
            holder.company =  convertView.findViewById(R.id.company4);
            holder.stipend =  convertView.findViewById(R.id.stipend4);
            holder.date = convertView.findViewById(R.id.date);
            holder.company.setText(addCompaniesModelArrayList.get(position).getCompany());
            holder.stipend.setText(addCompaniesModelArrayList.get(position).getStipend());
            holder.date.setText(addCompaniesModelArrayList.get(position).getDate());

        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("MeraError2",e.toString());
        }
        return view;
    }

    private class ViewHolder {

        protected TextView company;
        protected TextView stipend;
        protected TextView date;

    }
}
