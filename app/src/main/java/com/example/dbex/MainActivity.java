package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String st="NULLL";
    public static ArrayList<HashMap<String, String>> al;
    TextView cv;
    TextView tv;
    double longi;
    double latit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        al =new ArrayList<>();
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); //네트워크 접속을 위한 처리
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
        Button pension,restrant,theme,bt_station,search_history;



        restrant = (Button)findViewById(R.id.restrant);
        bt_station = (Button) findViewById(R.id.bt_station);
        search_history = (Button) findViewById(R.id.search_history);
        pension = (Button) findViewById(R.id.pension);
        theme = (Button) findViewById(R.id.theme);


            search_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(login.login_state==true){
                        Intent intent = new Intent(getApplicationContext(), com.example.dbex.search_history.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        pension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                int i = 0;
                connection_p ct = new connection_p(); //연결 준비
                ct.showResult(); //연결 후 결과 저장
                if (login.login_state == true) {
                    ArrayList<String> idd = new ArrayList<>();
                    hubu hu = new hubu();

                    for (i = 0; i < ct.idlist.size(); i++) {
                        if (30 > hu.hubua(ct.latilist.get(i), ct.longtilist.get(i), latit, longi)) {
                            idd.add(ct.idlist.get(i));
                        }
                    }
                    Toast.makeText(getApplicationContext(), "검색된 결과는" + idd.size() + "개 입니다", Toast.LENGTH_SHORT).show();
                    for (i = 0; i < idd.size(); i++) {
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
                                        String location = jsonObject.getString("location");
                                        String latitude = jsonObject.getString("latitude");
                                        String longitude = jsonObject.getString("longitude");
                                        String grade = jsonObject.getString("grade");

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", ID);
                                        hashMap.put("name", name);
                                        hashMap.put("phone", phone);
                                        hashMap.put("location", location);
                                        hashMap.put("latitude", latitude);
                                        hashMap.put("longitude", longitude);
                                        hashMap.put("grade", grade);


                                        al.add(hashMap);
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "검색 실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "검색 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Rest_p search = new Rest_p(idd.get(i), responseListener_r);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(search);
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, activity_resault_p.class); //화면 전환
                            startActivity(intent);
                        }
                    }, 1000); //딜레이 타임 조절
                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                }

            }

        });

        restrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                int i = 0;

                connection ct = new connection(); //연결 준비
                ct.showResult(); //연결 후 결과 저장
                if (login.login_state == true) {
                    ArrayList<String> idd = new ArrayList<>();
                    hubu hu = new hubu();

                    for (i = 0; i < ct.idlist.size(); i++) {
                        if (30 > hu.hubua(ct.latilist.get(i), ct.longtilist.get(i), latit, longi)) {
                            idd.add(ct.idlist.get(i));
                        }
                    }
                    Toast.makeText(getApplicationContext(), "검색된 결과는" + idd.size() + "개 입니다", Toast.LENGTH_SHORT).show();
                    for (i = 0; i < idd.size(); i++) {
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

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", ID);
                                        hashMap.put("name", name);
                                        hashMap.put("phone", phone);
                                        hashMap.put("top", top);
                                        hashMap.put("location", location);
                                        hashMap.put("latitude", latitude);
                                        hashMap.put("longitude", longitude);
                                        hashMap.put("grade", grade);


                                        al.add(hashMap);
                                        Log.d("반복", al.get(0).toString());
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "검색 실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "검색 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Rest r_search = new Rest(idd.get(i), responseListener_r);
                        RequestQueue r_queue = Volley.newRequestQueue(MainActivity.this);
                        r_queue.add(r_search);
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, Resault.class); //화면 전환
                            startActivity(intent);
                        }
                    }, 1000); //딜레이 타임 조절

                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                }

            }

        });

        bt_station.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (login.login_state == true) {
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
                                        String name = jsonObject.getString("name");
                                        String latitude = jsonObject.getString("latitude");
                                        String longitude = jsonObject.getString("longitude");
                                        longi = Double.parseDouble(longitude);
                                        latit = Double.parseDouble(latitude);
                                        Toast.makeText(getApplicationContext(), "검색할 정류장은 " + name, Toast.LENGTH_SHORT).show();
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "설정 실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        station_searchr_Request s_search = new station_searchr_Request(station, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(s_search);
                    }else{
                        Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                    }
                }

            });



        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                int i = 0;
                connection_t ct = new connection_t(); //연결 준비
                ct.showResult(); //연결 후 결과 저장
                if (login.login_state == true) {
                    ArrayList<String> idd = new ArrayList<>();
                    hubu hu = new hubu();

                    for (i = 0; i < ct.idlist.size(); i++) {
                        if (30 > hu.hubua(ct.latilist.get(i), ct.longtilist.get(i), latit, longi)) {
                            idd.add(ct.idlist.get(i));
                        }
                    }
                    Toast.makeText(getApplicationContext(), "검색된 결과는" + idd.size() + "개 입니다", Toast.LENGTH_SHORT).show();
                    for (i = 0; i < idd.size(); i++) {
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
                                        String location = jsonObject.getString("location");
                                        String latitude = jsonObject.getString("latitude");
                                        String longitude = jsonObject.getString("longitude");
                                        String grade = jsonObject.getString("grade");

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", ID);
                                        hashMap.put("name", name);
                                        hashMap.put("phone", phone);
                                        hashMap.put("location", location);
                                        hashMap.put("latitude", latitude);
                                        hashMap.put("longitude", longitude);
                                        hashMap.put("grade", grade);


                                        al.add(hashMap);
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "검색 실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "검색 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Rest_t search = new Rest_t(idd.get(i), responseListener_r);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(search);
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, activity_resault_p.class); //화면 전환
                            startActivity(intent);
                        }
                    }, 1000); //딜레이 타임 조절
                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

}