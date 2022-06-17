package sample;

import java.time.LocalDate;

public class Order {
    String orderID;
    String customer;
    String item;
    LocalDate date;
    int quantity;
    double totalPrice;

    public Order(String orderID, String item, String customer, LocalDate date, int quantity, double totalPrice){
        this.orderID = orderID;
        this.customer = customer;
        this.item = item;
        this.date = date;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getOrderID(){return orderID;}
    public String getCustomer(){return customer;}
    public String getItem(){return item;}
    public LocalDate getDate(){return date;}
    public int getQuantity(){return quantity;}
    public double getTotalPrice(){return totalPrice;}
}
