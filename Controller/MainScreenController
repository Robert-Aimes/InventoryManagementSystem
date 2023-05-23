package com.example.inventory_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.imageio.IIOParam;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class MainScreenController implements Initializable{

    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button addProductButton;

    @FXML private Button modifyProductButton;
    @FXML private Button exitButton;
    @FXML private Button deletePart;
    @FXML private Button deleteProduct;
    @FXML private Button searchPartButton;
    @FXML private Button searchProductButton;


    //Part Table
    @FXML private TableView<Parts> partsTableView;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Parts, Double> partPriceColumn;
    @FXML private TextField partSearchField;

    //Product Table
    @FXML private TableView<Products> productsTableView;
    @FXML private TableColumn<Products, Integer> productIDColumn;
    @FXML private TableColumn<Products, String> productNameColumn;
    @FXML private TableColumn<Products, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Products, Double> productPriceColumn;
    @FXML private TextField productSearchField;
    private FXMLLoader loader;


    //Method to change display scene to Add Part screen when Add part button clicked

    public void addPartButtonClicked(ActionEvent event) throws IOException {
        Parent addPartWindow = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartWindow);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    //Method to change display scene to Modify Part screen when Modify part button clicked
    //RUNTIME ERROR: Code wouldn't compile at this method when working on the functionality to save selectedPart to transition to the ModifyPart view. I had to update the FXML loader code and the .fxml resource to correct it.
    public void modifyPartButtonClicked(ActionEvent event) throws IOException {
        try {
            Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a Part from the Parts table.", ButtonType.OK);
                alert.showAndWait();
            } else {

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
                Parent scene = loader.load();
                ModifyPartController controller = loader.getController();
                controller.setParts(selectedPart);
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(scene));
                stage.show();

            }

        } finally {

        }

    }

    //Method to filter the parts tableview based on the input given in the parts search bar
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

    //Method to filter the product tableview based on the input given in the product search bar
    public void productSearchButtonClicked(){
        String input = productSearchField.getText();

        if(isInteger(input)){
            int inputId = Integer.parseInt(input);
            ObservableList<Products> filteredIDList = FXCollections.observableArrayList();
            for(Products products : Inventory.getAllProducts()) {
                if (products.getProductID() == inputId) {
                    filteredIDList.add(products);
                }
            }
            productsTableView.setItems(filteredIDList);

            if(productsTableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Product found. Please try a different ID", ButtonType.OK);
                alert.showAndWait();
            }

        } else if(input instanceof String){

            ObservableList<Products> filteredNameList = FXCollections.observableArrayList();
            for(Products products : Inventory.getAllProducts()) {
                if(products.getName().toLowerCase().contains(((String) input).toLowerCase())){
                    filteredNameList.add(products);
                }
            }
            productsTableView.setItems(filteredNameList);

            if(productsTableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Product found. Please try a different Name", ButtonType.OK);
                alert.showAndWait();
            }

        } else{
            productsTableView.setItems(Inventory.productList);
        }
    }

    //Method to check if a user's input is of type Integer
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    //Method to change display scene to Add Product screen when Add Product button clicked
    public void addProductButtonClicked(ActionEvent event) throws IOException {
        Parent addProductWindow = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductWindow);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }
    //Method to change display scene to Modify Product screen when Modify product button clicked
    public void modifyProductButtonClicked(ActionEvent event) throws IOException {

        try {
            Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a Product from the Products table.", ButtonType.OK);
                alert.showAndWait();
            } else {

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                Parent scene = loader.load();
                ModifyProductController controller = loader.getController();
                controller.setProduct(selectedProduct);
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(scene));
                stage.show();

            }

        } finally {

        }



    }
    //Method for pressing the Delete Part Button
    public void deletePartButtonClicked(ActionEvent event) throws IOException{
        Parts selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a Part from the Parts table.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this Part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES){
                Inventory.removePart(selectedPart);
            }

        }


        }

    //Method for pressing the Delete Product Button
    public void deleteProductButtonClicked(ActionEvent event) throws IOException{
        Products selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a Product from the Products table.", ButtonType.OK);
            alert.showAndWait();
        } else if(selectedProduct.getAllAssociatedParts().size() > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected Product has associated parts. Please remove all associated parts before deleting this Product", ButtonType.OK);
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this Product?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Inventory.removeProduct(selectedProduct);
            }
        }

    }

    //Method to terminate application upon clicking the Exit button
    public void exitButtonClicked(ActionEvent event) throws IOException {
        Platform.exit();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Parts, Double>("partCost"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Products, Double>("productPrice"));

    }
}
