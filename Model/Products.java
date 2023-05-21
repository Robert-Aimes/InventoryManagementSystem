package com.example.inventory_management_system;

import com.example.inventory_management_system.Parts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Products {
    private ObservableList<Parts> productPartsList = FXCollections.observableArrayList();

    static ObservableList<Parts> associatedPartList = FXCollections.observableArrayList();
    private int productID, stock, min, max;
    private String name;
    private double productPrice;

    public Products(int productID, int stock, int min, int max, String name, double productPrice){
        this.productID = productID;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.productPrice = productPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public static void addAssociatedPart(Parts associatedPart){
        associatedPartList.add(associatedPart);
    }

    public static void removeAssociatedPart(Parts associatedPart){
        associatedPartList.remove(associatedPart);
    }
    public static ObservableList<Parts> getAllAssociatedParts(){
        return associatedPartList;
    }





}
