package com.example.androidapp.Data.ProductAttribute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidapp.R;

import java.util.List;

public class ProductAttributeCategoryAdapter extends ArrayAdapter<ProductAttribute> {

    public ProductAttributeCategoryAdapter(@NonNull Context context, int resource, @NonNull List<ProductAttribute> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner, parent, false);
        TextView tvSelectedSpinner = convertView.findViewById((R.id.tv_selected_spinner));

        ProductAttribute productAttribute = this.getItem(position);
        if(productAttribute != null) {
            tvSelectedSpinner.setText(productAttribute.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView tvSpinner = convertView.findViewById((R.id.tv_spinner));

        ProductAttribute productAttribute = this.getItem(position);
        if(productAttribute != null) {
            tvSpinner.setText(productAttribute.getName());
        }
        return convertView;
    }
}
