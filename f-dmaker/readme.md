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
  
### Transaction
#### ACID
  - Atomic
  - Consistency
  - Isolation
  - Durability
  
### AOP (Aspect Oriented Programming)
- 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화

```java
private final EntityManager em;

public void createDeveloper() {
    EntityTransaction transaction = em.getTransaction(); 
    try {
        transaction.begin();
        
        // Business Logic Start
        
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(2)
                .name("Olaf")
                .age(28)
                .build();

        /* A -> B 1만원 송금 */
        // A 계좌에서 1만원 줄임
        developerRepository.save(developer);
        // B 계좌에서 1만원 늘림
        developerRepository.delete(developer);
        
        // Business Logic End
        
        transaction.commit();
    } catch (Exception e) {
        transaction.rollback();
        throw e;
    }
}
```

- Business logic 을 제외한 코드는 공통적으로 활용되는 `transaction` 코드
- AOP 기반으로 코드를 작성하면 비즈니스 로직만 강조할 수 있음 - `@Transactional`
```java
@Transactional
public void createDeveloper() {
    // Business Logic Start
    
    Developer developer = Developer.builder()
            .developerLevel(DeveloperLevel.JUNIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(2)
            .name("Olaf")
            .age(28)
            .build();
    
    /* A -> B 1만원 송금 */
    // A 계좌에서 1만원 줄임
    developerRepository.save(developer);
    // B 계좌에서 1만원 늘림
    developerRepository.delete(developer);
    
    // Business Logic End
}
```

### Entity, DTO
- Entity는 테이블에 정의한 내용
- DTO는 단어 그대로 transfer하기 위해 사용하는 객체
- 되도록이면 전송 또는 반환할 때 entity를 그대로 사용하지 말자 
    - reference. DTO 내에 `fromEntity`와 같은 정적 메소드 정의해서 사용

#### Entity
```java
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```
- `@EntityListeners(AuditingEntityListener.class)`
  - 이거 안 붙이면 `@CreatedDate`, `@LastModifiedDate` 작동 안 함
- `@Enumerated(EnumType.STRING)`
  - @Enumerated와 enum class를 활용
  - type 패키지를 생성해서 관련 설정 클래스를 패키징 가능
  
  ```java
  package org.example.dmaker.type;
  
  import lombok.AllArgsConstructor;
  import lombok.Getter;
  
  @AllArgsConstructor
  @Getter
  public enum DeveloperSkillType {
  
      BACK_END("백엔드 개발자"),
      FRONT_END("프론트엔드 개발자"),
      FULL_STACK("풀스택 개발자")
      ;
  
      private final String description;
  }
  ```

#### DTO
```java
public class CreateDeveloperDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId min 3 max 50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "name min 3 max 20")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;

        public static Response fromEntity(Developer developer) {
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYears())
                    .memberId(developer.getMemberId())
                    .build();
        }
    }
}
```
- 보통 request와 response용이 필요하니 내부에 정적 클래스를 만들어서 request와 response를 활용하자
- `@NotNull`, `@Min()`, `@Max()`, `@Size()`와 같은 어노테이션으로 최소한의 data validation을 하자
- `fromEntity` 메소드를 활용해서 서비스 파일에서 entity 정보를 그대로 반환 또는 전송하지 말고 정제된 데이터를 반환하자
  - entity를 그대로 반환하게 되면 아래 문제가 발생할 수 있음
     - entity 내에 포함된 민감정보를 출력할 수도 있음
     - 잘못된 코드로 인해 entity data가 변경될 수도 있음
  - 따라서 `fromEntity`와 같은 메소드를 통해 다시 빌드해서 리턴하자
  
### Validation
#### Exception
```java
@Getter
public class DmakerException extends RuntimeException {
    private DmakerErrorCode dmakerErrorCode;
    private String detailMessage;

    public DmakerException(DmakerErrorCode errorCode) {
        super(errorCode.getMessage());
        this.dmakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DmakerException(DmakerErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage());
        this.dmakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
```
```java
@AllArgsConstructor
@Getter
public enum DmakerErrorCode {
    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    DUPLICATED_MEMBER_ID("MemberId가 중복되는 개발자가 있습니다."),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCHED("개발자 레벨과 연차가 맞지 않습니다."),

    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.");


    private final String message;
}
```
- 작업을 하다보면 여러 가지 상황에 대한 exception 처리를 해줘야 함
- 단순하게 `RuntimeException`을 활용할 수도 있지만 서비스에 따라 다양한 exception이 발생할 수 있기 때문에 별도의 클래스를 만들어 커스터마이징해서 사용하자

