<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/atdd-subway-admin">
</p>

<br>

# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/atdd-subway-admin/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/atdd-subway-admin/blob/master/LICENSE.md) licensed.

## 1단계 - 지하철 노선 관리
#### 기능 요구사항
- [X] 지하철 노선 관련 기능의 인수 테스트를 작성하기
    - [X] 지하철 노선 생성 요청
    - [X] 지하철 노선 생성됨
    - [X] 지하철 노선 생성 실패됨
    - [X] 지하철 노선 목록 조회 요청
    - [X] 지하철 노선 목록 응답됨
    - [X] 지하철 노선 등록되어 있음
    - [X] 지하철 노선 목록 포함됨
    - [X] 지하철 노선 조회 요청
    - [X] 지하철 노선 응답됨
    - [X] 지하철 노선 수정 요청
    - [X] 지하철 노선 수정됨
    - [X] 지하철 노선 제거 요청
    - [X] 지하철 노선 삭제됨
    - [X] 없는 지하철 노선 조회 에러 처리
    - [X] 없는 지하철 노선 업데이트 에러 처리
- [X] 지하철 노선 관련 기능 구현하기
    - [X] 지하철 노선 목록 조회
    - [X] 지하철 노선 조회
    - [X] 지하철 노선 수정
    - [X] 지하철 노선 제거
    - [X] 없는 지하철 노선 조회 에러 처리
    - [X] 없는 지하철 노선 업데이트 에러 처리
- [X] 공통 에러처리
- [X] 트랜잭션 확인
- [X] 인수 테스트 리팩터링
    - [X] id 가져오기

#### 코드 리뷰사항
- [X] `@transactional` 어노테이션 변경
- [X] 객체지향 생활체조 원칙 규칙 4: 한 줄에 점을 하나만 찍는다.
- [X] 한개의 매개변수의 경우 from 둘 이상의 매개변수를 받아서 반환하는 경우 of
- [X] 테스트 메서드명 한글로 작성
- [X] 공통으로 사용할 수 있는 여지가 있는 테스트용 private 메서드를 외부 클래스로 분리

## 2단계 - 인수 테스트 리팩터링
#### 기능 요구사항
- [X] 구간 도메인 추가
- [X] 노선 객체에서 구간관리
- [X] 역 인수테스트 코드 분리
- [X] 노선 생성 요청 시 종점역(상행, 하행) 정보를 요청 파라미터에 함께 추가하기
- [X] 구간 저장 기능 추가
- [X] 노선 조회 시 응답 결과에 역 목록 추가
- [X] 상행역 부터 하행역 순으로 정렬되어야 함
- [X] 구간 리스트 포장
- [X] 구간 도메인 조건 추가

#### 코드 리뷰사항
- [X] 빈 주입되는 객체 `final` 사용
- [X] `throw`가 있는 메서드를 감싸서 중복되는 `throw` 제거
- [X] `@Transactional(readOnly = true)`를 클래스 레벨에 선언해주고 `@Transactional`을 실제 데이터 쓰기 작업이 있는 메서드에 선언
- [X] 기본 생성자의 노출 범위 확인
- [X] `Collections.unmodifiableList`를 활용
- [X] `from`(정적 팩토리 메서드)를 통해 객체 생성을 유도하는 곳에서 실제 생성자는 감추기
- [X] 변수명 너무 축약하지 말고 명확하게 사용
- [X] 테스트코드 변수명 한글 사용
- [X] utils 클래스명 변경
- [ ] `@DisplayName` 추가

## 3단계 - 구간 추가 기능
#### 기능 요구사항
- [ ] 역 사이에 새로운 역을 등록할 경우
    - [ ] 새로운 길이를 뺀 나머지를 새롭게 추가된 역과의 길이로 설정
    - [ ] 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없음
- [ ] 새로운 역을 상행 종점으로 등록할 경우
- [ ] 새로운 역을 하행 종점으로 등록할 경우
- [ ] 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없음
- [ ] 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음
- [X] 구간추가 기능 추가
- [ ] 구간에서 역 정보 상행->하행순으로 1개만 나오도록 수정

     
## 참고 링크
- [생성자 대신 정적 팩터리 메서드를 고려하라](https://ssoco.tistory.com/61)
- [@Transactional(readOnly=true) 성능 향상 이유](https://willseungh0.tistory.com/75)
- [방어적 복사와 Unmodifiable Collection](https://tecoble.techcourse.co.kr/post/2021-04-26-defensive-copy-vs-unmodifiable/)