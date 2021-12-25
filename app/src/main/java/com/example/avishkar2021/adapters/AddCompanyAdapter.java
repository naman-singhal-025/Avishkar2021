package com.example.avishkar2021.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AddCompaniesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//list view adapter for user adding companies in admin portal (associated with add companies activity)
public class AddCompanyAdapter extends BaseAdapter {
    private Context context;
    int year,month,day;
    final Calendar cal = Calendar.getInstance();
    public static ArrayList<AddCompaniesModel> addCompaniesModelArrayList;

    //public constructor
    public AddCompanyAdapter(Context context, ArrayList<AddCompaniesModel> addCompaniesModelArrayList) {

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

    //returns total of items in the list
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
        final AddCompanyAdapter.ViewHolder holder;

        // inflate the layout for each list row
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.add_company, null, true);

            holder.company = (EditText) convertView.findViewById(R.id.company1);
            holder.branches = (EditText) convertView.findViewById(R.id.branches1);
            holder.stipend = (EditText)convertView.findViewById(R.id.stipend1);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (AddCompanyAdapter.ViewHolder)convertView.getTag();
        }
        holder.company.setText(addCompaniesModelArrayList.get(position).getCompany());
        holder.branches.setText(addCompaniesModelArrayList.get(position).getBranches());
        holder.stipend.setText(addCompaniesModelArrayList.get(position).getStipend());
        holder.deadline = (TextView)convertView.findViewById(R.id.deadline1);
        TextView dateView = convertView.findViewById(R.id.deadline1);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        dateView.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        holder.deadline.setText(addCompaniesModelArrayList.get(position).getDeadline());

        holder.company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                holder.company.setSelection(holder.company.getText().length());
                holder.branches.setSelection(holder.branches.getText().length());
                holder.stipend.setSelection(holder.stipend.getText().length());
                addCompaniesModelArrayList.get(position).setCompany(holder.company.getText().toString());
                addCompaniesModelArrayList.get(position).setBranches(holder.branches.getText().toString());
                addCompaniesModelArrayList.get(position).setDeadline(holder.deadline.getText().toString());
                addCompaniesModelArrayList.get(position).setStipend(holder.stipend.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.deadline.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addCompaniesModelArrayList.get(position).setDeadline(holder.deadline.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return convertView;
    }

    private class ViewHolder {

        protected EditText company;
        protected EditText branches;
        protected EditText stipend;
        protected TextView deadline;

    }
}
