package com.example.lab2_bhautikpethani_c0854487_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity {

    ArrayList<Product> productList;
    ListView productListView;
    EditText txtSearch;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        dbHelper = new DatabaseHelper(this);
        productListView = findViewById(R.id.productListView);
        txtSearch = findViewById(R.id.txtSearch);

        productList = new ArrayList<Product>();

        loadAllProducts();
        productListView.setAdapter(new ProdAdapter(this,productList));

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchByKeyword(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void searchByKeyword(String keyword){
        Cursor cursor = dbHelper.searchProducts(keyword);
        if (cursor.moveToFirst()) {
            productList.removeAll(productList);
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