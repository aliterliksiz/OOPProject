package com.example.oopproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.oopproject.models.Credits.Kredi;
import com.example.oopproject.models.Users.User;

import java.util.ArrayList;
import java.util.Random;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Users.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE1_NAME = "customers";
    public static final String COLUMN1_ID = "_id";
    public static final String COLUMN1_TC = "customer_tc";
    public static final String COLUMN1_PW = "customer_pw";
    public static final String COLUMN1_NO = "customer_no";
    public static final String COLUMN1_AD = "customer_ad";
    public static final String COLUMN1_SOYAD = "customer_soyad";
    public static final String COLUMN1_BAKIYE = "customer_bakiye";
    public static final String COLUMN1_VERGINO = "customer_vergino";
    public static final String COLUMN1_TYPE = "customer_type";

    public static final String TABLE2_NAME = "admins";
    public static final String COLUMN2_ID = "_id";
    public static final String COLUMN2_PW = "admin_pw";
    public static final String COLUMN2_AD = "admin_ad";
    public static final String COLUMN2_TYPE = "admin_type";

    public static final String TABLE3_NAME = "krediler";
    public static final String COLUMN3_ID = "_id";
    public static final String COLUMN3_OWNER = "_owner";
    public static final String COLUMN3_MIKTAR = "_miktar";
    public static final String COLUMN3_TAKSITMIKTARI = "_taksitmiktari";
    public static final String COLUMN3_KALANMIKTAR = "_kalanmiktar";
    public static final String COLUMN3_TAKSITSAYISI = "_taksitsayisi";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE " + TABLE1_NAME +
                        " (" + COLUMN1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN1_AD + " TEXT, " +
                        COLUMN1_SOYAD + " TEXT, " +
                        COLUMN1_TC + " TEXT, " +
                        COLUMN1_PW + " TEXT, " +
                        COLUMN1_BAKIYE + " INTEGER, " +
                        COLUMN1_NO + " TEXT, " +
                        COLUMN1_VERGINO + " TEXT, " +
                        COLUMN1_TYPE + " TEXT);";
        sqLiteDatabase.execSQL(query);

        String query2 =
                "CREATE TABLE " + TABLE2_NAME +
                        " (" + COLUMN2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN2_PW + " TEXT, " +
                        COLUMN2_AD + " TEXT, " +
                        COLUMN2_TYPE + " TEXT);";
        sqLiteDatabase.execSQL(query2);

        String query3 =
                "CREATE TABLE " + TABLE3_NAME +
                        " (" + COLUMN3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN3_OWNER + " TEXT, " +
                        COLUMN3_MIKTAR + " INTEGER, " +
                        COLUMN3_TAKSITMIKTARI + " DOUBLE, " +
                        COLUMN3_KALANMIKTAR + " DOUBLE, " +
                        COLUMN3_TAKSITSAYISI + " INTEGER);";
        sqLiteDatabase.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(sqLiteDatabase);

    }



    public void krediOde(int id, int miktar, double taksitMiktar,double kalanMiktar,int taksitSayisi, String owner)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN3_KALANMIKTAR,kalanMiktar-taksitMiktar);
        cv.put(COLUMN3_OWNER,owner);
        cv.put(COLUMN3_TAKSITMIKTARI,taksitMiktar);
        cv.put(COLUMN3_MIKTAR,miktar);
        cv.put(COLUMN3_ID,id);
        cv.put(COLUMN3_TAKSITSAYISI,taksitSayisi-1);


        long result = db.update(TABLE3_NAME,cv,"_id=?",new String[]{String.valueOf(id)});
    }

    public ArrayList<Kredi> getKrediList()
    {
        ArrayList<Kredi> krediList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE3_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                Kredi kredi = new Kredi(cursor.getInt(0),cursor.getInt(2),cursor.getInt(5),cursor.getString(1),cursor.getDouble(3),cursor.getDouble(4));
                krediList.add(kredi);
            }
        }
        return krediList;
    }

    public ArrayList<Kredi> getMyKrediList(String hesapNo)
    {
        ArrayList<Kredi> krediList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE3_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (cursor.getString(1).equals(hesapNo))
                {
                    Kredi kredi = new Kredi(cursor.getInt(0),cursor.getInt(2),cursor.getInt(5),cursor.getString(1),cursor.getDouble(3),cursor.getDouble(4));
                    krediList.add(kredi);
                }
            }
        }
        return krediList;
    }


    public void addKredi(String owner, int miktar, int taksitsayisi, double geriOdeme)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN3_MIKTAR,miktar);
        cv.put(COLUMN3_TAKSITSAYISI,taksitsayisi);
        cv.put(COLUMN3_OWNER,owner);
        cv.put(COLUMN3_TAKSITMIKTARI,geriOdeme/taksitsayisi);
        cv.put(COLUMN3_KALANMIKTAR,geriOdeme);

        long result = db.insert(TABLE3_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Kredi çekme işleminde hata oldu, lütfen tekrar deneyiniz!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Kredi çekim işlemi başarılı kredilerim sayfasından takip edebilirsiniz",Toast.LENGTH_SHORT).show();
        }
    }

    void addAdmin(String ad,String pw){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN2_PW,pw);
        cv.put(COLUMN2_AD,ad);

        long result = db.insert(TABLE2_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Failed admin add",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Admin added",Toast.LENGTH_SHORT).show();
        }
    }

    boolean addCustomer(String ad, String pw, String type,String vergiNo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
            // TODO isCustomerexist() should be added
            cv.put(COLUMN1_AD,ad);
            cv.put(COLUMN1_SOYAD,"");
            cv.put(COLUMN1_TC,"");
            cv.put(COLUMN1_NO,getRandomNumberString());
            cv.put(COLUMN1_PW,pw);
            cv.put(COLUMN1_BAKIYE,0);
            cv.put(COLUMN1_VERGINO, vergiNo);
            cv.put(COLUMN1_TYPE,type);

            long result = db.insert(TABLE1_NAME,null,cv);
            if (result == -1){
                Toast.makeText(context,"Failed customer add",Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                Toast.makeText(context,"Kayıt işlemi tamamlandı",Toast.LENGTH_SHORT).show();
                return true;
            }

    }

    boolean addCustomer(String ad,String soyad,String tc, String pw, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (!isCustomerExist(tc))
        {
            cv.put(COLUMN1_AD,ad);
            cv.put(COLUMN1_SOYAD,soyad);
            cv.put(COLUMN1_TC,tc);
            cv.put(COLUMN1_NO,getRandomNumberString());
            cv.put(COLUMN1_PW,pw);
            cv.put(COLUMN1_BAKIYE,0);
            cv.put(COLUMN1_TYPE,type);
            cv.put(COLUMN1_VERGINO, "");

            long result = db.insert(TABLE1_NAME,null,cv);
            if (result == -1){
                Toast.makeText(context,"Failed customer add",Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                Toast.makeText(context,"Kayıt işlemi tamamlandı",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else{
            return false;
        }

    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public boolean isUserLoginSuccess(String tc, String pw)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }

        while (cursor.moveToNext())
        {
            if (cursor.getString(4).equals(pw))
            {
                if (cursor.getString(3).equals(tc)|| cursor.getString(6).equals(tc))
                {
                    return true;
                }
            }
        }

        Toast.makeText(context,"Kullanıcı giriş bilgileri yanlış !",Toast.LENGTH_SHORT).show();
        return false;
    }

    public String getType(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6)) || hesapNo.equals(cursor.getString(3))){
                    return cursor.getString(8);
                }
            }
        }
        return "";
    }

    public boolean isAdminExist(String ad,String pw){
        String query = "SELECT * FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }

        while (cursor.moveToNext())
        {
            if (cursor.getString(1).equals(pw) && cursor.getString(2).equals(ad))
            {
                return true;
            }
        }

        Toast.makeText(context,"Admin giriş bilgileri yanlış !",Toast.LENGTH_SHORT).show();
        return false;

    }

    Cursor readCustomers(){
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    boolean isCustomerExist(String tc){
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    Toast.makeText(context,"Bu TC kimlik numarası başka bir kullanıcıya ait",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        else
        {
            Toast.makeText(context,"Database is null",Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    public Integer deleteUser(String tc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE1_NAME,"customer_tc = ?", new String[] {tc});
    }

    public ArrayList<User> getUserList()
    {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                User user = new User(cursor.getInt(5),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(6));
                userList.add(user);
            }
        }
        return userList;
    }

    public String getUserName(String tc)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    return cursor.getString(1);
                }
            }
        }
        return "";
    }

    public String getUserNamefromHesapNo(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6))){
                    return cursor.getString(1);
                }
            }
        }
        return "";
    }

    public String getUserSurname(String tc)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    return cursor.getString(2);
                }
            }
        }
        return "";
    }

    public String getUserSurnamefromHesapNo(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6))){
                    return cursor.getString(2);
                }
            }
        }
        return "";
    }

    public String getUserHesapNo(String tc)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    return cursor.getString(6);
                }
            }
        }
        return "NULL";
    }

    public void displayDB()
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                Log.d("DİSPLAYDB" , "**********************************");
                Log.d("DİSPLAYDB" , " İD : " + cursor.getString(0));
                Log.d("DİSPLAYDB" , " AD : " + cursor.getString(1));
                Log.d("DİSPLAYDB" , " SOYAD : " + cursor.getString(2));
                Log.d("DİSPLAYDB" , " TC : " + cursor.getString(3));
                Log.d("DİSPLAYDB" , " PW : " + cursor.getString(4));
                Log.d("DİSPLAYDB" , " BAKİYE : " + cursor.getString(5));
                Log.d("DİSPLAYDB" , " HESAP NO : " + cursor.getString(6));
                Log.d("DİSPLAYDB" , " VERGI NO : " + cursor.getString(7));
                Log.d("DİSPLAYDB" , " TYPE : " + cursor.getString(8));
                Log.d("DİSPLAYDB" , "**********************************");

            }
        }
    }

    public void displayAdminDB()
    {
        String query = "SELECT * FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                Log.d("DİSPLAYDB" , "**********************************");
                Log.d("DİSPLAYDB" , " İD : " + cursor.getString(0));
                Log.d("DİSPLAYDB" , " ŞİFRE : " + cursor.getString(1));
                Log.d("DİSPLAYDB" , " AD : " + cursor.getString(2));
                Log.d("DİSPLAYDB" , "**********************************");

            }
        }
    }



    public int getBakiye(String tc)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    return cursor.getInt(5);
                }
            }
        }
        return 0;
    }

    public int getBakiyefromNo(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6))){
                    return cursor.getInt(5);
                }
            }
        }
        return 0;
    }

    public String getTcFromNo(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6))){
                    return cursor.getString(3);
                }
            }
        }
        return "";
    }
    public String getPwFromNo(String hesapNo)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (hesapNo.equals(cursor.getString(6))){
                    return cursor.getString(4);
                }
            }
        }
        return "";
    }

    public int getId(String tc)
    {
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                if (tc.equals(cursor.getString(3))){
                    return cursor.getInt(0);
                }
            }
        }
        return 0;
    }

    public void bakiyeGüncelle(int miktar,String tc,String ad,String soyad,String hesapNo,String pw,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN1_BAKIYE,miktar);
        cv.put(COLUMN1_SOYAD,soyad);
        cv.put(COLUMN1_PW,pw);
        cv.put(COLUMN1_ID,id);
        cv.put(COLUMN1_AD,ad);
        cv.put(COLUMN1_NO,hesapNo);
        cv.put(COLUMN1_TC,tc);

        long result = db.update(TABLE1_NAME,cv,"customer_no=?",new String[]{hesapNo});

    }
}
