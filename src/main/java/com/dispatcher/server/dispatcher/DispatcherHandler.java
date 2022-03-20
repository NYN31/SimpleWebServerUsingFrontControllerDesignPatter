package com.dispatcher.server.dispatcher;

import com.dispatcher.server.controller.CurrencyController;
import com.dispatcher.server.controller.HomeController;

import java.io.PrintWriter;

public class DispatcherHandler {
    private CurrencyController currencyController;
    private HomeController homeController;

    public DispatcherHandler() {
        currencyController = new CurrencyController();
        homeController = new HomeController();
    }

    public void dispatch(String type, String path, PrintWriter response) {
        if (type.equalsIgnoreCase("CURRENCY")) {
            currencyController.route(path, response);
        } else if (type.equalsIgnoreCase("HOME")) {
            homeController.route(path, response);
        } else {
            response.println("HTTP/1.1 200 OK");
            response.println("Content-Type: text/html");
            response.println();

            String content = pageNotFound();

            response.println(content);
        }
    }

    public String pageNotFound() {
        return "<html> <h1> 404 Page not found !! </h1> </html>";
    }
}