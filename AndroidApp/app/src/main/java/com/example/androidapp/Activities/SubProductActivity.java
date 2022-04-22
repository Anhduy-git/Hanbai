package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientSelectOnlyAdapter;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductSelectOnlyAdapter;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.HelperClass.ProductInfoAttributeAdapter;
import com.example.androidapp.HelperClass.ProductOrderSelectAttributeAdapter;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class SubProductActivity extends AppCompatActivity {


    public static final String EXTRA_PRODUCT =
            "com.example.androidapp.EXTRA_PRODUCT";
    public static final String EXTRA_PRODUCT_QUANTITY =
            "com.example.androidapp.EXTRA_PRODUCT_QUANTITY";

    private Button btnBack;

    private List<Product> mListProduct = new ArrayList<>();
    private ProductViewModel productViewModel;
    private RecyclerView rcvData;
    private RecyclerView rcvAttribute;
    private EditText productQuantity;
    private List<ProductAttribute> attributeList = new ArrayList<>();
    private ProductOrderSelectAttributeAdapter attributeAdapter;
    private EditText editSearchBar;
    private Button addProductBtn;
    private ProductDetail curProductDetailSelected;
    private Button btnDecrease;
    private Button btnIncrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        initUi();

        //product List
        //Create Recycler View
        rcvData = findViewById(R.id.product_lst);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(staggeredGridLayoutManager);

        final ProductSelectOnlyAdapter productSelectOnlyAdapter = new ProductSelectOnlyAdapter(mListProduct);
        rcvData.setAdapter(productSelectOnlyAdapter);

        //Create client view model
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productSelectOnlyAdapter.setProduct(products);
            }
        });
        //attribute list
        //setup recylcer view
        rcvAttribute.setLayoutManager(new LinearLayoutManager(this));
        attributeAdapter = new ProductOrderSelectAttributeAdapter(attributeList);
        rcvAttribute.setAdapter(attributeAdapter);


//        clientSelectOnlyAdapter.setOnItemClickListener(new ClientSelectOnlyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Client client) {
//                Intent data = new Intent();
//                data.putExtra(EXTRA_CLIENT, client);
//
//                setResult(RESULT_OK, data);
//                finish();
//            }
//        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(productQuantity.getText().toString());
                if (quantity <= curProductDetailSelected.getQuantity()) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_PRODUCT, curProductDetailSelected);
                    data.putExtra(EXTRA_PRODUCT_QUANTITY, quantity);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

        //Highlight an item on click
        productSelectOnlyAdapter.setOnItemClickListener(new ProductSelectOnlyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                handleOnClick(product);
            }
        });

        //Create search bar listener for SEARCH METHOD
        editSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Empty on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Empty on purpose
            }

            @Override
            public void afterTextChanged(Editable s) {
                productSelectOnlyAdapter.getFilter().filter(s.toString());
            }
        });
        //Button increase dish quantity
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productQuantity.setText(String.valueOf(Integer.parseInt(productQuantity.getText().toString()) + 1));
            }
        });

        //Button decrease dish quantity, minimum is 1
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(productQuantity.getText().toString()) > 1) {
                    productQuantity.setText(String.valueOf(Integer.parseInt(productQuantity.getText().toString()) - 1));
                }
            }
        });

    }

    void initUi(){
        btnBack = findViewById(R.id.back_btn);
        productQuantity = findViewById(R.id.product_quantity);
        productQuantity.setText("1");
        rcvAttribute = findViewById(R.id.attribute_list);
        editSearchBar = findViewById(R.id.product_search_bar);
        addProductBtn = findViewById(R.id.add_product_btn);
        btnDecrease = findViewById(R.id.decrease_btn);
        btnIncrease = findViewById(R.id.increase_btn);
    }


    private void handleOnClick(Product product) {
        String attribute1 = product.getAttribute1();
        String attribute2 = product.getAttribute2();
        List<ProductAttributeItem>attribute1ItemList = new ArrayList<>();
        List<ProductAttributeItem>attribute2ItemList = new ArrayList<>();
        attributeList = new ArrayList<>();
        attributeAdapter = new ProductOrderSelectAttributeAdapter(attributeList);
        rcvAttribute.setAdapter(attributeAdapter);

        //Query data
        List<ProductDetail> productDetails = AppDatabase.getInstance(SubProductActivity.this).
                productDetailDao().getAllProductDetail(product.getName());
        //init current selected item
        curProductDetailSelected = productDetails.get(0);
        //init selected item
        final String[] attributeItemSelected1 = {productDetails.get(0).getAttribute1()};
        final String[] attributeItemSelected2 = {productDetails.get(0).getAttribute2()};
        //init price, quantity
//        productPrice.setText(Integer.toString(productDetails.get(0).getPrice()));
        productQuantity.setText("1");

        //2 attribute
        if (attribute1 != null && attribute2 != null) {

            //Algorithm for filter attribute
            boolean addFullAtt2 = false;
            for (int i = 0; i < productDetails.size(); i++) {
                if (i == 0) {
                    attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));
                    attribute2ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute2()));
                } else {
                    if (!productDetails.get(i).getAttribute1().equals(productDetails.get(i - 1).getAttribute1())) {
                        attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));
                        addFullAtt2 = true;
                    } else if (!addFullAtt2) {
                        attribute2ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute2()));
                    }
                }
            }
            attributeList.add(new ProductAttribute(attribute1, attribute1ItemList));
            attributeList.add(new ProductAttribute(attribute2, attribute2ItemList));
            attributeAdapter.notifyDataSetChanged();
            //on item click
            attributeAdapter.setRvItemClickListener(new ProductOrderSelectAttributeAdapter.RvItemClickListener() {
                @Override
                public void onChildItemClick(int parentPosition, int childPosition, ProductAttributeItem item) {

                    if (parentPosition == 0) {
                        attributeItemSelected1[0] = item.getAttributeItemName();
                    } else if (parentPosition == 1){
                        attributeItemSelected2[0] = item.getAttributeItemName();
                    }
                    for (ProductDetail productDetail : productDetails) {
                        if (productDetail.getAttribute1().equals(attributeItemSelected1[0]) &&
                                productDetail.getAttribute2().equals(attributeItemSelected2[0])) {
//                            productPrice.setText(Integer.toString(productDetail.getPrice()));
                            productQuantity.setText("1");
                            curProductDetailSelected = productDetail;
                        }
                    }
                }
            });

        }
        //1 attribute
        else if (attribute1 != null && attribute2 == null) {
            //Algorithm for filter attribute

            for (int i = 0; i < productDetails.size(); i++) {
                attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));

            }
            attributeList.add(new ProductAttribute(attribute1, attribute1ItemList));
            attributeAdapter.notifyDataSetChanged();
            //on item click
            attributeAdapter.setRvItemClickListener(new ProductOrderSelectAttributeAdapter.RvItemClickListener() {
                @Override
                public void onChildItemClick(int parentPosition, int childPosition, ProductAttributeItem item) {

                    attributeItemSelected1[0] = item.getAttributeItemName();
                    for (ProductDetail productDetail : productDetails) {
                        if (productDetail.getAttribute1().equals(attributeItemSelected1[0])) {
//                            productPrice.setText(Integer.toString(productDetail.getPrice()));
                            productQuantity.setText("1");
                            curProductDetailSelected = productDetail;
                        }
                    }
                }
            });
        }
        //0 attribute
        else {
//            productPrice.setText(Integer.toString(productDetails.get(0).getPrice()));
            curProductDetailSelected = productDetails.get(0);
            productQuantity.setText("1");
        }
    }
}