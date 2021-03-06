package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OrderController extends Main implements Initializable {

    LinkedList<Order> reportList = new LinkedList<Order>();
    public static LocalDate startDate;
    public static LocalDate endDate;
    public static Order selectedItem;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ChoiceBox dateSorter;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    public Button addOrder;

    @FXML
    public Button removeOrder;

    @FXML
    private Button generateReports;

    @FXML
    private TableColumn<Order, String> idColumn;

    @FXML
    private TableColumn<Order, String> itemColumn;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, Double> priceColumn;


    @FXML
    public void onItemHighlight(MouseEvent event){
        try{
            selectedItem = orderTable.getSelectionModel().getSelectedItem();
        }catch (Error e){
            e.printStackTrace();
            System.out.println("Failed item selection");
        }
        if (selectedItem==null) return;
        removeOrder.setDisable(false);
    }


    @FXML
    public void generateReportsFunction(MouseEvent event){
        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();
        reportList = new LinkedList<Order>();


        if (startDate == null && endDate == null) return;
        if (startDate == null && endDate != null) {
            startDate = LocalDate.now();

            for (int i = 0; i < orderList.size(); i++) {
                if (startDate.isAfter(orderList.get(i).date)) startDate = orderList.get(i).date;
            }
            endDate = endDatePicker.getValue();
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).date.isAfter(startDate.minusDays(1)) && orderList.get(i).date.isBefore(endDate.plusDays(1))){
                    reportList.add(orderList.get(i));
                }
            }
            System.out.println(startDate + " " + endDate);
            writeOrder(reportList);
            generateGraph(reportList);
            return;
        }

        if (startDate != null && endDate == null){
            startDate = startDatePicker.getValue();
            endDate = LocalDate.now();
            for (int i = 0; i < orderList.size(); i++) {
                if (startDate.minusDays(1).isBefore(orderList.get(i).date) && endDate.plusDays(1).isAfter(orderList.get(i).date)){
                    reportList.add(orderList.get(i));
                }
            }
            writeOrder(reportList);
            generateGraph(reportList);
            return;
        }


        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();
        for (int i = 0; i < orderList.size(); i++) {
            if (startDate.minusDays(1).isBefore(orderList.get(i).date) && endDate.plusDays(1).isAfter(orderList.get(i).date)){
                reportList.add(orderList.get(i));
            }
        }
        writeOrder(reportList);
        generateGraph(reportList);
        return;
    }

    //Graph generation
    public void generateGraph(LinkedList<Order>reportList) {
        //removed duplicates array
        ArrayList<Integer> withoutDuplicates = new ArrayList<Integer>();
        ArrayList<Integer> withDuplicates = new ArrayList<Integer>();

        //By day
        if (dateSorter.getValue().equals("By day of year")) {
            for (int i = 0; i < reportList.size(); i++) {
                withDuplicates.add(reportList.get(i).getDate().getDayOfYear());
                if (!withoutDuplicates.contains(reportList.get(i).getDate().getDayOfYear())) {
                    withoutDuplicates.add(reportList.get(i).getDate().getDayOfYear());
                }
            }
        }

        //By month
        if (dateSorter.getValue().equals("By month")) {
            for (int i = 0; i < reportList.size(); i++) {
                withDuplicates.add(reportList.get(i).getDate().getMonthValue()%12);
                if (!withoutDuplicates.contains(reportList.get(i).getDate().getMonthValue()%12)) {
                    withoutDuplicates.add(reportList.get(i).getDate().getMonthValue()%12);
                }
            }
        }

        //By year
        if (dateSorter.getValue().equals("By year")) {
            for (int i = 0; i < reportList.size(); i++) {
                withDuplicates.add(reportList.get(i).getDate().getYear());
                if (!withoutDuplicates.contains(reportList.get(i).getDate().getYear())) {
                    withoutDuplicates.add(reportList.get(i).getDate().getYear());
                }
            }


        }

        //Create array of repetitions inside withDuplicates
        ArrayList<Integer> instanceArray = createInstanceArray(withoutDuplicates, withDuplicates);
        XYChart.Series series = new XYChart.Series();

        // +- 5 To add space for graph
        if (dateSorter.getValue().equals("By day of year")) {
            NumberAxis xAxis = new NumberAxis(returnLowest(withoutDuplicates) - 5, returnHighest(withoutDuplicates) + 5, withoutDuplicates.size());
            NumberAxis yAxis = new NumberAxis(returnLowest(instanceArray) - 5, returnHighest(instanceArray) + 5, withoutDuplicates.size());
            LineChart lineChart = new LineChart(xAxis, yAxis);
            xAxis.setLabel("Day of Year");
            yAxis.setLabel("Sales");
            lineChart.getData().add(series);
            Group root = new Group(lineChart);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sales Graph");
            stage.show();
        }

        if (dateSorter.getValue().equals("By month")) {
            NumberAxis xAxis = new NumberAxis(returnLowest(withoutDuplicates) - 2, returnHighest(withoutDuplicates) + 2, withoutDuplicates.size());
            NumberAxis yAxis = new NumberAxis(returnLowest(instanceArray) - 2, returnHighest(instanceArray) + 2, withoutDuplicates.size());
            LineChart lineChart = new LineChart(xAxis, yAxis);
            xAxis.setLabel("Month");
            yAxis.setLabel("Sales");
            lineChart.getData().add(series);
            Group root = new Group(lineChart);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sales Graph");
            stage.show();
        }

        if (dateSorter.getValue().equals("By year")) {
            NumberAxis xAxis = new NumberAxis(returnLowest(withoutDuplicates) - 2, returnHighest(withoutDuplicates) + 2, withoutDuplicates.size());
            NumberAxis yAxis = new NumberAxis(returnLowest(instanceArray) - 2, returnHighest(instanceArray) + 2, withoutDuplicates.size());
            LineChart lineChart = new LineChart(xAxis, yAxis);
            xAxis.setLabel("Year");
            yAxis.setLabel("Sales");
            lineChart.getData().add(series);
            Group root = new Group(lineChart);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sales Graph");
            stage.show();
        }

        //Each item in withoutDuplicates is data point on graph. Array withDuplicates is for tracking repetitions of withoutDuplicates.
        for (int i = 0; i < withoutDuplicates.size(); i++) {
            series.getData().add(new XYChart.Data(withoutDuplicates.get(i), returnInstances(withoutDuplicates.get(i), withDuplicates)));
        }

    }

    public static void writeOrder(LinkedList<Order> orderList){
        try {
            FileWriter myWriter = new FileWriter("assets/" + startDate + " to " + endDate + " Order Report.csv");
            myWriter.write("~~~Order Report -> orderID;item;customer;date;quantity;totalPrice\n");
            for (int i = 0; i < orderList.size(); i++) {
                myWriter.write(orderList.get(i).orderID + "," + orderList.get(i).item + ","
                        + orderList.get(i).customer + "," + orderList.get(i).date + ","
                        + orderList.get(i).quantity + "," + orderList.get(i).totalPrice + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the OrderReport csv.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int returnLowest(ArrayList<Integer> input){
        int j = 999999;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) < j){
                j = input.get(i);
            }
        }
        return j;
    }

    public int returnHighest(ArrayList<Integer> input){
        int j = -99999;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) > j){
                j = input.get(i);
            }
        }
        return j;
    }

    public int returnInstances(int target, ArrayList<Integer> input){
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            if (target == input.get(i)){
                counter+=1;
            }
        }
        return counter;
    }

    public ArrayList<Integer> createInstanceArray (ArrayList<Integer> woD, ArrayList<Integer> D){
        ArrayList<Integer> output = new ArrayList<>();
        int counter;
        System.out.println(woD + "/n" + D);
        for (int i = 0; i < woD.size(); i++) {
            counter = 0;
            for (int j = 0; j < D.size(); j++) {
                if (woD.get(i).equals(D.get(j))){ counter+=1;}
            }
            output.add(counter);
        }
        return output;
    }

    @FXML
    public void addOrderAction(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddOrderPopup.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    fillTable();
                    removeOrder.setDisable(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void fillTable(){
        orderTable.getItems().clear();
        for (int i = 0; i < orderList.size(); i++) {
            orderTable.getItems().add(orderList.get(i));
        }
    }

    @FXML
    void removeOrderAction(MouseEvent event){
        String[] item = selectedItem.item.split(";");
        boolean addStock = false;
        boolean found = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Confirm Delete Order? Stocks of the item will be added upon deletion");
        alert.setContentText("Delete order #" + selectedItem.orderID + " " + item[0] + item[1] + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //Checks if order item is in ItemList. If it is, then subtract stock.
            for (int i = 0; i < ItemList.size(); i++) {
                if (item[0].equals(ItemList.get(i).product) && item[1].equals(ItemList.get(i).variant)){
                    found = true;
                    ItemList.get(i).stock += selectedItem.quantity;
                    FileIO.writeItemFiles(ItemList);
                    break;
                }
            }

            if(found){
                for(int i=0; i<orderList.size(); i++){
                    if(orderList.get(i).orderID == selectedItem.orderID){
                        orderList.remove(i);
                        orderTable.getItems().clear();
                        fillTable();
                        FileIO.writeOrder(orderList);
                        break;
                    }
                }
            }
            else{
                alert.close();
                Alert alertAddStock = new Alert(Alert.AlertType.CONFIRMATION);
                alertAddStock.setTitle("Item cannot be found");
                alertAddStock.setHeaderText("Cannot update stocks because item cannot be found. Delete order anyway?");
                alertAddStock.setContentText("Delete order #" + selectedItem.orderID + " " + item[0] + item[1] + "?");
                Optional<ButtonType> result2 = alertAddStock.showAndWait();

                if (result2.get() == ButtonType.OK){
                    for(int i=0; i<orderList.size(); i++){
                        if(orderList.get(i).orderID == selectedItem.orderID){
                            orderList.remove(i);
                            orderTable.getItems().clear();
                            fillTable();
                            FileIO.writeOrder(orderList);
                            break;
                        }
                    }
                }

                else{
                    alertAddStock.close();
                }
            }
        }
        else {
            alert.close();
        }
        removeOrder.setDisable(true);
    }


    @FXML
    void goMenu(MouseEvent event) {
        switchScene(event,"MainMenu", "Menu");
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        for (int i = 0; i < orderList.size(); i++) {
            orderTable.getItems().add(orderList.get(i));
        }


        dateSorter.getItems().add("By day of year");
        dateSorter.getItems().add("By month");
        dateSorter.getItems().add("By year");
        dateSorter.setValue("By day of year");
        endDatePicker.setValue(now);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }
}
