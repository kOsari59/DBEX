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

public class connection extends AsyncTask<String,Void,String>{
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
        String url1 = "http://10.0.2.2/station.php";
        InputStream json;
        String receiveMsg;
        json = null;
        receiveMsg = "null";
        try {
            url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
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

    public void showResult(){
        int a=1;
        try {
            JSONObject jsonObject = new JSONObject(doInBackground());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i = 0 ; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String number = jsonObject.getString("number");
                String name = jsonObject.getString("name");
                String location = jsonObject.getString("location");
                String lati = jsonObject.getString("latitude");
                String longti = jsonObject.getString("longtitude");
                String grade = jsonObject.getString("grade");
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