#### 생성 기능 중 validation 작업
```java
@Service
@RequiredArgsConstructor
public class DmakerService {
  private final DeveloperRepository developerRepository;
  
  @Transactional
  public CreateDeveloperDto.Response createDeveloper(CreateDeveloperDto.Request request) {
    validateCreateDeveloperRequest(request);

    // business logic
    Developer developer = Developer.builder()
            .developerLevel(request.getDeveloperLevel())
            .developerSkillType(request.getDeveloperSkillType())
            .experienceYears(request.getExperienceYears())
            .memberId(request.getMemberId())
            .name(request.getName())
            .age(request.getAge())
            .statusCode(StatusCode.EMPLOYED)
            .build();
    developerRepository.save(developer);
    return CreateDeveloperDto.Response.fromEntity(developer);
  }

  private void validateCreateDeveloperRequest(CreateDeveloperDto.Request request) {
    validateDeveloperLevel(
            request.getDeveloperLevel(),
            request.getExperienceYears()
    );

    developerRepository.findByMemberId(request.getMemberId())
            .ifPresent((developer -> {
              throw new DmakerException(DUPLICATED_MEMBER_ID);
            }));
  }
  
  
    
    ...
}
```
- `@Transactional` : 데이터 관련 작업할 때는 디폴트하게 사용하자
  - atomic하게 작업을 할 수 있고, 데이터 수정할 때에도 별도의 save 없이 수정하면 저장되니까
- `ifPresent()` : Optional 객체를 리턴받을 때 try ~ catch 혹은 if null로 처리하지 말고 `ifPresent`를 활용해서 예외 처리

#### 조회 기능
```java
@Service
@RequiredArgsConstructor
public class DmakerService {
  private final DeveloperRepository developerRepository;

  public List<DeveloperDto> getAllEmployedDevelopers() {
    return developerRepository.findDeveloperByStatusCodeEquals(StatusCode.EMPLOYED)
            .stream().map(DeveloperDto::fromEntity)
            .collect(Collectors.toList());
  }

  public DeveloperDetailDto getDeveloperDetail(String memberId) {
    return developerRepository.findByMemberId(memberId)
            .map(DeveloperDetailDto::fromEntity)
            .orElseThrow(() -> new DmakerException(NO_DEVELOPER));
  }
  
  ...
}
```
- stream을 map()을 통해 mapping
  - stream 작업 이후에는 collect 잊지 말기
- `orElseThrow()`를 통해 깔끔하게 예외처리하자

#### @ExceptionHandler
```java
public class DmakerController {
    
    ...

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(DmakerException.class)
  public DmakerErrorResponse handleException(
          DmakerException e,
          HttpServletRequest request
  ) {
    log.error("errorCode : {}, url : {}, message : {}",
            e.getDmakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

    return DmakerErrorResponse.builder()
            .errorCode((e.getDmakerErrorCode()))
            .errorMessage(e.getDetailMessage())
            .build();
  }
  
  ...
  
}
```
- `@ExceptionHandler`를 통해 원하는 형태의 error response를 만들 수 있음.
- `@ResponseStatus`를 활용해서 HTTP status 값을 설정할 수 있음
- `HttpServletRequest`를 활용해서 구체적인 요청을 확인할 수 있음

#### @RestControllerAdvice
- 컨트롤러 별로 `@ExceptionHandler`을 만들면 컨트롤러의 수만큼 handler를 생성해줘야 함
- 따라서 exception handling을 위한 별도의 클래스를 만들어서 글로벌 예외처리를 할 수 있음
```java
package org.example.dmaker.exception; 

@Slf4j
@RestControllerAdvice
public class DmakerExceptionHandler {

    @ExceptionHandler(DmakerException.class)
    public DmakerErrorResponse handleException(
            DmakerException e,
            HttpServletRequest request
    ) {
        log.error("errorCode : {}, url : {}, message : {}",
                e.getDmakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DmakerErrorResponse.builder()
                .errorCode((e.getDmakerErrorCode()))
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DmakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
      log.error("url : {}, message : {}",
              request.getRequestURI(), e.getMessage());
  
      return DmakerErrorResponse.builder()
              .errorCode(INVALID_REQUEST)
              .errorMessage(INVALID_REQUEST.getMessage())
              .build();
    }

    @ExceptionHandler(Exception.class)
    public DmakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ) {
      log.error("url : {}, message : {}",
              request.getRequestURI(), e.getMessage());
  
      return DmakerErrorResponse.builder()
              .errorCode(INTERNAL_SERVER_ERROR)
              .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
              .build();
    }
}
```
- 요청 자체가 잘못돼서 커스터마이징한 exception에 걸리지 않는 경우도 대비
  - `HttpRequestMethodNotSupportedException` : HTTP Method가 올바르지 않은 경우
  - `MethodArgumentNotValidException` : 설정한 Argument가 올바르지 요청되지 않은 경우
- `@ExceptionHandler(Exception.class)` : exception의 종류를 대비할 수 없는 경우
