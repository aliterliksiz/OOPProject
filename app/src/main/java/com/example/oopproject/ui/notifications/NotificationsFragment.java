package com.example.oopproject.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.adminUI.AdminActivity;
import com.example.oopproject.adminUI.MyAdapter;
import com.example.oopproject.databinding.FragmentNotificationsBinding;
import com.example.oopproject.krediUI.KrediAdapter;
import com.example.oopproject.models.Credits.Kredi;
import com.example.oopproject.models.Users.User;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    private ArrayList<Kredi> krediArrayList;
    private RecyclerView recyclerView;

    MyDatabaseHelper myDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        myDB = new MyDatabaseHelper(getActivity().getApplicationContext());


        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String hesapNo = prefs.getString("hesapNo", "No name defined");


        if (myDB.getType(hesapNo).equals("BUSINESS"))
        {

        }
        else
        {
            hesapNo = myDB.getUserHesapNo(hesapNo);
        }
        krediArrayList = new ArrayList<>();
        krediArrayList = myDB.getMyKrediList(hesapNo);

        KrediAdapter adapter =  new KrediAdapter(krediArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.rvKredi.setLayoutManager(layoutManager);
        binding.rvKredi.setItemAnimator(new DefaultItemAnimator());
        binding.rvKredi.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}