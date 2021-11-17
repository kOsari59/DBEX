package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String st="NULLL";
    connection ct;

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); //네트워크 접속을 위한 처리
        }

        txt = (TextView)findViewById(R.id.textView2);
        txt.setText(st);
        
        ct = new connection(); //연결 준비
        ct.showResult(); //연결 후 결과 저장

        txt.setText(ct.namelist.get(0)); // 결과출력




    }


}