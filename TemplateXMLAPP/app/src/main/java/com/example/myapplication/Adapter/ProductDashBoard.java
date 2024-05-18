package com.example.myapplication.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class ProductDashBoard extends RecyclerView.Adapter<ProductDashBoard.ProductViewHoder>{
    public ArrayList<Product>list_Products=new ArrayList<Product>();
    public ArrayList<Product>list_ProductsBackUp;
    public optionProduct optionProdcut;

    public void setOptionProduct(ProductDashBoard.optionProduct optionProduct) {
        this.optionProdcut = optionProduct;
    }

    public ProductDashBoard(ArrayList<Product> list_Products) {
        this.list_Products = list_Products;
        list_ProductsBackUp=list_Products;
    }
    public void filter_list(ArrayList<Product> list_BackUp) {
        if (list_BackUp != null) {
        this.list_Products = list_BackUp;
        notifyDataSetChanged();}
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

    @NonNull
    @Override
    public ProductViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdashboard,parent,false);
        return new ProductViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHoder holder, int position) {
        if (list_Products != null && !list_Products.isEmpty()) {
            Product product = list_Products.get(position);
            Picasso.get().load(product.getListURL().get(0)).into(holder.imageProduct);
            holder.Name.setText(product.getProductName());
            holder.textView_title.setText(product.getProductTitle());
            double price = product.getPrice() * (100.0 - product.Discount) / 100;
            holder.textView_price.setText(price + "$");
            holder.discount.setText("Discount: " + product.getDiscount());
        }
    }

    @Override
    public int getItemCount() {
        if(list_Products==null||list_Products.isEmpty())
            return 0;
        return list_Products.size();
    }

    public class ProductViewHoder extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView Name;
        TextView textView_title;
        TextView textView_price;
        TextView discount;
        ImageView btn_Remove;
        ImageView edit;
        public ProductViewHoder(@NonNull View itemView) {
            super(itemView);
            imageProduct=itemView.findViewById(R.id.imageProduct);
            Name=itemView.findViewById(R.id.Name);
            textView_title=itemView.findViewById(R.id.textView_title);
            textView_price=itemView.findViewById(R.id.textView_price);
            discount=itemView.findViewById(R.id.discount);
            btn_Remove=itemView.findViewById(R.id.btn_Remove);
            edit=itemView.findViewById(R.id.edit);
            btn_Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ID=list_Products.get(getAbsoluteAdapterPosition()).getProductID();
                    optionProdcut.RemoveProduct(ID);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product=list_Products.get(getAbsoluteAdapterPosition());
                    optionProdcut.EditProduct(product);
                }
            });
        }
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

    public interface optionProduct
    {
        public void RemoveProduct(int ID);
        public void EditProduct(Product product);
    }
}
