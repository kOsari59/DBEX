package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Resault extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resault);
        try{
            ListAdapter adapter = new SimpleAdapter(
                    Resault.this, MainActivity.al, R.layout.item_list_p,
                    new String[]{"id","name", "phone" , "top" , "location" ,"grade"},
                    new int[]{R.id.textView_list_id, R.id.textView_list_user, R.id.textView_list_station , R.id.textView_list_restaurant , R.id.textView_list_pension , R.id.textView_list_theme}
            );

            ListView lv =(ListView) findViewById(R.id.listView_main_list_p);
            lv.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"리스트 뷰 오류",Toast.LENGTH_SHORT).show();
        }



    }


}