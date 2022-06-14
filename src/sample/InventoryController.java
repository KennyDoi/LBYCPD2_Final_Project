package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.event.ActionEvent;
import java.util.Formatter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InventoryController extends Main implements Initializable{
    public int selectedID;
    public static Item selectedItem;
    public int index;
    public static ArrayList<String> categories;

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
    public TableView<Item> tableView;

    @FXML
    public TableColumn<Item, Integer> idColumn;

    @FXML
    public TableColumn<Item, String> prodColumn;

    @FXML
    public TableColumn<Item, String> variantColumn;

    @FXML
    public TableColumn<Item, Double> priceColumn;

    @FXML
    public TableColumn<Item, Integer> stockColumn;

    @FXML
    public TableColumn<Item, String> catColumn;


    @FXML
    void goMenu(MouseEvent event) {
        switchScene(event,"MainMenu", "Menu");
    }

    @FXML
    void categoryClicked(MouseEvent event) {
        selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            deleteItemButton.setDisable(false);
            editItemButton.setDisable(false);
            addCheck.setDisable(false);
            subtractCheck.setDisable(false);
            System.out.println(selectedItem.category);
            return;
        }
    }


    @FXML
    public void getCategory(MouseEvent event) {
        tableView.getItems().clear();
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        editItemButton.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        System.out.println(category);

        if (category==null) return;

        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                tableView.getItems().add(ItemList.get(i));
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    tableView.getItems().add(ItemList.get(i));
                }
            }
        }
        selectedItem=null;
    }

    @FXML
    public void getCategory() {
        tableView.getItems().clear();
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        editItemButton.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);


        String category = categoryInventory.getSelectionModel().getSelectedItem();
        System.out.println(category);
        if (category==null){return;}

        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                tableView.getItems().add(ItemList.get(i));
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    tableView.getItems().add(ItemList.get(i));
                }
            }
        }
        selectedItem=null;
    }


    @FXML
    void addStock(MouseEvent event) {
        if(!addCheck.isSelected()){
            for(int i=0; i<ItemList.size(); i++){
                if(ItemList.get(i).id == selectedItem.id){
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
        tableView.getItems().clear();
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                tableView.getItems().add(ItemList.get(i));
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    tableView.getItems().add(ItemList.get(i));
                }
            }
        }
        FileIO.writeItemFiles(ItemList);
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        editItemButton.setDisable(true);
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
        tableView.getItems().clear();
        String category = categoryInventory.getSelectionModel().getSelectedItem();
        if(category.equals("ALL")) {
            for (int i = 0; i < ItemList.size(); i++) {
                tableView.getItems().add(ItemList.get(i));
            }
        }
        else if (category != null){
            for (int i = 0; i < ItemList.size(); i++) {
                if (category.equals(ItemList.get(i).category)) {
                    tableView.getItems().add(ItemList.get(i));
                }
            }
        }
        FileIO.writeItemFiles(ItemList);
        deleteItemButton.setDisable(true);
        addCheck.setDisable(true);
        editItemButton.setDisable(true);
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
                if(ItemList.get(i).id == selectedItem.id){
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
    void addItem(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addPopup.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    getCategory();
                    refreshCategories();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editItem(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editPopup.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    getCategory();
                    refreshCategories();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteItem(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Confirm Delete Item?");
        alert.setContentText("Delete product " + selectedItem.product + " " + selectedItem.variant + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for(int i=0; i<ItemList.size(); i++){
                if(ItemList.get(i).id == selectedItem.id){
                    ItemList.remove(i);
                    FileIO.writeItemFiles(ItemList);
                }
            }
            tableView.getItems().clear();
            String category = categoryInventory.getSelectionModel().getSelectedItem();
            if(category.equals("ALL")) {
                for (int i = 0; i < ItemList.size(); i++) {
                    tableView.getItems().add(ItemList.get(i));
                }
            }
            else if (category != null){
                for (int i = 0; i < ItemList.size(); i++) {
                    if (category.equals(ItemList.get(i).category)) {
                        tableView.getItems().add(ItemList.get(i));
                    }
                }
            }
        }
        else {
            alert.close();
        }
        deleteItemButton.setDisable(true);
        editItemButton.setDisable(true);
        addCheck.setDisable(true);
        addConfirmStock.setVisible(false);
        addStockSpinner.setDisable(true);
        subtractCheck.setDisable(true);
        subStockSpinner.setDisable(true);
        subConfirmStock.setVisible(false);
        addCheck.setSelected(false);
        subtractCheck.setSelected(false);
        selectedItem = null;
        refreshCategories();

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

    void refreshCategories(){
        FileIO.readItemFiles();
        categoryInventory.getItems().clear();
        categories = removeDuplicates(ItemList, ItemList.size());
        categoryInventory.getItems().add("ALL");
        for (int i = 0; i < categories.size(); i++) {
            categoryInventory.getItems().add(categories.get(i));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        refreshCategories();
        System.out.println("test");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        catColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        variantColumn.setCellValueFactory(new PropertyValueFactory<>("variant"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
