package org.example;

import java.io.IOException;

// GET /calculate 요청 시, 계산해서 결과를 리턴 (쿼리스트링은 아래 예시와 같음)
// GET /calculate?operand1=11&operator=*&operand2=55
public class Main {
    public static void main(String[] args) throws IOException {

        new CustomWebApplicationServer(8080).start();
    }
}