package com.example.lab2_bhautikpethani_c0854487_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab2_bhautikpethani_c0854487_android.models.Product;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

import java.util.ArrayList;

public class ProdAdapter extends BaseAdapter {

    private ArrayList<Product> prodList;
    private LayoutInflater inflater;

    public ProdAdapter(Context context, ArrayList<Product> prodList)
    {
        this.prodList=prodList;
        inflater=LayoutInflater.from(context);
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
        return view;
    }

    static class ViewHolder{
        private TextView prod_name,prod_price,prod_desc;
        private Button edit_details, delete_details;
    }
}
