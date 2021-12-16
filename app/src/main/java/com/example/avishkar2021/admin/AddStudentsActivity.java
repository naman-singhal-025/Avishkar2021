package com.example.avishkar2021.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avishkar2021.Adapters.AddUserAdapter;
import com.example.avishkar2021.databinding.ActivityAddStudentsBinding;
import com.example.avishkar2021.models.AddUserModel;
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
import java.util.ArrayList;
import java.util.Iterator;

public class AddStudentsActivity extends AppCompatActivity {

    ActivityAddStudentsBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AddStudentsActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Uploading data");


       binding.uploadButton1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressDialog.show();
               String mail = binding.editMail.getText().toString();
               String pass = binding.editPass.getText().toString();
               String reg_no = binding.editRegNo.getText().toString();

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
           }
       });

       binding.uploadButton2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressDialog.show();
               try {
                   InputStream myInput;
                   String fileName = binding.sheetLink.getText().toString();
                   //  open excel sheet
                   File file = new File(getExternalFilesDir(null),fileName);
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
                                   pass = myCell.toString();
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
                   Toast.makeText(AddStudentsActivity.this, "Error", Toast.LENGTH_SHORT).show();

               }

           }
       });
    }

    private void createUser(String mail, String pass, String reg_no) {

    }
}