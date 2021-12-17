package com.example.avishkar2021.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.avishkar2021.R;
import com.example.avishkar2021.models.AddUserModel;

import java.util.ArrayList;

public class AddUserAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<AddUserModel> addUserModelArrayList;

    public AddUserAdapter(Context context, ArrayList<AddUserModel> addUserModelArrayList) {

        this.context = context;
        this.addUserModelArrayList = addUserModelArrayList;
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
        return addUserModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return addUserModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AddUserAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.add_user, null, true);

            holder.editMail = (EditText) convertView.findViewById(R.id.editMail);
            holder.editPass = (EditText) convertView.findViewById(R.id.editPass);
            holder.reg_no = (EditText)convertView.findViewById(R.id.edit_reg_no);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (AddUserAdapter.ViewHolder)convertView.getTag();
        }
        holder.editMail.setText(addUserModelArrayList.get(position).getEditTextMail());
        holder.editPass.setText(addUserModelArrayList.get(position).getEditTextPassword());
        holder.reg_no.setText(addUserModelArrayList.get(position).getReg_no());
        holder.numbering = (TextView)convertView.findViewById(R.id.numbering);
        holder.numbering.setText(addUserModelArrayList.get(position).getNumbering());

        holder.editMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                holder.editPass.setSelection(holder.editPass.getText().length());
                holder.editMail.setSelection(holder.editMail.getText().length());
                holder.reg_no.setSelection(holder.reg_no.getText().length());
                addUserModelArrayList.get(position).setEditTextMail(holder.editMail.getText().toString());
                addUserModelArrayList.get(position).setEditTextPassword(holder.editPass.getText().toString());
                addUserModelArrayList.get(position).setReg_no(holder.reg_no.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText editMail;
        protected EditText editPass;
        protected TextView numbering;
        protected EditText reg_no;

    }
}
