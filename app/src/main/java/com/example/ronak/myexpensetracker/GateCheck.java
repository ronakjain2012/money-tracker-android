package com.example.ronak.myexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GateCheck extends SQLiteOpenHelper {
    Context context;

    public GateCheck(Context context) {
        super(context, "wallet.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table user " +
                        "(_id integer primary key AUTOINCREMENT , name text,pin int(6))"
        );

        db.execSQL(
                "create table category " +
                        "(_id integer primary key AUTOINCREMENT, name text)");
        db.execSQL(
                "create table accounts_dr " +
                        "(_id INTEGER  primary key AUTOINCREMENT,t_title text,t_amt DECIMAL(10,2),t_cate text,t_type text,t_date text,t_desc TEXT,t_bal DECIMAL(10,2),t_type_c TEXT NOT NULL DEFAULT 'Income')"
        );
        db.execSQL(
                "create table accounts_cr " +
                        "(_id INTEGER  primary key AUTOINCREMENT,t_title text,t_amt DECIMAL(10,2),t_cate text,t_type text,t_date text,t_desc TEXT,t_bal DECIMAL(10,2),t_type_c TEXT NOT NULL DEFAULT 'Expense')"
        );
        insertMaster(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user,category,accounts_cr,accounts_dr");
        onCreate(db);
    }

    public void initiDB() {
        SQLiteDatabase db = getReadableDatabase();
        Log.d("Database", "Initialization");
        db.close();
    }

    boolean isFirstTime() {
        SharedPreferences preferences = context.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        boolean userFirstLogin = preferences.getBoolean("userVisti", false);
        if (userFirstLogin == true) {
            return false;
        } else {
            return true;
        }
    }

    boolean setFirstTime() {
        SharedPreferences preferences = context.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("userVisit", true);
        if (editor.commit()) {
            return true;
        }
        return false;
    }

    void insertMaster(SQLiteDatabase db) {
        if (isFirstTime()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "root");
            contentValues.put("pin", 91000);
            db.insert("user", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Personal");
            db.insert("category", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Travel");
            db.insert("category", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Food");
            db.insert("category", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Electricity");
            db.insert("category", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Rent");
            db.insert("category", null, contentValues);
            contentValues.clear();

            contentValues.put("name", "Entertainment");
            db.insert("category", null, contentValues);
            contentValues.clear();
        }

    }

    public boolean checkUser(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (data.length() > 3) {
                Cursor res = db.rawQuery("select * from user where name='root' and pin=9" + Integer.parseInt(data), null);
                if (res.getCount() > 0) {
                    db.close();
                    return true;
                }
            }
        } catch (Exception a) {
            db.close();
            return false;
        }
        db.close();
        return false;
    }
}
