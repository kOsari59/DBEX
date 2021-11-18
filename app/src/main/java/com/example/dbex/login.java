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

public class login extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            et_id = findViewById(R.id.lid);
            et_pass = findViewById(R.id.lpw);
            btn_login = findViewById(R.id.loginbt);
            btn_register = findViewById(R.id.regibt);




        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        String userID = et_id.getText().toString();
                        String userPass = et_pass.getText().toString();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                    System.out.println("hongchul" + response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { // 로그인에 성공한 경우
                                        String userID = jsonObject.getString("userID");
                                        String userPass = jsonObject.getString("userPassword");
                                        String userName = jsonObject.getString("userName");

                                        Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this, loginresault.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("userPassword", userPass);
                                        intent.putExtra("userName", userName);
                                        startActivity(intent);
                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(login.this);
                        queue.add(loginRequest);
                    }

        });


    }
}