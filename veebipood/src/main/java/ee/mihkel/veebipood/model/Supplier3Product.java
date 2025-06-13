package ee.mihkel.veebipood.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3Product {
    private int id;
    private String title;
    private String description;
    private String category;
    private double price;
    private double discountPercentage;
    private double rating;
    private int stock;
    private ArrayList<String> tags;
    private String brand;
    private String sku;
    private int weight;
    private Object dimensions;
    private String warrantyInformation;
    private String shippingInformation;
    private String availabilityStatus;
    private ArrayList<Object> reviews;
    private String returnPolicy;
    private int minimumOrderQuantity;
    private Object meta;
    private ArrayList<String> images;
    private String thumbnail;
    private double retailPrice;
}
