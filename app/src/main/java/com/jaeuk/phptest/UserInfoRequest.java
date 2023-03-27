package com.jaeuk.phptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserInfoRequest extends StringRequest {

    /*
    [매개변수]
    patient_HP : 환자 전화번호
    consultant_HP : 상담사 전화 번호 즉, 기기 전화번호
     */

    // 서버 URL 설정 ( PHP 파일 연동)
    final static private String URL = "http://pipecoding.dothome.co.kr/getUserInfo.php";

    /*
    PHP 파일 설명
    환자 번호(patient_PH)와 상담사 번호(consultant_PH)를 매개변수로 하여 작동하는 코드
    전화가 올 경우, 실행되어야 하는 함수이다.
    JSON 형식의 응답을 반환합니다.

    [흐름]
    1. 환자 번호로 db 조회 (환자의 번호는 클라에서 전화 수신 시 저장해놔야함)
        1) 있을 경우
            - 환자 고유의 id 값과 이름, 그리고 상담 이력을 리스트로 반환한다.
        2) 없을 경우
            - 환자 고유의 id 값과 이름(알수없음), 비어있는 상담 리스트를 반환한다
            - 환자 등록을 진행한다

    [클라이언트에서 호출되어야할 url 예제]
    요청 : http://pipecoding.dothome.co.kr/getUserInfo.php
    body : patient_PH=환자번호 , consultant_PH=상담사번호

    특이사항 :
    1. 상담사번호는 DB에 미리 저장된 번호만 사용 가능합니다. 테스트 환경이니 여러분의 번호를 넣어주세요
    2. 번호는 000-0000-0000 형태로 "-" 포함해서 작성해주셔야 합니다.
    */

    private Map<String, String> map;

    public UserInfoRequest(String patient_HP, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        // "" 안에 들어있는 내용은 바뀌어선 안됨
        map.put("patient_HP", patient_HP);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}