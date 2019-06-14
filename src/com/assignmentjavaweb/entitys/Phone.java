package com.assignmentjavaweb.entitys;

import java.util.ArrayList;
import java.util.HashMap;

public class Phone {
    private int id;
    private String name;
    private String brand;
    private int price;
    private String description;

    HashMap<String, ArrayList<String>> errors = new HashMap<>();
    public Phone() {
    }

    public Phone(String name, String brand, int price, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        validate();
        return this.errors.size() == 0;
    }


    private static boolean isNumber(String string) {
        return string.matches(".*[0-9].*");
    }

    private static boolean isString(String string) {
        return string.matches(".*[a-zA-Z].*");
    }


    private void validate() {
        if (this.errors == null) {
            this.errors = new HashMap<>();
        }
        ArrayList<String> nameError = this.errors.get("name");
        ArrayList<String> priceError = this.errors.get("price");
        ArrayList<String> descriptionError = this.errors.get("description");

        if (nameError == null) {
            nameError = new ArrayList<>();
        }
        if (this.name == null || this.name.length() == 0) {
            nameError.add("name is required!!!");
        }
        if (this.name.length() < 2 || this.name.length() > 30) {
            nameError.add("name must be between 2 and 30 character");
        }
        if (nameError.size() > 0) {
            this.errors.put("name", nameError);
        }

        if (priceError == null) {
            priceError = new ArrayList<>();
        }
        if (this.price == 0) {
            priceError.add("price is required!!!");
        }
        if (priceError.size() > 0) {
            this.errors.put("price", priceError);
        }


        if (descriptionError == null) {
            descriptionError = new ArrayList<>();
        }
        if (this.description == null || this.description.length() == 0) {
            descriptionError.add("description is required!!!");

        }
        if (this.description.length() < 2 || this.description.length() > 30) {
            descriptionError.add("description must be between 6 and 8 character");
        }
        if (descriptionError.size() > 0) {
            this.errors.put("description", descriptionError);
        }
    }

    public HashMap<String, ArrayList<String>> getErrors() {
        return this.errors;
    }
}
