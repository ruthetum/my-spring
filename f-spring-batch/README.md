# Spring Batch
## Overview
### 배치 프로그램
- 정해진 시간에 일괄적으로 작업을 처리하는 프로그램 (대체로 대용량 데이터를 처리)
- 서비스를 운영하는 관점에서 주기적으로 작업을 처리하기 위해 배치 프로그램 사용

#### 필요한 상황
1. 필요한 데이터를 모아서 처리해야할 때
    - ex. 월별 거래 명세서 생성
2. 일부러 지연시켜 처리할 때
    - ex. 주문한 상품을 바로 배송 처리하지 않고, 일정 시간 뒤 처리
3. 자원을 효율적으로 활용해야할 때
    - ex. 트래픽이 적은 시간 대에 서버 리소스를 활용

#### 데이터 처리 배치 프로그램
1. 각 서비스의 데이터를 데이터 웨어하우스에 저장할 때 = ETL(Extract Transform Load)
2. 아마존에서 연관 상품을 추천하는 데이터 모델을 만들 때
3. 유저 리텐션, 엑티브 상태 등 마케팅에 참고할 데이터 지표를 집계할 때
    - 유저 리텐션 : 시간이 지날수록 얼마나 많은 유저가 제품으로 다시 돌아오는지를 측정한 것

#### 서비스 배치 프로그램
1. 메세지, 이메일, 푸시 등을 발송할 때
2. 데이터를 마이그레이션할 때
3. 실패한 트랜잭션을 재처리할 때
4. 쿠폰, 포인트 등이 만료되었을 때 소진시키는 처리를 할 때
5. 월말 또는 월초에 특징 데이터를 생성할 때 (ex. 월별 거래 명세서)

## Spring Batch 
### 기본 용어

![spring batch 도메인 언어](https://user-images.githubusercontent.com/59307414/153305364-3af076aa-ca0d-4922-869c-f278be2d2c86.png)

- JoLauncher : Job을 실행시키는 컴포넌트
- Job : 배치작업
- JobRepository : Job 실행과 Job, Step을 저장
- Step : 배치 작업의 단계
- ItemReader, ItemProcesser, ItemWriter : 데이터를 읽고 처리하고 쓰는 구성

### 아키텍처

![아키텍처](https://user-images.githubusercontent.com/59307414/153305443-eb35c56c-d277-454e-850b-a3175c2f4f25.png)

- Application Layer
    - 사용자(=우리)의 코드와 구성
    - 비즈니스, 서비스 로직
    - Core, Infrastructure를 이용해 배치의 기능을 생성

- Core Layer
    - 배치 작업을 시작하고 제어하는데 필수적인 클래스
    - Job, Step, JobLauncher를 포함

- Infrastructure Layer
    - 외부와 상호작용
    - ItemReader, ItemProcesser, ItemWriter를 포함

### Job

![job](https://user-images.githubusercontent.com/59307414/153305485-b878e66b-3a69-49a6-b1cb-22c666c83eb6.png)

- 전체 배치 프로세스를 캡슐화한 도메인
- Step의 순서를 정의
- JobParameters를 받음

- Ex. 
    ```java
    @Bean
    public Job footballJob() {
        return this.jobBuilderFactory.get("footballJob")
                            .start(playerLoad())            // step의 이름
                            .next(gameload())               // step의 이름
                            .next(playerSummarization())    // step의 이름
                            .build();
    }
    ```

### Step

![step](https://user-images.githubusercontent.com/59307414/153305530-0caf1493-8111-4931-ad41-724520f66cd5.png)

- 작업 처리의 단위
- Chunk 기반 스텝, Tasklet 스탭 2가지로 나뉨
    - Chunk 기반 스텝을 많이 사용
    - Tasklet 스탭은 하나의 트랜잭션 내에서 작동하고, 단순한 처리를 할 때 사용

> Chunk 기반 스텝
> ![chuck](https://user-images.githubusercontent.com/59307414/153305687-0c7a3769-c505-4651-b9ad-a904099fa8c0.png)
> - chunk 기반으로 하나의 트랜잭션에서 데이터를 처리
> - commitInterval만큼 데이터를 읽고 트랜잭션 경계 내에서 chunkSize만큼 write 진행
>    - chunkSize : 한 트랙잭션에서 쓸 아이템의 갯수
>    - commitInterval : reader가 한 번에 읽을 아이템의 갯수
>    - chunkSize >= commitInterval 하지만 보통 같게 맞춰서 사용하는 것이 좋음

- Ex. Chunk 기반
    ```java
    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return this.jobBuilderFactory.get("sampleJob")
                .repository(jobRepository)
                    start(sampleStep)
                    .build();
    }

    @Bean
    publuc Step sampleStep(PlatformTransactionManager transactionManager) {
        return this.stepBuilderFactory.get("sampleStep")
                .transactionManager(transactionManager)
                .<String, String>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }
    ```

- Ex. TaskletStep
```java
@Bean
public Step sampleTaskletStep() {
    return this.stepBuilderFactory.get("sampleTaskletStep")
                .tasklet(myTasklet())                       
                .build();
}
```
- Tasklet 구현체를 설정. 내부에 단순한 읽기, 쓰기, 처리 로직을 모두 넣음
- RepeatStatus(반복상태)를 설정 (RepeatStatus.FINISHED)


### Reference
- Spring batch docs : https://docs.spring.io/spring-batch/docs/current/reference/html/