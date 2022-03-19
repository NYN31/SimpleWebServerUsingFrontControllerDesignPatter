package com.dispatcher.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;

public class HomeController {
    private String INDEX = "/home/text";
    private String JSON_RETURN = "/home/json";

    public void route(String path, PrintWriter response) {
        response.println("HTTP/1.1 200 OK");
        String content = "";
        if (path.equals(INDEX)){
            response.println("Content-Type: text/html");
            response.println();
            content = getIndexHtml();
            response.println(content);
        } else if (path.equals(JSON_RETURN)){
            response.println("Content-Type: application/json");
            response.println("charset=UTF-8");
            response.println();

            User user = new User();
            user = getUser(user);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = mapper.writeValueAsString(user);
                response.println(jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            response.println("Content-Type: text/html");
            response.println();
            content = pageNotFound();
            response.println(content);
        }
    }

    public User getUser(User user) {
        user.setFirstName("Rashedul");
        user.setLastName("Islam");
        return user;
    }

    public String pageNotFound() {
        return "<html> <h1> 404 Page not found !! </h1> </html>";
    }

    public String getIndexHtml() {
        return String.format("<html> <h1> Home Page - From Home Controller </h1> </html>");
    }
}
