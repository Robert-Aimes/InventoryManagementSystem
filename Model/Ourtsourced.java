package com.example.inventory_management_system;

public class Outsourced extends Parts{

    private String companyName;
    public Outsourced(int partID, int stock, int min, int max, String name, double partCost, String companyName) {
        super(partID, stock, min, max, name, partCost);

        this.companyName = companyName;
    }

    public String getCompanyName(){
        return this.companyName;
    }

    public void setCompanyName(){
        this.companyName = companyName;
    }
}
