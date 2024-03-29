package com.example.androidapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.HelperClass.ProductAttribute;
import com.example.androidapp.HelperClass.ProductAttributeItem;
import com.example.androidapp.HelperClass.ProductInfoAttributeAdapter;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateProductActivity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT_ID =
            "com.example.androidapp.EXTRA_PRODUCT_ID";
    public static final String EXTRA_PRODUCT_NAME =
            "com.example.androidapp.EXTRA_PRODUCT_NAME";
    public static final String EXTRA_PRODUCT_ATTRIBUTE_1 =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE_1";
    public static final String EXTRA_PRODUCT_ATTRIBUTE_2 =
            "com.example.androidapp.EXTRA_PRODUCT_ATTRIBUTE_2";
    public static final String EXTRA_PRODUCT_TYPE =
            "com.example.androidapp.EXTRA_PRODUCT_TYPE";
    public static final String EXTRA_PRODUCT_IMAGE =
            "com.example.androidapp.EXTRA_PRODUCT_IMAGE";


    private final int GALLERY_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;
    private final int IMAGE_SIZE = 500;
    public static final Integer CONFIRM_REQUEST = 4;
    private TextView productName;
    private TextView productPrice;
    private TextView productType;
    private TextView productQuantity;
    private Button updateBtn;
    private ImageView imageView;
    private Button btnBack;
    private Button btnUpdate;
    private Button btnAddImage;
    private RecyclerView attributeRecycler;
    private List<ProductDetail> productDetails;
    private String attributeItemSelected1;
    private String attributeItemSelected2;
    private String name;
    private String type;
    private List<ProductAttributeItem>attribute1ItemList = new ArrayList<>();
    private List<ProductAttributeItem>attribute2ItemList = new ArrayList<>();
    private String attribute1, attribute2;
    private int productId;
    private String imageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        initUi();

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

        //init Recycler view
        List<ProductAttribute> attributeList = new ArrayList<>();
        //setup recylcer view
        attributeRecycler.setLayoutManager(new LinearLayoutManager(this));
        ProductInfoAttributeAdapter attributeAdapter = new ProductInfoAttributeAdapter(attributeList);

        attributeRecycler.setAdapter(attributeAdapter);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_PRODUCT_ID)) {
            name = intent.getStringExtra(EXTRA_PRODUCT_NAME);
            type = intent.getStringExtra(EXTRA_PRODUCT_TYPE);
            attribute1 = intent.getStringExtra(EXTRA_PRODUCT_ATTRIBUTE_1);
            attribute2 = intent.getStringExtra(EXTRA_PRODUCT_ATTRIBUTE_2);
            productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1);
            imageDir = intent.getStringExtra(EXTRA_PRODUCT_IMAGE);
            productName.setText(name);
            productType.setText(type);
            //set image
            if (imageDir != null) {
                try {
                    File f=new File(imageDir);
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    //holder.imageView.setRotation(90);
                    imageView.setImageBitmap(b);
                }
                catch (FileNotFoundException e) {
                    Resources res = imageView.getResources();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.product_ava_default);
                    imageView.setImageBitmap(bitmap);
                }
            }

            //Query data
            productDetails  = AppDatabase.getInstance(UpdateProductActivity.this).
                    productDetailDao().getAllProductDetail(intent.getStringExtra(EXTRA_PRODUCT_NAME));
            //init selected item
            attributeItemSelected1 = productDetails.get(0).getAttribute1();
            attributeItemSelected2 = productDetails.get(0).getAttribute2();
            //init price, quantity
            productPrice.setText(Integer.toString(productDetails.get(0).getPrice()));
            productQuantity.setText(Integer.toString(productDetails.get(0).getQuantity()));

            //2 attribute
            if (attribute1 != null && attribute2 != null) {

                //Algorithm for filter attribute
                boolean addFullAtt2 = false;
                for (int i = 0; i < productDetails.size(); i++) {
                    if (i == 0) {
                        attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));
                        attribute2ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute2()));
                    } else {
                        if (!productDetails.get(i).getAttribute1().equals(productDetails.get(i - 1).getAttribute1())) {
                            attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));
                            addFullAtt2 = true;
                        } else if (!addFullAtt2) {
                            attribute2ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute2()));
                        }
                    }
                }
                attributeList.add(new ProductAttribute(attribute1, attribute1ItemList));
                attributeList.add(new ProductAttribute(attribute2, attribute2ItemList));
                attributeAdapter.notifyDataSetChanged();
                //on item click
                attributeAdapter.setRvItemClickListener(new ProductInfoAttributeAdapter.RvItemClickListener() {
                    @Override
                    public void onChildItemClick(int parentPosition, int childPosition, ProductAttributeItem item) {

                        if (parentPosition == 0) {
                            attributeItemSelected1 = item.getAttributeItemName();
                        } else if (parentPosition == 1){
                            attributeItemSelected2 = item.getAttributeItemName();
                        }
                        for (ProductDetail productDetail : productDetails) {
                            if (productDetail.getAttribute1().equals(attributeItemSelected1) &&
                                    productDetail.getAttribute2().equals(attributeItemSelected2)) {
                                productPrice.setText(Integer.toString(productDetail.getPrice()));
                                productQuantity.setText(Integer.toString(productDetail.getQuantity()));
                            }
                        }
                    }
                });

            }
            //1 attribute
            else if (attribute1 != null && attribute2 == null) {
                //Algorithm for filter attribute

                for (int i = 0; i < productDetails.size(); i++) {
                    attribute1ItemList.add(new ProductAttributeItem(productDetails.get(i).getAttribute1()));

                }
                attributeList.add(new ProductAttribute(attribute1, attribute1ItemList));
                attributeAdapter.notifyDataSetChanged();
                //on item click
                attributeAdapter.setRvItemClickListener(new ProductInfoAttributeAdapter.RvItemClickListener() {
                    @Override
                    public void onChildItemClick(int parentPosition, int childPosition, ProductAttributeItem item) {

                        attributeItemSelected1 = item.getAttributeItemName();
                        for (ProductDetail productDetail : productDetails) {
                            if (productDetail.getAttribute1().equals(attributeItemSelected1)) {
                                productPrice.setText(Integer.toString(productDetail.getPrice()));
                                productQuantity.setText(Integer.toString(productDetail.getQuantity()));
                            }
                        }
                    }
                });
            }
            //0 attribute
            else {
                productPrice.setText(Integer.toString(productDetails.get(0).getPrice()));
                productQuantity.setText(Integer.toString(productDetails.get(0).getQuantity()));
            }


