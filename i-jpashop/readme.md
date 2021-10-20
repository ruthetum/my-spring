

### H2 데이터베이스 설치
- https://www.h2database.com
- 다운로드 및 설치
    - `jdbc:h2:~/jpashop` (최소 한번)
    - `~/jpashop.mv.db` 파일 생성 확인
    - 이후 부터는 `jdbc:h2:tcp://localhost/~/jpashop` 이렇게 접속

### 쿼리 파라미터 로그 남기기
- 1. `application.yml`에 `org.hibernate.type`
- 2. 외부 라이브러리 사용
     `implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'`
     
