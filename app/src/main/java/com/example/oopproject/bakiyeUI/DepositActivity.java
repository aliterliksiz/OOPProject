package com.example.oopproject.bakiyeUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;

public class DepositActivity extends AppCompatActivity {

    EditText etBakiyeYukle;
    Button btnBakiyeYukle;

    MyDatabaseHelper myDB = new MyDatabaseHelper(DepositActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakiye_yukle);

        etBakiyeYukle = findViewById(R.id.etBakiyeYukle);
        btnBakiyeYukle = findViewById(R.id.btnBakiyeYukle);

        Intent intent = getIntent();
        String tc = intent.getStringExtra("tc");
        String pw = intent.getStringExtra("pw");

        btnBakiyeYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDB.bakiyeGüncelle(myDB.getBakiye(tc)+ Integer.parseInt(etBakiyeYukle.getText().toString()),
                        tc,
                        myDB.getUserName(tc),
                        myDB.getUserSurname(tc),
                        myDB.getUserNamefromHesapNo(tc),
                        pw,
                        myDB.getId(tc));
                Toast.makeText(DepositActivity.this,"Yeni bakiyeniz : "  + myDB.getBakiye(tc) , Toast.LENGTH_SHORT).show();

            }
        });

    }
}