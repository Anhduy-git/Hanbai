package com.example.androidapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewOrderActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_NAME =
            "com.example.androidapp.EXTRA_ORDER_NAME";
    public static final String EXTRA_ORDER_ADDRESS =
            "com.example.androidapp.EXTRA_ORDER_ADDRESS";
    public static final String EXTRA_ORDER_NUMBER =
            "com.example.androidapp.EXTRA_ORDER_NUMBER";
    public static final String EXTRA_ORDER_DATE =
            "com.example.androidapp.EXTRA_ORDER_DATE";
    public static final String EXTRA_ORDER_TIME =
            "com.example.androidapp.EXTRA_ORDER_TIME";
    public static final String EXTRA_ORDER_BANK =
            "com.example.androidapp.EXTRA_ORDER_BANK";
    public static final String EXTRA_ORDER_EMAIL =
            "com.example.androidapp.EXTRA_ORDER_EMAIL";
    public static final String EXTRA_ORDER_DISH_LIST =
            "com.example.androidapp.EXTRA_ORDER_DISH_LIST";
    public static final String EXTRA_ORDER_IMAGE =
            "com.example.androidapp.EXTRA_ORDER_IMAGE";
    public static final String EXTRA_ORDER_CLIENT =
            "com.example.androidapp.EXTRA_ORDER_CLIENT";

    public static final int CHOOSE_CLIENT_REQUEST = 1;
    public static final int CHOOSE_PRODUCT_REQUEST= 2;

    private EditText editOrderName;
    private TextView editOrderTime;
    private TextView editOrderDate;
    private TextView editOrderBank;
    private TextView editOrderEmail;
    private ImageView addOrderDate;
    private ImageView addOrderTime;
    private ImageView imageView;
    private EditText editOrderAddress;
    private EditText editOrderNumber;
    private Button btnAddOrder;
    private Button btnBack;
    private Button btnAddProduct;
    private TextView btnAddClient;
    private String imageDir = "";
    private RecyclerView rcvData;
