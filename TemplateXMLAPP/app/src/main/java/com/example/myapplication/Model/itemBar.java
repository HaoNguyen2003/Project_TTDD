package com.example.myapplication.Model;

public class itemBar {
    public String ItemName;
    public int amount;

    public itemBar(String itemName, int amount) {
        ItemName = itemName;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "itemBar{" +
                "ItemName='" + ItemName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
