package com.example.inventory_management_system;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.inventory_management_system.Inventory.getAllParts;

public class AddPartController implements Initializable {

    @FXML private RadioButton outsourcedAddPart;
    @FXML private RadioButton inHouseAddPart;
    @FXML private Label label;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInventory;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMaximum;
    @FXML private TextField addPartMinimum;
    @FXML private TextField inhouseOutsourcedField;
    @FXML private Button savePartButton;
    @FXML private Button cancelPartButton;






    //Method to control the label text based on which radio button is selected
    public void radioAction(ActionEvent Event) {
        if(inHouseAddPart.isSelected()){
            label.setText("Machine ID");
        } else if(outsourcedAddPart.isSelected()){
            label.setText("Company Name");
        }
    }
    //Method for when save button is pressed
    public void savePartButtonClicked(ActionEvent event) throws IOException {
        try{
            int partInventory = Integer.parseInt(addPartInventory.getText());
            int partMin = Integer.parseInt(addPartMinimum.getText());
            int partMax = Integer.parseInt(addPartMaximum.getText());
            if(partMax < partMin){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum cannot be greater than Maximum", ButtonType.OK);
                alert.showAndWait();
            } else if(partInventory < partMin || partInventory> partMax){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory must be between Min and Max", ButtonType.OK);
                alert.showAndWait();
            }

            else{
                int newID = getNewID();
                String name = addPartName.getText();
                int stock = partInventory;
                double price = Double.parseDouble(addPartPrice.getText());
                int min = partMin;
                int max = partMax;
                if(outsourcedAddPart.isSelected()){
                    String companyName = inhouseOutsourcedField.getText();
                    Outsourced part = new Outsourced(newID, stock, min, max, name, price, companyName);
                    Inventory.addPart(part);
                } else{
                    int machineID = Integer.parseInt(inhouseOutsourcedField.getText());
                    InHouse part = new InHouse(newID, stock, min, max, name, price, machineID);
                    Inventory.addPart(part);
                }
                Parent mainScreenWindow = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenWindow);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error adding part. Please check all fields for correct input.", ButtonType.OK);
            alert.showAndWait();
        }


    }

    public void cancelPartButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to Cancel?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Parent mainScreenWindow = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenWindow);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }

    }

    public static int getNewID(){
        int partID = 1;
        for(int i = 0; i < getAllParts().size(); i++){
            partID++;
        }
        return partID;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
