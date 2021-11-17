package com.example.dbex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class connection extends AsyncTask<String,Void,String>{ //DB연동 시험으로 정류장 DB연동해 놓음
    JSONArray jsonArray;
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String>numberlist= new ArrayList<>();
    ArrayList<String>namelist= new ArrayList<>();
    ArrayList<String>locationlist= new ArrayList<>();
    ArrayList<String>latilist= new ArrayList<>();
    ArrayList<String>longtilist= new ArrayList<>();
    ArrayList<String>gradelist= new ArrayList<>();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String... strings) {
        Connection con; // 멤버변수
        String errString = null;
        Statement stmt;
        ResultSet rs;
        String str;
        URL url;
        String url1 = "http://10.0.2.2/station.php"; //정류장의 모든 데이터 출력 PHP 파일
        InputStream json;
        String receiveMsg;
        json = null;
        receiveMsg = "null";
        try {
            url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //연결 설정
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str); // 출력된 모든 글자 버퍼에 입력
                }
                receiveMsg = buffer.toString(); //버퍼에 있는거 출력
                Log.i("receiveMsg : ", receiveMsg);
                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("zzzzz",e.toString());
        }
        Log.d("zzzz",receiveMsg);
        return receiveMsg;
    }

    public void showResult(){ //파싱해서 보여주는 구문
        int a=1;
        try {
            JSONObject jsonObject = new JSONObject(doInBackground()); //넘겨진 String receivemsg Jsonobject로 변경
            JSONArray jsonArray = jsonObject.getJSONArray("result"); //JsonObject JsonArray로 변경
            for(int i = 0 ; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i); //순서대로 출력
                String id = jsonObject.getString("id"); // id로 시작하는거 검색
                String number = jsonObject.getString("number");// number로 시작하는거 검색
                String name = jsonObject.getString("name");// name으로 시작하는거 검색
                String location = jsonObject.getString("location");// location 으로 시작하는거 검색
                String lati = jsonObject.getString("latitude");// latitude로 시작하는거 검색
                String longti = jsonObject.getString("longtitude");// longtitude로 시작하는거 검색
                String grade = jsonObject.getString("grade");// grade로 시작하는거 검색
                idlist.add(id);
                numberlist.add(number);
                namelist.add(name);
                locationlist.add(location);
                latilist.add(lati);
                longtilist.add(longti);
                gradelist.add(grade);
            }
        } catch (Exception e) {
            Log.d("zzzzjjjjj", e.toString());
        }
    }


}
