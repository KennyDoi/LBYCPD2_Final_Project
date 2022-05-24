package sample;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileIO {
    public static ArrayList<Item> readItemFiles(){
        ArrayList<Item> ItemList = new ArrayList<Item>();
        boolean isFirstLine = true;
        try {
            File obj = new File("assets/ItemDatabase.txt");
            Scanner reader = new Scanner(obj);

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] attribute = data.split(";");

                int idInt = Integer.parseInt(attribute[0]);
                int stockInt = Integer.parseInt(attribute[4]);
                double priceDouble = Double.parseDouble(attribute[5]);


                Item med = new Item();
                med.id = idInt;
                med.name = attribute[1];
                med.category = attribute[2];
                med.variant = attribute[3];
                med.stock = stockInt;
                med.price = priceDouble;

                ItemList.add(med);
            }
            reader.close();
            return ItemList;
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
        }
        return ItemList;
    }

    public static void writeItemFiles(ArrayList<Item> ItemList){
        try {
            FileWriter myWriter = new FileWriter("assets/ItemDatabase.txt");
            myWriter.write("~~~Item Database -> id;name;category;variant;stock;price\n");
            for (int i = 0; i < ItemList.size(); i++) {
                myWriter.write(ItemList.get(i).id + ";" + ItemList.get(i).name + ";" + ";" + ItemList.get(i).category + ";" + ItemList.get(i).variant + ";" + ItemList.get(i).stock + ItemList.get(i).price + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
