package com.example.androidapp.Data.ProductType;

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

public class ProductTypeCategoryAdapter extends ArrayAdapter<ProductType> {

    public ProductTypeCategoryAdapter(@NonNull Context context, int resource, @NonNull List<ProductType> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner, parent, false);
        TextView tvSelectedSpinner = convertView.findViewById((R.id.tv_selected_spinner));

        ProductType productType = this.getItem(position);
        if(productType != null) {
            tvSelectedSpinner.setText(productType.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView tvSpinner = convertView.findViewById((R.id.tv_spinner));

        ProductType productType = this.getItem(position);
        if(productType != null) {
            tvSpinner.setText(productType.getName());
        }
        return convertView;
    }
}
