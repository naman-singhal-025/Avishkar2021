package com.example.avishkar2021.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.EditModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//list view adapter for displaying and edit personal details of user
// (associated with personal fragment)
public class PersonalDetailsAdapter extends BaseAdapter {


    private Context context;
    public static ArrayList<EditModel> editModelArrayList;
    int year,month,day;
    final Calendar cal = Calendar.getInstance();

    //public constructor
    public PersonalDetailsAdapter(Context context, ArrayList<EditModel> editModelArrayList) {

        this.context = context;
        this.editModelArrayList = editModelArrayList;
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
        return editModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return editModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // inflate the layout for each list row
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.items, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.profileText);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView = (TextView)convertView.findViewById(R.id.profileTitle);
        holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        holder.textView.setText(editModelArrayList.get(position).getTextValue());

        //disable edit text if user's profile is verified or locked
        //so that user can't edit details once verified/locked
        //for pos=0 and 5 since mail and reg no are provided by admin so they can't be edited anyhow
        if(editModelArrayList.get(position).getVerStatus().equals("Verified")||
                editModelArrayList.get(position).getLockStatus().equals("Locked")|| position==0 ||
                position==5){
            holder.editText.setEnabled(false);
        }

        //for date of birth (present at index 4 in list view) date picker is added
        else if(position == 4)
        {
            holder.editText.setInputType(InputType.TYPE_NULL);
            holder.editText.setFocusable(false);
            holder.editText.setOnClickListener(new View.OnClickListener() {
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
                            holder.editText.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()).toString());
                        }
                    },year,month,day);
                    datePickerDialog.show();
                }
            });
        }
        else
        {
            holder.editText.setEnabled(true);
        }

        //added listener for text changed in edit text so that text in edit text is not lost
        //once focus is lost from edit text
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());
                    holder.editText.setSelection(holder.editText.getText().length());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.editText.getText().toString().isEmpty() && position!=4)
                {
                    holder.editText.setError("Required " + editModelArrayList.get(position).getTextValue());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {

        protected EditText editText;
        protected TextView textView;

    }

}
