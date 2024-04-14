package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServerStep3 {
    private final int port;

    // thread pool 적용 기능 구현 (simple)
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServerStep3.class);

    public CustomWebApplicationServerStep3(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 서버 소켓 생성 => 해당하는 포트로 서버 기동
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServerStep3] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServerStep3] waiting for client.");

            // 서버 소켓이 클라이언트를 기다리게 함 -> 클라이언트가 들어오면 해당 소켓이 만들어져서 null이 아니게 됨
            while ((clientSocket = serverSocket.accept()) != null) {
                // 클라이언트가 연결되었음
                logger.info("[CustomWebApplicationServerStep3] client connected!");

                // ============ step2: 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다. ============
                // 클라이언트가 연결될 때마다 thread 를 생성함 => 그런데 요청이 있을 때마다 thread 를 생성한다면, 성능 이슈가 있을텐데.. (cpu/mem 사용률 증가, ..)
                // ==> 요청 시 마다 thread 를 생성하는 것이 아니라, 미리 생성해둔 thread 를 사용하는 thread pool 방식으로 개선!!
                // ==============================================================================================
                // ============ step3: Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다. ============
                // 위의 step2 문제점 해결을 위해 thread pool을 위의 ExecutorService 를 사용해서 적용
                // step2 의 경우, Thread-6 와 같이 새로 thread 가 생성되면서 번호를 sequence 하게 부여 되었는데
                // thread pool 을 적용한 step3 에서는 pool-1-thread-3 와 같이 pool 에서 꺼낸 thread-3 의미로 확인 가능
                // 만약, 기 생성한 thread pool (여기서는 10개) 을 다 사용했다면 다시 1번부터 반복함
                /*
                    22:37:40.437 [pool-1-thread-10] INFO org.example.ClientRequestHandler - [ClientRequestHandler] new client pool-1-thread-10 started.
                    22:37:42.671 [main] INFO org.example.CustomWebApplicationServerStep3 - [CustomWebApplicationServerStep3] client connected!
                    22:37:42.672 [pool-1-thread-1] INFO org.example.ClientRequestHandler - [ClientRequestHandler] new client pool-1-thread-1 started.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        }
    }
}
