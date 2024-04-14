package org.example;

import org.example.calculate.Calculator;
import org.example.calculate.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServerStep2 {
    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServerStep2.class);

    public CustomWebApplicationServerStep2(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 서버 소켓 생성 => 해당하는 포트로 서버 기동
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServerStep2] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServerStep2] waiting for client.");

            // 서버 소켓이 클라이언트를 기다리게 함 -> 클라이언트가 들어오면 해당 소켓이 만들어져서 null이 아니게 됨
            while ((clientSocket = serverSocket.accept()) != null) {
                // 클라이언트가 연결되었음
                logger.info("[CustomWebApplicationServerStep2] client connected!");

                // 클라이언트가 연결될 때마다 thread 를 생성함 => 그런데 요청이 있을 때마다 thread 를 생성한다면, 성능 이슈가 있을텐데.. (cpu/mem 사용률 증가, ..)
                // ==> 요청 시 마다 thread 를 생성하는 것이 아니라, 미리 생성해둔 thread 를 사용하는 thread pool 방식으로 개선!!
                new Thread(new ClientRequestHandler(clientSocket)).start();
            }
        }
    }
}
