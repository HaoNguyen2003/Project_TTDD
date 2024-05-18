package com.example.myapplication;

import static com.example.myapplication.API.APIService.retrofit;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.APIReponse;
import com.example.myapplication.API.APIService;
import com.example.myapplication.API.AccountService;
import com.example.myapplication.API.BrandService;
import com.example.myapplication.API.CategoryService;
import com.example.myapplication.API.ProductService;
import com.example.myapplication.Adapter.BrandRecyler;
import com.example.myapplication.Adapter.CategoryRecyler;
import com.example.myapplication.Adapter.ProductDashBoard;
import com.example.myapplication.DataBaseSQLite.SQLiteHelper;
import com.example.myapplication.Model.Brand;
import com.example.myapplication.Model.Category;
import com.example.myapplication.Model.Product;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDBActivity extends AppCompatActivity
implements BrandRecyler.brandOnclick,CategoryRecyler.CategoryOnClick, ProductDashBoard.optionProduct, BrandRecyler.brandSelectInForm , CategoryRecyler.categorySelectInForm {
    RecyclerView All_brand,selectBrand,selectImage;
    RecyclerView AllCategory,selectCategory;
    RecyclerView AllProduct;
    BrandRecyler brandRecyler,brandInForm;
    CategoryRecyler categoryRecyler,categoryInForm;
    ProductDashBoard productRecyler;
    android.widget.ProgressBar progressBarCategory;
    ConstraintLayout mainmanager;
    ConstraintLayout formAddProduct;
    ProgressBar ProgressBar;
    ProgressBar progressBarProduct;
    ImageView imageView_Return;
    ImageView hideform;
    ImageView editformAddproduct;
    TextView nameOfForm,textView_Select;

    EditText nameForm,TitleForm,PriceInForm,PriceOutForm,DiscountForm,DescriptionForm,listImage;

    AppCompatButton btn_submit,btn_edit,btn_selectImage;
    ArrayList<Product> list_Products=new ArrayList<Product>();
    ArrayList<Category>list_Categories=new ArrayList<Category>();
    public ArrayList<Brand> list_Brands=new ArrayList<Brand>();
    int brandId=-1;
    int categoryId=-1;

    int brandSubmit=-1;
    int categorySubmit=-1;
    ArrayList<String>Path=new ArrayList<String>();
    Product product=new Product();
    Product productCreate=new Product();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dbactivity);
        Init();
        setUpMenuBrand();
        setUpMenuCategory();
        setUpProductData();
        ReturnDashBoard();
        hideformAddProduct();
        SubmitForm();
    }
    public void Init(){
        All_brand = findViewById(R.id.All_brand);
        AllCategory = findViewById(R.id.AllCategory);
        AllProduct = findViewById(R.id.AllProduct);
        ProgressBar = findViewById(R.id.ProgressBar);
        progressBarProduct = findViewById(R.id.progressBarProduct);
        progressBarCategory = findViewById(R.id.progressBarCategory);
        imageView_Return=findViewById(R.id.imageView_Return);
        hideform=findViewById(R.id.hideform);
        mainmanager=findViewById(R.id.mainmanager);
        formAddProduct=findViewById(R.id.formAddProduct);
        editformAddproduct=findViewById(R.id.editformAddproduct);
        selectBrand=findViewById(R.id.selectBrand);
        selectCategory=findViewById(R.id.selectCategory);
        btn_submit=findViewById(R.id.btn_submit);
        nameForm=findViewById(R.id.nameForm);
        TitleForm=findViewById(R.id.TitleForm);
        PriceInForm=findViewById(R.id.PriceInForm);
        PriceOutForm=findViewById(R.id.PriceOutForm);
        DiscountForm=findViewById(R.id.DiscountForm);
        DescriptionForm=findViewById(R.id.DescriptionForm);
        btn_edit=findViewById(R.id.btn_edit);
        nameOfForm=findViewById(R.id.nameOfForm);
        btn_selectImage=findViewById(R.id.btn_selectImage);
        listImage=findViewById(R.id.listImage);
        textView_Select=findViewById(R.id.textView_Select);
    }
    public void MenuBrand()
    {
        ProgressBar.setVisibility(View.VISIBLE);
        brandRecyler=new BrandRecyler(list_Brands,this);
        brandInForm=new BrandRecyler(list_Brands,this);
        brandRecyler.setBrandOnclick(this);
        brandInForm.setBrandSelectInForm(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager selectBrandLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        All_brand.setLayoutManager(layoutManager);
        selectBrand.setLayoutManager(selectBrandLayoutManager);
        selectBrand.setAdapter(brandInForm);
        All_brand.setAdapter(brandRecyler);
        ProgressBar.setVisibility(View.GONE);
    }
    private void setUpMenuBrand() {
        ProgressBar.setVisibility(View.VISIBLE);
        BrandService brandService = retrofit.create(BrandService.class);
        Call<ArrayList<Brand>> call = brandService.getAllBrand();
        call.enqueue(new Callback<ArrayList<Brand>>() {
            @Override
            public void onResponse(Call<ArrayList<Brand>> call, Response<ArrayList<Brand>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list_Brands = response.body();
                    MenuBrand();
                } else {
                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Brand>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }

    public void MenuCategory()
    {
        progressBarCategory.setVisibility(View.VISIBLE);
        categoryRecyler=new CategoryRecyler(list_Categories,this);
        categoryInForm=new CategoryRecyler(list_Categories,this);
        categoryRecyler.setCategoryOnClick(this);
        categoryInForm.setCategorySelectInForm(this);
        LinearLayoutManager selectCategoryLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        selectCategory.setLayoutManager(selectCategoryLayoutManager);
        AllCategory.setLayoutManager(layoutManager);
        selectCategory.setAdapter(categoryInForm);
        AllCategory.setAdapter(categoryRecyler);
        progressBarCategory.setVisibility(View.GONE);
    }
    private void setUpMenuCategory() {
        ProgressBar.setVisibility(View.VISIBLE);
        CategoryService categoryService = retrofit.create(CategoryService.class);
        Call<ArrayList<Category>> call = categoryService.getAllCategory();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list_Categories = response.body();
                    MenuCategory();
                } else {
                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    public void ProductData()
    {
        if (list_Products != null && !list_Products.isEmpty()) {
            productRecyler = new ProductDashBoard(list_Products);
            productRecyler.setOptionProduct(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            AllProduct.setLayoutManager(layoutManager);
            AllProduct.setAdapter(productRecyler);
            progressBarProduct.setVisibility(View.GONE);
        }
    }
    public void setUpProductData()
    {
        progressBarProduct.setVisibility(View.VISIBLE);
        ProductService productService = retrofit.create(ProductService.class);
        Call<ArrayList<Product>> call = productService.getAllProduct();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> productList = response.body();
                    if (productList != null && !productList.isEmpty()) {
                        //list_Products.add(new Product());
                        list_Products.clear(); // Xóa danh sách hiện tại
                        list_Products.addAll(productList); // Thêm dữ liệu mới từ response
                        ProductData();
                    } else {
                        Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Log.d("logg",response.message());
                    if (statusCode == 404) {
                    } else {
                    }
                    Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    Log.e("API_CALL", "Lỗi: " + response.message());
                }
                progressBarProduct.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                progressBarProduct.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Lỗi: " + t.getMessage());
            }
        });
    }
    @Override
    public void brandlistener(View v, int position) {
        Brand selectedBrand = this.brandRecyler.list_Brand.get(position);
        brandId = selectedBrand.getBrandID();
        ArrayList<Product> filteredProducts=new ArrayList<Product>();
        filteredProducts=this.GetProduct(this.categoryId,this.brandId);
        this.productRecyler.filter_list(filteredProducts); //Áp dụng lọc vào danh sách sản phẩm và cập nhật giao diện
    }

    @Override
    public void Categorylistener(View v, int position) {
        Category selectedCategory=this.categoryRecyler.list_Categories.get(position);
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
        }
        else if(this.CheckOption(categorySelect,brandSelect)==2)
        {
            filteredProducts = this.productRecyler.GetProductByBrand(brandId);
        }
        else if(this.CheckOption(categorySelect,brandSelect)==3){
            filteredProducts = this.productRecyler.GetProductByCategory(categoryId);
        }
        else if(this.CheckOption(categorySelect,brandSelect)==4){
            filteredProducts = this.productRecyler.GetProductByCategoryAndBrand(categoryId,brandId);
        }
        return filteredProducts;
    }
    public void ReturnDashBoard()
    {
        imageView_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void RemoveProduct(int ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductService productService= retrofit.create(ProductService.class);
                        Call<APIReponse>call=productService.Removeproduct(ID);
                        call.enqueue(new Callback<APIReponse>() {
                            @Override
                            public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                                if(response.isSuccessful())
                                {
                                    APIReponse apiReponse=response.body();
                                    Toast.makeText(ProductDBActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    setUpProductData();
                                    SQLiteHelper sqLiteHelper=new SQLiteHelper(getApplicationContext());
                                    sqLiteHelper.removeProductInCart(ID);
                                }
                                else{
                                    Log.e("Error", "Delete: "+response.message());
                                }
                            }
                            @Override
                            public void onFailure(Call<APIReponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                                Log.e("API_CALL", "Lỗi: " + t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void EditProduct(Product product) {
        this.product=product;
        nameOfForm.setText("Edit Product");
        mainmanager.setVisibility(View.GONE);
        formAddProduct.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.GONE);
        btn_selectImage.setVisibility(View.GONE);
        textView_Select.setVisibility(View.GONE);
        listImage.setVisibility(View.GONE);
        listImage.setText("");
        nameForm.setText(product.getProductName()+"");
        TitleForm.setText(product.getProductTitle()+"");
        PriceInForm.setText(product.getPriceIn()+"");
        PriceOutForm.setText(product.getPrice()+"");
        DiscountForm.setText((product.getDiscount()+""));
        DescriptionForm.setText(product.getDescription());
        brandInForm.setBrand(product.BrandID);
        categoryInForm.setCategory(product.getCategoryID());
    }
    public void hideformAddProduct()
    {
        hideform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameOfForm.setText("Create Product");
                mainmanager.setVisibility(View.GONE);
                formAddProduct.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                btn_selectImage.setVisibility(View.VISIBLE);
                textView_Select.setVisibility(View.VISIBLE);
                listImage.setVisibility(View.VISIBLE);
                listImage.setText("");
                nameForm.setText("");
                TitleForm.setText("");
                PriceInForm.setText("");
                PriceOutForm.setText("");
                DiscountForm.setText((""));
                DescriptionForm.setText("");
                brandInForm.setBrand();
                categoryInForm.setCategory();
                Path.clear();
            }
        });
        editformAddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainmanager.setVisibility(View.VISIBLE);
                formAddProduct.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public void brandselect(int ID) {
        brandSubmit=ID;
    }

    @Override
    public void categoryselect(int ID) {
        categorySubmit=ID;
    }

    private void openGallery(){
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String dir=data.getDataString();
        if(dir==null||dir.isEmpty())
            return;
        Path.add(dir);
        Log.e("checkresult", "image: "+dir);
        Log.e("checkresult", "image: "+Path.toString());
    }
    public void SubmitForm()
    {
        btn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                listImage.setText("");
                listImage.setText(Path.toString());
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Product Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(TitleForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Product Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(PriceInForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Price In", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(PriceOutForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Price Out", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DiscountForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Discount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DescriptionForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(brandInForm.Select==-1) {
                    Toast.makeText(ProductDBActivity.this, "Please Select Brand", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "brandClick: " + "chon brand");
                    return;
                }
                if(categoryInForm.Select==-1){
                    Toast.makeText(ProductDBActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "brandClick: " + "chon category");
                    return;
                }
                productCreate.BrandID=brandSubmit;
                productCreate.CategoryID=categorySubmit;
                productCreate.ProductName=nameForm.getText().toString();
                productCreate.ProductTitle=TitleForm.getText().toString();
                productCreate.PriceIn=PriceInForm.getText().toString();
                productCreate.Price=Double.parseDouble(PriceOutForm.getText().toString());
                productCreate.Discount=Double.parseDouble(DiscountForm.getText().toString());
                productCreate.Description=DescriptionForm.getText().toString();

                ProductService productService= retrofit.create(ProductService.class);
                Call<APIReponse>call=productService.CreateProduct(productCreate);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        if(response.isSuccessful())
                        {
                            APIReponse apiReponse=response.body();
                            Toast.makeText(ProductDBActivity.this,apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                            int ID=Integer.parseInt(apiReponse.getMessage());
                            for(String path:Path)
                            {
                                uploadFile(path,ID);
                            }
                            restartActivity();
                        }
                        else{
                            Toast.makeText(ProductDBActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {
                        Toast.makeText(ProductDBActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("check", "checkError "+t.getMessage());
                    }
                });
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Product Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(TitleForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Product Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(PriceInForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Price In", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(PriceOutForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Price Out", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DiscountForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Discount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DescriptionForm.getText())) {
                    Toast.makeText(ProductDBActivity.this, "Please Input Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(brandInForm.Select==-1) {
                    Toast.makeText(ProductDBActivity.this, "Please Select Brand", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "brandClick: " + "chon brand");
                    return;
                }
                if(categoryInForm.Select==-1){
                    Toast.makeText(ProductDBActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "brandClick: " + "chon category");
                    return;
                }
                product.BrandID=brandInForm.getBrandID();
                product.CategoryID=categoryInForm.getCategoryID();
                product.ProductName=nameForm.getText().toString();
                product.ProductTitle=TitleForm.getText().toString();
                product.PriceIn=PriceInForm.getText().toString();
                product.Price=Double.parseDouble(PriceOutForm.getText().toString());
                product.Discount=Double.parseDouble(DiscountForm.getText().toString());
                product.Description=DescriptionForm.getText().toString();
                ProductService productService = APIService.retrofit.create(ProductService.class);
                Call<APIReponse> call = productService.updateProduct(product.ProductID,
                        product.BrandID,product.CategoryID, product.ProductName,product.ProductTitle,
                        product.Price,product.PriceIn,product.Description,product.Discount);
                call.enqueue(new Callback<APIReponse>() {
                    @Override
                    public void onResponse(Call<APIReponse> call, Response<APIReponse> response) {
                        APIReponse apiReponse = null;
                        if (response.isSuccessful()) {
                            apiReponse = response.body();
                            Toast.makeText(ProductDBActivity.this, apiReponse.getMessage(), Toast.LENGTH_SHORT).show();
                            setUpProductData();
                            SQLiteHelper sqLiteHelper=new SQLiteHelper(getApplicationContext());
                            double price=product.Price*(100-product.Discount)/100;
                            sqLiteHelper.UpdatePrice(product.ProductID,price);
                        } else {
                            Toast.makeText(ProductDBActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<APIReponse> call, Throwable t) {
                        Toast.makeText(ProductDBActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Lỗi", "fail: " + t.getMessage());
                    }
                });


            }
        });
    }
    public static String getPathFromUri(Uri uri, ContentResolver contentResolver) {
        String path = null;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if(cursor==null)
        {
            path=uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int index=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path=cursor.getString(index);
            cursor.close();;
        }
        return path;
    }
    private void uploadFile(String path,int ID) {
        Uri uri=Uri.parse(path);
        File file = new File(getPathFromUri(uri,getApplicationContext().getContentResolver()));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ProductService productService = APIService.retrofit.create(ProductService.class);
        Call<Void> call = productService.uploadFile(fileToUpload, filename,ID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Bắt API: "+t.getMessage());
            }
        });
    }
    public void restartActivity() {
       recreate();
    }
}