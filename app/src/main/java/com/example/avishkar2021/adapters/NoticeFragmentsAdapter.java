package com.example.avishkar2021.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.avishkar2021.fragments.AddNewNoticeFragment;
import com.example.avishkar2021.fragments.NoticeBoardFragment;

//fragments adapter for displaying 2 fragments within profile notice activity on admin side
public class NoticeFragmentsAdapter extends FragmentPagerAdapter {

    public NoticeFragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //Returns the fragment at the pos index.
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 1: return new NoticeBoardFragment();
            default: return new AddNewNoticeFragment();
        }
    }

    //This method returns the number of fragments to display
    @Override
    public int getCount() {
        return 2;
    }

    //this methods returns the title of the page at index pos.
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0)
        {
            title = "Publish New Notice";
        }
        if(position == 1)
        {
            title = "Notice Board";
        }
        return title;
    }
}

