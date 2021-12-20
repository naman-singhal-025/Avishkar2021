package com.example.avishkar2021.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.avishkar2021.R;
import com.example.avishkar2021.admin.AdminMainActivity;
import com.example.avishkar2021.databinding.ActivityMainBinding;
import com.example.avishkar2021.user.SignIn;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String m_Text;
    String pass = "n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InternetConnection internetConnection = new InternetConnection(MainActivity.this);
        internetConnection.execute();

        FirebaseDatabase.getInstance().getReference().child("KEY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    pass = snapshot.getValue().toString();
                }catch (Exception e)
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ADMIN MODE").setMessage("Enter Passkey");

                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        m_Text = input.getText().toString();
                        if(m_Text.equals(pass))
                        {
                            Intent intent = new Intent(MainActivity.this, AdminMainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Invalid Passkey", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setIcon(R.drawable.ic_baseline_warning_24).show();

            }
        });
    }

}