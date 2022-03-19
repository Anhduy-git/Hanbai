package com.example.androidapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.androidapp.AddQuantityPriceProductActivity;
import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeCategoryAdapter;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {
    private ProductTypeViewModel productTypeViewModel;
    private Spinner spinner;
    private Button backBtn;
    private ProductTypeCategoryAdapter productTypeCategoryAdapter;
    private RecyclerView attributeLst;
    private Button addAttributeBtn;
    private Button nextBtn;
    private EditText productName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        initUI();
        //get list type
        List<ProductType> productTypeList = getListCategory();

        productTypeCategoryAdapter = new ProductTypeCategoryAdapter(this, R.layout.item_selected_spinner, productTypeList);
        spinner.setAdapter(productTypeCategoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<ProductAttribute> attribute = new ArrayList<>();
        //setup recylcer view
        attributeLst.setLayoutManager(new LinearLayoutManager(this));
        ProductAttributeAdapter attributeAdapter = new ProductAttributeAdapter(attribute);
        attributeLst.setAdapter(attributeAdapter);

        //add attribute
        addAttributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductAttribute productAttribute = new ProductAttribute("New Attribute", new ArrayList<>());

                attribute.add(new ProductAttribute("New Attribute", new ArrayList<>()));
                attributeAdapter.notifyDataSetChanged();
            }
        });
        //delete attribute
        attributeAdapter.setOnItemClickDelListener(new ProductAttributeAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(ProductAttribute productAttribute) {
                attribute.remove(productAttribute);
                attributeAdapter.notifyDataSetChanged();
            }
        });
        //add attribute item
        attributeAdapter.setOnItemClickAddListener(new ProductAttributeAdapter.OnItemClickAddListener() {
            @Override
            public void onItemClickAdd(int position) {
                attribute.get(position).getProductAttributeItemList().add(new ProductAttributeItem("item"));
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
                Integer idx = spinner.getSelectedItemPosition();
                String strProductType = productTypeList.get(idx).getName();
                String strProductName = productName.getText().toString().trim();

                Intent intent = new Intent(NewProductActivity.this, AddQuantityPriceProductActivity.class);
                //put data
                intent.putExtra(AddQuantityPriceProductActivity.EXTRA_PRODUCT_NAME, strProductName);
                intent.putExtra(AddQuantityPriceProductActivity.EXTRA_PRODUCT_TYPE, strProductType);
                if (attribute.size() == 1) {
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                } else if (attribute.size() == 2) {
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_2, attribute.get(1).getAttributeTitle());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_2, (ArrayList<? extends Parcelable>) attribute.get(1).getProductAttributeItemList());
                }
                startActivityForResult(intent, 1);
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            onBackPressed();
        }
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
        productName = findViewById(R.id.product_name);
    }
}