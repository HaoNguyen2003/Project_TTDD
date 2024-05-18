package com.example.myapplication.Model;
import java.io.Serializable;
import java.sql.Date;
import java.time.DateTimeException;
import java.util.ArrayList;

public class Product implements Serializable {
    public int ProductID;
    public int BrandID;
    public int CategoryID;
    public String ProductName;
    public String ProductTitle;
    public double Price;
    public String PriceIn;
    public String Description;
    public double Discount;
    public String CreateAt;
    public String UpdateAt;
    public ArrayList<String> listURL = new ArrayList<>();
    public int Viewer;
    public int Active;

    public Product(){}
    public Product(int productID, int brandID,
                   int categoryID, String productName,
                   String productTitle, double price,
                   String priceIn, String description,
                   double discount, String createAt,
                   String updateAt, ArrayList<String> listURL,
                   int viewer, int active) {
        ProductID = productID;
        BrandID = brandID;
        CategoryID = categoryID;
        ProductName = productName;
        ProductTitle = productTitle;
        Price = price;
        PriceIn = priceIn;
        Description = description;
        Discount = discount;
        CreateAt = createAt;
        UpdateAt = updateAt;
        this.listURL = listURL;
        Viewer = viewer;
        Active = active;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int brandID) {
        BrandID = brandID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getPriceIn() {
        return PriceIn;
    }

    public void setPriceIn(String priceIn) {
        PriceIn = priceIn;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
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

    public ArrayList<String> getListURL() {
        return listURL;
    }

    public void setListURL(ArrayList<String> listURL) {
        this.listURL = listURL;
    }

    public int getViewer() {
        return Viewer;
    }

    public void setViewer(int viewer) {
        Viewer = viewer;
    }

    public int isActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductID=" + ProductID +
                ", BrandID=" + BrandID +
                ", CategoryID=" + CategoryID +
                ", ProductName='" + ProductName + '\'' +
                ", ProductTitle='" + ProductTitle + '\'' +
                ", Price=" + Price +
                ", PriceIn='" + PriceIn + '\'' +
                ", Description='" + Description + '\'' +
                ", Discount=" + Discount +
                ", CreateAt=" + CreateAt +
                ", UpdateAt=" + UpdateAt +
                ", listURL=" + listURL +
                ", Viewer=" + Viewer +
                ", Active=" + Active +
                '}';
    }
}
