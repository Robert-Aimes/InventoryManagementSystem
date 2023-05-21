package com.example.inventory_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    static ObservableList<Products> productList = FXCollections.observableArrayList();
    static ObservableList<Parts> partsList = FXCollections.observableArrayList();




    public static void addPart(Parts newPart){
        partsList.add(newPart);
    }

    public static ObservableList<Parts> getAllParts(){
        return partsList;
    }

    public static void addProduct(Products newProduct){
        productList.add(newProduct);
    }

    public static ObservableList<Products> getAllProducts(){
        return productList;
    }


    public static void updatePart(int index, Parts selectedPart){
        partsList.set(index, selectedPart);
    }

    public void updateProduct(int index, Products selectedProduct){
        productList.set(index, selectedProduct);
    }

    public static void removePart(Parts selectedPart){
        partsList.remove(selectedPart);
    }

    public static void removeProduct(Products selectedProduct){
        productList.remove(selectedProduct);
    }

}
