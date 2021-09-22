# Dmaker

## 목적
- 스프링을 통한 기본적인 CRUD 기능
- validation(Data, Business), 예외처리, 트랜잭션 활용

## 개요
- 개발자 키우기

## 기능
### 생성 (개발자 생성하기)
- POST 메소드를 통해 개발자 생성
- validation 처리

### 조회 (개발자 목록, 특정 개발자 상세 내용 확인)
- GET 메소드를 통해 개발자 정보 확인
- DTO 개념 및 역할 공부

### 수정 (개발자 정보 수정)
- PUT 메소드를 통해 개발자 정보 수정

### 삭제 (개발자 삭제)
- DELETE 메소드를 통해 개발자의 정보를 삭제
- 트랜잭션 활용

## 학습
### Lombok
- 반복적으로 타이핑해야되는 boilerplate 코드들을 간편하게 생성해주는 라이브러리
- `compileOnly 'org.projectlombok:lombok'`
  
- Annotation
  - @Setter, @Getter
  - @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor
  - @Data
    - Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value 포함
  - @ToString
    - 필요한 상황에서만 사용, 개인정보를 담는 경우 에러 메시지에 개인 정보가 담길 수도 있음
  - @Builder
    - 각각 set해줄 필요없이 atomic하게 생성 가능
    - Ex.
      ```java
        PersonDto personDto = PersonDto.builder()
                                .name("dong")
                                .age(25)
                                .createdAt(LocalDateTime.now())
                                .build();
      ```
  - @Slf4j
    - 로그 생성
  - @UtilityClass
    - 시간 변환이나 간단한 처리에 이용되는 static 메소드들을 담는 용, 
    - Ex.
      ```java
        @UtilityClass
        public class DevUtils {
           public static void printNow() {
                System.out.print(LocalDateTime.now());
           }
        }
      ```
### HTTP
 - Hypertext를 전송하는데 활용하는 프로토콜

#### HTTP Request 메시지 스펙
- 첫 줄: 요청라인 - HTTP 메소드(GET, POST 등)
- 두 번째줄부터 줄바꿈 나오기 전까지: Header(User-Agent, Accept 등)
- 헤더에서 줄바꿈 이후: Request Body
    ```java
      POST /movie HTTP/1.1
      Content-Type: application/json
      Accept: application/json
   
      {
        "movieLevel": "ALL",
        "category": "action",
        "title": "Spider Man",
        "releasedAt": "2019"
      }
    ```

#### HTTP Response 메시지 스펙
- 첫 줄: 상태라인 (200, 404, 500 등)
- 두 번째줄부터 줄바꿈 나오기 전까지: Header
- 헤더에서 줄바꿈 이후: Request Body
  ```java
    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 11, Sep 2021 13:00:00 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive
  
    {
      "movieLevel": "ALL",
      "category": "action",
      "title": "Spider Man",
      "releasedAt": "2019"
    }
  ```