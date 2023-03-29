package com.jaeuk.phptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveConsultationContentRequest extends StringRequest {

    /*
    [매개변수]
    Patient_HP : 환자 전화번호
    Patient : 환자명
    Patient_idx : 환자 고유 id 값
    Patient_Email : 환자 이메일
    Favorites : 관심항목
    Contents : 상담 내용
    Consultant_HP : 상담사 전화 번호 즉, 기기 전화번호
     */

    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "http://pipecoding.dothome.co.kr/saveConsultationContent.php";
    /*
    PHP 파일 설명

    위의 매개변수를 채워 상담 이력을 저장하는 파일이다.

    [클라이언트에서 호출되어야할 url 예제]
    요청 : http://pipecoding.dothome.co.kr/saveConsultationContent.php
    body : 위의 매개변수

    특이사항 :
    1. 상담사번호는 DB에 미리 저장된 번호만 사용 가능합니다. 테스트 환경이니 여러분의 번호를 넣어주세요
    2. 번호는 000-0000-0000 형태로 "-" 포함해서 작성해주셔야 합니다.
    */

    private Map<String, String> map;

    public SaveConsultationContentRequest(String Patient_HP, String Patient, String Patient_Email, String Favorites, String Contents, String Consultant_HP, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        // "" 안에 들어있는 내용은 바뀌어선 안됨
        map.put("Patient_HP", Patient_HP);
        map.put("Patient", Patient);
        map.put("Patient_Email", Patient_Email);
        map.put("Favorites", Favorites);
        map.put("Contents", Contents);
        map.put("Consultant_HP", Consultant_HP);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}