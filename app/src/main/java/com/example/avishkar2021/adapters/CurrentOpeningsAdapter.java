package com.example.avishkar2021.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.avishkar2021.R;
import com.example.avishkar2021.activities.MainActivity;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CurrentOpeningsAdapter extends BaseAdapter {
    private Context context;
    private static ArrayList<AddCompaniesModel> addCompaniesModelArrayList;

    public CurrentOpeningsAdapter(Context context, ArrayList<AddCompaniesModel> addCompaniesModelArrayList) {

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
        final CurrentOpeningsAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new CurrentOpeningsAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.current_openings, null, true);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (CurrentOpeningsAdapter.ViewHolder)convertView.getTag();
        }
        View view = convertView;
        try
        {
            holder.company =  convertView.findViewById(R.id.company3);
            holder.branches = convertView.findViewById(R.id.branches3);
            holder.stipend =  convertView.findViewById(R.id.stipend3);
            holder.deadline =  convertView.findViewById(R.id.deadline3);
            holder.button = convertView.findViewById(R.id.register3);
            holder.company.setText(addCompaniesModelArrayList.get(position).getCompany());
            holder.branches.setText(addCompaniesModelArrayList.get(position).getBranches());
            holder.stipend.setText(addCompaniesModelArrayList.get(position).getStipend());
            holder.deadline.setText(addCompaniesModelArrayList.get(position).getDeadline());

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InternetConnection internetConnection = new InternetConnection(context);
                    internetConnection.execute();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    AddCompaniesModel model = addCompaniesModelArrayList.get(position);
                    try
                    {
                        if (model.getLockS().equals("Locked")) {
                            Toast.makeText(context, "Alas! Your Profile is Locked!!!", Toast.LENGTH_SHORT).show();
                        } else if (model.getInternS().equals("Assigned")) {
                            Toast.makeText(context, "Congrats! You have already got an internship.", Toast.LENGTH_SHORT).show();
                        } else if (model.getVerS().equals("Verified")) {
                            ref.child("reg_com_uid").
                                    child(model.getCompany()).
                                    child(FirebaseAuth.getInstance().getUid()).setValue("Registered");
                            String date = DateFormat.getDateTimeInstance().format(new Date());
                            model.setDate(date);
                            FirebaseDatabase.getInstance().getReference().child("Users").
                                    child(FirebaseAuth.getInstance().getUid()).child("RegisteredCompanies").
                                    push().setValue(model);
//                    Toast.makeText(context, date, Toast.LENGTH_SHORT).show();

                            Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Your profile is under-verification!!!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(context, "Your profile is incomplete!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("MeraError2",e.toString());
        }
        return view;
    }

    private class ViewHolder {

        protected TextView company;
        protected TextView branches;
        protected TextView stipend;
        protected TextView deadline;
        protected Button button;

    }
}
