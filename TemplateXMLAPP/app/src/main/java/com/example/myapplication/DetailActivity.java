package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.ProductService;
import com.example.myapplication.Adapter.ImageSliderView;
import com.example.myapplication.Adapter.SizeRecyler;
import com.example.myapplication.Adapter.imagelistRecyler;
import com.example.myapplication.DataBaseSQLite.SQLiteHelper;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Size;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements SizeRecyler.SizeOnClick,imagelistRecyler.imageOnClick {
    SizeRecyler sizeRecyler;
    imagelistRecyler imagelistRecyler;

    ImageSliderView imageSliderView;
    ViewPager2 ViewPager_imagelist;
    RecyclerView select_size;
    RecyclerView selectimagelist;
    TextView textView_title;
    TextView textView_name;
    TextView textView_price;
    TextView textView_newprice;
    TextView textView_discount;
    TextView textView_Viewer;
    TextView Description;
    ImageView imageView_Return;
    ImageView image_gotocart;
    AppCompatButton AddToCart;
    ArrayList<Size>list_Sizes=new ArrayList<Size>();

    //----getIntent----//
    public Product product=new Product();
    public Account account=new Account();
    //---các biến xử lý----
    int position;
    String sizename;
    //---sql---//
    SQLiteHelper sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Init();
        getIntentData();
        SetDetail();
        viewerProduct();
        ReturnView();
        GoCart();
        fAddToCart();
    }

    public void getIntentData()
    {
        Intent intent=getIntent();
        if (intent != null) {
            product = (Product) intent.getSerializableExtra("Product");
            list_Sizes=(ArrayList<Size>) intent.getSerializableExtra("Sizes");
            account= (Account) intent.getSerializableExtra("Account");
        }
    }
    private void Init() {
        select_size=findViewById(R.id.select_size);
        selectimagelist=findViewById(R.id.selectimagelist);
        ViewPager_imagelist=findViewById(R.id.ViewPager_imagelist);
        textView_title=findViewById(R.id.textView_title);
        textView_name=findViewById(R.id.textView_name);
        textView_price=findViewById(R.id.textView_price);
        textView_newprice=findViewById(R.id.textView_newprice);
        textView_discount=findViewById(R.id.textView_discount);
        imageView_Return=findViewById(R.id.imageView_Return);
        Description=findViewById(R.id.Description);
        textView_Viewer=findViewById(R.id.textView_Viewer);
        image_gotocart=findViewById(R.id.image_gotocart);
        AddToCart=findViewById(R.id.AddToCart);
    }

    public void SetDetail()
    {
        textView_Viewer.setText(product.getViewer()+"");
        textView_title.setText(product.getProductTitle());
        textView_name.setText(product.getProductName());
        textView_price.setText("Old Price: $"+product.getPrice());
        double newprice=product.getPrice()*(1-product.getDiscount()/100);
        textView_newprice.setText("New Price: $"+newprice);
        textView_discount.setText("Discount: "+product.getDiscount()+" %");
        Description.setText(product.getDescription());
        menuSize();
        menuImage();
    }
    public void menuSize()
    {

        sizeRecyler=new SizeRecyler(list_Sizes,this);
        sizeRecyler.setSizeOnClick(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        select_size.setLayoutManager(layoutManager);
        select_size.setAdapter(sizeRecyler);
    }

    public void menuImage()
    {
        imageSliderView=new ImageSliderView(this.product.getListURL(),this);
        ViewPager_imagelist.setAdapter(imageSliderView);
        ViewPager_imagelist.setClipToPadding(false);
        ViewPager_imagelist.setClipChildren(false);
        ViewPager_imagelist.setOffscreenPageLimit(2);
        ViewPager_imagelist.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        imagelistRecyler=new imagelistRecyler(this.product.getListURL(),this);
        imagelistRecyler.setImageListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        selectimagelist.setLayoutManager(layoutManager);
        selectimagelist.setAdapter(imagelistRecyler);
    }

    public void fAddToCart()
    {
        sqLiteDatabase=new SQLiteHelper(getApplicationContext());
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sizename == null || sizename.equals("Size"))
                {
                    Toast.makeText(DetailActivity.this, "Please Select Size", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    int id=sqLiteDatabase.getCartID(product.ProductID,account.AccountID,sizename);
                    if(id==-1){
                        sqLiteDatabase.AddToCart(product,account.AccountID,sizename);
                    }
                    else{
                        sqLiteDatabase.UpdateCart(product,account.AccountID,sizename);
                    }
                    Toast.makeText(DetailActivity.this, "You have added 1 product to your cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void GoCart()
    {
        image_gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,bottomnavigationActivity.class);
                intent.putExtra("Account",account);
                intent.putExtra("targetFragment",1);
                startActivity(intent);
            }
        });
    }

    public void ReturnView()
    {
        imageView_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public String SizeListener(View v, int position) {
        Size size=this.sizeRecyler.list_Sizes.get(position);
        if(sizeRecyler.Select==-1){
            this.sizename="Size";
        }
        else{
            this.sizename=size.getSizeName();
        }
        Log.e("Check", "SizeListener: "+sizename);
        return this.sizename;
    }

    @Override
    public int imageListener(int Position) {
        this.position=Position;
        ViewPager_imagelist.setCurrentItem(position, true);
        return this.position;
    }
    public void viewerProduct() {
        ProductService productService = APIService.retrofit.create(ProductService.class);
        Call<APIReponse> call = productService.pushViewerProduct(product.getProductID());
        call.enqueue(new Callback<APIReponse>() {
            @Override
            public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                if (response.isSuccessful()) {
                    APIReponse apiResponse = response.body();
                } else {
                    String errorMessage = "Request failed with code: " + response.code();
                    Log.e("viewerProduct", errorMessage);
                }
            }

            @Override
            public void onFailure(Call<APIReponse> call, Throwable t) {
                Log.e("viewerProduct", "onFailure: " + t.getMessage());
            }
        });
    }

}