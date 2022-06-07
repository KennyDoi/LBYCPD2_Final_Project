package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.Formatter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InventoryController extends Main implements Initializable{
    public int selectedID;
    public String selectedItem;
    public int index;

    @FXML
    public ListView<String> listInventory;

    @FXML
    private ChoiceBox<String> categoryInventory;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button editItemButton;

    @FXML
    private Spinner<Integer> addStockSpinner;

    @FXML
    private Spinner<Integer> subStockSpinner;

    @FXML
    private CheckBox addCheck;

    @FXML
    private CheckBox subtractCheck;

    @FXML
    private Button addConfirmStock;

    @FXML
    private Button subConfirmStock;

    @FXML
    void goMenu(MouseEvent event) {
        switchScene(event,"MainMenu", "Menu");
    }

    @FXML
    void categoryClicked(MouseEvent event) {
        selectedItem = listInventory.getSelectionModel().getSelectedItem();
        String[] selectedIDString = selectedItem.split(" ");
        selectedID = Integer.parseInt(selectedIDString[0]);
        if(selectedItem != null){
            deleteItemButton.setDisable(false);
            editItemButton.setDisable(false);
            addCheck.setDisable(false);
            subtractCheck.setDisable(false);
        }
    }

    @FXML
    void getCategory(MouseEvent event) {
        listInventory.getItems().clear();
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);

        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
                }
            }
        }
    }

    @FXML
    void addStock(MouseEvent event) {
        if(!addCheck.isSelected()){
            for(int i=0; i<ItemList.size(); i++){
                if(ItemList.get(i).id == selectedID){
                    index = i;
                    break;
                }
            }
            SpinnerValueFactory<Integer> addValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
            addStockSpinner.setDisable(false);
            addConfirmStock.setVisible(true);
            addStockSpinner.setValueFactory(addValueFactory);
        }
        else{
            addStockSpinner.setDisable(true);
            addConfirmStock.setVisible(false);
        }
    }

    @FXML
    void confirmAdd(MouseEvent event) {
        ItemList.get(index).stock += addStockSpinner.getValue();
        listInventory.getItems().clear();
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
                }
            }
        }
        FileIO.writeItemFiles(ItemList);
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);
    }

    @FXML
    void confirmSub(MouseEvent event) {
        ItemList.get(index).stock -= subStockSpinner.getValue();
        listInventory.getItems().clear();
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
                }
            }
        }
        FileIO.writeItemFiles(ItemList);
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);
    }

    @FXML
    void SubStock(MouseEvent event) {
        if(subtractCheck.isSelected()){
            for(int i=0; i<ItemList.size(); i++){
                if(ItemList.get(i).id == selectedID){
                    index = i;
                    break;
                }
            }
            subStockSpinner.setDisable(false);
            subConfirmStock.setVisible(true);
            SpinnerValueFactory<Integer> subValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, ItemList.get(index).stock, 1);
            subStockSpinner.setValueFactory(subValueFactory);
        }
        else{
            subStockSpinner.setDisable(true);
            subConfirmStock.setVisible(false);
        }


    }

    String pad(String x, int len){
        int strlen = x.length();
        StringBuilder out = new StringBuilder();
        out.append(x);
        for(int i=0; i<len-strlen; i++){
            out.append(' ');
        }
        return out.toString();
    }

    String pad(int x, int len){
        String temp = Integer.toString(x);
        int strlen = temp.length();
        StringBuilder out = new StringBuilder();
        out.append(temp);
        for(int i=0; i<len-strlen; i++){
            out.append(' ');
        }
        return out.toString();
    }

    String pad(double x, int len){
        String temp = Double.toString(x);
        int strlen = temp.length();
        StringBuilder out = new StringBuilder();
        out.append(temp);
        for(int i=0; i<len-strlen; i++){
            out.append(' ');
        }
        return out.toString();
    }

    public static ArrayList<String> removeDuplicates(LinkedList<Item> ItemLists, int n) {
        ArrayList<String> categories = new ArrayList<String>();
        HashMap<String, Boolean> mp = new HashMap<>();

        for (int i = 0; i < n; ++i) {
            if (mp.get(ItemLists.get(i).category) == null){
                categories.add(ItemLists.get(i).category);
                mp.put(ItemLists.get(i).category, true);
            }
        }
        return categories;
    }

    @FXML
    void editItem(MouseEvent event) {

    }

    @FXML
    void deleteItem(MouseEvent event) {
        String[] selectedIDString = selectedItem.split(" ");
        selectedID = Integer.parseInt(selectedIDString[0]);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Confirm Delete ID?");
        alert.setContentText("Delete ID Number " + selectedID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for(int i=0; i<ItemList.size(); i++){
                if(ItemList.get(i).id == selectedID){
                    ItemList.remove(i);
                    FileIO.writeItemFiles(ItemList);
                }
            }
            listInventory.getItems().clear();
            String category = categoryInventory.getSelectionModel().getSelectedItem();
            if(category.equals("ALL")) {
                for (int i = 0; i < ItemList.size(); i++) {
                    listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
                }
            }
            else if (category != null){
                for (int i = 0; i < ItemList.size(); i++) {
                    if (category.equals(ItemList.get(i).category)) {
                        listInventory.getItems().add(pad(ItemList.get(i).id, 20) + pad(ItemList.get(i).name, 85) + pad(ItemList.get(i).variant, 45) + "P" + pad(ItemList.get(i).price, 30) + ItemList.get(i).stock);
                    }
                }
            }
        }
        else {
            alert.close();
        }

    }

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
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<String> categories = removeDuplicates(ItemList, ItemList.size());
        categoryInventory.getItems().add("ALL");
        for (int i = 0; i < categories.size(); i++) {
            categoryInventory.getItems().add(categories.get(i));
        }
    }
}
