package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Resault extends AppCompatActivity {
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resault);
        ListView lv =(ListView) findViewById(R.id.listView_main_list);
        try{
            adapter = new SimpleAdapter(
                    Resault.this, MainActivity.al, R.layout.item_list,
                    new String[]{"id","name", "phone" , "top" , "location" ,"grade"},
                    new int[]{R.id.textView_list_id, R.id.textView_list_user, R.id.textView_list_station , R.id.textView_list_restaurant , R.id.textView_list_pension , R.id.textView_list_theme}
            );

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"리스트 뷰 오류",Toast.LENGTH_SHORT).show();
        }
        Log.d("반복S", String.valueOf(MainActivity.al.size()));
        lv.setAdapter(adapter);

    }


}