package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {
    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 서버 소켓 생성 => 해당하는 포트로 서버 기동
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            // 서버 소켓이 클라이언트를 기다리게 함 -> 클라이언트가 들어오면 해당 소켓이 만들어져서 null이 아니게 됨
            while ((clientSocket = serverSocket.accept()) != null) {
                // 클라이언트가 연결되었음
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * step1 - 사용자 요청을 메인 thread가 처리하도록 한다.
                 */

                try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));  // 라인-바이-라인으로 읽어 들이기 위함
                    DataOutputStream dos = new DataOutputStream(out);

                    HttpRequest httpRequest = new HttpRequest(br);

                    // GET /calculate?operand1=11&operator=*&operand2=55
                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                        // GET 메서드이고 /calculate 로 들어오는 경우에만 쿼리스트링을 가져올꺼임
                        QueryStrings queryStrings = httpRequest.getQueryStrings();
                    }
                }
            }
        }
    }
}
