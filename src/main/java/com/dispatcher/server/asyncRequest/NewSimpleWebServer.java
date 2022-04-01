package com.dispatcher.server.asyncRequest;

import com.dispatcher.server.dispatcher.DispatcherHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewSimpleWebServer {
    private final int port;
    private DispatcherHandler router;
    private static int clientNo;
    private ExecutorService executorService;

    public NewSimpleWebServer(int port) {
        this.port = port;
        router = new DispatcherHandler();
        clientNo = 0;
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server listening 127.0.0.1:" + port);
            executorService = Executors.newFixedThreadPool(5);
            System.out.println("Initialize total 5 fixed thread pool using executor framework");
            listenRequestResponse(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenRequestResponse(ServerSocket server) {
        clientNo++;
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("Client " + clientNo + " connected");
                handleRequestResponse(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequestResponse(Socket client) {
        HttpRequestResponseHandler handler = new HttpRequestResponseHandler(client, router);
        executorService.execute(handler);
        setDelay(4);
    }

    private void setDelay(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
