# FastCampus self-study
- 패스트캠퍼스 강의 - '나만의 MVC 프레임워크 만들기' 중 chapter4. 웹 애플리케이션 이해 강의 테스트 코드

## Chapter4 : 웹 애플리케이션 이해
- 계산기 웹 프로그램 구현
- step1: 사용자 요청을 메인 thread가 처리하도록 함
- step2: 사용자 요청이 들어올 때마다 thread를 새로 생성해서 사용자 요청을 처리하도록 함
- step3: thread pool을 적용해 안정적인 서비스가 가능하도록 함

### HTTP 프로토콜 이해
- 서버와 클라이언트가 웹에서 데이터를 주고 받기 위한 프로토콜(규약)
    - HTTP 통해서 어떤 종류의 데이터든지 전송 가능
- HTTP/1.1, HTTP/2 는 TCP 기반 위에서 동작
    - HTTP 기반 위에서 동작 = 3-way hand-shake 통해 연결을 맺는다는 의미
- HTTP/3 은 UDP 기반 위에서 동작
    - UDP 기반 위에서 동작 = 3-way hand-shake 연결 불필요

### HTTP 요청/응답 구조
```
클라이언드 ------request------> 서버
        <-----response------
```
- 요청(request) 메시지는 request line/header/blank line/body 로 구성
- 응답(response) 메시지는 status line/header/blank line/body 로 구성

### HTTP 특징
- 클라이언트-서버 모델
    - 요쳥/응답 받는 구조
- 무상태 프로토콜(stateless)
    - 서버가 클라이언트 상태를 유지하지 않음 (서버가 response 보낸 후 바로 close함)
    - 해결책: keep-alive 속성 사용
        - 클라이언트가 서버에 다시 요청 시, 3-way hand-shake 를 거쳐야하는 비효율성을 제거하기 위해 "일정" 기간 동안은 해당 연결을 끊지않고 데이터를 주고 받을 수 있도록 함
        - keep-alive 가 계속 유지되면 성능 하락의 요인이 되기도 함 => 웹 서버 thread 부족 현상 발생
- 비 연결성 (connectionless)
    - 서버가 클라이언트 요청에 대해 응답을 마치면 맺었던 연결을 끊어 버림
    - 해결책: 쿠키(클라이언트에 정보 저장), 세션(서버에 정보 저장), JWT

### HTTP 요청 메서드
- GET, POST, PUT, DELETE 등

### HTTP 응답 코드
- 2xx(성공), 3xx(리다이렉션), 4xx(클라이언트 에러), 5xx(서버 에러) 등

### HTTP 헤더
- Content-type, Accept, Cookie, Set-Cookie, Authorization 등

## HttpRequest 구조
- RequestLine (GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
  - HttpMethod
  - path
  - queryString
- Header
- Body

## HttpResponse 구조
``` sample
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8
Content-Length: 3
```
- StatusLine (HTTP/1.1 200 OK)
- Header
- Body