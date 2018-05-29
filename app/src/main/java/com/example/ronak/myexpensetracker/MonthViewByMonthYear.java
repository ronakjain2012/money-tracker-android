package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MonthViewByMonthYear extends BaseAdapter {
    private static LayoutInflater inflater = null;
    String[] title, time, day, month, year, desc, type, amt;
    Context context;

    MonthViewByMonthYear(MonthWiseView inActivity, String[] tile, String[] time, String[] day, String[] month, String[] year, String[] desc, String[] type, String[] amt) {
        context = inActivity;
        this.title = tile;
        this.time = time;
        this.day = day;

        this.month = month;
        this.year = year;
        this.desc = desc;
        this.type = type;
        this.amt = amt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int postion, View v, ViewGroup parent) {
        ViewHolder h = new ViewHolder();
        View row;
        row = inflater.inflate(R.layout.statement_list_ad, null);
        h.t1 = (TextView) row.findViewById(R.id.st_title);
        h.t2 = (TextView) row.findViewById(R.id.st_time);
        h.t3 = (TextView) row.findViewById(R.id.st_date);
        h.t4 = (TextView) row.findViewById(R.id.st_month);
        h.t5 = (TextView) row.findViewById(R.id.st_year);
        h.t6 = (TextView) row.findViewById(R.id.st_desc);
        h.t7 = (TextView) row.findViewById(R.id.st_type);
        h.t8 = (TextView) row.findViewById(R.id.st_amt);
        h.t3.setText(day[postion]);
        try {
            String parts = month[postion];
            String month = null;
            switch (parts) {
                case "1":
                    month = "January";
                    break;
                case "2":
                    month = "February";
                    break;
                case "3":
                    month = "March";
                    break;
                case "4":
                    month = "April";
                    break;
                case "5":
                    month = "May";
                    break;
                case "6":
                    month = "June";
                    break;
                case "7":
                    month = "July";
                    break;
                case "8":
                    month = "August";
                    break;
                case "9":
                    month = "September";
                    break;
                case "10":
                    month = "October";
                    break;
                case "11":
                    month = "November";
                    break;
                case "12":
                    month = "December";
                    break;
            }
            h.t4.setText(month);
            h.t5.setText(year[postion]);
        } catch (Exception e) {
            h.t3.setText("-");
            h.t4.setText("-");
            h.t5.setText("-");
        }
        h.t1.setText(title[postion]);
        h.t2.setText("Time - " + time[postion]);


        h.t6.setText("Description - " + desc[postion]);
        h.t7.setText(type[postion]);
        if (type[postion].equalsIgnoreCase("income")) {
            h.t8.setTextColor(Color.rgb(61, 179, 71));
        } else {
            h.t8.setTextColor(Color.rgb(217, 84, 23));
        }
        h.t8.setText(amt[postion]);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return row;
    }

    class ViewHolder {
        TextView t1, t2, t3, t4, t5, t6, t7, t8;
    }
}
