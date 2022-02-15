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
import com.example.oopproject.UserBottomNavigationActivity;

public class TransferActivity extends AppCompatActivity {

    EditText etTransferHesapNo,etTransferMiktar;
    Button btnTransfer;

    MyDatabaseHelper myDB = new MyDatabaseHelper(TransferActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Bundle gameData = getIntent().getExtras();
        String tc = gameData.getString("tc");
        String pw = gameData.getString("pw");

        etTransferHesapNo = findViewById(R.id.etTransferHesapNo);
        etTransferMiktar = findViewById(R.id.etTransferMiktar);
        btnTransfer = findViewById(R.id.btnTransfer);


        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hesapNo = etTransferHesapNo.getText().toString();
                int miktar = Integer.parseInt(etTransferMiktar.getText().toString());

                if (myDB.getUserHesapNo(tc).equals("NULL"))
                {
                    Toast.makeText(TransferActivity.this,"HESAP NUMARASI YANLIŞ! " ,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // gönderenden bakiye eksiltme
                    myDB.bakiyeGüncelle(myDB.getBakiye(tc)-miktar,
                            tc,
                            myDB.getUserName(tc),
                            myDB.getUserSurname(tc),
                            myDB.getUserHesapNo(tc),
                            pw,
                            myDB.getId(tc));

                    // alıcıya bakiye yükleme
                    myDB.bakiyeGüncelle(myDB.getBakiye(myDB.getTcFromNo(hesapNo))+miktar,
                            myDB.getTcFromNo(hesapNo),
                            myDB.getUserName(myDB.getTcFromNo(hesapNo)),
                            myDB.getUserSurname(myDB.getTcFromNo(hesapNo)),
                            myDB.getUserHesapNo(myDB.getTcFromNo(hesapNo)),
                            myDB.getPwFromNo(hesapNo),
                            myDB.getId(myDB.getTcFromNo(hesapNo)));

                    Toast.makeText(TransferActivity.this,"TRANSFER BAŞARILI  " ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TransferActivity.this, UserBottomNavigationActivity.class);

                    intent.putExtra("tc",tc);
                    intent.putExtra("pw",pw);
                    startActivity(intent);

                }



            }
        });

    }
}