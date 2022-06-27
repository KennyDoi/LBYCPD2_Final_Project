package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {
    public static LinkedList<Item> ItemList;
    public static LinkedList<Order> orderList;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("21 Carts System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ItemList = FileIO.readItemFiles();
        orderList = FileIO.readOrder();
    }

    public LinkedList<Item> getItemList(){
        return ItemList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
