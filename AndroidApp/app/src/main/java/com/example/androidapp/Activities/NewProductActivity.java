package com.example.androidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidapp.Data.ProductAttribute.ProductAttribute;
import com.example.androidapp.Data.ProductAttribute.ProductAttributeCategoryAdapter;
import com.example.androidapp.Data.ProductAttribute.ProductAttributeViewModel;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {
    private ProductAttributeViewModel productAttributeViewModel;
    private Spinner spinner;
    private Button btnBack;
    private ProductAttributeCategoryAdapter productAttributeCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        spinner = findViewById(R.id.type_list);
        btnBack = findViewById(R.id.btn_back);
        productAttributeCategoryAdapter = new ProductAttributeCategoryAdapter(this, R.layout.item_selected_spinner, getListCategory());
        spinner.setAdapter(productAttributeCategoryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //button back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private List<ProductAttribute> getListCategory() {
        //view model
        productAttributeViewModel = new ViewModelProvider(this).get(ProductAttributeViewModel.class);
        List<ProductAttribute> attributeLst = productAttributeViewModel.getAllProductAttribute();
        return attributeLst;
    }
}