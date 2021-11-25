package com.example.dbex;

import android.os.AsyncTask;
import android.util.Log;

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

public class connection_p extends AsyncTask<String,Void,String>{ //팬션 테이블에 모든 값 출력
    JSONArray jsonArray;
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String>numberlist= new ArrayList<>();
    ArrayList<String>namelist= new ArrayList<>();
    ArrayList<String>locationlist= new ArrayList<>();
    ArrayList<Double>latilist= new ArrayList<>();
    ArrayList<Double>longtilist= new ArrayList<>();
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
        String url1 = "http://10.0.2.2/pension.php"; //레스토랑 모든 데이터 출력 PHP 파일
        InputStream json;
        String receiveMsg;
        json = null;
        receiveMsg = "null";
        try {
            url = new URL(url1); //새로운 url 설정
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
                String number = jsonObject.getString("phone");// number로 시작하는거 검색
                String name = jsonObject.getString("name");// name으로 시작하는거 검색
                String location = jsonObject.getString("location");// location 으로 시작하는거 검색
                String lati = jsonObject.getString("latitude");// latitude로 시작하는거 검색
                String longti = jsonObject.getString("longitude");// longtitude로 시작하는거 검색
                String grade = jsonObject.getString("grade");// grade로 시작하는거 검색
                idlist.add(id);//아이디값 저장
                numberlist.add(number); //번호 저장
                namelist.add(name); //이름 저장
                locationlist.add(location); //위치 저장
                latilist.add(Double.valueOf(lati)); //위도 저장
                longtilist.add(Double.valueOf(longti)); //경도 저장
                gradelist.add(grade); //점수 별점 저장
            }
        } catch (Exception e) {
            Log.d("zzzzjjjjj", e.toString());
        }
    }


}
