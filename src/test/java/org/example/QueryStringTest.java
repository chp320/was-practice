package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    // operand1=11
    // 만약 쿼리스트링이 n개 있는 경우는 어떻게 할까? List<QueryString> 과 같이 쿼리스트링이 여러 개 있는 리스트 형태로 구성하면 어떨까? => 일급 컬렉션 사용!
    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1", "11");

        assertThat(queryString).isNotNull();
    }
}
