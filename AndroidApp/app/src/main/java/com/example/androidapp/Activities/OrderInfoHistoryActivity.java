package com.example.androidapp.Activities;

import static com.example.androidapp.Activities.OrderInfoTodayActivity.EXTRA_ORDER_CLIENT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductOrderAdapter;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoHistoryActivity extends AppCompatActivity {
    public static final String EXTRA_ORDER_ID =
            "com.example.androidapp.EXTRA_ORDER_ID";
    public static final String EXTRA_ORDER_CLIENT =
            "com.example.androidapp.EXTRA_ORDER_CLIENT";

    public static final String EXTRA_ORDER_DATE =
            "com.example.androidapp.EXTRA_ORDER_DATE";
    public static final String EXTRA_ORDER_TIME =
            "com.example.androidapp.EXTRA_ORDER_TIME";
    public static final String EXTRA_ORDER_PRODUCT_LIST =
            "com.example.androidapp.EXTRA_ORDER_PRODUCT_LIST";
    public static final String EXTRA_ORDER_PRICE =
            "com.example.androidapp.EXTRA_ORDER_PRICE";



    private TextView tvOrderName;
    private TextView tvOrderPrice;
    private TextView tvOrderAddress;
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private TextView tvOrderDate;
    private TextView tvOrderBank;
    private TextView tvOrderEmail;
    private Button btnBack;
    private String imageDir;
    private RecyclerView rcvData;
    private List<ProductDetail> mListProduct = new ArrayList<>();
    private ImageView imageView;
    //info is view only
    final ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(mListProduct);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_history);

        initUi();
        initRecyclerView();
        //Get data from intent to display UI
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ORDER_ID)){
            Client client = intent.getParcelableExtra(EXTRA_ORDER_CLIENT);
            tvOrderName.setText(client.getClientName());
            tvOrderAddress.setText(client.getClientAddress());
            tvOrderNumber.setText(client.getClientNumber());
            tvOrderBank.setText(client.getClientBank());
            tvOrderEmail.setText(client.getClientEmail());
            int price = intent.getIntExtra(EXTRA_ORDER_PRICE, 0);
            tvOrderPrice.setText(String.format("%,d", price));
            tvOrderTime.setText(intent.getStringExtra(EXTRA_ORDER_TIME));
            tvOrderDate.setText(intent.getStringExtra(EXTRA_ORDER_DATE));
            imageDir = client.getImageDir();
            mListProduct = intent.getParcelableArrayListExtra(EXTRA_ORDER_PRODUCT_LIST);
            //read image from file
            if (imageDir != null) {
                try {
                    File f = new File(imageDir);
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    imageView.setImageBitmap(b);
                }
                catch (FileNotFoundException e) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ava_client_default);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
        //display list dish
        productOrderAdapter.setProduct(mListProduct);

        //Button back to UnpaidOrderFragment
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUi () {
        tvOrderPrice = findViewById(R.id.order_price);
        tvOrderName = findViewById(R.id.order_name);
        tvOrderAddress = findViewById(R.id.order_address);
        tvOrderNumber = findViewById(R.id.order_phone);
        tvOrderTime = findViewById(R.id.order_time);
        tvOrderBank = findViewById(R.id.order_bank);
        tvOrderEmail = findViewById(R.id.order_email);
        tvOrderDate = findViewById(R.id.order_date);
        imageView = findViewById(R.id.order_avatar);
        btnBack = findViewById(R.id.back_btn);
    }

    private void initRecyclerView() {
        //Dish view holder and recycler view and displaying
        rcvData = findViewById(R.id.order_product_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(staggeredGridLayoutManager);
        rcvData.setAdapter(productOrderAdapter);
    }
}
