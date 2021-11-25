package com.example.dbex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class login extends AppCompatActivity { //로그인 부분
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    public static boolean login_state = false; //로그인 상태를 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            et_id = findViewById(R.id.lid); 
            et_pass = findViewById(R.id.lpw); 
            btn_login = findViewById(R.id.loginbt); 
            btn_register = findViewById(R.id.regibt); 




        btn_register.setOnClickListener(new View.OnClickListener() { // 가입버튼 눌렀을때
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, RegisterActivity.class); //가입인텐트 지정
                startActivity(intent); //인텐트 실행
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        String userID = et_id.getText().toString(); //id저장
                        String userPass = et_pass.getText().toString(); // pw 저장
                
                        Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                            @Override
                            public void onResponse(String response) { 
                                try {
                                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                    System.out.println("hongchul" + response); 
                                    JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                                    boolean success = jsonObject.getBoolean("success"); // 그값중 success가 참이면 실행
                                    if (success) { // 로그인에 성공한 경우
                                        String userID = jsonObject.getString("userID"); // 그 값중 userID 검색
                                        String userPass = jsonObject.getString("userPassword"); // 그 값중 userPassword 검색
                                        String userName = jsonObject.getString("userName");// 그 값중 userName 검색

                                        Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this, loginresault.class); //로그인 결과 인텐트 지정
                                        intent.putExtra("userID", userID); //찾은 값 넘겨주기
                                        intent.putExtra("userPassword", userPass);//찾은 값 넘겨주기
                                        intent.putExtra("userName", userName);//찾은 값 넘겨주기
                                        startActivity(intent); //인텐트 실행

                                        login_state = true;
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener); //던져줄값 형식 맞춰서 만들어주기
                        RequestQueue queue = Volley.newRequestQueue(login.this); //던져줄값 던질 큐
                        queue.add(loginRequest);// 큐로 던지기
                    }

        });


    }
}