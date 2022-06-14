package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {
    public static LinkedList<Item> ItemList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("21 Carts System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ItemList = FileIO.readItemFiles();
        FileIO.writeOrder();
        //FileIO.writeItemFiles(ItemList);

//        for(int i=0; i<ItemList.size(); i++){
//            System.out.println(ItemList.get(i).id+ pad(ItemList.get(i).name, 90) + pad(ItemList.get(i).variant, 20) + pad("P" + ItemList.get(i).price, 20) + pad(ItemList.get(i).stock, 20));
//        }
//        System.out.println(pad("Bacon", 7) + pad("steakkkkk", 10));
//        System.out.println(pad("Cut", 7) + pad("steakkkkk", 10));
    }

    public LinkedList<Item> getItemList(){
        return ItemList;
    }

    public static void main(String[] args) {
        launch(args);
    }

//    String pad(String x, int len){
//        int strlen = x.length();
//        StringBuilder out = new StringBuilder();
//        for(int i=0; i<len-strlen; i++){
//            out.append(' ');
//        }
//        out.append(x);
//        return out.toString();
//    }
//
//    String pad(int x, int len){
//        String temp = Integer.toString(x);
//        int strlen = temp.length();
//        StringBuilder out = new StringBuilder();
//        for(int i=0; i<len-strlen; i++){
//            out.append(' ');
//        }
//        out.append(temp);
//        return out.toString();
//    }
//
//    String pad(double x, int len){
//        String temp = Double.toString(x);
//        int strlen = temp.length();
//        StringBuilder out = new StringBuilder();
//        for(int i=0; i<len-strlen; i++){
//            out.append(' ');
//        }
//        out.append(temp);
//        return out.toString();
//    }
}
