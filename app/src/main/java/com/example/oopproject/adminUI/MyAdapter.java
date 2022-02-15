package com.example.oopproject.adminUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.MainActivity;
import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.models.Users.User;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<User> userList;
    private Context context;

    MyDatabaseHelper myDB ;
    public MyAdapter(ArrayList<User> list)
    {
        userList = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        myDB = new MyDatabaseHelper(context);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterrow,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        String name = userList.get(position).getAd();
        String surname = userList.get(position).getSoyad();
        String tc = userList.get(position).getTc();
        String hesapNo = userList.get(position).getHesapNo();
        String pw = userList.get(position).getPw();
        String bakiye = String.valueOf(userList.get(position).getBakiye());

        holder.adTV.append(name);
        holder.bakiyeTV.append(bakiye);
        holder.soyadTV.append(surname);
        holder.pwTV.append(pw);
        holder.tcTV.append(tc);
        holder.hesapNoTV.append(hesapNo);

        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(name + " İsimli Kullaniciyi silmek istediğinize eminmisiniz?").setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDB.deleteUser(tc);
                        Intent intent = new Intent(context,AdminActivity.class);
                        view.getContext().startActivity(intent);
                    }
                }).setNegativeButton("Hayır",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView adTV,soyadTV,tcTV,pwTV,hesapNoTV,bakiyeTV;
        private Button sil,guncelle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            adTV = itemView.findViewById(R.id.adapterTvAd);
            soyadTV = itemView.findViewById(R.id.adapterTvSoyad);
            tcTV = itemView.findViewById(R.id.adapterTvTc);
            pwTV = itemView.findViewById(R.id.adapterTvSifre);
            hesapNoTV = itemView.findViewById(R.id.adapterTvHesapno);
            bakiyeTV = itemView.findViewById(R.id.adapterTvBakiye);
            sil = itemView.findViewById(R.id.btnKullaniciSil);
            guncelle = itemView.findViewById(R.id.btnKullaniciGuncelle);
        }
    }
}
