package org.example;

import java.util.Objects;

public class RequestLine {
    private final String method;    // get
    private final String urlPath;   // /calculate
    private String queryString;     // operand1=11&operator=*&operand2=55

    public RequestLine(String method, String urlPath, String queryString) {
        this.method = method;
        this.urlPath = urlPath;
        this.queryString = queryString;
    }

    /**
     * GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
     */
    public RequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        this.method = tokens[0];
        System.out.println(this.method);    // GET
        System.out.println(tokens[1]);      // /calculate?operand1=11&operator=*&operand2=55
        String[] urlPathTokens = tokens[1].split("\\?");
        this.urlPath = urlPathTokens[0];
        System.out.println(urlPath);        // /calculate

        // 앞서 ? 를 기준으로 쪼개면
        // /calculate ? operand1=11&operator=*&operand2=55
        // 이렇게 2개가 나온다. 이 경우 앞(/calculate)은 urlPath, 뒤(operand1=11&operator=*&operand2=55)는 쿼리스트링이 됨
        if (urlPathTokens.length == 2) {
            this.queryString = urlPathTokens[1];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryString);
    }
}
