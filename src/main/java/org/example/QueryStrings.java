package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryStrings {

    private List<QueryString> queryStrings = new ArrayList<>();     // QueryString 을 여러 개 가지는 List 형식

    /* 인자로 받아온 쿼리스트링을 parsing해야 함
     * 예시 형태) operand1=11&operator=*&operand2=55
     *          => operand1=11   operator=*   operand2=55
     */
    public QueryStrings(String queryStringLine) {
        String[] queryStringTokens = queryStringLine.split("&");

        Arrays.stream(queryStringTokens)
                .forEach(queryString -> {
                    String[] values = queryString.split("=");
                    if (values.length != 2) {
                        // 길이기 2가 아니라는 것은 key, value 조합이 아니라는 말임.
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열입니다.");
                    }

                    queryStrings.add(new QueryString(values[0], values[1]));    // key, value 로 구분된 쌍을 List에 담는다.
                });
    }

    public String getValue(String key) {
        return this.queryStrings.stream()
                .filter(queryString -> queryString.exists(key))
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }
}
