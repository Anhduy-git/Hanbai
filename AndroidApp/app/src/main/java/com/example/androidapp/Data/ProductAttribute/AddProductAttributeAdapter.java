package com.example.androidapp.Data.ProductAttribute;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AddProductAttributeAdapter extends RecyclerView.Adapter<AddProductAttributeAdapter.AddProductAttributeViewHolder> {
    private List<String> mListProductAttribute;

    private OnItemClickListener listener;

    public AddProductAttributeAdapter(List<String> mListProductAttribute) {
        this.mListProductAttribute = mListProductAttribute;
    }


    public void setProductAttribute(List<String> mListProductAttribute) {
        this.mListProductAttribute = mListProductAttribute;
        notifyDataSetChanged();
    }

    //Get the client position
    public String getAttributeAt(int position) {
        return mListProductAttribute.get(position);
    }

    @NonNull
    @Override
    public AddProductAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attribute, parent, false);

        return new AddProductAttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductAttributeViewHolder holder, int position) {
        String attribute = mListProductAttribute.get(position);
        if (attribute == null) {
            return;
        }

        holder.tvAttributeName.setText(attribute);
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientAddress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductAttribute != null) {
            return mListProductAttribute.size();
        }
        return 0;
    }



    public class AddProductAttributeViewHolder extends RecyclerView.ViewHolder {

//        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeName;
//        private final ImageView imageView;
//        private final LinearLayout item;

        public AddProductAttributeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttributeName = itemView.findViewById(R.id.attribute_name);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientAddress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mListProductAttribute.get(position));
                    }
                }
            });
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickListener {
        void onItemClick(String attribute);
    }

    //Method item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}