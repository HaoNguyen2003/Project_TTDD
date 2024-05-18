package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Size;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SizeRecyler extends RecyclerView.Adapter<SizeRecyler.SizeHolder>{
    public Context context;
    public ArrayList<Size>list_Sizes;
    public SizeOnClick sizeOnClick;
    public int Select=-1;
    public int lastSelect=-1;
    public void setSizeOnClick(SizeOnClick sizeOnClick) {
        this.sizeOnClick = sizeOnClick;
    }

    public SizeRecyler(ArrayList<Size> list_Sizes,Context context) {
        this.context = context;
        this.list_Sizes = list_Sizes;
    }

    @NonNull
    @Override
    public SizeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewholder_size,parent,false);
        return new SizeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeHolder holder, int position) {
        Size size=list_Sizes.get(position);
        holder.sizetxt.setText(size.getSizeName());
        if(Select==position)
        {
            holder.size_layout.setBackgroundResource(R.drawable.purple_bg);
            ImageViewCompat.setImageTintList(holder.tick, ColorStateList.valueOf(context.getColor(R.color.white)));
            holder.sizetxt.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.tick.setVisibility(View.VISIBLE);
        }
        else {
            holder.size_layout.setBackgroundResource(R.drawable.grey_bg);
            holder.sizetxt.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tick.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if(list_Sizes==null)
            return 0;
        return list_Sizes.size();
    }

    public class SizeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView tick;
        ConstraintLayout size_layout;
        TextView sizetxt;
        public SizeHolder(@NonNull View itemView) {
            super(itemView);
            size_layout=itemView.findViewById(R.id.size_layout);
            sizetxt=itemView.findViewById(R.id.sizetxt);
            tick=itemView.findViewById(R.id.tick);
            sizetxt.setOnClickListener(this);
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
            sizeOnClick.SizeListener(v,getAbsoluteAdapterPosition());

        }
    }
    public interface SizeOnClick{
        public String SizeListener(View v,int position);
    }
}
