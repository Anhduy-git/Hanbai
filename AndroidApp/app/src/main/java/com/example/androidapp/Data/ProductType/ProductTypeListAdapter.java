package com.example.androidapp.Data.ProductType;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeListAdapter extends RecyclerView.Adapter<ProductTypeListAdapter.ProductTypeViewHolder> {
    private Context context;
    private List<ProductType> productTypeList;
    private ArrayList<Integer> colors;
    private List<Double> productTypePercentage;
    private List<Integer> productTypeOrders;

    public ProductTypeListAdapter(Context context, List<ProductType> productTypeList, ArrayList<Integer> colors,
                                  List<Double> productTypePercentage, List<Integer> productTypeOrders) {
        this.context = context;
        this.productTypeList = productTypeList;
        this.colors = colors;
        this.productTypePercentage = productTypePercentage;
        this.productTypeOrders = productTypeOrders;
    }

    @NonNull
    @Override
    public ProductTypeListAdapter.ProductTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pie_chart_note,parent, false);
        return new ProductTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeListAdapter.ProductTypeViewHolder holder, int position) {
        holder.legendName.setText(productTypeList.get(position).getName());
        holder.legendColor.setBackgroundColor(colors.get(position));
        holder.legendPercentValue.setText(String.valueOf(productTypePercentage.get(position)));
        holder.legendNumberOfOrders.setText(String.valueOf(productTypeOrders.get(position)));
    }

    @Override
    public int getItemCount() {
        return productTypeList.size();
    }

    public static class ProductTypeViewHolder extends RecyclerView.ViewHolder {
//      private final RelativeLayout legendHolder;
        private final View legendColor;
        private final TextView legendName;
        private final TextView legendPercentValue;
        private final TextView legendNumberOfOrders;

        public ProductTypeViewHolder(@NonNull View itemView) {
            super(itemView);
//          legendHolder = itemView.findViewById(R.id.legend_holder);
            legendColor = itemView.findViewById(R.id.legend_color);
            legendName = itemView.findViewById(R.id.legend_name);
            legendPercentValue = itemView.findViewById(R.id.legend_percent);
            legendNumberOfOrders = itemView.findViewById(R.id.legend_num_order);
        }
    }
}
