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

public class AddProductController implements Initializable{

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button addPartButton;
    @FXML private Button removePartButton;
    @FXML private Button partSearchButton;
    @FXML private TextField partSearch;

    @FXML private TextField productName;
    @FXML private TextField productInventory;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
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

    private ObservableList<Parts> tempAssociatedPartsList = FXCollections.observableArrayList();


    private int productID;


    //Method when clicking save to populate all product information into observable list


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

    //Method to handle functionality when a user clicks the search button on the parts table view
    public void partSearchButtonClicked(){
        String input = partSearchField.getText();

        if(isInteger(input)){
            int inputId = Integer.parseInt(input);
            ObservableList<Parts> filteredIDList = FXCollections.observableArrayList();
            for(Parts parts : Inventory.getAllParts()) {
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
            for(Parts parts : Inventory.getAllParts()) {
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
            partsTableView.setItems(Inventory.partsList);
        }
    }

    //Method I created to check whether a user's input is an integer
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Method for the save part button functionality
    public void saveProductButtonClicked(ActionEvent event) throws IOException{
        try {
            int inventory = Integer.parseInt(productInventory.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum cannot be greater than Maximum", ButtonType.OK);
                alert.showAndWait();
            } else if (inventory < min || inventory > max) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory must be between Min and Max", ButtonType.OK);
                alert.showAndWait();
            } else {
                int prodId = getNewProductID();
                String prodName = productName.getText();
                double prodPrice = Double.parseDouble(productPrice.getText());
                Products p = new Products(prodId, inventory, min, max, prodName, prodPrice);
                for (Parts part: tempAssociatedPartsList) {
                    p.addAssociatedPart(part);
                }
                Inventory.addProduct(p);


            }
            Parent mainScreenWindow = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error adding product. Please check all fields for correct input.", ButtonType.OK);
            alert.showAndWait();

        }

    }

    //Method for the functionality when a user clicks the cancel product button
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

    //Method for assigning a productID to a newly added product
    public int getNewProductID(){
        this.productID = 1;
        for (int i = 0; i < getAllProducts().size(); i++){
            productID++;
        }
        return productID;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
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
