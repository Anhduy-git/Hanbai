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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidapp.Data.ClientData.Client;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        initUI();
        displayInfo();

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
        btnBack = findViewById(R.id.btn_back);
    }

    private void displayInfo(){
        Intent intent = getIntent();
        Client client = intent.getParcelableExtra(EXTRA_CLIENT);

        tvClientName.setText(client.getClientName());
        tvClientNumber.setText(client.getClientNumber());
        tvClientAddress.setText(client.getClientAddress());
        tvClientEmail.setText(client.getClientEmail());
        tvClientBank.setText(client.getClientBank());

        try {
            //read path image from intent and display
            File f = new File(client.getImageDir());
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
