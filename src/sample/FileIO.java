package sample;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileIO {
    public static LinkedList<Item> readItemFiles(){
        LinkedList<Item> ItemList = new LinkedList<Item>();
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

                Item item = new Item();
                item.id = idInt;
                item.name = attribute[1];
                item.category = attribute[2];
                item.variant = attribute[3];
                item.stock = stockInt;
                item.price = priceDouble;

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
                myWriter.write(ItemList.get(i).id + "," + ItemList.get(i).name + "," + ItemList.get(i).category + "," + ItemList.get(i).variant + "," + ItemList.get(i).stock + "," + ItemList.get(i).price + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeOrder(){
        try {
            FileWriter myWriter = new FileWriter("assets/OrderReport" + /* date +*/ ".csv");
            myWriter.write("~~~Order Report -> date;item;category;variant;customerName;location;quantity;paymentMethod\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


