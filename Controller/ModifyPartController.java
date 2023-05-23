package com.example.inventory_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.inventory_management_system.Inventory.getAllParts;

public class ModifyPartController {

    @FXML
    private RadioButton outsourcedModifyPart;
    @FXML private RadioButton inHouseModifyPart;
    @FXML private Label label;
    @FXML private TextField partID;
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField inhouseOutsourcedField;

    @FXML private Button cancelModifyPartButton;

    private Stage stage;
    private Object scene;
    private Parts selectedPart;
    private int id;

    public void radioAction(){
        if(inHouseModifyPart.isSelected()){
            label.setText("Machine ID");
        } else if(outsourcedModifyPart.isSelected()){
            label.setText("Company Name");
        }
    }

    //Method that populates all part data from main screen into proper fields

    public void setParts(Parts selectedPart) {
        this.selectedPart = selectedPart;
        id = getAllParts().indexOf(selectedPart);
        partID.setText(Integer.toString(selectedPart.getPartID()));
        Name.setText(selectedPart.getName());
        Inventory.setText(Integer.toString(selectedPart.getStock()));
        Price.setText(Double.toString(selectedPart.getPartCost()));
        Minimum.setText(Integer.toString(selectedPart.getMin()));
        Maximum.setText(Integer.toString(selectedPart.getMax()));
        if(selectedPart instanceof InHouse){
            InHouse inhouse = (InHouse) selectedPart;
            inHouseModifyPart.setSelected(true);
            this.label.setText("Machine ID");
            inhouseOutsourcedField.setText(Integer.toString(inhouse.getMachineID()));
        } else{
            Outsourced outsourced = (Outsourced) selectedPart;
            outsourcedModifyPart.setSelected(true);
            this.label.setText("Company Name");
            inhouseOutsourcedField.setText(outsourced.getCompanyName());
        }
    }

    //Need to add Save Button functionality

    public void savePartButtonClicked(ActionEvent event) throws IOException{
        try{
            int partInventory = Integer.parseInt(Inventory.getText());
            int partMin = Integer.parseInt(Minimum.getText());
            int partMax = Integer.parseInt(Maximum.getText());
            if(partMax < partMin){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Maximum cannot be greater than Minimum", ButtonType.OK);
                alert.showAndWait();
            } else if(partInventory < partMin || partInventory> partMax){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Inventory must be between Min and Max", ButtonType.OK);
                alert.showAndWait();
            }

            else{
                int ID = Integer.parseInt(partID.getText());
                String name = Name.getText();
                int stock = partInventory;
                double price = Double.parseDouble(Price.getText());
                int min = partMin;
                int max = partMax;
                if(outsourcedModifyPart.isSelected()){
                    String companyName = inhouseOutsourcedField.getText();
                    Outsourced update = new Outsourced(ID, stock, min, max, name, price, companyName);
                    com.example.inventory_management_system.Inventory.updatePart(id, update);
                } else{
                    int machineID = Integer.parseInt(inhouseOutsourcedField.getText());
                    InHouse update = new InHouse(ID, stock, min, max, name, price, machineID);
                    com.example.inventory_management_system.Inventory.updatePart(id, update);
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
}
