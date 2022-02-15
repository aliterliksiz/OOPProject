package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oopproject.bakiyeUI.WithdrawActivity;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    Button musteriOlBtn;
    EditText signupAd,signupSoyad,signupTc,signupSifre,signupVergi,signupSirket;
    ArrayList<String> arrayList_id,arrayList_ad,arrayList_soyad,arrayList_tc,arrayList_no,arrayList_pw;
    ArrayList<Integer> arrayList_bakiye;
    private String[] type_array = {"BUSINESS","SME","FARMER","RETIRED","INTERNAL"};
    private Spinner spinner;
    private ArrayAdapter<String> adapterForSpinner;

    private String selectedType;

    MyDatabaseHelper myDB = new MyDatabaseHelper(SignupActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        tanimla();

        adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,type_array);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapterForSpinner);


        signupVergi.setVisibility(View.VISIBLE);
        signupTc.setVisibility(View.GONE);
        signupAd.setVisibility(View.GONE);
        signupSoyad.setVisibility(View.GONE);
        signupSirket.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedType = adapterView.getSelectedItem().toString();
                if (selectedType.equals("BUSINESS"))
                {
                    signupSirket.setVisibility(View.VISIBLE);
                    signupVergi.setVisibility(View.VISIBLE);
                    signupTc.setVisibility(View.GONE);
                    signupAd.setVisibility(View.GONE);
                    signupSoyad.setVisibility(View.GONE);
                }
                else
                {
                    signupSirket.setVisibility(View.GONE);
                    signupVergi.setVisibility(View.GONE);
                    signupTc.setVisibility(View.VISIBLE);
                    signupAd.setVisibility(View.VISIBLE);
                    signupSoyad.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        musteriOlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if (signupSirket.getVisibility() == View.VISIBLE)
                    {
                        if (!signupVergi.getText().toString().equals("") && !signupSirket.getText().toString().equals("") && !signupSifre.getText().toString().equals(""))
                        {
                            // sirketse
                            if(myDB.addCustomer(signupSirket.getText().toString().trim(),
                                    signupSifre.getText().toString().trim(),
                                    selectedType,
                                    signupVergi.getText().toString().trim()
                            ))
                            {
                                displayDB();

                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this,"LUTFEN ALANLARI DOLDURUNUZ ", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        if (!signupAd.getText().toString().equals("") && !signupSoyad.getText().toString().equals("") && !signupTc.getText().toString().equals("") && !signupSifre.getText().toString().equals(""))
                        {
                        // bireyselse
                        if(myDB.addCustomer(signupAd.getText().toString().trim(),
                                signupSoyad.getText().toString().trim(),
                                signupTc.getText().toString().trim(),
                                signupSifre.getText().toString().trim(),
                                selectedType
                        ))
                        {
                            displayDB();

                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }


                        }
                        else{
                            Toast.makeText(SignupActivity.this,"LUTFEN ALANLARI DOLDURUNUZ ", Toast.LENGTH_SHORT).show();
                        }
                    }


            }
        });

        arrayList_id = new ArrayList<>();
        arrayList_ad = new ArrayList<>();
        arrayList_soyad = new ArrayList<>();
        arrayList_tc = new ArrayList<>();
        arrayList_no = new ArrayList<>();
        arrayList_bakiye = new ArrayList<>();
        arrayList_pw = new ArrayList<>();
    }

    private void displayDB(){

        Cursor cursor = myDB.readCustomers();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext())
            {
                arrayList_id.add(cursor.getString(0));
                arrayList_ad.add(cursor.getString(1));
                arrayList_soyad.add(cursor.getString(2));
                arrayList_tc.add(cursor.getString(3));
                arrayList_pw.add(cursor.getString(4));
                arrayList_bakiye.add(cursor.getInt(5));
                arrayList_no.add(cursor.getString(6));
                Log.d("*********","************");
                Log.d("CustomerDisplay: id",cursor.getString(0));
                Log.d("CustomerDisplay: ad",cursor.getString(1));
                Log.d("CustomerDisplay: soyad",cursor.getString(2));
                Log.d("CustomerDisplay: tc",cursor.getString(3));
                Log.d("CustomerDisplay: pw",cursor.getString(4));
                Log.d("CustomerDisplay: bakiye",String.valueOf(cursor.getInt(5)));
                Log.d("CustomerDisplay: hesap no", cursor.getString(6));

            }
        }

    }

    private void tanimla() {
        musteriOlBtn = findViewById(R.id.signup_MusteriOl);
        signupAd = findViewById(R.id.signupAd);
        signupSoyad = findViewById(R.id.signupSoyad);
        signupSifre = findViewById(R.id.signupSifre);
        signupTc = findViewById(R.id.signupTc);
        spinner = findViewById(R.id.spinnerSingUp);
        signupVergi = findViewById(R.id.signupVergi);
        signupSirket = findViewById(R.id.signupSirket);
    }
}