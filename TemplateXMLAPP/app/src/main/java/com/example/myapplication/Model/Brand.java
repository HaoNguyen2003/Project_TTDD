package com.example.myapplication.Model;

public class Brand {
    public int brandID;
    public String brandName;
    public String imageURL;

    public Brand(int brandID, String brandName, String imageURL) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.imageURL = imageURL;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "testBrand{" +
                "brandID='" + brandID + '\'' +
                ", brandName='" + brandName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
