package sample;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileIO {
    public static LinkedList<Item> readItemFiles(){
        LinkedList<Item> ItemList = new LinkedList<Item>();
        LinkedList<Order> orderList = new LinkedList<Order>();
        boolean isFirstLine = true;
        try {
            File obj = new File("assets/ItemDatabase.csv");
            Scanner reader = new Scanner(obj);

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] attribute = data.split(",");

                int idInt = Integer.parseInt(attribute[0]);
                int stockInt = Integer.parseInt(attribute[4]);
                double priceDouble = Double.parseDouble(attribute[5]);

                Item item = new Item(idInt, attribute[1], attribute[2], attribute[3], stockInt, priceDouble);

                ItemList.add(item);
            }
            reader.close();
            return ItemList;
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
        }
        return ItemList;
    }

    public static void writeItemFiles(LinkedList<Item> ItemList){
        try {
            FileWriter myWriter = new FileWriter("assets/ItemDatabase.csv");
            myWriter.write("~~~Item Database -> id;name;category;variant;stock;price\n");
            for (int i = 0; i < ItemList.size(); i++) {
                myWriter.write(ItemList.get(i).id + "," + ItemList.get(i).product + "," + ItemList.get(i).category + "," + ItemList.get(i).variant + "," + ItemList.get(i).stock + "," + ItemList.get(i).price + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the Items csv.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static LinkedList<Order> readOrder(){
        LinkedList<Order> orderList = new LinkedList<Order>();
        boolean isFirstLine = true;
        try {
            File obj = new File("assets/OrderReport.csv");
            Scanner reader = new Scanner(obj);

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] attribute = data.split(",");

                int orderNo = Integer.parseInt(attribute[0]);
                LocalDateTime date = LocalDateTime.parse(attribute[3]);
                int quantity = Integer.parseInt(attribute[4]);
                double priceDouble = Double.parseDouble(attribute[5]);

                Order order = new Order(orderNo, attribute[1], attribute[2], date, quantity, priceDouble);
                orderList.add(order);
            }
            reader.close();
            return orderList;
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
        }
        return orderList;
    }

    public static void writeOrder(LinkedList<Order> orderList){
        try {
            FileWriter myWriter = new FileWriter("assets/OrderReport.csv");
            myWriter.write("~~~Order Report -> orderNo;item;customer;date;quantity;totalPrice\n");
            for (int i = 0; i < orderList.size(); i++) {
                myWriter.write(orderList.get(i).orderNo + "," + orderList.get(i).item + "," + orderList.get(i).customer + "," + orderList.get(i).date + "," + orderList.get(i).quantity + "," + orderList.get(i).totalPrice + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


