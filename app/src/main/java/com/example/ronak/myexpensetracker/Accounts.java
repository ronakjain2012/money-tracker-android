package com.example.ronak.myexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Accounts extends SQLiteOpenHelper {

    Context context;

    public Accounts(Context context) {
        super(context, "wallet.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean makeTrans(String t_type, float amt, String title, String categ, String type, String date, String desc, float bal) {
        SQLiteDatabase db = this.getWritableDatabase();
        String t_types = null;
        if (t_type.equalsIgnoreCase("dr")) {
            t_types = "dr";
        }
        if (t_type.equalsIgnoreCase("cr")) {
            t_types = "cr";
        }

        float a = 0;

        Cursor res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr", null);
        try {
            if (res.getCount() > 0) {
                res.moveToNext();
                a = Float.parseFloat(res.getString(res.getColumnIndex("sum")));
            } else {
                a = 0 + bal;
            }
        } catch (Exception e) {
            a = 0 + bal;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("t_title", title);
        contentValues.put("t_amt", amt);
        contentValues.put("t_cate", categ);
        contentValues.put("t_type", type);
        contentValues.put("t_date", date);
        contentValues.put("t_desc", desc);
        contentValues.put("t_bal", a);
        long x = db.insert("accounts_" + t_types, null, contentValues);
        if (x > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

    double getBalance() {
        SQLiteDatabase db = this.getReadableDatabase();
        double dr = 0, cr = 0;
        try {
            Cursor res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr", null);
            try {
                if (res.getCount() > 0) {
                    res.moveToFirst();
                    dr = Double.parseDouble(res.getString(res.getColumnIndex("sum")));
                } else {
                    dr = 0;
                }
            } catch (Exception e) {
                dr = 0;
            }

            try {
                res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr", null);
                if (res.getCount() > 0) {
                    res.moveToFirst();
                    cr = Double.parseDouble(res.getString(res.getColumnIndex("sum")));
                } else {
                    cr = 0;
                }
            } catch (Exception e) {
                cr = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        db.close();
        return (dr - cr);
    }

    Cursor getMiniStatement() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from accounts_dr UNION select * from accounts_cr Order by t_date DESC LIMIT 20;", null);
        return cursor;
    }

    Cursor getStatement() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from accounts_dr UNION select * from accounts_cr Order by t_date DESC", null);
        return cursor;
    }

    Cursor getMonthStatement(String month, String year) {
        SQLiteDatabase db = getReadableDatabase();
        if (Integer.parseInt(month) <= 9) {
            month = "0" + month;
        }
        Cursor cursor = db.rawQuery("select * from accounts_dr where strftime('%m',t_date) ='" + month + "' and strftime('%Y',t_date) ='" + year + "' UNION select * from accounts_cr where strftime('%m',t_date) ='" + month + "' and strftime('%Y',t_date) ='" + year + "' Order by t_date DESC", null);
        Log.d("Query", "select * from accounts_dr where strftime('%m',t_date) ='" + month + "' and strftime('%Y',t_date) ='" + year + "' UNION select * from accounts_cr where strftime('%m',t_date) ='" + month + "' and strftime('%Y',t_date) ='" + year + "' Order by t_date DESC");
        Log.d("Query Result", cursor.getCount() + "");
        return cursor;
    }

    double getTotlaIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        db.close();
        return 0;
    }

    double getWeekIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getMonthIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getYearIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getTotlaExpense() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_cr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getWeekExpense() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }

    double getMonthExpense() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }

    double getYearExpense() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }

    boolean setPassword(String a) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("pin", "9" + a);
            if (db.update("user", values, null, null) > 0) {
                Log.d("SQLite", "Update Query Fired Success.");
                SharedPreferences preferences = context.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstPassSet", true);
                if (editor.commit()) {
                    Log.d("Shared Preference Set", " True");
                    db.close();
                    return true;
                } else {
                    Log.d("Shared Preference Set", "False");
                    db.close();
                    return false;
                }
            } else {
                Log.d("SQLite", "Update Query Fired Fail.");
                db.close();
                return false;
            }
        } catch (Exception e) {
            Log.d("SQLite Error", " ----- ");
            e.printStackTrace();
            db.close();
        }
        db.close();
        return false;
    }

    public Cursor getStatByAcc(String a) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from accounts_dr where t_cate='" + a + "' UNION select * from accounts_dr where t_cate='" + a + "' Order by t_date DESC", null);
        return cursor;
    }

    public String[] getCates() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from category Order by name ASC", null);
        arrayList.add("Select Account");
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }


    double getTotlaIncomeAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        db.close();
        return 0;
    }

    double getWeekIncomeAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_dr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getMonthIncomeAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getYearIncomeAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getTotlaExpenseAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT sum(t_amt)'sum' FROM accounts_cr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 0;
        }
        db.close();
        return 0;
    }

    double getWeekExpenseAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }

    double getMonthExpenseAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }

    double getYearExpenseAvg() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select sum(t_amt)'sum', from accounts_cr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 0;
        }
        return 0;
    }


    double getTotlaIncomeAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select count(t_amt)'sum' from accounts_dr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 1;
        }
        db.close();
        return 1;
    }

    double getWeekIncomeAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select count(t_amt)'sum' from accounts_dr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 1;
        }
        db.close();
        return 1;
    }

    double getMonthIncomeAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT count(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 1;
        }
        db.close();
        return 1;
    }

    double getYearIncomeAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT count(t_amt)'sum' FROM accounts_dr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 1;
        }
        db.close();
        return 1;
    }

    double getTotlaExpenseAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT count(t_amt)'sum' FROM accounts_cr", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            db.close();
            Log.d("", e.getMessage());
            return 1;
        }
        db.close();
        return 1;
    }

    double getWeekExpenseAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select count(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-7 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 1;
        }
        return 1;
    }

    double getMonthExpenseAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select count(t_amt)'sum' from accounts_cr WHERE t_date >= date('now','localtime', '-30 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 1;
        }
        return 1;
    }

    double getYearExpenseAvgLine() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select count(t_amt)'sum', from accounts_cr WHERE t_date >= date('now','localtime', '-365 day')", null);
            if (res.moveToNext()) {
                return Double.parseDouble(res.getString(res.getColumnIndex("sum")));
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
            db.close();
            return 1;
        }
        return 1;
    }

    boolean deleteCateg(String data) {
        SQLiteDatabase db = getWritableDatabase();
        if (db.delete("category", "name='" + data + "'", null) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
