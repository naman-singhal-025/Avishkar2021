package com.example.avishkar2021.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.FragmentAddNewInterviewBinding;
import com.example.avishkar2021.models.AddCompaniesModel;
import com.example.avishkar2021.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import top.defaults.colorpicker.ColorPickerPopup;

public class AddNewInterviewFragment extends Fragment{

    private EditText edittext;
    private int text_size = 14;
    private File filepath = null;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    final Calendar cal = Calendar.getInstance();
    String[] sizes = new String[]{"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};

    FragmentAddNewInterviewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                PackageManager.PERMISSION_GRANTED);
        binding = FragmentAddNewInterviewBinding.inflate(inflater, container, false);
        edittext = binding.editTextTextMultiLine;

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Letting your ink dry...");
        progressDialog.setMessage("Please, wait !!");

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

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

        binding.idFABSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                View root = binding.layoutSs;
                root.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
                root.setDrawingCacheEnabled(false);

                AddCompaniesModel user = new AddCompaniesModel();
                user.setName(binding.interName.getText().toString());
                user.setDate(binding.interDate.getText().toString());
                user.setCompany(binding.interCompany.getText().toString());
                //to store ss in firebase
                String key = UUID.randomUUID().toString();
                StorageReference storageRef = storage.getReference().child("InterviewExperiencesSS")
                        .child(key);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storageRef.putBytes(data);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        user.setDescription(downloadUrl);
                        database.getReference().child("InterviewExperiences").child(user.getCompany())
                                .child(key).setValue(user);
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                });


                //to store screenshot in local storage
//                filepath = new File(getActivity().getExternalFilesDir(null), "screenshot");
//                try {
//                    if (!filepath.exists()) {
//                        filepath.createNewFile();
//                    }
//                }catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                try {
//                    FileOutputStream fOut = new FileOutputStream(filepath);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//                    fOut.flush();
//                    fOut.close();
//                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });

        binding.interDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        binding.interDate.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        return binding.getRoot();
    }


}