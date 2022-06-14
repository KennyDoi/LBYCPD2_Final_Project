package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class EditPopupController extends InventoryController implements Initializable {

    @FXML
    public Button cancelEdit;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField variantInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField stockInput;
    @FXML
    private ChoiceBox<String> categoryInventory;
    @FXML
    public TableView tableView;

    @FXML
    public void cancelEdit(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelEdit.getScene().getWindow();
        stage.hide();
    }


    @FXML
    public void confirmEdit(MouseEvent event) {
        //Error Checking Flag
        boolean error = false;

        //Get info from textfields
        String name = nameInput.getText().trim();
        String variant = variantInput.getText().trim();
        String ph2 = priceInput.getText().replace(" ", "").trim();
        String ph3 = stockInput.getText().replace(" ", "").trim();
        String category = categoryInventory.getSelectionModel().getSelectedItem();


        //Textfield error catching
        if (category == null) error = true;
        if (name == null || name.length() <= 0) error = true;
        if (variant == null || variant.length() <= 0) error = true;
        if (ph2 == null || ph2.length() <= 0) error = true;
        if (ph3 == null || ph3.length() <= 0) error = true;


        //Convert non-string characters to respective data type
        int id = 0, stock;
        double price;
        try {
            price = Double.parseDouble(ph2);
            stock = Integer.parseInt(ph3);
        } catch (final NumberFormatException e){return;}

        //Get highest integer from current list of items
        for (int i = 0; i < ItemList.size(); i++) {
            if (ItemList.get(i).id > id) {
                id = ItemList.get(i).id;
            }
            id+=1;
        }

        //Error catch
        if (error) {
            return;
        }

        //Write new item details onto ItemList
        for (int i = 0; i < ItemList.size(); i++) {
            if (selectedItem.id == ItemList.get(i).id){
                ItemList.get(i).product = name;
                ItemList.get(i).variant = variant;
                ItemList.get(i).price = price;
                ItemList.get(i).stock = stock;
                ItemList.get(i).category = category;
            }
        }


        //Write changes done on ItemList to local CSV file
        FileIO.writeItemFiles(ItemList);
        Stage stage = (Stage) cancelEdit.getScene().getWindow();
        stage.hide();
    }


    public static ArrayList<String> removeDuplicates(LinkedList<Item> ItemLists, int n) {
        ArrayList<String> categories = new ArrayList<String>();
        HashMap<String, Boolean> mp = new HashMap<>();

        for (int i = 0; i < n; ++i) {
            if (mp.get(ItemLists.get(i).category) == null)
            {
                categories.add(ItemLists.get(i).category);
                mp.put(ItemLists.get(i).category, true);
            }
        }
        return categories;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<String> categories = removeDuplicates(ItemList, ItemList.size());
        for (int i = 0; i < categories.size(); i++) {
            categoryInventory.getItems().add(categories.get(i));

            nameInput.setText(selectedItem.getProduct());
            variantInput.setText(selectedItem.getVariant());
            stockInput.setText(String.valueOf(selectedItem.getStock()));
            priceInput.setText(String.valueOf(selectedItem.getPrice()));
            categoryInventory.setValue(selectedItem.category);


        }
    }
}
