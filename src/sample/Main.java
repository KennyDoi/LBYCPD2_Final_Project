package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public ArrayList<Item> ItemList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("21 Carts System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        ItemList = FileIO.readItemFiles();
        FileIO.writeItemFiles(ItemList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
