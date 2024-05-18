package com.example.myapplication.Model;

import java.io.Serializable;

public class Account implements Serializable {
    public int AccountID;
    public String Username;
    public String Password;
    public String Email;
    public String Phonenumber;
    public String Address;
    public int Access;
    public String CreateAt;
    public String UpdateAt;
    public int MissionID;

    public String ImageURL;
    public Account() {}
    public Account(int accountID, String username,
                   String password, String email,
                   String phonenumber, String address,
                   int access, String createAt,
                   String updateAt,int MissionID,String imageURL) {
        AccountID = accountID;
        Username = username;
        Password = password;
        Email = email;
        Phonenumber = phonenumber;
        Address = address;
        Access = access;
        CreateAt = createAt;
        UpdateAt = updateAt;
        this.MissionID=MissionID;
        this.ImageURL=imageURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int isAccess() {
        return Access;
    }

    public void setAccess(int access) {
        Access = access;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        UpdateAt = updateAt;
    }

    public int getMissionID() {
        return MissionID;
    }

    public void setMissionID(int missionID) {
        MissionID = missionID;
    }

    @Override
    public String toString() {
        return "Account{" +
                "AccountID=" + AccountID +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", Phonenumber='" + Phonenumber + '\'' +
                ", Address='" + Address + '\'' +
                ", Access=" + Access +
                ", CreateAt='" + CreateAt + '\'' +
                ", UpdateAt='" + UpdateAt + '\'' +
                ", MissionID=" + MissionID +
                ",IMG="+ImageURL+
                '}';
    }
}
