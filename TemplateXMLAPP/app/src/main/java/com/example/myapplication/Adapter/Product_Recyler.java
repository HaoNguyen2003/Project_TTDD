package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Product_Recyler extends RecyclerView.Adapter<Product_Recyler.ProductHolder>{

    public ArrayList<Product>list_ProductsBackUp;

    public ArrayList<Product>list_Products;

    public ProductOnClick productOnClick;

    public void setProductOnClick(ProductOnClick productOnClick) {
        this.productOnClick = productOnClick;
    }

    public void filter_list(ArrayList<Product> list_BackUp) {
        this.list_Products = list_BackUp;
        notifyDataSetChanged();
    }
    public Product_Recyler(ArrayList<Product> list_Products) {
        this.list_Products = list_Products;
        list_ProductsBackUp=list_Products;
    }

    public void setList_ProductsBackUp(ArrayList<Product> list_ProductsBackUp) {
        this.list_ProductsBackUp = list_ProductsBackUp;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=list_Products.get(position);
        if(product==null)
            return;
        Picasso.get().load(product.listURL.get(0)).into(holder.img_product);
        holder.nameproduct.setText(product.getProductName());
        holder.title.setText((product.getProductTitle()));
        holder.discount.setText(product.getDiscount()+"%");
        double pricenow = Math.round(product.getPrice() * (1 - product.getDiscount() / 100) * 100.0) / 100.0;
        holder.price.setText(pricenow+"$");
        holder.Viewer.setText("Lượt xem: "+product.Viewer+"");
    }

    @Override
    public int getItemCount() {
        if(list_Products!=null)
            return list_Products.size();
        return 0;
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_product;
        TextView nameproduct;
        TextView price;
        TextView title;
        TextView discount;
        TextView Viewer;
        ConstraintLayout itemproduct;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.img_product);
            nameproduct=itemView.findViewById(R.id.nameproduct);
            price=itemView.findViewById(R.id.price);
            title=itemView.findViewById(R.id.title);
            discount=itemView.findViewById(R.id.discount);
            Viewer=itemView.findViewById(R.id.Viewer);
            itemproduct=itemView.findViewById(R.id.itemproduct);
            itemproduct.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Product product=list_Products.get(getAdapterPosition());
            productOnClick.ProductListener(product.getProductID());
        }
    }

    public interface ProductOnClick{
        public void ProductListener(int ID);
    }

    public ArrayList<Product>GetProductByBrand(int BrandID)
    {
        ArrayList<Product>list=new ArrayList<Product>();
        for(Product p:this.list_ProductsBackUp){
            if(p.getBrandID()==BrandID)
            {
                list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Product>GetProductByCategory(int CategoryID)
    {
        ArrayList<Product>list=new ArrayList<Product>();
        for(Product p:this.list_ProductsBackUp){
            if(p.getCategoryID()==CategoryID)
            {
                list.add(p);
            }
        }
        return list;
    }


    public ArrayList<Product>GetProductByCategoryAndBrand(int CategoryID,int BrandID)
    {
        ArrayList<Product>list=new ArrayList<Product>();
        for(Product p:this.list_ProductsBackUp){
            if(p.getCategoryID()==CategoryID&&p.getBrandID()==BrandID)
            {
                list.add(p);
            }
        }
        return list;
    }


    public ArrayList<Product>GetProductByName(String text)
    {
        ArrayList<Product>list=new ArrayList<Product>();
        for(Product p:this.list_ProductsBackUp){
            if(p.getProductName().toLowerCase().contains(text.toLowerCase())){
                list.add(p);
            }
        }
        return list;
    }

    public Product GetProductByID(int ID)
    {
        Product product =new Product();
        for(Product p:this.list_ProductsBackUp)
        {
            if(p.getProductID()==ID)
            {
                product=p;
            }
        }
        return product;
    }
}
