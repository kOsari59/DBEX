package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
    TextView cv;
    double longi;
    double latit;
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

        try{
            ct = new connection(); //연결 준비
            ct.showResult(); //연결 후 결과 저장
            txt.setText(ct.namelist.get(0)); // 결과출력
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }


        Button loginbt = (Button) findViewById(R.id.Login);

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });

        cv = (TextView)findViewById(R.id.station);
        Button pension,restaurant,theme,bt_station,bt_search,search_history;

        pension = (Button)findViewById(R.id.pension);
        bt_station = (Button) findViewById(R.id.bt_station);
        search_history = (Button) findViewById(R.id.search_history);

        search_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.dbex.search_history.class);
                startActivity(intent);
            }
        });


        bt_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = cv.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");
                                longi = Double.parseDouble(longitude);
                                latit = Double.parseDouble(latitude);
                                Toast.makeText(getApplicationContext(),"설정 성공",Toast.LENGTH_SHORT).show();
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"설정 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                station_searchr_Request s_search = new station_searchr_Request(station , responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(s_search);
            }
        });

        pension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> idd = new ArrayList<>();
                hubu hu = new hubu();
                    for(int i = 0; i<ct.idlist.size();i++){
                        if(30 < hu.hubua(ct.latilist.get(i),ct.longtilist.get(i),latit,longi)){
                            idd.add(ct.idlist.get(i));
                        }
                    }
                    Toast.makeText(getApplicationContext(),Integer.toString(ct.idlist.size()),Toast.LENGTH_SHORT).show();
                    for(int i =0 ; i<idd.size();i++){
                        Response.Listener<String> responseListener_r = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                    System.out.println("hongchul" + response);
                                    JSONObject jsonObject = new JSONObject(response);

                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { // 로그인에 성공한 경우

                                        String ID = jsonObject.getString("id");
                                        String name = jsonObject.getString("name");
                                        String phone = jsonObject.getString("phone");
                                        String top = jsonObject.getString("top");
                                        String location = jsonObject.getString("location");
                                        String latitude = jsonObject.getString("latitude");
                                        String longitude = jsonObject.getString("longitude");
                                        String grade = jsonObject.getString("grade");

                                        txt.setText(ID+"\n"+name+"\n"+phone+"\n"+top+"\n"+location+"\n"+latitude+"\n"+longitude+"\n"+grade+"\n");
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(),"검색 실패",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Rest r_search = new Rest(idd.get(i), responseListener_r);
                        RequestQueue r_queue = Volley.newRequestQueue(MainActivity.this);
                        r_queue.add(r_search);
                    }


            }

        });



    }


}