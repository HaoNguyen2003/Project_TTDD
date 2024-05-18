package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ContentItem;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ContentItemHolder>{

    ArrayList<ContentItem>list_ContentItems;
    Context context;
    public ContentItemAdapter(ArrayList<ContentItem> list_ContentItems,Context context) {
        this.list_ContentItems = list_ContentItems;
        this.context=context;
    }

    @NonNull
    @Override
    public ContentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewpager_item,parent,false);
        return new ContentItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentItemHolder holder, int position) {
        ContentItem contentItem=list_ContentItems.get(position);
        if(contentItem==null)
            return;
        Picasso.get().load(contentItem.ImageURL).into(holder.imageContent);
    }

    @Override
    public int getItemCount() {
        if(list_ContentItems!=null)
            return list_ContentItems.size();
        return 0;
    }

    public class ContentItemHolder extends RecyclerView.ViewHolder{
        ImageView imageContent ;
        public ContentItemHolder(@NonNull View itemView) {
            super(itemView);
            imageContent=itemView.findViewById(R.id.image);
        }
    }
}
