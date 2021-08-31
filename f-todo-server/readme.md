# Todo server

## 목적
- 스프링 기본 환경 설정 복습
- 레이어 별 기본 설계 복습

## 개요
- to do list 구현을 통한 간단한 CRUD 기능 학습

## 기능
|번호|설명|
|---|---|
|1|todo 리스트 목록에 아이템을 추가|
|2|todo 리스트 목록 중 특정 아이템을 조회|
|3|todo 리스트 전체 목록을 조회|
|4|todo 리스트 목록 중 특정 아이템을 수정|
|5|todo 리스트 목록 중 특정 아이템을 삭제|
|6|todo 리스트 전체 목록을 삭제|

## 학습
### Gradle
- 빌드 및 의존성 관리 도구

#### plugins
- 미리 구성해 놓는 task들의 그룹
- 특정 빌드 과정에 필요한 기본 정보를 포함하여 목적에 맞게 사용 가능
```
plugins {
    id 'org.springframework.boot' version '2.5.2'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}
```

#### repositories
- 저장소 설정
    - mavenLocal() : maven 로컬 캐시 저장소
    - mavenCentral() : maven 중앙 저장소
    - maven { url "http://repo.company.com/maven" } : maven 원격 저장소

```
repositories {
    mavenCentral()
}
```

#### dependencies
- 의존성 관리
- implementation : 프로젝트 컴파일 과정에서 필요한 라이브러리
    - 수정 시 사용하는 모듈까지만 재빌드
    - 종속된 모듈의 하위 dependency를 패키지에 포함 X
- api : 수정 시 연관된 모든 모듈을 재빌드
    - 종속된 하위 모듈 모두를 패키지에 포함
- compileOnly : Gradle이 컴파일 클래스 경로에만 종속성을 추가
- providedCompile : 컴파일 시에는 필요하지만 배포 시에는 제외될 dependency
- runtimeOnly : Gradle이 런타임 시에 사용하도록 빌드 출력에만 종속성을 추가
- providedRuntime : 런타임 시에는 필요하지만 배포 시에는 제외될 dependency
- testImplementation : 테스트 시 필요한 라이브러리
- annotationProcessor : 주석 프로세서인 라이브러리에 종속성을 추가하려면 반드시 annotationProcessor구성을 사용하여 주석 프로세서 클래스 경로에 추가
    - 컴파일 클래스 경로를 주석 프로세서 클래스 경로와 분리하여 빌드 성능을 향상

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-rest'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    runtimeOnly 'com.h2database:h2'

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
}
```

### 간이 테스트
- https://todobackend.com/specs/index.html
  - root 주소 입력해서 테스트 가능
  
- https://todobackend.com/client/index.html?{root_주소}
  - todolist 기능 확인 가능