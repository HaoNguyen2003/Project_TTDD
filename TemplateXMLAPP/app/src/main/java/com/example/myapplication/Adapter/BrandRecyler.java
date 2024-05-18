package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Brand;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandRecyler extends RecyclerView.Adapter<BrandRecyler.BrandHolder> {
    public int Select=-1;
    public int lastSelect=-1;
    public ArrayList<Brand>list_Brand;
    Context context;
    brandOnclick brandOnclick;
    brandSelectInForm brandSelectInForm;

    public void setBrandSelectInForm(BrandRecyler.brandSelectInForm brandSelectInForm) {
        this.brandSelectInForm = brandSelectInForm;
    }

    public void setBrandOnclick(BrandRecyler.brandOnclick brandOnclick) {
        this.brandOnclick = brandOnclick;
    }


    public BrandRecyler(ArrayList<Brand> list_Brand,Context context) {
        this.context=context;
        this.list_Brand = list_Brand;
    }

    @NonNull
    @Override
    public BrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_brand,parent,false);
        return new BrandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandHolder holder, int position) {
        Brand brand=list_Brand.get(position);
        if(brand==null)
            return;
        Picasso.get().load(brand.getImageURL()).into(holder.imageView_itembrand);
        holder.textView_namebrand.setText(brand.brandName);
        if(Select==position)
        {
            holder.imageView_itembrand.setBackgroundResource(0);
            holder.mainlayout.setBackgroundResource(R.drawable.purple_bg);
            ImageViewCompat.setImageTintList(holder.imageView_itembrand,ColorStateList.valueOf(context.getColor(R.color.white)));
            holder.textView_namebrand.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageView_itembrand.setBackgroundResource(R.drawable.grey_bg);
            holder.mainlayout.setBackgroundResource(0);
            ImageViewCompat.setImageTintList(holder.imageView_itembrand,ColorStateList.valueOf(context.getColor(R.color.black)));
            holder.textView_namebrand.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(list_Brand!=null)
            return list_Brand.size();
        return 0;
    }

    public class BrandHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView_itembrand;
        TextView textView_namebrand;
        LinearLayout mainlayout;

        public BrandHolder(@NonNull View itemView) {
            super(itemView);
            imageView_itembrand=itemView.findViewById(R.id.imageView_itembrand);
            textView_namebrand=itemView.findViewById(R.id.textView_namebrand);
            mainlayout=itemView.findViewById(R.id.mainlayout);
            itemView.setOnClickListener(this);
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
            if(brandOnclick!=null){
                brandOnclick.brandlistener(v,getAbsoluteAdapterPosition());
            }
            if(brandSelectInForm!=null) {
                brandSelectInForm.brandselect(list_Brand.get(getAbsoluteAdapterPosition()).getBrandID());
            }
        }
    }

    public void setBrand(int IDBrand)
    {
        for(Brand b:list_Brand){
            if(b.getBrandID()==IDBrand)
            {
                lastSelect=Select;
                this.Select=list_Brand.indexOf(b);
                notifyItemChanged(lastSelect);
                notifyItemChanged(Select);
                return;
            }
        }
    }
    public int getBrandID()
    {
        int idBrand=list_Brand.get(Select).getBrandID();
        return idBrand;
    }
    public void setBrand()
    {
        lastSelect = -1;
        Select = -1;
        notifyDataSetChanged();
    }
    public interface brandOnclick{
        public void brandlistener(View v,int position);
    }
    public interface brandSelectInForm{
        public void brandselect(int ID);
    }
}
