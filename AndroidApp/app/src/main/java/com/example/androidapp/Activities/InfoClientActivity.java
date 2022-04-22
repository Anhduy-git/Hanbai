package com.example.androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidapp.Data.ClientData.Client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.Data.ProductDetailData.ProductDetailViewModel;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.HelperClass.PriceQuantity;
import com.example.androidapp.HelperClass.PriceQuantityAdapter;
import com.example.androidapp.HelperClass.PriceQuantityItem;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.R;
import com.google.android.flexbox.FlexboxLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class InfoClientActivity extends AppCompatActivity {
    public static final String EXTRA_CLIENT =
            "com.example.androidapp.EXTRA_CLIENT";

    private TextView tvClientName;
    private TextView tvClientNumber;
    private TextView tvClientAddress;
    private TextView tvClientEmail;
    private TextView tvClientBank;
    private ImageView imageView;
    private Button btnAddImage;
    private Button btnUpdateClient;
    private Button btnBack;
    private Button btnEdit;

    private Client client;
    private ClientViewModel clientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        initUI();
        displayInfo();
        clientViewModel = new ViewModelProvider(InfoClientActivity.this).get(ClientViewModel.class);

        //statusbar transparent
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowsFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowsFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoClientActivity.this, UpdateClientActivity.class);
                intent.putExtra(UpdateClientActivity.EXTRA_CLIENT, client);
                activityResultLauncher.launch(intent);
            }
        });

    }

    private static void setWindowsFlag(Activity activity, final int Bits, Boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams Winparams = win.getAttributes();
        if (on) {
            Winparams.flags |=Bits;
        } else {
            Winparams.flags &= ~Bits;
        }
        win.setAttributes(Winparams);
    }

    private void initUI(){
        tvClientName = findViewById(R.id.view_client_name);
        tvClientNumber = findViewById(R.id.view_client_phone);
        tvClientAddress = findViewById(R.id.view_client_address);
        tvClientEmail = findViewById(R.id.order_email);
        tvClientBank = findViewById(R.id.view_client_bank);
        imageView = findViewById(R.id.view_client_avatar);
        btnBack = findViewById(R.id.back_btn);
        btnEdit = findViewById(R.id.btn_edit);
    }

    private void displayInfo(){
        Intent intent = getIntent();
        client = intent.getParcelableExtra(EXTRA_CLIENT);

        tvClientName.setText(client.getClientName());
        tvClientNumber.setText(client.getClientNumber());
        tvClientAddress.setText(client.getClientAddress());
        tvClientEmail.setText(client.getClientEmail());
        tvClientBank.setText(client.getClientBank());

        if (client.getImageDir() != null) {
            try {
                //read path image from intent and display
                File f = new File(client.getImageDir());
                Bitmap image = BitmapFactory.decodeStream(new FileInputStream(f));
//            imageView.setRotation(90);
                imageView.setImageBitmap(image);
            }
            catch (FileNotFoundException e) {
                //display default image for client
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ava_client_default);
                imageView.setImageBitmap(image);
            }
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == -1) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Client clientUpdate = intent.getParcelableExtra(UpdateClientActivity.EXTRA_CLIENT);
                            clientViewModel.updateClient(clientUpdate);

                            tvClientName.setText(clientUpdate.getClientName());
                            tvClientNumber.setText(clientUpdate.getClientNumber());
                            tvClientAddress.setText(clientUpdate.getClientAddress());
                            tvClientEmail.setText(clientUpdate.getClientEmail());
                            tvClientBank.setText(clientUpdate.getClientBank());

                            try {
                                //read path image from intent and display
                                File f = new File(clientUpdate.getImageDir());
                                Bitmap image = BitmapFactory.decodeStream(new FileInputStream(f));
                                imageView.setImageBitmap(image);
                            }
                            catch (FileNotFoundException e) {
                                //display default image for client
                                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ava_client_default);
                                imageView.setImageBitmap(image);
                            }
                        }
                    }
                }
            }
    );

    public static class AddQuantityPriceProductActivity extends AppCompatActivity {
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

    public static class ConfirmProductActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_confirm_product);
        }
    }

    public static class PickTypeActivity extends AppCompatActivity {

        private Button confirmBtn;
        private FlexboxLayout lst1;
        private ProductTypeViewModel productTypeViewModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pick_type);

            //view model
            productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);

            //init view
            confirmBtn = findViewById(R.id.confirm_btn);
            lst1 = findViewById(R.id.lst1);

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAllAttribute(lst1);
                    Intent intent = new Intent(PickTypeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        }
        public void checkAllAttribute(FlexboxLayout lst) {
            for (int i = 0; i < lst.getChildCount(); i++) {
                final CheckBox attribute = (CheckBox)lst.getChildAt(i);
                if (attribute.isChecked()) {
                    ProductType productType = new ProductType(attribute.getText().toString());
                    productTypeViewModel.insert(productType);
                }
            }
        }

    }

    public static class ProductInfoActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_info);

        }
    }

    public static class QuantityPriceActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quantity_price);
        }
    }
}
