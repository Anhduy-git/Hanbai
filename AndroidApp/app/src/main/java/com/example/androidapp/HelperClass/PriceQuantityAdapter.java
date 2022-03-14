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

public class PriceQuantityAdapter extends RecyclerView.Adapter<PriceQuantityAdapter.PriceQuantityViewHolder> {
    private List<PriceQuantity> mListPriceQuantity;

    //    private OnItemClickDelListener listener;
    private OnItemClickDelListener delListener;
    private OnItemClickAddListener addListener;

    public PriceQuantityAdapter(List<PriceQuantity> mListPriceQuantity) {
        this.mListPriceQuantity = mListPriceQuantity;
    }


    public void setPriceQuantity(List<PriceQuantity> mListPriceQuantity) {
        this.mListPriceQuantity = mListPriceQuantity;
        notifyDataSetChanged();
    }

    //Get the client position
    public PriceQuantity getAttributeAt(int position) {
        return mListPriceQuantity.get(position);
    }

    @NonNull
    @Override
    public PriceQuantityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quantity_price, parent, false);

        return new PriceQuantityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceQuantityViewHolder holder, int position) {
        PriceQuantity priceQuantity = mListPriceQuantity.get(position);
        if (priceQuantity == null) {
            return;
        }

        holder.tvAttributeName.setText(priceQuantity.getAttribute1());
        setPriceQuantityItemRecycler(holder.itemLst, mListPriceQuantity.get(position).getAttribute2());
//        holder.tvClientNumber.setText(client.getClientNumber());
//        holder.tvClientress.setText(client.getClientAddress());

    }

    @Override
    public int getItemCount() {
        if (mListPriceQuantity != null) {
            return mListPriceQuantity.size();
        }
        return 0;
    }



    public class PriceQuantityViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView tvClientName;
//        private final TextView tvClientNumber;
        private final TextView tvAttributeName;

        private final RecyclerView itemLst;
//        private final LinearLayout item;

        public PriceQuantityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttributeName = itemView.findViewById(R.id.attribute_name);

            itemLst = itemView.findViewById(R.id.item_lst);
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
//                        mListPriceQuantity.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mListPriceQuantity.size());
//                    }
//                }
//            });
        }
    }

    //Interface to click on a dish item
    public interface OnItemClickDelListener {
        void onItemClickDel(PriceQuantity attribute);
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

    private void setPriceQuantityItemRecycler(RecyclerView recycler, List<PriceQuantityItem> PriceQuantityItemList) {
        PriceQuantityItemAdapter PriceQuantityItemAdapter = new PriceQuantityItemAdapter(PriceQuantityItemList);
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext(), RecyclerView.VERTICAL, false));
        recycler.setAdapter(PriceQuantityItemAdapter);

//        PriceQuantityItemAdapter.setOnItemClickDelListener(new PriceQuantityItemAdapter.OnItemClickDelListener() {
//            @Override
//            public void onItemClickDel(PriceQuantityItem PriceQuantityItem) {
//                PriceQuantityItemList.remove(PriceQuantityItem);
//                PriceQuantityItemAdapter.notifyDataSetChanged();
//            }
//        });

    }
}
