package com.jaeuk.phptest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText patient_PH;
    private EditText Patient_HP, Patient, Patient_idx, UserEmail, Favorites, Contents, Consultant_HP;
    private Button getUserInfo, saveConsultationContent;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아이디 값 찾아주기
        patient_PH = findViewById(R.id.patient_PH);

        Patient_HP = findViewById(R.id.Patient_HP);
        Patient = findViewById(R.id.Patient);
        UserEmail = findViewById(R.id.UserEmail);
        Favorites = findViewById(R.id.Favorites);
        Contents = findViewById(R.id.Contents);
        Consultant_HP = findViewById(R.id.Consultant_HP);

        getUserInfo = findViewById(R.id.getUserInfo);
        saveConsultationContent = findViewById(R.id.saveConsultationContent);

        result = findViewById(R.id.result);
        result.setMovementMethod(new ScrollingMovementMethod());

        // 전화 왔을 때를 가정해서 유저 정보 조회
        getUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "환자 정보 요청", Toast.LENGTH_SHORT).show();
                // EditText에 현재 입력된 값을 가져온다.
                String patient_ph = patient_PH.getText().toString(); // TODO: 전화 수신 시 sharedPreference 혹은 변수로 저장하고 있다가 기입할 것

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response.replaceAll("<br>", ""));

                            // "" 안의 name에 해당하는 부분은 바꿔선 안됨
                            // TODO: 이 부분을 가공하여 상담 이력 리스트(recyclerView에 보여주면 됨)
                            String userName = jsonObject.getString("userName");
                            JSONArray consultingList = jsonObject.getJSONArray("consultingList");
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < consultingList.length(); i++) {
                                JSONObject consulting = consultingList.getJSONObject(i);
                                String patient = consulting.getString("patient");
                                String patientIdx = consulting.getString("patient_idx");
                                String patientHP = consulting.getString("patient_hp");
                                String jiJum = consulting.getString("jijum");
                                String subject = consulting.getString("subject");
                                String contents = consulting.getString("contents");
                                String favorites = consulting.getString("favorites");
                                String writeDate = consulting.getString("writeDate");
                                String regDate = consulting.getString("regDate");
                                sb.append("Patient : " + patient + "\n" +
                                        "Patient_idx : " + patientIdx + "\n" +
                                        "Patient_HP : " + patientHP + "\n" +
                                        "JiJum : " + jiJum + "\n" +
                                        "Subject : " + subject + "\n" +
                                        "Contents : " + contents + "\n" +
                                        "Favorites : " + favorites + "\n" +
                                        "WriteDate : " + writeDate + "\n" +
                                        "RegDate : " + regDate + "\n\n");
                            }
                            String consultingContents = sb.toString();

                            // 화면에 결과 보여줌
                            result.setText("userName : " + userName + "\n\n" +
                                           "Consulting List : \n" + consultingContents);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 활용하여 요청
                UserInfoRequest userInfoRequest = new UserInfoRequest(patient_ph, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(userInfoRequest);
            }
        });

        // 상담 이력 저장
        saveConsultationContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "상담 이력 저장 요청", Toast.LENGTH_SHORT).show();
                result.setText("요청 들어옴");

                // EditText에 현재 입력된 값을 가져온다.
                String patientHP = Patient_HP.getText().toString(); // TODO: 전화 수신 시 sharedPreference 혹은 변수로 저장하고 있다가 기입할 것
                String patient = Patient.getText().toString();
                String userEmail = UserEmail.getText().toString();
                String favorites = Favorites.getText().toString();
                String contents = Contents.getText().toString();
                String consultantPH = Consultant_HP.getText().toString(); // TODO: 안드로이드 기기의 번호를 추출해서 기입할 것

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", "onResponse: " + response);

                            JSONObject jsonObject = new JSONObject(response.replaceAll("<br>", ""));
                            boolean success = jsonObject.getBoolean("success");

                            // 화면에 결과 보여줌 -> db에 담긴 내용은 myadmin에서 확인할 것
                            if (success) {
                                result.setText("요청 성공"); // result textView에 보일 내용
                            } else
                                result.setText("요청 실패");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 활용하여 요청
                SaveConsultationContentRequest saveConsultationContentRequest = new SaveConsultationContentRequest(patientHP, patient, userEmail, favorites, contents, consultantPH, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(saveConsultationContentRequest);
            }
        });
    }
}