package com.example.lab2_bhautikpethani_c0854487_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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


    public void onDetailViewPressed(View view) {
        startActivity(new Intent(this, ProductView.class));
    }

    public void onAddButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.add_new_product_dialog, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText txtPName = dialogView.findViewById(R.id.txtPName);
        final EditText txtPDesc = dialogView.findViewById(R.id.txtPDesc);
        final EditText txtPPrice = dialogView.findViewById(R.id.txtPPrice);

        dialogView.findViewById(R.id.btnAdd).setOnClickListener(v -> {
            String name = txtPName.getText().toString().trim();
            String description = txtPDesc.getText().toString().trim();
            String price = txtPPrice.getText().toString().trim();

            if (name.isEmpty()) {
                txtPName.setError("product name field can't be empty");
                txtPName.requestFocus();
                return;
            }
            if (description.isEmpty()) {
                txtPDesc.setError("product description can't be empty");
                txtPDesc.requestFocus();
                return;
            }
            if (price.isEmpty()) {
                txtPPrice.setError("product price can't be empty");
                txtPPrice.requestFocus();
                return;
            }
            if(!isNumeric(price)){
                txtPPrice.setError("product price should be numeric");
                txtPPrice.requestFocus();
                return;
            }

            if(dbHelper.addNewProduct(new Product(-1, name, description, Double.parseDouble(price)))){
                Toast toast = Toast.makeText(getApplicationContext(),
                        name+" has been added to products list.",
                        Toast.LENGTH_LONG);

                toast.show();
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        name+" couldn't add to product list.",
                        Toast.LENGTH_LONG);

                toast.show();
            }

            alertDialog.dismiss();
        });
    }

    public static boolean isNumeric(String string) {
        Double intValue;

        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }
}