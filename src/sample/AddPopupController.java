package sample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.naming.ldap.InitialLdapContext;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class AddPopupController extends InventoryController implements Initializable {

    @FXML
    public Button cancelAdd;
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
    private CheckBox confirmAddNewCategory;

    @FXML
    private TextField newTextCategory;

    @FXML
    public void cancelAdd(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelAdd.getScene().getWindow();
        stage.hide();
    }


    @FXML
    public void confirmAdd(MouseEvent event) {
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

        //Create new Item to add onto Itemlist
        Item item = new Item(id, name, category, variant, stock, price);
        ItemList.add(item);

        //Write changes done on ItemList to local CSV file
        FileIO.writeItemFiles(ItemList);
        Stage stage = (Stage) cancelAdd.getScene().getWindow();
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

    @FXML
    void showTextbox(MouseEvent event) {
        if(!confirmAddNewCategory.isSelected()){
            newTextCategory.setVisible(false);
            newTextCategory.setDisable(true);
        }
        else {
            newTextCategory.setVisible(true);
            newTextCategory.setDisable(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<String> categories = removeDuplicates(ItemList, ItemList.size());
        for (int i = 0; i < categories.size(); i++) {
            categoryInventory.getItems().add(categories.get(i));
        }
    }
}
