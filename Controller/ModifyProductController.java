package com.example.inventory_management_system;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.inventory_management_system.Inventory.getAllProducts;

import com.example.inventory_management_system.Inventory;
import static com.example.inventory_management_system.Inventory.getAllParts;


public class ModifyProductController implements Initializable{

    @FXML private Button cancelButton;
    @FXML private TextField productID;
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    private Products selectedProduct;
    @FXML private TableView<Parts> partsTableView;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Parts, Double> partPriceColumn;
    @FXML private TableView<Parts> associatedPartsTableView;
    @FXML private TableColumn<Parts, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Parts, String> associatedPartNameColumn;
    @FXML private TableColumn<Parts, Integer> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Parts, Double> associatedPartPriceColumn;
    @FXML private TextField partSearchField;
    private int id;

    private ObservableList<Parts> tempAssociatedPartsList = FXCollections.observableArrayList();
    Inventory inventory = new Inventory();

    //Method to populate the text fields based on the selected product to modify on the main screen
    public void setProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
        id = getAllProducts().indexOf(selectedProduct);
        productID.setText(Integer.toString(selectedProduct.getProductID()));
        Name.setText(selectedProduct.getName());
        Inventory.setText(Integer.toString(selectedProduct.getStock()));
        Price.setText(Double.toString(selectedProduct.getProductPrice()));
        Minimum.setText(Integer.toString(selectedProduct.getMin()));
        Maximum.setText(Integer.toString(selectedProduct.getMax()));

        tempAssociatedPartsList.addAll(selectedProduct.getAllAssociatedParts());
    }

    //Create a method to handle add associated part button and populate the associated part table view
    public void addAssociatedPartButtonClicked(ActionEvent event) throws IOException{
        Parts selectedAssociatedPart = partsTableView.getSelectionModel().getSelectedItem();
        tempAssociatedPartsList.add(selectedAssociatedPart);
        associatedPartsTableView.setItems(tempAssociatedPartsList);
    }

    //Method that removes selected part in associated part table
    public void removeAssociatedPartButtonClicked(ActionEvent event) throws IOException{
        Parts selectedAssociatedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        tempAssociatedPartsList.remove(selectedAssociatedPart);
        associatedPartsTableView.setItems(tempAssociatedPartsList);
    }

    public void partSearchButtonClicked(){
        String input = partSearchField.getText();

        if(isInteger(input)){
            int inputId = Integer.parseInt(input);
            ObservableList<Parts> filteredIDList = FXCollections.observableArrayList();
            for(Parts parts : inventory.getAllParts()) {
                if (parts.getPartID() == inputId) {
                    filteredIDList.add(parts);
                }
            }
            partsTableView.setItems(filteredIDList);

            if(partsTableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Part found. Please try a different ID", ButtonType.OK);
                alert.showAndWait();
            }

        } else if(input instanceof String){

            ObservableList<Parts> filteredNameList = FXCollections.observableArrayList();
            for(Parts parts : inventory.getAllParts()) {
                if(parts.getName().toLowerCase().contains(((String) input).toLowerCase())){
                    filteredNameList.add(parts);
                }
            }
            partsTableView.setItems(filteredNameList);

            if(partsTableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Part found. Please try a different Name", ButtonType.OK);
                alert.showAndWait();
            }

        } else{
            partsTableView.setItems(inventory.partsList);
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    //Method to handle functionality when clicking the save part  on the Modify Part Screen
    public void saveProductButtonClicked(ActionEvent event) throws IOException{
        try{
            int prodInventory = Integer.parseInt(Inventory.getText());
            int prodMin = Integer.parseInt(Minimum.getText());
            int prodMax = Integer.parseInt(Maximum.getText());
            if(prodMax < prodMin){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Maximum cannot be greater than Minimum", ButtonType.OK);
                alert.showAndWait();
            } else if(prodInventory < prodMin || prodInventory> prodMax){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory must be between Min and Max", ButtonType.OK);
                alert.showAndWait();
            }

            else {
                int ID = Integer.parseInt(productID.getText());
                String name = Name.getText();
                int stock = prodInventory;
                double price = Double.parseDouble(Price.getText());
                int min = prodMin;
                int max = prodMax;

                Products update = new Products(ID, stock, min, max, name, price);
                com.example.inventory_management_system.Inventory.updateProduct(id, update);

                for (Parts part: tempAssociatedPartsList) {
                    update.addAssociatedPart(part);
                }

                Parent mainScreenWindow = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenWindow);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }



        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error adding part. Please check all fields for correct input.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //Method to handle functionality when the cancel button is clicked on the Modify Part screen
    public void cancelProductButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to Cancel?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Parent mainScreenWindow = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
    }

    //RUNTIME ERROR: I got an error stating
    // "java: cannot find symbol
    //  symbol:   method getAllParts()
    //  location: variable Inventory of type javafx.scene.control.TextField"
    //I solved this by creating an instance of the Inventory class above my code
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Inventory inventory = new Inventory();

        partsTableView.setItems(inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Parts, Double>("partCost"));

        associatedPartsTableView.setItems(tempAssociatedPartsList);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Parts, Double>("partCost"));




    }
}
