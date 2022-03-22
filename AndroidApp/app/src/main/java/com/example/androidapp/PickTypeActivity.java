package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.androidapp.Activities.MainActivity;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.google.android.flexbox.FlexboxLayout;

public class PickTypeActivity extends AppCompatActivity {

    private Button confirmBtn;
    private FlexboxLayout lst1;
    private ProductTypeViewModel productTypeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_type);

        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);

        //init view
        confirmBtn = findViewById(R.id.confirm_btn);
        lst1 = findViewById(R.id.lst1);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllAttribute(lst1);
                Intent intent = new Intent(PickTypeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void checkAllAttribute(FlexboxLayout lst) {
        for (int i = 0; i < lst.getChildCount(); i++) {
            final CheckBox attribute = (CheckBox)lst.getChildAt(i);
            if (attribute.isChecked()) {
                ProductType productType = new ProductType(attribute.getText().toString());
                productTypeViewModel.insert(productType);
            }
        }
    }

}