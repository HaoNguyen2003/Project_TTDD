package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductInOrderAdapter extends RecyclerView.Adapter<ProductInOrderAdapter.ProductHolder> {

    public ArrayList<Cart> list_Carts;

    public ProductInOrderAdapter(ArrayList<Cart> list_Carts) {
        this.list_Carts = list_Carts;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_order,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Cart cart=list_Carts.get(position);
        Picasso.get().load(cart.getImg()).into(holder.imageProduct);
        holder.Name.setText(cart.productName);
        holder.textView_title.setText(cart.getProductTitle());
        holder.size.setText("Size: "+cart.size);
        holder.textView_price.setText("Price: "+cart.getPrice_out());
        holder.textViewtotal.setText("Price: "+cart.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return list_Carts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{

        ImageView imageProduct;
        TextView Name;
        TextView textView_title;
        TextView textView_price;
        TextView size;
        TextView textViewtotal;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            Name = itemView.findViewById(R.id.Name);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_price = itemView.findViewById(R.id.textView_price);
            size = itemView.findViewById(R.id.size);
            textViewtotal = itemView.findViewById(R.id.textViewtotal);
        }
    }
}
