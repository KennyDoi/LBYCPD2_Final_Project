package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Text currentStockText;

    @FXML
    private Button confirmItemVariationButton;

    @FXML
    void confirmCategory(MouseEvent event) {
        itemName.getItems().clear();
        itemVariant.getItems().clear();
        currentStockText.setText("");
        quantitySpinner.setDisable(true);
        if (categoryInventory.getSelectionModel().isEmpty()){
            itemName.setDisable(true);
            itemVariant.setDisable(true);
        }
        else {
            itemName.setDisable(false);
            itemVariant.setDisable(true);

            for (int i = 0; i < ItemList.size(); i++) {
                if (categoryInventory.getSelectionModel().getSelectedItem().equals(ItemList.get(i).category)) {
                    if(!itemName.getItems().contains(ItemList.get(i).product)) {
                        itemName.getItems().add(ItemList.get(i).product);
                    }
                }
            }
            confirmItemNameButton.setDisable(false);
            confirmItemVariationButton.setDisable(true);
        }
    }

    @FXML
    void confirmItemName(MouseEvent event){
        currentStockText.setText("");
        quantitySpinner.setDisable(true);
        itemVariant.getItems().clear();
        if (itemName.getSelectionModel().isEmpty()) {
            itemVariant.setDisable(true);
        }
        else{
            itemVariant.setDisable(false);
            confirmItemVariationButton.setDisable(false);

            for (int i = 0; i < ItemList.size(); i++) {
                if (itemName.getSelectionModel().getSelectedItem().equals(ItemList.get(i).product)) {
                    if(!itemVariant.getItems().contains(ItemList.get(i).product)) {
                        itemVariant.getItems().add(ItemList.get(i).variant);
                    }
                }
            }
        }
    }

    @FXML
    void confirmItemVariation(){
        if (itemVariant.getSelectionModel().isEmpty()) {
            quantitySpinner.setDisable(true);
        }
        else{
            for (int i = 0; i < ItemList.size(); i++) {
                if(itemName.getSelectionModel().getSelectedItem().equals(ItemList.get(i).product) && itemVariant.getSelectionModel().getSelectedItem().equals(ItemList.get(i).variant)){
                    quantitySpinner.setDisable(false);
                    currentStockText.setText("Current Stock: " + ItemList.get(i).stock);

                    SpinnerValueFactory<Integer> quantityFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, ItemList.get(i).stock, 1);
                    quantitySpinner.setValueFactory(quantityFactory);
                    break;
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
    public int findfromName(String itemName){
        for (int i = 0; i < ItemList.size(); i++) {
            if (itemName == ItemList.get(i).product) return i;
        }
        return 0;
    }

    @FXML
    void confirmAdd(MouseEvent event) {
        boolean error = false;

        String orderID = orderNumInput.getText().trim();
        String customerName = customerNameInput.getText().trim();
        String nameItem = itemName.getSelectionModel().getSelectedItem();
        String variantItem = itemVariant.getSelectionModel().getSelectedItem();
        LocalDate date = dateOrdered.getValue();
        String itemFinal = nameItem + ";" + variantItem;

        //TextField and choiceBox error catching
        if (quantitySpinner.getValue() == null) error = true;
        if (orderID == null || orderID.length() <= 0) error = true;
        if (customerName == null || orderID.length() <= 0) error = true;
        if (nameItem == null || orderID.length() <= 0) error = true;
        if (variantItem == null || orderID.length() <= 0) error = true;

        //Error catch
        if (error) {
            Alert alertError = new Alert(Alert.AlertType.INFORMATION);
            alertError.setTitle("Warning!");
            alertError.setHeaderText("Cannot add order because one of the inputs is empty!");
            Optional<ButtonType> result = alertError.showAndWait();
            if(result.get() == ButtonType.OK){
                return;
            }
            return;
        }

        if(orderID.contains(",") || customerName.contains(",")){
            Alert alertError = new Alert(Alert.AlertType.INFORMATION);
            alertError.setTitle("Warning!");
            alertError.setHeaderText("Cannot input \",\" in any text");
            Optional<ButtonType> result = alertError.showAndWait();
            if(result.get() == ButtonType.OK){
                return;
            }
            return;
        }

        int quantity = quantitySpinner.getValue();

        //Error catch quantity is 0
        if (quantity == 0) {
            Alert alertError = new Alert(Alert.AlertType.INFORMATION);
            alertError.setTitle("Warning!");
            alertError.setHeaderText("Cannot add order because quantity is 0");
            Optional<ButtonType> result = alertError.showAndWait();
            if(result.get() == ButtonType.OK){
                return;
            }
            return;
        }

        double totalPrice;
        totalPrice = quantity*ItemList.get(findfromName(nameItem)).price;

        Order order = new Order(orderID, itemFinal, customerName, date, quantity, totalPrice);
        orderList.add(order);
        for (int i = 0; i < ItemList.size(); i++) {
            if(nameItem.equals(ItemList.get(i).product) && variantItem.equals(ItemList.get(i).variant)){
                ItemList.get(i).stock -= quantity;
            }
        }
        FileIO.writeOrder(orderList);
        FileIO.writeItemFiles(ItemList);
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
