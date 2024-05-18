package com.example.myapplication.DataBaseSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Product;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="CTDH.db";
    public final static int DATABASE_VERSION=1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE ="CREATE TABLE Cart(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "productID INTEGER," +
                "AccountID INTEGER," +
                "price_out REAL," +
                "img TEXT," +
                "size TEXT," +
                "productName TEXT," +
                "ProductTitle TEXT," +
                "amount INTEGER," +
                "totalPrice REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Cart> getAllByAccountId(int accountId) {
        ArrayList<Cart> carts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {"id", "productID", "AccountID", "price_out", "img", "size", "productName", "ProductTitle", "amount", "totalPrice"};
        String selection = "AccountID = ?";
        String[] selectionArgs = {String.valueOf(accountId)};
        Cursor cursor = sqLiteDatabase.query("Cart", columns, selection, selectionArgs, null, null, "id");
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int productID = cursor.getInt(1);
            int AccountID = cursor.getInt(2);
            double price_out = cursor.getDouble(3);
            String img = cursor.getString(4);
            String size = cursor.getString(5);
            String productName = cursor.getString(6);
            String ProductTitle = cursor.getString(7);
            int amount = cursor.getInt(8);
            double totalPrice = cursor.getDouble(9);
            carts.add(new Cart(id, productID, AccountID, price_out, img, size, productName, ProductTitle, amount));
        }
        if (cursor != null) {
            cursor.close();
        }
        return carts;
    }
    public long AddToCart(Product product,int AccountID,String Size)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("productID",product.getProductID());
        contentValues.put("AccountID",AccountID);
        double price=product.getPrice()*(1-(product.getDiscount()/100.0));
        contentValues.put("price_out",price);
        contentValues.put("img",product.getListURL().get(0));
        contentValues.put("size",Size);
        contentValues.put("productName",product.getProductName());
        contentValues.put("ProductTitle",product.getProductTitle());
        contentValues.put("amount",1);
        contentValues.put("totalPrice",price);
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("Cart",null,contentValues);
    }
    public void UpdateCart(Product product, int accountID, String size) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT amount FROM Cart WHERE productID = ? AND AccountID = ? AND size = ?",
                new String[]{String.valueOf(product.getProductID()), String.valueOf(accountID), size});
        int currentAmount = 0;
        if (cursor != null && cursor.moveToFirst()) {
            currentAmount = cursor.getInt(0);
            cursor.close();
        }
        ContentValues values = new ContentValues();
        values.put("amount", currentAmount + 1);
        db.update("Cart", values, "productID = ? AND AccountID = ? AND size = ?",
                new String[]{String.valueOf(product.getProductID()), String.valueOf(accountID), size});
        double price = product.getPrice() * (1 - (product.getDiscount() / 100.0));
        double totalPrice = (currentAmount + 1) * price;
        ContentValues totalPriceValues = new ContentValues();
        totalPriceValues.put("totalPrice", totalPrice);
        db.update("Cart", totalPriceValues, "productID = ? AND AccountID = ? AND size = ?",
                new String[]{String.valueOf(product.getProductID()), String.valueOf(accountID), size});
        db.close();
    }
    public void plusCart(int id, int amount, double price) {
        SQLiteDatabase db = getWritableDatabase();
        double totalPrice = amount * price;
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("totalPrice", totalPrice);
        db.update("Cart", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void minusCart(int id, int amount, double price) {
        SQLiteDatabase db = getWritableDatabase();
        double totalPrice = amount * price;
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("totalPrice", totalPrice);
        db.update("Cart", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void removeFromCart(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("Cart", whereClause, whereArgs);
        db.close();
    }
    public void removeProductInCart(int productID) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "productID = ?";
        String[] whereArgs = {String.valueOf(productID)};
        db.delete("Cart", whereClause, whereArgs);
        db.close();
    }
    public int getCartID(int productID, int accountID, String size) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        int cartID = -1;
        try {
            String selection = "productID = ? AND AccountID = ? AND size = ?";
            String[] selectionArgs = {String.valueOf(productID), String.valueOf(accountID), size};
            cursor = db.query("Cart", new String[]{"id"}, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                cartID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return cartID;
    }

    public void UpdatePrice(int IDProduct,double price)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price_out", price);
        db.update("Cart", values, "productID = ?", new String[] { String.valueOf(IDProduct) });
        db.execSQL("UPDATE Cart SET totalPrice = price_out * amount WHERE productID = ?", new Object[]{IDProduct});
        db.close();
    }
}
