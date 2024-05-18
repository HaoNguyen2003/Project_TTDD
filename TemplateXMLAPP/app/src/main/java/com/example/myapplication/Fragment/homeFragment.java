package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication.API.APIService;
import com.example.myapplication.API.BrandService;
import com.example.myapplication.API.CategoryService;
import com.example.myapplication.API.ContentService;
import com.example.myapplication.API.ProductService;
import com.example.myapplication.API.SizeService;
import com.example.myapplication.Adapter.BrandRecyler;
import com.example.myapplication.Adapter.CategoryRecyler;
import com.example.myapplication.Adapter.Product_Recyler;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.Brand;
import com.example.myapplication.Model.Category;
import com.example.myapplication.Model.ContentItem;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Size;
import com.example.myapplication.R;
import com.example.myapplication.Util.Checkconnect;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment implements
        BrandRecyler.brandOnclick,
        CategoryRecyler.CategoryOnClick,
        SearchView.OnQueryTextListener,Product_Recyler.ProductOnClick{

    VideoView Newfeed;
    RecyclerView AllCategory;
    RecyclerView All_brand;
    RecyclerView AllProduct;
    TextView textview_Recommend;
    TextView textView_NameCustomer;
    SearchView searchView;
    ConstraintLayout layoutSearch;
    ImageView imageView_Up;
    ImageView search;
    ImageView phone;
    BrandRecyler brandRecyler;
    CategoryRecyler categoryRecyler;
    Product_Recyler productRecyler;
    android.widget.ProgressBar progressBarProduct;
    ProgressBar progressBarCategory;
    ProgressBar ProgressBar;
    ProgressBar progressBarBanner;
    ArrayList<Product> list_Products=new ArrayList<Product>();
    ArrayList<Category>list_Categories=new ArrayList<Category>();
    public ArrayList<Brand>list_Brands=new ArrayList<Brand>();

    public Account account=new Account();
    int brandId=-1;
    int categoryId=-1;

    String BrandName="All Product";
    String CategoryName="";
    //-------------------------------
    View view;

    public homeFragment() {}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Override
    public void onResume() {
        super.onResume();
        //setUpMenuBrand();
        //setUpMenuCategory();
        setUpProductData();
        setUpUserName();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        Init(view);
        if(Checkconnect.isInterNet(this.getContext()))
        {
            setUpNewfeed();
            setUpMenuBrand();
            setUpMenuCategory();
            setUpProductData();
        }
        else{
            Checkconnect.toast_confirm(getContext(),"Please connect Internet");
        }
        setUpUserName();
        CallPhone();
        searchfunction();
        return view;
    }
    public  void CallPhone()
    {
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntenCallPhone=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+84)0878193742"));
                startActivity(IntenCallPhone);
            }
        });
    }


    //----------find ID-------------//
    public void Init(View view) {
        //Phần find element
        Newfeed = view.findViewById(R.id.Newfeed);
        All_brand = view.findViewById(R.id.AllBrand);
        AllCategory = view.findViewById(R.id.AllCategory);
        AllProduct = view.findViewById(R.id.AllProduct);
        textview_Recommend = view.findViewById(R.id.textview_Recommend);
        textView_NameCustomer=view.findViewById(R.id.textView_NameCustomer);
        searchView = view.findViewById(R.id.searchView);
        search = view.findViewById(R.id.search);
        phone=view.findViewById(R.id.phone);
        imageView_Up = view.findViewById(R.id.imageView_Up);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        progressBarBanner = view.findViewById(R.id.progressBarBanner);
        ProgressBar = view.findViewById(R.id.LoadBrand);
        progressBarProduct = view.findViewById(R.id.progressBarProduct);
        progressBarCategory = view.findViewById(R.id.progressBarCategory);
    }
    //----------End find ID-------------//

    //---------getAccountformActivity----//

    public void setUpUserName()
    {
        account= (Account) getArguments().get("Account");
        textView_NameCustomer.setText(account.getUsername());
    }

    public void loadAgainUserName()
    {

    }
    //---------getAccountformActivity----//

    //---------NewFeed-----------//
    public void Newfeed(String URL)
    {
        MediaController mediaController=new MediaController(getContext());
        Newfeed.setVideoPath(URL);
        mediaController.setAnchorView(Newfeed);
        Newfeed.setMediaController(mediaController);
        Newfeed.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Newfeed.start();
            }
        });
        Newfeed.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("Video_View_Tag", "Video Complete");
            }
        });
        progressBarBanner.setVisibility(View.GONE);
    }

    public void setUpNewfeed()
    {
        progressBarBanner.setVisibility(View.VISIBLE);
        ContentService contentService=APIService.retrofit.create(ContentService.class);
        Call<ContentItem> call=contentService.getNewContent();
        call.enqueue(new Callback<ContentItem>() {
            @Override
            public void onResponse(Call<ContentItem> call, Response<ContentItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ContentItem contentItem = response.body();
                    Newfeed(contentItem.ImageURL);
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContentItem> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });

    }
    //---------End NewFeed-----------//


    //---------Menu Brand-----------//
    public void MenuBrand()
    {
        ProgressBar.setVisibility(View.VISIBLE);
        brandRecyler=new BrandRecyler(list_Brands,this.getActivity());
        brandRecyler.setBrandOnclick(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        All_brand.setLayoutManager(layoutManager);
        All_brand.setAdapter(brandRecyler);
        ProgressBar.setVisibility(View.GONE);
    }
    private void setUpMenuBrand() {
        ProgressBar.setVisibility(View.VISIBLE);
        BrandService brandService = APIService.retrofit.create(BrandService.class);
        Call<ArrayList<Brand>> call = brandService.getAllBrand();
        call.enqueue(new Callback<ArrayList<Brand>>() {
            @Override
            public void onResponse(Call<ArrayList<Brand>> call, Response<ArrayList<Brand>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list_Brands = response.body();
                    MenuBrand();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Brand>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    //---------End Menu Brand-----------//



    //---------Menu Category-----------//
    public void MenuCategory()
    {
        progressBarCategory.setVisibility(View.VISIBLE);
        categoryRecyler=new CategoryRecyler(list_Categories,this.getActivity());
        categoryRecyler.setCategoryOnClick(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        AllCategory.setLayoutManager(layoutManager);
        AllCategory.setAdapter(categoryRecyler);
        progressBarCategory.setVisibility(View.GONE);
    }
    private void setUpMenuCategory() {
        ProgressBar.setVisibility(View.VISIBLE);
        CategoryService categoryService = APIService.retrofit.create(CategoryService.class);
        Call<ArrayList<Category>> call = categoryService.getAllCategory();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list_Categories = response.body();
                    MenuCategory();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    //---------Menu Category-----------//



    //----------Product Data------------//
    public void ProductData()
    {
        productRecyler=new Product_Recyler(list_Products);
        productRecyler.setProductOnClick(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this.getActivity(),2);
        AllProduct.setLayoutManager(gridLayoutManager);
        AllProduct.setAdapter(productRecyler);
        progressBarProduct.setVisibility(View.GONE);
    }
    public void setUpProductData()
    {
        progressBarProduct.setVisibility(View.VISIBLE);
        ProductService productService = APIService.retrofit.create(ProductService.class);
        Call<ArrayList<Product>> call = productService.getAllProduct();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> productList = response.body();
                    if (productList != null && !productList.isEmpty()) {
                        list_Products = productList;
                        ProductData();
                        productRecyler.filter_list(GetProduct(categoryId,brandId));
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Log.d("logg",response.message());
                    if (statusCode == 404) {
                        // Xử lý khi không tìm thấy
                    } else {
                        // Xử lý khi có lỗi không xác định
                    }
                    Toast.makeText(getContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    Log.e("API_CALL", "Lỗi: " + response.message());
                }
                progressBarProduct.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                progressBarProduct.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    //----------Product Data------------//



    //----------search And Fill---------//
    public void searchfunction()
    {
        imageView_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSearch.setVisibility(View.GONE);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSearch.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText!=null&&!newText.isEmpty()){
            this.productRecyler.filter_list(this.productRecyler.GetProductByName(newText));
            return true;
        }
        return false;
    }



    @Override
    public void brandlistener(View v, int position) {
        Brand selectedBrand = this.brandRecyler.list_Brand.get(position);
        BrandName=selectedBrand.brandName;
        brandId = selectedBrand.getBrandID();
        ArrayList<Product> filteredProducts=new ArrayList<Product>();
        filteredProducts=this.GetProduct(this.categoryId,this.brandId);
        this.productRecyler.filter_list(filteredProducts); //Áp dụng lọc vào danh sách sản phẩm và cập nhật giao diện
    }

    @Override
    public void Categorylistener(View v, int position) {
        Category selectedCategory=this.categoryRecyler.list_Categories.get(position);
        CategoryName=selectedCategory.getCategoryName();
        categoryId=selectedCategory.getCategoryID();
        ArrayList<Product> filteredProducts=new ArrayList<Product>();
        filteredProducts=this.GetProduct(this.categoryId,this.brandId);
        this.productRecyler.filter_list(filteredProducts);
    }


    public int CheckOption(int categorySelect,int brandSelect)
    {
        if(categorySelect==-1&&brandSelect==-1)
            return 1;
        if(categorySelect==-1)
            return 2;
        if(brandSelect==-1)
            return 3;
        if(categorySelect!=-1&&brandSelect!=-1)
            return 4;
        return 0;
    }


    public ArrayList<Product>GetProduct(int categoryId,int brandId)
    {
        ArrayList<Product> filteredProducts=new ArrayList<Product>();
        int categorySelect=this.categoryRecyler.Select;
        int brandSelect=this.brandRecyler.Select;
        if(this.CheckOption(categorySelect,brandSelect)==1){
            filteredProducts=this.productRecyler.list_ProductsBackUp;
            textview_Recommend.setText("All Product");
        }
        else if(this.CheckOption(categorySelect,brandSelect)==2)
        {
            filteredProducts = this.productRecyler.GetProductByBrand(brandId);
            textview_Recommend.setText(BrandName);
        }
        else if(this.CheckOption(categorySelect,brandSelect)==3){
            filteredProducts = this.productRecyler.GetProductByCategory(categoryId);
            textview_Recommend.setText(CategoryName);
        }
        else if(this.CheckOption(categorySelect,brandSelect)==4){
            filteredProducts = this.productRecyler.GetProductByCategoryAndBrand(categoryId,brandId);
            textview_Recommend.setText(BrandName+" : "+CategoryName);
        }
        return filteredProducts;
    }

    @Override
    public void ProductListener(int ID) {
        ArrayList<Size> List_Sizes=new ArrayList<Size>();
        Product product = productRecyler.GetProductByID(ID);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("Product",product);
        intent.putExtra("Account",account);
        SizeService sizeService=APIService.retrofit.create(SizeService.class);
        Call<ArrayList<Size>> call=sizeService.getSizeByCategoryID(product.getCategoryID());
        call.enqueue(new Callback<ArrayList<Size>>() {
            @Override
            public void onResponse(Call<ArrayList<Size>> call, Response<ArrayList<Size>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Size> List_Sizes = response.body();
                    if (List_Sizes != null && !List_Sizes.isEmpty()) {
                        intent.putExtra("Sizes",List_Sizes);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Log.d("logg",response.message());
                    if (statusCode == 404) {
                        // Xử lý khi không tìm thấy
                    } else {
                        // Xử lý khi có lỗi không xác định
                    }
                    Toast.makeText(getContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    Log.e("API_CALL", "Lỗi: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Size>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }

    //----------End Search And Fill---------//

}