//    private List<Dish> mListDish = new ArrayList<>();
//    final DishOrderAdapter dishOrderAdapter = new DishOrderAdapter(mListDish);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        initUi();
        initRecyclerView();

        setupDateTimePicker();

        //Confirm add order
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });

        //Button back to OrderTodayFragment
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        //Button to choose a new dish from menu
//        btnAddDish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewOrderActivity.this, SubMenuActivity.class);
//                startActivityForResult(intent, CHOOSE_DISH_REQUEST);
//            }
//        });

        //Button to choose client from contact
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrderActivity.this, SubClientActivity.class);
                startActivityForResult(intent, CHOOSE_CLIENT_REQUEST);
            }
        });
        //Button to choose product
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrderActivity.this, SubProductActivity.class);
                startActivityForResult(intent, CHOOSE_PRODUCT_REQUEST);
            }
        });
    }

    private void initUi () {
        editOrderName = findViewById(R.id.add_order_name);
        editOrderAddress = findViewById(R.id.add_order_address);
        editOrderNumber = findViewById(R.id.add_order_number);
        editOrderBank = findViewById(R.id.add_client_bank);
        editOrderEmail = findViewById(R.id.add_client_email);
        btnBack = findViewById(R.id.back_btn);
        btnAddOrder = findViewById(R.id.add_new_order);
        btnAddProduct = findViewById(R.id.new_product_btn);
        btnAddClient = findViewById(R.id.new_client_btn);
        editOrderDate = findViewById(R.id.add_order_date_tv);
        editOrderTime = findViewById(R.id.add_order_time_tv);
        addOrderDate = findViewById(R.id.add_order_date);
        addOrderTime = findViewById(R.id.add_order_time);
        imageView = findViewById(R.id.order_avatar);
    }

    private void initRecyclerView() {
        //Dish view holder and recycler view and displaying
        rcvData = findViewById(R.id.order_product_recycler);
        rcvData.setLayoutManager(new LinearLayoutManager(this));
//        rcvData.setAdapter(dishOrderAdapter);
    }

    private void setupDateTimePicker() {
        addOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editOrderDate);
            }
        });
        addOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(editOrderTime);
            }
        });
        editOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editOrderDate);
            }
        });
        editOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(editOrderTime);
            }
        });
    }

    private void showDateDialog(final TextView date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(NewOrderActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final TextView time_in) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(NewOrderActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }

    //Add order to database
    private void addOrder() {
        String strOrderName = editOrderName.getText().toString().trim();
        String strOrderAddress = editOrderAddress.getText().toString().trim();
        String strOrderNumber = editOrderNumber.getText().toString().trim();
        String strOrderDate = editOrderDate.getText().toString().trim();
        String strOrderTime = editOrderTime.getText().toString().trim();
        String strOrderEmail = editOrderEmail.getText().toString().trim();
        String strOrderBank = editOrderBank.getText().toString().trim();

        //Only compare the date
//        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //Check if fields are empty, if so then don't add to database
        if (TextUtils.isEmpty(strOrderName) || TextUtils.isEmpty(strOrderAddress)
                || TextUtils.isEmpty(strOrderNumber) || TextUtils.isEmpty(strOrderDate)
                || TextUtils.isEmpty(strOrderTime)) {
            Toast.makeText(this, "Xin hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

//        //check if order list is empty
//        if (mListDish == null || mListDish.isEmpty()) {
//            Toast.makeText(NewOrderActivity.this, "Xin hãy thêm món", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        //Get the current date
//        Date today = calendar.getTime();
//        //Get order date
//        try {
//            Date orderDate = simpleDateFormat.parse(strOrderDate);
//            //Check if the day is not in the pass
//            int ret = dateTimeComparator.compare(orderDate, today);
//            if (ret < 0){
//                Toast.makeText(this, "Thời gian không hợp lệ!", Toast.LENGTH_SHORT).show();
//                return;
//            }

            Intent data = new Intent();
            Client client = new Client(strOrderName, strOrderNumber, strOrderAddress, strOrderEmail, strOrderBank, imageDir);
            data.putExtra(EXTRA_ORDER_DATE, strOrderDate);
            data.putExtra(EXTRA_ORDER_TIME, strOrderTime);
            data.putExtra(EXTRA_ORDER_CLIENT, client);
//            data.putParcelableArrayListExtra(EXTRA_ORDER_DISH_LIST, (ArrayList<? extends Parcelable>) mListDish);

            setResult(RESULT_OK, data);

//            //confirm sound
//            final MediaPlayer sound = MediaPlayer.create(this, R.raw.confirm_sound);
//            //release resource when completed
//            sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    sound.release();
//                }
//            });
//            sound.start();
//
            finish();
//        } catch (ParseException ex) {
//            Toast.makeText(NewOrderActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//        }
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_CLIENT_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Client client = data.getParcelableExtra(SubClientActivity.EXTRA_CLIENT);

            //Display client's info after having chosen from existing contact
            editOrderName.setText(client.getClientName());
            editOrderNumber.setText(client.getClientNumber());
            editOrderAddress.setText(client.getClientAddress());
            editOrderBank.setText(client.getClientBank());
            editOrderEmail.setText(client.getClientEmail());
            imageDir = client.getImageDir();
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
//        else if (requestCode == CHOOSE_DISH_REQUEST && resultCode == RESULT_OK) {
//            Dish dish = data.getParcelableExtra(SubMenuActivity.EXTRA_DISH);
//            int dishQuantity = data.getIntExtra(SubMenuActivity.EXTRA_DISH_QUANTITY, 0);
//            //check if dish existed
//            int checkExist = 0;
//
//            for (int i = 0; i < mListDish.size(); i++) {
//                if (mListDish.get(i).getName().equals(dish.getName()) &&
//                        mListDish.get(i).getPrice() == dish.getPrice()) {
//                    checkExist = 1;
//                    mListDish.get(i).setQuantity(mListDish.get(i).getQuantity() + dishQuantity);
//                    break;
//                }
//            }
//
//            if (checkExist == 0) {
//                dish.setQuantity(dishQuantity);
//                mListDish.add(dish);
//            }
//            //generate id for all dish
//            for (int i = 1; i <= mListDish.size(); i++) {
//                mListDish.get(i - 1).setDishID(i);
//            }
//            //Display the chosen dish to the current order
//            dishOrderAdapter.setDish(mListDish);
//        }
    }
}