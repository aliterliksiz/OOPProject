package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oopproject.adminUI.AdminActivity;
import com.example.oopproject.adminUI.AdminAraGecisActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private String admin1ad = "umut";
    private String admin1pw = "123";
    private Button adminLogin_btnLogin;
    private EditText adminLogin_adEt,adminLogin_pwEt;


    MyDatabaseHelper myDB = new MyDatabaseHelper(AdminLoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        tanimla();

        if (!myDB.isAdminExist(admin1ad,admin1pw))
        {
            myDB.addAdmin(admin1ad,admin1pw);
        }
        myDB.displayAdminDB();

        adminLogin_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(myDB.isAdminExist(adminLogin_adEt.getText().toString().trim(),adminLogin_pwEt.getText().toString().trim())){
                   Intent intent = new Intent(AdminLoginActivity.this, AdminAraGecisActivity.class);
                   startActivity(intent);
               }
            }
        });


    }

    private void tanimla() {
        adminLogin_adEt = findViewById(R.id.adminLogin_adEt);
        adminLogin_pwEt = findViewById(R.id.adminLogin_pwEt);
        adminLogin_btnLogin = findViewById(R.id.adminLogin_btnLogin);

    }
}