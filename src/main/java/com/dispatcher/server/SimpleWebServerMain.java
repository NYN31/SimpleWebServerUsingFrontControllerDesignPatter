package com.dispatcher.server;

import com.dispatcher.server.asyncRequest.NewSimpleWebServer;

public class SimpleWebServerMain {
    public static void main(String[] args) {
        NewSimpleWebServer server = new NewSimpleWebServer(9090);
        server.start();
    }
}