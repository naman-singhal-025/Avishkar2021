package com.example.avishkar2021.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentAddNewInterviewBinding;


import top.defaults.colorpicker.ColorPickerPopup;

public class AddNewInterviewFragment extends Fragment{

    private EditText edittext;
    private int text_size = 14;
    String[] sizes = new String[]{"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};

    FragmentAddNewInterviewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentAddNewInterviewBinding.inflate(inflater, container, false);
        edittext = binding.editTextTextMultiLine;

        edittext.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                menu.close();
                menu.clear();
                menu.setQwertyMode(false);
                return true;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        edittext.setTextIsSelectable(true);

        binding.bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new StyleSpan(Typeface.BOLD),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setText(str);
            }
        });

        binding.italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new StyleSpan(Typeface.ITALIC),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setText(str);
            }
        });

        binding.underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new UnderlineSpan(),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setText(str);
            }
        });

        binding.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new UnderlineSpan(),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setGravity(Gravity.LEFT);
            }
        });

        binding.center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new UnderlineSpan(),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setGravity(Gravity.CENTER);
            }
        });

        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start= edittext.getSelectionStart();
                int end=edittext.getSelectionEnd();
                Spannable str=edittext.getText();

                str.setSpan(new UnderlineSpan(),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edittext.setGravity(Gravity.RIGHT);
            }
        });

        binding.textColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if (view == null) {
                    view = new View(getContext());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                new ColorPickerPopup.Builder(getContext()).initialColor(
                        Color.RED).enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("OK")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        int start= edittext.getSelectionStart();
                                        int end=edittext.getSelectionEnd();
                                        Spannable str=edittext.getText();

                                        str.setSpan(new ForegroundColorSpan(color),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        edittext.setText(str);
                                    }
                                });

            }
        });

        binding.textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
                d.setMessage("Choose text size!!!");
                d.setView(dialogView);
                final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
                numberPicker.setMaxValue(72);
                numberPicker.setMinValue(8);
                numberPicker.setWrapSelectorWheel(false);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Log.d("TAG", "onValueChange: ");
                    }
                });
                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int start= edittext.getSelectionStart();
                        int end=edittext.getSelectionEnd();
                        Spannable str=edittext.getText();

                        str.setSpan(new AbsoluteSizeSpan(numberPicker.getValue(), true),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        edittext.setText(str);
                        Log.d("TAG", "onClick: " + numberPicker.getValue());
                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();
            }
        });

        binding.highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
//                if (view == null) {
//                    view = new View(getContext());
//                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                new ColorPickerPopup.Builder(getContext()).initialColor(
                        Color.RED).enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("OK")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void
                            onColorPicked(int color) {
                                int start= edittext.getSelectionStart();
                                int end=edittext.getSelectionEnd();
                                Spannable str=edittext.getText();

                                str.setSpan(new BackgroundColorSpan(color),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                edittext.setText(str);
                            }
                        });

            }
        });

        binding.nextLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("\n");
            }
        });

        binding.idFABSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }


}