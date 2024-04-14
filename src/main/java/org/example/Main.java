package org.example;

import java.io.IOException;

// GET /calculate 요청 시, 계산해서 결과를 리턴 (쿼리스트링은 아래 예시와 같음)
// GET /calculate?operand1=11&operator=*&operand2=55
public class Main {
    public static void main(String[] args) throws IOException {

        /*
         * step1 ~ step3 까지 단계별로 코드를 수정하였는데,
         *  step1: main thread 에서 처리하는 방식
         *  step2: thread 방식으로 처리
         *  step3: thread pool 방식으로 처리
         * 각 방식 확인은 주석 해제해서 확인
         */

//        new CustomWebApplicationServer(8080).start();     // step1
//        new CustomWebApplicationServerStep2(8080).start();        // step2
        new CustomWebApplicationServerStep3(8080).start();      // step3
    }
}