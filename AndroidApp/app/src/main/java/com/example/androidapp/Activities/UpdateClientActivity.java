package com.example.androidapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientViewModel;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpdateClientActivity extends AppCompatActivity {
    public static final String EXTRA_CLIENT =
            "com.example.androidapp.EXTRA_CLIENT";

    private final int GALLERY_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;

    private ImageView imageView;
    private Button btnBack;
    private Button btnConfirm;
    private Button btnCamera;
    private Button btnGallery;
    private EditText editName;
    private EditText editNumber;
    private EditText editAddress;
    private EditText editEmail;
    private EditText editBank;
    private String currentPhotoPath;
    private Client client;
    private boolean changeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_client);
        changeImg = false;
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClient();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureFromGallery();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequestPermission()){
                    takePictureFromCamera();
                }
            }
        });
    }

    private static void setWindowsFlag (Activity activity,final int Bits, Boolean on){
        Window win = activity.getWindow();
        WindowManager.LayoutParams Winparams = win.getAttributes();
        if (on) {
            Winparams.flags |= Bits;
        } else {
            Winparams.flags &= ~Bits;
        }
        win.setAttributes(Winparams);
    }

    private void initUI(){
        imageView = findViewById(R.id.client_avatar);
        btnBack = findViewById(R.id.btn_back);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCamera = findViewById(R.id.btn_camera);
        btnGallery = findViewById(R.id.btn_gallery);
        editName = findViewById(R.id.edit_name);
        editNumber = findViewById(R.id.edit_number);
        editAddress = findViewById(R.id.edit_address);
        editEmail = findViewById(R.id.edit_email);
        editBank = findViewById(R.id.edit_bank);
    }

    private void displayInfo(){
        Intent intent = getIntent();
        client = intent.getParcelableExtra(EXTRA_CLIENT);

        editName.setText(client.getClientName());
        editNumber.setText(client.getClientNumber());
        editAddress.setText(client.getClientAddress());
        editEmail.setText(client.getClientEmail());
        editBank.setText(client.getClientBank());

        try {
            //read path image from intent and display
            File f = new File(client.getImageDir());
            Bitmap image = BitmapFactory.decodeStream(new FileInputStream(f));
            //imageView.setRotation(90);
            imageView.setImageBitmap(image);
        }
        catch (FileNotFoundException e) {
            //display default image for client
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ava_client_default);
            imageView.setImageBitmap(image);
        }
    }

    private void updateClient(){
        String strClientName = editName.getText().toString().trim();
        String strClientNumber = editNumber.getText().toString().trim();
        String strClientAddress = editAddress.getText().toString().trim();
        String strClientEmail = editEmail.getText().toString().trim();
        String strClientBank = editBank.getText().toString().trim();

        if (TextUtils.isEmpty(strClientName) || TextUtils.isEmpty(strClientNumber) || TextUtils.isEmpty(strClientAddress)) {
            Toast.makeText(this, "Name, number and address are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        Client clientUpdate = new Client(strClientName, strClientNumber, strClientAddress,
                strClientEmail, strClientBank, "");

        int id = client.getClientId();
        if (id != -1){
            clientUpdate.setClientId(id);
        } else {
            Toast.makeText(UpdateClientActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkClientAvailableForUpdate(clientUpdate)) {
            File oldImage = new File(clientUpdate.getImageDir());
            boolean deleted = oldImage.delete();

            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            //Bitmap image = ImageConverter.getResizedBitmap(bitmap, IMAGE_SIZE);
            String fileName = strClientName + "-" + strClientAddress + "-" + strClientNumber;
            String imageDir = saveToInternalStorage(image, fileName);

            //Set the image dir to client
            clientUpdate.setImageDir(imageDir);
            intent.putExtra(EXTRA_CLIENT, clientUpdate);

            //release memory
            image.recycle();
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean checkClientAvailableForUpdate(@NonNull Client client) {
        List<Client> list  = AppDatabase.getInstance(UpdateClientActivity.this).clientDao().checkClientExist(client.getClientName(),
                client.getClientAddress(), client.getClientNumber(), client.getClientEmail(), client.getClientBank());
        return (list == null) || (list.size() == 0) || (list.size() == 1 && list.get(0).getClientId() == client.getClientId());
    }

    private boolean checkRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(UpdateClientActivity.this,
                    Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        UpdateClientActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST);
                return false;
            }
        }
        return true;
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageClientDir
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

    private void takePictureFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST);
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(16, 10)
//                .start(this);
    }

    private void takePictureFromCamera(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.androidapp.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
        //activityResultLauncher.launch(intent);
//        CropImage.activity()
//                .setAspectRatio(16, 10)
//                .setAllowRotation(true)
//                .start(NewClientActivity.this);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST) {
            //CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageView.setRotation(0);
                try {
                    changeImg = true;
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == CAMERA_REQUEST){
            if (resultCode == RESULT_OK) {
                imageView.setRotation(0);

                try {
                    //set changed Img
                    changeImg = true;
                    //Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = BitmapFactory.decodeFile(currentPhotoPath);
                    imageView.setRotation(90);
                    imageView.setImageBitmap(bitmapImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
