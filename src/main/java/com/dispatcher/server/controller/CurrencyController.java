package com.dispatcher.server.controller;

import java.io.PrintWriter;

public class CurrencyController {
    private String WELCOME = "/currency/welcome";

    public void route(String path, PrintWriter response) {
        response.println("HTTP/1.1 200 OK");
        response.println("Content-Type: text/html");
        response.println();

        String content = pageNotFound();
        if (path.equals(WELCOME)){
            content = getWelcomeHtml();
        }

        response.println(content);
    }

    public String pageNotFound() {
        return "<html> <h1> 404 Page not found !! </h1> </html>";
    }

    public String getWelcomeHtml() {
        return "<html> <h1> Welcome User From Currency Controller </h1> </html>";
    }
}
