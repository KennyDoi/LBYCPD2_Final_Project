package sample;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class Item {

    public int id;
    public String product;
    public String category;
    public String variant;
    public int stock;
    public double price;

    public Item(int idInt, String name, String category, String variant, int stockInt, double priceDouble) {
        this.id = idInt;
        this.product = name;
        this.category = category;
        this.variant = variant;
        this.stock = stockInt;
        this.price = priceDouble;
    }

    public int getId(){return id;}
    public String getProduct(){return product;}
    public String getCategory(){return category;}
    public String getVariant(){return variant;}
    public int getStock(){return stock;}
    public double getPrice(){return price;}
}
