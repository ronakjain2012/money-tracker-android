package com.example.ronak.myexpensetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FullStatement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_statement);
        ArrayList title = new ArrayList();
        ArrayList time = new ArrayList();
        ArrayList day = new ArrayList();
        ArrayList month = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList desc = new ArrayList();
        ArrayList type = new ArrayList();
        ArrayList amt = new ArrayList();
        ListView l1;
        l1 = (ListView) findViewById(R.id.full_state);

        Accounts obj = new Accounts(this);
        Cursor res = obj.getStatement();
        String date = "", dates;
        String[] tempHolder;
        String[] datePart;
        if (res.getCount() > 0) {
            while (res.moveToNext()) {

                title.add(res.getString(res.getColumnIndex("t_title")));
                date = (res.getString(res.getColumnIndex("t_date")));
                tempHolder = date.split(" ");
                dates = tempHolder[0] + "";
                datePart = dates.split("-");
                time.add(tempHolder[1]);
                day.add("" + datePart[2]);
                month.add("" + datePart[1]);
                year.add("" + datePart[0]);
                desc.add(res.getString(res.getColumnIndex("t_desc")));
                type.add(res.getString(res.getColumnIndex("t_type_c")));
                amt.add(res.getString(res.getColumnIndex("t_amt")));
            }
        }

        String[] tile, time1, day1, month1, year1, desc1, type1, amt1;
        tile = (String[]) title.toArray(new String[0]);
        time1 = (String[]) time.toArray(new String[0]);
        day1 = (String[]) day.toArray(new String[0]);
        month1 = (String[]) month.toArray(new String[0]);
        year1 = (String[]) year.toArray(new String[0]);
        desc1 = (String[]) desc.toArray(new String[0]);
        type1 = (String[]) type.toArray(new String[0]);
        amt1 = (String[]) amt.toArray(new String[0]);

        l1.setAdapter(new FullStatementAd(FullStatement.this, tile, time1, day1, month1, year1, desc1, type1, amt1));

    }
}
