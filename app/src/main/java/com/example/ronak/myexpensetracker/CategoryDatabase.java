package com.example.ronak.myexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ronak on 18-04-17.
 */

public class CategoryDatabase extends SQLiteOpenHelper {
    public CategoryDatabase(Context context) {
        super(context, "wallet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table category " +
                        "(_id integer primary key AUTOINCREMENT, name text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS category");
        onCreate(db);
    }

    public String[] getCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from category", null);
        ArrayList arrayList = new ArrayList();
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                arrayList.add(res.getString(res.getColumnIndex("name")));
            }
            db.close();
            return ((String[]) arrayList.toArray(new String[0]));
        }
        db.close();
        return ((String[]) arrayList.toArray(new String[0]));
    }

    public boolean setCategory(String Data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", Data);
        db.insert("category", null, contentValues);
        db.close();
        return true;
    }
}
