package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class loginresault extends AppCompatActivity {
    private TextView tv_id, tv_pass ,tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginresault);


        tv_id = findViewById(R.id.tid);
        tv_pass = findViewById(R.id.tpw);
        tv_name = findViewById(R.id.tname);


        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPassword");
        String userName = intent.getStringExtra("userName");

        tv_id.setText(userID);
        tv_pass.setText(userPass);
        tv_name.setText(userName);
    }
}