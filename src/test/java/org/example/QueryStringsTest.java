package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// operand1=11&operator=*&operand2=55
public class QueryStringsTest {

    @Test
    void createTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55"); // List<QueryString> 과 같이 여러 쿼리스트링을 가지는 형태임

        assertThat(queryStrings).isNotNull();
    }
}
