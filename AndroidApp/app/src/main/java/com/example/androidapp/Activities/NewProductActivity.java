package com.example.androidapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.HelperClass.ProductAttributeAdapter;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeCategoryAdapter;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {
    private ProductTypeViewModel productTypeViewModel;
    private Spinner spinner;
    private Button backBtn;
    private ProductTypeCategoryAdapter productTypeCategoryAdapter;
    private RecyclerView attributeLst;
    private Button addAttributeBtn;
    private Button nextBtn;
    private EditText productName;
    private ImageView imageView;
    private final int GALLERY_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;
    private final int PIC_CROP = 3;
    private final int ADD_SUCCESS = 4;
    private boolean changeImg;
    private String currentPhotoPath;
    private Button btnGallery;
    private final int IMAGE_SIZE = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        initUI();
        //get list type
        List<ProductType> productTypeList = getListCategory();

        productTypeCategoryAdapter = new ProductTypeCategoryAdapter(this, R.layout.item_selected_spinner, productTypeList);
        spinner.setAdapter(productTypeCategoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<ProductAttribute> attribute = new ArrayList<>();
        //setup recylcer view
        attributeLst.setLayoutManager(new LinearLayoutManager(this));
        ProductAttributeAdapter attributeAdapter = new ProductAttributeAdapter(attribute);
        attributeLst.setAdapter(attributeAdapter);

        //add attribute
        addAttributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                attribute.add(new ProductAttribute("", new ArrayList<>()));
                attributeAdapter.notifyDataSetChanged();
                //gone add btn
                if (attribute.size() == 2) {
                    addAttributeBtn.setVisibility(View.GONE);
                }
            }
        });
        //delete attribute
        attributeAdapter.setOnItemClickDelListener(new ProductAttributeAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(ProductAttribute productAttribute) {
                attribute.remove(productAttribute);
                attributeAdapter.notifyDataSetChanged();
                //add btn visible
                addAttributeBtn.setVisibility(View.VISIBLE);
            }
        });
        //add attribute item
        attributeAdapter.setOnItemClickAddListener(new ProductAttributeAdapter.OnItemClickAddListener() {
            @Override
            public void onItemClickAdd(int position) {
                attribute.get(position).getProductAttributeItemList().add(new ProductAttributeItem("item"));
                attributeAdapter.notifyDataSetChanged();
            }
        });

        //button back
        backBtn.setOnClickListener(new View.OnClickListener() {

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

        //button next
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idx = spinner.getSelectedItemPosition();
                String strProductType = productTypeList.get(idx).getName();
                String strProductName = productName.getText().toString().trim();

                Intent intent = new Intent(NewProductActivity.this, InfoClientActivity.AddQuantityPriceProductActivity.class);
                //put data
                intent.putExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_PRODUCT_NAME, strProductName);
                intent.putExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_PRODUCT_TYPE, strProductType);
                if (attribute.size() == 1) {
                    intent.putExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putParcelableArrayListExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                } else if (attribute.size() == 2) {
                    intent.putExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_1, attribute.get(0).getAttributeTitle());
                    intent.putExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_2, attribute.get(1).getAttributeTitle());
                    intent.putParcelableArrayListExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_1, (ArrayList<? extends Parcelable>) attribute.get(0).getProductAttributeItemList());
                    intent.putParcelableArrayListExtra(InfoClientActivity.AddQuantityPriceProductActivity.EXTRA_ATTRIBUTE_LIST_2, (ArrayList<? extends Parcelable>) attribute.get(1).getProductAttributeItemList());
                }
                startActivityForResult(intent, ADD_SUCCESS);
            }
        });


    }



    private List<ProductType> getListCategory() {
        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        List<ProductType> attributeLst = productTypeViewModel.getAllProductType();
        return attributeLst;
    }

    void initUI() {
        spinner = findViewById(R.id.type_list);
        backBtn = findViewById(R.id.back_btn);
        nextBtn = findViewById(R.id.next_btn);
        attributeLst = findViewById(R.id.attribute_list);
        addAttributeBtn = findViewById(R.id.add_attribute);
        productName = findViewById(R.id.product_name);
        imageView = findViewById(R.id.product_img);
        btnGallery = findViewById(R.id.btn_gallery);
    }


    private boolean checkRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(NewProductActivity.this,
                    Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        NewProductActivity.this,
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
        if (requestCode == ADD_SUCCESS && resultCode == RESULT_OK) {
            String strProductName = productName.getText().toString().trim();
            //Save image
            if (changeImg) {
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                image = ImageConverter.getResizedBitmap(image, IMAGE_SIZE);
                String fileName = strProductName;
                String imageDir = saveToInternalStorage(image, fileName);

                //Set the image dir to product
                AppDatabase.getInstance(NewProductActivity.this). productDao().updateImageProduct(strProductName, imageDir);

                //release memory
                image.recycle();
                //image.recycle();
            } else {
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.product_ava_default);
                String fileName = strProductName;
                String imageDir = saveToInternalStorage(image, fileName);

                //Set the image dir to product
                AppDatabase.getInstance(NewProductActivity.this). productDao().updateImageProduct(strProductName, imageDir);

                //release memory
                image.recycle();
            }
            onBackPressed();
        }
        else if (requestCode == GALLERY_REQUEST) {
            //CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                imageView.setRotation(0);

                try {
                    changeImg = true;
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        else if (requestCode == CAMERA_REQUEST){
            if (resultCode == RESULT_OK) {
//                imageView.setRotation(0);

                try {
                    //set changed Img
                    changeImg = true;
                    //Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = BitmapFactory.decodeFile(currentPhotoPath);
//                    imageView.setRotation(90);
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
            Toast.makeText(NewProductActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/your_app/app_data/imageClientDir
        File directory = cw.getDir("imageProductDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory,fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, fos);
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