package com.example.androidapp.HelperClass;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.List;

public class ProductInfoAttributeAdapter extends RecyclerView.Adapter<ProductInfoAttributeAdapter.ProductInfoAttributeViewHolder> {
    private List<ProductAttribute> mListProductInfoAttribute;

    private RvItemClickListener rvItemClickListener;
    

    public ProductInfoAttributeAdapter(List<ProductAttribute> mListProductInfoAttribute) {
        this.mListProductInfoAttribute = mListProductInfoAttribute;
    }


    public void setProductInfoAttribute(List<ProductAttribute> mListProductInfoAttribute) {
        this.mListProductInfoAttribute = mListProductInfoAttribute;
        notifyDataSetChanged();
    }

    //Get the client position
    public ProductAttribute getAttributeAt(int position) {
        return mListProductInfoAttribute.get(position);
    }

    @NonNull
    @Override
    public ProductInfoAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info_attribute, parent, false);

        return new ProductInfoAttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInfoAttributeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductAttribute attribute = mListProductInfoAttribute.get(position);
        if (attribute == null) {
            return;
        }

        holder.tvAttributeName.setText(attribute.getAttributeTitle());
        ProductInfoAttributeItemAdapter productInfoAttributeItemAdapter = new ProductInfoAttributeItemAdapter(mListProductInfoAttribute.get(position).getProductAttributeItemList());
        holder.itemLst.setLayoutManager(new LinearLayoutManager(holder.itemLst.getContext(), RecyclerView.HORIZONTAL, false));
        holder.itemLst.setAdapter(productInfoAttributeItemAdapter);
        productInfoAttributeItemAdapter.setOnItemClickListener(new ProductInfoAttributeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ProductAttributeItem productAttributeItem = mListProductInfoAttribute.get(holder.getAdapterPosition()).getProductAttributeItemList().get(pos);
                rvItemClickListener.onChildItemClick(holder.getAdapterPosition(), pos, productAttributeItem);
            }
        });


//        setProductInfoAttributeItemRecycler(holder.itemLst, mListProductInfoAttribute.get(position).getProductAttributeItemList(), position);
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductInfoAttribute != null) {
            return mListProductInfoAttribute.size();
        }
        return 0;
    }



    public class ProductInfoAttributeViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeName;
        private final RecyclerView itemLst;
//

//        private final LinearLayout item;

        public ProductInfoAttributeViewHolder(@NonNull View itemView) {
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
//            productInfoAttributeItemAdapter.setOnItemClickListener(new ProductInfoAttributeItemAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int childPosition) {
//                    ProductAttributeItem productInfoAttributeItem = productInfoAttributeItemList.get(childPosition);
//                    rvItemClickListener.onChildItemClick(parentPosition, childPosition, productInfoAttributeItem);
//                }
//            });

//            itemLst.setLayoutManager(new LinearLayoutManager(itemLst.getContext(), RecyclerView.HORIZONTAL, false));
//            itemLst.setAdapter(productInfoAttributeItemAdapter);
//            productInfoAttributeItemAdapter.setOnItemClickListener(new ProductInfoAttributeItemAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int childPosition) {
//                    ProductAttributeItem productInfoAttributeItem = mListProductInfoAttribute.get(getAdapterPosition()).getProductAttributeItemList().get(childPosition);
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

    private void setProductInfoAttributeItemRecycler(RecyclerView recycler, List<ProductAttributeItem> productInfoAttributeItemList, int parentPosition) {
        ProductInfoAttributeItemAdapter productInfoAttributeItemAdapter = new ProductInfoAttributeItemAdapter(productInfoAttributeItemList);
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext(), RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(productInfoAttributeItemAdapter);
//        productInfoAttributeItemAdapter.setOnItemClickListener(new ProductInfoAttributeItemAdapter.OnItemClickListener() {
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