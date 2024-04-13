package org.example.calculate;

public class DivisionOperator implements NewArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "/".equals(operator);
    }

    @Override
    public int calculate(PositiveNumber operand1, PositiveNumber operand2) {
        // PositiveNumber 가 무조건 양수임을 보장하기 때문에 아래 검증 로직은 불필요!
//        if (operand2.toInt() == 0) {
//            throw new IllegalArgumentException("0으로는 나눌 수 없습니다.");
//        }
        return operand1.toInt() / operand2.toInt();
    }
}
