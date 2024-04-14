package org.example;

import org.junit.jupiter.api.Test;

public class checkThreadTest {

    @Test
    void threadTest() {
        Thread thread = new MyThread();

        // 별도 thread 로 처리하기 위해서 start() 메서드 호출. Thread 클래스의 start() 메서드를 보면 synchronized 로 되어 있는데 한 번 이상 호출하면 exception을 발생 시킨다.
        // 참고) https://mangkyu.tistory.com/258
        thread.start();
//        thread.start();
//        thread.start();
        System.out.println("Hello: " + Thread.currentThread().getName());
    }
}