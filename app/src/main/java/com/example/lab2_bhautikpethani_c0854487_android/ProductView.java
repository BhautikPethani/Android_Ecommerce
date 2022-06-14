package com.example.lab2_bhautikpethani_c0854487_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity {

    ArrayList<Product> productList;
    ListView productListView;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        dbHelper = new DatabaseHelper(this);
        productListView = findViewById(R.id.productListView);
        productList = new ArrayList<Product>();

        loadAllProducts();
        productListView.setAdapter(new ProdAdapter(this,productList));
    }

    private void loadAllProducts(){
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor.moveToFirst()) {
            do {
                // create an employee instance
                productList.add(new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}