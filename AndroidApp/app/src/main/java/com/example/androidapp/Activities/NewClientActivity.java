package com.example.androidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.HelperClass.ImageConverter;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewClientActivity extends AppCompatActivity {
    public static final String EXTRA_CLIENT = "com.example.androidapp.EXTRA_CLIENT";

    private Button btnBack;
    private Button btnAddClient;
    private Button btnAddImage;
    private EditText editClientName;
    private EditText editClientNumber;
    private EditText editClientAddress;
    private EditText editClientEmail;
    private EditText editClientBank;
    private ImageView imageView;
    private boolean changeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        //Default changeImage is false
        changeImg = false;
        //Init UI
        initUI();

        //status bar transparent
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

        //Add client button
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClient();
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
        btnBack = findViewById(R.id.back_button);
        btnAddClient = findViewById(R.id.btn_add_contact);
        imageView = findViewById(R.id.client_avatar);
        editClientName = findViewById(R.id.edit_name);
        editClientNumber = findViewById(R.id.edit_number);
        editClientAddress = findViewById(R.id.edit_address);
        editClientEmail = findViewById(R.id.edit_email);
        editClientBank = findViewById(R.id.edit_bank);
    }

    private void addClient(){
        String strClientName = editClientName.getText().toString().trim();
        String strClientNumber = editClientNumber.getText().toString().trim();
        String strClientAddress = editClientAddress.getText().toString().trim();
        String strClientEmail = editClientEmail.getText().toString().trim();
        String strClientBank = editClientBank.getText().toString().trim();

        //Check if fields are empty, if so then don't add to database
        if (TextUtils.isEmpty(strClientName) || TextUtils.isEmpty(strClientNumber) || TextUtils.isEmpty(strClientAddress)) {
            Toast.makeText(this, "Name, phone number and address are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        Client newClient = new Client(strClientName, strClientNumber, strClientAddress,
                strClientEmail, strClientBank, "");

        //Save image
        if (changeImg) {
            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            //Bitmap image = ImageConverter.getResizedBitmap(bitmap, IMAGE_SIZE);
            String fileName = strClientName + "-" + strClientAddress + "-" + strClientNumber;
            String imageDir = saveToInternalStorage(image, fileName);

            //Set the image dir to client
            newClient.setImageDir(imageDir);
            data.putExtra(EXTRA_CLIENT, newClient);

            //release memory
            image.recycle();
            //image.recycle();
        } else {
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ava_client_default);
            String fileName = strClientName + "-" + strClientAddress + "-" + strClientNumber;
            String imageDir = saveToInternalStorage(image, fileName);

            newClient.setImageDir(imageDir);
            data.putExtra(EXTRA_CLIENT, newClient);

            //release memory
            image.recycle();
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/your_app/app_data/imageClientDir
        File directory = cw.getDir("imageClientDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + '/' + fileName;
    }
}