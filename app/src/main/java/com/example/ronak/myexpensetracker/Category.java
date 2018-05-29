package com.example.ronak.myexpensetracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;

public class Category extends AppCompatActivity {
    ListView l1;
    EditText e1;
    Button b1;
    String[] values;
    CategoryDatabase cdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        b1 = (Button) findViewById(R.id.c_button);
        e1 = (EditText) findViewById(R.id.c_editText);
        l1 = (ListView) findViewById(R.id.c_list);
        cdb = new CategoryDatabase(this);
        values = cdb.getCategory();
        Arrays.sort(values);
        l1.setAdapter(new CategoryAde(this, values));

    }

    public void addCategory(View v) {
        if (e1.getText().length() > 3) {
            CategoryDatabase cDB = new CategoryDatabase(this);
            if (cDB.setCategory(e1.getText().toString())) {
                new AlertDialog.Builder(this)
                        .setTitle("Success")
                        .setMessage(e1.getText().toString() + " is Added to Category List.")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }

            values = cdb.getCategory();
            Arrays.sort(values);
            l1.setAdapter(new CategoryAde(this, values));
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Failed")
                    .setMessage("Category name should be of Minimum Length 3.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
        }
    }
}
