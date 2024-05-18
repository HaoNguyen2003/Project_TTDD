package com.example.myapplication.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    public int OrderID;
    public int AccountID;
    public String CustomerName;
    public String PhoneNumber;
    public String Address;
    public String CreateDay;
    public double totalPrice;
    public int checkOrder;
    public String URLImage;
    public ArrayList<Cart>carts=new ArrayList<Cart>();

    public Order(int orderID, int accountID, String customerName, String phoneNumber, String address, String createDay,double price,String URLImage,ArrayList<Cart> carts) {
        OrderID = orderID;
        AccountID = accountID;
        CustomerName = customerName;
        PhoneNumber = phoneNumber;
        Address = address;
        CreateDay = createDay;
        this.totalPrice=price;
        this.carts = carts;
        this.URLImage=URLImage;
    }

    public Order(int orderID, int accountID, String customerName, String phoneNumber, String address, String createDay, double totalPrice, int checkOrder,String URLImage) {
        OrderID = orderID;
        AccountID = accountID;
        CustomerName = customerName;
        PhoneNumber = phoneNumber;
        Address = address;
        CreateDay = createDay;
        this.totalPrice = totalPrice;
        this.checkOrder = checkOrder;
        this.URLImage=URLImage;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCreateDay() {
        return CreateDay;
    }

    public void setCreateDay(String createDay) {
        CreateDay = createDay;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }
}
