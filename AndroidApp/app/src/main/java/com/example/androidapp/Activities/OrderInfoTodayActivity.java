package com.example.androidapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductData.ProductOrderAdapter;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoTodayActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID =
            "com.example.androidapp.EXTRA_ORDER_ID";
    public static final String EXTRA_ORDER_DATE =
            "com.example.androidapp.EXTRA_ORDER_DATE";
    public static final String EXTRA_ORDER_TIME =
            "com.example.androidapp.EXTRA_ORDER_TIME";
    public static final String EXTRA_ORDER_CLIENT =
            "com.example.androidapp.EXTRA_ORDER_CLIENT";
    public static final String EXTRA_ORDER_PRICE =
            "com.example.androidapp.EXTRA_ORDER_PRICE";

    public static final String EXTRA_CHECK_CONFIRM_SHIP =
            "com.example.androidapp.EXTRA_CHECK_CONFIRM_SHIP";
    public static final String EXTRA_ORDER_PRODUCT_LIST =
            "com.example.androidapp.EXTRA_ORDER_PRODUCT_LIST";
    public static final String EXTRA_CHECK_PAID =
            "com.example.androidapp.EXTRA_CHECK_PAID";
    public static final String EXTRA_CHECK_SHIP =
            "com.example.androidapp.EXTRA_CHECK_SHIP";

    private TextView tvOrderName;
    private TextView tvOrderAddress;
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private TextView tvOrderDate;
    private TextView tvOrderBank;
    private TextView tvOrderEmail;
    private TextView tvOrderPrice;
    private ImageView imageView;
    private Button btnBack;
    private Button btnShip;
    private Button btnAddDish;
    private LinearLayout checkBox;
    private  RelativeLayout checkIcon;
    private String imageDir;
    private boolean ship;
    private boolean beforePaid;
    private boolean currentPaid;
    private RecyclerView rcvData;
    private boolean updated;

    private String strOrderName;
    private String strOrderAddress;
    private String strOrderNumber ;
    private String strOrderDate ;
    private String strOrderTime;
    private String strOrderBank;
    private String strOrderEmail;
    private List<ProductDetail> mListProduct = new ArrayList<>();
    private final ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(mListProduct);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_today);
        //Reset
        currentPaid = false;
        beforePaid = false;
        updated = false;

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
            ship = intent.getBooleanExtra(EXTRA_CHECK_SHIP, ship);
            beforePaid = intent.getBooleanExtra(EXTRA_CHECK_PAID, beforePaid);
            currentPaid = beforePaid;
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
        productOrderAdapter.setProduct(mListProduct);

        //Check if Paid for checkbox:
        if (currentPaid){
            checkIcon.setVisibility(View.VISIBLE);
        }
        //Check if ship for disable button
        if (ship){
            btnShip.setVisibility(View.GONE);
        }

        //Convert to String
        strOrderName = tvOrderName.getText().toString().trim();
        strOrderAddress = tvOrderAddress.getText().toString().trim();
        strOrderNumber = tvOrderNumber.getText().toString().trim();
        strOrderTime = tvOrderTime.getText().toString().trim();
        strOrderDate = tvOrderDate.getText().toString().trim();
        strOrderBank = tvOrderBank.getText().toString().trim();
        strOrderEmail = tvOrderEmail.getText().toString().trim();

        //Checkbox to confirm paid
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIcon.getVisibility() == View.VISIBLE)
                    checkIcon.setVisibility(View.INVISIBLE);
                else checkIcon.setVisibility(View.VISIBLE);
                currentPaid = !currentPaid;
            }
        });



        //Ship button to confirm ship and show dialog confirm
        btnShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShipDialog();
            }
        });

        //Button back to OrderTodayFragment
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPaid != beforePaid) {
                    updated = true;
                }
                if (updated) {
                    Intent data = new Intent();
                    Client client = new Client(strOrderName, strOrderNumber, strOrderAddress, strOrderEmail, strOrderBank, imageDir);
                    data.putExtra(EXTRA_ORDER_CLIENT, client);
                    data.putExtra(EXTRA_CHECK_PAID, currentPaid);
                    data.putExtra(EXTRA_CHECK_SHIP, ship);
                    data.putExtra(EXTRA_ORDER_DATE, strOrderDate);
                    data.putExtra(EXTRA_ORDER_TIME, strOrderTime);
                    data.putParcelableArrayListExtra(EXTRA_ORDER_PRODUCT_LIST, (ArrayList<? extends Parcelable>) mListProduct);
                    int id = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
                    if (id != -1) {
                        data.putExtra(EXTRA_ORDER_ID, id);
                    }

                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });


    }

    private void showShipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_ship, (RelativeLayout) findViewById(R.id.layout_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        //ship btn
        view.findViewById(R.id.confirm_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent data = new Intent();
                Client client = new Client(strOrderName, strOrderNumber, strOrderAddress, strOrderEmail, strOrderBank, imageDir);
                data.putExtra(EXTRA_ORDER_CLIENT, client);
                data.putExtra(EXTRA_CHECK_SHIP, true);
                data.putExtra(EXTRA_CHECK_CONFIRM_SHIP, true);
                data.putExtra(EXTRA_CHECK_PAID, currentPaid);
                data.putExtra(EXTRA_ORDER_DATE, strOrderDate);
                data.putExtra(EXTRA_ORDER_TIME, strOrderTime);
                data.putParcelableArrayListExtra(EXTRA_ORDER_PRODUCT_LIST, (ArrayList<? extends Parcelable>) mListProduct);
//                data.putParcelableArrayListExtra(EXTRA_ORDER_DISH_LIST, (ArrayList<? extends Parcelable>) mListDish);
                int id = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
                if (id != -1) {
                    data.putExtra(EXTRA_ORDER_ID, id);
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });

        //cancel btn
        view.findViewById(R.id.cancel_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
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
        checkIcon = findViewById(R.id.check_ic);
        checkBox = findViewById(R.id.check_box);
        btnShip = findViewById(R.id.order_ship_btn);
//        btnAddDish = findViewById(R.id.new_dish_btn);
    }

    private void initRecyclerView() {
        //Dish view holder and recycler view and displaying
        rcvData = findViewById(R.id.order_product_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(staggeredGridLayoutManager);
        rcvData.setAdapter(productOrderAdapter);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHOOSE_DISH_REQUEST && resultCode == RESULT_OK) {
//            Dish dish = data.getParcelableExtra(SubMenuActivity.EXTRA_DISH);
//            int dishQuantity = data.getIntExtra(SubMenuActivity.EXTRA_DISH_QUANTITY, 0);
//            //check if dish existed
//            int checkExist = 0;
//            for (int i = 0; i < mListDish.size(); i++) {
//                if (mListDish.get(i).getName().equals(dish.getName()) &&
//                        mListDish.get(i).getPrice() == dish.getPrice()) {
//                    checkExist = 1;
//                    mListDish.get(i).setQuantity(mListDish.get(i).getQuantity() + dishQuantity);
//                }
//            }
//            if (checkExist == 0) {
//                dish.setQuantity(dishQuantity);
//                mListDish.add(dish);
//            }
//
//            int price = 0;
//            //generate id for all dish and update price
//            for (int i = 1; i <= mListDish.size(); i++) {
//                mListDish.get(i - 1).setDishID(i);
//                price += mListDish.get(i - 1).getPrice() * mListDish.get(i - 1).getQuantity();
//            }
//            //Change price textview
//            tvOrderPrice.setText(String.format("%,d", price));
//            //Display the chosen dish to the current order
//            dishOrderAdapter.setDish(mListDish);
//        }
    }
}
