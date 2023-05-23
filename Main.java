package com.example.inventory_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {

        //Add Parts InHouse
        Inventory.addPart(new InHouse(1, 20, 1, 50, "V6 Engine", 5000, 1));
        Inventory.addPart(new InHouse(2, 25, 1, 50, "v4 Engine", 3000, 12));


        //Add Parts OutSourced
        Inventory.addPart(new Outsourced(5, 10, 1, 50, "High performance Bumper", 39.99, "Bumper Company"));
        Inventory.addPart(new Outsourced(6, 5, 1, 50, "High performance brakes", 2000, "Brake Company"));

        //Add Products
        Inventory.addProduct(new Products(1, 10, 1, 50, "SUV", 15000));
        Inventory.addProduct(new Products(2, 8, 1, 50, "Mid-sized Sedan", 10000));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    //FUTURE FEATURE ENHANCEMENT: If I could implement a feature enhancement to this project, it would be to be able to select multiple parts in the add product or modify product windows to add them to the associated parts list. Currently, a user can only select and add one part at a time.
    public static void main(String[] args) {
        launch();
    }

