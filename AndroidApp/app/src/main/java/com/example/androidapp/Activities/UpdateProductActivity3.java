package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.Data.ProductDetailData.ProductDetailViewModel;
import com.example.androidapp.HelperClass.PriceQuantity;
import com.example.androidapp.HelperClass.PriceQuantityAdapter;
import com.example.androidapp.HelperClass.PriceQuantityItem;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductActivity3 extends AppCompatActivity {
    public static final String EXTRA_ATTRIBUTE_LIST_1 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_LIST_1 ";
    public static final String EXTRA_ATTRIBUTE_LIST_2 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_LIST_2 ";
    public static final String EXTRA_ATTRIBUTE_1 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_1 ";
    public static final String EXTRA_ATTRIBUTE_2 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_2 ";
    public static final String EXTRA_PRODUCT_NAME =
            "com.example.androidapp.EXTRA_PRODUCT_NAME ";
    public static final String EXTRA_PRODUCT_NAME_TMP =
            "com.example.androidapp.EXTRA_PRODUCT_NAME_TMP ";
    public static final String EXTRA_PRODUCT_TYPE =
            "com.example.androidapp.EXTRA_PRODUCT_TYPE ";
    public static final String EXTRA_PRODUCT_ID =
            "com.example.androidapp.EXTRA_PRODUCT_ID ";

    private RecyclerView priceQuantityRecycler;

    private Button confirmBtn;
    private List<PriceQuantity> priceQuantityList = new ArrayList<>();
    private Button backBtn;
    private String productName;
    private String productNameTmp;
    private String attribute1;
    private String attribute2;
    private String productType;

    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_quantity_price_product);

        initUI();
        //button back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //button add
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productId == -1) {
                    return;
                }

                ProductDetailViewModel productDetailViewModel = new ViewModelProvider(UpdateProductActivity3.this).get(ProductDetailViewModel.class);
                //delete old data
                AppDatabase.getInstance(UpdateProductActivity3.this).
                        productDetailDao().deleteAllProductDetailWithName(productNameTmp);
                //add data to detail
                for (PriceQuantity priceQuantity : priceQuantityList) {
                    for (PriceQuantityItem priceQuantityItem : priceQuantity.getAttribute2()) {
                        ProductDetail productDetail = new ProductDetail(productName,"", priceQuantity.getAttribute1(),
                                priceQuantityItem.getName(), priceQuantityItem.getPrice(),
                                priceQuantityItem.getQuantity());
                        productDetailViewModel.insert(productDetail);
                    }
                }
                ProductViewModel productViewModel = new ViewModelProvider(UpdateProductActivity3.this).get(ProductViewModel.class);
                Product product = new Product(productName, "", attribute1, attribute2, productType);
                product.setProductID(productId);
                productViewModel.update(product);

                setResult(RESULT_OK);

                onBackPressed();
            }
        });

        //get Intent
        Intent intent = getIntent();
        productName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
        productNameTmp = intent.getStringExtra(EXTRA_PRODUCT_NAME_TMP);
        productType = intent.getStringExtra(EXTRA_PRODUCT_TYPE);
        productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1);
        attribute1 = intent.getStringExtra(EXTRA_ATTRIBUTE_1);
        attribute2 = intent.getStringExtra(EXTRA_ATTRIBUTE_2);
        List<ProductAttributeItem> lstAttribut1 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_LIST_1);
        List<ProductAttributeItem> lstAttribut2 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_LIST_2);

        //2 attribute
        if (lstAttribut1 != null && lstAttribut2 != null) {
            for (int i = 0; i < lstAttribut1.size(); i++) {
                PriceQuantity priceQuantity = new PriceQuantity(lstAttribut1.get(i).getAttributeItemName(), new ArrayList<>());
                for (int j = 0; j < lstAttribut2.size(); j++) {
                    priceQuantity.getAttribute2().add(new PriceQuantityItem(lstAttribut2.get(j).getAttributeItemName(), 0, 0));
                }
                priceQuantityList.add(priceQuantity);
            }
        }
        //1 attribute
        else if (lstAttribut1 != null && lstAttribut2 == null) {
            for (int i = 0; i < lstAttribut1.size(); i++) {
                PriceQuantity priceQuantity = new PriceQuantity(lstAttribut1.get(i).getAttributeItemName(), new ArrayList<>());
                //for all attribute 2
                priceQuantity.getAttribute2().add(new PriceQuantityItem("All", 0, 0));
                priceQuantityList.add(priceQuantity);
            }
        }
        //0 attribute
        else {
            //for all attribute 1
            PriceQuantity priceQuantity = new PriceQuantity("All", new ArrayList<>());
            //for all attribute 2
            priceQuantity.getAttribute2().add(new PriceQuantityItem("All", 0, 0));
            priceQuantityList.add(priceQuantity);
        }

        //setup recylcer view

        priceQuantityRecycler.setLayoutManager(new LinearLayoutManager(this));
        PriceQuantityAdapter priceQuantityAdapter = new PriceQuantityAdapter(priceQuantityList);
        priceQuantityRecycler.setAdapter(priceQuantityAdapter);


    }



    void initUI() {
        backBtn = findViewById(R.id.back_btn);
        priceQuantityRecycler = findViewById(R.id.price_quantity_lst);
        confirmBtn = findViewById(R.id.modify_product_btn);
    }
}