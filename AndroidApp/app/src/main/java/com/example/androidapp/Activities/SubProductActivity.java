package com.example.androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientSelectOnlyAdapter;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductSelectOnlyAdapter;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class SubProductActivity extends AppCompatActivity {




    private Button btnBack;

    private List<Product> mListProduct = new ArrayList<>();
    private ProductViewModel productViewModel;
    private RecyclerView rcvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        initUi();

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
                //use for filter
                productSelectOnlyAdapter.setProduct(products);
                //use for animation
                productSelectOnlyAdapter.submitList(products);
            }
        });

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

    }

    void initUi(){
        btnBack = findViewById(R.id.back_btn);
    }
}