//            try {
//                //read path image from intent and display
//                File f=new File(intent.getStringExtra(EXTRA_OLD_IMAGE));
//                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//                imageView.setImageBitmap(b);
//            }
//            catch (FileNotFoundException e) {
//                //display default image for dish
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rec_ava_dish_default);
//                imageView.setImageBitmap(bitmap);
//            }
        }

//        //Check for update to show btn update
//        checkUpdate();

//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateProduct();
//            }
//        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //update
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProductActivity.this, UpdateProductActivity2.class);
                intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_TYPE, type);
                intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_NAME, name);
                intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_IMAGE, imageDir);
                if (attribute1 != null) {
                    intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_ATTRIBUTE1, attribute1);
                }
                if (attribute2 != null) {
                    intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_ATTRIBUTE2, attribute2);
                }
                if (attribute1ItemList.size() > 0) {
                    intent.putParcelableArrayListExtra(UpdateProductActivity2.EXTRA_PRODUCT_ATTRIBUTE1_LIST, (ArrayList<? extends Parcelable>) attribute1ItemList);
                }
                if (attribute1ItemList.size() > 0) {
                    intent.putParcelableArrayListExtra(UpdateProductActivity2.EXTRA_PRODUCT_ATTRIBUTE2_LIST, (ArrayList<? extends Parcelable>) attribute2ItemList);
                }
                //put id to update
                intent.putExtra(UpdateProductActivity2.EXTRA_PRODUCT_ID, productId);

                startActivityForResult(intent, CONFIRM_REQUEST);
            }
        });

