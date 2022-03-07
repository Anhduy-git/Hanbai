package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.androidapp.Activities.MainActivity;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderViewModel;
import com.example.androidapp.Data.ProductAttribute.ProductAttribute;
import com.example.androidapp.Data.ProductAttribute.ProductAttributeViewModel;

import java.util.List;

public class PickTypeActivity extends AppCompatActivity {

    private Button confirmBtn;
    private LinearLayout lst1;
    private LinearLayout lst2;
    private ProductAttributeViewModel productAttributeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_type);

        //view model
        productAttributeViewModel = new ViewModelProvider(this).get(ProductAttributeViewModel.class);

        //init view
        confirmBtn = findViewById(R.id.confirm_btn);
        lst1 = findViewById(R.id.lst1);
        lst2 = findViewById(R.id.lst2);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllAttribute(lst1);
                checkAllAttribute(lst2);
                Intent intent = new Intent(PickTypeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void checkAllAttribute(LinearLayout lst) {
        for (int i = 0; i < lst.getChildCount(); i++) {
            final CheckBox attribute = (CheckBox)lst.getChildAt(i);
            if (attribute.isChecked()) {
                ProductAttribute productAttribute = new ProductAttribute(attribute.getText().toString());
                productAttributeViewModel.insert(productAttribute);
            }
        }
    }

}