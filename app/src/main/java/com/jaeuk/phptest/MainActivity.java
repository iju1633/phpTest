package com.jaeuk.phptest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

    private EditText patient_PH, consultant_PH;
    private EditText Patient_HP, Patient, Patient_idx, UserEmail, Favorites, Contents, Consultant_HP;
    private Button getUserInfo, saveConsultationContent;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아이디 값 찾아주기
        patient_PH = findViewById(R.id.patient_PH);
        consultant_PH = findViewById(R.id.consultant_PH);

        Patient_HP = findViewById(R.id.Patient_HP);
        Patient = findViewById(R.id.Patient);
        Patient_idx = findViewById(R.id.Patient_idx);
        UserEmail = findViewById(R.id.UserEmail);
        Favorites = findViewById(R.id.Favorites);
        Contents = findViewById(R.id.Contents);
        Consultant_HP = findViewById(R.id.Consultant_HP);

        getUserInfo = findViewById(R.id.getUserInfo);
        saveConsultationContent = findViewById(R.id.saveConsultationContent);

        result = findViewById(R.id.result);
        result.setMovementMethod(new ScrollingMovementMethod());

        getUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 전화 왔을 때를 가정해서 유저 정보 조회
        getUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "환자 정보 요청", Toast.LENGTH_SHORT).show();
                // EditText에 현재 입력된 값을 가져온다.
                String patient_ph = patient_PH.getText().toString();
                String consultant_ph = consultant_PH.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {

                            result.setText(response);

//                            JSONObject jsonObject = new JSONObject(response.replaceAll("<br>", ""));
//                            int idx = jsonObject.getInt("idx");
//                            String userName = jsonObject.getString("userName");
//                            JSONArray consultingList = jsonObject.getJSONArray("consultingList");
//                            StringBuilder sb = new StringBuilder();
//                            for (int i = 0; i < consultingList.length(); i++) {
//                                JSONObject consulting = consultingList.getJSONObject(i);
//                                String patient = consulting.getString("Patient");
//                                String patientIdx = consulting.getString("Patient_idx");
//                                String patientHP = consulting.getString("Patient_HP");
//                                String jiJum = consulting.getString("JiJum");
//                                String subject = consulting.getString("Subject");
//                                String contents = consulting.getString("Contents");
//                                String favorites = consulting.getString("Favorites");
//                                String writeDate = consulting.getString("WriteDate");
//                                String regDate = consulting.getString("RegDate");
//                                sb.append("Patient : " + patient + "\n" +
//                                        "Patient_idx : " + patientIdx + "\n" +
//                                        "Patient_HP : " + patientHP + "\n" +
//                                        "JiJum : " + jiJum + "\n" +
//                                        "Subject : " + subject + "\n" +
//                                        "Contents : " + contents + "\n" +
//                                        "Favorites : " + favorites + "\n" +
//                                        "WriteDate : " + writeDate + "\n" +
//                                        "RegDate : " + regDate + "\n\n");
//                            }
//                            String consultingContents = sb.toString();
//
//                            // 화면에 결과 보여줌
//                            result.setText("idx : " + idx + "\n" +
//                                    "userName : " + userName + "\n\n" +
//                                    "Consulting List : \n" + consultingContents);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 활용하여 요청
                UserInfoRequest userInfoRequest = new UserInfoRequest(patient_ph, consultant_ph, responseListener);
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
                String patientHP = Patient_HP.getText().toString();
                String patient = Patient.getText().toString();
                String patientIdx = Patient_idx.getText().toString();
                String userEmail = UserEmail.getText().toString();
                String favorites = Favorites.getText().toString();
                String contents = Contents.getText().toString();
                String consultantPH = Consultant_HP.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 화면에 결과 보여줌
                            if (success) {
                                result.setText("요청 성공");
                            } else
                                result.setText("요청 실패");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 활용하여 요청
                SaveConsultationContentRequest saveConsultationContentRequest = new SaveConsultationContentRequest(patientHP, patient, patientIdx, userEmail, favorites, contents, consultantPH, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(saveConsultationContentRequest);
            }
        });
    }
}