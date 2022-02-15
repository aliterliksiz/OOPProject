package com.example.oopproject.krediUI;

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

import com.example.oopproject.MyDatabaseHelper;
import com.example.oopproject.R;
import com.example.oopproject.adminUI.AdminActivity;
import com.example.oopproject.models.Credits.Kredi;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KrediAdapter extends RecyclerView.Adapter<KrediAdapter.myViewHolder> {

    private ArrayList<Kredi> krediArrayList;
    private Context context;

    MyDatabaseHelper myDB ;
    public KrediAdapter(ArrayList<Kredi> list)
    {
        krediArrayList = list;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        myDB = new MyDatabaseHelper(context);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kredi_item,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String miktar = String.valueOf(krediArrayList.get(position).getMiktar());
        String taksitSayisi = String.valueOf(krediArrayList.get(position).getTaksitSayisi());
        String geriOdemeMiktari = String.valueOf(krediArrayList.get(position).getKalanMiktar());
        int taksitTutari = (int) krediArrayList.get(position).getAylikTaksit();
        String kalanMiktar = String.valueOf(krediArrayList.get(position).getKalanMiktar());
        String taksitMiktari = String.valueOf(krediArrayList.get(position).getAylikTaksit());
        int id = krediArrayList.get(position).getId();
        String owner = krediArrayList.get(position).getOwnerNo();


        holder.kalanmiktarTV.append(kalanMiktar+ " TL");
        //holder.geriOdemeMiktariTV.append(geriOdemeMiktari+ " TL");
        holder.miktarTV.append(miktar+ " TL");
        holder.taksitSayisiTV.append(taksitSayisi+ " AY");
        holder.adapterTvTaksitmiktari.append(taksitMiktari + " TL");

        holder.taksitOdeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(taksitMiktari + " TL hesabınızdan çekilecek onaylıyormusunuz?").setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDB.krediOde(id,
                                Integer.parseInt(miktar),
                                Double.parseDouble(taksitMiktari),
                                Double.parseDouble(kalanMiktar),
                                Integer.parseInt(taksitSayisi),
                                owner
                                );

                        if (myDB.getType(owner).equals("BUSINESS"))
                        {
                            myDB.bakiyeGüncelle(myDB.getBakiyefromNo(owner)-taksitTutari,myDB.getTcFromNo(owner),myDB.getUserNamefromHesapNo(owner),myDB.getUserSurnamefromHesapNo(owner),owner,myDB.getPwFromNo(owner),id);
                        }
                        else
                        {
                            myDB.bakiyeGüncelle(myDB.getBakiyefromNo(owner)-taksitTutari,myDB.getTcFromNo(owner),myDB.getUserNamefromHesapNo(owner),myDB.getUserSurnamefromHesapNo(owner),owner,myDB.getPwFromNo(owner),id);
                        }
                    }
                }).setNegativeButton("Hayır",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return krediArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        private TextView miktarTV,taksitSayisiTV,geriOdemeMiktariTV,kalanmiktarTV,adapterTvTaksitmiktari;
        private Button taksitOdeButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            miktarTV = itemView.findViewById(R.id.adapterTVMiktar);
            taksitSayisiTV = itemView.findViewById(R.id.adapterTvTaksitSayisi);
            kalanmiktarTV = itemView.findViewById(R.id.adapterTvKalanmiktar);
            taksitOdeButton = itemView.findViewById(R.id.taksitOdeButton);
            adapterTvTaksitmiktari = itemView.findViewById(R.id.adapterTvTaksitmiktari);
        }
    }
}
