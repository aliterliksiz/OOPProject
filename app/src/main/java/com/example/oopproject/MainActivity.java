package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oopproject.models.Users.User;

public class MainActivity extends AppCompatActivity {

    private Button btn_Login,btn_LoginKayitOl,btn_LoginAdmin;
    private EditText et_LoginTC,et_LoginPW;

    private String tc;
    private String pw;


    MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tanimla();

        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();



        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc = et_LoginTC.getText().toString();
                pw = et_LoginPW.getText().toString();

                if (myDB.isUserLoginSuccess(tc,pw)){
                    editor.putString("hesapNo", et_LoginTC.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this,UserBottomNavigationActivity.class);
                    intent.putExtra("tc",tc);
                    intent.putExtra("pw",pw);
                    startActivity(intent);
                }


            }
        });

        btn_LoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
                startActivity(intent);

            }
        });

        btn_LoginKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

    }

    private void tanimla() {
        btn_Login = findViewById(R.id.btn_Login);
        btn_LoginAdmin = findViewById(R.id.btn_LoginAdmin);
        btn_LoginKayitOl = findViewById(R.id.btn_LoginKayitOl);
        et_LoginPW = findViewById(R.id.et_LoginPW);
        et_LoginTC = findViewById(R.id.et_LoginTC);
    }
}