package sample;

import java.time.LocalDateTime;

public class Order {
    int orderNo;
    String customer;
    String item;
    LocalDateTime date;
    int quantity;
    double totalPrice;

    public Order(int orderNo, String customer, String item, LocalDateTime date, int quantity, double totalPrice){
        this.orderNo = orderNo;
        this.customer = customer;
        this.item = item;
        this.date = date;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderNo(){return orderNo;}
    public String getCustomer(){return customer;}
    public String getItem(){return item;}
    public LocalDateTime getDate(){return date;}
    public double getTotalPrice(){return totalPrice;}
}
