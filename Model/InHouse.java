package com.example.inventory_management_system;

public class InHouse extends Parts{

    private int machineID;
    public InHouse(int partID, int stock, int min, int max, String name, double partCost, int machineID) {
        super(partID, stock, min, max, name, partCost);

        this.machineID = machineID;
    }

    public int getMachineID(){
        return this.machineID;
    }

    public void setMachineID(){
        this.machineID = this.machineID;
    }
}
