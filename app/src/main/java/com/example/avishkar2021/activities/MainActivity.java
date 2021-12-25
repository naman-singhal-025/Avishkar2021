package com.example.avishkar2021.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.avishkar2021.R;
import com.example.avishkar2021.admin.AdminMainActivity;
import com.example.avishkar2021.databinding.ActivityMainBinding;
import com.example.avishkar2021.user.SignIn;
import com.example.avishkar2021.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// main activity for opting b/w user and admin login
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String m_Text;
    String pass = "n";
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_white));
        toolbar.setTitle("1-Placement");
        setSupportActionBar(toolbar);

        InternetConnection internetConnection = new InternetConnection(MainActivity.this);
        internetConnection.execute();

        // using firebase to extract key for admin mode
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

        //setting on click listener on college logo
        binding.imageViewCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(count>=5)
                {
                    count=0;
                    binding.register.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Admin mode activated", Toast.LENGTH_LONG).show();
                }
            }
        });

        //setting on click listener on login btn for user login
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        //setting on click listener on register btn for entering admin mode through passkey
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