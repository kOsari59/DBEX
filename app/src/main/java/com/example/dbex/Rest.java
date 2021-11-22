package com.example.dbex;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Rest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://10.0.2.2/Rest.php";
    private Map<String, String> map;

    public Rest(String longtitude, String latitude, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("long", longtitude);
        map.put("lat", latitude);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
