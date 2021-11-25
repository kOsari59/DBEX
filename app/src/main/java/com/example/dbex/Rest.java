package com.example.dbex;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Rest extends StringRequest { //레스토랑 검색 할때 던질 부분 만들기
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://10.0.2.2/reshearch.php";
    private Map<String, String> map;

    public Rest(String id,  Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("ID", id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
