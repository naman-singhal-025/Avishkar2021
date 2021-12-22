package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.avishkar2021.R;
import com.example.avishkar2021.databinding.ActivityAddStudentsBinding;
import com.example.avishkar2021.models.AddUserModel;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

public class AddStudentsActivity extends AppCompatActivity {

    ActivityAddStudentsBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String mail,pass,reg_no;
    DecimalFormat df = new DecimalFormat("0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("Registration Portal");
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AddStudentsActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Uploading data");

        InternetConnection internetConnection = new InternetConnection(AddStudentsActivity.this);
        internetConnection.execute();

       binding.uploadButton1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressDialog.show();
               try
               {
                   mail = binding.editMail.getText().toString();
                   pass = binding.editPass.getText().toString();
                   reg_no = binding.editRegNo.getText().toString();

                   auth.createUserWithEmailAndPassword(mail,pass).
                           addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   progressDialog.dismiss();
                                   if(task.isSuccessful()){
                                       AddUserModel user  = new AddUserModel();
                                       user.setEditTextMail(mail);
                                       user.setEditTextPassword(pass);
                                       user.setReg_no(reg_no);
                                       String id  = task.getResult().getUser().getUid();
                                       database.getReference().child("Users").child(id).setValue(user);
                                       Toast.makeText(AddStudentsActivity.this,"User Created Successfully",
                                               Toast.LENGTH_SHORT).show();
                                   }
                                   else{
                                       Toast.makeText(AddStudentsActivity.this,task.getException().getMessage()
                                               ,Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }catch (Exception e)
               {
                   progressDialog.dismiss();
                   Toast.makeText(AddStudentsActivity.this, "Registration fields can't be empty", Toast.LENGTH_SHORT).show();
               }
           }
       });

       binding.uploadButton2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               intent.addCategory(Intent.CATEGORY_OPENABLE);
               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               intent.setType("application/vnd.ms-excel");
               Intent i = Intent.createChooser(intent, "File");
               startActivityForResult(i, 1);

           }
       });
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && intent!=null && intent.getData() != null) {
                Uri uri = intent.getData();
                String path = getRealPathFromURI(uri);
                uploadData(path);
                }
            } else {
                Toast.makeText(AddStudentsActivity.this, "No file is selected", Toast.LENGTH_SHORT).show();
            }
    }

    private void uploadData(String path) {
        progressDialog.show();
        binding.sheetLink.setText(path);
        try {
            InputStream myInput;
            //  open excel sheet
//            File file = new File(getExternalFilesDir(null),fileName);
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            myDir.mkdirs();
            File file = new File(myDir+"/"+path);
            myInput = new FileInputStream(file);
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    String mail="", pass="";
                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==1){
                            mail = myCell.toString();
                        }else if (colno==2){
                            pass = df.format(myCell.getNumericCellValue());
                        }
                        colno++;
                    }
                    String finalPass = pass;
                    String finalMail = mail;
                    auth.createUserWithEmailAndPassword(mail,pass).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        AddUserModel user  = new AddUserModel();
                                        user.setEditTextMail(finalMail);
                                        user.setEditTextPassword(finalPass);
                                        user.setReg_no(finalPass);
                                        String id  = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(user);
                                        Toast.makeText(AddStudentsActivity.this,"User Created Successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(AddStudentsActivity.this,task.getException().getMessage()
                                                ,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                rowno++;
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            Log.d("pathCheck",e+"");
            Toast.makeText(AddStudentsActivity.this, e+"", Toast.LENGTH_LONG).show();

        }
    }

    public String getRealPathFromURI(Uri uri) {
        File file = new File(uri.getPath());//create path from uri
        final String[] split = file.getPath().split(":");
        return split[1];
    }
}