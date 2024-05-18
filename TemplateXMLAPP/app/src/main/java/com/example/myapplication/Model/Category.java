package com.example.myapplication.Model;

public class Category {
    public int categoryID;
    public String categoryName;
    public String imageURL;

    public Category(int categoryID, String categoryName, String imageURL) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.imageURL = imageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
