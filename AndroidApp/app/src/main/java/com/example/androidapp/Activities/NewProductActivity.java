package com.example.androidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.androidapp.ConfirmQuantityPriceProductActivity;
import com.example.androidapp.Data.ClientData.ClientAdapter;
import com.example.androidapp.Data.ProductAttribute.AddProductAttributeAdapter;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeCategoryAdapter;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {
    private ProductTypeViewModel productTypeViewModel;
    private Spinner spinner;
    private Button backBtn;
    private ProductTypeCategoryAdapter productTypeCategoryAdapter;
    private RecyclerView attributeLst;
    private Button addAttributeBtn;
    private Button nextBtn;

public class NewProductActivity extends AppCompatActivity {
    private ProductAttributeViewModel productAttributeViewModel;
    private Spinner spinner;
    private Button btnBack;
    private ProductAttributeCategoryAdapter productAttributeCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);


        initUI();


        productTypeCategoryAdapter = new ProductTypeCategoryAdapter(this, R.layout.item_selected_spinner, getListCategory());
        spinner.setAdapter(productTypeCategoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> attribute = new ArrayList<>();
        //setup recylcer view
        attributeLst.setLayoutManager(new LinearLayoutManager(this));
        AddProductAttributeAdapter attributeAdapter = new AddProductAttributeAdapter(attribute);
        attributeLst.setAdapter(attributeAdapter);

        //add attribute btn
        addAttributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attribute.add("New attribute");
                attributeAdapter.notifyDataSetChanged();
            }
        });

        //button back
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //button next
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewProductActivity.this, ConfirmQuantityPriceProductActivity.class);
                startActivity(intent);
            }
        });



    }


    private List<ProductType> getListCategory() {
        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        List<ProductType> attributeLst = productTypeViewModel.getAllProductType();
        return attributeLst;
    }

    void initUI() {
        spinner = findViewById(R.id.type_list);
        backBtn = findViewById(R.id.back_btn);
        nextBtn = findViewById(R.id.next_btn);
        attributeLst = findViewById(R.id.attribute_list);
        addAttributeBtn = findViewById(R.id.add_attribute);

    }
}