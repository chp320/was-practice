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

<hr>

## CGI 와 서블릿
### CGI (Common Gateway Interface)
- 웹 서버와 어플리케이션 사이에 데이터를 주고 받는 규약
- CGI 규칙에 따라 만들어진 프로그램을 CGI 프로그램이라고 함
- CGI 프로그램 종류로는 ``컴파일 방식``(C, C++, Java 등)과 ``인터프리터 방식``(Perl, PHP, Python 등)이 있음

#### 인터프리터 방식 CGI
```
웹 서버 <-> Script 엔진 <-> Script 파일
```
- 웹 서버는 Script 엔진을 실행, Script 엔진은 Script 파일을 해석
- 웹 서버와 Script 엔진 사이에 ``CGI 규칙``을 통해서 통신
#### 서블릿과 서블릿 컨테이너
```
웹 서버 <-> Servlet Container <-> Servlet 파일
```
- 웹 서버와 Servlet Container 사이는 ``CGI 규칙``에 따라 통신(데이터 주고 받음)
- Container?? '라이프 사이클'을 관리한다고 하면 ``container``라는 표현을 씀 → Servlet Container: Servlet의 라이프 사이클을 관리

### Servlet (Server + Applet 의 합성어)
- 자바에서 웹 애플리케이션을 만드는 기술
- 자바에서 동적인 웹 페이지를 구현하기 위힌 표준
![img.png](img.png)

### Servlet Container
- 서블릿의 생성부터 소멸까지의 라이프 사이클을 관리하는 역할
- 서클릿 컨테이너는 웹 서버와 소켓을 만들고 통신하는 과정을 대신 처리함. 개발자는 비즈니스 로직에만 집중! (앞서 미니 톰캣 구현한 과정을 대신 처리함)
- 서블릿 객체를 싱글톤으로 관리 (인스턴스 하나만 생성해서 공유하는 방식)
  - 상태를 유지(stateful)하게 설계하면 안됨 → Counter 코드 참고
    - 멀티 스레드 환경에서 하나의 객체(자원)을 공유하게 되면, race condition 이 발생하게 됨 => 동기화 처리로 해결 가능
    - race condition?? 여러 프로세스 혹은 스레드가 동시에 "하나의 자원"에 접근하기 위해 경쟁하는 상태
  - thread safety 하지 않음

### WAS vs 서블릿 컨테이너
- WAS 는 서블릿 컨테이너를 포함하는 개념 (거의 동일)
- WAS 는 매 요청마다 thread pool에서 기존 thread 를 사용
- WAS 의 주요 튜닝 포인트는 max thread 수
- 대표적인 WAS 는 톰캣이 있음

<hr>

## Servlet 상속 구조
![img_1.png](img_1.png)

### Servlet 인터페이스
- 서블릿 컨테이너가 서블릿 인터페이스에 있는 메소드들을 호출
- 서블릿 생명주기와 관련된 메서드 ★
  - init() : 서블릿 컨테이너가 서블릿 생성 후 '초기화' 작업 수행을 위해 호출
  - service() : 클라이언트 요청이 들어올 때마다 서블릿 컨테이너가 호출
  - destroy() : 서블릿 컨테이너가 종료될 때 호출
- 서블릿 기타 메서드
  - getServletConfig() : 서블릿 초기 설정 정보를 담고 있는 객체 반환 => 서블릿 이름, 초기 변수값 등 get
  - getServletInfo() : 서블릿 저작자/버전/저작권 등과 같은 서블릿에 대한 정보 get

#### Spring MVC Flow
![img_2.png](img_2.png)
- Spring의 핵심 "Dispatcher Servlet"

