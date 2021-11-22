package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class search_history extends AppCompatActivity {

    private static String TAG = "search_history";

    private static final String TAG_JSON = "result";
    private static final String TAG_ID = "ID";
    private static final String TAG_user = "user_s";
    private static final String TAG_station = "station_s";
    private static final String TAG_restaurant = "restaurant_s";
    private static final String TAG_pension = "pension_s";
    private static final String TAG_theme = "theme_s";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mListView;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mListView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://10.0.0.2/search_history.php");
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(search_history.this, "please wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result_e) {
            super.onPostExecute(result_e);

            progressDialog.dismiss();
            mTextViewResult.setText(result_e);
            Log.d(TAG, "response -" + result_e);

            if (result_e == null) {
                mTextViewResult.setText(errorString);
            } else {
                mJsonString = result_e;
                showResult();
            }

        }

        protected String doInBackground(String... params) {
            String serverURL = params[0];


            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code -" + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return null;
            }


        }
    }



    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String user = item.getString(TAG_user);
                String station = item.getString(TAG_station);
                String restaurant = item.getString(TAG_restaurant);
                String pension = item.getString(TAG_pension);
                String theme = item.getString(TAG_theme);




                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_user, user);
                hashMap.put(TAG_station, station);
                hashMap.put(TAG_restaurant, restaurant);
                hashMap.put(TAG_pension, pension);
                hashMap.put(TAG_theme, theme);



                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    search_history.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_user, TAG_station , TAG_restaurant , TAG_pension , TAG_theme},
                    new int[]{R.id.textView_list_id, R.id.textView_list_user, R.id.textView_list_station , R.id.textView_list_restaurant , R.id.textView_list_pension , R.id.textView_list_theme}
            );

            mListView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
