package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

import static org.example.App.*;


public class MyHandler implements HttpHandler {

    String all() {
        String allJSON = arrayAsJSON(sampleDishes);
        return allJSON;
    }

    @Override
    public void handle(HttpExchange exchange) {
        InputStream is = exchange.getRequestBody();
        URI uri = exchange.getRequestURI();


        String params="";

        String method = exchange.getRequestMethod();

        if(method.equals("POST")) {
            params = read(is);
        } else if(method.equals("GET")) {
            params = uri.getQuery();
        }

        Gson g = new Gson();
        System.out.println(method + ": " + params);
        params = "{" + params + "}";

        Command cmd = null;
        cmd = g.fromJson(params, Command.class);
        System.out.println("getting cmd");
        System.out.println(cmd.getCmd());
        String result = executeCmd(cmd, exchange);
        System.out.println("The result " + result);


        parseJSON(exchange, params);

    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader( new InputStreamReader(is) );
        System.out.println("\n");
        String received = "";
        while (true) {
            String s = "";
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }

    String executeCmd(Command cmd, HttpExchange exchange) {
        if (cmd == null) {
            return new ErrorAnswer("Something went wrong...", false).asJSON();
        }

        if (cmd.getCmd().equals("all")) {
            return all();
        }

        if(cmd.getCmd().equals("most_caloric")) {
            return mostCaloric(sampleDishes);
        }

        if(cmd.getCmd().equals("all_vegan")) {
            return onlyVegans(sampleDishes);
        }

        return new ErrorAnswer("Unknown Command", false).asJSON();

        }

    void sendBack(HttpExchange exchange, String response) {
        try {
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void parseJSON(HttpExchange exchange, String params ) {
        Gson g = new Gson();
        Command cmd = null;
        cmd = g.fromJson(params, Command.class);

        String result = executeCmd(cmd, exchange);
        System.out.println("The result " + result);

        String response = result;
        sendBack(exchange, response);
    }
}
