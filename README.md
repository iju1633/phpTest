# phpTest

### 구현되어 있는 기능
- 환자 정보 불러오기
  - 환자의 전화번호 000-0000-0000을 매개변수로 받습니다.
  - 환자의 이름과 상담 이력이 반환됩니다.
    - 상담 이력의 경우, 리스트로 반환됩니다.
  1) 있을 경우
      - 이름, 그리고 상담 이력을 리스트로 반환한다.
  2) 없을 경우
      - 이름(알수없음), 비어있는 상담 이력 리스트를 반환한다.
- 상담 이력 저장
  - 환자 전화번호, 환자명, 환자 이메일, 관심 항목, 상담 내용, 상담사 번호를 매개변수로 받습니다.
  - 상담 이력이 저장됩니다.
  
### 미션
  - 코틀린으로 migration 하기.
  - 상담사(= 앱 사용자)의 기기 번호를 추출하여 변수 혹은 sharedPreference로 저장하고 있다가 상담 이력 저장 기능 호출 시 매개변수로 넣어야 함.
  - json 형식으로 반환되는 데이터를 가공하여 화면 구성하기.
  - 환자의 번호를 변수 혹은 sharedPreference로 저장하고 있다가 환자 정보 불러오기 기능 호출 시 매개변수로 넣어야 함.
  
### 주의사항
  - 상담 이력 저장 기능 호출 시 매개변수로 들어갈 상담사 전화번호의 경우, 여러분의 번호를 넣어주시면 됩니다 !
  - MainActivity의 onResponse() 부분을 가공하여 화면에 담을 것.
    - 단, getString() 메서드 안의 String 값은 수정해선 안됩니다.
  - SaveConsultationContentRequest.java, UserInfoRequest.java 의 코드는 수정할 일이 없습니다.
  - DB는 수정하시면 안됩니다.
  - 메인 화면의 textView는 스크롤이 가능합니다.
  - 앱을 실행시켜 요청을 해보고 어떤 json 데이터가 반환되는지 직접 확인해볼 것.
  
### DB 확인 방법

- http://pipecoding.dothome.co.kr/myadmin/index.php?route=/ 에 접속합니다.  
- PM에게 전달받은 password를 입력합니다
<img width="1429" alt="스크린샷 2023-03-29 오후 10 57 40" src="https://user-images.githubusercontent.com/43805087/228561775-cf649df1-8107-4e23-b407-2b2ee61a0898.png">
- pipecoding 스키마를 클릭합니다.  
<img width="1425" alt="로그인 성공 화면" src="https://user-images.githubusercontent.com/43805087/228558404-71044be3-d279-4329-af21-7b6ff76c7592.png">
- 테이블을 눌러보며 데이터를 확인합니다.  
<img width="721" alt="테이블 확인" src="https://user-images.githubusercontent.com/43805087/228558473-2e79eb3a-74ae-4216-b2db-7e8c1573d395.png">

### DB 테이블
- pipecoding 스키마 안에 있는 테이블 3개를 클릭하며 정보가 추가되는 것을 확인합니다.  
  - CM_Member 테이블 : 상담사 테이블  
  - CM_Patient 테이블 : 환자 테이블  
  - CM_Patient_Consulting 테이블 : 환자 상담 이력 테이블
