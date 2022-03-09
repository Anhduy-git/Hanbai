package com.example.androidapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewClientActivity extends AppCompatActivity {
    public static final String EXTRA_CLIENT = "com.example.androidapp.EXTRA_CLIENT";

    private final int GALLERY_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;
    private final int PIC_CROP = 3;

    private Button btnBack;
    private Button btnAddClient;
    private Button btnGallery;
    private Button btnCamera;
    private EditText editClientName;
    private EditText editClientNumber;
    private EditText editClientAddress;
    private EditText editClientEmail;
    private EditText editClientBank;
    private ImageView imageView;
    private boolean changeImg;
    private String currentPhotoPath;

    private Uri picUri;

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

        //Choose image from gallery
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureFromGallery();
            }
        });

        //Choose image from camera
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequestPermission()){
                    takePictureFromCamera();
                }
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
        btnGallery = findViewById(R.id.btn_gallery);
        btnCamera = findViewById(R.id.btn_camera);
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

    private boolean checkRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(NewClientActivity.this,
                    Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        NewClientActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST);
                return false;
            }
        }
        return true;
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


//    On activity result for crop library
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

    public void rotateImage(Bitmap img, int degree) {

    }

//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK) {
//                        Intent intent = result.getData();
//
//                        if (intent != null && ) {
//                            Log.d("PHOTO", "success");
//                            try {
//                                //set changed Img
//                                changeImg = true;
//                                Uri selectedImage = intent.getData();
//                                imageView.setImageURI(selectedImage);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//    );

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                takePictureFromCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(NewClientActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
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
}

