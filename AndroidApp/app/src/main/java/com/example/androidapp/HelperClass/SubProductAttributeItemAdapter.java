package com.example.androidapp.HelperClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class SubProductAttributeItemAdapter extends RecyclerView.Adapter<SubProductAttributeItemAdapter.SubProductAttributeItemViewHolder> {
    private List<ProductAttributeItem> mListSubProductAttributeItem;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    //    private OnItemClickDelListener listener;
    private OnItemClickListener clickListener;
    private boolean isClicked = false;

    public SubProductAttributeItemAdapter(List<ProductAttributeItem> mListSubProductAttributeItem) {
        this.mListSubProductAttributeItem = mListSubProductAttributeItem;
        selectCheck.add(1);
        for (int i = 1; i < mListSubProductAttributeItem.size(); i++) {
            selectCheck.add(0);
        }
    }


    public void setSubProductAttributeItem(List<ProductAttributeItem> mListSubProductAttributeItem) {
        this.mListSubProductAttributeItem = mListSubProductAttributeItem;
        //initilize selectCheck

        notifyDataSetChanged();
    }

    //Get the client position
    public ProductAttributeItem getAttributeItemAt(int position) {
        return mListSubProductAttributeItem.get(position);
    }

    @NonNull
    @Override
    public SubProductAttributeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_attribute, parent, false);

        return new SubProductAttributeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubProductAttributeItemViewHolder holder, int position) {
        ProductAttributeItem attributeItem = mListSubProductAttributeItem.get(position);
        if (attributeItem == null) {
            return;
        }

        holder.attributeItemName.setText(attributeItem.getAttributeItemName());

//        if (!isClicked && position == 0) {
//            holder.attributeItemName.setChecked(true);
//        }


        if (selectCheck.get(position) == 1) {
            holder.attributeItemName.setChecked(true);
        } else {
            holder.attributeItemName.setChecked(false);
        }




//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientAddress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListSubProductAttributeItem != null) {
            return mListSubProductAttributeItem.size();
        }
        return 0;
    }


    public class SubProductAttributeItemViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final CheckBox attributeItemName;
//        private final LinearLayout item;

        public SubProductAttributeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            attributeItemName = itemView.findViewById(R.id.attribute_item_name);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);
            attributeItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    isClicked = true;
                    for(int k = 0; k < selectCheck.size(); k++) {
                        if(k == getAdapterPosition()) {
                            selectCheck.set(k,1);
                        } else {
                            selectCheck.set(k,0);
                        }
                    }
                    int position = getAdapterPosition();
                    if (clickListener != null && position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(position);
                    }
                    notifyDataSetChanged();
                }
            });

//            btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Get pos
//
//                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
//                        mListSubProductAttributeItem.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mListSubProductAttributeItem.size());
//                    }
//                }
//            });

        }
    }

    //    //Interface to click on a dish item
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
    //
//    //Method item click listener
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


}