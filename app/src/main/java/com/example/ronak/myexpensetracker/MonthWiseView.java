package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MonthWiseView extends AppCompatActivity {
    Spinner month, year;
    ListView m_view;
    List months = new ArrayList();
    List years = new ArrayList();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_view);
        context = getApplicationContext();
        month = (Spinner) findViewById(R.id.m_month);
        year = (Spinner) findViewById(R.id.m_year);
        m_view = (ListView) findViewById(R.id.m_view);
        for (int i = 2010; i <= 2025; i++) years.add(i);
        months.add(1);
        months.add(2);
        months.add(3);
        months.add(4);
        months.add(5);
        months.add(6);
        months.add(7);
        months.add(8);
        months.add(9);
        months.add(10);
        months.add(11);
        months.add(12);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, months.toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(arrayAdapter);
        Log.d("Adaptet", "for months set");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, years.toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(arrayAdapter);
        Log.d("Adaptet", "for years set");

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Function Call", "Item Selected");
                ArrayList title = new ArrayList();
                ArrayList time = new ArrayList();
                ArrayList day = new ArrayList();
                ArrayList monthx = new ArrayList();
                ArrayList yearx = new ArrayList();
                ArrayList desc = new ArrayList();
                ArrayList type = new ArrayList();
                ArrayList amt = new ArrayList();
                Accounts obj = new Accounts(context);
                Cursor res = obj.getMonthStatement(month.getSelectedItem().toString(), year.getSelectedItem().toString());
                String date, dates = "";
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
                        monthx.add("" + datePart[1]);
                        yearx.add("" + datePart[0]);
                        desc.add(res.getString(res.getColumnIndex("t_desc")));
                        type.add(res.getString(res.getColumnIndex("t_type_c")));
                        amt.add(res.getString(res.getColumnIndex("t_amt")));
                    }
                }

                String[] tile, time1, day1, month1, year1, desc1, type1, amt1;
                tile = (String[]) title.toArray(new String[0]);
                time1 = (String[]) time.toArray(new String[0]);
                day1 = (String[]) day.toArray(new String[0]);
                month1 = (String[]) monthx.toArray(new String[0]);
                year1 = (String[]) yearx.toArray(new String[0]);
                desc1 = (String[]) desc.toArray(new String[0]);
                type1 = (String[]) type.toArray(new String[0]);
                amt1 = (String[]) amt.toArray(new String[0]);
                Log.d("Function Call", "Set Adapter");
                m_view.setAdapter(new MonthViewByMonthYear(MonthWiseView.this, tile, time1, day1, month1, year1, desc1, type1, amt1));
                Log.d("Function Call", "Set...");
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Function Call", "Item Selected");
                ArrayList title = new ArrayList();
                ArrayList time = new ArrayList();
                ArrayList day = new ArrayList();
                ArrayList monthx = new ArrayList();
                ArrayList yearx = new ArrayList();
                ArrayList desc = new ArrayList();
                ArrayList type = new ArrayList();
                ArrayList amt = new ArrayList();
                Accounts obj = new Accounts(context);
                Cursor res = obj.getMonthStatement(month.getSelectedItem().toString(), year.getSelectedItem().toString());
                String date, dates = "";
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
                        monthx.add("" + datePart[1]);
                        yearx.add("" + datePart[0]);
                        desc.add(res.getString(res.getColumnIndex("t_desc")));
                        type.add(res.getString(res.getColumnIndex("t_type_c")));
                        amt.add(res.getString(res.getColumnIndex("t_amt")));
                    }
                }

                String[] tile, time1, day1, month1, year1, desc1, type1, amt1;
                tile = (String[]) title.toArray(new String[0]);
                time1 = (String[]) time.toArray(new String[0]);
                day1 = (String[]) day.toArray(new String[0]);
                month1 = (String[]) monthx.toArray(new String[0]);
                year1 = (String[]) yearx.toArray(new String[0]);
                desc1 = (String[]) desc.toArray(new String[0]);
                type1 = (String[]) type.toArray(new String[0]);
                amt1 = (String[]) amt.toArray(new String[0]);
                Log.d("Function Call", "Set Adapter");
                m_view.setAdapter(new MonthViewByMonthYear(MonthWiseView.this, tile, time1, day1, month1, year1, desc1, type1, amt1));
                Log.d("Function Call", "Set...");
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }
}
