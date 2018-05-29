package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Gate_Validation extends AppCompatActivity {
    EditText password;
    TextView forgetPass;
    Button k0, k1, k2, k3, k4, k5, k6, k7, k8, k9, clear;
    ImageButton clearLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gate_validation);
        GateCheck obj = new GateCheck(this);
        obj.initiDB();
        obj.close();
        password = (EditText) findViewById(R.id.pass);
        clearLast = (ImageButton) findViewById(R.id.k11);
        clear = (Button) findViewById(R.id.k10);
        forgetPass = (TextView) findViewById(R.id.k12);
        k0 = (Button) findViewById(R.id.k0);
        k1 = (Button) findViewById(R.id.k1);
        k2 = (Button) findViewById(R.id.k2);
        k3 = (Button) findViewById(R.id.k3);
        k4 = (Button) findViewById(R.id.k4);
        k5 = (Button) findViewById(R.id.k5);
        k6 = (Button) findViewById(R.id.k6);
        k7 = (Button) findViewById(R.id.k7);
        k8 = (Button) findViewById(R.id.k8);
        k9 = (Button) findViewById(R.id.k9);
        clearLast.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (password.length() > 0) {
                    password.setText("");
                }
                return false;
            }
        });

        if (isFirstPass()) {
            Intent i = new Intent(this, setFirstPassword.class);
            this.startActivity(i);
        }
    }

    boolean isFirstPass() {
        SharedPreferences preferences = this.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        boolean userFirstPass = preferences.getBoolean("firstPassSet", false);
        if (userFirstPass == true) {
            return false;
        } else {
            return true;
        }
    }

    public void keyFunction(View v) {
        int findButton = v.getId();
        String pass = password.getText().toString();
        switch (findButton) {
            case R.id.k0:
                pass += "0";
                break;
            case R.id.k1:
                pass += "1";
                break;
            case R.id.k2:
                pass += "2";
                break;
            case R.id.k3:
                pass += "3";
                break;
            case R.id.k4:
                pass += "4";
                break;
            case R.id.k5:
                pass += "5";
                break;
            case R.id.k6:
                pass += "6";
                break;
            case R.id.k7:
                pass += "7";
                break;
            case R.id.k8:
                pass += "8";
                break;
            case R.id.k9:
                pass += "9";
                break;
            case R.id.k10:
                pass = "";
                break;
            case R.id.k11:
                try {
                    pass = pass.substring(0, pass.length() - 1);
                } catch (Exception ex) {
                    pass = "";
                }
                break;
        }
        password.setText(pass);
        GateCheck obj = new GateCheck(this);
        if (obj.checkUser("9" + pass)) {
            obj.close();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}



