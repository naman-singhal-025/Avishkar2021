package com.example.avishkar2021.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avishkar2021.adapters.FragmentsAdapter;
import com.example.avishkar2021.databinding.FragmentProfileBinding;

//fragment to hold sub-fragments: personal, academic, project, photo and password
public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.viewPager.setAdapter(new FragmentsAdapter(getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }
}