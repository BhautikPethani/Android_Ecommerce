package com.example.lab2_bhautikpethani_c0854487_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.lab2_bhautikpethani_c0854487_android.databinding.ActivityMainBinding;
import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        insertCustomRecordsToDB();
        showFirstProductsDetails();
    }

    private void showFirstProductsDetails(){
        Product product = dbHelper.getFirstProduct();
        binding.prodName.setText(product.getName());
        binding.prodDesc.setText(product.getDescription());
        binding.prodPrice.setText(String.valueOf(product.getPrice()));
    }

    private void insertCustomRecordsToDB(){
        if(!dbHelper.checkDBHasDataOrNot()){

            List<Product> products = new ArrayList<Product>();

            products.add(new Product(-1, "Apples", "The fresh fruit ever. The description of Apples.", 4.2));
            products.add(new Product(-1, "Bananas", "The description of bananas.", 1.2));
            products.add(new Product(-1, "Oranges & citrus", "The description of Oranges & citrus.", 3.5));
            products.add(new Product(-1, "Grapes", "The description of Grapes.", 3.1));
            products.add(new Product(-1, "Berries & Cherries", "The description of Berries & Cherries.", 5.3));
            products.add(new Product(-1, "Avocados", "The description of Avocados.", 7.2));
            products.add(new Product(-1, "Pears", "The description of Pears.", 2.4));
            products.add(new Product(-1, "Tomatoes", "The description of Tomatoes.", 4.5));
            products.add(new Product(-1, "Celery", "The description of Celery.", 3.8));
            products.add(new Product(-1, "Broccoli", "The description of Broccoli.", 3.42));

            for (Product prod: products) {
                dbHelper.addNewProduct(prod);
            }
        }
    }


}