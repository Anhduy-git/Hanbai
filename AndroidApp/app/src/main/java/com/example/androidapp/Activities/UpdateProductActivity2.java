package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.AddQuantityPriceProductActivity;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeCategoryAdapter;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductActivity2 extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_TYPE =
            "com.example.androidapp.EXTRA_PRODUCT_TYPE ";
    public static final String EXTRA_PRODUCT_ID =
            "com.example.androidapp.EXTRA_PRODUCT_ID ";
    public static final String EXTRA_PRODUCT_NAME =
            "com.example.androidapp.EXTRA_PRODUCT_NAME ";
    public static final String EXTRA_PRODUCT_ATTRIBUTE1 =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE1 ";
    public static final String EXTRA_PRODUCT_ATTRIBUTE2 =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE2 ";
    public static final String EXTRA_PRODUCT_ATTRIBUTE1_LIST =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE1_LIST ";
    public static final String EXTRA_PRODUCT_ATTRIBUTE2_LIST =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE2_LIST ";
    public static final Integer CONFIRM_REQUEST = 3;


    private ProductTypeViewModel productTypeViewModel;
    private Spinner spinner;
    private Button backBtn;
    private ProductTypeCategoryAdapter productTypeCategoryAdapter;
    private RecyclerView attributeLst;
    private Button addAttributeBtn;
    private Button nextBtn;
    private EditText productName;
    private String strProductName;
    private String strProductNameTmp;
    private String strProductType;
    private List<ProductAttributeItem>attribute1ItemList = new ArrayList<>();
    private List<ProductAttributeItem>attribute2ItemList = new ArrayList<>();
    private String attribute1, attribute2;
    private int productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        //get Intent
        Intent intent = getIntent();
        strProductName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
        //temp value of name
        strProductNameTmp = strProductName;
        strProductType = intent.getStringExtra(EXTRA_PRODUCT_TYPE);
        productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1);
        attribute1 = intent.getStringExtra(EXTRA_PRODUCT_ATTRIBUTE1);
        attribute2 = intent.getStringExtra(EXTRA_PRODUCT_ATTRIBUTE2);
        attribute1ItemList = intent.getParcelableArrayListExtra(EXTRA_PRODUCT_ATTRIBUTE1_LIST);
        attribute2ItemList = intent.getParcelableArrayListExtra(EXTRA_PRODUCT_ATTRIBUTE2_LIST);

        initUI();
        //set name
        productName.setText(strProductName);
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

        //set spinner item selected
        spinner.setSelection(getIdx(productTypeList));

        List<ProductAttribute> attribute = new ArrayList<>();
        //setup recylcer view
        attributeLst.setLayoutManager(new LinearLayoutManager(this));
        ProductAttributeAdapter attributeAdapter = new ProductAttributeAdapter(attribute);
        attributeLst.setAdapter(attributeAdapter);



        //add attribute
        if (attribute1 != null) {
            attribute.add(new ProductAttribute(attribute1, attribute1ItemList));
            attributeAdapter.notifyDataSetChanged();
        }
        if (attribute2 != null) {
            attribute.add(new ProductAttribute(attribute2, attribute2ItemList));
            attributeAdapter.notifyDataSetChanged();
        }
        //gone add btn
        if (attribute.size() == 2) {
            addAttributeBtn.setVisibility(View.GONE);
        }


        addAttributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attribute.add(new ProductAttribute("New Attribute", new ArrayList<>()));
                attributeAdapter.notifyDataSetChanged();
                if (attribute.size() == 2) {
                    addAttributeBtn.setVisibility(View.GONE);
                }
            }
        });
        //delete attribute
        attributeAdapter.setOnItemClickDelListener(new ProductAttributeAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(ProductAttribute productAttribute) {
                attribute.remove(productAttribute);
                attributeAdapter.notifyDataSetChanged();
                //add btn visible
                addAttributeBtn.setVisibility(View.VISIBLE);
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
                strProductType = productTypeList.get(idx).getName();
                strProductName = productName.getText().toString().trim();

                Intent intent = new Intent(UpdateProductActivity2.this, UpdateProductActivity3.class);
                //put data
                intent.putExtra(UpdateProductActivity3.EXTRA_PRODUCT_NAME, strProductName);
                intent.putExtra(UpdateProductActivity3.EXTRA_PRODUCT_NAME_TMP, strProductNameTmp);
                intent.putExtra(UpdateProductActivity3.EXTRA_PRODUCT_TYPE, strProductType);
                intent.putExtra(UpdateProductActivity3.EXTRA_PRODUCT_ID, productId);
                if (attribute.size() == 1) {
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                } else if (attribute.size() == 2) {
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_2, attribute.get(1).getAttributeTitle());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                    intent.putParcelableArrayListExtra(AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_2, (ArrayList<? extends Parcelable>) attribute.get(1).getProductAttributeItemList());
                }
                startActivityForResult(intent, CONFIRM_REQUEST);
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONFIRM_REQUEST && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            onBackPressed();
        }
    }


    private List<ProductType> getListCategory() {
        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        List<ProductType> attributeLst = productTypeViewModel.getAllProductType();
        return attributeLst;
    }
    private int getIdx(List<ProductType> productTypeList) {

        for (int i = 0; i < productTypeList.size(); i++) {
            if (productTypeList.get(i).getName().equals(strProductType)) {
                return i;
            }
        }
        return -1;
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