package com.example.androidapp.HelperClass;


import android.text.Editable;
import android.text.TextWatcher;
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

public class ProductAttributeAdapter extends RecyclerView.Adapter<ProductAttributeAdapter.ProductAttributeViewHolder> {
    private List<ProductAttribute> mListProductAttribute;

//    private OnItemClickDelListener listener;
    private OnItemClickDelListener delListener;
    private OnItemClickAddListener addListener;

    public ProductAttributeAdapter(List<ProductAttribute> mListProductAttribute) {
        this.mListProductAttribute = mListProductAttribute;
    }


    public void setProductAttribute(List<ProductAttribute> mListProductAttribute) {
        this.mListProductAttribute = mListProductAttribute;
        notifyDataSetChanged();
    }

    //Get the client position
    public ProductAttribute getAttributeAt(int position) {
        return mListProductAttribute.get(position);
    }

    @NonNull
    @Override
    public ProductAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attribute, parent, false);

        return new ProductAttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAttributeViewHolder holder, int position) {
        ProductAttribute attribute = mListProductAttribute.get(position);
        if (attribute == null) {
            return;
        }

        holder.tvAttributeName.setText(attribute.getAttributeTitle());
        holder.tvAttributeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mListProductAttribute.get(holder.getAdapterPosition()).setAttributeTitle(s.toString());
            }
        });
        setProductAttributeItemRecycler(holder.itemLst, mListProductAttribute.get(position).getProductAttributeItemList());
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductAttribute != null) {
            return mListProductAttribute.size();
        }
        return 0;
    }



    public class ProductAttributeViewHolder extends RecyclerView.ViewHolder {

//        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeName;
        private final RecyclerView itemLst;
        private final Button btnDel;
        private final Button btnAdd;

//        private final LinearLayout item;

        public ProductAttributeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttributeName = itemView.findViewById(R.id.attribute_name);
            itemLst = itemView.findViewById(R.id.item_list);
            btnDel = itemView.findViewById(R.id.del_btn);
            btnAdd = itemView.findViewById(R.id.add_btn);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientAddress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (delListener != null && position != RecyclerView.NO_POSITION) {
                        delListener.onItemClickDel(mListProductAttribute.get(position));
                    }
                }
            });
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (addListener != null && position != RecyclerView.NO_POSITION) {
                        addListener.onItemClickAdd(position);
                    }
                }
            });
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickDelListener {
        void onItemClickDel(ProductAttribute attribute);
    }

    //Method item click listener
    public void setOnItemClickDelListener(OnItemClickDelListener delListener) {
        this.delListener = delListener;
    }

    //Interface to click on a dish item
    public interface OnItemClickAddListener {
        void onItemClickAdd(int position);
    }

    //Method item click listener
    public void setOnItemClickAddListener(OnItemClickAddListener addListener) {
        this.addListener = addListener;
    }

//    public interface OnItemClickDelListener{
//        void onItemClickDel(String attribute);
//    }
//    public void setOnItemClickDelListener(OnItemClickDelListener delListener){
//        this.delListener = delListener;
//    }

    private void setProductAttributeItemRecycler(RecyclerView recycler, List<ProductAttributeItem> productAttributeItemList) {
        ProductAttributeItemAdapter productAttributeItemAdapter = new ProductAttributeItemAdapter(productAttributeItemList);
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext(), RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(productAttributeItemAdapter);

        productAttributeItemAdapter.setOnItemClickDelListener(new ProductAttributeItemAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(ProductAttributeItem productAttributeItem) {
                productAttributeItemList.remove(productAttributeItem);
                productAttributeItemAdapter.notifyDataSetChanged();
            }
        });

    }
}