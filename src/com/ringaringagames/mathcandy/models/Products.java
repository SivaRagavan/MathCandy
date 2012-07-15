package com.ringaringagames.mathcandy.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Products {
    private final static ArrayList<Product> products;

    static {
        products = new ArrayList<Product>();

        products.add(new Product("cadbury", 5));
        products.add(new Product("gems", 3));
        products.add(new Product("doll", 100));
        products.add(new Product("lays", 10));
        products.add(new Product("ball", 20));
        products.add(new Product("ballon", 2));

    }

    public static HashSet<Product> getRandomProducts(int howMany) {

        HashSet<Product> randomProducts = new HashSet<Product>();
        Random randomGenerator = new Random();

        int randomInt;
        do {
            randomInt = randomGenerator.nextInt(6);
            randomProducts.add(products.get(randomInt));
        } while (randomProducts.size() < howMany);

        return randomProducts;
    }


}
