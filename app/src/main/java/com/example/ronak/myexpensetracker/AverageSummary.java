package com.example.ronak.myexpensetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AverageSummary extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_summary);
        Accounts obj = new Accounts(this);
        t1 = (TextView) findViewById(R.id.totalIncomeAvg);
        t2 = (TextView) findViewById(R.id.weekIncomeAvg);
        t3 = (TextView) findViewById(R.id.monthIncomeAvg);
        t4 = (TextView) findViewById(R.id.yearIncomeAvg);
        t5 = (TextView) findViewById(R.id.totalExpenseAvg);
        t6 = (TextView) findViewById(R.id.weekExpenseAvg);
        t7 = (TextView) findViewById(R.id.monthExpenseAvg);
        t8 = (TextView) findViewById(R.id.yearExpenseAvg);
        try {
            t1.setText((obj.getTotlaIncomeAvg() / obj.getTotlaIncomeAvgLine()) + "");
            t2.setText((obj.getWeekIncomeAvg() / obj.getWeekIncomeAvgLine()) + "");
            t3.setText((obj.getMonthIncomeAvg() / obj.getMonthIncomeAvgLine()) + "");
            t4.setText((obj.getYearIncomeAvg() / obj.getYearIncomeAvgLine()) + "");
            t5.setText((obj.getTotlaExpenseAvg() / obj.getTotlaExpenseAvgLine()) + "");
            t6.setText((obj.getWeekExpenseAvg() / obj.getWeekExpenseAvgLine()) + "");
            t7.setText((obj.getMonthExpenseAvg() / obj.getMonthExpenseAvgLine()) + "");
            t8.setText((obj.getYearExpenseAvg() / obj.getYearExpenseAvgLine()) + "");
        } catch (Exception e) {

        }
        obj.close();
    }
}
