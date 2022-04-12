# Spring Batch로 호갱노노 배치 시스템 만들기

## 서비스 로직
1. 아파트 실거래가 공공 API 조회
    - https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15058747

2. 실거래가 정보 수집

3. 데이터베이스에 데이터 저장 & 갱신
   
4. 사용자에게 실거래가 알림 전송 
   
## 좋은 배치 프로그램 만들기
- 재사용 가능하다.
- 재시작 가능하다.
   - 언제 돌려도 똑같은 결과가 발생해야 한다.
   
## Job 설계
### 1. 동 코드 마이그레이션

<details>
<summary>Deatil</summary>
<div markdown="1">

#### 정의
> (데이터 생성 용으로) 법정동 파일을 DB 테이블에 저장한다.

#### 배치 주기
> 최초, 데이터가 수정되었을 시

#### 데이터 저장
> 법정동 파일을 DB 테이블에 저장한다.

---

</div>
</details>

### 2. 실거래가 수집 배치 설계

<details>
<summary>Deatil</summary>
<div markdown="1">

#### 정의
> 매일 실거래가 정보를 가져와 데이터베이스에 저장한다.

#### 배치 주기
> 매일 새벽 1시(트래픽이 적은 시기)

#### Reader
> 법정동 '구' 코드 불러오기

#### Processor
> '구' 마다 현재 월에 대한 API 호출

#### Writer
> 새롭게 생성된 실거래가 정보만 데이터 베이스에 upsert

---

</div>
</details>
   
### 3. 실거래가 알림 배치

<details>
<summary>Deatil</summary>
<div markdown="1">

#### 정의
> 유저가 관심 설정한 구에 대해 실거래가 정보를 알린다

#### 배치 주기
> 매일 오전 8시(유저가 알림을 받아야 할 시기)

#### Reader
> 유저 관심 테이블 & 아파트 거래 테이블 조회하며 알림 대상 추출

#### Processor
> 데이터 -> 전송용 데이터로 변환

#### Writer
> 전송 인터페이스 구현

---

</div>
</details>

## Job 개발
### 1. 동 코드 마이그레이션

<details>
<summary>Deatil</summary>
<div markdown="1">

#### 법정동코드 자료 분석
- <a href="src/main/resources/data/LAWD_CODE.txt"> 법정동코드 전체 자료 텍스트 파일</a>
    - https://www.code.go.kr/index.do 다운로드 가능

#### TODO
- [x] 법정동 - lawd 엔티티, 래포지터리 생성
- [x] 법정동 관련 비즈니스 로직 서비스 구현
- [x] 잡 - 스텝 생성
    - [x] Reader : FlatFileReader - FieldSetMapper 사용
    - [x] Processor : -
    - [x] Writer : upsert 구현
  
---

</div>
</details>

### 2. 실거래가 수집 배치 설계

<details>
<summary>Deatil</summary>
<div markdown="1">


---

</div>
</details>

### 3. 실거래가 알림 배치

<details>
<summary>Deatil</summary>
<div markdown="1">

---

</div>
</details>
