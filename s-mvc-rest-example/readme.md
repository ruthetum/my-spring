# Spring MVC
- Spring MVC 기반으로 이것저것 해보기
- REST API - Spring Data JPA
- 문서화 - Swagger 적용

---

## Swagger
- 서버로 요청되는 API 리스트를 HTML 화면으로 문서화하여 테스트할 수 있는 라이브러리

### Dependency
```groovy
// gradle
implementation 'io.springfox:springfox-swagger2:2.9.2'
implementation 'io.springfox:springfox-swagger-ui:2.9.2'
```

### Caution
- Spring boot 2.6버전 이후에 spring.mvc.pathmatch.matching-strategy 값이 ant_apth_matcher에서 path_pattern_parser로 변경되면서 몇몇 라이브러리(swagger포함)에 오류가 발생
- yml 파일에서 path 추가 설정 필요

    ```yml
    spring:
      mvc:
        pathmatch:
          matching-strategy: ant_path_matcher
    ```
  
---