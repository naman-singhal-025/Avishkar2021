package com.example.avishkar2021;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avishkar2021.models.Users;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.avishkar2021.databinding.ActivityMain2Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView textItemCount;
    ProgressDialog progressDialog;
    long count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //to set user image, mail and name in nav header
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.userName);
        TextView navUserMail = headerView.findViewById(R.id.userMail);
        ImageView navUserImage = headerView.findViewById(R.id.userImage);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.viewInterviewFragment,
                R.id.registeredCompaniesFragment, R.id.forumFragment, R.id.contacts2,R.id.addNewInterviewFragment,
                R.id.profileFragment, R.id.currentOpeningsFragment, R.id.logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        database.getReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot subshot) {
                        DataSnapshot snapshot = subshot.child("Users").child(auth.getInstance().getUid());
                        Users users  = snapshot.getValue(Users.class);
                        //set profile image from database
                        Picasso.get().load(users.getProfilePic())
                                .placeholder(R.drawable.avatar)
                                .into(navUserImage);
                        if(snapshot.child("personal").child("1").exists())
                        {
                            navUserName.setText(snapshot.child("personal").child("1").getValue().toString());
                        }
                        else
                        {
                            navUserName.setText("User name");
                        }
                        try
                        {
                            long x=0,y=0;
                            x = snapshot.child("notice_seen").getChildrenCount();
                            y = subshot.child("notice").getChildrenCount();
                            count = y-x;
//                            Toast.makeText(MainActivity2.this, count+"", Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {

                        }
                        setupBadge();
                        navUserMail.setText(snapshot.child("editTextMail").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // set on click listener to logout in navigation view
        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            auth.signOut();
            finish();
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        MenuItem menuItem = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(menuItem,R.layout.custom_action_item_layout);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textItemCount = actionView.findViewById(R.id.badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,UserNoticeActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupBadge() {
        if (textItemCount!= null) {
            if (count == 0) {
                if (textItemCount.getVisibility() != View.GONE) {
                    textItemCount.setVisibility(View.GONE);
                }
            } else {
                if(count<=20)
                    textItemCount.setText(String.valueOf(count));
                else
                    textItemCount.setText("9+");
                if (textItemCount.getVisibility() != View.VISIBLE) {
                    textItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}