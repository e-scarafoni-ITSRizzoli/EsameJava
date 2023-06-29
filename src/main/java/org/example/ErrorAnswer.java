package org.example;
import com.google.gson.Gson;
public class ErrorAnswer {
    String ans;
    Boolean result;

    public ErrorAnswer(String ans, Boolean result) {
        this.ans = ans;
        this.result = result;
    }

    String asJSON() {
        Gson g = new Gson();
        String toJson = g.toJson(this);
        return toJson;
    }
}