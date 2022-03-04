package com.example.androidapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OrderInfoUpcomingActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID =
            "com.example.androidapp.EXTRA_ORDER_ID";
    public static final String EXTRA_ORDER_NAME =
            "com.example.androidapp.EXTRA_ORDER_NAME";
    public static final String EXTRA_ORDER_ADDRESS =
            "com.example.androidapp.EXTRA_ORDER_ADDRESS";
    public static final String EXTRA_ORDER_IMAGE =
            "com.example.androidapp.EXTRA_ORDER_IMAGE";
    public static final String EXTRA_ORDER_NUMBER =
            "com.example.androidapp.EXTRA_ORDER_NUMBER";
    public static final String EXTRA_ORDER_DATE =
            "com.example.androidapp.EXTRA_ORDER_DATE";
    public static final String EXTRA_ORDER_TIME =
            "com.example.androidapp.EXTRA_ORDER_TIME";
    public static final String EXTRA_CHECK_PAID =
            "com.example.androidapp.EXTRA_CHECK_PAID";
    public static final String EXTRA_ORDER_DISH_LIST =
            "com.example.androidapp.EXTRA_ORDER_DISH_LIST";
    public static final String EXTRA_ORDER_PRICE =
            "com.example.androidapp.EXTRA_ORDER_PRICE";
    public static final int CHOOSE_DISH_REQUEST= 1;

    private TextView tvOrderName;
    private TextView tvOrderPrice;
    private TextView tvOrderAddress;
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private TextView tvOrderDate;
    private ImageView imageView;
    private Button btnBack;

    private boolean beforePaid;
    private boolean currentPaid;
    private String imageDir;
    private LinearLayout checkBox;
    private RelativeLayout checkIcon;

    private RecyclerView rcvData;
//    private List<Dish> mListDish = new ArrayList<>();
//    final DishOrderAdapter dishOrderAdapter = new DishOrderAdapter(mListDish);
    private Button btnAddDish;
    private boolean updated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_upcoming);
        //Reset
        currentPaid = false;
        beforePaid = false;
        updated = false;

        initUi();
        initRecyclerView();



        //Get data from intent to display UI
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ORDER_ID)){
            tvOrderName.setText(intent.getStringExtra(EXTRA_ORDER_NAME));
            int price = intent.getIntExtra(EXTRA_ORDER_PRICE, 0);
            tvOrderPrice.setText(String.format("%,d", price));
            tvOrderAddress.setText(intent.getStringExtra(EXTRA_ORDER_ADDRESS));
            tvOrderTime.setText(intent.getStringExtra(EXTRA_ORDER_TIME));
            tvOrderNumber.setText(intent.getStringExtra(EXTRA_ORDER_NUMBER));
//            tvOrderDate.setText(intent.getStringExtra(EXTRA_ORDER_DATE));
            beforePaid = intent.getBooleanExtra(EXTRA_CHECK_PAID, beforePaid);
            currentPaid = beforePaid;
//            mListDish = intent.getParcelableArrayListExtra(EXTRA_ORDER_DISH_LIST);
            imageDir = intent.getStringExtra(EXTRA_ORDER_IMAGE);
            //read image from file
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
        //display list dish
//        dishOrderAdapter.setDish(mListDish);
//        //check for change to update price
//        dishOrderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
//            @Override
//            public void onChanged() {
//                //is updated
//                updated = true;
//                int price = 0;
//                //generate id for all dish and update price
//                for (Dish dish : mListDish) {
//                    price = price + dish.getPrice() * dish.getQuantity();
//                }
//                //Change price textview
//                tvOrderPrice.setText(String.format("%,d", price));
//            }
//        });

        //Check if Paid for checkbox:
        if (currentPaid){
            checkIcon.setVisibility(View.VISIBLE);
        }

        //Convert to String
        String strOrderName = tvOrderName.getText().toString().trim();
        String strOrderAddress = tvOrderAddress.getText().toString().trim();
        String strOrderNumber = tvOrderNumber.getText().toString().trim();
//        String strOrderDate = tvOrderDate.getText().toString().trim();
        String strOrderTime = tvOrderTime.getText().toString().trim();

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

        //Button back to OrderTodayFragment
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPaid != beforePaid) {
                    updated = true;
                }
                if (updated) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_CHECK_PAID, currentPaid);
                    data.putExtra(EXTRA_ORDER_NAME, strOrderName);
                    data.putExtra(EXTRA_ORDER_ADDRESS, strOrderAddress);
//                    data.putExtra(EXTRA_ORDER_DATE, strOrderDate);
                    data.putExtra(EXTRA_ORDER_TIME, strOrderTime);
                    data.putExtra(EXTRA_ORDER_NUMBER, strOrderNumber);
                    data.putExtra(EXTRA_ORDER_IMAGE, imageDir);
//                    data.putParcelableArrayListExtra(EXTRA_ORDER_DISH_LIST, (ArrayList<? extends Parcelable>) mListDish);
                    int id = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
                    if (id != -1) {
                        data.putExtra(EXTRA_ORDER_ID, id);
                    }
                    setResult(RESULT_OK, data);

                    //confirm sound
//                    final MediaPlayer sound_back = MediaPlayer.create(OrderInfoUpcomingActivity.this, R.raw.confirm_sound);
//                    //release resource when completed
//                    sound_back.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        public void onCompletion(MediaPlayer mp) {
//                            sound_back.release();
//                        }
//                    });
//                    sound_back.start();

                    finish();
                } else {
                    onBackPressed();
                }

            }
        });

        //Button to choose a new dish from menu
//        btnAddDish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OrderInfoUpcomingActivity.this, SubMenuActivity.class);
//                startActivityForResult(intent, CHOOSE_DISH_REQUEST);
//            }
//        });
    }

    private void initUi () {
        tvOrderPrice = findViewById(R.id.order_price);
        tvOrderName = findViewById(R.id.order_name);
        tvOrderAddress = findViewById(R.id.order_address);
//        tvOrderDate = findViewById(R.id.order_date);
        tvOrderNumber = findViewById(R.id.order_phone);
        tvOrderTime = findViewById(R.id.order_time);
        imageView = findViewById(R.id.order_avatar);
        btnBack = findViewById(R.id.btn_back);
        checkBox = findViewById(R.id.check_box);
        checkIcon = findViewById(R.id.check_ic);
        btnAddDish = findViewById(R.id.new_dish_btn);

    }

    private void initRecyclerView() {
        //Dish view holder and recycler view and displaying
        rcvData = findViewById(R.id.order_product_recycler);

//        rcvData.setAdapter(dishOrderAdapter);
        rcvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHOOSE_DISH_REQUEST && resultCode == RESULT_OK) {
//            Dish dish = data.getParcelableExtra(SubProductActivity.EXTRA_DISH);
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
