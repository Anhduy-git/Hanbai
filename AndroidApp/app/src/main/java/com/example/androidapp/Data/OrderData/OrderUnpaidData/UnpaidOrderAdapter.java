package com.example.androidapp.Data.OrderData.OrderUnpaidData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UnpaidOrderAdapter extends ListAdapter<UnpaidOrder,UnpaidOrderAdapter.UnpaidOrderViewHolder> {
    private List<UnpaidOrder> mListUnpaidOrder = new ArrayList<>();
    private OnItemClickListener listener;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public UnpaidOrderAdapter() {
        super(DIFF_CALLBACK);
        //Open 1 card only when delete
        viewBinderHelper.setOpenOnlyOne(true);
    }

    //setup for animation
    private static final DiffUtil.ItemCallback<UnpaidOrder> DIFF_CALLBACK = new DiffUtil.ItemCallback<UnpaidOrder>() {
        @Override
        public boolean areItemsTheSame(@NonNull UnpaidOrder oldItem, @NonNull UnpaidOrder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UnpaidOrder oldItem, @NonNull UnpaidOrder newItem) {
            return oldItem.getClient().getClientName().equals(newItem.getClient().getClientName()) &&
                    oldItem.getClient().getClientAddress().equals(newItem.getClient().getClientAddress()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTime().equals(newItem.getTime()) &&
                    oldItem.getClient().getClientNumber().equals(newItem.getClient().getClientNumber()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getPaid() == newItem.getPaid() &&
                    oldItem.getShip() == newItem.getShip();

//                    oldItem.getOrderListProduct().equals(newItem.getOrderListProduct());
        }
    };

    public void setOrder(List<UnpaidOrder> mListUnpaidOrder) {
        this.mListUnpaidOrder = mListUnpaidOrder;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UnpaidOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);

        return new UnpaidOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnpaidOrderViewHolder holder, int position) {
        UnpaidOrder unpaidOrder = getItem(position);
        if (unpaidOrder == null) {
            return;
        }


        holder.tvOrderName.setText(unpaidOrder.getClient().getClientName());
        holder.tvOrderTime.setText(unpaidOrder.getTime());
        holder.tvOrderPrice.setText(String.valueOf(unpaidOrder.getPrice()));
        //Read image from file
        if (unpaidOrder.getClient().getImageDir() != null) {
            try {
                File f = new File(unpaidOrder.getClient().getImageDir());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageView.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                Resources res = holder.imageView.getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ava_client_default);
                holder.imageView.setImageBitmap(bitmap);
            }
        }
    }

    public UnpaidOrder getOrderAt(int pos) {
        return getItem(pos);
    }

    public class UnpaidOrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrderName;
        private final TextView tvOrderTime;
        private final TextView tvOrderPrice;
        private final ImageView imageView;
        private final ImageView flagPaid;
        private final ImageView flagShip;
        private final SwipeRevealLayout swipeRevealLayout;
        private final RelativeLayout layoutDel;
        private final RelativeLayout item;

        public UnpaidOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            layoutDel = itemView.findViewById(R.id.order_item_del);
            tvOrderName = itemView.findViewById(R.id.order_name);
            tvOrderTime = itemView.findViewById(R.id.order_time);
            tvOrderPrice = itemView.findViewById(R.id.order_price);
            imageView = itemView.findViewById(R.id.order_avatar);
            flagPaid = itemView.findViewById(R.id.paid_icon);
            flagShip = itemView.findViewById(R.id.ship_icon);
            //This is the main layout in order_item_recycler
            item = itemView.findViewById(R.id.order_item);
            //Set onClick method for each item
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UnpaidOrder unpaidOrder);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}