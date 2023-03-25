package com.jaeuk.phptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveConsultationContentRequest extends StringRequest {

    /*
    [매개변수]
    Patient_HP
    Patient
    Patient_idx
    UserEmail
    Favorites
    Contents
    Consultant_HP
     */

    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "http://pipecoding.dothome.co.kr/saveConsultationContent.php";
    private Map<String, String> map;

    public SaveConsultationContentRequest(String Patient_HP, String Patient, String Patient_idx, String UserEmail, String Favorites, String Contents, String Consultant_HP, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Patient_HP", Patient_HP);
        map.put("Patient", Patient);
        map.put("Patient_idx", Patient_idx);
        map.put("UserEmail", UserEmail);
        map.put("Favorites", Favorites);
        map.put("Contents", Contents);
        map.put("Consultant_HP", Consultant_HP);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}