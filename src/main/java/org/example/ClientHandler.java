package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static org.example.App.*;
import static org.example.App.sampleDishes;


public class ClientHandler {


    private Socket clientSocket;
    private PrintWriter out = null; // allocate to write answer to client.
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    String all() {
        String allJSON = arrayAsJSONTCP(sampleDishes);
        return allJSON;
    }
    boolean manage(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Accepted");



        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            Thread.sleep(500);
            out.println("Welcome user");
            out.println("Please insert a command...");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// answer:
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println("Ricevuto: "+ s);
                Gson g = new Gson();
                Command cmd = null;
                String result;
                try {
                    cmd = g.fromJson(s, Command.class);
                } catch (Exception e) {

                }

                if (cmd!=null) {
                    result = executeCmd(cmd);
                    out.println(result);
                }
                else {
                    result = new ErrorAnswer("Command not recognized as JSON", false).asJSON();
                    out.println(result);
                }

                out.println("Please, write another command...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    String executeCmd(Command cmd) {
        if( cmd == null) {
            return new ErrorAnswer("Command not recognized", false).asJSON();
        }
        System.out.println(cmd.getCmd());

        if(cmd.getCmd() == null) {
            return new ErrorAnswer("You should use cmd...", false).asJSON();
        }

        if( cmd.getCmd().equals("most_caloric")) {
            return mostCaloricTCP(sampleDishes);
        }

        if(cmd.getCmd().equals("all_vegan")) {
            return onlyVegansTCP(sampleDishes);
        }

        if (cmd.getCmd().equals("all")) {
            return all();
        }

        return new ErrorAnswer("Inserted command not recognized", false).asJSON();
    }

    void sendMsg(String msg) {
        if(out!=null) {
            out.println(msg);
            out.flush();
        }
    }

}