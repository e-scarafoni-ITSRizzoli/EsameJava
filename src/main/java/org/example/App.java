package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    static ArrayList<Dish> sampleDishes = new ArrayList<>();
    static void buildList(ArrayList<Dish> dishes) {
        dishes.add(new Dish("Il famoso primo romano", 1, false, "Carbonara", 402.6, 9.50));
        dishes.add(new Dish("Gustoso secondo vegano", 2, true, "Burger di spinaci", 125, 8.25));
        dishes.add(new Dish("Il piatto simbolo italiano", 3, false, "Pizza margherita", 950, 6.5));
        dishes.add(new Dish("Primo semplice e piccante",4 , true, "Spaghetti aglio olio peperoncino", 350, 6.7));

    }

    static String arrayAsJSON(ArrayList<Dish> dishes) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String toJson = g.toJson(dishes);
        toJson = "{ \n \"piatti\": " + toJson + "\n}";
        return toJson;
    }

    static String mostCaloric(ArrayList<Dish> dishes) {
        double maxCal = 0;
        Dish mostCalDish = null;
        for (Dish d:
             dishes) {
            if (d.getCalories() >= maxCal) {
                maxCal = d.getCalories();
                mostCalDish = d;
            }
        }
        return  mostCalDish.asJSON();
    }

    static String onlyVegans(ArrayList<Dish> dishes) {
        ArrayList<Dish> veg = new ArrayList<>();
        for (Dish d:
             dishes) {
            if (d.isVegan()) {
                veg.add(d);
            }
        }
        return arrayAsJSON(veg);
    }

    public static void main(String[] args) {
        buildList(sampleDishes);
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new MyHandler());

        server.setExecutor(null);
        server.start();
    }

}
