package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderView extends RecyclerView.Adapter<ImageSliderView.imageViewHolder>{
    public ArrayList<String> list;
    Context context;
    public ImageSliderView(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.viewpager_image,parent,false);
        return new imageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {
        String img=list.get(position);
        Picasso.get().load(img).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }


    public class imageViewHolder extends RecyclerView.ViewHolder{

        LinearLayout CardViewSlider;
        ImageView imageView;
        public imageViewHolder(@NonNull View itemView) {
            super(itemView);
            CardViewSlider=itemView.findViewById(R.id.CardViewSlider);
            imageView=itemView.findViewById(R.id.imageView_slider);
        }
    }
}
