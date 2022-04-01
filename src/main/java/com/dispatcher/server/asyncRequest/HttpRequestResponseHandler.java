package com.dispatcher.server.asyncRequest;

import com.dispatcher.server.dispatcher.DispatcherHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpRequestResponseHandler implements Runnable{
    private final Socket client;
    private final DispatcherHandler dispatcher;

    public HttpRequestResponseHandler(Socket client, DispatcherHandler dispatcher) {
        this.client = client;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        System.out.println(Thread.currentThread().getName());
        try(
                BufferedReader request = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter response = new PrintWriter(client.getOutputStream(), true)
        ) {
            String line = request.readLine();
            int lineNo = 1;
            String path = "";
            String type = "";
            while (!line.isEmpty()) {
                //System.out.println("lineNo: " + lineNo + " ----> " + line);
                if (lineNo == 1) {
                    path = extractPathFromLine(line);
                    type = (line.contains("currency") || line.contains("home")) ?
                            (line.contains("home") ? "home" : "currency") : "";
                }
                lineNo++;
                line = request.readLine();
            }
            dispatcher.dispatch(type, path, response);
            setDelay(2);
            closeClientRequest(client, request, response);
        } catch (IOException e) {
            try {
                closeClientRequest(client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void closeClientRequest(Socket client, BufferedReader request, PrintWriter response) throws IOException {
        if (client != null) client.close();
        if (request != null) request.close();
        if (response != null) response.close();
    }

    private void closeClientRequest(Socket client) throws IOException {
        if (client != null) client.close();
    }

    private void setDelay(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String extractPathFromLine(String line) {
        if (line.startsWith("GET")) {
            String[] parts = line.trim().split(" ");
            return parts[1];
        }
        return "";
    }
}
