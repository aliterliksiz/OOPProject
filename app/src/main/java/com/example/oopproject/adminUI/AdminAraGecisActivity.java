package com.example.oopproject.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.oopproject.MainActivity;
import com.example.oopproject.R;

public class AdminAraGecisActivity extends AppCompatActivity {

    Button Btn_admingecis_kl,Btn_admingecis_al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ara_gecis);

        Btn_admingecis_kl = findViewById(R.id.Btn_admingecis_kl);
        Btn_admingecis_al = findViewById(R.id.Btn_admingecis_al);

        Btn_admingecis_kl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAraGecisActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

        Btn_admingecis_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAraGecisActivity.this, MainActivity.class);
        startActivity(intent);
    }
}