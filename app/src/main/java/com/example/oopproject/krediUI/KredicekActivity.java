package com.example.oopproject.krediUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.oopproject.MainActivity;
import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.models.Credits.Kredi;
import com.example.oopproject.models.Users.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KredicekActivity extends AppCompatActivity {

    private Button krediOnay;
    private EditText krediMiktari,taksitSayisi;
    private Spinner krediTaksitSpinner;
    private TextView odemePlaniTV,krediFaizTV,faizMiktariKrediCek,tvGeriOdememiktari;
    private int ItaksitSayisi;
    private String[] taksitSayisiArray = {"3","6","9","12","24","36"};
    private ArrayAdapter<String> adapterForSpinner;
    private double faizOrani;
    private double geriOdemeTutari;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kredicek);
        tanimla();

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,taksitSayisiArray);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myDB = new MyDatabaseHelper(KredicekActivity.this);

        String hesapNo = prefs.getString("hesapNo", "No name defined");

        krediFaizTV.setText("KREDİ ÇEKME EKRANINA HOŞGELDİNİZ HESAP TÜRÜNÜZ "  +  myDB.getType(hesapNo).toUpperCase());

        faizMiktariKrediCek.setText("HESAP TÜRÜNÜZ " + myDB.getType(hesapNo).toUpperCase() + " OLDUĞUNDAN DOLAYI SİZE SUNDUĞUMUZ FAİZ ORANI ");

        if (myDB.getType(hesapNo).equals("FARMER"))
        {
            faizMiktariKrediCek.append("1.78");
            faizOrani = 17.8;
        }
        else if (myDB.getType(hesapNo).equals("SME"))
        {
            faizMiktariKrediCek.append("2.08");
            faizOrani = 20.8;
        }
        else if(myDB.getType(hesapNo).equals("INTERNAL"))
        {
            faizMiktariKrediCek.append("1.50");
            faizOrani = 15.0;
        }
        else if (myDB.getType(hesapNo).equals("BUSINESS"))
        {
            faizMiktariKrediCek.append("2.20");
            faizOrani = 22.0;
        }
        else if (myDB.getType(hesapNo).equals("RETIRED"))
        {
            faizMiktariKrediCek.append("1.80");
            faizOrani = 18.0;
        }


        krediTaksitSpinner.setAdapter(adapterForSpinner);

        krediTaksitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ItaksitSayisi = Integer.parseInt(adapterView.getSelectedItem().toString());
                if (!krediMiktari.getText().toString().equals(""))
                {
                    DecimalFormat formatter = new DecimalFormat("#0.00");
                    int cekilenMiktar = Integer.parseInt(krediMiktari.getText().toString());
                    double aylikGeriOdeme = Double.parseDouble(krediMiktari.getText().toString()) / Double.parseDouble(adapterView.getSelectedItem().toString());
                    odemePlaniTV.setText("KREDİNİZİN AYLIK ODEME MIKTARI = " + formatter.format(aylikGeriOdeme) + " TL");
                    double aylikOdeme = (cekilenMiktar/ItaksitSayisi)+((cekilenMiktar/ItaksitSayisi)*(faizOrani/100));
                    geriOdemeTutari = aylikOdeme*ItaksitSayisi;
                    tvGeriOdememiktari.setText(formatter.format(geriOdemeTutari) + " TL");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        krediOnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int miktar = Integer.parseInt(krediMiktari.getText().toString());

                if (myDB.getType(hesapNo).equals("BUSINESS"))
                {
                    myDB.addKredi(hesapNo,miktar,ItaksitSayisi,geriOdemeTutari);
                }
                else
                {
                    myDB.addKredi(myDB.getUserHesapNo(hesapNo),miktar,ItaksitSayisi,geriOdemeTutari);
                }

                ArrayList<Kredi> krediArrayList = new ArrayList<>();

                krediArrayList = myDB.getKrediList();

                for (Kredi i : krediArrayList)
                {
                    Log.d("KREDİ LİST " , "***************");
                    Log.d("KREDİ LİST " , "owner no " +i.getOwnerNo());
                    Log.d("KREDİ LİST " , "id " +i.getId());
                    Log.d("KREDİ LİST " , "miktar "+i.getMiktar());
                    Log.d("KREDİ LİST " , "kalan miktar "+i.getKalanMiktar());
                    Log.d("KREDİ LİST " , "aylik taksit " + i.getAylikTaksit());
                    Log.d("KREDİ LİST " , "taksit sayisi " + i.getTaksitSayisi());
                    Log.d("KREDİ LİST " , "***************");
                }

            }
        });

    }
    private void tanimla() {
        krediOnay = findViewById(R.id.krediCekonayButton);
        krediMiktari = findViewById(R.id.krediCekMiktar);
        krediTaksitSpinner = findViewById(R.id.spinnerKrediTaksit);
        odemePlaniTV = findViewById(R.id.odemePlaniTV);
        krediFaizTV = findViewById(R.id.krediFaizTV);
        tvGeriOdememiktari = findViewById(R.id.tvGeriOdememiktari);
        faizMiktariKrediCek = findViewById(R.id.faizMiktariKrediCek);
    }
}