# 스프링 AOP
## 핵심 기능과 부가 기능
- 모든 애플리케이션 로직은 핵심 기능과 부가 기능으로 구성
  - 주문 서비스에서 핵심 기능은 주문 로직, 부가 기능은 로그 추적, 트랜잭션 등등
  - 부가 기능은 핵심 기능을 보조하기 위함

### 부가 기능 적용 시 문제점
<img width="665" alt="" src="https://user-images.githubusercontent.com/59307414/213113611-c3466b90-260e-4c62-a91b-3421aedcb5ac.png">

- 부가 기능을 적용할 때 아주 많은 반복 필요
- 부가 기능이 여러 곳에 퍼져서 중복 코드 생성
- 부가 기능을 변경할 때 중복 때문에 많은 수정 필요
- 부가 기능의 적용 대상을 변경할 때 많은 수정 필요

> 이러한 문제로 인해 Aspect 등장

## Aspect
- 애스팩트(aspect): 부가 기능과 부가 기능을 어디에 적용할지 선택하는 기능을 합해서 하나의 모듈로 만드는 것
  - ex. `@Aspect`
- 어드바이스(부가 기능)와 포인트컷(적용 대상)을 가지고 있어서 개념상 하나의 애스펙트
- 애스펙트를 사용한 프로그래밍 방식을 관점 지향 프로그래밍 AOP(Aspect-Oriented Programming)라고 함
  - AOP는 OOP를 대체하기 위한 것이 아니라 횡단 관심사를 깔끔하게 처리하기 어려운 OOP의 부족한 부분을 보조하는 목적으로 개발

