package org.example.counter;

// Runnable 을 구현한 구현쳄
public class Counter implements Runnable {
    // 서블릿 객체가 상태를 유지하게 설계하면 안되는 내용 확인을 위해 상태 관리하는 변수 추가
    private  int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getValue() {
        return count;
    }

    @Override
    public void run() {
        this.increment();
        System.out.println("Value for Thread after increment " + Thread.currentThread().getName() + " " + this.getValue()); // 1
        this.decrement();
        System.out.println("Value for Thread after decrement " + Thread.currentThread().getName() + " " + this.getValue()); // 0
    }
}