package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddOrderPopupController extends OrderController implements Initializable{


    @FXML
    private Button cancelAdd;

    @FXML
    private ChoiceBox<String> categoryInventory;

    @FXML
    private Button confirmItemNameButton;

    @FXML
    private Button confirmAdd;

    @FXML
    private DatePicker dateOrdered;

    @FXML
    private ChoiceBox<String> itemName;

    @FXML
    private ChoiceBox<String> itemVariant;

    @FXML
    private ImageView logo;

    @FXML
    private TextField newTextCategory;

    @FXML
    private TextField orderNumInput;

    @FXML
    private TextField customerNameInput;

    @FXML
    private TextField totalPriceInput;

    @FXML
    private TextField quantityInput;

    @FXML
    void confirmCategory(MouseEvent event) {
        itemName.getItems().clear();
        itemVariant.getItems().clear();
        if (categoryInventory.getSelectionModel().isEmpty()){
            itemName.setDisable(true);
            itemVariant.setDisable(true);
        }
        else {
            itemName.setDisable(false);
            itemVariant.setDisable(true);

            for (int i = 0; i < ItemList.size(); i++) {
                if (categoryInventory.getSelectionModel().getSelectedItem() == ItemList.get(i).category) {
                    itemName.getItems().add(ItemList.get(i).product);
                }
            }
            confirmItemNameButton.setDisable(false);
        }
    }

    @FXML
    void confirmItemName(MouseEvent event){
        itemVariant.getItems().clear();
        if (itemName.getSelectionModel().isEmpty()) {
            itemVariant.setDisable(true);
        }
        else{
            System.out.println("Test");
            itemVariant.setDisable(false);

            for (int i = 0; i < ItemList.size(); i++) {
                if (itemName.getSelectionModel().getSelectedItem() == ItemList.get(i).product) {
                    itemVariant.getItems().add(ItemList.get(i).variant);
                }
            }
        }
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
    void cancelAdd(MouseEvent event) {
        Stage stage = (Stage) cancelAdd.getScene().getWindow();
        stage.hide();
    }

    @FXML
    void confirmAdd(MouseEvent event) {
        String orderID = orderNumInput.getText().trim();
        String customerName = customerNameInput.getText().trim();
        String nameItem = itemName.getSelectionModel().getSelectedItem();
        String variantItem = itemVariant.getSelectionModel().getSelectedItem();
        String totalPriceString = totalPriceInput.getText().replace(" ", "").trim();
        String quantityString = quantityInput.getText().replace(" ", "").trim();
        LocalDate date = dateOrdered.getValue();
        String itemFinal = nameItem + ";" + variantItem;

        //convert to int or double
        int quantity;
        double totalPrice;

        try {
            quantity = Integer.parseInt(quantityString);
            totalPrice = Double.parseDouble(totalPriceString);
        } catch (final NumberFormatException e){return;}

        Order order = new Order(orderID, customerName, itemFinal, date, quantity, totalPrice);
        orderList.add(order);
        FileIO.writeOrder(orderList);
        Stage stage = (Stage) cancelAdd.getScene().getWindow();
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        dateOrdered.setValue(now);
        FileIO.readItemFiles();
        categoryInventory.getItems().clear();
        ArrayList<String> categories = removeDuplicates(ItemList, ItemList.size());
        for (int i = 0; i < categories.size(); i++) {
            categoryInventory.getItems().add(categories.get(i));
        }
    }
}
