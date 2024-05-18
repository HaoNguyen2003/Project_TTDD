package com.example.myapplication.Model;

import java.io.Serializable;

public class Size implements Serializable {
    public int SizeID;
    public String SizeName;

    public Size(int sizeID, String sizeName) {
        SizeID = sizeID;
        SizeName = sizeName;
    }

    public int getSizeID() {
        return SizeID;
    }

    public void setSizeID(int sizeID) {
        SizeID = sizeID;
    }

    public String getSizeName() {
        return SizeName;
    }

    public void setSizeName(String sizeName) {
        SizeName = sizeName;
    }
}
