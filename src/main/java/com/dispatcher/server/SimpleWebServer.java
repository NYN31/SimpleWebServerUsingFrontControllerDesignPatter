package com.dispatcher.server;

public class SimpleWebServer {
    public static void main(String[] args) {
        NewSimpleWebServer server = new NewSimpleWebServer(9090);
        server.start();
    }
}