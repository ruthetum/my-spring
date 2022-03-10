# Spring WebFlux REST Example
- Spring WebFlux로 REST API 만들어보기
- DB를 동기 처리하고 있어서 비동기 처리면에서 효율적이지는 않지만 Mono, Flux로 리턴해보기

---

## Init
- 기본적으로 Row 3개 생성
    ```java
    // config/init.java
    public class Init {
        ...
        @PostConstruct
        public void init() {
            initService.dbInit();
        }
        ...
    }
    ```

- 매번 값 초기화 : `spring.jpa.hibernate.ddl-auto: create` 설정

## API
|Method|URI|Description|
|---|---|---|
|GET|/members|전체 member 조회|
|GET|/members/{id}|특정 member 조회|
|POST|/members|member 생성|
|PUT|/members|member 수정|
|DELETE|/members/{id}|member 삭제|

> ### GET /members
```java
// controller/MemberController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public Flux<MemberResponse> getMembers() {
        return memberService.getMembers();
    }

    ...
}
```

```java
// service/MemberService.java
@RequestMapping("/members")
public class MemberService {

    private final MemberRepository memberRepository;

    public Flux<MemberResponse> getMembers() {
        List<MemberResponse> memberResponses = memberRepository.findAll().stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());
        return Flux.fromIterable(memberResponses);
    }

    ...
}
```

> ### GET /members/{id}
```java
// controller/MemberController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public Mono<MemberResponse> getMember(@PathVariable("id") Long id) {
        return memberService.getMember(id);
    }

    ...
}
```

```java
// service/MemberService.java
public class MemberService {

    ...

    public Mono<MemberResponse> getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException());
        return Mono.just(MemberResponse.fromEntity(member));
    }

    ...
}
```
