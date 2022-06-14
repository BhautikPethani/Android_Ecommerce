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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

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

        holder.delete_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(prodList.get(i));
            }

            private void deleteEmployee(final Product product) {
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