![image](https://user-images.githubusercontent.com/59307414/164704721-65da1a71-b5d7-4ddc-af89-ca285010756a.png)

- 동일한 기능이 흩어져 있으면 유지보수하는데 어려움이 존재
- 각 클래스 내에 흩어진 관심사를 묶어서 모듈화
- 애플리케이션 전체에 걸쳐 사용되는 기능을 재사용

### AspectJ 프레임워크
- AOP의 대표적인 구현으로 AspectJ 프레임워크(https://www.eclipse.org/aspectj/)
- 물론 스프링도 AOP를 지원하지만 대부분 AspectJ의 일부 문법을 차용하고, AspectJ가 제공하는 기능의 일부만 제공

## AOP 적용 방식
### AOP를 사용해서 부가 기능 로직이 실제 로직에 추가되는 방법
- 컴파일 시점
- 클래스 로딩 시점
- 런타임 시점(프록시) -> Dynamic Proxy

> 위빙(Weaving): 원본 로직에 부가 기능 로직이 추가되는 것

**컴파일 시점**
<img width="653" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213115369-7fd191b8-05b6-4f5c-8168-da8945185b4f.png">

- `.java` 코드를 컴파일러를 사용해서 `.class`를 만드는 시점에 부가 기능 로직 추가
  - AspectJ 컴파일러를 사용해야 함
- 즉 컴파일될 때 위빙
- 컴파일 시점에 부가 기능을 적용하려면 AspectJ 컴파일러가 필요하고 복잡하기 때문에 잘 사용하지 않음

**클래스 로딩 시점**
<img width="654" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213115391-e8ae2174-2579-481e-b24d-8bc028ece08b.png">

- 자바는 실행 시 `.class`파일을 JVM 내부의 클래스 로더에 보관
  - 이 때 중간에 `.class`파일을 조작해서 JVM에 저장 (java bytecode를 조작)
- `Java Instrumentation API` 활용
  - jar파일로 JVM으로 로딩되는 java bytecode를 변형하는 API
  - 모니터링 툴들이 이 방식을 많이 차용
  - ref.
    - https://blog.bespinglobal.com/post/java-instrumentation-api/
    - https://www.baeldung.com/java-instrumentation
- 로드 타임 위빙은 자바를 실행할 때 특별한 옵션(`java -javaagent`)을 통해 클래스 로더 조작기를 지정, 이 부분이 번거롭고 운영하기 어려움

**런타임 시점(프록시)**
<img width="655" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213115440-38634f2b-7a16-4c82-87b4-d95ec813891d.png">

- 런타임 시점은 컴파일이 끝나고, 클래스 로더에 이미 올라가고, 자바가 실행(main)된 다음의 시점
  - 이는 자바 언어가 제공하는 범위 내에서 부가 기능을 적용해야 한다는 것을 의미
  - 프록시, DI, 빈 후처리기 개념을 모두 활용

> 용어 리마인드
> - 스프링 컨테이너(ApplicationContext): 파라미터로 넘어온 설정 클래스 정보(@Configuration)를 사용해서 스프링 빈을 등록
> - 스프링 빈(Bean): 스프링 컨테이너에 등록된 객체
> - 객체를 외부에서 생성(설정 클래스 정보)하고, 이를 컨테이너에 등록하고, 외부에서 생성된 객체를 주입해서 사용

> 1. 객체를 스프링 컨테이너를 통해 전달
> 2. 빈 후처리기에서 AspectJ 모듈 확인, 이를 통해서 알맞는 로직이 추가된 프록시 생성
> 3. 생성된 프록시를 스프링 빈에 등록

> Proxy Bean 생성
> - Spring IoC: 기존 Bean을 대체하는 Dynamic Proxy Bean을 만들어 등록 시켜줌
> - [AbstractAutoProxyCreator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/framework/autoproxy/AbstractAutoProxyCreator.html)을 통해 원본 클래스를 감싸는 Proxy Bean을 생성
>   - `AbstractAutoProxyCreator implements BeanPostProcessor`

- 프록시를 사용하기 때문에 일부 기능에 제약
  - 프록시이기 때문에 생성자 같은 기능에는 제약이 있고, 메서드를 호출 가능
  - 다만 컴파일, 로드 타임 위빙보다 복잡하지 않음

- 프록시 패턴
  ![image](https://user-images.githubusercontent.com/59307414/164884146-dec5c2af-bd7a-4936-a763-a8485b7973e7.png)

<br>

**위빙 방법 별 정리**

- 컴파일 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다.
- 클래스 로딩 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다.
- 런타임 시점: 실제 대상 코드는 그대로 유지된다. 대신에 프록시를 통해 부가 기능이 적용된다. 따라서 항상 프록시를 통해야 부가 기능을 사용할 수 있다. 스프링 AOP는 이 방식을 사용한다.

### AOP 적용 위치
- 적용 가능 지점(조인 포인트): 생성자, 필드 값 접근, static 메서드 접근, 메서드 실행
- AspectJ를 사용해서 컴파일 시점과 클래스 로딩 시점에 적용하는 AOP는 바이트코드를 실제 조작하기 때문에 해당 기능을 모든 지점에 적용 가능
- 프록시 방식을 사용하는 스프링 AOP는 **메서드 실행 지점에만 AOP 적용 가능**
  - 프록시는 메서드 오버라이딩 개념으로 동작(생성자나 static 메서드, 필드 값 접근에 대해 적용 불가)
- 프록시 방식을 사용하는 스프링 AOP는 스프링 컨테이너가 관리할 수 있는 스프링 빈에만 AOP 적용 가능

## AOP 용어 정리
<img width="656" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213128046-6e4e73d3-f938-44e5-8937-e1ea5a2a550a.png">

### 조인 포인트(Join point)
- 어드바이스가 적용될 수 있는 위치, 메소드 실행, 생성자 호출, 필드 값 접근, static 메서드 접근 같은 프로그램 실행 중 지점
- 조인 포인트는 추상적인 개념이다. AOP를 적용할 수 있는 모든 지점이라 생각하면 된다.
- 스프링 AOP는 프록시 방식을 사용하므로 조인 포인트는 항상 **메소드 실행 지점**으로 제한된다.

### 포인트컷(Pointcut)
- 조인 포인트 중에서 **어드바이스가 적용될 위치를 선별**하는 기능
- 주로 AspectJ 표현식을 사용해서 지정
- 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능

### 타켓(Target)
- **어드바이스를 받는 객체**, 포인트컷으로 결정 

### 어드바이스(Advice)
- **부가 기능**
- 특정 조인 포인트에서 Aspect에 의해 취해지는 조치
- Around(주변), Before(전), After(후)와 같은 다양한 종류의 어드바이스가 있음

### 애스펙트(Aspect)
- **어드바이스 + 포인트컷을 모듈화** 한 것 @Aspect 를 생각하면 됨
- 여러 어드바이스와 포인트 컷이 함께 존재

### 어드바이저(Advisor)
- 하나의 어드바이스와 하나의 포인트 컷으로 구성
- 스프링 AOP에서만 사용되는 특별한 용어

### 위빙(Weaving)
- 포인트컷으로 결정한 타켓의 조인 포인트에 어드바이스를 적용하는 것
- 위빙을 통해 핵심 기능 코드에 영향을 주지 않고 부가 기능을 추가 할 수 있음
- AOP 적용을 위해 애스펙트를 객체에 연결한 상태 
  - 컴파일 타임(AspectJ compiler)
  - 로드 타임 
  - 런타임, 스프링 AOP는 런타임, 프록시 방식

### AOP 프록시
- AOP 기능을 구현하기 위해 만든 프록시 객체, 스프링에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시이다.

---

# 스프링 AOP 구현
## Dependency
- `implementation 'org.springframework.boot:spring-boot-starter-aop'`

> `@Aspect` 를 사용하려면 `@EnableAspectJAutoProxy` 를 스프링 설정에 추가해야 하지만, 스프링 부트를 사용하면 자동으로 추가된다.

## `@Aspect` 적용
- 가장 단순하게 AOP를 구현
- Bean으로 등록해야 하므로 (컴포넌트 스캔을 사용한다면) `@Componet`도 추가

```java
// order/aop/AspectV1.java
@Aspect
public class AspectV1 {

    // hello.aop.order 패키지와 하위 패키지
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }
}
```
- `@Around`의 값(`execution(* hello.aop.order..*(..))`)이 포인트컷(어드바이스 실행 위치)으로 정의
  - hello.aop.order 패키지와 하위 패키지에 적용
- `doLog()`가 어드바이스(부가 기능)

### PointCut 정의
**execution**
- ex. `@Around("execution(* hello.aop.order..*(..))")`
- execution은 기존 코드를 완전히 건드리지 않고 aspect 내에 작성된 표현식으로 기능을 수행할 수 있음
- 하지만 pointcut 조합이 어려움 
  - ex. &&, ||, !
 
**annotation**
- ex. `@Around("@annotation(PerfLogging)")`
- `@Retention`
  - RetentionPolicy.CLASS : 애노테이션 정보가 바이트 코드까지 남아 있음 (default)
  - RetentionPolicy.SOURCE : 컴파일 후에 사라짐 
  - RetentionPolicy.RUNTIME : 런타임까지 유지 (굳이 할 필요 없음)
- 애노테이션을 정의하고, 해당 애노테이션을 원하는 메소드에 추가

```java
@Aspect
@Component
public class PerfAspect {

    // @Around("execution(* com.example..*.EventService.*(..))") // execution
    @Around("@annotation(PerfLogging)")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
      return joinPoint.proceed();
    }
}

// interface
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface PerfLogging {
}

// service
@Service
public class OrderService {

  ...

  @PerfLogging
  public void orderItem(String itemId) {
    log.info("[orderService] 실행");
    orderRepository.save(itemId);
  }
}
```

## 포인트컷 분리
```java
// order/aop/AspectV2.java
@Aspect
public class AspectV2 {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }

}
```
- `@PointCut`에 표현식을 사용
- 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처라 함
- 메서드의 반환 타입은 void
- 코드 내용은 비워둠

## 어드바이스 추가
```java
// order/aop/AspectV3.java
@Aspect
public class AspectV3 {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }

    //hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }
}
```

<img width="658" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213159434-82f66be0-1524-412a-9b91-40452ecc2c8b.png">

## 포인트컷 참조
```java
// order/aop/Pointcuts.java
public class Pointcuts {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} //pointcut signature

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}

// order/aop/AspectV4Pointcut.java
@Aspect
public class AspectV4Pointcut {

  @Around("hello.aop.order.aop.Pointcuts.allOrder()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    ...
  }

  @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    ...
  }
}
```

- 포인트컷을 공용으로 사용하기 위해 별도의 외부 클래스에 모아두어도 됨
- 외부에서 호출할 때는 포인트컷의 접근 제어자를 `public` 으로 설정

## 어드바이스 순서
```java
@Aspect
@Order(1) // @Order(value = 1)
public class TxAspect { ... }

@Aspect
@Order(2) // @Order(value = 2)
public class LogAspect { ... }
```

또는 하나의 클래스에서 구현해야 하는 경우 static class로 분리해서 적용

```java
// order/aop/AspectV5Order.java
public class AspectV5Order {

  @Aspect
  @Order(2)
  public static class LogAspect {
    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }
  }

  @Aspect
  @Order(1)
  public static class TxAspect {
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }
  }
}
```
- Order는 클래스 단위로 적용
- value가 작을수록 먼저 실행
- Order를 설정하지 않을 경우 내부적으로 순서를 판단해서 동작

## 어드바이스 종류
```java
// order/aop/AspectV6Advice.java
@Aspect
public class AspectV6Advice {

  @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

    try {
      //@Before
      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      //@AfterReturning
      log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
      return result;
    } catch (Exception e) {
      //@AfterThrowing
      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
      throw e;
    } finally {
      //@After
      log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
    }
  }

  @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
  public void doBefore(JoinPoint joinPoint) {
    log.info("[before] {}", joinPoint.getSignature());
  }

  @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
  public void doReturn(JoinPoint joinPoint, Object result) {
    log.info("[return] {} return={}", joinPoint.getSignature(), result);
  }

  @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
  public void doThrowing(JoinPoint joinPoint, Exception ex) {
    log.info("[ex] {} message={}", ex);
  }

  @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
  public void doAfter(JoinPoint joinPoint) {
    log.info("[after] {}", joinPoint.getSignature());
  }
}
```

- `@Around`: 대상 메서드의 수행 전/후, 가장 강력한 어드바이스
- `@Before`: 대상 메서드의 수행 전 (조인 포인트 실행 여부)
- `@AfterReturning`: 대상 메서드의 정상적인 수행 후
- `@AfterThrowing`: 대상 메서드가 예외를 던지는 경우
- `@After`: 대상 메서드의 수행 후 (finally)

<img width="659" alt="스크린샷" src="https://user-images.githubusercontent.com/59307414/213164552-e216c83f-f0f9-431b-9a61-b6934e78672d.png">

### JoinPoint interface
- `@Around`를 제외한 어드바이스는 `JoinPoint`를 사용
- `getArgs()` : 메서드 인수를 반환
- `getThis()` : 프록시 객체를 반환
- `getTarget()` : 대상 객체를 반환
- `getSignature()` : 조언되는 메서드에 대한 설명을 반환
- `toString()` : 조언되는 방법에 대한 유용한 설명을 인쇄합니다.

### ProceedingJoinPoint interface
- `@Around`는 `ProceedingJoinPoint`를 사용
- `proceed()`: 다음 어드바이스나 타켓을 호출


## @Around 외에 다른 어드바이스가 존재하는 이유
- @Around 는 항상 joinPoint.proceed() 를 호출해야 함
- 만약 실수로 호출하지 않으면 타켓이 호출되지 않는 치명적인 버그 발생
  - @Before 는 joinPoint.proceed() 를 호출하는 고민을 하지 않아도 됨
  - @Around 가 가장 넓은 기능을 제공하는 것은 맞지만, 실수할 가능성이 있음
  - 반면 @Before , @After 같은 어드바이스는 기능은 적지만 실수할 가능성이 낮고, 코드도 단순

<br>
---
<br>

## Filter vs Interceptor vs AOP
![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9983FB455BB4E5D30C)
- Interceptor와 Filter는 Servlet 단위에서 실행
  - Filter는 Dispatcher Servlet 앞단, Interceptor는 뒷단
- AOP는 메소드 앞에 Proxy패턴의 형태로 실행
- Filter와 다르게 Interceptor는 Spring Context, Spring Bean을 활용할 수 있음
  - Filter는 스프링 컨텍스트 외부에 존재하기 때문에 Spring과 무관한 자원에 대한 처리를 담당

> Spring Security는 filter? interceptor?
> + https://mangkyu.tistory.com/173
> + https://mangkyu.tistory.com/221