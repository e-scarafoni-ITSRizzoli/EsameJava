package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dish {
    String descrizione;
    int id;
    boolean vegan;
    String name;
    double calories;
    double price;

    public Dish(String descrizione, int id, boolean vegan, String name, double calories, double price) {
        this.descrizione = descrizione;
        this.id = id;
        this.vegan = vegan;
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String asJSON() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String toJson = g.toJson(this);
        return toJson;
    }

    public String asJSONTCP() {
        Gson g = new Gson();
        String toJson = g.toJson(this);
        return toJson;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
