package com.example.avishkar2021.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.avishkar2021.fragments.AcademicFragment;
import com.example.avishkar2021.fragments.PasswordFragment;
import com.example.avishkar2021.fragments.PersonalFragment;
import com.example.avishkar2021.fragments.PhotoResumeFragment;
import com.example.avishkar2021.fragments.ProjectFragment;

//fragments adapter for displaying 5 sub-fragments within profile fragment
public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //Returns the fragment at the pos index.
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 1: return new AcademicFragment();
            case 2: return new ProjectFragment();
            case 3: return new PhotoResumeFragment();
            case 4: return new PasswordFragment();
            default: return new PersonalFragment();
        }
    }

    //This method returns the number of fragments to display
    @Override
    public int getCount() {
        return 5;
    }

    //this methods returns the title of the page at index pos.
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0)
        {
            title = "Personal";
        }
        if(position == 1)
        {
            title = "Academic";
        }
        if(position == 2)
        {
            title = "Project/Intern";
        }
        if(position == 3)
        {
            title = "Photo/Resume";
        }
        if(position == 4)
        {
            title = "Password";
        }
        return title;
    }
}

