package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidapp.Activities.MainActivity;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.Data.ProductDetailData.ProductDetailViewModel;
import com.example.androidapp.Fragments.ProductFragment;
import com.example.androidapp.HelperClass.PriceQuantity;
import com.example.androidapp.HelperClass.PriceQuantityAdapter;
import com.example.androidapp.HelperClass.PriceQuantityItem;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.HelperClass.ProductAttributeItem;

import java.util.ArrayList;
import java.util.List;


public class AddQuantityPriceProductActivity extends AppCompatActivity {
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
    public static final String EXTRA_PRODUCT_TYPE =
            "com.example.androidapp.EXTRA_PRODUCT_TYPE ";

    private RecyclerView priceQuantityRecycler;

    private Button addBtn;
    private List<PriceQuantity> priceQuantityList = new ArrayList<>();
    private Button backBtn;
    private String productName;
    private String attribute1;
    private String attribute2;
    private String productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_price);

        initUI();
        //button back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //button add
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add data to detail
                ProductDetailViewModel productDetailViewModel = new ViewModelProvider(AddQuantityPriceProductActivity.this).get(ProductDetailViewModel.class);
                for (PriceQuantity priceQuantity : priceQuantityList) {
                    for (PriceQuantityItem priceQuantityItem : priceQuantity.getAttribute2()) {
                        ProductDetail productDetail = new ProductDetail(productName,"", priceQuantity.getAttribute1(),
                                priceQuantityItem.getName(), priceQuantityItem.getPrice(),
                                priceQuantityItem.getQuantity());
                        productDetailViewModel.insert(productDetail);
                    }
                }
                ProductViewModel productViewModel = new ViewModelProvider(AddQuantityPriceProductActivity.this).get(ProductViewModel.class);
                Product product = new Product(productName, "", attribute1, attribute2, productType);
                productViewModel.insert(product);

                setResult(RESULT_OK);

                onBackPressed();
            }
        });

        //get Intent
        Intent intent = getIntent();
        productName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
        productType = intent.getStringExtra(EXTRA_PRODUCT_TYPE);
        attribute1 = intent.getStringExtra(EXTRA_ATTRIBUTE_1);
        attribute2 = intent.getStringExtra(EXTRA_ATTRIBUTE_2);
        List<ProductAttributeItem> lstAttribut1 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_LIST_1);
        List<ProductAttributeItem> lstAttribut2 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_LIST_2);

        //2 attribute
        if (lstAttribut1 != null && lstAttribut2 != null) {
            for (int i = 0; i < lstAttribut1.size(); i++) {
                PriceQuantity priceQuantity = new PriceQuantity(lstAttribut1.get(i).getAttributeItemName(), new ArrayList<>());
                for (int j = 0; j < lstAttribut2.size(); j++) {
                    priceQuantity.getAttribute2().add(new PriceQuantityItem(lstAttribut2.get(j).getAttributeItemName(), -1, -1));
                }
                priceQuantityList.add(priceQuantity);
            }
        }
        //1 attribute
        else if (lstAttribut1 != null && lstAttribut2 == null) {
            for (int i = 0; i < lstAttribut1.size(); i++) {
                PriceQuantity priceQuantity = new PriceQuantity(lstAttribut1.get(i).getAttributeItemName(), new ArrayList<>());
                //for all attribute 2
                priceQuantity.getAttribute2().add(new PriceQuantityItem("All", -1, -1));
                priceQuantityList.add(priceQuantity);
            }
        }
        //0 attribute
        else {
            //for all attribute 1
            PriceQuantity priceQuantity = new PriceQuantity("All", new ArrayList<>());
            //for all attribute 2
            priceQuantity.getAttribute2().add(new PriceQuantityItem("All", -1, -1));
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
        addBtn = findViewById(R.id.add_product_btn);
    }
}