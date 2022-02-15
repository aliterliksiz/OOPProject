package com.example.oopproject.bakiyeUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.oopproject.MainActivity;
import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.UserBottomNavigationActivity;
import com.google.zxing.Result;

public class WithdrawActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    MyDatabaseHelper myDB = new MyDatabaseHelper(WithdrawActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        Bundle gameData = getIntent().getExtras();
        String tc = gameData.getString("tc");
        String pw = gameData.getString("pw");

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int miktar =Integer.parseInt(result.toString());
                        Toast.makeText(WithdrawActivity.this, miktar + " TL ÇEKİM İŞLEMİ YAPILDI", Toast.LENGTH_SHORT).show();

                        // örnek bakiye güncelleme
                        myDB.bakiyeGüncelle(myDB.getBakiye(tc)-miktar,
                                tc,
                                myDB.getUserName(tc),
                                myDB.getUserSurname(tc),
                                myDB.getUserHesapNo(tc),
                                pw,
                                myDB.getId(tc));

                        Intent intent = new Intent(WithdrawActivity.this, UserBottomNavigationActivity.class);

                        intent.putExtra("tc",tc);
                        intent.putExtra("pw",pw);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}