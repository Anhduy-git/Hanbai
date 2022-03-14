package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidapp.HelperClass.PriceQuantity;
import com.example.androidapp.HelperClass.PriceQuantityAdapter;
import com.example.androidapp.HelperClass.PriceQuantityItem;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.HelperClass.ProductAttributeItem;

import java.util.ArrayList;
import java.util.List;


public class AddQuantityPriceProductActivity extends AppCompatActivity {
    public static final String EXTRA_ATTRIBUTE_1 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_1 ";
    public static final String EXTRA_ATTRIBUTE_2 =
            "com.example.androidapp.EXTRA_ATTRIBUTE_2 ";
    private RecyclerView priceQuantityRecycler;



    private Button backBtn;
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

        //get Intent
        Intent intent = getIntent();
        List<ProductAttributeItem> lstAttribut1 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_1);
        List<ProductAttributeItem> lstAttribut2 = intent.getParcelableArrayListExtra(EXTRA_ATTRIBUTE_2);
        List<PriceQuantity> priceQuantityList = new ArrayList<>();
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
        priceQuantityAdapter.setPriceQuantity(priceQuantityList);

    }



    void initUI() {
        backBtn = findViewById(R.id.back_btn);
        priceQuantityRecycler = findViewById(R.id.price_quantity_lst);
    }
}