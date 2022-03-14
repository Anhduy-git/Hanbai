package com.example.androidapp.HelperClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.List;

public class PriceQuantityItemAdapter extends RecyclerView.Adapter<PriceQuantityItemAdapter.PriceQuantityItemViewHolder> {
    private List<PriceQuantityItem> mListPriceQuantityItem;

    //    private OnItemClickDelListener listener;
    private OnItemClickDelListener delListener;
    private OnItemClickAddListener addListener;

    public PriceQuantityItemAdapter(List<PriceQuantityItem> mListPriceQuantityItem) {
        this.mListPriceQuantityItem = mListPriceQuantityItem;
    }


    public void setPriceQuantityItem(List<PriceQuantityItem> mListPriceQuantityItem) {
        this.mListPriceQuantityItem = mListPriceQuantityItem;
        notifyDataSetChanged();
    }

    //Get the client position
    public PriceQuantityItem getAttributeAt(int position) {
        return mListPriceQuantityItem.get(position);
    }

    @NonNull
    @Override
    public PriceQuantityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_input_quantity_price, parent, false);

        return new PriceQuantityItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceQuantityItemViewHolder holder, int position) {
        PriceQuantityItem priceQuantityItem = mListPriceQuantityItem.get(position);
        if (priceQuantityItem == null) {
            return;
        }

        holder.tvType.setText(priceQuantityItem.getName());
        holder.tvQuantity.setText(String.valueOf(priceQuantityItem.getQuantity()));
        holder.tvPrice.setText(String.valueOf(priceQuantityItem.getPrice()));
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListPriceQuantityItem != null) {
            return mListPriceQuantityItem.size();
        }
        return 0;
    }



    public class PriceQuantityItemViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvType;
        private final TextView tvPrice;
        private final TextView tvQuantity;
//        private final LinearLayout item;

        public PriceQuantityItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.type);
            tvPrice = itemView.findViewById(R.id.price);
            tvQuantity = itemView.findViewById(R.id.quantity);
//            tvClientName = itemView.findViewById(R.id.client_name);
//            tvClientNumber = itemView.findViewById(R.id.client_number);
//            tvClientAddress = itemView.findViewById(R.id.client_address);
//            imageView = itemView.findViewById(R.id.client_avatar);
//            item = itemView.findViewById(R.id.client_item);

//            btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Get pos
//
//                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
//                        mListPriceQuantityItem.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mListPriceQuantityItem.size());
//                    }
//                }
//            });
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickDelListener {
        void onItemClickDel(PriceQuantityItem attribute);
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


}
