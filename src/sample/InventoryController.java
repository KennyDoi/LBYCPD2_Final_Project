package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.Formatter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InventoryController extends Main implements Initializable{

    @FXML
    public ListView<String> listInventory;

    @FXML
    private ChoiceBox<String> categoryInventory;

    @FXML
    void goMenu(MouseEvent event) {
        switchScene(event,"MainMenu", "Menu");
    }

    @FXML
    void getCategory(MouseEvent event) {
        listInventory.getItems().clear();
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                listInventory.getItems().add(ItemList.get(i).id + "\t\t\t" + ItemList.get(i).name + "\t\t" + ItemList.get(i).variant + "\t\tP" + ItemList.get(i).price + "\t\t" + ItemList.get(i).stock);
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    listInventory.getItems().add(ItemList.get(i).id + "\t\t\t" + ItemList.get(i).name + "\t\t" + ItemList.get(i).variant + "\t\tP" + ItemList.get(i).price + "\t\t" + ItemList.get(i).stock);
                }
            }
        }
    }

//    public static ArrayList<String> removeDuplicates(ArrayList<Item> ItemLists, int n) {
//
//        //return newArray;
//    }


    public void switchScene(MouseEvent event, String filename, String title){
        Stage stage, currStage;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(filename+".fxml")));
            currStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setUserData(currStage.getUserData());
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        removeDuplicates(ItemList, ItemList.size());
        categoryInventory.getItems().add("ALL");
        for (int i = 0; i < ItemList.size(); i++) {
            categoryInventory.getItems().add(ItemList.get(i).category);
        }
    }
}
