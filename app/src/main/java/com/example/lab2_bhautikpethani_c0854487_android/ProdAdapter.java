package com.example.lab2_bhautikpethani_c0854487_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;
import com.example.lab2_bhautikpethani_c0854487_android.service.Utilities;

import java.util.ArrayList;

public class ProdAdapter extends BaseAdapter {

    private ArrayList<Product> prodList;
    Context context;
    private LayoutInflater inflater;
    DatabaseHelper dbHelper;

    public ProdAdapter(Context context, ArrayList<Product> prodList)
    {
        this.prodList=prodList;
        inflater=LayoutInflater.from(context);

        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return prodList.size();
    }

    @Override
    public Object getItem(int i) {
        return prodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.product_list,null);
            holder=new ViewHolder();
            holder.prod_name=view.findViewById(R.id.product_name);
            holder.prod_price=view.findViewById(R.id.product_price);
            holder.prod_desc=view.findViewById(R.id.product_desc);
            holder.edit_details=view.findViewById(R.id.btn_edit);
            holder.delete_details=view.findViewById(R.id.btn_delete);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.prod_name.setText(prodList.get(i).getName());
        holder.prod_price.setText(String.valueOf(prodList.get(i).getPrice()));
        holder.prod_desc.setText(prodList.get(i).getDescription());

        holder.edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct(prodList.get(i));
            }

            private void updateProduct(final Product product) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View dialogView = layoutInflater.inflate(R.layout.add_new_product_dialog, null);
                builder.setView(dialogView);
                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText txtPName = dialogView.findViewById(R.id.txtPName);
                final EditText txtPDesc = dialogView.findViewById(R.id.txtPDesc);
                final EditText txtPPrice = dialogView.findViewById(R.id.txtPPrice);
                final Button btnUpdate = dialogView.findViewById(R.id.btnAdd);

                txtPName.setText(prodList.get(i).getName());
                txtPDesc.setText(prodList.get(i).getDescription());
                txtPPrice.setText(String.valueOf(prodList.get(i).getPrice()));

                btnUpdate.setText("UPDATE");

                btnUpdate.setOnClickListener(v -> {
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
                    if(!Utilities.isNumeric(price)){
                        txtPPrice.setError("product price should be numeric");
                        txtPPrice.requestFocus();
                        return;
                    }

                    if(dbHelper.updateProduct(new Product(prodList.get(i).getId(), name, description, Double.parseDouble(price)))){
                        Toast toast = Toast.makeText(context,
                                name+" has been updated to products list.",
                                Toast.LENGTH_LONG);

                        toast.show();
                        refreshProductList();
                    }else{
                        Toast toast = Toast.makeText(context,
                                name+" couldn't updated to product list.",
                                Toast.LENGTH_LONG);

                        toast.show();
                    }

                    alertDialog.dismiss();
                });
            }
        });

        holder.delete_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(prodList.get(i));
            }

            private void deleteProduct(final Product product) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.deleteProduct(product.getId())){
                            Toast.makeText(context, product.getName() + " has been deleted", Toast.LENGTH_SHORT).show();
                            refreshProductList();
                        }else{
                            Toast.makeText(context, product.getName() + " couldn't delete", Toast.LENGTH_SHORT).show();
                        };

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, product.getName() + " couldn't delete", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void refreshProductList() {
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor.moveToFirst()) {
            prodList.clear();
            do {
                // create an employee instance
                prodList.add(new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        notifyDataSetChanged();
    }

    static class ViewHolder{
        private TextView prod_name,prod_price,prod_desc;
        private Button edit_details, delete_details;
    }
}
