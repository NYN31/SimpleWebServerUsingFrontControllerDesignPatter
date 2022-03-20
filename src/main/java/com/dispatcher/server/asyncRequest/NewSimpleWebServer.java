package com.dispatcher.server.asyncRequest;

import com.dispatcher.server.dispatcher.DispatcherHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NewSimpleWebServer {
    private final int port;
    private DispatcherHandler router;
    private static int clientNo;

    public NewSimpleWebServer(int port) {
        this.port = port;
        router = new DispatcherHandler();
        clientNo = 1;
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server listening 127.0.0.1:" + port);
            listenRequestResponse(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenRequestResponse(ServerSocket server) {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("Client connected");
                handleRequestResponse(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequestResponse(Socket client) {
        HttpRequestResponseHandler handler = new HttpRequestResponseHandler(client, router);
        Thread handleRequestResponse = new Thread(handler);
        handleRequestResponse.start();
    }
}
