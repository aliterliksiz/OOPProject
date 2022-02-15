package com.example.oopproject.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.oopproject.MainActivity;
import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.bakiyeUI.DepositActivity;
import com.example.oopproject.bakiyeUI.TransferActivity;
import com.example.oopproject.bakiyeUI.WithdrawActivity;
import com.example.oopproject.databinding.FragmentHomeBinding;
import com.example.oopproject.krediUI.KredicekActivity;

import org.eazegraph.lib.models.PieModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    MyDatabaseHelper myDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        myDB = new MyDatabaseHelper(getActivity().getApplicationContext());

        myDB.displayDB();

        // get user tc and pw
        Intent intent = getActivity().getIntent();
        String tc = intent.getStringExtra("tc");
        String pw = intent.getStringExtra("pw");


        // örnek bakiye güncelleme
        myDB.bakiyeGüncelle(myDB.getBakiye(tc)+0,
                tc,
                myDB.getUserName(tc),
                myDB.getUserSurname(tc),
                myDB.getUserHesapNo(tc),
                pw,
                myDB.getId(tc));

        // karşılama yazısı
        binding.tvHome.setText("HOŞGELDİN "  + myDB.getUserName(tc).toUpperCase() +" "+ myDB.getUserSurname(tc).toUpperCase());

        // pie chart implementation
        binding.pieChart.addPieSlice(new PieModel("Integer 1", 100, Color.parseColor("#FFA726")));
        binding.pieChart.setUseInnerValue(true);
        binding.pieChart.setInnerValueString("CURRENT BALANCE : "+myDB.getBakiye(tc));
        binding.pieChart.setInnerPaddingColor(R.color.red);
        binding.pieChart.setInnerValueSize(30);
        binding.pieChart.setInnerValueColor(R.color.black);
        binding.pieChart.startAnimation();

        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        binding.bakiyeYukleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity().getApplicationContext(), DepositActivity.class);
                intent1.putExtra("tc",tc);
                intent1.putExtra("pw",pw);
                startActivity(intent1);

            }
        });

        binding.paraCekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity().getApplicationContext(), WithdrawActivity.class);
                intent1.putExtra("tc",tc);
                intent1.putExtra("pw",pw);
                startActivity(intent1);
            }
        });

        binding.paraGonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity().getApplicationContext(), TransferActivity.class);
                intent1.putExtra("tc",tc);
                intent1.putExtra("pw",pw);
                startActivity(intent1);
            }
        });

        binding.krediCekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity().getApplicationContext(), KredicekActivity.class);
                intent1.putExtra("tc",tc);
                intent1.putExtra("pw",pw);
                startActivity(intent1);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}