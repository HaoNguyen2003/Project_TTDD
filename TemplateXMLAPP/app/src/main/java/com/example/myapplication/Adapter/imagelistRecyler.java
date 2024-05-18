package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class imagelistRecyler extends RecyclerView.Adapter<imagelistRecyler.imageViewHolder>{
    public ArrayList<String>list;
    Context context;
    imageOnClick imageListener;
    public int lastSelect=-1;
    public int Select=-1;

    public void setImageListener(imageOnClick imageListener) {
        this.imageListener = imageListener;
    }

    public imagelistRecyler(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewholder_image,parent,false);
        return new imageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {
        String img = list.get(position);
        Picasso.get().load(img).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView selectImage;
        ImageView imageView;
        ImageView tick;
        public imageViewHolder(@NonNull View itemView) {
            super(itemView);
            selectImage=itemView.findViewById(R.id.selectImage);
            imageView=itemView.findViewById(R.id.imageView);
            tick=itemView.findViewById(R.id.tick);
            selectImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            lastSelect=Select;
            int clickedPosition = getAbsoluteAdapterPosition();
            if (clickedPosition == Select) {
                Select = -1;
            } else {
                Select = clickedPosition;
            }
            notifyItemChanged(lastSelect);
            notifyItemChanged(Select);
            imageListener.imageListener(getAbsoluteAdapterPosition());
        }
    }
    public interface imageOnClick{
        int imageListener(int Position);
    }
}
