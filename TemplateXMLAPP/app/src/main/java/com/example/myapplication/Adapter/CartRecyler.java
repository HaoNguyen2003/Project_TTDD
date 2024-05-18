package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Cart;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartRecyler extends RecyclerView.Adapter<CartRecyler.CartHolder>{

    ArrayList<Cart>list_Carts;
    Context context;
    optionCart optionCart;

    public void setOptionCart(CartRecyler.optionCart optionCart) {
        this.optionCart = optionCart;
    }

    public CartRecyler(ArrayList<Cart> list_Carts, Context context) {
        this.list_Carts = list_Carts;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewholder_cart,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        int index=position;
        Cart cart=list_Carts.get(position);
        holder.Name.setText(cart.productName);
        holder.textView_title.setText(cart.ProductTitle);
        Picasso.get().load(cart.getImg()).into(holder.imageProduct);
        holder.amount.setText(String.valueOf(cart.amount));
        holder.textView_price.setText("$" + String.valueOf(cart.getPrice_out()));
        holder.textViewtotal.setText("total: " + String.valueOf(cart.totalPrice));
        holder.size.setText("Size: "+cart.size);
    }

    @Override
    public int getItemCount() {
        if(list_Carts!=null)
            return list_Carts.size();
        return 0;
    }
    public double GetPriceTotal()
    {
        double PriceTotal=0;
        for (Cart c:list_Carts)
        {
            PriceTotal+=c.totalPrice;
        }
        return PriceTotal;
    }
    public class CartHolder extends RecyclerView.ViewHolder{

        ImageView imageProduct;
        TextView Name;
        TextView textView_title;
        TextView textView_price;
        TextView textViewtotal;
        TextView textView_down;
        TextView textView_up;
        TextView amount;
        TextView size;
        ImageView imageView_trash;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct=itemView.findViewById(R.id.imageProduct);
            Name=itemView.findViewById(R.id.Name);
            textView_title=itemView.findViewById(R.id.textView_title);
            textView_price=itemView.findViewById(R.id.textView_price);
            textViewtotal=itemView.findViewById(R.id.textViewtotal);
            textView_down=itemView.findViewById(R.id.textView_down);
            textView_up=itemView.findViewById(R.id.textView_up);
            amount=itemView.findViewById(R.id.amount);
            size=itemView.findViewById(R.id.size);
            imageView_trash = itemView.findViewById(R.id.imageView_trash);

            imageView_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart=list_Carts.get(getAdapterPosition());
                    optionCart.deleteCart(cart.getId());
                }
            });

            textView_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart=list_Carts.get(getAdapterPosition());
                    int amount= cart.amount+1;
                    optionCart.plusCart(cart.getId(),amount,cart.price_out);
                }
            });
            textView_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart=list_Carts.get(getAdapterPosition());
                    int amount= cart.amount-1;
                    if(amount==0)
                        amount=1;
                    optionCart.minusCart(cart.getId(),amount,cart.price_out);
                }
            });
        }
    }
    public void filter(ArrayList<Cart>list_Carts)
    {
        this.list_Carts.clear();
        this.list_Carts=list_Carts;
        notifyDataSetChanged();
    }
    public interface optionCart{
        public void deleteCart(int Id);
        public void plusCart(int Id,int amount,double price);
        public void minusCart(int Id,int amount,double price);
    }
}
