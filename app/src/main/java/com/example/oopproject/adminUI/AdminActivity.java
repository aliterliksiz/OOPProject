package com.example.oopproject.adminUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.oopproject.AdminLoginActivity;
import com.example.oopproject.MainActivity;
import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.models.Users.User;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private ArrayList<User> userList;
    private RecyclerView recyclerView;

    MyDatabaseHelper myDB = new MyDatabaseHelper(AdminActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.rvAdmin);
        userList = new ArrayList<>();

        userList = myDB.getUserList();


        setAdapter();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminActivity.this,AdminAraGecisActivity.class);
        startActivity(intent);
    }

    private void setAdapter() {
        MyAdapter adapter =  new MyAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}