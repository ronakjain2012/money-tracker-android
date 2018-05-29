package com.example.ronak.myexpensetracker;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

public class expense extends AppCompatActivity {
    int mYear, mMonth, mDay, mHour, mMinute;
    Button e_date, e_time, submits;
    Spinner categs;
    RadioGroup rg1, rg2;
    RadioButton r1, r2, r3, r3a, r3b, r3c;
    EditText e_title, e_desc, e_amt;
    String type;
    Context context;
    String temp, temp1;

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        context = this;
        e_title = (EditText) findViewById(R.id.e_title);
        e_date = (Button) findViewById(R.id.e_date);
        submits = (Button) findViewById(R.id.e_submit);
        e_time = (Button) findViewById(R.id.e_time);
        e_desc = (EditText) findViewById(R.id.e_desc);
        e_amt = (EditText) findViewById(R.id.e_amt);
        categs = (Spinner) findViewById(R.id.e_spinner);
        rg1 = (RadioGroup) findViewById(R.id.e_rg1);
        rg2 = (RadioGroup) findViewById(R.id.e_rg2);
        r1 = (RadioButton) findViewById(R.id.e_cash);
        r2 = (RadioButton) findViewById(R.id.e_bank);
        r3 = (RadioButton) findViewById(R.id.e_wallet);
        r3a = (RadioButton) findViewById(R.id.e_paytm);
        r3b = (RadioButton) findViewById(R.id.e_mobikwik);
        r3c = (RadioButton) findViewById(R.id.e_airtel);

        CategoryDatabase cdb = new CategoryDatabase(this);

        String[] catList = cdb.getCategory();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, catList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categs.setAdapter(arrayAdapter);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        if ((mMonth + 1) <= 9) {
            temp = "0" + (mMonth + 1);
        } else {
            temp = (mMonth + 1) + "";
        }


        if (mDay <= 9) {
            temp1 = "0" + (mDay);
        } else {
            temp1 = (mDay) + "";
        }

        e_date.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(temp).append("-")
                .append(temp1).append(""));

        e_time.setText(new StringBuilder()
                .append(mHour).append(":")
                .append(mMinute).append(":")
                .append("00.000"));
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void openDatePicker(View v) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener pDateSetListener =
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        if ((mMonth + 1) <= 9) {
                            temp = "0" + (mMonth + 1);
                        } else {
                            temp = (mMonth + 1) + "";
                        }
                        if (mDay <= 9) {
                            temp1 = "0" + (mDay);
                        } else {
                            temp1 = (mDay) + "";
                        }
                        e_date.setText(new StringBuilder()
                                .append(mYear).append("-")
                                .append(temp).append("-")
                                .append(temp1).append(""));
                    }
                };

        DatePickerDialog dialog = new DatePickerDialog(expense.this,
                pDateSetListener, mYear, mMonth, mDay);
        dialog.show();
    }

    public void openTimePicker(View v) {
        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        e_time.setText(new StringBuilder()
                                .append(mHour).append(":")
                                .append(mMinute).append(":")
                                .append("00.000"));
                    }
                };
        TimePickerDialog dialog = new TimePickerDialog(expense.this, mTimeSetListener, mHour, mMinute, false);
        dialog.show();
    }

    public void openWallets(View v) {
        r3a.setVisibility(View.VISIBLE);
        r3a.setEnabled(true);
        r3b.setVisibility(View.VISIBLE);
        r3b.setEnabled(true);
        r3c.setVisibility(View.VISIBLE);
        r3c.setEnabled(true);
    }

    public void putTrans(View view) {
        int g1 = rg1.getCheckedRadioButtonId();
        switch (g1) {
            case R.id.e_cash:
                type = "cash";
                break;
            case R.id.e_bank:
                type = "bank";
                break;
            case R.id.e_wallet:
                int g2 = rg2.getCheckedRadioButtonId();
                switch (g2) {
                    case R.id.e_paytm:
                        type = "paytm";
                        break;
                    case R.id.e_mobikwik:
                        type = "mobikwik";
                        break;
                    case R.id.e_airtel:
                        type = "airtel";
                        break;
                }
                break;
        }
        Accounts ac = new Accounts(getApplicationContext());
        if (ac.makeTrans("cr", Float.parseFloat(e_amt.getText().toString() + ""), e_title.getText().toString(), categs.getSelectedItem().toString(), type, e_date.getText().toString() + " " + e_time.getText().toString(), e_desc.getText().toString(), Float.parseFloat(e_amt.getText().toString() + ""))) {

            new AlertDialog.Builder(context)
                    .setTitle("Success")
                    .setMessage("Expense added.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Failed")
                    .setMessage("Could not process due to error.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }
}
