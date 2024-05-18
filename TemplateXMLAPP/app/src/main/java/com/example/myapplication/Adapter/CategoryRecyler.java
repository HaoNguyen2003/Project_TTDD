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
import com.example.myapplication.Model.Category;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRecyler extends RecyclerView.Adapter<CategoryRecyler.CategoryHolder> {
    public ArrayList<Category>list_Categories;
    public Context context;
    public int Select=-1;
    public int lastSelect=-1;
    categorySelectInForm categorySelectInForm;
    CategoryOnClick categoryOnClick;

    public void setCategorySelectInForm(CategoryRecyler.categorySelectInForm categorySelectInForm) {
        this.categorySelectInForm = categorySelectInForm;
    }

    public void setCategoryOnClick(CategoryOnClick categoryOnClick) {
        this.categoryOnClick = categoryOnClick;
    }
    public void setList_Categories(ArrayList<Category> list_Categories) {
        this.list_Categories = list_Categories;
    }

    public CategoryRecyler(ArrayList<Category> list_Categories,Context context) {
        this.list_Categories = list_Categories;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category=list_Categories.get(position);
        if(category==null)
            return;
        holder.textView_nameCategory.setText(category.getCategoryName());
        Picasso.get().load(category.getImageURL()).into(holder.imageView_itemCategory);
        if(Select==position&&Select!=-1)
        {
            holder.imageView_itemCategory.setBackgroundResource(0);
            holder.mainlayout.setBackgroundResource(R.drawable.purple_bg);
            //ImageViewCompat.setImageTintList(holder.imageView_itemCategory, ColorStateList.valueOf(context.getColor(R.color.white)));
            holder.textView_nameCategory.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageView_itemCategory.setBackgroundResource(R.drawable.grey_bg);
            holder.mainlayout.setBackgroundResource(0);
            //ImageViewCompat.setImageTintList(holder.imageView_itemCategory,ColorStateList.valueOf(context.getColor(R.color.black)));
            holder.textView_nameCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(list_Categories!=null)
            return list_Categories.size();
        return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout mainlayout;
        ImageView imageView_itemCategory;
        TextView textView_nameCategory;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            imageView_itemCategory=itemView.findViewById(R.id.imageView_itemCategory);
            textView_nameCategory=itemView.findViewById(R.id.textView_nameCategory);
            mainlayout=itemView.findViewById(R.id.mainlayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            lastSelect=Select;
            int clickedPosition = getAdapterPosition();

            // Kiểm tra nếu mục được click là mục đã được chọn trước đó
            if (clickedPosition == Select) {
                // Đặt lại biến Select thành -1 để tắt chọn
                Select = -1;
            } else {
                // Nếu không, cập nhật vị trí được chọn mới
                Select = clickedPosition;
            }
            notifyItemChanged(lastSelect);
            notifyItemChanged(Select);
            if(categoryOnClick!=null){
                categoryOnClick.Categorylistener(v,getAbsoluteAdapterPosition());
            }
            if(categorySelectInForm!=null){
                categorySelectInForm.categoryselect(list_Categories.get(getAbsoluteAdapterPosition()).getCategoryID());
            }
        }
    }

    public void changeSelect()
    {
        notifyItemChanged(lastSelect);
        notifyItemChanged(Select);
    }
    public interface CategoryOnClick
    {
        public void Categorylistener(View v,int position);
    }
    public interface categorySelectInForm{
        public void categoryselect(int ID);
    }
    public void setCategory(int ID)
    {
        for(Category b:list_Categories){
            if(b.getCategoryID()==ID)
            {
                lastSelect=Select;
                this.Select=list_Categories.indexOf(b);
                notifyItemChanged(lastSelect);
                notifyItemChanged(Select);
                return;
            }
        }
    }
    public void setCategory()
    {
        lastSelect = -1;
        Select = -1;
        notifyDataSetChanged();
    }
    public int getCategoryID()
    {
        int idCategory=list_Categories.get(Select).getCategoryID();
        return idCategory;
    }
}