//        btnAddImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePictureFromGallery();
//            }
//        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequestPermission()) {
                    takePictureFromCamera();
                }
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


    private void initUi() {
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.product_quantity);
        productType = findViewById(R.id.product_type);
        imageView = findViewById(R.id.product_img);
        btnBack = findViewById(R.id.back_btn);
        attributeRecycler = findViewById(R.id.attribute_list);
        updateBtn = findViewById(R.id.update_btn);
    }

//    private void updateProduct() {
//        String strProductName = editProductName.getText().toString().trim();
//        String strProductPrice = editProductPrice.getText().toString().trim();
//
//        //Check if fields are empty, if so then don't add to database
//        if (TextUtils.isEmpty(strProductName) || TextUtils.isEmpty(strProductPrice)) {
//            Toast.makeText(this, "Xin hãy nhập tên và ", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Intent data = new Intent();
//        data.putExtra(EXTRA_DISH_NAME, strProductName);
//        data.putExtra(EXTRA_DISH_PRICE, Integer.parseInt(strProductPrice));
//        int id = getIntent().getIntExtra(EXTRA_DISH_ID, -1);
//        if (id != -1) {
//            data.putExtra(EXTRA_DISH_ID, id);
//        } else {
//            Toast.makeText(UpdateProductActivity.this, "Cập nhật không hợp lệ !", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Product dishUpdate = new Product(strProductName, Integer.parseInt(strProductPrice), "");
//        dishUpdate.setProductID(id);
//        if (checkProductAvailableForUpdate(dishUpdate)) {
//            //delete the old image when update
//            File oldImage = new File(getIntent().getStringExtra(EXTRA_OLD_IMAGE));
//            boolean deleted = oldImage.delete();
//            //save new image to file and get path
//            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//            Bitmap image = ImageConverter.getResizedBitmap(bitmap, IMAGE_SIZE);
//            String imageDir = saveToInternalStorage(image, strProductName + "-" +
//                    strProductPrice);
//            //release memory
//            bitmap.recycle();
//            image.recycle();
//
//            //put new image path to intent
//            data.putExtra(EXTRA_NEW_IMAGE, imageDir);
//
//            setResult(RESULT_OK, data);
//
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
//            finish();
//        } else {
//            Toast.makeText(UpdateProductActivity.this, "Món ăn đã tồn tại !", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

    private boolean checkRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(UpdateProductActivity.this,
                    Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        UpdateProductActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST);
                return false;
            }
        }
        return true;
    }

    private void takePictureFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALLERY_REQUEST);
    }

    private void takePictureFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONFIRM_REQUEST && resultCode == RESULT_OK) {
            onBackPressed();
        }
        else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                //update image
                btnUpdate.setVisibility(View.VISIBLE);

                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                //update image
                btnUpdate.setVisibility(View.VISIBLE);

                Bundle bundle = data.getExtras();
                Bitmap bitmapImage = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmapImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //back to product fragment when update successfully
        else if (requestCode == CONFIRM_REQUEST && resultCode == RESULT_OK) {
            onBackPressed();
        }
    }

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
            Toast.makeText(UpdateProductActivity.this, "Chưa được cấp quyền", Toast.LENGTH_SHORT).show();
        }
    }

//    private void checkUpdate() {
//        editProductName.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                btnUpdate.setVisibility(View.VISIBLE);
//            }
//        });
//
//        editProductPrice.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                btnUpdate.setVisibility(View.VISIBLE);
//            }
//        });
//
//    }


//    private boolean checkProductAvailableForUpdate(@NonNull Product dish) {
//        List<Product> list  = AppDatabase.getInstance(UpdateProductActivity.this).dishDao().checkProductExist(dish.getName(), dish.getPrice());
//        return (list == null) || (list.size() == 0) || (list.size() == 1 && list.get(0).getProductID() == dish.getProductID());
//    }
}