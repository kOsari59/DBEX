package com.example.dbex;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_resault_t extends AppCompatActivity {//검색된 theme 데이터 출력 코드
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resault_t);
        ListView lv =(ListView) findViewById(R.id.listView_main_list);//리스트뷰와 엑티비티에 리스트뷰 연결
        try{
            adapter = new SimpleAdapter(
                    activity_resault_t.this, MainActivity.al, R.layout.item_list_p,//연결할 엑티비티 선택 al 동적배열에 담긴 데이터 사용
                    new String[]{"id","name", "phone" , "location" ,"grade"}, //리스트에서 특정값 집어넣기
                    new int[]{R.id.textView_list_id, R.id.textView_list_user, R.id.textView_list_station , R.id.textView_list_restaurant , R.id.textView_list_pension } //집어넣은 값 위치 지정
            );

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"리스트 뷰 오류",Toast.LENGTH_SHORT).show(); //오류확인
        }
        Log.d("반복S", String.valueOf(MainActivity.al.size())); //로그출력
        lv.setAdapter(adapter); //리스트뷰에 어뎁터 연결

    }


}