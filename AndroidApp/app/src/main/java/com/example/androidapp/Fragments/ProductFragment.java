package com.example.androidapp.Fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidapp.Activities.NewProductActivity;
import com.example.androidapp.Activities.UpdateProductActivity;
import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductAdapter;
import com.example.androidapp.Data.ProductData.ProductViewModel;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeAdapter;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    public static final int ADD_DISH_REQUEST = 1;
    public static final int EDIT_DISH_REQUEST = 2;

    private ProductViewModel productViewModel;
    private ProductTypeViewModel productTypeViewModel;
    private Button btnAddDish;
    private EditText editSearchBar;
    private RecyclerView rcvData;
    private RecyclerView rcvType;
    private ProductAdapter productAdapter;
    private ProductTypeAdapter productTypeAdapter;
    private List<ProductType> mListProductType = new ArrayList<>();
    private List<Product> mListProduct = new ArrayList<>();

//    //confirm sound
//    private MediaPlayer sound = null;
//    public MenuFragment() {
//        //Empty on purpose
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
//        mListDish = new ArrayList<>();
        initUi(view);
//        //Sound
//        sound = MediaPlayer.create(getActivity(), R.raw.confirm_sound);
        setUpProductTypeList(view);
        setUpProductList(view);

        productTypeAdapter.setOnItemClickAddListener(new ProductTypeAdapter.OnItemClickAddListener() {
            @Override
            public void onItemClickAdd() {
                productTypeViewModel.insert(new ProductType("New type"));
            }
        });
        productTypeAdapter.setOnItemChangeListener(new ProductTypeAdapter.OnItemChangeListener() {
            @Override
            public void onItemChange(ProductType productType, String newName) {
                productType.setName(newName);
                productTypeViewModel.update(productType);
//                productAdapter.notifyDataSetChanged();
//                Log.d("test", "change");
            }
        });
//        //Create search bar listener for SEARCH METHOD
        editSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Empty on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Empty on purpose
            }

            @Override
            public void afterTextChanged(Editable s) {
                productAdapter.getFilter().filter(s.toString());
            }
        });
//
//        //Method CLICK TO VIEW info of an item in Recycler View
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(getActivity(), UpdateProductActivity.class);
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_ID, product.getProductID());
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_NAME, product.getName());
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_TYPE, product.getType());
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_ATTRIBUTE_1, product.getAttribute1());
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_ATTRIBUTE_2, product.getAttribute2());

                startActivityForResult(intent, EDIT_DISH_REQUEST);
            }
        });
//        //Delete item
        productAdapter.setOnItemClickDelListener(new ProductAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(Product product) {
                confirmDelDialog(product);
            }
        });

        //Method CLICK the add button
        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewProductActivity.class);
                startActivityForResult(intent, ADD_DISH_REQUEST);
            }
        });

        return view;

    }
    //reset adapter on resume
    @Override
    public void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged();
        rcvData.setAdapter(productAdapter);
    }

        //Method to init UI components
    private void initUi (View view) {
        btnAddDish = view.findViewById(R.id.btn_add_product);
        editSearchBar = view.findViewById(R.id.product_search_bar);
//        edtSearchBar = view.findViewById(R.id.dish_search_bar);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //ADD_DISH_REQUEST (Add a dish to database)
//        if (requestCode == ADD_DISH_REQUEST && resultCode == RESULT_OK) {
//
//            String name = data.getStringExtra(NewDishActivity.EXTRA_DISH_NAME);
//            int price = data.getIntExtra(NewDishActivity.EXTRA_DISH_PRICE, 0);
//            String imageDir = data.getStringExtra(NewDishActivity.EXTRA_DISH_IMAGE);
//            Dish dish = new Dish(name, price, imageDir);
//            dishViewModel.insertDish(dish);
//
//
//        }
//        //EDIT DISH REQUEST (Update an existing dish)
//        else if (requestCode == EDIT_DISH_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(UpdateDishActivity.EXTRA_DISH_ID, -1);
//            if (id == -1) {
//                Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String name = data.getStringExtra(UpdateDishActivity.EXTRA_DISH_NAME);
//            int price = data.getIntExtra(UpdateDishActivity.EXTRA_DISH_PRICE, 0);
//            String imageDir = data.getStringExtra(UpdateDishActivity.EXTRA_NEW_IMAGE);
//            Dish dish = new Dish(name, price, imageDir);
//            dish.setDishID(id);
//            dishViewModel.updateDish(dish);
//
//        }
//    }
//
//
//
    private void confirmDelDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_delete,
                (RelativeLayout)getView().findViewById(R.id.layout_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        //confirm delete btn
        view.findViewById(R.id.confirm_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //delete old data detail
                AppDatabase.getInstance(getActivity()).
                        productDetailDao().deleteAllProductDetailWithName(product.getName());
                //delete the old image
                File oldImage = new File(product.getImageDir());
                boolean deleted = oldImage.delete();
                productViewModel.delete(product);


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
    private void setUpProductTypeList(View view) {
        //Type list
        //Create Recycler View
        rcvType = view.findViewById(R.id.type_list);;
        //rcvData.setHasFixedSize(true);
        rcvType.setLayoutManager(new LinearLayoutManager(rcvType.getContext(), RecyclerView.HORIZONTAL, false));
        productTypeAdapter = new ProductTypeAdapter(mListProductType);
        rcvType.setAdapter(productTypeAdapter);
        productTypeViewModel = new ViewModelProvider(getActivity()).get(ProductTypeViewModel.class);
        productTypeViewModel.getAllProductTypeLive().observe(getActivity(), new Observer<List<ProductType>>() {
            @Override

            //Method DISPLAY the list on screen
            public void onChanged(List<ProductType> productTypes) {
                //use for animation
                productTypeAdapter.setProductType(productTypes);
            }
        });
    }

    private void setUpProductList(View view) {
        //Product list
        //Create Recycler View
        rcvData = view.findViewById(R.id.product_lst);;
        //rcvData.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rcvData.setLayoutManager(staggeredGridLayoutManager);
//        //Create Product ADAPTER
        productAdapter = new ProductAdapter(mListProduct);
        rcvData.setAdapter(productAdapter);
//
//        //Create Product VIEW MODEL
        productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(getActivity(), new Observer<List<Product>>() {
            @Override

            //Method DISPLAY the list on screen
            public void onChanged(List<Product> products) {
                //use for filter
                productAdapter.setProduct(products);
                //use for animation
                productAdapter.submitList(products);

            }
        });
    }

}