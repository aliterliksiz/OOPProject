package com.example.oopproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    MyDatabaseHelper myDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        myDB = new MyDatabaseHelper(getActivity().getApplicationContext());

        // get user tc and pw
        Intent intent = getActivity().getIntent();
        String tc = intent.getStringExtra("tc");
        String pw = intent.getStringExtra("pw");


        binding.dbtv1.append(myDB.getUserName(tc).toUpperCase());
        binding.dbtv2.append(myDB.getUserSurname(tc).toUpperCase());
        binding.dbtv3.append(pw);
        binding.dbtv4.append(tc);
        binding.dbtv5.append(myDB.getUserHesapNo(tc));
        binding.dbtv6.append(String.valueOf(myDB.getBakiye(tc)));



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}