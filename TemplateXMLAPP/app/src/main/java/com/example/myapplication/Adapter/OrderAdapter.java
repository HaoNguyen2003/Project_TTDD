package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Order;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{
    ArrayList<Order>list_Orders=new ArrayList<Order>();
    listenerOrder listenerOrder;

    public void setListenerOrder(OrderAdapter.listenerOrder listenerOrder) {
        this.listenerOrder = listenerOrder;
    }

    public OrderAdapter(ArrayList<Order> list_Orders) {
        this.list_Orders = list_Orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhoder_order,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order=list_Orders.get(position);
        holder.textView_date.setText("Ngày Đặt Hàng\n"+order.getCreateDay());
        holder.textView_price.setText("Giá tiền: "+order.totalPrice);
        Picasso.get().load(order.URLImage).into(holder.imageView_avatar);
    }

    @Override
    public int getItemCount() {
        if(list_Orders.isEmpty()||list_Orders==null)
        {
            return 0;
        }
        return list_Orders.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder{

        TextView textView_date;
        TextView textView_price;
        ImageView imageView_See;
        ImageView imageView_avatar;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            textView_date=itemView.findViewById(R.id.textView_date);
            textView_price=itemView.findViewById(R.id.textView_price);
            imageView_See=itemView.findViewById(R.id.imageView_See);
            imageView_avatar=itemView.findViewById(R.id.imageView_avatar);
            imageView_See.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order=list_Orders.get(getAbsoluteAdapterPosition());
                    listenerOrder.seeDetailOrder(order);
                }
            });
        }
    }
    public interface listenerOrder
    {
        public void seeDetailOrder(Order order);
    }
}
