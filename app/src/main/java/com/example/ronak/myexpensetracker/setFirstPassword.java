package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class setFirstPassword extends AppCompatActivity {
    EditText e1, e2;
    Button b1;
    int a, b;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_first_password);
        context = this;
        e1 = (EditText) findViewById(R.id.pass1);
        e2 = (EditText) findViewById(R.id.pass2);
        b1 = (Button) findViewById(R.id.buttonPassSet);
    }

    boolean checkPass(String a, String b) {
        if (Integer.parseInt(a) == Integer.parseInt(b)) {
            this.a = Integer.parseInt(a);
            this.b = Integer.parseInt(b);
            return true;
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("PIN is not matching.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            return false;
        }
    }

    public void setPass(View v) {
        if (checkPass(e1.getText().toString(), e2.getText().toString())) {
            Accounts obj = new Accounts(context);
            if (obj.setPassword("9" + e1.getText().toString())) {
                new AlertDialog.Builder(this)
                        .setTitle("Success")
                        .setMessage("PIN SET - " + e1.getText().toString())
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                finish();
            }
        }
    }

}
