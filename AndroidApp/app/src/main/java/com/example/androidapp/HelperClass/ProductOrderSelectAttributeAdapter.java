package com.example.androidapp.HelperClass;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ProductOrderSelectAttributeAdapter extends RecyclerView.Adapter<ProductOrderSelectAttributeAdapter.ProductOrderSelectAttributeViewHolder> {
    private List<ProductAttribute> mListProductOrderSelectAttribute;

    private RvItemClickListener rvItemClickListener;


    public ProductOrderSelectAttributeAdapter(List<ProductAttribute> mListProductOrderSelectAttribute) {
        this.mListProductOrderSelectAttribute = mListProductOrderSelectAttribute;
    }


    public void setProductOrderSelectAttribute(List<ProductAttribute> mListProductOrderSelectAttribute) {
        this.mListProductOrderSelectAttribute = mListProductOrderSelectAttribute;
        notifyDataSetChanged();
    }

    //Get the client position
    public ProductAttribute getAttributeAt(int position) {
        return mListProductOrderSelectAttribute.get(position);
    }

    @NonNull
    @Override
    public ProductOrderSelectAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attribute_in_sub_product, parent, false);

        return new ProductOrderSelectAttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderSelectAttributeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductAttribute attribute = mListProductOrderSelectAttribute.get(position);
        if (attribute == null) {
            return;
        }

        holder.tvAttributeName.setText(attribute.getAttributeTitle());
        ProductInfoAttributeItemAdapter productInfoAttributeItemAdapter = new ProductInfoAttributeItemAdapter(mListProductOrderSelectAttribute.get(position).getProductAttributeItemList());
        holder.itemLst.setLayoutManager(new LinearLayoutManager(holder.itemLst.getContext(), RecyclerView.HORIZONTAL, false));
        holder.itemLst.setAdapter(productInfoAttributeItemAdapter);


        productInfoAttributeItemAdapter.setOnItemClickListener(new ProductInfoAttributeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ProductAttributeItem productAttributeItem = mListProductOrderSelectAttribute.get(holder.getAdapterPosition()).getProductAttributeItemList().get(pos);
                rvItemClickListener.onChildItemClick(holder.getAdapterPosition(), pos, productAttributeItem);
            }
        });


//        setProductOrderSelectAttributeItemRecycler(holder.itemLst, mListProductOrderSelectAttribute.get(position).getProductAttributeItemList(), position);
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductOrderSelectAttribute != null) {
            return mListProductOrderSelectAttribute.size();
        }
        return 0;
    }



    public class ProductOrderSelectAttributeViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeName;
        private final RecyclerView itemLst;
//

//        private final LinearLayout item;

        public ProductOrderSelectAttributeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttributeName = itemView.findViewById(R.id.attribute_name);
            itemLst = itemView.findViewById(R.id.item_list);
//            btnAdd = itemView.findViewById(R.id.add_btn);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientAddress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);


//            btnAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (addListener != null && position != RecyclerView.NO_POSITION) {
//                        addListener.onItemClickAdd(position);
//                    }
//                }
//            });
//            productInfoAttributeItemAdapter.setOnItemClickListener(new ProductOrderSelectAttributeItemAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int childPosition) {
//                    ProductAttributeItem productInfoAttributeItem = productInfoAttributeItemList.get(childPosition);
//                    rvItemClickListener.onChildItemClick(parentPosition, childPosition, productInfoAttributeItem);
//                }
//            });

//            itemLst.setLayoutManager(new LinearLayoutManager(itemLst.getContext(), RecyclerView.HORIZONTAL, false));
//            itemLst.setAdapter(productInfoAttributeItemAdapter);
//            productInfoAttributeItemAdapter.setOnItemClickListener(new ProductOrderSelectAttributeItemAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int childPosition) {
//                    ProductAttributeItem productInfoAttributeItem = mListProductOrderSelectAttribute.get(getAdapterPosition()).getProductAttributeItemList().get(childPosition);
//                    rvItemClickListener.onChildItemClick(getAdapterPosition(), childPosition, productInfoAttributeItem);
//                }
//            });
        }
    }



//    //Interface to click on a dish item


//    public interface OnItemClickListener{
//        void onItemClick(String attribute);
//    }
//    public void setOnItemClickDelListener(OnItemClickDelListener delListener){
//        this.delListener = delListener;
//    }

    private void setProductOrderSelectAttributeItemRecycler(RecyclerView recycler, List<ProductAttributeItem> productInfoAttributeItemList, int parentPosition) {
        ProductInfoAttributeItemAdapter productInfoAttributeItemAdapter = new ProductInfoAttributeItemAdapter(productInfoAttributeItemList);
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext(), RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(productInfoAttributeItemAdapter);
//        productInfoAttributeItemAdapter.setOnItemClickListener(new ProductOrderSelectAttributeItemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int childPosition) {
//                ProductAttributeItem productInfoAttributeItem = productInfoAttributeItemList.get(childPosition);
//                rvItemClickListener.onChildItemClick(parentPosition, childPosition, productInfoAttributeItem);
//            }
//        });


    }


    public void setRvItemClickListener(RvItemClickListener rvItemClickListener){
        this.rvItemClickListener = rvItemClickListener;
    }

    public interface RvItemClickListener {
        void onChildItemClick(int parentPosition, int childPosition, ProductAttributeItem item);
    }
}