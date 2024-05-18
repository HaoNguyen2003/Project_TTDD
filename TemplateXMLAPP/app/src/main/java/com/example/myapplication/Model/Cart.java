package com.example.myapplication.Model;

public class Cart {
    int id;
    public int productID;
    public int AccountID;
    public double price_out;
    public String img;

    public String size;
    public String productName;
    public String ProductTitle;
    public int amount;
    public double totalPrice;

    public Cart(int id, int productID,int accountID, double price_out, String img, String size, String productName, String productTitle, int amount) {
        this.id = id;
        this.productID = productID;
        this.AccountID=accountID;
        this.price_out = price_out;
        this.img = img;
        this.size = size;
        this.productName = productName;
        ProductTitle = productTitle;
        this.amount = amount;
        totalPrice=amount*price_out;
    }

    public Cart(int id, int productID, int accountID, double priceOut, String img, String size, String productName, String productTitle, int amount, double totalPrice) {
        this.id = id;
        this.productID = productID;
        this.AccountID=accountID;
        this.price_out = price_out;
        this.img = img;
        this.size = size;
        this.productName = productName;
        ProductTitle = productTitle;
        this.amount = amount;
        this.totalPrice=totalPrice;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice_out() {
        return price_out;
    }

    public void setPrice_out(double price_out) {
        this.price_out = price_out;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
