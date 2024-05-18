package com.example.myapplication.Model;

public class ContentItem {
    public int ID;
    public String ImageURL;
    public String linkURL;

    public ContentItem(){}
    public ContentItem( int ID,String imageURL, String linkURL) {
        this.ID = ID;
        ImageURL = imageURL;
        this.linkURL = linkURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }
}
