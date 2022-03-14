package com.example.androidapp.HelperClass;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.List;

public class ProductAttributeItemAdapter extends RecyclerView.Adapter<ProductAttributeItemAdapter.ProductAttributeItemViewHolder> {
    private List<ProductAttributeItem> mListProductAttributeItem;

    //    private OnItemClickDelListener listener;
    private OnItemClickDelListener delListener;

    public ProductAttributeItemAdapter(List<ProductAttributeItem> mListProductAttributeItem) {
        this.mListProductAttributeItem = mListProductAttributeItem;
    }


    public void setProductAttributeItem(List<ProductAttributeItem> mListProductAttributeItem) {
        this.mListProductAttributeItem = mListProductAttributeItem;
        notifyDataSetChanged();
    }

    //Get the client position
    public ProductAttributeItem getAttributeItemAt(int position) {
        return mListProductAttributeItem.get(position);
    }

    @NonNull
    @Override
    public ProductAttributeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_input_type_attribute, parent, false);

        return new ProductAttributeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAttributeItemViewHolder holder, int position) {
        ProductAttributeItem attributeItem = mListProductAttributeItem.get(position);
        if (attributeItem == null) {
            return;
        }

        holder.tvAttributeItemName.setText(attributeItem.getAttributeItemName());
        holder.tvAttributeItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mListProductAttributeItem.get(holder.getAdapterPosition()).setAttributeItemName(s.toString());
            }
        });

//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientAddress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListProductAttributeItem != null) {
            return mListProductAttributeItem.size();
        }
        return 0;
    }



    public class ProductAttributeItemViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeItemName;
        private final Button btnDel;
//        private final LinearLayout item;

        public ProductAttributeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttributeItemName = itemView.findViewById(R.id.attribute_item_name);
            btnDel = itemView.findViewById(R.id.del_btn);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (delListener != null && position != RecyclerView.NO_POSITION) {
                        delListener.onItemClickDel(mListProductAttributeItem.get(position));
                    }
                }
            });
//            btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Get pos
//
//                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
//                        mListProductAttributeItem.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mListProductAttributeItem.size());
//                    }
//                }
//            });
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickDelListener {
        void onItemClickDel(ProductAttributeItem attributeItem);
    }

    //Method item click listener
    public void setOnItemClickDelListener(OnItemClickDelListener delListener) {
        this.delListener = delListener;
    }



//    public interface OnItemClickDelListener{
//        void onItemClickDel(String attribute);
//    }
//    public void setOnItemClickDelListener(OnItemClickDelListener delListener){
//        this.delListener = delListener;
//    }
}